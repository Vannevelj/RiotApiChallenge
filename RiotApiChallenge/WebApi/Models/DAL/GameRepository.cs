using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json;
using WebApi.Models.DAL.Interfaces;
using WebApi.Models.Riot;
using WebApi.Models.Utilities;
using WebApi.Models.Viewmodels;

namespace WebApi.Models.DAL
{
    public class GameRepository : IGameRepository
    {
        private const string ApiKey = "d20a2910-08fd-43c7-8ef1-e6308b938c8b";
        private const string BaseGameIdsUrl = "https://euw.api.pvp.net/api/lol/euw/v4.1/game/ids";
        private const string BaseMatchUrl = "https://euw.api.pvp.net/api/lol/euw/v2.2/match/";
        private readonly RiotContext _context;

        public GameRepository(RiotContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Game>> GetGamesAsync()
        {
            var lastDate = _context.Games.Select(x => x.PlayedOn).Distinct().OrderByDescending(x => x).FirstOrDefault() ?? new DateTime(2015, 04, 1, 08, 00, 00);
            lastDate = lastDate.AddMinutes(5);

            using (var client = new HttpClient())
            {
                // Epoch representation of the date we want to query. 
                // More details: https://developer.riotgames.com/api/methods#!/980/3340
                var matchIdsUrl = BaseGameIdsUrl + "?api_key=" + ApiKey + "&beginDate=" + lastDate.ToUnixTime();

                var matchIds = JsonConvert.DeserializeObject<IEnumerable<int>>(await (await client.GetAsync(matchIdsUrl)).Content.ReadAsStringAsync());

                var games = new List<Game>();
                foreach (var id in matchIds.Where(x => !_context.Games.Any(y => x == y.MatchId))) // Verify game isn't inserted yet
                {
                    var gameRequest = await client.GetAsync(BaseMatchUrl + id + "?api_key=" + ApiKey);
                    var game = JsonConvert.DeserializeObject<Game>(await gameRequest.Content.ReadAsStringAsync());
                    game.PlayedOn = lastDate;

                    if (!string.IsNullOrWhiteSpace(game.Region))
                    {
                        _context.Games.Add(game);
                        games.Add(game);
                    }
                }
                await _context.SaveChangesAsync();
                return games;
            }
        }

        public async Task<bool> InsertAnswerAsync(AnswerSubmission answer, string userId)
        {
            answer.IsCorrect = false;

            var user = await _context.Users.SingleOrDefaultAsync(x => x.Id == userId);
            if (user == null)
            {
                return false;
            }

            if (user.Answers.Any(x => x.MatchId == answer.MatchId))
            {
                return false;
            }

            var match = await _context.Games.FindAsync(answer.MatchId);
            if (match == null)
            {
                return false;
            }

            if (match.Teams.Single(x => x.Winner).TeamId == answer.WinningTeamId)
            {
                answer.IsCorrect = true;
            }

            user.Answers.Add(answer);
            await _context.SaveChangesAsync();
            return true;
        }

        public async Task<IEnumerable<Highscore>> GetHighscoresAsync(int page, int pageSize)
        {
            var results = (from user in _context.Users
                           let score = (from answer in user.Answers
                                        where answer.IsCorrect
                                        select answer).Count()
                           orderby score descending
                           select new Highscore
                           {
                               Score = score,
                               Username = user.UserName
                           }).Skip((page - 1)*pageSize).Take(pageSize);

            return await results.ToArrayAsync();
        }
    }
}
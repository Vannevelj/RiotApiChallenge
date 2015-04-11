using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json;
using WebApi.Models.DAL.Interfaces;
using WebApi.Models.Riot;
using WebApi.Models.Utilities;

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
    }
}
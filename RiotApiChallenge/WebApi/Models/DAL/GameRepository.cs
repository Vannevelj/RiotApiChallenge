using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json;
using WebApi.Models.DAL.Interfaces;
using WebApi.Models.Riot;

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
            // Check if database has recent games 
            // If yes: return data from database
            // If not: query api and store in database

            var games = new List<Game>();

            using (var client = new HttpClient())
            {
                // Epoch representation of the date we want to query. 
                // More details: https://developer.riotgames.com/api/methods#!/980/3340
                var randomDate = (new DateTime(2015, 04, 1, 12, 00, 00).Subtract(new DateTime(1970, 1, 1))).TotalSeconds;
                var matchIdsUrl = BaseGameIdsUrl + "?api_key=" + ApiKey + "&beginDate=" + randomDate;

                var matchIds = JsonConvert.DeserializeObject<IEnumerable<int>>(await (await client.GetAsync(matchIdsUrl)).Content.ReadAsStringAsync());

                foreach (var id in matchIds)
                {
                    var gameRequest = await client.GetAsync(BaseMatchUrl + id + "?api_key=" + ApiKey);
                    var game = JsonConvert.DeserializeObject<Game>(await gameRequest.Content.ReadAsStringAsync());

                    if (!string.IsNullOrWhiteSpace(game.Region)) // Null objects being returned for some reason
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
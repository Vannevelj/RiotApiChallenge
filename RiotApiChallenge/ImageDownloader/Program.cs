using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Nito.AsyncEx;

namespace ImageDownloader
{
    public class Program
    {
        public static void Main(string[] args)
        {
            AsyncContext.Run(() => new Program().MainAsync(args));
            Console.WriteLine("All done!");
            Console.Read();
        }

        public async Task MainAsync(string[] args)
        {
            var versions = (await GetVersionsAsync()).ToArray();
            var champions = (await GetChampionsAsync()).ToArray();

            foreach (var champion in champions)
            {
                foreach (var version in versions.OrderByDescending(x => x))
                {
                    var url = string.Format("http://ddragon.leagueoflegends.com/cdn/{0}/img/champion/{1}.png", version, champion.Item1);
                    var image = await GetImageAsync(url);
                    if (image != null)
                    {
                        using (var bitmap = new Bitmap(image))
                        {
                            var location = string.Format("C:\\Temp\\champion_{0}.png", champion.Item2);
                            bitmap.Save(location, ImageFormat.Png);

                            Console.WriteLine("Saved {0} to {1}", champion, location);
                            break;
                        }
                    }
                }
            }

            var summonerSpells = (await GetSummonerSpellsAsync()).ToArray();
            foreach (var summonerSpell in summonerSpells)
            {
                foreach (var version in versions.OrderByDescending(x => x))
                {
                    var url = string.Format("http://ddragon.leagueoflegends.com/cdn/{0}/img/spell/{1}.png", version, summonerSpell.Item1);
                    var image = await GetImageAsync(url);
                    if (image != null)
                    {
                        using (var bitmap = new Bitmap(image))
                        {
                            var location = string.Format("C:\\Temp\\summonerspell_{0}.png", summonerSpell.Item2);
                            bitmap.Save(location, ImageFormat.Png);

                            Console.WriteLine("Saved {0} to {1}", summonerSpell, location);
                            break;
                        }
                    }
                }
            }
        }

        public async Task<IEnumerable<string>> GetVersionsAsync()
        {
            const string url = "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/versions?api_key=d20a2910-08fd-43c7-8ef1-e6308b938c8b";
            using (var client = new HttpClient())
            {
                var response = await client.GetAsync(url);
                return JsonConvert.DeserializeObject<IEnumerable<string>>(await response.Content.ReadAsStringAsync());
            }
        }

        public async Task<IEnumerable<Tuple<string, string>>> GetChampionsAsync()
        {
            const string url = "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/champion?api_key=d20a2910-08fd-43c7-8ef1-e6308b938c8b";
            using (var client = new HttpClient())
            {
                var response = await client.GetAsync(url);
                var content = JsonConvert.DeserializeObject<JObject>(await response.Content.ReadAsStringAsync());
                return content["data"].Value<JObject>().Properties().Select(x => new Tuple<string, string>(x.Name, x.Value.Value<string>("id"))).ToArray();
            }
        }

        public async Task<IEnumerable<Tuple<string, string>>> GetSummonerSpellsAsync()
        {
            const string url = "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/summoner-spell?api_key=d20a2910-08fd-43c7-8ef1-e6308b938c8b";
            using (var client = new HttpClient())
            {
                var response = await client.GetAsync(url);
                var content = JsonConvert.DeserializeObject<JObject>(await response.Content.ReadAsStringAsync());
                return content["data"].Value<JObject>().Properties().Select(x => new Tuple<string, string>(x.Name, x.Value.Value<string>("id"))).ToArray();
            }
        }

        public async Task<Stream> GetImageAsync(string url)
        {
            using (var client = new HttpClient())
            {
                var response = await client.GetAsync(url);
                if (response.IsSuccessStatusCode)
                {
                    return await response.Content.ReadAsStreamAsync();
                }
                return null;
            }
        }
    }
}
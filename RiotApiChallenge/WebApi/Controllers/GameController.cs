using System.Collections.Generic;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using WebApi.Models.DAL.Interfaces;
using WebApi.Models.Riot;

namespace WebApi.Controllers
{
    [RoutePrefix("api/games")]
    public class GameController : ApiController
    {
        private readonly IGameRepository _gameRepository;
        private readonly IUserRepository _userRepository;

        public GameController(IUserRepository userRepository, IGameRepository gameRepository)
        {
            _userRepository = userRepository;
            _gameRepository = gameRepository;
        }

        [Route("")]
        [HttpGet]
        [ResponseType(typeof (IEnumerable<Game>))]
        public async Task<IHttpActionResult> GetNewGame()
        {
            return Ok(await _gameRepository.GetGamesAsync());
        }
    }
}
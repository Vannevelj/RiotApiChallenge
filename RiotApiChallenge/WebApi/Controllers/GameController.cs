using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using WebApi.Models.DAL;
using WebApi.Models.DAL.Interfaces;
using WebApi.Models.Riot;
using WebApi.Models.ValidationModels;

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

        [Route("")]
        [HttpPost]
        //[Authorize]
        [ResponseType(typeof (void))]
        public async Task<IHttpActionResult> SubmitAnswer([FromBody] AnswerSubmission answer)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(string.Join("\n", ModelState.Values.Select(x => x.Errors)));
            }

            if (await _gameRepository.InsertAnswerAsync(answer, new RiotContext().Users.First().Id)) // TODO: Authorization & Authentication
            {
                return Ok(answer.IsCorrect);
            }

            return BadRequest();
        }
    }
}
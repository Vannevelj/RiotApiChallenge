using System.Collections.Generic;
using System.Threading.Tasks;
using WebApi.Models.Riot;
using WebApi.Models.ValidationModels;

namespace WebApi.Models.DAL.Interfaces
{
    public interface IGameRepository
    {
        Task<IEnumerable<Game>> GetGamesAsync();
        Task<bool> InsertAnswerAsync(AnswerSubmission answer, string userId);
    }
}
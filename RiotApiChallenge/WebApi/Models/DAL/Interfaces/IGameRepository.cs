using System.Collections.Generic;
using System.Threading.Tasks;
using WebApi.Models.Riot;

namespace WebApi.Models.DAL.Interfaces
{
    public interface IGameRepository
    {
        Task<IEnumerable<Game>> GetGamesAsync();
    }
}
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNet.Identity;
using WebApi.Models.Users;
using WebApi.Models.Users.Authorization;

namespace WebApi.Models.DAL.Interfaces
{
    public interface IUserRepository
    {
        Task<IdentityResult> RegisterUserAsync(UserModel userModel);
        Task<ApplicationUser> FindUserAsync(string username, string password);
        Task<ClientApplication> FindClientAsync(string clientId);
        Task<bool> TryAddRefreshTokenAsync(RefreshToken refreshToken);
        Task<bool> TryRemoveRefreshTokenAsync(string refreshTokenId);
        Task<bool> TryRemoveRefreshTokenAsync(RefreshToken refreshToken);
        Task<RefreshToken> FindRefreshTokenAsync(string refreshTokenId);
        IEnumerable<RefreshToken> GetRefreshTokens();
        Task<bool> TryCreateClientAsync(ClientApplication clientApplication);
        Task<ApplicationUser> FindUserAsync(UserLoginInfo loginInfo);
        Task<IdentityResult> CreateAsync(ApplicationUser user);
        Task<IdentityResult> AddLoginAsync(string userId, UserLoginInfo loginInfo);
    }
}
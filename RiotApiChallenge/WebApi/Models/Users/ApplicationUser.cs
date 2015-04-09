using Microsoft.AspNet.Identity.EntityFramework;

namespace WebApi.Models.Users
{
    public class ApplicationUser : IdentityUser
    {
        public ApplicationUser()
        {
        }

        public ApplicationUser(string username) : base(username)
        {
        }
    }
}
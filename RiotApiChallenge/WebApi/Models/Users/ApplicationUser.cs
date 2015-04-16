using System.Collections.Generic;
using Microsoft.AspNet.Identity.EntityFramework;
using WebApi.Models.Viewmodels;

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

        public virtual ICollection<AnswerSubmission> Answers { get; set; }
    }
}
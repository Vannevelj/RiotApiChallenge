using System.Data.Entity.Migrations;
using WebApi.Models.DAL;
using WebApi.Models.Users.Authorization;

namespace WebApi.Migrations
{
    internal sealed class Configuration : DbMigrationsConfiguration<RiotContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = true;
        }

        protected override void Seed(RiotContext context)
        {
            context.ClientApplications.AddOrUpdate(new ClientApplication
            {
                Id = "default_web",
                IsActive = true,
                RefreshTokenLifeTime = 60*24*30, // 1 month
                AllowedOrigin = "*", //TODO: restrict to actual domain
                ApplicationType = ApplicationType.JavaScript,
                Name = "Default Web application"
            });
            context.SaveChanges();
        }
    }
}
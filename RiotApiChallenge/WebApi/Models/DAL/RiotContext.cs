using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity;
using Microsoft.AspNet.Identity.EntityFramework;
using WebApi.Models.Riot;
using WebApi.Models.Users;
using WebApi.Models.Users.Authorization;

namespace WebApi.Models.DAL
{
    public class RiotContext : IdentityDbContext<ApplicationUser>
    {
        public RiotContext() : base("name=riotcontext")
        {
        }

        public DbSet<ClientApplication> ClientApplications { get; set; }
        public DbSet<RefreshToken> RefreshTokens { get; set; }
        public DbSet<Game> Games { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<ClientApplication>().ToTable("ClientApplications");
            modelBuilder.Entity<ClientApplication>().HasKey(x => x.Id);
            modelBuilder.Entity<ClientApplication>().Property(x => x.Id).HasDatabaseGeneratedOption(DatabaseGeneratedOption.None);
            modelBuilder.Entity<ClientApplication>().Property(x => x.Name).IsRequired();
            modelBuilder.Entity<ClientApplication>().Property(x => x.Name).HasMaxLength(100);
            modelBuilder.Entity<ClientApplication>().Property(x => x.AllowedOrigin).HasMaxLength(100);

            modelBuilder.Entity<RefreshToken>().ToTable("Refreshtokens");
            modelBuilder.Entity<RefreshToken>().HasKey(x => x.Id);
            modelBuilder.Entity<RefreshToken>().Property(x => x.Id).HasDatabaseGeneratedOption(DatabaseGeneratedOption.None);
            modelBuilder.Entity<RefreshToken>().Property(x => x.Subject).IsRequired();
            modelBuilder.Entity<RefreshToken>().Property(x => x.Subject).HasMaxLength(50);
            modelBuilder.Entity<RefreshToken>().Property(x => x.ProtectedTicket).IsRequired();
            modelBuilder.Entity<RefreshToken>().HasRequired(x => x.ClientApplication).WithMany().HasForeignKey(x => x.ClientApplicationId);

            modelBuilder.Entity<Game>().HasKey(x => x.MatchId);
            modelBuilder.Entity<Game>().Property(x => x.MatchId).HasDatabaseGeneratedOption(DatabaseGeneratedOption.None);
            modelBuilder.Entity<Game>().HasMany(x => x.Participants).WithMany().Map(x =>
            {
                x.ToTable("GameParticipants");
                x.MapLeftKey("GameId");
                x.MapRightKey("ParticipantId");
            });

            modelBuilder.Entity<Game>().HasMany(x => x.Teams).WithMany().Map(x =>
            {
                x.ToTable("GameTeams");
                x.MapLeftKey("GameId");
                x.MapRightKey("TeamId");
            });

            modelBuilder.Entity<Participant>().HasKey(x => x.InternalParticipantId);
            modelBuilder.Entity<Participant>().Property(x => x.InternalParticipantId).HasDatabaseGeneratedOption(DatabaseGeneratedOption.Identity);

            modelBuilder.Entity<Team>().HasKey(x => x.InternalTeamId);
            modelBuilder.Entity<Team>().Property(x => x.InternalTeamId).HasDatabaseGeneratedOption(DatabaseGeneratedOption.Identity);
        }
    }
}
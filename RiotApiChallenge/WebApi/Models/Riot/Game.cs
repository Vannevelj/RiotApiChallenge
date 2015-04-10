using System.Collections.Generic;
using Newtonsoft.Json;

// Generated the models using http://json2csharp.com/
// Sue me

namespace WebApi.Models.Riot
{
    public class Participant
    {
        [JsonIgnore]
        public int InternalParticipantId { get; set; }

        public int TeamId { get; set; }
        public int Spell1Id { get; set; }
        public int Spell2Id { get; set; }
        public int ChampionId { get; set; }
        public string HighestAchievedSeasonTier { get; set; }
    }

    public class Team
    {
        [JsonIgnore]
        public int InternalTeamId { get; set; }

        public int TeamId { get; set; }
        public bool Winner { get; set; }
        public bool FirstBlood { get; set; }
        public bool FirstTower { get; set; }
        public bool FirstInhibitor { get; set; }
        public bool FirstBaron { get; set; }
        public bool FirstDragon { get; set; }
        public int TowerKills { get; set; }
        public int InhibitorKills { get; set; }
        public int BaronKills { get; set; }
        public int DragonKills { get; set; }
        public int VilemawKills { get; set; }
        public int DominionVictoryScore { get; set; }
    }

    public class Game
    {
        public int MatchId { get; set; }
        public string Region { get; set; }
        public string PlatformId { get; set; }
        public string MatchMode { get; set; }
        public string MatchType { get; set; }
        public long MatchCreation { get; set; }
        public int MatchDuration { get; set; }
        public string QueueType { get; set; }
        public int MapId { get; set; }
        public string Season { get; set; }
        public string MatchVersion { get; set; }
        public virtual ICollection<Participant> Participants { get; set; }
        public virtual ICollection<Team> Teams { get; set; }
    }
}
using System;
using System.Collections.Generic;

namespace WebApi.Models.Riot
{
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
        public DateTime? PlayedOn { get; set; }
        public virtual ICollection<Participant> Participants { get; set; }
        public virtual ICollection<Team> Teams { get; set; }
    }
}
using Newtonsoft.Json;

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
}
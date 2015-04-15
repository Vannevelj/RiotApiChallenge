using System.ComponentModel.DataAnnotations;
using Newtonsoft.Json;

namespace WebApi.Models.ValidationModels
{
    public class AnswerSubmission
    {
        [JsonIgnore]
        public int AnswerId { get; set; }

        [Required]
        public int MatchId { get; set; }

        [Required]
        public int WinningTeamId { get; set; }

        [JsonIgnore]
        public bool IsCorrect { get; set; }
    }
}
using System;

namespace WebApi.Models.Utilities
{
    public static class Extensions
    {
        public static long ToUnixTime(this DateTime date)
        {
            return (long) (date.Subtract(new DateTime(1970, 1, 1, 0, 0, 0))).TotalSeconds;
        }
    }
}
using System;
using System.Security.Cryptography;
using System.Text;

namespace WebApi.Models.Utilities
{
    public class AuthorizationHelpers
    {
        public static string GetHash(string input)
        {
            var algorithm = new SHA256CryptoServiceProvider();
            var byteValue = Encoding.UTF8.GetBytes(input);
            var byteHash = algorithm.ComputeHash(byteValue);
            return Convert.ToBase64String(byteHash);
        }
    }
}
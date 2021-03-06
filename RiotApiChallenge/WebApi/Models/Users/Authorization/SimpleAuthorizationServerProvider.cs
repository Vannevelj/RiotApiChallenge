﻿using System.Collections.Generic;
using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.Owin.Security;
using Microsoft.Owin.Security.OAuth;
using WebApi.Models.DAL.Interfaces;
using WebApi.Models.Utilities;

namespace WebApi.Models.Users.Authorization
{
    public class SimpleAuthorizationServerProvider : OAuthAuthorizationServerProvider
    {
        private readonly IUserRepository _userRepository;

        public SimpleAuthorizationServerProvider(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        public override async Task ValidateClientAuthentication(OAuthValidateClientAuthenticationContext context)
        {
            string clientId, clientSecret;
            if (!context.TryGetBasicCredentials(out clientId, out clientSecret))
            {
                context.TryGetFormCredentials(out clientId, out clientSecret);
            }

            if (clientId == null)
            {
                context.SetError("client_id_not_found", "ClientID should be sent.");
                return;
            }

            var clientApplication = await _userRepository.FindClientAsync(clientId);
            if (clientApplication == null)
            {
                context.SetError("invalid_client_id", string.Format("Client '{0}' is not registered in the system.", clientId));
                return;
            }

            // Only native apps are supposed to contain a secret
            // Javascript apps cannot store the secret safely anyway
            if (clientApplication.ApplicationType == ApplicationType.NativeConfidential)
            {
                if (string.IsNullOrWhiteSpace(clientSecret))
                {
                    context.SetError("client_secret_not_found", "Client secret should be sent.");
                    return;
                }

                if (clientApplication.Secret != AuthorizationHelpers.GetHash(clientSecret))
                {
                    context.SetError("invalid_client_secret", "Client secret is invalid");
                    return;
                }
            }

            if (!clientApplication.IsActive)
            {
                context.SetError("client_inactive", "Client is inactive");
                return;
            }

            context.OwinContext.Set("as:clientAllowedOrigin", clientApplication.AllowedOrigin);
            context.OwinContext.Set("as:clientRefreshTokenLifetime", clientApplication.RefreshTokenLifeTime.ToString());
            context.Validated();
        }

        public override async Task GrantResourceOwnerCredentials(OAuthGrantResourceOwnerCredentialsContext context)
        {
            var allowedOrigin = context.OwinContext.Get<string>("as:clientAllowedOrigin") ?? "*";
            context.OwinContext.Response.Headers.Add("Access-Control-Allow-Origin", new[] { allowedOrigin });

            var user = await _userRepository.FindUserAsync(context.UserName, context.Password);
            if (user == null)
            {
                context.SetError("invalid_grant", "The username or password is incorrect.");
                return;
            }

            var identity = new ClaimsIdentity(context.Options.AuthenticationType);
            identity.AddClaim(new Claim(ClaimTypes.Name, context.UserName));
            identity.AddClaim(new Claim("sub", context.UserName));
            identity.AddClaim(new Claim("role", "user"));

            var authenticationProperties = new AuthenticationProperties(new Dictionary<string, string>
            {
                { "as:client_id", context.ClientId ?? string.Empty },
                { "username", context.UserName }
            });
            var ticket = new AuthenticationTicket(identity, authenticationProperties);
            context.Validated(ticket);
        }

        public override Task TokenEndpoint(OAuthTokenEndpointContext context)
        {
            foreach (var property in context.Properties.Dictionary)
            {
                context.AdditionalResponseParameters.Add(property.Key, property.Value);
            }
            return Task.FromResult<object>(null);
        }

        public override Task GrantRefreshToken(OAuthGrantRefreshTokenContext context)
        {
            var originalClientId = context.Ticket.Properties.Dictionary["as:client_id"];
            var currentClientId = context.ClientId;

            if (originalClientId != currentClientId)
            {
                context.SetError("invalid_client_id", "Refresh token is issued to a different client");
                return Task.FromResult<object>(null);
            }

            var newIdentity = new ClaimsIdentity(context.Ticket.Identity);
            newIdentity.AddClaim(new Claim("newClaim", "newValue"));

            var newTicket = new AuthenticationTicket(newIdentity, context.Ticket.Properties);
            context.Validated(newTicket);
            return Task.FromResult<object>(null);
        }
    }
}
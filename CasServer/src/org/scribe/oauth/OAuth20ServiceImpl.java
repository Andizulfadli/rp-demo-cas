package org.scribe.oauth;

import org.jasig.cas.support.oauth.authentication.handler.support.OAuthAuthenticationHandler;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OAuth20ServiceImpl implements OAuthService
{
  private static final String VERSION = "2.0";
  
  private final DefaultApi20 api;
  private final OAuthConfig config;
  private static final Logger logger = LoggerFactory.getLogger(OAuthAuthenticationHandler.class);
  /**
   * Default constructor
   * 
   * @param api OAuth2.0 api information
   * @param config OAuth 2.0 configuration param object
   */
  public OAuth20ServiceImpl(DefaultApi20 api, OAuthConfig config)
  {
    this.api = api;
    this.config = config;
  }

  /**
   * {@inheritDoc}
   */
  public Token getAccessToken(Token requestToken, Verifier verifier)
  {
	  logger.debug("**********************  api.getAccessTokenVerb() {}", api.getAccessTokenVerb());
	  logger.debug("api.getAccessTokenEndpoint() {}", api.getAccessTokenEndpoint());
	  logger.debug("CLIENT_ID {}", config.getApiKey());
	  logger.debug("CLIENT_SECRET {}", config.getApiSecret());
	  logger.debug("CODE {}", verifier.getValue());
	  logger.debug("REDIRECT_URI {}", config.getCallback());
    OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
    request.addQuerystringParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
    request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
    request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue());
    request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
    if(config.hasScope()) request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope());
    Response response = request.send();
    logger.debug("response code {}", response.getCode());
    logger.debug("response {}", response.getBody());
    return api.getAccessTokenExtractor().extract(response.getBody());
  }

  /**
   * {@inheritDoc}
   */
  public Token getRequestToken()
  {
    throw new UnsupportedOperationException("Unsupported operation, please use 'getAuthorizationUrl' and redirect your users there");
  }

  /**
   * {@inheritDoc}
   */
  public String getVersion()
  {
    return VERSION;
  }

  /**
   * {@inheritDoc}
   */
  public void signRequest(Token accessToken, OAuthRequest request)
  {
    request.addQuerystringParameter(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
  }

  /**
   * {@inheritDoc}
   */
  public String getAuthorizationUrl(Token requestToken)
  {
    return api.getAuthorizationUrl(config);
  }

}

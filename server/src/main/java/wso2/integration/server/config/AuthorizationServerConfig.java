package wso2.integration.server.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import wso2.integration.server.domain.User;
import wso2.integration.server.log.LoggableDispatcherServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer
		        .inMemory()
		        .withClient(clientId)
		        .secret(clientSecret)
		        .authorizedGrantTypes(grantType)
		        .scopes("openid")
		        .resourceIds(resourceIds);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(
				claimsTokenEnhancer(), accessTokenConverter, idTokenTokenEnhancer()));
		endpoints.tokenStore(tokenStore)
				.tokenEnhancer(enhancerChain)
				.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer)throws Exception {
		oauthServer.allowFormAuthenticationForClients();
		oauthServer.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()");
	}

	@Bean
	public ServletRegistrationBean dispatcherRegistration() {
		return new ServletRegistrationBean(dispatcherServlet());
	}

	@Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
	public DispatcherServlet dispatcherServlet() {
		return new LoggableDispatcherServlet();
	}

	@Bean
	public TokenEnhancer claimsTokenEnhancer(){
		return (accessToken, oAuth2Authentication) -> {
            Map<String, Object> additionalJwtProperties = new HashMap();
			long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000l;
			additionalJwtProperties.put("iss", "http://localhost:7070");
			User user = (User) userDetailsService.loadUserByUsername(((UserDetails) oAuth2Authentication.getUserAuthentication().getPrincipal()).getUsername());
            additionalJwtProperties.put("sub", user.getUsername());
			additionalJwtProperties.put("aud", clientId);
			additionalJwtProperties.put("auth_time", now);
			additionalJwtProperties.put("iat", now);
			additionalJwtProperties.put("exp", accessToken.getExpiresIn());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalJwtProperties);
            return accessToken;
        };
	}

	@Bean
	public TokenEnhancer idTokenTokenEnhancer(){
		return (accessToken, oAuth2Authentication) -> {
			Map<String, Object> additionalJwtProperties = new HashMap();
			additionalJwtProperties.put("id_token", accessToken.getValue());
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalJwtProperties);
			return accessToken;
		};
	}

}

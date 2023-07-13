package com.juzi.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Collections;

/**
 * @author codejuzi
 */
@Configuration
@SuppressWarnings("deprecation")
public class TokenConfig {

    private static final String SIGNING_KEY = "auth-demo-secret";

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean(name = "authorizationServerTokenServicesCustom")
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices service = new DefaultTokenServices();
        service.setSupportRefreshToken(true);
        service.setTokenStore(tokenStore());

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(accessTokenConverter()));
        service.setTokenEnhancer(tokenEnhancerChain);


        service.setAccessTokenValiditySeconds(7200);
        service.setRefreshTokenValiditySeconds(259200);
        return service;
    }
}

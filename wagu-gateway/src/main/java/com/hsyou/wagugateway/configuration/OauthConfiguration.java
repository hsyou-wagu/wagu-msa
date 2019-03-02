package com.hsyou.wagugateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;


@Configuration
public class OauthConfiguration {

    @Value("${oauth.google.client.id}")
    private String clientId;

    @Value("${oauth.google.client.secret}")
    private String secret;

    @Value("${oauth.google.client.scope}")
    private String scope;

    @Value("${oauth.google.client.redirect_url}")
    private String redirect_url;

    @Bean
    GoogleConnectionFactory googleConnectionFactory(){
        return new GoogleConnectionFactory(clientId,secret);

    }

    @Bean
    OAuth2Parameters oAuth2Parameters(){
        OAuth2Parameters oAuth2Parameters=new OAuth2Parameters();
        oAuth2Parameters.setScope(scope);
        oAuth2Parameters.setRedirectUri(redirect_url);
        return oAuth2Parameters;
    }
}

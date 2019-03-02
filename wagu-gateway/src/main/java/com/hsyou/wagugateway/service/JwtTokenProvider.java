package com.hsyou.wagugateway.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hsyou.wagugateway.model.AccountDTO;
import com.hsyou.wagugateway.model.TokenClaim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private GoogleConnectionFactory googleConnectionFactory;
    @Autowired
    private OAuth2Parameters oAuth2Parameters;


    private OAuth2Operations oAuth2Operations;


    private static final long TTL = 24* 60 * 60 * 1000; // 24 hour

    public String createJWT(AccountDTO account){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("wagu-auth")
                    .withClaim("id", account.getId())
                    .withClaim("email", account.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis()+TTL))
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception){
            return "";
        }
    }

    public String getLoginURL(){
        oAuth2Operations= googleConnectionFactory.getOAuthOperations();
        String url = oAuth2Operations.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);

        return url;
    }
    public Person getProfileFromAuthServer(String code){
        oAuth2Operations = googleConnectionFactory.getOAuthOperations();
        AccessGrant accessGrant = oAuth2Operations.exchangeForAccess(code, oAuth2Parameters.getRedirectUri(),
                null);

        Connection<Google> connection = googleConnectionFactory.createConnection(accessGrant);
        Google google = connection == null ? new GoogleTemplate( ) : connection.getApi();

        PlusOperations plusOperations = google.plusOperations();
        return plusOperations.getGoogleProfile();

    }

    public TokenClaim validateTokenAndGetClaims (String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("wagu-auth")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            if(jwt.getExpiresAt().before(new Date())){
                throw new ExpiredAuthorizationException("Expired");
            }



            Map<String, Claim> claims = JWT.decode(token).getClaims();    //Key is the Claim name
            TokenClaim tokenClaim = TokenClaim.builder()
                    .accountId(claims.get("id").asLong())
                    .accountEmail(claims.get("email").asString())
                    .build();
            return tokenClaim;
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            throw new JWTVerificationException(exception.toString());
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}

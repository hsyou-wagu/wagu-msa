package com.hsyou.wagugateway;

import com.hsyou.wagugateway.model.AccountDTO;
import com.hsyou.wagugateway.model.TokenClaim;
import com.hsyou.wagugateway.service.JwtTokenProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.secret}")
    private String secret;
    private static final long TTL = 60 * 1000; // 1 min
    private final long userId = 1;
    private final String userEmail = "test@test.com";

    @Test
    public void 토큰만들기(){
        //given
        AccountDTO accountDTO=AccountDTO.builder()
                .id(userId)
                .email(userEmail)
                .build();

        //when
        String token = jwtTokenProvider.createJWT(accountDTO);

        //then
        assertThat(token).isNotEmpty();

    }

    @Test
    public void 토큰검증하기(){
        //given
        AccountDTO accountDTO=AccountDTO.builder()
                .id(userId)
                .email(userEmail)
                .build();
        String token = jwtTokenProvider.createJWT(accountDTO);

        //when
        TokenClaim tokenClaim= jwtTokenProvider.validateTokenAndGetClaims(token);

        //then
        assertThat(tokenClaim.getAccountId()).isEqualTo(userId);
        assertThat(tokenClaim.getAccountEmail()).isEqualTo(userEmail);

    }
}

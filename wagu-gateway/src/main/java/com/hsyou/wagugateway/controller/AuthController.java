package com.hsyou.wagugateway.controller;


import com.hsyou.wagugateway.model.AccountDTO;
import com.hsyou.wagugateway.service.AuthService;
import com.hsyou.wagugateway.service.JwtTokenProvider;
import org.apache.commons.lang3.concurrent.CircuitBreakingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.google.api.plus.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/auth")
public class AuthController {

//    @Autowired
//    private AccountService accountService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String login () throws IOException {

        return jwtTokenProvider.getLoginURL();
    }


    @GetMapping("/googlecallback")
    public ResponseEntity callback(@RequestParam String code, HttpServletRequest request, HttpServletResponse response){

        try {

            Person profile = jwtTokenProvider.getProfileFromAuthServer(code);
            AccountDTO rstAccnt = authService.requestUserInfo(profile);

            if(rstAccnt==null){
                return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
            }
            String token = jwtTokenProvider.createJWT(rstAccnt);

            HttpHeaders responseHeaders = new HttpHeaders();
            if (rstAccnt.getName() == null) {
                responseHeaders.setLocation(new URI("http://localhost:8765/account/info/"+token));
            } else {
                responseHeaders.setLocation(new URI("http://localhost:8765/done/"+token));
            }

            return new ResponseEntity<>(responseHeaders, HttpStatus.MOVED_PERMANENTLY);
        }catch (URISyntaxException ex){
            return new ResponseEntity<>(HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
        }catch (Exception ex){
            System.out.println(ex.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}

package com.hsyou.wagugateway;

import com.hsyou.wagugateway.model.TokenClaim;
import com.hsyou.wagugateway.service.JwtTokenProvider;
import com.netflix.zuul.context.RequestContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    public JwtFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String requestURI = request.getRequestURI();
        if(requestURI.startsWith("/auth")){
            filterChain.doFilter(request,response);
        }

        String token = jwtTokenProvider.resolveToken(request);
        System.out.println("Filter "+token);


        if(token == null){
            System.out.println("token is null");

            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            filterChain.doFilter(request,response);
        }else {

            try {

                TokenClaim tokenClaim = jwtTokenProvider.validateTokenAndGetClaims(token);
                long id = tokenClaim.getAccountId();
                String email = tokenClaim.getAccountEmail();
                UsernamePasswordAuthenticationToken auth;
                auth = new UsernamePasswordAuthenticationToken(id, null, null);

                System.out.println("Set Auth - " + id);
                SecurityContextHolder.getContext().setAuthentication(auth);

                    Map<String, List<String>> newParameterMap = new HashMap<>();
                    Map<String, String[]> parameterMap = request.getParameterMap();
                    //getting the current parameter
                    for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                        String key = entry.getKey();
                        String[] values = entry.getValue();
                        newParameterMap.put(key, Arrays.asList(values));
                    }
                    //add a new parameter

                    newParameterMap.put("accountId",Arrays.asList(String.valueOf(id)));
                    newParameterMap.put("accountEmail",Arrays.asList(String.valueOf(email)));
                    RequestContext.getCurrentContext().setRequestQueryParams(newParameterMap);


                filterChain.doFilter(request, response);
            } catch (Exception e) {


                e.printStackTrace();
                SecurityContextHolder.clearContext();

                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                filterChain.doFilter(request, response);
            }
        }
    }
}

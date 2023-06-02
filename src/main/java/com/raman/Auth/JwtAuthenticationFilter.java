package com.raman.Auth;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days

    @Autowired
    private AuthenticationManager authenticationManager;

   @Override
protected void successfulAuthentication(jakarta.servlet.http.HttpServletRequest request,
		jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain chain,
		Authentication authResult) throws IOException, jakarta.servlet.ServletException {
	   
	   String token = Jwts.builder()
               .setSubject(((User) authResult.getPrincipal()).getUsername())
               .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
               .signWith(SignatureAlgorithm.HS512, "YourSecretKey") // Replace with your secret key
               .compact();
       response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
}
   
   @Override
public Authentication attemptAuthentication(jakarta.servlet.http.HttpServletRequest request,
		jakarta.servlet.http.HttpServletResponse response) throws AuthenticationException {
	   String username = request.getParameter("username");
       String password = request.getParameter("password");
       if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
           throw new RuntimeException("Username or password missing");
       }
       return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
}
   
}

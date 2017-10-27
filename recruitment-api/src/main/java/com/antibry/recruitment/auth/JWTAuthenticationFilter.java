package com.antibry.recruitment.auth;

import static com.antibry.recruitment.auth.SecurityConstants.CLAIM_ROLES;
import static com.antibry.recruitment.auth.SecurityConstants.EXPIRATION_TIME;
import static com.antibry.recruitment.auth.SecurityConstants.HEADER_STRING;
import static com.antibry.recruitment.auth.SecurityConstants.SECRET;
import static com.antibry.recruitment.auth.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.antibry.recruitment.domain.acl.UserAccount;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
	private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        
    		System.out.println("DEBUG| HERE");
    		
    		try {
            UserAccount creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UserAccount.class);

            System.out.println("DEBUG| user: " + creds.getUsername());
            System.out.println("DEBUG| password: " + creds.getPassword());
            
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
    		
    		User user = (User) auth.getPrincipal();
    		
    		Claims claims = Jwts.claims().setSubject(user.getUsername());
    		claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
    		claims.put(CLAIM_ROLES, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")));
    	
        String token = Jwts.builder()
        			.setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
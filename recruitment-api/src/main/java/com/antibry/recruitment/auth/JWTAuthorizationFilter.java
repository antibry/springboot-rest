package com.antibry.recruitment.auth;

import static com.antibry.recruitment.auth.SecurityConstants.CLAIM_ROLES;
import static com.antibry.recruitment.auth.SecurityConstants.HEADER_STRING;
import static com.antibry.recruitment.auth.SecurityConstants.SECRET;
import static com.antibry.recruitment.auth.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
        	
        		Claims claims = Jwts.parser()
        				.setSigningKey(SECRET.getBytes())
        				.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
        				.getBody();
        	
        		String user = claims.getSubject();
        		String role = claims.get(CLAIM_ROLES, String.class);
        		
        		List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(role);

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, authorityList);
            }
            return null;
        }
        return null;
    }
}

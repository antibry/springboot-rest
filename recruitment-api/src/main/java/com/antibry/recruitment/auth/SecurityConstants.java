package com.antibry.recruitment.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
public class SecurityConstants {
	
	@Value("${spring.security.jwt.token.validity-time}")
	private static Long expirationTime;
	
	public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CLAIM_ROLES = "roles";
}

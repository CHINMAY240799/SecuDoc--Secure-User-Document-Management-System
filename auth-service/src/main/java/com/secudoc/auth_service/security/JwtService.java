package com.secudoc.auth_service.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder ) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    // This method returns a JWT. Header and Claims is build below and JWTEndocder already has the signature.
    public String generateToken(String username) {

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("secure-doc")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(username)
                .claim("scope", "USER")
                .build();
        
     // IMPORTANT: specify HS256 explicitly
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,claims))
                .getTokenValue();
    }
    
    // ---------- VALIDATE TOKEN ----------
    public Jwt validateToken(String token) {
        try {
            return jwtDecoder.decode(token); // verifies signature + expiry automatically
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isTokenValid(String token) {
        return validateToken(token) != null;
    }

    public String extractUsername(String token) {
        Jwt jwt = validateToken(token);
        return jwt != null ? jwt.getSubject() : null;
    }

}





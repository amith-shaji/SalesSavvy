package com.salessavvy.backend.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.salessavvy.backend.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${JWT_SECRET}")  // Get the value stored in the JWT_SECRET environment variable
    private String secretKey; // Put that value into this variable

    private SecretKey getSigningKey() {
        // Convert our secret String into bytes because the cryptographic library needs bytes
        byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);

        // Convert those bytes into a SecretKey that JJWT can use to sign the token
        return Keys.hmacShaKeyFor(key); // create a cryptographic key suitable for HMAC signing
    }

    // JWT has three fields Header, Payload and signature (xxxxx.yyyyy.zzzzz)
    // The header tells us how the JWT is signed like signing algorithm
    // The payload contains information/claims about the user and token.
    // The signature is what allows the server to detect that the JWT hasn't been modified. Header + Payload + SecretKey through an algorithm is signature

    public String generateToken(User user) {
        return Jwts.builder()  // The main API like construction tool, to which we chain things
             .subject(user.getEmail()) // Standard claim, sub means Who/what is this token about?
             .claim("role", user.getRole().getName()) // Custom claim
             .expiration(new Date(System.currentTimeMillis() + 3600000)) // One hour from now
             .signWith(getSigningKey())
             .compact(); // Takes everything and creates JWT String
}

   // JJWT can parse the token using our signing key. The important part is that parsing with the signing key also verifies the signature.
   public String extractEmail(String token) {
       return Jwts.parser()
       .verifyWith(getSigningKey())
       .build()
       .parseSignedClaims(token)
       .getPayload()
       .getSubject();
            
   }
}

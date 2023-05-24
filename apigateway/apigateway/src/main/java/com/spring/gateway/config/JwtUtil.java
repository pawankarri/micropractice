package com.spring.gateway.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {
    public static final String SECRET="556A586E3272357538782F413F4428472B4B6250655368566D59703373367639";


    public void validateToken(String token)
    {
        Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
//    public String generateToken(String userName) {
//        Map<String,Object> claims=new HashMap<>();
//        return createToken(claims,userName);
//    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();
    }


}

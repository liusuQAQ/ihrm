package com.ihrm.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class ParseJwtTest {
    public static void main(String[] args) {
        String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE2ODU5NjUxMzh9.AOj_gxO3Ay-5XZRKZuaFQC-x794hV0E95IbG83qNxTg";
        Claims claims = Jwts.parser().setSigningKey("ihrm").parseClaimsJws(token).getBody();
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        System.out.println("IssuedAt:"+claims.getIssuedAt());
    }

}

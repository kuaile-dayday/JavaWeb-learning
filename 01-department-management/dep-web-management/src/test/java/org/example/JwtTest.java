package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {


    /*
     * 生成JWT令牌
     * */
    @Test
    public void testGenerateJwt(){

        Map<String, Object> datamap = new HashMap<>();
        datamap.put("username","admin");
        datamap.put("id","1");

        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "aXRoZWltYQ==") // 指定加密算法，密钥
                .addClaims(datamap)// 添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600))// 设置过期时间
                .compact();// 生成令牌
        System.out.println(jwt);

    }

    /*
     * 解析JWT令牌
     * */
    @Test
    public void testParseJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJ1c2VybmFtZSI6ImFkbWluIiwiZXhwIjoxNzU1NTk1MjM5fQ.NCMPb1wpyvjv-JBNvRIB_mwhWs1oE_BE6PO9SBSRP28";
        Claims claims = Jwts.parser().setSigningKey("aXRoZWltYQ==")// 指定密钥
                .parseClaimsJws(token) // 解析令牌
                .getBody(); // 获取自定义信息
        System.out.println(claims);
    }


}

package com.swk.cpanel.api.util;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtUtil {
	
	/**
	 * 创建jwt_token
	 * @param claims 需要保存到playload(负载)的数据
	 * @param secretKey 加密key
	 * @param expired 超时时间
	 * @return
	 */
	public static String createJWT(Map<String, Object> claims,String secretKey,long expired){//secretKey
		long now = System.currentTimeMillis();
		JwtBuilder builder = Jwts.builder();
		if(claims!=null){
			builder.setClaims(claims);
		}
		if(expired>0){
			builder.setExpiration(new Date(now+expired));
		}
		return builder.setIssuedAt(new Date(now))
			.signWith(SignatureAlgorithm.HS512, secretKey)
			.compact();
	}
	
	/**
	 * 解析jwt_token 
	 * @param jwt  token
	 * @param secretKey  加密key
	 * @return
	 */
	public static Claims parseJWT(String jwt,String secretKey) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException{
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(jwt)
				.getBody();
	}
	
}

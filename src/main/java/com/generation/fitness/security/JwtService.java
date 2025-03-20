package com.generation.fitness.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
 
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	// Constante, uma cha 256 que será a chave de codificação do token
	public static final String SECRET = "ab276a42ea69da1b595b581d4ca2417321b5f728b7c63f0b650f58dc214f128c";

	// Chave secret foi codificada no base 68
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	// É como abrir o pacote
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey()).build()
				.parseClaimsJws(token).getBody();
	}
	// Vai extrair os claims --> informações do token 
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration); // Retorna a data de expiração do token
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date()); // Verá se o token expirou 
	}

	public Boolean validateToken(String token, UserDetails userDetails) {                                                                   
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private String createToken(Map<String, Object> claims, String userName) { //Construindo o token do 0 e validando
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(userName)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
					.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
		/*
		 * data atual + 100 *60 *60 = 1h - vai depender do tempo que você quer que dure o token
		 */
	}

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

};
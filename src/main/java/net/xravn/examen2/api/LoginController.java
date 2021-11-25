package net.xravn.examen2.api;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.xravn.examen2.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController("api/login")
public class LoginController {

	@PostMapping()
	public User login(@RequestBody User usuario) {
		String token = getJWTToken(usuario.getUser());
		usuario.setToken(token);
		usuario.setPassword(null);		
		return usuario;
	}

	private String getJWTToken(String username) {
        // usualmente se guarda en una variable de entorno
        // pero aqui lo hacemos de manera fija
        // por motivos de ejemplo
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");

		// recuperamos el usuario de la base de datos
		
		String token = Jwts
				.builder()
				.setId("examenJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
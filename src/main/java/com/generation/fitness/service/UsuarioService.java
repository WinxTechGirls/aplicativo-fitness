package com.generation.fitness.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.blogpessoal.model.UsuarioLogin;
import com.generation.fitness.model.Produto;
import com.generation.fitness.model.Usuario;
import com.generation.fitness.repository.ProdutoRepository;
import com.generation.fitness.repository.UsuarioRepository;
import com.generation.fitness.security.JwtService;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public Usuario cadastrarUsuario(Usuario usuario) {

		Optional<Usuario> optUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());
		if (optUsuario.isPresent())
			return optUsuario.get();

		Optional<Produto> optProduto = produtoRepository.findById(usuario.getProduto().getId());

		if (optProduto.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não existe!", null);
		}

		Produto produto = optProduto.get();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		usuario.setInicio(LocalDateTime.now());

		LocalDateTime dataFinal = null;

		if ("mensal".equalsIgnoreCase(produto.getTipoAssinatura())) {
			dataFinal = usuario.getInicio().plusMonths(1);
		} else if ("trimestral".equalsIgnoreCase(produto.getTipoAssinatura())) {
			dataFinal = usuario.getInicio().plusMonths(3);
		} else {
			dataFinal = usuario.getInicio().plusYears(1);
		}

		usuario.setTempoEstimado(dataFinal);

		return usuarioRepository.save(usuario);
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

		if (usuarioRepository.findById(usuario.getId()).isPresent()) {

			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

			if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != usuario.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			return Optional.ofNullable(usuarioRepository.save(usuario));

		}

		return Optional.empty();

	}

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(),
				usuarioLogin.get().getSenha());

		Authentication authentication = authenticationManager.authenticate(credenciais);

		if (authentication.isAuthenticated()) {

			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

			if (usuario.isPresent()) {

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));
				usuarioLogin.get().setSenha("");

				return usuarioLogin;

			}

		}

		return Optional.empty();

	}

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);

	}

	private String gerarToken(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario);
	}

}
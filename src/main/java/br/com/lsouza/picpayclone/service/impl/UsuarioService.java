package br.com.lsouza.picpayclone.service.impl;

import br.com.lsouza.picpayclone.conversor.UsuarioConversor;
import br.com.lsouza.picpayclone.dto.UsuarioDTO;
import br.com.lsouza.picpayclone.exceptions.NegocioException;
import br.com.lsouza.picpayclone.modelo.Transacao;
import br.com.lsouza.picpayclone.modelo.Usuario;
import br.com.lsouza.picpayclone.repository.UsuarioRepository;
import br.com.lsouza.picpayclone.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioConversor usuarioConversor;

    @Override
    public Usuario consultarEntidade(String login) {
        return usuarioRepository.findByLogin(login);
    }

    @Override
    public void validar(Usuario... usuarios) {
        Arrays.stream(usuarios).filter(Objects::isNull).forEach(usuario -> {
            throw new NegocioException("O usuário informado não existe!");
        });
    }

    @Override
    @Async("asyncExecutor")
    public void atualizarSaldo(Transacao transacao, Boolean isCartaoCredito) {
        decrementarSaldo(transacao, isCartaoCredito);
        incrementarSaldo(transacao);
        
    }

    @Override
    public UsuarioDTO consultar(String login) {
        Usuario usuario = consultarEntidade(login);
        return usuarioConversor.converterEntidadeParaDto(usuario);
    }

    @Override
    public List<UsuarioDTO> listar(String login) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Usuario> usuariosFiltrados = usuarios.stream().filter(v -> !v.getLogin().equals(login)).collect(Collectors.toList());
        return usuarioConversor.converterEntidadesParaDtos(usuariosFiltrados);
    }

    private void incrementarSaldo(Transacao transacaoSalva) {
        usuarioRepository.updateIncrementarSaldo(transacaoSalva.getDestino().getLogin(), transacaoSalva.getValor());
    }

    private void decrementarSaldo(Transacao transacaoSalva, Boolean isCartaoCredito) {
        if (!isCartaoCredito) {
            usuarioRepository.updateDecrementarSaldo(transacaoSalva.getDestino().getLogin(), transacaoSalva.getValor());
        }
    }
}

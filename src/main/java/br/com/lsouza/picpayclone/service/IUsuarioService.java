package br.com.lsouza.picpayclone.service;

import br.com.lsouza.picpayclone.dto.UsuarioDTO;
import br.com.lsouza.picpayclone.modelo.Transacao;
import br.com.lsouza.picpayclone.modelo.Usuario;

import java.util.List;

public interface IUsuarioService{
    Usuario consultarEntidade(String login);

    void validar(Usuario... usuarios);

    void atualizarSaldo(Transacao transacao, Boolean isCartaoCredito);

    UsuarioDTO consultar(String login);

    List<UsuarioDTO> listar(String login);
}

package br.com.lsouza.picpayclone.repository;

import br.com.lsouza.picpayclone.modelo.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    Page<Transacao> findByOrigem_LoginOrDestino_Login(String login, String login1, Pageable paginacao);
}

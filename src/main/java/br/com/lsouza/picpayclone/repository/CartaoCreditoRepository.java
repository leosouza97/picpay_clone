package br.com.lsouza.picpayclone.repository;

import br.com.lsouza.picpayclone.modelo.CartaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {
}

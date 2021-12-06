package br.com.lsouza.picpayclone.service.impl;

import br.com.lsouza.picpayclone.conversor.TransacaoConversor;
import br.com.lsouza.picpayclone.dto.TransacaoDTO;
import br.com.lsouza.picpayclone.modelo.Transacao;
import br.com.lsouza.picpayclone.repository.TransacaoRepository;
import br.com.lsouza.picpayclone.service.ICartaoCreditoService;
import br.com.lsouza.picpayclone.service.ITransacaoService;
import br.com.lsouza.picpayclone.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
public class TransacaoService implements ITransacaoService {

    @Autowired
    private TransacaoConversor transacaoConversor;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ICartaoCreditoService cartaoCreditoService;

    @Override
    public TransacaoDTO processar(TransacaoDTO transacaoDTO) {
        Transacao transacao = salvar(transacaoDTO);
        cartaoCreditoService.salvar(transacaoDTO.getCartaoCredito());
        usuarioService.atualizarSaldo(transacao, transacaoDTO.getIsCartaoCredito());
        return transacaoConversor.converterEntidadeParaDto(transacao);
    }

    private Transacao salvar(TransacaoDTO transacaoDTO) {
        Transacao transacao = transacaoConversor.converterDtoParaEntidade(transacaoDTO);
        usuarioService.validar(transacao.getDestino(), transacao.getOrigem());
        return transacaoRepository.save(transacao);

    }

    @Override
    public Page<TransacaoDTO> listar(Pageable paginacao, String login) {
       Page<Transacao> transacoes = transacaoRepository.findByOrigem_LoginOrDestino_Login(login, login, paginacao);
        return transacaoConversor.converterPageEntidadeParaDto(transacoes);
    }
}

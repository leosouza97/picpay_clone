package br.com.lsouza.picpayclone.resource;

import br.com.lsouza.picpayclone.dto.TransacaoDTO;
import br.com.lsouza.picpayclone.service.ITransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/transacoes")
public class TransacaoResource extends ResourceBase<TransacaoDTO> {

    //Camadas em API
    //Modelo
    //Persistencia
    //Serviços

    @Autowired
    private ITransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<TransacaoDTO> salvar(@RequestBody @Valid TransacaoDTO transacaoDTO,
                                               UriComponentsBuilder uriBuilder){
        TransacaoDTO transacaoRetornoDTO = transacaoService.processar(transacaoDTO);
        return responderItemCriadoComURI(transacaoRetornoDTO, uriBuilder, "/transacoes/{codigo}", transacaoRetornoDTO.getCodigo());
    }

    @GetMapping
    public ResponseEntity<Page<TransacaoDTO>> listar(@PageableDefault(page = 0, size = 20) Pageable paginacao, @RequestParam String login){
        Page<TransacaoDTO> transacoes = transacaoService.listar(paginacao, login);
        return responderListaDeItensPaginada(transacoes);
    }

}

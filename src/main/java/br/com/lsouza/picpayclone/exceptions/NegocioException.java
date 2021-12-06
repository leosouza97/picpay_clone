package br.com.lsouza.picpayclone.exceptions;

public class NegocioException extends RuntimeException {

    public NegocioException(String mensagem){
        super(mensagem);
    }

    public NegocioException(String mensagem, Exception exception){
        super(mensagem, exception);
    }

}

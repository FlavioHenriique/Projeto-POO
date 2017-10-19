
package com.mycompany.etapa2.excecoes;

/**
 * Exceção criada para erros no cadastro de movimentações e usuários 
 * @author Flavio
 */
public class CadastroException extends Exception {
    
    /**
     * Construtor de CadastroException
     * @param mensagem  é a mensagem que será passada, de acordo com o erro ocorrido
     */
    public CadastroException(String mensagem) {
        super(mensagem);
    }
}

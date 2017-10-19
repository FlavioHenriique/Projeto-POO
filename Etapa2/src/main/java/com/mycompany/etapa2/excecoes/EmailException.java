
package com.mycompany.etapa2.excecoes;

/**
 * Exceção criada para tentativas de cadastro de usuários com emails já existentes
 * @author Flavio
 */

public class EmailException extends Exception{
    
    /**
     * Construtor de EmailException
     * @param mensagem é a mensagem que será enviada caso a exceção ocorra.
     */
    public EmailException(String mensagem){
        super(mensagem);
    }
}
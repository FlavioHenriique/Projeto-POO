/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.projeto.excecoes;

/**
 *
 * @author Flavio
 */
public class EmailException extends Exception{
    
    public EmailException(String mensagem){
        super(mensagem);
    }
}

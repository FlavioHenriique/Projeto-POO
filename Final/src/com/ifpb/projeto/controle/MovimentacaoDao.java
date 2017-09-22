/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.projeto.controle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Flavio
 */
public interface MovimentacaoDao<T> {

    List<T> listar() throws FileNotFoundException, IOException,ClassNotFoundException;
    
    boolean salvar(T m)  throws FileNotFoundException, IOException,ClassNotFoundException;

    boolean atualizar(T m) throws FileNotFoundException,IOException,ClassNotFoundException;


    boolean remover(T m)throws FileNotFoundException,IOException, ClassNotFoundException;

}

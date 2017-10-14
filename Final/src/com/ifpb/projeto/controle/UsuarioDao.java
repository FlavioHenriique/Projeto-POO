/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.projeto.controle;

import com.ifpb.projeto.excecoes.CadastroException;
import com.ifpb.projeto.excecoes.EmailException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Flavio
 */
public interface UsuarioDao<T> {

    boolean salvar(T u) throws EmailException, CadastroException,
            FileNotFoundException, IOException, ClassNotFoundException,SQLException;
            
    boolean remover(T u);

    T atualizar(T u) throws FileNotFoundException, IOException, ClassNotFoundException;

    T excluir();
    
    List<T> listar() throws IOException, SQLException, ClassNotFoundException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.visao;

import com.ifpb.projeto.controle.UsuarioDaoArquivo;
import com.ifpb.projeto.excecoes.EmailException;
import com.ifpb.projeto.modelo.Movimentacao;
import com.ifpb.projeto.modelo.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Flavio
 */
public class app {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            UsuarioDaoArquivo dao = new UsuarioDaoArquivo();
            ArrayList<Usuario> lista = (ArrayList<Usuario>) dao.listar();
            Usuario novo = new Usuario();
            
            Usuario eu = dao.listar().get(0);
            System.out.println(eu.toString());
            dao.atualizar(eu);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(app.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(app.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(app.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

package com.ifpb.projeto.controle;

import com.ifpb.projeto.modelo.Movimentacao;
import com.ifpb.projeto.modelo.Usuario;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Cadastro {

    private List<Usuario> usuarios;

    public Cadastro() {
        usuarios = new ArrayList<>();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void salvar(Usuario u) {
        usuarios.add(u);
    }

    public boolean removerUsuario(String email, String senha) {
        if (usuarios.remove(localizar(email, senha))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean autenticar(String email, String senha) {
        for (Usuario user : usuarios) {
            if (user.getEmail().equals(email) && user.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    public Usuario localizar(String email, String senha) {

        for (Usuario user : usuarios) {
            if (user.getEmail().equals(email) && user.getSenha().equals(senha)) {
                return user;
            }
        }
        return null;

    }

    public void movimentacao(Usuario u, Movimentacao m) {
        for (Usuario user : usuarios) {
            if (user.equals(u)) {
                user.salvarMovimentacao(m);
            }
        }
    }

    public void listar(String email, String senha) {
        for (Usuario user : usuarios) {
            if (user.getEmail().equals(email) && user.getSenha().equals(senha)) {
                user.listarMovimentacoes();
            }
        }
    }

    public void atualizarUsuario(Usuario antigo, Usuario novo) {
        for (int k = 0; k < usuarios.size(); k++) {
            if (usuarios.get(k).equals(antigo)) {
                usuarios.set(k, novo);
            }
        }
    }
}

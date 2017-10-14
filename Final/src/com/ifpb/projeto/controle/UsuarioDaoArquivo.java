package com.ifpb.projeto.controle;

import com.ifpb.projeto.excecoes.CadastroException;
import com.ifpb.projeto.excecoes.EmailException;
import com.ifpb.projeto.modelo.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoArquivo {

    private File arquivo;

    public UsuarioDaoArquivo() throws IOException,SQLException {
        arquivo = new File("usuarios.bin");
        if (!arquivo.exists()) {
            arquivo.createNewFile();
        }
    }

    public List<Usuario> listar() throws ClassNotFoundException, IOException {

        List<Usuario> usuarios;
        if (arquivo.length() > 0) {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(arquivo));
            usuarios = (List<Usuario>) reader.readObject();
            reader.close();
            return usuarios;

        } else {
            usuarios = new ArrayList<>();
        }
        return usuarios;
    }

    public boolean atualizar(Usuario u) throws FileNotFoundException, IOException,
            ClassNotFoundException, EmailException, CadastroException,SQLException {

        List<Usuario> usuarios = listar();
        if (u.getEmail().equals("") || u.getNascimento() == null
                || u.getNome().equals("") || u.getSenha().equals("") || u.getSexo().equals("")) {
            throw new CadastroException("Preencha todos os campos!");
        }
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(arquivo));
        for (int k = 0; k < usuarios.size(); k++) {
            if (usuarios.get(k).getEmail().equals(u.getEmail())) {
                usuarios.remove(k);
                usuarios.add(u);
                writer.writeObject(usuarios);
                writer.close();
                return true;
            }
        }
        writer.close();
        return false;
    }

    public Usuario autenticar(String email, String senha) throws IOException, 
            FileNotFoundException, ClassNotFoundException, EmailException,SQLException {
        List<Usuario> usuarios = listar();
        if (email.equals("") && senha.equals("")) {
            throw new EmailException("Preencha todos os campos!");
        } else if (senha.equals("")) {
            throw new EmailException("Preencha o campo senha!");
        } else if (email.equals("")) {
            throw new EmailException("Preencha o campo email!");
        }
        for (Usuario user : usuarios) {
            if (user.getEmail().equals(email) && user.getSenha().equals(senha)) {
                return user;
            }
        }
        return null;
    }

    public boolean salvar(Usuario u) throws EmailException, CadastroException,
            FileNotFoundException, IOException, ClassNotFoundException,SQLException {
        List<Usuario> usuarios = listar();
        if (buscar(u.getEmail()) != null) {
            throw new EmailException("Este email está sendo utilizado!");
        }
        if (u.getEmail().equals("") || u.getNascimento() == null
                || u.getNome().equals("") || u.getSenha().equals("") || u.getSexo().equals("")) {
            throw new CadastroException("Preencha todos os campos!");
        }
        usuarios.add(u);
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(arquivo));
        writer.writeObject(usuarios);
        writer.close();
        return true;
    }

    public boolean remover(Usuario u) throws IOException, ClassNotFoundException {
        List<Usuario> usuarios = listar();
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(arquivo));
        if (usuarios.remove(u)) {
            writer.writeObject(usuarios);
            writer.close();
            return true;
        } else {
            writer.writeObject(usuarios);
            writer.close();
            return false;
        }
    }

    public boolean confirmaSenha(String senha, String confirma) throws CadastroException {

        if (confirma.equals("")) {
            throw new CadastroException("Digite a confirmação de senha!");
        }
        return senha.equals(confirma);
    }

    public Usuario buscar(String email) throws ClassNotFoundException, IOException {
        List<Usuario> usuarios = listar();
        for (Usuario user : usuarios) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

}

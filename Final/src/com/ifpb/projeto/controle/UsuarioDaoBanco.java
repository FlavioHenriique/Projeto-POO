/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.projeto.controle;

import com.ifpb.projeto.excecoes.CadastroException;
import com.ifpb.projeto.excecoes.EmailException;
import com.ifpb.projeto.modelo.Movimentacao;
import com.ifpb.projeto.modelo.Usuario;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.zoneId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Flavio
 */
public class UsuarioDaoBanco {

    private ConFactory factory;

    public UsuarioDaoBanco() throws SQLException, IOException {
        factory = new ConFactory();
    }

    public List<Usuario> listar() throws ClassNotFoundException, SQLException {

        Connection con = factory.getConnection();
        List<Usuario> usuarios = new ArrayList<>();
        PreparedStatement stmt = con.prepareStatement("select * from usuario");
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            Usuario user = new Usuario();
            user.setEmail(resultado.getString("email"));
            user.setNome(resultado.getString("nome"));
            user.setSexo(resultado.getString("sexo"));
            user.setSenha(resultado.getString("senha"));
            LocalDate data = resultado.getDate("nascimento").toLocalDate();
            user.setNascimento(data);
            PreparedStatement mov = con.prepareStatement("select * from movimentacao"
                    + " where email='" + user.getEmail() + "'");
            ResultSet resultadoMov = mov.executeQuery();
            List<Movimentacao> movs = new ArrayList<>();
            while (resultadoMov.next()) {
                Movimentacao m = new Movimentacao();
                m.setCategoria(resultadoMov.getString("categoria"));
                m.setTipo(resultadoMov.getString("tipo"));
                m.setDescricao(resultadoMov.getString("descricao"));
                m.setEmail(user.getEmail());
                LocalDate date = resultadoMov.getDate("data").toLocalDate();
                m.setData(date);
                m.setValor(resultadoMov.getFloat("valor"));
                movs.add(m);
            }
            user.setMovimentacoes(movs);
            usuarios.add(user);
        }
        return usuarios;
    }

    public boolean salvar(Usuario u) throws ClassNotFoundException, SQLException,
            IOException, CadastroException, EmailException {

        if (buscar(u.getEmail()) != null) {
            throw new EmailException("Este email está sendo utilizado!");
        }
        if (u.getEmail().equals("") || u.getNascimento() == null
                || u.getNome().equals("") || u.getSenha().equals("") || u.getSexo().equals("")) {
            throw new CadastroException("Preencha todos os campos!");
        }
        Connection con = factory.getConnection();
        PreparedStatement inserir = con.prepareStatement("insert into usuario"
                + "(email,nome,senha,sexo,nascimento) values(?,?,?,?,?)");
        inserir.setString(1, u.getEmail());
        inserir.setString(2, u.getNome());
        inserir.setString(3, u.getSenha());
        inserir.setString(4, u.getSexo());
        inserir.setDate(5, Date.valueOf(u.getNascimento()));
        if (inserir.executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Usuario autenticar(String email, String senha) throws
            ClassNotFoundException, SQLException, EmailException, IOException {

        List<Usuario> usuarios = listar();
        if (email.equals("") && senha.equals("")) {
            throw new EmailException("Preencha todos os campos!");
        } else if (senha.equals("")) {
            throw new EmailException("Preencha o campo senha!");
        } else if (email.equals("")) {
            throw new EmailException("Preencha o campo email!");
        }
        Usuario u = new Usuario();
        for (int k = 0; k < usuarios.size(); k++) {
            if (usuarios.get(k).getEmail().equals(email)
                    && usuarios.get(k).getSenha().equals(senha)) {
                usuarios.get(k).setMovimentacoes(listarMovs(email));
                return usuarios.get(k);
            }
        }

        return null;
    }

    public boolean confirmaSenha(String senha, String confirma) throws CadastroException {

        if (confirma.equals("")) {
            throw new CadastroException("Digite a confirmação de senha!");
        }
        return senha.equals(confirma);
    }

    public Usuario buscar(String email) throws ClassNotFoundException, SQLException {

        List<Usuario> usuarios = listar();
        for (Usuario user : usuarios) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public boolean atualizar(Usuario u) throws FileNotFoundException, IOException,
            ClassNotFoundException, EmailException, CadastroException, SQLException {

        List<Usuario> usuarios = listar();
        if (u.getEmail().equals("") || u.getNascimento() == null
                || u.getNome().equals("") || u.getSenha().equals("") || u.getSexo().equals("")) {
            throw new CadastroException("Preencha todos os campos!");
        }
        Connection con = factory.getConnection();
        PreparedStatement atualizaUsuario = con.prepareStatement("update usuario set "
                + "nome=?,sexo=?,senha=?, nascimento=? where email =?");
        atualizaUsuario.setString(1, u.getNome());
        atualizaUsuario.setString(2, u.getSexo());
        atualizaUsuario.setString(3, u.getSenha());
        atualizaUsuario.setDate(4, Date.valueOf(u.getNascimento()));
        atualizaUsuario.setString(5, u.getEmail());
        PreparedStatement atualizaMov = con.prepareStatement("delete from movimentacao where email= ? ");
        atualizaMov.setString(1, u.getEmail());
        if (atualizaMov.executeUpdate() >= 0) {
            salvaMovs(u.getMovimentacoes());
        }
        return atualizaUsuario.executeUpdate() > 0;
    }

    public List<Movimentacao> listarMovs(String email) throws ClassNotFoundException,
            SQLException {

        Connection con = factory.getConnection();
        PreparedStatement stmt = con.prepareStatement("select * from movimentacao where email = ?");
        stmt.setString(1, email);
        List<Movimentacao> movs = new ArrayList<>();
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            Movimentacao m = new Movimentacao();
            m.setCategoria(resultado.getString("categoria"));
            m.setTipo(resultado.getString("tipo"));
            m.setDescricao(resultado.getString("descricao"));
            m.setEmail(email);
            m.setValor(resultado.getFloat("valor"));
            m.setData(resultado.getDate("data").toLocalDate());
            movs.add(m);
        }
        return movs;
    }

    private void salvaMovs(List<Movimentacao> movimentacoes) throws
            ClassNotFoundException, SQLException {

        Connection con = factory.getConnection();
        PreparedStatement stmt = con.prepareStatement("insert into movimentacao"
                + " (descricao,tipo,categoria,valor,email,data) values (?,?,?,?,?,?)");
        for (int k = 0; k < movimentacoes.size(); k++) {
            stmt.setString(1, movimentacoes.get(k).getDescricao());
            stmt.setString(2, movimentacoes.get(k).getTipo());
            stmt.setString(3, movimentacoes.get(k).getCategoria());
            stmt.setFloat(4, movimentacoes.get(k).getValor());
            stmt.setString(5, movimentacoes.get(k).getEmail());
            Date data = Date.valueOf(movimentacoes.get(k).getData());
            stmt.setDate(6, data);
            stmt.executeUpdate();
        }
    }
}

package com.ifpb.projeto.visao;

import com.ifpb.projeto.controle.Cadastro;
import com.ifpb.projeto.modelo.Usuario;
import com.ifpb.projeto.modelo.Movimentacao;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Cadastro cadastro = new Cadastro();
        int selecao = 0;
        Scanner scanner = new Scanner(System.in);
        while (selecao != 4) {
            System.out.println("Deseja se cadastrar? aperte 1.");
            System.out.println("Deseja fazer login? aperte 2.");
            System.out.println("Deseja remover algum usuário? aperte 3.");
            System.out.println("Deseja fechar o sistema? aperte 4.");
            selecao = scanner.nextInt();

            switch (selecao) {
                case 1: {
                    Usuario usuario = new Usuario();
                    System.out.println("Digite o email: ");
                    usuario.setEmail(scanner.next());
                    System.out.println("Digite a sua senha: ");
                    usuario.setSenha(scanner.next());
                    System.out.println("Digite o nome: ");
                    usuario.setNome(scanner.next());
                    System.out.println("Digite a data de nascimento: ");
                    String nascimento = scanner.next();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate data = LocalDate.parse(nascimento, formatter);
                    usuario.setNascimento(data);
                    System.out.println("Digite o seu sexo: ");
                    usuario.setSexo(scanner.next().charAt(0));
                    cadastro.salvar(usuario);
                    System.out.println("Cadastro realizado com sucesso! ");
                    break;
                }
                case 2: {
                    System.out.println("Digite o email: ");
                    String email = scanner.next();
                    System.out.println("Digite a sua senha: ");
                    String senha = scanner.next();
                    if (cadastro.autenticar(email, senha) == true) {
                        System.out.println("Usuário cadastrado no sistema.");
                        System.out.println("Bem vindo, " + cadastro.localizar(email, senha).getNome() + "!");
                        int escolha = 0;
                        while (escolha != 3) {
                            System.out.println("Deseja cadastrar alguma movimentação? Digite 1.");
                            System.out.println("Deseja listar suas movimentações? digite 2.");
                            System.out.println("Deseja sair do sistema? Digite 3.");
                            escolha = scanner.nextInt();
                            switch (escolha) {
                                case 1: {
                                    Movimentacao movimentacao = new Movimentacao();
                                    System.out.println("Digite a descrição: ");
                                    movimentacao.setDescricao(scanner.next());
                                    System.out.println("Digite a data: ");
                                    String dataMovimentacao = scanner.next();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    LocalDate data = LocalDate.parse(dataMovimentacao, formatter);
                                    movimentacao.setData(data);
                                    System.out.println("Digite o valor: ");
                                    movimentacao.setValor(scanner.nextFloat());
                                    System.out.println("Digite o tipo: ");
                                    movimentacao.setTipo(scanner.next());
                                    System.out.println("Digite a categoria: ");
                                    movimentacao.setCategoria(scanner.next());
                                    cadastro.movimentacao(cadastro.localizar(email, senha), movimentacao);
                                    System.out.println("Movimentação cadastrada com sucesso !");
                                    break;

                                }
                                case 2: {
                                    cadastro.listar(email, senha);
                                    break;
                                }
                                case 3:
                                    escolha = 3;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Usuário não cadastrado.");
                        break;
                    }

                    break;
                }
                case 3: {
                    System.out.println("Digite o email do usuário: ");
                    String email = scanner.next();
                    System.out.println("Digite a senha do usuário: ");
                    String senha = scanner.next();
                    if (cadastro.removerUsuario(email, senha)) {
                        System.out.println("Usuário removido com sucesso!");
                    } else {
                        System.out.println("Usuário não encontrado.");
                    }
                    break;
                }
                case 4:
                    selecao = 4;
                    break;
            }
        }
    }
}

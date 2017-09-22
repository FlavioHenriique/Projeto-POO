/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.projeto.controle;

/**
 *
 * @author Flavio
 */
public interface UsuarioDao<T> {

    boolean salvar(T u);

    boolean remover(T u);

    T atualizar();

    T excluir();
}

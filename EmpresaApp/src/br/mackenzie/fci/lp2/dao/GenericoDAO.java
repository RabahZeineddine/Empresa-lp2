/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mackenzie.fci.lp2.dao;

import br.mackenzie.fci.lp2.exception.PersistenciaException;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author 41502779
 */
public interface GenericoDAO<E> extends Serializable{
    List<E> listar() throws PersistenciaException;
    
    void inserir(E e) throws PersistenciaException;
    
    void alterar(E e) throws PersistenciaException;
    
    void remover(E e) throws PersistenciaException;
    
    E listarPorID(E e)  throws PersistenciaException;
}

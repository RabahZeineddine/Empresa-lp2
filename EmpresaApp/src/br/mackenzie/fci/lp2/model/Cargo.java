/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mackenzie.fci.lp2.model;

import java.io.Serializable;

/**
 *
 * @author RABAH
 */
public class Cargo implements Serializable {
    private Long codigo;
    private String nome;

    public Cargo(Long codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Cargo() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return ""+codigo + "                  " + nome+"\n";
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mackenzie.fci.lp2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author 41502779
 */
public class Conexao {
    private final ResourceBundle BUNDLE = ResourceBundle.getBundle("br.mackenzie.fci.lp2.bundle.java_derby");    
    private static Conexao conexao;
    
    private Conexao(){
    
    }
    
    public static Conexao getInstance(){
        if(conexao == null){
            conexao = new Conexao();
        }
        return conexao;
    }
    
    public Connection getConnection() throws ClassNotFoundException,SQLException{
        Class.forName(BUNDLE.getString("driver"));
        return DriverManager.getConnection(BUNDLE.getString("url"),BUNDLE.getString("user"), BUNDLE.getString("password"));
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mackenzie.fci.lp2.dao;

import br.mackenzie.fci.lp2.exception.PersistenciaException;
import br.mackenzie.fci.lp2.model.Departamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 41502779
 */
public class DepartamentoDAO implements GenericoDAO<Departamento> {

    @Override
    public List<Departamento> listar() throws PersistenciaException {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT * FROM EMPRESA.DEPARTAMENTO";
        
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            ResultSet result = pStatement.executeQuery();
            while(result.next()){
                departamentos.add(new Departamento(result.getLong("ID_DEPARTAMENTO"),result.getString("NOME")));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar á base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível eniar o comando para a base de dados!");
        }finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return departamentos;
    }

    @Override
    public void inserir(Departamento departamento) throws PersistenciaException {
        String sql = "INSERT INTO EMPRESA.DEPARTAMENTO (NOME) VALUES(?)";

        Connection connection = null;

        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, departamento.getNome());
            pStatement.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados");
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void alterar(Departamento departamento) throws PersistenciaException {
        String sql= "UPDATE EMPRESA.DEPARTAMENTO SET NOME=? WHERE ID_DEPARTAMENTO=?";
        
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, departamento.getNome());
            pStatement.setLong(2, departamento.getCodigo());
            pStatement.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão");
            }
        }
    }

    @Override
    public void remover(Departamento departamento) throws PersistenciaException {
        String sql = "DELETE FROM EMPRESA.DEPARTAMENTO WHERE ID_DEPARTAMENTO=?";
        
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setLong(1, departamento.getCodigo());
            pStatement.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a cnexão!");
            }
        }
    }

    @Override
    public Departamento listarPorID(Departamento departamento) throws PersistenciaException {
        String sql = "SELECT * FROM EMPRESA.DEPARTAMENTO WHERE ID_DEPARTAMENTO = ?";
        
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setLong(1, departamento.getCodigo());
            ResultSet result = pStatement.executeQuery();
            if(result.next()){
                departamento.setCodigo(result.getLong("ID_DEPARTAMENTO"));
                departamento.setNome(result.getString("NOME"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão!");
            }
        } 
        return departamento;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mackenzie.fci.lp2.dao;

import br.mackenzie.fci.lp2.exception.PersistenciaException;
import br.mackenzie.fci.lp2.model.Cargo;
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
public class CargoDAO implements GenericoDAO<Cargo> {

    @Override
    public List<Cargo> listar() throws PersistenciaException {
        List<Cargo> cargos = new ArrayList<>();
        String sql = "SELECT * FROM EMPRESA.CARGO";
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            ResultSet result = pStatement.executeQuery();
            while(result.next()){
                cargos.add(new Cargo(result.getLong("ID_CARGO"),result.getString("NOME")));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para à base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão");
            }
        }
        return cargos;
    }

    @Override
    public void inserir(Cargo cargo) throws PersistenciaException {
        String sql = "INSERT INTO EMPRESA.CARGO(NOME) VALUES(?)";

        Connection connection = null;
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatment = connection.prepareStatement(sql);
            pStatment.setString(1, cargo.getNome());
            pStatment.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possivel carregar o driver de conexão com a base de dados");
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Erro ao enviar o comandopara a base de dados ");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Erro ao fechar a conexão!");
            }
        }

    }

    @Override
    public void alterar(Cargo cargo) throws PersistenciaException {
        String sql = "UPDATE EMPRESA.CARGO SET NOME =? WHERE ID_CARGO=?";
        
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, cargo.getNome());
            pStatement.setLong(2, cargo.getCodigo());
            pStatement.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão");
            }
        }
    }

    @Override
    public void remover(Cargo cargo) throws PersistenciaException {
        String sql = "DELETE FROMEMPRESA.CARGO WHERE ID_CARGO=?";
        
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setLong(1, cargo.getCodigo());
            pStatement.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão");
            }
        }
    }

    @Override
    public Cargo listarPorID(Cargo cargo) throws PersistenciaException {
        String sql = "SELECT * FROM EMPRESA.CARGO WHERE ID_CARGO=?";
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setLong(1,cargo.getCodigo());
            ResultSet result = pStatement.executeQuery();
            if(result.next()){
                cargo.setCodigo(result.getLong("ID_CARGO"));
                cargo.setNome(result.getString("NOME"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possivel fechar a conexão");
            }
        }
        return cargo;
    }

}

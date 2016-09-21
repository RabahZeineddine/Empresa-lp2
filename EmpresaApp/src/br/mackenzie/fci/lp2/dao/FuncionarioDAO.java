/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mackenzie.fci.lp2.dao;

import br.mackenzie.fci.lp2.exception.PersistenciaException;
import br.mackenzie.fci.lp2.model.Cargo;
import br.mackenzie.fci.lp2.model.Departamento;
import br.mackenzie.fci.lp2.model.Funcionario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 41502779
 */
public class FuncionarioDAO implements GenericoDAO<Funcionario> {

    @Override
    public List<Funcionario> listar() throws PersistenciaException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM EMPRESA.FUNCIONARIO";
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            ResultSet result = pStatement.executeQuery();
            while(result.next()){
                funcionarios.add(new Funcionario(result.getLong("ID_FUNCIONARIO"),result.getString("NOME")));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão!");
            }
        }
        return funcionarios;
    }
    
    @Override
    public Funcionario listarPorID(Funcionario funcionario) throws PersistenciaException {
        String sql = "SELECT F.ID_FUNCIONARIO,F.NOME,F.DT_NASCIMENTO,F.DT_CONTRATACAO,"
                + "C.ID_CARGO,C.NOME CARGO,"
                + "D.ID_DEPARTAMENTO,D.NOME DEPARTAMENTO "
                + "FROM EMPRESA.FUNCIONARIO F"
                + "INNER JOIN EMPRESA.CARGO C"
                + "ON F.CARGO_ID = C.ID_CARGO"
                + "INNER JOIN EMPRESA.DEPARTAMENTO D"
                + "ON F.DEPARTAMENTO_ID = D.ID_DEPARTAMENTO "
                + "WHERE F.ID_FUNCIONARIO=?";
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setLong(1, funcionario.getCodigo());
            ResultSet result = pStatement.executeQuery();
            if(result.next()){
                funcionario = new Funcionario(result.getLong("ID_FUNCIONARIO"),result.getString("NOME"),
                                    result.getDate("DT_NASCIMENTO").toLocalDate(),result.getTimestamp("DT_CONTRATACAO").toLocalDateTime(),
                                    new Cargo(result.getLong("ID_CARGO"),result.getString("CARGO")),
                                    new Departamento(result.getLong("ID_DEPARTAMENTO"),result.getString("DEPARTAMENTO")));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão!");
            }
        }
        return funcionario;
    }

    @Override
    public void inserir(Funcionario funcionario) throws PersistenciaException {
        String sql = "INSERT INTO EMPRESA.FUNCIONARIO (NOME,DT_NASCIMENTO,DT_CONTRATACAO,CARGO_ID,DEPARTAMENTO_ID) VALUES(?,?,?,?,?)";
        
         Connection connection = null;
         
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1,funcionario.getNome());
            pStatement.setDate(2,Date.valueOf(funcionario.getNascimento()));
            pStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pStatement.setLong(4, funcionario.getCargo().getCodigo());
            pStatement.setLong(5, funcionario.getDepartamento().getCodigo());
            pStatement.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados");
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão");
            }
        }
    
    }
    
    
    
    public long inserir(Funcionario funcionario,boolean retorno) throws PersistenciaException{
        String sql = "INSERT INTO EMPRESA.FUNCIONARIO (NOME,DT_NASCIMENTO,DT_CONTRATACAO,CARGO_ID,DEPARTAMENTO_ID) VALUES(?,?,?,?,?)";
        Connection connection = null;
        Long primaryKey = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1,funcionario.getNome());
            pStatement.setDate(2, Date.valueOf(funcionario.getNascimento()));
            pStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pStatement.setLong(4, funcionario.getCargo().getCodigo());
            pStatement.setLong(5, funcionario.getDepartamento().getCodigo());
            pStatement.execute();
            
            /* Recuperar a Ultima chave primaria*/
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if(generatedKeys != null && generatedKeys.next()){
                primaryKey = generatedKeys.getLong(1);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão");
            }
        }
        return primaryKey;
    }

    @Override
    public void alterar(Funcionario funcionario) throws PersistenciaException {
        String sql = "UPDATE EMPRESA.FUNCIONARIO SET NOME=?,DT_NASCIMENTO=?,DT_CONTRATACAO=?,CARGO_ID=?,DEPARTAMENTO_ID=? WHERE ID_FUNCIONARIO=? ";
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, funcionario.getNome());
            pStatement.setDate(2, Date.valueOf(funcionario.getNascimento()));
            pStatement.setTimestamp(3, Timestamp.valueOf(funcionario.getContratacao()));
            pStatement.setLong(4, funcionario.getCargo().getCodigo());
            pStatement.setLong(5, funcionario.getDepartamento().getCodigo());
            pStatement.setLong(6, funcionario.getCodigo());
            pStatement.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão");
            }
        }
    }

    @Override
    public void remover(Funcionario funcionario) throws PersistenciaException {
        String sql = "DELETE FROM EMPRESA.FUNCIONARIO WHERE ID_FUNCIONARIO = ?";
        
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setLong(1, funcionario.getCodigo());
            pStatement.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar á base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão");
            }
        }
    }
    
}

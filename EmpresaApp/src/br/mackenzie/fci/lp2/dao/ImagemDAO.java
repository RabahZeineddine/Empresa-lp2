/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mackenzie.fci.lp2.dao;

import br.mackenzie.fci.lp2.exception.PersistenciaException;
import br.mackenzie.fci.lp2.model.Imagem;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author RABAH
 */
public class ImagemDAO implements GenericoDAO<Imagem> {
    
    @Override 
    public List<Imagem> listar() throws PersistenciaException{
        List<Imagem> imagens = new ArrayList<>();
        String sql = "SELECT * FROM EMPRESA.IMAGEM";
        
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            ResultSet result = pStatement.executeQuery();
            while(result.next()){
                imagens.add(new Imagem(result.getLong("ID_IMAGEM"),result.getString("NOME"),result.getBytes("CONTEUDO")));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão!");
            }
        }
        return imagens;
    }
    
    @Override
    public void inserir(Imagem imagem) throws PersistenciaException{
        String sql = "INSERT INTO EMPRESA.IMAGEM (ID_IMAGEM,NOME,CONTEUDO) VALUES(?,?,?)";
        
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setLong(1, imagem.getCodigo());
            pStatement.setString(2, imagem.getNome());
            pStatement.setBlob(3, new SerialBlob(imagem.getConteudo()));
            pStatement.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão!");
            }
        }
    }
    
    @Override
    public void alterar(Imagem imagem) throws PersistenciaException{
        String sql = "UPDATE EMPRESA.IMAGEM SET NOME=?,CONTEUDO=? WHERE ID_IMAGEM=?";
        
        Connection connection = null ; 
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, imagem.getNome());
            pStatement.setBlob(2, new SerialBlob(imagem.getConteudo()));
            pStatement.setLong(3, imagem.getCodigo());
            pStatement.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível envar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão!");
            }
        }
    }
    
    @Override
    public void remover(Imagem imagem) throws PersistenciaException{
        String sql = "DELETE FROM EMPRESA.IMAGEM WHERE ID_IMAGEM=?";
        
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setLong(1, imagem.getCodigo());
            pStatement.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectr à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexçao!");
            }
        }
    }
    
    @Override
    public Imagem listarPorID(Imagem imagem) throws PersistenciaException{
        String sql = "SELECT * FROM EMPRESA.IMAGEM WHERE ID_IMAGEM = ?";
        
        Connection connection = null;
        
        try {
            connection = Conexao.getInstance().getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setLong(1, imagem.getCodigo());
            ResultSet result = pStatement.executeQuery();
            if(result.next()){
                imagem.setCodigo(result.getLong("ID_IMAGEM"));
                imagem.setNome(result.getString("NOME"));
                Blob blob = result.getBlob("CONTEUDO");
                imagem.setConteudo(blob.getBytes(1,(int) blob.length()));
                blob.free();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível conectar à base de dados!");
        } catch (SQLException ex) {
            Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Não foi possível enviar o comando para a base de dados!");
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ImagemDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistenciaException("Não foi possível fechar a conexão!");
            }
        }
        return imagem;
    }
    
    public Imagem listarPorID(Long codigo) throws PersistenciaException{
        return this.listarPorID(new Imagem(codigo));
    }
}

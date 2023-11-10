package br.com.agenda.dao;

import br.com.agenda.factory.ConnectionFactory;
import br.com.agenda.model.Contato;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import com.mysql.jdbc.Connection;



public class ContatoDAO {
	
	//CREATE
	public void save (Contato contato) {
		String sql = "insert into contato (nome, idade, datacadastro) values (?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = (Connection) ConnectionFactory.createConnectionToMySQL();
			pstm = conn.prepareStatement(sql);
			
			pstm.setString (1, contato.getNome());
			pstm.setInt (2, contato.getIdade());
			pstm.setDate (3, new Date(contato.getDataCadastro().getTime()));
			
			pstm.execute();
		}catch (Exception e){
			e.printStackTrace();
		}
		finally {
			try {
				if (conn!=null){
					conn.close();
				}
				if (pstm!=null) {
					pstm.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//READ
	public List<Contato> getContatos(){
		String sql = "select * from contatos;";
		return null;
	}
	
	//UPDATE
	public void update (Contato contato) {
		
	}
	
	//DELETE
	public void delete () {
	
	}

}

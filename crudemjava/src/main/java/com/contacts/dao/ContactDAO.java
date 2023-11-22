package com.contacts.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.contacts.factory.ConnectionFactory;
import com.contacts.model.Contact;

public class ContactDAO {
	
	//CREATE
	public void save (Contact contact) {
		String sql = "insert into contact (name, age, registerDate) values (?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = (Connection) ConnectionFactory.createConnectionToMySQL();
			pstm = conn.prepareStatement(sql);
			
			pstm.setString (1, contact.getName());
			pstm.setInt (2, contact.getAge());
			pstm.setDate (3, new Date(contact.getRegisterDate().getTime()));
			
			pstm.execute();
			//JOptionPane.showMessageDialog(null,"Contact salvo com sucesso!");
			
			System.out.println("Contact saved with success!");
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
	public List<Contact> listContact() {
		String sql ="select * from contact;";
		
		List<Contact> contacts = new ArrayList <Contact> ();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		ResultSet rset = null;
		
	try {
		conn = ConnectionFactory.createConnectionToMySQL();
		pstm = conn.prepareStatement(sql);
		
		rset = pstm.executeQuery();
		
		while (rset.next()) {
			Contact contact = new Contact();
			
			contact.setId(rset.getInt("id"));
			contact.setAge(rset.getInt("age"));
			contact.setName(rset.getString("name"));
			contact.setRegisterDate(rset.getDate ("registerDate"));
			
			contacts.add(contact);
		}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if (conn!=null) {
						conn.close();
					}
					if (rset!=null) {
						rset.close();
					}
					if (pstm!=null) {
						pstm.close();
					}
				}catch (Exception e){
				e.printStackTrace();
				}
			}
		return contacts;
			
		}
	

	//UPDATE
	public void update (Contact contact) {
		
		String sql = "update contact set name = ?, age = ?, registerDate = ?"+"where id = ?;";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
		conn = ConnectionFactory.createConnectionToMySQL();
		pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, contact.getName());
		pstm.setInt(2, contact.getAge());
		pstm.setDate(3, new Date (contact.getRegisterDate().getTime()));
		pstm.setInt(4, contact.getId());
		
		pstm.execute();
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn!=null) {
					conn.close();
				}
				if(pstm!=null) {
					pstm.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//DELETE
	public void deleteById(int id) {
		String sql = "delete from contact where id = ?;";
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, id);
		
			pstm.execute();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstm!=null) {
				pstm.close();	
				}
				if(conn!=null) {
					conn.close();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

}

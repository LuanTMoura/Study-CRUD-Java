package com.contacts.application;
import java.util.Date;

import com.contacts.dao.*;
import com.contacts.model.Contact;


public class Main {
 public static void main (String [] args) {
	 
	 //CREATE
	 Contact contact = new Contact ();
	 contact.setName("João Paulo");
	 contact.setAge(30);
	 contact.setRegisterDate(new Date());
	 
	 ContactDAO contactDao = new ContactDAO ();
	 contactDao.save(contact);
	 /*
	 or:
	 new ContactDAO().save(contact);
	 */
	 
	//READ
	for(Contact c : contactDao.listContact()) {
		    System.out.println("------------------------------");
		 	System.out.println("Contact: "+c.getName());
		 	System.out.println("Age: "+c.getAge());
		 	System.out.println("Register Date: "+c.getRegisterDate());
		 	System.out.println("Id: "+c.getId());
		 	System.out.println("------------------------------");
	 }
	
	//UPDATE
	Contact contactUpdate = new Contact();
	contactUpdate.setName("Maria Esteves Neves");
	contactUpdate.setAge(58);
	contactUpdate.setRegisterDate(new Date());
	contactUpdate.setId(4);
	 
	//contactDao.update(contactUpdate);
	
	//DELETE
	//contactDao.deleteById(3);
 }
}

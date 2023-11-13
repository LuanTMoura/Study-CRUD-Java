package br.com.agenda.aplicacao;
import java.util.Date;

import br.com.agenda.model.Contato;
import br.com.agenda.dao.*;

public class Main {
 public static void main (String [] args) {
	 
	 //ContatoDAO contatoDao = new ContatoDAO ();
	 Contato contato = new Contato ();
	 contato.setNome("João");
	 contato.setIdade(55);
	 contato.setDataCadastro(new Date());
	 
	 //contatoDao.save(contato);
	 
	 new ContatoDAO().save(contato);
	 
 }
}

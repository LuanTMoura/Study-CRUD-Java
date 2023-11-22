<div align="center"><img decoding="async" loading="lazy" width="300" height="300" data-id="1371" src="https://hardikchavda.in/wp-content/uploads/2022/08/javajdbc.png" alt="" class="wp-image-1371" srcset="https://hardikchavda.in/wp-content/uploads/2022/08/javajdbc.png 300w, https://hardikchavda.in/wp-content/uploads/2022/08/javajdbc-150x150.png 150w" sizes="(max-width: 300px) 100vw, 300px"></div>

## Índice
* [Estudo de CRUD em Java](#estudo-de-crud-em-java)
	* [Configurações](#configurações)
	* [Fonte do conteúdo aprendido](#fonte-do-conteúdo-aprendido)
	* [Licença](#licença)
	* [O que foi aprendido?](#o-que-foi-aprendido)
		* [Configuração da base de dados](#configuração-da-base-de-dados)
		* [Desenvolvendo o CRUD e conectando o banco de dados](#desenvolvendo-o-crud-e-conectando-o-banco-de-dados)
			* [01. Modelagem dos objetos](#01-modelagem-dos-objetos)
			* [02. Fábrica de Conexões](#02-fábrica-de-conexões)
   			* [03. Data Access Object](#03-data-access-object)
			* [04. Classe Main](#04-classe-main)
</br>
</br>
</br>
</br>
</br>
</br>

# Estudo de CRUD em Java

Nesse repositório busquei documentar o início do meu estudo com JDBC, aplicando e conectando o Java ao MySQL usando os design patterns DAO e Factory, a fim de construir um CRUD (Create, Read, Update e Delete).

## Configurações

Driver: 
[my-sql-connection-j-8.1.0.jar](https://downloads.mysql.com/archives/c-j/)
</br>
Versão da stack: [Java JDK-20](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html)
</br>

## Licença

[![License: MIT (https://github.com/LuanTMoura/Study-CRUD-Java/blob/main/LICENSE)](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Fonte do conteúdo aprendido

[CRUD em Java - Canal Cursos Kane Chan](https://www.youtube.com/playlist?list=PLXbKgo5jPQE-noZ7oj9AlQsSXAsRShSVl)
</br>
</br>
</br>
</br>
</br>
# O que foi aprendido?

### Configuração da base de dados

Primeiramente, a proposta foi desenvolver uma lista de contatos, onde houvesse o nome, idade e a data em que cada contato foi cadastrado. Tendo essas informações, o CRUD auxiliaria na manipulação desses dados, seja os criando, lendo, atualizando e deletando.
</br>
Portanto, eu parti do princípio da criação da base de dados, depois a tabela e então os atributos. Dentro da ferramenta MySQL Workbench, o meu script sql comportou os comandos: ``create database listcontacts;`` para a criação da base de dados e com a tabela e seus atributos da seguinte maneira:

</br>

```mysql
create table contatcs (
id int not null auto_increment primary key,
nome varchar (40),
idade int,
dataCadastro date);
```
</br>

### Desenvolvendo o CRUD e conectando o banco de dados

Dentro do ambiente do Eclipse, foram criados alguns **pacotes** e **classes públicas** para exercerem tarefas diferentes na execução do programa. 


#### 01. Modelagem dos objetos

Primeiramente, se incia com a criação da package que será responsável pela modelagem dos objetos que representarão os atributos que constam na base de dados.

```java
package com.contacts.model;

public class Contact {
    //conteúdo...
}
```
</br>
Portanto, dentro da classe de modelagem dos dados os objetos são definidos com tipos primitivos que correspondem aos atributos que se encontram na tabela e são acompanhados por seus respectivos getters e setters que auxiliarão na manipulação das informações que passarão entre a aplicação java e o banco de dados.

</br>

```java
    private int  id;
    private String  name;
    private int age;
    private Date registerDate;

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
```
</br>

#### 02. Fábrica de Conexões

Tendo definidos os objetos, o segundo passo é a criação do pacote e da classe responsáveis por apontar a conexão que está sendo feita e conectar a aplicação Java com o server MySQL. Essa classe se trata de um padrão de projeto que faz ponte entre uma stack e um administrador de banco de dados.

</br>

```java
package com.contacts.factory

public class ConnectionFactory
```
</br>

Dentro dessa classe, definimos três objetos públicos, estático e como final, pois os parâmetros do tipo String que são passados por dentro deles não se alteram pois correspondem a informações específicas que são definidas através do administrador de banco de dados. Desta forma, seriam criados respectivamente, um objeto para ``username``, a ``senha`` e o caminho para a base de dados a qual estará sendo acessada e manipulada através dos métodos responsáveis pela manipulação dos atributos vigentes.

</br>

```java
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/contactlist";
```
</br>

Após isso, criamos uma classe de conexão chamada ``Connection`` do tipo ``java.sql``, que é responsável por apontar o driver que está sendo utilizado e será carregado pela JVM. Juntamente a isso, fazemos uma conexão chamando o Driver Manager, que pega os parâmetros passados nos objetos estáticos e acessa o banco de dados para construir a conexão, por fim, retornamos a conexão.

</br>

```java
public static Connection createConnectionToMySQL () throws Exception {
    Class.forName ("com.mysql.cj.jdbc.Driver");

    Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);    
    return connection;
}
```

</br>

Para que não aconteça um sobrecarregamento do servidor da JVM e o usuário crie mais de uma conexão ativa no banco de dado — e posteriormente, ocupando mais espaço — nós encerramos a conexão dentro de um método main, chamando o método de conexões e verificando através de uma estrutura de decisão se o novo acesso ao banco é nulo e então encerra-ló.

</br>

```java
public static void main (String [] args) throws Exception {
    Connection con = createConnectionToMySQL ();

    if (con!=null){
    	System.out.println ("Connection with success!");
    	con.close ();
    }
}

```

</br>

#### 03. Data Access Object

Tendo criado a fábrica de conexões e os objetos para auxiliar na maniuplação dos dados acessado no banco, é o momento em que usarmos o padrão de software DAO ou Data Access Objetc, que é usado para aplicar as regras de negócioe trabalhar com as partes funcionais do programa e claro, numa nova package e em uma nova classe.

</br>

```java
package com.contacts.dao

public class ContactDAO
```

</br>

Dentro da classe, ao elaborarmos o CRUD, seguimos procedimentos parecidos para três dessas operações, onde alteramos somente o script usado na base de dados, sendo eles ``insert into`` para a operação ``create``, ``update`` para o ``update``, ``delete`` para o ``delete`` e quanto ao ``read``, além do comando ser diferente — no caso, ``select`` — é o único que se diferencia
pela maneira a qual é estruturado, no caso, com a interface Java List.
Portanto, com exceção dda operação de leitura de dados, as outras seguem os seguintes passos:

1. Primeiramente, é criado o método público e com o nome corresponde à sua operação e instanciando a classe a qual será usada para a manipulação dos dados;
2. É definido a String que carregará o comando para realizar a operação na query, onde os atrributos que serão **setados** são feitos através de um parse das informações que passam do Java para o banco;
3. Cria um objeto de conexão como ``null``;
4. Cria um objeto da classe ``PrepareStament`` do Java como ``com.mysql.jdbc``, também como ``null``;
5. Após isso, é criado um ``Try Catch``, iniciando com uma instancia da fábrica de conexões;
6. Ainda na estrutura do Try, é feita uma instância da ``PrepareStatement`` com a fábrica de conexões para executar a Query esperada pela String definida no início da classe;
7. Então, por fim, cada operação seguem o parâmetro padrão para fazer a operação com algum objeto/atributo e adicionar os valores esperados pela Query: 
	- ``PrepareStatement`` usando set com o **tipo primitivo** do valor a ser alterado > parâmetro passando a **posição** dele > o valor a ser manipulado, no caso, a **classe a qual será utilizada** e o **get do Objeto**;
8. Portanto, ao manipular todos os getters e setters, é **executada** a ``PrepareStatement``;
9. Para concluir o ``Try``, o ``Cacth`` é usado para printar uma **exceção** do que foi pedido para executar acima;
10. E por fim, o ``finally`` é responsável por gerenciar as **estruturas de decisão** que verificam se as conexões ainda estão sendo utilizadas e se for o caso, encerrá-las — e claro, posteriormente definindo também um ``Catch`` printando qualquer tipo de erro quanto a esses comandos.

Então, separando por cada operação e seguindo esses mesmos passos pra cada uma delas, o código seria apresentado da seguinte maneira:

</br>

```java
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
```

</br>

E tratando da operação de Read, ela usa do ``Java List`` instanciando a classe a qual carrega os objetos a serem manipulados, cria uma nova lista como uma instância de ``Array List`` do pacote ``java.util`` e além das configurações já citadas, também utiliza a classe ``ResultSet`` do ``java.sql`` que recupera os dados do banco e é definida como ``null`` e executa a Query do PrepareStatement. 
</br>
Com essa mesma nova classe, ela auxilia a na listagem do dados através de um ``while`` pois enquanto houver atributos a serem passado, a classe ResultSet espera que registros sejam retornados. Dentro desse loop, ela trabalha usando o get de cada atributo esperado na Query e cada registro, eles são adicionados na ``Array List``.

</br>

```java
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
```

</br>

#### 04. Classe Main

Por fim, a classe Main recebe o pacote nomeado como ``application`` por ser a principal classe a executar as operações de fato.

</br>

```java
package com.contacts.application

public class Main
```

</br>

E importando as classes DAO e "Contact" do pacote ``model``, a classe Main opera fazendo setters de informações que serão adiciodas ou alteradas como registros no banco de dados, exceto a operação ``read`` que apenas lê o que lhe é retornado usando o get de cada atributo da tabela usada, portanto, encerrando por completo a função de CRUD.

</br>

```java
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

```

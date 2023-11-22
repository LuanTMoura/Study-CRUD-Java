# Index
* [CRUD Study in Java](#crud-study-in-java)
	* [Settings](#settings)
	* [Source of learned content](#source-of-learned-content)
 	* [License](#license)
		* [What was learned?](#what-was-learned)
			* [Database configuration](#database-configuration)
			* [Developing CRUD and connecting the database](#developing-crud-and-connecting-the-database)
				* [01. Object modeling](#01-object-modeling)
				* [02. Connections Factory](#02-connections-factory)
				* [03. Data Access Object](#03-data-access-object)
				* [04. Main Class](#04-main-class)

</br>
</br>
</br>
</br>
</br>
</br>

# CRUD Study in Java

In this repository, I sought to document the beginning of my study with JDBC, applying and connecting Java to MySQL using the DAO and Factory Design Patterns, to build a CRUD (Create, Read, Update, and Delete).

## Settings

Driver: [my-sql-connection-j-8.1.0.jar](https://downloads.mysql.com/archives/c-j/)
</br>
Stack version: [Java JDK-20](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html)
</br>

## License

[![License: MIT (https://github.com/LuanTMoura/Study-CRUD-Java/blob/main/LICENSE)](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Source of learned content

[CRUD em Java - Channel Cursos Kane Chan](https://www.youtube.com/playlist?list=PLXbKgo5jPQE-noZ7oj9AlQsSXAsRShSVl)
</br>
</br>
</br>
</br>
</br>
# What was learned?

### Database configuration

Firstly, the proposal was to develop a list of contacts, containing the name, age, and date on which each contact was registered. Having this information, CRUD would assist in manipulating this data, whether creating, reading, updating, or deleting it.
</br>
Therefore, I started from the principle of creating the database, the table, and then the attributes. Within the MySQL Workbench tool, MySQL script contained the commands: ``create database listcontacts;`` to create the database and the table and its attributes as follows:

</br>

```mysql
create table contacts (
id int not null auto_increment primary key,
name varchar(40),
int age,
registration date date);
```
</br>

### Developing CRUD and connecting the database

Within the Eclipse environment, some **packages** and **public classes** were created to perform different tasks in executing the program.


#### 01. Modeling of objects

Firstly, it starts with creating the package that will be responsible for modeling the objects that will represent the attributes that appear in the database.

```java
package com.contacts.model;

public class Contact {
     //content...
}
```
</br>
Therefore, within the data modeling class, objects are defined with primitive types that correspond to the attributes found in the table that accompanied by their respective getters and setters that will assist in manipulating the information that will pass between the Java application and the database. data.

</br>

```java
     private int id;
     private String name;
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

#### 02. Connections Factory

With the objects defined, the second step is to create the package and class responsible for pointing out the connection that was made and connecting the Java application with the MySQL server. This class is a design pattern that bridges a stack and a database administrator.

</br>

```java
package com.contacts.factory

public class ConnectionFactory
```
</br>

Within this class, we define three public objects, static and final, as the String parameters being passed within them do not change as they correspond to specific information that is defined through the database administrator. In this way, an object would be created respectively for ``username``, the ``password``, and the path to the database which will be accessed and manipulated through the methods responsible for manipulating the current attributes.

</br>

```java
     public static final String USERNAME = "root";
     public static final String PASSWORD = "";
     public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/contactlist";
```
</br>

After that, we create a connection class called ``Connection`` of type ``java.sql``, which is responsible for pointing out the driver that is being used and will be loaded by the JVM. Along with this, we make a connection by calling the Driver Manager, which takes the parameters passed in the static objects and accesses the database to build the connection, finally, we return the connection.

</br>

```java
public static Connection createConnectionToMySQL() throws Exception {
     Class.forName ("com.mysql.cj.jdbc.Driver");

     Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
     return connection;
}
```

</br>

So that the JVM server does not overload and the user creates more than one active connection in the database — and subsequently takes up more space — we close the connection within a main method, calling the connections method and checking through a decision structure if new access to the bank is null and then closes it.

</br>

```java
public static void main (String [] args) throws Exception {
     Connection con = createConnectionToMySQL();

     if (con!=null){
     System.out.println ("Connection successful!");
     con.close();
     }
}

```

</br>

#### 03. Data Access Object

Having created the connection factory and the obobjects to assist in the manipulation of data accessed in the database, it is the moment when we use the DAO or Data Access Object software pattern, which is used to apply business rules and work with the functional parts of the program and of course, in a new package, and a new class.

</br>

```java
package com.contacts.dao

public class ContactDAO
```

</br>

Within the class, when developing CRUD, we followed similar procedures for three of these operations, where we only changed the script used in the database, namely ``insert into`` for the ``create``,  ``update`` for ``update``, ``delete`` for ``delete`` and as for ``read``, in addition to the command being different — in this case, ``select`` — it is the only one that differs because of the way it is structured, in this case, with the Java List interface.
Therefore, except for the data reading operation, the others follow the following steps:

1. First, the public method is created and the name corresponds to its operation and instantiating the class that will be used to manipulate the data;
2. The String that will carry the command to operate the Query is defined, where the attributes that will be **set** are made through a parse of the information that passes from Java to the database;
3. Creates a connection object as ``null``;
4. Creates an object from the Java ``PrepareStament`` class as ``com.mysql.jdbc``, also as ``null``;
5. After that, a ``Try Catch`` is created, starting with an instance of the connection factory;
6. Still in the Try structure, an instance of ``PrepareStatement`` is made with the connection factory to execute the Query expected by the String defined at the beginning of the class;
7. Finally, each operation follows the default parameter to perform the operation with some object/attribute and add the values expected by the Query:
- ``PrepareStatement`` using set with the **primitive type** of the value to be changed > parameter passing its **position** > the value that will be manipulated, in this case, the **class that will be used* * and **get from Object**;
8. Therefore, when manipulating all getters and setters, ``PrepareStatement`` is **executed**;
9. To complete the ``Try``, the ``Cacth`` is used to print an **exception** of what was requested to execute above;
10. And ``finally`` is responsible for managing the **decision structures** that check whether the connections are still being used and, if applicable, close them — of course, later also define a ``Catch`` printing any error regarding these commands.

So, separating each operation and following these same steps for each of them, the code would be presented as follows:

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

pstm.setString(1, contact.getName());
pstm.setInt (2, contact.getAge());
pstm.setDate (3, new Date(contact.getRegisterDate().getTime()));

pstm.execute();

System.out.println("Contact saved successfully!");
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

Dealing with the Read operation, it uses ``Java List`` to instantiate the class that loads the objects to manipulate, creates a new list as an instance of ``Array List`` from the ``java.util` package ` and in addition to the settings already mentioned, it also uses the ``ResultSet`` class from ``java.sql`` which retrieves data from the database and is defined as ``null`` and executes the PrepareStatement Query.
</br>
With this same new class, it helps to list data through a ``while`` because while there are attributes to be passed, the ResultSet class expects records to be returned. Within this loop, it works using the get of each attribute expected in the Query and each record, they are added to the ``Array List``.

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

#### 04. Main Class

Finally, the Main class receives the package named ``application `` because it is the main class that actually executes the operations.

</br>

```java
package com.contacts.application

public class Main
```

</br>

And importing the DAO and "Contact" classes from the ``model`` package, the Main class operates by making setters of information that will be added or changed as records in the database, except the ``read`` operation which only reads what is returned to you using the get of each attribute of the table used, therefore, completely ending the CRUD function.

</br>

```java
public class Main {
  public static void main (String [] args) {

//CREATE
Contact contact = new Contact ();
contact.setName("João Paulo");
contact.setAge(30);
contact.setRegisterDate(new Date());

ContactDAO contactDao = new ContactDAO();
contactDao.save(contact);
/*
or:
new ContactDAO().save(contact);
*/

//READ
for(Contact c : contactDao.listContact()) {
System.out.println("-----------------------------");
System.out.println("Contact: "+c.getName());
System.out.println("Age: "+c.getAge());
System.out.println("Register Date: "+c.getRegisterDate());
System.out.println("Id: "+c.getId());
System.out.println("-----------------------------");
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

</br>

# BMS-Koyanagi
OOD course design(2018.9 ~ 2019.1): Book Management System

By Prof.KOYANAGI Keiichi

Final score: A
## Introduction
According to the documentation requirements, the functions to be implemented can be summarized as:

(1)Manage book data
 
(2)Implement concurrent operations

(3)Book information is stored in file

It is the management of the collection of books, no extra operations such as borrowing books and buying books. 

For concurrency, since the socket is not considered, but the local server is assumed, there is a way for the user to access the system by remotely logging in to the host. In the case of concurrent operations, uncontrolled data may be read and stored, corrupt database consistency, and deadlocks may occur. 

In order to solve this problem, locking is a very important technology, and it is a good solution to realize database concurrency control. The commercial-grade database itself comes with this feature, but since the database cannot be used for this topic, it needs to be locked during the read and write operations.

Based on the above requirements, the system will be designed to be based on MVC, Spring framework, and use Maven to manage the project.

## Design
UML tool: ProcessOn

Environment: IntelliJ IDEA, Java JDK1.8

Operate System: Windows 10

## Goals
Book management system business process is mainly used for book information.

After entering the user name and password, the system administrator logs in to the system and the system main interface appears. The submenu in the menu bar includes all the functions of the system.

The administrator can write to the new administrator user. And Add, delete, and modify password management operations would be executed.

In the book management business, the administrator can perform various operations on the management of the book information. (Create, read, update and delete)

## UI Design
#### Login
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpiKdU.png"/></div>

#### Main interface (List)
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpi1JJ.png"/></div>
  Each line of the form displays information about a book.
  
  Double-click a line to pop up the book details.
  
#### Find
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpi0Fe.png"/></div>
  At least one text box is not empty.
  
  The searched results will be displayed in the List.
  
  If the result is not found, it will show empty in the List.

#### Delete
  Discoloration after selecting a row to be deleted in the List.

  Click the "Delete" button to complete the operation.

#### Add
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpiRw8.png"/></div> 
  Text box cannot be empty.
  
  There must be a number in "Price" text box.
  
#### Edit
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpiRw8.png"/></div> 
  Select a row in the List and click the edit button to pop up.
  
  Modify information about an existing book.

## System Designing 
### (1)Logic diagram
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpFJhQ.png"/></div> 
The system is based on the Model–view–controller architecture. And use the Spring framework for dependency injection. 

The Spring web MVC framework provides a model-view-and-control architecture and components that can be used to develop flexible, loosely coupled web applications. The MVC pattern leads to the separation of different aspects of the application (input logic, business logic, and UI logic) while providing loose coupling between these elements
#### Model
In the Model layer, User has username and password, Book has the required attributes, and the number.

In order to facilitate the DAO layer, they all implement the WithID abstract class, which has an Id field as the primary key.

#### Service
In the Service layer, the UserService only defines the login method. 

The BookService defines four methods for adding, deleting, updating and reading, and the data is processed by the injected DAO. The implementation of the interface is injected via the @Service annotation.

#### UI

In the UI layer, five uis are defined: login, list, add, edit, and find. The login and list are unique. The login calls the userservice login method to verify the account. The list creates the add, edit, and find windows through the button events. Add, edit, and find represent multiple instances via @Scope("prototype").

initLogic defines the interaction logic of the interface, initUI defines the position size of each control, and initRelation defines the parent-child relationship between the controls. Build calls these three methods to draw the interface.

Main takes the login UI from the context and uses the build to draw the login interface.

This layer uses the @Controller annotation for dependency injection.

#### DAO

In the DAO layer, the generic abstract class DAO defines readFromFile and writeToFile, and uses the FileLock file lock to open the file for reading and writing (reading the shared lock and writing the mutex), ensuring the security of the thread and even the process within the JVM (ie, the same Only one process in the JVM can open this file, as described in the FileLock documentation. And one-time read and write (the size of the file that can be read is 10kB, which can be adjusted).

“Locks are associated with files, not channels. Use locks to coordinate with external processes, not between threads in the same JVM.”

FileLock can lock a writable file to ensure that only one process can get the lock of the file at the same time. This process can access the file. Other processes that can not get the lock either choose to hang up and wait, or choose to do something else. This mechanism ensures that many processes can access the file sequentially.

DAO defines the basic method of adding, deleting, and changing. The storage format is one line for each piece of data, and the model JSON is serialized and saved.

Modeling the design of the database, the generic T must implement WithID, that is, each record must carry the primary key ID, and the ID keeps increasing.

DAO defines the newId method, which records the current id in the sequence.dat file (the file address is injected by the spring framework), which is convenient for generating the next record.

<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpkAuq.png"/></div> 

DAO retains the abstract method getFilePath(), which needs to return the file address holding this type of data.

BookDAO and UserDAO are DAO implementations that use the spring framework to read configuration items into the filePath variable and pass it to the base class DAO by getFilePath. Inject these two methods using the @Repository annotation.

### (2)Overall Structure Diagram
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpklvR.png"/></div> 
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpk8Dx.png"/></div> 

### (3)User Case Diagram 
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpkDKI.png"/></div> 
The role of the entire system are only administrators.

### (4)Sequence Diagram 
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpk6Vf.png"/></div> 

### (5)Component diagram
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpkRPg.png"/></div> 

### (6)Class Diagram
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpkWGQ.png"/></div> 

The basic idea is to create a GUI program based on JFrame, and the corresponding function is stored in the interface of each button to operate the book.

In the Service layer, the book information is manipulated by using the ID and Book.class. The ID has always existed, but it is not displayed. The ID is a hidden primary key.

### (7)Data Format
Data transfer is done by serializing objects into strings in JSON format. The book information is stored in the book.dat file in the root directory. Here is an example of a book:

```Java
{"author":"Ryan Turner","explanation":"Great book for Beginner to Intermediate programmers","iSBN":"1790110025","id":8,"name":"Python Programming","number":1,"price":286600,"publisher":"Independently"}
```

User information is saved in user.dat in the root directory.
```Java
{"id":1,"password":"123456","username":"admin"}
```

### (8)Strategy Description
This system uses Maven for project management. Pom.xml defines two packages that depend on it, Spring framework and Fastjson. Where Spring is used for dependency injection, and Fastjson is used for JSON serialization of data.

The JDK version requires 1.8 or more due to the use of syntax such as lambda expressions.

<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpkzs1.png"/></div> 

Spring needs to add @Configuration and @ComponentScan({"bms"}) annotations to the main function, @Configuration for injection configuration items, @ComponentScan for injection scope, and "bms" for the total package name of this project.

The UI layer is injected using @Controller, the Service layer is injected using @Service, and the DAO layer is injected using @Repository.

Use the @Autowired annotation to get it where you use it. The default injection method is a singleton. After adding @Scope("prototype") annotation, it becomes multiple instances.
<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpAARH.png"/></div> 
The configuration item obtains the configured file address through @PropertySource("classpath:config.properties"), the classpath indicates the generated classes directory, and the config.properties corresponds to the file name. Use @Value("${name}") to get the specific configuration value, such as @Value("${data_source_book}") to get the value corresponding to data_source_book, which is ./book.dat.

### (9)Multitasking Concurrency
For multitasking concurrency, the Java Swing used in this system is a single-threaded programming model based on event queue. The events on the GUI are executed one by one on the "event dispatch thread", and no event-to-resource competition occurs. The code related to GUI event processing is executed in the "event dispatch thread". Other interface-independent code should be executed in other Java threads.

Therefore, in Swing's event processing, Swing's single-threaded programming model is still used, while other business operations use the multi-threaded programming model, which can achieve system concurrency requirements.

<div align=center><img src="https://s1.ax1x.com/2020/05/24/tpAueP.png"/></div> 

In this system, FIleLock is used to ensure that files are not opened multiple times. FileLock is an exclusive lock that controls concurrent access by the different programs (JVM) to the same file. Because it is an exclusive lock, you can ensure that the file is accessed sequentially between processes to avoid data errors.


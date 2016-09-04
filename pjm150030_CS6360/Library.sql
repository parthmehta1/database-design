create schema library;
use library;
create table library_branch(branch_id int NOT null, branch_name varchar(20), address varchar(100), primary key(branch_id));

load data local infile 'C:/Users/Parth/Desktop/Books/Sem2/DB/proj1/data/CSV1/library_branch.csv' into table library_branch
fields terminated by ','
lines terminated by '\n';

create table Book_Loans(
Loan_ID int not null auto_increment, 
ISBN varchar(10) not null, 
branch_id int not null, 
Card_no varchar(8) not null, 
Date_out date, 
Due_date date, 
Date_in date, 
primary key(LOAN_ID),
FOREIGN KEY(ISBN) 
references BOOK(ISBN10)
on delete cascade,
foreign key(Branch_Id) 
references library_Branch(Branch_Id) 
on delete cascade, 
foreign key(Card_no) 
references Borrower(Card_no) 
on delete cascade
);
load data local infile 'C:/Users/Parth/Desktop/Books/Sem2/DB/proj1/data/CSV1/book_loans.csv' into table Book_Loans
fields terminated by ','
lines terminated by '\n';

create table Book_Copies(
Book_Id varchar(10) not null, 
Branch_Id int not null, 
No_of_Copies int,
primary key(Book_id,Branch_id), 
foreign key(Book_id) references Book(ISBN10) on delete cascade on update cascade, 
foreign key(Branch_id) references library_Branch(Branch_id) on delete cascade on update cascade);
load data local infile 'C:/Users/Parth/Desktop/Books/Sem2/DB/proj1/data/CSV1/book_copies.csv' into table Book_Copies
fields terminated by ','
lines terminated by '\n';

create table borrower(
Card_no varchar(8) not null,
ssn varchar(11) not null,
first_name varchar(20),
last_name varchar(20),
email varchar(50),
address varchar(150),
city varchar(20),
state varchar(20),
phone varchar(15), 
primary key(card_no, ssn)); 
load data local infile 'C:/Users/Parth/Desktop/Books/Sem2/DB/proj1/data/CSV1/borrowers.csv' into table borrower
fields terminated by ','
lines terminated by '\n';

SET FOREIGN_KEY_CHECKS=0;
create table Book(
Isbn10 varchar(10) not null,
Isbn13 varchar(13),
Title varchar(200),
Author varchar(100),
cover varchar(100),
publisher varchar(100),
pages int,
primary key(Isbn10)
);

load data local infile 'C:/Users/Parth/Desktop/Books/Sem2/DB/proj1/data/CSV1/books.csv' into table book
fields terminated by ',' OPTIONALLY ENCLOSED BY '"'
lines terminated by '\n';
create table authors1(
Author_id int not null auto_increment,
Author varchar(100) not null, 
fname varchar(40), 
mname varchar(40),
lname varchar(40), 
primary key(Author_id)
);

insert into authors1 (author,fname,mname,lname) (
select value1 as FullName,substring_index(substring_index(value1, ' ',1), ' ',-1)as fname,
if(length(value1)-length(replace(value1, ' ', ''))>1, substring_index(substring_index(value1, ' ',2), ' ',-1), NULL) 
as mname, 
substring_index(substring_index(value1,' ', 3), ' ', -1) as lname
from(
select distinct value1 from 
(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(author, ',', n.n), ',', -1) value1
  FROM book author CROSS JOIN
(
   SELECT a.N + b.N * 10 + 1 n
     FROM 
    (SELECT 0 AS N UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) a
   ,(SELECT 0 AS N UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) b
    ORDER BY n
) n
 WHERE n.n <= 1 + (LENGTH(Author) - LENGTH(REPLACE(Author, ',', '')))
 ) as temp)as temp) ;

create table book_authors(
ISBN varchar(10) not null, 
Author_Id int not null, 
primary key(ISBN,author_ID), 
foreign key(ISBN) references Book(ISBN10) on delete cascade on update cascade,
foreign key(Author_id) references authors1(Author_id) on delete cascade on update cascade
);

insert into book_authors(Isbn,Author_Id) (
select ISBN10,Author_id from book, authors1 where book.author = authors1.author);

 
 CREATE TABLE FINES(
	Loan_id	 INT(5) NOT NULL AUTO_INCREMENT,
	fine_amt DECIMAL(5,2) NOT NULL,
	paid tinyint(1) NOT NULL,
	PRIMARY KEY (Loan_id),
	FOREIGN KEY (Loan_id) REFERENCES BOOK_LOANS(Loan_id) ON DELETE CASCADE);
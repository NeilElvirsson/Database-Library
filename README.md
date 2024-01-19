
[Programmet hanteras i terminalen på intelliJ, instruktioner ska vara klara vad som ska göras. 
Har använt jdk 21.]

[Använd 'Neil@gmail.com' för inloggning och 'hhunter2' för lösenord. Har ändrat i några av de andra användare så användarnamn och lösenord stämmer inte övereens där.]

[Inte säkert jag fått med allt från databashanterare (inte säker på att back upp funkade som den skulle) skickar med textfil här.]

USE librarydb;

CREATE TABLE IF NOT EXISTS Users (
users_id INT AUTO_INCREMENT PRIMARY KEY,
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
name VARCHAR(255) NOT NULL
);
SHOW TABLES;

delete from users;
DELETE FROM loan;

ALTER TABLE users ADD COLUMN name;
INSERT INTO users (
	email,
	password,
	name
)
VALUES
('kungen1@gmail.com', 'hejsanhoppsan', 'Knugen Knugsson'),
('neil@gmail.com', 'hhunter2', 'Neil Huntersson'),
('fearandloathing@gmail.com', 'seeWHATgoddid', 'Hunter S Thompson'),
('peeOnCarpet@gmail.com', '123lebowski', 'Big Lebowski');
DROP TABLE users;
CREATE TABLE IF NOT EXISTS Books (
book_id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(255) NOT NULL,
author VARCHAR(255) NOT NULL,
genre VARCHAR(255) NOT NULL,
publication_year INT NOT NULL,
available_quantity INT NOT NULL,
sort VARCHAR(25) NOT NULL
);

INSERT INTO books (
title,
author,
genre,
publication_year,
available_quantity,
sort
)
VALUES(
'Harry Potter och den flammande bägaren','JK Rowling','Fantasi',2001 ,1 , 'bok'),
('Harry Potter och hemligheternas kamare', 'JK Rowling', 'Fantasi', 2003,1 ,'bok'),
('Det','Stephen King','skräck',1989,1, 'bok'),
('Hungerspelen','Suzanne Collins','Fantasi',2010,1, 'bok' ),
('Wim Hoff metoden','Wim Hoff','facklitteratur',2018,1,'bok'),
('Necronomicon','H.P Lovecraft','Skräck',1948,1, 'bok'),
('Hyser Area 51 utomjordingar', 'Bob Lazar', 'fakta',1989 , 1 ,'tidskrift'),
('Dykare träffade på Megladon under dykning', 'Lars von Haj', 'fakta', 2024, 1, 'tidskrift'),
('Skåning bortförd av gråa små männ', 'Nils Aliensson', 'fakta', 2022, 1, 'tidskrift');


SELECT book_id, title, author, genre, publication_year, available_quantity FROM Books WHERE title LIKE '%harry%';


CREATE TABLE IF NOT EXISTS Loan (
loan_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
users_id INT NOT NULL,
book_id INT NOT NULL,
loan_date DATE default CURRENT_TIMESTAMP,
status VARCHAR(25) NOT NULL,
FOREIGN KEY(users_id) REFERENCES Users(users_id),
FOREIGN KEY(book_id) REFERENCES Books(book_id)
);
DROP TABLE loan;
UPDATE loan SET status = 'available' WHERE users_id = 1 AND book_id = 1;
SELECT users_id, book_id, status FROM Loan WHERE status = 'unavailable' AND book_id = 2;
INSERT INTO Loan (
users_id,
book_id,
status
)
VALUES (
5,
2,
'unavailable'
);
SELECT book_id FROM Loan WHERE status = null;
SELECT users_id, email, password FROM Users WHERE email = 'neil@gmail.com' AND password = '' or 'a'='a';

CREATE TABLE IF NOT EXISTS Records (
loan_id INT 
);



SELECT 
loan_id,
users_id,
loan_date,
status,
title,
genre,
sort
FROM loan l INNER JOIN books b ON l.book_id = b.book_id WHERE users_id = 1; 


UPDATE Users SET email = 'fear1@gmail.com', password = 'fear123' WHERE email = 'fearandloathing@gmail.com' AND password = 'seeWHATgoddid';
UPDATE email, password FROM users WHERE 

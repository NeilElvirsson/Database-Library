package library;

import database.Database;
import jdk.jshell.execution.LoaderDelegate;
import pojo.*;

import java.sql.*;
import java.util.ArrayList;


public class Library {

    public boolean loanBook(int userId, int bookId) { // Method for loaning books
        //Sets connection and asks our SQL question checks if status is available or not
        Connection connection = Database.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT users_id, book_id, status FROM Loan WHERE status = 'utlånad' AND book_id = ?");
            preparedStatement.setInt(1, bookId);
            ResultSet result1 = preparedStatement.executeQuery();

            if (result1.next()) {

                return false;
            }
            //If true gets userId and bookId and change status so book or journal becomes unavailable
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO Loan(users_id, book_id, status) VALUES (?, ?, ?)");
            insertStatement.setInt(1, userId);
            insertStatement.setInt(2, bookId);
            insertStatement.setString(3, "utlånad");

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {

                return true;
            }
        } catch (SQLException e) {

            System.err.println(e.toString());
        }

        return false;
    }

    public boolean returnBook(int userId, int bookId) { // Method for returning books

        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            //Ask our SQL question to check if book is unavailable
            preparedStatement = connection.prepareStatement("SELECT users_id, book_id, status FROM Loan WHERE status = 'utlånad' AND book_id = ?");
            preparedStatement.setInt(1, bookId);
            ResultSet result = preparedStatement.executeQuery();
//If book is unavailable we proceed to ask SQL question to update book status to available
            if (!result.next()) {
                System.out.println("Kunde ej hitta lån");
                return false;
            }
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE loan SET status = 'tillgänglig' WHERE users_id = ? AND book_id = ?;");
            updateStatement.setInt(1, userId);
            updateStatement.setInt(2, bookId);
//Checks to see if rows been affected in our database, if rows been affected we return true, if not we get error message
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                System.out.println("Uppdatering gick ej igenom");
                return false;

            }

        } catch (SQLException e) {

            System.err.println(e.toString());
        }
        return false;
    }

    public ArrayList<LibraryItem> search(String searchTerm) { // Method for searching book,journal, author or title
        //Creates a connection and asks our sql question to check if user input matches title, author or genre
        Connection connection = Database.getConnection();
        try {
//Puts up question so user can search freely for any matching book,title or author
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT book_id, title, author, genre, publication_year, available_quantity FROM Books WHERE title LIKE ? OR author LIKE ? OR genre LIKE ?");
            preparedStatement.setString(1, "%" + searchTerm + "%");
            preparedStatement.setString(2, "%" + searchTerm + "%");
            preparedStatement.setString(3, "%" + searchTerm + "%");
            ResultSet result = preparedStatement.executeQuery();

            //Creates a variable arraylist to save our books information in
            ArrayList<LibraryItem> books = new ArrayList<>();
            //While loop to iterate through the book id , title and so on then saves it in variable book and returns it
            while (result.next()) {

                Book book = new Book();
                book.setTitle(result.getString("title"));
                book.setId(result.getInt("book_id"));
                book.setAuthor(result.getString("author"));
                book.setGenre(result.getString("genre"));
                book.setPublicationYear(result.getInt("publication_year"));
                book.setAvailableQuantity(result.getInt("available_quantity"));
                books.add(book);
            }
            //Closes our connection
            preparedStatement.close();
            return books;


        } catch (SQLException e) {
            System.err.println(e.toString());
            return null;

        }


    }
    public ArrayList <Loan> listLoan (int userId) { // Method for our loan history
        Connection connection = Database.getConnection();
//SQL question that selects all columns and joins books with loan to connect our book ids with user id
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT \n" +
                    "loan_id,\n" +
                    "users_id,\n" +
                    "l.book_id,\n" +
                    "loan_date,\n" +
                    "status,\n" +
                    "title,\n" +
                    "genre,\n" +
                    "sort,\n" +
                    "author\n" +
                    "FROM loan l INNER JOIN books b ON l.book_id = b.book_id WHERE users_id = ?");
            preparedStatement.setInt(1, userId);
            ResultSet result = preparedStatement.executeQuery();
            ArrayList<Loan> listLoan = new ArrayList<>();
            //Iterate through all our columns and saves our data to loan
            while(result.next()) {

                Loan loan = new Loan();

                Date date = result.getDate("loan_date");
                java.util.Date d = new java.util.Date(date.getTime());

                loan.setLoanId(result.getInt("loan_id"));
                loan.setUserId(result.getInt("users_id"));
                loan.setBookId(result.getInt("book_id"));
                loan.setLoanDate(d);
                loan.setStatus(result.getString("status"));

                String sort = result.getString("sort");
                //Check to see if sort is book or journal to set return date
                if (sort.equals("bok")) {
                    Book book = new Book();
                    book.setTitle(result.getString("title"));
                    book.setAuthor(result.getString("author"));
                    book.setGenre(result.getString("genre"));
                    book.setId(result.getInt("book_id"));
                    loan.setLoanable(book);
                } else {
                    Journal journal = new Journal();
                    journal.setTitle(result.getString("title"));
                    journal.setAuthor(result.getString("author"));
                    journal.setGenre(result.getString("genre"));
                    journal.setId(result.getInt("book_id"));
                    loan.setLoanable(journal);

                }
                listLoan.add(loan);
            }
            preparedStatement.close();
            return listLoan;

        } catch (SQLException ex) {

            System.err.println(ex.toString());
        }
        return null;
    }

}

import database.Database;
import library.Library;
import login.Login;
import pojo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        // database.Database db = new database.Database();

        Database.username = "root";
        Database.password = "";

        Database.database = "librarydb";

//Writes out instructions for user and saves it with scanner
        System.out.println("Ange användarnamn(email)");
        Scanner scan = new Scanner(System.in);
        String un = scan.nextLine();

        System.out.println("Ange lösenord");
        String pw = scan.nextLine();
//Calls upon the loggedInUser method with user class
        User loggedInUser = Login.login(un, pw);
        if (loggedInUser == null) {
            System.out.println("Loggades inte in");
            System.exit(0); // If wrong password or username exit program


        }
        System.out.println(un + " Inloggad");
        Library library = new Library();
        //Prints out a meny which is always true
        while (true) {
            System.out.println("Meny");
            System.out.println("1. Sök efter böcker");
            System.out.println("2. Låna bok");
            System.out.println("3. Se lånade böcker");
            System.out.println("4. Returnera böcker");
            System.out.println("5. Ändra användarnamn och lösenord");
            System.out.println("0. Avsluta");
            System.out.print("Val: ");
            //Creates a switch case to enter our methods trough our meny
            String ex = scan.nextLine();
            switch (ex) {
                //Enters our search method with 1
                case "1":
                    System.out.println("1. Sökning");
                    System.out.println("Vilken bok eller tidskrift vill du söka efter?: ");
                    ex = scan.nextLine();
//Calls upon our method to show searched books and journals
                    ArrayList<LibraryItem> searchResult = library.search(ex);

                    for (LibraryItem item : searchResult) {
                        int bookId = item.getId();
                        System.out.println("Id " + bookId + ": " + item);
                    }

                    break;
//Enters our method for loaning books
                case "2":
                    System.out.println("Ange Id numret för boken du vill låna");
                    int loanableId = Integer.parseInt(scan.nextLine());
//Check to see if ID is written than proceed to execute loan
                    if (loanableId != 0) {

                        boolean loanSuccsess = library.loanBook(loggedInUser.getUserId(), loanableId);

                        if (loanSuccsess) {
                            System.out.println("Lån utfört");

                        } else {
                            System.out.println("Gick ej att låna");
                        }
                    }
                    break;
//Enters our method for history of our loans
                case "3":
               //Calls upon users listLoan then check for bookID, and length of our loan
                    //Proceeds to write out history of our loaned books and journals
                    ArrayList<Loan> listLoan = library.listLoan(loggedInUser.getUserId());
                    for (Loan loan : listLoan) {
                        int bookId = loan.getBookId();
                        Loanable loanable = loan.getLoanable();
                        int loanLength = loanable.getLoanLength();
                        System.out.println("Id " + bookId + ": " + loan + " returneras " + loanLength + " dagar från utlåningsdag");
                    }

                    break;
//Enters our method for returning our books
                case "4":
                    System.out.println("Ange Id för boken du vill Returnera eller 0 för menyn");
                    int returnId = Integer.parseInt(scan.nextLine());

                    boolean returnSuccess = library.returnBook(loggedInUser.getUserId(), returnId);

                    if (returnSuccess) {
                        System.out.println("Boken har returnerats");
                    } else {
                        System.out.println("Gick ej att returnera");
                    }
                    break;
//Enters our method for changing username and password also sql questions.

                    case "5":
                    System.out.println("Skriv in nytt användarnamn(email)");
                    String newUserName = scan.nextLine();
                    System.out.println("Skriv in nytt lösenord");
                    String newPassword = scan.nextLine();
                   
                   Connection connection = Database.getConnection();
//SQL question to update username and password
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Users SET email = ?, password = ? WHERE email = ? AND password = ?");
                    preparedStatement.setString(1, newUserName);
                    preparedStatement.setString(2, newPassword);
                    preparedStatement.setString(3, loggedInUser.getEmail());
                    preparedStatement.setString(4, loggedInUser.getPassword());
                    int rowsAffected = preparedStatement.executeUpdate();
//Check to see if SQL question was executed
                    if (rowsAffected > 0) {
                        System.out.println("Uppdatering lyckades");
                        System.out.println("Du ändrade ditt användarnamn till " + newUserName + " och ditt lösenord till " + newPassword  );
                    } else {
                        System.out.println("Uppdatering misslyckades");
                    }

                    preparedStatement.close();

                    break;
//Exit program if user enters 0
                    case "0":
                    System.exit(0);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + ex);
            }
        }
    }
}
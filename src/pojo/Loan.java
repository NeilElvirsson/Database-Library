package pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

//Puts up instances of loan information and adds getters and setters to all
public class Loan {
    private int loanId;
    private int userId;
    private int bookId;
    private Date loanDate;
    private int returnDate;
    private String status;



    private Loanable loanable;



    public Loan() {


    }

    public int getLoanId(int loanId) {
        return this.loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public int getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(int returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Loanable getLoanable() {
        return loanable;
    }

    public void setLoanable(Loanable loanable) {
        this.loanable = loanable;
    }
    public String toString() {
        SimpleDateFormat dateFor = new SimpleDateFormat("yyyy/MM/dd");
        StringBuilder sb = new StringBuilder();
        sb.append(loanable).append(" ");
        sb.append(dateFor.format(loanDate)).append(" ");
        sb.append(returnDate).append(" ");
        sb.append(status);

        return sb.toString();
    }

}

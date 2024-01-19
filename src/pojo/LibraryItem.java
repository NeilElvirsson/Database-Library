package pojo;
//Super class with instances of relevant information for our books that implement our loanable interface
public abstract class LibraryItem implements Loanable {
    protected int id;
    protected String title;
    protected String author;
    protected String genre;
    protected int publicationYear;
    protected int availableQuantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append(" ");
        sb.append(author).append(" ");
        sb.append(genre);

        return sb.toString();
    }

}

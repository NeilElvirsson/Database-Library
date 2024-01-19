package pojo;
//Getter for journal loanlength that extends libraryItem class
public class Journal extends LibraryItem {


    @Override
    public int getLoanLength() {
        return 10;
    }
}

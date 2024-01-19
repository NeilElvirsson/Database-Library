package pojo;
//Getters for book loan length that extends from libraryItem
public class Book extends LibraryItem {




    @Override
    public int getLoanLength() {
        return 30;
    }
}

package com.librarymanagement.siddharth.snaplibrary;

/**
 * Created by NEEL on 02-12-2017.
 */

public class BookItem {
    public String Book_Title;
    public String Book_Author;
    public String Book_Publisher;
    public String Book_copies;
    public String Book_Status;

    public BookItem(String book_Title, String book_Author, String book_Publisher, String book_copies, String book_Status) {
        Book_Title = book_Title;
        Book_Author = book_Author;
        Book_Publisher = book_Publisher;
        Book_copies = book_copies;
        Book_Status = book_Status;
    }

    public String getBook_Title() {
        return Book_Title;
    }

    public String getBook_Author() {
        return Book_Author;
    }

    public String getBook_Publisher() {
        return Book_Publisher;
    }

    public String getBook_copies() {
        return Book_copies;
    }

    public String getBook_Status() {
        return Book_Status;
    }
}

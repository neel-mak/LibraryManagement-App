package com.librarymanagement.siddharth.snaplibrary;

/**
 * Created by NEEL on 02-12-2017.
 */

public class PatronBookItem {
    public String Book_Id;
    public String Book_Title;
    public String Book_Author;
    public String Book_Publisher;
    public String Book_copies;
    public String Book_Status;
    public String bookCheckedOutDate;
    public String bookDueDate;
    public String bookExpiratationDate;

    public PatronBookItem(String book_id, String book_Title, String book_Author, String book_Publisher, String book_copies, String book_Status, String bookCheckedOutDate, String bookDueDate, String bookExpiratationDate) {
        this.Book_Id = book_id;
        this.Book_Title = book_Title;
        this.Book_Author = book_Author;
        this.Book_Publisher = book_Publisher;
        this.Book_copies = book_copies;
        this.Book_Status = book_Status;
        this.bookCheckedOutDate = bookCheckedOutDate;
        this.bookDueDate = bookDueDate;
        this.bookExpiratationDate = bookExpiratationDate;
    }

    public String getBook_Title() {
        return this.Book_Title;
    }

    public String getBookExpiratationDate() {
        return this.bookExpiratationDate;
    }

    public String getBook_Author() {
        return this.Book_Author;
    }

    public String getBook_Publisher() {
        return this.Book_Publisher;
    }

    public String getBook_copies() {
        return this.Book_copies;
    }

    public String getBook_Status() {
        return this.Book_Status;
    }

    public String getBookCheckedOutDate() {
        return this.bookCheckedOutDate;
    }

    public String getBookDueDate() {
        return this.bookDueDate;
    }

    public String getDelimitedString () {
        return this.Book_Id + "@" + this.Book_Title + "@" + this.Book_Author + "@" + this.Book_Publisher + "@" + this.Book_copies + "@" + this.Book_Status + "@" + this.bookCheckedOutDate + "@" + this.bookDueDate + "@" + this.bookExpiratationDate;
    }
}

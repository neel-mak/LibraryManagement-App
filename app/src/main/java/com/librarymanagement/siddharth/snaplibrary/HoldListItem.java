package com.librarymanagement.siddharth.snaplibrary;

/**
 * Created by NEEL on 15-12-2017.
 */

public class HoldListItem {
    public String Hold_Book_Title;
    public String Hold_Book_Author;
    public String Hold_Book_Publisher;
    public String Hold_Book_expiratation_Date;

    public HoldListItem(String hold_Book_Title, String hold_Book_Author, String hold_Book_Publisher, String hold_Book_expiratation_Date) {
        Hold_Book_Title = hold_Book_Title;
        Hold_Book_Author = hold_Book_Author;
        Hold_Book_Publisher = hold_Book_Publisher;
        Hold_Book_expiratation_Date = hold_Book_expiratation_Date;
    }

    public String getHold_Book_Title() {
        return Hold_Book_Title;
    }

    public void setHold_Book_Title(String hold_Book_Title) {
        Hold_Book_Title = hold_Book_Title;
    }

    public String getHold_Book_Author() {
        return Hold_Book_Author;
    }

    public void setHold_Book_Author(String hold_Book_Author) {
        Hold_Book_Author = hold_Book_Author;
    }

    public String getHold_Book_Publisher() {
        return Hold_Book_Publisher;
    }

    public void setHold_Book_Publisher(String hold_Book_Publisher) {
        Hold_Book_Publisher = hold_Book_Publisher;
    }

    public String getHold_Book_expiratation_Date() {
        return Hold_Book_expiratation_Date;
    }

    public void setHold_Book_expiratation_Date(String hold_Book_expiratation_Date) {
        Hold_Book_expiratation_Date = hold_Book_expiratation_Date;
    }
}

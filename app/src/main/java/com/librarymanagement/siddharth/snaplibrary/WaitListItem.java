package com.librarymanagement.siddharth.snaplibrary;

/**
 * Created by NEEL on 16-12-2017.
 */

public class WaitListItem {
    public String WaitList_Book_Title;
    public String WaitList_Book_Author;
    public String WaitList_Book_Publisher;
    public String WaitList_Order;

    public WaitListItem(String waitList_Book_Title, String waitList_Book_Author, String waitList_Book_Publisher, String waitList_Order) {
        WaitList_Book_Title = waitList_Book_Title;
        WaitList_Book_Author = waitList_Book_Author;
        WaitList_Book_Publisher = waitList_Book_Publisher;
        WaitList_Order = waitList_Order;
    }

    public String getWaitList_Book_Title() {
        return WaitList_Book_Title;
    }

    public String getWaitList_Book_Author() {
        return WaitList_Book_Author;
    }

    public String getWaitList_Book_Publisher() {
        return WaitList_Book_Publisher;
    }

    public String getWaitList_Order() {
        return WaitList_Order;
    }
}

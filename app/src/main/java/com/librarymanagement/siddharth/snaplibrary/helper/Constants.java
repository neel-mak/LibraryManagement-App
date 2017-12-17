package com.librarymanagement.siddharth.snaplibrary.helper;

import java.util.regex.Pattern;

/**
 * Created by siddharthdaftari on 11/19/17.
 */

public class Constants {

    //URLs
    public static final String AWS_URL = "http://librarymanagement-backend.us-west-1.elasticbeanstalk.com";
    public static final String CALL_LOGIN_URL = "/user/login";
    public static final String CALL_REGISTER_URL = "/user/register";
    public static final String CALL_VERIFY_URL = "/user/verify";
    public static final String CALL_ADD_BOOK_URL = "/book/add";
    public static final String CALL_ISBN_URL = "/book/lookupISBN";
    public static final String CALL_GET_BOOKS = "/book/all";
    public static final String CALL_SEARCH_URL = "/book/search";
    public static final String CALL_UPDATE_BOOK_URL = "/book/update";
    public static final String CALL_DELETE_BOOK_URL = "/book/delete";
    public static final String CALL_CHECKED_OUT_BOOK_URL = "/book/myBooks";
    public static final String CALL_CHECKOUT_CART_URL = "/checkout";
    public static final String CALL_RETURN_BOOKS_URL = "/return";
    public static final String CALL_GET_BOOKS_ON_HOLD_URL = "/book/holds";
    public static final String CALL_GET_WAITLIST_BOOK_URL = "/book/waitlist";
    public static final String CALL_ADD_TO_WAITLIST_URL = "/checkout/addToWaitlist";
    public static final String CALL_TIME_URL = "/time";
    public static final String CALL_RENEW_URL = "/checkout/renew";

    //Update UI vars
    public static final String ACTION_UPDATE_LOGIN = "ACTION_UPDATE_LOGIN";
    public static final String ACTION_REGISTER = "ACTION_REGISTER";
    public static final String ACTION_VERIFY_USER = "ACTION_VERIFY_USER";
    public static final String ACTION_LOOKUP_ISBN = "ACTION_LOOKUP_ISBN";
    public static final String ACTION_GET_BOOKS = "ACTION_GET_BOOKS";
    public static final String ACTION_LOAD_UPDATE = "ACTION_LOAD_UPDATE";
    public static final String ACTION_UPDATE_BOOK = "ACTION_UPDATE_BOOK";
    public static final String ACTION_DELETE_BOOK = "ACTION_DELETE_BOOK";
    public static final String ACTION_ADD_BOOK = "ACTION_ADD_BOOK";
    public static final String ACTION_GET_BOOKS_FOR_PATRON = "ACTION_GET_BOOKS_FOR_PATRON";
    public static final String ACTION_GET_CHECKED_OUT_BOOKS = "ACTION_GET_CHECKED_OUT_BOOKS";
    public static final String ACTION_RETURN_BOOKS = "ACTION_RETURN_BOOKS";
    public static final String ACTION_CHECKOUT_CART = "ACTION_CHECKOUT_CART";
    public static final String ACTION_GET_BOOKS_ON_HOLD = "ACTION_GET_BOOKS_ON_HOLD";
    public static final String ACTION_GET_BOOKS_ON_WAITLIST = "ACTION_GET_BOOKS_ON_WAITLIST";
    public static final String ACTION_ADD_TO_WAITLIST = "ACTION_ADD_TO_WAITLIST";
    public static final String ACTION_LOAD_PATRON = "ACTION_LOAD_PATRON";
    public static final String ACTION_CHECK_AVAILABILITY = "ACTION_CHECK_AVAILABILITY";
    public static final String ACTION_GET_CURRENT_TIME = "ACTION_GET_CURRENT_TIME";
    public static final String ACTION_SET_NEW_TIME = "ACTION_SET_NEW_TIME";
    public static final String ACTION_CHECKOUT_HOLD_BOOK = "ACTION_CHECKOUT_HOLD_BOOK";
    public static final String ACTION_RENEW_BOOK = "ACTION_RENEW_BOOK";

    //error msgs
    public static final String GENERIC_ERROR_MSG = "Something went wrong. Please try again.";

    //keys of hashmap when calling an api
    public static final String REQUEST_JSON = "REQUEST_JSON";
    public static final String ACTION = "ACTION";
    public static final String ACTIVITY = "ACTIVITY";
    public static final String FRAGMENT = "FRAGMENT";
    public static final String VIEW = "VIEW";
    public static final String CONTEXT = "CONTEXT";

    //misc
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
}

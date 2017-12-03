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

    //Update UI vars
    public static final String ACTION_UPDATE_LOGIN = "ACTION_UPDATE_LOGIN";
    public static final String ACTION_REGISTER = "ACTION_REGISTER";
    public static final String ACTION_VERIFY_USER = "ACTION_VERIFY_USER";

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

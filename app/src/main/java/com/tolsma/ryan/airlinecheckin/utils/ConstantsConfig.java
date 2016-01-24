package com.tolsma.ryan.airlinecheckin.utils;

/**
 * Created by ryantolsma on 12/28/15.
 */
public class ConstantsConfig {
    //General Constants
    public final static String LOGIN_INTENT_ID = "ID";
    public final static String SOUTHWEST_AIRLINES = "Southwest Airlines";
    public final static String SOUTHWEST_API = "https://www.southwest.com";
    public final static long DAY_MILLLIS = 24 * 60 * 60 * 1000;

    /*
    These are the error screens that southwest provides when info
    is inputted incorrectly
     */
    public final static String SOUTHWEST_RESERVATION_ERROR =
            "We were unable to retrieve your reservation from our database";

    public final static String SOUTHWEST_TIMING_ERROR =
            "The request to check in and print your Boarding Pass" +
                    " is more than 24 hours prior to your scheduled departure " +
                    "or less than 1 hour prior to departure flight time";

    public static final String SOUTHWEST_NAME_ERROR =
            "The passenger name entered does not match" +
                    " one of the passenger names listed under the confirmation number";

    public static final String SOUTHWEST_PHONE_INCOMPLETE_ERROR =
            "The phone number was not entered completely.";

    public static final String SOUTHWEST_PHONE_BLANK_ERROR =
            "Please enter your phone number.";

    public static final String SOUTHWEST_EMAIL_BLANK_ERROR =
            "Please enter your e-mail address.";
    public static final String SOUTHWEST_EMAIL_FORMAT_ERROR =
            "The email address entered is in an invalid format " +
                    "or contains characters our system does not currently support.";


    /*
    Constants dealing with form-data for POST requests to southwest
     */
    public static final String SOUTHWEST_CONFIRMATION_CODE = "confirmationCode";

    public static final String SOUTHWEST_BOOK_NOW = "";
    public static final String SOUTHWEST_OPTION_EMAIL = "optionEmail";
    public static final String SOUTHWEST_OPTION_TEXT = "optionText";
    public static final String SOUTHWEST_BOARDING_INFO_CLASS = "boardingInfo";
    public final static String SOUTHWEST_CHECKIN_BUTTON = "Check In";

    /*
    View Constants
     */
    public static final float CARDVIEW_ELEVATION = 50.0f;
    // public static final float CARDVIEW_MAX_ELEVATION=10.0f;
    public static final float CARDVIEW_RADIUS = 30.0f;

    public static final String DATE_TITLE = "Date: ";
    public static final String NAME_TITLE = "Name: ";
    public static final String CONFIRMATION_CODE_TITLE = "Confirmation: ";
}

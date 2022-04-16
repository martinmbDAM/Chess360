package com.example.chess360.dialogs;

public interface ListenerSignUpForm {

    public static final int SIGN_UP_PLAYER = 0;
    public static final int SIGN_UP_CLUB = 1;
    public static final int SIGN_UP_ORGANIZER = 2;

    public void onSignUpFormClick(String [] str, int code);
}

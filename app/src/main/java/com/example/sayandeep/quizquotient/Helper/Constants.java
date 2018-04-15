package com.example.sayandeep.quizquotient.Helper;

import com.example.sayandeep.quizquotient.Objects.Questions;
import com.example.sayandeep.quizquotient.Objects.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sayandeep on 12-04-2018.
 * This is the class to create and store constants.
 */

public class Constants {
    public static String categoryId;
    public static User currentUser;
    public static final int SIGNUP_CODE =69;
    public static final int VERIFICATION_CODE=169;
    public static final String LOGIN_STATUS="isLoggedIn";
    public static boolean IS_LOGGED_IN=false;
    public static List<Questions> questionsList=new ArrayList<>();
    public final static long INTERVAL=1000;
    public final static long TIMEOUT=5000;
}

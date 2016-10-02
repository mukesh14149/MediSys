package com.example.mukesh.medisys.data;


import android.provider.BaseColumns;

/**
 * Created by mukesh on 1/10/16.
 */
public final class MediSysContract {



    private MediSysContract(){}

    public static class MediSysEntry implements BaseColumns{

        final public static String TABLE_NAME="sign_up";
        final public static String COLUMN_NAME_EMAIL="email";
        final public static String COLUMN_NAME_NAME="name";
        final public static String COLUMN_NAME_MOBILE="mobile";
        final public static String COLUMN_NAME_PASSWORD="password";


    }

}

package com.example.mukesh.medisys.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mukesh on 1/10/16.
 */
public final class MediSysContract {



    private MediSysContract(){}

    public static class MediSysEntry implements BaseColumns{



        public static String TABLE_NAME="sign_up";
        public static String COLUMN_NAME_EMAIL="email";
        public static String COLUMN_NAME_NAME="name";
        public static String COLUMN_NAME_MOBILE="mobile";
        public static String COLUMN_NAME_PASSWORD="password";


    }

}

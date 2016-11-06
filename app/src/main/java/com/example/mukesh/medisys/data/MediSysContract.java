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

    public static class MedicationEntry implements BaseColumns{

        final public static String TABLE_NAME="medication_detail";
        final public static String COLUMN_NAME_EMAIL="email";
        final public static String COLUMN_NAME_UNIQUE_ID="unique_id";
        final public static String COLUMN_NAME_DESCRIPTION="medication_description";
        final public static String COLUMN_NAME_REMINDER_TIMER="reminder_timer";
        final public static String COLUMN_NAME_SCHEDULE_DURAtION="schdule_duration";
        final public static String COLUMN_NAME_SCHEDULE_DAYS="schdule_days";
        final public static String COLUMN_NAME_SKIP="skip";

    }

    public static class MedicationReminders implements BaseColumns{
        final public static String TABLE_NAME="medication_reminders";
        final public static String COLUMN_NAME_UNIQUE_ID="unique_id";
        final public static String COLUMN_NAME_UNIQUE_TIMER_ID="unique_timer_id";
        final public static String COLUMN_NAME_DESCRIPTION="medication_description";
        final public static String COLUMN_NAME_REMINDER_TIMER="reminder_timer";

    }

}

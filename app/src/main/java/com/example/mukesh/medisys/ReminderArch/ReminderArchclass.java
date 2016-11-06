package com.example.mukesh.medisys.ReminderArch;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by mukesh on 21/10/16.
 */
public class ReminderArchclass implements Parcelable {
    private ArrayList<String> reminder_timer=null;
    private String schedule_duration=null;
    private String schedule_days=null;
    private String description=null;
    private String skip=null;
    private String unique_id=null;
    public ReminderArchclass(){

    }


    public void setReminder_timer(ArrayList<String> reminder_timer){
        this.reminder_timer=reminder_timer;
    }

    public void setSchedule_duration(String schedule_duration){
        this.schedule_duration=schedule_duration;
    }

    public  void setSchedule_days(String schedule_days){
        this.schedule_days=schedule_days;
    }

    public void setDescription(String description){
        this.description=description;
    }
    public void setUnique_id(String unique_id){
        this.unique_id=unique_id;
    }

    public void setskip(String skip){
        this.skip=skip;
    }


    public ArrayList<String> getReminder_timer(){
        return reminder_timer;
    }

    public String getSchedule_duration(){
        return schedule_duration;
    }

    public  String getSchedule_days(){
        return  schedule_days;
    }

    public String getDescription(){
        return description;
    }
    public String getUnique_id(){
        return unique_id;
    }
    public String getskip(){
        return skip;
    }
    private ReminderArchclass(Parcel in){
        reminder_timer = (ArrayList<String>) in.readSerializable();;;
        schedule_duration = in.readString();
        schedule_days = in.readString();
        description = in.readString();
        skip=in.readString();
        unique_id=in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(reminder_timer);
        parcel.writeString(schedule_duration);
        parcel.writeString(schedule_days);
        parcel.writeString(description);
        parcel.writeString(skip);
        parcel.writeString(unique_id);
    }
    public static final Parcelable.Creator<ReminderArchclass> CREATOR = new Parcelable.Creator<ReminderArchclass>()
    {

        @Override
        public ReminderArchclass createFromParcel(Parcel parcel)
        {
            return new ReminderArchclass(parcel);
        }

        @Override
        public ReminderArchclass[] newArray(int i)
        {
            return new ReminderArchclass[i];
        }
    };
}

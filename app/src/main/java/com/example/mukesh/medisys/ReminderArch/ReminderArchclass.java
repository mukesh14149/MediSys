package com.example.mukesh.medisys.ReminderArch;

/**
 * Created by mukesh on 21/10/16.
 */
public class ReminderArchclass {
    private String reminder_timer=null;
    private String schedule_duration=null;
    private String schedule_days=null;
    private String description=null;

    public void setReminder_timer(String reminder_timer){
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


    public String getReminder_timer(){
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


}

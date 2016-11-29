package com.example.mukesh.medisys;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mg on 11/6/16.
 */

public class PropNumberDialogFragment extends DialogFragment {
    NumberPicker numberpicker;
    String selected_no;
    String type="";
    ArrayList<Integer> mSelectedItems;
    String reminder_timer;

    public interface OnCompleteListener {
        void onComplete(String reminder_timer, int selectedHour, int selectedMinute);
    }

    private OnCompleteListener mListener;

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //getting proper access to LayoutInflater is the trick. getLayoutInflater is a                   //Function

        type=getArguments().getString("type");
        Log.i("type",type);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        switch (type) {
            case "popupnod":
                LayoutInflater inflater = getActivity().getLayoutInflater();

                View view = inflater.inflate(R.layout.number_picker_dialog, null);
                builder.setView(view);
                builder.setTitle("Select number").setNeutralButton(
                        "", null);


                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        TextView setnod = (TextView) getActivity().findViewById(R.id.set_nod);
                        setnod.setText(selected_no);


                        getDialog().dismiss();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        getDialog().dismiss();
                    }
                });


                numberpicker = (NumberPicker) view.findViewById(R.id.numberPicker1);
                numberpicker.setMinValue(0);
                numberpicker.setMaxValue(365);
                numberpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        //textview.setText("Selected Value is : " + newVal);
                        selected_no = Integer.toString(newVal);
                    }
                });
                break;
            case "popupsdow":
                mSelectedItems = new ArrayList();
                builder.setTitle("Pick Items")
                        // Specify the list array, the items to be selected by default (null for none),
                        // and the listener through which to receive callbacks when items are selected
                        .setMultiChoiceItems(R.array.toppings, null,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which,
                                                        boolean isChecked) {
                                        if (isChecked) {
                                            // If the user checked the item, add it to the selected items
                                            mSelectedItems.add(which);
                                        } else if (mSelectedItems.contains(which)) {
                                            // Else, if the item is already in the array, remove it
                                            mSelectedItems.remove(Integer.valueOf(which));
                                        }
                                    }
                                })
                        // Set the action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK, so save the mSelectedItems results somewhere
                                // or return them to the component that opened the dialog
                                Toast.makeText(getActivity().getApplicationContext(), "mmmml", Toast.LENGTH_LONG).show();
                                String temp = "";
                                Log.i("Length of array", Integer.toString(mSelectedItems.size()));
                                for (Integer i : mSelectedItems) {
                                    if (i == 0)
                                        temp += "Sun" + " ";
                                    if (i == 1)
                                        temp += "Mon" + " ";
                                    if (i == 2)
                                        temp += "Tue" + " ";
                                    if (i == 3)
                                        temp += "Wed" + " ";
                                    if (i == 4)
                                        temp += "Thu" + " ";
                                    if (i == 5)
                                        temp += "Fri" + " ";
                                    if (i == 6)
                                        temp += "Sat" + " ";
                                }
                                TextView textView = (TextView) getActivity().findViewById(R.id.set_sdow);
                                textView.setText(temp);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                Toast.makeText(getActivity().getApplicationContext(), "ccccl", Toast.LENGTH_LONG).show();
                            }
                        });
                break;
            case "popuptimepicker":
                Log.i("Check timepicker", "In timepicker");
                TextView startdate = (TextView) getActivity().findViewById(R.id.start_date);
                String[] te = startdate.getText().toString().split("-");
                final int Year = Integer.parseInt(te[0]);
                final int Month = Integer.parseInt(te[1]);
                final int Day = Integer.parseInt(te[2]);
                //System.out.println(Year+" "+Month+" "+Day+"in oncreate method"+settime.getId());



/*

                t=(TextView) findViewById(v.getId());
*/

                final Calendar beginCal = Calendar.getInstance();

                final Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Log.i("Check timepicker", "Below me");
                        System.out.println(selectedHour + ":" + selectedMinute + "mml");
                        //          t.setText( converttime(selectedHour + ":" + selectedMinute));
                        beginCal.set(Year, Month - 1, Day, selectedHour, selectedMinute);
                        System.out.println(selectedHour + ":" + selectedMinute + "mml" + beginCal.getTime().toString());

                        reminder_timer = beginCal.getTime().toString();
                        mListener.onComplete(reminder_timer, selectedHour, selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time

                mTimePicker.setTitle("Select Time");
                return mTimePicker;


        }

        return builder.create();
    }
}

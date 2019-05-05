package com.example.littlebrother;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements MessageListener{
    private int number_of_kids;
    public static int iterations;
    class_info[] childrenArray;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button blocation;
    ImageButton imageButton1;
    EditText et1;
    EditText et2;
    EditText et3;
    EditText et4Message;
    TextView status_update;
    TextView child_number;
    Button children[];
    TextView child_name;
    Button textviewexceptions;

    public int startmin=0, starthour=12, endmin=0, endhour=13;
    public boolean excepted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_of_kids();
            }
        });
        //Register sms listener
        SmsListener.bindListener(this);
    }

    private void number_of_kids() {
        setContentView(R.layout.number_of_kids);

        et1=findViewById(R.id.editText1);

        b2=findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_of_kids = Integer.parseInt(et1.getText().toString());
                childrenArray = new class_info[number_of_kids];
                child_info(0);
            }
        });
    }

    private void child_info(final int index) {
        setContentView(R.layout.child_info);

        child_number = findViewById(R.id.textView12up);
        child_number.setText("#" + (index+1));

        et2=findViewById(R.id.editText2);
        et3=findViewById(R.id.editText3);
        /*et4=(EditText)findViewById(R.id.editText4);*/

        b3 = findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            final int i = index;

            @Override
            public void onClick(View v) {
                childrenArray[i] = new class_info(et3.getText().toString());
                childrenArray[i].setTelephone(et2.getText().toString());
                if (index+1 < number_of_kids)
                    child_info(i+1);
                else
                    child_list();
            }
        });
        /*
        b4=(Button)findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.child_status);
            }
        });
        b5=(Button)findViewById(R.id.button5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.child_chat);
            }
        });
        b6=(Button)findViewById(R.id.button6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.number_of_kids);
            }
        });*/
    }

    private void child_list() {
        setContentView(R.layout.child_list);

        children = new Button[6];
        children[0] = findViewById(R.id.button);
        children[1] = findViewById(R.id.button9);
        children[2] = findViewById(R.id.button10);
        children[3] = findViewById(R.id.button11);
        children[4] = findViewById(R.id.button7);
        children[5] = findViewById(R.id.button12);

        for (int i = 0; i < number_of_kids; i++) {
            final int index = i;
            children[i].setText(childrenArray[i].name);
            children[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    child_status(index);
                }
            });
        }
    }

    private void child_status(final int index) {
        setContentView(R.layout.child_status);
        blocation = findViewById(R.id.buttonlocation);
        blocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(childrenArray[index].telephone, null, "*****RequestLocation*****", null, null);
            }
        });
        child_name = findViewById(R.id.textView2);
        child_name.setText(childrenArray[index].name);
        status_update=(TextView)findViewById(R.id.Status_field);
        b5=(Button)findViewById(R.id.button5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child_chat(index);
            }
        });
        b6=(Button)findViewById(R.id.button6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child_exceptions(index);
            }
        });
    }

    private void child_chat(final int index) {
        setContentView(R.layout.child_chat);
        et4Message=findViewById(R.id.editText4message);
        child_name = findViewById(R.id.textView2);
        child_name.setText(childrenArray[index].name);
        imageButton1=findViewById(R.id.imageButton);
        b4=(Button)findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child_status(index);
            }
        });
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(childrenArray[index].telephone, null, "*****" + et4Message.getText().toString() + "*****", null, null);
            }
        });
        b6=(Button)findViewById(R.id.button6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child_exceptions(index);
            }
        });
    }

    private void child_exceptions(final int index) {
        setContentView(R.layout.child_exceptions);

        child_name = findViewById(R.id.textView2);
        child_name.setText(childrenArray[index].name);

        b4=(Button)findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child_status(index);
            }
        });
        b5=(Button)findViewById(R.id.button5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child_chat(index);
            }
        });

        textviewexceptions = findViewById(R.id.textViewexceptions);
        final Context c = this;
        textviewexceptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!excepted) {
                    TimePickerDialog.OnTimeSetListener slistener = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if (view.isShown()) {
                                starthour = hourOfDay;
                                startmin = minute;
                                ((TextView)findViewById(R.id.exception)).setText(starthour+":"+String.format("%02d",startmin)+" - "+endhour+":"+String.format("%02d",endmin));
                            }
                        }
                    };
                    TimePickerDialog.OnTimeSetListener elistener = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if (view.isShown()) {
                                endhour = hourOfDay;
                                endmin = minute;
                                ((TextView)findViewById(R.id.exception)).setText(starthour+":"+String.format("%02d",startmin)+" - "+endhour+":"+String.format("%02d",endmin));
                            }
                        }
                    };
                    TimePickerDialog dialog = new TimePickerDialog(c, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, elistener, 13, 0, false);
                    dialog.setTitle("Set Exception End Time:");
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                    dialog = new TimePickerDialog(c, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, slistener, 12, 0, false);
                    dialog.setTitle("Set Exception Start Time:");
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                    textviewexceptions.setText("Disable");
                    excepted = true;
                } else {
                    ((TextView)findViewById(R.id.exception)).setText("");
                    textviewexceptions.setText("Enable");
                    excepted = false;
                }
            }
        });
    }

    @Override
    public void messageReceived(String message) {
        if (message.substring(0,5).equals("*****")) {
            status_update.setText("Message Worked");
        }
    }
}


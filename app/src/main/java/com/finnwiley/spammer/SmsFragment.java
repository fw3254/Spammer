package com.finnwiley.spammer;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class SmsFragment extends Fragment {
   public SmsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sms,  container, false);
        Button button1 = (Button) view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(), Contacts.class);
                startActivity(intent);*/

            }
        });

        final NotificationManager notifMgr = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        Button button2 = (Button) view.findViewById(R.id.button2);
        button2.setOnClickListener(new Button.OnClickListener() {

            int sent=1;
            int sent1;
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) view.findViewById(R.id.textView1);
                EditText editText1 = (EditText) view.findViewById(R.id.editText1);
                EditText editText2 = (EditText) view.findViewById(R.id.editText1);
                String number=editText1.getText().toString();
                String message=editText2.getText().toString();
                if(!number.equals("")) {
                    if(!message.equals("")) {
                        Notification notif=new Notification();
                        notifMgr.cancelAll();
                        notif.ledARGB=0xffffffff;
                        notif.flags|=Notification.FLAG_SHOW_LIGHTS;
                        notif.ledOnMS=100;
                        notif.ledOffMS=1000000000;
                        notifMgr.notify(1, notif);
                        sendSMS(number, message);
                        sent1=sent++;
                        if(sent1==1) {
                            textView.setText(sent1 + " spam message sent");
                        }else {
                            textView.setText(sent1 + " spam messages sent");
                        }
                        sent1=sent;
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Input valid phone number and message",
                            Toast.LENGTH_SHORT).show();
                    textView.setText("");
                    sent=1;
                }


            }
        });
        button2.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                EditText editText1 = (EditText) view.findViewById(R.id.editText1);
                EditText editText2 = (EditText) view.findViewById(R.id.editText2);
                TextView textView = (TextView) view.findViewById(R.id.textView1);
                editText1.setText("");
                editText2.setText("");
                textView.setText("");
                Toast.makeText(getActivity(), "Cleared", Toast.LENGTH_SHORT).show();
                notifMgr.cancelAll();
                return false;
            }
        });

        return view;

    }
    private void sendSMS(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }


}

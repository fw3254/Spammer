package com.finnwiley.spammer;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.techventus.server.voice.Voice;

import java.io.IOException;


public class GvFragment extends Fragment {

    public GvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_gv, container, false);
        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        textView2.setText("IMPORTANT: Make sure that your Google Voice account info is" +
                " entered in the Settings page before using this function");

        final Button button1 = (Button) view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

        final NotificationManager notifMgr = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        boolean loggedIn=false;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences sharedPrefs;
        sharedPrefs = getActivity().getSharedPreferences(
                "com.finnwiley.spammer", Context.MODE_PRIVATE);
        String email = sharedPrefs.getString("gvEmail", "");
        String passwd = sharedPrefs.getString("gvPasswd", "");
        if (!email.equals("")) {
            if (!passwd.equals("")) {
                try {

                    final Voice voice = new Voice(email, passwd);
                    voice.login();

                    Toast.makeText(getActivity(), "Logged in to " + email, Toast.LENGTH_SHORT).show();

                    Button button2 = (Button) view.findViewById(R.id.button2);
                    button2.setOnClickListener(new View.OnClickListener() {

                        int sent = 1;
                        int sent1;

                        @Override
                        public void onClick(View v) {
                            TextView textView1 = (TextView) view.findViewById(R.id.textView1);
                            EditText editText1 = (EditText) view.findViewById(R.id.editText1);
                            EditText editText2 = (EditText) view.findViewById(R.id.editText2);
                            String number = editText1.getText().toString();
                            String message = editText2.getText().toString();
                            if (!message.equals("")) {
                                try {
                                    Notification notif=new Notification();
                                    notifMgr.cancelAll();
                                    notif.ledARGB=0xffffffff;
                                    notif.flags|=Notification.FLAG_SHOW_LIGHTS;
                                    notif.ledOnMS=100;
                                    notif.ledOffMS=1000000000;
                                    notifMgr.notify(1, notif);
                                    voice.sendSMS(number, message);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                sent1 = sent++;
                                if (sent1 == 1) {
                                    textView1.setText(sent1 + " spam message sent");
                                } else {
                                    textView1.setText(sent1 + " spam messages sent");
                                }
                                sent1 = sent;
                            } else {
                                Toast.makeText(getActivity(), "Input valid phone number and message",
                                        Toast.LENGTH_SHORT).show();
                                textView1.setText("");
                                sent = 1;
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

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(getActivity(), "Not logged in", Toast.LENGTH_SHORT).show();
        }



        return view;
    }

}

package com.finnwiley.spammer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;


public class Settings extends Activity {

    SharedPreferences sharedPrefs;
    EditText editText1;
    EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView textView=(TextView) findViewById(R.id.textView1);
        textView.setText("Google Voice Account:");
        editText1=(EditText) findViewById(R.id.editText1);
        editText1.setHint("Email");
        editText2=(EditText) findViewById(R.id.editText2);
        editText2.setHint("Password");

        sharedPrefs=this.getSharedPreferences(
                "com.finnwiley.spammer", Context.MODE_PRIVATE);
        String email = sharedPrefs.getString("gvEmail", null);
        String passwd = sharedPrefs.getString("gvPasswd", null);
        editText1.setText(email);
        editText2.setText(passwd);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_save) {
            sharedPrefs=this.getSharedPreferences(
                    "com.finnwiley.spammer", Context.MODE_PRIVATE);
            editText1=(EditText) findViewById(R.id.editText1);
            editText2=(EditText) findViewById(R.id.editText2);
            String email=editText1.getText().toString();
            String passwd=editText2.getText().toString();
            sharedPrefs.edit().putString("gvEmail", email).apply();
            sharedPrefs.edit().putString("gvPasswd", passwd).apply();
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

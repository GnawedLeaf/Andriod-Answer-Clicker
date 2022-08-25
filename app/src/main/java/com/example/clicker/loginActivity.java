package com.example.clicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.view.View;
import android.content.Intent;
import java.net.URL;
import java.net.*;
import android.os.AsyncTask;
import android.os.Bundle;
import java.io.IOException;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import android.util.Log;


import android.os.Bundle;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;

public class loginActivity extends AppCompatActivity {
    private EditText passwordBox,ipInputBox;
    private ImageButton btnSignIn;
    private String passwordInput, correctCode, ipInput;
    private String ipMaster;
    private InputStream message;
    private TextView txtReplace;
    private int count, intInput,start = 0,master = 1;
    private Button btnUpdate,btnTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);

        btnTimer = (Button) findViewById(R.id.btnTimer);
        btnSignIn = (ImageButton) findViewById(R.id.btnSignIn);
        passwordBox = (EditText) findViewById(R.id.passwordEditText);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        ipInputBox = (EditText) findViewById(R.id.ipInput);
        txtReplace = (TextView) findViewById(R.id.replace);

        btnTimer.performClick();

       //txtCorrect.setVisibility(View.GONE);
        //txtResult.setVisibility(View.GONE);
        btnTimer.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.GONE);

        ipInput = ipInputBox.getText().toString();
        ipMaster = "http://" + ipInput + ":9999";
    }

    public void btnSignInHandler(View v){
        ipInput = ipInputBox.getText().toString();

        ipInput = "10.27.25.32";
        ipMaster = "http://" + ipInput + ":9999";
        ipMaster = "http://10.27.25.32:9999";

        //txtCorrect.setText(correctCode);
        txtReplace.setText("Clicked");
        passwordInput = passwordBox.getText().toString();
        passwordInput = passwordInput + "\n";

        try{
            if (passwordInput.contentEquals(correctCode)){
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("key", ipInput);
                txtReplace.setText("Success!");


                master = 0;
                startActivity(intent);
                finish();
            }
            else{
                txtReplace.setText("Wrong Code!");

            }

        } catch(Exception ex){

        }


    }
    public void btnTimerHandler(View view){
        if (start == 0 && master == 1){
            start =1;
            new CountDownTimer(2000,1000){
                public void onTick(long millisRemaining){
                    //title.setText("Seconds remaining: " + millisRemaining/1000);
                }
                public void onFinish(){
                    start = 0;

                    //FUNCTIONS FOR TIMER PUT HERE
                    btnTimer.performClick();
                    btnUpdate.performClick();
                    //txtReplace.setText(" ");
                }
            }.start();
        }
    }

    //http://192.168.60.175:9999/clicker/getcode
    public void btnUpdateHandler(View view){
        try{
            //new HttpTask().execute(ipMaster+ "/clicker/getcode");
            new HttpTask().execute("http://10.27.25.32:9999/clicker/getcode");
            if (correctCode != null){
                txtReplace.setText("Ip connected");
            }
            else {
                txtReplace.setText("Connecting ip...");
            }

        } catch (Exception ex){
            txtReplace.setText("Ip cannot connect");
        }


        //txtCorrect.setText(correctCode);
    }

    private class HttpTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strURLs) {
            URL url = null;
            HttpURLConnection conn = null;
            try {
                url = new URL(strURLs[0]);
                conn = (HttpURLConnection) url.openConnection();
                // Get the HTTP response code (e.g., 200 for "OK", 404 for "Not found")
                // and pass a string description in result to onPostExecute(String result
                try {
                    message = conn.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {  // 200
                    return "OK (" + responseCode + ")";
                } else {
                    return "Fail (" + responseCode + ")";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }

        }

        // Displays the result of the AsyncTask.
        // The String result is passed from doInBackground().
        @Override
        protected void onPostExecute(String result) {
            //txtResponse.setText(result);  // put it on TextView
            correctCode = convertStreamToString(message);

            //txtResponse2.setText(convertStreamToString(message));
        }
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }






}
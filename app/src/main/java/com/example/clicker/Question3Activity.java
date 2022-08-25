package com.example.clicker;

import android.util.*;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import android.os.CountDownTimer;
import android.widget.TextView;
import java.math.*;
import java.util.Random;
import android.widget.ImageButton;
import android.widget.ImageView;



public class Question3Activity extends AppCompatActivity {
    private WebView webView;
    //private Button btnA,btnB,btnC,btnD, btnTimer;
    public int qNo = 3, start =0,master = 1, pressed = 0;
    private String ipAddress = "http://10.27.25.32:9999/clicker/select?choice=";
    private String ipAddress2="http://10.27.25.32:9999";
    private String choice;
    private TextView txtTimer;
    private Button btnTimer;
    private ImageButton btnA,btnB,btnC,btnD,btnNext;
    private InputStream message;
    private String enableState;
    private String startState ="1" +"\n";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_question3);

        btnA = (ImageButton) findViewById(R.id.btnA);
        btnB = (ImageButton) findViewById(R.id.btnB);
        btnC = (ImageButton) findViewById(R.id.btnC);
        btnD = (ImageButton) findViewById(R.id.btnD);
        btnNext = (ImageButton) findViewById(R.id.btnNext);


        webView = (WebView) findViewById(R.id.webView);
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        btnTimer = (Button) findViewById(R.id.btnTimer);

        //webView.setVisibility(View.GONE);
        txtTimer.setVisibility(View.GONE);
        btnTimer.setVisibility(View.GONE);

        btnNext.setEnabled(false);
        //btnTimer.performClick();

        disableAllButtonsImg();
        btnTimer.performClick();

    }

    public void btnAHandler(View view){
        //btnA.setEnabled(false);
        pressed = 1;
        choice = "a";
        webView.loadUrl(ipAddress + choice +"&questionNo=" + qNo);
        btnA.setImageResource(R.drawable.btnadown);
        disableAllButtons();
        enableNextButton();
    }

    public void btnBHandler(View view){
        //btnB.setEnabled(false);
        pressed = 1;
        choice = "b";
        webView.loadUrl(ipAddress + choice +"&questionNo=" + qNo);
        btnB.setImageResource(R.drawable.btnbdown);
        disableAllButtons();

        enableNextButton();

    }

    public void btnCHandler(View view){
        //btnC.setEnabled(false);
        pressed = 1;
        choice = "c";
        webView.loadUrl(ipAddress + choice +"&questionNo=" + qNo);
        btnC.setImageResource(R.drawable.btncdown);
        disableAllButtons();
        enableNextButton();

    }
    public void btnDHandler(View view){
        //btnD.setEnabled(false);
        pressed = 1;
        choice = "d";
        webView.loadUrl(ipAddress + choice +"&questionNo=" + qNo);
        btnD.setImageResource(R.drawable.btnddown);
        disableAllButtons();
        enableNextButton();
    }


    public void btnNextHandler(View view){
        Intent intent = new Intent(this, endActivity.class);
        master =0 ;
        startActivity(intent);
        finish();

    }

    public void btnTimerHandler(View view){
        if (start == 0 && master == 1){
            start =1;
            new CountDownTimer(2000,1000){
                public void onTick(long millisRemaining){
                    txtTimer.setText("Seconds remaining: " + millisRemaining/1000);
                }
                public void onFinish(){
                    txtTimer.setText("Time's up! bitch");
                    start = 0;


                    //FUNCTIONS FOR TIMER PUT HERE
                    btnTimer.performClick();
                    //qNo = randomNumber(1,4);
                    //choice = randomChoice();
                    //btnA.performClick();
                    new HttpTask().execute(ipAddress2 +"/clicker/getenable");
                    btnTimer.setText(""+enableState);
                    try{
                        if (startState.contentEquals(enableState)){
                            if (pressed == 0){
                                enableAllButtonsImg();
                            }
                        }
                        //If user pressed alr, dont push all btns down, just leave it
                        //If user hasnt pressed yet, then disable all;
                        else if (pressed == 0){
                            disableAllButtonsImg();
                        }
                    } catch (Exception ex){
                    }
                }
            }.start();
        }
    }
    public int randomNumber(int min, int max){
        Random r = new Random();
        int randomNum = r.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public String randomChoice(){
        String randomChoice = "";
        int num = randomNumber(1,4);
        if (num == 1) {randomChoice = "a";}
        else if (num == 2) {randomChoice = "b";}
        else if (num == 3) {randomChoice = "c";}
        else if (num == 4) {randomChoice = "d";}

        return randomChoice;
    }


    public void disableAllButtons(){
        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);
    }

    public void enableAllButtons(){
        btnA.setEnabled(true);
        btnB.setEnabled(true);
        btnC.setEnabled(true);
        btnD.setEnabled(true);
    }

    public void disableAllButtonsImg(){
        btnA.setEnabled(false);
        btnA.setImageResource(R.drawable.btnadown);
        btnB.setEnabled(false);
        btnB.setImageResource(R.drawable.btnbdown);
        btnC.setEnabled(false);
        btnC.setImageResource(R.drawable.btncdown);
        btnD.setEnabled(false);
        btnD.setImageResource(R.drawable.btnddown);
    }

    public void enableAllButtonsImg(){
        btnA.setEnabled(true);
        btnA.setImageResource(R.drawable.btna);
        btnB.setEnabled(true);
        btnB.setImageResource(R.drawable.btnb);
        btnC.setEnabled(true);
        btnC.setImageResource(R.drawable.btnc);
        btnD.setEnabled(true);
        btnD.setImageResource(R.drawable.btnd);
    }

    public void enableNextButton(){
        btnNext.setEnabled(true);
        btnNext.setImageResource(R.drawable.appnextbutton);
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
            enableState = convertStreamToString(message);

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
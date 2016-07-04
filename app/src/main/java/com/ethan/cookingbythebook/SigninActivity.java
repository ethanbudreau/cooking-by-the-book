package com.ethan.cookingbythebook;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.io.*;

import android.content.Intent;
import android.util.Log;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.app.Activity;

public class SigninActivity  extends AsyncTask<String,Void,String>{
    private Context context;
    private int byGetOrPost = 0;
    public final static String RESULT = "com.ethan.cookingbythebook.RESULT";

    //flag 0 means get and 1 means post.(By default it is get.)
    public SigninActivity(Context context,int flag) {
        this.context = context;
        byGetOrPost = flag;
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {

        if(byGetOrPost == 1){ //means by Get Method
            InputStream is = null;
            int len=500;
            try{
                Log.d("DEBUG_COOKING_BY_THE_BOOK", "RETURN_VALUE6");
                String username = (String)arg0[0];
                String password = (String)arg0[1];
                String link = "http://myphpmysqlweb.hostei.com/login.php?username="+username+"& password="+password;
                Log.d("DEBUG_COOKING_BY_THE_BOOK", "RETURN_VALUE5");
/*
                URL url = new URL(link);
                AndroidHttpClient client = new HttpURLConnection();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
*/
                String data  = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                URL url = new URL("http://maseratiftp.no-ip.org:8080/login.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d("DEBUG_COOKING_BY_THE_BOOK","RETURN_VALUE54");
                conn.setReadTimeout(1000 /* milliseconds */);
                conn.setConnectTimeout(1500 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept-Charset","UTF-8");
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36"); // Do as if you're using Chrome 41 on Windows 7.
                conn.setDoOutput(true);
                conn.setDoInput(true);

                DataOutputStream dStream = new DataOutputStream(conn.getOutputStream());

                // Starts the query
                conn.connect();
                dStream.writeBytes(data); //Writes out the string to the underlying output stream as a sequence of bytes
                dStream.flush(); // Flushes the data output stream.
                dStream.close(); // Closing the output stream.

                Log.d("DEBUG_COOKING_BY_THE_BOOK","data"+data);
                int response = conn.getResponseCode();
                Log.d("DEBUG_COOKING_BY_THE_BOOK", "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                Log.d("DEBUG_COOKING_BY_THE_BOOK",contentAsString);
                return contentAsString;
            }

            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }
        else{
            try{
                String username = (String)arg0[0];
                String password = (String)arg0[1];

                String link="http://myphpmysqlweb.hostei.com/loginpost.php";
                String data  = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }
    }

    @Override
        protected void onPostExecute(String result){
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(RESULT, result);
        context.startActivity(intent);
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}

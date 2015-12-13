package uk.ac.ic.ad5915.androidrs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button send;
    //private URI uri;

    private static String myPARAMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.textName);
        password = (EditText) findViewById(R.id.textPassword);

        send = (Button) findViewById(R.id.send_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    login(view);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class DoPostRequest extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            HashMap<String, String> map = new HashMap<>();
            map.put("username", params[0]);
            map.put("password", params[1]);
            String responseCode = performPostCall("http://3ced98c4.ngrok.io/api/login/", map);

            return responseCode;
        }

        protected void onPostExecute(String result) {
            if (!result.equals("")) {
                Log.e("some id", "valid auth");
            } else {
                Log.e("some id", "invalid auth");
            }

            finish();
        }
    }


    public void login(View view) throws IOException {
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();

        new DoPostRequest().execute(usernameString, passwordString);

    }

    public String  performPostCall(String requestURL, HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        Log.e("alex", "performing post");
        HttpURLConnection conn = null;
        try {
            url = new URL(requestURL.trim());
            System.setProperty("http.keepAlive", "false");
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(300000);
            conn.setConnectTimeout(300000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "Accept-Encoding", "" );
            conn.setRequestProperty("Connection", "close");
            Log.e("alex", "so far so good");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            Log.e("alex", "so far so good 2");

            Log.e("alex", "string: " + getPostDataString(postDataParams));
            //writer.flush();
            //writer.close();
            os.close();
            int responseCode=conn.getResponseCode();


            Log.e("alex", "Response code: " + responseCode + "");
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
                response += "sss";
            }
            else {
                response="";

            }
        } catch (Exception e) {
            Log.e("alex", "error message", e);

        } finally {
            conn.disconnect();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}

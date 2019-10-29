package com.example.notify;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class secondActivity extends AppCompatActivity
{

    String myKey = "AAAApuSY3t0:APA91bEeY-GLxCEgfDhSh0r7hbfnmfaPnj2-YYBL48dXzRbtk6JQRGNT4jyUWJ3un-lqbo4bhFS9ybrWKlO3XqQSQBFg9QK_DK_WXYa6sun8esonZaQSGMHmzhR_u2XPx3RY4yPJpprS" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        sendRequestByOk();
    }



    @SuppressLint("StaticFieldLeak")
    private void sendRequestByOk() {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                JSONObject json = new JSONObject();
                JSONObject jsonData = new JSONObject();
                String topic =  "/topics/mess" ;
                try {
                    jsonData.put("body","Hi!! This is the message from device");
                    jsonData.put("title","dummy title");
                    json.put("notification",jsonData);
                    json.put("to",topic);

                    RequestBody body = RequestBody.create(JSON,json.toString());
                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .header("Authorization", "key=" + myKey)
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();
                    String finalResponse = response.body().string();
                    // Toast.makeText(MainActivity.this, finalResponse,Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(secondActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return  null;
            }
        }.execute();
    }


}



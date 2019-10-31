package com.example.notify;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class secondActivity extends AppCompatActivity
{
    private Button btn;
    private EditText t,b;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private ProgressDialog dialog;
    private Button btn4;
    String myKey = "AAAApuSY3t0:APA91bEeY-GLxCEgfDhSh0r7hbfnmfaPnj2-YYBL48dXzRbtk6JQRGNT4jyUWJ3un-lqbo4bhFS9ybrWKlO3XqQSQBFg9QK_DK_WXYa6sun8esonZaQSGMHmzhR_u2XPx3RY4yPJpprS" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btn = findViewById(R.id.button3);
        t = findViewById(R.id.title);
        b = findViewById(R.id.body);
        btn4 = findViewById(R.id.button4);
        dialog = new ProgressDialog(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestByOk();
            }
        });
//        sendRequestByOk();

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(secondActivity.this,NotificationLog.class));
            }
        });

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
                    jsonData.put("body",b.getText().toString());
                    jsonData.put("title",t.getText().toString());
//                    jsonData.put("body","this is a body...");
//                    jsonData.put("title","title");
                    json.put("notification",jsonData);
                    json.put("to",topic);

                    RequestBody body = RequestBody.create(JSON,json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization", "key=" + myKey)
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build();

                    Response response = client.newCall(request).execute();
                    String finalResponse = response.body().string();
//                     Toast.makeText(secondActivity.this, finalResponse,Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(secondActivity.this, "hello", Toast.LENGTH_SHORT).show();

//                    Toast.makeText(secondActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                return  null;
            }
        }.execute();

        dialog.setMessage("Sending Notifications...");
        dialog.show();
        final Map<String,String> data = new HashMap<>();
        data.put("title",t.getText().toString());
        data.put("body",b.getText().toString());

        firestore.collection("notificationLog").document("id").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String x= documentSnapshot.getString("id");
                int y = (Integer.valueOf(x)+1);
                x = String.valueOf(y);
                final String finalX = x;
                data.put("id",finalX);
                firestore.collection("notificationLog").document("admin").collection("notifications").document(x).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Map<String,String> nid = new HashMap<>();
                        nid.put("id", finalX);
                        firestore.collection("notificationLog").document("id").update("id", finalX).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(secondActivity.this, "Successfully sent the notification", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(secondActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(secondActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(secondActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });



    }


}



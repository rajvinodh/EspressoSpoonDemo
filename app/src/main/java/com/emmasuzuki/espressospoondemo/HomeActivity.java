package com.emmasuzuki.espressospoondemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.reusable_activities.MenuActivity;

import java.io.IOException;

public class HomeActivity extends Activity {

    TextView txtString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        txtString = (TextView)findViewById(R.id.welcomeMsg);

        View testMeButton = findViewById(R.id.testme);
        testMeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(HomeActivity.this, MenuActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }

//    void run() throws IOException {
//
//        OkHttpClient client = new NetworkModule().provideOkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                call.cancel();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                final String myResponse = response.body().string();
//
//                HomeActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        txtString.setText(myResponse);
//                    }
//                });
//
//            }
//        });
//    }
}

package com.indrul.bfciinstructor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void announce(View view) {
        Intent intent =new Intent(this,Announcement_Activity.class);
        startActivity(intent);
    }

    public void matrial(View view) {
        Intent intent =new Intent(this,MatrialActivity.class);
        startActivity(intent);
    }

    public void profile(View view) {
        Intent intent =new Intent(this,profileActivity.class);
        startActivity(intent);
    }

    public void question(View view) {
        Intent intent =new Intent(this,StudentQuestionActivity.class);
        startActivity(intent);
    }
}

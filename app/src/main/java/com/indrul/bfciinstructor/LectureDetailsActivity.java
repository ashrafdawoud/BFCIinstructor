package com.indrul.bfciinstructor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class LectureDetailsActivity extends AppCompatActivity {
    String video_link,lecture_link,assignment_link,matrialname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            video_link = extras.getString("vedio_link");
            lecture_link = extras.getString("lecture_link");
            assignment_link = extras.getString("assigment_link");
            matrialname = extras.getString("matrial");

            // and get whatever type user account id is
        }
    }

    public void back(View view) {
        finish();
    }

    public void youtube(View view) {
        Intent intent=new Intent(this,Video_Details.class);
        intent.putExtra("vediolink",video_link);
        startActivity(intent);
    }

    public void dwonloadPdf(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(lecture_link)));

    }

    public void uploadAssigment(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(assignment_link)));

    }

    public void delete(View view) { ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MatrialsDetails");
        query.whereEqualTo("matrial_name",matrialname );
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> player, ParseException e) {
                if (e == null) {
                    for (int i=0;i<player.size();i++){
                        if (player.get(i).get("lecture_link").equals(lecture_link)){
                            player.get(i).deleteInBackground(new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    finish();
                                }
                            });
                        }

                    }
                } else {
                    // Something is wrong
                    Log.e("return2", "true");
                } }});
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

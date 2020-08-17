package com.indrul.bfciinstructor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.indrul.bfciinstructor.adapters.QuestionAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class StudentQuestionActivity extends AppCompatActivity implements QuestionAdapter.MyListener {
RecyclerView lecturRecy;
    ArrayList<String> question=new ArrayList<>();
    ArrayList<String> email=new ArrayList<>();
    ArrayList<String> date=new ArrayList<>();
    ArrayList<String> id=new ArrayList<>();
    ArrayList<String> answer=new ArrayList<>();
    QuestionAdapter questionAdapter;
    TextView no_data;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_question);
        lecturRecy=findViewById(R.id.questionrecy);
        progressBar=findViewById(R.id.progress);
        lecturRecy.setHasFixedSize(true);
        no_data=findViewById(R.id.no_data);
        lecturRecy.setLayoutManager(new LinearLayoutManager(this));
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("QuestionBank");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> player, ParseException e) {
                if (e == null) {
                    for (int i=0;i<player.size();i++){
                        if (player.get(i).getString("answer").equals(" ")) {
                            question.add(player.get(i).getString("question"));
                            email.add(player.get(i).getString("email"));
                            date.add(player.get(i).getString("date"));
                            id.add(player.get(i).getString("objectId"));

                        }
                        //answer.add(player.get(i).getString("answer"));
                    }
                   // Log.e("ididid",String.valueOf(id.size())+"    "+id.get(0));
                    progressBar.setVisibility(View.GONE);
                    if (question.size()==0){
                        lecturRecy.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                    }else {
                        lecturRecy.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);

                    }
                    questionAdapter=new QuestionAdapter(StudentQuestionActivity.this,StudentQuestionActivity.this,question,email,date);
                    lecturRecy.setAdapter(questionAdapter);
                } else {
                    // Something is wrong
                    Log.e("return2", "true");
                }
            }
        });
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void callback() {
        question.clear();
        email.clear();
        date.clear();
        id.clear();
        Log.d("MyApp", "Successfully saved!"+1);

                ParseInstallation.getCurrentInstallation().saveInBackground();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("QuestionBank");
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(final List<ParseObject> player, ParseException e) {
                        if (e == null) {
                            for (int i=0;i<player.size();i++){
                                if (player.get(i).getString("answer").equals(" ")) {
                                    question.add(player.get(i).getString("question"));
                                    email.add(player.get(i).getString("email"));
                                    date.add(player.get(i).getString("date"));
                                    id.add(player.get(i).getString("objectId"));

                                }
                                //answer.add(player.get(i).getString("answer"));
                            }
                          //  Log.e("ididid",String.valueOf(id.size())+"    "+id.get(0));
                            progressBar.setVisibility(View.GONE);
                            if (question.size()==0){
                                lecturRecy.setVisibility(View.GONE);
                                no_data.setVisibility(View.VISIBLE);
                            }else {
                                lecturRecy.setVisibility(View.VISIBLE);
                                no_data.setVisibility(View.GONE);

                            }

                            questionAdapter=new QuestionAdapter(StudentQuestionActivity.this,StudentQuestionActivity.this,question,email,date);
                            lecturRecy.setAdapter(questionAdapter);
                        } else {
                            // Something is wrong
                            Log.e("return2", "true");
                        }
                    }
                });

    }

    public void search(View view) {
        question.clear();
        email.clear();
        date.clear();
        id.clear();
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("QuestionBank");
        Spinner spinner=findViewById(R.id.spinner1);
        if (!spinner.getSelectedItem().toString().equals("All")){
            query.whereEqualTo("linked_matrial",spinner.getSelectedItem().toString().toLowerCase());
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> player, ParseException e) {
                if (e == null) {
                    for (int i=0;i<player.size();i++){
                        if (player.get(i).getString("answer").equals(" ")) {
                            question.add(player.get(i).getString("question"));
                            email.add(player.get(i).getString("email"));
                            date.add(player.get(i).getString("date"));
                            id.add(player.get(i).getString("objectId"));

                        }
                        //answer.add(player.get(i).getString("answer"));
                    }
                    // Log.e("ididid",String.valueOf(id.size())+"    "+id.get(0));
                    progressBar.setVisibility(View.GONE);
                    if (question.size()==0){
                        lecturRecy.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                    }else {
                        lecturRecy.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);

                    }
                    questionAdapter=new QuestionAdapter(StudentQuestionActivity.this,StudentQuestionActivity.this,question,email,date);
                    lecturRecy.setAdapter(questionAdapter);
                } else {
                    // Something is wrong
                    Log.e("return2", "true");
                }
            }
        });
    }
}

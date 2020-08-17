package com.indrul.bfciinstructor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.indrul.bfciinstructor.adapters.LectureAdapter;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MatrialDetailsActivity extends AppCompatActivity {
    String matrialnamestring;
    RecyclerView lecturRecy;
    ArrayList<String> matrialimage=new ArrayList<>();
    ArrayList<String> assigment_link=new ArrayList<>();
    ArrayList<String> vedio_link=new ArrayList<>();
    ArrayList<String> lecture_link=new ArrayList<>();
    LectureAdapter lectureAdapter;
    ProgressBar progressBar;
    TextView no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrial_details);
        progressBar=findViewById(R.id.progress2);
        no_data=findViewById(R.id.no_data);
        Bundle extras=getIntent().getExtras();
        if (extras!=null){
            matrialnamestring=extras.getString("matrial");
        }
        TextView matrialname2=findViewById(R.id.matrialname);
        matrialname2.setText(matrialnamestring);
        lecturRecy=findViewById(R.id.lecture_details);
        lecturRecy.setHasFixedSize(true);
        lecturRecy.setLayoutManager(new LinearLayoutManager(this));
    }

    public void back(View view) {
        finish();
    }
    void  getWeakLectures(){
        matrialimage.clear();
        assigment_link.clear();
        vedio_link.clear();
        lecture_link.clear();
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MatrialsDetails");
        query.whereEqualTo("matrial_name", matrialnamestring);
        query.orderByAscending("weak_num");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> player, ParseException e) {
                if (e == null) {
                    for (int i=0;i<player.size();i++){
                        matrialimage.add(player.get(i).getString("matrial_image"));
                        assigment_link.add(player.get(i).getString("assignment_link"));
                        vedio_link.add(player.get(i).getString("lecture_video"));
                        lecture_link.add(player.get(i).getString("lecture_link"));

                    }
                    progressBar.setVisibility(View.GONE);
                    if (assigment_link.size()==0){
                        lecturRecy.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                    }else {
                        lecturRecy.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);

                    }
                    lectureAdapter=new LectureAdapter(matrialnamestring,MatrialDetailsActivity.this,matrialimage,assigment_link,vedio_link,lecture_link);
                    lecturRecy.setAdapter(lectureAdapter);
                } else {
                    // Something is wrong
                    Log.e("return2", "true");
                } }}); }


    public void delete(View view) {
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Matrial");
        query.whereEqualTo("name", matrialnamestring);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> player, ParseException e) {
                if (e == null) {
                  player.get(0).deleteInBackground(new DeleteCallback() {
                      @Override
                      public void done(ParseException e) {
                          finish();
                      }
                  });
                } else {
                    // Something is wrong
                    Log.e("return2", "true");
                } }});
    }

    public void addweek(View view) {
        Dialog_add_week();
    }
    private void Dialog_add_week() {
        Rect displayRectangle = new Rect();
        final Window window = MatrialDetailsActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MatrialDetailsActivity.this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View contactUsView = LayoutInflater.from(MatrialDetailsActivity.this).inflate(R.layout.add_week_dilog, viewGroup, false);

        builder.setView(contactUsView);
        final AlertDialog contactUsDialog = builder.create();
       /* String[] a={"1","2"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, a);
        spinner.setAdapter(adapter);*/
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(contactUsDialog.getWindow().getAttributes());
        wm.width = (int) (displayRectangle.width() * 0.9f);
        wm.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wm.gravity = Gravity.CENTER_HORIZONTAL;
        wm.dimAmount = 0.60f;
        final EditText video =contactUsView.findViewById(R.id.lecturevideo);
        final EditText lecturelink =contactUsView.findViewById(R.id.lectureLink);
        final EditText assignment =contactUsView.findViewById(R.id.assignmentlink);
        final EditText matrialImage =contactUsView.findViewById(R.id.matrialimage);
        final EditText matrialname =contactUsView.findViewById(R.id.matrialname);


        Button send=contactUsView.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner =contactUsView.findViewById(R.id.spinner1);
                String textWho=spinner.getSelectedItem().toString();
                textWho=textWho.toLowerCase();
                ParseObject soccerPlayers = new ParseObject("MatrialsDetails");// Store an object
                soccerPlayers.put("matrial_name", matrialnamestring);
                soccerPlayers.put("matrial_image", matrialImage.getText().toString());
                soccerPlayers.put("assignment_link",assignment.getText().toString() );
                soccerPlayers.put("lecture_video",video.getText().toString() );
                soccerPlayers.put("lecture_link", lecturelink.getText().toString());
                // soccerPlayers.addAllUnique("attributes", Arrays.asList("fast", "good conditioning"));
// Saving object
                soccerPlayers.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            contactUsDialog.dismiss();
                            Toast.makeText(MatrialDetailsActivity.this,"Data Sended",Toast.LENGTH_LONG).show();
                            getWeakLectures();
                            // Success
                        } else {
                            // Error
                        }
                    }
                });
            }
        });



        contactUsDialog.show();
        contactUsDialog.getWindow().setAttributes(wm);
        contactUsDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        contactUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        contactUsDialog.setCancelable(true);

    }

    @Override
    protected void onResume() {
        getWeakLectures();
        super.onResume();
    }
}

package com.indrul.bfciinstructor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
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

import com.indrul.bfciinstructor.adapters.MatrialAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MatrialActivity extends AppCompatActivity {
    RecyclerView recy;
    ArrayList<String> matrialName=new ArrayList<>();
    ArrayList<String> matrialimage=new ArrayList<>();
    MatrialAdapter matrialAdapter;
    String semester_name="first semester";
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    TextView no_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=MatrialActivity.this.getSharedPreferences("user",MODE_PRIVATE);
        setContentView(R.layout.activity_matrial);
        progressBar=findViewById(R.id.progress2);
        no_data=findViewById(R.id.no_data);
        recy=findViewById(R.id.matrialRecy);
        recy.setHasFixedSize(true);
        recy.setLayoutManager(new LinearLayoutManager(this));
       // getMatrial();
    }

    public void back(View view) {
        finish();
    }
    void getMatrial(){
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Matrial");
        query.whereEqualTo("semister",semester_name);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> player, ParseException e) {
                if (e == null) {
                    matrialName.clear();
                    matrialimage.clear();
                    for (int i=0;i<player.size();i++){
                        if (player.get(i).getString("doc_email").equals(sharedPreferences.getString("useremail",""))) {
                            matrialName.add(player.get(i).getString("name"));
                            matrialimage.add(player.get(i).getString("image"));
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                    if (matrialName.size()==0){
                        recy.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                    }else {
                        recy.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);

                    }
                    matrialAdapter=new MatrialAdapter(MatrialActivity.this,matrialName,matrialimage,semester_name);
                    recy.setAdapter(matrialAdapter);
                    matrialAdapter.notifyDataSetChanged();
                } else {
                    // Something is wrong
                    Log.e("return2", "true");
                }
            }
        });
    }

    public void announce(View view) {
        Dialog_add_announce();
    }
    private void Dialog_add_announce() {
        Rect displayRectangle = new Rect();
        final Window window = MatrialActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MatrialActivity.this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View contactUsView = LayoutInflater.from(MatrialActivity.this).inflate(R.layout.add_matrial_dialog, viewGroup, false);

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
        final EditText name =contactUsView.findViewById(R.id.name);
        final EditText discription =contactUsView.findViewById(R.id.discription);
        final EditText image =contactUsView.findViewById(R.id.image);
        Button send=contactUsView.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner =contactUsView.findViewById(R.id.spinner1);
                String textWho=spinner.getSelectedItem().toString();
                textWho=textWho.toLowerCase();
                ParseObject soccerPlayers = new ParseObject("Matrial");// Store an object
                soccerPlayers.put("name", name.getText().toString());
                soccerPlayers.put("image", image.getText().toString());
                soccerPlayers.put("semister",textWho );
                soccerPlayers.put("doc_email", "ashraf@gmail.com");
                // soccerPlayers.addAllUnique("attributes", Arrays.asList("fast", "good conditioning"));
// Saving object
                soccerPlayers.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            contactUsDialog.dismiss();
                            Toast.makeText(MatrialActivity.this,"Data Sended",Toast.LENGTH_LONG).show();
                            getMatrial();
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

    public void search(View view) {
        Spinner spinner=findViewById(R.id.spinner1);
        semester_name=spinner.getSelectedItem().toString();
        semester_name=semester_name.toLowerCase();
        matrialName.clear();
        matrialimage.clear();
        getMatrial();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMatrial();
    }
}

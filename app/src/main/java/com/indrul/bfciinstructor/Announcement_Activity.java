package com.indrul.bfciinstructor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.indrul.bfciinstructor.adapters.AnnouncementAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Announcement_Activity extends AppCompatActivity {
    RecyclerView announceRecy;
    ArrayList<String> notyname=new ArrayList<>();
    ArrayList<String> notytime=new ArrayList<>();
    ArrayList<String> notytext=new ArrayList<>();
    AnnouncementAdapter announceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_);
        announceRecy=findViewById(R.id.recy);
        announceRecy.setHasFixedSize(true);
        announceRecy.setLayoutManager(new LinearLayoutManager(this));
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Announce");
        query.whereEqualTo("who","instructor");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> player, ParseException e) {
                if (e == null) {
                    for (int i=0;i<player.size();i++){
                        notytext.add(player.get(i).getString("text"));
                        notytime.add(player.get(i).getString("time"));
                        notyname.add(player.get(i).getString("name"));
                    }
                    announceAdapter=new AnnouncementAdapter(notyname,notytime,notytext);
                    announceRecy.setAdapter(announceAdapter);
                } else {
                    // Something is wrong
                    Log.e("return2", "true");
                }
            }
        });
    }
    private void Dialog_add_announce() {
        Rect displayRectangle = new Rect();
        final Window window = Announcement_Activity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(Announcement_Activity.this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View contactUsView = LayoutInflater.from(Announcement_Activity.this).inflate(R.layout.add_announce_dialog, viewGroup, false);

        builder.setView(contactUsView);
        final AlertDialog contactUsDialog = builder.create();

        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(contactUsDialog.getWindow().getAttributes());
        wm.width = (int) (displayRectangle.width() * 0.9f);
        wm.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wm.gravity = Gravity.CENTER_HORIZONTAL;
        wm.dimAmount = 0.60f;
        final EditText editText =contactUsView.findViewById(R.id.announceText);
        Button send=contactUsView.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner =contactUsView.findViewById(R.id.spinner1);
                String textWho=spinner.getSelectedItem().toString();
                textWho=textWho.toLowerCase();
                ParseObject soccerPlayers = new ParseObject("Announce");// Store an object
                soccerPlayers.put("text", editText.getText().toString());
                soccerPlayers.put("time", "12/12/2020");
                soccerPlayers.put("name", "D.ashraf");
                Log.e("textWho",textWho);
                soccerPlayers.put("who", textWho);
                // soccerPlayers.addAllUnique("attributes", Arrays.asList("fast", "good conditioning"));
// Saving object
                soccerPlayers.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            contactUsDialog.dismiss();
                            Toast.makeText(Announcement_Activity.this,"Data Sended",Toast.LENGTH_LONG).show();
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

    public void announce(View view) {
        Dialog_add_announce();
    }

    public void back(View view) {
        finish();
    }
}

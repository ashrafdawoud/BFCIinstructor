package com.indrul.bfciinstructor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class profileActivity extends AppCompatActivity {
    View dialogViewPlaystore;
    AlertDialog alertDialogPlaystore;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ///////////////////////////////////////////////////////////
        sharedPreferences=this.getSharedPreferences("user", Context.MODE_PRIVATE);
        Log.e("userid",sharedPreferences.getString("userId","")+"4");
        TextView emaildetails=findViewById(R.id.emaildetails);
        TextView username=findViewById(R.id.username);
        username.setText(sharedPreferences.getString("username",""));
        emaildetails.setText(sharedPreferences.getString("useremail",""));
        /////////////////////////////
      /*  LinearLayout matrial=findViewById(R.id.matrial);
        matrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(this, SubJectActivity.class);
                startActivity(intent);
            }
        });*/
        LinearLayout semester=findViewById(R.id.semester);
        LinearLayout changePass=findViewById(R.id.changePassword);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_password();
            }
        });
        TextView logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // finish();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear().commit();
                Intent intent=new Intent(profileActivity.this , Login.class);
                startActivity(intent);

            }
        });


    }
    public void change_password() {
        Rect displayRectangle = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogViewPlaystore = LayoutInflater.from(this).inflate(R.layout.change_password, viewGroup, false);
        builder.setView(dialogViewPlaystore);
        alertDialogPlaystore = builder.create();
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(alertDialogPlaystore.getWindow().getAttributes());
        wm.width = (int) (displayRectangle.width() * 0.9f);
        //wm.width=WindowManager.LayoutParams.MATCH_PARENT;
        wm.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wm.gravity = Gravity.CENTER_HORIZONTAL;
        wm.dimAmount = 0.60f;
        Button change=dialogViewPlaystore.findViewById(R.id.change);
        EditText oldpass=dialogViewPlaystore.findViewById(R.id.oldpass);
        final EditText newpass=dialogViewPlaystore.findViewById(R.id.newpass);
        final String oladpasst=oldpass.getText().toString();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("userpassword","15").equals(oladpasst)){
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Student");
// Retrieve the object by id
                    query.getInBackground(sharedPreferences.getString("userId",""), new GetCallback<ParseObject>() {
                        public void done(ParseObject player, ParseException e) {
                            if (e == null) {
                                // Now let's update it with some new data. In this case, only cheatMode and score
                                // will get sent to the Parse Cloud. playerName hasn't changed.
                                player.put("password", newpass.getText());
                                player.saveInBackground();
                            } else {
                                // Failed
                            }
                        }
                    });
                }
                alertDialogPlaystore.dismiss();
            }
        });

        alertDialogPlaystore.show();
        alertDialogPlaystore.getWindow().setAttributes(wm);

        alertDialogPlaystore.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        alertDialogPlaystore.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }



}

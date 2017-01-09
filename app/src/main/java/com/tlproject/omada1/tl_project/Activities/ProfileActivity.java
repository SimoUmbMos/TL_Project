package com.tlproject.omada1.tl_project.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tlproject.omada1.tl_project.Service.DAOController;
import com.tlproject.omada1.tl_project.Controller.UserController;
import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;
import com.tlproject.omada1.tl_project.R;

public class ProfileActivity extends AppCompatActivity {
    com.tlproject.omada1.tl_project.Model.User CurUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle extras = getIntent().getExtras();
        String User = extras.getString("User");
        String Quest = extras.getString("Quest");
        Quest CurQuest=new Quest();
        CurQuest.setQuest(Quest);
        CurUser = new User();
        CurUser.setUser(User);

        TextView username=(TextView) findViewById(R.id.usernameprofile);
        TextView lvl=(TextView) findViewById(R.id.lvl);
        TextView curexp=(TextView) findViewById(R.id.curexp);
        TextView nextlvlexp=(TextView) findViewById(R.id.nextlvlexp);
        TextView questdesc=(TextView) findViewById(R.id.questdesc);
        ProgressBar exp=(ProgressBar) findViewById(R.id.expbar);
        username.setText(CurUser.getUsername());
        lvl.setText(String.valueOf(CurUser.getLvl()));
        UserController control=new UserController();
        questdesc.setText(CurQuest.getDesc());
        int expcur=CurUser.getExp();
        int nextexp=control.expforLvl(CurUser);
        curexp.setText(String.valueOf(expcur));
        nextlvlexp.setText(String.valueOf(nextexp));
        int prog=(expcur*100)/nextexp;
        exp.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
        exp.setProgress(prog);
    }

    public void back(View view) {
        finish();
    }

    public void Reset(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog2);
        Button ButtonYes = (Button) dialog.findViewById(R.id.btn_yes);
        Button ButtonNo = (Button) dialog.findViewById(R.id.btn_no);
        ButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DAOController().ResetUser(CurUser);
                dialog.dismiss();
            }
        });
        ButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void Edit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New username:");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String NewUsername = input.getText().toString();
                new DAOController().EditUser(CurUser,NewUsername);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}

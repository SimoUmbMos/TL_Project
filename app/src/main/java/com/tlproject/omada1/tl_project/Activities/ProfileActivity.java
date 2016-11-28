package com.tlproject.omada1.tl_project.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tlproject.omada1.tl_project.Controller.UserController;
import com.tlproject.omada1.tl_project.Model.User;
import com.tlproject.omada1.tl_project.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserController control=new UserController();

        setContentView(R.layout.activity_profile);
        TextView username=(TextView) findViewById(R.id.username);
        TextView lvl=(TextView) findViewById(R.id.lvl);
        TextView curexp=(TextView) findViewById(R.id.curexp);
        TextView nextlvlexp=(TextView) findViewById(R.id.nextlvlexp);
        TextView questdesc=(TextView) findViewById(R.id.questdesc);
        ProgressBar exp=(ProgressBar) findViewById(R.id.expbar);

        Bundle extras = getIntent().getExtras();
        String Name = extras.getString("NAME");
        System.out.print("testy: "+Name);
        int Exp=extras.getInt("EXP");
        int Lvl=extras.getInt("LVL");
        int Queston=extras.getInt("QUESTON");
        String QuestD= extras.getString("QUESTD");
        com.tlproject.omada1.tl_project.Model.User curruser;
        curruser=new User(Name,Queston,Lvl,Exp);

        username.setText(Name);
        lvl.setText(String.valueOf(Lvl));
        questdesc.setText(QuestD);
        int expcur=curruser.getExp();
        int nextexp=control.expforLvl(curruser);
        curexp.setText(String.valueOf(expcur));
        nextlvlexp.setText(String.valueOf(nextexp));
        int prog=(expcur*100)/nextexp;
        exp.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
        exp.setProgress(prog);
    }

    public void back(View view) {
        finish();
    }
}

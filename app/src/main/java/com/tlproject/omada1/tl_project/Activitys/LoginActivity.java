package com.tlproject.omada1.tl_project.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.tlproject.omada1.tl_project.Controller.CheckController;
import com.tlproject.omada1.tl_project.Controller.UserController;

import com.tlproject.omada1.tl_project.Model.User;
import com.tlproject.omada1.tl_project.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void LoginClick(View view) {
            CheckController CheckGps = new CheckController();
            EditText username = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
            String Username = username.getText().toString();
            String Password = password.getText().toString();
            UserController control = new UserController();
            if (control.Login(Username, Password, getApplicationContext())) {
                if (CheckGps.GpsEnable(this)) {
                    User user = new User();
                    Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                    intent.putExtra("User", user.ToString());
                    finish();
                    startActivity(intent);
                }
            }
    }

    public void RegisterClick(View view) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
    }
}

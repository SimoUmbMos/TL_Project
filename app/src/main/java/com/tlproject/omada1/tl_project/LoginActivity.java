package com.tlproject.omada1.tl_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tlproject.omada1.tl_project.Controller.UserController;
import com.tlproject.omada1.tl_project.Model.User;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void LoginClick(View view) {
        EditText username=(EditText) findViewById(R.id.username);
        EditText password=(EditText) findViewById(R.id.password);
        String Username=username.getText().toString();
        String Password=password.getText().toString();
        UserController control=new UserController();
        if(control.Login(Username,Password,getApplicationContext())){
            User user=new User();
            //user=control.getUser(Username);
            Intent intent=new Intent(LoginActivity.this, MapsActivity.class);;
            intent.putExtra("User", user.ToString());
            finish();
            startActivity(intent);
        }
    }

    public void RegisterClick(View view) {
        Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}

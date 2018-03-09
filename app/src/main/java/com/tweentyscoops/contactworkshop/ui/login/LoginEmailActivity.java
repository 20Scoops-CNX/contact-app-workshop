package com.tweentyscoops.contactworkshop.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.ui.home.MainActivity;
import com.tweentyscoops.contactworkshop.ui.register.RegisterActivity;
import com.tweentyscoops.contactworkshop.utils.DialogUtil;

public class LoginEmailActivity extends AppCompatActivity {
    private Button buttonSummit;
    private EditText editTextEmail,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        setID();
        setOnClick();
        setupView();
    }

    private void setupView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.login_email);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setOnClick() {
        buttonSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail.getText().toString().equals("")){
                    dialogEditTextEmail();
                }else if( editTextPassword.getText().toString().equals("")) {
                    dialogEditTextPassword();
                } else {
                    checkLoginEmail();
                }
            }
        });
    }

    private void dialogEditTextEmail(){
        DialogUtil.showDialogMessage(this, R.string.check_email);
    }
    private  void dialogEditTextPassword(){
        DialogUtil.showDialogMessage(this, R.string.check_password);
    }

    private void setID() {
        editTextEmail = (EditText) findViewById(R.id.signup_input_email);
        editTextPassword = (EditText) findViewById(R.id.signup_input_password);
        buttonSummit = (Button) findViewById(R.id.btnSummit);
    }

    private void checkLoginEmail(){
        //TODO retofit LoginEmail
        Intent intent = new Intent(LoginEmailActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

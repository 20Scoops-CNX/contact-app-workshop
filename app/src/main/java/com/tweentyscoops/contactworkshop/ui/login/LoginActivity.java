package com.tweentyscoops.contactworkshop.ui.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tweentyscoops.contactworkshop.R;

public class LoginActivity extends AppCompatActivity {

    private Button buttonSummit;
    private EditText editTextEmail,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setID();
        setOnClick();
    }

    private void setOnClick() {
        buttonSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "E-mail should not empty", Toast.LENGTH_LONG).show();
                }else if( editTextPassword.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Password should not empty", Toast.LENGTH_LONG).show();
                }else {
                    setDialog();
                }
            }
        });
    }

    private void setID() {
        editTextEmail = (EditText) findViewById(R.id.signup_input_email);
        editTextPassword = (EditText) findViewById(R.id.signup_input_password);
        buttonSummit = (Button) findViewById(R.id.btnSummit);
    }

    private void setDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Register");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

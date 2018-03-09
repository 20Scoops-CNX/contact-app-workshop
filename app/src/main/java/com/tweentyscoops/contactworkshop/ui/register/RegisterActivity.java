package com.tweentyscoops.contactworkshop.ui.register;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.model.ContactModel;
import com.tweentyscoops.contactworkshop.ui.home.MainActivity;
import com.tweentyscoops.contactworkshop.utils.DialogUtil;

public class RegisterActivity extends AppCompatActivity {

    private Button buttonSummit;
    private EditText editTextEmail,editTextPassword,editTextRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setID();
        setOnClick();
        setupView();
    }

    private void setupView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.register);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setOnClick() {
        buttonSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail.getText().toString().equals("")){
                    dialogeditTextEmail();
                }else if( editTextPassword.getText().toString().equals("")) {
                    dialogeditTextPassword();
                }else if( !editTextPassword.getText().toString().equals( editTextRePassword.getText().toString())){
                    dialogcheck_RePassword();
                }else {
                    setDialog();
                }
            }
        });
    }

    private void dialogeditTextEmail(){
        DialogUtil.showDialogMessage(this, R.string.check_email);
    }
    private  void dialogeditTextPassword(){
        DialogUtil.showDialogMessage(this, R.string.check_password);
    }
    private  void dialogcheck_RePassword(){
        DialogUtil.showDialogMessage(this, R.string.check_RePassword);
    }


    private void setID() {
        editTextEmail = (EditText) findViewById(R.id.signup_input_email);
        editTextRePassword = (EditText) findViewById(R.id.signup_input_Repassword);
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

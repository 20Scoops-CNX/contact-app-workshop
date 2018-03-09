package com.tweentyscoops.contactworkshop.ui.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.utils.DialogUtil;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupView();
    }

    private void setupView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.register);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        editTextEmail = findViewById(R.id.signup_input_email);
        editTextRePassword = findViewById(R.id.signup_input_Repassword);
        editTextPassword = findViewById(R.id.signup_input_password);
        findViewById(R.id.btnSummit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextEmail.getText().toString().trim().length() == 0) {
                    DialogUtil.showDialogMessage(this, R.string.check_email);
                } else if (editTextPassword.getText().toString().trim().length() == 0) {
                    DialogUtil.showDialogMessage(this, R.string.check_password);
                } else if (!editTextPassword.getText().toString().equals(editTextRePassword.getText().toString())) {
                    DialogUtil.showDialogMessage(this, R.string.check_RePassword);
                } else {
                    // TODO : request APIs register
                }
            }
        });
    }
}

package com.tweentyscoops.contactworkshop.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.http.HttpConnection;
import com.tweentyscoops.contactworkshop.model.ResponseApi;
import com.tweentyscoops.contactworkshop.ui.login.LoginActivity;
import com.tweentyscoops.contactworkshop.utils.DialogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword, editTextRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                handleField();
            }
        });
    }

    private void handleField() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (editTextEmail.getText().toString().trim().length() == 0) {
            DialogUtil.showDialogMessage(this, R.string.check_email);
        } else if (editTextPassword.getText().toString().trim().length() == 0) {
            DialogUtil.showDialogMessage(this, R.string.check_password);
        } else if (!editTextPassword.getText().toString().equals(editTextRePassword.getText().toString())) {
            DialogUtil.showDialogMessage(this, R.string.check_RePassword);
        } else {
            setPostRegister(email, password);
        }
    }

    private void handleRegister() {
        DialogUtil.showDialogMessage(this, R.string.register_error);
    }

    private void setPostRegister(String username, String password) {
        HttpConnection.request().crataRegister(username, password).enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                if (response.isSuccessful() && response.body().getSuccess()) {
                    handleRegister();
                } else {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

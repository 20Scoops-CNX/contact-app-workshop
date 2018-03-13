package com.tweentyscoops.contactworkshop.ui.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.http.ContactApi;
import com.tweentyscoops.contactworkshop.model.ResponseApi;
import com.tweentyscoops.contactworkshop.ui.login.LoginActivity;
import com.tweentyscoops.contactworkshop.utils.DialogUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                handleField();
            }
        });
    }
    private void handleField(){
        String email=editTextEmail.getText().toString();
        String password=editTextPassword.getText().toString();
        if (editTextEmail.getText().toString().trim().length() == 0) {
            DialogUtil.showDialogMessage(this , R.string.check_email);
        } else if (editTextPassword.getText().toString().trim().length() == 0) {
            DialogUtil.showDialogMessage(this, R.string.check_password);
        } else if (!editTextPassword.getText().toString().equals(editTextRePassword.getText().toString())) {
            DialogUtil.showDialogMessage(this, R.string.check_RePassword);
        } else {
            setPostRegister(email , password);
        }
    }

     private void handleRegister(){
         DialogUtil.showDialogMessage(this , R.string.register_error);
     }

    private void  setPostRegister(String username , String password){
    Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.8.14.110:330/contact-api-workshop/public/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ContactApi client = retrofit.create(ContactApi.class);
        Call<ResponseApi> call=client.crataRegister(username , password);
        call.enqueue(new Callback<ResponseApi>() {

            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                Log.e("response ",response.body().getSuccess());
                String success=response.body().getSuccess();
                if(success.equals("false")){
                    handleRegister();
                }else {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                Log.e("error ",t.getMessage());
            }
        });
    }
}

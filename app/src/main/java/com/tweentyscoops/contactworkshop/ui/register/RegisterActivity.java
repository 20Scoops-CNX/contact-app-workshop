package com.tweentyscoops.contactworkshop.ui.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.http.ContactApi;
import com.tweentyscoops.contactworkshop.model.UserModel;
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
        if (editTextEmail.getText().toString().trim().length() == 0) {
            DialogUtil.showDialogMessage(this , R.string.check_email);
        } else if (editTextPassword.getText().toString().trim().length() == 0) {
            DialogUtil.showDialogMessage(this, R.string.check_password);
        } else if (!editTextPassword.getText().toString().equals(editTextRePassword.getText().toString())) {
            DialogUtil.showDialogMessage(this, R.string.check_RePassword);
        } else {
            UserModel user=new UserModel(editTextEmail.getText().toString().trim(),editTextPassword.getText().toString().trim());
            setPostRegister(user);
        }
    }

    private void  setPostRegister(UserModel user){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ContactApi client = retrofit.create(ContactApi.class);
        Call<UserModel> call=client.crataRegister(user);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                String responseRegister=response.body().getSuccess();
                if(responseRegister.equals("true")){
                    Toast.makeText(getApplication(),"Register Success", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplication(),"no Success"+response.body().getSuccess(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("onError","onError"+t.getLocalizedMessage());
            }
        });
    }
}

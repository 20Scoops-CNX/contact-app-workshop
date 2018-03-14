package com.tweentyscoops.contactworkshop.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.http.HttpConnection;
import com.tweentyscoops.contactworkshop.model.ResponseApi;
import com.tweentyscoops.contactworkshop.ui.home.MainActivity;
import com.tweentyscoops.contactworkshop.ui.register.RegisterActivity;
import com.tweentyscoops.contactworkshop.utils.DialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button buttonLoginEmail;
    private TextView textRegister;
    private LoginButton btnLogin;
    private CallbackManager callbackManager;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setID();
        setOnClick();
        setOnClickFacebook();
        setcheckLoginFacebook();
    }

    private void setOnClickFacebook() {
        btnLogin.setReadPermissions(Arrays.asList("user_photos", "email", "public_profile"));
        btnLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "Success " + loginResult.getAccessToken().getUserId(), Toast.LENGTH_SHORT).show();
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,last_name,link,email,picture");
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        try {
                            String str_email = jsonObject.getString("email");
                            Toast.makeText(LoginActivity.this, str_email, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity.this, "Error " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClick() {
        buttonLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleField();
            }
        });
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setID() {
        buttonLoginEmail = findViewById(R.id.loginEmail);
        textRegister = findViewById(R.id.register);
        callbackManager = CallbackManager.Factory.create();
        btnLogin = findViewById(R.id.login_fb);
        editTextEmail = findViewById(R.id.signup_input_email);
        editTextPassword = findViewById(R.id.signup_input_password);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //TODO checkLoginFacebook retrofit()
    private void setcheckLoginFacebook() {
    }

    private void handleField() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (email.trim().length() == 0) {
            DialogUtil.showDialogMessage(this, R.string.check_email);
        } else if (password.trim().length() == 0) {
            DialogUtil.showDialogMessage(this, R.string.check_password);
        } else {
            setPostLoginEmail(email, password);
        }
    }

    private void handleLoginEmail() {
        DialogUtil.showDialogMessage(this, R.string.register_error);
    }

    private void setPostLoginEmail(String username, String password) {
        HttpConnection.request().login(username, password).enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                if (response.isSuccessful() && response.body().getSuccess()) {
                    handleLoginEmail();
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

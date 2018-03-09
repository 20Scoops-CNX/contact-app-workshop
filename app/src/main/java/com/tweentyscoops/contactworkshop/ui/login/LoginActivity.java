package com.tweentyscoops.contactworkshop.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.tweentyscoops.contactworkshop.ui.register.RegisterActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private Button buttonLoginEmail;
    private TextView textRegister;
    private LoginButton btnLogin;
    private CallbackManager callbackManager;

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
                            e.printStackTrace();
                        }
                        Log.i("user", jsonObject.toString());
                    }
                });
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {}
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
                Intent intent = new Intent(LoginActivity.this, LoginEmailActivity.class);
                startActivity(intent);
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
        buttonLoginEmail = (Button) findViewById(R.id.loginEmail);
        textRegister = (TextView) findViewById(R.id.register);
        callbackManager = CallbackManager.Factory.create();
        btnLogin = (LoginButton) findViewById(R.id.login_fb);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //TODO checkLoginFacebook retrofit()
    private void setcheckLoginFacebook() {}

}

package com.tweentyscoops.contactworkshop.ui.form;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tweentyscoops.contactworkshop.R;

public class FormContactActivity extends AppCompatActivity {

    public static final String KEY_MODE_EDIT = "mode_edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_contact);
    }
}

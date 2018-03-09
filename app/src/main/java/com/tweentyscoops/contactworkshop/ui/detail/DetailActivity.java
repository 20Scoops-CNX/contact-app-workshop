package com.tweentyscoops.contactworkshop.ui.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.model.ContactModel;
import com.tweentyscoops.contactworkshop.ui.home.MainActivity;
import com.tweentyscoops.contactworkshop.utils.ImageLoader;

public class DetailActivity extends AppCompatActivity {
    private TextView tvName, tvTel, tvEmail, tvWebsite;
    private ImageView ivMap, ivEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        setView();
    }

    private void initView() {
        tvName = findViewById(R.id.tvDetailName);
        tvTel = findViewById(R.id.tvDetailTel);
        tvEmail = findViewById(R.id.tvDetailEmail);
        tvWebsite = findViewById(R.id.tvDetailWebsite);
        ivMap = findViewById(R.id.ivMap);
    }

    private void setView() {
        ContactModel model = getIntent().getExtras().getParcelable(MainActivity.KEY_CONTACT_DETAIL);
        String name = model.getName();
        String tel = model.getPhoneNumber();
        String email = model.getEmail();
        String website = model.getWebsite();

        tvName.setText(name);
        tvTel.setText(tel);
        tvEmail.setText(email);
        tvWebsite.setText(website);

        ImageLoader.url(ivMap, "http://maps.google.com/maps/api/staticmap?center=" + model.getLat() + "," + model.getLng() + "&zoom=14&size=300x300&sensor=false");

//        ivEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //todo add action intent to edit here use data in model
//            }
//        });
    }
}
package com.tweentyscoops.contactworkshop.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.model.ContactModel;
import com.tweentyscoops.contactworkshop.ui.home.MainActivity;
import com.tweentyscoops.contactworkshop.ui.webview.WebViewActivity;
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
        ivEdit = findViewById(R.id.tvEdt);
    }

    private void setView() {
        final ContactModel model = getIntent().getExtras().getParcelable(MainActivity.KEY_CONTACT_DETAIL);
        String name = null;
        String tel = null;
        String email = null;
        String website = null;
        String lat = null;
        String lng = null;
        if (model != null) {
            name = model.getName();
            tel = model.getPhoneNumber();
            email = model.getEmail();
            website = model.getWebsite();
            lat =model.getLat();
            lng = model.getLng();
        }

        tvName.setText(name);
        tvTel.setText(tel);
        tvEmail.setText(email);
        tvWebsite.setText(website);

        tvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.KEY_WEB_VIEW , tvWebsite.getText().toString());
                startActivity(intent);
            }
        });

        ImageLoader.url(ivMap, "http://maps.google.com/maps/api/staticmap?center=" + lat + "," + lng + "&zoom=14&size=300x300&sensor=false");

        tvTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (model != null) {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tvTel.getText().toString(), null));
                }
                startActivity(intent);
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
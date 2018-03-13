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
import com.tweentyscoops.contactworkshop.ui.form.FormContactActivity;
import com.tweentyscoops.contactworkshop.ui.home.MainActivity;
import com.tweentyscoops.contactworkshop.ui.webview.WebViewActivity;
import com.tweentyscoops.contactworkshop.utils.ImageLoader;

import static com.tweentyscoops.contactworkshop.ui.form.FormContactActivity.KEY_CONTACT_MODEL;
import static com.tweentyscoops.contactworkshop.ui.form.FormContactActivity.KEY_MODE_EDIT;

public class DetailActivity extends AppCompatActivity {

    public static final int REQUEST_EDIT = 1005;

    private TextView tvName, tvTel, tvEmail, tvWebsite,tvAddress;
    private ImageView ivMap;
    private ContactModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupInstance();
        setupView();
        initData();
    }

    private void setupInstance() {
        model = getIntent().getParcelableExtra(MainActivity.KEY_CONTACT_DETAIL);
    }

    private void setupView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(model.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvName = findViewById(R.id.tvDetailName);
        tvTel = findViewById(R.id.tvDetailTel);
        tvEmail = findViewById(R.id.tvDetailEmail);
        tvWebsite = findViewById(R.id.tvDetailWebsite);
        ivMap = findViewById(R.id.ivMap);
        tvAddress=findViewById(R.id.tvAddress);

        tvTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tvTel.getText().toString(), null));
                startActivity(intent);
            }
        });

        tvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.KEY_WEB_VIEW, tvWebsite.getText().toString());
                startActivity(intent);
            }
        });

        findViewById(R.id.tvEdt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, FormContactActivity.class);
                intent.putExtra(KEY_MODE_EDIT, true);
                intent.putExtra(KEY_CONTACT_MODEL, model);
                startActivityForResult(intent, REQUEST_EDIT);
            }
        });
    }

    private void initData() {
        tvName.setText(model.getName());
        tvTel.setText(model.getPhoneNumber());
        tvEmail.setText(model.getEmail());
        tvWebsite.setText(model.getWebsite());
        tvAddress.setText(model.getAddress());
        ImageLoader.url(ivMap, "http://maps.google.com/maps/api/staticmap?center=" +
                this.model.getLat() + "," + this.model.getLng() + "&zoom=14&size=300x300&sensor=false");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.KEY_CONTACT_MODEL, model);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK) {
            model = data.getParcelableExtra(MainActivity.KEY_CONTACT_MODEL);
            if (model != null) {
                initData();
            }
        }
    }
}
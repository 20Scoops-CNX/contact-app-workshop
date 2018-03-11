package com.tweentyscoops.contactworkshop.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.model.ContactModel;
import com.tweentyscoops.contactworkshop.ui.detail.DetailActivity;
import com.tweentyscoops.contactworkshop.ui.form.FormContactActivity;
import com.tweentyscoops.contactworkshop.ui.home.adapter.ContactAdapter;
import com.tweentyscoops.contactworkshop.utils.DialogUtil;

public class MainActivity extends AppCompatActivity implements ContactAdapter.ContactAdapterListener {

    private static final int REQUEST_CODE_ADD_CONTACT = 1002;
    private static final int REQUEST_CODE_DETAILS = 1003;
    public static final String KEY_CONTACT_DETAIL = "contact_detail";
    public static final String KEY_CONTACT_MODEL = "contact_data";

    private ContactAdapter adapter;
    private View viewEmpty;
    private int positionItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupInstance();
        setupView();
        // TODO : request APIs get list contact
    }

    private void setupInstance() {
        adapter = new ContactAdapter();
        adapter.setListener(this);
    }

    private void setupView() {
        viewEmpty = findViewById(R.id.viewEmpty);
        RecyclerView rvContact = findViewById(R.id.rvContact);
        rvContact.setLayoutManager(new LinearLayoutManager(this));
        rvContact.setAdapter(adapter);
        FloatingActionButton fabAddContact = findViewById(R.id.fabAddContact);
        fabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormContactActivity.class);
                intent.putExtra(FormContactActivity.KEY_MODE_EDIT, false);
                startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_CONTACT && resultCode == RESULT_OK) {
            ContactModel model = data.getParcelableExtra(KEY_CONTACT_MODEL);
            if (model != null) {
                adapter.addItem(model);
            }
        } else if (requestCode == REQUEST_CODE_DETAILS && resultCode == RESULT_OK) {
            ContactModel model = data.getParcelableExtra(KEY_CONTACT_MODEL);
            if (model != null) {
                adapter.updateItem(positionItem, model);
            }
        }
    }

    @Override
    public void onAddedItemContact() {
        viewEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position, ContactModel model) {
        positionItem = position;
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(KEY_CONTACT_DETAIL, model);
        startActivityForResult(intent, REQUEST_CODE_DETAILS);
    }

    public void onBackPressed() {
        DialogUtil.showDialogMessageLogout(this, R.string.messageLogout);
    }
}
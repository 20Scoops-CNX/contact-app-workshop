package com.tweentyscoops.contactworkshop.ui.form;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.model.ContactModel;
import com.tweentyscoops.contactworkshop.ui.home.MainActivity;
import com.tweentyscoops.contactworkshop.utils.DialogUtil;

import java.util.List;

public class FormContactActivity extends AppCompatActivity implements DialogUtil.MenuAddPhotoListener {

    public static final String KEY_MODE_EDIT = "mode_edit";
    public static final String KEY_CONTACT_MODEL = "contact_model";

    private TextInputEditText etName;
    private TextInputEditText etPhoneNumber;
    private TextInputEditText etEmail;
    private TextInputEditText etWebsite;

    private boolean isModeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_contact);
        setInstance();
        setupView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setInstance() {
        isModeEdit = getIntent().getBooleanExtra(KEY_MODE_EDIT, false);
    }

    private void setupView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.create_contact);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etWebsite = findViewById(R.id.etWebsite);
        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : request APIs insert/update contact
                ContactModel model = getContactModel();
                if (model != null) {
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.KEY_CONTACT_MODEL, model);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        findViewById(R.id.imgProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showDialogChooseImage(FormContactActivity.this, FormContactActivity.this);
            }
        });
        if (isModeEdit) {
            preFillForm();
        }
    }

    private void preFillForm() {
        ContactModel model = getIntent().getParcelableExtra(KEY_CONTACT_MODEL);
        if (model != null) {
            etName.setText(model.getName());
            etWebsite.setText(model.getWebsite());
            etPhoneNumber.setText(model.getPhoneNumber());
            etEmail.setText(model.getEmail());
        }
    }

    private ContactModel getContactModel() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String website = etWebsite.getText().toString();
        if (name.trim().length() == 0) {
            DialogUtil.showDialogMessage(this, R.string.require_field_name);
            return null;
        } else if (phoneNumber.trim().length() == 0) {
            DialogUtil.showDialogMessage(this, R.string.require_field_phone_number);
            return null;
        } else if (email.trim().length() == 0) {
            DialogUtil.showDialogMessage(this, R.string.require_field_email);
            return null;
        } else if (website.trim().length() == 0) {
            DialogUtil.showDialogMessage(this, R.string.require_field_website);
            return null;
        } else {
            ContactModel model = new ContactModel();
            model.setName(name);
            // TODO : get address, lat and lng by location
            model.setAddress("address");
            model.setLat("lat");
            model.setLng("lng");
            model.setPhoneNumber(phoneNumber);
            model.setEmail(email);
            // TODO : get url image before upload image
            model.setImageUrl("");
            model.setWebsite(website);
            return model;
        }
    }

    @Override
    public void onTakePhoto() {
        checkPermissionAddPhoto(true);
    }

    @Override
    public void onPickFormGallery() {
        checkPermissionAddPhoto(false);
    }

    private void checkPermissionAddPhoto(final boolean isTakePhoto) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (!hasDeniedPermission(report)) {
                            if (isTakePhoto) {
                                intentToTakePhoto();
                            } else {
                                intentToPickFromGallery();
                            }
                        } else {
                            Toast.makeText(FormContactActivity.this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                    private boolean hasDeniedPermission(MultiplePermissionsReport report) {
                        List<PermissionDeniedResponse> denyPermission = report.getDeniedPermissionResponses();
                        return denyPermission != null && denyPermission.size() > 0;
                    }
                }).check();
    }

    private void intentToTakePhoto() {

    }

    private void intentToPickFromGallery() {

    }
}
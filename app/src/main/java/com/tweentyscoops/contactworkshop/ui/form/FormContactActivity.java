package com.tweentyscoops.contactworkshop.ui.form;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tweentyscoops.contactworkshop.BuildConfig;
import com.tweentyscoops.contactworkshop.R;
import com.tweentyscoops.contactworkshop.model.ContactModel;
import com.tweentyscoops.contactworkshop.ui.home.MainActivity;
import com.tweentyscoops.contactworkshop.ui.map.MapActivity;
import com.tweentyscoops.contactworkshop.utils.DialogUtil;
import com.tweentyscoops.contactworkshop.utils.FileUtil;
import com.tweentyscoops.contactworkshop.utils.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FormContactActivity extends AppCompatActivity implements DialogUtil.MenuAddPhotoListener {

    public static final String KEY_MODE_EDIT = "mode_edit";
    public static final String KEY_CONTACT_MODEL = "contact_model";
    private static final int REQUEST_CODE_ADD_LOCATION = 1000;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_PICK_PHOTO = 2;

    private String latitude="";
    private String longtitude="";

    private String mCurrentPhotoPath;
    private TextInputEditText etName;
    private TextInputEditText etPhoneNumber;
    private TextInputEditText etEmail;
    private TextInputEditText etWebsite;
    private ImageView imgProfile;

    private File folder;
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
        findViewById(R.id.btnLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormContactActivity.this, MapActivity.class);
                intent.putExtra(FormContactActivity.KEY_MODE_EDIT, false);
                startActivityForResult(intent, REQUEST_CODE_ADD_LOCATION);}
        });
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
        imgProfile = findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(new View.OnClickListener() {
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
            if (latitude.trim().length()!=0){
                model.setLat(latitude);
                model.setLng(longtitude);
            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            ImageLoader.url(imgProfile, mCurrentPhotoPath);
            galleryAddPic();
            Log.d("POND", mCurrentPhotoPath);
        } else if (requestCode == REQUEST_PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                DialogUtil.showDialogMessage(this, R.string.please_try_again);
                return;
            }
            try {
                if (data.getData() != null) {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    File file = createImageFile();
                    FileUtil.copyInputStreamToFile(inputStream, file);
                    galleryAddPic();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageLoader.url(imgProfile, mCurrentPhotoPath);
        }
        if (requestCode == REQUEST_CODE_ADD_LOCATION && resultCode == RESULT_OK) {
            ContactModel model = getContactModel();
            String lat = data.getStringExtra(MapActivity.KEY_LOCATION_LAT);
            String lng = data.getStringExtra(MapActivity.KEY_LOCATION_LNG);
            if (model != null){
                if (lat.trim().length() != 0 && lng.trim().length() != 0) {
                    latitude = lat;
                    longtitude = lng;
                }}
        }
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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                DialogUtil.showDialogMessage(this, ex.getMessage());
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void intentToPickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {
        folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES
                + "/" + getString(R.string.app_name));
        if (!folder.isDirectory()) folder.mkdir();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File imageFile = File.createTempFile(imageFileName, ".jpg", folder);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

}

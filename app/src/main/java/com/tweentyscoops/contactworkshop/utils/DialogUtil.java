package com.tweentyscoops.contactworkshop.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.tweentyscoops.contactworkshop.R;

public class DialogUtil {

    public interface MenuAddPhotoListener {
        void onTakePhoto();

        void onPickFormGallery();
    }

    public static void showDialogMessage(Context context, @StringRes int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.error))
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.ok), null)
                .setNegativeButton(null, null);
        builder.show();
    }

    public static void showDialogChooseImage(Context context, final MenuAddPhotoListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.add_photo))
                .setItems(R.array.menu_add_photo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            if (which == 0) {
                                listener.onTakePhoto();
                            } else {
                                listener.onPickFormGallery();
                            }
                        }
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}

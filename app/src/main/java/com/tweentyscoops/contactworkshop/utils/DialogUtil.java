package com.tweentyscoops.contactworkshop.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.tweentyscoops.contactworkshop.R;

public class DialogUtil {

    public static void showDialogMessage(Context context, @StringRes int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.error))
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.ok), null)
                .setNegativeButton(null, null);
        builder.show();
    }
}

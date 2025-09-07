package com.raylabs.mathverse.util;

import android.content.Context;
import android.widget.Toast;

public class GeneralHelper {

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}

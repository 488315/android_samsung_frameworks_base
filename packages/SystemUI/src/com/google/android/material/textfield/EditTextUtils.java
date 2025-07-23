package com.google.android.material.textfield;

import android.widget.EditText;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class EditTextUtils {
    private EditTextUtils() {
    }

    public static boolean isEditable(EditText editText) {
        return editText.getInputType() != 0;
    }
}

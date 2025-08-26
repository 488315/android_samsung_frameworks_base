package com.google.android.material.textfield;

import android.widget.EditText;

/* loaded from: classes4.dex */
public class EditTextUtils {
    private EditTextUtils() {
    }

    public static boolean isEditable(EditText editText) {
        return editText.getInputType() != 0;
    }
}

package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.EditTextPreference;

/* loaded from: classes.dex */
public class CustomEditTextPreferenceCompat extends EditTextPreference {
    public CustomEditTextPreferenceCompat(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public CustomEditTextPreferenceCompat(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public CustomEditTextPreferenceCompat(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomEditTextPreferenceCompat(Context context) {
        super(context);
    }
}

package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.EditTextPreference;

@Deprecated
/* loaded from: classes.dex */
public class CustomEditTextPreference extends EditTextPreference {
    public CustomEditTextPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public CustomEditTextPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public CustomEditTextPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomEditTextPreference(Context context) {
        super(context);
    }
}

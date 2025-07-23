package com.android.settingslib;

import android.content.Context;
import androidx.preference.DropDownPreference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class RestrictedDropDownPreference extends DropDownPreference {
    public final RestrictedPreferenceHelper mHelper;

    public RestrictedDropDownPreference(Context context) {
        super(context);
        this.mHelper = new RestrictedPreferenceHelper(context, this, null);
    }

    @Override // androidx.preference.DropDownPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mHelper.onBindViewHolder(preferenceViewHolder);
    }

    @Override // androidx.preference.Preference
    public final void performClick() {
        if (this.mHelper.performClick()) {
            return;
        }
        super.performClick();
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        if (z) {
            RestrictedPreferenceHelper restrictedPreferenceHelper = this.mHelper;
            if (restrictedPreferenceHelper.mDisabledByEcm) {
                restrictedPreferenceHelper.setDisabledByEcm();
                return;
            }
        }
        super.setEnabled(z);
    }
}

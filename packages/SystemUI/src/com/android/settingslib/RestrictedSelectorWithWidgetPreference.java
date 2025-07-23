package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class RestrictedSelectorWithWidgetPreference extends SelectorWithWidgetPreference {
    public final RestrictedPreferenceHelper mHelper;

    public RestrictedSelectorWithWidgetPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet);
    }

    @Override // androidx.preference.Preference
    public final void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mHelper.onAttachedToHierarchy();
        super.onAttachedToHierarchy(preferenceManager);
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference, androidx.preference.CheckBoxPreference, androidx.preference.Preference
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
            if (restrictedPreferenceHelper.mDisabledByAdmin) {
                restrictedPreferenceHelper.setDisabledByAdmin(null);
                return;
            }
        }
        super.setEnabled(z);
    }

    public RestrictedSelectorWithWidgetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet);
    }

    public RestrictedSelectorWithWidgetPreference(Context context, boolean z) {
        super(context, (AttributeSet) null);
        this.mHelper = new RestrictedPreferenceHelper(context, this, null);
    }

    public RestrictedSelectorWithWidgetPreference(Context context) {
        this(context, (AttributeSet) null);
    }
}

package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.widget.SliderPreference;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class RestrictedSliderPreference extends SliderPreference {
    public final RestrictedPreferenceHelper mHelper;

    public RestrictedSliderPreference(Context context, AttributeSet attributeSet, int i, String str, int i2) {
        super(context, attributeSet, i);
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet, str, i2);
    }

    @Override // androidx.preference.Preference
    public final void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mHelper.onAttachedToHierarchy();
        super.onAttachedToHierarchy(preferenceManager);
    }

    @Override // com.android.settingslib.widget.SliderPreference, androidx.preference.Preference
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
        if (z) {
            RestrictedPreferenceHelper restrictedPreferenceHelper2 = this.mHelper;
            if (restrictedPreferenceHelper2.mDisabledByEcm) {
                restrictedPreferenceHelper2.setDisabledByEcm();
                return;
            }
        }
        super.setEnabled(z);
    }

    public RestrictedSliderPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, null, -1);
    }

    public RestrictedSliderPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.preferenceStyle, android.R.attr.preferenceStyle));
    }

    public RestrictedSliderPreference(Context context) {
        this(context, null);
    }

    public RestrictedSliderPreference(Context context, String str, int i) {
        this(context, null, TypedArrayUtils.getAttr(context, R.attr.preferenceStyle, android.R.attr.preferenceStyle), str, i);
    }
}

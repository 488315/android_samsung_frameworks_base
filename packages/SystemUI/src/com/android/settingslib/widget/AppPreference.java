package com.android.settingslib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AppPreference extends Preference {
    public AppPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLayoutResId = R.layout.sec_preference_app;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.summary_container);
        if (findViewById != null) {
            findViewById.setVisibility(TextUtils.isEmpty(getSummary()) ? 8 : 0);
        }
    }

    public AppPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLayoutResId = R.layout.sec_preference_app;
    }

    public AppPreference(Context context) {
        super(context);
        this.mLayoutResId = R.layout.sec_preference_app;
    }

    public AppPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLayoutResId = R.layout.sec_preference_app;
    }
}

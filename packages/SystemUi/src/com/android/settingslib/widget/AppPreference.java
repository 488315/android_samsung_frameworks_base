package com.android.settingslib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AppPreference extends Preference {
    public AppPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLayoutResId = R.layout.sec_preference_app;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.findViewById(R.id.summary_container).setVisibility(TextUtils.isEmpty(getSummary()) ? 8 : 0);
        ((ProgressBar) preferenceViewHolder.findViewById(android.R.id.progress)).setVisibility(8);
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

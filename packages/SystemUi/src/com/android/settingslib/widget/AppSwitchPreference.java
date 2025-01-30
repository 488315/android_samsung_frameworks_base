package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AppSwitchPreference extends SwitchPreference {
    public AppSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLayoutResId = R.layout.sec_preference_app;
    }

    @Override // androidx.preference.SwitchPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(android.R.id.switch_widget);
        if (findViewById != null) {
            findViewById.getRootView().setFilterTouchesWhenObscured(true);
        }
    }

    public AppSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLayoutResId = R.layout.sec_preference_app;
    }

    public AppSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLayoutResId = R.layout.sec_preference_app;
    }

    public AppSwitchPreference(Context context) {
        super(context);
        this.mLayoutResId = R.layout.sec_preference_app;
    }
}

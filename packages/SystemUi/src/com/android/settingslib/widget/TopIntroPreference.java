package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class TopIntroPreference extends Preference {
    public TopIntroPreference(Context context) {
        super(context);
        this.mLayoutResId = R.layout.top_intro_preference;
        setSelectable(false);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
    }

    public TopIntroPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLayoutResId = R.layout.top_intro_preference;
        setSelectable(false);
    }
}

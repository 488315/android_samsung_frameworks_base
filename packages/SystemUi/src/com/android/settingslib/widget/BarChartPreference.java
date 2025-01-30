package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class BarChartPreference extends Preference {
    public BarChartPreference(Context context) {
        super(context);
        init();
    }

    public final void init() {
        setSelectable(false);
        this.mLayoutResId = R.layout.settings_bar_chart;
        this.mContext.getResources().getDimensionPixelSize(R.dimen.settings_bar_view_max_height);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = true;
        preferenceViewHolder.mDividerAllowedBelow = true;
        throw null;
    }

    public BarChartPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public BarChartPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public BarChartPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }
}

package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BarChartPreference extends Preference {
    public BarChartPreference(Context context) {
        super(context);
        init$7();
    }

    public final void init$7() {
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
        init$7();
    }

    public BarChartPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init$7();
    }

    public BarChartPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init$7();
    }
}

package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class BarChartPreference extends Preference {
    public BarChartPreference(Context context) throws Resources.NotFoundException {
        super(context);
        init$7();
    }

    public final void init$7() throws Resources.NotFoundException {
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

    public BarChartPreference(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        init$7();
    }

    public BarChartPreference(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        super(context, attributeSet, i);
        init$7();
    }

    public BarChartPreference(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i, i2);
        init$7();
    }
}

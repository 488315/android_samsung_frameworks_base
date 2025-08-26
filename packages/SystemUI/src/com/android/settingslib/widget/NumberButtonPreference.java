package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class NumberButtonPreference extends Preference {
    public BannerMessagePreferenceGroup$onBindViewHolder$1$1 clickListener;
    public int count;

    public NumberButtonPreference(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        View viewFindViewById = preferenceViewHolder.findViewById(R.id.settingslib_number_button);
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(this.clickListener);
        }
        View viewFindViewById2 = preferenceViewHolder.findViewById(R.id.settingslib_number_title);
        TextView textView = viewFindViewById2 instanceof TextView ? (TextView) viewFindViewById2 : null;
        if (textView != null) {
            textView.setText(this.mTitle);
        }
        View viewFindViewById3 = preferenceViewHolder.findViewById(R.id.settingslib_number_count);
        TextView textView2 = viewFindViewById3 instanceof TextView ? (TextView) viewFindViewById3 : null;
        if (textView2 != null) {
            textView2.setText(String.valueOf(this.count));
        }
    }

    public NumberButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public NumberButtonPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ NumberButtonPreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public NumberButtonPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPersistent = false;
        setOrder(Integer.MAX_VALUE);
        this.mLayoutResId = R.layout.settingslib_number_button;
    }
}

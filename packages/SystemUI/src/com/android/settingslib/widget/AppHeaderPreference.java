package com.android.settingslib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AppHeaderPreference extends Preference {
    public AppHeaderPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init$6();
    }

    public final void init$6() {
        this.mLayoutResId = R.layout.app_header_preference;
        setSelectable(false);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.install_type);
        if (textView != null) {
            textView.setVisibility(8);
        }
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.second_summary);
        if (textView2 != null) {
            if (TextUtils.isEmpty(null)) {
                textView2.setVisibility(8);
            } else {
                textView2.setText((CharSequence) null);
                textView2.setVisibility(0);
            }
        }
    }

    public AppHeaderPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init$6();
    }

    public AppHeaderPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init$6();
    }

    public AppHeaderPreference(Context context) {
        super(context);
        init$6();
    }
}

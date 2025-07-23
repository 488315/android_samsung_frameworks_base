package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class TwoTargetPreference extends Preference {
    public TwoTargetPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init$3(context);
    }

    public int getSecondTargetResId() {
        return 0;
    }

    public final void init$3(Context context) {
        this.mLayoutResId = SettingsThemeHelper.isExpressiveTheme(context) ? R.layout.settingslib_expressive_preference_two_target : R.layout.preference_two_target;
        context.getResources().getDimensionPixelSize(R.dimen.two_target_pref_small_icon_size);
        context.getResources().getDimensionPixelSize(R.dimen.two_target_pref_medium_icon_size);
        int secondTargetResId = getSecondTargetResId();
        if (secondTargetResId != 0) {
            this.mWidgetLayoutResId = secondTargetResId;
        }
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.two_target_divider);
        View findViewById2 = preferenceViewHolder.findViewById(android.R.id.widget_frame);
        boolean shouldHideSecondTarget = shouldHideSecondTarget();
        if (findViewById != null) {
            findViewById.setVisibility(shouldHideSecondTarget ? 8 : 0);
        }
        if (findViewById2 != null) {
            findViewById2.setVisibility(shouldHideSecondTarget ? 8 : 0);
        }
    }

    public boolean shouldHideSecondTarget() {
        return getSecondTargetResId() == 0;
    }

    public TwoTargetPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init$3(context);
    }

    public TwoTargetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init$3(context);
    }

    public TwoTargetPreference(Context context) {
        super(context);
        init$3(context);
    }
}

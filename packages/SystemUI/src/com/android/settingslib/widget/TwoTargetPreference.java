package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class TwoTargetPreference extends Preference {
    public TwoTargetPreference(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i, i2);
        init$3(context);
    }

    public int getSecondTargetResId() {
        return 0;
    }

    public final void init$3(Context context) throws Resources.NotFoundException {
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
        View viewFindViewById = preferenceViewHolder.findViewById(R.id.two_target_divider);
        View viewFindViewById2 = preferenceViewHolder.findViewById(android.R.id.widget_frame);
        boolean zShouldHideSecondTarget = shouldHideSecondTarget();
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(zShouldHideSecondTarget ? 8 : 0);
        }
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility(zShouldHideSecondTarget ? 8 : 0);
        }
    }

    public boolean shouldHideSecondTarget() {
        return getSecondTargetResId() == 0;
    }

    public TwoTargetPreference(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        super(context, attributeSet, i);
        init$3(context);
    }

    public TwoTargetPreference(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        init$3(context);
    }

    public TwoTargetPreference(Context context) throws Resources.NotFoundException {
        super(context);
        init$3(context);
    }
}

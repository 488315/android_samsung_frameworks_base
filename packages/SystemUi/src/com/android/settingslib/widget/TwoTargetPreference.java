package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class TwoTargetPreference extends Preference {
    public TwoTargetPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context);
    }

    public int getSecondTargetResId() {
        return 0;
    }

    public final void init(Context context) {
        this.mLayoutResId = R.layout.preference_two_target;
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
        init(context);
    }

    public TwoTargetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public TwoTargetPreference(Context context) {
        super(context);
        init(context);
    }
}

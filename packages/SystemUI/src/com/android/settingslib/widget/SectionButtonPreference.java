package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;
import com.google.android.material.button.MaterialButton;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SectionButtonPreference extends Preference {
    public MaterialButton button;
    public BannerMessagePreferenceGroup$$ExternalSyntheticLambda0 clickListener;

    public SectionButtonPreference(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        View findViewById = preferenceViewHolder.findViewById(R.id.settingslib_section_button);
        MaterialButton materialButton = findViewById instanceof MaterialButton ? (MaterialButton) findViewById : null;
        this.button = materialButton;
        if (materialButton != null) {
            materialButton.setText(this.mTitle);
            materialButton.setFocusable(this.mSelectable);
            materialButton.setClickable(this.mSelectable);
            materialButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.widget.SectionButtonPreference$onBindViewHolder$1$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BannerMessagePreferenceGroup$$ExternalSyntheticLambda0 bannerMessagePreferenceGroup$$ExternalSyntheticLambda0 = SectionButtonPreference.this.clickListener;
                    if (bannerMessagePreferenceGroup$$ExternalSyntheticLambda0 != null) {
                        view.getClass();
                        bannerMessagePreferenceGroup$$ExternalSyntheticLambda0.mo779invoke(view);
                    }
                }
            });
        }
        MaterialButton materialButton2 = this.button;
        if (materialButton2 != null) {
            materialButton2.setEnabled(isEnabled());
        }
        MaterialButton materialButton3 = this.button;
        if (materialButton3 != null) {
            materialButton3.setIcon(getIcon());
        }
    }

    public SectionButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public SectionButtonPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ SectionButtonPreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public SectionButtonPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPersistent = false;
        setOrder(Integer.MAX_VALUE);
        this.mLayoutResId = R.layout.settingslib_section_button;
    }
}

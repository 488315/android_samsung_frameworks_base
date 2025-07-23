package com.android.settingslib.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CardPreference extends Preference {
    public CardPreference(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedBelow = false;
        preferenceViewHolder.mDividerAllowedAbove = false;
        View findViewById = preferenceViewHolder.findViewById(R.id.closeButton);
        if (findViewById != null) {
            findViewById.setVisibility(8);
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.widget.CardPreference$onBindViewHolder$1$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CardPreference.this.setVisible(false);
                }
            });
        }
    }

    public CardPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public CardPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ CardPreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public CardPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLayoutResId = com.android.systemui.R.layout.settingslib_expressive_preference_card;
    }
}

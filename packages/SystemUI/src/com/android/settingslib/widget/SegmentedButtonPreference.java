package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SegmentedButtonPreference extends Preference {
    public final List buttonLabels;

    public SegmentedButtonPreference(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedBelow = false;
        preferenceViewHolder.mDividerAllowedAbove = false;
        ((ArrayList) this.buttonLabels).add((TextView) preferenceViewHolder.findViewById(R.id.button_1_text));
        ((ArrayList) this.buttonLabels).add((TextView) preferenceViewHolder.findViewById(R.id.button_2_text));
        ((ArrayList) this.buttonLabels).add((TextView) preferenceViewHolder.findViewById(R.id.button_3_text));
        ((ArrayList) this.buttonLabels).add((TextView) preferenceViewHolder.findViewById(R.id.button_4_text));
    }

    public SegmentedButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public SegmentedButtonPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ SegmentedButtonPreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public SegmentedButtonPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.buttonLabels = new ArrayList();
        this.mLayoutResId = R.layout.settingslib_expressive_preference_segmentedbutton;
    }
}

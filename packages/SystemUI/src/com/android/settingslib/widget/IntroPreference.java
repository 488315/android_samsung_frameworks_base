package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public final class IntroPreference extends Preference {
    public final boolean isCollapsable;
    public final int minLines;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public IntroPreference(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedBelow = false;
        preferenceViewHolder.mDividerAllowedAbove = false;
        View viewFindViewById = preferenceViewHolder.findViewById(R.id.collapsable_summary);
        CollapsableTextView collapsableTextView = viewFindViewById instanceof CollapsableTextView ? (CollapsableTextView) viewFindViewById : null;
        if (collapsableTextView != null) {
            boolean z = this.isCollapsable;
            collapsableTextView.isCollapsable = z;
            if (z) {
                collapsableTextView.isCollapsed = true;
            }
            collapsableTextView.updateView$1();
            collapsableTextView.minLines = RangesKt___RangesKt.coerceIn(this.minLines, 1, 10);
            collapsableTextView.updateView$1();
            CharSequence summary = getSummary();
            collapsableTextView.setVisibility((summary == null || summary.length() == 0) ? 8 : 0);
            collapsableTextView.titleTextView.setText(String.valueOf(getSummary()));
        }
    }

    public IntroPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public IntroPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ IntroPreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public IntroPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.isCollapsable = true;
        this.minLines = 1;
        this.mLayoutResId = R.layout.settingslib_expressive_preference_intro;
        setSelectable(false);
    }
}

package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.widget.theme.R$styleable;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class TopIntroPreference extends Preference {
    public static final int[] COLLAPSABLE_TEXT_VIEW_ATTRS;
    public static final int IS_COLLAPSABLE;
    public static final int MIN_LINES;
    public final boolean isCollapsable;
    public final int minLines;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        COLLAPSABLE_TEXT_VIEW_ATTRS = R$styleable.CollapsableTextView;
        MIN_LINES = 1;
        IS_COLLAPSABLE = 2;
    }

    public TopIntroPreference(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        View findViewById = preferenceViewHolder.findViewById(R.id.collapsable_text_view);
        CollapsableTextView collapsableTextView = findViewById instanceof CollapsableTextView ? (CollapsableTextView) findViewById : null;
        if (collapsableTextView != null) {
            boolean z = this.isCollapsable;
            collapsableTextView.isCollapsable = z;
            if (z) {
                collapsableTextView.isCollapsed = true;
            }
            collapsableTextView.updateView$1();
            collapsableTextView.minLines = RangesKt___RangesKt.coerceIn(this.minLines, 1, 10);
            collapsableTextView.updateView$1();
            CharSequence charSequence = this.mTitle;
            collapsableTextView.setVisibility((charSequence == null || charSequence.length() == 0) ? 8 : 0);
            collapsableTextView.titleTextView.setText(String.valueOf(this.mTitle));
        }
    }

    public TopIntroPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public TopIntroPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ TopIntroPreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public TopIntroPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.minLines = 2;
        this.mLayoutResId = R.layout.settingslib_expressive_top_intro;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, COLLAPSABLE_TEXT_VIEW_ATTRS, i, 0);
        boolean z = obtainStyledAttributes.getBoolean(IS_COLLAPSABLE, false);
        this.isCollapsable = z;
        this.minLines = RangesKt___RangesKt.coerceIn(obtainStyledAttributes.getInt(MIN_LINES, z ? 2 : 10), 1, 10);
        obtainStyledAttributes.recycle();
        setSelectable(false);
    }
}

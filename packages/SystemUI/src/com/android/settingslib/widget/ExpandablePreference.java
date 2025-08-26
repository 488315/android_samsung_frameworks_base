package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public final class ExpandablePreference extends PreferenceGroup {
    public ImageView expandIcon;
    public boolean isDirty;
    public boolean isExpanded;

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

    public ExpandablePreference(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // androidx.preference.PreferenceGroup
    public final boolean addPreference(Preference preference) {
        preference.setVisible(this.isExpanded);
        super.addPreference(preference);
        return true;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        this.expandIcon = (ImageView) preferenceViewHolder.findViewById(R.id.expand_icon);
        updateExpandedState();
        preferenceViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.widget.ExpandablePreference.onBindViewHolder.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ExpandablePreference expandablePreference = ExpandablePreference.this;
                expandablePreference.isExpanded = !expandablePreference.isExpanded;
                expandablePreference.isDirty = true;
                expandablePreference.updateExpandedState();
                expandablePreference.notifyChanged();
            }
        });
    }

    @Override // androidx.preference.PreferenceGroup
    public final void onPrepareAddPreference(Preference preference) {
        preference.setVisible(this.isExpanded);
        super.onPrepareAddPreference(preference);
    }

    public final void updateExpandedState() {
        float f;
        ImageView imageView = this.expandIcon;
        if (imageView != null) {
            boolean z = this.isExpanded;
            if (z) {
                f = 180.0f;
            } else {
                if (z) {
                    throw new NoWhenBranchMatchedException();
                }
                f = 0.0f;
            }
            imageView.setRotation(f);
        }
        if (this.isDirty) {
            IntProgressionIterator it = RangesKt___RangesKt.until(0, getPreferenceCount()).iterator();
            while (it.hasNext) {
                getPreference(it.nextInt()).setVisible(this.isExpanded);
            }
            this.isDirty = false;
        }
    }

    public ExpandablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public ExpandablePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ ExpandablePreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public ExpandablePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.isDirty = true;
        this.mLayoutResId = R.layout.settingslib_expressive_preference;
        this.mWidgetLayoutResId = R.layout.settingslib_widget_expandable_icon;
    }
}

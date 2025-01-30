package com.android.settingslib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class RadioButtonPreference extends CheckBoxPreference {
    public View mAppendix;
    public final int mAppendixVisibility;
    public ImageView mExtraWidget;
    public View mExtraWidgetContainer;
    public View.OnClickListener mExtraWidgetOnClickListener;

    public RadioButtonPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAppendixVisibility = -1;
        this.mWidgetLayoutResId = R.layout.preference_widget_radiobutton;
        this.mLayoutResId = R.layout.preference_radio;
        if (this.mIconSpaceReserved) {
            this.mIconSpaceReserved = false;
            notifyChanged();
        }
    }

    @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        int i;
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.summary_container);
        if (findViewById != null) {
            findViewById.setVisibility(TextUtils.isEmpty(getSummary()) ? 8 : 0);
            View findViewById2 = preferenceViewHolder.findViewById(R.id.appendix);
            this.mAppendix = findViewById2;
            if (findViewById2 != null && (i = this.mAppendixVisibility) != -1) {
                findViewById2.setVisibility(i);
            }
        }
        this.mExtraWidget = (ImageView) preferenceViewHolder.findViewById(R.id.radio_extra_widget);
        View findViewById3 = preferenceViewHolder.findViewById(R.id.radio_extra_widget_container);
        this.mExtraWidgetContainer = findViewById3;
        View.OnClickListener onClickListener = this.mExtraWidgetOnClickListener;
        this.mExtraWidgetOnClickListener = onClickListener;
        ImageView imageView = this.mExtraWidget;
        if (imageView == null || findViewById3 == null) {
            return;
        }
        imageView.setOnClickListener(onClickListener);
        this.mExtraWidgetContainer.setVisibility(this.mExtraWidgetOnClickListener == null ? 8 : 0);
    }

    public RadioButtonPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAppendixVisibility = -1;
        this.mWidgetLayoutResId = R.layout.preference_widget_radiobutton;
        this.mLayoutResId = R.layout.preference_radio;
        if (this.mIconSpaceReserved) {
            this.mIconSpaceReserved = false;
            notifyChanged();
        }
    }

    public RadioButtonPreference(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
    }
}

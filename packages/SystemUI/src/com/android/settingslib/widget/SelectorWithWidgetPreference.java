package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.widget.preference.selector.R$styleable;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class SelectorWithWidgetPreference extends CheckBoxPreference {
    static final int DEFAULT_MAX_LINES = 2;
    public View mAppendix;
    public final int mAppendixVisibility;
    public ImageView mExtraWidget;
    public View mExtraWidgetContainer;
    public final boolean mIsCheckBox;
    public int mTitleMaxLines;

    public SelectorWithWidgetPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAppendixVisibility = -1;
        this.mIsCheckBox = false;
        init(context, attributeSet, i);
    }

    public View getExtraWidget() {
        return this.mExtraWidget;
    }

    public final void init(Context context, AttributeSet attributeSet, int i) {
        if (this.mIsCheckBox) {
            this.mWidgetLayoutResId = R.layout.settingslib_preference_widget_checkbox;
        } else {
            this.mWidgetLayoutResId = R.layout.settingslib_preference_widget_radiobutton;
        }
        this.mLayoutResId = R.layout.preference_selector_with_widget;
        if (this.mIconSpaceReserved) {
            this.mIconSpaceReserved = false;
            notifyChanged();
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SelectorWithWidgetPreference, i, 0);
        this.mTitleMaxLines = typedArrayObtainStyledAttributes.getInt(0, 2);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        int i;
        super.onBindViewHolder(preferenceViewHolder);
        View viewFindViewById = preferenceViewHolder.findViewById(R.id.summary_container);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(TextUtils.isEmpty(getSummary()) ? 8 : 0);
            View viewFindViewById2 = preferenceViewHolder.findViewById(R.id.appendix);
            this.mAppendix = viewFindViewById2;
            if (viewFindViewById2 != null && (i = this.mAppendixVisibility) != -1) {
                viewFindViewById2.setVisibility(i);
            }
        }
        this.mExtraWidget = (ImageView) preferenceViewHolder.findViewById(R.id.selector_extra_widget);
        View viewFindViewById3 = preferenceViewHolder.findViewById(R.id.selector_extra_widget_container);
        this.mExtraWidgetContainer = viewFindViewById3;
        ImageView imageView = this.mExtraWidget;
        if (imageView != null && viewFindViewById3 != null) {
            imageView.setOnClickListener(null);
            this.mExtraWidgetContainer.setVisibility(8);
        }
        ImageView imageView2 = this.mExtraWidget;
        if (imageView2 != null) {
            imageView2.setContentDescription(this.mContext.getString(R.string.settings_label));
        }
        ((TextView) preferenceViewHolder.findViewById(android.R.id.title)).setMaxLines(this.mTitleMaxLines);
    }

    public SelectorWithWidgetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAppendixVisibility = -1;
        this.mIsCheckBox = false;
        init(context, attributeSet, 0);
    }

    public SelectorWithWidgetPreference(Context context, boolean z) {
        super(context, null);
        this.mAppendixVisibility = -1;
        this.mIsCheckBox = z;
        init(context, null, 0);
    }

    public SelectorWithWidgetPreference(Context context) {
        this(context, (AttributeSet) null);
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
    }
}

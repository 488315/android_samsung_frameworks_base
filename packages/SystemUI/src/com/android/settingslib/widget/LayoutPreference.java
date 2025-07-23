package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.R$styleable;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LayoutPreference extends Preference {
    public boolean mAllowDividerAbove;
    public boolean mAllowDividerBelow;
    public final View.OnClickListener mClickListener;
    public final boolean mIsRelativeLinkView;
    public View mRootView;

    public LayoutPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClickListener = new View.OnClickListener() { // from class: com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LayoutPreference.this.performClick();
            }
        };
        this.mIsRelativeLinkView = false;
        init$1(context, attributeSet, 0);
    }

    public final void init$1(Context context, AttributeSet attributeSet, int i) {
        int[] iArr = R$styleable.Preference;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        this.mAllowDividerAbove = obtainStyledAttributes.getBoolean(16, obtainStyledAttributes.getBoolean(16, false));
        this.mAllowDividerBelow = obtainStyledAttributes.getBoolean(17, obtainStyledAttributes.getBoolean(17, false));
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        int resourceId = obtainStyledAttributes2.getResourceId(3, 0);
        if (resourceId == 0) {
            throw new IllegalArgumentException("LayoutPreference requires a layout to be defined");
        }
        obtainStyledAttributes2.recycle();
        setView$1(LayoutInflater.from(this.mContext).inflate(resourceId, (ViewGroup) null, false));
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        if (this.mIsRelativeLinkView) {
            preferenceViewHolder.itemView.setOnClickListener(null);
            preferenceViewHolder.itemView.setFocusable(false);
            preferenceViewHolder.itemView.setClickable(false);
        } else {
            preferenceViewHolder.itemView.setOnClickListener(this.mClickListener);
            boolean z = this.mSelectable;
            preferenceViewHolder.itemView.setFocusable(z);
            preferenceViewHolder.itemView.setClickable(z);
            preferenceViewHolder.mDividerAllowedAbove = this.mAllowDividerAbove;
            preferenceViewHolder.mDividerAllowedBelow = this.mAllowDividerBelow;
        }
        FrameLayout frameLayout = (FrameLayout) preferenceViewHolder.itemView;
        frameLayout.removeAllViews();
        ViewGroup viewGroup = (ViewGroup) this.mRootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this.mRootView);
        }
        frameLayout.addView(this.mRootView);
    }

    public final void setView$1(View view) {
        this.mLayoutResId = R.layout.layout_preference_frame;
        this.mRootView = view;
        if (this.mShouldDisableView) {
            this.mShouldDisableView = false;
            notifyChanged();
        }
    }

    public LayoutPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mClickListener = new View.OnClickListener() { // from class: com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LayoutPreference.this.performClick();
            }
        };
        this.mIsRelativeLinkView = false;
        init$1(context, attributeSet, i);
    }

    public LayoutPreference(Context context, int i) {
        this(context, LayoutInflater.from(context).inflate(i, (ViewGroup) null, false));
    }

    public LayoutPreference(Context context, View view) {
        super(context);
        this.mClickListener = new View.OnClickListener() { // from class: com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LayoutPreference.this.performClick();
            }
        };
        this.mIsRelativeLinkView = false;
        setView$1(view);
    }

    public LayoutPreference(Context context, View view, boolean z) {
        super(context);
        this.mClickListener = new View.OnClickListener() { // from class: com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LayoutPreference.this.performClick();
            }
        };
        this.mIsRelativeLinkView = false;
        setView$1(view);
        this.mIsRelativeLinkView = z;
    }

    public LayoutPreference(Context context, View view, int i) {
        super(context);
        this.mClickListener = new View.OnClickListener() { // from class: com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LayoutPreference.this.performClick();
            }
        };
        this.mIsRelativeLinkView = false;
        setView$1(view);
    }
}

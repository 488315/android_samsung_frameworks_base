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
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LayoutPreference extends Preference {
    public boolean mAllowDividerAbove;
    public boolean mAllowDividerBelow;
    public final LayoutPreference$$ExternalSyntheticLambda0 mClickListener;
    public final boolean mIsRelativeLinkView;
    public View mRootView;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0] */
    public LayoutPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        final int i = 1;
        this.mClickListener = new View.OnClickListener(this) { // from class: com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
            public final /* synthetic */ LayoutPreference f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                }
                this.f$0.performClick();
            }
        };
        this.mIsRelativeLinkView = false;
        init(context, attributeSet, 0);
    }

    public final void init(Context context, AttributeSet attributeSet, int i) {
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
        setView(LayoutInflater.from(this.mContext).inflate(resourceId, (ViewGroup) null, false));
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        boolean z = this.mIsRelativeLinkView;
        View view = preferenceViewHolder.itemView;
        if (z) {
            view.setOnClickListener(null);
            view.setFocusable(false);
            view.setClickable(false);
        } else {
            view.setOnClickListener(this.mClickListener);
            boolean z2 = this.mSelectable;
            view.setFocusable(z2);
            view.setClickable(z2);
            preferenceViewHolder.mDividerAllowedAbove = this.mAllowDividerAbove;
            preferenceViewHolder.mDividerAllowedBelow = this.mAllowDividerBelow;
        }
        FrameLayout frameLayout = (FrameLayout) view;
        frameLayout.removeAllViews();
        ViewGroup viewGroup = (ViewGroup) this.mRootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this.mRootView);
        }
        frameLayout.addView(this.mRootView);
    }

    public final void setView(View view) {
        this.mLayoutResId = R.layout.layout_preference_frame;
        this.mRootView = view;
        if (this.mShouldDisableView) {
            this.mShouldDisableView = false;
            notifyChanged();
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0] */
    public LayoutPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        final int i2 = 3;
        this.mClickListener = new View.OnClickListener(this) { // from class: com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
            public final /* synthetic */ LayoutPreference f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                }
                this.f$0.performClick();
            }
        };
        this.mIsRelativeLinkView = false;
        init(context, attributeSet, i);
    }

    public LayoutPreference(Context context, int i) {
        this(context, LayoutInflater.from(context).inflate(i, (ViewGroup) null, false));
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0] */
    public LayoutPreference(Context context, View view) {
        super(context);
        final int i = 4;
        this.mClickListener = new View.OnClickListener(this) { // from class: com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
            public final /* synthetic */ LayoutPreference f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i) {
                }
                this.f$0.performClick();
            }
        };
        this.mIsRelativeLinkView = false;
        setView(view);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0] */
    public LayoutPreference(Context context, View view, boolean z) {
        super(context);
        final int i = 0;
        this.mClickListener = new View.OnClickListener(this) { // from class: com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
            public final /* synthetic */ LayoutPreference f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i) {
                }
                this.f$0.performClick();
            }
        };
        this.mIsRelativeLinkView = false;
        setView(view);
        this.mIsRelativeLinkView = z;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0] */
    public LayoutPreference(Context context, View view, int i) {
        super(context);
        final int i2 = 2;
        this.mClickListener = new View.OnClickListener(this) { // from class: com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
            public final /* synthetic */ LayoutPreference f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i2) {
                }
                this.f$0.performClick();
            }
        };
        this.mIsRelativeLinkView = false;
        setView(view);
    }
}

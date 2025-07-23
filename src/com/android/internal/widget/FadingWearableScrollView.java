package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import com.android.internal.widget.ViewGroupFader;

/* loaded from: classes6.dex */
public class FadingWearableScrollView extends ScrollView {
    private ViewGroupFader mFader;

    public FadingWearableScrollView(Context context) {
        this(context, null);
    }

    public FadingWearableScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842880);
    }

    public FadingWearableScrollView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public FadingWearableScrollView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    private void init() {
        this.mFader = createFader(this);
    }

    private ViewGroupFader createFader(ViewGroup viewGroup) {
        return new ViewGroupFader(viewGroup, new ViewGroupFader.AnimationCallback(this) { // from class: com.android.internal.widget.FadingWearableScrollView.1
            @Override // com.android.internal.widget.ViewGroupFader.AnimationCallback
            public boolean shouldFadeFromBottom(View view) {
                return true;
            }

            @Override // com.android.internal.widget.ViewGroupFader.AnimationCallback
            public boolean shouldFadeFromTop(View view) {
                return true;
            }

            @Override // com.android.internal.widget.ViewGroupFader.AnimationCallback
            public void viewHasBecomeFullSize(View view) {
            }
        }, new ViewGroupFader.GlobalVisibleViewBoundsProvider());
    }

    @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mFader.updateFade();
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        this.mFader.updateFade();
    }
}

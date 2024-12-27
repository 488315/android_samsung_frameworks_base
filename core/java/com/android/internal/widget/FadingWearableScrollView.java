package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class FadingWearableScrollView extends ScrollView {
    private ViewGroupFader mFader;

    public FadingWearableScrollView(Context context) {
        this(context, null);
    }

    public FadingWearableScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842880);
    }

    public FadingWearableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FadingWearableScrollView(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        this.mFader = createFader(this);
    }

    private ViewGroupFader createFader(ViewGroup container) {
        return new ViewGroupFader(
                container,
                new ViewGroupFader
                        .AnimationCallback() { // from class:
                                               // com.android.internal.widget.FadingWearableScrollView.1
                    @Override // com.android.internal.widget.ViewGroupFader.AnimationCallback
                    public boolean shouldFadeFromTop(View view) {
                        return true;
                    }

                    @Override // com.android.internal.widget.ViewGroupFader.AnimationCallback
                    public boolean shouldFadeFromBottom(View view) {
                        return true;
                    }

                    @Override // com.android.internal.widget.ViewGroupFader.AnimationCallback
                    public void viewHasBecomeFullSize(View view) {}
                },
                new ViewGroupFader.GlobalVisibleViewBoundsProvider());
    }

    @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.ViewGroup,
              // android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mFader.updateFade();
    }

    @Override // android.view.View
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        this.mFader.updateFade();
    }
}

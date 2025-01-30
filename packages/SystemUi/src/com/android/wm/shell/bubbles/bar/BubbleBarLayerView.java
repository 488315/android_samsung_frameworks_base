package com.android.wm.shell.bubbles.bar;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubblePositioner;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleBarLayerView extends FrameLayout implements ViewTreeObserver.OnComputeInternalInsetsListener {
    public final BubbleController mBubbleController;
    public BubbleBarExpandedView mExpandedView;
    public boolean mIsExpanded;
    public final BubblePositioner mPositioner;
    public final View mScrimView;
    public final Rect mTempRect;
    public final Region mTouchableRegion;

    public BubbleBarLayerView(Context context, BubbleController bubbleController) {
        super(context);
        this.mIsExpanded = false;
        this.mTouchableRegion = new Region();
        this.mTempRect = new Rect();
        this.mBubbleController = bubbleController;
        BubblePositioner positioner = bubbleController.getPositioner();
        this.mPositioner = positioner;
        new BubbleBarAnimationHelper(context, this, positioner);
        View view = new View(getContext());
        this.mScrimView = view;
        view.setImportantForAccessibility(2);
        view.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.system_neutral1_1000)));
        addView(view);
        view.setAlpha(0.0f);
        view.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.system_neutral1_1000)));
        setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarLayerView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BubbleBarLayerView.this.mBubbleController.collapseStack();
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mPositioner.update();
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
    }

    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        internalInsetsInfo.setTouchableInsets(3);
        this.mTouchableRegion.setEmpty();
        Region region = this.mTouchableRegion;
        this.mTempRect.setEmpty();
        if (this.mIsExpanded) {
            getBoundsOnScreen(this.mTempRect);
            region.op(this.mTempRect, Region.Op.UNION);
        }
        internalInsetsInfo.touchableRegion.set(this.mTouchableRegion);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
        BubbleBarExpandedView bubbleBarExpandedView = this.mExpandedView;
        if (bubbleBarExpandedView != null) {
            removeView(bubbleBarExpandedView);
            this.mExpandedView = null;
        }
    }
}

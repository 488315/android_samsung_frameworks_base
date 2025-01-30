package com.android.systemui.statusbar.notification.stack;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.view.View;
import com.android.systemui.statusbar.notification.row.ExpandableView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationSection {
    public final int mBucket;
    public ExpandableView mFirstVisibleChild;
    public ExpandableView mLastVisibleChild;
    public final View mOwningView;
    public final Rect mBounds = new Rect();
    public final Rect mCurrentBounds = new Rect(-1, -1, -1, -1);
    public final Rect mStartAnimationRect = new Rect();
    public final Rect mEndAnimationRect = new Rect();
    public ObjectAnimator mTopAnimator = null;
    public ObjectAnimator mBottomAnimator = null;

    public NotificationSection(View view, int i) {
        this.mOwningView = view;
        this.mBucket = i;
    }

    public final int updateBounds(int i, int i2, boolean z) {
        int i3;
        ExpandableView expandableView = this.mFirstVisibleChild;
        boolean z2 = true;
        Rect rect = this.mEndAnimationRect;
        Rect rect2 = this.mCurrentBounds;
        Rect rect3 = this.mBounds;
        if (expandableView != null) {
            int ceil = (int) Math.ceil(ViewState.getFinalTranslationY(expandableView));
            ObjectAnimator objectAnimator = this.mTopAnimator;
            i3 = Math.max((objectAnimator == null && rect2.top == ceil) || (objectAnimator != null && rect.top == ceil) ? ceil : (int) Math.ceil(expandableView.getTranslationY()), i);
            if (expandableView.showingPulsing()) {
                i = Math.max(i, ExpandableViewState.getFinalActualHeight(expandableView) + ceil);
                if (z) {
                    rect3.left = (int) (Math.max(expandableView.getTranslation(), 0.0f) + rect3.left);
                    rect3.right = (int) (Math.min(expandableView.getTranslation(), 0.0f) + rect3.right);
                }
            }
        } else {
            i3 = i;
        }
        ExpandableView expandableView2 = this.mLastVisibleChild;
        if (expandableView2 != null) {
            int floor = (int) Math.floor((ViewState.getFinalTranslationY(expandableView2) + ExpandableViewState.getFinalActualHeight(expandableView2)) - expandableView2.mClipBottomAmount);
            ObjectAnimator objectAnimator2 = this.mBottomAnimator;
            if ((objectAnimator2 != null || rect2.bottom != floor) && (objectAnimator2 == null || rect.bottom != floor)) {
                z2 = false;
            }
            if (!z2) {
                floor = (int) ((expandableView2.getTranslationY() + expandableView2.mActualHeight) - expandableView2.mClipBottomAmount);
                i2 = (int) Math.min(expandableView2.getTranslationY() + expandableView2.mActualHeight, i2);
            }
            i = Math.max(i, Math.max(floor, i2));
        }
        int max = Math.max(i3, i);
        rect3.top = i3;
        rect3.bottom = max;
        return max;
    }
}

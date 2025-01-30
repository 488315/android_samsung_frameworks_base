package com.android.p038wm.shell.bubbles.animation;

import android.content.Context;
import android.view.ViewConfiguration;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.p038wm.shell.animation.FlingAnimationUtils;
import com.android.p038wm.shell.bubbles.BubblePositioner;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ExpandedViewAnimationControllerImpl {
    public static final C38321 COLLAPSE_HEIGHT_PROPERTY = new FloatPropertyCompat("CollapseSpring") { // from class: com.android.wm.shell.bubbles.animation.ExpandedViewAnimationControllerImpl.1
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((ExpandedViewAnimationControllerImpl) obj).mCollapsedAmount;
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            ExpandedViewAnimationControllerImpl expandedViewAnimationControllerImpl = (ExpandedViewAnimationControllerImpl) obj;
            C38321 c38321 = ExpandedViewAnimationControllerImpl.COLLAPSE_HEIGHT_PROPERTY;
            if (expandedViewAnimationControllerImpl.mCollapsedAmount != f) {
                expandedViewAnimationControllerImpl.mCollapsedAmount = f;
            }
        }
    };
    public float mCollapsedAmount;
    public final int mMinFlingVelocity;
    public float mSwipeDownVelocity;
    public float mSwipeUpVelocity;

    public ExpandedViewAnimationControllerImpl(Context context, BubblePositioner bubblePositioner) {
        new FlingAnimationUtils(context.getResources().getDisplayMetrics(), 0.35f);
        this.mMinFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }
}

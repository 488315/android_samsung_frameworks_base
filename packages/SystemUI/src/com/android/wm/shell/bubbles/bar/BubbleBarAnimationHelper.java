package com.android.wm.shell.bubbles.bar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.util.Size;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.bubbles.BubbleViewProvider;
import com.android.wm.shell.bubbles.animation.AnimatableScaleMatrix;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.taskview.TaskView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubbleBarAnimationHelper {
    public BubbleViewProvider mExpandedBubble;
    public final BubblePositioner mPositioner;
    public Animator mRunningAnimator;
    public final PhysicsAnimator.SpringConfig mScaleInSpringConfig = new PhysicsAnimator.SpringConfig(300.0f, 0.9f);
    public final PhysicsAnimator.SpringConfig mScaleOutSpringConfig = new PhysicsAnimator.SpringConfig(900.0f, 1.0f);
    public final AnimatableScaleMatrix mExpandedViewContainerMatrix = new AnimatableScaleMatrix();
    public final int[] mTmpLocation = new int[2];

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DragAnimatorListenerAdapter extends AnimatorListenerAdapter {
        public final BubbleBarExpandedView mBubbleBarExpandedView;

        public DragAnimatorListenerAdapter(BubbleBarExpandedView bubbleBarExpandedView) {
            this.mBubbleBarExpandedView = bubbleBarExpandedView;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.mBubbleBarExpandedView.setAnimating(false);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            this.mBubbleBarExpandedView.setAnimating(true);
        }
    }

    public BubbleBarAnimationHelper(Context context, BubblePositioner bubblePositioner) {
        this.mPositioner = bubblePositioner;
        context.getResources().getDimensionPixelSize(R.dimen.bubble_bar_expanded_view_switch_offset);
    }

    public static void setDragPivot(BubbleBarExpandedView bubbleBarExpandedView) {
        bubbleBarExpandedView.setPivotX(bubbleBarExpandedView.getWidth() / 2.0f);
        bubbleBarExpandedView.setPivotY(0.0f);
    }

    public final void cancelAnimations() {
        PhysicsAnimator.Companion.getClass();
        PhysicsAnimator.Companion.getInstance(this.mExpandedViewContainerMatrix).cancel();
        BubbleBarExpandedView expandedView = getExpandedView();
        if (expandedView != null) {
            expandedView.animate().cancel();
        }
        Animator animator = this.mRunningAnimator;
        if (animator != null) {
            if (animator.isRunning()) {
                this.mRunningAnimator.cancel();
            }
            this.mRunningAnimator = null;
        }
    }

    public final BubbleBarExpandedView getExpandedView() {
        BubbleViewProvider bubbleViewProvider = this.mExpandedBubble;
        if (bubbleViewProvider != null) {
            return bubbleViewProvider.getBubbleBarExpandedView();
        }
        return null;
    }

    public final Point getExpandedViewRestPosition(Size size) {
        BubblePositioner bubblePositioner = this.mPositioner;
        int i = bubblePositioner.mExpandedViewPadding;
        Point point = new Point();
        if (bubblePositioner.isBubbleBarOnLeft()) {
            point.x = bubblePositioner.mInsets.left + i;
        } else {
            point.x = (bubblePositioner.mPositionRect.width() - size.getWidth()) - i;
        }
        point.y = (bubblePositioner.mBubbleBarTopOnScreen - bubblePositioner.mExpandedViewPadding) - size.getHeight();
        return point;
    }

    public final Size getExpandedViewSize() {
        boolean equals = this.mExpandedBubble.getKey().equals("Overflow");
        BubblePositioner bubblePositioner = this.mPositioner;
        return new Size(equals ? bubblePositioner.mOverflowWidth : bubblePositioner.mExpandedViewBubbleBarWidth, bubblePositioner.getExpandedViewHeightForBubbleBar(equals));
    }

    public final void startNewAnimator(Animator animator) {
        cancelAnimations();
        this.mRunningAnimator = animator;
        animator.start();
    }

    public final void updateExpandedView(final BubbleBarExpandedView bubbleBarExpandedView) {
        if (bubbleBarExpandedView == null) {
            Log.w("BubbleBarAnimationHelper", "Trying to update the expanded view without a bubble");
            return;
        }
        Size expandedViewSize = getExpandedViewSize();
        Point expandedViewRestPosition = getExpandedViewRestPosition(expandedViewSize);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) bubbleBarExpandedView.getLayoutParams();
        layoutParams.width = expandedViewSize.getWidth();
        layoutParams.height = expandedViewSize.getHeight();
        bubbleBarExpandedView.setLayoutParams(layoutParams);
        bubbleBarExpandedView.setX(expandedViewRestPosition.x);
        bubbleBarExpandedView.setY(expandedViewRestPosition.y);
        bubbleBarExpandedView.setScaleX(1.0f);
        bubbleBarExpandedView.setScaleY(1.0f);
        TaskView taskView = bubbleBarExpandedView.mTaskView;
        if (taskView != null) {
            taskView.getBoundsOnScreen(taskView.mTmpRect);
            taskView.mTaskViewController.setTaskBounds(taskView.mTaskViewTaskController, taskView.mTmpRect);
        }
        if (bubbleBarExpandedView.mOverflowView != null) {
            bubbleBarExpandedView.post(new Runnable() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarExpandedView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    BubbleBarExpandedView.this.mOverflowView.show();
                }
            });
        }
    }
}

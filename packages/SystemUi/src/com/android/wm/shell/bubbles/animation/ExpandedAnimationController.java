package com.android.wm.shell.bubbles.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.Interpolator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.wm.shell.animation.Interpolators;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.bubbles.BubbleStackView;
import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;
import com.android.systemui.R;
import com.google.android.collect.Sets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ExpandedAnimationController extends PhysicsAnimationLayout.PhysicsAnimationController {
    public Runnable mAfterCollapse;
    public Runnable mAfterExpand;
    public float mBubbleSizePx;
    public final BubbleStackView mBubbleStackView;
    public PointF mCollapsePoint;
    public Runnable mLeadBubbleEndAction;
    public C38311 mMagnetizedBubbleDraggingOut;
    public final Runnable mOnBubbleAnimatedOutAction;
    public final BubblePositioner mPositioner;
    public float mStackOffsetPx;
    public final PhysicsAnimator.SpringConfig mAnimateOutSpringConfig = new PhysicsAnimator.SpringConfig(400.0f, 1.0f);
    public boolean mAnimatingExpand = false;
    public boolean mPreparingToCollapse = false;
    public boolean mAnimatingCollapse = false;
    public boolean mSpringingBubbleToTouch = false;
    public boolean mBubbleDraggedOutEnough = false;

    public ExpandedAnimationController(BubblePositioner bubblePositioner, Runnable runnable, BubbleStackView bubbleStackView) {
        this.mPositioner = bubblePositioner;
        updateResources();
        this.mOnBubbleAnimatedOutAction = runnable;
        this.mCollapsePoint = bubblePositioner.getDefaultStartPosition();
        this.mBubbleStackView = bubbleStackView;
    }

    public final void expandFromStack(Runnable runnable) {
        this.mPreparingToCollapse = false;
        this.mAnimatingCollapse = false;
        this.mAnimatingExpand = true;
        this.mAfterExpand = runnable;
        this.mLeadBubbleEndAction = null;
        startOrUpdatePathAnimation(true);
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final Set getAnimatedProperties() {
        return Sets.newHashSet(new DynamicAnimation.ViewProperty[]{DynamicAnimation.TRANSLATION_X, DynamicAnimation.TRANSLATION_Y, DynamicAnimation.SCALE_X, DynamicAnimation.SCALE_Y, DynamicAnimation.ALPHA});
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final int getNextAnimationInChain(DynamicAnimation.ViewProperty viewProperty, int i) {
        return -1;
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final float getOffsetForChainedPropertyAnimation(DynamicAnimation.ViewProperty viewProperty, int i) {
        return 0.0f;
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final SpringForce getSpringForce() {
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(0.65f);
        springForce.setStiffness(200.0f);
        return springForce;
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final void onActiveControllerForLayout(PhysicsAnimationLayout physicsAnimationLayout) {
        updateResources();
        this.mLayout.setVisibility(0);
        animationsForChildrenFromIndex(new ExpandedAnimationController$$ExternalSyntheticLambda2()).startAll(new Runnable[0]);
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final void onChildAdded(View view, int i) {
        if (this.mAnimatingExpand) {
            startOrUpdatePathAnimation(true);
            return;
        }
        if (this.mAnimatingCollapse) {
            startOrUpdatePathAnimation(false);
            return;
        }
        PointF pointF = this.mCollapsePoint;
        BubblePositioner bubblePositioner = this.mPositioner;
        if (pointF == null) {
            pointF = bubblePositioner.getRestingPosition();
        } else {
            bubblePositioner.getClass();
        }
        boolean z = (bubblePositioner.mBubbleSize / 2) + ((int) pointF.x) < bubblePositioner.mScreenRect.width() / 2;
        PointF expandedBubbleXY = bubblePositioner.getExpandedBubbleXY(i, this.mBubbleStackView.getState());
        if (bubblePositioner.showBubblesVertically()) {
            view.setTranslationY(expandedBubbleXY.y);
        } else {
            view.setTranslationX(expandedBubbleXY.x);
        }
        if (this.mPreparingToCollapse) {
            return;
        }
        if (bubblePositioner.showBubblesVertically()) {
            float f = z ? expandedBubbleXY.x - (this.mBubbleSizePx * 4.0f) : expandedBubbleXY.x + (this.mBubbleSizePx * 4.0f);
            PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild = animationForChild(view);
            Map map = animationForChild.mInitialPropertyValues;
            DynamicAnimation.C01841 c01841 = DynamicAnimation.TRANSLATION_X;
            ((HashMap) map).put(c01841, Float.valueOf(f));
            animationForChild.mPathAnimator = null;
            animationForChild.property(c01841, expandedBubbleXY.y, new Runnable[0]);
            animationForChild.start(new Runnable[0]);
        } else {
            float f2 = expandedBubbleXY.y - (this.mBubbleSizePx * 4.0f);
            PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild2 = animationForChild(view);
            ((HashMap) animationForChild2.mInitialPropertyValues).put(DynamicAnimation.TRANSLATION_Y, Float.valueOf(f2));
            animationForChild2.translationY(expandedBubbleXY.y, new Runnable[0]);
            animationForChild2.start(new Runnable[0]);
        }
        updateBubblePositions();
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final void onChildRemoved(View view, PhysicsAnimationLayout$$ExternalSyntheticLambda1 physicsAnimationLayout$$ExternalSyntheticLambda1) {
        C38311 c38311 = this.mMagnetizedBubbleDraggingOut;
        boolean equals = view.equals(c38311 == null ? null : (View) c38311.underlyingObject);
        Runnable runnable = this.mOnBubbleAnimatedOutAction;
        if (equals) {
            this.mMagnetizedBubbleDraggingOut = null;
            physicsAnimationLayout$$ExternalSyntheticLambda1.run();
            runnable.run();
        } else {
            PhysicsAnimator physicsAnimator = PhysicsAnimator.getInstance(view);
            physicsAnimator.spring(DynamicAnimation.ALPHA, 0.0f, 0.0f, physicsAnimator.defaultSpring);
            DynamicAnimation.C01934 c01934 = DynamicAnimation.SCALE_X;
            PhysicsAnimator.SpringConfig springConfig = this.mAnimateOutSpringConfig;
            physicsAnimator.spring(c01934, 0.0f, 0.0f, springConfig);
            physicsAnimator.spring(DynamicAnimation.SCALE_Y, 0.0f, 0.0f, springConfig);
            physicsAnimator.withEndActions(physicsAnimationLayout$$ExternalSyntheticLambda1, runnable);
            physicsAnimator.start();
        }
        updateBubblePositions();
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final void onChildReordered() {
        if (this.mPreparingToCollapse) {
            return;
        }
        if (this.mAnimatingCollapse) {
            startOrUpdatePathAnimation(false);
        } else {
            updateBubblePositions();
        }
    }

    public final void startOrUpdatePathAnimation(final boolean z) {
        ExpandedAnimationController$$ExternalSyntheticLambda0 expandedAnimationController$$ExternalSyntheticLambda0 = z ? new ExpandedAnimationController$$ExternalSyntheticLambda0(this, 0) : new ExpandedAnimationController$$ExternalSyntheticLambda0(this, 1);
        final boolean showBubblesVertically = this.mPositioner.showBubblesVertically();
        final boolean z2 = this.mLayout.getContext().getResources().getConfiguration().getLayoutDirection() == 1;
        animationsForChildrenFromIndex(new PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator() { // from class: com.android.wm.shell.bubbles.animation.ExpandedAnimationController$$ExternalSyntheticLambda1
            @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator
            public final void configureAnimationForChildAtIndex(int i, PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator) {
                ExpandedAnimationController expandedAnimationController = ExpandedAnimationController.this;
                View childAt = expandedAnimationController.mLayout.getChildAt(i);
                Path path = new Path();
                path.moveTo(childAt.getTranslationX(), childAt.getTranslationY());
                PointF expandedBubbleXY = expandedAnimationController.mPositioner.getExpandedBubbleXY(i, expandedAnimationController.mBubbleStackView.getState());
                boolean z3 = z;
                if (z3) {
                    path.lineTo(childAt.getTranslationX(), expandedBubbleXY.y);
                    path.lineTo(expandedBubbleXY.x, expandedBubbleXY.y);
                } else {
                    float f = expandedAnimationController.mCollapsePoint.x;
                    path.lineTo(f, expandedBubbleXY.y);
                    path.lineTo(f, (Math.min(i, 1) * expandedAnimationController.mStackOffsetPx) + expandedAnimationController.mCollapsePoint.y);
                }
                boolean z4 = showBubblesVertically || !z2 ? !((!z3 || expandedAnimationController.mLayout.isFirstChildXLeftOfCenter(childAt.getTranslationX())) && (z3 || !expandedAnimationController.mLayout.isFirstChildXLeftOfCenter(expandedAnimationController.mCollapsePoint.x))) : !(!(z3 && expandedAnimationController.mLayout.isFirstChildXLeftOfCenter(childAt.getTranslationX())) && (z3 || expandedAnimationController.mLayout.isFirstChildXLeftOfCenter(expandedAnimationController.mCollapsePoint.x)));
                int childCount = z4 ? i * 10 : (expandedAnimationController.mLayout.getChildCount() - i) * 10;
                boolean z5 = (z4 && i == 0) || (!z4 && i == expandedAnimationController.mLayout.getChildCount() - 1);
                Interpolator interpolator = z3 ? Interpolators.EMPHASIZED_ACCELERATE : Interpolators.EMPHASIZED_DECELERATE;
                Runnable[] runnableArr = new Runnable[2];
                runnableArr[0] = z5 ? expandedAnimationController.mLeadBubbleEndAction : null;
                runnableArr[1] = new ExpandedAnimationController$$ExternalSyntheticLambda0(expandedAnimationController, 2);
                ObjectAnimator objectAnimator = physicsPropertyAnimator.mPathAnimator;
                if (objectAnimator != null) {
                    objectAnimator.cancel();
                }
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(physicsPropertyAnimator, physicsPropertyAnimator.mCurrentPointOnPathXProperty, physicsPropertyAnimator.mCurrentPointOnPathYProperty, path);
                physicsPropertyAnimator.mPathAnimator = ofFloat;
                ofFloat.addListener(new AnimatorListenerAdapter(physicsPropertyAnimator, runnableArr) { // from class: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsPropertyAnimator.3
                    public final /* synthetic */ Runnable[] val$pathAnimEndActions;

                    public C38403(PhysicsPropertyAnimator physicsPropertyAnimator2, Runnable[] runnableArr2) {
                        this.val$pathAnimEndActions = runnableArr2;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        for (Runnable runnable : this.val$pathAnimEndActions) {
                            if (runnable != null) {
                                runnable.run();
                            }
                        }
                    }
                });
                physicsPropertyAnimator2.mPathAnimator.setDuration(175);
                physicsPropertyAnimator2.mPathAnimator.setInterpolator(interpolator);
                Map map = physicsPropertyAnimator2.mAnimatedProperties;
                DynamicAnimation.C01841 c01841 = DynamicAnimation.TRANSLATION_X;
                HashMap hashMap = (HashMap) map;
                hashMap.remove(c01841);
                DynamicAnimation.C01912 c01912 = DynamicAnimation.TRANSLATION_Y;
                hashMap.remove(c01912);
                HashMap hashMap2 = (HashMap) physicsPropertyAnimator2.mInitialPropertyValues;
                hashMap2.remove(c01841);
                hashMap2.remove(c01912);
                PhysicsAnimationLayout physicsAnimationLayout = PhysicsAnimationLayout.this;
                physicsAnimationLayout.mEndActionForProperty.remove(c01841);
                physicsAnimationLayout.mEndActionForProperty.remove(c01912);
                physicsPropertyAnimator2.mStartDelay = childCount;
                physicsPropertyAnimator2.mStiffness = 400.0f;
            }
        }).startAll(new Runnable[]{expandedAnimationController$$ExternalSyntheticLambda0});
    }

    public final void updateBubblePositions() {
        if (this.mAnimatingExpand || this.mAnimatingCollapse) {
            return;
        }
        for (int i = 0; i < this.mLayout.getChildCount(); i++) {
            View childAt = this.mLayout.getChildAt(i);
            C38311 c38311 = this.mMagnetizedBubbleDraggingOut;
            if (childAt.equals(c38311 == null ? null : (View) c38311.underlyingObject)) {
                return;
            }
            PointF expandedBubbleXY = this.mPositioner.getExpandedBubbleXY(i, this.mBubbleStackView.getState());
            PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild = animationForChild(childAt);
            animationForChild.mPathAnimator = null;
            animationForChild.property(DynamicAnimation.TRANSLATION_X, expandedBubbleXY.x, new Runnable[0]);
            animationForChild.translationY(expandedBubbleXY.y, new Runnable[0]);
            animationForChild.start(new Runnable[0]);
        }
    }

    public final void updateResources() {
        if (this.mLayout == null) {
            return;
        }
        this.mStackOffsetPx = r0.getContext().getResources().getDimensionPixelSize(R.dimen.bubble_stack_offset);
        this.mBubbleSizePx = this.mPositioner.mBubbleSize;
    }
}

package com.android.wm.shell.bubbles.animation;

import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.systemui.R;
import com.android.systemui.keyguard.animator.ActionUpOrCancelHandler$$ExternalSyntheticOutline0;
import com.google.android.collect.Sets;
import java.util.HashMap;
import java.util.Set;
import java.util.function.IntSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StackAnimationController extends PhysicsAnimationLayout.PhysicsAnimationController {
    public final IntSupplier mBubbleCountSupplier;
    public int mBubblePaddingTop;
    public int mBubbleSize;
    public int mElevation;
    public final FloatingContentCoordinator mFloatingContentCoordinator;
    public C38442 mMagnetizedStack;
    public int mMaxBubbles;
    public final Runnable mOnBubbleAnimatedOutAction;
    public final Runnable mOnStackAnimationFinished;
    public final BubblePositioner mPositioner;
    public float mStackOffset;
    public float mSwapAnimationOffset;
    public final PhysicsAnimator.SpringConfig mAnimateOutSpringConfig = new PhysicsAnimator.SpringConfig(700.0f, 1.0f);
    public final PointF mStackPosition = new PointF(-1.0f, -1.0f);
    public Rect mAnimatingToBounds = new Rect();
    public boolean mStackMovedToStartPosition = false;
    public float mPreImeY = -1.4E-45f;
    public final HashMap mStackPositionAnimations = new HashMap();
    public boolean mIsMovingFromFlinging = false;
    public boolean mFirstBubbleSpringingToTouch = false;
    public final C38431 mStackFloatingContent = new C38431();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.bubbles.animation.StackAnimationController$1 */
    public final class C38431 implements FloatingContentCoordinator.FloatingContent {
        public final Rect mFloatingBoundsOnScreen = new Rect();

        public C38431() {
        }

        @Override // com.android.wm.shell.common.FloatingContentCoordinator.FloatingContent
        public final Rect getAllowedFloatingBoundsRegion() {
            Rect floatingBoundsOnScreen = getFloatingBoundsOnScreen();
            Rect rect = new Rect();
            StackAnimationController stackAnimationController = StackAnimationController.this;
            stackAnimationController.mPositioner.getAllowableStackPositionRegion(stackAnimationController.getBubbleCount()).roundOut(rect);
            rect.right = floatingBoundsOnScreen.width() + rect.right;
            rect.bottom = floatingBoundsOnScreen.height() + rect.bottom;
            return rect;
        }

        @Override // com.android.wm.shell.common.FloatingContentCoordinator.FloatingContent
        public final Rect getFloatingBoundsOnScreen() {
            StackAnimationController stackAnimationController = StackAnimationController.this;
            if (!stackAnimationController.mAnimatingToBounds.isEmpty()) {
                return stackAnimationController.mAnimatingToBounds;
            }
            int childCount = stackAnimationController.mLayout.getChildCount();
            Rect rect = this.mFloatingBoundsOnScreen;
            if (childCount > 0) {
                PointF pointF = stackAnimationController.mStackPosition;
                float f = pointF.x;
                float f2 = pointF.y;
                int i = stackAnimationController.mBubbleSize;
                rect.set((int) f, (int) f2, ((int) f) + i, ((int) f2) + i + stackAnimationController.mBubblePaddingTop);
            } else {
                rect.setEmpty();
            }
            return rect;
        }

        @Override // com.android.wm.shell.common.FloatingContentCoordinator.FloatingContent
        public final void moveToBounds(Rect rect) {
            StackAnimationController.this.springStack(rect.left, rect.top, 700.0f);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StackPositionProperty extends FloatPropertyCompat {
        public final DynamicAnimation.ViewProperty mProperty;

        public /* synthetic */ StackPositionProperty(StackAnimationController stackAnimationController, DynamicAnimation.ViewProperty viewProperty, int i) {
            this(viewProperty);
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            StackAnimationController stackAnimationController = StackAnimationController.this;
            if (stackAnimationController.mLayout.getChildCount() <= 0) {
                return 0.0f;
            }
            return this.mProperty.getValue(stackAnimationController.mLayout.getChildAt(0));
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            StackAnimationController.this.moveFirstBubbleWithStackFollowing(this.mProperty, f);
        }

        private StackPositionProperty(DynamicAnimation.ViewProperty viewProperty) {
            super(viewProperty.toString());
            this.mProperty = viewProperty;
        }
    }

    public StackAnimationController(FloatingContentCoordinator floatingContentCoordinator, IntSupplier intSupplier, Runnable runnable, Runnable runnable2, BubblePositioner bubblePositioner) {
        this.mFloatingContentCoordinator = floatingContentCoordinator;
        this.mBubbleCountSupplier = intSupplier;
        this.mOnBubbleAnimatedOutAction = runnable;
        this.mOnStackAnimationFinished = runnable2;
        this.mPositioner = bubblePositioner;
    }

    public final void animateInBubble(View view, int i) {
        if (isActiveController()) {
            float offsetForChainedPropertyAnimation = getOffsetForChainedPropertyAnimation(DynamicAnimation.TRANSLATION_Y, 0);
            PointF pointF = this.mStackPosition;
            float f = (offsetForChainedPropertyAnimation * i) + pointF.y;
            float f2 = pointF.x;
            BubblePositioner bubblePositioner = this.mPositioner;
            if (bubblePositioner.showBubblesVertically()) {
                view.setTranslationY(f);
                view.setTranslationX(isStackOnLeftSide() ? f2 - 100.0f : f2 + 100.0f);
            } else {
                view.setTranslationX(pointF.x);
                view.setTranslationY(100.0f + f);
            }
            view.setScaleX(0.5f);
            view.setScaleY(0.5f);
            view.setAlpha(0.0f);
            ViewPropertyAnimator withEndAction = view.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(300L).withEndAction(new StackAnimationController$$ExternalSyntheticLambda0(view, 0));
            view.setTag(R.id.reorder_animator_tag, withEndAction);
            if (bubblePositioner.showBubblesVertically()) {
                withEndAction.translationX(f2);
            } else {
                withEndAction.translationY(f);
            }
        }
    }

    public final void cancelStackPositionAnimation(DynamicAnimation.ViewProperty viewProperty) {
        HashMap hashMap = this.mStackPositionAnimations;
        if (hashMap.containsKey(viewProperty)) {
            ((DynamicAnimation) hashMap.get(viewProperty)).cancel();
        }
    }

    public final void flingThenSpringFirstBubbleWithStackFollowing(final DynamicAnimation.ViewProperty viewProperty, float f, float f2, final SpringForce springForce, final Float f3) {
        if (isActiveController()) {
            Log.d("Bubbs.StackCtrl", String.format("Flinging %s.", PhysicsAnimationLayout.getReadablePropertyName(viewProperty)));
            StackPositionProperty stackPositionProperty = new StackPositionProperty(this, viewProperty, 0);
            StackAnimationController stackAnimationController = StackAnimationController.this;
            float value = stackAnimationController.mLayout.getChildCount() > 0 ? stackPositionProperty.mProperty.getValue(stackAnimationController.mLayout.getChildAt(0)) : 0.0f;
            RectF allowableStackPositionRegion = this.mPositioner.getAllowableStackPositionRegion(getBubbleCount());
            DynamicAnimation.C01841 c01841 = DynamicAnimation.TRANSLATION_X;
            final float f4 = viewProperty.equals(c01841) ? allowableStackPositionRegion.left : allowableStackPositionRegion.top;
            final float f5 = viewProperty.equals(c01841) ? allowableStackPositionRegion.right : allowableStackPositionRegion.bottom;
            FlingAnimation flingAnimation = new FlingAnimation(this, stackPositionProperty);
            if (f2 <= 0.0f) {
                throw new IllegalArgumentException("Friction must be positive");
            }
            flingAnimation.mFlingForce.mFriction = (-4.2f) * f2;
            flingAnimation.mVelocity = f;
            flingAnimation.mMinValue = Math.min(value, f4);
            flingAnimation.mMaxValue = Math.max(value, f5);
            flingAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda2
                @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f6, float f7) {
                    float max;
                    DynamicAnimation.ViewProperty viewProperty2 = viewProperty;
                    SpringForce springForce2 = springForce;
                    StackAnimationController stackAnimationController2 = StackAnimationController.this;
                    if (z) {
                        stackAnimationController2.getClass();
                        return;
                    }
                    stackAnimationController2.mPositioner.setRestingPosition(stackAnimationController2.mStackPosition);
                    Float f8 = f3;
                    if (f8 != null) {
                        max = f8.floatValue();
                    } else {
                        max = Math.max(f4, Math.min(f5, f6));
                    }
                    stackAnimationController2.springFirstBubbleWithStackFollowing(viewProperty2, springForce2, f7, max, new Runnable[0]);
                }
            });
            cancelStackPositionAnimation(viewProperty);
            this.mStackPositionAnimations.put(viewProperty, flingAnimation);
            flingAnimation.start();
        }
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final Set getAnimatedProperties() {
        return Sets.newHashSet(new DynamicAnimation.ViewProperty[]{DynamicAnimation.TRANSLATION_X, DynamicAnimation.TRANSLATION_Y, DynamicAnimation.ALPHA, DynamicAnimation.SCALE_X, DynamicAnimation.SCALE_Y});
    }

    public final int getBubbleCount() {
        return this.mBubbleCountSupplier.getAsInt();
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final int getNextAnimationInChain(DynamicAnimation.ViewProperty viewProperty, int i) {
        if (viewProperty.equals(DynamicAnimation.TRANSLATION_X) || viewProperty.equals(DynamicAnimation.TRANSLATION_Y)) {
            return i + 1;
        }
        return -1;
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final float getOffsetForChainedPropertyAnimation(DynamicAnimation.ViewProperty viewProperty, int i) {
        if (!viewProperty.equals(DynamicAnimation.TRANSLATION_Y) || i > 1) {
            return 0.0f;
        }
        return this.mStackOffset;
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final SpringForce getSpringForce() {
        float f = Settings.Secure.getFloat(this.mLayout.getContext().getContentResolver(), "bubble_damping", 0.9f);
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(f);
        springForce.setStiffness(800.0f);
        return springForce;
    }

    public final boolean isStackOnLeftSide() {
        if (this.mLayout == null || !this.mStackMovedToStartPosition) {
            return true;
        }
        PointF pointF = this.mStackPosition;
        BubblePositioner bubblePositioner = this.mPositioner;
        if (pointF == null) {
            pointF = bubblePositioner.getRestingPosition();
        } else {
            bubblePositioner.getClass();
        }
        return (bubblePositioner.mBubbleSize / 2) + ((int) pointF.x) < bubblePositioner.mScreenRect.width() / 2;
    }

    public final void moveFirstBubbleWithStackFollowing(DynamicAnimation.ViewProperty viewProperty, float f) {
        boolean equals = viewProperty.equals(DynamicAnimation.TRANSLATION_X);
        PointF pointF = this.mStackPosition;
        if (equals) {
            pointF.x = f;
        } else if (viewProperty.equals(DynamicAnimation.TRANSLATION_Y)) {
            pointF.y = f;
        }
        if (this.mLayout.getChildCount() > 0) {
            viewProperty.setValue(this.mLayout.getChildAt(0), f);
            if (this.mLayout.getChildCount() > 1) {
                float offsetForChainedPropertyAnimation = getOffsetForChainedPropertyAnimation(viewProperty, 0) + f;
                PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild = animationForChild(this.mLayout.getChildAt(1));
                animationForChild.property(viewProperty, offsetForChainedPropertyAnimation, new Runnable[0]);
                animationForChild.start(new Runnable[0]);
            }
        }
    }

    public final void moveToFinalIndex(View view, int i, Runnable runnable) {
        if (view != null) {
            view.setTag(R.id.reorder_animator_tag, view.animate().translationY((Math.min(i, 1) * this.mStackOffset) + this.mStackPosition.y).setDuration(300L).withEndAction(new StackAnimationController$$ExternalSyntheticLambda4(1, view, runnable)));
        }
    }

    public final void notifyFloatingCoordinatorStackAnimatingTo(float f, float f2) {
        C38431 c38431 = this.mStackFloatingContent;
        Rect floatingBoundsOnScreen = c38431.getFloatingBoundsOnScreen();
        floatingBoundsOnScreen.offsetTo((int) f, (int) f2);
        this.mAnimatingToBounds = floatingBoundsOnScreen;
        this.mFloatingContentCoordinator.onContentMoved(c38431);
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final void onActiveControllerForLayout(PhysicsAnimationLayout physicsAnimationLayout) {
        Resources resources = physicsAnimationLayout.getResources();
        BubblePositioner bubblePositioner = this.mPositioner;
        this.mStackOffset = bubblePositioner.mStackOffset;
        this.mSwapAnimationOffset = resources.getDimensionPixelSize(R.dimen.bubble_swap_animation_offset);
        this.mMaxBubbles = resources.getInteger(R.integer.bubbles_max_rendered);
        this.mElevation = resources.getDimensionPixelSize(R.dimen.bubble_elevation);
        this.mBubbleSize = bubblePositioner.mBubbleSize;
        this.mBubblePaddingTop = bubblePositioner.mBubblePaddingTop;
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final void onChildAdded(View view, int i) {
        if (getBubbleCount() == 1) {
            this.mLayout.setVisibility(4);
            this.mLayout.post(new StackAnimationController$$ExternalSyntheticLambda0(this, 1));
        } else {
            if (this.mStackMovedToStartPosition && this.mLayout.indexOfChild(view) == 0) {
                animateInBubble(view, i);
                return;
            }
            view.setAlpha(1.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        }
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final void onChildRemoved(View view, PhysicsAnimationLayout$$ExternalSyntheticLambda1 physicsAnimationLayout$$ExternalSyntheticLambda1) {
        PhysicsAnimator physicsAnimator = PhysicsAnimator.getInstance(view);
        physicsAnimator.spring(DynamicAnimation.ALPHA, 0.0f, 0.0f, physicsAnimator.defaultSpring);
        DynamicAnimation.C01934 c01934 = DynamicAnimation.SCALE_X;
        PhysicsAnimator.SpringConfig springConfig = this.mAnimateOutSpringConfig;
        physicsAnimator.spring(c01934, 0.0f, 0.0f, springConfig);
        physicsAnimator.spring(DynamicAnimation.SCALE_Y, 0.0f, 0.0f, springConfig);
        physicsAnimator.withEndActions(physicsAnimationLayout$$ExternalSyntheticLambda1, this.mOnBubbleAnimatedOutAction);
        physicsAnimator.start();
        if (getBubbleCount() <= 0) {
            BubblePositioner bubblePositioner = this.mPositioner;
            bubblePositioner.setRestingPosition(bubblePositioner.getRestingPosition());
            ((HashMap) this.mFloatingContentCoordinator.allContentBounds).remove(this.mStackFloatingContent);
        } else {
            PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild = animationForChild(this.mLayout.getChildAt(0));
            animationForChild.mPathAnimator = null;
            animationForChild.property(DynamicAnimation.TRANSLATION_X, this.mStackPosition.x, new Runnable[0]);
            animationForChild.start(new Runnable[0]);
        }
    }

    public final void setStackPosition(PointF pointF) {
        Log.d("Bubbs.StackCtrl", String.format("Setting position to (%f, %f).", Float.valueOf(pointF.x), Float.valueOf(pointF.y)));
        PointF pointF2 = this.mStackPosition;
        pointF2.set(pointF.x, pointF.y);
        this.mPositioner.setRestingPosition(pointF2);
        if (isActiveController()) {
            PhysicsAnimationLayout physicsAnimationLayout = this.mLayout;
            DynamicAnimation.C01841 c01841 = DynamicAnimation.TRANSLATION_X;
            DynamicAnimation.C01912 c01912 = DynamicAnimation.TRANSLATION_Y;
            physicsAnimationLayout.cancelAllAnimationsOfProperties(c01841, c01912);
            cancelStackPositionAnimation(c01841);
            cancelStackPositionAnimation(c01912);
            this.mLayout.mEndActionForProperty.remove(c01841);
            this.mLayout.mEndActionForProperty.remove(c01912);
            float offsetForChainedPropertyAnimation = getOffsetForChainedPropertyAnimation(c01841, 0);
            float offsetForChainedPropertyAnimation2 = getOffsetForChainedPropertyAnimation(c01912, 0);
            for (int i = 0; i < this.mLayout.getChildCount(); i++) {
                float min = Math.min(i, 1);
                this.mLayout.getChildAt(i).setTranslationX((min * offsetForChainedPropertyAnimation) + pointF.x);
                this.mLayout.getChildAt(i).setTranslationY((min * offsetForChainedPropertyAnimation2) + pointF.y);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void springFirstBubbleWithStackFollowing(DynamicAnimation.ViewProperty viewProperty, SpringForce springForce, float f, float f2, final Runnable... runnableArr) {
        if (this.mLayout.getChildCount() == 0 || !isActiveController()) {
            return;
        }
        Log.d("Bubbs.StackCtrl", String.format("Springing %s to final position %f.", PhysicsAnimationLayout.getReadablePropertyName(viewProperty), Float.valueOf(f2)));
        SpringAnimation springAnimation = new SpringAnimation(this, new StackPositionProperty(this, viewProperty, 0));
        springAnimation.mSpring = springForce;
        final Object[] objArr = 0 == true ? 1 : 0;
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f3, float f4) {
                boolean z2 = objArr;
                StackAnimationController stackAnimationController = StackAnimationController.this;
                if (!z2) {
                    stackAnimationController.mPositioner.setRestingPosition(stackAnimationController.mStackPosition);
                }
                Runnable runnable = stackAnimationController.mOnStackAnimationFinished;
                if (runnable != null) {
                    runnable.run();
                }
                Runnable[] runnableArr2 = runnableArr;
                if (runnableArr2 != null) {
                    for (Runnable runnable2 : runnableArr2) {
                        runnable2.run();
                    }
                }
            }
        });
        springAnimation.mVelocity = f;
        cancelStackPositionAnimation(viewProperty);
        this.mStackPositionAnimations.put(viewProperty, springAnimation);
        springAnimation.animateToFinalPosition(f2);
    }

    public final void springStack(float f, float f2, float f3) {
        notifyFloatingCoordinatorStackAnimatingTo(f, f2);
        springFirstBubbleWithStackFollowing(DynamicAnimation.TRANSLATION_X, ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(f3, 0.85f), 0.0f, f, new Runnable[0]);
        springFirstBubbleWithStackFollowing(DynamicAnimation.TRANSLATION_Y, ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(f3, 0.85f), 0.0f, f2, new Runnable[0]);
    }

    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController
    public final void onChildReordered() {
    }
}

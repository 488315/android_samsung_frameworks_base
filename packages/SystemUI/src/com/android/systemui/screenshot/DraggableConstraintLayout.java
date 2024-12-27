package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.MathUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R;
import com.android.systemui.screenshot.DraggableConstraintLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class DraggableConstraintLayout extends ConstraintLayout implements ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public View mActionsContainer;
    public SwipeDismissCallbacks mCallbacks;
    public final DisplayMetrics mDisplayMetrics;
    public final GestureDetector mSwipeDetector;
    public final SwipeDismissHandler mSwipeDismissHandler;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SwipeDismissHandler implements View.OnTouchListener {
        public int mDirectionX;
        public ValueAnimator mDismissAnimation;
        public final DisplayMetrics mDisplayMetrics;
        public final GestureDetector mGestureDetector;
        public float mPreviousX;
        public float mStartX;
        public final DraggableConstraintLayout mView;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.screenshot.DraggableConstraintLayout$SwipeDismissHandler$1, reason: invalid class name */
        public final class AnonymousClass1 extends AnimatorListenerAdapter {
            public boolean mCancelled;

            public AnonymousClass1() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                this.mCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (this.mCancelled) {
                    return;
                }
                DraggableConstraintLayout.this.mCallbacks.onDismissComplete();
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class SwipeDismissGestureListener extends GestureDetector.SimpleOnGestureListener {
            public SwipeDismissGestureListener() {
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (SwipeDismissHandler.this.mView.getTranslationX() * f <= 0.0f) {
                    return false;
                }
                ValueAnimator valueAnimator = SwipeDismissHandler.this.mDismissAnimation;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    return false;
                }
                ValueAnimator createSwipeDismissAnimation = SwipeDismissHandler.this.createSwipeDismissAnimation(f / 1000.0f);
                DraggableConstraintLayout.this.mCallbacks.onSwipeDismissInitiated(createSwipeDismissAnimation);
                SwipeDismissHandler swipeDismissHandler = SwipeDismissHandler.this;
                swipeDismissHandler.mDismissAnimation = createSwipeDismissAnimation;
                createSwipeDismissAnimation.addListener(swipeDismissHandler.new AnonymousClass1());
                swipeDismissHandler.mDismissAnimation.start();
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                SwipeDismissHandler.this.mView.setTranslationX(motionEvent2.getRawX() - SwipeDismissHandler.this.mStartX);
                SwipeDismissHandler swipeDismissHandler = SwipeDismissHandler.this;
                float rawX = motionEvent2.getRawX();
                SwipeDismissHandler swipeDismissHandler2 = SwipeDismissHandler.this;
                swipeDismissHandler.mDirectionX = rawX < swipeDismissHandler2.mPreviousX ? -1 : 1;
                swipeDismissHandler2.mPreviousX = motionEvent2.getRawX();
                return true;
            }
        }

        public SwipeDismissHandler(Context context, DraggableConstraintLayout draggableConstraintLayout) {
            this.mView = draggableConstraintLayout;
            this.mGestureDetector = new GestureDetector(context, new SwipeDismissGestureListener());
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.mDisplayMetrics = displayMetrics;
            context.getDisplay().getRealMetrics(displayMetrics);
        }

        public final ValueAnimator createSwipeDismissAnimation(float f) {
            int i;
            float min = Math.min(3.0f, Math.max(1.0f, f));
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            final float translationX = this.mView.getTranslationX();
            int layoutDirection = this.mView.getContext().getResources().getConfiguration().getLayoutDirection();
            if (translationX > 0.0f || (translationX == 0.0f && layoutDirection == 1)) {
                i = this.mDisplayMetrics.widthPixels;
            } else {
                DraggableConstraintLayout draggableConstraintLayout = DraggableConstraintLayout.this;
                int i2 = DraggableConstraintLayout.$r8$clinit;
                View findViewById = draggableConstraintLayout.findViewById(R.id.actions_container_background);
                i = (findViewById == null ? 0 : findViewById.getRight()) * (-1);
            }
            float f2 = i - translationX;
            float min2 = Math.min(Math.abs(f2), FloatingWindowUtil.dpToPx(this.mDisplayMetrics, 400.0f));
            final float copySign = Math.copySign(min2, f2);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.DraggableConstraintLayout$SwipeDismissHandler$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    DraggableConstraintLayout.SwipeDismissHandler swipeDismissHandler = DraggableConstraintLayout.SwipeDismissHandler.this;
                    float f3 = translationX;
                    float f4 = copySign;
                    swipeDismissHandler.getClass();
                    swipeDismissHandler.mView.setTranslationX(MathUtils.lerp(f3, f4 + f3, valueAnimator.getAnimatedFraction()));
                    swipeDismissHandler.mView.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
                }
            });
            ofFloat.setDuration((long) Math.abs(min2 / min));
            return ofFloat;
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            boolean onTouchEvent = this.mGestureDetector.onTouchEvent(motionEvent);
            DraggableConstraintLayout.this.mCallbacks.onInteraction();
            if (motionEvent.getActionMasked() == 0) {
                float rawX = motionEvent.getRawX();
                this.mStartX = rawX;
                this.mPreviousX = rawX;
                return true;
            }
            if (motionEvent.getActionMasked() != 1) {
                return onTouchEvent;
            }
            ValueAnimator valueAnimator = this.mDismissAnimation;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                return true;
            }
            float translationX = this.mView.getTranslationX();
            if (this.mDirectionX * translationX <= 0.0f || Math.abs(translationX) < FloatingWindowUtil.dpToPx(this.mDisplayMetrics, 20.0f)) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                final float translationX2 = this.mView.getTranslationX();
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.DraggableConstraintLayout$SwipeDismissHandler$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        DraggableConstraintLayout.SwipeDismissHandler swipeDismissHandler = DraggableConstraintLayout.SwipeDismissHandler.this;
                        float f = translationX2;
                        swipeDismissHandler.getClass();
                        swipeDismissHandler.mView.setTranslationX(MathUtils.lerp(f, 0.0f, valueAnimator2.getAnimatedFraction()));
                    }
                });
                ofFloat.start();
            } else {
                ValueAnimator createSwipeDismissAnimation = createSwipeDismissAnimation(FloatingWindowUtil.dpToPx(this.mDisplayMetrics, 1.0f));
                DraggableConstraintLayout.this.mCallbacks.onSwipeDismissInitiated(createSwipeDismissAnimation);
                this.mDismissAnimation = createSwipeDismissAnimation;
                createSwipeDismissAnimation.addListener(new AnonymousClass1());
                this.mDismissAnimation.start();
            }
            return true;
        }
    }

    public DraggableConstraintLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
    }

    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        Region region = new Region();
        Rect rect = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                childAt.getGlobalVisibleRect(rect);
                rect.inset((int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f));
                region.op(rect, Region.Op.UNION);
            }
        }
        internalInsetsInfo.setTouchableInsets(3);
        internalInsetsInfo.touchableRegion.set(region);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        this.mActionsContainer = findViewById(R.id.actions_container);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        this.mCallbacks.onInteraction();
        return super.onInterceptHoverEvent(motionEvent);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            this.mSwipeDismissHandler.onTouch(this, motionEvent);
        }
        return this.mSwipeDetector.onTouchEvent(motionEvent);
    }

    public void setCallbacks(SwipeDismissCallbacks swipeDismissCallbacks) {
        this.mCallbacks = swipeDismissCallbacks;
    }

    public DraggableConstraintLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DraggableConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDisplayMetrics = displayMetrics;
        ((ViewGroup) this).mContext.getDisplay().getRealMetrics(displayMetrics);
        SwipeDismissHandler swipeDismissHandler = new SwipeDismissHandler(((ViewGroup) this).mContext, this);
        this.mSwipeDismissHandler = swipeDismissHandler;
        setOnTouchListener(swipeDismissHandler);
        GestureDetector gestureDetector = new GestureDetector(((ViewGroup) this).mContext, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.screenshot.DraggableConstraintLayout.1
            public final Rect mActionsRect = new Rect();

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                DraggableConstraintLayout.this.mActionsContainer.getBoundsOnScreen(this.mActionsRect);
                return (this.mActionsRect.contains((int) motionEvent2.getRawX(), (int) motionEvent2.getRawY()) && DraggableConstraintLayout.this.mActionsContainer.canScrollHorizontally((int) f)) ? false : true;
            }
        });
        this.mSwipeDetector = gestureDetector;
        gestureDetector.setIsLongpressEnabled(false);
        this.mCallbacks = new SwipeDismissCallbacks(this) { // from class: com.android.systemui.screenshot.DraggableConstraintLayout.2
        };
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SwipeDismissCallbacks {
        default void onDismissComplete() {
        }

        default void onInteraction() {
        }

        default void onSwipeDismissInitiated(Animator animator) {
        }
    }
}

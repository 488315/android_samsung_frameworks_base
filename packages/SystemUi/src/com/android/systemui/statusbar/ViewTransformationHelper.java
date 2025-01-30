package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.statusbar.notification.TransformState;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ViewTransformationHelper implements TransformableView, TransformState.TransformInfo {
    public ValueAnimator mViewTransformationAnimation;
    public final ArrayMap mTransformedViews = new ArrayMap();
    public final ArraySet mKeysTransformingToSimilar = new ArraySet();
    public final ArrayMap mCustomTransformations = new ArrayMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class CustomTransformation {
        public boolean customTransformTarget(TransformState transformState, TransformState transformState2) {
            return false;
        }

        public Interpolator getCustomInterpolator(int i, boolean z) {
            return null;
        }

        public boolean initTransformation(TransformState transformState, TransformState transformState2) {
            return false;
        }

        public abstract boolean transformFrom(TransformState transformState, TransformableView transformableView, float f);

        public abstract boolean transformTo(TransformState transformState, TransformableView transformableView, float f);
    }

    /* renamed from: -$$Nest$mabortTransformations, reason: not valid java name */
    public static void m1704$$Nest$mabortTransformations(ViewTransformationHelper viewTransformationHelper) {
        Iterator it = viewTransformationHelper.mTransformedViews.keySet().iterator();
        while (it.hasNext()) {
            TransformState currentState = viewTransformationHelper.getCurrentState(((Integer) it.next()).intValue());
            if (currentState != null) {
                currentState.abortTransformation();
                currentState.recycle();
            }
        }
    }

    public final void addTransformedView(View view, int i) {
        this.mTransformedViews.put(Integer.valueOf(i), view);
    }

    public final void addViewTransformingToSimilar(View view) {
        int id = view.getId();
        if (id == -1) {
            throw new IllegalArgumentException("View argument does not have a valid id");
        }
        addTransformedView(view, id);
        this.mKeysTransformingToSimilar.add(Integer.valueOf(id));
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public final TransformState getCurrentState(int i) {
        View view = (View) this.mTransformedViews.get(Integer.valueOf(i));
        if (view == null || view.getVisibility() == 8) {
            return null;
        }
        TransformState createFrom = TransformState.createFrom(view, this);
        if ((i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8) || this.mKeysTransformingToSimilar.contains(Integer.valueOf(i))) {
            createFrom.mSameAsAny = true;
        }
        return createFrom;
    }

    public final void setCustomTransformation(CustomTransformation customTransformation, int i) {
        this.mCustomTransformations.put(Integer.valueOf(i), customTransformation);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public final void setVisible(boolean z) {
        ValueAnimator valueAnimator = this.mViewTransformationAnimation;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        Iterator it = this.mTransformedViews.keySet().iterator();
        while (it.hasNext()) {
            TransformState currentState = getCurrentState(((Integer) it.next()).intValue());
            if (currentState != null) {
                currentState.setVisible(z, false);
                currentState.recycle();
            }
        }
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public final void transformFrom(final TransformableView transformableView) {
        ValueAnimator valueAnimator = this.mViewTransformationAnimation;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mViewTransformationAnimation = ofFloat;
        ofFloat.addUpdateListener(new ViewTransformationHelper$$ExternalSyntheticLambda0(this, transformableView, 1));
        this.mViewTransformationAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.ViewTransformationHelper.2
            public boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (!this.mCancelled) {
                    ViewTransformationHelper.this.setVisible(true);
                    return;
                }
                ViewTransformationHelper.m1704$$Nest$mabortTransformations(ViewTransformationHelper.this);
                CustomTransformation customTransformation = (CustomTransformation) ViewTransformationHelper.this.mCustomTransformations.get(6);
                TransformState currentState = ViewTransformationHelper.this.getCurrentState(6);
                if (customTransformation == null || currentState == null || !customTransformation.transformFrom(currentState, transformableView, 1.0f)) {
                    return;
                }
                currentState.recycle();
            }
        });
        this.mViewTransformationAnimation.setInterpolator(Interpolators.LINEAR);
        this.mViewTransformationAnimation.setDuration(360L);
        this.mViewTransformationAnimation.start();
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public final void transformTo(TransformableView transformableView, final Runnable runnable) {
        ValueAnimator valueAnimator = this.mViewTransformationAnimation;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mViewTransformationAnimation = ofFloat;
        ofFloat.addUpdateListener(new ViewTransformationHelper$$ExternalSyntheticLambda0(this, transformableView, 0));
        this.mViewTransformationAnimation.setInterpolator(Interpolators.LINEAR);
        this.mViewTransformationAnimation.setDuration(360L);
        this.mViewTransformationAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.ViewTransformationHelper.1
            public boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (this.mCancelled) {
                    ViewTransformationHelper.m1704$$Nest$mabortTransformations(ViewTransformationHelper.this);
                    return;
                }
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                ViewTransformationHelper.this.setVisible(false);
                ViewTransformationHelper.this.mViewTransformationAnimation = null;
            }
        });
        this.mViewTransformationAnimation.start();
    }

    public final void addTransformedView(View view) {
        int id = view.getId();
        if (id == -1) {
            throw new IllegalArgumentException("View argument does not have a valid id");
        }
        addTransformedView(view, id);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public final void transformFrom(float f, TransformableView transformableView) {
        for (Integer num : this.mTransformedViews.keySet()) {
            TransformState currentState = getCurrentState(num.intValue());
            if (currentState != null) {
                CustomTransformation customTransformation = (CustomTransformation) this.mCustomTransformations.get(num);
                if (customTransformation != null && customTransformation.transformFrom(currentState, transformableView, f)) {
                    currentState.recycle();
                } else {
                    TransformState currentState2 = transformableView.getCurrentState(num.intValue());
                    if (currentState2 != null) {
                        currentState.transformViewFrom(currentState2, f);
                        currentState2.recycle();
                    } else {
                        currentState.appear(f, transformableView);
                    }
                    currentState.recycle();
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public final void transformTo(float f, TransformableView transformableView) {
        for (Integer num : this.mTransformedViews.keySet()) {
            TransformState currentState = getCurrentState(num.intValue());
            if (currentState != null) {
                CustomTransformation customTransformation = (CustomTransformation) this.mCustomTransformations.get(num);
                if (customTransformation != null && customTransformation.transformTo(currentState, transformableView, f)) {
                    currentState.recycle();
                } else {
                    TransformState currentState2 = transformableView.getCurrentState(num.intValue());
                    if (currentState2 != null) {
                        currentState.transformViewTo(currentState2, f);
                        currentState2.recycle();
                    } else {
                        currentState.disappear(f, transformableView);
                    }
                    currentState.recycle();
                }
            }
        }
    }
}

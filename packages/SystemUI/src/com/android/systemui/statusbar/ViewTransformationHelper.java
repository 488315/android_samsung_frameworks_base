package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.TransformState;
import java.util.Iterator;
import java.util.Stack;

public final class ViewTransformationHelper implements TransformableView {
    public ValueAnimator mViewTransformationAnimation;
    public final ArrayMap mTransformedViews = new ArrayMap();
    public final ArraySet mKeysTransformingToSimilar = new ArraySet();
    public final ArrayMap mCustomTransformations = new ArrayMap();

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
    public static void m2117$$Nest$mabortTransformations(ViewTransformationHelper viewTransformationHelper) {
        Iterator it = viewTransformationHelper.mTransformedViews.keySet().iterator();
        while (it.hasNext()) {
            TransformState currentState = viewTransformationHelper.getCurrentState(((Integer) it.next()).intValue());
            if (currentState != null) {
                currentState.abortTransformation();
                currentState.recycle();
            }
        }
    }

    public final void addRemainingTransformTypes(View view) {
        int id;
        int size = this.mTransformedViews.size();
        for (int i = 0; i < size; i++) {
            View view2 = (View) this.mTransformedViews.valueAt(i);
            if (view2 != null) {
                while (view2 != view.getParent()) {
                    view2.setTag(R.id.contains_transformed_view, Boolean.TRUE);
                    view2 = (View) view2.getParent();
                }
            }
        }
        Stack stack = new Stack();
        stack.push(view);
        while (!stack.isEmpty()) {
            View view3 = (View) stack.pop();
            if (((Boolean) view3.getTag(R.id.contains_transformed_view)) != null || (id = view3.getId()) == -1 || id == 16909118) {
                view3.setTag(R.id.contains_transformed_view, null);
                if ((view3 instanceof ViewGroup) && !this.mTransformedViews.containsValue(view3)) {
                    ViewGroup viewGroup = (ViewGroup) view3;
                    for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                        stack.push(viewGroup.getChildAt(i2));
                    }
                }
            } else {
                addTransformedView(view3, id);
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
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || this.mKeysTransformingToSimilar.contains(Integer.valueOf(i))) {
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
                ViewTransformationHelper.m2117$$Nest$mabortTransformations(ViewTransformationHelper.this);
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
                    ViewTransformationHelper.m2117$$Nest$mabortTransformations(ViewTransformationHelper.this);
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

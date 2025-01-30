package com.android.p038wm.shell.bubbles.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.p038wm.shell.bubbles.BadgedImageView;
import com.android.p038wm.shell.bubbles.animation.PhysicsAnimationLayout;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PhysicsAnimationLayout extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public PhysicsAnimationController mController;
    public final HashMap mEndActionForProperty;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AllAnimationsForPropertyFinishedEndListener implements DynamicAnimation.OnAnimationEndListener {
        public final DynamicAnimation.ViewProperty mProperty;

        public AllAnimationsForPropertyFinishedEndListener(DynamicAnimation.ViewProperty viewProperty) {
            this.mProperty = viewProperty;
        }

        @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
        public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
            PhysicsAnimationLayout physicsAnimationLayout;
            Runnable runnable;
            DynamicAnimation.ViewProperty viewProperty = this.mProperty;
            DynamicAnimation.ViewProperty[] viewPropertyArr = {viewProperty};
            boolean z2 = false;
            int i = 0;
            while (true) {
                physicsAnimationLayout = PhysicsAnimationLayout.this;
                if (i >= physicsAnimationLayout.getChildCount()) {
                    break;
                }
                if (PhysicsAnimationLayout.arePropertiesAnimatingOnView(physicsAnimationLayout.getChildAt(i), viewPropertyArr)) {
                    z2 = true;
                    break;
                }
                i++;
            }
            if (z2 || !physicsAnimationLayout.mEndActionForProperty.containsKey(viewProperty) || (runnable = (Runnable) physicsAnimationLayout.mEndActionForProperty.get(viewProperty)) == null) {
                return;
            }
            runnable.run();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class PhysicsAnimationController {
        public PhysicsAnimationLayout mLayout;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public interface ChildAnimationConfigurator {
            void configureAnimationForChildAtIndex(int i, PhysicsPropertyAnimator physicsPropertyAnimator);
        }

        public final PhysicsPropertyAnimator animationForChild(View view) {
            PhysicsPropertyAnimator physicsPropertyAnimator = (PhysicsPropertyAnimator) view.getTag(R.id.physics_animator_tag);
            if (physicsPropertyAnimator == null) {
                PhysicsAnimationLayout physicsAnimationLayout = this.mLayout;
                Objects.requireNonNull(physicsAnimationLayout);
                physicsPropertyAnimator = physicsAnimationLayout.new PhysicsPropertyAnimator(view);
                view.setTag(R.id.physics_animator_tag, physicsPropertyAnimator);
            }
            physicsPropertyAnimator.clearAnimator();
            physicsPropertyAnimator.mAssociatedController = this;
            return physicsPropertyAnimator;
        }

        public final C3833x49ea1e05 animationsForChildrenFromIndex(ChildAnimationConfigurator childAnimationConfigurator) {
            HashSet hashSet = new HashSet();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.mLayout.getChildCount(); i++) {
                PhysicsPropertyAnimator animationForChild = animationForChild(this.mLayout.getChildAt(i));
                childAnimationConfigurator.configureAnimationForChildAtIndex(i, animationForChild);
                HashSet hashSet2 = new HashSet(((HashMap) animationForChild.mAnimatedProperties).keySet());
                if (animationForChild.mPathAnimator != null) {
                    hashSet2.add(DynamicAnimation.TRANSLATION_X);
                    hashSet2.add(DynamicAnimation.TRANSLATION_Y);
                }
                hashSet.addAll(hashSet2);
                arrayList.add(animationForChild);
            }
            return new C3833x49ea1e05(this, hashSet, arrayList);
        }

        public abstract Set getAnimatedProperties();

        public abstract int getNextAnimationInChain(DynamicAnimation.ViewProperty viewProperty, int i);

        public abstract float getOffsetForChainedPropertyAnimation(DynamicAnimation.ViewProperty viewProperty, int i);

        public abstract SpringForce getSpringForce();

        public final boolean isActiveController() {
            PhysicsAnimationLayout physicsAnimationLayout = this.mLayout;
            return physicsAnimationLayout != null && this == physicsAnimationLayout.mController;
        }

        public abstract void onActiveControllerForLayout(PhysicsAnimationLayout physicsAnimationLayout);

        public abstract void onChildAdded(View view, int i);

        public abstract void onChildRemoved(View view, PhysicsAnimationLayout$$ExternalSyntheticLambda1 physicsAnimationLayout$$ExternalSyntheticLambda1);

        public abstract void onChildReordered();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PhysicsPropertyAnimator {
        public PhysicsAnimationController mAssociatedController;
        public ObjectAnimator mPathAnimator;
        public Runnable[] mPositionEndActions;
        public final View mView;
        public float mDefaultStartVelocity = -3.4028235E38f;
        public long mStartDelay = 0;
        public float mDampingRatio = -1.0f;
        public float mStiffness = -1.0f;
        public final Map mEndActionsForProperty = new HashMap();
        public final Map mPositionStartVelocities = new HashMap();
        public final Map mAnimatedProperties = new HashMap();
        public final Map mInitialPropertyValues = new HashMap();
        public final PointF mCurrentPointOnPath = new PointF();
        public final C38381 mCurrentPointOnPathXProperty = new FloatProperty("PathX") { // from class: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsPropertyAnimator.1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(PhysicsPropertyAnimator.this.mCurrentPointOnPath.x);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                PhysicsPropertyAnimator.this.mCurrentPointOnPath.x = f;
            }
        };
        public final C38392 mCurrentPointOnPathYProperty = new FloatProperty("PathY") { // from class: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsPropertyAnimator.2
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(PhysicsPropertyAnimator.this.mCurrentPointOnPath.y);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                PhysicsPropertyAnimator.this.mCurrentPointOnPath.y = f;
            }
        };

        /* JADX WARN: Type inference failed for: r3v8, types: [com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$PhysicsPropertyAnimator$1] */
        /* JADX WARN: Type inference failed for: r3v9, types: [com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$PhysicsPropertyAnimator$2] */
        public PhysicsPropertyAnimator(View view) {
            this.mView = view;
        }

        public final void animateValueForChild(DynamicAnimation.ViewProperty viewProperty, View view, final float f, final float f2, long j, final float f3, final float f4, final Runnable... runnableArr) {
            if (view != null) {
                int i = PhysicsAnimationLayout.$r8$clinit;
                PhysicsAnimationLayout physicsAnimationLayout = PhysicsAnimationLayout.this;
                physicsAnimationLayout.getClass();
                final SpringAnimation springAnimation = (SpringAnimation) view.getTag(PhysicsAnimationLayout.getTagIdForProperty(viewProperty));
                if (springAnimation == null) {
                    return;
                }
                if (runnableArr != null) {
                    springAnimation.addEndListener(new OneTimeEndListener(this) { // from class: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsPropertyAnimator.4
                        @Override // com.android.p038wm.shell.bubbles.animation.OneTimeEndListener, androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                        public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f5, float f6) {
                            dynamicAnimation.removeEndListener(this);
                            for (Runnable runnable : runnableArr) {
                                runnable.run();
                            }
                        }
                    });
                }
                final SpringForce springForce = springAnimation.mSpring;
                if (springForce == null) {
                    return;
                }
                Runnable runnable = new Runnable() { // from class: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        SpringForce springForce2 = SpringForce.this;
                        float f5 = f3;
                        float f6 = f4;
                        float f7 = f2;
                        SpringAnimation springAnimation2 = springAnimation;
                        float f8 = f;
                        springForce2.setStiffness(f5);
                        springForce2.setDampingRatio(f6);
                        if (f7 > -3.4028235E38f) {
                            springAnimation2.mVelocity = f7;
                        }
                        springForce2.mFinalPosition = f8;
                        springAnimation2.start();
                    }
                };
                if (j > 0) {
                    physicsAnimationLayout.postDelayed(runnable, j);
                } else {
                    runnable.run();
                }
            }
        }

        public final void clearAnimator() {
            ((HashMap) this.mInitialPropertyValues).clear();
            ((HashMap) this.mAnimatedProperties).clear();
            ((HashMap) this.mPositionStartVelocities).clear();
            this.mDefaultStartVelocity = -3.4028235E38f;
            this.mStartDelay = 0L;
            this.mStiffness = -1.0f;
            this.mDampingRatio = -1.0f;
            ((HashMap) this.mEndActionsForProperty).clear();
            this.mPathAnimator = null;
            this.mPositionEndActions = null;
        }

        public final void property(DynamicAnimation.ViewProperty viewProperty, float f, Runnable... runnableArr) {
            ((HashMap) this.mAnimatedProperties).put(viewProperty, Float.valueOf(f));
            ((HashMap) this.mEndActionsForProperty).put(viewProperty, runnableArr);
        }

        public final void start(Runnable... runnableArr) {
            PhysicsAnimationController physicsAnimationController = this.mAssociatedController;
            PhysicsAnimationLayout physicsAnimationLayout = PhysicsAnimationLayout.this;
            if (!(physicsAnimationLayout.mController == physicsAnimationController)) {
                Log.w("Bubbs.PAL", "Only the active animation controller is allowed to start animations. Use PhysicsAnimationLayout#setActiveController to set the active animation controller.");
                return;
            }
            Map map = this.mAnimatedProperties;
            HashSet hashSet = new HashSet(((HashMap) map).keySet());
            if (this.mPathAnimator != null) {
                hashSet.add(DynamicAnimation.TRANSLATION_X);
                hashSet.add(DynamicAnimation.TRANSLATION_Y);
            }
            if (runnableArr.length > 0) {
                DynamicAnimation.ViewProperty[] viewPropertyArr = (DynamicAnimation.ViewProperty[]) hashSet.toArray(new DynamicAnimation.ViewProperty[0]);
                PhysicsAnimationController physicsAnimationController2 = this.mAssociatedController;
                RunnableC3834x4b8fea75 runnableC3834x4b8fea75 = new RunnableC3834x4b8fea75(runnableArr, 0);
                physicsAnimationController2.getClass();
                RunnableC3835x4b8fea76 runnableC3835x4b8fea76 = new RunnableC3835x4b8fea76(physicsAnimationController2, viewPropertyArr, 1, runnableC3834x4b8fea75);
                for (DynamicAnimation.ViewProperty viewProperty : viewPropertyArr) {
                    physicsAnimationController2.mLayout.mEndActionForProperty.put(viewProperty, runnableC3835x4b8fea76);
                }
            }
            Runnable[] runnableArr2 = this.mPositionEndActions;
            Map map2 = this.mEndActionsForProperty;
            View view = this.mView;
            if (runnableArr2 != null) {
                DynamicAnimation.C01841 c01841 = DynamicAnimation.TRANSLATION_X;
                SpringAnimation springAnimationFromView = PhysicsAnimationLayout.getSpringAnimationFromView(c01841, view);
                DynamicAnimation.C01912 c01912 = DynamicAnimation.TRANSLATION_Y;
                RunnableC3835x4b8fea76 runnableC3835x4b8fea762 = new RunnableC3835x4b8fea76(this, springAnimationFromView, 0, PhysicsAnimationLayout.getSpringAnimationFromView(c01912, view));
                HashMap hashMap = (HashMap) map2;
                hashMap.put(c01841, new Runnable[]{runnableC3835x4b8fea762});
                hashMap.put(c01912, new Runnable[]{runnableC3835x4b8fea762});
            }
            if (this.mPathAnimator != null) {
                PhysicsAnimationController physicsAnimationController3 = physicsAnimationLayout.mController;
                DynamicAnimation.C01841 c018412 = DynamicAnimation.TRANSLATION_X;
                final SpringForce springForce = physicsAnimationController3.getSpringForce();
                final SpringForce springForce2 = physicsAnimationLayout.mController.getSpringForce();
                long j = this.mStartDelay;
                if (j > 0) {
                    this.mPathAnimator.setStartDelay(j);
                }
                final RunnableC3834x4b8fea75 runnableC3834x4b8fea752 = new RunnableC3834x4b8fea75(this, 2);
                this.mPathAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        runnableC3834x4b8fea752.run();
                    }
                });
                this.mPathAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsPropertyAnimator.5
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        runnableC3834x4b8fea752.run();
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        PhysicsPropertyAnimator physicsPropertyAnimator = PhysicsPropertyAnimator.this;
                        DynamicAnimation.C01841 c018413 = DynamicAnimation.TRANSLATION_X;
                        View view2 = physicsPropertyAnimator.mView;
                        float f = physicsPropertyAnimator.mCurrentPointOnPath.x;
                        float f2 = physicsPropertyAnimator.mDefaultStartVelocity;
                        float f3 = physicsPropertyAnimator.mStiffness;
                        if (f3 < 0.0f) {
                            double d = springForce.mNaturalFreq;
                            f3 = (float) (d * d);
                        }
                        float f4 = physicsPropertyAnimator.mDampingRatio;
                        if (f4 < 0.0f) {
                            f4 = (float) springForce.mDampingRatio;
                        }
                        physicsPropertyAnimator.animateValueForChild(c018413, view2, f, f2, 0L, f3, f4, new Runnable[0]);
                        PhysicsPropertyAnimator physicsPropertyAnimator2 = PhysicsPropertyAnimator.this;
                        DynamicAnimation.C01912 c019122 = DynamicAnimation.TRANSLATION_Y;
                        View view3 = physicsPropertyAnimator2.mView;
                        float f5 = physicsPropertyAnimator2.mCurrentPointOnPath.y;
                        float f6 = physicsPropertyAnimator2.mDefaultStartVelocity;
                        float f7 = physicsPropertyAnimator2.mStiffness;
                        if (f7 < 0.0f) {
                            double d2 = springForce2.mNaturalFreq;
                            f7 = (float) (d2 * d2);
                        }
                        float f8 = f7;
                        float f9 = physicsPropertyAnimator2.mDampingRatio;
                        physicsPropertyAnimator2.animateValueForChild(c019122, view3, f5, f6, 0L, f8, f9 >= 0.0f ? f9 : (float) springForce2.mDampingRatio, new Runnable[0]);
                    }
                });
                ObjectAnimator objectAnimator = view == null ? null : (ObjectAnimator) view.getTag(R.id.target_animator_tag);
                if (objectAnimator != null) {
                    objectAnimator.cancel();
                }
                view.setTag(R.id.target_animator_tag, this.mPathAnimator);
                this.mPathAnimator.start();
            }
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                DynamicAnimation.ViewProperty viewProperty2 = (DynamicAnimation.ViewProperty) it.next();
                if (this.mPathAnimator != null && (viewProperty2.equals(DynamicAnimation.TRANSLATION_X) || viewProperty2.equals(DynamicAnimation.TRANSLATION_Y))) {
                    return;
                }
                HashMap hashMap2 = (HashMap) this.mInitialPropertyValues;
                if (hashMap2.containsKey(viewProperty2)) {
                    viewProperty2.setValue(view, ((Float) hashMap2.get(viewProperty2)).floatValue());
                }
                SpringForce springForce3 = physicsAnimationLayout.mController.getSpringForce();
                View view2 = this.mView;
                float floatValue = ((Float) ((HashMap) map).get(viewProperty2)).floatValue();
                float floatValue2 = ((Float) ((HashMap) this.mPositionStartVelocities).getOrDefault(viewProperty2, Float.valueOf(this.mDefaultStartVelocity))).floatValue();
                long j2 = this.mStartDelay;
                float f = this.mStiffness;
                if (f < 0.0f) {
                    double d = springForce3.mNaturalFreq;
                    f = (float) (d * d);
                }
                float f2 = this.mDampingRatio;
                if (f2 < 0.0f) {
                    f2 = (float) springForce3.mDampingRatio;
                }
                animateValueForChild(viewProperty2, view2, floatValue, floatValue2, j2, f, f2, (Runnable[]) ((HashMap) map2).get(viewProperty2));
            }
            clearAnimator();
        }

        public final void translationY(float f, Runnable... runnableArr) {
            this.mPathAnimator = null;
            property(DynamicAnimation.TRANSLATION_Y, f, runnableArr);
        }

        public final void updateValueForChild(DynamicAnimation.ViewProperty viewProperty, View view, float f) {
            SpringForce springForce;
            if (view != null) {
                int i = PhysicsAnimationLayout.$r8$clinit;
                PhysicsAnimationLayout.this.getClass();
                SpringAnimation springAnimation = (SpringAnimation) view.getTag(PhysicsAnimationLayout.getTagIdForProperty(viewProperty));
                if (springAnimation == null || (springForce = springAnimation.mSpring) == null) {
                    return;
                }
                springForce.mFinalPosition = f;
                springAnimation.start();
            }
        }
    }

    public PhysicsAnimationLayout(Context context) {
        super(context);
        this.mEndActionForProperty = new HashMap();
    }

    public static boolean arePropertiesAnimatingOnView(View view, DynamicAnimation.ViewProperty... viewPropertyArr) {
        ObjectAnimator objectAnimator = view == null ? null : (ObjectAnimator) view.getTag(R.id.target_animator_tag);
        for (DynamicAnimation.ViewProperty viewProperty : viewPropertyArr) {
            SpringAnimation springAnimationFromView = getSpringAnimationFromView(viewProperty, view);
            if (springAnimationFromView != null && springAnimationFromView.mRunning) {
                return true;
            }
            if ((viewProperty.equals(DynamicAnimation.TRANSLATION_X) || viewProperty.equals(DynamicAnimation.TRANSLATION_Y)) && objectAnimator != null && objectAnimator.isRunning()) {
                return true;
            }
        }
        return false;
    }

    public static String getReadablePropertyName(DynamicAnimation.ViewProperty viewProperty) {
        return viewProperty.equals(DynamicAnimation.TRANSLATION_X) ? "TRANSLATION_X" : viewProperty.equals(DynamicAnimation.TRANSLATION_Y) ? "TRANSLATION_Y" : viewProperty.equals(DynamicAnimation.SCALE_X) ? "SCALE_X" : viewProperty.equals(DynamicAnimation.SCALE_Y) ? "SCALE_Y" : viewProperty.equals(DynamicAnimation.ALPHA) ? "ALPHA" : "Unknown animation property.";
    }

    public static SpringAnimation getSpringAnimationFromView(DynamicAnimation.ViewProperty viewProperty, View view) {
        if (view == null) {
            return null;
        }
        return (SpringAnimation) view.getTag(getTagIdForProperty(viewProperty));
    }

    public static int getTagIdForProperty(DynamicAnimation.ViewProperty viewProperty) {
        if (viewProperty.equals(DynamicAnimation.TRANSLATION_X)) {
            return R.id.translation_x_dynamicanimation_tag;
        }
        if (viewProperty.equals(DynamicAnimation.TRANSLATION_Y)) {
            return R.id.translation_y_dynamicanimation_tag;
        }
        if (viewProperty.equals(DynamicAnimation.SCALE_X)) {
            return R.id.scale_x_dynamicanimation_tag;
        }
        if (viewProperty.equals(DynamicAnimation.SCALE_Y)) {
            return R.id.scale_y_dynamicanimation_tag;
        }
        if (viewProperty.equals(DynamicAnimation.ALPHA)) {
            return R.id.alpha_dynamicanimation_tag;
        }
        return -1;
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        addViewInternal(view, i, layoutParams, false);
    }

    public final void addViewInternal(View view, int i, ViewGroup.LayoutParams layoutParams, boolean z) {
        if (view != null) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            removeTransientView(view);
        }
        super.addView(view, i, layoutParams);
        PhysicsAnimationController physicsAnimationController = this.mController;
        if (physicsAnimationController == null || z) {
            return;
        }
        Iterator it = physicsAnimationController.getAnimatedProperties().iterator();
        while (it.hasNext()) {
            setUpAnimationForChild((DynamicAnimation.ViewProperty) it.next(), view);
        }
        this.mController.onChildAdded(view, i);
    }

    public final void cancelAllAnimationsOfProperties(DynamicAnimation.ViewProperty... viewPropertyArr) {
        if (this.mController == null) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            for (DynamicAnimation.ViewProperty viewProperty : viewPropertyArr) {
                SpringAnimation springAnimationFromView = getSpringAnimationFromView(viewProperty, getChildAt(i));
                if (springAnimationFromView != null) {
                    springAnimationFromView.cancel();
                }
            }
            View childAt = getChildAt(i);
            ViewPropertyAnimator viewPropertyAnimator = childAt == null ? null : (ViewPropertyAnimator) childAt.getTag(R.id.reorder_animator_tag);
            if (viewPropertyAnimator != null) {
                viewPropertyAnimator.cancel();
            }
        }
    }

    public final void cancelAnimationsOnView(View view) {
        ObjectAnimator objectAnimator = view == null ? null : (ObjectAnimator) view.getTag(R.id.target_animator_tag);
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        Iterator it = this.mController.getAnimatedProperties().iterator();
        while (it.hasNext()) {
            SpringAnimation springAnimationFromView = getSpringAnimationFromView((DynamicAnimation.ViewProperty) it.next(), view);
            if (springAnimationFromView != null) {
                springAnimationFromView.cancel();
            }
        }
    }

    public final boolean isFirstChildXLeftOfCenter(float f) {
        return getChildCount() > 0 && f + ((float) (getChildAt(0).getWidth() / 2)) < ((float) (getWidth() / 2));
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void removeView(View view) {
        if (this.mController == null) {
            super.removeView(view);
            return;
        }
        int indexOfChild = indexOfChild(view);
        super.removeView(view);
        addTransientView(view, indexOfChild);
        this.mController.onChildRemoved(view, new PhysicsAnimationLayout$$ExternalSyntheticLambda1(this, view));
    }

    @Override // android.view.ViewGroup
    public final void removeViewAt(int i) {
        removeView(getChildAt(i));
    }

    public final void reorderView(BadgedImageView badgedImageView, int i) {
        if (badgedImageView == null) {
            return;
        }
        indexOfChild(badgedImageView);
        super.removeView(badgedImageView);
        if (badgedImageView.getParent() != null) {
            removeTransientView(badgedImageView);
        }
        addViewInternal(badgedImageView, i, badgedImageView.getLayoutParams(), true);
        PhysicsAnimationController physicsAnimationController = this.mController;
        if (physicsAnimationController != null) {
            physicsAnimationController.onChildReordered();
        }
    }

    public final void setActiveController(PhysicsAnimationController physicsAnimationController) {
        PhysicsAnimationController physicsAnimationController2 = this.mController;
        if (physicsAnimationController2 != null) {
            cancelAllAnimationsOfProperties((DynamicAnimation.ViewProperty[]) physicsAnimationController2.getAnimatedProperties().toArray(new DynamicAnimation.ViewProperty[0]));
        }
        this.mEndActionForProperty.clear();
        this.mController = physicsAnimationController;
        physicsAnimationController.mLayout = this;
        physicsAnimationController.onActiveControllerForLayout(this);
        for (DynamicAnimation.ViewProperty viewProperty : this.mController.getAnimatedProperties()) {
            for (int i = 0; i < getChildCount(); i++) {
                setUpAnimationForChild(viewProperty, getChildAt(i));
            }
        }
    }

    public final void setUpAnimationForChild(final DynamicAnimation.ViewProperty viewProperty, final View view) {
        SpringAnimation springAnimation = new SpringAnimation(view, viewProperty);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$$ExternalSyntheticLambda0
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                SpringAnimation springAnimationFromView;
                View view2 = view;
                PhysicsAnimationLayout physicsAnimationLayout = this;
                int indexOfChild = physicsAnimationLayout.indexOfChild(view2);
                PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController = physicsAnimationLayout.mController;
                DynamicAnimation.ViewProperty viewProperty2 = viewProperty;
                int nextAnimationInChain = physicsAnimationController.getNextAnimationInChain(viewProperty2, indexOfChild);
                if (nextAnimationInChain == -1 || indexOfChild < 0) {
                    return;
                }
                float offsetForChainedPropertyAnimation = physicsAnimationLayout.mController.getOffsetForChainedPropertyAnimation(viewProperty2, nextAnimationInChain);
                if (nextAnimationInChain >= physicsAnimationLayout.getChildCount() || (springAnimationFromView = PhysicsAnimationLayout.getSpringAnimationFromView(viewProperty2, physicsAnimationLayout.getChildAt(nextAnimationInChain))) == null) {
                    return;
                }
                springAnimationFromView.animateToFinalPosition(f + offsetForChainedPropertyAnimation);
            }
        });
        springAnimation.mSpring = this.mController.getSpringForce();
        springAnimation.addEndListener(new AllAnimationsForPropertyFinishedEndListener(viewProperty));
        view.setTag(getTagIdForProperty(viewProperty), springAnimation);
    }
}

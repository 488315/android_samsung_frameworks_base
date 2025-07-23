package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.TransformationCallback;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton.TransformationCallbackWrapper;
import com.google.android.material.floatingactionbutton.FloatingActionButtonImpl;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BottomAppBar extends Toolbar implements CoordinatorLayout.AttachedBehavior {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Behavior behavior;
    public int bottomInset;
    public int fabAlignmentMode;
    public final int fabAlignmentModeEndMargin;
    public final int fabAnchorMode;
    public final AnonymousClass1 fabAnimationListener;
    public boolean fabAttached;
    public final int fabOffsetEndMode;
    public final AnonymousClass2 fabTransformationCallback;
    public final boolean hideOnScroll;
    public int leftInset;
    public final MaterialShapeDrawable materialShapeDrawable;
    public final int menuAlignmentMode;
    public Animator menuAnimator;
    public final Integer navigationIconTint;
    public final boolean paddingBottomSystemWindowInsets;
    public final boolean paddingLeftSystemWindowInsets;
    public final boolean paddingRightSystemWindowInsets;
    public final boolean removeEmbeddedFabElevation;
    public int rightInset;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.android.material.bottomappbar.BottomAppBar$1, reason: invalid class name */
    public class AnonymousClass1 extends AnimatorListenerAdapter {
        public AnonymousClass1() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            final ActionMenuView actionMenuView;
            final BottomAppBar bottomAppBar = BottomAppBar.this;
            bottomAppBar.getClass();
            final int i = bottomAppBar.fabAlignmentMode;
            final boolean z = bottomAppBar.fabAttached;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (bottomAppBar.isLaidOut()) {
                Animator animator2 = bottomAppBar.menuAnimator;
                if (animator2 != null) {
                    animator2.cancel();
                }
                ArrayList arrayList = new ArrayList();
                if (!bottomAppBar.isFabVisibleOrWillBeShown()) {
                    i = 0;
                    z = false;
                }
                int i2 = 0;
                while (true) {
                    if (i2 >= bottomAppBar.getChildCount()) {
                        actionMenuView = null;
                        break;
                    }
                    View childAt = bottomAppBar.getChildAt(i2);
                    if (childAt instanceof ActionMenuView) {
                        actionMenuView = (ActionMenuView) childAt;
                        break;
                    }
                    i2++;
                }
                if (actionMenuView != null) {
                    float resolveThemeDuration = MotionUtils.resolveThemeDuration(bottomAppBar.getContext(), R.attr.motionDurationLong2, 300);
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(actionMenuView, "alpha", 1.0f);
                    ofFloat.setDuration((long) (0.8f * resolveThemeDuration));
                    if (Math.abs(actionMenuView.getTranslationX() - bottomAppBar.getActionMenuViewTranslationX(actionMenuView, i, z)) > 1.0f) {
                        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(actionMenuView, "alpha", 0.0f);
                        ofFloat2.setDuration((long) (resolveThemeDuration * 0.2f));
                        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.7
                            public boolean cancelled;

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationCancel(Animator animator3) {
                                this.cancelled = true;
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator3) {
                                if (this.cancelled) {
                                    return;
                                }
                                BottomAppBar.this.getClass();
                                BottomAppBar.this.getClass();
                                BottomAppBar.this.translateActionMenuView(actionMenuView, i, z);
                            }
                        });
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playSequentially(ofFloat2, ofFloat);
                        arrayList.add(animatorSet);
                    } else if (actionMenuView.getAlpha() < 1.0f) {
                        arrayList.add(ofFloat);
                    }
                }
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(arrayList);
                bottomAppBar.menuAnimator = animatorSet2;
                animatorSet2.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.6
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator3) {
                        BottomAppBar bottomAppBar2 = BottomAppBar.this;
                        bottomAppBar2.getClass();
                        bottomAppBar2.menuAnimator = null;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator3) {
                        BottomAppBar.this.getClass();
                    }
                });
                bottomAppBar.menuAnimator.start();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.android.material.bottomappbar.BottomAppBar$2, reason: invalid class name */
    public class AnonymousClass2 implements TransformationCallback {
        public AnonymousClass2() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: com.google.android.material.bottomappbar.BottomAppBar.SavedState.1
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        };
        public int fabAlignmentMode;
        public boolean fabAttached;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.fabAlignmentMode);
            parcel.writeInt(this.fabAttached ? 1 : 0);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.fabAlignmentMode = parcel.readInt();
            this.fabAttached = parcel.readInt() != 0;
        }
    }

    public BottomAppBar(Context context) {
        this(context, null);
    }

    public final View findDependentView() {
        if (getParent() instanceof CoordinatorLayout) {
            ArrayList arrayList = (ArrayList) ((CoordinatorLayout) getParent()).mChildDag.mGraph.get(this);
            List<View> arrayList2 = arrayList == null ? null : new ArrayList(arrayList);
            if (arrayList2 == null) {
                arrayList2 = Collections.EMPTY_LIST;
            }
            for (View view : arrayList2) {
                if ((view instanceof FloatingActionButton) || (view instanceof ExtendedFloatingActionButton)) {
                    return view;
                }
            }
        }
        return null;
    }

    public final int getActionMenuViewTranslationX(ActionMenuView actionMenuView, int i, boolean z) {
        int i2 = 0;
        if (this.menuAlignmentMode != 1 && (i != 1 || !z)) {
            return 0;
        }
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int measuredWidth = isLayoutRtl ? getMeasuredWidth() : 0;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            View childAt = getChildAt(i3);
            if ((childAt.getLayoutParams() instanceof Toolbar.LayoutParams) && (((Toolbar.LayoutParams) childAt.getLayoutParams()).gravity & 8388615) == 8388611) {
                measuredWidth = isLayoutRtl ? Math.min(measuredWidth, childAt.getLeft()) : Math.max(measuredWidth, childAt.getRight());
            }
        }
        int right = isLayoutRtl ? actionMenuView.getRight() : actionMenuView.getLeft();
        int i4 = isLayoutRtl ? this.rightInset : -this.leftInset;
        if (getNavigationIcon() == null) {
            int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.m3_bottomappbar_horizontal_padding);
            if (!isLayoutRtl) {
                dimensionPixelOffset = -dimensionPixelOffset;
            }
            i2 = dimensionPixelOffset;
        }
        return measuredWidth - ((right + i4) + i2);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    public final CoordinatorLayout.Behavior getBehavior() {
        if (this.behavior == null) {
            this.behavior = new Behavior();
        }
        return this.behavior;
    }

    public final float getFabTranslationX() {
        int i = this.fabAlignmentMode;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        if (i != 1) {
            return 0.0f;
        }
        View findDependentView = findDependentView();
        int i2 = isLayoutRtl ? this.leftInset : this.rightInset;
        return ((getMeasuredWidth() / 2) - ((this.fabAlignmentModeEndMargin == -1 || findDependentView == null) ? this.fabOffsetEndMode + i2 : ((findDependentView.getMeasuredWidth() / 2) + this.fabAlignmentModeEndMargin) + i2)) * (isLayoutRtl ? -1 : 1);
    }

    public final BottomAppBarTopEdgeTreatment getTopEdgeTreatment() {
        return (BottomAppBarTopEdgeTreatment) this.materialShapeDrawable.drawableState.shapeAppearanceModel.topEdge;
    }

    public final boolean isFabVisibleOrWillBeShown() {
        View findDependentView = findDependentView();
        FloatingActionButton floatingActionButton = findDependentView instanceof FloatingActionButton ? (FloatingActionButton) findDependentView : null;
        if (floatingActionButton == null) {
            return false;
        }
        FloatingActionButtonImpl impl = floatingActionButton.getImpl();
        if (impl.view.getVisibility() != 0) {
            if (impl.animState != 2) {
                return false;
            }
        } else if (impl.animState == 1) {
            return false;
        }
        return true;
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.materialShapeDrawable);
        if (getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).setClipChildren(false);
        }
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            Animator animator = this.menuAnimator;
            if (animator != null) {
                animator.cancel();
            }
            setCutoutStateAndTranslateFab();
            final View findDependentView = findDependentView();
            if (findDependentView != null) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (findDependentView.isLaidOut()) {
                    findDependentView.post(new Runnable() { // from class: com.google.android.material.bottomappbar.BottomAppBar$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            View view = findDependentView;
                            int i5 = BottomAppBar.$r8$clinit;
                            view.requestLayout();
                        }
                    });
                }
            }
        }
        setActionMenuViewPosition();
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        this.fabAlignmentMode = savedState.fabAlignmentMode;
        this.fabAttached = savedState.fabAttached;
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.fabAlignmentMode = this.fabAlignmentMode;
        savedState.fabAttached = this.fabAttached;
        return savedState;
    }

    public final void setActionMenuViewPosition() {
        ActionMenuView actionMenuView;
        int i = 0;
        while (true) {
            if (i >= getChildCount()) {
                actionMenuView = null;
                break;
            }
            View childAt = getChildAt(i);
            if (childAt instanceof ActionMenuView) {
                actionMenuView = (ActionMenuView) childAt;
                break;
            }
            i++;
        }
        if (actionMenuView == null || this.menuAnimator != null) {
            return;
        }
        actionMenuView.setAlpha(1.0f);
        if (isFabVisibleOrWillBeShown()) {
            translateActionMenuView(actionMenuView, this.fabAlignmentMode, this.fabAttached);
        } else {
            translateActionMenuView(actionMenuView, 0, false);
        }
    }

    public final void setCutoutStateAndTranslateFab() {
        float f;
        getTopEdgeTreatment().horizontalOffset = getFabTranslationX();
        this.materialShapeDrawable.setInterpolation((this.fabAttached && isFabVisibleOrWillBeShown() && this.fabAnchorMode == 1) ? 1.0f : 0.0f);
        View findDependentView = findDependentView();
        if (findDependentView != null) {
            if (this.fabAnchorMode == 1) {
                f = -getTopEdgeTreatment().cradleVerticalOffset;
            } else {
                View findDependentView2 = findDependentView();
                f = findDependentView2 != null ? (-((getMeasuredHeight() + this.bottomInset) - findDependentView2.getMeasuredHeight())) / 2 : 0;
            }
            findDependentView.setTranslationY(f);
            findDependentView.setTranslationX(getFabTranslationX());
        }
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        this.materialShapeDrawable.setElevation(f);
        MaterialShapeDrawable materialShapeDrawable = this.materialShapeDrawable;
        int shadowOffsetY = materialShapeDrawable.drawableState.shadowCompatRadius - materialShapeDrawable.getShadowOffsetY();
        if (this.behavior == null) {
            this.behavior = new Behavior();
        }
        Behavior behavior = this.behavior;
        behavior.additionalHiddenOffsetY = shadowOffsetY;
        if (behavior.currentState == 1) {
            setTranslationY(behavior.height + shadowOffsetY);
        }
    }

    @Override // androidx.appcompat.widget.Toolbar
    public final void setNavigationIcon(Drawable drawable) {
        if (drawable != null && this.navigationIconTint != null) {
            drawable = drawable.mutate();
            drawable.setTint(this.navigationIconTint.intValue());
        }
        super.setNavigationIcon(drawable);
    }

    public final void translateActionMenuView(final ActionMenuView actionMenuView, final int i, final boolean z) {
        new Runnable() { // from class: com.google.android.material.bottomappbar.BottomAppBar.8
            @Override // java.lang.Runnable
            public final void run() {
                actionMenuView.setTranslationX(BottomAppBar.this.getActionMenuViewTranslationX(r0, i, z));
            }
        }.run();
    }

    public BottomAppBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.bottomAppBarStyle);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Behavior extends HideBottomViewOnScrollBehavior<BottomAppBar> {
        public final Rect fabContentRect;
        public final AnonymousClass1 fabLayoutListener;
        public int originalBottomMargin;
        public WeakReference viewRef;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.material.bottomappbar.BottomAppBar$Behavior$1] */
        public Behavior() {
            this.fabLayoutListener = new View.OnLayoutChangeListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.Behavior.1
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    BottomAppBar bottomAppBar = (BottomAppBar) Behavior.this.viewRef.get();
                    if (bottomAppBar == null || !((view instanceof FloatingActionButton) || (view instanceof ExtendedFloatingActionButton))) {
                        view.removeOnLayoutChangeListener(this);
                        return;
                    }
                    int height = view.getHeight();
                    if (view instanceof FloatingActionButton) {
                        FloatingActionButton floatingActionButton = (FloatingActionButton) view;
                        Rect rect = Behavior.this.fabContentRect;
                        rect.set(0, 0, floatingActionButton.getMeasuredWidth(), floatingActionButton.getMeasuredHeight());
                        floatingActionButton.offsetRectWithShadow(rect);
                        int height2 = Behavior.this.fabContentRect.height();
                        float f = height2;
                        if (f != bottomAppBar.getTopEdgeTreatment().fabDiameter) {
                            bottomAppBar.getTopEdgeTreatment().fabDiameter = f;
                            bottomAppBar.materialShapeDrawable.invalidateSelf();
                        }
                        ShapeAppearanceModel shapeAppearanceModel = floatingActionButton.getImpl().shapeAppearance;
                        shapeAppearanceModel.getClass();
                        float cornerSize = shapeAppearanceModel.topLeftCornerSize.getCornerSize(new RectF(Behavior.this.fabContentRect));
                        if (cornerSize != bottomAppBar.getTopEdgeTreatment().fabCornerSize) {
                            bottomAppBar.getTopEdgeTreatment().fabCornerSize = cornerSize;
                            bottomAppBar.materialShapeDrawable.invalidateSelf();
                        }
                        height = height2;
                    }
                    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
                    if (Behavior.this.originalBottomMargin == 0) {
                        if (bottomAppBar.fabAnchorMode == 1) {
                            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = bottomAppBar.bottomInset + (bottomAppBar.getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fab_bottom_margin) - ((view.getMeasuredHeight() - height) / 2));
                        }
                        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = bottomAppBar.leftInset;
                        ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = bottomAppBar.rightInset;
                        if (ViewUtils.isLayoutRtl(view)) {
                            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin += bottomAppBar.fabOffsetEndMode;
                        } else {
                            ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin += bottomAppBar.fabOffsetEndMode;
                        }
                    }
                    int i9 = BottomAppBar.$r8$clinit;
                    bottomAppBar.setCutoutStateAndTranslateFab();
                }
            };
            this.fabContentRect = new Rect();
        }

        @Override // com.google.android.material.behavior.HideBottomViewOnScrollBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            final BottomAppBar bottomAppBar = (BottomAppBar) view;
            this.viewRef = new WeakReference(bottomAppBar);
            int i2 = BottomAppBar.$r8$clinit;
            View findDependentView = bottomAppBar.findDependentView();
            if (findDependentView != null) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (!findDependentView.isLaidOut()) {
                    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) findDependentView.getLayoutParams();
                    layoutParams.anchorGravity = 17;
                    int i3 = bottomAppBar.fabAnchorMode;
                    if (i3 == 1) {
                        layoutParams.anchorGravity = 49;
                    }
                    if (i3 == 0) {
                        layoutParams.anchorGravity |= 80;
                    }
                    this.originalBottomMargin = ((ViewGroup.MarginLayoutParams) ((CoordinatorLayout.LayoutParams) findDependentView.getLayoutParams())).bottomMargin;
                    if (findDependentView instanceof FloatingActionButton) {
                        FloatingActionButton floatingActionButton = (FloatingActionButton) findDependentView;
                        if (bottomAppBar.fabAnchorMode == 0 && bottomAppBar.removeEmbeddedFabElevation) {
                            ViewCompat.Api21Impl.setElevation(floatingActionButton, 0.0f);
                            FloatingActionButtonImpl impl = floatingActionButton.getImpl();
                            if (impl.elevation != 0.0f) {
                                impl.elevation = 0.0f;
                                impl.onElevationsChanged(0.0f, impl.hoveredFocusedTranslationZ, impl.pressedTranslationZ);
                            }
                        }
                        if (floatingActionButton.getImpl().showMotionSpec == null) {
                            floatingActionButton.getImpl().showMotionSpec = MotionSpec.createFromResource(R.animator.mtrl_fab_show_motion_spec, floatingActionButton.getContext());
                        }
                        if (floatingActionButton.getImpl().hideMotionSpec == null) {
                            floatingActionButton.getImpl().hideMotionSpec = MotionSpec.createFromResource(R.animator.mtrl_fab_hide_motion_spec, floatingActionButton.getContext());
                        }
                        AnonymousClass1 anonymousClass1 = bottomAppBar.fabAnimationListener;
                        FloatingActionButtonImpl impl2 = floatingActionButton.getImpl();
                        if (impl2.hideListeners == null) {
                            impl2.hideListeners = new ArrayList();
                        }
                        impl2.hideListeners.add(anonymousClass1);
                        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.9
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                                BottomAppBar.this.fabAnimationListener.onAnimationStart(animator);
                                View findDependentView2 = BottomAppBar.this.findDependentView();
                                FloatingActionButton floatingActionButton2 = findDependentView2 instanceof FloatingActionButton ? (FloatingActionButton) findDependentView2 : null;
                                if (floatingActionButton2 != null) {
                                    floatingActionButton2.setTranslationX(BottomAppBar.this.getFabTranslationX());
                                }
                            }
                        };
                        FloatingActionButtonImpl impl3 = floatingActionButton.getImpl();
                        if (impl3.showListeners == null) {
                            impl3.showListeners = new ArrayList();
                        }
                        impl3.showListeners.add(animatorListenerAdapter);
                        AnonymousClass2 anonymousClass2 = bottomAppBar.fabTransformationCallback;
                        FloatingActionButtonImpl impl4 = floatingActionButton.getImpl();
                        FloatingActionButton.TransformationCallbackWrapper transformationCallbackWrapper = floatingActionButton.new TransformationCallbackWrapper(anonymousClass2);
                        if (impl4.transformationCallbacks == null) {
                            impl4.transformationCallbacks = new ArrayList();
                        }
                        impl4.transformationCallbacks.add(transformationCallbackWrapper);
                    }
                    findDependentView.addOnLayoutChangeListener(this.fabLayoutListener);
                    bottomAppBar.setCutoutStateAndTranslateFab();
                }
            }
            coordinatorLayout.onLayoutChild(bottomAppBar, i);
            super.onLayoutChild(coordinatorLayout, bottomAppBar, i);
            return false;
        }

        @Override // com.google.android.material.behavior.HideBottomViewOnScrollBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
            BottomAppBar bottomAppBar = (BottomAppBar) view;
            return bottomAppBar.hideOnScroll && super.onStartNestedScroll(coordinatorLayout, bottomAppBar, view2, view3, i, i2);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [com.google.android.material.bottomappbar.BottomAppBar$Behavior$1] */
        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.fabLayoutListener = new View.OnLayoutChangeListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.Behavior.1
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    BottomAppBar bottomAppBar = (BottomAppBar) Behavior.this.viewRef.get();
                    if (bottomAppBar == null || !((view instanceof FloatingActionButton) || (view instanceof ExtendedFloatingActionButton))) {
                        view.removeOnLayoutChangeListener(this);
                        return;
                    }
                    int height = view.getHeight();
                    if (view instanceof FloatingActionButton) {
                        FloatingActionButton floatingActionButton = (FloatingActionButton) view;
                        Rect rect = Behavior.this.fabContentRect;
                        rect.set(0, 0, floatingActionButton.getMeasuredWidth(), floatingActionButton.getMeasuredHeight());
                        floatingActionButton.offsetRectWithShadow(rect);
                        int height2 = Behavior.this.fabContentRect.height();
                        float f = height2;
                        if (f != bottomAppBar.getTopEdgeTreatment().fabDiameter) {
                            bottomAppBar.getTopEdgeTreatment().fabDiameter = f;
                            bottomAppBar.materialShapeDrawable.invalidateSelf();
                        }
                        ShapeAppearanceModel shapeAppearanceModel = floatingActionButton.getImpl().shapeAppearance;
                        shapeAppearanceModel.getClass();
                        float cornerSize = shapeAppearanceModel.topLeftCornerSize.getCornerSize(new RectF(Behavior.this.fabContentRect));
                        if (cornerSize != bottomAppBar.getTopEdgeTreatment().fabCornerSize) {
                            bottomAppBar.getTopEdgeTreatment().fabCornerSize = cornerSize;
                            bottomAppBar.materialShapeDrawable.invalidateSelf();
                        }
                        height = height2;
                    }
                    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
                    if (Behavior.this.originalBottomMargin == 0) {
                        if (bottomAppBar.fabAnchorMode == 1) {
                            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = bottomAppBar.bottomInset + (bottomAppBar.getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fab_bottom_margin) - ((view.getMeasuredHeight() - height) / 2));
                        }
                        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = bottomAppBar.leftInset;
                        ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = bottomAppBar.rightInset;
                        if (ViewUtils.isLayoutRtl(view)) {
                            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin += bottomAppBar.fabOffsetEndMode;
                        } else {
                            ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin += bottomAppBar.fabOffsetEndMode;
                        }
                    }
                    int i9 = BottomAppBar.$r8$clinit;
                    bottomAppBar.setCutoutStateAndTranslateFab();
                }
            };
            this.fabContentRect = new Rect();
        }
    }

    public BottomAppBar(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, R.style.Widget_MaterialComponents_BottomAppBar), attributeSet, i);
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        this.materialShapeDrawable = materialShapeDrawable;
        this.fabAttached = true;
        this.fabAnimationListener = new AnonymousClass1();
        this.fabTransformationCallback = new AnonymousClass2();
        Context context2 = getContext();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.BottomAppBar, i, R.style.Widget_MaterialComponents_BottomAppBar, new int[0]);
        ColorStateList colorStateList = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 1);
        if (obtainStyledAttributes.hasValue(12)) {
            this.navigationIconTint = Integer.valueOf(obtainStyledAttributes.getColor(12, -1));
            Drawable navigationIcon = getNavigationIcon();
            if (navigationIcon != null) {
                setNavigationIcon(navigationIcon);
            }
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        float dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(7, 0);
        float dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(8, 0);
        float dimensionPixelOffset3 = obtainStyledAttributes.getDimensionPixelOffset(9, 0);
        this.fabAlignmentMode = obtainStyledAttributes.getInt(3, 0);
        obtainStyledAttributes.getInt(6, 0);
        this.fabAnchorMode = obtainStyledAttributes.getInt(5, 1);
        this.removeEmbeddedFabElevation = obtainStyledAttributes.getBoolean(16, true);
        this.menuAlignmentMode = obtainStyledAttributes.getInt(11, 0);
        this.hideOnScroll = obtainStyledAttributes.getBoolean(10, false);
        this.paddingBottomSystemWindowInsets = obtainStyledAttributes.getBoolean(13, false);
        this.paddingLeftSystemWindowInsets = obtainStyledAttributes.getBoolean(14, false);
        this.paddingRightSystemWindowInsets = obtainStyledAttributes.getBoolean(15, false);
        this.fabAlignmentModeEndMargin = obtainStyledAttributes.getDimensionPixelOffset(4, -1);
        boolean z = obtainStyledAttributes.getBoolean(0, true);
        obtainStyledAttributes.recycle();
        this.fabOffsetEndMode = getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fabOffsetEndMode);
        BottomAppBarTopEdgeTreatment bottomAppBarTopEdgeTreatment = new BottomAppBarTopEdgeTreatment(dimensionPixelOffset, dimensionPixelOffset2, dimensionPixelOffset3);
        RelativeCornerSize relativeCornerSize = ShapeAppearanceModel.PILL;
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder();
        builder.topEdge = bottomAppBarTopEdgeTreatment;
        materialShapeDrawable.setShapeAppearanceModel(builder.build());
        if (z) {
            materialShapeDrawable.setShadowCompatibilityMode(2);
        } else {
            materialShapeDrawable.setShadowCompatibilityMode(1);
            setOutlineAmbientShadowColor(0);
            setOutlineSpotShadowColor(0);
        }
        Paint.Style style = Paint.Style.FILL;
        materialShapeDrawable.setPaintStyle();
        materialShapeDrawable.initializeElevationOverlay(context2);
        setElevation(dimensionPixelSize);
        materialShapeDrawable.setTintList(colorStateList);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setBackground(materialShapeDrawable);
        ViewUtils.OnApplyWindowInsetsListener onApplyWindowInsetsListener = new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.3
            @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                boolean z2;
                BottomAppBar bottomAppBar = BottomAppBar.this;
                if (bottomAppBar.paddingBottomSystemWindowInsets) {
                    bottomAppBar.bottomInset = windowInsetsCompat.getSystemWindowInsetBottom();
                }
                boolean z3 = false;
                if (bottomAppBar.paddingLeftSystemWindowInsets) {
                    z2 = bottomAppBar.leftInset != windowInsetsCompat.getSystemWindowInsetLeft();
                    bottomAppBar.leftInset = windowInsetsCompat.getSystemWindowInsetLeft();
                } else {
                    z2 = false;
                }
                if (bottomAppBar.paddingRightSystemWindowInsets) {
                    boolean z4 = bottomAppBar.rightInset != windowInsetsCompat.getSystemWindowInsetRight();
                    bottomAppBar.rightInset = windowInsetsCompat.getSystemWindowInsetRight();
                    z3 = z4;
                }
                if (!z2 && !z3) {
                    return windowInsetsCompat;
                }
                Animator animator = bottomAppBar.menuAnimator;
                if (animator != null) {
                    animator.cancel();
                }
                bottomAppBar.setCutoutStateAndTranslateFab();
                bottomAppBar.setActionMenuViewPosition();
                return windowInsetsCompat;
            }
        };
        TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(attributeSet, R$styleable.Insets, i, R.style.Widget_MaterialComponents_BottomAppBar);
        boolean z2 = obtainStyledAttributes2.getBoolean(3, false);
        boolean z3 = obtainStyledAttributes2.getBoolean(4, false);
        boolean z4 = obtainStyledAttributes2.getBoolean(5, false);
        obtainStyledAttributes2.recycle();
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.internal.ViewUtils.1
            public final /* synthetic */ OnApplyWindowInsetsListener val$listener;
            public final /* synthetic */ boolean val$paddingBottomSystemWindowInsets;
            public final /* synthetic */ boolean val$paddingLeftSystemWindowInsets;
            public final /* synthetic */ boolean val$paddingRightSystemWindowInsets;

            public AnonymousClass1(boolean z22, boolean z32, boolean z42, OnApplyWindowInsetsListener onApplyWindowInsetsListener2) {
                r1 = z22;
                r2 = z32;
                r3 = z42;
                r4 = onApplyWindowInsetsListener2;
            }

            @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, RelativePadding relativePadding) {
                if (r1) {
                    relativePadding.bottom = windowInsetsCompat.getSystemWindowInsetBottom() + relativePadding.bottom;
                }
                boolean isLayoutRtl = ViewUtils.isLayoutRtl(view);
                if (r2) {
                    if (isLayoutRtl) {
                        relativePadding.end = windowInsetsCompat.getSystemWindowInsetLeft() + relativePadding.end;
                    } else {
                        relativePadding.start = windowInsetsCompat.getSystemWindowInsetLeft() + relativePadding.start;
                    }
                }
                if (r3) {
                    if (isLayoutRtl) {
                        relativePadding.start = windowInsetsCompat.getSystemWindowInsetRight() + relativePadding.start;
                    } else {
                        relativePadding.end = windowInsetsCompat.getSystemWindowInsetRight() + relativePadding.end;
                    }
                }
                int i2 = relativePadding.start;
                int i3 = relativePadding.end;
                int i4 = relativePadding.bottom;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                view.setPaddingRelative(i2, relativePadding.top, i3, i4);
                OnApplyWindowInsetsListener onApplyWindowInsetsListener2 = r4;
                return onApplyWindowInsetsListener2 != null ? onApplyWindowInsetsListener2.onApplyWindowInsets(view, windowInsetsCompat, relativePadding) : windowInsetsCompat;
            }
        });
    }

    @Override // androidx.appcompat.widget.Toolbar
    public final void setSubtitle(CharSequence charSequence) {
    }

    @Override // androidx.appcompat.widget.Toolbar
    public final void setTitle(CharSequence charSequence) {
    }
}

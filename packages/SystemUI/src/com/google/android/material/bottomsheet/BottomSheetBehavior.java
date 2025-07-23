package com.google.android.material.bottomsheet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.activity.BackEventCompat;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MaterialBackHandler;
import com.google.android.material.motion.MaterialBottomContainerBackHelper;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior implements MaterialBackHandler {
    static final int DEFAULT_SIGNIFICANT_VEL_THRESHOLD = 500;
    static final int VIEW_INDEX_ACCESSIBILITY_DELEGATE_VIEW = 1;
    public WeakReference accessibilityDelegateViewRef;
    public int activePointerId;
    public final ColorStateList backgroundTint;
    public MaterialBottomContainerBackHelper bottomContainerBackHelper;
    public final ArrayList callbacks;
    public int childHeight;
    public int collapsedOffset;
    public final AnonymousClass5 dragCallback;
    public final boolean draggable;
    public final float elevation;
    final SparseIntArray expandHalfwayActionIds;
    public boolean expandedCornersRemoved;
    public final int expandedOffset;
    public boolean fitToContents;
    public int fitToContentsOffset;
    public int gestureInsetBottom;
    public final boolean gestureInsetBottomIgnored;
    public int halfExpandedOffset;
    public final float halfExpandedRatio;
    public final float hideFriction;
    public boolean hideable;
    public boolean ignoreEvents;
    public Map importantForAccessibilityMap;
    public int initialY;
    public int insetBottom;
    public int insetTop;
    public ValueAnimator interpolatorAnimator;
    public int lastNestedScrollDy;
    public final boolean marginLeftSystemWindowInsets;
    public final boolean marginRightSystemWindowInsets;
    public final boolean marginTopSystemWindowInsets;
    public final MaterialShapeDrawable materialShapeDrawable;
    public final int maxHeight;
    public final int maxWidth;
    public final float maximumVelocity;
    public boolean nestedScrolled;
    public WeakReference nestedScrollingChildRef;
    public final boolean paddingBottomSystemWindowInsets;
    public final boolean paddingLeftSystemWindowInsets;
    public final boolean paddingRightSystemWindowInsets;
    public final boolean paddingTopSystemWindowInsets;
    public int parentHeight;
    public int parentWidth;
    public int peekHeight;
    public boolean peekHeightAuto;
    public final int peekHeightGestureInsetBuffer;
    public int peekHeightMin;
    public final int saveFlags;
    public final ShapeAppearanceModel shapeAppearanceModelDefault;
    public final boolean shouldRemoveExpandedCorners;
    public final int significantVelocityThreshold;
    public boolean skipCollapsed;
    public int state;
    public final StateSettlingTracker stateSettlingTracker;
    public boolean touchingScrollingChild;
    public VelocityTracker velocityTracker;
    public ViewDragHelper viewDragHelper;
    public WeakReference viewRef;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class BottomSheetCallback {
        public abstract void onStateChanged(int i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.SavedState.1
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
                return new SavedState(parcel, (ClassLoader) null);
            }
        };
        public final boolean fitToContents;
        public final boolean hideable;
        public final int peekHeight;
        public final boolean skipCollapsed;
        public final int state;

        public SavedState(Parcel parcel) {
            this(parcel, (ClassLoader) null);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.state);
            parcel.writeInt(this.peekHeight);
            parcel.writeInt(this.fitToContents ? 1 : 0);
            parcel.writeInt(this.hideable ? 1 : 0);
            parcel.writeInt(this.skipCollapsed ? 1 : 0);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
            this.peekHeight = parcel.readInt();
            this.fitToContents = parcel.readInt() == 1;
            this.hideable = parcel.readInt() == 1;
            this.skipCollapsed = parcel.readInt() == 1;
        }

        public SavedState(Parcelable parcelable, BottomSheetBehavior<?> bottomSheetBehavior) {
            super(parcelable);
            this.state = bottomSheetBehavior.state;
            this.peekHeight = bottomSheetBehavior.peekHeight;
            this.fitToContents = bottomSheetBehavior.fitToContents;
            this.hideable = bottomSheetBehavior.hideable;
            this.skipCollapsed = bottomSheetBehavior.skipCollapsed;
        }

        @Deprecated
        public SavedState(Parcelable parcelable, int i) {
            super(parcelable);
            this.state = i;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class StateSettlingTracker {
        public final AnonymousClass1 continueSettlingRunnable;
        public boolean isContinueSettlingRunnablePosted;
        public int targetState;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.google.android.material.bottomsheet.BottomSheetBehavior$StateSettlingTracker$1] */
        private StateSettlingTracker() {
            this.continueSettlingRunnable = new Runnable() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.StateSettlingTracker.1
                @Override // java.lang.Runnable
                public final void run() {
                    StateSettlingTracker stateSettlingTracker = StateSettlingTracker.this;
                    stateSettlingTracker.isContinueSettlingRunnablePosted = false;
                    ViewDragHelper viewDragHelper = BottomSheetBehavior.this.viewDragHelper;
                    if (viewDragHelper != null && viewDragHelper.continueSettling()) {
                        StateSettlingTracker stateSettlingTracker2 = StateSettlingTracker.this;
                        stateSettlingTracker2.continueSettlingToState(stateSettlingTracker2.targetState);
                        return;
                    }
                    StateSettlingTracker stateSettlingTracker3 = StateSettlingTracker.this;
                    BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                    if (bottomSheetBehavior.state == 2) {
                        bottomSheetBehavior.setStateInternal(stateSettlingTracker3.targetState);
                    }
                }
            };
        }

        public final void continueSettlingToState(int i) {
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            WeakReference weakReference = bottomSheetBehavior.viewRef;
            if (weakReference == null || weakReference.get() == null) {
                return;
            }
            this.targetState = i;
            if (this.isContinueSettlingRunnablePosted) {
                return;
            }
            View view = (View) bottomSheetBehavior.viewRef.get();
            AnonymousClass1 anonymousClass1 = this.continueSettlingRunnable;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            view.postOnAnimation(anonymousClass1);
            this.isContinueSettlingRunnablePosted = true;
        }
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [com.google.android.material.bottomsheet.BottomSheetBehavior$5] */
    public BottomSheetBehavior() {
        this.saveFlags = 0;
        this.fitToContents = true;
        this.maxWidth = -1;
        this.maxHeight = -1;
        this.stateSettlingTracker = new StateSettlingTracker();
        this.halfExpandedRatio = 0.5f;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.hideFriction = 0.1f;
        this.callbacks = new ArrayList();
        this.initialY = -1;
        this.expandHalfwayActionIds = new SparseIntArray();
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int clampViewPositionHorizontal(View view, int i) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int clampViewPositionVertical(View view, int i) {
                return MathUtils.clamp(i, BottomSheetBehavior.this.getExpandedOffset(), getViewVerticalDragRange());
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int getViewVerticalDragRange() {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return bottomSheetBehavior.hideable ? bottomSheetBehavior.parentHeight : bottomSheetBehavior.collapsedOffset;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final void onViewDragStateChanged(int i) {
                if (i == 1) {
                    BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                    if (bottomSheetBehavior.draggable) {
                        bottomSheetBehavior.setStateInternal(1);
                    }
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final void onViewPositionChanged(View view, int i, int i2) {
                BottomSheetBehavior.this.dispatchOnSlide(i2);
            }

            /* JADX WARN: Code restructure failed: missing block: B:10:0x001c, code lost:
            
                if (r6 > r4.halfExpandedOffset) goto L52;
             */
            /* JADX WARN: Code restructure failed: missing block: B:25:0x006c, code lost:
            
                if (java.lang.Math.abs(r5.getTop() - r4.getExpandedOffset()) < java.lang.Math.abs(r5.getTop() - r4.halfExpandedOffset)) goto L6;
             */
            /* JADX WARN: Code restructure failed: missing block: B:39:0x00b7, code lost:
            
                if (java.lang.Math.abs(r6 - r4.fitToContentsOffset) < java.lang.Math.abs(r6 - r4.collapsedOffset)) goto L6;
             */
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onViewReleased(android.view.View r5, float r6, float r7) {
                /*
                    Method dump skipped, instructions count: 233
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.AnonymousClass5.onViewReleased(android.view.View, float, float):void");
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final boolean tryCaptureView(View view, int i) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                int i2 = bottomSheetBehavior.state;
                if (i2 == 1 || bottomSheetBehavior.touchingScrollingChild) {
                    return false;
                }
                if (i2 == 3 && bottomSheetBehavior.activePointerId == i) {
                    WeakReference weakReference = bottomSheetBehavior.nestedScrollingChildRef;
                    View view2 = weakReference != null ? (View) weakReference.get() : null;
                    if (view2 != null && view2.canScrollVertically(-1)) {
                        return false;
                    }
                }
                System.currentTimeMillis();
                WeakReference weakReference2 = bottomSheetBehavior.viewRef;
                return weakReference2 != null && weakReference2.get() == view;
            }
        };
    }

    public static int getChildMeasureSpec(int i, int i2, int i3, int i4) {
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, i2, i4);
        if (i3 == -1) {
            return childMeasureSpec;
        }
        int mode = View.MeasureSpec.getMode(childMeasureSpec);
        int size = View.MeasureSpec.getSize(childMeasureSpec);
        if (mode == 1073741824) {
            return View.MeasureSpec.makeMeasureSpec(Math.min(size, i3), 1073741824);
        }
        if (size != 0) {
            i3 = Math.min(size, i3);
        }
        return View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
    }

    public final void calculateCollapsedOffset() {
        int calculatePeekHeight = calculatePeekHeight();
        if (this.fitToContents) {
            this.collapsedOffset = Math.max(this.parentHeight - calculatePeekHeight, this.fitToContentsOffset);
        } else {
            this.collapsedOffset = this.parentHeight - calculatePeekHeight;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float calculateInterpolationWithCornersRemoved() {
        /*
            r5 = this;
            com.google.android.material.shape.MaterialShapeDrawable r0 = r5.materialShapeDrawable
            r1 = 0
            if (r0 == 0) goto L6b
            java.lang.ref.WeakReference r0 = r5.viewRef
            if (r0 == 0) goto L6b
            java.lang.Object r0 = r0.get()
            if (r0 == 0) goto L6b
            java.lang.ref.WeakReference r0 = r5.viewRef
            java.lang.Object r0 = r0.get()
            android.view.View r0 = (android.view.View) r0
            boolean r2 = r5.isAtTopOfScreen()
            if (r2 == 0) goto L6b
            android.view.WindowInsets r0 = r0.getRootWindowInsets()
            if (r0 == 0) goto L6b
            com.google.android.material.shape.MaterialShapeDrawable r2 = r5.materialShapeDrawable
            float r2 = r2.getTopLeftCornerResolvedSize()
            r3 = 0
            android.view.RoundedCorner r3 = r0.getRoundedCorner(r3)
            if (r3 == 0) goto L3f
            int r3 = r3.getRadius()
            float r3 = (float) r3
            int r4 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r4 <= 0) goto L3f
            int r4 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r4 <= 0) goto L3f
            float r3 = r3 / r2
            goto L40
        L3f:
            r3 = r1
        L40:
            com.google.android.material.shape.MaterialShapeDrawable r5 = r5.materialShapeDrawable
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r2 = r5.drawableState
            com.google.android.material.shape.ShapeAppearanceModel r2 = r2.shapeAppearanceModel
            com.google.android.material.shape.CornerSize r2 = r2.topRightCornerSize
            android.graphics.RectF r5 = r5.getBoundsAsRectF$1()
            float r5 = r2.getCornerSize(r5)
            r2 = 1
            android.view.RoundedCorner r0 = r0.getRoundedCorner(r2)
            if (r0 == 0) goto L66
            int r0 = r0.getRadius()
            float r0 = (float) r0
            int r2 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r2 <= 0) goto L66
            int r2 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r2 <= 0) goto L66
            float r1 = r0 / r5
        L66:
            float r5 = java.lang.Math.max(r3, r1)
            return r5
        L6b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.calculateInterpolationWithCornersRemoved():float");
    }

    public final int calculatePeekHeight() {
        int i;
        int i2;
        int i3;
        if (this.peekHeightAuto) {
            i = Math.min(Math.max(this.peekHeightMin, this.parentHeight - ((this.parentWidth * 9) / 16)), this.childHeight);
            i2 = this.insetBottom;
        } else {
            if (!this.gestureInsetBottomIgnored && !this.paddingBottomSystemWindowInsets && (i3 = this.gestureInsetBottom) > 0) {
                return Math.max(this.peekHeight, i3 + this.peekHeightGestureInsetBuffer);
            }
            i = this.peekHeight;
            i2 = this.insetBottom;
        }
        return i + i2;
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void cancelBackProgress() {
        MaterialBottomContainerBackHelper materialBottomContainerBackHelper = this.bottomContainerBackHelper;
        if (materialBottomContainerBackHelper == null || materialBottomContainerBackHelper.onCancelBackProgress() == null) {
            return;
        }
        Animator createResetScaleAnimator = materialBottomContainerBackHelper.createResetScaleAnimator();
        createResetScaleAnimator.setDuration(materialBottomContainerBackHelper.cancelDuration);
        createResetScaleAnimator.start();
    }

    public final void clearAccessibilityAction(View view, int i) {
        if (view == null) {
            return;
        }
        ViewCompat.removeActionWithId(view, NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        ViewCompat.removeActionWithId(view, 262144);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        ViewCompat.removeActionWithId(view, 1048576);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        int i2 = this.expandHalfwayActionIds.get(i, -1);
        if (i2 != -1) {
            ViewCompat.removeActionWithId(view, i2);
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
            this.expandHalfwayActionIds.delete(i);
        }
    }

    public void disableShapeAnimations() {
        this.interpolatorAnimator = null;
    }

    public final void dispatchOnSlide(int i) {
        if (((View) this.viewRef.get()) == null || this.callbacks.isEmpty()) {
            return;
        }
        int i2 = this.collapsedOffset;
        if (i <= i2 && i2 != getExpandedOffset()) {
            getExpandedOffset();
        }
        for (int i3 = 0; i3 < this.callbacks.size(); i3++) {
            ((BottomSheetCallback) this.callbacks.get(i3)).getClass();
        }
    }

    public View findScrollingChild(View view) {
        if (view.getVisibility() != 0) {
            return null;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api21Impl.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View findScrollingChild = findScrollingChild(viewGroup.getChildAt(i));
                if (findScrollingChild != null) {
                    return findScrollingChild;
                }
            }
        }
        return null;
    }

    public MaterialBottomContainerBackHelper getBackHelper() {
        return this.bottomContainerBackHelper;
    }

    public final int getExpandedOffset() {
        if (this.fitToContents) {
            return this.fitToContentsOffset;
        }
        return Math.max(this.expandedOffset, this.paddingTopSystemWindowInsets ? 0 : this.insetTop);
    }

    public int getPeekHeightMin() {
        return this.peekHeightMin;
    }

    public final int getTopOffsetForState(int i) {
        if (i == 3) {
            return getExpandedOffset();
        }
        if (i == 4) {
            return this.collapsedOffset;
        }
        if (i == 5) {
            return this.parentHeight;
        }
        if (i == 6) {
            return this.halfExpandedOffset;
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid state to get top offset: "));
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void handleBackInvoked() {
        MaterialBottomContainerBackHelper materialBottomContainerBackHelper = this.bottomContainerBackHelper;
        if (materialBottomContainerBackHelper == null) {
            return;
        }
        BackEventCompat backEventCompat = materialBottomContainerBackHelper.backEvent;
        materialBottomContainerBackHelper.backEvent = null;
        if (backEventCompat == null) {
            setState$1(this.hideable ? 5 : 4);
            return;
        }
        boolean z = this.hideable;
        int i = materialBottomContainerBackHelper.hideDurationMin;
        int i2 = materialBottomContainerBackHelper.hideDurationMax;
        float f = backEventCompat.progress;
        if (!z) {
            Animator createResetScaleAnimator = materialBottomContainerBackHelper.createResetScaleAnimator();
            createResetScaleAnimator.setDuration(AnimationUtils.lerp(f, i2, i));
            createResetScaleAnimator.start();
            setState$1(4);
            return;
        }
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                BottomSheetBehavior.this.setStateInternal(5);
                WeakReference weakReference = BottomSheetBehavior.this.viewRef;
                if (weakReference == null || weakReference.get() == null) {
                    return;
                }
                ((View) BottomSheetBehavior.this.viewRef.get()).requestLayout();
            }
        };
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(materialBottomContainerBackHelper.view, (Property<View, Float>) View.TRANSLATION_Y, materialBottomContainerBackHelper.view.getScaleY() * materialBottomContainerBackHelper.view.getHeight());
        ofFloat.setInterpolator(new FastOutSlowInInterpolator());
        ofFloat.setDuration(AnimationUtils.lerp(f, i2, i));
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.motion.MaterialBottomContainerBackHelper.1
            public AnonymousClass1() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                MaterialBottomContainerBackHelper.this.view.setTranslationY(0.0f);
                MaterialBottomContainerBackHelper.this.updateBackProgress(0.0f);
            }
        });
        ofFloat.addListener(animatorListenerAdapter);
        ofFloat.start();
    }

    public final boolean isAtTopOfScreen() {
        WeakReference weakReference = this.viewRef;
        if (weakReference != null && weakReference.get() != null) {
            int[] iArr = new int[2];
            ((View) this.viewRef.get()).getLocationOnScreen(iArr);
            if (iArr[1] == 0) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        this.viewRef = null;
        this.viewDragHelper = null;
        this.bottomContainerBackHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onDetachedFromLayoutParams() {
        this.viewRef = null;
        this.viewDragHelper = null;
        this.bottomContainerBackHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        int i;
        ViewDragHelper viewDragHelper;
        if (!view.isShown() || !this.draggable) {
            this.ignoreEvents = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            reset$2$1();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (actionMasked == 0) {
            int x = (int) motionEvent.getX();
            this.initialY = (int) motionEvent.getY();
            if (this.state != 2) {
                WeakReference weakReference = this.nestedScrollingChildRef;
                View view2 = weakReference != null ? (View) weakReference.get() : null;
                if (view2 != null && coordinatorLayout.isPointInChildBounds(view2, x, this.initialY)) {
                    this.activePointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.touchingScrollingChild = true;
                }
            }
            this.ignoreEvents = this.activePointerId == -1 && !coordinatorLayout.isPointInChildBounds(view, x, this.initialY);
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.touchingScrollingChild = false;
            this.activePointerId = -1;
            if (this.ignoreEvents) {
                this.ignoreEvents = false;
                return false;
            }
        }
        if (this.ignoreEvents || (viewDragHelper = this.viewDragHelper) == null || !viewDragHelper.shouldInterceptTouchEvent(motionEvent)) {
            WeakReference weakReference2 = this.nestedScrollingChildRef;
            View view3 = weakReference2 != null ? (View) weakReference2.get() : null;
            if (actionMasked != 2 || view3 == null || this.ignoreEvents || this.state == 1 || coordinatorLayout.isPointInChildBounds(view3, (int) motionEvent.getX(), (int) motionEvent.getY()) || this.viewDragHelper == null || (i = this.initialY) == -1 || Math.abs(i - motionEvent.getY()) <= this.viewDragHelper.mTouchSlop) {
                return false;
            }
        }
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        int i2 = this.maxHeight;
        MaterialShapeDrawable materialShapeDrawable = this.materialShapeDrawable;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (coordinatorLayout.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
            view.setFitsSystemWindows(true);
        }
        if (this.viewRef == null) {
            this.peekHeightMin = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            final boolean z = (this.gestureInsetBottomIgnored || this.peekHeightAuto) ? false : true;
            if (this.paddingBottomSystemWindowInsets || this.paddingLeftSystemWindowInsets || this.paddingRightSystemWindowInsets || this.marginLeftSystemWindowInsets || this.marginRightSystemWindowInsets || this.marginTopSystemWindowInsets || z) {
                ViewUtils.doOnApplyWindowInsets(view, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.4
                    /* JADX WARN: Removed duplicated region for block: B:32:0x007c  */
                    /* JADX WARN: Removed duplicated region for block: B:35:0x008a  */
                    @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final androidx.core.view.WindowInsetsCompat onApplyWindowInsets(android.view.View r11, androidx.core.view.WindowInsetsCompat r12, com.google.android.material.internal.ViewUtils.RelativePadding r13) {
                        /*
                            r10 = this;
                            androidx.core.view.WindowInsetsCompat$Impl r0 = r12.mImpl
                            r1 = 7
                            androidx.core.graphics.Insets r1 = r0.getInsets(r1)
                            r2 = 32
                            androidx.core.graphics.Insets r0 = r0.getInsets(r2)
                            int r2 = r1.top
                            com.google.android.material.bottomsheet.BottomSheetBehavior r3 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                            r3.insetTop = r2
                            boolean r2 = com.google.android.material.internal.ViewUtils.isLayoutRtl(r11)
                            int r4 = r11.getPaddingBottom()
                            int r5 = r11.getPaddingLeft()
                            int r6 = r11.getPaddingRight()
                            boolean r7 = r3.paddingBottomSystemWindowInsets
                            if (r7 == 0) goto L30
                            int r4 = r12.getSystemWindowInsetBottom()
                            r3.insetBottom = r4
                            int r7 = r13.bottom
                            int r4 = r4 + r7
                        L30:
                            boolean r7 = r3.paddingLeftSystemWindowInsets
                            int r8 = r1.left
                            if (r7 == 0) goto L3e
                            if (r2 == 0) goto L3b
                            int r5 = r13.end
                            goto L3d
                        L3b:
                            int r5 = r13.start
                        L3d:
                            int r5 = r5 + r8
                        L3e:
                            boolean r7 = r3.paddingRightSystemWindowInsets
                            int r9 = r1.right
                            if (r7 == 0) goto L4d
                            if (r2 == 0) goto L49
                            int r13 = r13.start
                            goto L4b
                        L49:
                            int r13 = r13.end
                        L4b:
                            int r6 = r13 + r9
                        L4d:
                            android.view.ViewGroup$LayoutParams r13 = r11.getLayoutParams()
                            android.view.ViewGroup$MarginLayoutParams r13 = (android.view.ViewGroup.MarginLayoutParams) r13
                            boolean r2 = r3.marginLeftSystemWindowInsets
                            r7 = 1
                            if (r2 == 0) goto L60
                            int r2 = r13.leftMargin
                            if (r2 == r8) goto L60
                            r13.leftMargin = r8
                            r2 = r7
                            goto L61
                        L60:
                            r2 = 0
                        L61:
                            boolean r8 = r3.marginRightSystemWindowInsets
                            if (r8 == 0) goto L6c
                            int r8 = r13.rightMargin
                            if (r8 == r9) goto L6c
                            r13.rightMargin = r9
                            r2 = r7
                        L6c:
                            boolean r8 = r3.marginTopSystemWindowInsets
                            if (r8 == 0) goto L79
                            int r8 = r13.topMargin
                            int r1 = r1.top
                            if (r8 == r1) goto L79
                            r13.topMargin = r1
                            goto L7a
                        L79:
                            r7 = r2
                        L7a:
                            if (r7 == 0) goto L7f
                            r11.setLayoutParams(r13)
                        L7f:
                            int r13 = r11.getPaddingTop()
                            r11.setPadding(r5, r13, r6, r4)
                            boolean r10 = r2
                            if (r10 == 0) goto L8e
                            int r11 = r0.bottom
                            r3.gestureInsetBottom = r11
                        L8e:
                            boolean r11 = r3.paddingBottomSystemWindowInsets
                            if (r11 != 0) goto L96
                            if (r10 == 0) goto L95
                            goto L96
                        L95:
                            return r12
                        L96:
                            r3.updatePeekHeight()
                            return r12
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.AnonymousClass4.onApplyWindowInsets(android.view.View, androidx.core.view.WindowInsetsCompat, com.google.android.material.internal.ViewUtils$RelativePadding):androidx.core.view.WindowInsetsCompat");
                    }
                });
            }
            ViewCompat.setWindowInsetsAnimationCallback(view, new InsetsAnimationCallback(view));
            this.viewRef = new WeakReference(view);
            this.bottomContainerBackHelper = new MaterialBottomContainerBackHelper(view);
            if (materialShapeDrawable != null) {
                view.setBackground(materialShapeDrawable);
                float f = this.elevation;
                if (f == -1.0f) {
                    f = ViewCompat.Api21Impl.getElevation(view);
                }
                materialShapeDrawable.setElevation(f);
            } else {
                ColorStateList colorStateList = this.backgroundTint;
                if (colorStateList != null) {
                    ViewCompat.Api21Impl.setBackgroundTintList(view, colorStateList);
                }
            }
            updateAccessibilityActions();
            if (view.getImportantForAccessibility() == 0) {
                view.setImportantForAccessibility(1);
            }
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(coordinatorLayout, this.dragCallback);
        }
        int top = view.getTop();
        coordinatorLayout.onLayoutChild(view, i);
        this.parentWidth = coordinatorLayout.getWidth();
        this.parentHeight = coordinatorLayout.getHeight();
        int height = view.getHeight();
        this.childHeight = height;
        int i3 = this.parentHeight;
        int i4 = i3 - height;
        int i5 = this.insetTop;
        if (i4 < i5) {
            if (this.paddingTopSystemWindowInsets) {
                if (i2 != -1) {
                    i3 = Math.min(i3, i2);
                }
                this.childHeight = i3;
            } else {
                int i6 = i3 - i5;
                if (i2 != -1) {
                    i6 = Math.min(i6, i2);
                }
                this.childHeight = i6;
            }
        }
        this.fitToContentsOffset = Math.max(0, this.parentHeight - this.childHeight);
        this.halfExpandedOffset = (int) ((1.0f - this.halfExpandedRatio) * this.parentHeight);
        calculateCollapsedOffset();
        int i7 = this.state;
        if (i7 == 3) {
            view.offsetTopAndBottom(getExpandedOffset());
        } else if (i7 == 6) {
            view.offsetTopAndBottom(this.halfExpandedOffset);
        } else if (this.hideable && i7 == 5) {
            view.offsetTopAndBottom(this.parentHeight);
        } else if (i7 == 4) {
            view.offsetTopAndBottom(this.collapsedOffset);
        } else if (i7 == 1 || i7 == 2) {
            view.offsetTopAndBottom(top - view.getTop());
        }
        updateDrawableForTargetState(this.state, false);
        this.nestedScrollingChildRef = new WeakReference(findScrollingChild(view));
        for (int i8 = 0; i8 < this.callbacks.size(); i8++) {
            ((BottomSheetCallback) this.callbacks.get(i8)).getClass();
        }
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, coordinatorLayout.getPaddingRight() + coordinatorLayout.getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, this.maxWidth, marginLayoutParams.width), getChildMeasureSpec(i3, coordinatorLayout.getPaddingBottom() + coordinatorLayout.getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, this.maxHeight, marginLayoutParams.height));
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onNestedPreFling(View view, View view2, float f) {
        WeakReference weakReference = this.nestedScrollingChildRef;
        return (weakReference == null || view2 != weakReference.get() || this.state == 3) ? false : true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int[] iArr, int i3) {
        boolean z = this.draggable;
        if (i3 == 1) {
            return;
        }
        WeakReference weakReference = this.nestedScrollingChildRef;
        if (view2 != (weakReference != null ? (View) weakReference.get() : null)) {
            return;
        }
        int top = view.getTop();
        int i4 = top - i2;
        if (i2 > 0) {
            if (i4 < getExpandedOffset()) {
                int expandedOffset = top - getExpandedOffset();
                iArr[1] = expandedOffset;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(-expandedOffset);
                setStateInternal(3);
            } else {
                if (!z) {
                    return;
                }
                iArr[1] = i2;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(-i2);
                setStateInternal(1);
            }
        } else if (i2 < 0 && !view2.canScrollVertically(-1)) {
            int i5 = this.collapsedOffset;
            if (i4 > i5 && !this.hideable) {
                int i6 = top - i5;
                iArr[1] = i6;
                WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(-i6);
                setStateInternal(4);
            } else {
                if (!z) {
                    return;
                }
                iArr[1] = i2;
                WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(-i2);
                setStateInternal(1);
            }
        }
        dispatchOnSlide(view.getTop());
        this.lastNestedScrollDy = i2;
        this.nestedScrolled = true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onRestoreInstanceState(View view, Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        int i = this.saveFlags;
        if (i != 0) {
            if (i == -1 || (i & 1) == 1) {
                this.peekHeight = savedState.peekHeight;
            }
            if (i == -1 || (i & 2) == 2) {
                this.fitToContents = savedState.fitToContents;
            }
            if (i == -1 || (i & 4) == 4) {
                this.hideable = savedState.hideable;
            }
            if (i == -1 || (i & 8) == 8) {
                this.skipCollapsed = savedState.skipCollapsed;
            }
        }
        int i2 = savedState.state;
        if (i2 == 1 || i2 == 2) {
            this.state = 4;
        } else {
            this.state = i2;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final Parcelable onSaveInstanceState(View view) {
        return new SavedState((Parcelable) View.BaseSavedState.EMPTY_STATE, (BottomSheetBehavior<?>) this);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        return (i & 2) != 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0030, code lost:
    
        if (r4.getTop() <= r2.halfExpandedOffset) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0071, code lost:
    
        if (java.lang.Math.abs(r3 - r2.fitToContentsOffset) < java.lang.Math.abs(r3 - r2.collapsedOffset)) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0080, code lost:
    
        if (r3 < java.lang.Math.abs(r3 - r2.collapsedOffset)) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0090, code lost:
    
        if (java.lang.Math.abs(r3 - r1) < java.lang.Math.abs(r3 - r2.collapsedOffset)) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ac, code lost:
    
        if (java.lang.Math.abs(r3 - r2.halfExpandedOffset) < java.lang.Math.abs(r3 - r2.collapsedOffset)) goto L50;
     */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStopNestedScroll(androidx.coordinatorlayout.widget.CoordinatorLayout r3, android.view.View r4, android.view.View r5, int r6) {
        /*
            r2 = this;
            int r3 = r4.getTop()
            int r6 = r2.getExpandedOffset()
            r0 = 3
            if (r3 != r6) goto Lf
            r2.setStateInternal(r0)
            return
        Lf:
            java.lang.ref.WeakReference r3 = r2.nestedScrollingChildRef
            if (r3 == 0) goto Lb5
            java.lang.Object r3 = r3.get()
            if (r5 != r3) goto Lb5
            boolean r3 = r2.nestedScrolled
            if (r3 != 0) goto L1f
            goto Lb5
        L1f:
            int r3 = r2.lastNestedScrollDy
            r5 = 6
            if (r3 <= 0) goto L34
            boolean r3 = r2.fitToContents
            if (r3 == 0) goto L2a
            goto Laf
        L2a:
            int r3 = r4.getTop()
            int r6 = r2.halfExpandedOffset
            if (r3 <= r6) goto Laf
            goto Lae
        L34:
            boolean r3 = r2.hideable
            if (r3 == 0) goto L55
            android.view.VelocityTracker r3 = r2.velocityTracker
            if (r3 != 0) goto L3e
            r3 = 0
            goto L4d
        L3e:
            r6 = 1000(0x3e8, float:1.401E-42)
            float r1 = r2.maximumVelocity
            r3.computeCurrentVelocity(r6, r1)
            android.view.VelocityTracker r3 = r2.velocityTracker
            int r6 = r2.activePointerId
            float r3 = r3.getYVelocity(r6)
        L4d:
            boolean r3 = r2.shouldHide(r3, r4)
            if (r3 == 0) goto L55
            r0 = 5
            goto Laf
        L55:
            int r3 = r2.lastNestedScrollDy
            r6 = 4
            if (r3 != 0) goto L93
            int r3 = r4.getTop()
            boolean r1 = r2.fitToContents
            if (r1 == 0) goto L74
            int r5 = r2.fitToContentsOffset
            int r5 = r3 - r5
            int r5 = java.lang.Math.abs(r5)
            int r1 = r2.collapsedOffset
            int r3 = r3 - r1
            int r3 = java.lang.Math.abs(r3)
            if (r5 >= r3) goto L97
            goto Laf
        L74:
            int r1 = r2.halfExpandedOffset
            if (r3 >= r1) goto L83
            int r6 = r2.collapsedOffset
            int r6 = r3 - r6
            int r6 = java.lang.Math.abs(r6)
            if (r3 >= r6) goto Lae
            goto Laf
        L83:
            int r0 = r3 - r1
            int r0 = java.lang.Math.abs(r0)
            int r1 = r2.collapsedOffset
            int r3 = r3 - r1
            int r3 = java.lang.Math.abs(r3)
            if (r0 >= r3) goto L97
            goto Lae
        L93:
            boolean r3 = r2.fitToContents
            if (r3 == 0) goto L99
        L97:
            r0 = r6
            goto Laf
        L99:
            int r3 = r4.getTop()
            int r0 = r2.halfExpandedOffset
            int r0 = r3 - r0
            int r0 = java.lang.Math.abs(r0)
            int r1 = r2.collapsedOffset
            int r3 = r3 - r1
            int r3 = java.lang.Math.abs(r3)
            if (r0 >= r3) goto L97
        Lae:
            r0 = r5
        Laf:
            r3 = 0
            r2.startSettling(r4, r0, r3)
            r2.nestedScrolled = r3
        Lb5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.onStopNestedScroll(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.View, int):void");
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        if (!view.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        int i = this.state;
        if (i == 1 && actionMasked == 0) {
            return true;
        }
        ViewDragHelper viewDragHelper = this.viewDragHelper;
        if (viewDragHelper != null && (this.draggable || i == 1)) {
            viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            reset$2$1();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (this.viewDragHelper != null && ((this.draggable || this.state == 1) && actionMasked == 2 && !this.ignoreEvents)) {
            float abs = Math.abs(this.initialY - motionEvent.getY());
            ViewDragHelper viewDragHelper2 = this.viewDragHelper;
            if (abs > viewDragHelper2.mTouchSlop) {
                viewDragHelper2.captureChildView(view, motionEvent.getPointerId(motionEvent.getActionIndex()));
            }
        }
        return !this.ignoreEvents;
    }

    public final void reset$2$1() {
        this.activePointerId = -1;
        this.initialY = -1;
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    public final void setAccessibilityDelegateView(BottomSheetDragHandleView bottomSheetDragHandleView) {
        WeakReference weakReference;
        if (bottomSheetDragHandleView != null || (weakReference = this.accessibilityDelegateViewRef) == null) {
            this.accessibilityDelegateViewRef = new WeakReference(bottomSheetDragHandleView);
            updateAccessibilityActions(bottomSheetDragHandleView, 1);
        } else {
            clearAccessibilityAction((View) weakReference.get(), 1);
            this.accessibilityDelegateViewRef = null;
        }
    }

    public final void setPeekHeight(int i) {
        if (i == -1) {
            if (this.peekHeightAuto) {
                return;
            } else {
                this.peekHeightAuto = true;
            }
        } else {
            if (!this.peekHeightAuto && this.peekHeight == i) {
                return;
            }
            this.peekHeightAuto = false;
            this.peekHeight = Math.max(0, i);
        }
        updatePeekHeight();
    }

    public final void setState$1(int i) {
        if (i == 1 || i == 2) {
            throw new IllegalArgumentException(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("STATE_"), i == 1 ? "DRAGGING" : "SETTLING", " should not be set externally."));
        }
        if (!this.hideable && i == 5) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Cannot set state: ", "BottomSheetBehavior");
            return;
        }
        final int i2 = (i == 6 && this.fitToContents && getTopOffsetForState(i) <= this.fitToContentsOffset) ? 3 : i;
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            setStateInternal(i);
            return;
        }
        final View view = (View) this.viewRef.get();
        Runnable runnable = new Runnable() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.1
            @Override // java.lang.Runnable
            public final void run() {
                BottomSheetBehavior.this.startSettling(view, i2, false);
            }
        };
        ViewParent parent = view.getParent();
        if (parent != null && parent.isLayoutRequested()) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (view.isAttachedToWindow()) {
                view.post(runnable);
                return;
            }
        }
        runnable.run();
    }

    public final void setStateInternal(int i) {
        if (this.state == i) {
            return;
        }
        this.state = i;
        if (i != 4 && i != 3 && i != 6) {
            boolean z = this.hideable;
        }
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || ((View) weakReference.get()) == null) {
            return;
        }
        if (i == 3) {
            updateImportantForAccessibility(true);
        } else if (i == 6 || i == 5 || i == 4) {
            updateImportantForAccessibility(false);
        }
        updateDrawableForTargetState(i, true);
        for (int i2 = 0; i2 < this.callbacks.size(); i2++) {
            ((BottomSheetCallback) this.callbacks.get(i2)).onStateChanged(i);
        }
        updateAccessibilityActions();
    }

    public final boolean shouldHide(float f, View view) {
        if (this.skipCollapsed) {
            return true;
        }
        if (view.getTop() < this.collapsedOffset) {
            return false;
        }
        return Math.abs(((f * this.hideFriction) + ((float) view.getTop())) - ((float) this.collapsedOffset)) / ((float) calculatePeekHeight()) > 0.5f;
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void startBackProgress(BackEventCompat backEventCompat) {
        MaterialBottomContainerBackHelper materialBottomContainerBackHelper = this.bottomContainerBackHelper;
        if (materialBottomContainerBackHelper == null) {
            return;
        }
        materialBottomContainerBackHelper.backEvent = backEventCompat;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0030, code lost:
    
        if (r3 != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0012, code lost:
    
        if (r1.settleCapturedViewAt(r3.getLeft(), r0) != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0032, code lost:
    
        setStateInternal(2);
        updateDrawableForTargetState(r4, true);
        r2.stateSettlingTracker.continueSettlingToState(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x003f, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startSettling(android.view.View r3, int r4, boolean r5) {
        /*
            r2 = this;
            int r0 = r2.getTopOffsetForState(r4)
            androidx.customview.widget.ViewDragHelper r1 = r2.viewDragHelper
            if (r1 == 0) goto L40
            if (r5 == 0) goto L15
            int r3 = r3.getLeft()
            boolean r3 = r1.settleCapturedViewAt(r3, r0)
            if (r3 == 0) goto L40
            goto L32
        L15:
            int r5 = r3.getLeft()
            r1.mCapturedView = r3
            r3 = -1
            r1.mActivePointerId = r3
            r3 = 0
            boolean r3 = r1.forceSettleCapturedViewAt(r5, r0, r3, r3)
            if (r3 != 0) goto L30
            int r5 = r1.mDragState
            if (r5 != 0) goto L30
            android.view.View r5 = r1.mCapturedView
            if (r5 == 0) goto L30
            r5 = 0
            r1.mCapturedView = r5
        L30:
            if (r3 == 0) goto L40
        L32:
            r3 = 2
            r2.setStateInternal(r3)
            r3 = 1
            r2.updateDrawableForTargetState(r4, r3)
            com.google.android.material.bottomsheet.BottomSheetBehavior$StateSettlingTracker r2 = r2.stateSettlingTracker
            r2.continueSettlingToState(r4)
            return
        L40:
            r2.setStateInternal(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.startSettling(android.view.View, int, boolean):void");
    }

    public final void updateAccessibilityActions() {
        WeakReference weakReference = this.viewRef;
        if (weakReference != null) {
            updateAccessibilityActions((View) weakReference.get(), 0);
        }
        WeakReference weakReference2 = this.accessibilityDelegateViewRef;
        if (weakReference2 != null) {
            updateAccessibilityActions((View) weakReference2.get(), 1);
        }
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void updateBackProgress(BackEventCompat backEventCompat) {
        MaterialBottomContainerBackHelper materialBottomContainerBackHelper = this.bottomContainerBackHelper;
        if (materialBottomContainerBackHelper == null) {
            return;
        }
        if (materialBottomContainerBackHelper.backEvent == null) {
            Log.w("MaterialBackHelper", "Must call startBackProgress() before updateBackProgress()");
        }
        BackEventCompat backEventCompat2 = materialBottomContainerBackHelper.backEvent;
        materialBottomContainerBackHelper.backEvent = backEventCompat;
        if (backEventCompat2 == null) {
            return;
        }
        materialBottomContainerBackHelper.updateBackProgress(backEventCompat.progress);
    }

    public final void updateDrawableForTargetState(int i, boolean z) {
        ValueAnimator valueAnimator;
        MaterialShapeDrawable materialShapeDrawable = this.materialShapeDrawable;
        if (i == 2) {
            return;
        }
        boolean z2 = this.state == 3 && (this.shouldRemoveExpandedCorners || isAtTopOfScreen());
        if (this.expandedCornersRemoved == z2 || materialShapeDrawable == null) {
            return;
        }
        this.expandedCornersRemoved = z2;
        if (!z || (valueAnimator = this.interpolatorAnimator) == null) {
            ValueAnimator valueAnimator2 = this.interpolatorAnimator;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.interpolatorAnimator.cancel();
            }
            materialShapeDrawable.setInterpolation(this.expandedCornersRemoved ? calculateInterpolationWithCornersRemoved() : 1.0f);
            return;
        }
        if (valueAnimator.isRunning()) {
            this.interpolatorAnimator.reverse();
        } else {
            this.interpolatorAnimator.setFloatValues(materialShapeDrawable.drawableState.interpolation, z2 ? calculateInterpolationWithCornersRemoved() : 1.0f);
            this.interpolatorAnimator.start();
        }
    }

    public final void updateImportantForAccessibility(boolean z) {
        WeakReference weakReference = this.viewRef;
        if (weakReference == null) {
            return;
        }
        ViewParent parent = ((View) weakReference.get()).getParent();
        if (parent instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
            int childCount = coordinatorLayout.getChildCount();
            if (z) {
                if (this.importantForAccessibilityMap != null) {
                    return;
                } else {
                    this.importantForAccessibilityMap = new HashMap(childCount);
                }
            }
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if (childAt != this.viewRef.get() && z) {
                    ((HashMap) this.importantForAccessibilityMap).put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                }
            }
            if (z) {
                return;
            }
            this.importantForAccessibilityMap = null;
        }
    }

    public final void updatePeekHeight() {
        View view;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
            if (this.state != 4 || (view = (View) this.viewRef.get()) == null) {
                return;
            }
            view.requestLayout();
        }
    }

    public final void updateAccessibilityActions(View view, int i) {
        int i2;
        AccessibilityDelegateCompat accessibilityDelegateCompat;
        if (view == null) {
            return;
        }
        clearAccessibilityAction(view, i);
        if (!this.fitToContents && this.state != 6) {
            SparseIntArray sparseIntArray = this.expandHalfwayActionIds;
            String string = view.getResources().getString(R.string.bottomsheet_action_expand_halfway);
            AccessibilityViewCommand accessibilityViewCommand = new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.6
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view2) {
                    BottomSheetBehavior.this.setState$1(r2);
                    return true;
                }
            };
            List actionList = ViewCompat.getActionList(view);
            int i3 = 0;
            while (true) {
                if (i3 >= actionList.size()) {
                    int i4 = -1;
                    for (int i5 = 0; i5 < 32 && i4 == -1; i5++) {
                        int i6 = ViewCompat.ACCESSIBILITY_ACTIONS_RESOURCE_IDS[i5];
                        boolean z = true;
                        for (int i7 = 0; i7 < actionList.size(); i7++) {
                            z &= ((AccessibilityNodeInfoCompat.AccessibilityActionCompat) actionList.get(i7)).getId() != i6;
                        }
                        if (z) {
                            i4 = i6;
                        }
                    }
                    i2 = i4;
                } else {
                    if (TextUtils.equals(string, ((AccessibilityNodeInfo.AccessibilityAction) ((AccessibilityNodeInfoCompat.AccessibilityActionCompat) actionList.get(i3)).mAction).getLabel())) {
                        i2 = ((AccessibilityNodeInfoCompat.AccessibilityActionCompat) actionList.get(i3)).getId();
                        break;
                    }
                    i3++;
                }
            }
            if (i2 != -1) {
                AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i2, string, accessibilityViewCommand);
                View.AccessibilityDelegate accessibilityDelegate = ViewCompat.Api29Impl.getAccessibilityDelegate(view);
                if (accessibilityDelegate == null) {
                    accessibilityDelegateCompat = null;
                } else if (accessibilityDelegate instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter) {
                    accessibilityDelegateCompat = ((AccessibilityDelegateCompat.AccessibilityDelegateAdapter) accessibilityDelegate).mCompat;
                } else {
                    accessibilityDelegateCompat = new AccessibilityDelegateCompat(accessibilityDelegate);
                }
                if (accessibilityDelegateCompat == null) {
                    accessibilityDelegateCompat = new AccessibilityDelegateCompat();
                }
                ViewCompat.setAccessibilityDelegate(view, accessibilityDelegateCompat);
                ViewCompat.removeActionWithId(view, accessibilityActionCompat.getId());
                ViewCompat.getActionList(view).add(accessibilityActionCompat);
                ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
            }
            sparseIntArray.put(i, i2);
        }
        if (this.hideable) {
            final int i8 = 5;
            if (this.state != 5) {
                ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, null, new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.6
                    @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                    public final boolean perform(View view2) {
                        BottomSheetBehavior.this.setState$1(i8);
                        return true;
                    }
                });
            }
        }
        int i9 = this.state;
        final int i10 = 4;
        final int i11 = 3;
        if (i9 == 3) {
            r4 = this.fitToContents ? 4 : 6;
            ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, null, new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.6
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view2) {
                    BottomSheetBehavior.this.setState$1(r2);
                    return true;
                }
            });
        } else if (i9 != 4) {
            if (i9 != 6) {
                return;
            }
            ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, null, new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.6
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view2) {
                    BottomSheetBehavior.this.setState$1(i10);
                    return true;
                }
            });
            ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, null, new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.6
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view2) {
                    BottomSheetBehavior.this.setState$1(i11);
                    return true;
                }
            });
        } else {
            r4 = this.fitToContents ? 3 : 6;
            ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, null, new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.6
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view2) {
                    BottomSheetBehavior.this.setState$1(r2);
                    return true;
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r7v3, types: [com.google.android.material.bottomsheet.BottomSheetBehavior$5] */
    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int i;
        this.saveFlags = 0;
        this.fitToContents = true;
        this.maxWidth = -1;
        this.maxHeight = -1;
        this.stateSettlingTracker = new StateSettlingTracker();
        this.halfExpandedRatio = 0.5f;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.hideFriction = 0.1f;
        this.callbacks = new ArrayList();
        this.initialY = -1;
        this.expandHalfwayActionIds = new SparseIntArray();
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int clampViewPositionHorizontal(View view, int i2) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int clampViewPositionVertical(View view, int i2) {
                return MathUtils.clamp(i2, BottomSheetBehavior.this.getExpandedOffset(), getViewVerticalDragRange());
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int getViewVerticalDragRange() {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return bottomSheetBehavior.hideable ? bottomSheetBehavior.parentHeight : bottomSheetBehavior.collapsedOffset;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final void onViewDragStateChanged(int i2) {
                if (i2 == 1) {
                    BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                    if (bottomSheetBehavior.draggable) {
                        bottomSheetBehavior.setStateInternal(1);
                    }
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final void onViewPositionChanged(View view, int i2, int i22) {
                BottomSheetBehavior.this.dispatchOnSlide(i22);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final void onViewReleased(View view, float f, float f2) {
                /*
                    Method dump skipped, instructions count: 233
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.AnonymousClass5.onViewReleased(android.view.View, float, float):void");
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final boolean tryCaptureView(View view, int i2) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                int i22 = bottomSheetBehavior.state;
                if (i22 == 1 || bottomSheetBehavior.touchingScrollingChild) {
                    return false;
                }
                if (i22 == 3 && bottomSheetBehavior.activePointerId == i2) {
                    WeakReference weakReference = bottomSheetBehavior.nestedScrollingChildRef;
                    View view2 = weakReference != null ? (View) weakReference.get() : null;
                    if (view2 != null && view2.canScrollVertically(-1)) {
                        return false;
                    }
                }
                System.currentTimeMillis();
                WeakReference weakReference2 = bottomSheetBehavior.viewRef;
                return weakReference2 != null && weakReference2.get() == view;
            }
        };
        this.peekHeightGestureInsetBuffer = context.getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BottomSheetBehavior_Layout);
        if (obtainStyledAttributes.hasValue(3)) {
            this.backgroundTint = MaterialResources.getColorStateList(context, obtainStyledAttributes, 3);
        }
        if (obtainStyledAttributes.hasValue(21)) {
            this.shapeAppearanceModelDefault = ShapeAppearanceModel.builder(context, attributeSet, R.attr.bottomSheetStyle, R.style.Widget_Design_BottomSheet_Modal).build();
        }
        ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearanceModelDefault;
        if (shapeAppearanceModel != null) {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
            this.materialShapeDrawable = materialShapeDrawable;
            materialShapeDrawable.initializeElevationOverlay(context);
            ColorStateList colorStateList = this.backgroundTint;
            if (colorStateList != null) {
                this.materialShapeDrawable.setFillColor(colorStateList);
            } else {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(android.R.attr.colorBackground, typedValue, true);
                this.materialShapeDrawable.setTint(typedValue.data);
            }
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(calculateInterpolationWithCornersRemoved(), 1.0f);
        this.interpolatorAnimator = ofFloat;
        ofFloat.setDuration(500L);
        this.interpolatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                MaterialShapeDrawable materialShapeDrawable2 = BottomSheetBehavior.this.materialShapeDrawable;
                if (materialShapeDrawable2 != null) {
                    materialShapeDrawable2.setInterpolation(floatValue);
                }
            }
        });
        this.elevation = obtainStyledAttributes.getDimension(2, -1.0f);
        if (obtainStyledAttributes.hasValue(0)) {
            this.maxWidth = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        }
        if (obtainStyledAttributes.hasValue(1)) {
            this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(1, -1);
        }
        TypedValue peekValue = obtainStyledAttributes.peekValue(9);
        if (peekValue != null && (i = peekValue.data) == -1) {
            setPeekHeight(i);
        } else {
            setPeekHeight(obtainStyledAttributes.getDimensionPixelSize(9, -1));
        }
        boolean z = obtainStyledAttributes.getBoolean(8, false);
        if (this.hideable != z) {
            this.hideable = z;
            if (!z && this.state == 5) {
                setState$1(4);
            }
            updateAccessibilityActions();
        }
        this.gestureInsetBottomIgnored = obtainStyledAttributes.getBoolean(13, false);
        boolean z2 = obtainStyledAttributes.getBoolean(6, true);
        if (this.fitToContents != z2) {
            this.fitToContents = z2;
            if (this.viewRef != null) {
                calculateCollapsedOffset();
            }
            setStateInternal((this.fitToContents && this.state == 6) ? 3 : this.state);
            updateDrawableForTargetState(this.state, true);
            updateAccessibilityActions();
        }
        this.skipCollapsed = obtainStyledAttributes.getBoolean(12, false);
        this.draggable = obtainStyledAttributes.getBoolean(4, true);
        this.saveFlags = obtainStyledAttributes.getInt(10, 0);
        float f = obtainStyledAttributes.getFloat(7, 0.5f);
        if (f > 0.0f && f < 1.0f) {
            this.halfExpandedRatio = f;
            if (this.viewRef != null) {
                this.halfExpandedOffset = (int) ((1.0f - f) * this.parentHeight);
            }
            TypedValue peekValue2 = obtainStyledAttributes.peekValue(5);
            if (peekValue2 != null && peekValue2.type == 16) {
                int i2 = peekValue2.data;
                if (i2 >= 0) {
                    this.expandedOffset = i2;
                    updateDrawableForTargetState(this.state, true);
                } else {
                    throw new IllegalArgumentException("offset must be greater than or equal to 0");
                }
            } else {
                int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(5, 0);
                if (dimensionPixelOffset >= 0) {
                    this.expandedOffset = dimensionPixelOffset;
                    updateDrawableForTargetState(this.state, true);
                } else {
                    throw new IllegalArgumentException("offset must be greater than or equal to 0");
                }
            }
            this.significantVelocityThreshold = obtainStyledAttributes.getInt(11, 500);
            this.paddingBottomSystemWindowInsets = obtainStyledAttributes.getBoolean(17, false);
            this.paddingLeftSystemWindowInsets = obtainStyledAttributes.getBoolean(18, false);
            this.paddingRightSystemWindowInsets = obtainStyledAttributes.getBoolean(19, false);
            this.paddingTopSystemWindowInsets = obtainStyledAttributes.getBoolean(20, true);
            this.marginLeftSystemWindowInsets = obtainStyledAttributes.getBoolean(14, false);
            this.marginRightSystemWindowInsets = obtainStyledAttributes.getBoolean(15, false);
            this.marginTopSystemWindowInsets = obtainStyledAttributes.getBoolean(16, false);
            this.shouldRemoveExpandedCorners = obtainStyledAttributes.getBoolean(23, true);
            obtainStyledAttributes.recycle();
            this.maximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
            return;
        }
        throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4, int i5, int[] iArr) {
    }
}

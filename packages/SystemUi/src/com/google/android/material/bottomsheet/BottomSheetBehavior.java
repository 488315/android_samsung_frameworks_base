package com.google.android.material.bottomsheet;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.core.math.MathUtils;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public int activePointerId;
    public final ColorStateList backgroundTint;
    public final ArrayList callbacks;
    public int childHeight;
    public int collapsedOffset;
    public final C42344 dragCallback;
    public boolean draggable;
    public final float elevation;
    public int expandHalfwayActionId;
    public int expandedOffset;
    public boolean fitToContents;
    public int fitToContentsOffset;
    public int gestureInsetBottom;
    public boolean gestureInsetBottomIgnored;
    public int halfExpandedOffset;
    public float halfExpandedRatio;
    public final float hideFriction;
    public boolean hideable;
    public boolean ignoreEvents;
    public Map importantForAccessibilityMap;
    public int initialY;
    public int insetBottom;
    public int insetTop;
    public ValueAnimator interpolatorAnimator;
    public boolean isShapeExpanded;
    public int lastNestedScrollDy;
    public final boolean marginLeftSystemWindowInsets;
    public final boolean marginRightSystemWindowInsets;
    public final boolean marginTopSystemWindowInsets;
    public MaterialShapeDrawable materialShapeDrawable;
    public int maxHeight;
    public int maxWidth;
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
    public int saveFlags;
    public final ShapeAppearanceModel shapeAppearanceModelDefault;
    public boolean skipCollapsed;
    public int state;
    public final StateSettlingTracker stateSettlingTracker;
    public boolean touchingScrollingChild;
    public VelocityTracker velocityTracker;
    public ViewDragHelper viewDragHelper;
    public WeakReference viewRef;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends AbsSavedState {
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
            parcel.writeParcelable(this.mSuperState, i);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StateSettlingTracker {
        public final RunnableC42371 continueSettlingRunnable;
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
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postOnAnimation(view, this.continueSettlingRunnable);
            this.isContinueSettlingRunnablePosted = true;
        }
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.google.android.material.bottomsheet.BottomSheetBehavior$4] */
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
        this.expandHalfwayActionId = -1;
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.4
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int clampViewPositionHorizontal(View view, int i) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int clampViewPositionVertical(View view, int i) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return MathUtils.clamp(i, bottomSheetBehavior.getExpandedOffset(), bottomSheetBehavior.hideable ? bottomSheetBehavior.parentHeight : bottomSheetBehavior.collapsedOffset);
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
            public final void onViewPositionChanged(View view, int i, int i2, int i3) {
                BottomSheetBehavior.this.dispatchOnSlide(i2);
            }

            /* JADX WARN: Code restructure failed: missing block: B:28:0x006c, code lost:
            
                if (java.lang.Math.abs(r4.getTop() - r3.getExpandedOffset()) < java.lang.Math.abs(r4.getTop() - r3.halfExpandedOffset)) goto L50;
             */
            /* JADX WARN: Code restructure failed: missing block: B:37:0x0098, code lost:
            
                if (java.lang.Math.abs(r5 - r3.halfExpandedOffset) < java.lang.Math.abs(r5 - r3.collapsedOffset)) goto L53;
             */
            /* JADX WARN: Code restructure failed: missing block: B:42:0x00b2, code lost:
            
                if (java.lang.Math.abs(r5 - r3.fitToContentsOffset) < java.lang.Math.abs(r5 - r3.collapsedOffset)) goto L50;
             */
            /* JADX WARN: Code restructure failed: missing block: B:46:0x00c1, code lost:
            
                if (r5 < java.lang.Math.abs(r5 - r3.collapsedOffset)) goto L50;
             */
            /* JADX WARN: Code restructure failed: missing block: B:48:0x00d2, code lost:
            
                if (java.lang.Math.abs(r5 - r6) < java.lang.Math.abs(r5 - r3.collapsedOffset)) goto L53;
             */
            /* JADX WARN: Code restructure failed: missing block: B:6:0x0017, code lost:
            
                if (r5 > r3.halfExpandedOffset) goto L53;
             */
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onViewReleased(View view, float f, float f2) {
                int i;
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                if (f2 < 0.0f) {
                    if (!bottomSheetBehavior.fitToContents) {
                        int top = view.getTop();
                        System.currentTimeMillis();
                    }
                    i = 3;
                } else if (bottomSheetBehavior.hideable && bottomSheetBehavior.shouldHide(view, f2)) {
                    if (Math.abs(f) >= Math.abs(f2) || f2 <= 500.0f) {
                        if (!(view.getTop() > (bottomSheetBehavior.getExpandedOffset() + bottomSheetBehavior.parentHeight) / 2)) {
                            if (!bottomSheetBehavior.fitToContents) {
                            }
                            i = 3;
                        }
                    }
                    i = 5;
                } else if (f2 == 0.0f || Math.abs(f) > Math.abs(f2)) {
                    int top2 = view.getTop();
                    if (!bottomSheetBehavior.fitToContents) {
                        int i2 = bottomSheetBehavior.halfExpandedOffset;
                        if (top2 < i2) {
                        }
                        i = 6;
                    }
                } else {
                    if (!bottomSheetBehavior.fitToContents) {
                        int top3 = view.getTop();
                    }
                    i = 4;
                }
                bottomSheetBehavior.getClass();
                bottomSheetBehavior.startSettling(view, i, true);
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
            return View.MeasureSpec.makeMeasureSpec(Math.min(size, i3), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        }
        if (size != 0) {
            i3 = Math.min(size, i3);
        }
        return View.MeasureSpec.makeMeasureSpec(i3, VideoPlayer.MEDIA_ERROR_SYSTEM);
    }

    public final void calculateCollapsedOffset() {
        int calculatePeekHeight = calculatePeekHeight();
        if (this.fitToContents) {
            this.collapsedOffset = Math.max(this.parentHeight - calculatePeekHeight, this.fitToContentsOffset);
        } else {
            this.collapsedOffset = this.parentHeight - calculatePeekHeight;
        }
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

    public void disableShapeAnimations() {
        this.interpolatorAnimator = null;
    }

    public final void dispatchOnSlide(int i) {
        View view = (View) this.viewRef.get();
        if (view != null) {
            ArrayList arrayList = this.callbacks;
            if (arrayList.isEmpty()) {
                return;
            }
            int i2 = this.collapsedOffset;
            if (i <= i2 && i2 != getExpandedOffset()) {
                getExpandedOffset();
            }
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                ((BottomSheetCallback) arrayList.get(i3)).onSlide(view);
            }
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
        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Invalid state to get top offset: ", i));
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onDetachedFromLayoutParams() {
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        ViewDragHelper viewDragHelper;
        if (!view.isShown() || !this.draggable) {
            this.ignoreEvents = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.activePointerId = -1;
            VelocityTracker velocityTracker = this.velocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.velocityTracker = null;
            }
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
        if (!this.ignoreEvents && (viewDragHelper = this.viewDragHelper) != null && viewDragHelper.shouldInterceptTouchEvent(motionEvent)) {
            return true;
        }
        WeakReference weakReference2 = this.nestedScrollingChildRef;
        View view3 = weakReference2 != null ? (View) weakReference2.get() : null;
        return (actionMasked != 2 || view3 == null || this.ignoreEvents || this.state == 1 || coordinatorLayout.isPointInChildBounds(view3, (int) motionEvent.getX(), (int) motionEvent.getY()) || this.viewDragHelper == null || Math.abs(((float) this.initialY) - motionEvent.getY()) <= ((float) this.viewDragHelper.mTouchSlop)) ? false : true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api16Impl.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.Api16Impl.getFitsSystemWindows(view)) {
            view.setFitsSystemWindows(true);
        }
        int i2 = 0;
        if (this.viewRef == null) {
            this.peekHeightMin = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            final boolean z = (this.gestureInsetBottomIgnored || this.peekHeightAuto) ? false : true;
            if (this.paddingBottomSystemWindowInsets || this.paddingLeftSystemWindowInsets || this.paddingRightSystemWindowInsets || this.marginLeftSystemWindowInsets || this.marginRightSystemWindowInsets || this.marginTopSystemWindowInsets || z) {
                ViewUtils.doOnApplyWindowInsets(view, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.3
                    /* JADX WARN: Removed duplicated region for block: B:32:0x007a  */
                    /* JADX WARN: Removed duplicated region for block: B:35:0x0088  */
                    @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                        boolean z2;
                        boolean z3;
                        Insets insets = windowInsetsCompat.getInsets(7);
                        Insets insets2 = windowInsetsCompat.getInsets(32);
                        int i3 = insets.top;
                        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                        bottomSheetBehavior.insetTop = i3;
                        boolean isLayoutRtl = ViewUtils.isLayoutRtl(view2);
                        int paddingBottom = view2.getPaddingBottom();
                        int paddingLeft = view2.getPaddingLeft();
                        int paddingRight = view2.getPaddingRight();
                        boolean z4 = bottomSheetBehavior.paddingBottomSystemWindowInsets;
                        if (z4) {
                            int systemWindowInsetBottom = windowInsetsCompat.getSystemWindowInsetBottom();
                            bottomSheetBehavior.insetBottom = systemWindowInsetBottom;
                            paddingBottom = systemWindowInsetBottom + relativePadding.bottom;
                        }
                        boolean z5 = bottomSheetBehavior.paddingLeftSystemWindowInsets;
                        int i4 = insets.left;
                        if (z5) {
                            paddingLeft = (isLayoutRtl ? relativePadding.end : relativePadding.start) + i4;
                        }
                        boolean z6 = bottomSheetBehavior.paddingRightSystemWindowInsets;
                        int i5 = insets.right;
                        if (z6) {
                            paddingRight = (isLayoutRtl ? relativePadding.start : relativePadding.end) + i5;
                        }
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
                        boolean z7 = true;
                        if (!bottomSheetBehavior.marginLeftSystemWindowInsets || marginLayoutParams.leftMargin == i4) {
                            z2 = false;
                        } else {
                            marginLayoutParams.leftMargin = i4;
                            z2 = true;
                        }
                        if (bottomSheetBehavior.marginRightSystemWindowInsets && marginLayoutParams.rightMargin != i5) {
                            marginLayoutParams.rightMargin = i5;
                            z2 = true;
                        }
                        if (bottomSheetBehavior.marginTopSystemWindowInsets) {
                            int i6 = marginLayoutParams.topMargin;
                            int i7 = insets.top;
                            if (i6 != i7) {
                                marginLayoutParams.topMargin = i7;
                                if (z7) {
                                    view2.setLayoutParams(marginLayoutParams);
                                }
                                view2.setPadding(paddingLeft, view2.getPaddingTop(), paddingRight, paddingBottom);
                                z3 = z;
                                if (z3) {
                                    bottomSheetBehavior.gestureInsetBottom = insets2.bottom;
                                }
                                if (!z4 || z3) {
                                    bottomSheetBehavior.updatePeekHeight();
                                }
                                return windowInsetsCompat;
                            }
                        }
                        z7 = z2;
                        if (z7) {
                        }
                        view2.setPadding(paddingLeft, view2.getPaddingTop(), paddingRight, paddingBottom);
                        z3 = z;
                        if (z3) {
                        }
                        if (!z4) {
                        }
                        bottomSheetBehavior.updatePeekHeight();
                        return windowInsetsCompat;
                    }
                });
            }
            this.viewRef = new WeakReference(view);
            MaterialShapeDrawable materialShapeDrawable = this.materialShapeDrawable;
            if (materialShapeDrawable != null) {
                ViewCompat.Api16Impl.setBackground(view, materialShapeDrawable);
                MaterialShapeDrawable materialShapeDrawable2 = this.materialShapeDrawable;
                float f = this.elevation;
                if (f == -1.0f) {
                    f = ViewCompat.Api21Impl.getElevation(view);
                }
                materialShapeDrawable2.setElevation(f);
                boolean z2 = this.state == 3;
                this.isShapeExpanded = z2;
                this.materialShapeDrawable.setInterpolation(z2 ? 0.0f : 1.0f);
            } else {
                ColorStateList colorStateList = this.backgroundTint;
                if (colorStateList != null) {
                    ViewCompat.Api21Impl.setBackgroundTintList(view, colorStateList);
                }
            }
            updateAccessibilityActions$1();
            if (ViewCompat.Api16Impl.getImportantForAccessibility(view) == 0) {
                ViewCompat.Api16Impl.setImportantForAccessibility(view, 1);
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
                this.childHeight = i3;
            } else {
                this.childHeight = i3 - i5;
            }
        }
        this.fitToContentsOffset = Math.max(0, i3 - this.childHeight);
        this.halfExpandedOffset = (int) ((1.0f - this.halfExpandedRatio) * this.parentHeight);
        calculateCollapsedOffset();
        int i6 = this.state;
        if (i6 == 3) {
            view.offsetTopAndBottom(getExpandedOffset());
        } else if (i6 == 6) {
            view.offsetTopAndBottom(this.halfExpandedOffset);
        } else if (this.hideable && i6 == 5) {
            view.offsetTopAndBottom(this.parentHeight);
        } else if (i6 == 4) {
            view.offsetTopAndBottom(this.collapsedOffset);
        } else if (i6 == 1 || i6 == 2) {
            view.offsetTopAndBottom(top - view.getTop());
        }
        this.nestedScrollingChildRef = new WeakReference(findScrollingChild(view));
        while (true) {
            ArrayList arrayList = this.callbacks;
            if (i2 >= arrayList.size()) {
                return true;
            }
            ((BottomSheetCallback) arrayList.get(i2)).onLayout(view);
            i2++;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, coordinatorLayout.getPaddingRight() + coordinatorLayout.getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, this.maxWidth, marginLayoutParams.width), getChildMeasureSpec(i3, coordinatorLayout.getPaddingBottom() + coordinatorLayout.getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + 0, this.maxHeight, marginLayoutParams.height));
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onNestedPreFling(View view, View view2, float f) {
        WeakReference weakReference = this.nestedScrollingChildRef;
        return (weakReference == null || view2 != weakReference.get() || this.state == 3) ? false : true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int[] iArr, int i3) {
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
                int i5 = -expandedOffset;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(i5);
                setStateInternal(3);
            } else {
                if (!this.draggable) {
                    return;
                }
                iArr[1] = i2;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(-i2);
                setStateInternal(1);
            }
        } else if (i2 < 0 && !view2.canScrollVertically(-1)) {
            int i6 = this.collapsedOffset;
            if (i4 > i6 && !this.hideable) {
                int i7 = top - i6;
                iArr[1] = i7;
                int i8 = -i7;
                WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(i8);
                setStateInternal(4);
            } else {
                if (!this.draggable) {
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002f, code lost:
    
        if (r3.getTop() <= r1.halfExpandedOffset) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006f, code lost:
    
        if (java.lang.Math.abs(r2 - r1.fitToContentsOffset) < java.lang.Math.abs(r2 - r1.collapsedOffset)) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x007e, code lost:
    
        if (r2 < java.lang.Math.abs(r2 - r1.collapsedOffset)) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x008e, code lost:
    
        if (java.lang.Math.abs(r2 - r4) < java.lang.Math.abs(r2 - r1.collapsedOffset)) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a9, code lost:
    
        if (java.lang.Math.abs(r2 - r1.halfExpandedOffset) < java.lang.Math.abs(r2 - r1.collapsedOffset)) goto L50;
     */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i) {
        float yVelocity;
        int i2 = 3;
        if (view.getTop() == getExpandedOffset()) {
            setStateInternal(3);
            return;
        }
        WeakReference weakReference = this.nestedScrollingChildRef;
        if (weakReference != null && view2 == weakReference.get() && this.nestedScrolled) {
            if (this.lastNestedScrollDy > 0) {
                if (!this.fitToContents) {
                }
                startSettling(view, i2, false);
                this.nestedScrolled = false;
            }
            if (this.hideable) {
                VelocityTracker velocityTracker = this.velocityTracker;
                if (velocityTracker == null) {
                    yVelocity = 0.0f;
                } else {
                    velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
                    yVelocity = this.velocityTracker.getYVelocity(this.activePointerId);
                }
                if (shouldHide(view, yVelocity)) {
                    i2 = 5;
                    startSettling(view, i2, false);
                    this.nestedScrolled = false;
                }
            }
            if (this.lastNestedScrollDy == 0) {
                int top = view.getTop();
                if (!this.fitToContents) {
                    int i3 = this.halfExpandedOffset;
                    if (top < i3) {
                    }
                    i2 = 6;
                }
            } else {
                if (!this.fitToContents) {
                    int top2 = view.getTop();
                }
                i2 = 4;
            }
            startSettling(view, i2, false);
            this.nestedScrolled = false;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        boolean z = false;
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
            this.activePointerId = -1;
            VelocityTracker velocityTracker = this.velocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.velocityTracker = null;
            }
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (this.viewDragHelper != null && (this.draggable || this.state == 1)) {
            z = true;
        }
        if (z && actionMasked == 2 && !this.ignoreEvents) {
            float abs = Math.abs(this.initialY - motionEvent.getY());
            ViewDragHelper viewDragHelper2 = this.viewDragHelper;
            if (abs > viewDragHelper2.mTouchSlop) {
                viewDragHelper2.captureChildView(view, motionEvent.getPointerId(motionEvent.getActionIndex()));
            }
        }
        return !this.ignoreEvents;
    }

    public final void setHideable(boolean z) {
        if (this.hideable != z) {
            this.hideable = z;
            if (!z && this.state == 5) {
                setState(4);
            }
            updateAccessibilityActions$1();
        }
    }

    public final void setPeekHeight(int i) {
        boolean z = false;
        if (i == -1) {
            if (!this.peekHeightAuto) {
                this.peekHeightAuto = true;
                z = true;
            }
        } else if (this.peekHeightAuto || this.peekHeight != i) {
            this.peekHeightAuto = false;
            this.peekHeight = Math.max(0, i);
            z = true;
        }
        if (z) {
            updatePeekHeight();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0052, code lost:
    
        if (androidx.core.view.ViewCompat.Api19Impl.isAttachedToWindow(r4) != false) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setState(int i) {
        boolean z = true;
        if (i == 1 || i == 2) {
            throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("STATE_"), i == 1 ? "DRAGGING" : "SETTLING", " should not be set externally."));
        }
        if (!this.hideable && i == 5) {
            IconCompat$$ExternalSyntheticOutline0.m30m("Cannot set state: ", i, "BottomSheetBehavior");
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
        }
        z = false;
        if (z) {
            view.post(runnable);
        } else {
            runnable.run();
        }
    }

    public final void setStateInternal(int i) {
        View view;
        if (this.state == i) {
            return;
        }
        this.state = i;
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || (view = (View) weakReference.get()) == null) {
            return;
        }
        int i2 = 0;
        if (i == 3) {
            updateImportantForAccessibility(true);
        } else if (i == 6 || i == 5 || i == 4) {
            updateImportantForAccessibility(false);
        }
        updateDrawableForTargetState(i);
        while (true) {
            ArrayList arrayList = this.callbacks;
            if (i2 >= arrayList.size()) {
                updateAccessibilityActions$1();
                return;
            } else {
                ((BottomSheetCallback) arrayList.get(i2)).onStateChanged(view, i);
                i2++;
            }
        }
    }

    public final boolean shouldHide(View view, float f) {
        if (this.skipCollapsed) {
            return true;
        }
        if (view.getTop() < this.collapsedOffset) {
            return false;
        }
        return Math.abs(((f * this.hideFriction) + ((float) view.getTop())) - ((float) this.collapsedOffset)) / ((float) calculatePeekHeight()) > 0.5f;
    }

    public final void startSettling(View view, int i, boolean z) {
        int topOffsetForState = getTopOffsetForState(i);
        ViewDragHelper viewDragHelper = this.viewDragHelper;
        if (!(viewDragHelper != null && (!z ? !viewDragHelper.smoothSlideViewTo(view, view.getLeft(), topOffsetForState) : !viewDragHelper.settleCapturedViewAt(view.getLeft(), topOffsetForState)))) {
            setStateInternal(i);
            return;
        }
        setStateInternal(2);
        updateDrawableForTargetState(i);
        this.stateSettlingTracker.continueSettlingToState(i);
    }

    public final void updateAccessibilityActions$1() {
        View view;
        int i;
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || (view = (View) weakReference.get()) == null) {
            return;
        }
        ViewCompat.removeActionWithId(view, 524288);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        ViewCompat.removeActionWithId(view, 262144);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        ViewCompat.removeActionWithId(view, QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        int i2 = this.expandHalfwayActionId;
        if (i2 != -1) {
            ViewCompat.removeActionWithId(view, i2);
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        }
        if (!this.fitToContents && this.state != 6) {
            String string = view.getResources().getString(R.string.bottomsheet_action_expand_halfway);
            AccessibilityViewCommand accessibilityViewCommand = new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view2) {
                    BottomSheetBehavior.this.setState(r2);
                    return true;
                }
            };
            List actionList = ViewCompat.getActionList(view);
            int i3 = 0;
            while (true) {
                if (i3 >= actionList.size()) {
                    int i4 = 0;
                    int i5 = -1;
                    while (true) {
                        int[] iArr = ViewCompat.ACCESSIBILITY_ACTIONS_RESOURCE_IDS;
                        if (i4 >= iArr.length || i5 != -1) {
                            break;
                        }
                        int i6 = iArr[i4];
                        boolean z = true;
                        for (int i7 = 0; i7 < actionList.size(); i7++) {
                            z &= ((AccessibilityNodeInfoCompat.AccessibilityActionCompat) actionList.get(i7)).getId() != i6;
                        }
                        if (z) {
                            i5 = i6;
                        }
                        i4++;
                    }
                    i = i5;
                } else {
                    if (TextUtils.equals(string, ((AccessibilityNodeInfo.AccessibilityAction) ((AccessibilityNodeInfoCompat.AccessibilityActionCompat) actionList.get(i3)).mAction).getLabel())) {
                        i = ((AccessibilityNodeInfoCompat.AccessibilityActionCompat) actionList.get(i3)).getId();
                        break;
                    }
                    i3++;
                }
            }
            if (i != -1) {
                AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i, string, accessibilityViewCommand);
                View.AccessibilityDelegate accessibilityDelegate = ViewCompat.Api29Impl.getAccessibilityDelegate(view);
                AccessibilityDelegateCompat accessibilityDelegateCompat = accessibilityDelegate == null ? null : accessibilityDelegate instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter ? ((AccessibilityDelegateCompat.AccessibilityDelegateAdapter) accessibilityDelegate).mCompat : new AccessibilityDelegateCompat(accessibilityDelegate);
                if (accessibilityDelegateCompat == null) {
                    accessibilityDelegateCompat = new AccessibilityDelegateCompat();
                }
                ViewCompat.setAccessibilityDelegate(view, accessibilityDelegateCompat);
                ViewCompat.removeActionWithId(view, accessibilityActionCompat.getId());
                ViewCompat.getActionList(view).add(accessibilityActionCompat);
                ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
            }
            this.expandHalfwayActionId = i;
        }
        if (this.hideable) {
            final int i8 = 5;
            if (this.state != 5) {
                ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, null, new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
                    @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                    public final boolean perform(View view2) {
                        BottomSheetBehavior.this.setState(i8);
                        return true;
                    }
                });
            }
        }
        int i9 = this.state;
        final int i10 = 4;
        final int i11 = 3;
        if (i9 == 3) {
            r5 = this.fitToContents ? 4 : 6;
            ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, null, new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view2) {
                    BottomSheetBehavior.this.setState(r2);
                    return true;
                }
            });
        } else if (i9 == 4) {
            r5 = this.fitToContents ? 3 : 6;
            ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, null, new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view2) {
                    BottomSheetBehavior.this.setState(r2);
                    return true;
                }
            });
        } else {
            if (i9 != 6) {
                return;
            }
            ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, null, new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view2) {
                    BottomSheetBehavior.this.setState(i10);
                    return true;
                }
            });
            ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, null, new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view2) {
                    BottomSheetBehavior.this.setState(i11);
                    return true;
                }
            });
        }
    }

    public final void updateDrawableForTargetState(int i) {
        ValueAnimator valueAnimator;
        if (i == 2) {
            return;
        }
        boolean z = i == 3;
        if (this.isShapeExpanded != z) {
            this.isShapeExpanded = z;
            if (this.materialShapeDrawable == null || (valueAnimator = this.interpolatorAnimator) == null) {
                return;
            }
            if (valueAnimator.isRunning()) {
                this.interpolatorAnimator.reverse();
                return;
            }
            float f = z ? 0.0f : 1.0f;
            this.interpolatorAnimator.setFloatValues(1.0f - f, f);
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

    /* JADX WARN: Type inference failed for: r6v2, types: [com.google.android.material.bottomsheet.BottomSheetBehavior$4] */
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
        this.expandHalfwayActionId = -1;
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.4
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int clampViewPositionHorizontal(View view, int i2) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int clampViewPositionVertical(View view, int i2) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return MathUtils.clamp(i2, bottomSheetBehavior.getExpandedOffset(), bottomSheetBehavior.hideable ? bottomSheetBehavior.parentHeight : bottomSheetBehavior.collapsedOffset);
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
            public final void onViewPositionChanged(View view, int i2, int i22, int i3) {
                BottomSheetBehavior.this.dispatchOnSlide(i22);
            }

            /* JADX WARN: Code restructure failed: missing block: B:28:0x006c, code lost:
            
                if (java.lang.Math.abs(r4.getTop() - r3.getExpandedOffset()) < java.lang.Math.abs(r4.getTop() - r3.halfExpandedOffset)) goto L50;
             */
            /* JADX WARN: Code restructure failed: missing block: B:37:0x0098, code lost:
            
                if (java.lang.Math.abs(r5 - r3.halfExpandedOffset) < java.lang.Math.abs(r5 - r3.collapsedOffset)) goto L53;
             */
            /* JADX WARN: Code restructure failed: missing block: B:42:0x00b2, code lost:
            
                if (java.lang.Math.abs(r5 - r3.fitToContentsOffset) < java.lang.Math.abs(r5 - r3.collapsedOffset)) goto L50;
             */
            /* JADX WARN: Code restructure failed: missing block: B:46:0x00c1, code lost:
            
                if (r5 < java.lang.Math.abs(r5 - r3.collapsedOffset)) goto L50;
             */
            /* JADX WARN: Code restructure failed: missing block: B:48:0x00d2, code lost:
            
                if (java.lang.Math.abs(r5 - r6) < java.lang.Math.abs(r5 - r3.collapsedOffset)) goto L53;
             */
            /* JADX WARN: Code restructure failed: missing block: B:6:0x0017, code lost:
            
                if (r5 > r3.halfExpandedOffset) goto L53;
             */
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onViewReleased(View view, float f, float f2) {
                int i2;
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                if (f2 < 0.0f) {
                    if (!bottomSheetBehavior.fitToContents) {
                        int top = view.getTop();
                        System.currentTimeMillis();
                    }
                    i2 = 3;
                } else if (bottomSheetBehavior.hideable && bottomSheetBehavior.shouldHide(view, f2)) {
                    if (Math.abs(f) >= Math.abs(f2) || f2 <= 500.0f) {
                        if (!(view.getTop() > (bottomSheetBehavior.getExpandedOffset() + bottomSheetBehavior.parentHeight) / 2)) {
                            if (!bottomSheetBehavior.fitToContents) {
                            }
                            i2 = 3;
                        }
                    }
                    i2 = 5;
                } else if (f2 == 0.0f || Math.abs(f) > Math.abs(f2)) {
                    int top2 = view.getTop();
                    if (!bottomSheetBehavior.fitToContents) {
                        int i22 = bottomSheetBehavior.halfExpandedOffset;
                        if (top2 < i22) {
                        }
                        i2 = 6;
                    }
                } else {
                    if (!bottomSheetBehavior.fitToContents) {
                        int top3 = view.getTop();
                    }
                    i2 = 4;
                }
                bottomSheetBehavior.getClass();
                bottomSheetBehavior.startSettling(view, i2, true);
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
        if (obtainStyledAttributes.hasValue(20)) {
            this.shapeAppearanceModelDefault = ShapeAppearanceModel.builder(context, attributeSet, R.attr.bottomSheetStyle, 2132018815).build();
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
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.interpolatorAnimator = ofFloat;
        ofFloat.setDuration(500L);
        this.interpolatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.2
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
        setHideable(obtainStyledAttributes.getBoolean(8, false));
        this.gestureInsetBottomIgnored = obtainStyledAttributes.getBoolean(12, false);
        boolean z = obtainStyledAttributes.getBoolean(6, true);
        if (this.fitToContents != z) {
            this.fitToContents = z;
            if (this.viewRef != null) {
                calculateCollapsedOffset();
            }
            setStateInternal((this.fitToContents && this.state == 6) ? 3 : this.state);
            updateAccessibilityActions$1();
        }
        this.skipCollapsed = obtainStyledAttributes.getBoolean(11, false);
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
                } else {
                    throw new IllegalArgumentException("offset must be greater than or equal to 0");
                }
            } else {
                int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(5, 0);
                if (dimensionPixelOffset >= 0) {
                    this.expandedOffset = dimensionPixelOffset;
                } else {
                    throw new IllegalArgumentException("offset must be greater than or equal to 0");
                }
            }
            this.paddingBottomSystemWindowInsets = obtainStyledAttributes.getBoolean(16, false);
            this.paddingLeftSystemWindowInsets = obtainStyledAttributes.getBoolean(17, false);
            this.paddingRightSystemWindowInsets = obtainStyledAttributes.getBoolean(18, false);
            this.paddingTopSystemWindowInsets = obtainStyledAttributes.getBoolean(19, true);
            this.marginLeftSystemWindowInsets = obtainStyledAttributes.getBoolean(13, false);
            this.marginRightSystemWindowInsets = obtainStyledAttributes.getBoolean(14, false);
            this.marginTopSystemWindowInsets = obtainStyledAttributes.getBoolean(15, false);
            obtainStyledAttributes.recycle();
            this.maximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
            return;
        }
        throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class BottomSheetCallback {
        public abstract void onSlide(View view);

        public abstract void onStateChanged(View view, int i);

        public void onLayout(View view) {
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4, int i5, int[] iArr) {
    }
}

package com.google.android.material.appbar;

import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.appcompat.animation.SeslAnimationUtils;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.util.SeslMisc;
import androidx.coordinatorlayout.widget.AppBarLayoutBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.math.MathUtils;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.SeslImmersiveScrollBehavior;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AppBarLayout extends LinearLayout implements CoordinatorLayout.AttachedBehavior, AppBarLayoutBehavior {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Behavior behavior;
    public int currentOffset;
    public int downPreScrollRange;
    public int downScrollRange;
    public ValueAnimator elevationOverlayAnimator;
    public boolean haveChildWithInterpolator;
    public boolean isMouse;
    public WindowInsetsCompat lastInsets;
    public boolean liftOnScroll;
    public final List liftOnScrollListeners;
    public WeakReference liftOnScrollTargetView;
    public final int liftOnScrollTargetViewId;
    public boolean liftable;
    public boolean lifted;
    public List listeners;
    public final SeslAppbarState mAppbarState;
    public Drawable mBackground;
    public int mBottomPadding;
    public float mCollapsedHeight;
    public int mCurrentOrientation;
    public int mCurrentScreenHeight;
    public final float mCustomHeightProportion;
    public float mHeightProportion;
    public int mImmersiveTopInset;
    public boolean mIsActivatedByUser;
    public boolean mIsActivatedImmersiveScroll;
    public boolean mIsCanScroll;
    public boolean mIsDetachedState;
    public Insets mLastSysInsets;
    public Insets mLastTappableInsets;
    public final Resources mResources;
    public final boolean mSetCustomProportion;
    public boolean mUseCollapsedHeight;
    public final boolean mUseCustomHeight;
    public final boolean mUseCustomPadding;
    public int pendingAction;
    public Drawable statusBarForeground;
    public int[] tmpStatesArray;
    public int totalScrollRange;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class BaseBehavior<T extends AppBarLayout> extends HeaderBehavior<T> {
        public boolean coordinatorLayoutA11yScrollable;
        public WeakReference lastNestedScrollingChildRef;
        public int lastStartedType;
        public float mDiffY_Touch;
        public boolean mDirectTouchAppbar;
        public boolean mIsFlingScrollDown;
        public boolean mIsFlingScrollUp;
        public boolean mIsScrollHold;
        public boolean mIsSetStaticDuration;
        public float mLastMotionY_Touch;
        public boolean mLifted;
        public boolean mToolisMouse;
        public int mTouchSlop;
        public float mVelocity;
        public ValueAnimator offsetAnimator;
        public int offsetDelta;
        public SavedState savedState;

        public BaseBehavior() {
            this.mIsFlingScrollDown = false;
            this.mIsFlingScrollUp = false;
            this.mDirectTouchAppbar = false;
            this.mTouchSlop = -1;
            this.mVelocity = 0.0f;
            this.mIsSetStaticDuration = false;
            this.mIsScrollHold = false;
        }

        public static View findFirstScrollingChild(CoordinatorLayout coordinatorLayout) {
            int childCount = coordinatorLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if ((childAt instanceof NestedScrollingChild2) || (childAt instanceof ListView) || (childAt instanceof ScrollView)) {
                    return childAt;
                }
            }
            return null;
        }

        public static int getChildIndexOnOffset(AppBarLayout appBarLayout, int i) {
            int paddingBottom = i + (appBarLayout.lifted ? appBarLayout.getPaddingBottom() : 0);
            int childCount = appBarLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = appBarLayout.getChildAt(i2);
                int top = childAt.getTop();
                int bottom = childAt.getBottom();
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if ((layoutParams.scrollFlags & 32) == 32) {
                    top -= ((LinearLayout.LayoutParams) layoutParams).topMargin;
                    bottom += ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                }
                int i3 = -paddingBottom;
                if (top <= i3 && bottom >= i3) {
                    return i2;
                }
            }
            return -1;
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x006d  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x007b  */
        /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static void updateAppBarLayoutDrawableState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2) {
            View view;
            boolean z;
            int abs = Math.abs(i);
            int childCount = appBarLayout.getChildCount();
            boolean z2 = false;
            int i3 = 0;
            while (true) {
                if (i3 >= childCount) {
                    view = null;
                    break;
                }
                view = appBarLayout.getChildAt(i3);
                if (abs >= view.getTop() && abs <= view.getBottom()) {
                    break;
                } else {
                    i3++;
                }
            }
            if (view != null) {
                int i4 = ((LayoutParams) view.getLayoutParams()).scrollFlags;
                if ((i4 & 1) != 0) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    int minimumHeight = ViewCompat.Api16Impl.getMinimumHeight(view);
                    if (i2 <= 0 || (i4 & 12) == 0 ? !((i4 & 2) == 0 || (-i) < ((view.getBottom() - minimumHeight) - appBarLayout.getTopInset()) - appBarLayout.getImmersiveTopInset()) : (-i) >= ((view.getBottom() - minimumHeight) - appBarLayout.getTopInset()) - appBarLayout.getImmersiveTopInset()) {
                        z = true;
                        if (appBarLayout.liftOnScroll) {
                            z = appBarLayout.shouldLift(findFirstScrollingChild(coordinatorLayout));
                        }
                        if (appBarLayout.setLiftedState(z)) {
                            return;
                        }
                        ArrayList arrayList = (ArrayList) coordinatorLayout.mChildDag.mGraph.get(appBarLayout);
                        List arrayList2 = arrayList != null ? new ArrayList(arrayList) : null;
                        if (arrayList2 == null) {
                            arrayList2 = Collections.emptyList();
                        }
                        int size = arrayList2.size();
                        int i5 = 0;
                        while (true) {
                            if (i5 >= size) {
                                break;
                            }
                            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) ((View) arrayList2.get(i5)).getLayoutParams()).mBehavior;
                            if (!(behavior instanceof ScrollingViewBehavior)) {
                                i5++;
                            } else if (((ScrollingViewBehavior) behavior).overlayTop != 0) {
                                z2 = true;
                            }
                        }
                        if (z2) {
                            appBarLayout.jumpDrawablesToCurrentState();
                            return;
                        }
                        return;
                    }
                }
            }
            z = false;
            if (appBarLayout.liftOnScroll) {
            }
            if (appBarLayout.setLiftedState(z)) {
            }
        }

        public final void animateOffsetTo(final CoordinatorLayout coordinatorLayout, final AppBarLayout appBarLayout, int i) {
            float abs = Math.abs(this.mVelocity);
            int i2 = IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend;
            int abs2 = (abs <= 0.0f || Math.abs(this.mVelocity) > 3000.0f) ? 250 : (int) ((3000.0f - Math.abs(this.mVelocity)) * 0.4d);
            if (abs2 <= 250) {
                abs2 = 250;
            }
            if (this.mIsSetStaticDuration) {
                this.mIsSetStaticDuration = false;
            } else {
                i2 = abs2;
            }
            if (Math.abs(this.mVelocity) < 2000.0f) {
                int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
                if (topBottomOffsetForScrollingSibling == i) {
                    ValueAnimator valueAnimator = this.offsetAnimator;
                    if (valueAnimator != null && valueAnimator.isRunning()) {
                        this.offsetAnimator.cancel();
                    }
                } else {
                    ValueAnimator valueAnimator2 = this.offsetAnimator;
                    if (valueAnimator2 == null) {
                        ValueAnimator valueAnimator3 = new ValueAnimator();
                        this.offsetAnimator = valueAnimator3;
                        valueAnimator3.setInterpolator(SeslAnimationUtils.SINE_OUT_80);
                        this.offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator4) {
                                BaseBehavior.this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, ((Integer) valueAnimator4.getAnimatedValue()).intValue());
                            }
                        });
                    } else {
                        valueAnimator2.cancel();
                    }
                    this.offsetAnimator.setDuration(Math.min(i2, 450));
                    this.offsetAnimator.setIntValues(topBottomOffsetForScrollingSibling, i);
                    this.offsetAnimator.start();
                }
            }
            this.mVelocity = 0.0f;
        }

        @Override // com.google.android.material.appbar.HeaderBehavior
        public final boolean canDragView(View view) {
            View view2;
            WeakReference weakReference = this.lastNestedScrollingChildRef;
            return weakReference == null || !((view2 = (View) weakReference.get()) == null || !view2.isShown() || view2.canScrollVertically(-1));
        }

        @Override // com.google.android.material.appbar.HeaderBehavior
        public final int getMaxDragOffset(View view) {
            return -((AppBarLayout) view).getDownNestedScrollRange();
        }

        @Override // com.google.android.material.appbar.HeaderBehavior
        public final int getScrollRangeForDragFling(View view) {
            return ((AppBarLayout) view).getTotalScrollRange();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public final int getTopBottomOffsetForScrollingSibling() {
            return getTopAndBottomOffset() + this.offsetDelta;
        }

        public boolean isOffsetAnimatorRunning() {
            ValueAnimator valueAnimator = this.offsetAnimator;
            return valueAnimator != null && valueAnimator.isRunning();
        }

        @Override // com.google.android.material.appbar.HeaderBehavior
        public final void onFlingFinished(View view, CoordinatorLayout coordinatorLayout) {
            OverScroller overScroller = this.scroller;
            if (overScroller != null) {
                overScroller.forceFinished(true);
            }
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            int round;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            super.onLayoutChild(coordinatorLayout, appBarLayout, i);
            int i2 = appBarLayout.pendingAction;
            SavedState savedState = this.savedState;
            if (savedState == null || (i2 & 8) != 0) {
                if (i2 != 0) {
                    boolean z = (i2 & 4) != 0;
                    if ((i2 & 2) != 0) {
                        float seslGetCollapsedHeight = (((appBarLayout.mIsCanScroll && (((Behavior) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).mBehavior) instanceof SeslImmersiveScrollBehavior)) ? ((int) appBarLayout.seslGetCollapsedHeight()) + 0 : 0) + (-appBarLayout.getTotalScrollRange())) - appBarLayout.getImmersiveTopInset();
                        if (z) {
                            animateOffsetTo(coordinatorLayout, appBarLayout, (int) seslGetCollapsedHeight);
                        } else {
                            setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, (int) seslGetCollapsedHeight);
                        }
                    } else if ((i2 & 512) != 0) {
                        float seslGetCollapsedHeight2 = ((appBarLayout.mIsCanScroll && (((Behavior) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).mBehavior) instanceof SeslImmersiveScrollBehavior)) ? ((int) appBarLayout.seslGetCollapsedHeight()) + 0 : 0) + (-appBarLayout.getTotalScrollRange());
                        if (coordinatorLayout.getContext().getResources().getConfiguration().orientation == 1 && appBarLayout.getImmersiveTopInset() == 0 && appBarLayout.mHeightProportion == 0.0f) {
                            seslGetCollapsedHeight2 = 0.0f;
                        }
                        if (z) {
                            animateOffsetTo(coordinatorLayout, appBarLayout, (int) seslGetCollapsedHeight2);
                        } else {
                            setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, (int) seslGetCollapsedHeight2);
                        }
                    } else if ((i2 & 1) != 0) {
                        if (z) {
                            animateOffsetTo(coordinatorLayout, appBarLayout, 0);
                        } else {
                            setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, 0);
                        }
                    }
                }
            } else if (savedState.fullyScrolled) {
                setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, -appBarLayout.getTotalScrollRange());
            } else if (savedState.fullyExpanded) {
                setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, 0);
            } else {
                View childAt = appBarLayout.getChildAt(savedState.firstVisibleChildIndex);
                int i3 = -childAt.getBottom();
                if (this.savedState.firstVisibleChildAtMinimumHeight) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    round = appBarLayout.getTopInset() + ViewCompat.Api16Impl.getMinimumHeight(childAt) + i3;
                } else {
                    round = Math.round(childAt.getHeight() * this.savedState.firstVisibleChildPercentageShown) + i3;
                }
                setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, round);
            }
            appBarLayout.pendingAction = 0;
            this.savedState = null;
            int clamp = MathUtils.clamp(getTopAndBottomOffset(), -appBarLayout.getTotalScrollRange(), 0);
            ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
            if (viewOffsetHelper != null) {
                viewOffsetHelper.setTopAndBottomOffset(clamp);
            } else {
                this.tempTopBottomOffset = clamp;
            }
            updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, getTopAndBottomOffset(), 0);
            appBarLayout.onOffsetChanged(getTopAndBottomOffset());
            updateAccessibilityActions(coordinatorLayout, appBarLayout);
            return true;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onNestedPreFling(View view, View view2, float f) {
            this.mVelocity = f;
            if (f < -300.0f) {
                this.mIsFlingScrollDown = true;
                this.mIsFlingScrollUp = false;
            } else {
                if (f <= 300.0f) {
                    this.mVelocity = 0.0f;
                    this.mIsFlingScrollDown = false;
                    this.mIsFlingScrollUp = false;
                    return true;
                }
                this.mIsFlingScrollDown = false;
                this.mIsFlingScrollUp = true;
            }
            return false;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final void onRestoreInstanceState(View view, Parcelable parcelable) {
            if (!(parcelable instanceof SavedState)) {
                this.savedState = null;
            } else {
                SavedState savedState = this.savedState;
                this.savedState = (SavedState) parcelable;
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final Parcelable onSaveInstanceState(View view) {
            AbsSavedState absSavedState = View.BaseSavedState.EMPTY_STATE;
            SavedState saveScrollState = saveScrollState(absSavedState, (AppBarLayout) view);
            return saveScrollState == null ? absSavedState : saveScrollState;
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x0027, code lost:
        
            if (((r4.getTotalScrollRange() != 0) && r3.getHeight() - r5.getHeight() <= r4.getHeight()) != false) goto L16;
         */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0043  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x004b  */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i, int i2) {
            boolean z;
            float seslGetCollapsedHeight;
            float height;
            ValueAnimator valueAnimator;
            if ((i & 2) != 0) {
                if (!appBarLayout.liftOnScroll) {
                }
                z = true;
                if (z && (valueAnimator = this.offsetAnimator) != null) {
                    valueAnimator.cancel();
                }
                if (appBarLayout.getBottom() > appBarLayout.seslGetCollapsedHeight()) {
                    this.mLifted = true;
                    appBarLayout.setLiftedState(true);
                    this.mDiffY_Touch = 0.0f;
                } else {
                    this.mLifted = false;
                    appBarLayout.setLiftedState(false);
                }
                if (!appBarLayout.mUseCollapsedHeight && (appBarLayout.getImmBehavior() == null || !appBarLayout.mIsCanScroll)) {
                    seslGetCollapsedHeight = appBarLayout.seslGetCollapsedHeight();
                    height = appBarLayout.getHeight() - appBarLayout.getTotalScrollRange();
                    if (height != seslGetCollapsedHeight && height > 0.0f) {
                        Log.i("AppBarLayout", "Internal collapsedHeight/ oldCollapsedHeight :" + seslGetCollapsedHeight + " newCollapsedHeight :" + height);
                        appBarLayout.mUseCollapsedHeight = false;
                        appBarLayout.mCollapsedHeight = height;
                        appBarLayout.updateInternalHeight();
                    }
                }
                this.lastNestedScrollingChildRef = null;
                this.lastStartedType = i2;
                this.mToolisMouse = appBarLayout.isMouse;
                return z;
            }
            z = false;
            if (z) {
                valueAnimator.cancel();
            }
            if (appBarLayout.getBottom() > appBarLayout.seslGetCollapsedHeight()) {
            }
            if (!appBarLayout.mUseCollapsedHeight) {
                seslGetCollapsedHeight = appBarLayout.seslGetCollapsedHeight();
                height = appBarLayout.getHeight() - appBarLayout.getTotalScrollRange();
                if (height != seslGetCollapsedHeight) {
                    Log.i("AppBarLayout", "Internal collapsedHeight/ oldCollapsedHeight :" + seslGetCollapsedHeight + " newCollapsedHeight :" + height);
                    appBarLayout.mUseCollapsedHeight = false;
                    appBarLayout.mCollapsedHeight = height;
                    appBarLayout.updateInternalHeight();
                }
            }
            this.lastNestedScrollingChildRef = null;
            this.lastStartedType = i2;
            this.mToolisMouse = appBarLayout.isMouse;
            return z;
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0026, code lost:
        
            if (r0 != 3) goto L32;
         */
        @Override // com.google.android.material.appbar.HeaderBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean onTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (this.mTouchSlop < 0) {
                this.mTouchSlop = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
            }
            int action = motionEvent.getAction();
            this.mToolisMouse = appBarLayout.isMouse;
            if (action != 0) {
                if (action != 1) {
                    if (action == 2) {
                        this.mDirectTouchAppbar = true;
                        float y = motionEvent.getY();
                        float f = y - this.mLastMotionY_Touch;
                        if (f != 0.0f) {
                            this.mDiffY_Touch = f;
                        }
                        if (Math.abs(this.mDiffY_Touch) > this.mTouchSlop) {
                            this.mLastMotionY_Touch = y;
                        }
                    }
                }
                if (Math.abs(this.mDiffY_Touch) > 21.0f) {
                    float f2 = this.mDiffY_Touch;
                    if (f2 < 0.0f) {
                        this.mIsFlingScrollUp = true;
                        this.mIsFlingScrollDown = false;
                    } else if (f2 > 0.0f) {
                        this.mIsFlingScrollUp = false;
                        this.mIsFlingScrollDown = true;
                    }
                } else {
                    this.mIsFlingScrollUp = false;
                    this.mIsFlingScrollDown = false;
                    this.mLastMotionY_Touch = 0.0f;
                }
                if (this.mDirectTouchAppbar) {
                    this.mDirectTouchAppbar = false;
                    snapToChildIfNeeded(coordinatorLayout, appBarLayout);
                }
            } else {
                this.mDirectTouchAppbar = true;
                motionEvent.getX();
                this.mLastMotionY_Touch = motionEvent.getY();
                this.mDiffY_Touch = 0.0f;
            }
            return super.onTouchEvent(coordinatorLayout, appBarLayout, motionEvent);
        }

        public final SavedState saveScrollState(Parcelable parcelable, AppBarLayout appBarLayout) {
            int topAndBottomOffset = getTopAndBottomOffset();
            int childCount = appBarLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = appBarLayout.getChildAt(i);
                int bottom = childAt.getBottom() + topAndBottomOffset;
                if (childAt.getTop() + topAndBottomOffset <= 0 && bottom >= 0) {
                    if (parcelable == null) {
                        parcelable = androidx.customview.view.AbsSavedState.EMPTY_STATE;
                    }
                    SavedState savedState = new SavedState(parcelable);
                    boolean z = topAndBottomOffset == 0;
                    savedState.fullyExpanded = z;
                    savedState.fullyScrolled = !z && (-topAndBottomOffset) >= appBarLayout.getTotalScrollRange();
                    savedState.firstVisibleChildIndex = i;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    savedState.firstVisibleChildAtMinimumHeight = bottom == appBarLayout.getTopInset() + ViewCompat.Api16Impl.getMinimumHeight(childAt);
                    savedState.firstVisibleChildPercentageShown = bottom / childAt.getHeight();
                    return savedState;
                }
            }
            return null;
        }

        @Override // com.google.android.material.appbar.HeaderBehavior
        public final int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
            int i4;
            boolean z;
            int i5;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
            int i6 = 0;
            if (i2 == 0 || topBottomOffsetForScrollingSibling < i2 || topBottomOffsetForScrollingSibling > i3) {
                this.offsetDelta = 0;
            } else {
                int clamp = MathUtils.clamp(i, i2, i3);
                if (topBottomOffsetForScrollingSibling != clamp) {
                    if (appBarLayout.haveChildWithInterpolator) {
                        int abs = Math.abs(clamp);
                        int childCount = appBarLayout.getChildCount();
                        int i7 = 0;
                        while (true) {
                            if (i7 >= childCount) {
                                break;
                            }
                            View childAt = appBarLayout.getChildAt(i7);
                            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                            Interpolator interpolator = layoutParams.scrollInterpolator;
                            if (abs < childAt.getTop() || abs > childAt.getBottom()) {
                                i7++;
                            } else if (interpolator != null) {
                                int i8 = layoutParams.scrollFlags;
                                if ((i8 & 1) != 0) {
                                    i5 = childAt.getHeight() + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + 0;
                                    if ((i8 & 2) != 0) {
                                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                        i5 -= ViewCompat.Api16Impl.getMinimumHeight(childAt);
                                    }
                                } else {
                                    i5 = 0;
                                }
                                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                if (ViewCompat.Api16Impl.getFitsSystemWindows(childAt)) {
                                    i5 -= appBarLayout.getTopInset();
                                }
                                if (i5 > 0) {
                                    float f = i5;
                                    i4 = (childAt.getTop() + Math.round(interpolator.getInterpolation((abs - childAt.getTop()) / f) * f)) * Integer.signum(clamp);
                                }
                            }
                        }
                    }
                    i4 = clamp;
                    ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
                    if (viewOffsetHelper != null) {
                        z = viewOffsetHelper.setTopAndBottomOffset(i4);
                    } else {
                        this.tempTopBottomOffset = i4;
                        z = false;
                    }
                    int i9 = topBottomOffsetForScrollingSibling - clamp;
                    this.offsetDelta = clamp - i4;
                    if (z) {
                        while (i6 < appBarLayout.getChildCount()) {
                            LayoutParams layoutParams2 = (LayoutParams) appBarLayout.getChildAt(i6).getLayoutParams();
                            ChildScrollEffect childScrollEffect = layoutParams2.scrollEffect;
                            if (childScrollEffect != null && (layoutParams2.scrollFlags & 1) != 0) {
                                childScrollEffect.onOffsetChanged(appBarLayout, appBarLayout.getChildAt(i6), getTopAndBottomOffset());
                            }
                            i6++;
                        }
                    }
                    if (!z && appBarLayout.haveChildWithInterpolator) {
                        coordinatorLayout.dispatchDependentViewsChanged(appBarLayout);
                    }
                    appBarLayout.onOffsetChanged(getTopAndBottomOffset());
                    updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, clamp, clamp < topBottomOffsetForScrollingSibling ? -1 : 1);
                    i6 = i9;
                }
            }
            updateAccessibilityActions(coordinatorLayout, appBarLayout);
            return i6;
        }

        public final void snapToChildIfNeeded(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling() - (appBarLayout.getPaddingTop() + appBarLayout.getTopInset());
            int childIndexOnOffset = getChildIndexOnOffset(appBarLayout, topBottomOffsetForScrollingSibling);
            View childAt = coordinatorLayout.getChildAt(1);
            if (childIndexOnOffset >= 0) {
                View childAt2 = appBarLayout.getChildAt(childIndexOnOffset);
                LayoutParams layoutParams = (LayoutParams) childAt2.getLayoutParams();
                int i = layoutParams.scrollFlags;
                if ((i & 4096) == 4096) {
                    this.mHasNoSnapFlag = true;
                    return;
                }
                this.mHasNoSnapFlag = false;
                if (appBarLayout.getBottom() < appBarLayout.seslGetCollapsedHeight()) {
                    if (appBarLayout.mIsCanScroll) {
                        int seslGetCollapsedHeight = (((int) appBarLayout.seslGetCollapsedHeight()) - appBarLayout.getTotalScrollRange()) + 0;
                        int i2 = -appBarLayout.getTotalScrollRange();
                        int i3 = ((double) (appBarLayout.getBottom() + 0)) >= ((double) appBarLayout.seslGetCollapsedHeight()) * 0.48d ? seslGetCollapsedHeight : i2;
                        if (!this.mIsFlingScrollUp) {
                            i2 = i3;
                        }
                        if (!this.mIsFlingScrollDown) {
                            seslGetCollapsedHeight = i2;
                        }
                        animateOffsetTo(coordinatorLayout, appBarLayout, MathUtils.clamp(seslGetCollapsedHeight, -appBarLayout.getTotalScrollRange(), 0));
                        return;
                    }
                    return;
                }
                int i4 = -childAt2.getTop();
                int i5 = -childAt2.getBottom();
                if (childIndexOnOffset == 0) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (ViewCompat.Api16Impl.getFitsSystemWindows(appBarLayout) && ViewCompat.Api16Impl.getFitsSystemWindows(childAt2)) {
                        i4 -= appBarLayout.getTopInset();
                    }
                }
                if (!((i & 2) == 2)) {
                    if ((i & 5) == 5) {
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        int minimumHeight = ViewCompat.Api16Impl.getMinimumHeight(childAt2) + i5;
                        if (topBottomOffsetForScrollingSibling < minimumHeight) {
                            i4 = minimumHeight;
                        } else {
                            i5 = minimumHeight;
                        }
                    }
                } else if (appBarLayout.mIsCanScroll) {
                    i5 = (int) ((appBarLayout.seslGetCollapsedHeight() - appBarLayout.getPaddingBottom()) + i5);
                } else {
                    WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                    i5 += ViewCompat.Api16Impl.getMinimumHeight(childAt2);
                }
                if ((i & 32) == 32) {
                    i4 += ((LinearLayout.LayoutParams) layoutParams).topMargin;
                    i5 -= ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                }
                int i6 = (!this.mLifted ? ((double) topBottomOffsetForScrollingSibling) < ((double) (i5 + i4)) * 0.43d : ((double) topBottomOffsetForScrollingSibling) < ((double) (i5 + i4)) * 0.52d) ? i4 : i5;
                if (childAt == null) {
                    int i7 = AppBarLayout.$r8$clinit;
                    Log.w("AppBarLayout", "coordinatorLayout.getChildAt(1) is null");
                    i4 = i6;
                } else {
                    if (this.mIsFlingScrollUp) {
                        this.mIsFlingScrollUp = false;
                        this.mIsFlingScrollDown = false;
                    } else {
                        i5 = i6;
                    }
                    if (!this.mIsFlingScrollDown || childAt.getTop() <= appBarLayout.seslGetCollapsedHeight()) {
                        i4 = i5;
                    } else {
                        this.mIsFlingScrollDown = false;
                    }
                }
                animateOffsetTo(coordinatorLayout, appBarLayout, MathUtils.clamp(i4, -appBarLayout.getTotalScrollRange(), 0));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void stopNestedScrollIfNeeded(int i, AppBarLayout appBarLayout, View view, int i2) {
            if (i2 == 1) {
                int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
                if ((i >= 0 || topBottomOffsetForScrollingSibling != 0) && (i <= 0 || topBottomOffsetForScrollingSibling != (-appBarLayout.getDownNestedScrollRange()))) {
                    return;
                }
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (view instanceof NestedScrollingChild2) {
                    ((NestedScrollingChild2) view).stopNestedScroll(1);
                }
            }
        }

        public final void updateAccessibilityActions(final CoordinatorLayout coordinatorLayout, final AppBarLayout appBarLayout) {
            final View findFirstScrollingChild;
            final boolean z;
            boolean z2;
            ViewCompat.removeActionWithId(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
            final boolean z3 = false;
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(coordinatorLayout, 0);
            ViewCompat.removeActionWithId(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(coordinatorLayout, 0);
            if (appBarLayout.getTotalScrollRange() == 0 || (findFirstScrollingChild = findFirstScrollingChild(coordinatorLayout)) == null) {
                return;
            }
            int childCount = appBarLayout.getChildCount();
            int i = 0;
            while (true) {
                z = true;
                if (i >= childCount) {
                    z2 = false;
                    break;
                } else {
                    if (((LayoutParams) appBarLayout.getChildAt(i).getLayoutParams()).scrollFlags != 0) {
                        z2 = true;
                        break;
                    }
                    i++;
                }
            }
            if (z2) {
                if (!(ViewCompat.Api29Impl.getAccessibilityDelegate(coordinatorLayout) != null)) {
                    ViewCompat.setAccessibilityDelegate(coordinatorLayout, new AccessibilityDelegateCompat() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.2
                        @Override // androidx.core.view.AccessibilityDelegateCompat
                        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                            accessibilityNodeInfoCompat.setScrollable(BaseBehavior.this.coordinatorLayoutA11yScrollable);
                            accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
                        }
                    });
                }
                if (getTopBottomOffsetForScrollingSibling() != (-appBarLayout.getTotalScrollRange())) {
                    ViewCompat.replaceAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD, null, new AccessibilityViewCommand(this) { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.4
                        @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                        public final boolean perform(View view) {
                            appBarLayout.setExpanded(z3);
                            return true;
                        }
                    });
                    z3 = true;
                }
                if (getTopBottomOffsetForScrollingSibling() != 0) {
                    if (findFirstScrollingChild.canScrollVertically(-1)) {
                        final int i2 = -appBarLayout.getDownNestedPreScrollRange();
                        if (i2 != 0) {
                            ViewCompat.replaceAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, null, new AccessibilityViewCommand() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.3
                                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                                public final boolean perform(View view) {
                                    BaseBehavior.this.onNestedPreScroll(coordinatorLayout, appBarLayout, findFirstScrollingChild, 0, i2, new int[]{0, 0}, 1);
                                    return true;
                                }
                            });
                        }
                    } else {
                        ViewCompat.replaceAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, null, new AccessibilityViewCommand(this) { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.4
                            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                            public final boolean perform(View view) {
                                appBarLayout.setExpanded(z);
                                return true;
                            }
                        });
                    }
                    this.coordinatorLayoutA11yScrollable = z;
                }
                z = z3;
                this.coordinatorLayoutA11yScrollable = z;
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3) {
            if (((ViewGroup.MarginLayoutParams) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams())).height != -2) {
                return false;
            }
            coordinatorLayout.onMeasureChild(appBarLayout, i, i2, View.MeasureSpec.makeMeasureSpec(0, 0));
            return true;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
            int i4;
            int i5;
            OverScroller overScroller;
            if (i2 != 0) {
                if (i2 < 0) {
                    int i6 = -appBarLayout.getTotalScrollRange();
                    int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange() + i6;
                    this.mIsFlingScrollDown = true;
                    this.mIsFlingScrollUp = false;
                    if (appBarLayout.getBottom() >= appBarLayout.getHeight() * 0.52d) {
                        this.mIsSetStaticDuration = true;
                    }
                    if (i2 < -30) {
                        this.mIsFlingScrollDown = true;
                    } else {
                        this.mVelocity = 0.0f;
                        this.mIsFlingScrollDown = false;
                    }
                    i5 = i6;
                    i4 = downNestedPreScrollRange;
                } else {
                    int i7 = -appBarLayout.getTotalScrollRange();
                    this.mIsFlingScrollDown = false;
                    this.mIsFlingScrollUp = true;
                    if (appBarLayout.getBottom() <= appBarLayout.getHeight() * 0.43d) {
                        this.mIsSetStaticDuration = true;
                    }
                    if (i2 > 30) {
                        this.mIsFlingScrollUp = true;
                    } else {
                        this.mVelocity = 0.0f;
                        this.mIsFlingScrollUp = false;
                    }
                    if (getTopAndBottomOffset() == i7) {
                        this.mIsScrollHold = true;
                    }
                    i4 = 0;
                    i5 = i7;
                }
                if ((this.flingRunnable != null) && (overScroller = this.scroller) != null) {
                    overScroller.forceFinished(true);
                }
                if (i5 != i4) {
                    iArr[1] = scroll(coordinatorLayout, appBarLayout, i2, i5, i4);
                }
            }
            if (appBarLayout.liftOnScroll) {
                appBarLayout.setLiftedState(appBarLayout.shouldLift(view));
            }
            stopNestedScrollIfNeeded(i2, appBarLayout, view, i3);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            int childIndexOnOffset;
            if (!this.mToolisMouse && ((childIndexOnOffset = getChildIndexOnOffset(appBarLayout, getTopBottomOffsetForScrollingSibling())) < 0 || (((LayoutParams) appBarLayout.getChildAt(childIndexOnOffset).getLayoutParams()).scrollFlags & 65536) != 65536)) {
                if (i4 >= 0 || this.mIsScrollHold) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (view instanceof NestedScrollingChild2) {
                        ((NestedScrollingChild2) view).stopNestedScroll(1);
                    }
                } else {
                    iArr[1] = scroll(coordinatorLayout, appBarLayout, i4, -appBarLayout.getDownNestedScrollRange(), 0);
                    stopNestedScrollIfNeeded(i4, appBarLayout, view, i5);
                }
            } else if (i4 < 0) {
                iArr[1] = scroll(coordinatorLayout, appBarLayout, i4, -appBarLayout.getDownNestedScrollRange(), 0);
                stopNestedScrollIfNeeded(i4, appBarLayout, view, i5);
            }
            if (i4 == 0) {
                updateAccessibilityActions(coordinatorLayout, appBarLayout);
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i) {
            int i2;
            int i3 = this.mLastTouchEvent;
            if (i3 == 3 || i3 == 1 || (i2 = this.mLastInterceptTouchEvent) == 3 || i2 == 1) {
                snapToChildIfNeeded(coordinatorLayout, appBarLayout);
            }
            if (this.lastStartedType == 0 || i == 1) {
                if (appBarLayout.liftOnScroll) {
                    appBarLayout.setLiftedState(appBarLayout.shouldLift(view));
                }
                if (this.mIsScrollHold) {
                    this.mIsScrollHold = false;
                }
            }
            this.lastNestedScrollingChildRef = new WeakReference(view);
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class SavedState extends androidx.customview.view.AbsSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.SavedState.1
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
            public boolean firstVisibleChildAtMinimumHeight;
            public int firstVisibleChildIndex;
            public float firstVisibleChildPercentageShown;
            public boolean fullyExpanded;
            public boolean fullyScrolled;

            public SavedState(Parcel parcel, ClassLoader classLoader) {
                super(parcel, classLoader);
                this.fullyScrolled = parcel.readByte() != 0;
                this.fullyExpanded = parcel.readByte() != 0;
                this.firstVisibleChildIndex = parcel.readInt();
                this.firstVisibleChildPercentageShown = parcel.readFloat();
                this.firstVisibleChildAtMinimumHeight = parcel.readByte() != 0;
            }

            @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
            public final void writeToParcel(Parcel parcel, int i) {
                parcel.writeParcelable(this.mSuperState, i);
                parcel.writeByte(this.fullyScrolled ? (byte) 1 : (byte) 0);
                parcel.writeByte(this.fullyExpanded ? (byte) 1 : (byte) 0);
                parcel.writeInt(this.firstVisibleChildIndex);
                parcel.writeFloat(this.firstVisibleChildPercentageShown);
                parcel.writeByte(this.firstVisibleChildAtMinimumHeight ? (byte) 1 : (byte) 0);
            }

            public SavedState(Parcelable parcelable) {
                super(parcelable);
            }
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mIsFlingScrollDown = false;
            this.mIsFlingScrollUp = false;
            this.mDirectTouchAppbar = false;
            this.mTouchSlop = -1;
            this.mVelocity = 0.0f;
            this.mIsSetStaticDuration = false;
            this.mIsScrollHold = false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface BaseOnOffsetChangedListener {
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Behavior extends BaseBehavior<AppBarLayout> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ChildScrollEffect {
        public abstract void onOffsetChanged(AppBarLayout appBarLayout, View view, float f);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CompressChildScrollEffect extends ChildScrollEffect {
        public final Rect relativeRect = new Rect();
        public final Rect ghostRect = new Rect();

        @Override // com.google.android.material.appbar.AppBarLayout.ChildScrollEffect
        public final void onOffsetChanged(AppBarLayout appBarLayout, View view, float f) {
            Rect rect = this.relativeRect;
            view.getDrawingRect(rect);
            appBarLayout.offsetDescendantRectToMyCoords(view, rect);
            rect.offset(0, -appBarLayout.getTopInset());
            float abs = rect.top - Math.abs(f);
            if (abs > 0.0f) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api18Impl.setClipBounds(view, null);
                view.setTranslationY(0.0f);
                return;
            }
            float clamp = 1.0f - MathUtils.clamp(Math.abs(abs / rect.height()), 0.0f, 1.0f);
            float height = (-abs) - ((rect.height() * 0.3f) * (1.0f - (clamp * clamp)));
            view.setTranslationY(height);
            Rect rect2 = this.ghostRect;
            view.getDrawingRect(rect2);
            rect2.offset(0, (int) (-height));
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api18Impl.setClipBounds(view, rect2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnOffsetChangedListener extends BaseOnOffsetChangedListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
        public ScrollingViewBehavior() {
        }

        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        public final AppBarLayout findFirstDependency$1(List list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                View view = (View) list.get(i);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view;
                }
            }
            return null;
        }

        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        public final float getOverlapRatioForOffset(View view) {
            int i;
            if (view instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange();
                CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).mBehavior;
                int topBottomOffsetForScrollingSibling = behavior instanceof BaseBehavior ? ((BaseBehavior) behavior).getTopBottomOffsetForScrollingSibling() : 0;
                if ((downNestedPreScrollRange == 0 || totalScrollRange + topBottomOffsetForScrollingSibling > downNestedPreScrollRange) && (i = totalScrollRange - downNestedPreScrollRange) != 0) {
                    return (topBottomOffsetForScrollingSibling / i) + 1.0f;
                }
            }
            return 0.0f;
        }

        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        public final int getScrollRange(View view) {
            return view instanceof AppBarLayout ? ((AppBarLayout) view).getTotalScrollRange() : view.getMeasuredHeight();
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean layoutDependsOn(View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            int clamp;
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) view2.getLayoutParams()).mBehavior;
            if (behavior instanceof BaseBehavior) {
                int bottom = (view2.getBottom() - view.getTop()) + ((BaseBehavior) behavior).offsetDelta + this.verticalLayoutGap;
                if (this.overlayTop == 0) {
                    clamp = 0;
                } else {
                    float overlapRatioForOffset = getOverlapRatioForOffset(view2);
                    int i = this.overlayTop;
                    clamp = MathUtils.clamp((int) (overlapRatioForOffset * i), 0, i);
                }
                int i2 = bottom - clamp;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(i2);
            }
            if (view2 instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view2;
                if (appBarLayout.liftOnScroll) {
                    appBarLayout.setLiftedState(appBarLayout.shouldLift(view));
                }
            }
            return false;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, View view) {
            if (view instanceof AppBarLayout) {
                ViewCompat.removeActionWithId(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
                ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(coordinatorLayout, 0);
                ViewCompat.removeActionWithId(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
                ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(coordinatorLayout, 0);
                ViewCompat.setAccessibilityDelegate(coordinatorLayout, null);
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z) {
            AppBarLayout appBarLayout;
            List dependencies = coordinatorLayout.getDependencies(view);
            int size = dependencies.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    appBarLayout = null;
                    break;
                }
                View view2 = (View) dependencies.get(i);
                if (view2 instanceof AppBarLayout) {
                    appBarLayout = (AppBarLayout) view2;
                    break;
                }
                i++;
            }
            if (appBarLayout != null) {
                rect.offset(view.getLeft(), view.getTop());
                int width = coordinatorLayout.getWidth();
                int height = coordinatorLayout.getHeight();
                Rect rect2 = this.tempRect1;
                rect2.set(0, 0, width, height);
                if (!rect2.contains(rect)) {
                    appBarLayout.setExpanded(false, !z, true);
                    return true;
                }
            }
            return false;
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ScrollingViewBehavior_Layout);
            this.overlayTop = obtainStyledAttributes.getDimensionPixelSize(0, 0);
            obtainStyledAttributes.recycle();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SeslAppbarState {
        public int mCurrentState = 3;
    }

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public final void addOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList();
        }
        if (onOffsetChangedListener == null || ((ArrayList) this.listeners).contains(onOffsetChangedListener)) {
            return;
        }
        ((ArrayList) this.listeners).add(onOffsetChangedListener);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.View
    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 8) {
            if (this.liftOnScrollTargetView != null) {
                if (motionEvent.getAxisValue(9) < 0.0f) {
                    setExpanded(false);
                } else if (motionEvent.getAxisValue(9) > 0.0f && !canScrollVertically(-1)) {
                    setExpanded(true);
                }
            } else if (motionEvent.getAxisValue(9) < 0.0f) {
                setExpanded(false);
            } else if (motionEvent.getAxisValue(9) > 0.0f) {
                setExpanded(true);
            }
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.statusBarForeground != null && getTopInset() > 0) {
            int save = canvas.save();
            canvas.translate(0.0f, -this.currentOffset);
            this.statusBarForeground.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.statusBarForeground;
        if (drawable != null && drawable.isStateful() && drawable.setState(drawableState)) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateLayoutParams(layoutParams);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    public final CoordinatorLayout.Behavior getBehavior() {
        Behavior behavior = new Behavior();
        this.behavior = behavior;
        return behavior;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getDownNestedPreScrollRange() {
        int i;
        int minimumHeight;
        int i2 = this.downPreScrollRange;
        if (i2 != -1) {
            return i2;
        }
        int childCount = getChildCount() - 1;
        int i3 = 0;
        while (true) {
            if (childCount < 0) {
                break;
            }
            View childAt = getChildAt(childCount);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredHeight = childAt.getMeasuredHeight();
                int i4 = layoutParams.scrollFlags;
                if ((i4 & 5) == 5) {
                    int i5 = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                    if ((i4 & 8) != 0) {
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        minimumHeight = ViewCompat.Api16Impl.getMinimumHeight(childAt);
                    } else if ((i4 & 2) != 0) {
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        minimumHeight = measuredHeight - ViewCompat.Api16Impl.getMinimumHeight(childAt);
                    } else {
                        i = i5 + measuredHeight;
                        if (childCount == 0) {
                            WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                            if (ViewCompat.Api16Impl.getFitsSystemWindows(childAt)) {
                                i = Math.min(i, measuredHeight - getTopInset());
                            }
                        }
                        i3 += i;
                    }
                    i = minimumHeight + i5;
                    if (childCount == 0) {
                    }
                    i3 += i;
                } else if (this.mIsCanScroll) {
                    i3 = (int) (seslGetCollapsedHeight() + 0 + i3);
                }
            }
            childCount--;
        }
        int max = Math.max(0, i3);
        this.downPreScrollRange = max;
        return max;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.view.View] */
    public final int getDownNestedScrollRange() {
        int minimumHeight;
        int i;
        int i2 = this.downScrollRange;
        if (i2 != -1) {
            return i2;
        }
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= childCount) {
                break;
            }
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredHeight = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + childAt.getMeasuredHeight();
                int i5 = layoutParams.scrollFlags;
                if ((i5 & 1) == 0) {
                    break;
                }
                i4 += measuredHeight;
                if ((i5 & 2) != 0) {
                    if (this.mIsCanScroll && (childAt instanceof CollapsingToolbarLayout)) {
                        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) childAt;
                        ViewGroup viewGroup = collapsingToolbarLayout.toolbar;
                        if (viewGroup != null) {
                            ?? r2 = collapsingToolbarLayout.toolbarDirectChild;
                            if (r2 != 0 && r2 != collapsingToolbarLayout) {
                                viewGroup = r2;
                            }
                            ViewGroup.LayoutParams layoutParams2 = viewGroup.getLayoutParams();
                            if (layoutParams2 instanceof ViewGroup.MarginLayoutParams) {
                                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams2;
                                i = marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                minimumHeight = ViewCompat.Api16Impl.getMinimumHeight(collapsingToolbarLayout) - i;
                            }
                        }
                        i = 0;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        minimumHeight = ViewCompat.Api16Impl.getMinimumHeight(collapsingToolbarLayout) - i;
                    } else {
                        WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                        minimumHeight = ViewCompat.Api16Impl.getMinimumHeight(childAt);
                    }
                    i4 -= minimumHeight;
                }
            }
            i3++;
        }
        int max = Math.max(0, i4);
        this.downScrollRange = max;
        return max;
    }

    public final SeslImmersiveScrollBehavior getImmBehavior() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            return null;
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).mBehavior;
        if (behavior instanceof SeslImmersiveScrollBehavior) {
            return (SeslImmersiveScrollBehavior) behavior;
        }
        return null;
    }

    public final int getImmersiveTopInset() {
        if (this.mIsCanScroll) {
            return this.mImmersiveTopInset;
        }
        return 0;
    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int minimumHeight = ViewCompat.Api16Impl.getMinimumHeight(this);
        if (minimumHeight == 0) {
            int childCount = getChildCount();
            minimumHeight = childCount >= 1 ? ViewCompat.Api16Impl.getMinimumHeight(getChildAt(childCount - 1)) : 0;
            if (minimumHeight == 0) {
                return getHeight() / 3;
            }
        }
        return (minimumHeight * 2) + topInset;
    }

    public final int getTopInset() {
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        if (windowInsetsCompat != null) {
            return windowInsetsCompat.getSystemWindowInsetTop();
        }
        return 0;
    }

    public final int getTotalScrollRange() {
        int i = this.totalScrollRange;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredHeight = childAt.getMeasuredHeight();
                int i4 = layoutParams.scrollFlags;
                if ((i4 & 1) == 0) {
                    break;
                }
                int i5 = measuredHeight + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + i3;
                if (i2 == 0) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (ViewCompat.Api16Impl.getFitsSystemWindows(childAt)) {
                        i5 -= getTopInset();
                    }
                }
                i3 = i5;
                if ((i4 & 2) != 0) {
                    if (this.mIsCanScroll) {
                        i3 += getTopInset() + this.mBottomPadding + 0;
                    } else {
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        i3 -= ViewCompat.Api16Impl.getMinimumHeight(childAt);
                    }
                }
            }
            i2++;
        }
        int max = Math.max(0, i3);
        this.totalScrollRange = max;
        return max;
    }

    public final void invalidateScrollRanges() {
        Behavior behavior = this.behavior;
        BaseBehavior.SavedState saveScrollState = (behavior == null || this.totalScrollRange == -1 || this.pendingAction != 0) ? null : behavior.saveScrollState(androidx.customview.view.AbsSavedState.EMPTY_STATE, this);
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        if (saveScrollState != null) {
            Behavior behavior2 = this.behavior;
            if (behavior2.savedState != null) {
                return;
            }
            behavior2.savedState = saveScrollState;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsDetachedState = false;
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            setBackgroundDrawable(drawable == getBackground() ? this.mBackground : getBackground());
        } else if (getBackground() != null) {
            Drawable background = getBackground();
            this.mBackground = background;
            setBackgroundDrawable(background);
        } else {
            this.mBackground = null;
            setBackgroundColor(this.mResources.getColor(SeslMisc.isLightTheme(getContext()) ? R.color.sesl_action_bar_background_color_light : R.color.sesl_action_bar_background_color_dark));
        }
        if (this.mCurrentScreenHeight != configuration.screenHeightDp || this.mCurrentOrientation != configuration.orientation) {
            boolean z = this.mUseCustomPadding;
            if (!z && !this.mUseCollapsedHeight) {
                Log.i("AppBarLayout", "Update bottom padding");
                int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.sesl_extended_appbar_bottom_padding);
                this.mBottomPadding = dimensionPixelSize;
                setPadding(0, 0, 0, dimensionPixelSize);
                float dimensionPixelSize2 = this.mResources.getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding) + this.mBottomPadding;
                this.mUseCollapsedHeight = false;
                this.mCollapsedHeight = dimensionPixelSize2;
            } else if (z && this.mBottomPadding == 0 && !this.mUseCollapsedHeight) {
                float dimensionPixelSize3 = this.mResources.getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding);
                this.mUseCollapsedHeight = false;
                this.mCollapsedHeight = dimensionPixelSize3;
            }
        }
        if (!this.mSetCustomProportion) {
            Resources resources = this.mResources;
            ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
            this.mHeightProportion = resources.getFloat(R.dimen.sesl_appbar_height_proportion);
        }
        updateInternalHeight();
        if (this.lifted || (this.mCurrentOrientation == 1 && configuration.orientation == 2)) {
            setExpanded(false, false, true);
        } else {
            setExpanded(true, false, true);
        }
        this.mCurrentOrientation = configuration.orientation;
        this.mCurrentScreenHeight = configuration.screenHeightDp;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        if (this.tmpStatesArray == null) {
            this.tmpStatesArray = new int[4];
        }
        int[] iArr = this.tmpStatesArray;
        int[] onCreateDrawableState = super.onCreateDrawableState(i + iArr.length);
        boolean z = this.liftable;
        iArr[0] = z ? R.attr.state_liftable : -2130970039;
        iArr[1] = (z && this.lifted) ? R.attr.state_lifted : -2130970040;
        iArr[2] = z ? R.attr.state_collapsible : -2130970032;
        iArr[3] = (z && this.lifted) ? R.attr.state_collapsed : -2130970031;
        return LinearLayout.mergeDrawableStates(onCreateDrawableState, iArr);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        this.mIsDetachedState = true;
        SeslImmersiveScrollBehavior immBehavior = getImmBehavior();
        if (immBehavior != null) {
            Log.i("SeslImmersiveScrollBehavior", "DetachedFromWindow");
            SeslImmersiveScrollBehavior.WindowInsetsControllerOnControllableInsetsChangedListenerC42113 windowInsetsControllerOnControllableInsetsChangedListenerC42113 = immBehavior.mOnInsetsChangedListener;
            if (windowInsetsControllerOnControllableInsetsChangedListenerC42113 != null) {
                immBehavior.mWindowInsetsController.removeOnControllableInsetsChangedListener(windowInsetsControllerOnControllableInsetsChangedListenerC42113);
                immBehavior.mOnInsetsChangedListener = null;
            }
            immBehavior.mAnimationController = null;
            immBehavior.mCancellationSignal = null;
            immBehavior.mShownAtDown = false;
        }
        super.onDetachedFromWindow();
        WeakReference weakReference = this.liftOnScrollTargetView;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.liftOnScrollTargetView = null;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        super.onLayout(z, i, i2, i3, i4);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        boolean z3 = true;
        if (ViewCompat.Api16Impl.getFitsSystemWindows(this) && shouldOffsetFirstChild()) {
            int topInset = getTopInset();
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                getChildAt(childCount).offsetTopAndBottom(topInset);
            }
        }
        invalidateScrollRanges();
        this.haveChildWithInterpolator = false;
        int childCount2 = getChildCount();
        int i5 = 0;
        while (true) {
            if (i5 >= childCount2) {
                break;
            }
            if (((LayoutParams) getChildAt(i5).getLayoutParams()).scrollInterpolator != null) {
                this.haveChildWithInterpolator = true;
                break;
            }
            i5++;
        }
        Drawable drawable = this.statusBarForeground;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getTopInset());
        }
        if (!this.liftOnScroll) {
            int childCount3 = getChildCount();
            int i6 = 0;
            while (true) {
                if (i6 >= childCount3) {
                    z2 = false;
                    break;
                }
                int i7 = ((LayoutParams) getChildAt(i6).getLayoutParams()).scrollFlags;
                if ((i7 & 1) == 1 && (i7 & 10) != 0) {
                    z2 = true;
                    break;
                }
                i6++;
            }
            if (!z2) {
                z3 = false;
            }
        }
        if (this.liftable != z3) {
            this.liftable = z3;
            refreshDrawableState();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        updateInternalHeight();
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        if (mode != 1073741824) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api16Impl.getFitsSystemWindows(this) && shouldOffsetFirstChild()) {
                int measuredHeight = getMeasuredHeight();
                if (mode == Integer.MIN_VALUE) {
                    measuredHeight = MathUtils.clamp(getTopInset() + getMeasuredHeight(), 0, View.MeasureSpec.getSize(i2));
                } else if (mode == 0) {
                    measuredHeight += getTopInset();
                }
                setMeasuredDimension(getMeasuredWidth(), measuredHeight);
            }
        }
        invalidateScrollRanges();
    }

    public final void onOffsetChanged(int i) {
        this.currentOffset = i;
        int totalScrollRange = getTotalScrollRange();
        int height = getHeight() - ((int) seslGetCollapsedHeight());
        if (Math.abs(i) >= totalScrollRange) {
            if (this.mIsCanScroll) {
                SeslAppbarState seslAppbarState = this.mAppbarState;
                if (seslAppbarState.mCurrentState != 2) {
                    seslAppbarState.mCurrentState = 2;
                }
            } else {
                SeslAppbarState seslAppbarState2 = this.mAppbarState;
                if (seslAppbarState2.mCurrentState != 0) {
                    seslAppbarState2.mCurrentState = 0;
                }
            }
        } else if (Math.abs(i) >= height) {
            SeslAppbarState seslAppbarState3 = this.mAppbarState;
            if (seslAppbarState3.mCurrentState != 0) {
                seslAppbarState3.mCurrentState = 0;
            }
        } else if (Math.abs(i) == 0) {
            SeslAppbarState seslAppbarState4 = this.mAppbarState;
            if (seslAppbarState4.mCurrentState != 1) {
                seslAppbarState4.mCurrentState = 1;
            }
        } else {
            SeslAppbarState seslAppbarState5 = this.mAppbarState;
            if (seslAppbarState5.mCurrentState != 3) {
                seslAppbarState5.mCurrentState = 3;
            }
        }
        if (!willNotDraw()) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
        List list = this.listeners;
        if (list != null) {
            int size = ((ArrayList) list).size();
            for (int i2 = 0; i2 < size; i2++) {
                BaseOnOffsetChangedListener baseOnOffsetChangedListener = (BaseOnOffsetChangedListener) ((ArrayList) this.listeners).get(i2);
                if (baseOnOffsetChangedListener != null) {
                    baseOnOffsetChangedListener.onOffsetChanged(this, i);
                }
            }
        }
    }

    public final float seslGetCollapsedHeight() {
        return this.mCollapsedHeight + getImmersiveTopInset();
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeUtils.setElevation(this, f);
    }

    public final void setExpanded(boolean z) {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setExpanded(z, ViewCompat.Api19Impl.isLaidOut(this), true);
    }

    public final boolean setLiftedState(boolean z) {
        if (this.lifted == z) {
            return false;
        }
        this.lifted = z;
        refreshDrawableState();
        if (this.liftOnScroll && (getBackground() instanceof MaterialShapeDrawable)) {
            final MaterialShapeDrawable materialShapeDrawable = (MaterialShapeDrawable) getBackground();
            float dimension = this.mResources.getDimension(R.dimen.sesl_appbar_elevation);
            float f = z ? 0.0f : dimension;
            if (!z) {
                dimension = 0.0f;
            }
            ValueAnimator valueAnimator = this.elevationOverlayAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(f, dimension);
            this.elevationOverlayAnimator = ofFloat;
            ofFloat.setDuration(this.mResources.getInteger(R.integer.app_bar_elevation_anim_duration));
            this.elevationOverlayAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
            this.elevationOverlayAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.AppBarLayout.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    materialShapeDrawable.setElevation(floatValue);
                    Drawable drawable = AppBarLayout.this.statusBarForeground;
                    if (drawable instanceof MaterialShapeDrawable) {
                        ((MaterialShapeDrawable) drawable).setElevation(floatValue);
                    }
                    Iterator it = ((ArrayList) AppBarLayout.this.liftOnScrollListeners).iterator();
                    if (it.hasNext()) {
                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(it.next());
                        int i = materialShapeDrawable.resolvedTintColor;
                        throw null;
                    }
                }
            });
            this.elevationOverlayAnimator.start();
        }
        return true;
    }

    @Override // android.widget.LinearLayout
    public final void setOrientation(int i) {
        if (i != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(i);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.statusBarForeground;
        if (drawable != null) {
            drawable.setVisible(z, false);
        }
    }

    public final boolean shouldLift(View view) {
        int i;
        if (this.liftOnScrollTargetView == null && (i = this.liftOnScrollTargetViewId) != -1) {
            View findViewById = view != null ? view.findViewById(i) : null;
            if (findViewById == null && (getParent() instanceof ViewGroup)) {
                findViewById = ((ViewGroup) getParent()).findViewById(this.liftOnScrollTargetViewId);
            }
            if (findViewById != null) {
                this.liftOnScrollTargetView = new WeakReference(findViewById);
            }
        }
        WeakReference weakReference = this.liftOnScrollTargetView;
        View view2 = weakReference != null ? (View) weakReference.get() : null;
        if (view2 != null) {
            view = view2;
        }
        return view != null && (view.canScrollVertically(-1) || view.getScrollY() > 0);
    }

    public final boolean shouldOffsetFirstChild() {
        if (getChildCount() <= 0) {
            return false;
        }
        View childAt = getChildAt(0);
        if (childAt.getVisibility() == 8) {
            return false;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return !ViewCompat.Api16Impl.getFitsSystemWindows(childAt);
    }

    public final void updateInternalHeight() {
        float f;
        CoordinatorLayout.LayoutParams layoutParams;
        float f2;
        int i = this.mResources.getDisplayMetrics().heightPixels;
        if (this.mUseCustomHeight) {
            float f3 = this.mCustomHeightProportion;
            if (f3 != 0.0f) {
                if (this.mIsCanScroll) {
                    float f4 = this.mResources.getDisplayMetrics().heightPixels;
                    float immersiveTopInset = getImmersiveTopInset();
                    if (f4 == 0.0f) {
                        f4 = 1.0f;
                    }
                    f2 = immersiveTopInset / f4;
                } else {
                    f2 = 0.0f;
                }
                f = f3 + f2;
            } else {
                f = 0.0f;
            }
        } else {
            f = this.mHeightProportion;
        }
        float f5 = i * f;
        if (f5 == 0.0f) {
            if (!this.mUseCollapsedHeight && (getImmBehavior() == null || !this.mIsCanScroll)) {
                float seslGetCollapsedHeight = seslGetCollapsedHeight();
                Log.i("AppBarLayout", "update InternalCollapsedHeight from updateInternalHeight() : " + seslGetCollapsedHeight);
                this.mUseCollapsedHeight = false;
                this.mCollapsedHeight = seslGetCollapsedHeight;
            }
            f5 = seslGetCollapsedHeight();
        }
        try {
            layoutParams = (CoordinatorLayout.LayoutParams) getLayoutParams();
        } catch (ClassCastException e) {
            Log.e("AppBarLayout", Log.getStackTraceString(e));
            layoutParams = null;
        }
        String str = "[updateInternalHeight] orientation : " + this.mResources.getConfiguration().orientation + ", density : " + this.mResources.getConfiguration().densityDpi + ", windowHeight : " + i;
        if (this.mUseCustomHeight) {
            if (this.mSetCustomProportion && layoutParams != null) {
                ((ViewGroup.MarginLayoutParams) layoutParams).height = (int) f5;
                setLayoutParams(layoutParams);
                str = str + ", [1]updateInternalHeight: lp.height : " + ((ViewGroup.MarginLayoutParams) layoutParams).height + ", mCustomHeightProportion : " + this.mCustomHeightProportion;
            }
        } else if (layoutParams != null) {
            ((ViewGroup.MarginLayoutParams) layoutParams).height = (int) f5;
            setLayoutParams(layoutParams);
            str = str + ", [3]updateInternalHeight: lp.height : " + ((ViewGroup.MarginLayoutParams) layoutParams).height + ", mHeightProportion : " + this.mHeightProportion;
        }
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, " , mIsImmersiveScroll : ");
        m2m.append(this.mIsActivatedImmersiveScroll);
        m2m.append(" , mIsSetByUser : ");
        m2m.append(this.mIsActivatedByUser);
        m2m.append(" , mImmersiveTopInset : ");
        m2m.append(this.mImmersiveTopInset);
        String sb = m2m.toString();
        WindowInsets rootWindowInsets = getRootView().getRootWindowInsets();
        if (rootWindowInsets != null) {
            sb = sb + "\n" + rootWindowInsets;
        }
        Log.i("AppBarLayout", sb);
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.statusBarForeground;
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.appBarLayoutStyle);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ LinearLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateLayoutParams(layoutParams);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132018812), attributeSet, i);
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        boolean z = false;
        this.pendingAction = 0;
        this.lifted = false;
        this.liftOnScrollListeners = new ArrayList();
        this.mBottomPadding = 0;
        this.mUseCollapsedHeight = false;
        this.isMouse = false;
        this.mIsDetachedState = false;
        this.mIsActivatedImmersiveScroll = false;
        this.mIsActivatedByUser = false;
        this.mIsCanScroll = false;
        this.mImmersiveTopInset = 0;
        this.mLastTappableInsets = null;
        this.mLastSysInsets = null;
        Context context2 = getContext();
        setOrientation(1);
        Context context3 = getContext();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context3, attributeSet, ViewUtilsLollipop.STATE_LIST_ANIM_ATTRS, i, 2132018812, new int[0]);
        try {
            if (obtainStyledAttributes.hasValue(0)) {
                setStateListAnimator(AnimatorInflater.loadStateListAnimator(context3, obtainStyledAttributes.getResourceId(0, 0)));
            }
            obtainStyledAttributes.recycle();
            TypedArray obtainStyledAttributes2 = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.AppBarLayout, i, 2132018812, new int[0]);
            this.mAppbarState = new SeslAppbarState();
            Resources resources = getResources();
            this.mResources = resources;
            boolean isLightTheme = SeslMisc.isLightTheme(context2);
            if (obtainStyledAttributes2.hasValue(0)) {
                Drawable drawable = obtainStyledAttributes2.getDrawable(0);
                this.mBackground = drawable;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.setBackground(this, drawable);
            } else {
                this.mBackground = null;
                setBackgroundColor(resources.getColor(isLightTheme ? R.color.sesl_action_bar_background_color_light : R.color.sesl_action_bar_background_color_dark));
            }
            if (getBackground() instanceof ColorDrawable) {
                ColorDrawable colorDrawable = (ColorDrawable) getBackground();
                MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
                materialShapeDrawable.setFillColor(ColorStateList.valueOf(colorDrawable.getColor()));
                materialShapeDrawable.initializeElevationOverlay(context2);
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.setBackground(this, materialShapeDrawable);
            }
            if (obtainStyledAttributes2.hasValue(5)) {
                setExpanded(obtainStyledAttributes2.getBoolean(5, false), false, false);
            }
            if (obtainStyledAttributes2.hasValue(4)) {
                ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, obtainStyledAttributes2.getDimensionPixelSize(4, 0));
            }
            if (obtainStyledAttributes2.hasValue(9)) {
                this.mUseCustomHeight = obtainStyledAttributes2.getBoolean(9, false);
            }
            if (obtainStyledAttributes2.hasValue(8)) {
                this.mSetCustomProportion = true;
                this.mCustomHeightProportion = obtainStyledAttributes2.getFloat(8, 0.39f);
            } else {
                this.mSetCustomProportion = false;
                this.mCustomHeightProportion = 0.39f;
            }
            ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
            this.mHeightProportion = resources.getFloat(R.dimen.sesl_appbar_height_proportion);
            if (obtainStyledAttributes2.hasValue(10)) {
                this.mUseCustomPadding = obtainStyledAttributes2.getBoolean(10, false);
            }
            if (this.mUseCustomPadding) {
                this.mBottomPadding = obtainStyledAttributes2.getDimensionPixelSize(1, 0);
            } else {
                this.mBottomPadding = resources.getDimensionPixelOffset(R.dimen.sesl_extended_appbar_bottom_padding);
            }
            setPadding(0, 0, 0, this.mBottomPadding);
            float dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding) + this.mBottomPadding;
            this.mUseCollapsedHeight = false;
            this.mCollapsedHeight = dimensionPixelSize;
            if (obtainStyledAttributes2.hasValue(4)) {
                ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, obtainStyledAttributes2.getDimensionPixelSize(4, 0));
            }
            if (obtainStyledAttributes2.hasValue(3)) {
                setKeyboardNavigationCluster(obtainStyledAttributes2.getBoolean(3, false));
            }
            if (obtainStyledAttributes2.hasValue(2)) {
                setTouchscreenBlocksFocus(obtainStyledAttributes2.getBoolean(2, false));
            }
            this.liftOnScroll = obtainStyledAttributes2.getBoolean(6, false);
            this.liftOnScrollTargetViewId = obtainStyledAttributes2.getResourceId(7, -1);
            Drawable drawable2 = obtainStyledAttributes2.getDrawable(11);
            Drawable drawable3 = this.statusBarForeground;
            if (drawable3 != drawable2) {
                if (drawable3 != null) {
                    drawable3.setCallback(null);
                }
                Drawable mutate = drawable2 != null ? drawable2.mutate() : null;
                this.statusBarForeground = mutate;
                if (mutate != null) {
                    if (mutate.isStateful()) {
                        this.statusBarForeground.setState(getDrawableState());
                    }
                    Drawable drawable4 = this.statusBarForeground;
                    WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                    drawable4.setLayoutDirection(ViewCompat.Api17Impl.getLayoutDirection(this));
                    this.statusBarForeground.setVisible(getVisibility() == 0, false);
                    this.statusBarForeground.setCallback(this);
                }
                if (this.statusBarForeground != null && getTopInset() > 0) {
                    z = true;
                }
                setWillNotDraw(!z);
                WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
            }
            obtainStyledAttributes2.recycle();
            OnApplyWindowInsetsListener onApplyWindowInsetsListener = new OnApplyWindowInsetsListener() { // from class: com.google.android.material.appbar.AppBarLayout.1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    int i2 = AppBarLayout.$r8$clinit;
                    Insets insets = windowInsetsCompat.getInsets(7);
                    Insets insets2 = windowInsetsCompat.getInsets(64);
                    AppBarLayout appBarLayout = AppBarLayout.this;
                    if (!insets2.equals(appBarLayout.mLastTappableInsets) || !insets.equals(appBarLayout.mLastSysInsets)) {
                        Log.d("AppBarLayout", "[onApplyWindowInsets] sysInsets : " + insets + ", tappableInsets : " + insets2);
                        if (appBarLayout.getImmBehavior() != null) {
                            SeslImmersiveScrollBehavior immBehavior = appBarLayout.getImmBehavior();
                            if (immBehavior.mAppBarLayout != null) {
                                immBehavior.cancelWindowInsetsAnimationController();
                                immBehavior.updateSystemBarsHeight();
                                immBehavior.mAppBarLayout.onOffsetChanged(immBehavior.getTopAndBottomOffset());
                            }
                        }
                        appBarLayout.mLastSysInsets = insets;
                        appBarLayout.mLastTappableInsets = insets2;
                    }
                    WeakHashMap weakHashMap5 = ViewCompat.sViewPropertyAnimatorMap;
                    WindowInsetsCompat windowInsetsCompat2 = ViewCompat.Api16Impl.getFitsSystemWindows(appBarLayout) ? windowInsetsCompat : null;
                    if (!Objects.equals(appBarLayout.lastInsets, windowInsetsCompat2)) {
                        appBarLayout.lastInsets = windowInsetsCompat2;
                        appBarLayout.setWillNotDraw(!(appBarLayout.statusBarForeground != null && appBarLayout.getTopInset() > 0));
                        appBarLayout.requestLayout();
                    }
                    return windowInsetsCompat;
                }
            };
            WeakHashMap weakHashMap5 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, onApplyWindowInsetsListener);
            this.mCurrentOrientation = resources.getConfiguration().orientation;
            this.mCurrentScreenHeight = resources.getConfiguration().screenHeightDp;
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final LinearLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public final void setExpanded(boolean z, boolean z2, boolean z3) {
        int i;
        setLiftedState(!z);
        if (z) {
            i = 1;
        } else {
            i = this.mIsActivatedImmersiveScroll ? 512 : 2;
        }
        this.pendingAction = i | (z2 ? 4 : 0) | (z3 ? 8 : 0);
        requestLayout();
    }

    public static LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            return new LayoutParams((LinearLayout.LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LayoutParams extends LinearLayout.LayoutParams {
        public ChildScrollEffect scrollEffect;
        public final int scrollFlags;
        public final Interpolator scrollInterpolator;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.scrollFlags = 1;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AppBarLayout_Layout);
            this.scrollFlags = obtainStyledAttributes.getInt(1, 0);
            this.scrollEffect = obtainStyledAttributes.getInt(0, 0) != 1 ? null : new CompressChildScrollEffect();
            if (obtainStyledAttributes.hasValue(2)) {
                this.scrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, obtainStyledAttributes.getResourceId(2, 0));
            }
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.scrollFlags = 1;
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
            this.scrollFlags = 1;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.scrollFlags = 1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.scrollFlags = 1;
        }

        public LayoutParams(LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
            this.scrollFlags = 1;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((LinearLayout.LayoutParams) layoutParams);
            this.scrollFlags = 1;
            this.scrollFlags = layoutParams.scrollFlags;
            this.scrollEffect = layoutParams.scrollEffect;
            this.scrollInterpolator = layoutParams.scrollInterpolator;
        }
    }
}

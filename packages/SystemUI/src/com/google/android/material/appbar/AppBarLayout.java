package com.google.android.material.appbar;

import android.animation.AnimatorInflater;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.AbsSavedState;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.appcompat.animation.SeslAnimationUtils;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.AppBarLayoutBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.math.MathUtils;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.SeslAppBarHelper;
import com.google.android.material.appbar.SeslImmersiveScrollBehavior;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
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

/* loaded from: classes4.dex */
public class AppBarLayout extends LinearLayout implements CoordinatorLayout.AttachedBehavior, AppBarLayoutBehavior {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final float appBarElevation;
    public Behavior behavior;
    public int currentOffset;
    public int downPreScrollRange;
    public int downScrollRange;
    public final boolean hasLiftOnScrollColor;
    public boolean haveChildWithInterpolator;
    public boolean isMouse;
    public WindowInsetsCompat lastInsets;
    public final boolean liftOnScroll;
    public ValueAnimator liftOnScrollColorAnimator;
    public final long liftOnScrollColorDuration;
    public final TimeInterpolator liftOnScrollColorInterpolator;
    public final ValueAnimator.AnimatorUpdateListener liftOnScrollColorUpdateListener;
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
    public boolean mIsActivatedImmersiveScroll;
    public boolean mIsCanScroll;
    public boolean mIsDetachedState;
    public Insets mLastSysInsets;
    public Insets mLastTappableInsets;
    public final Resources mResources;
    public final boolean mSetCustomProportion;
    public final boolean mUseCustomHeight;
    public final boolean mUseCustomPadding;
    public int pendingAction;
    public final Drawable statusBarForeground;
    public final Integer statusBarForegroundOriginalColor;
    public int[] tmpStatesArray;
    public int totalScrollRange;

    public class BaseBehavior<T extends AppBarLayout> extends HeaderBehavior {
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

        /* renamed from: com.google.android.material.appbar.AppBarLayout$BaseBehavior$2, reason: invalid class name */
        public class AnonymousClass2 extends AccessibilityDelegateCompat {
            public final /* synthetic */ AppBarLayout val$appBarLayout;
            public final /* synthetic */ CoordinatorLayout val$coordinatorLayout;

            public AnonymousClass2(AppBarLayout appBarLayout, CoordinatorLayout coordinatorLayout) {
                this.val$appBarLayout = appBarLayout;
                this.val$coordinatorLayout = coordinatorLayout;
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                BaseBehavior baseBehavior;
                View viewAccess$600;
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
                AppBarLayout appBarLayout = this.val$appBarLayout;
                if (appBarLayout.getTotalScrollRange() == 0 || (viewAccess$600 = BaseBehavior.access$600((baseBehavior = BaseBehavior.this), this.val$coordinatorLayout)) == null) {
                    return;
                }
                int childCount = appBarLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (((LayoutParams) appBarLayout.getChildAt(i).getLayoutParams()).scrollFlags != 0) {
                        if (baseBehavior.getTopBottomOffsetForScrollingSibling() != (-appBarLayout.getTotalScrollRange())) {
                            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
                            accessibilityNodeInfoCompat.setScrollable(true);
                        }
                        if (baseBehavior.getTopBottomOffsetForScrollingSibling() != 0) {
                            if (!viewAccess$600.canScrollVertically(-1)) {
                                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
                                accessibilityNodeInfoCompat.setScrollable(true);
                                return;
                            } else {
                                if ((-appBarLayout.getDownNestedPreScrollRange()) != 0) {
                                    accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
                                    accessibilityNodeInfoCompat.setScrollable(true);
                                    return;
                                }
                                return;
                            }
                        }
                        return;
                    }
                }
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                AppBarLayout appBarLayout = this.val$appBarLayout;
                if (i == 4096) {
                    appBarLayout.setExpanded(false);
                    return true;
                }
                if (i != 8192) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                BaseBehavior baseBehavior = BaseBehavior.this;
                if (baseBehavior.getTopBottomOffsetForScrollingSibling() != 0) {
                    View viewAccess$600 = BaseBehavior.access$600(baseBehavior, this.val$coordinatorLayout);
                    if (!viewAccess$600.canScrollVertically(-1)) {
                        appBarLayout.setExpanded(true);
                        return true;
                    }
                    int i2 = -appBarLayout.getDownNestedPreScrollRange();
                    if (i2 != 0) {
                        BaseBehavior.this.onNestedPreScroll(this.val$coordinatorLayout, this.val$appBarLayout, viewAccess$600, 0, i2, new int[]{0, 0}, 1);
                        return true;
                    }
                }
                return false;
            }
        }

        public BaseBehavior() {
            this.mIsFlingScrollDown = false;
            this.mIsFlingScrollUp = false;
            this.mDirectTouchAppbar = false;
            this.mTouchSlop = -1;
            this.mVelocity = 0.0f;
            this.mIsSetStaticDuration = false;
            this.mIsScrollHold = false;
        }

        public static View access$600(BaseBehavior baseBehavior, CoordinatorLayout coordinatorLayout) {
            baseBehavior.getClass();
            int childCount = coordinatorLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if (((CoordinatorLayout.LayoutParams) childAt.getLayoutParams()).mBehavior instanceof ScrollingViewBehavior) {
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

        /* JADX WARN: Removed duplicated region for block: B:27:0x0067  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static void updateAppBarLayoutDrawableState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2) {
            View childAt;
            boolean zShouldLift;
            View childAt2;
            int iAbs = Math.abs(i);
            int childCount = appBarLayout.getChildCount();
            int i3 = 0;
            while (true) {
                if (i3 >= childCount) {
                    childAt = null;
                    break;
                }
                childAt = appBarLayout.getChildAt(i3);
                if (iAbs >= childAt.getTop() && iAbs <= childAt.getBottom()) {
                    break;
                } else {
                    i3++;
                }
            }
            if (childAt != null) {
                int i4 = ((LayoutParams) childAt.getLayoutParams()).scrollFlags;
                if ((i4 & 1) != 0) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    int minimumHeight = childAt.getMinimumHeight();
                    zShouldLift = i2 <= 0 || (i4 & 12) == 0 ? !((i4 & 2) == 0 || (-i) < ((childAt.getBottom() - minimumHeight) - appBarLayout.getTopInset()) - appBarLayout.getImmersiveTopInset()) : (-i) >= ((childAt.getBottom() - minimumHeight) - appBarLayout.getTopInset()) - appBarLayout.getImmersiveTopInset();
                }
            }
            if (appBarLayout.liftOnScroll) {
                int childCount2 = coordinatorLayout.getChildCount();
                int i5 = 0;
                while (true) {
                    if (i5 >= childCount2) {
                        childAt2 = null;
                        break;
                    }
                    childAt2 = coordinatorLayout.getChildAt(i5);
                    if ((childAt2 instanceof NestedScrollingChild2) || (childAt2 instanceof AbsListView) || (childAt2 instanceof ScrollView)) {
                        break;
                    } else {
                        i5++;
                    }
                }
                zShouldLift = appBarLayout.shouldLift(childAt2);
            }
            if (appBarLayout.setLiftedState(zShouldLift)) {
                ArrayList arrayList = (ArrayList) coordinatorLayout.mChildDag.mGraph.get(appBarLayout);
                List arrayList2 = arrayList != null ? new ArrayList(arrayList) : null;
                if (arrayList2 == null) {
                    arrayList2 = Collections.EMPTY_LIST;
                }
                int size = arrayList2.size();
                for (int i6 = 0; i6 < size; i6++) {
                    CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) ((View) arrayList2.get(i6)).getLayoutParams()).mBehavior;
                    if (behavior instanceof ScrollingViewBehavior) {
                        if (((ScrollingViewBehavior) behavior).overlayTop != 0) {
                            if (appBarLayout.getBackground() != null) {
                                appBarLayout.getBackground().jumpToCurrentState();
                            }
                            if (appBarLayout.getForeground() != null) {
                                appBarLayout.getForeground().jumpToCurrentState();
                            }
                            if (appBarLayout.getStateListAnimator() != null) {
                                appBarLayout.getStateListAnimator().jumpToCurrentState();
                                return;
                            }
                            return;
                        }
                        return;
                    }
                }
            }
        }

        public final void animateOffsetTo(final CoordinatorLayout coordinatorLayout, final AppBarLayout appBarLayout, int i) {
            float fAbs = Math.abs(this.mVelocity);
            int i2 = IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend;
            int iAbs = (fAbs <= 0.0f || Math.abs(this.mVelocity) > 3000.0f) ? 250 : (int) ((3000.0f - Math.abs(this.mVelocity)) * 0.4d);
            if (iAbs <= 250) {
                iAbs = 250;
            }
            if (this.mIsSetStaticDuration) {
                this.mIsSetStaticDuration = false;
            } else {
                i2 = iAbs;
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
            WeakReference weakReference = this.lastNestedScrollingChildRef;
            if (weakReference == null) {
                return true;
            }
            View view2 = (View) weakReference.get();
            return (view2 == null || !view2.isShown() || view2.canScrollVertically(-1)) ? false : true;
        }

        @Override // com.google.android.material.appbar.HeaderBehavior
        public final int getMaxDragOffset(View view) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            return appBarLayout.getTopInset() + (-appBarLayout.getDownNestedScrollRange());
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
        public final void onFlingFinished(CoordinatorLayout coordinatorLayout, View view) {
            OverScroller overScroller = this.scroller;
            if (overScroller != null) {
                overScroller.forceFinished(true);
            }
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            int iRound;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            super.onLayoutChild(coordinatorLayout, appBarLayout, i);
            int i2 = appBarLayout.pendingAction;
            SavedState savedState = this.savedState;
            if (savedState == null || (i2 & 8) != 0) {
                if (i2 != 0) {
                    boolean z = (i2 & 4) != 0;
                    if ((i2 & 2) != 0) {
                        float fSeslGetCollapsedHeight = (((appBarLayout.mIsCanScroll && (((Behavior) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).mBehavior) instanceof SeslImmersiveScrollBehavior)) ? (int) appBarLayout.seslGetCollapsedHeight() : 0) + (-appBarLayout.getTotalScrollRange())) - appBarLayout.getImmersiveTopInset();
                        if (z) {
                            animateOffsetTo(coordinatorLayout, appBarLayout, (int) fSeslGetCollapsedHeight);
                        } else {
                            setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, (int) fSeslGetCollapsedHeight);
                        }
                    } else if ((i2 & 512) != 0) {
                        float fSeslGetCollapsedHeight2 = ((appBarLayout.mIsCanScroll && (((Behavior) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).mBehavior) instanceof SeslImmersiveScrollBehavior)) ? (int) appBarLayout.seslGetCollapsedHeight() : 0) + (-appBarLayout.getTotalScrollRange());
                        if (coordinatorLayout.getContext().getResources().getConfiguration().orientation == 1 && appBarLayout.getImmersiveTopInset() == 0 && appBarLayout.mHeightProportion == 0.0f) {
                            fSeslGetCollapsedHeight2 = 0.0f;
                        }
                        if (z) {
                            animateOffsetTo(coordinatorLayout, appBarLayout, (int) fSeslGetCollapsedHeight2);
                        } else {
                            setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, (int) fSeslGetCollapsedHeight2);
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
                    iRound = appBarLayout.getTopInset() + childAt.getMinimumHeight() + i3;
                } else {
                    iRound = Math.round(childAt.getHeight() * this.savedState.firstVisibleChildPercentageShown) + i3;
                }
                setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, iRound);
            }
            appBarLayout.pendingAction = 0;
            this.savedState = null;
            int iClamp = MathUtils.clamp(getTopAndBottomOffset(), -appBarLayout.getTotalScrollRange(), 0);
            ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
            if (viewOffsetHelper != null) {
                viewOffsetHelper.setTopAndBottomOffset(iClamp);
            } else {
                this.tempTopBottomOffset = iClamp;
            }
            updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, getTopAndBottomOffset(), 0);
            appBarLayout.onOffsetChanged(getTopAndBottomOffset());
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api29Impl.getAccessibilityDelegate(coordinatorLayout) != null) {
                return true;
            }
            ViewCompat.setAccessibilityDelegate(coordinatorLayout, new AnonymousClass2(appBarLayout, coordinatorLayout));
            return true;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onNestedPreFling(View view, View view2, float f) {
            this.mVelocity = f;
            if (f < -300.0f) {
                this.mIsFlingScrollDown = true;
                this.mIsFlingScrollUp = false;
                return false;
            }
            if (f > 300.0f) {
                this.mIsFlingScrollDown = false;
                this.mIsFlingScrollUp = true;
                return false;
            }
            this.mVelocity = 0.0f;
            this.mIsFlingScrollDown = false;
            this.mIsFlingScrollUp = false;
            return true;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final void onRestoreInstanceState(View view, Parcelable parcelable) {
            if (parcelable instanceof SavedState) {
                this.savedState = (SavedState) parcelable;
            } else {
                this.savedState = null;
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final Parcelable onSaveInstanceState(View view) {
            AbsSavedState absSavedState = View.BaseSavedState.EMPTY_STATE;
            SavedState savedStateSaveScrollState = saveScrollState(absSavedState, (AppBarLayout) view);
            return savedStateSaveScrollState == null ? absSavedState : savedStateSaveScrollState;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i, int i2) {
            ValueAnimator valueAnimator;
            boolean z = (i & 2) != 0 && (appBarLayout.liftOnScroll || (appBarLayout.getTotalScrollRange() != 0 && coordinatorLayout.getHeight() - view.getHeight() <= appBarLayout.getHeight()));
            if (z && (valueAnimator = this.offsetAnimator) != null) {
                valueAnimator.cancel();
            }
            if (appBarLayout.getBottom() <= appBarLayout.seslGetCollapsedHeight()) {
                this.mLifted = true;
                appBarLayout.setLiftedState(true);
                this.mDiffY_Touch = 0.0f;
            } else {
                this.mLifted = false;
                appBarLayout.setLiftedState(false);
            }
            if (appBarLayout.getImmBehavior() == null || !appBarLayout.mIsCanScroll) {
                float fSeslGetCollapsedHeight = appBarLayout.seslGetCollapsedHeight();
                float height = appBarLayout.getHeight() - appBarLayout.getTotalScrollRange();
                if (height != fSeslGetCollapsedHeight && height > 0.0f) {
                    Log.i("AppBarLayout", "Internal collapsedHeight/ oldCollapsedHeight :" + fSeslGetCollapsedHeight + " newCollapsedHeight :" + height);
                    appBarLayout.mCollapsedHeight = height;
                    appBarLayout.updateInternalHeight();
                }
            }
            this.lastNestedScrollingChildRef = null;
            this.lastStartedType = i2;
            this.mToolisMouse = appBarLayout.isMouse;
            return z;
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0049  */
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
            if (action == 0) {
                this.mDirectTouchAppbar = true;
                motionEvent.getX();
                this.mLastMotionY_Touch = motionEvent.getY();
                this.mDiffY_Touch = 0.0f;
            } else if (action == 1) {
                if (Math.abs(this.mDiffY_Touch) > 21.0f) {
                    float f = this.mDiffY_Touch;
                    if (f < 0.0f) {
                        this.mIsFlingScrollUp = true;
                        this.mIsFlingScrollDown = false;
                    } else if (f > 0.0f) {
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
            } else if (action == 2) {
                this.mDirectTouchAppbar = true;
                float y = motionEvent.getY();
                float f2 = y - this.mLastMotionY_Touch;
                if (f2 != 0.0f) {
                    this.mDiffY_Touch = f2;
                }
                if (Math.abs(this.mDiffY_Touch) > this.mTouchSlop) {
                    this.mLastMotionY_Touch = y;
                }
            } else if (action == 3) {
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
                    savedState.firstVisibleChildAtMinimumHeight = bottom == appBarLayout.getTopInset() + childAt.getMinimumHeight();
                    savedState.firstVisibleChildPercentageShown = bottom / childAt.getHeight();
                    return savedState;
                }
            }
            return null;
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x008c  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0091  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x009c  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x014e  */
        @Override // com.google.android.material.appbar.HeaderBehavior
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
            int top;
            ViewOffsetHelper viewOffsetHelper;
            boolean topAndBottomOffset;
            int topInset;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
            int i4 = 0;
            if (i2 == 0 || topBottomOffsetForScrollingSibling < i2 || topBottomOffsetForScrollingSibling > i3) {
                this.offsetDelta = 0;
            } else {
                int iClamp = MathUtils.clamp(i, i2, i3);
                if (topBottomOffsetForScrollingSibling != iClamp) {
                    if (appBarLayout.haveChildWithInterpolator) {
                        int iAbs = Math.abs(iClamp);
                        int childCount = appBarLayout.getChildCount();
                        int i5 = 0;
                        while (true) {
                            if (i5 >= childCount) {
                                break;
                            }
                            View childAt = appBarLayout.getChildAt(i5);
                            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                            Interpolator interpolator = layoutParams.scrollInterpolator;
                            if (iAbs < childAt.getTop() || iAbs > childAt.getBottom()) {
                                i5++;
                            } else if (interpolator != null) {
                                int i6 = layoutParams.scrollFlags;
                                if ((i6 & 1) != 0) {
                                    topInset = childAt.getHeight() + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                                    if ((i6 & 2) != 0) {
                                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                        topInset -= childAt.getMinimumHeight();
                                    }
                                } else {
                                    topInset = 0;
                                }
                                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                if (childAt.getFitsSystemWindows()) {
                                    topInset -= appBarLayout.getTopInset();
                                }
                                if (topInset > 0) {
                                    float f = topInset;
                                    top = (childAt.getTop() + Math.round(interpolator.getInterpolation((iAbs - childAt.getTop()) / f) * f)) * Integer.signum(iClamp);
                                }
                            }
                        }
                        top = iClamp;
                        viewOffsetHelper = this.viewOffsetHelper;
                        if (viewOffsetHelper == null) {
                            topAndBottomOffset = viewOffsetHelper.setTopAndBottomOffset(top);
                        } else {
                            this.tempTopBottomOffset = top;
                            topAndBottomOffset = false;
                        }
                        int i7 = topBottomOffsetForScrollingSibling - iClamp;
                        this.offsetDelta = iClamp - top;
                        if (topAndBottomOffset) {
                            for (int i8 = 0; i8 < appBarLayout.getChildCount(); i8++) {
                                LayoutParams layoutParams2 = (LayoutParams) appBarLayout.getChildAt(i8).getLayoutParams();
                                CompressChildScrollEffect compressChildScrollEffect = layoutParams2.scrollEffect;
                                if (compressChildScrollEffect != null && (layoutParams2.scrollFlags & 1) != 0) {
                                    View childAt2 = appBarLayout.getChildAt(i8);
                                    float topAndBottomOffset2 = getTopAndBottomOffset();
                                    Rect rect = compressChildScrollEffect.relativeRect;
                                    childAt2.getDrawingRect(rect);
                                    appBarLayout.offsetDescendantRectToMyCoords(childAt2, rect);
                                    rect.offset(0, -appBarLayout.getTopInset());
                                    float fAbs = compressChildScrollEffect.relativeRect.top - Math.abs(topAndBottomOffset2);
                                    if (fAbs <= 0.0f) {
                                        float fClamp = 1.0f - MathUtils.clamp(Math.abs(fAbs / compressChildScrollEffect.relativeRect.height()), 0.0f, 1.0f);
                                        float fHeight = (-fAbs) - ((compressChildScrollEffect.relativeRect.height() * 0.3f) * (1.0f - (fClamp * fClamp)));
                                        childAt2.setTranslationY(fHeight);
                                        childAt2.getDrawingRect(compressChildScrollEffect.ghostRect);
                                        compressChildScrollEffect.ghostRect.offset(0, (int) (-fHeight));
                                        if (fHeight >= compressChildScrollEffect.ghostRect.height()) {
                                            childAt2.setVisibility(4);
                                        } else {
                                            childAt2.setVisibility(0);
                                        }
                                        Rect rect2 = compressChildScrollEffect.ghostRect;
                                        WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                                        childAt2.setClipBounds(rect2);
                                    } else {
                                        WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                                        childAt2.setClipBounds(null);
                                        childAt2.setTranslationY(0.0f);
                                        childAt2.setVisibility(0);
                                    }
                                }
                            }
                        }
                        if (!topAndBottomOffset && appBarLayout.haveChildWithInterpolator) {
                            coordinatorLayout.dispatchDependentViewsChanged(appBarLayout);
                        }
                        appBarLayout.onOffsetChanged(getTopAndBottomOffset());
                        updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, iClamp, iClamp < topBottomOffsetForScrollingSibling ? -1 : 1);
                        i4 = i7;
                    } else {
                        top = iClamp;
                        viewOffsetHelper = this.viewOffsetHelper;
                        if (viewOffsetHelper == null) {
                        }
                        int i72 = topBottomOffsetForScrollingSibling - iClamp;
                        this.offsetDelta = iClamp - top;
                        if (topAndBottomOffset) {
                        }
                        if (!topAndBottomOffset) {
                            coordinatorLayout.dispatchDependentViewsChanged(appBarLayout);
                        }
                        appBarLayout.onOffsetChanged(getTopAndBottomOffset());
                        updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, iClamp, iClamp < topBottomOffsetForScrollingSibling ? -1 : 1);
                        i4 = i72;
                    }
                }
            }
            WeakHashMap weakHashMap5 = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api29Impl.getAccessibilityDelegate(coordinatorLayout) != null) {
                return i4;
            }
            ViewCompat.setAccessibilityDelegate(coordinatorLayout, new AnonymousClass2(appBarLayout, coordinatorLayout));
            return i4;
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
                        int iSeslGetCollapsedHeight = ((int) appBarLayout.seslGetCollapsedHeight()) - appBarLayout.getTotalScrollRange();
                        int i2 = -appBarLayout.getTotalScrollRange();
                        int i3 = ((double) appBarLayout.getBottom()) >= ((double) appBarLayout.seslGetCollapsedHeight()) * 0.48d ? iSeslGetCollapsedHeight : i2;
                        if (!this.mIsFlingScrollUp) {
                            i2 = i3;
                        }
                        if (!this.mIsFlingScrollDown) {
                            iSeslGetCollapsedHeight = i2;
                        }
                        animateOffsetTo(coordinatorLayout, appBarLayout, MathUtils.clamp(iSeslGetCollapsedHeight, -appBarLayout.getTotalScrollRange(), 0));
                        return;
                    }
                    return;
                }
                int topInset = -childAt2.getTop();
                int minimumHeight = -childAt2.getBottom();
                if (childIndexOnOffset == 0) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (appBarLayout.getFitsSystemWindows() && childAt2.getFitsSystemWindows()) {
                        topInset -= appBarLayout.getTopInset();
                    }
                }
                if ((i & 2) == 2) {
                    if (appBarLayout.mIsCanScroll) {
                        minimumHeight = (int) ((appBarLayout.seslGetCollapsedHeight() - appBarLayout.getPaddingBottom()) + minimumHeight);
                    } else {
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        minimumHeight += childAt2.getMinimumHeight();
                    }
                } else if ((i & 5) == 5) {
                    WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                    int minimumHeight2 = childAt2.getMinimumHeight() + minimumHeight;
                    if (topBottomOffsetForScrollingSibling < minimumHeight2) {
                        topInset = minimumHeight2;
                    } else {
                        minimumHeight = minimumHeight2;
                    }
                }
                if ((i & 32) == 32) {
                    topInset += ((LinearLayout.LayoutParams) layoutParams).topMargin;
                    minimumHeight -= ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                }
                int i4 = (!this.mLifted ? ((double) topBottomOffsetForScrollingSibling) < ((double) (minimumHeight + topInset)) * 0.43d : ((double) topBottomOffsetForScrollingSibling) < ((double) (minimumHeight + topInset)) * 0.52d) ? topInset : minimumHeight;
                if (childAt == null) {
                    int i5 = AppBarLayout.$r8$clinit;
                    Log.w("AppBarLayout", "coordinatorLayout.getChildAt(1) is null");
                    topInset = i4;
                } else {
                    if (this.mIsFlingScrollUp) {
                        this.mIsFlingScrollUp = false;
                        this.mIsFlingScrollDown = false;
                    } else {
                        minimumHeight = i4;
                    }
                    if (!this.mIsFlingScrollDown || childAt.getTop() <= appBarLayout.seslGetCollapsedHeight()) {
                        topInset = minimumHeight;
                    } else {
                        this.mIsFlingScrollDown = false;
                    }
                }
                animateOffsetTo(coordinatorLayout, appBarLayout, MathUtils.clamp(topInset, -appBarLayout.getTotalScrollRange(), 0));
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
                    i4 = -appBarLayout.getTotalScrollRange();
                    int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange() + i4;
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
                    i5 = downNestedPreScrollRange;
                } else {
                    i4 = -appBarLayout.getTotalScrollRange();
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
                    if (getTopAndBottomOffset() == i4) {
                        this.mIsScrollHold = true;
                    }
                    i5 = 0;
                }
                int i6 = i4;
                if (this.flingRunnable != null && (overScroller = this.scroller) != null) {
                    overScroller.forceFinished(true);
                }
                if (i6 != i5) {
                    iArr[1] = setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, getTopBottomOffsetForScrollingSibling() - i2, i6, i5);
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
            BaseBehavior<T> baseBehavior;
            CoordinatorLayout coordinatorLayout2;
            AppBarLayout appBarLayout2;
            int childIndexOnOffset;
            if (!this.mToolisMouse && ((childIndexOnOffset = getChildIndexOnOffset(appBarLayout, getTopBottomOffsetForScrollingSibling())) < 0 || (((LayoutParams) appBarLayout.getChildAt(childIndexOnOffset).getLayoutParams()).scrollFlags & 65536) != 65536)) {
                baseBehavior = this;
                coordinatorLayout2 = coordinatorLayout;
                appBarLayout2 = appBarLayout;
                if (i4 >= 0 || baseBehavior.mIsScrollHold) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (view instanceof NestedScrollingChild2) {
                        ((NestedScrollingChild2) view).stopNestedScroll(1);
                    }
                } else {
                    iArr[1] = baseBehavior.setHeaderTopBottomOffset(coordinatorLayout2, appBarLayout2, baseBehavior.getTopBottomOffsetForScrollingSibling() - i4, -appBarLayout2.getDownNestedScrollRange(), 0);
                    baseBehavior.stopNestedScrollIfNeeded(i4, appBarLayout2, view, i5);
                }
            } else if (i4 < 0) {
                baseBehavior = this;
                coordinatorLayout2 = coordinatorLayout;
                appBarLayout2 = appBarLayout;
                iArr[1] = baseBehavior.setHeaderTopBottomOffset(coordinatorLayout2, appBarLayout2, getTopBottomOffsetForScrollingSibling() - i4, -appBarLayout.getDownNestedScrollRange(), 0);
                baseBehavior.stopNestedScrollIfNeeded(i4, appBarLayout2, view, i5);
            } else {
                baseBehavior = this;
                coordinatorLayout2 = coordinatorLayout;
                appBarLayout2 = appBarLayout;
            }
            if (i4 == 0) {
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api29Impl.getAccessibilityDelegate(coordinatorLayout2) != null) {
                    return;
                }
                ViewCompat.setAccessibilityDelegate(coordinatorLayout2, baseBehavior.new AnonymousClass2(appBarLayout2, coordinatorLayout2));
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

        public class SavedState extends androidx.customview.view.AbsSavedState {
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
                super.writeToParcel(parcel, i);
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

    public class Behavior extends BaseBehavior<AppBarLayout> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    public abstract class ChildScrollEffect {
    }

    public class CompressChildScrollEffect extends ChildScrollEffect {
        public final Rect relativeRect = new Rect();
        public final Rect ghostRect = new Rect();
    }

    public interface OnOffsetChangedListener {
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    public class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
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
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            int iClamp;
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) view2.getLayoutParams()).mBehavior;
            if (behavior instanceof BaseBehavior) {
                int bottom = (view2.getBottom() - view.getTop()) + ((BaseBehavior) behavior).offsetDelta + this.verticalLayoutGap;
                if (this.overlayTop == 0) {
                    iClamp = 0;
                } else {
                    float overlapRatioForOffset = getOverlapRatioForOffset(view2);
                    int i = this.overlayTop;
                    iClamp = MathUtils.clamp((int) (overlapRatioForOffset * i), 0, i);
                }
                int i2 = bottom - iClamp;
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
                Rect rect2 = new Rect(rect);
                rect2.offset(view.getLeft(), view.getTop());
                Rect rect3 = this.tempRect1;
                rect3.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                if (!rect3.contains(rect2)) {
                    appBarLayout.setExpanded(false, !z, true);
                    return true;
                }
            }
            return false;
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ScrollingViewBehavior_Layout);
            this.overlayTop = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public class SeslAppbarState {
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
        if (this.statusBarForeground == null || getTopInset() <= 0) {
            return;
        }
        int iSave = canvas.save();
        canvas.translate(0.0f, -this.currentOffset);
        this.statusBarForeground.draw(canvas);
        canvas.restoreToCount(iSave);
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getDownNestedPreScrollRange() {
        int iMin;
        int minimumHeight;
        int i = this.downPreScrollRange;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount() - 1;
        int iSeslGetCollapsedHeight = 0;
        while (true) {
            if (childCount < 0) {
                break;
            }
            View childAt = getChildAt(childCount);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredHeight = childAt.getMeasuredHeight();
                int i2 = layoutParams.scrollFlags;
                if ((i2 & 5) == 5) {
                    int i3 = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                    if ((i2 & 8) != 0) {
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        minimumHeight = childAt.getMinimumHeight();
                    } else if ((i2 & 2) != 0) {
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        minimumHeight = measuredHeight - childAt.getMinimumHeight();
                    } else {
                        iMin = i3 + measuredHeight;
                        if (childCount == 0) {
                            WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                            if (childAt.getFitsSystemWindows()) {
                                iMin = Math.min(iMin, measuredHeight - getTopInset());
                            }
                        }
                        iSeslGetCollapsedHeight += iMin;
                    }
                    iMin = minimumHeight + i3;
                    if (childCount == 0) {
                    }
                    iSeslGetCollapsedHeight += iMin;
                } else if (this.mIsCanScroll) {
                    iSeslGetCollapsedHeight = (int) (seslGetCollapsedHeight() + 0 + iSeslGetCollapsedHeight);
                }
            }
            childCount--;
        }
        int iMax = Math.max(0, iSeslGetCollapsedHeight);
        this.downPreScrollRange = iMax;
        return iMax;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005d  */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.view.View] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
                        if (viewGroup == null) {
                            i = 0;
                            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            minimumHeight = collapsingToolbarLayout.getMinimumHeight() - i;
                        } else {
                            ?? r2 = collapsingToolbarLayout.toolbarDirectChild;
                            if (r2 != 0 && r2 != collapsingToolbarLayout) {
                                viewGroup = r2;
                            }
                            ViewGroup.LayoutParams layoutParams2 = viewGroup.getLayoutParams();
                            if (layoutParams2 instanceof ViewGroup.MarginLayoutParams) {
                                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams2;
                                i = marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                            }
                            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                            minimumHeight = collapsingToolbarLayout.getMinimumHeight() - i;
                        }
                    } else {
                        WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                        minimumHeight = childAt.getMinimumHeight();
                    }
                    i4 -= minimumHeight;
                }
            }
            i3++;
        }
        int iMax = Math.max(0, i4);
        this.downScrollRange = iMax;
        return iMax;
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
        int minimumHeight = getMinimumHeight();
        if (minimumHeight == 0) {
            int childCount = getChildCount();
            minimumHeight = childCount >= 1 ? getChildAt(childCount - 1).getMinimumHeight() : 0;
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
        int minimumHeight = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredHeight = childAt.getMeasuredHeight();
                int i3 = layoutParams.scrollFlags;
                if ((i3 & 1) == 0) {
                    break;
                }
                int topInset = measuredHeight + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + minimumHeight;
                if (i2 == 0) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (childAt.getFitsSystemWindows()) {
                        topInset -= getTopInset();
                    }
                }
                minimumHeight = topInset;
                if ((i3 & 2) != 0) {
                    if (this.mIsCanScroll) {
                        minimumHeight += getTopInset() + this.mBottomPadding;
                    } else {
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        minimumHeight -= childAt.getMinimumHeight();
                    }
                }
            }
            i2++;
        }
        int iMax = Math.max(0, minimumHeight);
        this.totalScrollRange = iMax;
        return iMax;
    }

    public final int getWindowHeight() {
        Insets insets;
        if (this.mIsActivatedImmersiveScroll) {
            return getContext().getResources().getDisplayMetrics().heightPixels;
        }
        SeslAppBarHelper.Companion.getClass();
        WindowMetrics currentWindowMetrics = ((WindowManager) getContext().getSystemService("window")).getCurrentWindowMetrics();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        WindowInsetsCompat rootWindowInsets = ViewCompat.Api23Impl.getRootWindowInsets(this);
        if (rootWindowInsets == null || (insets = rootWindowInsets.mImpl.getInsets(7)) == null) {
            insets = Insets.NONE;
        }
        int i = insets.top;
        int iHeight = currentWindowMetrics.getBounds().height() - i;
        int i2 = insets.bottom;
        int i3 = iHeight - i2;
        RecyclerView$$ExternalSyntheticOutline0.m(i2, "SeslAppBarHelper", MutableObjectList$$ExternalSyntheticOutline0.m(i3, i, "screenHeight(px)=", ", status=", ", navi="));
        return i3;
    }

    public final void invalidateScrollRanges() {
        Behavior behavior = this.behavior;
        BaseBehavior.SavedState savedStateSaveScrollState = (behavior == null || this.totalScrollRange == -1 || this.pendingAction != 0) ? null : behavior.saveScrollState(androidx.customview.view.AbsSavedState.EMPTY_STATE, this);
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        if (savedStateSaveScrollState != null) {
            Behavior behavior2 = this.behavior;
            if (behavior2.savedState != null) {
                return;
            }
            behavior2.savedState = savedStateSaveScrollState;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsDetachedState = false;
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
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
            if (!z) {
                Log.i("AppBarLayout", "Update bottom padding");
                int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.sesl_extended_appbar_bottom_padding);
                this.mBottomPadding = dimensionPixelSize;
                setPadding(0, 0, 0, dimensionPixelSize);
                this.mCollapsedHeight = this.mResources.getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding) + this.mBottomPadding;
            } else if (z && this.mBottomPadding == 0) {
                this.mCollapsedHeight = this.mResources.getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding);
            }
        }
        if (!this.mSetCustomProportion) {
            SeslAppBarHelper.Companion companion = SeslAppBarHelper.Companion;
            Context context = getContext();
            companion.getClass();
            this.mHeightProportion = SeslAppBarHelper.Companion.getAppBarProPortion(context);
        }
        updateInternalHeight();
        Log.i("AppBarLayout", "onConfigurationChanged : mCollapsedHeight = " + this.mCollapsedHeight + ", mHeightProportion = " + this.mHeightProportion + ", mHasSuggestion = false, mUseCollapsedHeight = false");
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
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + iArr.length);
        boolean z = this.liftable;
        iArr[0] = z ? R.attr.state_liftable : -2130970211;
        iArr[1] = (z && this.lifted) ? R.attr.state_lifted : -2130970212;
        iArr[2] = z ? R.attr.state_collapsible : -2130970204;
        iArr[3] = (z && this.lifted) ? R.attr.state_collapsed : -2130970203;
        return LinearLayout.mergeDrawableStates(iArrOnCreateDrawableState, iArr);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        this.mIsDetachedState = true;
        SeslImmersiveScrollBehavior immBehavior = getImmBehavior();
        if (immBehavior != null) {
            Log.i("SeslImmersiveScrollBehavior", "DetachedFromWindow");
            SeslImmersiveScrollBehavior.AnonymousClass3 anonymousClass3 = immBehavior.mOnInsetsChangedListener;
            if (anonymousClass3 != null) {
                immBehavior.mWindowInsetsController.removeOnControllableInsetsChangedListener(anonymousClass3);
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
        boolean z2 = true;
        super.onLayout(z, i, i2, i3, i4);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (getFitsSystemWindows() && getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (childAt.getVisibility() != 8 && !childAt.getFitsSystemWindows()) {
                int topInset = getTopInset();
                for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                    getChildAt(childCount).offsetTopAndBottom(topInset);
                }
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
                    break;
                } else {
                    i6++;
                }
            }
        }
        if (this.liftable != z2) {
            this.liftable = z2;
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
            if (getFitsSystemWindows() && getChildCount() > 0) {
                View childAt = getChildAt(0);
                if (childAt.getVisibility() != 8 && !childAt.getFitsSystemWindows()) {
                    int measuredHeight = getMeasuredHeight();
                    if (mode == Integer.MIN_VALUE) {
                        measuredHeight = MathUtils.clamp(getTopInset() + getMeasuredHeight(), 0, View.MeasureSpec.getSize(i2));
                    } else if (mode == 0) {
                        measuredHeight += getTopInset();
                    }
                    setMeasuredDimension(getMeasuredWidth(), measuredHeight);
                }
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
            postInvalidateOnAnimation();
        }
        List list = this.listeners;
        if (list != null) {
            int size = ((ArrayList) list).size();
            for (int i2 = 0; i2 < size; i2++) {
                OnOffsetChangedListener onOffsetChangedListener = (OnOffsetChangedListener) ((ArrayList) this.listeners).get(i2);
                if (onOffsetChangedListener != null) {
                    onOffsetChangedListener.onOffsetChanged(this, i);
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
        Drawable background = getBackground();
        if (background instanceof MaterialShapeDrawable) {
            ((MaterialShapeDrawable) background).setElevation(f);
        }
    }

    public final void setExpanded(boolean z) {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setExpanded(z, isLaidOut(), true);
    }

    public final boolean setLiftedState(boolean z) {
        if (this.lifted == z) {
            return false;
        }
        this.lifted = z;
        refreshDrawableState();
        if (!(getBackground() instanceof MaterialShapeDrawable)) {
            return true;
        }
        if (this.hasLiftOnScrollColor) {
            startLiftOnScrollColorAnimation(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
            return true;
        }
        if (!this.liftOnScroll) {
            return true;
        }
        startLiftOnScrollColorAnimation(z ? 0.0f : this.appBarElevation, z ? this.appBarElevation : 0.0f);
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
            View viewFindViewById = view != null ? view.findViewById(i) : null;
            if (viewFindViewById == null && (getParent() instanceof ViewGroup)) {
                viewFindViewById = ((ViewGroup) getParent()).findViewById(this.liftOnScrollTargetViewId);
            }
            if (viewFindViewById != null) {
                this.liftOnScrollTargetView = new WeakReference(viewFindViewById);
            }
        }
        WeakReference weakReference = this.liftOnScrollTargetView;
        View view2 = weakReference != null ? (View) weakReference.get() : null;
        if (view2 != null) {
            view = view2;
        }
        if (view != null) {
            return view.canScrollVertically(-1) || view.getScrollY() > 0;
        }
        return false;
    }

    public final void startLiftOnScrollColorAnimation(float f, float f2) {
        ValueAnimator valueAnimator = this.liftOnScrollColorAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        this.liftOnScrollColorAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(this.liftOnScrollColorDuration);
        this.liftOnScrollColorAnimator.setInterpolator(this.liftOnScrollColorInterpolator);
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = this.liftOnScrollColorUpdateListener;
        if (animatorUpdateListener != null) {
            this.liftOnScrollColorAnimator.addUpdateListener(animatorUpdateListener);
        }
        this.liftOnScrollColorAnimator.start();
    }

    public final void updateInternalHeight() {
        float f;
        float f2;
        int windowHeight = getWindowHeight();
        if (this.mUseCustomHeight) {
            float f3 = this.mCustomHeightProportion;
            if (f3 != 0.0f) {
                if (this.mIsCanScroll) {
                    float windowHeight2 = getWindowHeight();
                    float immersiveTopInset = getImmersiveTopInset();
                    if (windowHeight2 == 0.0f) {
                        windowHeight2 = 1.0f;
                    }
                    f2 = immersiveTopInset / windowHeight2;
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
        float fSeslGetCollapsedHeight = windowHeight * f;
        if (fSeslGetCollapsedHeight == 0.0f) {
            if (getImmBehavior() == null || !this.mIsCanScroll) {
                float fSeslGetCollapsedHeight2 = seslGetCollapsedHeight();
                Log.i("AppBarLayout", "update InternalCollapsedHeight from updateInternalHeight() : " + fSeslGetCollapsedHeight2);
                this.mCollapsedHeight = fSeslGetCollapsedHeight2;
            }
            fSeslGetCollapsedHeight = seslGetCollapsedHeight();
        }
        StringBuilder sb = new StringBuilder("[calculateInternalHeight] orientation:");
        sb.append(this.mResources.getConfiguration().orientation);
        sb.append(", density:");
        ViewPager$$ExternalSyntheticOutline0.m(sb, this.mResources.getConfiguration().densityDpi, ", windowHeight:", windowHeight, ", heightDp:");
        sb.append(fSeslGetCollapsedHeight);
        StringBuilder sb2 = new StringBuilder(sb.toString());
        if (!this.mUseCustomHeight) {
            sb2.append(", [3]mHeightProportion : ");
            sb2.append(this.mHeightProportion);
        } else if (this.mSetCustomProportion) {
            sb2.append(", [1]mCustomHeightProportion : ");
            sb2.append(this.mCustomHeightProportion);
        }
        if (this.mIsActivatedImmersiveScroll) {
            Log.i("AppBarLayout", sb2.toString());
        }
        int i = (int) fSeslGetCollapsedHeight;
        boolean z = this.mUseCustomHeight;
        if (!z || (z && this.mSetCustomProportion)) {
            try {
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) getLayoutParams();
                ((ViewGroup.MarginLayoutParams) layoutParams).height = i;
                setLayoutParams(layoutParams);
            } catch (ClassCastException e) {
                Log.e("AppBarLayout", Log.getStackTraceString(e));
            }
        }
        if (this.mIsActivatedImmersiveScroll) {
            StringBuilder sb3 = new StringBuilder("[updateInternalHeight] mUseCustomHeight : " + this.mUseCustomHeight + ", mSetCustomProportion : " + this.mSetCustomProportion + ", mSetCustomHeight : false, mIsImmersiveScroll : " + this.mIsActivatedImmersiveScroll + ", mIsSetByUser : false, mImmersiveTopInset : " + this.mImmersiveTopInset);
            WindowInsets rootWindowInsets = getRootView().getRootWindowInsets();
            if (rootWindowInsets != null) {
                sb3.append("\n");
                sb3.append(rootWindowInsets);
            }
            Log.i("AppBarLayout", sb3.toString());
        }
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

    public AppBarLayout(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        final Integer numValueOf;
        int color;
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, R.style.Widget_Design_AppBarLayout), attributeSet, i);
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        boolean z = false;
        this.pendingAction = 0;
        this.lifted = false;
        this.liftOnScrollListeners = new ArrayList();
        this.mBottomPadding = 0;
        this.isMouse = false;
        this.mIsDetachedState = false;
        this.mIsActivatedImmersiveScroll = false;
        this.mIsCanScroll = false;
        this.mImmersiveTopInset = 0;
        Integer numValueOf2 = null;
        this.mLastTappableInsets = null;
        this.mLastSysInsets = null;
        Context context2 = getContext();
        super.setOrientation(1);
        Context context3 = getContext();
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context3, attributeSet, ViewUtilsLollipop.STATE_LIST_ANIM_ATTRS, i, R.style.Widget_Design_AppBarLayout, new int[0]);
        try {
            if (typedArrayObtainStyledAttributes.hasValue(0)) {
                setStateListAnimator(AnimatorInflater.loadStateListAnimator(context3, typedArrayObtainStyledAttributes.getResourceId(0, 0)));
            }
            typedArrayObtainStyledAttributes.recycle();
            TypedArray typedArrayObtainStyledAttributes2 = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.AppBarLayout, i, R.style.Widget_Design_AppBarLayout, new int[0]);
            this.mAppbarState = new SeslAppbarState();
            Resources resources = getResources();
            this.mResources = resources;
            boolean zIsLightTheme = SeslMisc.isLightTheme(context2);
            if (typedArrayObtainStyledAttributes2.hasValue(0)) {
                Drawable drawable = typedArrayObtainStyledAttributes2.getDrawable(0);
                this.mBackground = drawable;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                setBackground(drawable);
            } else {
                this.mBackground = null;
                setBackgroundColor(resources.getColor(zIsLightTheme ? R.color.sesl_action_bar_background_color_light : R.color.sesl_action_bar_background_color_dark));
            }
            final ColorStateList colorStateList = MaterialResources.getColorStateList(context2, typedArrayObtainStyledAttributes2, 7);
            this.hasLiftOnScrollColor = colorStateList != null;
            final ColorStateList colorStateListOrNull = DrawableUtils.getColorStateListOrNull(getBackground());
            if (colorStateListOrNull != null) {
                final MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
                materialShapeDrawable.setFillColor(colorStateListOrNull);
                if (colorStateList != null) {
                    Context context4 = getContext();
                    TypedValue typedValueResolve = MaterialAttributes.resolve(R.attr.colorSurface, context4);
                    if (typedValueResolve != null) {
                        int i2 = typedValueResolve.resourceId;
                        if (i2 != 0) {
                            color = context4.getColor(i2);
                        } else {
                            color = typedValueResolve.data;
                        }
                        numValueOf = Integer.valueOf(color);
                    } else {
                        numValueOf = null;
                    }
                    this.liftOnScrollColorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.AppBarLayout$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            Integer num;
                            AppBarLayout appBarLayout = this.f$0;
                            ColorStateList colorStateList2 = colorStateListOrNull;
                            ColorStateList colorStateList3 = colorStateList;
                            MaterialShapeDrawable materialShapeDrawable2 = materialShapeDrawable;
                            Integer num2 = numValueOf;
                            int i3 = AppBarLayout.$r8$clinit;
                            int iLayer = MaterialColors.layer(((Float) valueAnimator.getAnimatedValue()).floatValue(), colorStateList2.getDefaultColor(), colorStateList3.getDefaultColor());
                            materialShapeDrawable2.setFillColor(ColorStateList.valueOf(iLayer));
                            if (appBarLayout.statusBarForeground != null && (num = appBarLayout.statusBarForegroundOriginalColor) != null && num.equals(num2)) {
                                appBarLayout.statusBarForeground.setTint(iLayer);
                            }
                            if (((ArrayList) appBarLayout.liftOnScrollListeners).isEmpty()) {
                                return;
                            }
                            ArrayList arrayList = (ArrayList) appBarLayout.liftOnScrollListeners;
                            int size = arrayList.size();
                            int i4 = 0;
                            while (i4 < size) {
                                Object obj = arrayList.get(i4);
                                i4++;
                                if (obj != null) {
                                    throw new ClassCastException();
                                }
                                if (materialShapeDrawable2.drawableState.fillColor != null) {
                                    throw null;
                                }
                            }
                        }
                    };
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    setBackground(materialShapeDrawable);
                } else {
                    materialShapeDrawable.initializeElevationOverlay(context2);
                    this.liftOnScrollColorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.AppBarLayout$$ExternalSyntheticLambda1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AppBarLayout appBarLayout = this.f$0;
                            MaterialShapeDrawable materialShapeDrawable2 = materialShapeDrawable;
                            int i3 = AppBarLayout.$r8$clinit;
                            float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            materialShapeDrawable2.setElevation(fFloatValue);
                            Drawable drawable2 = appBarLayout.statusBarForeground;
                            if (drawable2 instanceof MaterialShapeDrawable) {
                                ((MaterialShapeDrawable) drawable2).setElevation(fFloatValue);
                            }
                            Iterator it = ((ArrayList) appBarLayout.liftOnScrollListeners).iterator();
                            if (it.hasNext()) {
                                throw FragmentManager$$ExternalSyntheticOutline0.m(it);
                            }
                        }
                    };
                    WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                    setBackground(materialShapeDrawable);
                }
            }
            this.liftOnScrollColorDuration = MotionUtils.resolveThemeDuration(context2, R.attr.motionDurationMedium2, getResources().getInteger(R.integer.app_bar_elevation_anim_duration));
            this.liftOnScrollColorInterpolator = MotionUtils.resolveThemeInterpolator(context2, R.attr.motionEasingStandardInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
            if (typedArrayObtainStyledAttributes2.hasValue(5)) {
                setExpanded(typedArrayObtainStyledAttributes2.getBoolean(5, false), false, false);
            }
            if (typedArrayObtainStyledAttributes2.hasValue(4)) {
                ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(typedArrayObtainStyledAttributes2.getDimensionPixelSize(4, 0), this);
            }
            if (typedArrayObtainStyledAttributes2.hasValue(10)) {
                this.mUseCustomHeight = typedArrayObtainStyledAttributes2.getBoolean(10, false);
            }
            if (typedArrayObtainStyledAttributes2.hasValue(9)) {
                this.mSetCustomProportion = true;
                this.mCustomHeightProportion = typedArrayObtainStyledAttributes2.getFloat(9, 0.39f);
            } else {
                this.mSetCustomProportion = false;
                this.mCustomHeightProportion = 0.39f;
            }
            SeslAppBarHelper.Companion companion = SeslAppBarHelper.Companion;
            Context context5 = getContext();
            companion.getClass();
            this.mHeightProportion = SeslAppBarHelper.Companion.getAppBarProPortion(context5);
            if (typedArrayObtainStyledAttributes2.hasValue(11)) {
                this.mUseCustomPadding = typedArrayObtainStyledAttributes2.getBoolean(11, false);
            }
            if (this.mUseCustomPadding) {
                this.mBottomPadding = typedArrayObtainStyledAttributes2.getDimensionPixelSize(1, 0);
            } else {
                this.mBottomPadding = resources.getDimensionPixelOffset(R.dimen.sesl_extended_appbar_bottom_padding);
            }
            setPadding(0, 0, 0, this.mBottomPadding);
            this.mCollapsedHeight = resources.getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding) + this.mBottomPadding;
            if (typedArrayObtainStyledAttributes2.hasValue(4)) {
                ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(typedArrayObtainStyledAttributes2.getDimensionPixelSize(4, 0), this);
            }
            if (typedArrayObtainStyledAttributes2.hasValue(3)) {
                setKeyboardNavigationCluster(typedArrayObtainStyledAttributes2.getBoolean(3, false));
            }
            if (typedArrayObtainStyledAttributes2.hasValue(2)) {
                setTouchscreenBlocksFocus(typedArrayObtainStyledAttributes2.getBoolean(2, false));
            }
            this.appBarElevation = getResources().getDimension(R.dimen.design_appbar_elevation);
            this.liftOnScroll = typedArrayObtainStyledAttributes2.getBoolean(6, false);
            this.liftOnScrollTargetViewId = typedArrayObtainStyledAttributes2.getResourceId(8, -1);
            Drawable drawable2 = typedArrayObtainStyledAttributes2.getDrawable(12);
            Drawable drawable3 = this.statusBarForeground;
            if (drawable3 != drawable2) {
                if (drawable3 != null) {
                    drawable3.setCallback(null);
                }
                Drawable drawableMutate = drawable2 != null ? drawable2.mutate() : null;
                this.statusBarForeground = drawableMutate;
                if (drawableMutate instanceof MaterialShapeDrawable) {
                    numValueOf2 = Integer.valueOf(((MaterialShapeDrawable) drawableMutate).resolvedTintColor);
                } else {
                    ColorStateList colorStateListOrNull2 = DrawableUtils.getColorStateListOrNull(drawableMutate);
                    if (colorStateListOrNull2 != null) {
                        numValueOf2 = Integer.valueOf(colorStateListOrNull2.getDefaultColor());
                    }
                }
                this.statusBarForegroundOriginalColor = numValueOf2;
                Drawable drawable4 = this.statusBarForeground;
                if (drawable4 != null) {
                    if (drawable4.isStateful()) {
                        this.statusBarForeground.setState(getDrawableState());
                    }
                    Drawable drawable5 = this.statusBarForeground;
                    WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                    drawable5.setLayoutDirection(getLayoutDirection());
                    this.statusBarForeground.setVisible(getVisibility() == 0, false);
                    this.statusBarForeground.setCallback(this);
                }
                if (this.statusBarForeground != null && getTopInset() > 0) {
                    z = true;
                }
                setWillNotDraw(!z);
                WeakHashMap weakHashMap5 = ViewCompat.sViewPropertyAnimatorMap;
                postInvalidateOnAnimation();
            }
            typedArrayObtainStyledAttributes2.recycle();
            OnApplyWindowInsetsListener onApplyWindowInsetsListener = new OnApplyWindowInsetsListener() { // from class: com.google.android.material.appbar.AppBarLayout.1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public final WindowInsetsCompat onApplyWindowInsets(WindowInsetsCompat windowInsetsCompat, View view) {
                    int i3 = AppBarLayout.$r8$clinit;
                    WindowInsetsCompat.Impl impl = windowInsetsCompat.mImpl;
                    Insets insets = impl.getInsets(7);
                    Insets insets2 = impl.getInsets(64);
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
                    WeakHashMap weakHashMap6 = ViewCompat.sViewPropertyAnimatorMap;
                    WindowInsetsCompat windowInsetsCompat2 = appBarLayout.getFitsSystemWindows() ? windowInsetsCompat : null;
                    if (!Objects.equals(appBarLayout.lastInsets, windowInsetsCompat2)) {
                        appBarLayout.lastInsets = windowInsetsCompat2;
                        appBarLayout.setWillNotDraw(!(appBarLayout.statusBarForeground != null && appBarLayout.getTopInset() > 0));
                        appBarLayout.requestLayout();
                    }
                    return windowInsetsCompat;
                }
            };
            WeakHashMap weakHashMap6 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, onApplyWindowInsetsListener);
            this.mCurrentOrientation = resources.getConfiguration().orientation;
            this.mCurrentScreenHeight = resources.getConfiguration().screenHeightDp;
            StringBuilder sb = new StringBuilder("Init : mUseCustomHeight = ");
            sb.append(this.mUseCustomHeight);
            sb.append(", mCustomHeightProportion = ");
            sb.append(this.mCustomHeightProportion);
            sb.append(", mHeightProportion = ");
            sb.append(this.mHeightProportion);
            sb.append(", mUseCustomPadding = ");
            sb.append(this.mUseCustomPadding);
            sb.append(", mCurrentScreenHeight = ");
            TooltipPopup$$ExternalSyntheticOutline0.m(this.mCurrentScreenHeight, "AppBarLayout", sb);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
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

    public class LayoutParams extends LinearLayout.LayoutParams {
        public final CompressChildScrollEffect scrollEffect;
        public int scrollFlags;
        public final Interpolator scrollInterpolator;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.scrollFlags = 1;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AppBarLayout_Layout);
            this.scrollFlags = typedArrayObtainStyledAttributes.getInt(1, 0);
            this.scrollEffect = typedArrayObtainStyledAttributes.getInt(0, 0) != 1 ? null : new CompressChildScrollEffect();
            if (typedArrayObtainStyledAttributes.hasValue(2)) {
                this.scrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, typedArrayObtainStyledAttributes.getResourceId(2, 0));
            }
            typedArrayObtainStyledAttributes.recycle();
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

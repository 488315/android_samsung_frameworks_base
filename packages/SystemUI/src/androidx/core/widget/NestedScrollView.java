package androidx.core.widget;

import android.R;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.constraintlayout.motion.widget.TouchResponse;
import androidx.core.os.BuildCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.DifferentialMotionFlingController;
import androidx.core.view.DifferentialMotionFlingTarget;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.EdgeEffectCompat;
import androidx.reflect.provider.SeslSettingsReflector$SeslSystemReflector;
import androidx.reflect.view.SeslPointerIconReflector;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.view.SeslViewRuneReflector;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class NestedScrollView extends FrameLayout implements NestedScrollingParent3, NestedScrollingChild2 {
    public int mActivePointerId;
    public final AnonymousClass3 mAutoHide;
    public final AnonymousClass10 mCheckGoToTopAndAutoScrollCondition;
    public final NestedScrollingChildHelper mChildHelper;
    public View mChildToScrollTo;
    public final Context mContext;
    public final DifferentialMotionFlingController mDifferentialMotionFlingController;
    public final EdgeEffect mEdgeGlowBottom;
    public final EdgeEffect mEdgeGlowTop;
    public final boolean mFillViewport;
    public final AnonymousClass2 mGoToTopFadeInRunnable;
    public final AnonymousClass1 mGoToTopFadeOutRunnable;
    public final Rect mGoToTopRect;
    public int mGoToTopState;
    public boolean mHasNestedScrollRange;
    public boolean mHoverAreaEnter;
    public int mHoverBottomAreaHeight;
    public HoverScrollHandler mHoverHandler;
    public long mHoverRecognitionDurationTime;
    public long mHoverRecognitionStartTime;
    public int mHoverScrollDirection;
    public final boolean mHoverScrollEnabled;
    public int mHoverScrollSpeed;
    public long mHoverScrollStartTime;
    public final long mHoverScrollTimeInterval;
    public int mHoverTopAreaHeight;
    public int mInitialTopOffsetOfScreen;
    public boolean mIsBeingDragged;
    public boolean mIsGoToTopPressed;
    public boolean mIsHoverOverscrolled;
    public boolean mIsLaidOut;
    public boolean mIsLayoutDirty;
    public boolean mIsSupportGoToTop;
    public boolean mIsSupportHoverScroll;
    public int mLastMotionY;
    public long mLastScroll;
    public int mLastScrollerY;
    public final int mMaximumVelocity;
    public final int mMinimumVelocity;
    public boolean mNeedsHoverScroll;
    public int mNestedScrollRange;
    public int mNestedYOffset;
    public final AnonymousClass4 mOnLayoutChangeListener;
    public TouchResponse.AnonymousClass2 mOnScrollChangeListener;
    public final NestedScrollingParentHelper mParentHelper;
    public final float mPhysicalCoeff;
    public int mRemainNestedScrollRange;
    public SavedState mSavedState;
    public final int[] mScrollConsumed;
    public final int[] mScrollOffset;
    public final OverScroller mScroller;
    public final boolean mSmoothScrollingEnabled;
    public final Rect mTempRect;
    public final int mTouchSlop;
    public VelocityTracker mVelocityTracker;
    public float mVerticalScrollFactor;
    public final int[] mWindowOffsets;
    public static final float DECELERATION_RATE = (float) (Math.log(0.78d) / Math.log(0.9d));
    public static final AccessibilityDelegate ACCESSIBILITY_DELEGATE = new AccessibilityDelegate();
    public static final int[] SCROLLVIEW_STYLEABLE = {R.attr.fillViewport};

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AccessibilityDelegate extends AccessibilityDelegateCompat {
        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            accessibilityEvent.setClassName(ScrollView.class.getName());
            accessibilityEvent.setScrollable(nestedScrollView.getScrollRange$1() > 0);
            accessibilityEvent.setScrollX(nestedScrollView.getScrollX());
            accessibilityEvent.setScrollY(nestedScrollView.getScrollY());
            accessibilityEvent.setMaxScrollX(nestedScrollView.getScrollX());
            accessibilityEvent.setMaxScrollY(nestedScrollView.getScrollRange$1());
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int scrollRange$1;
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
            if (!nestedScrollView.isEnabled() || (scrollRange$1 = nestedScrollView.getScrollRange$1()) <= 0) {
                return;
            }
            accessibilityNodeInfoCompat.setScrollable(true);
            if (nestedScrollView.getScrollY() > 0) {
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP);
            }
            if (nestedScrollView.getScrollY() < scrollRange$1) {
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN);
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            if (!nestedScrollView.isEnabled()) {
                return false;
            }
            int height = nestedScrollView.getHeight();
            Rect rect = new Rect();
            if (nestedScrollView.getMatrix().isIdentity() && nestedScrollView.getGlobalVisibleRect(rect)) {
                height = rect.height();
            }
            if (i != 4096) {
                if (i == 8192 || i == 16908344) {
                    int max = Math.max(nestedScrollView.getScrollY() - ((height - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), 0);
                    if (max == nestedScrollView.getScrollY()) {
                        return false;
                    }
                    nestedScrollView.scrollTo(0, max);
                    return true;
                }
                if (i != 16908346) {
                    return false;
                }
            }
            int min = Math.min(nestedScrollView.getScrollY() + ((height - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), nestedScrollView.getScrollRange$1());
            if (min == nestedScrollView.getScrollY()) {
                return false;
            }
            nestedScrollView.scrollTo(0, min);
            return true;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DifferentialMotionFlingTargetImpl implements DifferentialMotionFlingTarget {
        public DifferentialMotionFlingTargetImpl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class HoverScrollHandler extends Handler {
        public final WeakReference mScrollView;

        public HoverScrollHandler(NestedScrollView nestedScrollView) {
            this.mScrollView = new WeakReference(nestedScrollView);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            NestedScrollView nestedScrollView = (NestedScrollView) this.mScrollView.get();
            if (nestedScrollView != null) {
                float f = NestedScrollView.DECELERATION_RATE;
                if (message.what != 1) {
                    return;
                }
                int scrollRange$1 = nestedScrollView.getScrollRange$1();
                long currentTimeMillis = System.currentTimeMillis();
                nestedScrollView.mHoverRecognitionDurationTime = (currentTimeMillis - nestedScrollView.mHoverRecognitionStartTime) / 1000;
                if (currentTimeMillis - nestedScrollView.mHoverScrollStartTime < nestedScrollView.mHoverScrollTimeInterval) {
                    return;
                }
                int applyDimension = (int) (TypedValue.applyDimension(1, 10.0f, nestedScrollView.mContext.getResources().getDisplayMetrics()) + 0.5f);
                nestedScrollView.mHoverScrollSpeed = applyDimension;
                long j = nestedScrollView.mHoverRecognitionDurationTime;
                if (j > 2 && j < 4) {
                    nestedScrollView.mHoverScrollSpeed = applyDimension + ((int) (applyDimension * 0.1d));
                } else if (j >= 4 && j < 5) {
                    nestedScrollView.mHoverScrollSpeed = applyDimension + ((int) (applyDimension * 0.2d));
                } else if (j >= 5) {
                    nestedScrollView.mHoverScrollSpeed = applyDimension + ((int) (applyDimension * 0.3d));
                }
                int i = nestedScrollView.mHoverScrollDirection == 2 ? nestedScrollView.mHoverScrollSpeed * (-1) : nestedScrollView.mHoverScrollSpeed;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                nestedScrollView.getLayoutDirection();
                boolean z = false;
                if ((i < 0 && nestedScrollView.getScrollY() > 0) || (i > 0 && nestedScrollView.getScrollY() < scrollRange$1)) {
                    nestedScrollView.startNestedScroll(2, 1);
                    if (!nestedScrollView.mChildHelper.dispatchNestedPreScroll(0, i, 1, null, null)) {
                        nestedScrollView.smoothScrollBy(0, i, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
                    } else if (nestedScrollView.mHasNestedScrollRange && (!nestedScrollView.canScrollVertically(-1) || nestedScrollView.mRemainNestedScrollRange != 0)) {
                        int i2 = nestedScrollView.mRemainNestedScrollRange - i;
                        nestedScrollView.mRemainNestedScrollRange = i2;
                        if (i2 < 0) {
                            nestedScrollView.mRemainNestedScrollRange = 0;
                        } else {
                            int i3 = nestedScrollView.mNestedScrollRange;
                            if (i2 > i3) {
                                nestedScrollView.mRemainNestedScrollRange = i3;
                            }
                        }
                    }
                    nestedScrollView.mHoverHandler.sendEmptyMessageDelayed(1, 7L);
                    return;
                }
                int overScrollMode = nestedScrollView.getOverScrollMode();
                if (overScrollMode == 0 || (overScrollMode == 1 && scrollRange$1 > 0)) {
                    z = true;
                }
                if (z && !nestedScrollView.mIsHoverOverscrolled) {
                    int i4 = nestedScrollView.mHoverScrollDirection;
                    if (i4 == 2) {
                        nestedScrollView.mEdgeGlowTop.setSize((nestedScrollView.getWidth() - nestedScrollView.getPaddingLeft()) - nestedScrollView.getPaddingRight(), nestedScrollView.getHeight());
                        nestedScrollView.mEdgeGlowTop.onAbsorb(10000);
                        if (!nestedScrollView.mEdgeGlowBottom.isFinished()) {
                            nestedScrollView.mEdgeGlowBottom.onRelease();
                        }
                    } else if (i4 == 1) {
                        nestedScrollView.mEdgeGlowBottom.setSize((nestedScrollView.getWidth() - nestedScrollView.getPaddingLeft()) - nestedScrollView.getPaddingRight(), nestedScrollView.getHeight());
                        nestedScrollView.mEdgeGlowBottom.onAbsorb(10000);
                        if (!nestedScrollView.mEdgeGlowTop.isFinished()) {
                            nestedScrollView.mEdgeGlowTop.onRelease();
                        }
                    }
                    if (!nestedScrollView.mEdgeGlowTop.isFinished() || !nestedScrollView.mEdgeGlowBottom.isFinished()) {
                        nestedScrollView.invalidate();
                    }
                    nestedScrollView.mIsHoverOverscrolled = true;
                }
                if (z || nestedScrollView.mIsHoverOverscrolled) {
                    return;
                }
                nestedScrollView.mIsHoverOverscrolled = true;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.core.widget.NestedScrollView.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int scrollPosition;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("HorizontalScrollView.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" scrollPosition=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.scrollPosition, "}", sb);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.scrollPosition);
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.scrollPosition = parcel.readInt();
        }
    }

    static {
        new LinearInterpolator();
    }

    public NestedScrollView(Context context) {
        this(context, null);
    }

    public static boolean isViewDescendantOf(View view, NestedScrollView nestedScrollView) {
        if (view == nestedScrollView) {
            return true;
        }
        Object parent = view.getParent();
        return (parent instanceof ViewGroup) && isViewDescendantOf((View) parent, nestedScrollView);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view);
    }

    public final boolean arrowScroll(int i) {
        View findFocus = findFocus();
        if (findFocus == this) {
            findFocus = null;
        }
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, i);
        int height = (int) (getHeight() * 0.5f);
        if (findNextFocus == null || !isWithinDeltaOfScreen(findNextFocus, height, getHeight())) {
            if (i == 33 && getScrollY() < height) {
                height = getScrollY();
            } else if (i == 130 && getChildCount() > 0) {
                View childAt = getChildAt(0);
                height = Math.min((childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin) - ((getHeight() + getScrollY()) - getPaddingBottom()), height);
            }
            if (height == 0) {
                return false;
            }
            if (i != 130) {
                height = -height;
            }
            doScrollY(height);
        } else {
            findNextFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(findNextFocus, this.mTempRect);
            int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
            this.mLastScrollerY = getScrollY();
            doScrollY(computeScrollDeltaToGetChildRectOnScreen);
            findNextFocus.requestFocus(i);
        }
        if (findFocus == null || !findFocus.isFocused() || isWithinDeltaOfScreen(findFocus, 0, getHeight())) {
            return true;
        }
        int descendantFocusability = getDescendantFocusability();
        setDescendantFocusability(131072);
        requestFocus();
        setDescendantFocusability(descendantFocusability);
        return true;
    }

    public final boolean canOverScroll() {
        int overScrollMode = getOverScrollMode();
        return overScrollMode == 0 || (overScrollMode == 1 && getScrollRange$1() > 0);
    }

    @Override // android.view.View
    public final int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    @Override // android.view.View
    public final int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @Override // android.view.View
    public final int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:5|(11:(2:51|(1:53))|13|14|15|16|(5:18|(1:20)|21|(1:26)|25)|(3:28|(1:(2:34|(1:36))(2:37|(1:39)))|32)|40|(1:42)(1:45)|43|44)(2:9|(1:11))|12|13|14|15|16|(0)|(0)|40|(0)(0)|43|44) */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0121  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void computeScroll() {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.NestedScrollView.computeScroll():void");
    }

    public final int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        int i2 = rect.bottom < (childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin ? i - verticalFadingEdgeLength : i;
        int i3 = rect.bottom;
        if (i3 > i2 && rect.top > scrollY) {
            return Math.min(rect.height() > height ? rect.top - scrollY : rect.bottom - i2, (childAt.getBottom() + layoutParams.bottomMargin) - i);
        }
        if (rect.top >= scrollY || i3 >= i2) {
            return 0;
        }
        return Math.max(rect.height() > height ? 0 - (i2 - rect.bottom) : 0 - (scrollY - rect.top), -getScrollY());
    }

    @Override // android.view.View
    public final int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override // android.view.View
    public final int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @Override // android.view.View
    public final int computeVerticalScrollRange() {
        int childCount = getChildCount();
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        if (childCount == 0) {
            return height;
        }
        View childAt = getChildAt(0);
        int bottom = childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
        int scrollY = getScrollY();
        int max = Math.max(0, bottom - height);
        return scrollY < 0 ? bottom - scrollY : scrollY > max ? (scrollY - max) + bottom : bottom;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 9) {
            if (this.mHasNestedScrollRange) {
                getLocationInWindow(this.mWindowOffsets);
                int i = this.mNestedScrollRange;
                int i2 = this.mInitialTopOffsetOfScreen;
                int i3 = this.mWindowOffsets[1];
                int i4 = i2 - i3;
                int i5 = i - i4;
                this.mRemainNestedScrollRange = i5;
                if (i4 < 0) {
                    this.mNestedScrollRange = i5;
                    this.mInitialTopOffsetOfScreen = i3;
                }
            }
            int toolType = motionEvent.getToolType(0);
            this.mNeedsHoverScroll = true;
            if (!this.mIsSupportHoverScroll || !this.mHoverScrollEnabled) {
                this.mNeedsHoverScroll = false;
            }
            if (this.mNeedsHoverScroll && toolType == 2) {
                if (Settings.System.getInt(this.mContext.getContentResolver(), SeslSettingsReflector$SeslSystemReflector.getField_SEM_PEN_HOVERING(), 0) != 1) {
                    this.mNeedsHoverScroll = false;
                }
            }
            if (this.mNeedsHoverScroll && toolType == 3) {
                this.mNeedsHoverScroll = false;
            }
        }
        if (!this.mNeedsHoverScroll) {
            return super.dispatchHoverEvent(motionEvent);
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int childCount = getChildCount();
        int scrollRange$1 = getScrollRange$1();
        if (this.mHoverHandler == null) {
            this.mHoverHandler = new HoverScrollHandler(this);
        }
        if (this.mHoverTopAreaHeight <= 0 || this.mHoverBottomAreaHeight <= 0) {
            this.mHoverTopAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
            this.mHoverBottomAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
        }
        int height = childCount != 0 ? getHeight() : 0;
        boolean z = motionEvent.getToolType(0) == 2;
        if ((y > this.mHoverTopAreaHeight && y < (height - this.mHoverBottomAreaHeight) - this.mRemainNestedScrollRange) || x <= 0 || x > getRight() || scrollRange$1 == 0 || ((y >= 0 && y <= this.mHoverTopAreaHeight && getScrollY() <= 0 && this.mIsHoverOverscrolled) || ((y >= height - this.mHoverBottomAreaHeight && y <= height && getScrollY() >= scrollRange$1 && this.mIsHoverOverscrolled) || ((z && motionEvent.getButtonState() == 32) || !z || ((KeyguardManager) this.mContext.getSystemService("keyguard")).inKeyguardRestrictedInputMode())))) {
            if (this.mHoverHandler.hasMessages(1)) {
                this.mHoverHandler.removeMessages(1);
                SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT();
                motionEvent.getDevice();
            }
            if ((y > this.mHoverTopAreaHeight && y < height - this.mHoverBottomAreaHeight) || x <= 0 || x > getRight()) {
                this.mIsHoverOverscrolled = false;
            }
            if (this.mHoverAreaEnter || this.mHoverScrollStartTime != 0) {
                SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT();
                motionEvent.getDevice();
            }
            this.mHoverRecognitionStartTime = 0L;
            this.mHoverScrollStartTime = 0L;
            this.mHoverAreaEnter = false;
            return super.dispatchHoverEvent(motionEvent);
        }
        if (!this.mHoverAreaEnter) {
            this.mHoverScrollStartTime = System.currentTimeMillis();
        }
        if (action != 7) {
            if (action == 9) {
                this.mHoverAreaEnter = true;
                if (y < 0 || y > this.mHoverTopAreaHeight) {
                    if (y >= (height - this.mHoverBottomAreaHeight) - this.mRemainNestedScrollRange && y <= height && !this.mHoverHandler.hasMessages(1)) {
                        this.mHoverRecognitionStartTime = System.currentTimeMillis();
                        SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_SCROLL_DOWN();
                        motionEvent.getDevice();
                        this.mHoverScrollDirection = 1;
                        this.mHoverHandler.sendEmptyMessage(1);
                        return true;
                    }
                } else if (!this.mHoverHandler.hasMessages(1)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_SCROLL_UP();
                    motionEvent.getDevice();
                    this.mHoverScrollDirection = 2;
                    this.mHoverHandler.sendEmptyMessage(1);
                    return true;
                }
            } else if (action == 10) {
                if (this.mHoverHandler.hasMessages(1)) {
                    this.mHoverHandler.removeMessages(1);
                }
                SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT();
                motionEvent.getDevice();
                this.mHoverRecognitionStartTime = 0L;
                this.mHoverScrollStartTime = 0L;
                this.mIsHoverOverscrolled = false;
                this.mHoverAreaEnter = false;
                this.mScroller.forceFinished(true);
                return super.dispatchHoverEvent(motionEvent);
            }
        } else {
            if (!this.mHoverAreaEnter) {
                this.mHoverAreaEnter = true;
                motionEvent.setAction(10);
                return super.dispatchHoverEvent(motionEvent);
            }
            if (y < 0 || y > this.mHoverTopAreaHeight) {
                if (y >= (height - this.mHoverBottomAreaHeight) - this.mRemainNestedScrollRange && y <= height && !this.mHoverHandler.hasMessages(1)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    if (!this.mIsHoverOverscrolled || this.mHoverScrollDirection == 2) {
                        SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_SCROLL_DOWN();
                        motionEvent.getDevice();
                    }
                    this.mHoverScrollDirection = 1;
                    this.mHoverHandler.sendEmptyMessage(1);
                }
            } else if (!this.mHoverHandler.hasMessages(1)) {
                this.mHoverRecognitionStartTime = System.currentTimeMillis();
                if (!this.mIsHoverOverscrolled || this.mHoverScrollDirection == 1) {
                    SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_SCROLL_UP();
                    motionEvent.getDevice();
                }
                this.mHoverScrollDirection = 2;
                this.mHoverHandler.sendEmptyMessage(1);
                return true;
            }
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    @Override // android.view.View
    public final boolean dispatchNestedFling(float f, float f2, boolean z) {
        return this.mChildHelper.dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.View
    public final boolean dispatchNestedPreFling(float f, float f2) {
        SeslViewReflector.setFrameContentVelocity(1.0f, this);
        return this.mChildHelper.dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.View
    public final boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return this.mChildHelper.dispatchNestedPreScroll(i, i2, 0, iArr, iArr2);
    }

    @Override // android.view.View
    public final boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return this.mChildHelper.dispatchNestedScrollInternal(i, i2, i3, i4, iArr, 0, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int childCount = getChildCount();
        int scrollRange$1 = getScrollRange$1();
        if (this.mHoverHandler == null) {
            this.mHoverHandler = new HoverScrollHandler(this);
        }
        if (this.mHoverTopAreaHeight <= 0 || this.mHoverBottomAreaHeight <= 0) {
            this.mHoverTopAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
            this.mHoverBottomAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
        }
        int height = childCount != 0 ? getHeight() : 0;
        boolean z = motionEvent.getToolType(0) == 2;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mIsGoToTopPressed = false;
            if (this.mIsSupportGoToTop && this.mGoToTopState != 2 && this.mGoToTopRect.contains(x, y)) {
                setupGoToTop();
                throw null;
            }
        } else if (action != 1) {
            if (action != 2) {
                if (action == 3 && this.mIsSupportGoToTop && this.mGoToTopState != 0) {
                    int[] iArr = StateSet.NOTHING;
                    throw null;
                }
            } else if (this.mIsSupportGoToTop && this.mGoToTopState == 2) {
                if (this.mGoToTopRect.contains(x, y)) {
                    return true;
                }
                this.mGoToTopState = 1;
                int[] iArr2 = StateSet.NOTHING;
                throw null;
            }
        } else if (this.mIsSupportGoToTop && this.mGoToTopState == 2) {
            if (canScrollVertically(-1)) {
                post(new Runnable() { // from class: androidx.core.widget.NestedScrollView.8
                    @Override // java.lang.Runnable
                    public final void run() {
                        NestedScrollView nestedScrollView = NestedScrollView.this;
                        nestedScrollView.mIsGoToTopPressed = true;
                        if (Settings.System.getInt(nestedScrollView.getContext().getContentResolver(), SettingsHelper.INDEX_REMOVE_ANIMATION, 0) == 1) {
                            NestedScrollView.this.scrollTo(0, 0);
                        } else {
                            NestedScrollView nestedScrollView2 = NestedScrollView.this;
                            nestedScrollView2.smoothScrollBy(0 - nestedScrollView2.getScrollX(), 0 - nestedScrollView2.getScrollY(), KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED);
                        }
                    }
                });
            }
            this.mGoToTopState = 1;
            int[] iArr3 = StateSet.NOTHING;
            throw null;
        }
        if ((y > this.mHoverTopAreaHeight && y < height - this.mHoverBottomAreaHeight) || scrollRange$1 == 0 || !z || motionEvent.getButtonState() != 32) {
            if (this.mHoverHandler.hasMessages(1)) {
                this.mHoverHandler.removeMessages(1);
            }
            this.mHoverRecognitionStartTime = 0L;
            this.mHoverScrollStartTime = 0L;
            this.mHoverAreaEnter = false;
            this.mIsHoverOverscrolled = false;
            return super.dispatchTouchEvent(motionEvent);
        }
        if (!this.mHoverAreaEnter) {
            this.mHoverScrollStartTime = System.currentTimeMillis();
        }
        switch (action) {
            case IKnoxCustomManager.Stub.TRANSACTION_getWifiHotspotEnabledState /* 211 */:
                if (this.mIsSupportGoToTop && this.mGoToTopState != 2 && this.mGoToTopRect.contains(x, y)) {
                    setupGoToTop();
                    throw null;
                }
                break;
            case IKnoxCustomManager.Stub.TRANSACTION_getWifiState /* 212 */:
                if (!this.mIsSupportGoToTop || this.mGoToTopState != 2) {
                    if (this.mHoverHandler.hasMessages(1)) {
                        this.mHoverHandler.removeMessages(1);
                    }
                    this.mHoverRecognitionStartTime = 0L;
                    this.mHoverScrollStartTime = 0L;
                    this.mIsHoverOverscrolled = false;
                    this.mHoverAreaEnter = false;
                    return super.dispatchTouchEvent(motionEvent);
                }
                Log.d("NestedScrollView", "pen up false GOTOTOP");
                if (canScrollVertically(-1)) {
                    smoothScrollBy(0 - getScrollX(), 0 - getScrollY(), IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
                    this.mEdgeGlowTop.onAbsorb(10000);
                    invalidate();
                }
                setupGoToTop();
                int[] iArr4 = StateSet.NOTHING;
                throw null;
            case IKnoxCustomManager.Stub.TRANSACTION_addAutoCallNumber /* 213 */:
                if (this.mIsSupportGoToTop && this.mGoToTopState == 2 && !this.mGoToTopRect.contains(x, y)) {
                    this.mGoToTopState = 1;
                    int[] iArr5 = StateSet.NOTHING;
                    throw null;
                }
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final void doScrollY(int i) {
        if (i != 0) {
            if (this.mSmoothScrollingEnabled) {
                smoothScrollBy(0, i, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
            } else {
                scrollBy(0, i);
            }
        }
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        int i;
        super.draw(canvas);
        int scrollY = getScrollY();
        int i2 = 0;
        if (!this.mEdgeGlowTop.isFinished()) {
            int save = canvas.save();
            int width = getWidth();
            int height = getHeight();
            int min = Math.min(0, scrollY);
            if (getClipToPadding()) {
                width -= getPaddingRight() + getPaddingLeft();
                i = getPaddingLeft();
            } else {
                i = 0;
            }
            if (getClipToPadding()) {
                height -= getPaddingBottom() + getPaddingTop();
                min += getPaddingTop();
            }
            canvas.translate(i, min);
            this.mEdgeGlowTop.setSize(width, height);
            if (this.mEdgeGlowTop.draw(canvas)) {
                postInvalidateOnAnimation();
            }
            canvas.restoreToCount(save);
        }
        if (this.mEdgeGlowBottom.isFinished()) {
            return;
        }
        int save2 = canvas.save();
        int width2 = getWidth();
        int height2 = getHeight();
        int max = Math.max(getScrollRange$1(), scrollY) + height2;
        if (getClipToPadding()) {
            width2 -= getPaddingRight() + getPaddingLeft();
            i2 = getPaddingLeft();
        }
        if (getClipToPadding()) {
            height2 -= getPaddingBottom() + getPaddingTop();
            max -= getPaddingBottom();
        }
        canvas.translate(i2 - width2, max);
        canvas.rotate(180.0f, width2, 0.0f);
        this.mEdgeGlowBottom.setSize(width2, height2);
        if (this.mEdgeGlowBottom.draw(canvas)) {
            postInvalidateOnAnimation();
        }
        canvas.restoreToCount(save2);
    }

    public final boolean executeKeyEvent(KeyEvent keyEvent) {
        this.mTempRect.setEmpty();
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            if (childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin > (getHeight() - getPaddingTop()) - getPaddingBottom()) {
                if (keyEvent.getAction() == 0) {
                    int keyCode = keyEvent.getKeyCode();
                    if (keyCode == 19) {
                        return !keyEvent.isAltPressed() ? arrowScroll(33) : fullScroll(33);
                    }
                    if (keyCode == 20) {
                        return !keyEvent.isAltPressed() ? arrowScroll(130) : fullScroll(130);
                    }
                    if (keyCode == 62) {
                        int i = keyEvent.isShiftPressed() ? 33 : 130;
                        boolean z = i == 130;
                        int height = getHeight();
                        if (z) {
                            this.mTempRect.top = getScrollY() + height;
                            int childCount = getChildCount();
                            if (childCount > 0) {
                                View childAt2 = getChildAt(childCount - 1);
                                int paddingBottom = getPaddingBottom() + childAt2.getBottom() + ((FrameLayout.LayoutParams) childAt2.getLayoutParams()).bottomMargin;
                                Rect rect = this.mTempRect;
                                if (rect.top + height > paddingBottom) {
                                    rect.top = paddingBottom - height;
                                }
                            }
                        } else {
                            this.mTempRect.top = getScrollY() - height;
                            Rect rect2 = this.mTempRect;
                            if (rect2.top < 0) {
                                rect2.top = 0;
                            }
                        }
                        Rect rect3 = this.mTempRect;
                        int i2 = rect3.top;
                        int i3 = height + i2;
                        rect3.bottom = i3;
                        scrollAndFocus(i, i2, i3);
                        return false;
                    }
                }
                return false;
            }
        }
        if (isFocused() && keyEvent.getKeyCode() != 4) {
            View findFocus = findFocus();
            if (findFocus == this) {
                findFocus = null;
            }
            View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, 130);
            if (findNextFocus != null && findNextFocus != this && findNextFocus.requestFocus(130)) {
                return true;
            }
        }
        return false;
    }

    public final void fling(int i) {
        if (getChildCount() > 0) {
            this.mScroller.fling(getScrollX(), getScrollY(), 0, i, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
            SeslViewReflector.setFrameContentVelocity(Math.abs(this.mScroller.getCurrVelocity()), this);
            startNestedScroll(2, 1);
            this.mLastScrollerY = getScrollY();
            postInvalidateOnAnimation();
            int i2 = BuildCompat.$r8$clinit;
            try {
                setFrameContentVelocity(Math.abs(this.mScroller.getCurrVelocity()));
            } catch (LinkageError unused) {
            }
        }
    }

    public final boolean fullScroll(int i) {
        int childCount;
        boolean z = i == 130;
        int height = getHeight();
        Rect rect = this.mTempRect;
        rect.top = 0;
        rect.bottom = height;
        if (z && (childCount = getChildCount()) > 0) {
            View childAt = getChildAt(childCount - 1);
            this.mTempRect.bottom = getPaddingBottom() + childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
            Rect rect2 = this.mTempRect;
            rect2.top = rect2.bottom - height;
        }
        Rect rect3 = this.mTempRect;
        return scrollAndFocus(i, rect3.top, rect3.bottom);
    }

    @Override // android.view.View
    public final float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int bottom = ((childAt.getBottom() + layoutParams.bottomMargin) - getScrollY()) - (getHeight() - getPaddingBottom());
        if (bottom < verticalFadingEdgeLength) {
            return bottom / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    @Override // android.view.ViewGroup
    public final int getNestedScrollAxes() {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mParentHelper;
        return nestedScrollingParentHelper.mNestedScrollAxesNonTouch | nestedScrollingParentHelper.mNestedScrollAxesTouch;
    }

    public final int getScrollRange$1() {
        if (getChildCount() <= 0) {
            return 0;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        return Math.max(0, ((childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin) - ((getHeight() - getPaddingTop()) - getPaddingBottom()));
    }

    @Override // android.view.View
    public final float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int scrollY = getScrollY();
        if (scrollY < verticalFadingEdgeLength) {
            return scrollY / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    public final float getVerticalScrollFactorCompat() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue typedValue = new TypedValue();
            Context context = getContext();
            if (!context.getTheme().resolveAttribute(R.attr.listPreferredItemHeight, typedValue, true)) {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
            this.mVerticalScrollFactor = typedValue.getDimension(context.getResources().getDisplayMetrics());
        }
        return this.mVerticalScrollFactor;
    }

    @Override // android.view.View
    public final boolean hasNestedScrollingParent() {
        return this.mChildHelper.hasNestedScrollingParent(0);
    }

    @Override // android.view.View
    public final boolean isNestedScrollingEnabled() {
        return this.mChildHelper.mIsNestedScrollingEnabled;
    }

    public final boolean isWithinDeltaOfScreen(View view, int i, int i2) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        return this.mTempRect.bottom + i >= getScrollY() && this.mTempRect.top - i <= getScrollY() + i2;
    }

    @Override // android.view.ViewGroup
    public final void measureChild(View view, int i, int i2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(FrameLayout.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft(), layoutParams.width), View.MeasureSpec.makeMeasureSpec(0, 0));
    }

    @Override // android.view.ViewGroup
    public final void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(FrameLayout.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, 0));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsLaidOut = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x020a  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onGenericMotionEvent(android.view.MotionEvent r31) {
        /*
            Method dump skipped, instructions count: 671
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.NestedScrollView.onGenericMotionEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        boolean z = true;
        if (action == 2 && this.mIsBeingDragged) {
            return true;
        }
        int i = action & 255;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    int i2 = this.mActivePointerId;
                    if (i2 != -1) {
                        int findPointerIndex = motionEvent.findPointerIndex(i2);
                        if (findPointerIndex == -1) {
                            Log.e("NestedScrollView", "Invalid pointerId=" + i2 + " in onInterceptTouchEvent");
                        } else {
                            int y = (int) motionEvent.getY(findPointerIndex);
                            if (Math.abs(y - this.mLastMotionY) > this.mTouchSlop) {
                                NestedScrollingParentHelper nestedScrollingParentHelper = this.mParentHelper;
                                if ((2 & (nestedScrollingParentHelper.mNestedScrollAxesNonTouch | nestedScrollingParentHelper.mNestedScrollAxesTouch)) == 0) {
                                    this.mIsBeingDragged = true;
                                    this.mLastMotionY = y;
                                    if (this.mVelocityTracker == null) {
                                        this.mVelocityTracker = VelocityTracker.obtain();
                                    }
                                    this.mVelocityTracker.addMovement(motionEvent);
                                    this.mNestedYOffset = 0;
                                    ViewParent parent = getParent();
                                    if (parent != null) {
                                        parent.requestDisallowInterceptTouchEvent(true);
                                    }
                                }
                            }
                        }
                    }
                } else if (i != 3) {
                    if (i == 6) {
                        onSecondaryPointerUp$1(motionEvent);
                    }
                }
            }
            this.mIsBeingDragged = false;
            this.mActivePointerId = -1;
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange$1())) {
                postInvalidateOnAnimation();
            }
            stopNestedScroll(0);
        } else {
            int y2 = (int) motionEvent.getY();
            int x = (int) motionEvent.getX();
            if (getChildCount() > 0) {
                int scrollY = getScrollY();
                View childAt = getChildAt(0);
                if (y2 >= childAt.getTop() - scrollY && y2 < childAt.getBottom() - scrollY && x >= childAt.getLeft() && x < childAt.getRight()) {
                    this.mLastMotionY = y2;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    VelocityTracker velocityTracker2 = this.mVelocityTracker;
                    if (velocityTracker2 == null) {
                        this.mVelocityTracker = VelocityTracker.obtain();
                    } else {
                        velocityTracker2.clear();
                    }
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mScroller.computeScrollOffset();
                    SeslViewReflector.setFrameContentVelocity(Math.abs(this.mScroller.getCurrVelocity()), this);
                    if (!stopGlowAnimations(motionEvent) && this.mScroller.isFinished()) {
                        z = false;
                    }
                    this.mIsBeingDragged = z;
                    startNestedScroll(2, 0);
                }
            }
            if (!stopGlowAnimations(motionEvent) && this.mScroller.isFinished()) {
                z = false;
            }
            this.mIsBeingDragged = z;
            VelocityTracker velocityTracker3 = this.mVelocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.recycle();
                this.mVelocityTracker = null;
            }
        }
        return this.mIsBeingDragged;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        super.onLayout(z, i, i2, i3, i4);
        this.mIsLayoutDirty = false;
        View view = this.mChildToScrollTo;
        if (view != null && isViewDescendantOf(view, this)) {
            View view2 = this.mChildToScrollTo;
            view2.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(view2, this.mTempRect);
            int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
            if (computeScrollDeltaToGetChildRectOnScreen != 0) {
                scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
            }
        }
        this.mChildToScrollTo = null;
        if (z) {
            getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_nestedscrollview_overlay_feature_hidden_height);
        }
        if (!this.mIsLaidOut) {
            if (this.mSavedState != null) {
                scrollTo(getScrollX(), this.mSavedState.scrollPosition);
                this.mSavedState = null;
            }
            if (getChildCount() > 0) {
                View childAt = getChildAt(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                i5 = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            } else {
                i5 = 0;
            }
            int paddingTop = ((i4 - i2) - getPaddingTop()) - getPaddingBottom();
            int scrollY = getScrollY();
            int i6 = (paddingTop >= i5 || scrollY < 0) ? 0 : paddingTop + scrollY > i5 ? i5 - paddingTop : scrollY;
            if (i6 != scrollY) {
                scrollTo(getScrollX(), i6);
            }
        }
        scrollTo(getScrollX(), getScrollY());
        this.mIsLaidOut = true;
        if (!z || super.computeHorizontalScrollRange() > super.computeHorizontalScrollExtent()) {
            return;
        }
        this.mHasNestedScrollRange = false;
        ViewParent parent = getParent();
        while (true) {
            if (parent == null || !(parent instanceof ViewGroup)) {
                break;
            }
            if (parent instanceof NestedScrollingParent2) {
                for (Class<?> cls = parent.getClass(); cls != null; cls = cls.getSuperclass()) {
                    if (cls.getSimpleName().equals("CoordinatorLayout")) {
                        ViewGroup viewGroup = (ViewGroup) parent;
                        viewGroup.getLocationInWindow(this.mWindowOffsets);
                        int height = viewGroup.getHeight() + this.mWindowOffsets[1];
                        getLocationInWindow(this.mWindowOffsets);
                        this.mInitialTopOffsetOfScreen = this.mWindowOffsets[1];
                        int height2 = getHeight() - (height - this.mInitialTopOffsetOfScreen);
                        this.mRemainNestedScrollRange = height2;
                        if (height2 < 0) {
                            this.mRemainNestedScrollRange = 0;
                        }
                        this.mNestedScrollRange = this.mRemainNestedScrollRange;
                        this.mHasNestedScrollRange = true;
                    }
                }
            }
            parent = parent.getParent();
        }
        if (this.mHasNestedScrollRange) {
            return;
        }
        this.mInitialTopOffsetOfScreen = 0;
        this.mRemainNestedScrollRange = 0;
        this.mNestedScrollRange = 0;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mFillViewport && View.MeasureSpec.getMode(i2) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int measuredHeight2 = (((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - layoutParams.topMargin) - layoutParams.bottomMargin;
            if (measuredHeight < measuredHeight2) {
                childAt.measure(FrameLayout.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + layoutParams.leftMargin + layoutParams.rightMargin, layoutParams.width), View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824));
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (z) {
            return false;
        }
        dispatchNestedFling(0.0f, f2, true);
        fling((int) f2);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f, float f2) {
        return dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        this.mChildHelper.dispatchNestedPreScroll(i, i2, 0, iArr, null);
    }

    @Override // androidx.core.view.NestedScrollingParent3
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        onNestedScrollInternal(i4, i5, iArr);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mParentHelper;
        if (i2 == 1) {
            nestedScrollingParentHelper.mNestedScrollAxesNonTouch = i;
        } else {
            nestedScrollingParentHelper.mNestedScrollAxesTouch = i;
        }
        startNestedScroll(2, i2);
    }

    public final void onNestedScrollInternal(int i, int i2, int[] iArr) {
        if (!this.mIsGoToTopPressed || this.mScroller.isFinished()) {
            int scrollY = getScrollY();
            scrollBy(0, i);
            this.mLastScrollerY = getScrollY();
            if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange$1())) {
                postInvalidateOnAnimation();
            }
            int scrollY2 = getScrollY() - scrollY;
            if (iArr != null) {
                iArr[1] = iArr[1] + scrollY2;
            }
            this.mChildHelper.dispatchNestedScrollInternal(0, scrollY2, 0, i - scrollY2, null, i2, iArr);
        }
    }

    @Override // android.view.View
    public final void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        super.scrollTo(i, i2);
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        if (i == 2) {
            i = 130;
        } else if (i == 1) {
            i = 33;
        }
        View findNextFocus = rect == null ? FocusFinder.getInstance().findNextFocus(this, null, i) : FocusFinder.getInstance().findNextFocusFromRect(this, rect, i);
        if (findNextFocus != null && isWithinDeltaOfScreen(findNextFocus, 0, getHeight())) {
            return findNextFocus.requestFocus(i, rect);
        }
        return false;
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSavedState = savedState;
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.scrollPosition = getScrollY();
        return savedState;
    }

    @Override // android.view.View
    public final void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        canOverScroll();
    }

    public final void onSecondaryPointerUp$1(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mLastMotionY = (int) motionEvent.getY(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View findFocus = findFocus();
        if (findFocus == null || this == findFocus || !isWithinDeltaOfScreen(findFocus, 0, i4)) {
            return;
        }
        findFocus.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(findFocus, this.mTempRect);
        doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        return (i & 2) != 0;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onStopNestedScroll(View view, int i) {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mParentHelper;
        if (i == 1) {
            nestedScrollingParentHelper.mNestedScrollAxesNonTouch = 0;
        } else {
            nestedScrollingParentHelper.mNestedScrollAxesTouch = 0;
        }
        stopNestedScroll(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0156  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r25) {
        /*
            Method dump skipped, instructions count: 808
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.NestedScrollView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final boolean overScrollByCompat(int i, int i2, int i3, int i4) {
        int i5;
        boolean z;
        int i6;
        boolean z2;
        getOverScrollMode();
        super.computeHorizontalScrollRange();
        super.computeHorizontalScrollExtent();
        computeVerticalScrollRange();
        super.computeVerticalScrollExtent();
        int i7 = i3 + i;
        if (i2 <= 0 && i2 >= 0) {
            i5 = i2;
            z = false;
        } else {
            i5 = 0;
            z = true;
        }
        if (i7 > i4) {
            i6 = i4;
        } else {
            if (i7 >= 0) {
                i6 = i7;
                z2 = false;
                if (z2 && !this.mChildHelper.hasNestedScrollingParent(1)) {
                    this.mScroller.springBack(i5, i6, 0, 0, 0, getScrollRange$1());
                }
                super.scrollTo(i5, i6);
                return !z || z2;
            }
            i6 = 0;
        }
        z2 = true;
        if (z2) {
            this.mScroller.springBack(i5, i6, 0, 0, 0, getScrollRange$1());
        }
        super.scrollTo(i5, i6);
        if (z) {
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestChildFocus(View view, View view2) {
        if (this.mIsLayoutDirty) {
            this.mChildToScrollTo = view2;
        } else {
            view2.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(view2, this.mTempRect);
            int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
            if (computeScrollDeltaToGetChildRectOnScreen != 0) {
                scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
            }
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean z2 = computeScrollDeltaToGetChildRectOnScreen != 0;
        if (z2) {
            if (z) {
                scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
                return z2;
            }
            smoothScrollBy(0, computeScrollDeltaToGetChildRectOnScreen, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
        }
        return z2;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        VelocityTracker velocityTracker;
        if (z && (velocityTracker = this.mVelocityTracker) != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    public final boolean scrollAndFocus(int i, int i2, int i3) {
        boolean z;
        int height = getHeight();
        int scrollY = getScrollY();
        int i4 = height + scrollY;
        boolean z2 = i == 33;
        ArrayList focusables = getFocusables(2);
        int size = focusables.size();
        View view = null;
        boolean z3 = false;
        for (int i5 = 0; i5 < size; i5++) {
            View view2 = (View) focusables.get(i5);
            int top = view2.getTop();
            int bottom = view2.getBottom();
            if (i2 < bottom && top < i3) {
                boolean z4 = i2 < top && bottom < i3;
                if (view == null) {
                    view = view2;
                    z3 = z4;
                } else {
                    boolean z5 = (z2 && top < view.getTop()) || (!z2 && bottom > view.getBottom());
                    if (z3) {
                        if (z4) {
                            if (!z5) {
                            }
                            view = view2;
                        }
                    } else if (z4) {
                        view = view2;
                        z3 = true;
                    } else {
                        if (!z5) {
                        }
                        view = view2;
                    }
                }
            }
        }
        if (view == null) {
            view = this;
        }
        if (i2 < scrollY || i3 > i4) {
            doScrollY(z2 ? i2 - scrollY : i3 - i4);
            z = true;
        } else {
            z = false;
        }
        if (view != findFocus()) {
            view.requestFocus(i);
        }
        return z;
    }

    @Override // android.view.View
    public final void scrollTo(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
            int width2 = childAt.getWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
            int height2 = childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            if (width >= width2 || i < 0) {
                i = 0;
            } else if (width + i > width2) {
                i = width2 - width;
            }
            if (height >= height2 || i2 < 0) {
                i2 = 0;
            } else if (height + i2 > height2) {
                i2 = height2 - height;
            }
            if (i == getScrollX() && i2 == getScrollY()) {
                return;
            }
            super.scrollTo(i, i2);
        }
    }

    @Override // android.view.View
    public final void setNestedScrollingEnabled(boolean z) {
        NestedScrollingChildHelper nestedScrollingChildHelper = this.mChildHelper;
        if (nestedScrollingChildHelper.mIsNestedScrollingEnabled) {
            View view = nestedScrollingChildHelper.mView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.stopNestedScroll(view);
        }
        nestedScrollingChildHelper.mIsNestedScrollingEnabled = z;
    }

    public final void setupGoToTop() {
        String string;
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        if (accessibilityManager == null || !accessibilityManager.isEnabled() || (string = Settings.Secure.getString(getContext().getContentResolver(), SettingsHelper.INDEX_ENABLED_ACCESSIBILITY_SERVICES)) == null || string.matches("(?i).*com.samsung.accessibility/com.samsung.android.app.talkback.TalkBackService.*") || string.matches("(?i).*com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService.*") || string.matches("(?i).*com.google.android.marvin.talkback.TalkBackService.*")) {
            return;
        }
        string.matches("(?i).*com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService.*");
    }

    public final boolean shouldAbsorb(EdgeEffect edgeEffect, int i) {
        if (i > 0) {
            return true;
        }
        float distance = EdgeEffectCompat.getDistance(edgeEffect) * getHeight();
        double log = Math.log((Math.abs(-i) * 0.35f) / (this.mPhysicalCoeff * 0.015f));
        double d = DECELERATION_RATE;
        return ((float) (Math.exp((d / (d - 1.0d)) * log) * ((double) (this.mPhysicalCoeff * 0.015f)))) < distance;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return true;
    }

    public final void smoothScrollBy(int i, int i2, int i3) {
        if (getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int height = childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
            int scrollY = getScrollY();
            this.mScroller.startScroll(getScrollX(), scrollY, 0, Math.max(0, Math.min(i2 + scrollY, Math.max(0, height - height2))) - scrollY, i3);
            stopNestedScroll(1);
            this.mLastScrollerY = getScrollY();
            postInvalidateOnAnimation();
        } else {
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                stopNestedScroll(1);
            }
            scrollBy(i, i2);
        }
        this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
    }

    public final void startNestedScroll(int i, int i2) {
        this.mChildHelper.startNestedScroll(i, i2);
    }

    public final boolean stopGlowAnimations(MotionEvent motionEvent) {
        boolean z;
        if (EdgeEffectCompat.getDistance(this.mEdgeGlowTop) != 0.0f) {
            EdgeEffectCompat.onPullDistance(this.mEdgeGlowTop, 0.0f, motionEvent.getX() / getWidth());
            z = true;
        } else {
            z = false;
        }
        if (EdgeEffectCompat.getDistance(this.mEdgeGlowBottom) == 0.0f) {
            return z;
        }
        EdgeEffectCompat.onPullDistance(this.mEdgeGlowBottom, 0.0f, 1.0f - (motionEvent.getX() / getWidth()));
        return true;
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public final void stopNestedScroll(int i) {
        this.mChildHelper.stopNestedScroll(i);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.nestedScrollViewStyle);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3) {
        this.mChildHelper.dispatchNestedPreScroll(i, i2, i3, iArr, null);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
        onNestedScrollInternal(i4, i5, null);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onStartNestedScroll(View view, View view2, int i) {
        return onStartNestedScroll(view, view2, i, 0);
    }

    @Override // android.view.View
    public final boolean startNestedScroll(int i) {
        return this.mChildHelper.startNestedScroll(i, 0);
    }

    @Override // android.view.View
    public final void stopNestedScroll() {
        stopNestedScroll(0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [android.view.View$OnLayoutChangeListener, androidx.core.widget.NestedScrollView$4] */
    /* JADX WARN: Type inference failed for: r3v3, types: [androidx.core.widget.NestedScrollView$10, java.lang.Runnable] */
    /* JADX WARN: Type inference failed for: r5v1, types: [androidx.core.widget.NestedScrollView$1] */
    /* JADX WARN: Type inference failed for: r5v2, types: [androidx.core.widget.NestedScrollView$2] */
    /* JADX WARN: Type inference failed for: r5v3, types: [androidx.core.widget.NestedScrollView$3] */
    public NestedScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        EdgeEffect edgeEffect;
        EdgeEffect edgeEffect2;
        this.mTempRect = new Rect();
        this.mIsLayoutDirty = true;
        this.mIsLaidOut = false;
        this.mChildToScrollTo = null;
        this.mIsBeingDragged = false;
        this.mSmoothScrollingEnabled = true;
        this.mActivePointerId = -1;
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mIsGoToTopPressed = false;
        this.mHoverScrollSpeed = 0;
        this.mIsSupportGoToTop = false;
        this.mGoToTopRect = new Rect();
        this.mGoToTopState = 0;
        new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f);
        Paint paint = new Paint();
        this.mGoToTopFadeOutRunnable = new Runnable() { // from class: androidx.core.widget.NestedScrollView.1
            @Override // java.lang.Runnable
            public final void run() {
                NestedScrollView nestedScrollView = NestedScrollView.this;
                float f = NestedScrollView.DECELERATION_RATE;
                nestedScrollView.getClass();
                throw null;
            }
        };
        this.mGoToTopFadeInRunnable = new Runnable() { // from class: androidx.core.widget.NestedScrollView.2
            @Override // java.lang.Runnable
            public final void run() {
                NestedScrollView nestedScrollView = NestedScrollView.this;
                float f = NestedScrollView.DECELERATION_RATE;
                nestedScrollView.getClass();
                throw null;
            }
        };
        this.mAutoHide = new Runnable() { // from class: androidx.core.widget.NestedScrollView.3
            @Override // java.lang.Runnable
            public final void run() {
                NestedScrollView nestedScrollView = NestedScrollView.this;
                float f = NestedScrollView.DECELERATION_RATE;
                nestedScrollView.setupGoToTop();
            }
        };
        this.mIsSupportHoverScroll = false;
        this.mHoverScrollEnabled = true;
        this.mHoverAreaEnter = false;
        this.mNeedsHoverScroll = false;
        this.mHoverTopAreaHeight = 0;
        this.mHoverBottomAreaHeight = 0;
        this.mHoverScrollDirection = -1;
        this.mHoverRecognitionDurationTime = 0L;
        this.mHoverRecognitionStartTime = 0L;
        this.mHoverScrollTimeInterval = 300L;
        this.mHoverScrollStartTime = 0L;
        this.mIsHoverOverscrolled = false;
        this.mInitialTopOffsetOfScreen = 0;
        this.mHasNestedScrollRange = false;
        this.mWindowOffsets = new int[2];
        this.mRemainNestedScrollRange = 0;
        this.mNestedScrollRange = 0;
        this.mDifferentialMotionFlingController = new DifferentialMotionFlingController(getContext(), new DifferentialMotionFlingTargetImpl());
        ?? r2 = new View.OnLayoutChangeListener() { // from class: androidx.core.widget.NestedScrollView.4
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                NestedScrollView nestedScrollView = NestedScrollView.this;
                nestedScrollView.post(nestedScrollView.mCheckGoToTopAndAutoScrollCondition);
            }
        };
        this.mOnLayoutChangeListener = r2;
        new Runnable() { // from class: androidx.core.widget.NestedScrollView.9
            @Override // java.lang.Runnable
            public final void run() {
                NestedScrollView.this.mEdgeGlowTop.onAbsorb(10000);
                NestedScrollView.this.invalidate();
            }
        };
        ?? r3 = new Runnable() { // from class: androidx.core.widget.NestedScrollView.10
            @Override // java.lang.Runnable
            public final void run() {
                boolean z = true;
                NestedScrollView nestedScrollView = NestedScrollView.this;
                float f = NestedScrollView.DECELERATION_RATE;
                nestedScrollView.getClass();
                NestedScrollView nestedScrollView2 = NestedScrollView.this;
                if (nestedScrollView2.mHoverScrollEnabled) {
                    if (nestedScrollView2.getChildCount() > 0 && (nestedScrollView2.getChildAt(0) instanceof ViewGroup)) {
                        ViewGroup viewGroup = (ViewGroup) nestedScrollView2.getChildAt(0);
                        if (viewGroup.getHeight() < nestedScrollView2.getHeight()) {
                            Log.i("NestedScrollView", "GTT HSC not support : Small Height child");
                        } else {
                            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                                View childAt = viewGroup.getChildAt(i2);
                                if (childAt.getVisibility() != 8 && (childAt.canScrollVertically(1) || childAt.canScrollVertically(-1))) {
                                    Log.i("NestedScrollView", "GTT HSC not support : Some child view can scroll index: " + i2 + " " + childAt);
                                }
                            }
                        }
                        z = false;
                        break;
                    }
                    nestedScrollView2.mIsSupportHoverScroll = z;
                    nestedScrollView2.mIsSupportGoToTop = z;
                }
            }
        };
        this.mCheckGoToTopAndAutoScrollCondition = r3;
        this.mContext = context;
        if (SeslViewRuneReflector.isEdgeEffectStretchType()) {
            edgeEffect = EdgeEffectCompat.Api31Impl.create(context, attributeSet);
        } else {
            edgeEffect = new EdgeEffect(context);
        }
        this.mEdgeGlowTop = edgeEffect;
        if (SeslViewRuneReflector.isEdgeEffectStretchType()) {
            edgeEffect2 = EdgeEffectCompat.Api31Impl.create(context, attributeSet);
        } else {
            edgeEffect2 = new EdgeEffect(context);
        }
        this.mEdgeGlowBottom = edgeEffect2;
        this.mPhysicalCoeff = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        this.mScroller = new OverScroller(getContext());
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        post(r3);
        addOnLayoutChangeListener(r2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, SCROLLVIEW_STYLEABLE, i, 0);
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        if (z != this.mFillViewport) {
            this.mFillViewport = z;
            requestLayout();
        }
        obtainStyledAttributes.recycle();
        this.mParentHelper = new NestedScrollingParentHelper(this);
        this.mChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        ViewCompat.setAccessibilityDelegate(this, ACCESSIBILITY_DELEGATE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        onNestedScrollInternal(i4, 0, null);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i) {
        if (getChildCount() <= 0) {
            super.addView(view, i);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScrollAccepted(View view, View view2, int i) {
        onNestedScrollAccepted(view, view2, i, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onStopNestedScroll(View view) {
        onStopNestedScroll(view, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() <= 0) {
            super.addView(view, layoutParams);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() <= 0) {
            super.addView(view, i, layoutParams);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }
}

package com.android.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.OverScroller;
import android.widget.ScrollView;
import com.android.internal.R;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.widget.RecyclerView;

/* loaded from: classes6.dex */
public class ResolverDrawerLayout extends ViewGroup {
    private static final String TAG = "ResolverDrawerLayout";
    private int mActivePointerId;
    private int mAlwaysShowHeight;
    private float mCollapseOffset;
    private int mCollapsibleHeight;
    private int mCollapsibleHeightReserved;
    private boolean mDisableDrag;
    private boolean mDismissLocked;
    private boolean mDismissOnScrollerFinished;
    private float mDragRemainder;
    private int mIgnoreOffsetTopLimitViewId;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private boolean mIsDragging;
    private final boolean mIsMaxCollapsedHeightSmallExplicit;
    private float mLastTouchY;
    private int mMaxCollapsedHeight;
    private int mMaxCollapsedHeightSmall;
    private int mMaxWidth;
    private int mMaxWidthResId;
    private MetricsLogger mMetricsLogger;
    private final float mMinFlingVelocity;
    private AbsListView mNestedListChild;
    private RecyclerView mNestedRecyclerChild;
    private OnCollapsedChangedListener mOnCollapsedChangedListener;
    private OnDismissedListener mOnDismissedListener;
    private boolean mOpenOnClick;
    private boolean mOpenOnLayout;
    private RunOnDismissedListener mRunOnDismissedListener;
    private Drawable mScrollIndicatorDrawable;
    private final OverScroller mScroller;
    private boolean mShowAtTop;
    private boolean mSmallCollapsed;
    private final Rect mTempRect;
    private int mTopOffset;
    private final ViewTreeObserver.OnTouchModeChangeListener mTouchModeChangeListener;
    private final int mTouchSlop;
    private int mUncollapsibleHeight;
    private final VelocityTracker mVelocityTracker;

    public interface OnCollapsedChangedListener {
        void onCollapsedChanged(boolean z);
    }

    public interface OnDismissedListener {
        void onDismissed();
    }

    public ResolverDrawerLayout(Context context) {
        this(context, null);
    }

    public ResolverDrawerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ResolverDrawerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDragRemainder = 0.0f;
        this.mIgnoreOffsetTopLimitViewId = 0;
        this.mActivePointerId = -1;
        this.mTempRect = new Rect();
        this.mTouchModeChangeListener = new ViewTreeObserver.OnTouchModeChangeListener() { // from class: com.android.internal.widget.ResolverDrawerLayout.1
            @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
            public void onTouchModeChanged(boolean z) {
                if (z || !ResolverDrawerLayout.this.hasFocus()) {
                    return;
                }
                ResolverDrawerLayout resolverDrawerLayout = ResolverDrawerLayout.this;
                if (resolverDrawerLayout.isDescendantClipped(resolverDrawerLayout.getFocusedChild())) {
                    ResolverDrawerLayout.this.smoothScrollTo(0, 0.0f);
                }
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ResolverDrawerLayout, i, 0);
        this.mMaxWidthResId = typedArrayObtainStyledAttributes.getResourceId(0, -1);
        this.mMaxWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, -1);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, 0);
        this.mMaxCollapsedHeight = dimensionPixelSize;
        this.mMaxCollapsedHeightSmall = typedArrayObtainStyledAttributes.getDimensionPixelSize(3, dimensionPixelSize);
        this.mIsMaxCollapsedHeightSmallExplicit = typedArrayObtainStyledAttributes.hasValue(3);
        this.mShowAtTop = typedArrayObtainStyledAttributes.getBoolean(4, false);
        if (typedArrayObtainStyledAttributes.hasValue(1)) {
            this.mIgnoreOffsetTopLimitViewId = typedArrayObtainStyledAttributes.getResourceId(1, 0);
        }
        typedArrayObtainStyledAttributes.recycle();
        this.mScrollIndicatorDrawable = this.mContext.getDrawable(R.drawable.scroll_indicator_material);
        this.mScroller = new OverScroller(context, AnimationUtils.loadInterpolator(context, 17563653));
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mMinFlingVelocity = r4.getScaledMinimumFlingVelocity();
        setImportantForAccessibility(1);
    }

    public void setMaxCollapsedHeight(int i) {
        if (i == this.mMaxCollapsedHeight) {
            return;
        }
        this.mMaxCollapsedHeight = i;
        if (!this.mIsMaxCollapsedHeightSmallExplicit) {
            this.mMaxCollapsedHeightSmall = i;
        }
        requestLayout();
    }

    public void setSmallCollapsed(boolean z) {
        if (this.mSmallCollapsed != z) {
            this.mSmallCollapsed = z;
            requestLayout();
        }
    }

    public boolean isSmallCollapsed() {
        return this.mSmallCollapsed;
    }

    public boolean isCollapsed() {
        return this.mCollapseOffset > 0.0f;
    }

    public void setShowAtTop(boolean z) {
        if (this.mShowAtTop != z) {
            this.mShowAtTop = z;
            requestLayout();
        }
    }

    public boolean getShowAtTop() {
        return this.mShowAtTop;
    }

    public void setCollapsed(boolean z) {
        if (!isLaidOut()) {
            this.mOpenOnLayout = !z;
        } else {
            smoothScrollTo(z ? this.mCollapsibleHeight : 0, 0.0f);
        }
    }

    public void setCollapsibleHeightReserved(int i) {
        int i2 = this.mCollapsibleHeightReserved;
        this.mCollapsibleHeightReserved = i;
        if (i2 != i) {
            requestLayout();
        }
        int i3 = this.mCollapsibleHeightReserved - i2;
        if (i3 != 0 && this.mIsDragging) {
            this.mLastTouchY -= i3;
        }
        int i4 = this.mCollapsibleHeight;
        this.mCollapsibleHeight = Math.min(i4, getMaxCollapsedHeight());
        if (updateCollapseOffset(i4, !isDragging())) {
            return;
        }
        invalidate();
    }

    public void setDismissLocked(boolean z) {
        this.mDismissLocked = z;
    }

    private boolean isMoving() {
        return this.mIsDragging || !this.mScroller.isFinished();
    }

    private boolean isDragging() {
        return this.mIsDragging || getNestedScrollAxes() == 2;
    }

    private boolean updateCollapseOffset(int i, boolean z) {
        int i2;
        if (i == this.mCollapsibleHeight) {
            return false;
        }
        if (getShowAtTop()) {
            setCollapseOffset(0.0f);
            return false;
        }
        if (isLaidOut()) {
            float f = this.mCollapseOffset;
            boolean z2 = f != 0.0f;
            if (z && i < (i2 = this.mCollapsibleHeight) && f == i) {
                setCollapseOffset(i2);
            } else {
                setCollapseOffset(Math.min(f, this.mCollapsibleHeight));
            }
            boolean z3 = this.mCollapseOffset != 0.0f;
            if (z2 != z3) {
                onCollapsedChanged(z3);
            }
        } else {
            setCollapseOffset(this.mOpenOnLayout ? 0.0f : this.mCollapsibleHeight);
        }
        return true;
    }

    private void setCollapseOffset(float f) {
        if (this.mCollapseOffset != f) {
            this.mCollapseOffset = f;
            requestLayout();
        }
    }

    private int getMaxCollapsedHeight() {
        return (isSmallCollapsed() ? this.mMaxCollapsedHeightSmall : this.mMaxCollapsedHeight) + this.mCollapsibleHeightReserved;
    }

    public void setOnDismissedListener(OnDismissedListener onDismissedListener) {
        this.mOnDismissedListener = onDismissedListener;
    }

    private boolean isDismissable() {
        return (this.mOnDismissedListener == null || this.mDismissLocked) ? false : true;
    }

    public void setOnCollapsedChangedListener(OnCollapsedChangedListener onCollapsedChangedListener) {
        this.mOnCollapsedChangedListener = onCollapsedChangedListener;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0065  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mVelocityTracker.clear();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.mInitialTouchX = x;
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
            this.mOpenOnClick = isListChildUnderClipped(x, y) && this.mCollapseOffset > 0.0f;
        } else if (actionMasked == 1) {
            resetTouch();
        } else if (actionMasked == 2) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            float f = y2 - this.mInitialTouchY;
            if (Math.abs(f) > this.mTouchSlop && findChildUnder(x2, y2) != null && (getNestedScrollAxes() & 2) == 0) {
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.mIsDragging = true;
                float f2 = this.mLastTouchY;
                int i = this.mTouchSlop;
                this.mLastTouchY = Math.max(f2 - i, Math.min(f + f2, f2 + i));
            }
        } else if (actionMasked != 3) {
            if (actionMasked == 6) {
                onSecondaryPointerUp(motionEvent);
            }
        }
        if (this.mIsDragging) {
            abortAnimation();
        }
        if (this.mDisableDrag) {
            this.mIsDragging = false;
        }
        return this.mIsDragging || this.mOpenOnClick;
    }

    private boolean isNestedListChildScrolled() {
        AbsListView absListView = this.mNestedListChild;
        return absListView != null && absListView.getChildCount() > 0 && (this.mNestedListChild.getFirstVisiblePosition() > 0 || this.mNestedListChild.getChildAt(0).getTop() < 0);
    }

    private boolean isNestedRecyclerChildScrolled() {
        RecyclerView recyclerView = this.mNestedRecyclerChild;
        if (recyclerView == null || recyclerView.getChildCount() <= 0) {
            return false;
        }
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.mNestedRecyclerChild.findViewHolderForAdapterPosition(0);
        return viewHolderFindViewHolderForAdapterPosition == null || viewHolderFindViewHolderForAdapterPosition.itemView.getTop() < 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01a1  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        int actionMasked = motionEvent.getActionMasked();
        this.mVelocityTracker.addMovement(motionEvent);
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.mInitialTouchX = x;
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
            this.mActivePointerId = motionEvent.getPointerId(0);
            boolean z = findChildUnder(this.mInitialTouchX, this.mInitialTouchY) != null;
            boolean z2 = isDismissable() || this.mCollapsibleHeight > 0;
            this.mIsDragging = z && z2;
            abortAnimation();
            return z2;
        }
        if (actionMasked == 1) {
            boolean z3 = this.mIsDragging;
            this.mIsDragging = false;
            if (!z3 && findChildUnder(this.mInitialTouchX, this.mInitialTouchY) == null && findChildUnder(motionEvent.getX(), motionEvent.getY()) == null && isDismissable()) {
                dispatchOnDismissed();
                resetTouch();
                return true;
            }
            if (this.mOpenOnClick && Math.abs(motionEvent.getX() - this.mInitialTouchX) < this.mTouchSlop && Math.abs(motionEvent.getY() - this.mInitialTouchY) < this.mTouchSlop) {
                smoothScrollTo(0, 0.0f);
                return true;
            }
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
            if (Math.abs(yVelocity) > this.mMinFlingVelocity) {
                if (getShowAtTop()) {
                    if (isDismissable() && yVelocity < 0.0f) {
                        abortAnimation();
                        dismiss();
                    } else {
                        smoothScrollTo(yVelocity < 0.0f ? 0 : this.mCollapsibleHeight, yVelocity);
                    }
                } else if (!isDismissable() || yVelocity <= 0.0f) {
                    scrollNestedScrollableChildBackToTop();
                    smoothScrollTo(yVelocity < 0.0f ? 0 : this.mCollapsibleHeight, yVelocity);
                } else {
                    float f = this.mCollapseOffset;
                    int i = this.mCollapsibleHeight;
                    if (f > i) {
                        smoothScrollTo(i + this.mUncollapsibleHeight, yVelocity);
                        this.mDismissOnScrollerFinished = true;
                    }
                }
            } else {
                float f2 = this.mCollapseOffset;
                int i2 = this.mCollapsibleHeight;
                if (f2 < i2 / 2) {
                    i2 = 0;
                }
                smoothScrollTo(i2, 0.0f);
            }
            resetTouch();
            return false;
        }
        if (actionMasked != 2) {
            if (actionMasked == 3) {
                if (this.mIsDragging) {
                    float f3 = this.mCollapseOffset;
                    int i3 = this.mCollapsibleHeight;
                    smoothScrollTo(f3 >= ((float) (i3 / 2)) ? i3 : 0, 0.0f);
                }
                resetTouch();
                return true;
            }
            if (actionMasked != 5) {
                if (actionMasked != 6) {
                    return false;
                }
                onSecondaryPointerUp(motionEvent);
                return false;
            }
            int actionIndex = motionEvent.getActionIndex();
            this.mActivePointerId = motionEvent.getPointerId(actionIndex);
            this.mInitialTouchX = motionEvent.getX(actionIndex);
            float y2 = motionEvent.getY(actionIndex);
            this.mLastTouchY = y2;
            this.mInitialTouchY = y2;
            return false;
        }
        int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        if (iFindPointerIndex < 0) {
            Log.e(TAG, "Bad pointer id " + this.mActivePointerId + ", resetting");
            this.mActivePointerId = motionEvent.getPointerId(0);
            this.mInitialTouchX = motionEvent.getX();
            float y3 = motionEvent.getY();
            this.mLastTouchY = y3;
            this.mInitialTouchY = y3;
            iFindPointerIndex = 0;
        }
        float x2 = motionEvent.getX(iFindPointerIndex);
        float y4 = motionEvent.getY(iFindPointerIndex);
        if (!this.mIsDragging) {
            float f4 = y4 - this.mInitialTouchY;
            if (Math.abs(f4) <= this.mTouchSlop || findChildUnder(x2, y4) == null) {
                z = false;
            } else {
                this.mIsDragging = true;
                float f5 = this.mLastTouchY;
                int i4 = this.mTouchSlop;
                this.mLastTouchY = Math.max(f5 - i4, Math.min(f4 + f5, f5 + i4));
            }
        }
        if (this.mIsDragging) {
            float f6 = y4 - this.mLastTouchY;
            if (f6 > 0.0f && isNestedListChildScrolled()) {
                this.mNestedListChild.smoothScrollBy((int) (-f6), 0);
            } else if (f6 > 0.0f && isNestedRecyclerChildScrolled()) {
                this.mNestedRecyclerChild.scrollBy(0, (int) (-f6));
            } else {
                performDrag(f6);
            }
        }
        this.mLastTouchY = y4;
        return z;
    }

    public void scrollNestedScrollableChildBackToTop() {
        if (isNestedListChildScrolled()) {
            this.mNestedListChild.smoothScrollToPosition(0);
        } else if (isNestedRecyclerChildScrolled()) {
            this.mNestedRecyclerChild.smoothScrollToPosition(0);
        }
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mInitialTouchX = motionEvent.getX(i);
            float y = motionEvent.getY(i);
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
            this.mActivePointerId = motionEvent.getPointerId(i);
        }
    }

    private void resetTouch() {
        this.mActivePointerId = -1;
        this.mIsDragging = false;
        this.mOpenOnClick = false;
        this.mLastTouchY = 0.0f;
        this.mInitialTouchY = 0.0f;
        this.mInitialTouchX = 0.0f;
        this.mVelocityTracker.clear();
    }

    private void dismiss() {
        RunOnDismissedListener runOnDismissedListener = new RunOnDismissedListener();
        this.mRunOnDismissedListener = runOnDismissedListener;
        post(runOnDismissedListener);
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            boolean zIsFinished = this.mScroller.isFinished();
            performDrag(this.mScroller.getCurrY() - this.mCollapseOffset);
            if (!zIsFinished) {
                postInvalidateOnAnimation();
            } else {
                if (!this.mDismissOnScrollerFinished || this.mOnDismissedListener == null) {
                    return;
                }
                dismiss();
            }
        }
    }

    private void abortAnimation() {
        this.mScroller.abortAnimation();
        this.mRunOnDismissedListener = null;
        this.mDismissOnScrollerFinished = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v6 */
    private float performDrag(float f) {
        int bottom;
        boolean z;
        if (getShowAtTop() || this.mDisableDrag) {
            return 0.0f;
        }
        float fMax = Math.max(0.0f, Math.min(this.mCollapseOffset + f, this.mCollapsibleHeight + this.mUncollapsibleHeight));
        float f2 = this.mCollapseOffset;
        if (fMax == f2) {
            return 0.0f;
        }
        float f3 = fMax - f2;
        float f4 = this.mDragRemainder + (f3 - ((int) f3));
        this.mDragRemainder = f4;
        if (f4 >= 1.0f) {
            this.mDragRemainder = f4 - 1.0f;
            f3 += 1.0f;
        } else if (f4 <= -1.0f) {
            this.mDragRemainder = f4 + 1.0f;
            f3 -= 1.0f;
        }
        View viewFindIgnoreOffsetLimitView = findIgnoreOffsetLimitView();
        if (viewFindIgnoreOffsetLimitView != null) {
            bottom = viewFindIgnoreOffsetLimitView.getBottom() + ((LayoutParams) viewFindIgnoreOffsetLimitView.getLayoutParams()).bottomMargin;
            z = true;
        } else {
            bottom = 0;
            z = false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (!layoutParams.ignoreOffset) {
                    childAt.offsetTopAndBottom((int) f3);
                } else if (z) {
                    int top = childAt.getTop();
                    int iMax = Math.max((int) (bottom + layoutParams.topMargin + f3), layoutParams.mFixedTop);
                    if (top != iMax) {
                        childAt.offsetTopAndBottom(iMax - top);
                    }
                    bottom = childAt.getBottom() + layoutParams.bottomMargin;
                }
            }
        }
        boolean z2 = this.mCollapseOffset != 0.0f;
        this.mCollapseOffset = fMax;
        this.mTopOffset = (int) (this.mTopOffset + f3);
        ?? r3 = fMax == 0.0f ? 0 : 1;
        if (z2 != r3) {
            onCollapsedChanged(r3);
            getMetricsLogger().write(new LogMaker(MetricsProto.MetricsEvent.ACTION_SHARESHEET_COLLAPSED_CHANGED).setSubtype(r3));
        }
        onScrollChanged(0, (int) fMax, 0, (int) (fMax - f3));
        postInvalidateOnAnimation();
        return f3;
    }

    private void onCollapsedChanged(boolean z) {
        notifyViewAccessibilityStateChangedIfNeeded(0);
        if (this.mScrollIndicatorDrawable != null) {
            setWillNotDraw(!z);
        }
        OnCollapsedChangedListener onCollapsedChangedListener = this.mOnCollapsedChangedListener;
        if (onCollapsedChangedListener != null) {
            onCollapsedChangedListener.onCollapsedChanged(z);
        }
    }

    void dispatchOnDismissed() {
        OnDismissedListener onDismissedListener = this.mOnDismissedListener;
        if (onDismissedListener != null) {
            onDismissedListener.onDismissed();
        }
        RunOnDismissedListener runOnDismissedListener = this.mRunOnDismissedListener;
        if (runOnDismissedListener != null) {
            removeCallbacks(runOnDismissedListener);
            this.mRunOnDismissedListener = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void smoothScrollTo(int i, float f) {
        int iAbs;
        abortAnimation();
        int i2 = (int) this.mCollapseOffset;
        int i3 = i - i2;
        if (i == 0 && i3 == 0) {
            return;
        }
        int height = getHeight();
        int i4 = height / 2;
        float f2 = height;
        float f3 = i4;
        float fDistanceInfluenceForSnapDuration = f3 + (distanceInfluenceForSnapDuration(Math.min(1.0f, (Math.abs(i3) * 1.0f) / f2)) * f3);
        float fAbs = Math.abs(f);
        if (fAbs > 0.0f) {
            iAbs = Math.round(Math.abs(fDistanceInfluenceForSnapDuration / fAbs) * 1000.0f) * 4;
        } else {
            iAbs = (int) (((Math.abs(i3) / f2) + 1.0f) * 100.0f);
        }
        this.mScroller.startScroll(0, i2, 0, i3, Math.min(iAbs, 300));
        postInvalidateOnAnimation();
    }

    private float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((float) ((f - 0.5f) * 0.4712389167638204d));
    }

    private View findChildUnder(float f, float f2) {
        return findChildUnder(this, f, f2);
    }

    private static View findChildUnder(ViewGroup viewGroup, float f, float f2) {
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (isChildUnder(childAt, f, f2)) {
                return childAt;
            }
        }
        return null;
    }

    private View findListChildUnder(float f, float f2) {
        View viewFindChildUnder = findChildUnder(f, f2);
        while (viewFindChildUnder != null) {
            f -= viewFindChildUnder.getX();
            f2 -= viewFindChildUnder.getY();
            if (viewFindChildUnder instanceof AbsListView) {
                return findChildUnder((ViewGroup) viewFindChildUnder, f, f2);
            }
            viewFindChildUnder = viewFindChildUnder instanceof ViewGroup ? findChildUnder((ViewGroup) viewFindChildUnder, f, f2) : null;
        }
        return viewFindChildUnder;
    }

    private boolean isListChildUnderClipped(float f, float f2) {
        View viewFindListChildUnder = findListChildUnder(f, f2);
        return viewFindListChildUnder != null && isDescendantClipped(viewFindListChildUnder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDescendantClipped(View view) {
        this.mTempRect.set(0, 0, view.getWidth(), view.getHeight());
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        if (view.getParent() != this) {
            ViewParent parent = view.getParent();
            while (parent != this) {
                view = parent;
                parent = view.getParent();
            }
        }
        int height = getHeight() - getPaddingBottom();
        int childCount = getChildCount();
        for (int iIndexOfChild = indexOfChild(view) + 1; iIndexOfChild < childCount; iIndexOfChild++) {
            View childAt = getChildAt(iIndexOfChild);
            if (childAt.getVisibility() != 8) {
                height = Math.min(height, childAt.getTop());
            }
        }
        return this.mTempRect.bottom > height;
    }

    private static boolean isChildUnder(View view, float f, float f2) {
        float x = view.getX();
        float y = view.getY();
        return f >= x && f2 >= y && f < ((float) view.getWidth()) + x && f2 < ((float) view.getHeight()) + y;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (isInTouchMode() || !isDescendantClipped(view2)) {
            return;
        }
        smoothScrollTo(0, 0.0f);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnTouchModeChangeListener(this.mTouchModeChangeListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnTouchModeChangeListener(this.mTouchModeChangeListener);
        abortAnimation();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        if ((i & 2) == 0) {
            return false;
        }
        if (view2 instanceof AbsListView) {
            this.mNestedListChild = (AbsListView) view2;
        }
        if (!(view2 instanceof RecyclerView)) {
            return true;
        }
        this.mNestedRecyclerChild = (RecyclerView) view2;
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        super.onNestedScrollAccepted(view, view2, i);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onStopNestedScroll(View view) {
        super.onStopNestedScroll(view);
        if (this.mScroller.isFinished()) {
            float f = this.mCollapseOffset;
            int i = this.mCollapsibleHeight;
            if (f < i / 2) {
                i = 0;
            }
            smoothScrollTo(i, 0.0f);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        if (i4 < 0) {
            performDrag(-i4);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        if (i2 > 0) {
            iArr[1] = (int) (-performDrag(-i2));
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedPreFling(View view, float f, float f2) {
        if (getShowAtTop() || f2 <= this.mMinFlingVelocity || this.mCollapseOffset == 0.0f) {
            return false;
        }
        smoothScrollTo(0, f2);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004c  */
    @Override // android.view.ViewGroup, android.view.ViewParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (z || Math.abs(f2) <= this.mMinFlingVelocity) {
            return false;
        }
        if (getShowAtTop()) {
            if (isDismissable() && f2 > 0.0f) {
                abortAnimation();
                dismiss();
            } else {
                smoothScrollTo(f2 < 0.0f ? this.mCollapsibleHeight : 0, f2);
            }
        } else if (!isDismissable() || f2 >= 0.0f) {
            smoothScrollTo(f2 <= 0.0f ? this.mCollapsibleHeight : 0, f2);
        } else {
            float f3 = this.mCollapseOffset;
            int i = this.mCollapsibleHeight;
            if (f3 > i) {
                smoothScrollTo(i + this.mUncollapsibleHeight, f2);
                this.mDismissOnScrollerFinished = true;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean performAccessibilityActionCommon(int i) {
        if (i == 4096 || i == 262144) {
            if (this.mCollapseOffset != 0.0f) {
                smoothScrollTo(0, 0.0f);
                return true;
            }
        } else if (i == 524288) {
            float f = this.mCollapseOffset;
            int i2 = this.mCollapsibleHeight;
            if (f < i2) {
                smoothScrollTo(i2, 0.0f);
                return true;
            }
        } else if (i != 1048576) {
            if (i == 16908346) {
            }
        } else if (this.mCollapseOffset < this.mCollapsibleHeight + this.mUncollapsibleHeight && isDismissable()) {
            smoothScrollTo(this.mCollapsibleHeight + this.mUncollapsibleHeight, 0.0f);
            this.mDismissOnScrollerFinished = true;
            return true;
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedPrePerformAccessibilityAction(View view, int i, Bundle bundle) {
        if (super.onNestedPrePerformAccessibilityAction(view, i, bundle)) {
            return true;
        }
        return performAccessibilityActionCommon(i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ScrollView.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (isEnabled()) {
            if (this.mCollapseOffset != 0.0f) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
                accessibilityNodeInfo.setScrollable(true);
            }
            float f = this.mCollapseOffset;
            int i = this.mCollapsibleHeight;
            if (f < this.mUncollapsibleHeight + i && (f < i || isDismissable())) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
                accessibilityNodeInfo.setScrollable(true);
            }
            if (this.mCollapseOffset < this.mCollapsibleHeight) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
            }
            if (this.mCollapseOffset < this.mCollapsibleHeight + this.mUncollapsibleHeight && isDismissable()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
            }
        }
        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS);
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (i == AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS.getId()) {
            return false;
        }
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        return performAccessibilityActionCommon(i);
    }

    @Override // android.view.View
    public void onDrawForeground(Canvas canvas) {
        Drawable drawable = this.mScrollIndicatorDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        super.onDrawForeground(canvas);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        ResolverDrawerLayout resolverDrawerLayout = this;
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int i7 = resolverDrawerLayout.mMaxWidth;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i7 >= 0 ? Math.min(size, i7 + resolverDrawerLayout.getPaddingLeft() + resolverDrawerLayout.getPaddingRight()) : size, 1073741824);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size2, 1073741824);
        int childCount = resolverDrawerLayout.getChildCount();
        int i8 = 0;
        int measuredHeight = 0;
        while (true) {
            i3 = Integer.MIN_VALUE;
            if (i8 >= childCount) {
                break;
            }
            int i9 = iMakeMeasureSpec;
            View childAt = resolverDrawerLayout.getChildAt(i8);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (!layoutParams.alwaysShow || childAt.getVisibility() == 8) {
                measuredHeight = measuredHeight;
            } else {
                if (layoutParams.maxHeight != -1) {
                    int i10 = size2 - measuredHeight;
                    resolverDrawerLayout.measureChildWithMargins(childAt, i9, 0, View.MeasureSpec.makeMeasureSpec(layoutParams.maxHeight, Integer.MIN_VALUE), layoutParams.maxHeight > i10 ? layoutParams.maxHeight - i10 : 0);
                    resolverDrawerLayout = this;
                    i6 = measuredHeight;
                } else {
                    resolverDrawerLayout = this;
                    i6 = measuredHeight;
                    resolverDrawerLayout.measureChildWithMargins(childAt, i9, 0, iMakeMeasureSpec2, i6);
                }
                measuredHeight = i6 + childAt.getMeasuredHeight();
            }
            i8++;
            iMakeMeasureSpec = i9;
        }
        int i11 = iMakeMeasureSpec;
        resolverDrawerLayout.mAlwaysShowHeight = measuredHeight;
        int i12 = 0;
        while (i12 < childCount) {
            View childAt2 = resolverDrawerLayout.getChildAt(i12);
            LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
            if (layoutParams2.alwaysShow || childAt2.getVisibility() == 8) {
                i4 = iMakeMeasureSpec2;
                measuredHeight = measuredHeight;
            } else {
                if (layoutParams2.maxHeight != -1) {
                    int i13 = size2 - measuredHeight;
                    resolverDrawerLayout.measureChildWithMargins(childAt2, i11, 0, View.MeasureSpec.makeMeasureSpec(layoutParams2.maxHeight, i3), layoutParams2.maxHeight > i13 ? layoutParams2.maxHeight - i13 : 0);
                    resolverDrawerLayout = this;
                    i4 = iMakeMeasureSpec2;
                    i5 = measuredHeight;
                } else {
                    resolverDrawerLayout = this;
                    i4 = iMakeMeasureSpec2;
                    i5 = measuredHeight;
                    resolverDrawerLayout.measureChildWithMargins(childAt2, i11, 0, i4, i5);
                }
                measuredHeight = i5 + childAt2.getMeasuredHeight();
            }
            i12++;
            iMakeMeasureSpec2 = i4;
            i3 = Integer.MIN_VALUE;
        }
        int i14 = measuredHeight;
        int i15 = resolverDrawerLayout.mCollapsibleHeight;
        if (resolverDrawerLayout.mDisableDrag) {
            resolverDrawerLayout.mCollapsibleHeight = 0;
        } else {
            resolverDrawerLayout.mCollapsibleHeight = Math.max(0, (i14 - resolverDrawerLayout.mAlwaysShowHeight) - resolverDrawerLayout.getMaxCollapsedHeight());
        }
        resolverDrawerLayout.mUncollapsibleHeight = i14 - resolverDrawerLayout.mCollapsibleHeight;
        resolverDrawerLayout.updateCollapseOffset(i15, !resolverDrawerLayout.isDragging());
        if (size2 < i14 || resolverDrawerLayout.getShowAtTop()) {
            resolverDrawerLayout.mTopOffset = 0;
        } else {
            resolverDrawerLayout.mTopOffset = Math.max(0, size2 - i14) + ((int) resolverDrawerLayout.mCollapseOffset);
        }
        resolverDrawerLayout.setMeasuredDimension(size, size2);
    }

    public int getAlwaysShowHeight() {
        return this.mAlwaysShowHeight;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mMaxWidthResId > 0) {
            this.mMaxWidth = getResources().getDimensionPixelSize(this.mMaxWidthResId);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        int width = getWidth();
        int i5 = this.mTopOffset;
        int paddingLeft = getPaddingLeft();
        int paddingRight = (width - getPaddingRight()) - paddingLeft;
        int childCount = getChildCount();
        View view = null;
        boolean z2 = false;
        int measuredHeight = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams.hasNestedScrollIndicator) {
                view = childAt;
            }
            if (childAt.getVisibility() != 8) {
                int i7 = this.mIgnoreOffsetTopLimitViewId;
                if (i7 != 0 && !z2 && i7 == childAt.getId()) {
                    measuredHeight = layoutParams.bottomMargin + childAt.getBottom();
                    z2 = true;
                }
                int iMax = i5 + layoutParams.topMargin;
                if (layoutParams.ignoreOffset) {
                    if (!isDragging()) {
                        layoutParams.mFixedTop = (int) (iMax - this.mCollapseOffset);
                    }
                    if (z2) {
                        iMax = Math.max(measuredHeight + layoutParams.topMargin, (int) (iMax - this.mCollapseOffset));
                        measuredHeight = childAt.getMeasuredHeight() + iMax + layoutParams.bottomMargin;
                    } else {
                        iMax = (int) (iMax - this.mCollapseOffset);
                    }
                }
                int measuredHeight2 = childAt.getMeasuredHeight() + iMax;
                int measuredWidth = childAt.getMeasuredWidth();
                int i8 = ((paddingRight - measuredWidth) / 2) + paddingLeft;
                childAt.layout(i8, iMax, measuredWidth + i8, measuredHeight2);
                i5 = measuredHeight2 + layoutParams.bottomMargin;
            }
        }
        if (this.mScrollIndicatorDrawable != null) {
            if (view != null) {
                int left = view.getLeft();
                int right = view.getRight();
                int top = view.getTop();
                this.mScrollIndicatorDrawable.setBounds(left, top - this.mScrollIndicatorDrawable.getIntrinsicHeight(), right, top);
                setWillNotDraw(!isCollapsed());
                return;
            }
            this.mScrollIndicatorDrawable = null;
            setWillNotDraw(true);
        }
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.open = this.mCollapsibleHeight > 0 && this.mCollapseOffset == 0.0f;
        savedState.mCollapsibleHeightReserved = this.mCollapsibleHeightReserved;
        return savedState;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mOpenOnLayout = savedState.open;
        this.mCollapsibleHeightReserved = savedState.mCollapsibleHeightReserved;
    }

    private View findIgnoreOffsetLimitView() {
        View viewFindViewById;
        int i = this.mIgnoreOffsetTopLimitViewId;
        if (i == 0 || (viewFindViewById = findViewById(i)) == null || viewFindViewById == this || viewFindViewById.getParent() != this || viewFindViewById.getVisibility() == 8) {
            return null;
        }
        return viewFindViewById;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public boolean alwaysShow;
        public boolean hasNestedScrollIndicator;
        public boolean ignoreOffset;
        int mFixedTop;
        public int maxHeight;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ResolverDrawerLayout_LayoutParams);
            this.alwaysShow = typedArrayObtainStyledAttributes.getBoolean(1, false);
            this.ignoreOffset = typedArrayObtainStyledAttributes.getBoolean(3, false);
            this.hasNestedScrollIndicator = typedArrayObtainStyledAttributes.getBoolean(2, false);
            this.maxHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(4, -1);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.alwaysShow = layoutParams.alwaysShow;
            this.ignoreOffset = layoutParams.ignoreOffset;
            this.hasNestedScrollIndicator = layoutParams.hasNestedScrollIndicator;
            this.maxHeight = layoutParams.maxHeight;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.android.internal.widget.ResolverDrawerLayout.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private int mCollapsibleHeightReserved;
        boolean open;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.open = parcel.readInt() != 0;
            this.mCollapsibleHeightReserved = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.open ? 1 : 0);
            parcel.writeInt(this.mCollapsibleHeightReserved);
        }
    }

    private class RunOnDismissedListener implements Runnable {
        private RunOnDismissedListener() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ResolverDrawerLayout.this.dispatchOnDismissed();
        }
    }

    private MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    public void semSetMaxWidth(int i) {
        this.mMaxWidth = i;
    }

    public void semDisableDrag(boolean z) {
        this.mDisableDrag = z;
    }
}

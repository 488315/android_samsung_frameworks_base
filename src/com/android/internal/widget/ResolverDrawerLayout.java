package com.android.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ResolverDrawerLayout, i, 0);
        this.mMaxWidthResId = obtainStyledAttributes.getResourceId(0, -1);
        this.mMaxWidth = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        this.mMaxCollapsedHeight = dimensionPixelSize;
        this.mMaxCollapsedHeightSmall = obtainStyledAttributes.getDimensionPixelSize(3, dimensionPixelSize);
        this.mIsMaxCollapsedHeightSmallExplicit = obtainStyledAttributes.hasValue(3);
        this.mShowAtTop = obtainStyledAttributes.getBoolean(4, false);
        if (obtainStyledAttributes.hasValue(1)) {
            this.mIgnoreOffsetTopLimitViewId = obtainStyledAttributes.getResourceId(1, 0);
        }
        obtainStyledAttributes.recycle();
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

    @Override // android.view.ViewGroup
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
        } else {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
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
            }
            resetTouch();
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
        RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.mNestedRecyclerChild.findViewHolderForAdapterPosition(0);
        return findViewHolderForAdapterPosition == null || findViewHolderForAdapterPosition.itemView.getTop() < 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00c6  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
            Method dump skipped, instructions count: 510
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.ResolverDrawerLayout.onTouchEvent(android.view.MotionEvent):boolean");
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
            boolean isFinished = this.mScroller.isFinished();
            performDrag(this.mScroller.getCurrY() - this.mCollapseOffset);
            if (!isFinished) {
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
        int i;
        boolean z;
        if (getShowAtTop() || this.mDisableDrag) {
            return 0.0f;
        }
        float max = Math.max(0.0f, Math.min(this.mCollapseOffset + f, this.mCollapsibleHeight + this.mUncollapsibleHeight));
        float f2 = this.mCollapseOffset;
        if (max == f2) {
            return 0.0f;
        }
        float f3 = max - f2;
        float f4 = this.mDragRemainder + (f3 - ((int) f3));
        this.mDragRemainder = f4;
        if (f4 >= 1.0f) {
            this.mDragRemainder = f4 - 1.0f;
            f3 += 1.0f;
        } else if (f4 <= -1.0f) {
            this.mDragRemainder = f4 + 1.0f;
            f3 -= 1.0f;
        }
        View findIgnoreOffsetLimitView = findIgnoreOffsetLimitView();
        if (findIgnoreOffsetLimitView != null) {
            i = findIgnoreOffsetLimitView.getBottom() + ((LayoutParams) findIgnoreOffsetLimitView.getLayoutParams()).bottomMargin;
            z = true;
        } else {
            i = 0;
            z = false;
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (!layoutParams.ignoreOffset) {
                    childAt.offsetTopAndBottom((int) f3);
                } else if (z) {
                    int top = childAt.getTop();
                    int max2 = Math.max((int) (i + layoutParams.topMargin + f3), layoutParams.mFixedTop);
                    if (top != max2) {
                        childAt.offsetTopAndBottom(max2 - top);
                    }
                    i = childAt.getBottom() + layoutParams.bottomMargin;
                }
            }
        }
        boolean z2 = this.mCollapseOffset != 0.0f;
        this.mCollapseOffset = max;
        this.mTopOffset = (int) (this.mTopOffset + f3);
        ?? r3 = max == 0.0f ? 0 : 1;
        if (z2 != r3) {
            onCollapsedChanged(r3);
            getMetricsLogger().write(new LogMaker(MetricsProto.MetricsEvent.ACTION_SHARESHEET_COLLAPSED_CHANGED).setSubtype(r3));
        }
        onScrollChanged(0, (int) max, 0, (int) (max - f3));
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
        int abs;
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
        float distanceInfluenceForSnapDuration = f3 + (distanceInfluenceForSnapDuration(Math.min(1.0f, (Math.abs(i3) * 1.0f) / f2)) * f3);
        float abs2 = Math.abs(f);
        if (abs2 > 0.0f) {
            abs = Math.round(Math.abs(distanceInfluenceForSnapDuration / abs2) * 1000.0f) * 4;
        } else {
            abs = (int) (((Math.abs(i3) / f2) + 1.0f) * 100.0f);
        }
        this.mScroller.startScroll(0, i2, 0, i3, Math.min(abs, 300));
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
        View findChildUnder = findChildUnder(f, f2);
        while (findChildUnder != null) {
            f -= findChildUnder.getX();
            f2 -= findChildUnder.getY();
            if (findChildUnder instanceof AbsListView) {
                return findChildUnder((ViewGroup) findChildUnder, f, f2);
            }
            findChildUnder = findChildUnder instanceof ViewGroup ? findChildUnder((ViewGroup) findChildUnder, f, f2) : null;
        }
        return findChildUnder;
    }

    private boolean isListChildUnderClipped(float f, float f2) {
        View findListChildUnder = findListChildUnder(f, f2);
        return findListChildUnder != null && isDescendantClipped(findListChildUnder);
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
        for (int indexOfChild = indexOfChild(view) + 1; indexOfChild < childCount; indexOfChild++) {
            View childAt = getChildAt(indexOfChild);
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

    @Override // android.view.ViewGroup, android.view.ViewParent
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
        } else {
            if (isDismissable() && f2 < 0.0f) {
                float f3 = this.mCollapseOffset;
                int i = this.mCollapsibleHeight;
                if (f3 > i) {
                    smoothScrollTo(i + this.mUncollapsibleHeight, f2);
                    this.mDismissOnScrollerFinished = true;
                }
            }
            smoothScrollTo(f2 <= 0.0f ? this.mCollapsibleHeight : 0, f2);
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0016, code lost:
    
        if (r6 != 16908346) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean performAccessibilityActionCommon(int r6) {
        /*
            r5 = this;
            r0 = 4096(0x1000, float:5.74E-42)
            r1 = 0
            r2 = 1
            r3 = 0
            if (r6 == r0) goto L43
            r0 = 262144(0x40000, float:3.67342E-40)
            if (r6 == r0) goto L43
            r0 = 524288(0x80000, float:7.34684E-40)
            if (r6 == r0) goto L36
            r0 = 1048576(0x100000, float:1.469368E-39)
            if (r6 == r0) goto L19
            r0 = 16908346(0x102003a, float:2.3877392E-38)
            if (r6 == r0) goto L43
            goto L4d
        L19:
            float r6 = r5.mCollapseOffset
            int r0 = r5.mCollapsibleHeight
            int r4 = r5.mUncollapsibleHeight
            int r0 = r0 + r4
            float r0 = (float) r0
            int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r6 >= 0) goto L4d
            boolean r6 = r5.isDismissable()
            if (r6 == 0) goto L4d
            int r6 = r5.mCollapsibleHeight
            int r0 = r5.mUncollapsibleHeight
            int r6 = r6 + r0
            r5.smoothScrollTo(r6, r3)
            r5.mDismissOnScrollerFinished = r2
            return r2
        L36:
            float r6 = r5.mCollapseOffset
            int r0 = r5.mCollapsibleHeight
            float r4 = (float) r0
            int r6 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r6 >= 0) goto L4d
            r5.smoothScrollTo(r0, r3)
            return r2
        L43:
            float r6 = r5.mCollapseOffset
            int r6 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r6 == 0) goto L4d
            r5.smoothScrollTo(r1, r3)
            return r2
        L4d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.ResolverDrawerLayout.performAccessibilityActionCommon(int):boolean");
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
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i7 >= 0 ? Math.min(size, i7 + resolverDrawerLayout.getPaddingLeft() + resolverDrawerLayout.getPaddingRight()) : size, 1073741824);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size2, 1073741824);
        int childCount = resolverDrawerLayout.getChildCount();
        int i8 = 0;
        int i9 = 0;
        while (true) {
            i3 = Integer.MIN_VALUE;
            if (i8 >= childCount) {
                break;
            }
            int i10 = makeMeasureSpec;
            View childAt = resolverDrawerLayout.getChildAt(i8);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (!layoutParams.alwaysShow || childAt.getVisibility() == 8) {
                i9 = i9;
            } else {
                if (layoutParams.maxHeight != -1) {
                    int i11 = size2 - i9;
                    resolverDrawerLayout.measureChildWithMargins(childAt, i10, 0, View.MeasureSpec.makeMeasureSpec(layoutParams.maxHeight, Integer.MIN_VALUE), layoutParams.maxHeight > i11 ? layoutParams.maxHeight - i11 : 0);
                    resolverDrawerLayout = this;
                    i6 = i9;
                } else {
                    resolverDrawerLayout = this;
                    i6 = i9;
                    resolverDrawerLayout.measureChildWithMargins(childAt, i10, 0, makeMeasureSpec2, i6);
                }
                i9 = i6 + childAt.getMeasuredHeight();
            }
            i8++;
            makeMeasureSpec = i10;
        }
        int i12 = makeMeasureSpec;
        resolverDrawerLayout.mAlwaysShowHeight = i9;
        int i13 = 0;
        while (i13 < childCount) {
            View childAt2 = resolverDrawerLayout.getChildAt(i13);
            LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
            if (layoutParams2.alwaysShow || childAt2.getVisibility() == 8) {
                i4 = makeMeasureSpec2;
                i9 = i9;
            } else {
                if (layoutParams2.maxHeight != -1) {
                    int i14 = size2 - i9;
                    resolverDrawerLayout.measureChildWithMargins(childAt2, i12, 0, View.MeasureSpec.makeMeasureSpec(layoutParams2.maxHeight, i3), layoutParams2.maxHeight > i14 ? layoutParams2.maxHeight - i14 : 0);
                    resolverDrawerLayout = this;
                    i4 = makeMeasureSpec2;
                    i5 = i9;
                } else {
                    resolverDrawerLayout = this;
                    i4 = makeMeasureSpec2;
                    i5 = i9;
                    resolverDrawerLayout.measureChildWithMargins(childAt2, i12, 0, i4, i5);
                }
                i9 = i5 + childAt2.getMeasuredHeight();
            }
            i13++;
            makeMeasureSpec2 = i4;
            i3 = Integer.MIN_VALUE;
        }
        int i15 = i9;
        int i16 = resolverDrawerLayout.mCollapsibleHeight;
        if (resolverDrawerLayout.mDisableDrag) {
            resolverDrawerLayout.mCollapsibleHeight = 0;
        } else {
            resolverDrawerLayout.mCollapsibleHeight = Math.max(0, (i15 - resolverDrawerLayout.mAlwaysShowHeight) - resolverDrawerLayout.getMaxCollapsedHeight());
        }
        resolverDrawerLayout.mUncollapsibleHeight = i15 - resolverDrawerLayout.mCollapsibleHeight;
        resolverDrawerLayout.updateCollapseOffset(i16, !resolverDrawerLayout.isDragging());
        if (size2 < i15) {
            resolverDrawerLayout.mTopOffset = 0;
        } else if (resolverDrawerLayout.getShowAtTop()) {
            resolverDrawerLayout.mTopOffset = 0;
        } else {
            resolverDrawerLayout.mTopOffset = Math.max(0, size2 - i15) + ((int) resolverDrawerLayout.mCollapseOffset);
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
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int width = getWidth();
        int i5 = this.mTopOffset;
        int paddingLeft = getPaddingLeft();
        int paddingRight = (width - getPaddingRight()) - paddingLeft;
        int childCount = getChildCount();
        View view = null;
        boolean z2 = false;
        int i6 = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams.hasNestedScrollIndicator) {
                view = childAt;
            }
            if (childAt.getVisibility() != 8) {
                int i8 = this.mIgnoreOffsetTopLimitViewId;
                if (i8 != 0 && !z2 && i8 == childAt.getId()) {
                    i6 = layoutParams.bottomMargin + childAt.getBottom();
                    z2 = true;
                }
                int i9 = i5 + layoutParams.topMargin;
                if (layoutParams.ignoreOffset) {
                    if (!isDragging()) {
                        layoutParams.mFixedTop = (int) (i9 - this.mCollapseOffset);
                    }
                    if (z2) {
                        i9 = Math.max(i6 + layoutParams.topMargin, (int) (i9 - this.mCollapseOffset));
                        i6 = childAt.getMeasuredHeight() + i9 + layoutParams.bottomMargin;
                    } else {
                        i9 = (int) (i9 - this.mCollapseOffset);
                    }
                }
                int measuredHeight = childAt.getMeasuredHeight() + i9;
                int measuredWidth = childAt.getMeasuredWidth();
                int i10 = ((paddingRight - measuredWidth) / 2) + paddingLeft;
                childAt.layout(i10, i9, measuredWidth + i10, measuredHeight);
                i5 = measuredHeight + layoutParams.bottomMargin;
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
        View findViewById;
        int i = this.mIgnoreOffsetTopLimitViewId;
        if (i == 0 || (findViewById = findViewById(i)) == null || findViewById == this || findViewById.getParent() != this || findViewById.getVisibility() == 8) {
            return null;
        }
        return findViewById;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public boolean alwaysShow;
        public boolean hasNestedScrollIndicator;
        public boolean ignoreOffset;
        int mFixedTop;
        public int maxHeight;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ResolverDrawerLayout_LayoutParams);
            this.alwaysShow = obtainStyledAttributes.getBoolean(1, false);
            this.ignoreOffset = obtainStyledAttributes.getBoolean(3, false);
            this.hasNestedScrollIndicator = obtainStyledAttributes.getBoolean(2, false);
            this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(4, -1);
            obtainStyledAttributes.recycle();
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

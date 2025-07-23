package com.android.internal.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ListView;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class WatchListDecorLayout extends FrameLayout implements ViewTreeObserver.OnScrollChangedListener {
    private View mBottomPanel;
    private int mForegroundPaddingBottom;
    private int mForegroundPaddingLeft;
    private int mForegroundPaddingRight;
    private int mForegroundPaddingTop;
    private ListView mListView;
    private final ArrayList<View> mMatchParentChildren;
    private ViewTreeObserver mObserver;
    private int mPendingScroll;
    private View mTopPanel;

    public WatchListDecorLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mForegroundPaddingLeft = 0;
        this.mForegroundPaddingTop = 0;
        this.mForegroundPaddingRight = 0;
        this.mForegroundPaddingBottom = 0;
        this.mMatchParentChildren = new ArrayList<>(1);
    }

    public WatchListDecorLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mForegroundPaddingLeft = 0;
        this.mForegroundPaddingTop = 0;
        this.mForegroundPaddingRight = 0;
        this.mForegroundPaddingBottom = 0;
        this.mMatchParentChildren = new ArrayList<>(1);
    }

    public WatchListDecorLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mForegroundPaddingLeft = 0;
        this.mForegroundPaddingTop = 0;
        this.mForegroundPaddingRight = 0;
        this.mForegroundPaddingBottom = 0;
        this.mMatchParentChildren = new ArrayList<>(1);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mPendingScroll = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ListView) {
                if (this.mListView != null) {
                    throw new IllegalArgumentException("only one ListView child allowed");
                }
                ListView listView = (ListView) childAt;
                this.mListView = listView;
                listView.setNestedScrollingEnabled(true);
                ViewTreeObserver viewTreeObserver = this.mListView.getViewTreeObserver();
                this.mObserver = viewTreeObserver;
                viewTreeObserver.addOnScrollChangedListener(this);
            } else {
                int i2 = ((FrameLayout.LayoutParams) childAt.getLayoutParams()).gravity & 112;
                if (i2 == 48 && this.mTopPanel == null) {
                    this.mTopPanel = childAt;
                } else if (i2 == 80 && this.mBottomPanel == null) {
                    this.mBottomPanel = childAt;
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        this.mListView = null;
        this.mBottomPanel = null;
        this.mTopPanel = null;
        ViewTreeObserver viewTreeObserver = this.mObserver;
        if (viewTreeObserver != null) {
            if (viewTreeObserver.isAlive()) {
                this.mObserver.removeOnScrollChangedListener(this);
            }
            this.mObserver = null;
        }
    }

    private void applyMeasureToChild(View view, int i, int i2) {
        int childMeasureSpec;
        int childMeasureSpec2;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (marginLayoutParams.width == -1) {
            childMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, (((getMeasuredWidth() - getPaddingLeftWithForeground()) - getPaddingRightWithForeground()) - marginLayoutParams.leftMargin) - marginLayoutParams.rightMargin), 1073741824);
        } else {
            childMeasureSpec = getChildMeasureSpec(i, getPaddingLeftWithForeground() + getPaddingRightWithForeground() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width);
        }
        if (marginLayoutParams.height == -1) {
            childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.max(0, (((getMeasuredHeight() - getPaddingTopWithForeground()) - getPaddingBottomWithForeground()) - marginLayoutParams.topMargin) - marginLayoutParams.bottomMargin), 1073741824);
        } else {
            childMeasureSpec2 = getChildMeasureSpec(i2, getPaddingTopWithForeground() + getPaddingBottomWithForeground() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    private int measureAndGetHeight(View view, int i, int i2) {
        if (view == null) {
            return 0;
        }
        if (view.getVisibility() != 8) {
            applyMeasureToChild(this.mBottomPanel, i, i2);
            return view.getMeasuredHeight();
        }
        if (!getMeasureAllChildren()) {
            return 0;
        }
        applyMeasureToChild(this.mBottomPanel, i, i2);
        return 0;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        boolean z = (View.MeasureSpec.getMode(i) == 1073741824 && View.MeasureSpec.getMode(i2) == 1073741824) ? false : true;
        this.mMatchParentChildren.clear();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (getMeasureAllChildren() || childAt.getVisibility() != 8) {
                measureChildWithMargins(childAt, i, 0, i2, 0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                i3 = Math.max(i3, childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                i4 = Math.max(i4, childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                i5 = combineMeasuredStates(i5, childAt.getMeasuredState());
                if (z && (layoutParams.width == -1 || layoutParams.height == -1)) {
                    this.mMatchParentChildren.add(childAt);
                }
            }
        }
        int paddingLeftWithForeground = i3 + getPaddingLeftWithForeground() + getPaddingRightWithForeground();
        int max = Math.max(i4 + getPaddingTopWithForeground() + getPaddingBottomWithForeground(), getSuggestedMinimumHeight());
        int max2 = Math.max(paddingLeftWithForeground, getSuggestedMinimumWidth());
        Drawable foreground = getForeground();
        if (foreground != null) {
            max = Math.max(max, foreground.getMinimumHeight());
            max2 = Math.max(max2, foreground.getMinimumWidth());
        }
        setMeasuredDimension(resolveSizeAndState(max2, i, i5), resolveSizeAndState(max, i2, i5 << 16));
        ListView listView = this.mListView;
        if (listView != null) {
            int i7 = this.mPendingScroll;
            if (i7 != 0) {
                listView.scrollListBy(i7);
                this.mPendingScroll = 0;
            }
            int max3 = Math.max(this.mListView.getPaddingTop(), measureAndGetHeight(this.mTopPanel, i, i2));
            int max4 = Math.max(this.mListView.getPaddingBottom(), measureAndGetHeight(this.mBottomPanel, i, i2));
            if (max3 != this.mListView.getPaddingTop() || max4 != this.mListView.getPaddingBottom()) {
                this.mPendingScroll += this.mListView.getPaddingTop() - max3;
                ListView listView2 = this.mListView;
                listView2.setPadding(listView2.getPaddingLeft(), max3, this.mListView.getPaddingRight(), max4);
            }
        }
        int size = this.mMatchParentChildren.size();
        if (size > 1) {
            for (int i8 = 0; i8 < size; i8++) {
                View view = this.mMatchParentChildren.get(i8);
                if (this.mListView == null || (view != this.mTopPanel && view != this.mBottomPanel)) {
                    applyMeasureToChild(view, i, i2);
                }
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void setForegroundGravity(int i) {
        if (getForegroundGravity() != i) {
            super.setForegroundGravity(i);
            Drawable foreground = getForeground();
            if (getForegroundGravity() == 119 && foreground != null) {
                Rect rect = new Rect();
                if (foreground.getPadding(rect)) {
                    this.mForegroundPaddingLeft = rect.left;
                    this.mForegroundPaddingTop = rect.top;
                    this.mForegroundPaddingRight = rect.right;
                    this.mForegroundPaddingBottom = rect.bottom;
                    return;
                }
                return;
            }
            this.mForegroundPaddingLeft = 0;
            this.mForegroundPaddingTop = 0;
            this.mForegroundPaddingRight = 0;
            this.mForegroundPaddingBottom = 0;
        }
    }

    private int getPaddingLeftWithForeground() {
        return isForegroundInsidePadding() ? Math.max(this.mPaddingLeft, this.mForegroundPaddingLeft) : this.mPaddingLeft + this.mForegroundPaddingLeft;
    }

    private int getPaddingRightWithForeground() {
        return isForegroundInsidePadding() ? Math.max(this.mPaddingRight, this.mForegroundPaddingRight) : this.mPaddingRight + this.mForegroundPaddingRight;
    }

    private int getPaddingTopWithForeground() {
        return isForegroundInsidePadding() ? Math.max(this.mPaddingTop, this.mForegroundPaddingTop) : this.mPaddingTop + this.mForegroundPaddingTop;
    }

    private int getPaddingBottomWithForeground() {
        return isForegroundInsidePadding() ? Math.max(this.mPaddingBottom, this.mForegroundPaddingBottom) : this.mPaddingBottom + this.mForegroundPaddingBottom;
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public void onScrollChanged() {
        ListView listView = this.mListView;
        if (listView == null) {
            return;
        }
        if (this.mTopPanel != null) {
            if (listView.getChildCount() > 0) {
                if (this.mListView.getFirstVisiblePosition() == 0) {
                    setScrolling(this.mTopPanel, (this.mListView.getChildAt(0).getY() - this.mTopPanel.getHeight()) - this.mTopPanel.getTop());
                } else {
                    setScrolling(this.mTopPanel, -r0.getHeight());
                }
            } else {
                setScrolling(this.mTopPanel, 0.0f);
            }
        }
        if (this.mBottomPanel != null) {
            if (this.mListView.getChildCount() > 0) {
                if (this.mListView.getLastVisiblePosition() >= this.mListView.getCount() - 1) {
                    setScrolling(this.mBottomPanel, Math.max(0.0f, (this.mListView.getChildAt(r0.getChildCount() - 1).getY() + r0.getHeight()) - this.mBottomPanel.getTop()));
                    return;
                } else {
                    setScrolling(this.mBottomPanel, r0.getHeight());
                    return;
                }
            }
            setScrolling(this.mBottomPanel, 0.0f);
        }
    }

    private void setScrolling(View view, float f) {
        if (view.getTranslationY() != f) {
            view.setTranslationY(f);
        }
    }
}

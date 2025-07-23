package android.widget;

import android.app.slice.Slice;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.RemotableViewMethod;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RemoteViews;
import android.widget.SemHorizontalAbsListView;
import com.android.internal.R;
import com.google.android.collect.Lists;
import com.samsung.android.animation.SemAbsDragAndDropAnimator;
import com.samsung.android.animation.SemAddDeleteHorizontalListAnimator;
import com.samsung.android.animation.SemDragAndDropHorizontalListAnimator;
import com.samsung.android.widget.SemHorizontalHeaderViewListAdapter;
import java.util.ArrayList;
import java.util.function.Predicate;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class SemHorizontalListView extends SemHorizontalAbsListView {
    private static final int BITS_PER_LONG = 64;
    private static final float MAX_SCROLL_FACTOR = 0.33f;
    private static final int MIN_SCROLL_PREVIEW_PIXELS = 2;
    static final int NO_POSITION = -1;
    private static final String TAG = "SemHorizontalListView";
    private static final String XML_FIXED_SIZE_ITEMS_ATTRIBUTE = "fixed_size_items";
    private static final String XML_SEC_ANDROID_NAMESPACE = "http://schemas.android.samsung.com.samsung.android";
    private SemAddDeleteHorizontalListAnimator mAddDeleteListAnimator;
    private boolean mAreAllItemsSelectable;
    private final ArrowScrollFocusResult mArrowScrollFocusResult;
    Drawable mDivider;
    int mDividerHeight;
    private boolean mDividerIsOpaque;
    private Paint mDividerPaint;
    private SemDragAndDropHorizontalListAnimator mDndListAnimator;
    private final boolean mFixedSizeItems;
    private FocusSelector mFocusSelector;
    private boolean mFooterDividersEnabled;
    private ArrayList<FixedViewInfo> mFooterViewInfos;
    private boolean mHeaderDividersEnabled;
    private ArrayList<FixedViewInfo> mHeaderViewInfos;
    private boolean mIsCacheColorOpaque;
    boolean mIsFolderTypeFeature;
    private boolean mItemsCanFocus;
    Drawable mOverScrollFooter;
    Drawable mOverScrollHeader;
    private final Rect mTempRect;

    @ViewDebug.ExportedProperty(category = Slice.HINT_LIST)
    protected boolean recycleOnMeasure() {
        return true;
    }

    boolean shouldCorrectTooHigh() {
        return true;
    }

    @Deprecated
    public class FixedViewInfo {

        @Deprecated
        public Object data;

        @Deprecated
        public boolean isSelectable;

        @Deprecated
        public View view;

        public FixedViewInfo(SemHorizontalListView semHorizontalListView) {
        }
    }

    public void setAddDeleteListAnimator(SemAddDeleteHorizontalListAnimator semAddDeleteHorizontalListAnimator) {
        this.mAddDeleteListAnimator = semAddDeleteHorizontalListAnimator;
    }

    public void setDndListAnimator(SemDragAndDropHorizontalListAnimator semDragAndDropHorizontalListAnimator) {
        this.mDndListAnimator = semDragAndDropHorizontalListAnimator;
        setChildrenDrawingOrderEnabled(true);
        this.mDndListAnimator.setAutoScrollListener(new SemAbsDragAndDropAnimator.SemDragAutoScrollListener() { // from class: android.widget.SemHorizontalListView.1
            @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.SemDragAutoScrollListener
            public void onAutoScroll(int i) {
                SemHorizontalListView.this.trackMotionScroll(i, i);
            }
        });
    }

    @Deprecated
    public SemHorizontalListView(Context context) {
        this(context, null);
    }

    @Deprecated
    public SemHorizontalListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842868);
    }

    @Deprecated
    public SemHorizontalListView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    @Deprecated
    public SemHorizontalListView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mHeaderViewInfos = Lists.newArrayList();
        this.mFooterViewInfos = Lists.newArrayList();
        this.mAreAllItemsSelectable = true;
        this.mItemsCanFocus = false;
        this.mTempRect = new Rect();
        this.mIsFolderTypeFeature = false;
        this.mArrowScrollFocusResult = new ArrowScrollFocusResult();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListView, i, i2);
        CharSequence[] textArray = obtainStyledAttributes.getTextArray(0);
        if (textArray != null) {
            setAdapter((ListAdapter) new ArrayAdapter(context, 17367043, textArray));
        }
        Drawable drawable = obtainStyledAttributes.getDrawable(1);
        if (drawable != null) {
            setDivider(drawable);
        }
        Drawable drawable2 = obtainStyledAttributes.getDrawable(5);
        if (drawable2 != null) {
            setOverscrollHeader(drawable2);
        }
        Drawable drawable3 = obtainStyledAttributes.getDrawable(6);
        if (drawable3 != null) {
            setOverscrollFooter(drawable3);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        if (dimensionPixelSize != 0) {
            setDividerHeight(dimensionPixelSize);
        }
        this.mHeaderDividersEnabled = obtainStyledAttributes.getBoolean(3, true);
        this.mFooterDividersEnabled = obtainStyledAttributes.getBoolean(4, true);
        obtainStyledAttributes.recycle();
        if (attributeSet != null) {
            this.mFixedSizeItems = attributeSet.getAttributeBooleanValue(XML_SEC_ANDROID_NAMESPACE, XML_FIXED_SIZE_ITEMS_ATTRIBUTE, false);
        } else {
            this.mFixedSizeItems = false;
        }
    }

    @Deprecated
    public int getMaxScrollAmount() {
        return (int) ((this.mRight - this.mLeft) * MAX_SCROLL_FACTOR);
    }

    private void adjustViewsLeftOrRight() {
        int right;
        int width;
        int left;
        int i;
        int childCount = getChildCount();
        if (childCount > 0) {
            int i2 = 0;
            if (!this.mStackFromBottom) {
                View childAt = getChildAt(0);
                if (this.mIsRTL) {
                    left = childAt.getRight();
                    i = getWidth() - this.mListPadding.right;
                } else {
                    left = childAt.getLeft();
                    i = this.mListPadding.left;
                }
                int i3 = left - i;
                if (this.mFirstPosition != 0) {
                    if (this.mIsRTL) {
                        i3 += this.mDividerHeight;
                    } else {
                        i3 -= this.mDividerHeight;
                    }
                }
                if (!this.mIsRTL ? i3 >= 0 : i3 <= 0) {
                    i2 = i3;
                }
            } else {
                View childAt2 = getChildAt(childCount - 1);
                if (this.mIsRTL) {
                    right = childAt2.getLeft();
                    width = this.mListPadding.left;
                } else {
                    right = childAt2.getRight();
                    width = getWidth() - this.mListPadding.right;
                }
                int i4 = right - width;
                if (this.mFirstPosition + childCount < this.mItemCount) {
                    if (this.mIsRTL) {
                        i4 -= this.mDividerHeight;
                    } else {
                        i4 += this.mDividerHeight;
                    }
                }
                if (!this.mIsRTL ? i4 <= 0 : i4 >= 0) {
                    i2 = i4;
                }
            }
            if (i2 != 0) {
                semOffsetChildrenLeftAndRight(-i2);
            }
        }
    }

    @Deprecated
    public void addHeaderView(View view, Object obj, boolean z) {
        FixedViewInfo fixedViewInfo = new FixedViewInfo(this);
        fixedViewInfo.view = view;
        fixedViewInfo.data = obj;
        fixedViewInfo.isSelectable = z;
        this.mHeaderViewInfos.add(fixedViewInfo);
        this.mAreAllItemsSelectable &= z;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof SemHorizontalHeaderViewListAdapter)) {
                this.mAdapter = new SemHorizontalHeaderViewListAdapter(this.mHeaderViewInfos, this.mFooterViewInfos, this.mAdapter);
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    @Deprecated
    public void addHeaderView(View view) {
        addHeaderView(view, null, true);
    }

    @Override // android.widget.SemHorizontalAbsListView
    @Deprecated
    public int getHeaderViewsCount() {
        return this.mHeaderViewInfos.size();
    }

    @Deprecated
    public boolean removeHeaderView(View view) {
        boolean z = false;
        if (this.mHeaderViewInfos.size() > 0) {
            if (this.mAdapter != null && ((SemHorizontalHeaderViewListAdapter) this.mAdapter).removeHeader(view)) {
                if (this.mDataSetObserver != null) {
                    this.mDataSetObserver.onChanged();
                }
                z = true;
            }
            removeFixedViewInfo(view, this.mHeaderViewInfos);
        }
        return z;
    }

    private void removeFixedViewInfo(View view, ArrayList<FixedViewInfo> arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (arrayList.get(i).view == view) {
                arrayList.remove(i);
                return;
            }
        }
    }

    @Deprecated
    public void addFooterView(View view, Object obj, boolean z) {
        FixedViewInfo fixedViewInfo = new FixedViewInfo(this);
        fixedViewInfo.view = view;
        fixedViewInfo.data = obj;
        fixedViewInfo.isSelectable = z;
        this.mFooterViewInfos.add(fixedViewInfo);
        this.mAreAllItemsSelectable &= z;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof SemHorizontalHeaderViewListAdapter)) {
                this.mAdapter = new SemHorizontalHeaderViewListAdapter(this.mHeaderViewInfos, this.mFooterViewInfos, this.mAdapter);
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    @Deprecated
    public void addFooterView(View view) {
        addFooterView(view, null, true);
    }

    @Override // android.widget.SemHorizontalAbsListView
    @Deprecated
    public int getFooterViewsCount() {
        return this.mFooterViewInfos.size();
    }

    @Deprecated
    public boolean removeFooterView(View view) {
        boolean z = false;
        if (this.mFooterViewInfos.size() > 0) {
            if (this.mAdapter != null && ((SemHorizontalHeaderViewListAdapter) this.mAdapter).removeFooter(view)) {
                if (this.mDataSetObserver != null) {
                    this.mDataSetObserver.onChanged();
                }
                z = true;
            }
            removeFixedViewInfo(view, this.mFooterViewInfos);
        }
        return z;
    }

    @Override // android.widget.AdapterView
    @Deprecated
    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.widget.SemHorizontalAbsListView
    @RemotableViewMethod(asyncImpl = "setRemoteViewsAdapterAsync")
    @Deprecated
    public void setRemoteViewsAdapter(Intent intent) {
        super.setRemoteViewsAdapter(intent);
    }

    @Override // android.widget.AdapterView
    @Deprecated
    public void setAdapter(ListAdapter listAdapter) {
        int lookForSelectablePosition;
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        resetList();
        this.mRecycler.clear();
        if (this.mHeaderViewInfos.size() > 0 || this.mFooterViewInfos.size() > 0) {
            this.mAdapter = new SemHorizontalHeaderViewListAdapter(this.mHeaderViewInfos, this.mFooterViewInfos, listAdapter);
        } else {
            this.mAdapter = listAdapter;
        }
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        if (this.mAdapter != null) {
            this.mAreAllItemsSelectable = this.mAdapter.areAllItemsEnabled();
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            checkFocus();
            this.mDataSetObserver = new SemHorizontalAbsListView.AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mRecycler.setViewTypeCount(this.mAdapter.getViewTypeCount());
            if (this.mStackFromBottom) {
                if (this.mIsRTL) {
                    lookForSelectablePosition = lookForSelectablePosition(this.mItemCount - 1, true);
                } else {
                    lookForSelectablePosition = lookForSelectablePosition(this.mItemCount - 1, false);
                }
            } else if (this.mIsRTL) {
                lookForSelectablePosition = lookForSelectablePosition(0, false);
            } else {
                lookForSelectablePosition = lookForSelectablePosition(0, true);
            }
            setSelectedPositionInt(lookForSelectablePosition);
            setNextSelectedPositionInt(lookForSelectablePosition);
            if (this.mItemCount == 0) {
                checkSelectionChanged();
            }
        } else {
            this.mAreAllItemsSelectable = true;
            checkFocus();
            checkSelectionChanged();
        }
        requestLayout();
    }

    @Override // android.widget.SemHorizontalAbsListView
    void resetList() {
        clearRecycledState(this.mHeaderViewInfos);
        clearRecycledState(this.mFooterViewInfos);
        super.resetList();
        this.mLayoutMode = 0;
    }

    private void clearRecycledState(ArrayList<FixedViewInfo> arrayList) {
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                SemHorizontalAbsListView.LayoutParams layoutParams = (SemHorizontalAbsListView.LayoutParams) arrayList.get(i).view.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.recycledHeaderFooter = false;
                }
            }
        }
    }

    private boolean showingLeftFadingEdge() {
        int i = this.mScrollX + this.mListPadding.left;
        int childCount = getChildCount();
        return this.mIsRTL ? (this.mFirstPosition + childCount) - 1 < this.mItemCount - 1 || getChildAt(childCount + (-1)).getLeft() < i : this.mFirstPosition > 0 || getChildAt(0).getLeft() > i;
    }

    private boolean showingRightFadingEdge() {
        int childCount = getChildCount();
        int right = getChildAt(childCount - 1).getRight();
        int i = (this.mFirstPosition + childCount) - 1;
        int width = (this.mScrollX + getWidth()) - this.mListPadding.right;
        return this.mIsRTL ? this.mFirstPosition > 0 || getChildAt(0).getRight() < width : i < this.mItemCount - 1 || right < width;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    @Deprecated
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        int i;
        int i2;
        int i3;
        int i4 = rect.left;
        rect.offset(view.getLeft(), view.getTop());
        rect.offset(-view.getScrollX(), -view.getScrollY());
        int width = getWidth();
        int scrollX = getScrollX();
        int i5 = scrollX + width;
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (showingLeftFadingEdge() && (this.mSelectedPosition > 0 || i4 > horizontalFadingEdgeLength)) {
            scrollX += horizontalFadingEdgeLength;
        }
        int right = getChildAt(getChildCount() - 1).getRight();
        if (showingRightFadingEdge() && (this.mSelectedPosition < this.mItemCount - 1 || rect.right < right - horizontalFadingEdgeLength)) {
            i5 -= horizontalFadingEdgeLength;
        }
        if (rect.right > i5 && rect.left > scrollX) {
            if (rect.width() > width) {
                i3 = rect.left - scrollX;
            } else {
                i3 = rect.right - i5;
            }
            i = Math.min(i3, right - i5);
        } else if (rect.left >= scrollX || rect.right >= i5) {
            i = 0;
        } else {
            if (rect.width() > width) {
                i2 = 0 - (i5 - rect.right);
            } else {
                i2 = 0 - (scrollX - rect.left);
            }
            i = Math.max(i2, getChildAt(0).getLeft() - scrollX);
        }
        boolean z2 = i != 0;
        if (z2) {
            scrollListItemsBy(-i);
            positionSelector(-1, view);
            this.mSelectedLeft = view.getLeft();
            invalidate();
        }
        return z2;
    }

    @Override // android.widget.SemHorizontalAbsListView
    void fillGap(boolean z) {
        int width;
        int childCount = getChildCount();
        if (z) {
            int listPaddingLeft = (this.mGroupFlags & 34) == 34 ? getListPaddingLeft() : 0;
            if (childCount > 0) {
                listPaddingLeft = this.mDividerHeight + getChildAt(childCount - 1).getRight();
            }
            fillRight(this.mFirstPosition + childCount, listPaddingLeft);
            correctTooHigh(getChildCount());
            return;
        }
        int listPaddingRight = (this.mGroupFlags & 34) == 34 ? getListPaddingRight() : 0;
        if (childCount > 0) {
            width = getChildAt(0).getLeft() - this.mDividerHeight;
        } else {
            width = getWidth() - listPaddingRight;
        }
        fillLeft(this.mFirstPosition - 1, width);
        correctTooLow(getChildCount());
    }

    @Override // android.widget.SemHorizontalAbsListView
    void fillGapRTL(boolean z) {
        int width;
        int childCount = getChildCount();
        if (z) {
            int listPaddingLeft = (this.mGroupFlags & 34) == 34 ? getListPaddingLeft() : 0;
            if (childCount > 0) {
                listPaddingLeft = getChildAt(0).getRight() + this.mDividerHeight;
            }
            fillRightRTL(this.mFirstPosition - 1, listPaddingLeft);
            correctTooLowRTL(getChildCount());
            return;
        }
        int listPaddingRight = (this.mGroupFlags & 34) == 34 ? getListPaddingRight() : 0;
        if (childCount > 0) {
            width = getChildAt(childCount - 1).getLeft();
            listPaddingRight = this.mDividerHeight;
        } else {
            width = getWidth();
        }
        fillLeftRTL(this.mFirstPosition + childCount, width - listPaddingRight);
        correctTooHighRTL(getChildCount());
    }

    private View fillRight(int i, int i2) {
        int i3 = this.mRight - this.mLeft;
        View view = null;
        if ((this.mGroupFlags & 34) == 34) {
            i3 -= this.mListPadding.right;
        }
        int i4 = i;
        int i5 = i2;
        while (true) {
            if (i5 >= i3 || i4 >= this.mItemCount) {
                break;
            }
            boolean z = i4 == this.mSelectedPosition;
            SemHorizontalListView semHorizontalListView = this;
            View makeAndAddView = semHorizontalListView.makeAndAddView(i4, i5, true, this.mListPadding.top, z);
            if (makeAndAddView != null) {
                int right = makeAndAddView.getRight() + semHorizontalListView.mDividerHeight;
                if (z) {
                    view = makeAndAddView;
                }
                i5 = right;
            }
            i4++;
            this = semHorizontalListView;
        }
        SemHorizontalListView semHorizontalListView2 = this;
        semHorizontalListView2.setVisibleRangeHint(semHorizontalListView2.mFirstPosition, (semHorizontalListView2.mFirstPosition + semHorizontalListView2.getChildCount()) - 1);
        return view;
    }

    private View fillRightRTL(int i, int i2) {
        int i3 = this.mRight - this.mLeft;
        if ((this.mGroupFlags & 34) == 34) {
            i3 -= this.mListPadding.right;
        }
        View view = null;
        int i4 = i;
        int i5 = i2;
        while (true) {
            if (i5 >= i3 || i4 < 0) {
                break;
            }
            boolean z = i4 == this.mSelectedPosition;
            SemHorizontalListView semHorizontalListView = this;
            View makeAndAddView = semHorizontalListView.makeAndAddView(i4, i5, true, this.mListPadding.top, z);
            if (makeAndAddView != null) {
                int right = makeAndAddView.getRight() + semHorizontalListView.mDividerHeight;
                if (z) {
                    view = makeAndAddView;
                }
                i5 = right;
            }
            i4--;
            this = semHorizontalListView;
        }
        SemHorizontalListView semHorizontalListView2 = this;
        semHorizontalListView2.mFirstPosition = i4 + 1;
        semHorizontalListView2.setVisibleRangeHint(semHorizontalListView2.mFirstPosition, (semHorizontalListView2.mFirstPosition + semHorizontalListView2.getChildCount()) - 1);
        return view;
    }

    private View fillLeft(int i, int i2) {
        int i3;
        int i4;
        int i5;
        View view = null;
        if ((this.mGroupFlags & 34) == 34) {
            i5 = this.mListPadding.left;
            i3 = i;
            i4 = i2;
        } else {
            i3 = i;
            i4 = i2;
            i5 = 0;
        }
        while (i4 > i5 && i3 >= 0) {
            boolean z = i3 == this.mSelectedPosition;
            SemHorizontalListView semHorizontalListView = this;
            View makeAndAddView = semHorizontalListView.makeAndAddView(i3, i4, false, this.mListPadding.top, z);
            if (makeAndAddView != null) {
                int left = makeAndAddView.getLeft() - semHorizontalListView.mDividerHeight;
                if (z) {
                    view = makeAndAddView;
                }
                i4 = left;
            }
            i3--;
            this = semHorizontalListView;
        }
        SemHorizontalListView semHorizontalListView2 = this;
        semHorizontalListView2.mFirstPosition = i3 + 1;
        semHorizontalListView2.setVisibleRangeHint(semHorizontalListView2.mFirstPosition, (semHorizontalListView2.mFirstPosition + semHorizontalListView2.getChildCount()) - 1);
        return view;
    }

    private View fillLeftRTL(int i, int i2) {
        int i3;
        int i4;
        int i5;
        View view = null;
        if ((this.mGroupFlags & 34) == 34) {
            i5 = this.mListPadding.left;
            i3 = i;
            i4 = i2;
        } else {
            i3 = i;
            i4 = i2;
            i5 = 0;
        }
        while (i4 > i5 && i3 < this.mItemCount) {
            boolean z = i3 == this.mSelectedPosition;
            SemHorizontalListView semHorizontalListView = this;
            View makeAndAddView = semHorizontalListView.makeAndAddView(i3, i4, false, this.mListPadding.top, z);
            if (makeAndAddView != null) {
                int left = makeAndAddView.getLeft() - semHorizontalListView.mDividerHeight;
                if (z) {
                    view = makeAndAddView;
                }
                i4 = left;
            }
            i3++;
            this = semHorizontalListView;
        }
        SemHorizontalListView semHorizontalListView2 = this;
        semHorizontalListView2.setVisibleRangeHint(semHorizontalListView2.mFirstPosition, (semHorizontalListView2.mFirstPosition + semHorizontalListView2.getChildCount()) - 1);
        return view;
    }

    private View fillFromLeft(int i) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return fillRight(this.mFirstPosition, i);
    }

    private View fillFromRight(int i) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return fillLeftRTL(this.mFirstPosition, i);
    }

    private View fillFromMiddle(int i, int i2) {
        int i3 = i2 - i;
        int reconcileSelectedPosition = reconcileSelectedPosition();
        View makeAndAddView = makeAndAddView(reconcileSelectedPosition, i, true, this.mListPadding.top, true);
        this.mFirstPosition = reconcileSelectedPosition;
        int measuredWidth = makeAndAddView.getMeasuredWidth();
        if (measuredWidth <= i3) {
            makeAndAddView.offsetLeftAndRight((i3 - measuredWidth) / 2);
        }
        fillLeftAndRight(makeAndAddView, reconcileSelectedPosition);
        if (!this.mStackFromBottom) {
            if (this.mIsRTL) {
                correctTooHighRTL(getChildCount());
                return makeAndAddView;
            }
            correctTooHigh(getChildCount());
            return makeAndAddView;
        }
        if (this.mIsRTL) {
            correctTooLowRTL(getChildCount());
            return makeAndAddView;
        }
        correctTooLow(getChildCount());
        return makeAndAddView;
    }

    private void fillLeftAndRight(View view, int i) {
        int i2 = this.mDividerHeight;
        if (this.mIsRTL) {
            if (!this.mStackFromBottom) {
                fillRightRTL(i - 1, view.getRight() + i2);
                adjustViewsLeftOrRight();
                fillLeftRTL(i + 1, view.getLeft() - i2);
                return;
            } else {
                fillLeftRTL(i + 1, view.getLeft() - i2);
                adjustViewsLeftOrRight();
                fillRightRTL(i - 1, view.getRight() + i2);
                return;
            }
        }
        if (!this.mStackFromBottom) {
            fillLeft(i - 1, view.getLeft() - i2);
            adjustViewsLeftOrRight();
            fillRight(i + 1, view.getRight() + i2);
        } else {
            fillRight(i + 1, view.getRight() + i2);
            adjustViewsLeftOrRight();
            fillLeft(i - 1, view.getLeft() - i2);
        }
    }

    private View fillFromSelection(int i, int i2, int i3) {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        int i4 = this.mSelectedPosition;
        int leftSelectionPixel = getLeftSelectionPixel(i2, horizontalFadingEdgeLength, i4);
        int rightSelectionPixel = getRightSelectionPixel(i3, horizontalFadingEdgeLength, i4);
        View makeAndAddView = makeAndAddView(i4, i, true, this.mListPadding.top, true);
        if (makeAndAddView.getRight() > rightSelectionPixel) {
            makeAndAddView.offsetLeftAndRight(-Math.min(makeAndAddView.getLeft() - leftSelectionPixel, makeAndAddView.getRight() - rightSelectionPixel));
        } else if (makeAndAddView.getLeft() < leftSelectionPixel) {
            makeAndAddView.offsetLeftAndRight(Math.min(leftSelectionPixel - makeAndAddView.getLeft(), rightSelectionPixel - makeAndAddView.getRight()));
        }
        fillLeftAndRight(makeAndAddView, i4);
        if (!this.mStackFromBottom) {
            if (this.mIsRTL) {
                correctTooLowRTL(getChildCount());
                return makeAndAddView;
            }
            correctTooHigh(getChildCount());
            return makeAndAddView;
        }
        if (this.mIsRTL) {
            correctTooHighRTL(getChildCount());
            return makeAndAddView;
        }
        correctTooLow(getChildCount());
        return makeAndAddView;
    }

    private int getRightSelectionPixel(int i, int i2, int i3) {
        if (this.mIsRTL) {
            if (i3 <= 0) {
                return i;
            }
        } else if (i3 == this.mItemCount - 1) {
            return i;
        }
        return i - i2;
    }

    private int getLeftSelectionPixel(int i, int i2, int i3) {
        if (this.mIsRTL) {
            if (i3 == this.mItemCount - 1) {
                return i;
            }
        } else if (i3 <= 0) {
            return i;
        }
        return i + i2;
    }

    @Override // android.widget.SemHorizontalAbsListView
    @RemotableViewMethod
    @Deprecated
    public void smoothScrollToPosition(int i) {
        super.smoothScrollToPosition(i);
    }

    @Override // android.widget.SemHorizontalAbsListView
    @RemotableViewMethod
    @Deprecated
    public void smoothScrollByOffset(int i) {
        super.smoothScrollByOffset(i);
    }

    private View moveSelection(View view, View view2, int i, int i2, int i3) {
        View makeAndAddView;
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        int i4 = this.mSelectedPosition;
        int leftSelectionPixel = getLeftSelectionPixel(i2, horizontalFadingEdgeLength, i4);
        int rightSelectionPixel = getRightSelectionPixel(i2, horizontalFadingEdgeLength, i4);
        if (i > 0) {
            View makeAndAddView2 = makeAndAddView(i4 - 1, view.getLeft(), true, this.mListPadding.top, false);
            int i5 = this.mDividerHeight;
            View makeAndAddView3 = makeAndAddView(i4, makeAndAddView2.getRight() + i5, true, this.mListPadding.top, true);
            if (makeAndAddView3.getRight() > rightSelectionPixel) {
                int i6 = -Math.min(Math.min(makeAndAddView3.getLeft() - leftSelectionPixel, makeAndAddView3.getRight() - rightSelectionPixel), (i3 - i2) / 2);
                makeAndAddView2.offsetLeftAndRight(i6);
                makeAndAddView3.offsetLeftAndRight(i6);
            }
            if (!this.mStackFromBottom) {
                fillLeft(this.mSelectedPosition - 2, makeAndAddView3.getLeft() - i5);
                adjustViewsLeftOrRight();
                fillRight(this.mSelectedPosition + 1, makeAndAddView3.getRight() + i5);
                return makeAndAddView3;
            }
            fillRight(this.mSelectedPosition + 1, makeAndAddView3.getRight() + i5);
            adjustViewsLeftOrRight();
            fillLeft(this.mSelectedPosition - 2, makeAndAddView3.getLeft() - i5);
            return makeAndAddView3;
        }
        if (i < 0) {
            if (view2 != null) {
                makeAndAddView = makeAndAddView(i4, view2.getLeft(), true, this.mListPadding.top, true);
            } else {
                makeAndAddView = makeAndAddView(i4, view.getLeft(), false, this.mListPadding.top, true);
            }
            if (makeAndAddView.getLeft() < leftSelectionPixel) {
                makeAndAddView.offsetLeftAndRight(Math.min(Math.min(leftSelectionPixel - makeAndAddView.getLeft(), rightSelectionPixel - makeAndAddView.getRight()), (i3 - i2) / 2));
            }
            fillLeftAndRight(makeAndAddView, i4);
            return makeAndAddView;
        }
        int left = view.getLeft();
        View makeAndAddView4 = makeAndAddView(i4, left, true, this.mListPadding.top, true);
        if (left < i2 && makeAndAddView4.getRight() < i2 + 20) {
            makeAndAddView4.offsetLeftAndRight(i2 - makeAndAddView4.getLeft());
        }
        fillLeftAndRight(makeAndAddView4, i4);
        return makeAndAddView4;
    }

    private class FocusSelector implements Runnable {
        private int mPosition;
        private int mPositionLeft;

        private FocusSelector() {
        }

        public FocusSelector setup(int i, int i2) {
            this.mPosition = i;
            this.mPositionLeft = i2;
            return this;
        }

        @Override // java.lang.Runnable
        public void run() {
            SemHorizontalListView.this.setSelectionFromStart(this.mPosition, this.mPositionLeft);
        }
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        View focusedChild;
        if (getChildCount() > 0 && (focusedChild = getFocusedChild()) != null) {
            int indexOfChild = this.mFirstPosition + indexOfChild(focusedChild);
            int right = this.mIsRTL ? focusedChild.getRight() : focusedChild.getLeft() - Math.max(0, focusedChild.getRight() - (i - this.mPaddingLeft));
            if (this.mFocusSelector == null) {
                this.mFocusSelector = new FocusSelector();
            }
            post(this.mFocusSelector.setup(indexOfChild, right));
        }
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        SemHorizontalListView semHorizontalListView;
        int i5;
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int i6 = 0;
        this.mItemCount = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        if (this.mItemCount <= 0 || !(mode == 0 || mode2 == 0)) {
            i3 = 0;
            i4 = 0;
        } else {
            View obtainView = obtainView(0, this.mIsScrap);
            measureScrapChild(obtainView, 0, i2);
            i3 = obtainView.getMeasuredWidth();
            i4 = obtainView.getMeasuredHeight();
            i6 = combineMeasuredStates(0, obtainView.getMeasuredState());
            if (recycleOnMeasure() && this.mRecycler.shouldRecycleViewType(((SemHorizontalAbsListView.LayoutParams) obtainView.getLayoutParams()).viewType)) {
                this.mRecycler.addScrapView(obtainView, -1);
            }
        }
        int horizontalScrollbarHeight = mode2 == 0 ? this.mListPadding.top + this.mListPadding.bottom + i4 + getHorizontalScrollbarHeight() : ((-16777216) & i6) | size2;
        if (mode == 0) {
            size = this.mListPadding.left + this.mListPadding.right + i3 + (getHorizontalFadingEdgeLength() * 2);
        }
        int i7 = size;
        if (mode == Integer.MIN_VALUE) {
            semHorizontalListView = this;
            i5 = i2;
            i7 = semHorizontalListView.measureWidthOfChildren(i5, 0, -1, i7, -1);
        } else {
            semHorizontalListView = this;
            i5 = i2;
        }
        semHorizontalListView.setMeasuredDimension(i7, horizontalScrollbarHeight);
        semHorizontalListView.mHeightMeasureSpec = i5;
    }

    private void measureScrapChild(View view, int i, int i2) {
        int makeMeasureSpec;
        SemHorizontalAbsListView.LayoutParams layoutParams = (SemHorizontalAbsListView.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = (SemHorizontalAbsListView.LayoutParams) generateDefaultLayoutParams();
            view.setLayoutParams(layoutParams);
        }
        layoutParams.viewType = this.mAdapter.getItemViewType(i);
        layoutParams.forceAdd = true;
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, this.mListPadding.top + this.mListPadding.bottom, layoutParams.height);
        int i3 = layoutParams.width;
        if (i3 > 0) {
            makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
        } else {
            makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        }
        view.measure(makeMeasureSpec, childMeasureSpec);
    }

    final int measureWidthOfChildren(int i, int i2, int i3, int i4, int i5) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null) {
            return this.mListPadding.left + this.mListPadding.right;
        }
        int i6 = this.mListPadding.left + this.mListPadding.right;
        int i7 = this.mDividerHeight;
        int i8 = 0;
        if (i7 <= 0 || this.mDivider == null) {
            i7 = 0;
        }
        if (i3 == -1) {
            i3 = listAdapter.getCount() - 1;
        }
        SemHorizontalAbsListView.RecycleBin recycleBin = this.mRecycler;
        boolean recycleOnMeasure = recycleOnMeasure();
        boolean[] zArr = this.mIsScrap;
        while (i2 <= i3) {
            View obtainView = obtainView(i2, zArr);
            measureScrapChild(obtainView, i2, i);
            if (i2 > 0) {
                i6 = this.mIsRTL ? i6 - i7 : i6 + i7;
            }
            if (recycleOnMeasure && recycleBin.shouldRecycleViewType(((SemHorizontalAbsListView.LayoutParams) obtainView.getLayoutParams()).viewType)) {
                recycleBin.addScrapView(obtainView, -1);
            }
            i6 += obtainView.getMeasuredWidth();
            if (i6 >= i4) {
                return (i5 < 0 || i2 <= i5 || i8 <= 0 || i6 == i4) ? i4 : i8;
            }
            if (i5 >= 0 && i2 >= i5) {
                i8 = i6;
            }
            i2++;
        }
        return i6;
    }

    @Override // android.widget.SemHorizontalAbsListView
    int findMotionRow(int i) {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return -1;
        }
        int i2 = 0;
        if (this.mIsRTL) {
            if (!this.mStackFromBottom) {
                while (i2 < childCount) {
                    if (i >= getChildAt(i2).getLeft()) {
                        return this.mFirstPosition + i2;
                    }
                    i2++;
                }
                return -1;
            }
            for (int i3 = childCount - 1; i3 >= 0; i3--) {
                if (i <= getChildAt(i3).getRight()) {
                    return this.mFirstPosition + i3;
                }
            }
            return -1;
        }
        if (!this.mStackFromBottom) {
            while (i2 < childCount) {
                if (i <= getChildAt(i2).getRight()) {
                    return this.mFirstPosition + i2;
                }
                i2++;
            }
            return -1;
        }
        for (int i4 = childCount - 1; i4 >= 0; i4--) {
            if (i >= getChildAt(i4).getLeft()) {
                return this.mFirstPosition + i4;
            }
        }
        return -1;
    }

    private View fillSpecific(int i, int i2) {
        View view;
        View view2;
        boolean z = i == this.mSelectedPosition;
        View makeAndAddView = makeAndAddView(i, i2, true, this.mListPadding.top, z);
        this.mFirstPosition = i;
        int i3 = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            view = fillLeft(i - 1, makeAndAddView.getLeft() - i3);
            adjustViewsLeftOrRight();
            view2 = fillRight(i + 1, makeAndAddView.getRight() + i3);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHigh(childCount);
            }
        } else {
            View fillRight = fillRight(i + 1, makeAndAddView.getRight() + i3);
            adjustViewsLeftOrRight();
            View fillLeft = fillLeft(i - 1, makeAndAddView.getLeft() - i3);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLow(childCount2);
            }
            view = fillLeft;
            view2 = fillRight;
        }
        return z ? makeAndAddView : view != null ? view : view2;
    }

    private View fillSpecificRTL(int i, int i2) {
        View view;
        View view2;
        boolean z = i == this.mSelectedPosition;
        View makeAndAddView = makeAndAddView(i, i2, false, this.mListPadding.top, z);
        this.mFirstPosition = i;
        int i3 = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            view = fillRightRTL(i - 1, makeAndAddView.getRight() + i3);
            adjustViewsLeftOrRight();
            view2 = fillLeftRTL(i + 1, makeAndAddView.getLeft() - i3);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHighRTL(childCount);
            }
        } else {
            View fillLeftRTL = fillLeftRTL(i + 1, makeAndAddView.getLeft() - i3);
            adjustViewsLeftOrRight();
            View fillRightRTL = fillRightRTL(i - 1, makeAndAddView.getRight() + i3);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLowRTL(childCount2);
            }
            view = fillRightRTL;
            view2 = fillLeftRTL;
        }
        return z ? makeAndAddView : view2 != null ? view2 : view;
    }

    private void correctTooHigh(int i) {
        if ((this.mFirstPosition + i) - 1 != this.mItemCount - 1 || i <= 0) {
            return;
        }
        int right = ((this.mRight - this.mLeft) - this.mListPadding.right) - getChildAt(i - 1).getRight();
        View childAt = getChildAt(0);
        int left = childAt.getLeft();
        if (right > 0) {
            if (this.mFirstPosition > 0 || left < this.mListPadding.left) {
                if (this.mFirstPosition == 0) {
                    right = Math.min(right, this.mListPadding.left - left);
                }
                semOffsetChildrenLeftAndRight(right);
                if (this.mFirstPosition > 0) {
                    fillLeft(this.mFirstPosition - 1, childAt.getLeft() - this.mDividerHeight);
                    adjustViewsLeftOrRight();
                }
            }
        }
    }

    private void correctTooHighRTL(int i) {
        if ((this.mFirstPosition + i) - 1 != this.mItemCount - 1 || i <= 0) {
            return;
        }
        int left = getChildAt(i - 1).getLeft();
        int i2 = this.mListPadding.left;
        int i3 = (this.mRight - this.mLeft) - this.mListPadding.right;
        int i4 = left - i2;
        View childAt = getChildAt(0);
        int right = childAt.getRight();
        if (i4 > 0) {
            if (this.mFirstPosition > 0 || right > i3) {
                if (this.mFirstPosition == 0) {
                    i4 = Math.min(i4, right - i3);
                }
                semOffsetChildrenLeftAndRight(-i4);
                if (this.mFirstPosition > 0) {
                    fillRightRTL(this.mFirstPosition - 1, childAt.getRight() + this.mDividerHeight);
                    adjustViewsLeftOrRight();
                }
            }
        }
    }

    private void correctTooLow(int i) {
        if (this.mFirstPosition != 0 || i <= 0) {
            return;
        }
        int left = getChildAt(0).getLeft();
        int i2 = this.mListPadding.left;
        int i3 = (this.mRight - this.mLeft) - this.mListPadding.right;
        int i4 = left - i2;
        View childAt = getChildAt(i - 1);
        int right = childAt.getRight();
        int i5 = this.mFirstPosition + i;
        int i6 = i5 - 1;
        if (i4 > 0) {
            if (i6 < this.mItemCount - 1 || right > i3) {
                if (i6 == this.mItemCount - 1) {
                    i4 = Math.min(i4, right - i3);
                }
                semOffsetChildrenLeftAndRight(-i4);
                if (i6 < this.mItemCount - 1) {
                    fillRight(i5, childAt.getRight() + this.mDividerHeight);
                    adjustViewsLeftOrRight();
                    return;
                }
                return;
            }
            if (i6 == this.mItemCount - 1) {
                adjustViewsLeftOrRight();
            }
        }
    }

    private void correctTooLowRTL(int i) {
        if (this.mFirstPosition != 0 || i <= 0) {
            return;
        }
        int right = ((this.mRight - this.mLeft) - this.mListPadding.right) - getChildAt(0).getRight();
        View childAt = getChildAt(i - 1);
        int left = childAt.getLeft();
        int i2 = this.mFirstPosition + i;
        int i3 = i2 - 1;
        if (right > 0) {
            if (i3 < this.mItemCount - 1 || left < this.mListPadding.left) {
                if (i3 == this.mItemCount - 1) {
                    right = Math.min(right, this.mListPadding.left - left);
                }
                semOffsetChildrenLeftAndRight(right);
                if (i3 < this.mItemCount - 1) {
                    fillLeftRTL(i2, childAt.getLeft() - this.mDividerHeight);
                    adjustViewsLeftOrRight();
                    return;
                }
                return;
            }
            if (i3 == this.mItemCount - 1) {
                adjustViewsLeftOrRight();
            }
        }
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.ViewGroup
    @Deprecated
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        SemDragAndDropHorizontalListAnimator semDragAndDropHorizontalListAnimator = this.mDndListAnimator;
        if (semDragAndDropHorizontalListAnimator == null || !semDragAndDropHorizontalListAnimator.onInterceptTouchEvent(motionEvent)) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    public boolean onTouchEvent(MotionEvent motionEvent) {
        SemDragAndDropHorizontalListAnimator semDragAndDropHorizontalListAnimator = this.mDndListAnimator;
        if (semDragAndDropHorizontalListAnimator == null || !semDragAndDropHorizontalListAnimator.onTouchEvent(motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.ViewGroup
    @Deprecated
    protected int getChildDrawingOrder(int i, int i2) {
        SemDragAndDropHorizontalListAnimator semDragAndDropHorizontalListAnimator = this.mDndListAnimator;
        return semDragAndDropHorizontalListAnimator != null ? semDragAndDropHorizontalListAnimator.getChildDrawingOrder(i, i2) : super.getChildDrawingOrder(i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:127:0x03a8 A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x03ba A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x03d1 A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x03d9 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:140:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02e3 A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0159 A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0163 A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:213:0x017b A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0197 A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:222:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x01c5 A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:231:0x013f A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0093 A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009a A[Catch: all -> 0x0412, TRY_LEAVE, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a5 A[Catch: all -> 0x0412, TRY_ENTER, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x02a0 A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x032a A[Catch: all -> 0x0412, TryCatch #0 {all -> 0x0412, blocks: (B:5:0x0014, B:7:0x001e, B:13:0x0029, B:22:0x004c, B:25:0x0055, B:26:0x005b, B:28:0x0063, B:29:0x006a, B:30:0x008f, B:32:0x0093, B:33:0x0096, B:35:0x009a, B:40:0x00a5, B:42:0x00af, B:44:0x00ba, B:46:0x00c0, B:51:0x00cf, B:53:0x00d5, B:55:0x00db, B:59:0x00e7, B:60:0x00f8, B:63:0x0100, B:67:0x0114, B:68:0x011b, B:72:0x0126, B:75:0x0146, B:76:0x014e, B:79:0x0155, B:81:0x01e0, B:83:0x01e4, B:84:0x029b, B:86:0x02a0, B:88:0x02a4, B:90:0x02aa, B:94:0x02b4, B:96:0x02c0, B:97:0x02dc, B:99:0x032a, B:102:0x0332, B:104:0x0338, B:107:0x0340, B:108:0x034f, B:111:0x0356, B:113:0x036c, B:116:0x0373, B:118:0x0385, B:119:0x0396, B:122:0x039e, B:124:0x03a3, B:125:0x0392, B:127:0x03a8, B:129:0x03ae, B:130:0x03b1, B:132:0x03ba, B:133:0x03c2, B:135:0x03d1, B:136:0x03d4, B:141:0x02ba, B:143:0x02ca, B:145:0x02d0, B:146:0x02d3, B:147:0x02d8, B:148:0x02e3, B:150:0x02e8, B:153:0x02ee, B:155:0x02f3, B:157:0x02fe, B:158:0x031d, B:161:0x0325, B:162:0x0304, B:163:0x030d, B:165:0x0318, B:166:0x01f2, B:167:0x0208, B:169:0x020c, B:170:0x021b, B:171:0x0233, B:173:0x0237, B:175:0x023b, B:177:0x0241, B:180:0x024a, B:181:0x0246, B:182:0x024f, B:184:0x0255, B:187:0x025e, B:188:0x025a, B:189:0x0263, B:191:0x0269, B:193:0x026d, B:195:0x0273, B:198:0x027c, B:199:0x0278, B:200:0x0281, B:202:0x0287, B:205:0x0290, B:206:0x028c, B:207:0x0295, B:208:0x0159, B:209:0x0163, B:211:0x0167, B:212:0x0171, B:213:0x017b, B:215:0x017f, B:216:0x018b, B:217:0x0197, B:219:0x019b, B:220:0x01ac, B:221:0x01a4, B:224:0x01b5, B:225:0x01bf, B:226:0x01c5, B:228:0x01c9, B:229:0x01d9, B:230:0x01d2, B:231:0x013f, B:232:0x010b, B:234:0x0111, B:236:0x00e3, B:241:0x03dd, B:242:0x0411, B:245:0x007a, B:248:0x0083), top: B:4:0x0014 }] */
    @Override // android.widget.SemHorizontalAbsListView
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void layoutChildren() {
        /*
            Method dump skipped, instructions count: 1066
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SemHorizontalListView.layoutChildren():void");
    }

    private boolean isDirectChildHeaderOrFooter(View view) {
        ArrayList<FixedViewInfo> arrayList = this.mHeaderViewInfos;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (view == arrayList.get(i).view) {
                return true;
            }
        }
        ArrayList<FixedViewInfo> arrayList2 = this.mFooterViewInfos;
        int size2 = arrayList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            if (view == arrayList2.get(i2).view) {
                return true;
            }
        }
        return false;
    }

    private View makeAndAddView(int i, int i2, boolean z, int i3, boolean z2) {
        View activeView;
        if (!this.mDataChanged && (activeView = this.mRecycler.getActiveView(i)) != null) {
            setupChild(activeView, i, i2, z, i3, z2, true);
            return activeView;
        }
        View obtainView = obtainView(i, this.mIsScrap);
        if (obtainView != null) {
            setupChild(obtainView, i, i2, z, i3, z2, this.mIsScrap[0]);
        }
        return obtainView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setupChild(View view, int i, int i2, boolean z, int i3, boolean z2, boolean z3) {
        int makeMeasureSpec;
        Trace.traceBegin(8L, "setupListItem");
        boolean z4 = z2 && shouldShowSelector();
        boolean z5 = z4 != view.isSelected();
        int i4 = this.mTouchMode;
        boolean z6 = i4 > 0 && i4 < 3 && this.mMotionPosition == i;
        boolean z7 = z6 != view.isPressed();
        boolean needToMeasureChild = needToMeasureChild(view, z5, z3);
        SemHorizontalAbsListView.LayoutParams layoutParams = (SemHorizontalAbsListView.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = (SemHorizontalAbsListView.LayoutParams) generateDefaultLayoutParams();
        }
        layoutParams.viewType = this.mAdapter.getItemViewType(i);
        if ((z3 && !layoutParams.forceAdd) || (layoutParams.recycledHeaderFooter && layoutParams.viewType == -2)) {
            if (this.mIsRTL) {
                attachViewToParent(view, z ? 0 : -1, layoutParams);
            } else {
                attachViewToParent(view, z ? -1 : 0, layoutParams);
            }
        } else {
            layoutParams.forceAdd = false;
            if (layoutParams.viewType == -2) {
                layoutParams.recycledHeaderFooter = true;
            }
            if (this.mIsRTL) {
                addViewInLayout(view, z ? 0 : -1, layoutParams, true);
            } else {
                addViewInLayout(view, z ? -1 : 0, layoutParams, true);
            }
        }
        if (z5) {
            view.setSelected(z4);
            if (z4 && this.mIsFolderTypeFeature) {
                view.requestFocus();
            }
        }
        if (z7) {
            view.setPressed(z6);
        }
        if (this.mChoiceMode != 0 && this.mCheckStates != null) {
            if (view instanceof Checkable) {
                ((Checkable) view).setChecked(this.mCheckStates.get(i));
            } else if (getContext().getApplicationInfo().targetSdkVersion >= 11) {
                view.setActivated(this.mCheckStates.get(i));
            }
        }
        if (needToMeasureChild) {
            int childMeasureSpec = ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mListPadding.top + this.mListPadding.bottom, layoutParams.height);
            int i5 = layoutParams.width;
            if (i5 > 0) {
                makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
            } else {
                makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            }
            view.measure(makeMeasureSpec, childMeasureSpec);
        } else {
            cleanupLayoutState(view);
        }
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        if (this.mIsRTL) {
            int i6 = z ? i2 + measuredWidth : i2;
            int i7 = z ? i2 : i2 - measuredWidth;
            if (needToMeasureChild) {
                view.layout(i7, i3, i6, measuredHeight + i3);
            } else {
                view.offsetLeftAndRight(i7 - view.getLeft());
                view.offsetTopAndBottom(i3 - view.getTop());
            }
        } else {
            int i8 = z ? i2 : i2 - measuredWidth;
            int i9 = measuredWidth + i8;
            if (needToMeasureChild) {
                view.layout(i8, i3, i9, measuredHeight + i3);
            } else {
                view.offsetLeftAndRight(i8 - view.getLeft());
                view.offsetTopAndBottom(i3 - view.getTop());
            }
        }
        if (this.mCachingStarted && !view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }
        if (z3 && ((SemHorizontalAbsListView.LayoutParams) view.getLayoutParams()).scrappedFromPosition != i) {
            view.jumpDrawablesToCurrentState();
        }
        Trace.traceEnd(8L);
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup
    @Deprecated
    protected boolean canAnimate() {
        return super.canAnimate() && this.mItemCount > 0;
    }

    @Override // android.widget.AdapterView
    @Deprecated
    public void setSelection(int i) {
        setSelectionFromStart(i, 0);
    }

    public void setSelectionFromTop(int i, int i2) {
        if (this.mAdapter == null) {
            return;
        }
        if (!isInTouchMode()) {
            i = lookForSelectablePosition(i, true);
            if (i >= 0) {
                setNextSelectedPositionInt(i);
            }
        } else {
            this.mResurrectToPosition = i;
        }
        if (i >= 0) {
            this.mLayoutMode = 4;
            if (this.mIsRTL) {
                this.mSpecificTop = getWidth() - i2;
            } else {
                this.mSpecificTop = this.mListPadding.left + i2;
            }
            if (this.mNeedSync) {
                this.mSyncPosition = i;
                this.mSyncRowId = this.mAdapter.getItemId(i);
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            requestLayout();
        }
    }

    @Override // android.widget.SemHorizontalAbsListView
    @Deprecated
    public void setSelectionFromStart(int i, int i2) {
        if (this.mAdapter == null) {
            return;
        }
        if (!isInTouchMode()) {
            i = lookForSelectablePosition(i, true);
            if (i >= 0) {
                setNextSelectedPositionInt(i);
            }
        } else {
            this.mResurrectToPosition = i;
        }
        if (i >= 0) {
            this.mLayoutMode = 4;
            if (this.mIsRTL) {
                this.mSpecificTop = getWidth() - i2;
            } else {
                this.mSpecificTop = this.mListPadding.left + i2;
            }
            if (this.mNeedSync) {
                this.mSyncPosition = i;
                this.mSyncRowId = this.mAdapter.getItemId(i);
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            requestLayout();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x000e, code lost:
    
        if (r4 == (r0 + 1)) goto L11;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0016  */
    @Override // android.widget.SemHorizontalAbsListView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void setSelectionInt(int r4) {
        /*
            r3 = this;
            r3.setNextSelectedPositionInt(r4)
            int r0 = r3.mSelectedPosition
            if (r0 < 0) goto L11
            int r1 = r0 + (-1)
            r2 = 1
            if (r4 != r1) goto Ld
            goto L12
        Ld:
            int r0 = r0 + r2
            if (r4 != r0) goto L11
            goto L12
        L11:
            r2 = 0
        L12:
            android.widget.SemHorizontalAbsListView$AbsPositionScroller r4 = r3.mPositionScroller
            if (r4 == 0) goto L1b
            android.widget.SemHorizontalAbsListView$AbsPositionScroller r4 = r3.mPositionScroller
            r4.stop()
        L1b:
            r3.layoutChildren()
            if (r2 == 0) goto L23
            r3.awakenScrollBars()
        L23:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SemHorizontalListView.setSelectionInt(int):void");
    }

    @Override // android.widget.AdapterView
    int lookForSelectablePosition(int i, boolean z) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null && !isInTouchMode()) {
            int count = listAdapter.getCount();
            if (!this.mAreAllItemsSelectable) {
                if (this.mIsRTL) {
                    if (z) {
                        i = Math.min(i, count - 1);
                        while (i >= 0 && !listAdapter.isEnabled(i)) {
                            i--;
                        }
                    } else {
                        i = Math.max(0, i);
                        while (i < count && !listAdapter.isEnabled(i)) {
                            i++;
                        }
                    }
                } else if (z) {
                    i = Math.max(0, i);
                    while (i < count && !listAdapter.isEnabled(i)) {
                        i++;
                    }
                } else {
                    i = Math.min(i, count - 1);
                    while (i >= 0 && !listAdapter.isEnabled(i)) {
                        i--;
                    }
                }
            }
            if (i >= 0 && i < count) {
                return i;
            }
        }
        return -1;
    }

    int lookForSelectablePositionAfter(int i, int i2, boolean z) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null || isInTouchMode()) {
            return -1;
        }
        int lookForSelectablePosition = lookForSelectablePosition(i2, z);
        if (lookForSelectablePosition != -1) {
            return lookForSelectablePosition;
        }
        int count = listAdapter.getCount() - 1;
        int constrain = MathUtils.constrain(i, -1, count);
        if (this.mIsRTL) {
            if (z) {
                int max = Math.max(0, i2 + 1);
                while (max < constrain && !listAdapter.isEnabled(max)) {
                    max++;
                }
                if (max >= constrain) {
                    return -1;
                }
                return max;
            }
            int min = Math.min(i2 - 1, count);
            while (min > constrain && !listAdapter.isEnabled(min)) {
                min--;
            }
            if (min <= constrain) {
                return -1;
            }
            return min;
        }
        if (z) {
            int min2 = Math.min(i2 - 1, count);
            while (min2 > constrain && !listAdapter.isEnabled(min2)) {
                min2--;
            }
            if (min2 <= constrain) {
                return -1;
            }
            return min2;
        }
        int max2 = Math.max(0, i2 + 1);
        while (max2 < constrain && !listAdapter.isEnabled(max2)) {
            max2++;
        }
        if (max2 >= constrain) {
            return -1;
        }
        return max2;
    }

    @Deprecated
    public void setSelectionAfterHeaderView() {
        int size = this.mHeaderViewInfos.size();
        if (size > 0) {
            this.mNextSelectedPosition = 0;
        } else if (this.mAdapter != null) {
            setSelection(size);
        } else {
            this.mNextSelectedPosition = size;
            this.mLayoutMode = 2;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean dispatchKeyEvent = super.dispatchKeyEvent(keyEvent);
        return (dispatchKeyEvent || getFocusedChild() == null || keyEvent.getAction() != 0) ? dispatchKeyEvent : onKeyDown(keyEvent.getKeyCode(), keyEvent);
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View, android.view.KeyEvent.Callback
    @Deprecated
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return commonKey(i, 1, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    @Deprecated
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return commonKey(i, i2, keyEvent);
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View, android.view.KeyEvent.Callback
    @Deprecated
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return commonKey(i, 1, keyEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0109, code lost:
    
        if (fullScroll(17) != false) goto L142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x005c, code lost:
    
        if (fullScroll(66) != false) goto L142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0097, code lost:
    
        if (fullScroll(17) != false) goto L142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00f5, code lost:
    
        if (fullScroll(66) != false) goto L142;
     */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean commonKey(int r8, int r9, android.view.KeyEvent r10) {
        /*
            Method dump skipped, instructions count: 470
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SemHorizontalListView.commonKey(int, int, android.view.KeyEvent):boolean");
    }

    boolean pageScroll(int i) {
        int min;
        boolean z;
        int lookForSelectablePositionAfter;
        if (i != 17) {
            if (i == 66) {
                min = Math.min(this.mItemCount - 1, (this.mSelectedPosition + getChildCount()) - 1);
                z = true;
            }
            return false;
        }
        min = Math.max(0, (this.mSelectedPosition - getChildCount()) - 1);
        z = false;
        if (min >= 0 && (lookForSelectablePositionAfter = lookForSelectablePositionAfter(this.mSelectedPosition, min, z)) >= 0) {
            this.mLayoutMode = 4;
            this.mSpecificTop = this.mPaddingLeft + getHorizontalFadingEdgeLength();
            if (z && lookForSelectablePositionAfter > this.mItemCount - getChildCount()) {
                this.mLayoutMode = 3;
            }
            if (!z && lookForSelectablePositionAfter < getChildCount()) {
                this.mLayoutMode = 1;
            }
            setSelectionInt(lookForSelectablePositionAfter);
            invokeOnItemScrollListener();
            if (!awakenScrollBars()) {
                invalidate();
            }
            return true;
        }
        return false;
    }

    boolean fullScroll(int i) {
        int i2;
        boolean z = true;
        if (i == 17) {
            if (this.mSelectedPosition != 0) {
                int lookForSelectablePositionAfter = lookForSelectablePositionAfter(this.mSelectedPosition, 0, true);
                if (lookForSelectablePositionAfter >= 0) {
                    this.mLayoutMode = 1;
                    setSelectionInt(lookForSelectablePositionAfter);
                    invokeOnItemScrollListener();
                }
            }
            z = false;
        } else {
            if (i == 66 && this.mSelectedPosition < (i2 = this.mItemCount - 1)) {
                int lookForSelectablePositionAfter2 = lookForSelectablePositionAfter(this.mSelectedPosition, i2, false);
                if (lookForSelectablePositionAfter2 >= 0) {
                    this.mLayoutMode = 3;
                    setSelectionInt(lookForSelectablePositionAfter2);
                    invokeOnItemScrollListener();
                }
            }
            z = false;
        }
        if (z && !awakenScrollBars()) {
            awakenScrollBars();
            invalidate();
        }
        return z;
    }

    private boolean handleVerticalFocusWithinListItem(int i) {
        View selectedView;
        if (i != 33 && i != 130) {
            throw new IllegalArgumentException("direction must be one of {View.FOCUS_UP, View.FOCUS_DOWN}");
        }
        int childCount = getChildCount();
        if (this.mItemsCanFocus && childCount > 0 && this.mSelectedPosition != -1 && (selectedView = getSelectedView()) != null && selectedView.hasFocus() && (selectedView instanceof ViewGroup)) {
            View findFocus = selectedView.findFocus();
            View findNextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup) selectedView, findFocus, i);
            if (findNextFocus != null) {
                findFocus.getFocusedRect(this.mTempRect);
                offsetDescendantRectToMyCoords(findFocus, this.mTempRect);
                offsetRectIntoDescendantCoords(findNextFocus, this.mTempRect);
                if (findNextFocus.requestFocus(i, this.mTempRect)) {
                    if (findFocus != findNextFocus && this.mIsFolderTypeFeature) {
                        findFocus.setSelected(false);
                    }
                    playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
                    return true;
                }
            }
            View findNextFocus2 = FocusFinder.getInstance().findNextFocus((ViewGroup) getRootView(), findFocus, i);
            if (findNextFocus2 != null) {
                return isViewAncestorOf(findNextFocus2, this);
            }
        }
        return false;
    }

    boolean arrowScroll(int i) {
        try {
            this.mInLayout = true;
            boolean arrowScrollImpl = arrowScrollImpl(i);
            if (arrowScrollImpl) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
            }
            return arrowScrollImpl;
        } finally {
            this.mInLayout = false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0025, code lost:
    
        if (r7 <= r6) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0027, code lost:
    
        r6 = r7 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x005b, code lost:
    
        if (r7 <= r6) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final int nextSelectedPositionForDirection(android.view.View r6, int r7, int r8) {
        /*
            r5 = this;
            r0 = 1
            r1 = -1
            r2 = 66
            if (r8 != r2) goto L37
            int r3 = r5.getWidth()
            android.graphics.Rect r4 = r5.mListPadding
            int r4 = r4.right
            int r3 = r3 - r4
            if (r6 == 0) goto L36
            int r6 = r6.getRight()
            if (r6 > r3) goto L36
            int r6 = r5.mFirstPosition
            int r3 = r5.getChildCount()
            int r6 = r6 + r3
            int r6 = r6 - r0
            boolean r3 = r5.mIsRTL
            if (r3 == 0) goto L2a
            if (r7 == r1) goto L5e
            if (r7 > r6) goto L5e
        L27:
            int r6 = r7 + (-1)
            goto L5e
        L2a:
            if (r7 == r1) goto L33
            int r6 = r5.mFirstPosition
            if (r7 < r6) goto L33
        L30:
            int r7 = r7 + r0
        L31:
            r6 = r7
            goto L5e
        L33:
            int r6 = r5.mFirstPosition
            goto L5e
        L36:
            return r1
        L37:
            android.graphics.Rect r3 = r5.mListPadding
            int r3 = r3.left
            if (r6 == 0) goto L72
            int r6 = r6.getLeft()
            if (r6 < r3) goto L72
            int r6 = r5.mFirstPosition
            int r3 = r5.getChildCount()
            int r6 = r6 + r3
            int r6 = r6 - r0
            boolean r3 = r5.mIsRTL
            if (r3 == 0) goto L59
            if (r7 == r1) goto L56
            int r6 = r5.mFirstPosition
            if (r7 < r6) goto L56
            goto L30
        L56:
            int r7 = r5.mFirstPosition
            goto L31
        L59:
            if (r7 == r1) goto L5e
            if (r7 > r6) goto L5e
            goto L27
        L5e:
            if (r6 < 0) goto L72
            android.widget.ListAdapter r7 = r5.mAdapter
            int r7 = r7.getCount()
            if (r6 < r7) goto L69
            goto L72
        L69:
            if (r8 != r2) goto L6c
            goto L6d
        L6c:
            r0 = 0
        L6d:
            int r5 = r5.lookForSelectablePosition(r6, r0)
            return r5
        L72:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SemHorizontalListView.nextSelectedPositionForDirection(android.view.View, int, int):int");
    }

    private boolean arrowScrollImpl(int i) {
        View focusedChild;
        if (getChildCount() <= 0) {
            return false;
        }
        View selectedView = getSelectedView();
        int i2 = this.mSelectedPosition;
        int nextSelectedPositionForDirection = nextSelectedPositionForDirection(selectedView, i2, i);
        int amountToScroll = amountToScroll(i, nextSelectedPositionForDirection);
        View view = null;
        ArrowScrollFocusResult arrowScrollFocused = this.mItemsCanFocus ? arrowScrollFocused(i) : null;
        if (arrowScrollFocused != null) {
            nextSelectedPositionForDirection = arrowScrollFocused.getSelectedPosition();
            amountToScroll = arrowScrollFocused.getAmountToScroll();
        }
        boolean z = arrowScrollFocused != null;
        if (nextSelectedPositionForDirection != -1) {
            handleNewSelectionChange(selectedView, i, nextSelectedPositionForDirection, arrowScrollFocused != null);
            setSelectedPositionInt(nextSelectedPositionForDirection);
            setNextSelectedPositionInt(nextSelectedPositionForDirection);
            selectedView = getSelectedView();
            if (this.mItemsCanFocus && arrowScrollFocused == null && (focusedChild = getFocusedChild()) != null) {
                focusedChild.clearFocus();
            }
            checkSelectionChanged();
            i2 = nextSelectedPositionForDirection;
            z = true;
        }
        if (amountToScroll > 0) {
            if (i != 17) {
                amountToScroll = -amountToScroll;
            }
            scrollListItemsBy(amountToScroll);
            z = true;
        }
        if (this.mItemsCanFocus && arrowScrollFocused == null && selectedView != null && selectedView.hasFocus()) {
            View findFocus = selectedView.findFocus();
            if (!isViewAncestorOf(findFocus, this) || distanceToView(findFocus) > 0) {
                findFocus.clearFocus();
            }
        }
        if (nextSelectedPositionForDirection != -1 || selectedView == null || isViewAncestorOf(selectedView, this)) {
            view = selectedView;
        } else {
            this.mSelectorRect.setEmpty();
            hideSelector();
            this.mResurrectToPosition = -1;
        }
        if (!z) {
            return false;
        }
        if (view != null) {
            positionSelectorLikeFocus(i2, view);
            this.mSelectedLeft = view.getLeft();
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        invokeOnItemScrollListener();
        return true;
    }

    private void handleNewSelectionChange(View view, int i, int i2, boolean z) {
        View childAt;
        boolean z2;
        if (i2 == -1) {
            throw new IllegalArgumentException("newSelectedPosition needs to be valid");
        }
        int i3 = this.mSelectedPosition - this.mFirstPosition;
        int i4 = i2 - this.mFirstPosition;
        if (i == 17) {
            childAt = view;
            view = getChildAt(i4);
            i3 = i4;
            i4 = i3;
            z2 = true;
        } else {
            childAt = getChildAt(i4);
            z2 = false;
        }
        int childCount = getChildCount();
        if (view != null) {
            view.setSelected(!z && z2);
            measureAndAdjustRight(view, i3, childCount);
        }
        if (childAt != null) {
            childAt.setSelected((z || z2) ? false : true);
            measureAndAdjustRight(childAt, i4, childCount);
        }
    }

    private void measureAndAdjustRight(View view, int i, int i2) {
        int width = view.getWidth();
        measureItem(view);
        if (view.getMeasuredWidth() == width) {
            return;
        }
        relayoutMeasuredItem(view);
        int measuredWidth = view.getMeasuredWidth() - width;
        while (true) {
            i++;
            if (i >= i2) {
                return;
            } else {
                getChildAt(i).offsetLeftAndRight(measuredWidth);
            }
        }
    }

    private void measureItem(View view) {
        int makeMeasureSpec;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -2);
        }
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mListPadding.top + this.mListPadding.bottom, layoutParams.height);
        int i = layoutParams.width;
        if (i > 0) {
            makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
        } else {
            makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        }
        view.measure(makeMeasureSpec, childMeasureSpec);
    }

    private void relayoutMeasuredItem(View view) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int left = view.getLeft();
        int i = this.mListPadding.top;
        view.layout(left, i, measuredWidth + left, measuredHeight + i);
    }

    private int getArrowScrollPreviewLength() {
        return Math.max(2, getHorizontalFadingEdgeLength());
    }

    private int amountToScroll(int i, int i2) {
        int width = getWidth() - this.mListPadding.right;
        int i3 = this.mListPadding.left;
        int childCount = getChildCount();
        if (i == 66) {
            int i4 = childCount - 1;
            if (this.mIsRTL) {
                i4 = 0;
            }
            if (i2 != -1) {
                i4 = i2 - this.mFirstPosition;
            }
            if (this.mIsRTL) {
                while (i4 < 0) {
                    addViewRightSide(getChildAt(0), this.mFirstPosition);
                    this.mFirstPosition--;
                    i4 = i2 - this.mFirstPosition;
                }
            } else {
                while (childCount <= i4) {
                    addViewRightSide(getChildAt(childCount - 1), (this.mFirstPosition + childCount) - 1);
                    childCount++;
                }
            }
            int i5 = this.mFirstPosition + i4;
            View childAt = getChildAt(i4);
            int arrowScrollPreviewLength = (!this.mIsRTL ? i5 < this.mItemCount + (-1) : i5 > 0) ? width : width - getArrowScrollPreviewLength();
            if (childAt.getRight() <= arrowScrollPreviewLength) {
                return 0;
            }
            if (i2 != -1 && arrowScrollPreviewLength - childAt.getLeft() >= getMaxScrollAmount()) {
                return 0;
            }
            int right = childAt.getRight() - arrowScrollPreviewLength;
            if (!this.mIsRTL ? this.mFirstPosition + childCount == this.mItemCount : this.mFirstPosition == 0) {
                right = Math.min(right, getChildAt(this.mIsRTL ? 0 : childCount - 1).getRight() - width);
            }
            return Math.min(right, getMaxScrollAmount());
        }
        int i6 = this.mIsRTL ? childCount - 1 : 0;
        if (i2 != -1) {
            i6 = i2 - this.mFirstPosition;
        }
        if (this.mIsRTL) {
            while (childCount <= i6) {
                addViewLeftSide(getChildAt(childCount - 1), (this.mFirstPosition + childCount) - 1);
                childCount++;
            }
        } else {
            while (i6 < 0) {
                addViewLeftSide(getChildAt(0), this.mFirstPosition);
                this.mFirstPosition--;
                i6 = i2 - this.mFirstPosition;
            }
        }
        int i7 = this.mFirstPosition + i6;
        View childAt2 = getChildAt(i6);
        int arrowScrollPreviewLength2 = (!this.mIsRTL ? i7 > 0 : i7 < this.mItemCount + (-1)) ? i3 : getArrowScrollPreviewLength() + i3;
        if (childAt2.getLeft() >= arrowScrollPreviewLength2) {
            return 0;
        }
        if (i2 != -1 && childAt2.getRight() - arrowScrollPreviewLength2 >= getMaxScrollAmount()) {
            return 0;
        }
        int left = arrowScrollPreviewLength2 - childAt2.getLeft();
        if (!this.mIsRTL ? this.mFirstPosition == 0 : this.mFirstPosition + childCount == this.mItemCount) {
            left = Math.min(left, i3 - getChildAt(this.mIsRTL ? childCount - 1 : 0).getLeft());
        }
        return Math.min(left, getMaxScrollAmount());
    }

    private static class ArrowScrollFocusResult {
        private int mAmountToScroll;
        private int mSelectedPosition;

        private ArrowScrollFocusResult() {
        }

        void populate(int i, int i2) {
            this.mSelectedPosition = i;
            this.mAmountToScroll = i2;
        }

        public int getSelectedPosition() {
            return this.mSelectedPosition;
        }

        public int getAmountToScroll() {
            return this.mAmountToScroll;
        }
    }

    private int lookForSelectablePositionOnScreen(int i) {
        int childCount;
        int i2 = this.mFirstPosition;
        if (i == 66) {
            int i3 = this.mSelectedPosition != -1 ? this.mSelectedPosition + 1 : i2;
            if (i3 >= this.mAdapter.getCount()) {
                return -1;
            }
            if (i3 < i2) {
                i3 = i2;
            }
            int lastVisiblePosition = getLastVisiblePosition();
            ListAdapter adapter = getAdapter();
            if (this.mIsRTL) {
                while (i3 >= i2) {
                    if (adapter.isEnabled(i3) && getChildAt(i3 - i2).getVisibility() == 0) {
                        return i3;
                    }
                    i3--;
                }
            } else {
                while (i3 <= lastVisiblePosition) {
                    if (adapter.isEnabled(i3) && getChildAt(i3 - i2).getVisibility() == 0) {
                        return i3;
                    }
                    i3++;
                }
            }
        } else {
            int childCount2 = (getChildCount() + i2) - 1;
            int lastVisiblePosition2 = getLastVisiblePosition();
            if (this.mSelectedPosition != -1) {
                childCount = this.mSelectedPosition;
            } else {
                childCount = getChildCount() + i2;
            }
            int i4 = childCount - 1;
            if (i4 >= 0 && i4 < this.mAdapter.getCount()) {
                if (i4 <= childCount2) {
                    childCount2 = i4;
                }
                ListAdapter adapter2 = getAdapter();
                if (this.mIsRTL) {
                    while (childCount2 <= lastVisiblePosition2) {
                        if (adapter2.isEnabled(childCount2) && getChildAt(childCount2 - i2).getVisibility() == 0) {
                            return childCount2;
                        }
                        childCount2++;
                    }
                } else {
                    while (childCount2 >= i2) {
                        if (adapter2.isEnabled(childCount2) && getChildAt(childCount2 - i2).getVisibility() == 0) {
                            return childCount2;
                        }
                        childCount2--;
                    }
                }
            }
        }
        return -1;
    }

    private ArrowScrollFocusResult arrowScrollFocused(int i) {
        View findNextFocusFromRect;
        int lookForSelectablePositionOnScreen;
        View selectedView = getSelectedView();
        if (selectedView != null && selectedView.hasFocus()) {
            findNextFocusFromRect = FocusFinder.getInstance().findNextFocus(this, selectedView.findFocus(), i);
        } else {
            if (i == 66) {
                int arrowScrollPreviewLength = this.mListPadding.left + (this.mFirstPosition > 0 ? getArrowScrollPreviewLength() : 0);
                if (selectedView != null && selectedView.getLeft() > arrowScrollPreviewLength) {
                    arrowScrollPreviewLength = selectedView.getLeft();
                }
                this.mTempRect.set(arrowScrollPreviewLength, 0, arrowScrollPreviewLength, 0);
            } else {
                int width = (getWidth() - this.mListPadding.right) - ((this.mFirstPosition + getChildCount()) - 1 < this.mItemCount ? getArrowScrollPreviewLength() : 0);
                if (selectedView != null && selectedView.getRight() < width) {
                    width = selectedView.getRight();
                }
                this.mTempRect.set(width, 0, width, 0);
            }
            findNextFocusFromRect = FocusFinder.getInstance().findNextFocusFromRect(this, this.mTempRect, i);
        }
        if (findNextFocusFromRect != null) {
            int positionOfNewFocus = positionOfNewFocus(findNextFocusFromRect);
            if (this.mSelectedPosition != -1 && positionOfNewFocus != this.mSelectedPosition && (lookForSelectablePositionOnScreen = lookForSelectablePositionOnScreen(i)) != -1 && ((i == 66 && lookForSelectablePositionOnScreen < positionOfNewFocus) || (i == 17 && lookForSelectablePositionOnScreen > positionOfNewFocus))) {
                return null;
            }
            int amountToScrollToNewFocus = amountToScrollToNewFocus(i, findNextFocusFromRect, positionOfNewFocus);
            int maxScrollAmount = getMaxScrollAmount();
            if (amountToScrollToNewFocus < maxScrollAmount) {
                findNextFocusFromRect.requestFocus(i);
                this.mArrowScrollFocusResult.populate(positionOfNewFocus, amountToScrollToNewFocus);
                return this.mArrowScrollFocusResult;
            }
            if (distanceToView(findNextFocusFromRect) < maxScrollAmount) {
                findNextFocusFromRect.requestFocus(i);
                this.mArrowScrollFocusResult.populate(positionOfNewFocus, maxScrollAmount);
                return this.mArrowScrollFocusResult;
            }
        }
        return null;
    }

    private int positionOfNewFocus(View view) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (isViewAncestorOf(view, getChildAt(i))) {
                return this.mFirstPosition + i;
            }
        }
        throw new IllegalArgumentException("newFocus is not a child of any of the children of the list!");
    }

    private boolean isViewAncestorOf(View view, View view2) {
        if (view == view2) {
            return true;
        }
        Object parent = view.getParent();
        return (parent instanceof ViewGroup) && isViewAncestorOf((View) parent, view2);
    }

    private int amountToScrollToNewFocus(int i, View view, int i2) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        if (i == 17) {
            if (this.mTempRect.left >= this.mListPadding.left) {
                return 0;
            }
            int i3 = this.mListPadding.left - this.mTempRect.left;
            if (this.mIsRTL) {
                if (i2 >= this.mItemCount - 1) {
                    return i3;
                }
            } else if (i2 <= 0) {
                return i3;
            }
            return i3 + getArrowScrollPreviewLength();
        }
        int width = getWidth() - this.mListPadding.right;
        if (this.mTempRect.right <= width) {
            return 0;
        }
        int i4 = this.mTempRect.right - width;
        if (this.mIsRTL) {
            if (i2 <= 0) {
                return i4;
            }
        } else if (i2 >= this.mItemCount - 1) {
            return i4;
        }
        return i4 + getArrowScrollPreviewLength();
    }

    private int distanceToView(View view) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        int i = (this.mRight - this.mLeft) - this.mListPadding.right;
        if (this.mTempRect.right < this.mListPadding.left) {
            return this.mListPadding.left - this.mTempRect.right;
        }
        if (this.mTempRect.left > i) {
            return this.mTempRect.left - i;
        }
        return 0;
    }

    private void scrollListItemsBy(int i) {
        int i2;
        View childAt;
        int i3;
        semOffsetChildrenLeftAndRight(i);
        int width = getWidth() - this.mListPadding.right;
        int i4 = this.mListPadding.left;
        SemHorizontalAbsListView.RecycleBin recycleBin = this.mRecycler;
        if (i < 0) {
            int childCount = getChildCount();
            if (this.mIsRTL) {
                childAt = getChildAt(0);
                while (childAt.getRight() < width) {
                    if (this.mFirstPosition > 0) {
                        childAt = addViewRightSide(childAt, this.mFirstPosition);
                    }
                    if (childAt == null) {
                        return;
                    } else {
                        this.mFirstPosition--;
                    }
                }
            } else {
                childAt = getChildAt(childCount - 1);
                while (childAt.getRight() < width && (this.mFirstPosition + childCount) - 1 < this.mItemCount - 1) {
                    childAt = addViewRightSide(childAt, i3);
                    childCount++;
                }
            }
            if (childAt.getRight() < width) {
                semOffsetChildrenLeftAndRight(width - childAt.getRight());
            }
            View childAt2 = getChildAt(0);
            if (this.mIsRTL) {
                childAt2 = getChildAt(childCount - 1);
            }
            if (childAt2 == null) {
                return;
            }
            if (this.mIsRTL) {
                int childCount2 = getChildCount() - 1;
                while (childAt2.getRight() < i4) {
                    if (recycleBin.shouldRecycleViewType(((SemHorizontalAbsListView.LayoutParams) childAt2.getLayoutParams()).viewType)) {
                        recycleBin.addScrapView(childAt2, this.mFirstPosition + childCount2);
                    }
                    detachViewFromParent(childAt2);
                    childCount2--;
                    childAt2 = getChildAt(childCount2);
                }
                return;
            }
            while (childAt2.getRight() < i4) {
                if (recycleBin.shouldRecycleViewType(((SemHorizontalAbsListView.LayoutParams) childAt2.getLayoutParams()).viewType)) {
                    recycleBin.addScrapView(childAt2, this.mFirstPosition);
                }
                detachViewFromParent(childAt2);
                childAt2 = getChildAt(0);
                this.mFirstPosition++;
            }
            return;
        }
        int childCount3 = getChildCount();
        View childAt3 = this.mIsRTL ? getChildAt(childCount3 - 1) : getChildAt(0);
        if (this.mIsRTL) {
            while (childAt3.getLeft() > i4 && (this.mFirstPosition + childCount3) - 1 < this.mItemCount - 1) {
                childAt3 = addViewLeftSide(childAt3, i2);
                if (childAt3 == null) {
                    return;
                } else {
                    childCount3++;
                }
            }
        } else {
            while (childAt3.getLeft() > i4 && this.mFirstPosition > 0) {
                childAt3 = addViewLeftSide(childAt3, this.mFirstPosition);
                if (childAt3 == null) {
                    return;
                } else {
                    this.mFirstPosition--;
                }
            }
        }
        if (childAt3.getLeft() > i4) {
            semOffsetChildrenLeftAndRight(i4 - childAt3.getLeft());
        }
        int childCount4 = getChildCount() - 1;
        View childAt4 = getChildAt(childCount4);
        if (this.mIsRTL) {
            View childAt5 = getChildAt(0);
            while (childAt5 != null && childAt5.getLeft() > width) {
                if (recycleBin.shouldRecycleViewType(((SemHorizontalAbsListView.LayoutParams) childAt5.getLayoutParams()).viewType)) {
                    recycleBin.addScrapView(childAt5, this.mFirstPosition);
                }
                detachViewFromParent(childAt5);
                childAt5 = getChildAt(0);
                this.mFirstPosition++;
            }
            return;
        }
        while (childAt4 != null && childAt4.getLeft() > width) {
            if (recycleBin.shouldRecycleViewType(((SemHorizontalAbsListView.LayoutParams) childAt4.getLayoutParams()).viewType)) {
                recycleBin.addScrapView(childAt4, this.mFirstPosition + childCount4);
            }
            detachViewFromParent(childAt4);
            childCount4--;
            childAt4 = getChildAt(childCount4);
        }
    }

    private View addViewLeftSide(View view, int i) {
        int i2 = this.mIsRTL ? i + 1 : i - 1;
        View obtainView = obtainView(i2, this.mIsScrap);
        int left = view.getLeft() - this.mDividerHeight;
        if (obtainView != null) {
            setupChild(obtainView, i2, left, false, this.mListPadding.top, false, this.mIsScrap[0]);
        }
        return obtainView;
    }

    private View addViewRightSide(View view, int i) {
        int i2 = i + 1;
        View obtainView = obtainView(i2, this.mIsScrap);
        int right = view != null ? view.getRight() + this.mDividerHeight : 0;
        if (obtainView != null) {
            setupChild(obtainView, i2, right, true, this.mListPadding.top, false, this.mIsScrap[0]);
        }
        return obtainView;
    }

    @Deprecated
    public void setItemsCanFocus(boolean z) {
        this.mItemsCanFocus = z;
        if (z) {
            return;
        }
        setDescendantFocusability(393216);
    }

    @Deprecated
    public boolean getItemsCanFocus() {
        return this.mItemsCanFocus;
    }

    @Override // android.view.View
    @Deprecated
    public boolean isOpaque() {
        boolean z = (this.mCachingActive && this.mIsCacheColorOpaque && this.mDividerIsOpaque && hasOpaqueScrollbars()) || super.isOpaque();
        if (z) {
            if (this.mIsRTL) {
                int width = getWidth() - (this.mListPadding != null ? this.mListPadding.right : this.mPaddingRight);
                View childAt = getChildAt(0);
                if (childAt != null && childAt.getRight() + getDividerHeight() >= width) {
                    int i = this.mListPadding != null ? this.mListPadding.left : this.mPaddingLeft;
                    View childAt2 = getChildAt(getChildCount() - 1);
                    if (childAt2 == null || childAt2.getLeft() < i) {
                    }
                }
                return false;
            }
            int i2 = this.mListPadding != null ? this.mListPadding.left : this.mPaddingLeft;
            View childAt3 = getChildAt(0);
            if (childAt3 != null && childAt3.getLeft() <= i2) {
                int width2 = getWidth() - (this.mListPadding != null ? this.mListPadding.right : this.mPaddingRight);
                View childAt4 = getChildAt(getChildCount() - 1);
                if (childAt4 == null || childAt4.getRight() + getDividerHeight() < width2) {
                }
            }
            return false;
        }
        return z;
    }

    void drawOverscrollHeader(Canvas canvas, Drawable drawable, Rect rect) {
        int minimumWidth = drawable.getMinimumWidth();
        canvas.save();
        canvas.clipRect(rect);
        if (rect.right - rect.left < minimumWidth) {
            rect.left = rect.right - minimumWidth;
        }
        drawable.setBounds(rect);
        drawable.draw(canvas);
        canvas.restore();
    }

    void drawOverscrollFooter(Canvas canvas, Drawable drawable, Rect rect) {
        int minimumWidth = drawable.getMinimumWidth();
        canvas.save();
        canvas.clipRect(rect);
        if (rect.right - rect.left < minimumWidth) {
            rect.right = rect.left + minimumWidth;
        }
        drawable.setBounds(rect);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.ViewGroup, android.view.View
    @Deprecated
    protected void dispatchDraw(Canvas canvas) {
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        if (this.mCachingStarted) {
            this.mCachingActive = true;
        }
        int i5 = this.mDividerHeight;
        Drawable drawable = this.mOverScrollHeader;
        Drawable drawable2 = this.mOverScrollFooter;
        int i6 = drawable != null ? 1 : 0;
        boolean z2 = drawable2 != null;
        boolean z3 = i5 > 0 && this.mDivider != null;
        if (z3 || i6 != 0 || z2) {
            Rect rect = this.mTempRect;
            rect.top = this.mPaddingTop;
            rect.bottom = (this.mBottom - this.mTop) - this.mPaddingBottom;
            int childCount = getChildCount();
            int size = this.mHeaderViewInfos.size();
            int i7 = this.mItemCount;
            int size2 = (i7 - this.mFooterViewInfos.size()) - 1;
            boolean z4 = this.mHeaderDividersEnabled;
            boolean z5 = this.mFooterDividersEnabled;
            int i8 = this.mFirstPosition;
            ListAdapter listAdapter = this.mAdapter;
            boolean z6 = isOpaque() && !super.isOpaque();
            if (z6 && this.mDividerPaint == null && this.mIsCacheColorOpaque) {
                Paint paint = new Paint();
                this.mDividerPaint = paint;
                z = z2;
                paint.setColor(getCacheColorHint());
            } else {
                z = z2;
            }
            Paint paint2 = this.mDividerPaint;
            boolean z7 = z3;
            if ((this.mGroupFlags & 34) == 34) {
                i = this.mListPadding.left;
                i2 = this.mListPadding.right;
            } else {
                i = 0;
                i2 = 0;
            }
            int i9 = ((this.mRight - this.mLeft) - i2) + this.mScrollX;
            if (!this.mStackFromBottom) {
                int i10 = this.mScrollX;
                if (childCount > 0 && i10 < 0) {
                    if (i6 != 0) {
                        rect.right = 0;
                        rect.left = i10;
                        drawOverscrollHeader(canvas, drawable, rect);
                    } else if (z7) {
                        rect.right = 0;
                        rect.left = -i5;
                        drawDivider(canvas, rect, -1);
                    }
                }
                int i11 = 0;
                int i12 = 0;
                while (i12 < childCount) {
                    int i13 = i8 + i12;
                    boolean z8 = i13 < size;
                    boolean z9 = i13 >= size2;
                    if ((z4 || !z8) && (z5 || !z9)) {
                        i11 = getChildAt(i12).getRight();
                        i4 = i5;
                        boolean z10 = i12 == childCount + (-1);
                        if (z7 && i11 < i9 && (!z || !z10)) {
                            boolean z11 = z10;
                            int i14 = i13 + 1;
                            if (listAdapter.isEnabled(i13) && ((z4 || (!z8 && i14 >= size)) && (z11 || (listAdapter.isEnabled(i14) && (z5 || (!z9 && i14 < size2)))))) {
                                rect.left = i11;
                                rect.right = i11 + i4;
                                drawDivider(canvas, rect, i12);
                            } else if (z6) {
                                rect.left = i11;
                                rect.right = i11 + i4;
                                canvas.drawRect(rect, paint2);
                            }
                        }
                    } else {
                        i4 = i5;
                    }
                    i12++;
                    i5 = i4;
                }
                int i15 = this.mRight + this.mScrollX;
                if (z && i8 + childCount == i7 && i15 > i11) {
                    rect.left = i11;
                    rect.right = i15;
                    drawOverscrollFooter(canvas, drawable2, rect);
                }
            } else {
                int i16 = this.mScrollX;
                if (childCount > 0 && i6 != 0) {
                    rect.left = i16;
                    rect.right = getChildAt(0).getLeft();
                    drawOverscrollHeader(canvas, drawable, rect);
                }
                int i17 = i6;
                while (i17 < childCount) {
                    int i18 = i8 + i17;
                    boolean z12 = i18 < size;
                    boolean z13 = i18 >= size2;
                    if ((z4 || !z12) && (z5 || !z13)) {
                        int left = getChildAt(i17).getLeft();
                        if (z7 && left > i) {
                            boolean z14 = i17 == i6;
                            i3 = i16;
                            int i19 = i18 - 1;
                            if (listAdapter.isEnabled(i18) && ((z4 || (!z12 && i19 >= size)) && (z14 || (listAdapter.isEnabled(i19) && (z5 || (!z13 && i19 < size2)))))) {
                                rect.left = left - i5;
                                rect.right = left;
                                drawDivider(canvas, rect, i17 - 1);
                            } else if (z6) {
                                rect.left = left - i5;
                                rect.right = left;
                                canvas.drawRect(rect, paint2);
                            }
                            i17++;
                            i16 = i3;
                        }
                    }
                    i3 = i16;
                    i17++;
                    i16 = i3;
                }
                int i20 = i16;
                if (childCount > 0 && i20 > 0) {
                    if (z) {
                        int i21 = this.mRight;
                        rect.left = i21;
                        rect.right = i21 + i20;
                        drawOverscrollFooter(canvas, drawable2, rect);
                    } else if (z7) {
                        rect.left = i9;
                        rect.right = i9 + i5;
                        drawDivider(canvas, rect, -1);
                    }
                }
            }
        }
        SemAddDeleteHorizontalListAnimator semAddDeleteHorizontalListAnimator = this.mAddDeleteListAnimator;
        if (semAddDeleteHorizontalListAnimator != null) {
            semAddDeleteHorizontalListAnimator.draw(canvas);
        }
        super.dispatchDraw(canvas);
        SemDragAndDropHorizontalListAnimator semDragAndDropHorizontalListAnimator = this.mDndListAnimator;
        if (semDragAndDropHorizontalListAnimator != null) {
            semDragAndDropHorizontalListAnimator.dispatchDraw(canvas);
        }
    }

    @Override // android.view.ViewGroup
    @Deprecated
    protected boolean drawChild(Canvas canvas, View view, long j) {
        SemDragAndDropHorizontalListAnimator semDragAndDropHorizontalListAnimator = this.mDndListAnimator;
        if (semDragAndDropHorizontalListAnimator != null && !semDragAndDropHorizontalListAnimator.preDrawChild(canvas, view, j)) {
            return false;
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        if (this.mCachingActive && view.mCachingFailed) {
            this.mCachingActive = false;
        }
        SemDragAndDropHorizontalListAnimator semDragAndDropHorizontalListAnimator2 = this.mDndListAnimator;
        if (semDragAndDropHorizontalListAnimator2 != null) {
            semDragAndDropHorizontalListAnimator2.postDrawChild(canvas, view, j);
        }
        return drawChild;
    }

    void drawDivider(Canvas canvas, Rect rect, int i) {
        Drawable drawable = this.mDivider;
        drawable.setBounds(rect);
        drawable.draw(canvas);
    }

    @Deprecated
    public Drawable getDivider() {
        return this.mDivider;
    }

    @Deprecated
    public void setDivider(Drawable drawable) {
        if (drawable != null) {
            this.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerHeight = 0;
        }
        this.mDivider = drawable;
        this.mDividerIsOpaque = drawable == null || drawable.getOpacity() == -1;
        requestLayout();
        invalidate();
    }

    @Deprecated
    public int getDividerHeight() {
        return this.mDividerHeight;
    }

    @Deprecated
    public void setDividerHeight(int i) {
        this.mDividerHeight = i;
        requestLayout();
        invalidate();
    }

    @Deprecated
    public void setHeaderDividersEnabled(boolean z) {
        this.mHeaderDividersEnabled = z;
        invalidate();
    }

    @Deprecated
    public boolean areHeaderDividersEnabled() {
        return this.mHeaderDividersEnabled;
    }

    @Deprecated
    public void setFooterDividersEnabled(boolean z) {
        this.mFooterDividersEnabled = z;
        invalidate();
    }

    @Deprecated
    public boolean areFooterDividersEnabled() {
        return this.mFooterDividersEnabled;
    }

    @Deprecated
    public void setOverscrollHeader(Drawable drawable) {
        this.mOverScrollHeader = drawable;
        if (this.mScrollX < 0) {
            invalidate();
        }
    }

    @Deprecated
    public Drawable getOverscrollHeader() {
        return this.mOverScrollHeader;
    }

    @Deprecated
    public void setOverscrollFooter(Drawable drawable) {
        this.mOverScrollFooter = drawable;
        invalidate();
    }

    @Deprecated
    public Drawable getOverscrollFooter() {
        return this.mOverScrollFooter;
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        ListAdapter listAdapter = this.mAdapter;
        int i2 = -1;
        int i3 = 0;
        if (listAdapter != null && z && rect != null) {
            rect.offset(this.mScrollX, this.mScrollY);
            if (listAdapter.getCount() < getChildCount() + this.mFirstPosition) {
                this.mLayoutMode = 0;
                layoutChildren();
            }
            Rect rect2 = this.mTempRect;
            int childCount = getChildCount();
            int i4 = this.mFirstPosition;
            int i5 = Integer.MAX_VALUE;
            int i6 = 0;
            while (i3 < childCount) {
                if (listAdapter.isEnabled(i4 + i3)) {
                    View childAt = getChildAt(i3);
                    childAt.getDrawingRect(rect2);
                    offsetDescendantRectToMyCoords(childAt, rect2);
                    int distance = getDistance(rect, rect2, i);
                    if (distance < i5) {
                        i6 = childAt.getLeft();
                        i2 = i3;
                        i5 = distance;
                    }
                }
                i3++;
            }
            i3 = i6;
        }
        if (i2 >= 0) {
            setSelectionFromStart(i2 + this.mFirstPosition, i3);
        } else {
            requestLayout();
        }
        if (!z || this.mDndListAnimator == null) {
            return;
        }
        post(new Runnable() { // from class: android.widget.SemHorizontalListView.2
            @Override // java.lang.Runnable
            public void run() {
                SemHorizontalListView.this.mDndListAnimator.speakDescriptionForAccessibility();
            }
        });
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (!z || this.mDndListAnimator == null) {
            return;
        }
        post(new Runnable() { // from class: android.widget.SemHorizontalListView.3
            @Override // java.lang.Runnable
            public void run() {
                SemHorizontalListView.this.mDndListAnimator.speakDescriptionForAccessibility();
            }
        });
    }

    @Override // android.view.View
    @Deprecated
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                addHeaderView(getChildAt(i));
            }
            removeAllViews();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected <T extends View> T findViewTraversal(int i) {
        T t = (T) super.findViewTraversal(i);
        if (t != null) {
            return t;
        }
        T t2 = (T) findViewInHeadersOrFooters(this.mHeaderViewInfos, i);
        return t2 != null ? t2 : (T) findViewInHeadersOrFooters(this.mFooterViewInfos, i);
    }

    View findViewInHeadersOrFooters(ArrayList<FixedViewInfo> arrayList, int i) {
        View findViewById;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = arrayList.get(i2).view;
            if (!view.isRootNamespace() && (findViewById = view.findViewById(i)) != null) {
                return findViewById;
            }
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected <T extends View> T findViewWithTagTraversal(Object obj) {
        T t = (T) super.findViewWithTagTraversal(obj);
        if (t != null) {
            return t;
        }
        T t2 = (T) findViewWithTagInHeadersOrFooters(this.mHeaderViewInfos, obj);
        return t2 != null ? t2 : (T) findViewWithTagInHeadersOrFooters(this.mFooterViewInfos, obj);
    }

    View findViewWithTagInHeadersOrFooters(ArrayList<FixedViewInfo> arrayList, Object obj) {
        View findViewWithTag;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            View view = arrayList.get(i).view;
            if (!view.isRootNamespace() && (findViewWithTag = view.findViewWithTag(obj)) != null) {
                return findViewWithTag;
            }
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected <T extends View> T findViewByPredicateTraversal(Predicate<View> predicate, View view) {
        T t = (T) super.findViewByPredicateTraversal(predicate, view);
        if (t != null) {
            return t;
        }
        T t2 = (T) findViewByPredicateInHeadersOrFooters(this.mHeaderViewInfos, predicate, view);
        return t2 != null ? t2 : (T) findViewByPredicateInHeadersOrFooters(this.mFooterViewInfos, predicate, view);
    }

    View findViewByPredicateInHeadersOrFooters(ArrayList<FixedViewInfo> arrayList, Predicate<View> predicate, View view) {
        View findViewByPredicate;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            View view2 = arrayList.get(i).view;
            if (view2 != view && !view2.isRootNamespace() && (findViewByPredicate = view2.findViewByPredicate(predicate)) != null) {
                return findViewByPredicate;
            }
        }
        return null;
    }

    @Override // android.widget.SemHorizontalAbsListView
    int getWidthForPosition(int i) {
        int widthForPosition = super.getWidthForPosition(i);
        return shouldAdjustWidthForDivider(i) ? widthForPosition + this.mDividerHeight : widthForPosition;
    }

    private boolean shouldAdjustWidthForDivider(int i) {
        int i2 = this.mDividerHeight;
        Drawable drawable = this.mOverScrollHeader;
        Drawable drawable2 = this.mOverScrollFooter;
        int i3 = drawable != null ? 1 : 0;
        boolean z = drawable2 != null;
        if (i2 > 0 && this.mDivider != null) {
            boolean z2 = isOpaque() && !super.isOpaque();
            int i4 = this.mItemCount;
            int size = this.mHeaderViewInfos.size();
            int size2 = i4 - this.mFooterViewInfos.size();
            boolean z3 = i < size;
            boolean z4 = i >= size2;
            boolean z5 = this.mHeaderDividersEnabled;
            boolean z6 = this.mFooterDividersEnabled;
            if ((z5 || !z3) && (z6 || !z4)) {
                ListAdapter listAdapter = this.mAdapter;
                if (this.mStackFromBottom) {
                    boolean z7 = i == i3;
                    if (!z7) {
                        int i5 = i - 1;
                        if ((listAdapter.isEnabled(i) && ((z5 || (!z3 && i5 >= size)) && (z7 || (listAdapter.isEnabled(i5) && (z6 || (!z4 && i5 < size2)))))) || z2) {
                            return true;
                        }
                    }
                } else {
                    boolean z8 = i == i4 - 1;
                    if (!z || !z8) {
                        int i6 = i + 1;
                        if ((listAdapter.isEnabled(i) && ((z5 || (!z3 && i6 >= size)) && (z8 || (listAdapter.isEnabled(i6) && (z6 || (!z4 && i6 < size2)))))) || z2) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(SemHorizontalListView.class.getName());
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(SemHorizontalListView.class.getName());
        accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(1, getCount(), false, getSelectionModeForAccessibility()));
    }

    @Override // android.widget.SemHorizontalAbsListView
    @Deprecated
    public void onInitializeAccessibilityNodeInfoForItem(View view, int i, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoForItem(view, i, accessibilityNodeInfo);
        SemHorizontalAbsListView.LayoutParams layoutParams = (SemHorizontalAbsListView.LayoutParams) view.getLayoutParams();
        accessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(0, 1, i, 1, layoutParams != null && layoutParams.viewType == -2, isItemChecked(i)));
    }

    private boolean needToMeasureChild(View view, boolean z, boolean z2) {
        if (!z2 || z) {
            return true;
        }
        boolean isLayoutRequested = view.isLayoutRequested();
        if (!this.mFixedSizeItems) {
            return isLayoutRequested;
        }
        Object tag = view.getTag(268435456);
        if (tag == null) {
            tag = new ItemInfoTag(this);
            view.setTag(268435456, tag);
        }
        if (tag instanceof ItemInfoTag) {
            ItemInfoTag itemInfoTag = (ItemInfoTag) tag;
            int childWidthSpec = getChildWidthSpec(view);
            int childHeightSpec = getChildHeightSpec(view);
            byte[] bArr = {0};
            long childCountAndOrder = getChildCountAndOrder(view, bArr, 8);
            byte b = bArr[0];
            Configuration configuration = view.getContext().getResources().getConfiguration();
            if (childWidthSpec == itemInfoTag.mWidthSpec && childHeightSpec == itemInfoTag.mHeightSpec && childCountAndOrder == itemInfoTag.mChildrenVisibilityBitsGone && b > 0 && b <= 64 && b == itemInfoTag.mChildrenNumberTotal && configuration.compareTo(itemInfoTag.mConfiguration) == 0 && this.mLastScrollState == 2) {
                return false;
            }
            itemInfoTag.mWidthSpec = childWidthSpec;
            itemInfoTag.mHeightSpec = childHeightSpec;
            itemInfoTag.mChildrenVisibilityBitsGone = childCountAndOrder;
            itemInfoTag.mChildrenNumberTotal = b;
            itemInfoTag.mConfiguration.setTo(configuration);
            view.forceLayout();
            return true;
        }
        view.forceLayout();
        return true;
    }

    private long getChildCountAndOrder(View view, byte[] bArr, int i) {
        long j;
        if (view == null) {
            return 0L;
        }
        if (!(view instanceof ViewGroup)) {
            j = view.getVisibility() == i ? 1 << bArr[0] : 0L;
            bArr[0] = (byte) (bArr[0] + 1);
            return j;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        j = viewGroup.getVisibility() == i ? 1 << bArr[0] : 0L;
        bArr[0] = (byte) (bArr[0] + 1);
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            j |= getChildCountAndOrder(viewGroup.getChildAt(i2), bArr, i);
        }
        return j;
    }

    private int getChildHeightSpec(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -2);
        }
        return ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mListPadding.top + this.mListPadding.bottom, layoutParams.height);
    }

    private int getChildWidthSpec(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -2);
        }
        int i = layoutParams.width;
        if (i > 0) {
            return View.MeasureSpec.makeMeasureSpec(i, 1073741824);
        }
        return View.MeasureSpec.makeMeasureSpec(0, 0);
    }

    private class ItemInfoTag {
        protected Configuration mConfiguration;
        protected int mHeightSpec;
        protected int mWidthSpec;
        protected long mChildrenVisibilityBitsGone = 0;
        protected int mChildrenNumberTotal = -1;

        public ItemInfoTag(SemHorizontalListView semHorizontalListView) {
            Configuration configuration = new Configuration();
            this.mConfiguration = configuration;
            configuration.setToDefaults();
            this.mWidthSpec = -1;
            this.mHeightSpec = -1;
        }
    }
}

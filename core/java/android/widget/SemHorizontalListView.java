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

import com.android.internal.R;

import com.google.android.collect.Lists;
import com.samsung.android.animation.SemAbsDragAndDropAnimator;
import com.samsung.android.animation.SemAddDeleteHorizontalListAnimator;
import com.samsung.android.animation.SemDragAndDropHorizontalListAnimator;
import com.samsung.android.widget.SemHorizontalHeaderViewListAdapter;

import java.util.ArrayList;
import java.util.function.Predicate;

@RemoteViews.RemoteView
public class SemHorizontalListView extends SemHorizontalAbsListView {
    private static final int BITS_PER_LONG = 64;
    private static final float MAX_SCROLL_FACTOR = 0.33f;
    private static final int MIN_SCROLL_PREVIEW_PIXELS = 2;
    static final int NO_POSITION = -1;
    private static final String TAG = "SemHorizontalListView";
    private static final String XML_FIXED_SIZE_ITEMS_ATTRIBUTE = "fixed_size_items";
    private static final String XML_SEC_ANDROID_NAMESPACE =
            "http://schemas.android.samsung.com.samsung.android";
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

    @Deprecated
    public class FixedViewInfo {

        @Deprecated public Object data;

        @Deprecated public boolean isSelectable;

        @Deprecated public View view;

        public FixedViewInfo() {}
    }

    public void setAddDeleteListAnimator(SemAddDeleteHorizontalListAnimator animator) {
        this.mAddDeleteListAnimator = animator;
    }

    public void setDndListAnimator(SemDragAndDropHorizontalListAnimator animator) {
        this.mDndListAnimator = animator;
        setChildrenDrawingOrderEnabled(true);
        this.mDndListAnimator.setAutoScrollListener(
                new SemAbsDragAndDropAnimator
                        .SemDragAutoScrollListener() { // from class:
                                                       // android.widget.SemHorizontalListView.1
                    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.SemDragAutoScrollListener
                    public void onAutoScroll(int delta) {
                        SemHorizontalListView.this.trackMotionScroll(delta, delta);
                    }
                });
    }

    @Deprecated
    public SemHorizontalListView(Context context) {
        this(context, null);
    }

    @Deprecated
    public SemHorizontalListView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842868);
    }

    @Deprecated
    public SemHorizontalListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @Deprecated
    public SemHorizontalListView(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mHeaderViewInfos = Lists.newArrayList();
        this.mFooterViewInfos = Lists.newArrayList();
        this.mAreAllItemsSelectable = true;
        this.mItemsCanFocus = false;
        this.mTempRect = new Rect();
        this.mIsFolderTypeFeature = false;
        this.mArrowScrollFocusResult = new ArrowScrollFocusResult();
        TypedArray a =
                context.obtainStyledAttributes(
                        attrs, R.styleable.ListView, defStyleAttr, defStyleRes);
        CharSequence[] entries = a.getTextArray(0);
        if (entries != null) {
            setAdapter((ListAdapter) new ArrayAdapter(context, 17367043, entries));
        }
        Drawable d = a.getDrawable(1);
        if (d != null) {
            setDivider(d);
        }
        Drawable osHeader = a.getDrawable(5);
        if (osHeader != null) {
            setOverscrollHeader(osHeader);
        }
        Drawable osFooter = a.getDrawable(6);
        if (osFooter != null) {
            setOverscrollFooter(osFooter);
        }
        int dividerHeight = a.getDimensionPixelSize(2, 0);
        if (dividerHeight != 0) {
            setDividerHeight(dividerHeight);
        }
        this.mHeaderDividersEnabled = a.getBoolean(3, true);
        this.mFooterDividersEnabled = a.getBoolean(4, true);
        a.recycle();
        if (attrs != null) {
            this.mFixedSizeItems =
                    attrs.getAttributeBooleanValue(
                            XML_SEC_ANDROID_NAMESPACE, XML_FIXED_SIZE_ITEMS_ATTRIBUTE, false);
        } else {
            this.mFixedSizeItems = false;
        }
    }

    @Deprecated
    public int getMaxScrollAmount() {
        return (int) ((this.mRight - this.mLeft) * MAX_SCROLL_FACTOR);
    }

    private void adjustViewsLeftOrRight() {
        int delta;
        int childCount = getChildCount();
        if (childCount > 0) {
            if (!this.mStackFromBottom) {
                View child = getChildAt(0);
                if (this.mIsRTL) {
                    delta = child.getRight() - (getWidth() - this.mListPadding.right);
                } else {
                    int delta2 = child.getLeft();
                    delta = delta2 - this.mListPadding.left;
                }
                if (this.mFirstPosition != 0) {
                    if (this.mIsRTL) {
                        delta += this.mDividerHeight;
                    } else {
                        delta -= this.mDividerHeight;
                    }
                }
                if (this.mIsRTL) {
                    if (delta > 0) {
                        delta = 0;
                    }
                } else if (delta < 0) {
                    delta = 0;
                }
            } else {
                View child2 = getChildAt(childCount - 1);
                if (this.mIsRTL) {
                    delta = child2.getLeft() - this.mListPadding.left;
                } else {
                    int delta3 = child2.getRight();
                    delta = delta3 - (getWidth() - this.mListPadding.right);
                }
                if (this.mFirstPosition + childCount < this.mItemCount) {
                    if (this.mIsRTL) {
                        delta -= this.mDividerHeight;
                    } else {
                        delta += this.mDividerHeight;
                    }
                }
                if (this.mIsRTL) {
                    if (delta < 0) {
                        delta = 0;
                    }
                } else if (delta > 0) {
                    delta = 0;
                }
            }
            if (delta != 0) {
                semOffsetChildrenLeftAndRight(-delta);
            }
        }
    }

    @Deprecated
    public void addHeaderView(View v, Object data, boolean isSelectable) {
        FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        this.mHeaderViewInfos.add(info);
        this.mAreAllItemsSelectable &= isSelectable;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof SemHorizontalHeaderViewListAdapter)) {
                this.mAdapter =
                        new SemHorizontalHeaderViewListAdapter(
                                this.mHeaderViewInfos, this.mFooterViewInfos, this.mAdapter);
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    @Deprecated
    public void addHeaderView(View v) {
        addHeaderView(v, null, true);
    }

    @Override // android.widget.SemHorizontalAbsListView
    @Deprecated
    public int getHeaderViewsCount() {
        return this.mHeaderViewInfos.size();
    }

    @Deprecated
    public boolean removeHeaderView(View v) {
        if (this.mHeaderViewInfos.size() > 0) {
            boolean result = false;
            if (this.mAdapter != null
                    && ((SemHorizontalHeaderViewListAdapter) this.mAdapter).removeHeader(v)) {
                if (this.mDataSetObserver != null) {
                    this.mDataSetObserver.onChanged();
                }
                result = true;
            }
            removeFixedViewInfo(v, this.mHeaderViewInfos);
            return result;
        }
        return false;
    }

    private void removeFixedViewInfo(View v, ArrayList<FixedViewInfo> where) {
        int len = where.size();
        for (int i = 0; i < len; i++) {
            FixedViewInfo info = where.get(i);
            if (info.view == v) {
                where.remove(i);
                return;
            }
        }
    }

    @Deprecated
    public void addFooterView(View v, Object data, boolean isSelectable) {
        FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        this.mFooterViewInfos.add(info);
        this.mAreAllItemsSelectable &= isSelectable;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof SemHorizontalHeaderViewListAdapter)) {
                this.mAdapter =
                        new SemHorizontalHeaderViewListAdapter(
                                this.mHeaderViewInfos, this.mFooterViewInfos, this.mAdapter);
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    @Deprecated
    public void addFooterView(View v) {
        addFooterView(v, null, true);
    }

    @Override // android.widget.SemHorizontalAbsListView
    @Deprecated
    public int getFooterViewsCount() {
        return this.mFooterViewInfos.size();
    }

    @Deprecated
    public boolean removeFooterView(View v) {
        if (this.mFooterViewInfos.size() > 0) {
            boolean result = false;
            if (this.mAdapter != null
                    && ((SemHorizontalHeaderViewListAdapter) this.mAdapter).removeFooter(v)) {
                if (this.mDataSetObserver != null) {
                    this.mDataSetObserver.onChanged();
                }
                result = true;
            }
            removeFixedViewInfo(v, this.mFooterViewInfos);
            return result;
        }
        return false;
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
    public void setAdapter(ListAdapter adapter) {
        int position;
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        resetList();
        this.mRecycler.clear();
        if (this.mHeaderViewInfos.size() > 0 || this.mFooterViewInfos.size() > 0) {
            this.mAdapter =
                    new SemHorizontalHeaderViewListAdapter(
                            this.mHeaderViewInfos, this.mFooterViewInfos, adapter);
        } else {
            this.mAdapter = adapter;
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
                    position = lookForSelectablePosition(this.mItemCount - 1, true);
                } else {
                    int position2 = this.mItemCount;
                    position = lookForSelectablePosition(position2 - 1, false);
                }
            } else if (this.mIsRTL) {
                position = lookForSelectablePosition(0, false);
            } else {
                position = lookForSelectablePosition(0, true);
            }
            setSelectedPositionInt(position);
            setNextSelectedPositionInt(position);
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

    private void clearRecycledState(ArrayList<FixedViewInfo> infos) {
        if (infos != null) {
            int count = infos.size();
            for (int i = 0; i < count; i++) {
                View child = infos.get(i).view;
                SemHorizontalAbsListView.LayoutParams p =
                        (SemHorizontalAbsListView.LayoutParams) child.getLayoutParams();
                if (p != null) {
                    p.recycledHeaderFooter = false;
                }
            }
        }
    }

    private boolean showingLeftFadingEdge() {
        int listLeft = this.mScrollX + this.mListPadding.left;
        int childCount = getChildCount();
        int rightOfRightChild = getChildAt(childCount - 1).getLeft();
        int lastVisiblePosition = (this.mFirstPosition + childCount) - 1;
        return this.mIsRTL
                ? lastVisiblePosition < this.mItemCount - 1 || rightOfRightChild < listLeft
                : this.mFirstPosition > 0 || getChildAt(0).getLeft() > listLeft;
    }

    private boolean showingRightFadingEdge() {
        int childCount = getChildCount();
        int rightOfRightChild = getChildAt(childCount - 1).getRight();
        int lastVisiblePosition = (this.mFirstPosition + childCount) - 1;
        int listRight = (this.mScrollX + getWidth()) - this.mListPadding.right;
        return this.mIsRTL
                ? this.mFirstPosition > 0 || getChildAt(0).getRight() < listRight
                : lastVisiblePosition < this.mItemCount - 1 || rightOfRightChild < listRight;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    @Deprecated
    public boolean requestChildRectangleOnScreen(View child, Rect rect, boolean immediate) {
        int rectLeftWithinChild = rect.left;
        rect.offset(child.getLeft(), child.getTop());
        rect.offset(-child.getScrollX(), -child.getScrollY());
        int width = getWidth();
        int listUnfadedLeft = getScrollX();
        int listUnfadedRight = listUnfadedLeft + width;
        int fadingEdge = getHorizontalFadingEdgeLength();
        if (showingLeftFadingEdge()
                && (this.mSelectedPosition > 0 || rectLeftWithinChild > fadingEdge)) {
            listUnfadedLeft += fadingEdge;
        }
        int childCount = getChildCount();
        int rightOfRightChild = getChildAt(childCount - 1).getRight();
        if (showingRightFadingEdge()
                && (this.mSelectedPosition < this.mItemCount - 1
                        || rect.right < rightOfRightChild - fadingEdge)) {
            listUnfadedRight -= fadingEdge;
        }
        int scrollXDelta = 0;
        if (rect.right > listUnfadedRight && rect.left > listUnfadedLeft) {
            int distanceToRight = rightOfRightChild - listUnfadedRight;
            scrollXDelta =
                    Math.min(
                            rect.width() > width
                                    ? 0 + (rect.left - listUnfadedLeft)
                                    : 0 + (rect.right - listUnfadedRight),
                            distanceToRight);
        } else if (rect.left < listUnfadedLeft && rect.right < listUnfadedRight) {
            int scrollXDelta2 =
                    rect.width() > width
                            ? 0 - (listUnfadedRight - rect.right)
                            : 0 - (listUnfadedLeft - rect.left);
            int left = getChildAt(0).getLeft();
            int deltaToLeft = left - listUnfadedLeft;
            scrollXDelta = Math.max(scrollXDelta2, deltaToLeft);
        }
        boolean scroll = scrollXDelta != 0;
        if (scroll) {
            scrollListItemsBy(-scrollXDelta);
            positionSelector(-1, child);
            this.mSelectedLeft = child.getLeft();
            invalidate();
        }
        return scroll;
    }

    @Override // android.widget.SemHorizontalAbsListView
    void fillGap(boolean rightSide) {
        int count = getChildCount();
        if (rightSide) {
            int paddingLeft = 0;
            if ((this.mGroupFlags & 34) == 34) {
                paddingLeft = getListPaddingLeft();
            }
            int startOffset =
                    count > 0
                            ? getChildAt(count - 1).getRight() + this.mDividerHeight
                            : paddingLeft;
            fillRight(this.mFirstPosition + count, startOffset);
            correctTooHigh(getChildCount());
            return;
        }
        int paddingRight = 0;
        if ((this.mGroupFlags & 34) == 34) {
            paddingRight = getListPaddingRight();
        }
        int startOffset2 =
                count > 0
                        ? getChildAt(0).getLeft() - this.mDividerHeight
                        : getWidth() - paddingRight;
        fillLeft(this.mFirstPosition - 1, startOffset2);
        correctTooLow(getChildCount());
    }

    @Override // android.widget.SemHorizontalAbsListView
    void fillGapRTL(boolean rightSide) {
        int count = getChildCount();
        if (rightSide) {
            int paddingLeft = 0;
            if ((this.mGroupFlags & 34) == 34) {
                paddingLeft = getListPaddingLeft();
            }
            int startOffset =
                    count > 0 ? getChildAt(0).getRight() + this.mDividerHeight : paddingLeft;
            fillRightRTL(this.mFirstPosition - 1, startOffset);
            correctTooLowRTL(getChildCount());
            return;
        }
        int paddingRight = 0;
        if ((this.mGroupFlags & 34) == 34) {
            paddingRight = getListPaddingRight();
        }
        int startOffset2 =
                count > 0
                        ? getChildAt(count - 1).getLeft() - this.mDividerHeight
                        : getWidth() - paddingRight;
        fillLeftRTL(this.mFirstPosition + count, startOffset2);
        correctTooHighRTL(getChildCount());
    }

    private View fillRight(int pos, int nextLeft) {
        View selectedView = null;
        int end = this.mRight - this.mLeft;
        if ((this.mGroupFlags & 34) == 34) {
            end -= this.mListPadding.right;
        }
        while (true) {
            if (nextLeft >= end || pos >= this.mItemCount) {
                break;
            }
            boolean selected = pos == this.mSelectedPosition;
            View child = makeAndAddView(pos, nextLeft, true, this.mListPadding.top, selected);
            if (child != null) {
                int nextLeft2 = child.getRight() + this.mDividerHeight;
                if (!selected) {
                    nextLeft = nextLeft2;
                } else {
                    selectedView = child;
                    nextLeft = nextLeft2;
                }
            }
            pos++;
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return selectedView;
    }

    private View fillRightRTL(int pos, int nextLeft) {
        View selectedView = null;
        int end = this.mRight - this.mLeft;
        if ((this.mGroupFlags & 34) == 34) {
            end -= this.mListPadding.right;
        }
        while (true) {
            if (nextLeft >= end || pos < 0) {
                break;
            }
            boolean selected = pos == this.mSelectedPosition;
            View child = makeAndAddView(pos, nextLeft, true, this.mListPadding.top, selected);
            if (child != null) {
                int nextLeft2 = child.getRight() + this.mDividerHeight;
                if (!selected) {
                    nextLeft = nextLeft2;
                } else {
                    selectedView = child;
                    nextLeft = nextLeft2;
                }
            }
            pos--;
        }
        this.mFirstPosition = pos + 1;
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return selectedView;
    }

    private View fillLeft(int pos, int nextRight) {
        View selectedView = null;
        int end = 0;
        if ((this.mGroupFlags & 34) == 34) {
            end = this.mListPadding.left;
        }
        while (true) {
            if (nextRight <= end || pos < 0) {
                break;
            }
            boolean selected = pos == this.mSelectedPosition;
            View child = makeAndAddView(pos, nextRight, false, this.mListPadding.top, selected);
            if (child != null) {
                int nextRight2 = child.getLeft() - this.mDividerHeight;
                if (!selected) {
                    nextRight = nextRight2;
                } else {
                    selectedView = child;
                    nextRight = nextRight2;
                }
            }
            pos--;
        }
        this.mFirstPosition = pos + 1;
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return selectedView;
    }

    private View fillLeftRTL(int pos, int nextRight) {
        View selectedView = null;
        int end = 0;
        if ((this.mGroupFlags & 34) == 34) {
            end = this.mListPadding.left;
        }
        while (true) {
            if (nextRight <= end || pos >= this.mItemCount) {
                break;
            }
            boolean selected = pos == this.mSelectedPosition;
            View child = makeAndAddView(pos, nextRight, false, this.mListPadding.top, selected);
            if (child != null) {
                int nextRight2 = child.getLeft() - this.mDividerHeight;
                if (!selected) {
                    nextRight = nextRight2;
                } else {
                    selectedView = child;
                    nextRight = nextRight2;
                }
            }
            pos++;
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return selectedView;
    }

    private View fillFromLeft(int nextLeft) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return fillRight(this.mFirstPosition, nextLeft);
    }

    private View fillFromRight(int nextRight) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return fillLeftRTL(this.mFirstPosition, nextRight);
    }

    private View fillFromMiddle(int childrenLeft, int childrenRight) {
        int width = childrenRight - childrenLeft;
        int position = reconcileSelectedPosition();
        View sel = makeAndAddView(position, childrenLeft, true, this.mListPadding.top, true);
        this.mFirstPosition = position;
        int selWidth = sel.getMeasuredWidth();
        if (selWidth <= width) {
            sel.offsetLeftAndRight((width - selWidth) / 2);
        }
        fillLeftAndRight(sel, position);
        if (!this.mStackFromBottom) {
            if (this.mIsRTL) {
                correctTooHighRTL(getChildCount());
            } else {
                correctTooHigh(getChildCount());
            }
        } else if (this.mIsRTL) {
            correctTooLowRTL(getChildCount());
        } else {
            correctTooLow(getChildCount());
        }
        return sel;
    }

    private void fillLeftAndRight(View sel, int position) {
        int dividerHeight = this.mDividerHeight;
        if (this.mIsRTL) {
            if (!this.mStackFromBottom) {
                fillRightRTL(position - 1, sel.getRight() + dividerHeight);
                adjustViewsLeftOrRight();
                fillLeftRTL(position + 1, sel.getLeft() - dividerHeight);
                return;
            } else {
                fillLeftRTL(position + 1, sel.getLeft() - dividerHeight);
                adjustViewsLeftOrRight();
                fillRightRTL(position - 1, sel.getRight() + dividerHeight);
                return;
            }
        }
        if (!this.mStackFromBottom) {
            fillLeft(position - 1, sel.getLeft() - dividerHeight);
            adjustViewsLeftOrRight();
            fillRight(position + 1, sel.getRight() + dividerHeight);
        } else {
            fillRight(position + 1, sel.getRight() + dividerHeight);
            adjustViewsLeftOrRight();
            fillLeft(position - 1, sel.getLeft() - dividerHeight);
        }
    }

    private View fillFromSelection(int selectedLeft, int childrenLeft, int childrenRight) {
        int fadingEdgeLength = getHorizontalFadingEdgeLength();
        int selectedPosition = this.mSelectedPosition;
        int leftSelectionPixel =
                getLeftSelectionPixel(childrenLeft, fadingEdgeLength, selectedPosition);
        int rightSelectionPixel =
                getRightSelectionPixel(childrenRight, fadingEdgeLength, selectedPosition);
        View sel =
                makeAndAddView(selectedPosition, selectedLeft, true, this.mListPadding.top, true);
        if (sel.getRight() > rightSelectionPixel) {
            int spaceLeft = sel.getLeft() - leftSelectionPixel;
            int spaceRight = sel.getRight() - rightSelectionPixel;
            int offset = Math.min(spaceLeft, spaceRight);
            sel.offsetLeftAndRight(-offset);
        } else if (sel.getLeft() < leftSelectionPixel) {
            int spaceLeft2 = leftSelectionPixel - sel.getLeft();
            int spaceRight2 = rightSelectionPixel - sel.getRight();
            int offset2 = Math.min(spaceLeft2, spaceRight2);
            sel.offsetLeftAndRight(offset2);
        }
        fillLeftAndRight(sel, selectedPosition);
        if (!this.mStackFromBottom) {
            if (this.mIsRTL) {
                correctTooLowRTL(getChildCount());
            } else {
                correctTooHigh(getChildCount());
            }
        } else if (this.mIsRTL) {
            correctTooHighRTL(getChildCount());
        } else {
            correctTooLow(getChildCount());
        }
        return sel;
    }

    private int getRightSelectionPixel(
            int childrenRight, int fadingEdgeLength, int selectedPosition) {
        if (this.mIsRTL) {
            if (selectedPosition <= 0) {
                return childrenRight;
            }
        } else if (selectedPosition == this.mItemCount - 1) {
            return childrenRight;
        }
        int rightSelectionPixel = childrenRight - fadingEdgeLength;
        return rightSelectionPixel;
    }

    private int getLeftSelectionPixel(
            int childrenLeft, int fadingEdgeLength, int selectedPosition) {
        if (this.mIsRTL) {
            if (selectedPosition == this.mItemCount - 1) {
                return childrenLeft;
            }
        } else if (selectedPosition <= 0) {
            return childrenLeft;
        }
        int leftSelectionPixel = childrenLeft + fadingEdgeLength;
        return leftSelectionPixel;
    }

    @Override // android.widget.SemHorizontalAbsListView
    @RemotableViewMethod
    @Deprecated
    public void smoothScrollToPosition(int position) {
        super.smoothScrollToPosition(position);
    }

    @Override // android.widget.SemHorizontalAbsListView
    @RemotableViewMethod
    @Deprecated
    public void smoothScrollByOffset(int offset) {
        super.smoothScrollByOffset(offset);
    }

    private View moveSelection(
            View oldSel, View newSel, int delta, int childrenLeft, int childrenRight) {
        View sel;
        int fadingEdgeLength = getHorizontalFadingEdgeLength();
        int selectedPosition = this.mSelectedPosition;
        int leftSelectionPixel =
                getLeftSelectionPixel(childrenLeft, fadingEdgeLength, selectedPosition);
        int rightSelectionPixel =
                getRightSelectionPixel(childrenLeft, fadingEdgeLength, selectedPosition);
        if (delta > 0) {
            View oldSel2 =
                    makeAndAddView(
                            selectedPosition - 1,
                            oldSel.getLeft(),
                            true,
                            this.mListPadding.top,
                            false);
            int dividerHeight = this.mDividerHeight;
            sel =
                    makeAndAddView(
                            selectedPosition,
                            oldSel2.getRight() + dividerHeight,
                            true,
                            this.mListPadding.top,
                            true);
            if (sel.getRight() > rightSelectionPixel) {
                int spaceLeft = sel.getLeft() - leftSelectionPixel;
                int spaceRight = sel.getRight() - rightSelectionPixel;
                int halfHorizontalSpace = (childrenRight - childrenLeft) / 2;
                int offset = Math.min(spaceLeft, spaceRight);
                int offset2 = Math.min(offset, halfHorizontalSpace);
                oldSel2.offsetLeftAndRight(-offset2);
                sel.offsetLeftAndRight(-offset2);
            }
            if (!this.mStackFromBottom) {
                fillLeft(this.mSelectedPosition - 2, sel.getLeft() - dividerHeight);
                adjustViewsLeftOrRight();
                fillRight(this.mSelectedPosition + 1, sel.getRight() + dividerHeight);
            } else {
                fillRight(this.mSelectedPosition + 1, sel.getRight() + dividerHeight);
                adjustViewsLeftOrRight();
                fillLeft(this.mSelectedPosition - 2, sel.getLeft() - dividerHeight);
            }
        } else if (delta < 0) {
            sel =
                    newSel != null
                            ? makeAndAddView(
                                    selectedPosition,
                                    newSel.getLeft(),
                                    true,
                                    this.mListPadding.top,
                                    true)
                            : makeAndAddView(
                                    selectedPosition,
                                    oldSel.getLeft(),
                                    false,
                                    this.mListPadding.top,
                                    true);
            if (sel.getLeft() < leftSelectionPixel) {
                int spaceLeft2 = leftSelectionPixel - sel.getLeft();
                int spaceRight2 = rightSelectionPixel - sel.getRight();
                int halfHorizontalSpace2 = (childrenRight - childrenLeft) / 2;
                int offset3 = Math.min(spaceLeft2, spaceRight2);
                sel.offsetLeftAndRight(Math.min(offset3, halfHorizontalSpace2));
            }
            fillLeftAndRight(sel, selectedPosition);
        } else {
            int oldLeft = oldSel.getLeft();
            sel = makeAndAddView(selectedPosition, oldLeft, true, this.mListPadding.top, true);
            if (oldLeft < childrenLeft) {
                int newRight = sel.getRight();
                if (newRight < childrenLeft + 20) {
                    sel.offsetLeftAndRight(childrenLeft - sel.getLeft());
                }
            }
            fillLeftAndRight(sel, selectedPosition);
        }
        return sel;
    }

    private class FocusSelector implements Runnable {
        private int mPosition;
        private int mPositionLeft;

        private FocusSelector() {}

        public FocusSelector setup(int position, int left) {
            this.mPosition = position;
            this.mPositionLeft = left;
            return this;
        }

        @Override // java.lang.Runnable
        public void run() {
            SemHorizontalListView.this.setSelectionFromStart(this.mPosition, this.mPositionLeft);
        }
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        View focusedChild;
        int left;
        if (getChildCount() > 0 && (focusedChild = getFocusedChild()) != null) {
            int childPosition = this.mFirstPosition + indexOfChild(focusedChild);
            int childRight = focusedChild.getRight();
            int offset = Math.max(0, childRight - (w - this.mPaddingLeft));
            if (this.mIsRTL) {
                left = focusedChild.getRight();
            } else {
                int left2 = focusedChild.getLeft();
                left = left2 - offset;
            }
            if (this.mFocusSelector == null) {
                this.mFocusSelector = new FocusSelector();
            }
            post(this.mFocusSelector.setup(childPosition, left));
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize;
        int widthSize;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthSize2 = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = 0;
        int childHeight = 0;
        int childState = 0;
        this.mItemCount = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        if (this.mItemCount > 0 && (widthMode == 0 || heightMode == 0)) {
            View child = obtainView(0, this.mIsScrap);
            measureScrapChild(child, 0, heightMeasureSpec);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            childState = combineMeasuredStates(0, child.getMeasuredState());
            if (recycleOnMeasure()
                    && this.mRecycler.shouldRecycleViewType(
                            ((SemHorizontalAbsListView.LayoutParams) child.getLayoutParams())
                                    .viewType)) {
                this.mRecycler.addScrapView(child, -1);
            }
        }
        int childWidth2 = childWidth;
        int childHeight2 = childHeight;
        int childState2 = childState;
        if (heightMode == 0) {
            heightSize =
                    this.mListPadding.top
                            + this.mListPadding.bottom
                            + childHeight2
                            + getHorizontalScrollbarHeight();
        } else {
            heightSize = ((-16777216) & childState2) | heightSize2;
        }
        if (widthMode != 0) {
            widthSize = widthSize2;
        } else {
            int widthSize3 =
                    this.mListPadding.left
                            + this.mListPadding.right
                            + childWidth2
                            + (getHorizontalFadingEdgeLength() * 2);
            widthSize = widthSize3;
        }
        if (widthMode == Integer.MIN_VALUE) {
            widthSize = measureWidthOfChildren(heightMeasureSpec, 0, -1, widthSize, -1);
        }
        setMeasuredDimension(widthSize, heightSize);
        this.mHeightMeasureSpec = heightMeasureSpec;
    }

    private void measureScrapChild(View child, int position, int heightMeasureSpec) {
        int childWidthSpec;
        SemHorizontalAbsListView.LayoutParams p =
                (SemHorizontalAbsListView.LayoutParams) child.getLayoutParams();
        if (p == null) {
            p = (SemHorizontalAbsListView.LayoutParams) generateDefaultLayoutParams();
            child.setLayoutParams(p);
        }
        p.viewType = this.mAdapter.getItemViewType(position);
        p.forceAdd = true;
        int childHeightSpec =
                ViewGroup.getChildMeasureSpec(
                        heightMeasureSpec,
                        this.mListPadding.top + this.mListPadding.bottom,
                        p.height);
        int lpWidth = p.width;
        if (lpWidth > 0) {
            childWidthSpec = View.MeasureSpec.makeMeasureSpec(lpWidth, 1073741824);
        } else {
            childWidthSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @ViewDebug.ExportedProperty(category = Slice.HINT_LIST)
    protected boolean recycleOnMeasure() {
        return true;
    }

    final int measureWidthOfChildren(
            int heightMeasureSpec,
            int startPosition,
            int endPosition,
            int maxWidth,
            int disallowPartialChildPosition) {
        ListAdapter adapter = this.mAdapter;
        if (adapter == null) {
            return this.mListPadding.left + this.mListPadding.right;
        }
        int returnedWidth = this.mListPadding.left + this.mListPadding.right;
        int dividerHeight =
                (this.mDividerHeight <= 0 || this.mDivider == null) ? 0 : this.mDividerHeight;
        int prevWidthWithoutPartialChild = 0;
        int endPosition2 = endPosition == -1 ? adapter.getCount() - 1 : endPosition;
        SemHorizontalAbsListView.RecycleBin recycleBin = this.mRecycler;
        boolean recyle = recycleOnMeasure();
        boolean[] isScrap = this.mIsScrap;
        for (int i = startPosition; i <= endPosition2; i++) {
            View child = obtainView(i, isScrap);
            measureScrapChild(child, i, heightMeasureSpec);
            if (i > 0) {
                if (this.mIsRTL) {
                    returnedWidth -= dividerHeight;
                } else {
                    returnedWidth += dividerHeight;
                }
            }
            if (recyle
                    && recycleBin.shouldRecycleViewType(
                            ((SemHorizontalAbsListView.LayoutParams) child.getLayoutParams())
                                    .viewType)) {
                recycleBin.addScrapView(child, -1);
            }
            returnedWidth += child.getMeasuredWidth();
            if (returnedWidth >= maxWidth) {
                if (disallowPartialChildPosition >= 0
                        && i > disallowPartialChildPosition
                        && prevWidthWithoutPartialChild > 0
                        && returnedWidth != maxWidth) {
                    return prevWidthWithoutPartialChild;
                }
                return maxWidth;
            }
            if (disallowPartialChildPosition >= 0 && i >= disallowPartialChildPosition) {
                prevWidthWithoutPartialChild = returnedWidth;
            }
        }
        return returnedWidth;
    }

    @Override // android.widget.SemHorizontalAbsListView
    int findMotionRow(int x) {
        int childCount = getChildCount();
        if (childCount > 0) {
            if (this.mIsRTL) {
                if (!this.mStackFromBottom) {
                    for (int i = 0; i < childCount; i++) {
                        View v = getChildAt(i);
                        if (x >= v.getLeft()) {
                            return this.mFirstPosition + i;
                        }
                    }
                    return -1;
                }
                for (int i2 = childCount - 1; i2 >= 0; i2--) {
                    View v2 = getChildAt(i2);
                    if (x <= v2.getRight()) {
                        return this.mFirstPosition + i2;
                    }
                }
                return -1;
            }
            if (!this.mStackFromBottom) {
                for (int i3 = 0; i3 < childCount; i3++) {
                    View v3 = getChildAt(i3);
                    if (x <= v3.getRight()) {
                        return this.mFirstPosition + i3;
                    }
                }
                return -1;
            }
            for (int i4 = childCount - 1; i4 >= 0; i4--) {
                View v4 = getChildAt(i4);
                if (x >= v4.getLeft()) {
                    return this.mFirstPosition + i4;
                }
            }
            return -1;
        }
        return -1;
    }

    private View fillSpecific(int position, int left) {
        View rightSide;
        View leftSide;
        boolean tempIsSelected = position == this.mSelectedPosition;
        View temp = makeAndAddView(position, left, true, this.mListPadding.top, tempIsSelected);
        this.mFirstPosition = position;
        int dividerHeight = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            leftSide = fillLeft(position - 1, temp.getLeft() - dividerHeight);
            adjustViewsLeftOrRight();
            rightSide = fillRight(position + 1, temp.getRight() + dividerHeight);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHigh(childCount);
            }
        } else {
            rightSide = fillRight(position + 1, temp.getRight() + dividerHeight);
            adjustViewsLeftOrRight();
            leftSide = fillLeft(position - 1, temp.getLeft() - dividerHeight);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLow(childCount2);
            }
        }
        if (tempIsSelected) {
            return temp;
        }
        if (leftSide != null) {
            return leftSide;
        }
        return rightSide;
    }

    private View fillSpecificRTL(int position, int right) {
        View leftSide;
        View rightSide;
        boolean tempIsSelected = position == this.mSelectedPosition;
        View temp = makeAndAddView(position, right, false, this.mListPadding.top, tempIsSelected);
        this.mFirstPosition = position;
        int dividerHeight = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            rightSide = fillRightRTL(position - 1, temp.getRight() + dividerHeight);
            adjustViewsLeftOrRight();
            leftSide = fillLeftRTL(position + 1, temp.getLeft() - dividerHeight);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHighRTL(childCount);
            }
        } else {
            leftSide = fillLeftRTL(position + 1, temp.getLeft() - dividerHeight);
            adjustViewsLeftOrRight();
            rightSide = fillRightRTL(position - 1, temp.getRight() + dividerHeight);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLowRTL(childCount2);
            }
        }
        if (tempIsSelected) {
            return temp;
        }
        if (leftSide != null) {
            return leftSide;
        }
        return rightSide;
    }

    boolean shouldCorrectTooHigh() {
        return true;
    }

    private void correctTooHigh(int childCount) {
        int lastPosition = (this.mFirstPosition + childCount) - 1;
        if (lastPosition == this.mItemCount - 1 && childCount > 0) {
            View lastChild = getChildAt(childCount - 1);
            int lastRight = lastChild.getRight();
            int end = (this.mRight - this.mLeft) - this.mListPadding.right;
            int rightOffset = end - lastRight;
            View firstChild = getChildAt(0);
            int firstLeft = firstChild.getLeft();
            if (rightOffset > 0) {
                if (this.mFirstPosition > 0 || firstLeft < this.mListPadding.left) {
                    if (this.mFirstPosition == 0) {
                        rightOffset = Math.min(rightOffset, this.mListPadding.left - firstLeft);
                    }
                    semOffsetChildrenLeftAndRight(rightOffset);
                    if (this.mFirstPosition > 0) {
                        fillLeft(
                                this.mFirstPosition - 1,
                                firstChild.getLeft() - this.mDividerHeight);
                        adjustViewsLeftOrRight();
                    }
                }
            }
        }
    }

    private void correctTooHighRTL(int childCount) {
        int lastPosition = (this.mFirstPosition + childCount) - 1;
        if (lastPosition == this.mItemCount - 1 && childCount > 0) {
            View lastChild = getChildAt(childCount - 1);
            int lastLeft = lastChild.getLeft();
            int start = this.mListPadding.left;
            int end = (this.mRight - this.mLeft) - this.mListPadding.right;
            int leftOffset = lastLeft - start;
            View firstChild = getChildAt(0);
            int firstRight = firstChild.getRight();
            if (leftOffset > 0) {
                if (this.mFirstPosition > 0 || firstRight > end) {
                    if (this.mFirstPosition == 0) {
                        leftOffset = Math.min(leftOffset, firstRight - end);
                    }
                    semOffsetChildrenLeftAndRight(-leftOffset);
                    if (this.mFirstPosition > 0) {
                        fillRightRTL(
                                this.mFirstPosition - 1,
                                firstChild.getRight() + this.mDividerHeight);
                        adjustViewsLeftOrRight();
                    }
                }
            }
        }
    }

    private void correctTooLow(int childCount) {
        if (this.mFirstPosition == 0 && childCount > 0) {
            View firstChild = getChildAt(0);
            int firstLeft = firstChild.getLeft();
            int start = this.mListPadding.left;
            int end = (this.mRight - this.mLeft) - this.mListPadding.right;
            int leftOffset = firstLeft - start;
            View lastChild = getChildAt(childCount - 1);
            int lastRight = lastChild.getRight();
            int lastPosition = (this.mFirstPosition + childCount) - 1;
            if (leftOffset > 0) {
                if (lastPosition < this.mItemCount - 1 || lastRight > end) {
                    if (lastPosition == this.mItemCount - 1) {
                        leftOffset = Math.min(leftOffset, lastRight - end);
                    }
                    semOffsetChildrenLeftAndRight(-leftOffset);
                    if (lastPosition < this.mItemCount - 1) {
                        fillRight(lastPosition + 1, lastChild.getRight() + this.mDividerHeight);
                        adjustViewsLeftOrRight();
                        return;
                    }
                    return;
                }
                if (lastPosition == this.mItemCount - 1) {
                    adjustViewsLeftOrRight();
                }
            }
        }
    }

    private void correctTooLowRTL(int childCount) {
        if (this.mFirstPosition == 0 && childCount > 0) {
            View firstChild = getChildAt(0);
            int firstRight = firstChild.getRight();
            int start = this.mListPadding.right;
            int end = (this.mRight - this.mLeft) - start;
            int rightOffset = end - firstRight;
            View lastChild = getChildAt(childCount - 1);
            int lastLeft = lastChild.getLeft();
            int lastPosition = (this.mFirstPosition + childCount) - 1;
            if (rightOffset > 0) {
                if (lastPosition < this.mItemCount - 1 || lastLeft < this.mListPadding.left) {
                    if (lastPosition == this.mItemCount - 1) {
                        rightOffset = Math.min(rightOffset, this.mListPadding.left - lastLeft);
                    }
                    semOffsetChildrenLeftAndRight(rightOffset);
                    if (lastPosition < this.mItemCount - 1) {
                        fillLeftRTL(lastPosition + 1, lastChild.getLeft() - this.mDividerHeight);
                        adjustViewsLeftOrRight();
                        return;
                    }
                    return;
                }
                if (lastPosition == this.mItemCount - 1) {
                    adjustViewsLeftOrRight();
                }
            }
        }
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.ViewGroup
    @Deprecated
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.mDndListAnimator == null || !this.mDndListAnimator.onInterceptTouchEvent(ev)) {
            return super.onInterceptTouchEvent(ev);
        }
        return true;
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mDndListAnimator == null || !this.mDndListAnimator.onTouchEvent(ev)) {
            return super.onTouchEvent(ev);
        }
        return true;
    }

    @Override // android.view.ViewGroup
    @Deprecated
    protected int getChildDrawingOrder(int childCount, int i) {
        return this.mDndListAnimator != null
                ? this.mDndListAnimator.getChildDrawingOrder(childCount, i)
                : super.getChildDrawingOrder(childCount, i);
    }

    @Override // android.widget.SemHorizontalAbsListView
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void layoutChildren() {
        /*
            Method dump skipped, instructions count: 1210
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.widget.SemHorizontalListView.layoutChildren():void");
    }

    private boolean isDirectChildHeaderOrFooter(View child) {
        ArrayList<FixedViewInfo> headers = this.mHeaderViewInfos;
        int numHeaders = headers.size();
        for (int i = 0; i < numHeaders; i++) {
            if (child == headers.get(i).view) {
                return true;
            }
        }
        ArrayList<FixedViewInfo> footers = this.mFooterViewInfos;
        int numFooters = footers.size();
        for (int i2 = 0; i2 < numFooters; i2++) {
            if (child == footers.get(i2).view) {
                return true;
            }
        }
        return false;
    }

    private View makeAndAddView(
            int position, int x, boolean flow, int childrenTop, boolean selected) {
        View child;
        if (!this.mDataChanged && (child = this.mRecycler.getActiveView(position)) != null) {
            setupChild(child, position, x, flow, childrenTop, selected, true);
            return child;
        }
        View child2 = obtainView(position, this.mIsScrap);
        if (child2 != null) {
            setupChild(child2, position, x, flow, childrenTop, selected, this.mIsScrap[0]);
        }
        return child2;
    }

    private void setupChild(
            View view,
            int position,
            int x,
            boolean flowRightSide,
            int childrenTop,
            boolean selected,
            boolean recycled) {
        int childWidthSpec;
        Trace.traceBegin(8L, "setupListItem");
        boolean isSelected = selected && shouldShowSelector();
        boolean updateChildSelected = isSelected != view.isSelected();
        int mode = this.mTouchMode;
        boolean isPressed = mode > 0 && mode < 3 && this.mMotionPosition == position;
        boolean updateChildPressed = isPressed != view.isPressed();
        boolean needToMeasure = needToMeasureChild(view, updateChildSelected, recycled);
        SemHorizontalAbsListView.LayoutParams p =
                (SemHorizontalAbsListView.LayoutParams) view.getLayoutParams();
        if (p == null) {
            p = (SemHorizontalAbsListView.LayoutParams) generateDefaultLayoutParams();
        }
        p.viewType = this.mAdapter.getItemViewType(position);
        if ((recycled && !p.forceAdd) || (p.recycledHeaderFooter && p.viewType == -2)) {
            if (this.mIsRTL) {
                attachViewToParent(view, !flowRightSide ? -1 : 0, p);
            } else {
                attachViewToParent(view, flowRightSide ? -1 : 0, p);
            }
        } else {
            p.forceAdd = false;
            if (p.viewType == -2) {
                p.recycledHeaderFooter = true;
            }
            if (this.mIsRTL) {
                addViewInLayout(view, !flowRightSide ? -1 : 0, p, true);
            } else {
                addViewInLayout(view, flowRightSide ? -1 : 0, p, true);
            }
        }
        if (updateChildSelected) {
            view.setSelected(isSelected);
            if (isSelected && this.mIsFolderTypeFeature) {
                view.requestFocus();
            }
        }
        if (updateChildPressed) {
            view.setPressed(isPressed);
        }
        if (this.mChoiceMode != 0 && this.mCheckStates != null) {
            if (view instanceof Checkable) {
                ((Checkable) view).setChecked(this.mCheckStates.get(position));
            } else if (getContext().getApplicationInfo().targetSdkVersion >= 11) {
                view.setActivated(this.mCheckStates.get(position));
            }
        }
        if (needToMeasure) {
            int childHeightSpec =
                    ViewGroup.getChildMeasureSpec(
                            this.mHeightMeasureSpec,
                            this.mListPadding.top + this.mListPadding.bottom,
                            p.height);
            int lpWidth = p.width;
            if (lpWidth > 0) {
                childWidthSpec = View.MeasureSpec.makeMeasureSpec(lpWidth, 1073741824);
            } else {
                childWidthSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            }
            view.measure(childWidthSpec, childHeightSpec);
        } else {
            cleanupLayoutState(view);
        }
        int w = view.getMeasuredWidth();
        int h = view.getMeasuredHeight();
        if (this.mIsRTL) {
            int childRight = flowRightSide ? x + w : x;
            int childLeft = flowRightSide ? x : x - w;
            if (needToMeasure) {
                int childBottom = childrenTop + h;
                view.layout(childLeft, childrenTop, childRight, childBottom);
            } else {
                view.offsetLeftAndRight(childLeft - view.getLeft());
                view.offsetTopAndBottom(childrenTop - view.getTop());
            }
        } else {
            int childLeft2 = flowRightSide ? x : x - w;
            int childRight2 = childLeft2 + w;
            if (needToMeasure) {
                int childBottom2 = childrenTop + h;
                view.layout(childLeft2, childrenTop, childRight2, childBottom2);
            } else {
                view.offsetLeftAndRight(childLeft2 - view.getLeft());
                view.offsetTopAndBottom(childrenTop - view.getTop());
            }
        }
        if (this.mCachingStarted && !view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }
        if (recycled
                && ((SemHorizontalAbsListView.LayoutParams) view.getLayoutParams())
                                .scrappedFromPosition
                        != position) {
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
    public void setSelection(int position) {
        setSelectionFromStart(position, 0);
    }

    public void setSelectionFromTop(int position, int x) {
        if (this.mAdapter == null) {
            return;
        }
        if (!isInTouchMode()) {
            position = lookForSelectablePosition(position, true);
            if (position >= 0) {
                setNextSelectedPositionInt(position);
            }
        } else {
            this.mResurrectToPosition = position;
        }
        if (position >= 0) {
            this.mLayoutMode = 4;
            if (this.mIsRTL) {
                this.mSpecificTop = getWidth() - x;
            } else {
                this.mSpecificTop = this.mListPadding.left + x;
            }
            if (this.mNeedSync) {
                this.mSyncPosition = position;
                this.mSyncRowId = this.mAdapter.getItemId(position);
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            requestLayout();
        }
    }

    @Override // android.widget.SemHorizontalAbsListView
    @Deprecated
    public void setSelectionFromStart(int position, int x) {
        if (this.mAdapter == null) {
            return;
        }
        if (!isInTouchMode()) {
            position = lookForSelectablePosition(position, true);
            if (position >= 0) {
                setNextSelectedPositionInt(position);
            }
        } else {
            this.mResurrectToPosition = position;
        }
        if (position >= 0) {
            this.mLayoutMode = 4;
            if (this.mIsRTL) {
                this.mSpecificTop = getWidth() - x;
            } else {
                this.mSpecificTop = this.mListPadding.left + x;
            }
            if (this.mNeedSync) {
                this.mSyncPosition = position;
                this.mSyncRowId = this.mAdapter.getItemId(position);
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            requestLayout();
        }
    }

    @Override // android.widget.SemHorizontalAbsListView
    void setSelectionInt(int position) {
        setNextSelectedPositionInt(position);
        boolean awakeScrollbars = false;
        int selectedPosition = this.mSelectedPosition;
        if (selectedPosition >= 0) {
            if (position == selectedPosition - 1) {
                awakeScrollbars = true;
            } else if (position == selectedPosition + 1) {
                awakeScrollbars = true;
            }
        }
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        layoutChildren();
        if (awakeScrollbars) {
            awakenScrollBars();
        }
    }

    @Override // android.widget.AdapterView
    int lookForSelectablePosition(int position, boolean lookDown) {
        ListAdapter adapter = this.mAdapter;
        if (adapter == null || isInTouchMode()) {
            return -1;
        }
        int count = adapter.getCount();
        if (!this.mAreAllItemsSelectable) {
            if (this.mIsRTL) {
                if (lookDown) {
                    position = Math.min(position, count - 1);
                    while (position >= 0 && !adapter.isEnabled(position)) {
                        position--;
                    }
                } else {
                    position = Math.max(0, position);
                    while (position < count && !adapter.isEnabled(position)) {
                        position++;
                    }
                }
            } else if (lookDown) {
                position = Math.max(0, position);
                while (position < count && !adapter.isEnabled(position)) {
                    position++;
                }
            } else {
                position = Math.min(position, count - 1);
                while (position >= 0 && !adapter.isEnabled(position)) {
                    position--;
                }
            }
        }
        if (position < 0 || position >= count) {
            return -1;
        }
        return position;
    }

    int lookForSelectablePositionAfter(int current, int position, boolean lookDown) {
        int position2;
        ListAdapter adapter = this.mAdapter;
        if (adapter == null || isInTouchMode()) {
            return -1;
        }
        int after = lookForSelectablePosition(position, lookDown);
        if (after != -1) {
            return after;
        }
        int count = adapter.getCount();
        int current2 = MathUtils.constrain(current, -1, count - 1);
        if (this.mIsRTL) {
            if (lookDown) {
                position2 = Math.max(0, position + 1);
                while (position2 < current2 && !adapter.isEnabled(position2)) {
                    position2++;
                }
                if (position2 >= current2) {
                    return -1;
                }
            } else {
                position2 = Math.min(position - 1, count - 1);
                while (position2 > current2 && !adapter.isEnabled(position2)) {
                    position2--;
                }
                if (position2 <= current2) {
                    return -1;
                }
            }
        } else if (lookDown) {
            position2 = Math.min(position - 1, count - 1);
            while (position2 > current2 && !adapter.isEnabled(position2)) {
                position2--;
            }
            if (position2 <= current2) {
                return -1;
            }
        } else {
            position2 = Math.max(0, position + 1);
            while (position2 < current2 && !adapter.isEnabled(position2)) {
                position2++;
            }
            if (position2 >= current2) {
                return -1;
            }
        }
        return position2;
    }

    @Deprecated
    public void setSelectionAfterHeaderView() {
        int count = this.mHeaderViewInfos.size();
        if (count > 0) {
            this.mNextSelectedPosition = 0;
        } else if (this.mAdapter != null) {
            setSelection(count);
        } else {
            this.mNextSelectedPosition = count;
            this.mLayoutMode = 2;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = super.dispatchKeyEvent(event);
        if (!handled) {
            View focused = getFocusedChild();
            if (focused != null && event.getAction() == 0) {
                return onKeyDown(event.getKeyCode(), event);
            }
            return handled;
        }
        return handled;
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View,
              // android.view.KeyEvent.Callback
    @Deprecated
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return commonKey(keyCode, 1, event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    @Deprecated
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return commonKey(keyCode, repeatCount, event);
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View,
              // android.view.KeyEvent.Callback
    @Deprecated
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return commonKey(keyCode, 1, event);
    }

    private boolean commonKey(int keyCode, int count, KeyEvent event) {
        int count2;
        if (this.mAdapter == null || !isAttachedToWindow()) {
            return false;
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        boolean handled = false;
        int action = event.getAction();
        if (action != 1) {
            switch (keyCode) {
                case 19:
                    if (event.hasNoModifiers() || event.hasModifiers(1)) {
                        this.mSemCurrentFocusPosition = this.mSelectedPosition;
                        handled = handleVerticalFocusWithinListItem(33);
                        break;
                    }
                    break;
                case 20:
                    if (event.hasNoModifiers() || event.hasModifiers(1)) {
                        this.mSemCurrentFocusPosition = this.mSelectedPosition;
                        handled = handleVerticalFocusWithinListItem(130);
                        break;
                    }
                    break;
                case 21:
                    if (event.hasNoModifiers() || event.hasModifiers(1)) {
                        this.mSemCurrentFocusPosition = this.mSelectedPosition;
                        handled = resurrectSelectionIfNeeded();
                        if (!handled) {
                            while (true) {
                                count2 = count - 1;
                                if (count > 0 && arrowScroll(17)) {
                                    handled = true;
                                    count = count2;
                                }
                            }
                            count = count2;
                            break;
                        }
                    } else if (event.hasModifiers(2)) {
                        handled = resurrectSelectionIfNeeded() || fullScroll(17);
                        break;
                    }
                    break;
                case 22:
                    if (event.hasNoModifiers() || event.hasModifiers(1)) {
                        this.mSemCurrentFocusPosition = this.mSelectedPosition;
                        handled = resurrectSelectionIfNeeded();
                        if (!handled) {
                            while (true) {
                                count2 = count - 1;
                                if (count > 0 && arrowScroll(66)) {
                                    handled = true;
                                    count = count2;
                                }
                            }
                            count = count2;
                            break;
                        }
                    } else if (event.hasModifiers(2)) {
                        handled = resurrectSelectionIfNeeded() || fullScroll(66);
                        break;
                    }
                    break;
                case 23:
                case 66:
                case 160:
                    if (event.hasNoModifiers()
                            && !(handled = resurrectSelectionIfNeeded())
                            && event.getRepeatCount() == 0
                            && getChildCount() > 0) {
                        keyPressed();
                        handled = true;
                        break;
                    }
                    break;
                case 62:
                    if (this.mPopup == null || !this.mPopup.isShowing()) {
                        if (event.hasNoModifiers()) {
                            boolean z = resurrectSelectionIfNeeded() || pageScroll(66);
                        } else if (event.hasModifiers(1)) {
                            boolean z2 = resurrectSelectionIfNeeded() || pageScroll(17);
                        }
                        handled = true;
                        break;
                    }
                    break;
                case 92:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded() || pageScroll(17);
                        break;
                    } else if (event.hasModifiers(2)) {
                        handled = resurrectSelectionIfNeeded() || fullScroll(17);
                        break;
                    }
                    break;
                case 93:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded() || pageScroll(66);
                        break;
                    } else if (event.hasModifiers(2)) {
                        handled = resurrectSelectionIfNeeded() || fullScroll(66);
                        break;
                    }
                    break;
                case 122:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded() || fullScroll(17);
                        break;
                    }
                    break;
                case 123:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded() || fullScroll(66);
                        break;
                    }
                    break;
            }
        }
        if (!handled && !sendToTextFilter(keyCode, count, event)) {
            switch (action) {
            }
            return true;
        }
        return true;
    }

    boolean pageScroll(int direction) {
        int nextPage;
        boolean rightSide;
        int position;
        if (direction == 17) {
            nextPage = Math.max(0, (this.mSelectedPosition - getChildCount()) - 1);
            rightSide = false;
        } else {
            if (direction != 66) {
                return false;
            }
            nextPage =
                    Math.min(this.mItemCount - 1, (this.mSelectedPosition + getChildCount()) - 1);
            rightSide = true;
        }
        if (nextPage < 0
                || (position =
                                lookForSelectablePositionAfter(
                                        this.mSelectedPosition, nextPage, rightSide))
                        < 0) {
            return false;
        }
        this.mLayoutMode = 4;
        this.mSpecificTop = this.mPaddingLeft + getHorizontalFadingEdgeLength();
        if (rightSide && position > this.mItemCount - getChildCount()) {
            this.mLayoutMode = 3;
        }
        if (!rightSide && position < getChildCount()) {
            this.mLayoutMode = 1;
        }
        setSelectionInt(position);
        invokeOnItemScrollListener();
        if (!awakenScrollBars()) {
            invalidate();
        }
        return true;
    }

    boolean fullScroll(int direction) {
        int lastItem;
        boolean moved = false;
        if (direction == 17) {
            if (this.mSelectedPosition != 0) {
                int position = lookForSelectablePositionAfter(this.mSelectedPosition, 0, true);
                if (position >= 0) {
                    this.mLayoutMode = 1;
                    setSelectionInt(position);
                    invokeOnItemScrollListener();
                }
                moved = true;
            }
        } else if (direction == 66 && this.mSelectedPosition < (lastItem = this.mItemCount - 1)) {
            int position2 = lookForSelectablePositionAfter(this.mSelectedPosition, lastItem, false);
            if (position2 >= 0) {
                this.mLayoutMode = 3;
                setSelectionInt(position2);
                invokeOnItemScrollListener();
            }
            moved = true;
        }
        if (moved && !awakenScrollBars()) {
            awakenScrollBars();
            invalidate();
        }
        return moved;
    }

    private boolean handleVerticalFocusWithinListItem(int direction) {
        View selectedView;
        if (direction != 33 && direction != 130) {
            throw new IllegalArgumentException(
                    "direction must be one of {View.FOCUS_UP, View.FOCUS_DOWN}");
        }
        int numChildren = getChildCount();
        if (this.mItemsCanFocus
                && numChildren > 0
                && this.mSelectedPosition != -1
                && (selectedView = getSelectedView()) != null
                && selectedView.hasFocus()
                && (selectedView instanceof ViewGroup)) {
            View currentFocus = selectedView.findFocus();
            View nextFocus =
                    FocusFinder.getInstance()
                            .findNextFocus((ViewGroup) selectedView, currentFocus, direction);
            if (nextFocus != null) {
                currentFocus.getFocusedRect(this.mTempRect);
                offsetDescendantRectToMyCoords(currentFocus, this.mTempRect);
                offsetRectIntoDescendantCoords(nextFocus, this.mTempRect);
                if (nextFocus.requestFocus(direction, this.mTempRect)) {
                    if (currentFocus != nextFocus && this.mIsFolderTypeFeature) {
                        currentFocus.setSelected(false);
                    }
                    playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
                    return true;
                }
            }
            View globalNextFocus =
                    FocusFinder.getInstance()
                            .findNextFocus((ViewGroup) getRootView(), currentFocus, direction);
            if (globalNextFocus != null) {
                return isViewAncestorOf(globalNextFocus, this);
            }
        }
        return false;
    }

    boolean arrowScroll(int direction) {
        try {
            this.mInLayout = true;
            boolean handled = arrowScrollImpl(direction);
            if (handled) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
            }
            return handled;
        } finally {
            this.mInLayout = false;
        }
    }

    private final int nextSelectedPositionForDirection(
            View selectedView, int selectedPos, int direction) {
        int nextSelected;
        if (direction == 66) {
            int listRight = getWidth() - this.mListPadding.right;
            if (selectedView == null || selectedView.getRight() > listRight) {
                return -1;
            }
            int lastPos = (this.mFirstPosition + getChildCount()) - 1;
            if (this.mIsRTL) {
                nextSelected =
                        (selectedPos == -1 || selectedPos > lastPos) ? lastPos : selectedPos - 1;
            } else {
                nextSelected =
                        (selectedPos == -1 || selectedPos < this.mFirstPosition)
                                ? this.mFirstPosition
                                : selectedPos + 1;
            }
        } else {
            int listLeft = this.mListPadding.left;
            if (selectedView == null || selectedView.getLeft() < listLeft) {
                return -1;
            }
            int lastPos2 = (this.mFirstPosition + getChildCount()) - 1;
            if (this.mIsRTL) {
                nextSelected =
                        (selectedPos == -1 || selectedPos < this.mFirstPosition)
                                ? this.mFirstPosition
                                : selectedPos + 1;
            } else {
                nextSelected =
                        (selectedPos == -1 || selectedPos > lastPos2) ? lastPos2 : selectedPos - 1;
            }
        }
        if (nextSelected < 0 || nextSelected >= this.mAdapter.getCount()) {
            return -1;
        }
        return lookForSelectablePosition(nextSelected, direction == 66);
    }

    private boolean arrowScrollImpl(int direction) {
        View focused;
        if (getChildCount() <= 0) {
            return false;
        }
        View selectedView = getSelectedView();
        int selectedPos = this.mSelectedPosition;
        int nextSelectedPosition =
                nextSelectedPositionForDirection(selectedView, selectedPos, direction);
        int amountToScroll = amountToScroll(direction, nextSelectedPosition);
        ArrowScrollFocusResult focusResult =
                this.mItemsCanFocus ? arrowScrollFocused(direction) : null;
        if (focusResult != null) {
            nextSelectedPosition = focusResult.getSelectedPosition();
            amountToScroll = focusResult.getAmountToScroll();
        }
        boolean needToRedraw = focusResult != null;
        if (nextSelectedPosition != -1) {
            handleNewSelectionChange(
                    selectedView, direction, nextSelectedPosition, focusResult != null);
            setSelectedPositionInt(nextSelectedPosition);
            setNextSelectedPositionInt(nextSelectedPosition);
            selectedView = getSelectedView();
            selectedPos = nextSelectedPosition;
            if (this.mItemsCanFocus
                    && focusResult == null
                    && (focused = getFocusedChild()) != null) {
                focused.clearFocus();
            }
            needToRedraw = true;
            checkSelectionChanged();
        }
        if (amountToScroll > 0) {
            scrollListItemsBy(direction == 17 ? amountToScroll : -amountToScroll);
            needToRedraw = true;
        }
        if (this.mItemsCanFocus
                && focusResult == null
                && selectedView != null
                && selectedView.hasFocus()) {
            View focused2 = selectedView.findFocus();
            if (!isViewAncestorOf(focused2, this) || distanceToView(focused2) > 0) {
                focused2.clearFocus();
            }
        }
        if (nextSelectedPosition == -1
                && selectedView != null
                && !isViewAncestorOf(selectedView, this)) {
            selectedView = null;
            this.mSelectorRect.setEmpty();
            hideSelector();
            this.mResurrectToPosition = -1;
        }
        if (!needToRedraw) {
            return false;
        }
        if (selectedView != null) {
            positionSelectorLikeFocus(selectedPos, selectedView);
            this.mSelectedLeft = selectedView.getLeft();
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        invokeOnItemScrollListener();
        return true;
    }

    private void handleNewSelectionChange(
            View selectedView, int direction, int newSelectedPosition, boolean newFocusAssigned) {
        int leftViewIndex;
        int rightViewIndex;
        View leftView;
        View rightView;
        if (newSelectedPosition == -1) {
            throw new IllegalArgumentException("newSelectedPosition needs to be valid");
        }
        boolean leftSelected = false;
        int selectedIndex = this.mSelectedPosition - this.mFirstPosition;
        int nextSelectedIndex = newSelectedPosition - this.mFirstPosition;
        if (direction == 17) {
            leftViewIndex = nextSelectedIndex;
            rightViewIndex = selectedIndex;
            leftView = getChildAt(leftViewIndex);
            rightView = selectedView;
            leftSelected = true;
        } else {
            leftViewIndex = selectedIndex;
            rightViewIndex = nextSelectedIndex;
            leftView = selectedView;
            rightView = getChildAt(rightViewIndex);
        }
        int numChildren = getChildCount();
        if (leftView != null) {
            leftView.setSelected(!newFocusAssigned && leftSelected);
            measureAndAdjustRight(leftView, leftViewIndex, numChildren);
        }
        if (rightView != null) {
            rightView.setSelected((newFocusAssigned || leftSelected) ? false : true);
            measureAndAdjustRight(rightView, rightViewIndex, numChildren);
        }
    }

    private void measureAndAdjustRight(View child, int childIndex, int numChildren) {
        int oldWidth = child.getWidth();
        measureItem(child);
        if (child.getMeasuredWidth() != oldWidth) {
            relayoutMeasuredItem(child);
            int widthDelta = child.getMeasuredWidth() - oldWidth;
            for (int i = childIndex + 1; i < numChildren; i++) {
                getChildAt(i).offsetLeftAndRight(widthDelta);
            }
        }
    }

    private void measureItem(View child) {
        int childWidthSpec;
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-1, -2);
        }
        int childHeightSpec =
                ViewGroup.getChildMeasureSpec(
                        this.mHeightMeasureSpec,
                        this.mListPadding.top + this.mListPadding.bottom,
                        p.height);
        int lpWidth = p.width;
        if (lpWidth > 0) {
            childWidthSpec = View.MeasureSpec.makeMeasureSpec(lpWidth, 1073741824);
        } else {
            childWidthSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    private void relayoutMeasuredItem(View child) {
        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        int childLeft = child.getLeft();
        int childRight = childLeft + w;
        int childTop = this.mListPadding.top;
        int childBottom = childTop + h;
        child.layout(childLeft, childTop, childRight, childBottom);
    }

    private int getArrowScrollPreviewLength() {
        return Math.max(2, getHorizontalFadingEdgeLength());
    }

    private int amountToScroll(int direction, int nextSelectedPosition) {
        int listRight = getWidth() - this.mListPadding.right;
        int listLeft = this.mListPadding.left;
        int numChildren = getChildCount();
        if (direction == 66) {
            int indexToMakeVisible = numChildren - 1;
            if (this.mIsRTL) {
                indexToMakeVisible = 0;
            }
            if (nextSelectedPosition != -1) {
                indexToMakeVisible = nextSelectedPosition - this.mFirstPosition;
            }
            if (this.mIsRTL) {
                while (indexToMakeVisible < 0) {
                    addViewRightSide(getChildAt(0), this.mFirstPosition);
                    this.mFirstPosition--;
                    indexToMakeVisible = nextSelectedPosition - this.mFirstPosition;
                }
            } else {
                while (numChildren <= indexToMakeVisible) {
                    addViewRightSide(
                            getChildAt(numChildren - 1), (this.mFirstPosition + numChildren) - 1);
                    numChildren++;
                }
            }
            int positionToMakeVisible = this.mFirstPosition + indexToMakeVisible;
            View viewToMakeVisible = getChildAt(indexToMakeVisible);
            int goalRight = listRight;
            if (!this.mIsRTL
                    ? positionToMakeVisible < this.mItemCount - 1
                    : positionToMakeVisible > 0) {
                goalRight -= getArrowScrollPreviewLength();
            }
            if (viewToMakeVisible.getRight() <= goalRight) {
                return 0;
            }
            if (nextSelectedPosition != -1
                    && goalRight - viewToMakeVisible.getLeft() >= getMaxScrollAmount()) {
                return 0;
            }
            int amountToScroll = viewToMakeVisible.getRight() - goalRight;
            if (!this.mIsRTL
                    ? this.mFirstPosition + numChildren == this.mItemCount
                    : this.mFirstPosition == 0) {
                int max = getChildAt(this.mIsRTL ? 0 : numChildren - 1).getRight() - listRight;
                amountToScroll = Math.min(amountToScroll, max);
            }
            int max2 = getMaxScrollAmount();
            return Math.min(amountToScroll, max2);
        }
        int indexToMakeVisible2 = 0;
        if (this.mIsRTL) {
            indexToMakeVisible2 = numChildren - 1;
        }
        if (nextSelectedPosition != -1) {
            indexToMakeVisible2 = nextSelectedPosition - this.mFirstPosition;
        }
        if (this.mIsRTL) {
            while (numChildren <= indexToMakeVisible2) {
                addViewLeftSide(
                        getChildAt(numChildren - 1), (this.mFirstPosition + numChildren) - 1);
                numChildren++;
            }
        } else {
            while (indexToMakeVisible2 < 0) {
                addViewLeftSide(getChildAt(0), this.mFirstPosition);
                this.mFirstPosition--;
                indexToMakeVisible2 = nextSelectedPosition - this.mFirstPosition;
            }
        }
        int positionToMakeVisible2 = this.mFirstPosition + indexToMakeVisible2;
        View viewToMakeVisible2 = getChildAt(indexToMakeVisible2);
        int goalLeft = listLeft;
        if (!this.mIsRTL
                ? positionToMakeVisible2 > 0
                : positionToMakeVisible2 < this.mItemCount - 1) {
            goalLeft += getArrowScrollPreviewLength();
        }
        if (viewToMakeVisible2.getLeft() >= goalLeft) {
            return 0;
        }
        if (nextSelectedPosition != -1
                && viewToMakeVisible2.getRight() - goalLeft >= getMaxScrollAmount()) {
            return 0;
        }
        int amountToScroll2 = goalLeft - viewToMakeVisible2.getLeft();
        if (!this.mIsRTL
                ? this.mFirstPosition == 0
                : this.mFirstPosition + numChildren == this.mItemCount) {
            int max3 = listLeft - getChildAt(this.mIsRTL ? numChildren - 1 : 0).getLeft();
            amountToScroll2 = Math.min(amountToScroll2, max3);
        }
        int max4 = getMaxScrollAmount();
        return Math.min(amountToScroll2, max4);
    }

    private static class ArrowScrollFocusResult {
        private int mAmountToScroll;
        private int mSelectedPosition;

        private ArrowScrollFocusResult() {}

        void populate(int selectedPosition, int amountToScroll) {
            this.mSelectedPosition = selectedPosition;
            this.mAmountToScroll = amountToScroll;
        }

        public int getSelectedPosition() {
            return this.mSelectedPosition;
        }

        public int getAmountToScroll() {
            return this.mAmountToScroll;
        }
    }

    private int lookForSelectablePositionOnScreen(int direction) {
        int startPos;
        int firstPosition = this.mFirstPosition;
        if (direction == 66) {
            if (this.mSelectedPosition != -1) {
                startPos = this.mSelectedPosition + 1;
            } else {
                startPos = firstPosition;
            }
            if (startPos >= this.mAdapter.getCount()) {
                return -1;
            }
            if (startPos < firstPosition) {
                startPos = firstPosition;
            }
            int lastVisiblePos = getLastVisiblePosition();
            ListAdapter adapter = getAdapter();
            if (this.mIsRTL) {
                for (int pos = startPos; pos >= firstPosition; pos--) {
                    if (adapter.isEnabled(pos)
                            && getChildAt(pos - firstPosition).getVisibility() == 0) {
                        return pos;
                    }
                }
            } else {
                for (int pos2 = startPos; pos2 <= lastVisiblePos; pos2++) {
                    if (adapter.isEnabled(pos2)
                            && getChildAt(pos2 - firstPosition).getVisibility() == 0) {
                        return pos2;
                    }
                }
            }
        } else {
            int last = (getChildCount() + firstPosition) - 1;
            int lastVisiblePos2 = getLastVisiblePosition();
            int startPos2 =
                    this.mSelectedPosition != -1
                            ? this.mSelectedPosition - 1
                            : (getChildCount() + firstPosition) - 1;
            if (startPos2 < 0 || startPos2 >= this.mAdapter.getCount()) {
                return -1;
            }
            if (startPos2 > last) {
                startPos2 = last;
            }
            ListAdapter adapter2 = getAdapter();
            if (this.mIsRTL) {
                for (int pos3 = startPos2; pos3 <= lastVisiblePos2; pos3++) {
                    if (adapter2.isEnabled(pos3)
                            && getChildAt(pos3 - firstPosition).getVisibility() == 0) {
                        return pos3;
                    }
                }
            } else {
                for (int pos4 = startPos2; pos4 >= firstPosition; pos4--) {
                    if (adapter2.isEnabled(pos4)
                            && getChildAt(pos4 - firstPosition).getVisibility() == 0) {
                        return pos4;
                    }
                }
            }
        }
        return -1;
    }

    private ArrowScrollFocusResult arrowScrollFocused(int direction) {
        boolean leftFadingEdgeShowing;
        int xSearchPoint;
        View oldFocus;
        int xSearchPoint2;
        int selectablePosition;
        View selectedView = getSelectedView();
        if (selectedView == null || !selectedView.hasFocus()) {
            if (direction != 66) {
                leftFadingEdgeShowing =
                        (this.mFirstPosition + getChildCount()) - 1 < this.mItemCount;
                int listRight =
                        (getWidth() - this.mListPadding.right)
                                - (leftFadingEdgeShowing ? getArrowScrollPreviewLength() : 0);
                if (selectedView != null && selectedView.getRight() < listRight) {
                    xSearchPoint = selectedView.getRight();
                } else {
                    xSearchPoint = listRight;
                }
                this.mTempRect.set(xSearchPoint, 0, xSearchPoint, 0);
            } else {
                leftFadingEdgeShowing = this.mFirstPosition > 0;
                int listLeft =
                        this.mListPadding.left
                                + (leftFadingEdgeShowing ? getArrowScrollPreviewLength() : 0);
                if (selectedView != null && selectedView.getLeft() > listLeft) {
                    xSearchPoint2 = selectedView.getLeft();
                } else {
                    xSearchPoint2 = listLeft;
                }
                this.mTempRect.set(xSearchPoint2, 0, xSearchPoint2, 0);
            }
            oldFocus =
                    FocusFinder.getInstance()
                            .findNextFocusFromRect(this, this.mTempRect, direction);
        } else {
            View oldFocus2 = selectedView.findFocus();
            oldFocus = FocusFinder.getInstance().findNextFocus(this, oldFocus2, direction);
        }
        if (oldFocus != null) {
            int positionOfNewFocus = positionOfNewFocus(oldFocus);
            if (this.mSelectedPosition != -1
                    && positionOfNewFocus != this.mSelectedPosition
                    && (selectablePosition = lookForSelectablePositionOnScreen(direction)) != -1
                    && ((direction == 66 && selectablePosition < positionOfNewFocus)
                            || (direction == 17 && selectablePosition > positionOfNewFocus))) {
                return null;
            }
            int focusScroll = amountToScrollToNewFocus(direction, oldFocus, positionOfNewFocus);
            int maxScrollAmount = getMaxScrollAmount();
            if (focusScroll < maxScrollAmount) {
                oldFocus.requestFocus(direction);
                this.mArrowScrollFocusResult.populate(positionOfNewFocus, focusScroll);
                return this.mArrowScrollFocusResult;
            }
            if (distanceToView(oldFocus) < maxScrollAmount) {
                oldFocus.requestFocus(direction);
                this.mArrowScrollFocusResult.populate(positionOfNewFocus, maxScrollAmount);
                return this.mArrowScrollFocusResult;
            }
        }
        return null;
    }

    private int positionOfNewFocus(View newFocus) {
        int numChildren = getChildCount();
        for (int i = 0; i < numChildren; i++) {
            View child = getChildAt(i);
            if (isViewAncestorOf(newFocus, child)) {
                return this.mFirstPosition + i;
            }
        }
        throw new IllegalArgumentException(
                "newFocus is not a child of any of the children of the list!");
    }

    private boolean isViewAncestorOf(View child, View parent) {
        if (child == parent) {
            return true;
        }
        Object parent2 = child.getParent();
        return (parent2 instanceof ViewGroup) && isViewAncestorOf((View) parent2, parent);
    }

    private int amountToScrollToNewFocus(int direction, View newFocus, int positionOfNewFocus) {
        newFocus.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(newFocus, this.mTempRect);
        if (direction == 17) {
            if (this.mTempRect.left >= this.mListPadding.left) {
                return 0;
            }
            int amountToScroll = this.mListPadding.left - this.mTempRect.left;
            if (this.mIsRTL) {
                if (positionOfNewFocus >= this.mItemCount - 1) {
                    return amountToScroll;
                }
            } else if (positionOfNewFocus <= 0) {
                return amountToScroll;
            }
            return amountToScroll + getArrowScrollPreviewLength();
        }
        int listRight = getWidth() - this.mListPadding.right;
        if (this.mTempRect.right <= listRight) {
            return 0;
        }
        int amountToScroll2 = this.mTempRect.right - listRight;
        if (this.mIsRTL) {
            if (positionOfNewFocus <= 0) {
                return amountToScroll2;
            }
        } else if (positionOfNewFocus >= this.mItemCount - 1) {
            return amountToScroll2;
        }
        return amountToScroll2 + getArrowScrollPreviewLength();
    }

    private int distanceToView(View descendant) {
        descendant.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(descendant, this.mTempRect);
        int listRight = (this.mRight - this.mLeft) - this.mListPadding.right;
        if (this.mTempRect.right < this.mListPadding.left) {
            int distance = this.mListPadding.left - this.mTempRect.right;
            return distance;
        }
        if (this.mTempRect.left <= listRight) {
            return 0;
        }
        int distance2 = this.mTempRect.left - listRight;
        return distance2;
    }

    private void scrollListItemsBy(int amount) {
        int lastVisiblePosition;
        View last;
        int lastVisiblePosition2;
        semOffsetChildrenLeftAndRight(amount);
        int listRight = getWidth() - this.mListPadding.right;
        int listLeft = this.mListPadding.left;
        SemHorizontalAbsListView.RecycleBin recycleBin = this.mRecycler;
        if (amount < 0) {
            int numChildren = getChildCount();
            if (this.mIsRTL) {
                last = getChildAt(0);
                while (last.getRight() < listRight) {
                    if (this.mFirstPosition > 0) {
                        last = addViewRightSide(last, this.mFirstPosition);
                    }
                    if (last == null) {
                        return;
                    } else {
                        this.mFirstPosition--;
                    }
                }
            } else {
                last = getChildAt(numChildren - 1);
                while (last.getRight() < listRight
                        && (this.mFirstPosition + numChildren) - 1 < this.mItemCount - 1) {
                    last = addViewRightSide(last, lastVisiblePosition2);
                    numChildren++;
                }
            }
            if (last.getRight() < listRight) {
                semOffsetChildrenLeftAndRight(listRight - last.getRight());
            }
            View first = getChildAt(0);
            if (this.mIsRTL) {
                first = getChildAt(numChildren - 1);
            }
            if (first == null) {
                return;
            }
            if (this.mIsRTL) {
                int lastIndex = getChildCount() - 1;
                while (first.getRight() < listLeft) {
                    SemHorizontalAbsListView.LayoutParams layoutParams =
                            (SemHorizontalAbsListView.LayoutParams) first.getLayoutParams();
                    if (recycleBin.shouldRecycleViewType(layoutParams.viewType)) {
                        recycleBin.addScrapView(first, this.mFirstPosition + lastIndex);
                    }
                    detachViewFromParent(first);
                    lastIndex--;
                    first = getChildAt(lastIndex);
                }
                return;
            }
            while (first.getRight() < listLeft) {
                SemHorizontalAbsListView.LayoutParams layoutParams2 =
                        (SemHorizontalAbsListView.LayoutParams) first.getLayoutParams();
                if (recycleBin.shouldRecycleViewType(layoutParams2.viewType)) {
                    recycleBin.addScrapView(first, this.mFirstPosition);
                }
                detachViewFromParent(first);
                first = getChildAt(0);
                this.mFirstPosition++;
            }
            return;
        }
        int numChildren2 = getChildCount();
        View first2 = this.mIsRTL ? getChildAt(numChildren2 - 1) : getChildAt(0);
        if (this.mIsRTL) {
            while (first2.getLeft() > listLeft
                    && (this.mFirstPosition + numChildren2) - 1 < this.mItemCount - 1) {
                first2 = addViewLeftSide(first2, lastVisiblePosition);
                if (first2 == null) {
                    return;
                } else {
                    numChildren2++;
                }
            }
        } else {
            while (first2.getLeft() > listLeft && this.mFirstPosition > 0) {
                first2 = addViewLeftSide(first2, this.mFirstPosition);
                if (first2 == null) {
                    return;
                } else {
                    this.mFirstPosition--;
                }
            }
        }
        if (first2.getLeft() > listLeft) {
            semOffsetChildrenLeftAndRight(listLeft - first2.getLeft());
        }
        int lastIndex2 = getChildCount() - 1;
        View last2 = getChildAt(lastIndex2);
        if (this.mIsRTL) {
            View last3 = getChildAt(0);
            while (last3 != null && last3.getLeft() > listRight) {
                SemHorizontalAbsListView.LayoutParams layoutParams3 =
                        (SemHorizontalAbsListView.LayoutParams) last3.getLayoutParams();
                if (recycleBin.shouldRecycleViewType(layoutParams3.viewType)) {
                    recycleBin.addScrapView(last3, this.mFirstPosition);
                }
                detachViewFromParent(last3);
                last3 = getChildAt(0);
                this.mFirstPosition++;
            }
            return;
        }
        while (last2 != null && last2.getLeft() > listRight) {
            SemHorizontalAbsListView.LayoutParams layoutParams4 =
                    (SemHorizontalAbsListView.LayoutParams) last2.getLayoutParams();
            if (recycleBin.shouldRecycleViewType(layoutParams4.viewType)) {
                recycleBin.addScrapView(last2, this.mFirstPosition + lastIndex2);
            }
            detachViewFromParent(last2);
            lastIndex2--;
            last2 = getChildAt(lastIndex2);
        }
    }

    private View addViewLeftSide(View theView, int position) {
        int abovePosition;
        if (this.mIsRTL) {
            abovePosition = position + 1;
        } else {
            abovePosition = position - 1;
        }
        View view = obtainView(abovePosition, this.mIsScrap);
        int edgeOfNewChild = theView.getLeft() - this.mDividerHeight;
        if (view != null) {
            setupChild(
                    view,
                    abovePosition,
                    edgeOfNewChild,
                    false,
                    this.mListPadding.top,
                    false,
                    this.mIsScrap[0]);
        }
        return view;
    }

    private View addViewRightSide(View theView, int position) {
        int edgeOfNewChild;
        int belowPosition = position + 1;
        View view = obtainView(belowPosition, this.mIsScrap);
        if (theView == null) {
            edgeOfNewChild = 0;
        } else {
            int edgeOfNewChild2 = theView.getRight() + this.mDividerHeight;
            edgeOfNewChild = edgeOfNewChild2;
        }
        if (view != null) {
            setupChild(
                    view,
                    belowPosition,
                    edgeOfNewChild,
                    true,
                    this.mListPadding.top,
                    false,
                    this.mIsScrap[0]);
        }
        return view;
    }

    @Deprecated
    public void setItemsCanFocus(boolean itemsCanFocus) {
        this.mItemsCanFocus = itemsCanFocus;
        if (!itemsCanFocus) {
            setDescendantFocusability(393216);
        }
    }

    @Deprecated
    public boolean getItemsCanFocus() {
        return this.mItemsCanFocus;
    }

    @Override // android.view.View
    @Deprecated
    public boolean isOpaque() {
        boolean retValue =
                (this.mCachingActive
                                && this.mIsCacheColorOpaque
                                && this.mDividerIsOpaque
                                && hasOpaqueScrollbars())
                        || super.isOpaque();
        if (retValue) {
            if (this.mIsRTL) {
                int listRight =
                        getWidth()
                                - (this.mListPadding != null
                                        ? this.mListPadding.right
                                        : this.mPaddingRight);
                View first = getChildAt(0);
                if (first == null || first.getRight() + getDividerHeight() < listRight) {
                    return false;
                }
                int listLeft =
                        this.mListPadding != null ? this.mListPadding.left : this.mPaddingLeft;
                View last = getChildAt(getChildCount() - 1);
                if (last == null || last.getLeft() < listLeft) {
                    return false;
                }
            } else {
                int listLeft2 =
                        this.mListPadding != null ? this.mListPadding.left : this.mPaddingLeft;
                View first2 = getChildAt(0);
                if (first2 == null || first2.getLeft() > listLeft2) {
                    return false;
                }
                int listRight2 =
                        getWidth()
                                - (this.mListPadding != null
                                        ? this.mListPadding.right
                                        : this.mPaddingRight);
                View last2 = getChildAt(getChildCount() - 1);
                if (last2 == null || last2.getRight() + getDividerHeight() < listRight2) {
                    return false;
                }
            }
        }
        return retValue;
    }

    void drawOverscrollHeader(Canvas canvas, Drawable drawable, Rect bounds) {
        int width = drawable.getMinimumWidth();
        canvas.save();
        canvas.clipRect(bounds);
        int span = bounds.right - bounds.left;
        if (span < width) {
            bounds.left = bounds.right - width;
        }
        drawable.setBounds(bounds);
        drawable.draw(canvas);
        canvas.restore();
    }

    void drawOverscrollFooter(Canvas canvas, Drawable drawable, Rect bounds) {
        int width = drawable.getMinimumWidth();
        canvas.save();
        canvas.clipRect(bounds);
        int span = bounds.right - bounds.left;
        if (span < width) {
            bounds.right = bounds.left + width;
        }
        drawable.setBounds(bounds);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.ViewGroup, android.view.View
    @Deprecated
    protected void dispatchDraw(Canvas canvas) {
        ListAdapter adapter;
        int itemCount;
        int effectivePaddingLeft;
        Drawable overscrollHeader;
        int first;
        boolean footerDividers;
        Drawable overscrollFooter;
        int start;
        int right;
        Drawable overscrollFooter2;
        boolean drawOverscrollHeader;
        int listRight;
        boolean drawDividers;
        ListAdapter adapter2;
        Paint paint;
        if (this.mCachingStarted) {
            this.mCachingActive = true;
        }
        int dividerHeight = this.mDividerHeight;
        Drawable overscrollHeader2 = this.mOverScrollHeader;
        Drawable overscrollFooter3 = this.mOverScrollFooter;
        boolean drawOverscrollHeader2 = overscrollHeader2 != null;
        boolean drawOverscrollFooter = overscrollFooter3 != null;
        boolean drawDividers2 = dividerHeight > 0 && this.mDivider != null;
        if (drawDividers2 || drawOverscrollHeader2 || drawOverscrollFooter) {
            Rect bounds = this.mTempRect;
            bounds.top = this.mPaddingTop;
            bounds.bottom = (this.mBottom - this.mTop) - this.mPaddingBottom;
            int count = getChildCount();
            int headerCount = this.mHeaderViewInfos.size();
            int itemCount2 = this.mItemCount;
            int footerLimit = (itemCount2 - this.mFooterViewInfos.size()) - 1;
            boolean headerDividers = this.mHeaderDividersEnabled;
            boolean footerDividers2 = this.mFooterDividersEnabled;
            int first2 = this.mFirstPosition;
            boolean z = this.mAreAllItemsSelectable;
            ListAdapter adapter3 = this.mAdapter;
            boolean fillForMissingDividers = isOpaque() && !super.isOpaque();
            if (fillForMissingDividers) {
                itemCount = itemCount2;
                if (this.mDividerPaint == null && this.mIsCacheColorOpaque) {
                    this.mDividerPaint = new Paint();
                    adapter = adapter3;
                    this.mDividerPaint.setColor(getCacheColorHint());
                } else {
                    adapter = adapter3;
                }
            } else {
                adapter = adapter3;
                itemCount = itemCount2;
            }
            Paint paint2 = this.mDividerPaint;
            int effectivePaddingRight = 0;
            int effectivePaddingLeft2 = this.mGroupFlags;
            Paint paint3 = paint2;
            if ((effectivePaddingLeft2 & 34) != 34) {
                effectivePaddingLeft = 0;
            } else {
                effectivePaddingLeft = this.mListPadding.left;
                effectivePaddingRight = this.mListPadding.right;
            }
            int i = this.mRight;
            int effectivePaddingLeft3 = effectivePaddingLeft;
            int effectivePaddingLeft4 = this.mLeft;
            int listRight2 = ((i - effectivePaddingLeft4) - effectivePaddingRight) + this.mScrollX;
            boolean drawOverscrollFooter2 = drawOverscrollFooter;
            if (this.mStackFromBottom) {
                boolean drawOverscrollHeader3 = drawOverscrollHeader2;
                boolean drawDividers3 = drawDividers2;
                Drawable overscrollFooter4 = overscrollFooter3;
                int itemCount3 = itemCount;
                ListAdapter adapter4 = adapter;
                int listRight3 = this.mScrollX;
                if (count <= 0 || !drawOverscrollHeader3) {
                    overscrollHeader = overscrollHeader2;
                } else {
                    bounds.left = listRight3;
                    bounds.right = getChildAt(0).getLeft();
                    overscrollHeader = overscrollHeader2;
                    drawOverscrollHeader(canvas, overscrollHeader, bounds);
                }
                int i2 = drawOverscrollHeader3 ? 1 : 0;
                int start2 = i2;
                int i3 = i2;
                while (i3 < count) {
                    int itemCount4 = itemCount3;
                    int itemCount5 = first2 + i3;
                    boolean isHeader = itemCount5 < headerCount;
                    boolean isFooter = itemCount5 >= footerLimit;
                    if ((headerDividers || !isHeader) && (footerDividers2 || !isFooter)) {
                        View child = getChildAt(i3);
                        first = first2;
                        int left = child.getLeft();
                        if (drawDividers3) {
                            overscrollFooter = overscrollFooter4;
                            int effectivePaddingLeft5 = effectivePaddingLeft3;
                            if (left <= effectivePaddingLeft5) {
                                footerDividers = footerDividers2;
                                effectivePaddingLeft3 = effectivePaddingLeft5;
                                start = start2;
                            } else {
                                effectivePaddingLeft3 = effectivePaddingLeft5;
                                int effectivePaddingLeft6 = start2;
                                boolean isFirstItem = i3 == effectivePaddingLeft6;
                                start = effectivePaddingLeft6;
                                int start3 = itemCount5 - 1;
                                if (!adapter4.isEnabled(itemCount5)) {
                                    footerDividers = footerDividers2;
                                } else if (!headerDividers && (isHeader || start3 < headerCount)) {
                                    footerDividers = footerDividers2;
                                } else if (isFirstItem
                                        || (adapter4.isEnabled(start3)
                                                && (footerDividers2
                                                        || (!isFooter && start3 < footerLimit)))) {
                                    footerDividers = footerDividers2;
                                    bounds.left = left - dividerHeight;
                                    bounds.right = left;
                                    drawDivider(canvas, bounds, i3 - 1);
                                } else {
                                    footerDividers = footerDividers2;
                                }
                                if (fillForMissingDividers) {
                                    bounds.left = left - dividerHeight;
                                    bounds.right = left;
                                    canvas.drawRect(bounds, paint3);
                                }
                            }
                        } else {
                            footerDividers = footerDividers2;
                            overscrollFooter = overscrollFooter4;
                            start = start2;
                        }
                    } else {
                        footerDividers = footerDividers2;
                        first = first2;
                        overscrollFooter = overscrollFooter4;
                        start = start2;
                    }
                    i3++;
                    itemCount3 = itemCount4;
                    first2 = first;
                    overscrollFooter4 = overscrollFooter;
                    start2 = start;
                    footerDividers2 = footerDividers;
                }
                Drawable overscrollFooter5 = overscrollFooter4;
                if (count > 0 && listRight3 > 0) {
                    if (drawOverscrollFooter2) {
                        int absListRight = this.mRight;
                        bounds.left = absListRight;
                        bounds.right = absListRight + listRight3;
                        drawOverscrollFooter(canvas, overscrollFooter5, bounds);
                    } else if (drawDividers3) {
                        bounds.left = listRight2;
                        bounds.right = listRight2 + dividerHeight;
                        drawDivider(canvas, bounds, -1);
                    }
                }
            } else {
                int scrollX = this.mScrollX;
                if (count <= 0 || scrollX >= 0) {
                    right = 0;
                } else if (drawOverscrollHeader2) {
                    right = 0;
                    bounds.right = 0;
                    bounds.left = scrollX;
                    drawOverscrollHeader(canvas, overscrollHeader2, bounds);
                } else {
                    right = 0;
                    if (drawDividers2) {
                        bounds.right = 0;
                        bounds.left = -dividerHeight;
                        drawDivider(canvas, bounds, -1);
                    }
                }
                int i4 = 0;
                int scrollX2 = right;
                while (i4 < count) {
                    Drawable overscrollHeader3 = overscrollHeader2;
                    int itemIndex = first2 + i4;
                    boolean isHeader2 = itemIndex < headerCount;
                    boolean isFooter2 = itemIndex >= footerLimit;
                    if ((!headerDividers && isHeader2) || (!footerDividers2 && isFooter2)) {
                        listRight = listRight2;
                        drawOverscrollHeader = drawOverscrollHeader2;
                        drawDividers = drawDividers2;
                        adapter2 = adapter;
                        paint = paint3;
                    } else {
                        View child2 = getChildAt(i4);
                        scrollX2 = child2.getRight();
                        drawOverscrollHeader = drawOverscrollHeader2;
                        boolean isLastItem = i4 == count + (-1);
                        if (!drawDividers2 || scrollX2 >= listRight2) {
                            listRight = listRight2;
                            drawDividers = drawDividers2;
                            adapter2 = adapter;
                            paint = paint3;
                        } else if (drawOverscrollFooter2 && isLastItem) {
                            listRight = listRight2;
                            drawDividers = drawDividers2;
                            adapter2 = adapter;
                            paint = paint3;
                        } else {
                            listRight = listRight2;
                            int listRight4 = itemIndex + 1;
                            drawDividers = drawDividers2;
                            adapter2 = adapter;
                            if (adapter2.isEnabled(itemIndex)) {
                                if (headerDividers || (!isHeader2 && listRight4 >= headerCount)) {
                                    if (isLastItem
                                            || (adapter2.isEnabled(listRight4)
                                                    && (footerDividers2
                                                            || (!isFooter2
                                                                    && listRight4
                                                                            < footerLimit)))) {
                                        bounds.left = scrollX2;
                                        bounds.right = scrollX2 + dividerHeight;
                                        drawDivider(canvas, bounds, i4);
                                        paint = paint3;
                                    }
                                }
                            }
                            if (!fillForMissingDividers) {
                                paint = paint3;
                            } else {
                                bounds.left = scrollX2;
                                bounds.right = scrollX2 + dividerHeight;
                                paint = paint3;
                                canvas.drawRect(bounds, paint);
                            }
                        }
                    }
                    i4++;
                    paint3 = paint;
                    adapter = adapter2;
                    overscrollHeader2 = overscrollHeader3;
                    drawOverscrollHeader2 = drawOverscrollHeader;
                    listRight2 = listRight;
                    drawDividers2 = drawDividers;
                }
                int overFooterRight = this.mRight + this.mScrollX;
                if (!drawOverscrollFooter2) {
                    overscrollFooter2 = overscrollFooter3;
                } else if (first2 + count != itemCount || overFooterRight <= scrollX2) {
                    overscrollFooter2 = overscrollFooter3;
                } else {
                    bounds.left = scrollX2;
                    bounds.right = overFooterRight;
                    overscrollFooter2 = overscrollFooter3;
                    drawOverscrollFooter(canvas, overscrollFooter2, bounds);
                }
            }
        }
        if (this.mAddDeleteListAnimator != null) {
            this.mAddDeleteListAnimator.draw(canvas);
        }
        super.dispatchDraw(canvas);
        if (this.mDndListAnimator != null) {
            this.mDndListAnimator.dispatchDraw(canvas);
        }
    }

    @Override // android.view.ViewGroup
    @Deprecated
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (this.mDndListAnimator != null
                && !this.mDndListAnimator.preDrawChild(canvas, child, drawingTime)) {
            return false;
        }
        boolean more = super.drawChild(canvas, child, drawingTime);
        if (this.mCachingActive && child.mCachingFailed) {
            this.mCachingActive = false;
        }
        if (this.mDndListAnimator != null) {
            this.mDndListAnimator.postDrawChild(canvas, child, drawingTime);
        }
        return more;
    }

    void drawDivider(Canvas canvas, Rect bounds, int childIndex) {
        Drawable divider = this.mDivider;
        divider.setBounds(bounds);
        divider.draw(canvas);
    }

    @Deprecated
    public Drawable getDivider() {
        return this.mDivider;
    }

    @Deprecated
    public void setDivider(Drawable divider) {
        if (divider != null) {
            this.mDividerHeight = divider.getIntrinsicHeight();
        } else {
            this.mDividerHeight = 0;
        }
        this.mDivider = divider;
        this.mDividerIsOpaque = divider == null || divider.getOpacity() == -1;
        requestLayout();
        invalidate();
    }

    @Deprecated
    public int getDividerHeight() {
        return this.mDividerHeight;
    }

    @Deprecated
    public void setDividerHeight(int height) {
        this.mDividerHeight = height;
        requestLayout();
        invalidate();
    }

    @Deprecated
    public void setHeaderDividersEnabled(boolean headerDividersEnabled) {
        this.mHeaderDividersEnabled = headerDividersEnabled;
        invalidate();
    }

    @Deprecated
    public boolean areHeaderDividersEnabled() {
        return this.mHeaderDividersEnabled;
    }

    @Deprecated
    public void setFooterDividersEnabled(boolean footerDividersEnabled) {
        this.mFooterDividersEnabled = footerDividersEnabled;
        invalidate();
    }

    @Deprecated
    public boolean areFooterDividersEnabled() {
        return this.mFooterDividersEnabled;
    }

    @Deprecated
    public void setOverscrollHeader(Drawable header) {
        this.mOverScrollHeader = header;
        if (this.mScrollX < 0) {
            invalidate();
        }
    }

    @Deprecated
    public Drawable getOverscrollHeader() {
        return this.mOverScrollHeader;
    }

    @Deprecated
    public void setOverscrollFooter(Drawable footer) {
        this.mOverScrollFooter = footer;
        invalidate();
    }

    @Deprecated
    public Drawable getOverscrollFooter() {
        return this.mOverScrollFooter;
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        ListAdapter adapter = this.mAdapter;
        int closetChildIndex = -1;
        int closestChildLeft = 0;
        if (adapter != null && gainFocus && previouslyFocusedRect != null) {
            previouslyFocusedRect.offset(this.mScrollX, this.mScrollY);
            if (adapter.getCount() < getChildCount() + this.mFirstPosition) {
                this.mLayoutMode = 0;
                layoutChildren();
            }
            Rect otherRect = this.mTempRect;
            int minDistance = Integer.MAX_VALUE;
            int childCount = getChildCount();
            int firstPosition = this.mFirstPosition;
            for (int i = 0; i < childCount; i++) {
                if (adapter.isEnabled(firstPosition + i)) {
                    View other = getChildAt(i);
                    other.getDrawingRect(otherRect);
                    offsetDescendantRectToMyCoords(other, otherRect);
                    int distance = getDistance(previouslyFocusedRect, otherRect, direction);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closetChildIndex = i;
                        closestChildLeft = other.getLeft();
                    }
                }
            }
        }
        if (closetChildIndex >= 0) {
            setSelectionFromStart(this.mFirstPosition + closetChildIndex, closestChildLeft);
        } else {
            requestLayout();
        }
        if (gainFocus && this.mDndListAnimator != null) {
            post(
                    new Runnable() { // from class: android.widget.SemHorizontalListView.2
                        @Override // java.lang.Runnable
                        public void run() {
                            SemHorizontalListView.this.mDndListAnimator
                                    .speakDescriptionForAccessibility();
                        }
                    });
        }
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && this.mDndListAnimator != null) {
            post(
                    new Runnable() { // from class: android.widget.SemHorizontalListView.3
                        @Override // java.lang.Runnable
                        public void run() {
                            SemHorizontalListView.this.mDndListAnimator
                                    .speakDescriptionForAccessibility();
                        }
                    });
        }
    }

    @Override // android.view.View
    @Deprecated
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                addHeaderView(getChildAt(i));
            }
            removeAllViews();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected <T extends View> T findViewTraversal(int i) {
        T t = (T) super.findViewTraversal(i);
        if (t == null) {
            T t2 = (T) findViewInHeadersOrFooters(this.mHeaderViewInfos, i);
            if (t2 != null) {
                return t2;
            }
            t = (T) findViewInHeadersOrFooters(this.mFooterViewInfos, i);
            if (t != null) {
                return t;
            }
        }
        return t;
    }

    View findViewInHeadersOrFooters(ArrayList<FixedViewInfo> where, int id) {
        View v;
        if (where != null) {
            int len = where.size();
            for (int i = 0; i < len; i++) {
                View v2 = where.get(i).view;
                if (!v2.isRootNamespace() && (v = v2.findViewById(id)) != null) {
                    return v;
                }
            }
            return null;
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected <T extends View> T findViewWithTagTraversal(Object obj) {
        T t = (T) super.findViewWithTagTraversal(obj);
        if (t == null) {
            T t2 = (T) findViewWithTagInHeadersOrFooters(this.mHeaderViewInfos, obj);
            if (t2 != null) {
                return t2;
            }
            t = (T) findViewWithTagInHeadersOrFooters(this.mFooterViewInfos, obj);
            if (t != null) {
                return t;
            }
        }
        return t;
    }

    View findViewWithTagInHeadersOrFooters(ArrayList<FixedViewInfo> where, Object tag) {
        View v;
        if (where != null) {
            int len = where.size();
            for (int i = 0; i < len; i++) {
                View v2 = where.get(i).view;
                if (!v2.isRootNamespace() && (v = v2.findViewWithTag(tag)) != null) {
                    return v;
                }
            }
            return null;
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected <T extends View> T findViewByPredicateTraversal(
            Predicate<View> predicate, View view) {
        T t = (T) super.findViewByPredicateTraversal(predicate, view);
        if (t == null) {
            T t2 =
                    (T)
                            findViewByPredicateInHeadersOrFooters(
                                    this.mHeaderViewInfos, predicate, view);
            if (t2 != null) {
                return t2;
            }
            t = (T) findViewByPredicateInHeadersOrFooters(this.mFooterViewInfos, predicate, view);
            if (t != null) {
                return t;
            }
        }
        return t;
    }

    View findViewByPredicateInHeadersOrFooters(
            ArrayList<FixedViewInfo> where, Predicate<View> predicate, View childToSkip) {
        View v;
        if (where != null) {
            int len = where.size();
            for (int i = 0; i < len; i++) {
                View v2 = where.get(i).view;
                if (v2 != childToSkip
                        && !v2.isRootNamespace()
                        && (v = v2.findViewByPredicate(predicate)) != null) {
                    return v;
                }
            }
            return null;
        }
        return null;
    }

    @Override // android.widget.SemHorizontalAbsListView
    int getWidthForPosition(int position) {
        int width = super.getWidthForPosition(position);
        if (shouldAdjustWidthForDivider(position)) {
            return this.mDividerHeight + width;
        }
        return width;
    }

    private boolean shouldAdjustWidthForDivider(int itemIndex) {
        boolean z;
        boolean z2;
        int dividerHeight = this.mDividerHeight;
        Drawable overscrollHeader = this.mOverScrollHeader;
        Drawable overscrollFooter = this.mOverScrollFooter;
        boolean drawOverscrollHeader = overscrollHeader != null;
        boolean drawOverscrollFooter = overscrollFooter != null;
        boolean drawDividers = dividerHeight > 0 && this.mDivider != null;
        if (drawDividers) {
            boolean fillForMissingDividers = isOpaque() && !super.isOpaque();
            int itemCount = this.mItemCount;
            int headerCount = this.mHeaderViewInfos.size();
            int footerLimit = itemCount - this.mFooterViewInfos.size();
            boolean isHeader = itemIndex < headerCount;
            boolean isFooter = itemIndex >= footerLimit;
            boolean headerDividers = this.mHeaderDividersEnabled;
            boolean footerDividers = this.mFooterDividersEnabled;
            if ((headerDividers || !isHeader) && (footerDividers || !isFooter)) {
                ListAdapter adapter = this.mAdapter;
                if (!this.mStackFromBottom) {
                    boolean isLastItem = itemIndex == itemCount + (-1);
                    if (!drawOverscrollFooter || !isLastItem) {
                        int nextIndex = itemIndex + 1;
                        if (!adapter.isEnabled(itemIndex)) {
                            z2 = true;
                        } else if (!headerDividers && (isHeader || nextIndex < headerCount)) {
                            z2 = true;
                        } else if (!isLastItem) {
                            if (adapter.isEnabled(nextIndex)) {
                                if (footerDividers) {
                                    return true;
                                }
                                if (!isFooter && nextIndex < footerLimit) {
                                    return true;
                                }
                            }
                            z2 = true;
                        } else {
                            return true;
                        }
                        if (fillForMissingDividers) {
                            return z2;
                        }
                        return false;
                    }
                    return false;
                }
                boolean isFirstItem = itemIndex == (drawOverscrollHeader ? 1 : 0);
                if (!isFirstItem) {
                    int start = itemIndex - 1;
                    if (!adapter.isEnabled(itemIndex)) {
                        z = true;
                    } else if (!headerDividers && (isHeader || start < headerCount)) {
                        z = true;
                    } else if (!isFirstItem) {
                        if (adapter.isEnabled(start)) {
                            if (footerDividers) {
                                return true;
                            }
                            if (!isFooter && start < footerLimit) {
                                return true;
                            }
                        }
                        z = true;
                    } else {
                        return true;
                    }
                    if (fillForMissingDividers) {
                        return z;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(SemHorizontalListView.class.getName());
    }

    @Override // android.widget.SemHorizontalAbsListView, android.view.View
    @Deprecated
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(SemHorizontalListView.class.getName());
        int count = getCount();
        int selectionMode = getSelectionModeForAccessibility();
        AccessibilityNodeInfo.CollectionInfo collectionInfo =
                AccessibilityNodeInfo.CollectionInfo.obtain(1, count, false, selectionMode);
        info.setCollectionInfo(collectionInfo);
    }

    @Override // android.widget.SemHorizontalAbsListView
    @Deprecated
    public void onInitializeAccessibilityNodeInfoForItem(
            View view, int position, AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoForItem(view, position, info);
        SemHorizontalAbsListView.LayoutParams lp =
                (SemHorizontalAbsListView.LayoutParams) view.getLayoutParams();
        boolean isHeading = lp != null && lp.viewType == -2;
        boolean isSelected = isItemChecked(position);
        AccessibilityNodeInfo.CollectionItemInfo itemInfo =
                AccessibilityNodeInfo.CollectionItemInfo.obtain(
                        0, 1, position, 1, isHeading, isSelected);
        info.setCollectionItemInfo(itemInfo);
    }

    private boolean needToMeasureChild(View child, boolean updateChildSelected, boolean recycled) {
        boolean needToMeasure = !recycled || updateChildSelected;
        if (needToMeasure) {
            return true;
        }
        boolean needToMeasure2 = child.isLayoutRequested();
        if (this.mFixedSizeItems) {
            Object listViewItemTag = child.getTag(268435456);
            if (listViewItemTag == null) {
                listViewItemTag = new ItemInfoTag();
                child.setTag(268435456, listViewItemTag);
            }
            if (listViewItemTag instanceof ItemInfoTag) {
                ItemInfoTag itemInfoTag = (ItemInfoTag) listViewItemTag;
                int currentWidthSpec = getChildWidthSpec(child);
                int currentHeightSpec = getChildHeightSpec(child);
                byte[] offset = {0};
                long currentChildrenVisibilityBitsGone = getChildCountAndOrder(child, offset, 8);
                int currentChildrenNumberTotal = offset[0];
                Configuration currentConfiguration =
                        child.getContext().getResources().getConfiguration();
                if (currentWidthSpec == itemInfoTag.mWidthSpec
                        && currentHeightSpec == itemInfoTag.mHeightSpec
                        && currentChildrenVisibilityBitsGone
                                == itemInfoTag.mChildrenVisibilityBitsGone
                        && currentChildrenNumberTotal > 0
                        && currentChildrenNumberTotal <= 64
                        && currentChildrenNumberTotal == itemInfoTag.mChildrenNumberTotal
                        && currentConfiguration.compareTo(itemInfoTag.mConfiguration) == 0
                        && this.mLastScrollState == 2) {
                    return false;
                }
                itemInfoTag.mWidthSpec = currentWidthSpec;
                itemInfoTag.mHeightSpec = currentHeightSpec;
                itemInfoTag.mChildrenVisibilityBitsGone = currentChildrenVisibilityBitsGone;
                itemInfoTag.mChildrenNumberTotal = currentChildrenNumberTotal;
                itemInfoTag.mConfiguration.setTo(currentConfiguration);
                child.forceLayout();
                return true;
            }
            child.forceLayout();
            return true;
        }
        return needToMeasure2;
    }

    private long getChildCountAndOrder(View child, byte[] offset, int visibility) {
        long count;
        if (child == null) {
            return 0L;
        }
        if (!(child instanceof ViewGroup)) {
            count = child.getVisibility() == visibility ? 0 | (1 << offset[0]) : 0L;
            offset[0] = (byte) (offset[0] + 1);
            return count;
        }
        ViewGroup viewGroup = (ViewGroup) child;
        count = viewGroup.getVisibility() == visibility ? 0 | (1 << offset[0]) : 0L;
        offset[0] = (byte) (offset[0] + 1);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            count |= getChildCountAndOrder(v, offset, visibility);
        }
        return count;
    }

    private int getChildHeightSpec(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-1, -2);
        }
        return ViewGroup.getChildMeasureSpec(
                this.mHeightMeasureSpec,
                this.mListPadding.top + this.mListPadding.bottom,
                p.height);
    }

    private int getChildWidthSpec(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-1, -2);
        }
        int lpWidth = p.width;
        if (lpWidth > 0) {
            int childWidthSpec = View.MeasureSpec.makeMeasureSpec(lpWidth, 1073741824);
            return childWidthSpec;
        }
        int childWidthSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        return childWidthSpec2;
    }

    private class ItemInfoTag {
        protected int mHeightSpec;
        protected int mWidthSpec;
        protected long mChildrenVisibilityBitsGone = 0;
        protected int mChildrenNumberTotal = -1;
        protected Configuration mConfiguration = new Configuration();

        public ItemInfoTag() {
            this.mConfiguration.setToDefaults();
            this.mWidthSpec = -1;
            this.mHeightSpec = -1;
        }
    }
}

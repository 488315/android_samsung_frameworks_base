package android.widget;

import android.app.slice.Slice;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseBooleanArray;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.RemotableViewMethod;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;

import com.android.internal.R;

import com.google.android.collect.Lists;
import com.samsung.android.animation.SemAbsDragAndDropAnimator;
import com.samsung.android.animation.SemAddDeleteListAnimator;
import com.samsung.android.animation.SemDragAndDropListAnimator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@RemoteViews.RemoteView
public class ListView extends AbsListView {
    private static final float MAX_SCROLL_FACTOR = 0.33f;
    private static final int MIN_SCROLL_PREVIEW_PIXELS = 2;
    static final int NO_POSITION = -1;
    static final String TAG = "ListView";
    private SemAddDeleteListAnimator mAddDeleteListAnimator;
    private boolean mAreAllItemsSelectable;
    private final ArrowScrollFocusResult mArrowScrollFocusResult;
    Drawable mDivider;
    int mDividerHeight;
    private boolean mDividerIsOpaque;
    private Paint mDividerPaint;
    private SemDragAndDropListAnimator mDndListAnimator;
    private FocusSelector mFocusSelector;
    private boolean mFooterDividersEnabled;
    ArrayList<FixedViewInfo> mFooterViewInfos;
    private boolean mHeaderDividersEnabled;
    ArrayList<FixedViewInfo> mHeaderViewInfos;
    private boolean mIsCacheColorOpaque;
    private boolean mItemsCanFocus;
    Drawable mOverScrollFooter;
    Drawable mOverScrollHeader;
    private boolean mSelectZeroPositionOnKeyTab;
    private final Rect mTempRect;

    public final class InspectionCompanion
            implements android.view.inspector.InspectionCompanion<ListView> {
        private int mDividerHeightId;
        private int mDividerId;
        private int mFooterDividersEnabledId;
        private int mHeaderDividersEnabledId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mDividerId = propertyMapper.mapObject("divider", 16843049);
            this.mDividerHeightId = propertyMapper.mapInt("dividerHeight", 16843050);
            this.mFooterDividersEnabledId =
                    propertyMapper.mapBoolean("footerDividersEnabled", 16843311);
            this.mHeaderDividersEnabledId =
                    propertyMapper.mapBoolean("headerDividersEnabled", 16843310);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(ListView node, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mDividerId, node.getDivider());
            propertyReader.readInt(this.mDividerHeightId, node.getDividerHeight());
            propertyReader.readBoolean(
                    this.mFooterDividersEnabledId, node.areFooterDividersEnabled());
            propertyReader.readBoolean(
                    this.mHeaderDividersEnabledId, node.areHeaderDividersEnabled());
        }
    }

    public class FixedViewInfo {
        public Object data;
        public boolean isSelectable;
        public View view;

        public FixedViewInfo() {}
    }

    public void setAddDeleteListAnimator(SemAddDeleteListAnimator animator) {
        this.mAddDeleteListAnimator = animator;
    }

    public void setDndListAnimator(SemDragAndDropListAnimator animator) {
        this.mDndListAnimator = animator;
        setChildrenDrawingOrderEnabled(true);
        this.mDndListAnimator.setAutoScrollListener(
                new SemAbsDragAndDropAnimator
                        .SemDragAutoScrollListener() { // from class: android.widget.ListView.1
                    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.SemDragAutoScrollListener
                    public void onAutoScroll(int delta) {
                        ListView.this.trackMotionScroll(delta, delta);
                    }
                });
    }

    public ListView(Context context) {
        this(context, null);
    }

    public ListView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842868);
    }

    public ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        int dividerHeight;
        this.mHeaderViewInfos = Lists.newArrayList();
        this.mFooterViewInfos = Lists.newArrayList();
        this.mAreAllItemsSelectable = true;
        this.mItemsCanFocus = false;
        this.mTempRect = new Rect();
        this.mArrowScrollFocusResult = new ArrowScrollFocusResult();
        this.mSelectZeroPositionOnKeyTab = false;
        TypedArray a =
                context.obtainStyledAttributes(
                        attrs, R.styleable.ListView, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(
                context, R.styleable.ListView, attrs, a, defStyleAttr, defStyleRes);
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
        if (a.hasValueOrEmpty(2) && (dividerHeight = a.getDimensionPixelSize(2, 0)) != 0) {
            setDividerHeight(dividerHeight);
        }
        this.mHeaderDividersEnabled = a.getBoolean(3, true);
        this.mFooterDividersEnabled = a.getBoolean(4, true);
        a.recycle();
    }

    public int getMaxScrollAmount() {
        return (int) ((this.mBottom - this.mTop) * MAX_SCROLL_FACTOR);
    }

    private void adjustViewsUpOrDown() {
        int delta;
        int childCount = getChildCount();
        if (childCount > 0) {
            if (!this.mStackFromBottom) {
                View child = getChildAt(0);
                delta = child.getTop() - this.mListPadding.top;
                if (this.mFirstPosition != 0) {
                    delta -= this.mDividerHeight;
                }
                if (delta < 0) {
                    delta = 0;
                }
            } else {
                View child2 = getChildAt(childCount - 1);
                delta = child2.getBottom() - (getHeight() - this.mListPadding.bottom);
                if (this.mFirstPosition + childCount < this.mItemCount) {
                    delta += this.mDividerHeight;
                }
                if (delta > 0) {
                    delta = 0;
                }
            }
            if (delta != 0) {
                offsetChildrenTopAndBottom(-delta);
            }
        }
    }

    public void addHeaderView(View v, Object data, boolean isSelectable) {
        if (v.getParent() != null && v.getParent() != this && Log.isLoggable(TAG, 5)) {
            Log.w(
                    TAG,
                    "The specified child already has a parent. You must call removeView() on the"
                        + " child's parent first.");
        }
        FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        this.mHeaderViewInfos.add(info);
        this.mAreAllItemsSelectable &= isSelectable;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof HeaderViewListAdapter)) {
                wrapHeaderListAdapterInternal();
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    public void addHeaderView(View v) {
        addHeaderView(v, null, true);
    }

    @Override // android.widget.AbsListView
    public int getHeaderViewsCount() {
        return this.mHeaderViewInfos.size();
    }

    public boolean removeHeaderView(View v) {
        if (this.mHeaderViewInfos.size() > 0) {
            boolean result = false;
            if (this.mAdapter != null && ((HeaderViewListAdapter) this.mAdapter).removeHeader(v)) {
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

    public void addFooterView(View v, Object data, boolean isSelectable) {
        if (v.getParent() != null && v.getParent() != this && Log.isLoggable(TAG, 5)) {
            Log.w(
                    TAG,
                    "The specified child already has a parent. You must call removeView() on the"
                        + " child's parent first.");
        }
        FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        this.mFooterViewInfos.add(info);
        this.mAreAllItemsSelectable &= isSelectable;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof HeaderViewListAdapter)) {
                wrapHeaderListAdapterInternal();
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    public void addFooterView(View v) {
        addFooterView(v, null, true);
    }

    @Override // android.widget.AbsListView
    public int getFooterViewsCount() {
        return this.mFooterViewInfos.size();
    }

    public boolean removeFooterView(View v) {
        if (this.mFooterViewInfos.size() > 0) {
            boolean result = false;
            if (this.mAdapter != null && ((HeaderViewListAdapter) this.mAdapter).removeFooter(v)) {
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

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.widget.AdapterView
    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod(asyncImpl = "setRemoteViewsAdapterAsync")
    public void setRemoteViewsAdapter(Intent intent) {
        super.setRemoteViewsAdapter(intent);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.widget.AbsListView, android.widget.AdapterView
    public void setAdapter(ListAdapter adapter) {
        int position;
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        resetList();
        this.mRecycler.clear();
        if (this.mHeaderViewInfos.size() > 0 || this.mFooterViewInfos.size() > 0) {
            this.mAdapter =
                    wrapHeaderListAdapterInternal(
                            this.mHeaderViewInfos, this.mFooterViewInfos, adapter);
        } else {
            this.mAdapter = adapter;
        }
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        super.setAdapter(adapter);
        if (this.mAdapter != null) {
            this.mAreAllItemsSelectable = this.mAdapter.areAllItemsEnabled();
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            checkFocus();
            this.mDataSetObserver = new AbsListView.AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mRecycler.setViewTypeCount(this.mAdapter.getViewTypeCount());
            if (this.mStackFromBottom) {
                position = lookForSelectablePosition(this.mItemCount - 1, false);
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
        if (semGetRoundedCorners() != 0) {
            this.mSemEnableFillOut = sIsSamsungBasicInteraction;
        }
        requestLayout();
    }

    @Override // android.widget.AbsListView
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
                ViewGroup.LayoutParams params = child.getLayoutParams();
                if (checkLayoutParams(params)) {
                    ((AbsListView.LayoutParams) params).recycledHeaderFooter = false;
                }
            }
        }
    }

    private boolean showingTopFadingEdge() {
        int listTop = this.mScrollY + this.mListPadding.top;
        return this.mFirstPosition > 0 || getChildAt(0).getTop() > listTop;
    }

    private boolean showingBottomFadingEdge() {
        int childCount = getChildCount();
        int bottomOfBottomChild = getChildAt(childCount - 1).getBottom();
        int lastVisiblePosition = (this.mFirstPosition + childCount) - 1;
        int listBottom = (this.mScrollY + getHeight()) - this.mListPadding.bottom;
        return lastVisiblePosition < this.mItemCount - 1 || bottomOfBottomChild < listBottom;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View child, Rect rect, boolean immediate) {
        int rectTopWithinChild = rect.top;
        rect.offset(child.getLeft(), child.getTop());
        rect.offset(-child.getScrollX(), -child.getScrollY());
        int height = getHeight();
        int listUnfadedTop = getScrollY();
        int listUnfadedBottom = listUnfadedTop + height;
        int fadingEdge = getVerticalFadingEdgeLength();
        if (showingTopFadingEdge()
                && (this.mSelectedPosition > 0 || rectTopWithinChild > fadingEdge)) {
            listUnfadedTop += fadingEdge;
        }
        int childCount = getChildCount();
        int bottomOfBottomChild = getChildAt(childCount - 1).getBottom();
        if (showingBottomFadingEdge()
                && (this.mSelectedPosition < this.mItemCount - 1
                        || rect.bottom < bottomOfBottomChild - fadingEdge)) {
            listUnfadedBottom -= fadingEdge;
        }
        int scrollYDelta = 0;
        if (rect.bottom > listUnfadedBottom && rect.top > listUnfadedTop) {
            int distanceToBottom = bottomOfBottomChild - listUnfadedBottom;
            scrollYDelta =
                    Math.min(
                            rect.height() > height
                                    ? 0 + (rect.top - listUnfadedTop)
                                    : 0 + (rect.bottom - listUnfadedBottom),
                            distanceToBottom);
        } else if (rect.top < listUnfadedTop && rect.bottom < listUnfadedBottom) {
            int scrollYDelta2 =
                    rect.height() > height
                            ? 0 - (listUnfadedBottom - rect.bottom)
                            : 0 - (listUnfadedTop - rect.top);
            int top = getChildAt(0).getTop();
            int deltaToTop = top - listUnfadedTop;
            scrollYDelta = Math.max(scrollYDelta2, deltaToTop);
        }
        boolean scroll = scrollYDelta != 0;
        if (scroll) {
            scrollListItemsBy(-scrollYDelta);
            positionSelector(-1, child);
            this.mSelectedTop = child.getTop();
            invalidate();
        }
        return scroll;
    }

    @Override // android.widget.AbsListView
    void fillGap(boolean down) {
        int count = getChildCount();
        if (down) {
            int paddingTop = 0;
            if ((this.mGroupFlags & 34) == 34) {
                paddingTop = getListPaddingTop();
            }
            int startOffset =
                    count > 0
                            ? getChildAt(count - 1).getBottom() + this.mDividerHeight
                            : paddingTop;
            fillDown(this.mFirstPosition + count, startOffset);
            correctTooHigh(getChildCount());
            return;
        }
        int paddingBottom = 0;
        if ((this.mGroupFlags & 34) == 34) {
            paddingBottom = getListPaddingBottom();
        }
        int startOffset2 =
                count > 0
                        ? getChildAt(0).getTop() - this.mDividerHeight
                        : getHeight() - paddingBottom;
        fillUp(this.mFirstPosition - 1, startOffset2);
        correctTooLow(getChildCount());
    }

    private View fillDown(int pos, int nextTop) {
        View selectedView = null;
        int end = this.mBottom - this.mTop;
        if ((this.mGroupFlags & 34) == 34) {
            end -= this.mListPadding.bottom;
        }
        while (true) {
            if (nextTop >= end || pos >= this.mItemCount) {
                break;
            }
            boolean selected = pos == this.mSelectedPosition;
            View child = makeAndAddView(pos, nextTop, true, this.mListPadding.left, selected);
            nextTop = child.getBottom() + this.mDividerHeight;
            if (selected) {
                selectedView = child;
            }
            pos++;
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return selectedView;
    }

    private View fillUp(int pos, int nextBottom) {
        View selectedView = null;
        int end = 0;
        if ((this.mGroupFlags & 34) == 34) {
            end = this.mListPadding.top;
        }
        while (true) {
            if (nextBottom <= end || pos < 0) {
                break;
            }
            boolean selected = pos == this.mSelectedPosition;
            View child = makeAndAddView(pos, nextBottom, false, this.mListPadding.left, selected);
            nextBottom = child.getTop() - this.mDividerHeight;
            if (selected) {
                selectedView = child;
            }
            pos--;
        }
        this.mFirstPosition = pos + 1;
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return selectedView;
    }

    private View fillFromTop(int nextTop) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return fillDown(this.mFirstPosition, nextTop);
    }

    private View fillFromMiddle(int childrenTop, int childrenBottom) {
        int height = childrenBottom - childrenTop;
        int position = reconcileSelectedPosition();
        View sel = makeAndAddView(position, childrenTop, true, this.mListPadding.left, true);
        this.mFirstPosition = position;
        int selHeight = sel.getMeasuredHeight();
        if (selHeight <= height) {
            sel.offsetTopAndBottom((height - selHeight) / 2);
        }
        fillAboveAndBelow(sel, position);
        if (!this.mStackFromBottom) {
            correctTooHigh(getChildCount());
        } else {
            correctTooLow(getChildCount());
        }
        return sel;
    }

    private void fillAboveAndBelow(View sel, int position) {
        int dividerHeight = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            fillUp(position - 1, sel.getTop() - dividerHeight);
            adjustViewsUpOrDown();
            fillDown(position + 1, sel.getBottom() + dividerHeight);
        } else {
            fillDown(position + 1, sel.getBottom() + dividerHeight);
            adjustViewsUpOrDown();
            fillUp(position - 1, sel.getTop() - dividerHeight);
        }
    }

    private View fillFromSelection(int selectedTop, int childrenTop, int childrenBottom) {
        int fadingEdgeLength = getVerticalFadingEdgeLength();
        int selectedPosition = this.mSelectedPosition;
        int topSelectionPixel =
                getTopSelectionPixel(childrenTop, fadingEdgeLength, selectedPosition);
        int bottomSelectionPixel =
                getBottomSelectionPixel(childrenBottom, fadingEdgeLength, selectedPosition);
        View sel =
                makeAndAddView(selectedPosition, selectedTop, true, this.mListPadding.left, true);
        if (sel.getBottom() > bottomSelectionPixel) {
            int spaceAbove = sel.getTop() - topSelectionPixel;
            int spaceBelow = sel.getBottom() - bottomSelectionPixel;
            int offset = Math.min(spaceAbove, spaceBelow);
            sel.offsetTopAndBottom(-offset);
        } else if (sel.getTop() < topSelectionPixel) {
            int spaceAbove2 = topSelectionPixel - sel.getTop();
            int spaceBelow2 = bottomSelectionPixel - sel.getBottom();
            int offset2 = Math.min(spaceAbove2, spaceBelow2);
            sel.offsetTopAndBottom(offset2);
        }
        fillAboveAndBelow(sel, selectedPosition);
        if (!this.mStackFromBottom) {
            correctTooHigh(getChildCount());
        } else {
            correctTooLow(getChildCount());
        }
        return sel;
    }

    private int getBottomSelectionPixel(
            int childrenBottom, int fadingEdgeLength, int selectedPosition) {
        if (selectedPosition == this.mItemCount - 1) {
            return childrenBottom;
        }
        int bottomSelectionPixel = childrenBottom - fadingEdgeLength;
        return bottomSelectionPixel;
    }

    private int getTopSelectionPixel(int childrenTop, int fadingEdgeLength, int selectedPosition) {
        if (selectedPosition <= 0) {
            return childrenTop;
        }
        int topSelectionPixel = childrenTop + fadingEdgeLength;
        return topSelectionPixel;
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void smoothScrollToPosition(int position) {
        super.smoothScrollToPosition(position);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void smoothScrollByOffset(int offset) {
        super.smoothScrollByOffset(offset);
    }

    private View moveSelection(
            View oldSel, View newSel, int delta, int childrenTop, int childrenBottom) {
        View sel;
        int fadingEdgeLength = getVerticalFadingEdgeLength();
        int selectedPosition = this.mSelectedPosition;
        int topSelectionPixel =
                getTopSelectionPixel(childrenTop, fadingEdgeLength, selectedPosition);
        int bottomSelectionPixel =
                getBottomSelectionPixel(childrenTop, fadingEdgeLength, selectedPosition);
        if (delta > 0) {
            View oldSel2 =
                    makeAndAddView(
                            selectedPosition - 1,
                            oldSel.getTop(),
                            true,
                            this.mListPadding.left,
                            false);
            int dividerHeight = this.mDividerHeight;
            sel =
                    makeAndAddView(
                            selectedPosition,
                            oldSel2.getBottom() + dividerHeight,
                            true,
                            this.mListPadding.left,
                            true);
            if (sel.getBottom() > bottomSelectionPixel) {
                int spaceAbove = sel.getTop() - topSelectionPixel;
                int spaceBelow = sel.getBottom() - bottomSelectionPixel;
                int halfVerticalSpace = (childrenBottom - childrenTop) / 2;
                int offset = Math.min(spaceAbove, spaceBelow);
                int offset2 = Math.min(offset, halfVerticalSpace);
                oldSel2.offsetTopAndBottom(-offset2);
                sel.offsetTopAndBottom(-offset2);
            }
            if (!this.mStackFromBottom) {
                fillUp(this.mSelectedPosition - 2, sel.getTop() - dividerHeight);
                adjustViewsUpOrDown();
                fillDown(this.mSelectedPosition + 1, sel.getBottom() + dividerHeight);
            } else {
                fillDown(this.mSelectedPosition + 1, sel.getBottom() + dividerHeight);
                adjustViewsUpOrDown();
                fillUp(this.mSelectedPosition - 2, sel.getTop() - dividerHeight);
            }
        } else if (delta < 0) {
            sel =
                    newSel != null
                            ? makeAndAddView(
                                    selectedPosition,
                                    newSel.getTop(),
                                    true,
                                    this.mListPadding.left,
                                    true)
                            : makeAndAddView(
                                    selectedPosition,
                                    oldSel.getTop(),
                                    false,
                                    this.mListPadding.left,
                                    true);
            if (sel.getTop() < topSelectionPixel) {
                int spaceAbove2 = topSelectionPixel - sel.getTop();
                int spaceBelow2 = bottomSelectionPixel - sel.getBottom();
                int halfVerticalSpace2 = (childrenBottom - childrenTop) / 2;
                int offset3 = Math.min(spaceAbove2, spaceBelow2);
                sel.offsetTopAndBottom(Math.min(offset3, halfVerticalSpace2));
            }
            fillAboveAndBelow(sel, selectedPosition);
        } else {
            int oldTop = oldSel.getTop();
            sel = makeAndAddView(selectedPosition, oldTop, true, this.mListPadding.left, true);
            if (oldTop < childrenTop) {
                int newBottom = sel.getBottom();
                if (newBottom < childrenTop + 20) {
                    sel.offsetTopAndBottom(childrenTop - sel.getTop());
                }
            }
            fillAboveAndBelow(sel, selectedPosition);
        }
        return sel;
    }

    private class FocusSelector implements Runnable {
        private static final int STATE_REQUEST_FOCUS = 3;
        private static final int STATE_SET_SELECTION = 1;
        private static final int STATE_WAIT_FOR_LAYOUT = 2;
        private int mAction;
        private int mPosition;
        private int mPositionTop;

        private FocusSelector() {}

        FocusSelector setupForSetSelection(int position, int top) {
            this.mPosition = position;
            this.mPositionTop = top;
            this.mAction = 1;
            return this;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mAction == 1) {
                ListView.this.setSelectionFromTop(this.mPosition, this.mPositionTop);
                this.mAction = 2;
            } else if (this.mAction == 3) {
                int childIndex = this.mPosition - ListView.this.mFirstPosition;
                View child = ListView.this.getChildAt(childIndex);
                if (child != null) {
                    child.requestFocus();
                }
                this.mAction = -1;
            }
        }

        Runnable setupFocusIfValid(int position) {
            if (this.mAction != 2 || position != this.mPosition) {
                return null;
            }
            this.mAction = 3;
            return this;
        }

        void onLayoutComplete() {
            if (this.mAction == 2) {
                this.mAction = -1;
            }
        }
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup,
              // android.view.View
    protected void onDetachedFromWindow() {
        if (this.mFocusSelector != null) {
            removeCallbacks(this.mFocusSelector);
            this.mFocusSelector = null;
        }
        if (this.mAppWidgetInnerFocus) {
            this.mClickableViewStates.clear();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        View focusedChild;
        if (getChildCount() > 0 && (focusedChild = getFocusedChild()) != null) {
            int childPosition = this.mFirstPosition + indexOfChild(focusedChild);
            int childBottom = focusedChild.getBottom();
            int offset = Math.max(0, childBottom - (h - this.mPaddingTop));
            int top = focusedChild.getTop() - offset;
            if (this.mFocusSelector == null) {
                this.mFocusSelector = new FocusSelector();
            }
            post(this.mFocusSelector.setupForSetSelection(childPosition, top));
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize;
        int heightSize;
        View child;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthSize2 = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = 0;
        int childHeight = 0;
        int childState = 0;
        this.mItemCount = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        if (this.mItemCount > 0
                && ((widthMode == 0 || heightMode == 0)
                        && (child = obtainView(0, this.mIsScrap)) != null)) {
            measureScrapChild(child, 0, widthMeasureSpec, heightSize2);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            childState = combineMeasuredStates(0, child.getMeasuredState());
            if (recycleOnMeasure()
                    && this.mRecycler.shouldRecycleViewType(
                            ((AbsListView.LayoutParams) child.getLayoutParams()).viewType)) {
                this.mRecycler.addScrapView(child, -1);
            }
        }
        int childWidth2 = childWidth;
        int childHeight2 = childHeight;
        int childState2 = childState;
        if (widthMode == 0) {
            widthSize =
                    this.mListPadding.left
                            + this.mListPadding.right
                            + childWidth2
                            + getVerticalScrollbarWidth();
        } else {
            widthSize = ((-16777216) & childState2) | widthSize2;
        }
        if (heightMode != 0) {
            heightSize = heightSize2;
        } else {
            heightSize =
                    this.mListPadding.top
                            + this.mListPadding.bottom
                            + childHeight2
                            + (getVerticalFadingEdgeLength() * 2);
        }
        if (heightMode == Integer.MIN_VALUE) {
            heightSize = measureHeightOfChildren(widthMeasureSpec, 0, -1, heightSize, -1);
        }
        setMeasuredDimension(widthSize, heightSize);
        this.mWidthMeasureSpec = widthMeasureSpec;
    }

    private void measureScrapChild(View child, int position, int widthMeasureSpec, int heightHint) {
        int childHeightSpec;
        AbsListView.LayoutParams p = (AbsListView.LayoutParams) child.getLayoutParams();
        if (p == null) {
            p = (AbsListView.LayoutParams) generateDefaultLayoutParams();
            child.setLayoutParams(p);
        }
        p.viewType = this.mAdapter.getItemViewType(position);
        p.isEnabled = this.mAdapter.isEnabled(position);
        p.forceAdd = true;
        int childWidthSpec =
                ViewGroup.getChildMeasureSpec(
                        widthMeasureSpec,
                        this.mListPadding.left + this.mListPadding.right,
                        p.width);
        int lpHeight = p.height;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            childHeightSpec = View.MeasureSpec.makeSafeMeasureSpec(heightHint, 0);
        }
        child.measure(childWidthSpec, childHeightSpec);
        child.forceLayout();
    }

    @ViewDebug.ExportedProperty(category = Slice.HINT_LIST)
    protected boolean recycleOnMeasure() {
        return true;
    }

    final int measureHeightOfChildren(
            int widthMeasureSpec,
            int startPosition,
            int endPosition,
            int maxHeight,
            int disallowPartialChildPosition) {
        ListAdapter adapter = this.mAdapter;
        if (adapter == null) {
            return this.mListPadding.top + this.mListPadding.bottom;
        }
        int returnedHeight = this.mListPadding.top + this.mListPadding.bottom;
        int dividerHeight = this.mDividerHeight;
        int prevHeightWithoutPartialChild = 0;
        int endPosition2 = endPosition == -1 ? adapter.getCount() - 1 : endPosition;
        AbsListView.RecycleBin recycleBin = this.mRecycler;
        boolean recyle = recycleOnMeasure();
        boolean[] isScrap = this.mIsScrap;
        for (int i = startPosition; i <= endPosition2; i++) {
            View child = obtainView(i, isScrap);
            measureScrapChild(child, i, widthMeasureSpec, maxHeight);
            if (i > 0) {
                returnedHeight += dividerHeight;
            }
            if (recyle
                    && recycleBin.shouldRecycleViewType(
                            ((AbsListView.LayoutParams) child.getLayoutParams()).viewType)) {
                recycleBin.addScrapView(child, -1);
            }
            returnedHeight += child.getMeasuredHeight();
            if (returnedHeight >= maxHeight) {
                if (disallowPartialChildPosition >= 0
                        && i > disallowPartialChildPosition
                        && prevHeightWithoutPartialChild > 0
                        && returnedHeight != maxHeight) {
                    return prevHeightWithoutPartialChild;
                }
                return maxHeight;
            }
            if (disallowPartialChildPosition >= 0 && i >= disallowPartialChildPosition) {
                prevHeightWithoutPartialChild = returnedHeight;
            }
        }
        return returnedHeight;
    }

    @Override // android.widget.AbsListView
    int findMotionRow(int y) {
        int childCount = getChildCount();
        if (childCount > 0) {
            if (!this.mStackFromBottom) {
                for (int i = 0; i < childCount; i++) {
                    View v = getChildAt(i);
                    if (y <= v.getBottom()) {
                        return this.mFirstPosition + i;
                    }
                }
                return -1;
            }
            for (int i2 = childCount - 1; i2 >= 0; i2--) {
                View v2 = getChildAt(i2);
                if (y >= v2.getTop()) {
                    return this.mFirstPosition + i2;
                }
            }
            return -1;
        }
        return -1;
    }

    private View fillSpecific(int position, int top) {
        View below;
        View above;
        boolean tempIsSelected = position == this.mSelectedPosition;
        View temp = makeAndAddView(position, top, true, this.mListPadding.left, tempIsSelected);
        this.mFirstPosition = position;
        int dividerHeight = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            above = fillUp(position - 1, temp.getTop() - dividerHeight);
            adjustViewsUpOrDown();
            below = fillDown(position + 1, temp.getBottom() + dividerHeight);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHigh(childCount);
            }
        } else {
            below = fillDown(position + 1, temp.getBottom() + dividerHeight);
            adjustViewsUpOrDown();
            above = fillUp(position - 1, temp.getTop() - dividerHeight);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLow(childCount2);
            }
        }
        if (tempIsSelected) {
            return temp;
        }
        if (above != null) {
            return above;
        }
        return below;
    }

    private void correctTooHigh(int childCount) {
        int lastPosition = (this.mFirstPosition + childCount) - 1;
        if (lastPosition == this.mItemCount - 1 && childCount > 0) {
            View lastChild = getChildAt(childCount - 1);
            int lastBottom = lastChild.getBottom();
            int end = (this.mBottom - this.mTop) - this.mListPadding.bottom;
            int bottomOffset = end - lastBottom;
            View firstChild = getChildAt(0);
            int firstTop = firstChild.getTop();
            if (bottomOffset > 0) {
                if (this.mFirstPosition > 0 || firstTop < this.mListPadding.top) {
                    if (this.mFirstPosition == 0) {
                        bottomOffset = Math.min(bottomOffset, this.mListPadding.top - firstTop);
                    }
                    offsetChildrenTopAndBottom(bottomOffset);
                    if (this.mFirstPosition > 0) {
                        fillUp(this.mFirstPosition - 1, firstChild.getTop() - this.mDividerHeight);
                        adjustViewsUpOrDown();
                    }
                }
            }
        }
    }

    private void correctTooLow(int childCount) {
        if (this.mFirstPosition == 0 && childCount > 0) {
            View firstChild = getChildAt(0);
            int firstTop = firstChild.getTop();
            int start = this.mListPadding.top;
            int end = (this.mBottom - this.mTop) - this.mListPadding.bottom;
            int topOffset = firstTop - start;
            View lastChild = getChildAt(childCount - 1);
            int lastBottom = lastChild.getBottom();
            int lastPosition = (this.mFirstPosition + childCount) - 1;
            if (topOffset > 0) {
                if (lastPosition < this.mItemCount - 1 || lastBottom > end) {
                    if (lastPosition == this.mItemCount - 1) {
                        topOffset = Math.min(topOffset, lastBottom - end);
                    }
                    offsetChildrenTopAndBottom(-topOffset);
                    if (lastPosition < this.mItemCount - 1) {
                        fillDown(lastPosition + 1, lastChild.getBottom() + this.mDividerHeight);
                        adjustViewsUpOrDown();
                        return;
                    }
                    return;
                }
                if (lastPosition == this.mItemCount - 1) {
                    adjustViewsUpOrDown();
                }
            }
        }
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.mDndListAnimator != null && this.mDndListAnimator.onInterceptTouchEvent(ev)) {
            return true;
        }
        if (this.mSweepListAnimator != null
                && this.mSweepListAnimator.isSweepAnimatorEnabled()
                && this.mSweepListAnimator.onInterceptTouchEvent(ev)) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mDndListAnimator != null && this.mDndListAnimator.onTouchEvent(ev)) {
            return true;
        }
        if (this.mSweepListAnimator != null
                && this.mSweepListAnimator.isSweepAnimatorEnabled()
                && this.mSweepListAnimator.onTouchEvent(ev)) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int childCount, int i) {
        return this.mDndListAnimator != null
                ? this.mDndListAnimator.getChildDrawingOrder(childCount, i)
                : super.getChildDrawingOrder(childCount, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:183:0x0328 A[Catch: all -> 0x04a6, TryCatch #0 {all -> 0x04a6, blocks: (B:7:0x000b, B:9:0x0015, B:17:0x0029, B:18:0x0045, B:19:0x0048, B:20:0x0072, B:23:0x007a, B:24:0x007f, B:26:0x0088, B:27:0x008e, B:28:0x009c, B:30:0x00a2, B:31:0x00a5, B:33:0x00a9, B:41:0x00bd, B:43:0x00c7, B:45:0x00d8, B:47:0x00de, B:52:0x00e9, B:54:0x00ef, B:56:0x00f5, B:58:0x0100, B:59:0x0119, B:62:0x0125, B:64:0x012b, B:66:0x0131, B:68:0x0145, B:70:0x014b, B:72:0x0159, B:76:0x0164, B:79:0x017d, B:80:0x0185, B:83:0x0196, B:85:0x0266, B:86:0x02c0, B:88:0x02d0, B:90:0x02d4, B:92:0x02da, B:97:0x02e6, B:101:0x02f7, B:103:0x02fd, B:104:0x0300, B:106:0x0311, B:109:0x0374, B:112:0x037c, B:114:0x0383, B:117:0x038c, B:119:0x039b, B:122:0x03a2, B:124:0x03b7, B:128:0x03bf, B:130:0x03d0, B:131:0x03e1, B:134:0x03e9, B:136:0x03ee, B:137:0x03dd, B:138:0x03f1, B:140:0x03f5, B:143:0x0422, B:145:0x0428, B:146:0x042b, B:148:0x0436, B:149:0x043d, B:151:0x044c, B:152:0x044f, B:160:0x03f9, B:162:0x03fd, B:165:0x0409, B:168:0x041a, B:169:0x0416, B:170:0x041d, B:171:0x0304, B:172:0x02ec, B:175:0x030e, B:176:0x0318, B:178:0x031d, B:183:0x0328, B:185:0x0333, B:187:0x0366, B:190:0x036e, B:191:0x0339, B:193:0x033d, B:195:0x0343, B:197:0x0347, B:199:0x0352, B:200:0x0358, B:202:0x035c, B:204:0x0276, B:205:0x028c, B:207:0x0290, B:209:0x0296, B:212:0x02a0, B:213:0x029c, B:214:0x02a5, B:216:0x02ab, B:219:0x02b5, B:220:0x02b1, B:221:0x02ba, B:222:0x019a, B:223:0x01b4, B:225:0x01c4, B:226:0x01d3, B:227:0x01dd, B:229:0x01f5, B:231:0x01f9, B:233:0x0201, B:234:0x0204, B:236:0x0208, B:238:0x020c, B:240:0x0210, B:242:0x0216, B:245:0x023d, B:246:0x0247, B:247:0x024d, B:248:0x0178, B:249:0x0135, B:251:0x013d, B:255:0x00f9, B:259:0x0465, B:260:0x04a5, B:261:0x004b, B:264:0x0055), top: B:6:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0339 A[Catch: all -> 0x04a6, TryCatch #0 {all -> 0x04a6, blocks: (B:7:0x000b, B:9:0x0015, B:17:0x0029, B:18:0x0045, B:19:0x0048, B:20:0x0072, B:23:0x007a, B:24:0x007f, B:26:0x0088, B:27:0x008e, B:28:0x009c, B:30:0x00a2, B:31:0x00a5, B:33:0x00a9, B:41:0x00bd, B:43:0x00c7, B:45:0x00d8, B:47:0x00de, B:52:0x00e9, B:54:0x00ef, B:56:0x00f5, B:58:0x0100, B:59:0x0119, B:62:0x0125, B:64:0x012b, B:66:0x0131, B:68:0x0145, B:70:0x014b, B:72:0x0159, B:76:0x0164, B:79:0x017d, B:80:0x0185, B:83:0x0196, B:85:0x0266, B:86:0x02c0, B:88:0x02d0, B:90:0x02d4, B:92:0x02da, B:97:0x02e6, B:101:0x02f7, B:103:0x02fd, B:104:0x0300, B:106:0x0311, B:109:0x0374, B:112:0x037c, B:114:0x0383, B:117:0x038c, B:119:0x039b, B:122:0x03a2, B:124:0x03b7, B:128:0x03bf, B:130:0x03d0, B:131:0x03e1, B:134:0x03e9, B:136:0x03ee, B:137:0x03dd, B:138:0x03f1, B:140:0x03f5, B:143:0x0422, B:145:0x0428, B:146:0x042b, B:148:0x0436, B:149:0x043d, B:151:0x044c, B:152:0x044f, B:160:0x03f9, B:162:0x03fd, B:165:0x0409, B:168:0x041a, B:169:0x0416, B:170:0x041d, B:171:0x0304, B:172:0x02ec, B:175:0x030e, B:176:0x0318, B:178:0x031d, B:183:0x0328, B:185:0x0333, B:187:0x0366, B:190:0x036e, B:191:0x0339, B:193:0x033d, B:195:0x0343, B:197:0x0347, B:199:0x0352, B:200:0x0358, B:202:0x035c, B:204:0x0276, B:205:0x028c, B:207:0x0290, B:209:0x0296, B:212:0x02a0, B:213:0x029c, B:214:0x02a5, B:216:0x02ab, B:219:0x02b5, B:220:0x02b1, B:221:0x02ba, B:222:0x019a, B:223:0x01b4, B:225:0x01c4, B:226:0x01d3, B:227:0x01dd, B:229:0x01f5, B:231:0x01f9, B:233:0x0201, B:234:0x0204, B:236:0x0208, B:238:0x020c, B:240:0x0210, B:242:0x0216, B:245:0x023d, B:246:0x0247, B:247:0x024d, B:248:0x0178, B:249:0x0135, B:251:0x013d, B:255:0x00f9, B:259:0x0465, B:260:0x04a5, B:261:0x004b, B:264:0x0055), top: B:6:0x000b }] */
    @Override // android.widget.AbsListView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void layoutChildren() {
        /*
            Method dump skipped, instructions count: 1236
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.widget.ListView.layoutChildren():void");
    }

    @Override // android.widget.AbsListView
    boolean trackMotionScroll(int deltaY, int incrementalDeltaY) {
        boolean result = super.trackMotionScroll(deltaY, incrementalDeltaY);
        removeUnusedFixedViews(this.mHeaderViewInfos);
        removeUnusedFixedViews(this.mFooterViewInfos);
        return result;
    }

    private void removeUnusedFixedViews(List<FixedViewInfo> infoList) {
        if (infoList == null) {
            return;
        }
        for (int i = infoList.size() - 1; i >= 0; i--) {
            FixedViewInfo fixedViewInfo = infoList.get(i);
            View view = fixedViewInfo.view;
            AbsListView.LayoutParams lp = (AbsListView.LayoutParams) view.getLayoutParams();
            if (view.getParent() == null && lp != null && lp.recycledHeaderFooter) {
                removeDetachedView(view, false);
                lp.recycledHeaderFooter = false;
            }
        }
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
            int position, int y, boolean flow, int childrenLeft, boolean selected) {
        View activeView;
        if (!this.mDataChanged && (activeView = this.mRecycler.getActiveView(position)) != null) {
            setupChild(activeView, position, y, flow, childrenLeft, selected, true);
            return activeView;
        }
        View child = obtainView(position, this.mIsScrap);
        if (child != null) {
            setupChild(child, position, y, flow, childrenLeft, selected, this.mIsScrap[0]);
        }
        return child;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setupChild(
            View view,
            int position,
            int y,
            boolean flowDown,
            int childrenLeft,
            boolean selected,
            boolean isAttachedToWindow) {
        int childHeightSpec;
        Trace.traceBegin(8L, "setupListItem");
        boolean isSelected = selected && shouldShowSelector();
        boolean updateChildSelected = isSelected != view.isSelected();
        int mode = this.mTouchMode;
        boolean isPressed = mode > 0 && mode < 3 && this.mMotionPosition == position;
        boolean updateChildPressed = isPressed != view.isPressed();
        boolean needToMeasure =
                !isAttachedToWindow || updateChildSelected || view.isLayoutRequested();
        AbsListView.LayoutParams p = (AbsListView.LayoutParams) view.getLayoutParams();
        if (p == null) {
            p = (AbsListView.LayoutParams) generateDefaultLayoutParams();
        }
        p.viewType = this.mAdapter.getItemViewType(position);
        p.isEnabled = this.mAdapter.isEnabled(position);
        if (updateChildSelected) {
            view.setSelected(isSelected);
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
        if ((isAttachedToWindow && !p.forceAdd) || (p.recycledHeaderFooter && p.viewType == -2)) {
            attachViewToParent(view, flowDown ? -1 : 0, p);
            if (isAttachedToWindow
                    && ((AbsListView.LayoutParams) view.getLayoutParams()).scrappedFromPosition
                            != position) {
                view.jumpDrawablesToCurrentState();
            }
        } else {
            p.forceAdd = false;
            if (p.viewType == -2) {
                p.recycledHeaderFooter = true;
            }
            addViewInLayout(view, flowDown ? -1 : 0, p, true);
            view.resolveRtlPropertiesIfNeeded();
        }
        if (needToMeasure) {
            int childWidthSpec =
                    ViewGroup.getChildMeasureSpec(
                            this.mWidthMeasureSpec,
                            this.mListPadding.left + this.mListPadding.right,
                            p.width);
            int lpHeight = p.height;
            if (lpHeight > 0) {
                childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
            } else {
                childHeightSpec = View.MeasureSpec.makeSafeMeasureSpec(getMeasuredHeight(), 0);
            }
            view.measure(childWidthSpec, childHeightSpec);
        } else {
            cleanupLayoutState(view);
        }
        int w = view.getMeasuredWidth();
        int h = view.getMeasuredHeight();
        int childTop = flowDown ? y : y - h;
        if (needToMeasure) {
            int childRight = childrenLeft + w;
            int childBottom = childTop + h;
            view.layout(childrenLeft, childTop, childRight, childBottom);
        } else {
            view.offsetLeftAndRight(childrenLeft - view.getLeft());
            view.offsetTopAndBottom(childTop - view.getTop());
        }
        if (this.mCachingStarted && !view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }
        Trace.traceEnd(8L);
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup
    protected boolean canAnimate() {
        return super.canAnimate() && this.mItemCount > 0;
    }

    @Override // android.widget.AdapterView
    @RemotableViewMethod
    public void setSelection(int position) {
        setSelectionFromTop(position, 0);
    }

    @Override // android.widget.AbsListView
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
            if (lookDown) {
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
        if (lookDown) {
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

    public void setSelectionAfterHeaderView() {
        int count = getHeaderViewsCount();
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

    @Override // android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return commonKey(keyCode, 1, event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return commonKey(keyCode, repeatCount, event);
    }

    @Override // android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return commonKey(keyCode, 1, event);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:43:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0303  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean commonKey(int r21, int r22, android.view.KeyEvent r23) {
        /*
            Method dump skipped, instructions count: 848
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.widget.ListView.commonKey(int, int,"
                    + " android.view.KeyEvent):boolean");
    }

    boolean pageScroll(int direction) {
        int nextPage;
        boolean down;
        int position;
        if (direction == 33) {
            nextPage = Math.max(0, (this.mSelectedPosition - getChildCount()) - 1);
            down = false;
        } else {
            if (direction != 130) {
                return false;
            }
            nextPage =
                    Math.min(this.mItemCount - 1, (this.mSelectedPosition + getChildCount()) - 1);
            down = true;
        }
        if (nextPage < 0
                || (position =
                                lookForSelectablePositionAfter(
                                        this.mSelectedPosition, nextPage, down))
                        < 0) {
            return false;
        }
        this.mLayoutMode = 4;
        this.mSpecificTop = this.mPaddingTop + getVerticalFadingEdgeLength();
        if (down && position > this.mItemCount - getChildCount()) {
            this.mLayoutMode = 3;
        }
        if (!down && position < getChildCount()) {
            this.mLayoutMode = 1;
        }
        setSelectionInt(position);
        semShowGoToTOP();
        invokeOnItemScrollListener();
        if (!awakenScrollBars()) {
            invalidate();
        }
        return true;
    }

    boolean fullScroll(int direction) {
        int lastItem;
        boolean moved = false;
        if (direction == 33) {
            if (this.mSelectedPosition != 0) {
                int position = lookForSelectablePositionAfter(this.mSelectedPosition, 0, true);
                if (position >= 0) {
                    this.mLayoutMode = 1;
                    setSelectionInt(position);
                    invokeOnItemScrollListener();
                }
                moved = true;
            }
        } else if (direction == 130 && this.mSelectedPosition < (lastItem = this.mItemCount - 1)) {
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
            semShowGoToTOP();
            invalidate();
        }
        return moved;
    }

    private boolean handleHorizontalFocusWithinListItem(int direction) {
        View selectedView;
        if (direction != 17 && direction != 66) {
            throw new IllegalArgumentException(
                    "direction must be one of {View.FOCUS_LEFT, View.FOCUS_RIGHT}");
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
                if (currentFocus != null) {
                    currentFocus.getFocusedRect(this.mTempRect);
                    offsetDescendantRectToMyCoords(currentFocus, this.mTempRect);
                    offsetRectIntoDescendantCoords(nextFocus, this.mTempRect);
                }
                if (nextFocus.requestFocus(direction, this.mTempRect)) {
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
            return false;
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
        int i;
        int nextSelected;
        if (direction == 130) {
            int listBottom = getHeight() - this.mListPadding.bottom;
            if (selectedView == null || selectedView.getBottom() > listBottom) {
                return -1;
            }
            if (selectedPos != -1 && selectedPos >= this.mFirstPosition) {
                nextSelected = selectedPos + 1;
            } else {
                nextSelected = this.mFirstPosition;
            }
        } else {
            int listTop = this.mListPadding.top;
            if (selectedView == null || selectedView.getTop() < listTop) {
                return -1;
            }
            int lastPos = (this.mFirstPosition + getChildCount()) - 1;
            if (selectedPos != -1 && selectedPos <= lastPos) {
                i = selectedPos - 1;
            } else {
                i = lastPos;
            }
            nextSelected = i;
        }
        if (nextSelected < 0 || nextSelected >= this.mAdapter.getCount()) {
            return -1;
        }
        return lookForSelectablePosition(nextSelected, direction == 130);
    }

    private boolean arrowScrollImpl(int direction) {
        View focused;
        View focused2;
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
        boolean handledAppWidget = false;
        if (this.mAppWidgetSnapScroll) {
            if (selectedView == null) {
                return false;
            }
            int listBottom = getHeight() - this.mListPadding.bottom;
            if (direction == 33 && selectedPos > 0 && amountToScroll > 0) {
                if (selectedView.getHeight() > listBottom - this.mListPadding.top
                        && selectedView.getTop() != 0) {
                    if (selectedView.getTop() + amountToScroll > 0) {
                        amountToScroll = -selectedView.getTop();
                    }
                } else {
                    nextSelectedPosition = selectedPos - 1;
                    smoothScrollToPosition(nextSelectedPosition);
                    handledAppWidget = true;
                }
            } else if (direction == 130
                    && selectedPos < this.mAdapter.getCount() - 1
                    && amountToScroll > 0
                    && selectedView.getHeight() == listBottom - this.mListPadding.top) {
                nextSelectedPosition = selectedPos + 1;
                smoothScrollToPositionFromTop(nextSelectedPosition, 0);
                handledAppWidget = true;
            }
        }
        if (handledAppWidget && !isFocused()) {
            requestFocus();
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
                    && (focused2 = getFocusedChild()) != null) {
                focused2.clearFocus();
            }
            needToRedraw = true;
            checkSelectionChanged();
        }
        if (amountToScroll > 0) {
            semShowGoToTOP();
            if (!handledAppWidget) {
                scrollListItemsBy(direction == 33 ? amountToScroll : -amountToScroll);
            }
            needToRedraw = true;
        }
        if (this.mItemsCanFocus
                && focusResult == null
                && selectedView != null
                && selectedView.hasFocus()
                && (focused = selectedView.findFocus()) != null
                && (!isViewAncestorOf(focused, this) || distanceToView(focused) > 0)) {
            focused.clearFocus();
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
            this.mSelectedTop = selectedView.getTop();
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        invokeOnItemScrollListener();
        return true;
    }

    private void handleNewSelectionChange(
            View selectedView, int direction, int newSelectedPosition, boolean newFocusAssigned) {
        int topViewIndex;
        int bottomViewIndex;
        View topView;
        View bottomView;
        if (newSelectedPosition == -1) {
            throw new IllegalArgumentException("newSelectedPosition needs to be valid");
        }
        boolean topSelected = false;
        int selectedIndex = this.mSelectedPosition - this.mFirstPosition;
        int nextSelectedIndex = newSelectedPosition - this.mFirstPosition;
        if (direction == 33) {
            topViewIndex = nextSelectedIndex;
            bottomViewIndex = selectedIndex;
            topView = getChildAt(topViewIndex);
            bottomView = selectedView;
            topSelected = true;
        } else {
            topViewIndex = selectedIndex;
            bottomViewIndex = nextSelectedIndex;
            topView = selectedView;
            bottomView = getChildAt(bottomViewIndex);
        }
        int numChildren = getChildCount();
        if (topView != null) {
            topView.setSelected(!newFocusAssigned && topSelected);
            measureAndAdjustDown(topView, topViewIndex, numChildren);
        }
        if (bottomView != null) {
            bottomView.setSelected((newFocusAssigned || topSelected) ? false : true);
            measureAndAdjustDown(bottomView, bottomViewIndex, numChildren);
        }
    }

    private void measureAndAdjustDown(View child, int childIndex, int numChildren) {
        int oldHeight = child.getHeight();
        measureItem(child);
        if (child.getMeasuredHeight() != oldHeight) {
            relayoutMeasuredItem(child);
            int heightDelta = child.getMeasuredHeight() - oldHeight;
            for (int i = childIndex + 1; i < numChildren; i++) {
                getChildAt(i).offsetTopAndBottom(heightDelta);
            }
        }
    }

    private void measureItem(View child) {
        int childHeightSpec;
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-1, -2);
        }
        int childWidthSpec =
                ViewGroup.getChildMeasureSpec(
                        this.mWidthMeasureSpec,
                        this.mListPadding.left + this.mListPadding.right,
                        p.width);
        int lpHeight = p.height;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            int childHeightSpec2 = getMeasuredHeight();
            childHeightSpec = View.MeasureSpec.makeSafeMeasureSpec(childHeightSpec2, 0);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    private void relayoutMeasuredItem(View child) {
        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        int childLeft = this.mListPadding.left;
        int childRight = childLeft + w;
        int childTop = child.getTop();
        int childBottom = childTop + h;
        child.layout(childLeft, childTop, childRight, childBottom);
    }

    private int getArrowScrollPreviewLength() {
        return Math.max(2, getVerticalFadingEdgeLength());
    }

    private int amountToScroll(int direction, int nextSelectedPosition) {
        int listBottom = getHeight() - this.mListPadding.bottom;
        int listTop = this.mListPadding.top;
        int numChildren = getChildCount();
        if (direction == 130) {
            int indexToMakeVisible = numChildren - 1;
            if (nextSelectedPosition != -1) {
                indexToMakeVisible = nextSelectedPosition - this.mFirstPosition;
            }
            while (numChildren <= indexToMakeVisible) {
                addViewBelow(getChildAt(numChildren - 1), (this.mFirstPosition + numChildren) - 1);
                numChildren++;
            }
            int positionToMakeVisible = this.mFirstPosition + indexToMakeVisible;
            View viewToMakeVisible = getChildAt(indexToMakeVisible);
            int goalBottom = listBottom;
            if (positionToMakeVisible < this.mItemCount - 1) {
                goalBottom -= getArrowScrollPreviewLength();
            }
            if (viewToMakeVisible.getBottom() <= goalBottom) {
                return 0;
            }
            if (nextSelectedPosition != -1
                    && goalBottom - viewToMakeVisible.getTop() >= getMaxScrollAmount()) {
                return 0;
            }
            int amountToScroll = viewToMakeVisible.getBottom() - goalBottom;
            if (this.mFirstPosition + numChildren == this.mItemCount) {
                int max = getChildAt(numChildren - 1).getBottom() - listBottom;
                amountToScroll = Math.min(amountToScroll, max);
            }
            int max2 = getMaxScrollAmount();
            return Math.min(amountToScroll, max2);
        }
        int indexToMakeVisible2 = 0;
        if (nextSelectedPosition != -1) {
            indexToMakeVisible2 = nextSelectedPosition - this.mFirstPosition;
        }
        while (indexToMakeVisible2 < 0) {
            addViewAbove(getChildAt(0), this.mFirstPosition);
            this.mFirstPosition--;
            indexToMakeVisible2 = nextSelectedPosition - this.mFirstPosition;
        }
        int positionToMakeVisible2 = this.mFirstPosition + indexToMakeVisible2;
        View viewToMakeVisible2 = getChildAt(indexToMakeVisible2);
        int goalTop = listTop;
        if (positionToMakeVisible2 > 0) {
            goalTop += getArrowScrollPreviewLength();
        }
        if (viewToMakeVisible2.getTop() >= goalTop) {
            return 0;
        }
        if (nextSelectedPosition != -1
                && viewToMakeVisible2.getBottom() - goalTop >= getMaxScrollAmount()) {
            return 0;
        }
        int amountToScroll2 = goalTop - viewToMakeVisible2.getTop();
        if (this.mFirstPosition == 0) {
            int max3 = listTop - getChildAt(0).getTop();
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
        if (direction == 130) {
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
            for (int pos = startPos; pos <= lastVisiblePos; pos++) {
                if (adapter.isEnabled(pos)
                        && getChildAt(pos - firstPosition).getVisibility() == 0) {
                    return pos;
                }
            }
        } else {
            int last = (getChildCount() + firstPosition) - 1;
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
            for (int pos2 = startPos2; pos2 >= firstPosition; pos2--) {
                if (adapter2.isEnabled(pos2)
                        && getChildAt(pos2 - firstPosition).getVisibility() == 0) {
                    return pos2;
                }
            }
        }
        return -1;
    }

    private ArrowScrollFocusResult arrowScrollFocused(int direction) {
        boolean topFadingEdgeShowing;
        int ySearchPoint;
        View oldFocus;
        int ySearchPoint2;
        int selectablePosition;
        View selectedView = getSelectedView();
        if (selectedView == null || !selectedView.hasFocus()) {
            if (direction != 130) {
                topFadingEdgeShowing =
                        (this.mFirstPosition + getChildCount()) - 1 < this.mItemCount;
                int listBottom =
                        (getHeight() - this.mListPadding.bottom)
                                - (topFadingEdgeShowing ? getArrowScrollPreviewLength() : 0);
                if (selectedView != null && selectedView.getBottom() < listBottom) {
                    ySearchPoint = selectedView.getBottom();
                } else {
                    ySearchPoint = listBottom;
                }
                this.mTempRect.set(0, ySearchPoint, 0, ySearchPoint);
            } else {
                topFadingEdgeShowing = this.mFirstPosition > 0;
                int listTop =
                        this.mListPadding.top
                                + (topFadingEdgeShowing ? getArrowScrollPreviewLength() : 0);
                if (selectedView != null && selectedView.getTop() > listTop) {
                    ySearchPoint2 = selectedView.getTop();
                } else {
                    ySearchPoint2 = listTop;
                }
                this.mTempRect.set(0, ySearchPoint2, 0, ySearchPoint2);
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
                    && ((direction == 130 && selectablePosition < positionOfNewFocus)
                            || (direction == 33 && selectablePosition > positionOfNewFocus))) {
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
        if (direction == 33) {
            if (this.mTempRect.top >= this.mListPadding.top) {
                return 0;
            }
            int amountToScroll = this.mListPadding.top - this.mTempRect.top;
            if (positionOfNewFocus > 0) {
                return amountToScroll + getArrowScrollPreviewLength();
            }
            return amountToScroll;
        }
        int listBottom = getHeight() - this.mListPadding.bottom;
        if (this.mTempRect.bottom <= listBottom) {
            return 0;
        }
        int amountToScroll2 = this.mTempRect.bottom - listBottom;
        if (positionOfNewFocus < this.mItemCount - 1) {
            return amountToScroll2 + getArrowScrollPreviewLength();
        }
        return amountToScroll2;
    }

    private int distanceToView(View descendant) {
        descendant.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(descendant, this.mTempRect);
        int listBottom = (this.mBottom - this.mTop) - this.mListPadding.bottom;
        if (this.mTempRect.bottom < this.mListPadding.top) {
            int distance = this.mListPadding.top - this.mTempRect.bottom;
            return distance;
        }
        if (this.mTempRect.top <= listBottom) {
            return 0;
        }
        int distance2 = this.mTempRect.top - listBottom;
        return distance2;
    }

    private void scrollListItemsBy(int amount) {
        int lastVisiblePosition;
        int oldX = this.mScrollX;
        int oldY = this.mScrollY;
        offsetChildrenTopAndBottom(amount);
        int listBottom = getHeight() - this.mListPadding.bottom;
        int listTop = this.mListPadding.top;
        AbsListView.RecycleBin recycleBin = this.mRecycler;
        if (amount < 0) {
            int numChildren = getChildCount();
            View last = getChildAt(numChildren - 1);
            while (last.getBottom() < listBottom
                    && (this.mFirstPosition + numChildren) - 1 < this.mItemCount - 1) {
                last = addViewBelow(last, lastVisiblePosition);
                numChildren++;
            }
            if (last.getBottom() < listBottom) {
                offsetChildrenTopAndBottom(listBottom - last.getBottom());
            }
            View first = getChildAt(0);
            while (first.getBottom() < listTop) {
                AbsListView.LayoutParams layoutParams =
                        (AbsListView.LayoutParams) first.getLayoutParams();
                if (recycleBin.shouldRecycleViewType(layoutParams.viewType)) {
                    recycleBin.addScrapView(first, this.mFirstPosition);
                }
                detachViewFromParent(first);
                first = getChildAt(0);
                this.mFirstPosition++;
            }
        } else {
            View first2 = getChildAt(0);
            while (first2.getTop() > listTop && this.mFirstPosition > 0) {
                first2 = addViewAbove(first2, this.mFirstPosition);
                this.mFirstPosition--;
            }
            if (first2.getTop() > listTop) {
                offsetChildrenTopAndBottom(listTop - first2.getTop());
            }
            int lastIndex = getChildCount() - 1;
            View last2 = getChildAt(lastIndex);
            while (last2.getTop() > listBottom) {
                AbsListView.LayoutParams layoutParams2 =
                        (AbsListView.LayoutParams) last2.getLayoutParams();
                if (recycleBin.shouldRecycleViewType(layoutParams2.viewType)) {
                    recycleBin.addScrapView(last2, this.mFirstPosition + lastIndex);
                }
                detachViewFromParent(last2);
                lastIndex--;
                last2 = getChildAt(lastIndex);
            }
        }
        recycleBin.fullyDetachScrapViews();
        removeUnusedFixedViews(this.mHeaderViewInfos);
        removeUnusedFixedViews(this.mFooterViewInfos);
        onScrollChanged(this.mScrollX, this.mScrollY, oldX, oldY);
    }

    private View addViewAbove(View theView, int position) {
        int abovePosition = position - 1;
        View view = obtainView(abovePosition, this.mIsScrap);
        int edgeOfNewChild = theView.getTop() - this.mDividerHeight;
        setupChild(
                view,
                abovePosition,
                edgeOfNewChild,
                false,
                this.mListPadding.left,
                false,
                this.mIsScrap[0]);
        return view;
    }

    private View addViewBelow(View theView, int position) {
        int belowPosition = position + 1;
        View view = obtainView(belowPosition, this.mIsScrap);
        int edgeOfNewChild = theView.getBottom() + this.mDividerHeight;
        setupChild(
                view,
                belowPosition,
                edgeOfNewChild,
                true,
                this.mListPadding.left,
                false,
                this.mIsScrap[0]);
        return view;
    }

    public void setItemsCanFocus(boolean itemsCanFocus) {
        this.mItemsCanFocus = itemsCanFocus;
        if (!itemsCanFocus) {
            setDescendantFocusability(393216);
        }
    }

    public boolean getItemsCanFocus() {
        return this.mItemsCanFocus;
    }

    @Override // android.view.View
    public boolean isOpaque() {
        boolean retValue =
                (this.mCachingActive
                                && this.mIsCacheColorOpaque
                                && this.mDividerIsOpaque
                                && hasOpaqueScrollbars())
                        || super.isOpaque();
        if (retValue) {
            int listTop = this.mListPadding != null ? this.mListPadding.top : this.mPaddingTop;
            View first = getChildAt(0);
            if (first == null || first.getTop() > listTop) {
                return false;
            }
            int listBottom =
                    getHeight()
                            - (this.mListPadding != null
                                    ? this.mListPadding.bottom
                                    : this.mPaddingBottom);
            View last = getChildAt(getChildCount() - 1);
            if (last == null || last.getBottom() < listBottom) {
                return false;
            }
        }
        return retValue;
    }

    @Override // android.widget.AbsListView
    public void setCacheColorHint(int color) {
        boolean opaque = (color >>> 24) == 255;
        this.mIsCacheColorOpaque = opaque;
        if (opaque) {
            if (this.mDividerPaint == null) {
                this.mDividerPaint = new Paint();
            }
            this.mDividerPaint.setColor(color);
        }
        super.setCacheColorHint(color);
    }

    void drawOverscrollHeader(Canvas canvas, Drawable drawable, Rect bounds) {
        int height = drawable.getMinimumHeight();
        canvas.save();
        canvas.clipRect(bounds);
        int span = bounds.bottom - bounds.top;
        if (span < height) {
            bounds.top = bounds.bottom - height;
        }
        drawable.setBounds(bounds);
        drawable.draw(canvas);
        canvas.restore();
    }

    void drawOverscrollFooter(Canvas canvas, Drawable drawable, Rect bounds) {
        int height = drawable.getMinimumHeight();
        canvas.save();
        canvas.clipRect(bounds);
        int span = bounds.bottom - bounds.top;
        if (span < height) {
            bounds.bottom = bounds.top + height;
        }
        drawable.setBounds(bounds);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        ListAdapter adapter;
        int itemCount;
        int effectivePaddingTop;
        Drawable overscrollHeader;
        Drawable overscrollFooter;
        boolean headerDividers;
        boolean drawOverscrollFooter;
        int start;
        int first;
        int listBottom;
        boolean drawDividers;
        ListAdapter adapter2;
        Paint paint;
        if (this.mCachingStarted) {
            this.mCachingActive = true;
        }
        int dividerHeight = this.mDividerHeight;
        Drawable overscrollHeader2 = this.mOverScrollHeader;
        Drawable overscrollFooter2 = this.mOverScrollFooter;
        boolean drawOverscrollHeader = overscrollHeader2 != null;
        boolean drawOverscrollFooter2 = overscrollFooter2 != null;
        boolean drawDividers2 = dividerHeight > 0 && this.mDivider != null;
        if (drawDividers2 || drawOverscrollHeader || drawOverscrollFooter2) {
            Rect bounds = this.mTempRect;
            bounds.left = this.mPaddingLeft;
            bounds.right = (this.mRight - this.mLeft) - this.mPaddingRight;
            int count = getChildCount();
            int headerCount = getHeaderViewsCount();
            int itemCount2 = this.mItemCount;
            int footerLimit = itemCount2 - this.mFooterViewInfos.size();
            boolean headerDividers2 = this.mHeaderDividersEnabled;
            boolean footerDividers = this.mFooterDividersEnabled;
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
            int effectivePaddingBottom = 0;
            int effectivePaddingTop2 = this.mGroupFlags;
            Paint paint3 = paint2;
            if ((effectivePaddingTop2 & 34) != 34) {
                effectivePaddingTop = 0;
            } else {
                effectivePaddingTop = this.mListPadding.top;
                effectivePaddingBottom = this.mListPadding.bottom;
            }
            int i = this.mBottom;
            int effectivePaddingTop3 = effectivePaddingTop;
            int effectivePaddingTop4 = this.mTop;
            int listBottom2 = ((i - effectivePaddingTop4) - effectivePaddingBottom) + this.mScrollY;
            if (!this.mStackFromBottom) {
                int bottom = this.mScrollY;
                if (count > 0 && bottom < 0) {
                    if (drawOverscrollHeader) {
                        bounds.bottom = 0;
                        bounds.top = bottom;
                        drawOverscrollHeader(canvas, overscrollHeader2, bounds);
                    } else if (drawDividers2) {
                        bounds.bottom = 0;
                        bounds.top = -dividerHeight;
                        drawDivider(canvas, bounds, -1);
                    }
                }
                int i2 = 0;
                int scrollY = 0;
                while (i2 < count) {
                    Drawable overscrollHeader3 = overscrollHeader2;
                    int itemIndex = first2 + i2;
                    boolean isHeader = itemIndex < headerCount;
                    boolean isFooter = itemIndex >= footerLimit;
                    if ((!headerDividers2 && isHeader) || (!footerDividers && isFooter)) {
                        first = first2;
                        listBottom = listBottom2;
                        drawDividers = drawDividers2;
                        adapter2 = adapter;
                        paint = paint3;
                    } else {
                        View child = getChildAt(i2);
                        scrollY = child.getBottom();
                        first = first2;
                        boolean isLastItem = i2 == count + (-1);
                        if (!drawDividers2 || scrollY >= listBottom2) {
                            listBottom = listBottom2;
                            drawDividers = drawDividers2;
                            adapter2 = adapter;
                            paint = paint3;
                        } else if (drawOverscrollFooter2 && isLastItem) {
                            listBottom = listBottom2;
                            drawDividers = drawDividers2;
                            adapter2 = adapter;
                            paint = paint3;
                        } else {
                            listBottom = listBottom2;
                            int listBottom3 = itemIndex + 1;
                            drawDividers = drawDividers2;
                            adapter2 = adapter;
                            if (adapter2.isEnabled(itemIndex)) {
                                if (headerDividers2 || (!isHeader && listBottom3 >= headerCount)) {
                                    if (isLastItem
                                            || (adapter2.isEnabled(listBottom3)
                                                    && (footerDividers
                                                            || (!isFooter
                                                                    && listBottom3
                                                                            < footerLimit)))) {
                                        bounds.top = scrollY;
                                        bounds.bottom = scrollY + dividerHeight;
                                        drawDivider(canvas, bounds, i2);
                                        paint = paint3;
                                    }
                                }
                            }
                            if (!fillForMissingDividers) {
                                paint = paint3;
                            } else {
                                bounds.top = scrollY;
                                bounds.bottom = scrollY + dividerHeight;
                                paint = paint3;
                                canvas.drawRect(bounds, paint);
                            }
                        }
                    }
                    i2++;
                    paint3 = paint;
                    adapter = adapter2;
                    overscrollHeader2 = overscrollHeader3;
                    first2 = first;
                    listBottom2 = listBottom;
                    drawDividers2 = drawDividers;
                }
                int first3 = first2;
                int overFooterBottom = this.mBottom + this.mScrollY;
                if (drawOverscrollFooter2) {
                    if (first3 + count == itemCount && overFooterBottom > scrollY) {
                        bounds.top = scrollY;
                        bounds.bottom = overFooterBottom;
                        drawOverscrollFooter(canvas, overscrollFooter2, bounds);
                    }
                }
            } else {
                Drawable overscrollFooter3 = overscrollFooter2;
                ListAdapter adapter4 = adapter;
                int scrollY2 = this.mScrollY;
                if (count <= 0 || !drawOverscrollHeader) {
                    overscrollHeader = overscrollHeader2;
                } else {
                    bounds.top = scrollY2;
                    bounds.bottom = getChildAt(0).getTop();
                    overscrollHeader = overscrollHeader2;
                    drawOverscrollHeader(canvas, overscrollHeader, bounds);
                }
                int i3 = drawOverscrollHeader ? 1 : 0;
                int start2 = i3;
                int itemCount3 = i3;
                while (itemCount3 < count) {
                    Drawable overscrollHeader4 = overscrollHeader;
                    int itemIndex2 = first2 + itemCount3;
                    boolean isHeader2 = itemIndex2 < headerCount;
                    boolean isFooter2 = itemIndex2 >= footerLimit;
                    if ((headerDividers2 || !isHeader2) && (footerDividers || !isFooter2)) {
                        View child2 = getChildAt(itemCount3);
                        overscrollFooter = overscrollFooter3;
                        int top = child2.getTop();
                        if (drawDividers2) {
                            drawOverscrollFooter = drawOverscrollFooter2;
                            int effectivePaddingTop5 = effectivePaddingTop3;
                            if (top <= effectivePaddingTop5) {
                                headerDividers = headerDividers2;
                                effectivePaddingTop3 = effectivePaddingTop5;
                                start = start2;
                            } else {
                                effectivePaddingTop3 = effectivePaddingTop5;
                                int effectivePaddingTop6 = start2;
                                boolean isFirstItem = itemCount3 == effectivePaddingTop6;
                                start = effectivePaddingTop6;
                                int start3 = itemIndex2 - 1;
                                if (!adapter4.isEnabled(itemIndex2)) {
                                    headerDividers = headerDividers2;
                                } else if (!headerDividers2
                                        && (isHeader2 || start3 < headerCount)) {
                                    headerDividers = headerDividers2;
                                } else if (isFirstItem
                                        || (adapter4.isEnabled(start3)
                                                && (footerDividers
                                                        || (!isFooter2 && start3 < footerLimit)))) {
                                    headerDividers = headerDividers2;
                                    bounds.top = top - dividerHeight;
                                    bounds.bottom = top;
                                    drawDivider(canvas, bounds, itemCount3 - 1);
                                } else {
                                    headerDividers = headerDividers2;
                                }
                                if (fillForMissingDividers) {
                                    bounds.top = top - dividerHeight;
                                    bounds.bottom = top;
                                    canvas.drawRect(bounds, paint3);
                                }
                            }
                        } else {
                            headerDividers = headerDividers2;
                            drawOverscrollFooter = drawOverscrollFooter2;
                            start = start2;
                        }
                    } else {
                        headerDividers = headerDividers2;
                        overscrollFooter = overscrollFooter3;
                        drawOverscrollFooter = drawOverscrollFooter2;
                        start = start2;
                    }
                    itemCount3++;
                    overscrollHeader = overscrollHeader4;
                    overscrollFooter3 = overscrollFooter;
                    drawOverscrollFooter2 = drawOverscrollFooter;
                    start2 = start;
                    headerDividers2 = headerDividers;
                }
                Drawable overscrollFooter4 = overscrollFooter3;
                boolean drawOverscrollFooter3 = drawOverscrollFooter2;
                if (count > 0 && scrollY2 > 0) {
                    if (drawOverscrollFooter3) {
                        int absListBottom = this.mBottom;
                        bounds.top = absListBottom;
                        bounds.bottom = absListBottom + scrollY2;
                        drawOverscrollFooter(canvas, overscrollFooter4, bounds);
                    } else if (drawDividers2) {
                        bounds.top = listBottom2;
                        bounds.bottom = listBottom2 + dividerHeight;
                        drawDivider(canvas, bounds, -1);
                    }
                }
            }
        }
        if (this.mAddDeleteListAnimator != null) {
            this.mAddDeleteListAnimator.draw(canvas);
        }
        if (this.mSemEnableFillOut && this.mSemFillOutEmptyArea != -1) {
            canvas.drawRect(
                    0.0f,
                    this.mSemFillOutEmptyArea,
                    this.mRight,
                    this.mBottom,
                    this.mSemFillOutPaint);
        }
        super.dispatchDraw(canvas);
        if (this.mDndListAnimator != null) {
            this.mDndListAnimator.dispatchDraw(canvas);
        }
        if (this.mSweepListAnimator != null) {
            this.mSweepListAnimator.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup
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

    public Drawable getDivider() {
        return this.mDivider;
    }

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

    public int getDividerHeight() {
        return this.mDividerHeight;
    }

    public void setDividerHeight(int height) {
        this.mDividerHeight = height;
        requestLayout();
        invalidate();
    }

    public void setHeaderDividersEnabled(boolean headerDividersEnabled) {
        this.mHeaderDividersEnabled = headerDividersEnabled;
        invalidate();
    }

    public boolean areHeaderDividersEnabled() {
        return this.mHeaderDividersEnabled;
    }

    public void setFooterDividersEnabled(boolean footerDividersEnabled) {
        this.mFooterDividersEnabled = footerDividersEnabled;
        invalidate();
    }

    public boolean areFooterDividersEnabled() {
        return this.mFooterDividersEnabled;
    }

    public void setOverscrollHeader(Drawable header) {
        this.mOverScrollHeader = header;
        if (this.mScrollY < 0) {
            invalidate();
        }
    }

    public Drawable getOverscrollHeader() {
        return this.mOverScrollHeader;
    }

    public void setOverscrollFooter(Drawable footer) {
        this.mOverScrollFooter = footer;
        invalidate();
    }

    public Drawable getOverscrollFooter() {
        return this.mOverScrollFooter;
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        ListAdapter adapter = this.mAdapter;
        int closetChildIndex = -1;
        int closestChildTop = 0;
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
                        closestChildTop = other.getTop();
                    }
                }
            }
        }
        if (closetChildIndex >= 0) {
            setSelectionFromTop(this.mFirstPosition + closetChildIndex, closestChildTop);
        } else {
            requestLayout();
        }
        if (gainFocus && this.mDndListAnimator != null) {
            post(
                    new Runnable() { // from class: android.widget.ListView.2
                        @Override // java.lang.Runnable
                        public void run() {
                            ListView.this.mDndListAnimator.speakDescriptionForAccessibility();
                        }
                    });
        }
    }

    @Override // android.view.View
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

    @Deprecated
    public long[] getCheckItemIds() {
        if (this.mAdapter != null && this.mAdapter.hasStableIds()) {
            return getCheckedItemIds();
        }
        if (this.mChoiceMode != 0 && this.mCheckStates != null && this.mAdapter != null) {
            SparseBooleanArray states = this.mCheckStates;
            int count = states.size();
            long[] ids = new long[count];
            ListAdapter adapter = this.mAdapter;
            int checkedCount = 0;
            for (int i = 0; i < count; i++) {
                if (states.valueAt(i)) {
                    ids[checkedCount] = adapter.getItemId(states.keyAt(i));
                    checkedCount++;
                }
            }
            if (checkedCount == count) {
                return ids;
            }
            long[] result = new long[checkedCount];
            System.arraycopy(ids, 0, result, 0, checkedCount);
            return result;
        }
        return new long[0];
    }

    @Override // android.widget.AbsListView
    int getHeightForPosition(int position) {
        int height = super.getHeightForPosition(position);
        if (shouldAdjustHeightForDivider(position)) {
            return this.mDividerHeight + height;
        }
        return height;
    }

    private boolean shouldAdjustHeightForDivider(int itemIndex) {
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
            int headerCount = getHeaderViewsCount();
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

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup,
              // android.view.View
    public CharSequence getAccessibilityClassName() {
        return ListView.class.getName();
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup,
              // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        int rowsCount = getCount();
        int selectionMode = getSelectionModeForAccessibility();
        AccessibilityNodeInfo.CollectionInfo collectionInfo =
                AccessibilityNodeInfo.CollectionInfo.obtain(-1, -1, false, selectionMode);
        info.setCollectionInfo(collectionInfo);
        if (rowsCount > 0) {
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION);
        }
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
        if (super.performAccessibilityActionInternal(action, arguments)) {
            return true;
        }
        switch (action) {
            case 16908343:
                int row = arguments.getInt(AccessibilityNodeInfo.ACTION_ARGUMENT_ROW_INT, -1);
                int position = Math.min(row, getCount() - 1);
                if (row >= 0) {
                    smoothScrollToPosition(position);
                    break;
                }
                break;
        }
        return true;
    }

    @Override // android.widget.AbsListView
    public void onInitializeAccessibilityNodeInfoForItem(
            View view, int position, AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoForItem(view, position, info);
        AbsListView.LayoutParams lp = (AbsListView.LayoutParams) view.getLayoutParams();
        boolean isHeading = lp != null && lp.viewType == -2;
        boolean isSelected = isItemChecked(position);
        AccessibilityNodeInfo.CollectionItemInfo itemInfo =
                AccessibilityNodeInfo.CollectionItemInfo.obtain(
                        position, 1, 0, 1, isHeading, isSelected);
        info.setCollectionItemInfo(itemInfo);
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup,
              // android.view.View
    protected void encodeProperties(ViewHierarchyEncoder encoder) {
        super.encodeProperties(encoder);
        encoder.addProperty("recycleOnMeasure", recycleOnMeasure());
    }

    protected HeaderViewListAdapter wrapHeaderListAdapterInternal(
            ArrayList<FixedViewInfo> headerViewInfos,
            ArrayList<FixedViewInfo> footerViewInfos,
            ListAdapter adapter) {
        return new HeaderViewListAdapter(headerViewInfos, footerViewInfos, adapter);
    }

    protected void wrapHeaderListAdapterInternal() {
        this.mAdapter =
                wrapHeaderListAdapterInternal(
                        this.mHeaderViewInfos, this.mFooterViewInfos, this.mAdapter);
    }

    protected void dispatchDataSetObserverOnChangedInternal() {
        if (this.mDataSetObserver != null) {
            this.mDataSetObserver.onChanged();
        }
    }

    @Override // android.widget.AbsListView
    boolean performLongPress(View child, int longPressPosition, long longPressId) {
        if (this.mSweepListAnimator != null && this.mSweepListAnimator.isSwiping()) {
            return false;
        }
        return super.performLongPress(child, longPressPosition, longPressId);
    }

    @Override // android.widget.AbsListView
    public void removePendingCallbacks() {
        super.removePendingCallbacks();
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetSnapScroll(boolean enabled) {
        super.semSetAppWidgetSnapScroll(enabled);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetEnabled(boolean enabled) {
        super.semSetAppWidgetEnabled(enabled);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetGetCurrentPosition(String component) {
        super.semSetAppWidgetGetCurrentPosition(component);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetGetFirstPosition(String component) {
        super.semSetAppWidgetGetFirstPosition(component);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetIndicator(boolean enabled) {
        super.semSetAppWidgetIndicator(enabled);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetIndicatorBottomPadding(int bottomPadding) {
        super.semSetAppWidgetIndicatorBottomPadding(bottomPadding);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetIndicatorMarginHorizontal(int marginHorizontal) {
        super.semSetAppWidgetIndicatorMarginHorizontal(marginHorizontal);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetIndicatorWhere(int where) {
        super.semSetAppWidgetIndicatorWhere(where);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetGoToTopEnabledForAppWidget(boolean enabled) {
        super.semSetGoToTopEnabledForAppWidget(enabled);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semAllowDeferNotifyAfterRemoteViewsAdapterSet(boolean update) {
        super.semAllowDeferNotifyAfterRemoteViewsAdapterSet(update);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetGoToTopOffsetForAppWidget(int offset) {
        super.semSetGoToTopOffsetForAppWidget(offset);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetFastScrollEnabledForAppWidget(boolean enabled) {
        super.semSetFastScrollEnabledForAppWidget(enabled);
    }

    @Override // android.widget.AbsListView, android.view.View
    @RemotableViewMethod
    public void semSetScrollBarBottomPadding(int scrollBarBottomPadding) {
        super.semSetScrollBarBottomPadding(scrollBarBottomPadding);
    }

    @Override // android.widget.AbsListView, android.view.View
    @RemotableViewMethod
    public void semSetScrollBarTopPadding(int scrollBarTopPadding) {
        super.semSetScrollBarTopPadding(scrollBarTopPadding);
    }

    @RemotableViewMethod
    public void semEnableSelectZeroOnLastFocusTab(boolean enable) {
        this.mSelectZeroPositionOnKeyTab = enable;
    }

    @RemotableViewMethod
    public boolean semRequestFocus() {
        return super.requestFocus();
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetInnerFocus(boolean innerFocus) {
        super.semSetAppWidgetInnerFocus(innerFocus);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetNeedLayoutSpecificDone(boolean needLayoutSpecificDone) {
        super.semSetAppWidgetNeedLayoutSpecificDone(needLayoutSpecificDone);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetImmersiveEnabled(boolean enabled) {
        super.semSetAppWidgetImmersiveEnabled(enabled);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        View firstVisibleView;
        super.addFocusables(views, direction, focusableMode);
        if (this.mAppWidgetSnapScroll
                && direction == 2
                && (firstVisibleView = getChildAt(this.mSelectedPosition - this.mFirstPosition))
                        != null) {
            int cententH = (getHeight() - this.mListPadding.bottom) - this.mListPadding.top;
            int childH = firstVisibleView.getHeight();
            if (cententH == childH) {
                ArrayList<View> children = new ArrayList<>();
                for (int i = 0; i < ((ViewGroup) firstVisibleView).getChildCount(); i++) {
                    children.add(((ViewGroup) firstVisibleView).getChildAt(i));
                }
                ArrayList<View> viewsToRemove = new ArrayList<>();
                Iterator<View> it = views.iterator();
                while (it.hasNext()) {
                    View child = it.next();
                    if (child != this && !children.contains(child) && !child.hasFocusable()) {
                        viewsToRemove.add(child);
                    }
                }
                if (getChildAt(1) != null) {
                    getViewsToRemove(
                            viewsToRemove,
                            (ViewGroup)
                                    getChildAt(
                                            this.mSelectedPosition - this.mFirstPosition == 1
                                                    ? 0
                                                    : 1));
                }
                views.removeAll(viewsToRemove);
            }
        }
    }

    private void getViewsToRemove(ArrayList<View> viewsToRemove, ViewGroup unfocusedView) {
        for (int i = 0; i < unfocusedView.getChildCount(); i++) {
            View child = unfocusedView.getChildAt(i);
            if (child != null && child.hasFocusable()) {
                if (child.isFocusable()) {
                    viewsToRemove.add(child);
                }
                if (child instanceof ViewGroup) {
                    ViewGroup children = (ViewGroup) child;
                    if (children.getDescendantFocusability() != 393216) {
                        getViewsToRemove(viewsToRemove, children);
                    }
                }
            }
        }
    }

    private void addClickables(View view) {
        if (this.mClickableViewStates.isEmpty() && (view instanceof ViewGroup)) {
            ViewGroup parent = (ViewGroup) view;
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child != null
                        && child != this
                        && !(child instanceof ViewGroup)
                        && child.isClickable()
                        && child.getVisibility() == 0) {
                    this.mClickableViewStates.put(
                            Integer.valueOf(this.mClickableViewStates.size()),
                            new AbsListView.ClickableViewState(child, false));
                }
                addClickables(child);
            }
        }
    }

    private boolean consumeClickables() {
        if (this.mClickableViewStates.isEmpty()) {
            return false;
        }
        for (Map.Entry<Integer, AbsListView.ClickableViewState> entry :
                this.mClickableViewStates.entrySet()) {
            Integer i = entry.getKey();
            AbsListView.ClickableViewState clickableView = entry.getValue();
            if (clickableView != null && !clickableView.getWasFocused()) {
                this.mNextClickable = clickableView.getView();
                if (this.mNextClickable != null && this.mNextClickable.getVisibility() != 0) {
                    this.mClickableViewStates.remove(i);
                    this.mNextClickable = null;
                } else {
                    clickableView.setWasFocused(true);
                    clickableView.setIsFocused(true);
                    this.mClickableViewStates.put(i, clickableView);
                    return true;
                }
            }
        }
        this.mClickableViewStates.clear();
        return false;
    }

    private View getDeepestFocusedChild() {
        View v = this;
        while (true) {
            View view = null;
            if (v == null) {
                return null;
            }
            if (v.isFocused()) {
                return v;
            }
            if (v instanceof ViewGroup) {
                view = ((ViewGroup) v).getFocusedChild();
            }
            v = view;
        }
    }

    @Override // android.widget.AbsListView
    public boolean semHandleGenericMotionEvent(int direction) {
        return pointerScroll(direction);
    }

    private final int nextFocusedPositionForDirection(
            View focusedView, int selectedPos, int direction) {
        int i;
        int nextFocused;
        int i2;
        if (direction == 130) {
            int listBottom = getHeight() - this.mListPadding.bottom;
            if (focusedView == null || focusedView.getBottom() > listBottom) {
                return -1;
            }
            if (selectedPos != -1 && selectedPos >= this.mFirstPosition) {
                i2 = selectedPos + 1;
            } else {
                i2 = this.mFirstPosition;
            }
            nextFocused = i2;
        } else {
            int listTop = this.mListPadding.top;
            if (focusedView == null || focusedView.getTop() < listTop) {
                return -1;
            }
            int lastPos = (this.mFirstPosition + getChildCount()) - 1;
            if (selectedPos != -1 && selectedPos <= lastPos) {
                i = selectedPos - 1;
            } else {
                i = lastPos;
            }
            nextFocused = i;
        }
        if (nextFocused < 0 || nextFocused >= this.mAdapter.getCount()) {
            return -1;
        }
        return nextFocused;
    }

    private boolean pointerScroll(int direction) {
        if (this.mAnimator.isRunning()) {
            this.mAnimator.end();
        }
        if (getChildCount() <= 0) {
            return false;
        }
        View focusedView = getChildAt(0);
        int focusedPos = this.mNewFocusedPos;
        int nextFocusedPosition =
                nextFocusedPositionForDirection(focusedView, focusedPos, direction);
        int amountToScroll = amountToScroll(direction, nextFocusedPosition);
        if (focusedView == null) {
            return true;
        }
        int listBottom = getHeight() - this.mListPadding.bottom;
        boolean handled = false;
        if (amountToScroll > 0) {
            if (direction == 33 && focusedPos > 0) {
                nextFocusedPosition = focusedPos - 1;
                handled = true;
            } else if (direction == 130 && focusedPos < this.mAdapter.getCount() - 1) {
                nextFocusedPosition = focusedPos + 1;
                handled = true;
            }
            if (direction == 33
                    && focusedView.getHeight() > listBottom - this.mListPadding.top
                    && focusedView.getTop() != 0) {
                handled = false;
                if (focusedView.getTop() + amountToScroll >= 0) {
                    amountToScroll = -focusedView.getTop();
                }
            }
        }
        if (handled) {
            setSelection(nextFocusedPosition);
            return true;
        }
        if (amountToScroll > 0) {
            semShowGoToTOP();
            scrollListItemsBy(direction == 33 ? amountToScroll : -amountToScroll);
        }
        return true;
    }

    @Override // android.widget.AbsListView
    public void setOnScrollOffsetListener(AbsListView.OnScrollOffsetListener scrollOffsetListener) {
        super.setOnScrollOffsetListener(scrollOffsetListener);
    }

    public boolean semGetAppWidgetImmersiveEnabled() {
        return this.mAppWidgetImmersiveEnalbed;
    }
}

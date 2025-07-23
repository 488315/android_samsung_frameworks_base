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
import android.widget.AbsListView;
import android.widget.RemoteViews;
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
/* loaded from: classes5.dex */
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

    private int getTopSelectionPixel(int i, int i2, int i3) {
        return i3 > 0 ? i + i2 : i;
    }

    @ViewDebug.ExportedProperty(category = Slice.HINT_LIST)
    protected boolean recycleOnMeasure() {
        return true;
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<ListView> {
        private int mDividerHeightId;
        private int mDividerId;
        private int mFooterDividersEnabledId;
        private int mHeaderDividersEnabledId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mDividerId = propertyMapper.mapObject("divider", 16843049);
            this.mDividerHeightId = propertyMapper.mapInt("dividerHeight", 16843050);
            this.mFooterDividersEnabledId = propertyMapper.mapBoolean("footerDividersEnabled", 16843311);
            this.mHeaderDividersEnabledId = propertyMapper.mapBoolean("headerDividersEnabled", 16843310);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(ListView listView, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mDividerId, listView.getDivider());
            propertyReader.readInt(this.mDividerHeightId, listView.getDividerHeight());
            propertyReader.readBoolean(this.mFooterDividersEnabledId, listView.areFooterDividersEnabled());
            propertyReader.readBoolean(this.mHeaderDividersEnabledId, listView.areHeaderDividersEnabled());
        }
    }

    public class FixedViewInfo {
        public Object data;
        public boolean isSelectable;
        public View view;

        public FixedViewInfo(ListView listView) {
        }
    }

    public void setAddDeleteListAnimator(SemAddDeleteListAnimator semAddDeleteListAnimator) {
        this.mAddDeleteListAnimator = semAddDeleteListAnimator;
    }

    public void setDndListAnimator(SemDragAndDropListAnimator semDragAndDropListAnimator) {
        this.mDndListAnimator = semDragAndDropListAnimator;
        setChildrenDrawingOrderEnabled(true);
        this.mDndListAnimator.setAutoScrollListener(new SemAbsDragAndDropAnimator.SemDragAutoScrollListener() { // from class: android.widget.ListView.1
            @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.SemDragAutoScrollListener
            public void onAutoScroll(int i) {
                ListView.this.trackMotionScroll(i, i);
            }
        });
    }

    public ListView(Context context) {
        this(context, null);
    }

    public ListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842868);
    }

    public ListView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ListView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        int dimensionPixelSize;
        this.mHeaderViewInfos = Lists.newArrayList();
        this.mFooterViewInfos = Lists.newArrayList();
        this.mAreAllItemsSelectable = true;
        this.mItemsCanFocus = false;
        this.mTempRect = new Rect();
        this.mArrowScrollFocusResult = new ArrowScrollFocusResult();
        this.mSelectZeroPositionOnKeyTab = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListView, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.ListView, attributeSet, obtainStyledAttributes, i, i2);
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
        if (obtainStyledAttributes.hasValueOrEmpty(2) && (dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(2, 0)) != 0) {
            setDividerHeight(dimensionPixelSize);
        }
        this.mHeaderDividersEnabled = obtainStyledAttributes.getBoolean(3, true);
        this.mFooterDividersEnabled = obtainStyledAttributes.getBoolean(4, true);
        obtainStyledAttributes.recycle();
    }

    public int getMaxScrollAmount() {
        return (int) ((this.mBottom - this.mTop) * MAX_SCROLL_FACTOR);
    }

    private void adjustViewsUpOrDown() {
        int childCount = getChildCount();
        if (childCount > 0) {
            int i = 0;
            if (!this.mStackFromBottom) {
                int top = getChildAt(0).getTop() - this.mListPadding.top;
                if (this.mFirstPosition != 0) {
                    top -= this.mDividerHeight;
                }
                if (top >= 0) {
                    i = top;
                }
            } else {
                int bottom = getChildAt(childCount - 1).getBottom() - (getHeight() - this.mListPadding.bottom);
                if (this.mFirstPosition + childCount < this.mItemCount) {
                    bottom += this.mDividerHeight;
                }
                if (bottom <= 0) {
                    i = bottom;
                }
            }
            if (i != 0) {
                offsetChildrenTopAndBottom(-i);
            }
        }
    }

    public void addHeaderView(View view, Object obj, boolean z) {
        if (view.getParent() != null && view.getParent() != this && Log.isLoggable(TAG, 5)) {
            Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first.");
        }
        FixedViewInfo fixedViewInfo = new FixedViewInfo(this);
        fixedViewInfo.view = view;
        fixedViewInfo.data = obj;
        fixedViewInfo.isSelectable = z;
        this.mHeaderViewInfos.add(fixedViewInfo);
        this.mAreAllItemsSelectable &= z;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof HeaderViewListAdapter)) {
                wrapHeaderListAdapterInternal();
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    public void addHeaderView(View view) {
        addHeaderView(view, null, true);
    }

    @Override // android.widget.AbsListView
    public int getHeaderViewsCount() {
        return this.mHeaderViewInfos.size();
    }

    public boolean removeHeaderView(View view) {
        boolean z = false;
        if (this.mHeaderViewInfos.size() > 0) {
            if (this.mAdapter != null && ((HeaderViewListAdapter) this.mAdapter).removeHeader(view)) {
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

    public void addFooterView(View view, Object obj, boolean z) {
        if (view.getParent() != null && view.getParent() != this && Log.isLoggable(TAG, 5)) {
            Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first.");
        }
        FixedViewInfo fixedViewInfo = new FixedViewInfo(this);
        fixedViewInfo.view = view;
        fixedViewInfo.data = obj;
        fixedViewInfo.isSelectable = z;
        this.mFooterViewInfos.add(fixedViewInfo);
        this.mAreAllItemsSelectable &= z;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof HeaderViewListAdapter)) {
                wrapHeaderListAdapterInternal();
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    public void addFooterView(View view) {
        addFooterView(view, null, true);
    }

    @Override // android.widget.AbsListView
    public int getFooterViewsCount() {
        return this.mFooterViewInfos.size();
    }

    public boolean removeFooterView(View view) {
        boolean z = false;
        if (this.mFooterViewInfos.size() > 0) {
            if (this.mAdapter != null && ((HeaderViewListAdapter) this.mAdapter).removeFooter(view)) {
                if (this.mDataSetObserver != null) {
                    this.mDataSetObserver.onChanged();
                }
                z = true;
            }
            removeFixedViewInfo(view, this.mFooterViewInfos);
        }
        return z;
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
    public void setAdapter(ListAdapter listAdapter) {
        int lookForSelectablePosition;
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        resetList();
        this.mRecycler.clear();
        if (this.mHeaderViewInfos.size() > 0 || this.mFooterViewInfos.size() > 0) {
            this.mAdapter = wrapHeaderListAdapterInternal(this.mHeaderViewInfos, this.mFooterViewInfos, listAdapter);
        } else {
            this.mAdapter = listAdapter;
        }
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        super.setAdapter(listAdapter);
        if (this.mAdapter != null) {
            this.mAreAllItemsSelectable = this.mAdapter.areAllItemsEnabled();
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            checkFocus();
            this.mDataSetObserver = new AbsListView.AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mRecycler.setViewTypeCount(this.mAdapter.getViewTypeCount());
            if (this.mStackFromBottom) {
                lookForSelectablePosition = lookForSelectablePosition(this.mItemCount - 1, false);
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

    private void clearRecycledState(ArrayList<FixedViewInfo> arrayList) {
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ViewGroup.LayoutParams layoutParams = arrayList.get(i).view.getLayoutParams();
                if (checkLayoutParams(layoutParams)) {
                    ((AbsListView.LayoutParams) layoutParams).recycledHeaderFooter = false;
                }
            }
        }
    }

    private boolean showingTopFadingEdge() {
        return this.mFirstPosition > 0 || getChildAt(0).getTop() > this.mScrollY + this.mListPadding.top;
    }

    private boolean showingBottomFadingEdge() {
        int childCount = getChildCount();
        return (this.mFirstPosition + childCount) - 1 < this.mItemCount - 1 || getChildAt(childCount + (-1)).getBottom() < (this.mScrollY + getHeight()) - this.mListPadding.bottom;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        int i;
        int i2;
        int i3;
        int i4 = rect.top;
        rect.offset(view.getLeft(), view.getTop());
        rect.offset(-view.getScrollX(), -view.getScrollY());
        int height = getHeight();
        int scrollY = getScrollY();
        int i5 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (showingTopFadingEdge() && (this.mSelectedPosition > 0 || i4 > verticalFadingEdgeLength)) {
            scrollY += verticalFadingEdgeLength;
        }
        int bottom = getChildAt(getChildCount() - 1).getBottom();
        if (showingBottomFadingEdge() && (this.mSelectedPosition < this.mItemCount - 1 || rect.bottom < bottom - verticalFadingEdgeLength)) {
            i5 -= verticalFadingEdgeLength;
        }
        if (rect.bottom > i5 && rect.top > scrollY) {
            if (rect.height() > height) {
                i3 = rect.top - scrollY;
            } else {
                i3 = rect.bottom - i5;
            }
            i = Math.min(i3, bottom - i5);
        } else if (rect.top >= scrollY || rect.bottom >= i5) {
            i = 0;
        } else {
            if (rect.height() > height) {
                i2 = 0 - (i5 - rect.bottom);
            } else {
                i2 = 0 - (scrollY - rect.top);
            }
            i = Math.max(i2, getChildAt(0).getTop() - scrollY);
        }
        boolean z2 = i != 0;
        if (z2) {
            scrollListItemsBy(-i);
            positionSelector(-1, view);
            this.mSelectedTop = view.getTop();
            invalidate();
        }
        return z2;
    }

    @Override // android.widget.AbsListView
    void fillGap(boolean z) {
        int height;
        int childCount = getChildCount();
        if (z) {
            int listPaddingTop = (this.mGroupFlags & 34) == 34 ? getListPaddingTop() : 0;
            if (childCount > 0) {
                listPaddingTop = this.mDividerHeight + getChildAt(childCount - 1).getBottom();
            }
            fillDown(this.mFirstPosition + childCount, listPaddingTop);
            correctTooHigh(getChildCount());
            return;
        }
        int listPaddingBottom = (this.mGroupFlags & 34) == 34 ? getListPaddingBottom() : 0;
        if (childCount > 0) {
            height = getChildAt(0).getTop() - this.mDividerHeight;
        } else {
            height = getHeight() - listPaddingBottom;
        }
        fillUp(this.mFirstPosition - 1, height);
        correctTooLow(getChildCount());
    }

    private View fillDown(int i, int i2) {
        int i3 = this.mBottom - this.mTop;
        View view = null;
        if ((this.mGroupFlags & 34) == 34) {
            i3 -= this.mListPadding.bottom;
        }
        int i4 = i;
        int i5 = i2;
        while (true) {
            if (i5 >= i3 || i4 >= this.mItemCount) {
                break;
            }
            boolean z = i4 == this.mSelectedPosition;
            ListView listView = this;
            View makeAndAddView = listView.makeAndAddView(i4, i5, true, this.mListPadding.left, z);
            i5 = makeAndAddView.getBottom() + listView.mDividerHeight;
            if (z) {
                view = makeAndAddView;
            }
            i4++;
            this = listView;
        }
        ListView listView2 = this;
        listView2.setVisibleRangeHint(listView2.mFirstPosition, (listView2.mFirstPosition + listView2.getChildCount()) - 1);
        return view;
    }

    private View fillUp(int i, int i2) {
        int i3;
        int i4;
        int i5;
        View view = null;
        if ((this.mGroupFlags & 34) == 34) {
            i5 = this.mListPadding.top;
            i3 = i;
            i4 = i2;
        } else {
            i3 = i;
            i4 = i2;
            i5 = 0;
        }
        while (i4 > i5 && i3 >= 0) {
            boolean z = i3 == this.mSelectedPosition;
            ListView listView = this;
            View makeAndAddView = listView.makeAndAddView(i3, i4, false, this.mListPadding.left, z);
            i4 = makeAndAddView.getTop() - listView.mDividerHeight;
            if (z) {
                view = makeAndAddView;
            }
            i3--;
            this = listView;
        }
        ListView listView2 = this;
        listView2.mFirstPosition = i3 + 1;
        listView2.setVisibleRangeHint(listView2.mFirstPosition, (listView2.mFirstPosition + listView2.getChildCount()) - 1);
        return view;
    }

    private View fillFromTop(int i) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return fillDown(this.mFirstPosition, i);
    }

    private View fillFromMiddle(int i, int i2) {
        int i3 = i2 - i;
        int reconcileSelectedPosition = reconcileSelectedPosition();
        View makeAndAddView = makeAndAddView(reconcileSelectedPosition, i, true, this.mListPadding.left, true);
        this.mFirstPosition = reconcileSelectedPosition;
        int measuredHeight = makeAndAddView.getMeasuredHeight();
        if (measuredHeight <= i3) {
            makeAndAddView.offsetTopAndBottom((i3 - measuredHeight) / 2);
        }
        fillAboveAndBelow(makeAndAddView, reconcileSelectedPosition);
        if (!this.mStackFromBottom) {
            correctTooHigh(getChildCount());
            return makeAndAddView;
        }
        correctTooLow(getChildCount());
        return makeAndAddView;
    }

    private void fillAboveAndBelow(View view, int i) {
        int i2 = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            fillUp(i - 1, view.getTop() - i2);
            adjustViewsUpOrDown();
            fillDown(i + 1, view.getBottom() + i2);
        } else {
            fillDown(i + 1, view.getBottom() + i2);
            adjustViewsUpOrDown();
            fillUp(i - 1, view.getTop() - i2);
        }
    }

    private View fillFromSelection(int i, int i2, int i3) {
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int i4 = this.mSelectedPosition;
        int topSelectionPixel = getTopSelectionPixel(i2, verticalFadingEdgeLength, i4);
        int bottomSelectionPixel = getBottomSelectionPixel(i3, verticalFadingEdgeLength, i4);
        View makeAndAddView = makeAndAddView(i4, i, true, this.mListPadding.left, true);
        if (makeAndAddView.getBottom() > bottomSelectionPixel) {
            makeAndAddView.offsetTopAndBottom(-Math.min(makeAndAddView.getTop() - topSelectionPixel, makeAndAddView.getBottom() - bottomSelectionPixel));
        } else if (makeAndAddView.getTop() < topSelectionPixel) {
            makeAndAddView.offsetTopAndBottom(Math.min(topSelectionPixel - makeAndAddView.getTop(), bottomSelectionPixel - makeAndAddView.getBottom()));
        }
        fillAboveAndBelow(makeAndAddView, i4);
        if (!this.mStackFromBottom) {
            correctTooHigh(getChildCount());
            return makeAndAddView;
        }
        correctTooLow(getChildCount());
        return makeAndAddView;
    }

    private int getBottomSelectionPixel(int i, int i2, int i3) {
        return i3 != this.mItemCount + (-1) ? i - i2 : i;
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void smoothScrollToPosition(int i) {
        super.smoothScrollToPosition(i);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void smoothScrollByOffset(int i) {
        super.smoothScrollByOffset(i);
    }

    private View moveSelection(View view, View view2, int i, int i2, int i3) {
        View makeAndAddView;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int i4 = this.mSelectedPosition;
        int topSelectionPixel = getTopSelectionPixel(i2, verticalFadingEdgeLength, i4);
        int bottomSelectionPixel = getBottomSelectionPixel(i2, verticalFadingEdgeLength, i4);
        if (i > 0) {
            View makeAndAddView2 = makeAndAddView(i4 - 1, view.getTop(), true, this.mListPadding.left, false);
            int i5 = this.mDividerHeight;
            View makeAndAddView3 = makeAndAddView(i4, makeAndAddView2.getBottom() + i5, true, this.mListPadding.left, true);
            if (makeAndAddView3.getBottom() > bottomSelectionPixel) {
                int i6 = -Math.min(Math.min(makeAndAddView3.getTop() - topSelectionPixel, makeAndAddView3.getBottom() - bottomSelectionPixel), (i3 - i2) / 2);
                makeAndAddView2.offsetTopAndBottom(i6);
                makeAndAddView3.offsetTopAndBottom(i6);
            }
            if (!this.mStackFromBottom) {
                fillUp(this.mSelectedPosition - 2, makeAndAddView3.getTop() - i5);
                adjustViewsUpOrDown();
                fillDown(this.mSelectedPosition + 1, makeAndAddView3.getBottom() + i5);
                return makeAndAddView3;
            }
            fillDown(this.mSelectedPosition + 1, makeAndAddView3.getBottom() + i5);
            adjustViewsUpOrDown();
            fillUp(this.mSelectedPosition - 2, makeAndAddView3.getTop() - i5);
            return makeAndAddView3;
        }
        if (i < 0) {
            if (view2 != null) {
                makeAndAddView = makeAndAddView(i4, view2.getTop(), true, this.mListPadding.left, true);
            } else {
                makeAndAddView = makeAndAddView(i4, view.getTop(), false, this.mListPadding.left, true);
            }
            if (makeAndAddView.getTop() < topSelectionPixel) {
                makeAndAddView.offsetTopAndBottom(Math.min(Math.min(topSelectionPixel - makeAndAddView.getTop(), bottomSelectionPixel - makeAndAddView.getBottom()), (i3 - i2) / 2));
            }
            fillAboveAndBelow(makeAndAddView, i4);
            return makeAndAddView;
        }
        int top = view.getTop();
        View makeAndAddView4 = makeAndAddView(i4, top, true, this.mListPadding.left, true);
        if (top < i2 && makeAndAddView4.getBottom() < i2 + 20) {
            makeAndAddView4.offsetTopAndBottom(i2 - makeAndAddView4.getTop());
        }
        fillAboveAndBelow(makeAndAddView4, i4);
        return makeAndAddView4;
    }

    private class FocusSelector implements Runnable {
        private static final int STATE_REQUEST_FOCUS = 3;
        private static final int STATE_SET_SELECTION = 1;
        private static final int STATE_WAIT_FOR_LAYOUT = 2;
        private int mAction;
        private int mPosition;
        private int mPositionTop;

        private FocusSelector() {
        }

        FocusSelector setupForSetSelection(int i, int i2) {
            this.mPosition = i;
            this.mPositionTop = i2;
            this.mAction = 1;
            return this;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i = this.mAction;
            if (i == 1) {
                ListView.this.setSelectionFromTop(this.mPosition, this.mPositionTop);
                this.mAction = 2;
            } else if (i == 3) {
                View childAt = ListView.this.getChildAt(this.mPosition - ListView.this.mFirstPosition);
                if (childAt != null) {
                    childAt.requestFocus();
                }
                this.mAction = -1;
            }
        }

        Runnable setupFocusIfValid(int i) {
            if (this.mAction != 2 || i != this.mPosition) {
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

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        FocusSelector focusSelector = this.mFocusSelector;
        if (focusSelector != null) {
            removeCallbacks(focusSelector);
            this.mFocusSelector = null;
        }
        if (this.mAppWidgetInnerFocus) {
            this.mClickableViewStates.clear();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        View focusedChild;
        if (getChildCount() > 0 && (focusedChild = getFocusedChild()) != null) {
            int indexOfChild = this.mFirstPosition + indexOfChild(focusedChild);
            int top = focusedChild.getTop() - Math.max(0, focusedChild.getBottom() - (i2 - this.mPaddingTop));
            if (this.mFocusSelector == null) {
                this.mFocusSelector = new FocusSelector();
            }
            post(this.mFocusSelector.setupForSetSelection(indexOfChild, top));
        }
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        ListView listView;
        int i5;
        View obtainView;
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int i6 = 0;
        this.mItemCount = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        if (this.mItemCount <= 0 || (!(mode == 0 || mode2 == 0) || (obtainView = obtainView(0, this.mIsScrap)) == null)) {
            i3 = 0;
            i4 = 0;
        } else {
            measureScrapChild(obtainView, 0, i, size2);
            i3 = obtainView.getMeasuredWidth();
            i4 = obtainView.getMeasuredHeight();
            i6 = combineMeasuredStates(0, obtainView.getMeasuredState());
            if (recycleOnMeasure() && this.mRecycler.shouldRecycleViewType(((AbsListView.LayoutParams) obtainView.getLayoutParams()).viewType)) {
                this.mRecycler.addScrapView(obtainView, -1);
            }
        }
        int verticalScrollbarWidth = mode == 0 ? this.mListPadding.left + this.mListPadding.right + i3 + getVerticalScrollbarWidth() : ((-16777216) & i6) | size;
        if (mode2 == 0) {
            size2 = this.mListPadding.top + this.mListPadding.bottom + i4 + (getVerticalFadingEdgeLength() * 2);
        }
        int i7 = size2;
        if (mode2 == Integer.MIN_VALUE) {
            listView = this;
            i5 = i;
            i7 = listView.measureHeightOfChildren(i5, 0, -1, i7, -1);
        } else {
            listView = this;
            i5 = i;
        }
        listView.setMeasuredDimension(verticalScrollbarWidth, i7);
        listView.mWidthMeasureSpec = i5;
    }

    private void measureScrapChild(View view, int i, int i2, int i3) {
        int makeSafeMeasureSpec;
        AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = (AbsListView.LayoutParams) generateDefaultLayoutParams();
            view.setLayoutParams(layoutParams);
        }
        layoutParams.viewType = this.mAdapter.getItemViewType(i);
        layoutParams.isEnabled = this.mAdapter.isEnabled(i);
        layoutParams.forceAdd = true;
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, this.mListPadding.left + this.mListPadding.right, layoutParams.width);
        int i4 = layoutParams.height;
        if (i4 > 0) {
            makeSafeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
        } else {
            makeSafeMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(i3, 0);
        }
        view.measure(childMeasureSpec, makeSafeMeasureSpec);
        view.forceLayout();
    }

    final int measureHeightOfChildren(int i, int i2, int i3, int i4, int i5) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null) {
            return this.mListPadding.top + this.mListPadding.bottom;
        }
        int i6 = this.mListPadding.top + this.mListPadding.bottom;
        int i7 = this.mDividerHeight;
        if (i3 == -1) {
            i3 = listAdapter.getCount() - 1;
        }
        AbsListView.RecycleBin recycleBin = this.mRecycler;
        boolean recycleOnMeasure = recycleOnMeasure();
        boolean[] zArr = this.mIsScrap;
        int i8 = 0;
        while (i2 <= i3) {
            View obtainView = obtainView(i2, zArr);
            measureScrapChild(obtainView, i2, i, i4);
            if (i2 > 0) {
                i6 += i7;
            }
            if (recycleOnMeasure && recycleBin.shouldRecycleViewType(((AbsListView.LayoutParams) obtainView.getLayoutParams()).viewType)) {
                recycleBin.addScrapView(obtainView, -1);
            }
            i6 += obtainView.getMeasuredHeight();
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

    @Override // android.widget.AbsListView
    int findMotionRow(int i) {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return -1;
        }
        if (this.mStackFromBottom) {
            for (int i2 = childCount - 1; i2 >= 0; i2--) {
                if (i >= getChildAt(i2).getTop()) {
                    return this.mFirstPosition + i2;
                }
            }
            return -1;
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            if (i <= getChildAt(i3).getBottom()) {
                return this.mFirstPosition + i3;
            }
        }
        return -1;
    }

    private View fillSpecific(int i, int i2) {
        View view;
        View view2;
        boolean z = i == this.mSelectedPosition;
        View makeAndAddView = makeAndAddView(i, i2, true, this.mListPadding.left, z);
        this.mFirstPosition = i;
        int i3 = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            view = fillUp(i - 1, makeAndAddView.getTop() - i3);
            adjustViewsUpOrDown();
            view2 = fillDown(i + 1, makeAndAddView.getBottom() + i3);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHigh(childCount);
            }
        } else {
            View fillDown = fillDown(i + 1, makeAndAddView.getBottom() + i3);
            adjustViewsUpOrDown();
            View fillUp = fillUp(i - 1, makeAndAddView.getTop() - i3);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLow(childCount2);
            }
            view = fillUp;
            view2 = fillDown;
        }
        return z ? makeAndAddView : view != null ? view : view2;
    }

    private void correctTooHigh(int i) {
        if ((this.mFirstPosition + i) - 1 != this.mItemCount - 1 || i <= 0) {
            return;
        }
        int bottom = ((this.mBottom - this.mTop) - this.mListPadding.bottom) - getChildAt(i - 1).getBottom();
        View childAt = getChildAt(0);
        int top = childAt.getTop();
        if (bottom > 0) {
            if (this.mFirstPosition > 0 || top < this.mListPadding.top) {
                if (this.mFirstPosition == 0) {
                    bottom = Math.min(bottom, this.mListPadding.top - top);
                }
                offsetChildrenTopAndBottom(bottom);
                if (this.mFirstPosition > 0) {
                    fillUp(this.mFirstPosition - 1, childAt.getTop() - this.mDividerHeight);
                    adjustViewsUpOrDown();
                }
            }
        }
    }

    private void correctTooLow(int i) {
        if (this.mFirstPosition != 0 || i <= 0) {
            return;
        }
        int top = getChildAt(0).getTop();
        int i2 = this.mListPadding.top;
        int i3 = (this.mBottom - this.mTop) - this.mListPadding.bottom;
        int i4 = top - i2;
        View childAt = getChildAt(i - 1);
        int bottom = childAt.getBottom();
        int i5 = this.mFirstPosition + i;
        int i6 = i5 - 1;
        if (i4 > 0) {
            if (i6 < this.mItemCount - 1 || bottom > i3) {
                if (i6 == this.mItemCount - 1) {
                    i4 = Math.min(i4, bottom - i3);
                }
                offsetChildrenTopAndBottom(-i4);
                if (i6 < this.mItemCount - 1) {
                    fillDown(i5, childAt.getBottom() + this.mDividerHeight);
                    adjustViewsUpOrDown();
                    return;
                }
                return;
            }
            if (i6 == this.mItemCount - 1) {
                adjustViewsUpOrDown();
            }
        }
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        SemDragAndDropListAnimator semDragAndDropListAnimator = this.mDndListAnimator;
        if (semDragAndDropListAnimator != null && semDragAndDropListAnimator.onInterceptTouchEvent(motionEvent)) {
            return true;
        }
        if (this.mSweepListAnimator != null && this.mSweepListAnimator.isSweepAnimatorEnabled() && this.mSweepListAnimator.onInterceptTouchEvent(motionEvent)) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        SemDragAndDropListAnimator semDragAndDropListAnimator = this.mDndListAnimator;
        if (semDragAndDropListAnimator != null && semDragAndDropListAnimator.onTouchEvent(motionEvent)) {
            return true;
        }
        if (this.mSweepListAnimator != null && this.mSweepListAnimator.isSweepAnimatorEnabled() && this.mSweepListAnimator.onTouchEvent(motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        SemDragAndDropListAnimator semDragAndDropListAnimator = this.mDndListAnimator;
        return semDragAndDropListAnimator != null ? semDragAndDropListAnimator.getChildDrawingOrder(i, i2) : super.getChildDrawingOrder(i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0262 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x02fc A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x037c A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x03ac A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x03c0 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x03d7 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x03e1  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x03e6  */
    /* JADX WARN: Removed duplicated region for block: B:162:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0390 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:181:0x02a7 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:223:0x017c A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:224:0x0186 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:228:0x01a3 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:243:0x01ce A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:244:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x01f0 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0162 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:84:0x0142, B:88:0x0149, B:91:0x0169, B:92:0x0171, B:95:0x0178, B:97:0x01fd, B:98:0x0253, B:100:0x0262, B:102:0x0266, B:104:0x026c, B:109:0x0278, B:111:0x0284, B:112:0x02a0, B:114:0x02fc, B:117:0x0304, B:119:0x030a, B:122:0x0312, B:123:0x0321, B:126:0x0328, B:128:0x033e, B:131:0x0345, B:133:0x0357, B:134:0x0368, B:137:0x0370, B:139:0x0375, B:140:0x0364, B:141:0x0378, B:143:0x037c, B:146:0x03ac, B:148:0x03b2, B:149:0x03b5, B:151:0x03c0, B:152:0x03c8, B:154:0x03d7, B:155:0x03da, B:163:0x0380, B:165:0x0384, B:168:0x0392, B:171:0x03a4, B:172:0x03a0, B:173:0x03a7, B:174:0x027e, B:176:0x028e, B:178:0x0294, B:179:0x0297, B:180:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:195:0x02ef, B:198:0x02f7, B:199:0x02d2, B:201:0x02d6, B:202:0x02df, B:204:0x02ea, B:205:0x020b, B:206:0x0222, B:208:0x0226, B:210:0x022c, B:213:0x0235, B:214:0x0231, B:215:0x023a, B:217:0x0240, B:220:0x0249, B:221:0x0245, B:222:0x024e, B:223:0x017c, B:224:0x0186, B:226:0x018a, B:227:0x0199, B:228:0x01a3, B:230:0x01af, B:232:0x01b3, B:234:0x01b9, B:235:0x01bc, B:237:0x01c0, B:239:0x01c4, B:241:0x01c8, B:243:0x01ce, B:246:0x01e1, B:247:0x01eb, B:248:0x01f0, B:249:0x0162, B:263:0x03ec, B:264:0x0420), top: B:47:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0094 A[Catch: all -> 0x0423, TryCatch #1 {all -> 0x0423, blocks: (B:5:0x000e, B:7:0x0018, B:16:0x002a, B:25:0x004d, B:28:0x0056, B:29:0x005c, B:31:0x0064, B:32:0x006b, B:33:0x0090, B:35:0x0094, B:36:0x0097, B:38:0x009b, B:46:0x00ad, B:49:0x00b7, B:51:0x00c2, B:53:0x00c8, B:58:0x00d7, B:60:0x00dd, B:62:0x00e3, B:66:0x00ef, B:67:0x00f9, B:70:0x0101, B:72:0x0107, B:74:0x010d, B:78:0x012c, B:80:0x0132, B:82:0x013e, B:250:0x0118, B:252:0x011e, B:258:0x00eb, B:274:0x007b, B:277:0x0084), top: B:4:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009b A[Catch: all -> 0x0423, TRY_LEAVE, TryCatch #1 {all -> 0x0423, blocks: (B:5:0x000e, B:7:0x0018, B:16:0x002a, B:25:0x004d, B:28:0x0056, B:29:0x005c, B:31:0x0064, B:32:0x006b, B:33:0x0090, B:35:0x0094, B:36:0x0097, B:38:0x009b, B:46:0x00ad, B:49:0x00b7, B:51:0x00c2, B:53:0x00c8, B:58:0x00d7, B:60:0x00dd, B:62:0x00e3, B:66:0x00ef, B:67:0x00f9, B:70:0x0101, B:72:0x0107, B:74:0x010d, B:78:0x012c, B:80:0x0132, B:82:0x013e, B:250:0x0118, B:252:0x011e, B:258:0x00eb, B:274:0x007b, B:277:0x0084), top: B:4:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00ad A[Catch: all -> 0x0423, TRY_ENTER, TryCatch #1 {all -> 0x0423, blocks: (B:5:0x000e, B:7:0x0018, B:16:0x002a, B:25:0x004d, B:28:0x0056, B:29:0x005c, B:31:0x0064, B:32:0x006b, B:33:0x0090, B:35:0x0094, B:36:0x0097, B:38:0x009b, B:46:0x00ad, B:49:0x00b7, B:51:0x00c2, B:53:0x00c8, B:58:0x00d7, B:60:0x00dd, B:62:0x00e3, B:66:0x00ef, B:67:0x00f9, B:70:0x0101, B:72:0x0107, B:74:0x010d, B:78:0x012c, B:80:0x0132, B:82:0x013e, B:250:0x0118, B:252:0x011e, B:258:0x00eb, B:274:0x007b, B:277:0x0084), top: B:4:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0132 A[Catch: all -> 0x0423, TryCatch #1 {all -> 0x0423, blocks: (B:5:0x000e, B:7:0x0018, B:16:0x002a, B:25:0x004d, B:28:0x0056, B:29:0x005c, B:31:0x0064, B:32:0x006b, B:33:0x0090, B:35:0x0094, B:36:0x0097, B:38:0x009b, B:46:0x00ad, B:49:0x00b7, B:51:0x00c2, B:53:0x00c8, B:58:0x00d7, B:60:0x00dd, B:62:0x00e3, B:66:0x00ef, B:67:0x00f9, B:70:0x0101, B:72:0x0107, B:74:0x010d, B:78:0x012c, B:80:0x0132, B:82:0x013e, B:250:0x0118, B:252:0x011e, B:258:0x00eb, B:274:0x007b, B:277:0x0084), top: B:4:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0174  */
    @Override // android.widget.AbsListView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void layoutChildren() {
        /*
            Method dump skipped, instructions count: 1092
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ListView.layoutChildren():void");
    }

    @Override // android.widget.AbsListView
    boolean trackMotionScroll(int i, int i2) {
        boolean trackMotionScroll = super.trackMotionScroll(i, i2);
        removeUnusedFixedViews(this.mHeaderViewInfos);
        removeUnusedFixedViews(this.mFooterViewInfos);
        return trackMotionScroll;
    }

    private void removeUnusedFixedViews(List<FixedViewInfo> list) {
        if (list == null) {
            return;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            View view = list.get(size).view;
            AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) view.getLayoutParams();
            if (view.getParent() == null && layoutParams != null && layoutParams.recycledHeaderFooter) {
                removeDetachedView(view, false);
                layoutParams.recycledHeaderFooter = false;
            }
        }
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
        int makeSafeMeasureSpec;
        Trace.traceBegin(8L, "setupListItem");
        boolean z4 = z2 && shouldShowSelector();
        boolean z5 = z4 != view.isSelected();
        int i4 = this.mTouchMode;
        boolean z6 = i4 > 0 && i4 < 3 && this.mMotionPosition == i;
        boolean z7 = z6 != view.isPressed();
        boolean z8 = !z3 || z5 || view.isLayoutRequested();
        AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = (AbsListView.LayoutParams) generateDefaultLayoutParams();
        }
        layoutParams.viewType = this.mAdapter.getItemViewType(i);
        layoutParams.isEnabled = this.mAdapter.isEnabled(i);
        if (z5) {
            view.setSelected(z4);
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
        if ((z3 && !layoutParams.forceAdd) || (layoutParams.recycledHeaderFooter && layoutParams.viewType == -2)) {
            attachViewToParent(view, z ? -1 : 0, layoutParams);
            if (z3 && ((AbsListView.LayoutParams) view.getLayoutParams()).scrappedFromPosition != i) {
                view.jumpDrawablesToCurrentState();
            }
        } else {
            layoutParams.forceAdd = false;
            if (layoutParams.viewType == -2) {
                layoutParams.recycledHeaderFooter = true;
            }
            addViewInLayout(view, z ? -1 : 0, layoutParams, true);
            view.resolveRtlPropertiesIfNeeded();
        }
        if (z8) {
            int childMeasureSpec = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mListPadding.left + this.mListPadding.right, layoutParams.width);
            int i5 = layoutParams.height;
            if (i5 > 0) {
                makeSafeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
            } else {
                makeSafeMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(getMeasuredHeight(), 0);
            }
            view.measure(childMeasureSpec, makeSafeMeasureSpec);
        } else {
            cleanupLayoutState(view);
        }
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i6 = z ? i2 : i2 - measuredHeight;
        if (z8) {
            view.layout(i3, i6, measuredWidth + i3, measuredHeight + i6);
        } else {
            view.offsetLeftAndRight(i3 - view.getLeft());
            view.offsetTopAndBottom(i6 - view.getTop());
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
    public void setSelection(int i) {
        setSelectionFromTop(i, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x000e, code lost:
    
        if (r4 == (r0 + 1)) goto L11;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0016  */
    @Override // android.widget.AbsListView
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
            android.widget.AbsListView$AbsPositionScroller r4 = r3.mPositionScroller
            if (r4 == 0) goto L1b
            android.widget.AbsListView$AbsPositionScroller r4 = r3.mPositionScroller
            r4.stop()
        L1b:
            r3.layoutChildren()
            if (r2 == 0) goto L23
            r3.awakenScrollBars()
        L23:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ListView.setSelectionInt(int):void");
    }

    @Override // android.widget.AdapterView
    int lookForSelectablePosition(int i, boolean z) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null && !isInTouchMode()) {
            int count = listAdapter.getCount();
            if (!this.mAreAllItemsSelectable) {
                if (z) {
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
        if (z) {
            int min = Math.min(i2 - 1, count);
            while (min > constrain && !listAdapter.isEnabled(min)) {
                min--;
            }
            if (min <= constrain) {
                return -1;
            }
            return min;
        }
        int max = Math.max(0, i2 + 1);
        while (max < constrain && !listAdapter.isEnabled(max)) {
            max++;
        }
        if (max >= constrain) {
            return -1;
        }
        return max;
    }

    public void setSelectionAfterHeaderView() {
        int headerViewsCount = getHeaderViewsCount();
        if (headerViewsCount > 0) {
            this.mNextSelectedPosition = 0;
        } else if (this.mAdapter != null) {
            setSelection(headerViewsCount);
        } else {
            this.mNextSelectedPosition = headerViewsCount;
            this.mLayoutMode = 2;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean dispatchKeyEvent = super.dispatchKeyEvent(keyEvent);
        return (dispatchKeyEvent || getFocusedChild() == null || keyEvent.getAction() != 0) ? dispatchKeyEvent : onKeyDown(keyEvent.getKeyCode(), keyEvent);
    }

    @Override // android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return commonKey(i, 1, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return commonKey(i, i2, keyEvent);
    }

    @Override // android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return commonKey(i, 1, keyEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x011e, code lost:
    
        if (fullScroll(33) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x014e, code lost:
    
        if (fullScroll(130) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0161, code lost:
    
        if (fullScroll(33) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0175, code lost:
    
        if (pageScroll(130) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0189, code lost:
    
        if (fullScroll(130) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x019d, code lost:
    
        if (pageScroll(33) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x01b1, code lost:
    
        if (fullScroll(33) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x0281, code lost:
    
        if (arrowScroll(130) == false) goto L193;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x0284, code lost:
    
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x029b, code lost:
    
        if (r9 != false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x0298, code lost:
    
        if (arrowScroll(33) == false) goto L193;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00da, code lost:
    
        if (fullScroll(130) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00dd, code lost:
    
        r7 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:171:0x023a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x02a3  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x02b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean commonKey(int r17, int r18, android.view.KeyEvent r19) {
        /*
            Method dump skipped, instructions count: 736
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ListView.commonKey(int, int, android.view.KeyEvent):boolean");
    }

    boolean pageScroll(int i) {
        int min;
        boolean z;
        int lookForSelectablePositionAfter;
        if (i != 33) {
            if (i == 130) {
                min = Math.min(this.mItemCount - 1, (this.mSelectedPosition + getChildCount()) - 1);
                z = true;
            }
            return false;
        }
        min = Math.max(0, (this.mSelectedPosition - getChildCount()) - 1);
        z = false;
        if (min >= 0 && (lookForSelectablePositionAfter = lookForSelectablePositionAfter(this.mSelectedPosition, min, z)) >= 0) {
            this.mLayoutMode = 4;
            this.mSpecificTop = this.mPaddingTop + getVerticalFadingEdgeLength();
            if (z && lookForSelectablePositionAfter > this.mItemCount - getChildCount()) {
                this.mLayoutMode = 3;
            }
            if (!z && lookForSelectablePositionAfter < getChildCount()) {
                this.mLayoutMode = 1;
            }
            setSelectionInt(lookForSelectablePositionAfter);
            semShowGoToTOP();
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
        if (i == 33) {
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
            if (i == 130 && this.mSelectedPosition < (i2 = this.mItemCount - 1)) {
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
            semShowGoToTOP();
            invalidate();
        }
        return z;
    }

    private boolean handleHorizontalFocusWithinListItem(int i) {
        View selectedView;
        if (i != 17 && i != 66) {
            throw new IllegalArgumentException("direction must be one of {View.FOCUS_LEFT, View.FOCUS_RIGHT}");
        }
        int childCount = getChildCount();
        if (!this.mItemsCanFocus || childCount <= 0 || this.mSelectedPosition == -1 || (selectedView = getSelectedView()) == null || !selectedView.hasFocus() || !(selectedView instanceof ViewGroup)) {
            return false;
        }
        View findFocus = selectedView.findFocus();
        View findNextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup) selectedView, findFocus, i);
        if (findNextFocus != null) {
            if (findFocus != null) {
                findFocus.getFocusedRect(this.mTempRect);
                offsetDescendantRectToMyCoords(findFocus, this.mTempRect);
                offsetRectIntoDescendantCoords(findNextFocus, this.mTempRect);
            }
            if (findNextFocus.requestFocus(i, this.mTempRect)) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
                return true;
            }
        }
        View findNextFocus2 = FocusFinder.getInstance().findNextFocus((ViewGroup) getRootView(), findFocus, i);
        if (findNextFocus2 != null) {
            return isViewAncestorOf(findNextFocus2, this);
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

    private final int nextSelectedPositionForDirection(View view, int i, int i2) {
        int i3;
        if (i2 == 130) {
            int height = getHeight() - this.mListPadding.bottom;
            if (view == null || view.getBottom() > height) {
                return -1;
            }
            i3 = (i == -1 || i < this.mFirstPosition) ? this.mFirstPosition : i + 1;
        } else {
            int i4 = this.mListPadding.top;
            if (view != null && view.getTop() >= i4) {
                int childCount = (this.mFirstPosition + getChildCount()) - 1;
                i3 = (i == -1 || i > childCount) ? childCount : i - 1;
            }
            return -1;
        }
        if (i3 >= 0 && i3 < this.mAdapter.getCount()) {
            return lookForSelectablePosition(i3, i2 == 130);
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0125 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean arrowScrollImpl(int r13) {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ListView.arrowScrollImpl(int):boolean");
    }

    private void handleNewSelectionChange(View view, int i, int i2, boolean z) {
        View childAt;
        boolean z2;
        if (i2 == -1) {
            throw new IllegalArgumentException("newSelectedPosition needs to be valid");
        }
        int i3 = this.mSelectedPosition - this.mFirstPosition;
        int i4 = i2 - this.mFirstPosition;
        if (i == 33) {
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
            measureAndAdjustDown(view, i3, childCount);
        }
        if (childAt != null) {
            childAt.setSelected((z || z2) ? false : true);
            measureAndAdjustDown(childAt, i4, childCount);
        }
    }

    private void measureAndAdjustDown(View view, int i, int i2) {
        int height = view.getHeight();
        measureItem(view);
        if (view.getMeasuredHeight() == height) {
            return;
        }
        relayoutMeasuredItem(view);
        int measuredHeight = view.getMeasuredHeight() - height;
        while (true) {
            i++;
            if (i >= i2) {
                return;
            } else {
                getChildAt(i).offsetTopAndBottom(measuredHeight);
            }
        }
    }

    private void measureItem(View view) {
        int makeSafeMeasureSpec;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -2);
        }
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mListPadding.left + this.mListPadding.right, layoutParams.width);
        int i = layoutParams.height;
        if (i > 0) {
            makeSafeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
        } else {
            makeSafeMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(getMeasuredHeight(), 0);
        }
        view.measure(childMeasureSpec, makeSafeMeasureSpec);
    }

    private void relayoutMeasuredItem(View view) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i = this.mListPadding.left;
        int top = view.getTop();
        view.layout(i, top, measuredWidth + i, measuredHeight + top);
    }

    private int getArrowScrollPreviewLength() {
        return Math.max(2, getVerticalFadingEdgeLength());
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009d  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x008b -> B:27:0x0085). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int amountToScroll(int r7, int r8) {
        /*
            r6 = this;
            int r0 = r6.getHeight()
            android.graphics.Rect r1 = r6.mListPadding
            int r1 = r1.bottom
            int r0 = r0 - r1
            android.graphics.Rect r1 = r6.mListPadding
            int r1 = r1.top
            int r2 = r6.getChildCount()
            r3 = 130(0x82, float:1.82E-43)
            r4 = -1
            r5 = 0
            if (r7 != r3) goto L81
            int r7 = r2 + (-1)
            if (r8 == r4) goto L1f
            int r7 = r6.mFirstPosition
            int r7 = r8 - r7
        L1f:
            if (r2 > r7) goto L32
            int r1 = r2 + (-1)
            android.view.View r1 = r6.getChildAt(r1)
            int r3 = r6.mFirstPosition
            int r3 = r3 + r2
            int r3 = r3 + (-1)
            r6.addViewBelow(r1, r3)
            int r2 = r2 + 1
            goto L1f
        L32:
            int r1 = r6.mFirstPosition
            int r1 = r1 + r7
            android.view.View r7 = r6.getChildAt(r7)
            int r3 = r6.mItemCount
            int r3 = r3 + (-1)
            if (r1 >= r3) goto L46
            int r1 = r6.getArrowScrollPreviewLength()
            int r1 = r0 - r1
            goto L47
        L46:
            r1 = r0
        L47:
            int r3 = r7.getBottom()
            if (r3 > r1) goto L4e
            return r5
        L4e:
            if (r8 == r4) goto L5d
            int r8 = r7.getTop()
            int r8 = r1 - r8
            int r3 = r6.getMaxScrollAmount()
            if (r8 < r3) goto L5d
            return r5
        L5d:
            int r7 = r7.getBottom()
            int r7 = r7 - r1
            int r8 = r6.mFirstPosition
            int r8 = r8 + r2
            int r1 = r6.mItemCount
            if (r8 != r1) goto L78
            int r2 = r2 + (-1)
            android.view.View r8 = r6.getChildAt(r2)
            int r8 = r8.getBottom()
            int r8 = r8 - r0
            int r7 = java.lang.Math.min(r7, r8)
        L78:
            int r6 = r6.getMaxScrollAmount()
            int r6 = java.lang.Math.min(r7, r6)
            return r6
        L81:
            if (r8 == r4) goto L88
            int r7 = r6.mFirstPosition
        L85:
            int r7 = r8 - r7
            goto L89
        L88:
            r7 = r5
        L89:
            if (r7 >= 0) goto L9d
            android.view.View r7 = r6.getChildAt(r5)
            int r0 = r6.mFirstPosition
            r6.addViewAbove(r7, r0)
            int r7 = r6.mFirstPosition
            int r7 = r7 + (-1)
            r6.mFirstPosition = r7
            int r7 = r6.mFirstPosition
            goto L85
        L9d:
            int r0 = r6.mFirstPosition
            int r0 = r0 + r7
            android.view.View r7 = r6.getChildAt(r7)
            if (r0 <= 0) goto Lac
            int r0 = r6.getArrowScrollPreviewLength()
            int r0 = r0 + r1
            goto Lad
        Lac:
            r0 = r1
        Lad:
            int r2 = r7.getTop()
            if (r2 < r0) goto Lb4
            return r5
        Lb4:
            if (r8 == r4) goto Lc2
            int r8 = r7.getBottom()
            int r8 = r8 - r0
            int r2 = r6.getMaxScrollAmount()
            if (r8 < r2) goto Lc2
            return r5
        Lc2:
            int r7 = r7.getTop()
            int r0 = r0 - r7
            int r7 = r6.mFirstPosition
            if (r7 != 0) goto Ld8
            android.view.View r7 = r6.getChildAt(r5)
            int r7 = r7.getTop()
            int r1 = r1 - r7
            int r0 = java.lang.Math.min(r0, r1)
        Ld8:
            int r6 = r6.getMaxScrollAmount()
            int r6 = java.lang.Math.min(r0, r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ListView.amountToScroll(int, int):int");
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
        if (i == 130) {
            int i3 = this.mSelectedPosition != -1 ? this.mSelectedPosition + 1 : i2;
            if (i3 >= this.mAdapter.getCount()) {
                return -1;
            }
            if (i3 < i2) {
                i3 = i2;
            }
            int lastVisiblePosition = getLastVisiblePosition();
            ListAdapter adapter = getAdapter();
            while (i3 <= lastVisiblePosition) {
                if (adapter.isEnabled(i3) && getChildAt(i3 - i2).getVisibility() == 0) {
                    return i3;
                }
                i3++;
            }
        } else {
            int childCount2 = (getChildCount() + i2) - 1;
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
                while (childCount2 >= i2) {
                    if (adapter2.isEnabled(childCount2) && getChildAt(childCount2 - i2).getVisibility() == 0) {
                        return childCount2;
                    }
                    childCount2--;
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
            if (i == 130) {
                int arrowScrollPreviewLength = this.mListPadding.top + (this.mFirstPosition > 0 ? getArrowScrollPreviewLength() : 0);
                if (selectedView != null && selectedView.getTop() > arrowScrollPreviewLength) {
                    arrowScrollPreviewLength = selectedView.getTop();
                }
                this.mTempRect.set(0, arrowScrollPreviewLength, 0, arrowScrollPreviewLength);
            } else {
                int height = (getHeight() - this.mListPadding.bottom) - ((this.mFirstPosition + getChildCount()) - 1 < this.mItemCount ? getArrowScrollPreviewLength() : 0);
                if (selectedView != null && selectedView.getBottom() < height) {
                    height = selectedView.getBottom();
                }
                this.mTempRect.set(0, height, 0, height);
            }
            findNextFocusFromRect = FocusFinder.getInstance().findNextFocusFromRect(this, this.mTempRect, i);
        }
        if (findNextFocusFromRect != null) {
            int positionOfNewFocus = positionOfNewFocus(findNextFocusFromRect);
            if (this.mSelectedPosition != -1 && positionOfNewFocus != this.mSelectedPosition && (lookForSelectablePositionOnScreen = lookForSelectablePositionOnScreen(i)) != -1 && ((i == 130 && lookForSelectablePositionOnScreen < positionOfNewFocus) || (i == 33 && lookForSelectablePositionOnScreen > positionOfNewFocus))) {
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
        if (i == 33) {
            if (this.mTempRect.top >= this.mListPadding.top) {
                return 0;
            }
            int i3 = this.mListPadding.top - this.mTempRect.top;
            return i2 > 0 ? i3 + getArrowScrollPreviewLength() : i3;
        }
        int height = getHeight() - this.mListPadding.bottom;
        if (this.mTempRect.bottom <= height) {
            return 0;
        }
        int i4 = this.mTempRect.bottom - height;
        return i2 < this.mItemCount + (-1) ? i4 + getArrowScrollPreviewLength() : i4;
    }

    private int distanceToView(View view) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        int i = (this.mBottom - this.mTop) - this.mListPadding.bottom;
        if (this.mTempRect.bottom < this.mListPadding.top) {
            return this.mListPadding.top - this.mTempRect.bottom;
        }
        if (this.mTempRect.top > i) {
            return this.mTempRect.top - i;
        }
        return 0;
    }

    private void scrollListItemsBy(int i) {
        int i2;
        int i3 = this.mScrollX;
        int i4 = this.mScrollY;
        offsetChildrenTopAndBottom(i);
        int height = getHeight() - this.mListPadding.bottom;
        int i5 = this.mListPadding.top;
        AbsListView.RecycleBin recycleBin = this.mRecycler;
        if (i < 0) {
            int childCount = getChildCount();
            View childAt = getChildAt(childCount - 1);
            while (childAt.getBottom() < height && (this.mFirstPosition + childCount) - 1 < this.mItemCount - 1) {
                childAt = addViewBelow(childAt, i2);
                childCount++;
            }
            if (childAt.getBottom() < height) {
                offsetChildrenTopAndBottom(height - childAt.getBottom());
            }
            View childAt2 = getChildAt(0);
            while (childAt2.getBottom() < i5) {
                if (recycleBin.shouldRecycleViewType(((AbsListView.LayoutParams) childAt2.getLayoutParams()).viewType)) {
                    recycleBin.addScrapView(childAt2, this.mFirstPosition);
                }
                detachViewFromParent(childAt2);
                childAt2 = getChildAt(0);
                this.mFirstPosition++;
            }
        } else {
            View childAt3 = getChildAt(0);
            while (childAt3.getTop() > i5 && this.mFirstPosition > 0) {
                childAt3 = addViewAbove(childAt3, this.mFirstPosition);
                this.mFirstPosition--;
            }
            if (childAt3.getTop() > i5) {
                offsetChildrenTopAndBottom(i5 - childAt3.getTop());
            }
            int childCount2 = getChildCount() - 1;
            View childAt4 = getChildAt(childCount2);
            while (childAt4.getTop() > height) {
                if (recycleBin.shouldRecycleViewType(((AbsListView.LayoutParams) childAt4.getLayoutParams()).viewType)) {
                    recycleBin.addScrapView(childAt4, this.mFirstPosition + childCount2);
                }
                detachViewFromParent(childAt4);
                childCount2--;
                childAt4 = getChildAt(childCount2);
            }
        }
        recycleBin.fullyDetachScrapViews();
        removeUnusedFixedViews(this.mHeaderViewInfos);
        removeUnusedFixedViews(this.mFooterViewInfos);
        onScrollChanged(this.mScrollX, this.mScrollY, i3, i4);
    }

    private View addViewAbove(View view, int i) {
        int i2 = i - 1;
        View obtainView = obtainView(i2, this.mIsScrap);
        setupChild(obtainView, i2, view.getTop() - this.mDividerHeight, false, this.mListPadding.left, false, this.mIsScrap[0]);
        return obtainView;
    }

    private View addViewBelow(View view, int i) {
        int i2 = i + 1;
        View obtainView = obtainView(i2, this.mIsScrap);
        setupChild(obtainView, i2, view.getBottom() + this.mDividerHeight, true, this.mListPadding.left, false, this.mIsScrap[0]);
        return obtainView;
    }

    public void setItemsCanFocus(boolean z) {
        this.mItemsCanFocus = z;
        if (z) {
            return;
        }
        setDescendantFocusability(393216);
    }

    public boolean getItemsCanFocus() {
        return this.mItemsCanFocus;
    }

    @Override // android.view.View
    public boolean isOpaque() {
        boolean z = (this.mCachingActive && this.mIsCacheColorOpaque && this.mDividerIsOpaque && hasOpaqueScrollbars()) || super.isOpaque();
        if (z) {
            int i = this.mListPadding != null ? this.mListPadding.top : this.mPaddingTop;
            View childAt = getChildAt(0);
            if (childAt != null && childAt.getTop() <= i) {
                int height = getHeight() - (this.mListPadding != null ? this.mListPadding.bottom : this.mPaddingBottom);
                View childAt2 = getChildAt(getChildCount() - 1);
                if (childAt2 == null || childAt2.getBottom() < height) {
                }
            }
            return false;
        }
        return z;
    }

    @Override // android.widget.AbsListView
    public void setCacheColorHint(int i) {
        boolean z = (i >>> 24) == 255;
        this.mIsCacheColorOpaque = z;
        if (z) {
            if (this.mDividerPaint == null) {
                this.mDividerPaint = new Paint();
            }
            this.mDividerPaint.setColor(i);
        }
        super.setCacheColorHint(i);
    }

    void drawOverscrollHeader(Canvas canvas, Drawable drawable, Rect rect) {
        int minimumHeight = drawable.getMinimumHeight();
        canvas.save();
        canvas.clipRect(rect);
        if (rect.bottom - rect.top < minimumHeight) {
            rect.top = rect.bottom - minimumHeight;
        }
        drawable.setBounds(rect);
        drawable.draw(canvas);
        canvas.restore();
    }

    void drawOverscrollFooter(Canvas canvas, Drawable drawable, Rect rect) {
        int minimumHeight = drawable.getMinimumHeight();
        canvas.save();
        canvas.clipRect(rect);
        if (rect.bottom - rect.top < minimumHeight) {
            rect.bottom = rect.top + minimumHeight;
        }
        drawable.setBounds(rect);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        boolean z;
        int i;
        int i2;
        boolean z2;
        int i3;
        int i4;
        if (this.mCachingStarted) {
            this.mCachingActive = true;
        }
        int i5 = this.mDividerHeight;
        Drawable drawable = this.mOverScrollHeader;
        Drawable drawable2 = this.mOverScrollFooter;
        int i6 = drawable != null ? 1 : 0;
        boolean z3 = drawable2 != null;
        boolean z4 = i5 > 0 && this.mDivider != null;
        if (z4 || i6 != 0 || z3) {
            Rect rect = this.mTempRect;
            rect.left = this.mPaddingLeft;
            rect.right = (this.mRight - this.mLeft) - this.mPaddingRight;
            int childCount = getChildCount();
            int headerViewsCount = getHeaderViewsCount();
            int i7 = this.mItemCount;
            int size = i7 - this.mFooterViewInfos.size();
            boolean z5 = this.mHeaderDividersEnabled;
            boolean z6 = this.mFooterDividersEnabled;
            int i8 = this.mFirstPosition;
            ListAdapter listAdapter = this.mAdapter;
            boolean z7 = isOpaque() && !super.isOpaque();
            if (z7 && this.mDividerPaint == null && this.mIsCacheColorOpaque) {
                Paint paint = new Paint();
                this.mDividerPaint = paint;
                z = z3;
                paint.setColor(getCacheColorHint());
            } else {
                z = z3;
            }
            Paint paint2 = this.mDividerPaint;
            boolean z8 = z4;
            if ((this.mGroupFlags & 34) == 34) {
                i = this.mListPadding.top;
                i2 = this.mListPadding.bottom;
            } else {
                i = 0;
                i2 = 0;
            }
            int i9 = ((this.mBottom - this.mTop) - i2) + this.mScrollY;
            if (!this.mStackFromBottom) {
                int i10 = this.mScrollY;
                if (childCount > 0 && i10 < 0) {
                    if (i6 != 0) {
                        rect.bottom = 0;
                        rect.top = i10;
                        drawOverscrollHeader(canvas, drawable, rect);
                    } else if (z8) {
                        rect.bottom = 0;
                        rect.top = -i5;
                        drawDivider(canvas, rect, -1);
                    }
                }
                int i11 = 0;
                int i12 = 0;
                while (i12 < childCount) {
                    int i13 = i8 + i12;
                    boolean z9 = i13 < headerViewsCount;
                    boolean z10 = i13 >= size;
                    if ((z5 || !z9) && (z6 || !z10)) {
                        i11 = getChildAt(i12).getBottom();
                        i4 = i5;
                        boolean z11 = i12 == childCount + (-1);
                        if (z8 && i11 < i9 && (!z || !z11)) {
                            boolean z12 = z11;
                            int i14 = i13 + 1;
                            if (listAdapter.isEnabled(i13) && ((z5 || (!z9 && i14 >= headerViewsCount)) && (z12 || (listAdapter.isEnabled(i14) && (z6 || (!z10 && i14 < size)))))) {
                                rect.top = i11;
                                rect.bottom = i11 + i4;
                                drawDivider(canvas, rect, i12);
                            } else if (z7) {
                                rect.top = i11;
                                rect.bottom = i11 + i4;
                                canvas.drawRect(rect, paint2);
                            }
                        }
                    } else {
                        i4 = i5;
                    }
                    i12++;
                    i5 = i4;
                }
                int i15 = this.mBottom + this.mScrollY;
                if (z && i8 + childCount == i7 && i15 > i11) {
                    rect.top = i11;
                    rect.bottom = i15;
                    drawOverscrollFooter(canvas, drawable2, rect);
                }
            } else {
                int i16 = this.mScrollY;
                if (childCount <= 0 || i6 == 0) {
                    z2 = false;
                } else {
                    rect.top = i16;
                    z2 = false;
                    rect.bottom = getChildAt(0).getTop();
                    drawOverscrollHeader(canvas, drawable, rect);
                }
                int i17 = i6;
                while (i17 < childCount) {
                    int i18 = i8 + i17;
                    boolean z13 = i18 < headerViewsCount ? true : z2;
                    boolean z14 = i18 >= size ? true : z2;
                    if ((z5 || !z13) && (z6 || !z14)) {
                        int top = getChildAt(i17).getTop();
                        if (z8 && top > i) {
                            boolean z15 = i17 == i6;
                            i3 = i16;
                            int i19 = i18 - 1;
                            if (listAdapter.isEnabled(i18) && ((z5 || (!z13 && i19 >= headerViewsCount)) && (z15 || (listAdapter.isEnabled(i19) && (z6 || (!z14 && i19 < size)))))) {
                                rect.top = top - i5;
                                rect.bottom = top;
                                drawDivider(canvas, rect, i17 - 1);
                            } else if (z7) {
                                rect.top = top - i5;
                                rect.bottom = top;
                                canvas.drawRect(rect, paint2);
                            }
                            i17++;
                            i16 = i3;
                            z2 = false;
                        }
                    }
                    i3 = i16;
                    i17++;
                    i16 = i3;
                    z2 = false;
                }
                int i20 = i16;
                if (childCount > 0 && i20 > 0) {
                    if (z) {
                        int i21 = this.mBottom;
                        rect.top = i21;
                        rect.bottom = i21 + i20;
                        drawOverscrollFooter(canvas, drawable2, rect);
                    } else if (z8) {
                        rect.top = i9;
                        rect.bottom = i9 + i5;
                        drawDivider(canvas, rect, -1);
                    }
                }
            }
        }
        SemAddDeleteListAnimator semAddDeleteListAnimator = this.mAddDeleteListAnimator;
        if (semAddDeleteListAnimator != null) {
            semAddDeleteListAnimator.draw(canvas);
        }
        if (this.mSemEnableFillOut && this.mSemFillOutEmptyArea != -1) {
            canvas.drawRect(0.0f, this.mSemFillOutEmptyArea, this.mRight, this.mBottom, this.mSemFillOutPaint);
        }
        super.dispatchDraw(canvas);
        SemDragAndDropListAnimator semDragAndDropListAnimator = this.mDndListAnimator;
        if (semDragAndDropListAnimator != null) {
            semDragAndDropListAnimator.dispatchDraw(canvas);
        }
        if (this.mSweepListAnimator != null) {
            this.mSweepListAnimator.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        SemDragAndDropListAnimator semDragAndDropListAnimator = this.mDndListAnimator;
        if (semDragAndDropListAnimator != null && !semDragAndDropListAnimator.preDrawChild(canvas, view, j)) {
            return false;
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        if (this.mCachingActive && view.mCachingFailed) {
            this.mCachingActive = false;
        }
        SemDragAndDropListAnimator semDragAndDropListAnimator2 = this.mDndListAnimator;
        if (semDragAndDropListAnimator2 != null) {
            semDragAndDropListAnimator2.postDrawChild(canvas, view, j);
        }
        return drawChild;
    }

    void drawDivider(Canvas canvas, Rect rect, int i) {
        Drawable drawable = this.mDivider;
        drawable.setBounds(rect);
        drawable.draw(canvas);
    }

    public Drawable getDivider() {
        return this.mDivider;
    }

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

    public int getDividerHeight() {
        return this.mDividerHeight;
    }

    public void setDividerHeight(int i) {
        this.mDividerHeight = i;
        requestLayout();
        invalidate();
    }

    public void setHeaderDividersEnabled(boolean z) {
        this.mHeaderDividersEnabled = z;
        invalidate();
    }

    public boolean areHeaderDividersEnabled() {
        return this.mHeaderDividersEnabled;
    }

    public void setFooterDividersEnabled(boolean z) {
        this.mFooterDividersEnabled = z;
        invalidate();
    }

    public boolean areFooterDividersEnabled() {
        return this.mFooterDividersEnabled;
    }

    public void setOverscrollHeader(Drawable drawable) {
        this.mOverScrollHeader = drawable;
        if (this.mScrollY < 0) {
            invalidate();
        }
    }

    public Drawable getOverscrollHeader() {
        return this.mOverScrollHeader;
    }

    public void setOverscrollFooter(Drawable drawable) {
        this.mOverScrollFooter = drawable;
        invalidate();
    }

    public Drawable getOverscrollFooter() {
        return this.mOverScrollFooter;
    }

    @Override // android.widget.AbsListView, android.view.View
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
                        i6 = childAt.getTop();
                        i2 = i3;
                        i5 = distance;
                    }
                }
                i3++;
            }
            i3 = i6;
        }
        if (i2 >= 0) {
            setSelectionFromTop(i2 + this.mFirstPosition, i3);
        } else {
            requestLayout();
        }
        if (!z || this.mDndListAnimator == null) {
            return;
        }
        post(new Runnable() { // from class: android.widget.ListView.2
            @Override // java.lang.Runnable
            public void run() {
                ListView.this.mDndListAnimator.speakDescriptionForAccessibility();
            }
        });
    }

    @Override // android.view.View
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

    @Deprecated
    public long[] getCheckItemIds() {
        if (this.mAdapter != null && this.mAdapter.hasStableIds()) {
            return getCheckedItemIds();
        }
        if (this.mChoiceMode != 0 && this.mCheckStates != null && this.mAdapter != null) {
            SparseBooleanArray sparseBooleanArray = this.mCheckStates;
            int size = sparseBooleanArray.size();
            long[] jArr = new long[size];
            ListAdapter listAdapter = this.mAdapter;
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                if (sparseBooleanArray.valueAt(i2)) {
                    jArr[i] = listAdapter.getItemId(sparseBooleanArray.keyAt(i2));
                    i++;
                }
            }
            if (i == size) {
                return jArr;
            }
            long[] jArr2 = new long[i];
            System.arraycopy(jArr, 0, jArr2, 0, i);
            return jArr2;
        }
        return new long[0];
    }

    @Override // android.widget.AbsListView
    int getHeightForPosition(int i) {
        int heightForPosition = super.getHeightForPosition(i);
        return shouldAdjustHeightForDivider(i) ? heightForPosition + this.mDividerHeight : heightForPosition;
    }

    private boolean shouldAdjustHeightForDivider(int i) {
        int i2 = this.mDividerHeight;
        Drawable drawable = this.mOverScrollHeader;
        Drawable drawable2 = this.mOverScrollFooter;
        int i3 = drawable != null ? 1 : 0;
        boolean z = drawable2 != null;
        if (i2 > 0 && this.mDivider != null) {
            boolean z2 = isOpaque() && !super.isOpaque();
            int i4 = this.mItemCount;
            int headerViewsCount = getHeaderViewsCount();
            int size = i4 - this.mFooterViewInfos.size();
            boolean z3 = i < headerViewsCount;
            boolean z4 = i >= size;
            boolean z5 = this.mHeaderDividersEnabled;
            boolean z6 = this.mFooterDividersEnabled;
            if ((z5 || !z3) && (z6 || !z4)) {
                ListAdapter listAdapter = this.mAdapter;
                if (this.mStackFromBottom) {
                    boolean z7 = i == i3;
                    if (!z7) {
                        int i5 = i - 1;
                        if ((listAdapter.isEnabled(i) && ((z5 || (!z3 && i5 >= headerViewsCount)) && (z7 || (listAdapter.isEnabled(i5) && (z6 || (!z4 && i5 < size)))))) || z2) {
                            return true;
                        }
                    }
                } else {
                    boolean z8 = i == i4 - 1;
                    if (!z || !z8) {
                        int i6 = i + 1;
                        if ((listAdapter.isEnabled(i) && ((z5 || (!z3 && i6 >= headerViewsCount)) && (z8 || (listAdapter.isEnabled(i6) && (z6 || (!z4 && i6 < size)))))) || z2) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ListView.class.getName();
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        int count = getCount();
        accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(-1, -1, false, getSelectionModeForAccessibility()));
        if (count > 0) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION);
        }
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (i != 16908343) {
            return false;
        }
        int i2 = bundle.getInt(AccessibilityNodeInfo.ACTION_ARGUMENT_ROW_INT, -1);
        int min = Math.min(i2, getCount() - 1);
        if (i2 < 0) {
            return false;
        }
        smoothScrollToPosition(min);
        return true;
    }

    @Override // android.widget.AbsListView
    public void onInitializeAccessibilityNodeInfoForItem(View view, int i, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoForItem(view, i, accessibilityNodeInfo);
        AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) view.getLayoutParams();
        accessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(i, 1, 0, 1, layoutParams != null && layoutParams.viewType == -2, isItemChecked(i)));
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("recycleOnMeasure", recycleOnMeasure());
    }

    protected HeaderViewListAdapter wrapHeaderListAdapterInternal(ArrayList<FixedViewInfo> arrayList, ArrayList<FixedViewInfo> arrayList2, ListAdapter listAdapter) {
        return new HeaderViewListAdapter(arrayList, arrayList2, listAdapter);
    }

    protected void wrapHeaderListAdapterInternal() {
        this.mAdapter = wrapHeaderListAdapterInternal(this.mHeaderViewInfos, this.mFooterViewInfos, this.mAdapter);
    }

    protected void dispatchDataSetObserverOnChangedInternal() {
        if (this.mDataSetObserver != null) {
            this.mDataSetObserver.onChanged();
        }
    }

    @Override // android.widget.AbsListView
    boolean performLongPress(View view, int i, long j) {
        if (this.mSweepListAnimator == null || !this.mSweepListAnimator.isSwiping()) {
            return super.performLongPress(view, i, j);
        }
        return false;
    }

    @Override // android.widget.AbsListView
    public void removePendingCallbacks() {
        super.removePendingCallbacks();
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetSnapScroll(boolean z) {
        super.semSetAppWidgetSnapScroll(z);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetEnabled(boolean z) {
        super.semSetAppWidgetEnabled(z);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetGetCurrentPosition(String str) {
        super.semSetAppWidgetGetCurrentPosition(str);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetGetFirstPosition(String str) {
        super.semSetAppWidgetGetFirstPosition(str);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetIndicator(boolean z) {
        super.semSetAppWidgetIndicator(z);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetIndicatorBottomPadding(int i) {
        super.semSetAppWidgetIndicatorBottomPadding(i);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetIndicatorMarginHorizontal(int i) {
        super.semSetAppWidgetIndicatorMarginHorizontal(i);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetIndicatorWhere(int i) {
        super.semSetAppWidgetIndicatorWhere(i);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetGoToTopEnabledForAppWidget(boolean z) {
        super.semSetGoToTopEnabledForAppWidget(z);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semAllowDeferNotifyAfterRemoteViewsAdapterSet(boolean z) {
        super.semAllowDeferNotifyAfterRemoteViewsAdapterSet(z);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetGoToTopOffsetForAppWidget(int i) {
        super.semSetGoToTopOffsetForAppWidget(i);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetFastScrollEnabledForAppWidget(boolean z) {
        super.semSetFastScrollEnabledForAppWidget(z);
    }

    @Override // android.widget.AbsListView, android.view.View
    @RemotableViewMethod
    public void semSetScrollBarBottomPadding(int i) {
        super.semSetScrollBarBottomPadding(i);
    }

    @Override // android.widget.AbsListView, android.view.View
    @RemotableViewMethod
    public void semSetScrollBarTopPadding(int i) {
        super.semSetScrollBarTopPadding(i);
    }

    @RemotableViewMethod
    public void semEnableSelectZeroOnLastFocusTab(boolean z) {
        this.mSelectZeroPositionOnKeyTab = z;
    }

    @RemotableViewMethod
    public boolean semRequestFocus() {
        return super.requestFocus();
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetInnerFocus(boolean z) {
        super.semSetAppWidgetInnerFocus(z);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetNeedLayoutSpecificDone(boolean z) {
        super.semSetAppWidgetNeedLayoutSpecificDone(z);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetImmersiveEnabled(boolean z) {
        super.semSetAppWidgetImmersiveEnabled(z);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        View childAt;
        super.addFocusables(arrayList, i, i2);
        if (this.mAppWidgetSnapScroll && i == 2 && (childAt = getChildAt(this.mSelectedPosition - this.mFirstPosition)) != null && (getHeight() - this.mListPadding.bottom) - this.mListPadding.top == childAt.getHeight()) {
            ArrayList arrayList2 = new ArrayList();
            int i3 = 0;
            while (true) {
                ViewGroup viewGroup = (ViewGroup) childAt;
                if (i3 >= viewGroup.getChildCount()) {
                    break;
                }
                arrayList2.add(viewGroup.getChildAt(i3));
                i3++;
            }
            ArrayList<View> arrayList3 = new ArrayList<>();
            Iterator<View> it = arrayList.iterator();
            while (it.hasNext()) {
                View next = it.next();
                if (next != this && !arrayList2.contains(next) && !next.hasFocusable()) {
                    arrayList3.add(next);
                }
            }
            if (getChildAt(1) != null) {
                getViewsToRemove(arrayList3, (ViewGroup) getChildAt(this.mSelectedPosition - this.mFirstPosition != 1 ? 1 : 0));
            }
            arrayList.removeAll(arrayList3);
        }
    }

    private void getViewsToRemove(ArrayList<View> arrayList, ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt != null && childAt.hasFocusable()) {
                if (childAt.isFocusable()) {
                    arrayList.add(childAt);
                }
                if (childAt instanceof ViewGroup) {
                    ViewGroup viewGroup2 = (ViewGroup) childAt;
                    if (viewGroup2.getDescendantFocusability() != 393216) {
                        getViewsToRemove(arrayList, viewGroup2);
                    }
                }
            }
        }
    }

    private void addClickables(View view) {
        if (this.mClickableViewStates.isEmpty() && (view instanceof ViewGroup)) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt != null && childAt != this && !(childAt instanceof ViewGroup) && childAt.isClickable() && childAt.getVisibility() == 0) {
                    this.mClickableViewStates.put(Integer.valueOf(this.mClickableViewStates.size()), new AbsListView.ClickableViewState(childAt, false));
                }
                addClickables(childAt);
            }
        }
    }

    private boolean consumeClickables() {
        if (this.mClickableViewStates.isEmpty()) {
            return false;
        }
        for (Map.Entry<Integer, AbsListView.ClickableViewState> entry : this.mClickableViewStates.entrySet()) {
            Integer key = entry.getKey();
            AbsListView.ClickableViewState value = entry.getValue();
            if (value != null && !value.getWasFocused()) {
                this.mNextClickable = value.getView();
                if (this.mNextClickable != null && this.mNextClickable.getVisibility() != 0) {
                    this.mClickableViewStates.remove(key);
                    this.mNextClickable = null;
                } else {
                    value.setWasFocused(true);
                    value.setIsFocused(true);
                    this.mClickableViewStates.put(key, value);
                    return true;
                }
            }
        }
        this.mClickableViewStates.clear();
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.widget.ListView] */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    private View getDeepestFocusedChild() {
        while (this != 0) {
            if (this.isFocused()) {
                return this;
            }
            this = this instanceof ViewGroup ? ((ViewGroup) this).getFocusedChild() : 0;
        }
        return null;
    }

    @Override // android.widget.AbsListView
    public boolean semHandleGenericMotionEvent(int i) {
        return pointerScroll(i);
    }

    private final int nextFocusedPositionForDirection(View view, int i, int i2) {
        int i3;
        if (i2 == 130) {
            int height = getHeight() - this.mListPadding.bottom;
            if (view == null || view.getBottom() > height) {
                return -1;
            }
            i3 = (i == -1 || i < this.mFirstPosition) ? this.mFirstPosition : i + 1;
        } else {
            int i4 = this.mListPadding.top;
            if (view != null && view.getTop() >= i4) {
                int childCount = (this.mFirstPosition + getChildCount()) - 1;
                i3 = (i == -1 || i > childCount) ? childCount : i - 1;
            }
            return -1;
        }
        if (i3 < 0 || i3 >= this.mAdapter.getCount()) {
            return -1;
        }
        return i3;
    }

    private boolean pointerScroll(int i) {
        boolean z;
        if (this.mAnimator.isRunning()) {
            this.mAnimator.end();
        }
        boolean z2 = false;
        if (getChildCount() <= 0) {
            return false;
        }
        View childAt = getChildAt(0);
        int i2 = this.mNewFocusedPos;
        int nextFocusedPositionForDirection = nextFocusedPositionForDirection(childAt, i2, i);
        int amountToScroll = amountToScroll(i, nextFocusedPositionForDirection);
        if (childAt == null) {
            return true;
        }
        int height = getHeight() - this.mListPadding.bottom;
        if (amountToScroll > 0) {
            if (i == 33 && i2 > 0) {
                nextFocusedPositionForDirection = i2 - 1;
            } else if (i != 130 || i2 >= this.mAdapter.getCount() - 1) {
                z = false;
                if (i == 33 || childAt.getHeight() <= height - this.mListPadding.top || childAt.getTop() == 0) {
                    z2 = z;
                } else if (childAt.getTop() + amountToScroll >= 0) {
                    amountToScroll = -childAt.getTop();
                }
            } else {
                nextFocusedPositionForDirection = i2 + 1;
            }
            z = true;
            if (i == 33) {
            }
            z2 = z;
        }
        if (z2) {
            setSelection(nextFocusedPositionForDirection);
            return true;
        }
        if (amountToScroll > 0) {
            semShowGoToTOP();
            if (i != 33) {
                amountToScroll = -amountToScroll;
            }
            scrollListItemsBy(amountToScroll);
        }
        return true;
    }

    @Override // android.widget.AbsListView
    public void setOnScrollOffsetListener(AbsListView.OnScrollOffsetListener onScrollOffsetListener) {
        super.setOnScrollOffsetListener(onScrollOffsetListener);
    }

    public boolean semGetAppWidgetImmersiveEnabled() {
        return this.mAppWidgetImmersiveEnalbed;
    }
}

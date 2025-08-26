package android.widget;

import android.app.slice.Slice;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
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
import android.view.ViewRootImpl;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
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
import java.io.IOException;
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
    private int mMonotoneBaseColor;
    Drawable mOverScrollFooter;
    Drawable mOverScrollHeader;
    private boolean mSelectZeroPositionOnKeyTab;
    private final Rect mTempRect;

    private int getTopSelectionPixel(int i, int i2, int i3) {
        return i3 > 0 ? i + i2 : i;
    }

    private int semGetAlphaFromColor(int i) {
        return (i >> 24) & 255;
    }

    private int semGetMixedColorWithAlpha(int i, int i2) {
        return (i2 << 24) | i;
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
            public void onAutoScroll(int i) throws Resources.NotFoundException {
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
        int dimensionPixelSize;
        super(context, attributeSet, i, i2);
        this.mHeaderViewInfos = Lists.newArrayList();
        this.mFooterViewInfos = Lists.newArrayList();
        this.mAreAllItemsSelectable = true;
        this.mItemsCanFocus = false;
        this.mTempRect = new Rect();
        this.mArrowScrollFocusResult = new ArrowScrollFocusResult();
        this.mSelectZeroPositionOnKeyTab = false;
        this.mMonotoneBaseColor = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListView, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.ListView, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        CharSequence[] textArray = typedArrayObtainStyledAttributes.getTextArray(0);
        if (textArray != null) {
            setAdapter((ListAdapter) new ArrayAdapter(context, 17367043, textArray));
        }
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(1);
        if (drawable != null) {
            setDivider(drawable);
        }
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(5);
        if (drawable2 != null) {
            setOverscrollHeader(drawable2);
        }
        Drawable drawable3 = typedArrayObtainStyledAttributes.getDrawable(6);
        if (drawable3 != null) {
            setOverscrollFooter(drawable3);
        }
        if (typedArrayObtainStyledAttributes.hasValueOrEmpty(2) && (dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, 0)) != 0) {
            setDividerHeight(dimensionPixelSize);
        }
        this.mHeaderDividersEnabled = typedArrayObtainStyledAttributes.getBoolean(3, true);
        this.mFooterDividersEnabled = typedArrayObtainStyledAttributes.getBoolean(4, true);
        typedArrayObtainStyledAttributes.recycle();
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

    @Override // android.widget.AdapterView
    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod(asyncImpl = "setRemoteViewsAdapterAsync")
    public void setRemoteViewsAdapter(Intent intent) {
        super.setRemoteViewsAdapter(intent);
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView
    public void setAdapter(ListAdapter listAdapter) {
        int iLookForSelectablePosition;
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
                iLookForSelectablePosition = lookForSelectablePosition(this.mItemCount - 1, false);
            } else {
                iLookForSelectablePosition = lookForSelectablePosition(0, true);
            }
            setSelectedPositionInt(iLookForSelectablePosition);
            setNextSelectedPositionInt(iLookForSelectablePosition);
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
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) throws Resources.NotFoundException {
        int iMax;
        int i;
        int i2;
        int i3 = rect.top;
        rect.offset(view.getLeft(), view.getTop());
        rect.offset(-view.getScrollX(), -view.getScrollY());
        int height = getHeight();
        int scrollY = getScrollY();
        int i4 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (showingTopFadingEdge() && (this.mSelectedPosition > 0 || i3 > verticalFadingEdgeLength)) {
            scrollY += verticalFadingEdgeLength;
        }
        int bottom = getChildAt(getChildCount() - 1).getBottom();
        if (showingBottomFadingEdge() && (this.mSelectedPosition < this.mItemCount - 1 || rect.bottom < bottom - verticalFadingEdgeLength)) {
            i4 -= verticalFadingEdgeLength;
        }
        if (rect.bottom > i4 && rect.top > scrollY) {
            if (rect.height() > height) {
                i2 = rect.top - scrollY;
            } else {
                i2 = rect.bottom - i4;
            }
            iMax = Math.min(i2, bottom - i4);
        } else if (rect.top >= scrollY || rect.bottom >= i4) {
            iMax = 0;
        } else {
            if (rect.height() > height) {
                i = 0 - (i4 - rect.bottom);
            } else {
                i = 0 - (scrollY - rect.top);
            }
            iMax = Math.max(i, getChildAt(0).getTop() - scrollY);
        }
        boolean z2 = iMax != 0;
        if (z2) {
            scrollListItemsBy(-iMax);
            positionSelector(-1, view);
            this.mSelectedTop = view.getTop();
            invalidate();
        }
        return z2;
    }

    @Override // android.widget.AbsListView
    void fillGap(boolean z) throws Resources.NotFoundException {
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

    private View fillDown(int i, int i2) throws Resources.NotFoundException {
        int i3 = this.mBottom - this.mTop;
        View view = null;
        if ((this.mGroupFlags & 34) == 34) {
            i3 -= this.mListPadding.bottom;
        }
        int i4 = i;
        int bottom = i2;
        while (true) {
            if (bottom >= i3 || i4 >= this.mItemCount) {
                break;
            }
            boolean z = i4 == this.mSelectedPosition;
            ListView listView = this;
            View viewMakeAndAddView = listView.makeAndAddView(i4, bottom, true, this.mListPadding.left, z);
            bottom = viewMakeAndAddView.getBottom() + listView.mDividerHeight;
            if (z) {
                view = viewMakeAndAddView;
            }
            i4++;
            this = listView;
        }
        ListView listView2 = this;
        listView2.setVisibleRangeHint(listView2.mFirstPosition, (listView2.mFirstPosition + listView2.getChildCount()) - 1);
        return view;
    }

    private View fillUp(int i, int i2) throws Resources.NotFoundException {
        int i3;
        int top;
        int i4;
        View view = null;
        if ((this.mGroupFlags & 34) == 34) {
            i4 = this.mListPadding.top;
            i3 = i;
            top = i2;
        } else {
            i3 = i;
            top = i2;
            i4 = 0;
        }
        while (top > i4 && i3 >= 0) {
            boolean z = i3 == this.mSelectedPosition;
            ListView listView = this;
            View viewMakeAndAddView = listView.makeAndAddView(i3, top, false, this.mListPadding.left, z);
            top = viewMakeAndAddView.getTop() - listView.mDividerHeight;
            if (z) {
                view = viewMakeAndAddView;
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

    private View fillFromMiddle(int i, int i2) throws Resources.NotFoundException {
        int i3 = i2 - i;
        int iReconcileSelectedPosition = reconcileSelectedPosition();
        View viewMakeAndAddView = makeAndAddView(iReconcileSelectedPosition, i, true, this.mListPadding.left, true);
        this.mFirstPosition = iReconcileSelectedPosition;
        int measuredHeight = viewMakeAndAddView.getMeasuredHeight();
        if (measuredHeight <= i3) {
            viewMakeAndAddView.offsetTopAndBottom((i3 - measuredHeight) / 2);
        }
        fillAboveAndBelow(viewMakeAndAddView, iReconcileSelectedPosition);
        if (!this.mStackFromBottom) {
            correctTooHigh(getChildCount());
            return viewMakeAndAddView;
        }
        correctTooLow(getChildCount());
        return viewMakeAndAddView;
    }

    private void fillAboveAndBelow(View view, int i) throws Resources.NotFoundException {
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

    private View fillFromSelection(int i, int i2, int i3) throws Resources.NotFoundException {
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int i4 = this.mSelectedPosition;
        int topSelectionPixel = getTopSelectionPixel(i2, verticalFadingEdgeLength, i4);
        int bottomSelectionPixel = getBottomSelectionPixel(i3, verticalFadingEdgeLength, i4);
        View viewMakeAndAddView = makeAndAddView(i4, i, true, this.mListPadding.left, true);
        if (viewMakeAndAddView.getBottom() > bottomSelectionPixel) {
            viewMakeAndAddView.offsetTopAndBottom(-Math.min(viewMakeAndAddView.getTop() - topSelectionPixel, viewMakeAndAddView.getBottom() - bottomSelectionPixel));
        } else if (viewMakeAndAddView.getTop() < topSelectionPixel) {
            viewMakeAndAddView.offsetTopAndBottom(Math.min(topSelectionPixel - viewMakeAndAddView.getTop(), bottomSelectionPixel - viewMakeAndAddView.getBottom()));
        }
        fillAboveAndBelow(viewMakeAndAddView, i4);
        if (!this.mStackFromBottom) {
            correctTooHigh(getChildCount());
            return viewMakeAndAddView;
        }
        correctTooLow(getChildCount());
        return viewMakeAndAddView;
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

    private View moveSelection(View view, View view2, int i, int i2, int i3) throws Resources.NotFoundException {
        View viewMakeAndAddView;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int i4 = this.mSelectedPosition;
        int topSelectionPixel = getTopSelectionPixel(i2, verticalFadingEdgeLength, i4);
        int bottomSelectionPixel = getBottomSelectionPixel(i2, verticalFadingEdgeLength, i4);
        if (i > 0) {
            View viewMakeAndAddView2 = makeAndAddView(i4 - 1, view.getTop(), true, this.mListPadding.left, false);
            int i5 = this.mDividerHeight;
            View viewMakeAndAddView3 = makeAndAddView(i4, viewMakeAndAddView2.getBottom() + i5, true, this.mListPadding.left, true);
            if (viewMakeAndAddView3.getBottom() > bottomSelectionPixel) {
                int i6 = -Math.min(Math.min(viewMakeAndAddView3.getTop() - topSelectionPixel, viewMakeAndAddView3.getBottom() - bottomSelectionPixel), (i3 - i2) / 2);
                viewMakeAndAddView2.offsetTopAndBottom(i6);
                viewMakeAndAddView3.offsetTopAndBottom(i6);
            }
            if (!this.mStackFromBottom) {
                fillUp(this.mSelectedPosition - 2, viewMakeAndAddView3.getTop() - i5);
                adjustViewsUpOrDown();
                fillDown(this.mSelectedPosition + 1, viewMakeAndAddView3.getBottom() + i5);
                return viewMakeAndAddView3;
            }
            fillDown(this.mSelectedPosition + 1, viewMakeAndAddView3.getBottom() + i5);
            adjustViewsUpOrDown();
            fillUp(this.mSelectedPosition - 2, viewMakeAndAddView3.getTop() - i5);
            return viewMakeAndAddView3;
        }
        if (i < 0) {
            if (view2 != null) {
                viewMakeAndAddView = makeAndAddView(i4, view2.getTop(), true, this.mListPadding.left, true);
            } else {
                viewMakeAndAddView = makeAndAddView(i4, view.getTop(), false, this.mListPadding.left, true);
            }
            if (viewMakeAndAddView.getTop() < topSelectionPixel) {
                viewMakeAndAddView.offsetTopAndBottom(Math.min(Math.min(topSelectionPixel - viewMakeAndAddView.getTop(), bottomSelectionPixel - viewMakeAndAddView.getBottom()), (i3 - i2) / 2));
            }
            fillAboveAndBelow(viewMakeAndAddView, i4);
            return viewMakeAndAddView;
        }
        int top = view.getTop();
        View viewMakeAndAddView4 = makeAndAddView(i4, top, true, this.mListPadding.left, true);
        if (top < i2 && viewMakeAndAddView4.getBottom() < i2 + 20) {
            viewMakeAndAddView4.offsetTopAndBottom(i2 - viewMakeAndAddView4.getTop());
        }
        fillAboveAndBelow(viewMakeAndAddView4, i4);
        return viewMakeAndAddView4;
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
        public void run() throws Resources.NotFoundException {
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
            int iIndexOfChild = this.mFirstPosition + indexOfChild(focusedChild);
            int top = focusedChild.getTop() - Math.max(0, focusedChild.getBottom() - (i2 - this.mPaddingTop));
            if (this.mFocusSelector == null) {
                this.mFocusSelector = new FocusSelector();
            }
            post(this.mFocusSelector.setupForSetSelection(iIndexOfChild, top));
        }
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onMeasure(int i, int i2) {
        int measuredWidth;
        int measuredHeight;
        ListView listView;
        int i3;
        View viewObtainView;
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int iCombineMeasuredStates = 0;
        this.mItemCount = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        if (this.mItemCount <= 0 || (!(mode == 0 || mode2 == 0) || (viewObtainView = obtainView(0, this.mIsScrap)) == null)) {
            measuredWidth = 0;
            measuredHeight = 0;
        } else {
            measureScrapChild(viewObtainView, 0, i, size2);
            measuredWidth = viewObtainView.getMeasuredWidth();
            measuredHeight = viewObtainView.getMeasuredHeight();
            iCombineMeasuredStates = combineMeasuredStates(0, viewObtainView.getMeasuredState());
            if (recycleOnMeasure() && this.mRecycler.shouldRecycleViewType(((AbsListView.LayoutParams) viewObtainView.getLayoutParams()).viewType)) {
                this.mRecycler.addScrapView(viewObtainView, -1);
            }
        }
        int verticalScrollbarWidth = mode == 0 ? this.mListPadding.left + this.mListPadding.right + measuredWidth + getVerticalScrollbarWidth() : ((-16777216) & iCombineMeasuredStates) | size;
        if (mode2 == 0) {
            size2 = this.mListPadding.top + this.mListPadding.bottom + measuredHeight + (getVerticalFadingEdgeLength() * 2);
        }
        int iMeasureHeightOfChildren = size2;
        if (mode2 == Integer.MIN_VALUE) {
            listView = this;
            i3 = i;
            iMeasureHeightOfChildren = listView.measureHeightOfChildren(i3, 0, -1, iMeasureHeightOfChildren, -1);
        } else {
            listView = this;
            i3 = i;
        }
        listView.setMeasuredDimension(verticalScrollbarWidth, iMeasureHeightOfChildren);
        listView.mWidthMeasureSpec = i3;
    }

    private void measureScrapChild(View view, int i, int i2, int i3) {
        int iMakeSafeMeasureSpec;
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
            iMakeSafeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
        } else {
            iMakeSafeMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(i3, 0);
        }
        view.measure(childMeasureSpec, iMakeSafeMeasureSpec);
        view.forceLayout();
    }

    final int measureHeightOfChildren(int i, int i2, int i3, int i4, int i5) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null) {
            return this.mListPadding.top + this.mListPadding.bottom;
        }
        int measuredHeight = this.mListPadding.top + this.mListPadding.bottom;
        int i6 = this.mDividerHeight;
        if (i3 == -1) {
            i3 = listAdapter.getCount() - 1;
        }
        AbsListView.RecycleBin recycleBin = this.mRecycler;
        boolean zRecycleOnMeasure = recycleOnMeasure();
        boolean[] zArr = this.mIsScrap;
        int i7 = 0;
        while (i2 <= i3) {
            View viewObtainView = obtainView(i2, zArr);
            measureScrapChild(viewObtainView, i2, i, i4);
            if (i2 > 0) {
                measuredHeight += i6;
            }
            if (zRecycleOnMeasure && recycleBin.shouldRecycleViewType(((AbsListView.LayoutParams) viewObtainView.getLayoutParams()).viewType)) {
                recycleBin.addScrapView(viewObtainView, -1);
            }
            measuredHeight += viewObtainView.getMeasuredHeight();
            if (measuredHeight >= i4) {
                return (i5 < 0 || i2 <= i5 || i7 <= 0 || measuredHeight == i4) ? i4 : i7;
            }
            if (i5 >= 0 && i2 >= i5) {
                i7 = measuredHeight;
            }
            i2++;
        }
        return measuredHeight;
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

    private View fillSpecific(int i, int i2) throws Resources.NotFoundException {
        View viewFillUp;
        View viewFillDown;
        boolean z = i == this.mSelectedPosition;
        View viewMakeAndAddView = makeAndAddView(i, i2, true, this.mListPadding.left, z);
        this.mFirstPosition = i;
        int i3 = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            viewFillUp = fillUp(i - 1, viewMakeAndAddView.getTop() - i3);
            adjustViewsUpOrDown();
            viewFillDown = fillDown(i + 1, viewMakeAndAddView.getBottom() + i3);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHigh(childCount);
            }
        } else {
            View viewFillDown2 = fillDown(i + 1, viewMakeAndAddView.getBottom() + i3);
            adjustViewsUpOrDown();
            View viewFillUp2 = fillUp(i - 1, viewMakeAndAddView.getTop() - i3);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLow(childCount2);
            }
            viewFillUp = viewFillUp2;
            viewFillDown = viewFillDown2;
        }
        return z ? viewMakeAndAddView : viewFillUp != null ? viewFillUp : viewFillDown;
    }

    private void correctTooHigh(int i) throws Resources.NotFoundException {
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

    private void correctTooLow(int i) throws Resources.NotFoundException {
        if (this.mFirstPosition != 0 || i <= 0) {
            return;
        }
        int top = getChildAt(0).getTop();
        int i2 = this.mListPadding.top;
        int i3 = (this.mBottom - this.mTop) - this.mListPadding.bottom;
        int iMin = top - i2;
        View childAt = getChildAt(i - 1);
        int bottom = childAt.getBottom();
        int i4 = this.mFirstPosition + i;
        int i5 = i4 - 1;
        if (iMin > 0) {
            if (i5 < this.mItemCount - 1 || bottom > i3) {
                if (i5 == this.mItemCount - 1) {
                    iMin = Math.min(iMin, bottom - i3);
                }
                offsetChildrenTopAndBottom(-iMin);
                if (i5 < this.mItemCount - 1) {
                    fillDown(i4, childAt.getBottom() + this.mDividerHeight);
                    adjustViewsUpOrDown();
                    return;
                }
                return;
            }
            if (i5 == this.mItemCount - 1) {
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

    /* JADX WARN: Removed duplicated region for block: B:103:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0162 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:101:0x0142, B:105:0x0149, B:108:0x0169, B:109:0x0171, B:112:0x0178, B:140:0x01fd, B:159:0x0253, B:161:0x0262, B:163:0x0266, B:165:0x026c, B:170:0x0278, B:174:0x0284, B:180:0x02a0, B:206:0x02fc, B:209:0x0304, B:211:0x030a, B:214:0x0312, B:215:0x0321, B:218:0x0328, B:220:0x033e, B:223:0x0345, B:225:0x0357, B:227:0x0368, B:230:0x0370, B:232:0x0375, B:226:0x0364, B:233:0x0378, B:235:0x037c, B:249:0x03ac, B:251:0x03b2, B:252:0x03b5, B:254:0x03c0, B:255:0x03c8, B:257:0x03d7, B:258:0x03da, B:237:0x0380, B:239:0x0384, B:242:0x0392, B:246:0x03a4, B:245:0x03a0, B:247:0x03a7, B:172:0x027e, B:175:0x028e, B:177:0x0294, B:178:0x0297, B:179:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:201:0x02ef, B:204:0x02f7, B:195:0x02d2, B:197:0x02d6, B:198:0x02df, B:200:0x02ea, B:141:0x020b, B:142:0x0222, B:144:0x0226, B:146:0x022c, B:150:0x0235, B:149:0x0231, B:151:0x023a, B:153:0x0240, B:157:0x0249, B:156:0x0245, B:158:0x024e, B:113:0x017c, B:114:0x0186, B:116:0x018a, B:117:0x0199, B:118:0x01a3, B:120:0x01af, B:122:0x01b3, B:124:0x01b9, B:125:0x01bc, B:127:0x01c0, B:129:0x01c4, B:131:0x01c8, B:133:0x01ce, B:136:0x01e1, B:137:0x01eb, B:138:0x01f0, B:107:0x0162, B:266:0x03ec, B:267:0x0420), top: B:278:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x017c A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:101:0x0142, B:105:0x0149, B:108:0x0169, B:109:0x0171, B:112:0x0178, B:140:0x01fd, B:159:0x0253, B:161:0x0262, B:163:0x0266, B:165:0x026c, B:170:0x0278, B:174:0x0284, B:180:0x02a0, B:206:0x02fc, B:209:0x0304, B:211:0x030a, B:214:0x0312, B:215:0x0321, B:218:0x0328, B:220:0x033e, B:223:0x0345, B:225:0x0357, B:227:0x0368, B:230:0x0370, B:232:0x0375, B:226:0x0364, B:233:0x0378, B:235:0x037c, B:249:0x03ac, B:251:0x03b2, B:252:0x03b5, B:254:0x03c0, B:255:0x03c8, B:257:0x03d7, B:258:0x03da, B:237:0x0380, B:239:0x0384, B:242:0x0392, B:246:0x03a4, B:245:0x03a0, B:247:0x03a7, B:172:0x027e, B:175:0x028e, B:177:0x0294, B:178:0x0297, B:179:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:201:0x02ef, B:204:0x02f7, B:195:0x02d2, B:197:0x02d6, B:198:0x02df, B:200:0x02ea, B:141:0x020b, B:142:0x0222, B:144:0x0226, B:146:0x022c, B:150:0x0235, B:149:0x0231, B:151:0x023a, B:153:0x0240, B:157:0x0249, B:156:0x0245, B:158:0x024e, B:113:0x017c, B:114:0x0186, B:116:0x018a, B:117:0x0199, B:118:0x01a3, B:120:0x01af, B:122:0x01b3, B:124:0x01b9, B:125:0x01bc, B:127:0x01c0, B:129:0x01c4, B:131:0x01c8, B:133:0x01ce, B:136:0x01e1, B:137:0x01eb, B:138:0x01f0, B:107:0x0162, B:266:0x03ec, B:267:0x0420), top: B:278:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0186 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:101:0x0142, B:105:0x0149, B:108:0x0169, B:109:0x0171, B:112:0x0178, B:140:0x01fd, B:159:0x0253, B:161:0x0262, B:163:0x0266, B:165:0x026c, B:170:0x0278, B:174:0x0284, B:180:0x02a0, B:206:0x02fc, B:209:0x0304, B:211:0x030a, B:214:0x0312, B:215:0x0321, B:218:0x0328, B:220:0x033e, B:223:0x0345, B:225:0x0357, B:227:0x0368, B:230:0x0370, B:232:0x0375, B:226:0x0364, B:233:0x0378, B:235:0x037c, B:249:0x03ac, B:251:0x03b2, B:252:0x03b5, B:254:0x03c0, B:255:0x03c8, B:257:0x03d7, B:258:0x03da, B:237:0x0380, B:239:0x0384, B:242:0x0392, B:246:0x03a4, B:245:0x03a0, B:247:0x03a7, B:172:0x027e, B:175:0x028e, B:177:0x0294, B:178:0x0297, B:179:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:201:0x02ef, B:204:0x02f7, B:195:0x02d2, B:197:0x02d6, B:198:0x02df, B:200:0x02ea, B:141:0x020b, B:142:0x0222, B:144:0x0226, B:146:0x022c, B:150:0x0235, B:149:0x0231, B:151:0x023a, B:153:0x0240, B:157:0x0249, B:156:0x0245, B:158:0x024e, B:113:0x017c, B:114:0x0186, B:116:0x018a, B:117:0x0199, B:118:0x01a3, B:120:0x01af, B:122:0x01b3, B:124:0x01b9, B:125:0x01bc, B:127:0x01c0, B:129:0x01c4, B:131:0x01c8, B:133:0x01ce, B:136:0x01e1, B:137:0x01eb, B:138:0x01f0, B:107:0x0162, B:266:0x03ec, B:267:0x0420), top: B:278:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x01a3 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:101:0x0142, B:105:0x0149, B:108:0x0169, B:109:0x0171, B:112:0x0178, B:140:0x01fd, B:159:0x0253, B:161:0x0262, B:163:0x0266, B:165:0x026c, B:170:0x0278, B:174:0x0284, B:180:0x02a0, B:206:0x02fc, B:209:0x0304, B:211:0x030a, B:214:0x0312, B:215:0x0321, B:218:0x0328, B:220:0x033e, B:223:0x0345, B:225:0x0357, B:227:0x0368, B:230:0x0370, B:232:0x0375, B:226:0x0364, B:233:0x0378, B:235:0x037c, B:249:0x03ac, B:251:0x03b2, B:252:0x03b5, B:254:0x03c0, B:255:0x03c8, B:257:0x03d7, B:258:0x03da, B:237:0x0380, B:239:0x0384, B:242:0x0392, B:246:0x03a4, B:245:0x03a0, B:247:0x03a7, B:172:0x027e, B:175:0x028e, B:177:0x0294, B:178:0x0297, B:179:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:201:0x02ef, B:204:0x02f7, B:195:0x02d2, B:197:0x02d6, B:198:0x02df, B:200:0x02ea, B:141:0x020b, B:142:0x0222, B:144:0x0226, B:146:0x022c, B:150:0x0235, B:149:0x0231, B:151:0x023a, B:153:0x0240, B:157:0x0249, B:156:0x0245, B:158:0x024e, B:113:0x017c, B:114:0x0186, B:116:0x018a, B:117:0x0199, B:118:0x01a3, B:120:0x01af, B:122:0x01b3, B:124:0x01b9, B:125:0x01bc, B:127:0x01c0, B:129:0x01c4, B:131:0x01c8, B:133:0x01ce, B:136:0x01e1, B:137:0x01eb, B:138:0x01f0, B:107:0x0162, B:266:0x03ec, B:267:0x0420), top: B:278:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x01ce A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:101:0x0142, B:105:0x0149, B:108:0x0169, B:109:0x0171, B:112:0x0178, B:140:0x01fd, B:159:0x0253, B:161:0x0262, B:163:0x0266, B:165:0x026c, B:170:0x0278, B:174:0x0284, B:180:0x02a0, B:206:0x02fc, B:209:0x0304, B:211:0x030a, B:214:0x0312, B:215:0x0321, B:218:0x0328, B:220:0x033e, B:223:0x0345, B:225:0x0357, B:227:0x0368, B:230:0x0370, B:232:0x0375, B:226:0x0364, B:233:0x0378, B:235:0x037c, B:249:0x03ac, B:251:0x03b2, B:252:0x03b5, B:254:0x03c0, B:255:0x03c8, B:257:0x03d7, B:258:0x03da, B:237:0x0380, B:239:0x0384, B:242:0x0392, B:246:0x03a4, B:245:0x03a0, B:247:0x03a7, B:172:0x027e, B:175:0x028e, B:177:0x0294, B:178:0x0297, B:179:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:201:0x02ef, B:204:0x02f7, B:195:0x02d2, B:197:0x02d6, B:198:0x02df, B:200:0x02ea, B:141:0x020b, B:142:0x0222, B:144:0x0226, B:146:0x022c, B:150:0x0235, B:149:0x0231, B:151:0x023a, B:153:0x0240, B:157:0x0249, B:156:0x0245, B:158:0x024e, B:113:0x017c, B:114:0x0186, B:116:0x018a, B:117:0x0199, B:118:0x01a3, B:120:0x01af, B:122:0x01b3, B:124:0x01b9, B:125:0x01bc, B:127:0x01c0, B:129:0x01c4, B:131:0x01c8, B:133:0x01ce, B:136:0x01e1, B:137:0x01eb, B:138:0x01f0, B:107:0x0162, B:266:0x03ec, B:267:0x0420), top: B:278:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x01f0 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:101:0x0142, B:105:0x0149, B:108:0x0169, B:109:0x0171, B:112:0x0178, B:140:0x01fd, B:159:0x0253, B:161:0x0262, B:163:0x0266, B:165:0x026c, B:170:0x0278, B:174:0x0284, B:180:0x02a0, B:206:0x02fc, B:209:0x0304, B:211:0x030a, B:214:0x0312, B:215:0x0321, B:218:0x0328, B:220:0x033e, B:223:0x0345, B:225:0x0357, B:227:0x0368, B:230:0x0370, B:232:0x0375, B:226:0x0364, B:233:0x0378, B:235:0x037c, B:249:0x03ac, B:251:0x03b2, B:252:0x03b5, B:254:0x03c0, B:255:0x03c8, B:257:0x03d7, B:258:0x03da, B:237:0x0380, B:239:0x0384, B:242:0x0392, B:246:0x03a4, B:245:0x03a0, B:247:0x03a7, B:172:0x027e, B:175:0x028e, B:177:0x0294, B:178:0x0297, B:179:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:201:0x02ef, B:204:0x02f7, B:195:0x02d2, B:197:0x02d6, B:198:0x02df, B:200:0x02ea, B:141:0x020b, B:142:0x0222, B:144:0x0226, B:146:0x022c, B:150:0x0235, B:149:0x0231, B:151:0x023a, B:153:0x0240, B:157:0x0249, B:156:0x0245, B:158:0x024e, B:113:0x017c, B:114:0x0186, B:116:0x018a, B:117:0x0199, B:118:0x01a3, B:120:0x01af, B:122:0x01b3, B:124:0x01b9, B:125:0x01bc, B:127:0x01c0, B:129:0x01c4, B:131:0x01c8, B:133:0x01ce, B:136:0x01e1, B:137:0x01eb, B:138:0x01f0, B:107:0x0162, B:266:0x03ec, B:267:0x0420), top: B:278:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0262 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:101:0x0142, B:105:0x0149, B:108:0x0169, B:109:0x0171, B:112:0x0178, B:140:0x01fd, B:159:0x0253, B:161:0x0262, B:163:0x0266, B:165:0x026c, B:170:0x0278, B:174:0x0284, B:180:0x02a0, B:206:0x02fc, B:209:0x0304, B:211:0x030a, B:214:0x0312, B:215:0x0321, B:218:0x0328, B:220:0x033e, B:223:0x0345, B:225:0x0357, B:227:0x0368, B:230:0x0370, B:232:0x0375, B:226:0x0364, B:233:0x0378, B:235:0x037c, B:249:0x03ac, B:251:0x03b2, B:252:0x03b5, B:254:0x03c0, B:255:0x03c8, B:257:0x03d7, B:258:0x03da, B:237:0x0380, B:239:0x0384, B:242:0x0392, B:246:0x03a4, B:245:0x03a0, B:247:0x03a7, B:172:0x027e, B:175:0x028e, B:177:0x0294, B:178:0x0297, B:179:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:201:0x02ef, B:204:0x02f7, B:195:0x02d2, B:197:0x02d6, B:198:0x02df, B:200:0x02ea, B:141:0x020b, B:142:0x0222, B:144:0x0226, B:146:0x022c, B:150:0x0235, B:149:0x0231, B:151:0x023a, B:153:0x0240, B:157:0x0249, B:156:0x0245, B:158:0x024e, B:113:0x017c, B:114:0x0186, B:116:0x018a, B:117:0x0199, B:118:0x01a3, B:120:0x01af, B:122:0x01b3, B:124:0x01b9, B:125:0x01bc, B:127:0x01c0, B:129:0x01c4, B:131:0x01c8, B:133:0x01ce, B:136:0x01e1, B:137:0x01eb, B:138:0x01f0, B:107:0x0162, B:266:0x03ec, B:267:0x0420), top: B:278:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:181:0x02a7 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:101:0x0142, B:105:0x0149, B:108:0x0169, B:109:0x0171, B:112:0x0178, B:140:0x01fd, B:159:0x0253, B:161:0x0262, B:163:0x0266, B:165:0x026c, B:170:0x0278, B:174:0x0284, B:180:0x02a0, B:206:0x02fc, B:209:0x0304, B:211:0x030a, B:214:0x0312, B:215:0x0321, B:218:0x0328, B:220:0x033e, B:223:0x0345, B:225:0x0357, B:227:0x0368, B:230:0x0370, B:232:0x0375, B:226:0x0364, B:233:0x0378, B:235:0x037c, B:249:0x03ac, B:251:0x03b2, B:252:0x03b5, B:254:0x03c0, B:255:0x03c8, B:257:0x03d7, B:258:0x03da, B:237:0x0380, B:239:0x0384, B:242:0x0392, B:246:0x03a4, B:245:0x03a0, B:247:0x03a7, B:172:0x027e, B:175:0x028e, B:177:0x0294, B:178:0x0297, B:179:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:201:0x02ef, B:204:0x02f7, B:195:0x02d2, B:197:0x02d6, B:198:0x02df, B:200:0x02ea, B:141:0x020b, B:142:0x0222, B:144:0x0226, B:146:0x022c, B:150:0x0235, B:149:0x0231, B:151:0x023a, B:153:0x0240, B:157:0x0249, B:156:0x0245, B:158:0x024e, B:113:0x017c, B:114:0x0186, B:116:0x018a, B:117:0x0199, B:118:0x01a3, B:120:0x01af, B:122:0x01b3, B:124:0x01b9, B:125:0x01bc, B:127:0x01c0, B:129:0x01c4, B:131:0x01c8, B:133:0x01ce, B:136:0x01e1, B:137:0x01eb, B:138:0x01f0, B:107:0x0162, B:266:0x03ec, B:267:0x0420), top: B:278:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:206:0x02fc A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:101:0x0142, B:105:0x0149, B:108:0x0169, B:109:0x0171, B:112:0x0178, B:140:0x01fd, B:159:0x0253, B:161:0x0262, B:163:0x0266, B:165:0x026c, B:170:0x0278, B:174:0x0284, B:180:0x02a0, B:206:0x02fc, B:209:0x0304, B:211:0x030a, B:214:0x0312, B:215:0x0321, B:218:0x0328, B:220:0x033e, B:223:0x0345, B:225:0x0357, B:227:0x0368, B:230:0x0370, B:232:0x0375, B:226:0x0364, B:233:0x0378, B:235:0x037c, B:249:0x03ac, B:251:0x03b2, B:252:0x03b5, B:254:0x03c0, B:255:0x03c8, B:257:0x03d7, B:258:0x03da, B:237:0x0380, B:239:0x0384, B:242:0x0392, B:246:0x03a4, B:245:0x03a0, B:247:0x03a7, B:172:0x027e, B:175:0x028e, B:177:0x0294, B:178:0x0297, B:179:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:201:0x02ef, B:204:0x02f7, B:195:0x02d2, B:197:0x02d6, B:198:0x02df, B:200:0x02ea, B:141:0x020b, B:142:0x0222, B:144:0x0226, B:146:0x022c, B:150:0x0235, B:149:0x0231, B:151:0x023a, B:153:0x0240, B:157:0x0249, B:156:0x0245, B:158:0x024e, B:113:0x017c, B:114:0x0186, B:116:0x018a, B:117:0x0199, B:118:0x01a3, B:120:0x01af, B:122:0x01b3, B:124:0x01b9, B:125:0x01bc, B:127:0x01c0, B:129:0x01c4, B:131:0x01c8, B:133:0x01ce, B:136:0x01e1, B:137:0x01eb, B:138:0x01f0, B:107:0x0162, B:266:0x03ec, B:267:0x0420), top: B:278:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0390 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:247:0x03a7 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:101:0x0142, B:105:0x0149, B:108:0x0169, B:109:0x0171, B:112:0x0178, B:140:0x01fd, B:159:0x0253, B:161:0x0262, B:163:0x0266, B:165:0x026c, B:170:0x0278, B:174:0x0284, B:180:0x02a0, B:206:0x02fc, B:209:0x0304, B:211:0x030a, B:214:0x0312, B:215:0x0321, B:218:0x0328, B:220:0x033e, B:223:0x0345, B:225:0x0357, B:227:0x0368, B:230:0x0370, B:232:0x0375, B:226:0x0364, B:233:0x0378, B:235:0x037c, B:249:0x03ac, B:251:0x03b2, B:252:0x03b5, B:254:0x03c0, B:255:0x03c8, B:257:0x03d7, B:258:0x03da, B:237:0x0380, B:239:0x0384, B:242:0x0392, B:246:0x03a4, B:245:0x03a0, B:247:0x03a7, B:172:0x027e, B:175:0x028e, B:177:0x0294, B:178:0x0297, B:179:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:201:0x02ef, B:204:0x02f7, B:195:0x02d2, B:197:0x02d6, B:198:0x02df, B:200:0x02ea, B:141:0x020b, B:142:0x0222, B:144:0x0226, B:146:0x022c, B:150:0x0235, B:149:0x0231, B:151:0x023a, B:153:0x0240, B:157:0x0249, B:156:0x0245, B:158:0x024e, B:113:0x017c, B:114:0x0186, B:116:0x018a, B:117:0x0199, B:118:0x01a3, B:120:0x01af, B:122:0x01b3, B:124:0x01b9, B:125:0x01bc, B:127:0x01c0, B:129:0x01c4, B:131:0x01c8, B:133:0x01ce, B:136:0x01e1, B:137:0x01eb, B:138:0x01f0, B:107:0x0162, B:266:0x03ec, B:267:0x0420), top: B:278:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:254:0x03c0 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:101:0x0142, B:105:0x0149, B:108:0x0169, B:109:0x0171, B:112:0x0178, B:140:0x01fd, B:159:0x0253, B:161:0x0262, B:163:0x0266, B:165:0x026c, B:170:0x0278, B:174:0x0284, B:180:0x02a0, B:206:0x02fc, B:209:0x0304, B:211:0x030a, B:214:0x0312, B:215:0x0321, B:218:0x0328, B:220:0x033e, B:223:0x0345, B:225:0x0357, B:227:0x0368, B:230:0x0370, B:232:0x0375, B:226:0x0364, B:233:0x0378, B:235:0x037c, B:249:0x03ac, B:251:0x03b2, B:252:0x03b5, B:254:0x03c0, B:255:0x03c8, B:257:0x03d7, B:258:0x03da, B:237:0x0380, B:239:0x0384, B:242:0x0392, B:246:0x03a4, B:245:0x03a0, B:247:0x03a7, B:172:0x027e, B:175:0x028e, B:177:0x0294, B:178:0x0297, B:179:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:201:0x02ef, B:204:0x02f7, B:195:0x02d2, B:197:0x02d6, B:198:0x02df, B:200:0x02ea, B:141:0x020b, B:142:0x0222, B:144:0x0226, B:146:0x022c, B:150:0x0235, B:149:0x0231, B:151:0x023a, B:153:0x0240, B:157:0x0249, B:156:0x0245, B:158:0x024e, B:113:0x017c, B:114:0x0186, B:116:0x018a, B:117:0x0199, B:118:0x01a3, B:120:0x01af, B:122:0x01b3, B:124:0x01b9, B:125:0x01bc, B:127:0x01c0, B:129:0x01c4, B:131:0x01c8, B:133:0x01ce, B:136:0x01e1, B:137:0x01eb, B:138:0x01f0, B:107:0x0162, B:266:0x03ec, B:267:0x0420), top: B:278:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:257:0x03d7 A[Catch: all -> 0x0421, TryCatch #0 {all -> 0x0421, blocks: (B:101:0x0142, B:105:0x0149, B:108:0x0169, B:109:0x0171, B:112:0x0178, B:140:0x01fd, B:159:0x0253, B:161:0x0262, B:163:0x0266, B:165:0x026c, B:170:0x0278, B:174:0x0284, B:180:0x02a0, B:206:0x02fc, B:209:0x0304, B:211:0x030a, B:214:0x0312, B:215:0x0321, B:218:0x0328, B:220:0x033e, B:223:0x0345, B:225:0x0357, B:227:0x0368, B:230:0x0370, B:232:0x0375, B:226:0x0364, B:233:0x0378, B:235:0x037c, B:249:0x03ac, B:251:0x03b2, B:252:0x03b5, B:254:0x03c0, B:255:0x03c8, B:257:0x03d7, B:258:0x03da, B:237:0x0380, B:239:0x0384, B:242:0x0392, B:246:0x03a4, B:245:0x03a0, B:247:0x03a7, B:172:0x027e, B:175:0x028e, B:177:0x0294, B:178:0x0297, B:179:0x029c, B:181:0x02a7, B:183:0x02ac, B:186:0x02b2, B:188:0x02b7, B:190:0x02bd, B:192:0x02c1, B:194:0x02cc, B:201:0x02ef, B:204:0x02f7, B:195:0x02d2, B:197:0x02d6, B:198:0x02df, B:200:0x02ea, B:141:0x020b, B:142:0x0222, B:144:0x0226, B:146:0x022c, B:150:0x0235, B:149:0x0231, B:151:0x023a, B:153:0x0240, B:157:0x0249, B:156:0x0245, B:158:0x024e, B:113:0x017c, B:114:0x0186, B:116:0x018a, B:117:0x0199, B:118:0x01a3, B:120:0x01af, B:122:0x01b3, B:124:0x01b9, B:125:0x01bc, B:127:0x01c0, B:129:0x01c4, B:131:0x01c8, B:133:0x01ce, B:136:0x01e1, B:137:0x01eb, B:138:0x01f0, B:107:0x0162, B:266:0x03ec, B:267:0x0420), top: B:278:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:261:0x03e1  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x03e6  */
    /* JADX WARN: Removed duplicated region for block: B:283:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0094 A[Catch: all -> 0x0423, TryCatch #1 {all -> 0x0423, blocks: (B:6:0x000e, B:8:0x0018, B:15:0x002a, B:24:0x004d, B:27:0x0056, B:29:0x005c, B:31:0x0064, B:33:0x006b, B:40:0x0090, B:42:0x0094, B:43:0x0097, B:45:0x009b, B:52:0x00ad, B:54:0x00b7, B:56:0x00c2, B:58:0x00c8, B:64:0x00d7, B:66:0x00dd, B:68:0x00e3, B:73:0x00ef, B:76:0x00f9, B:79:0x0101, B:81:0x0107, B:83:0x010d, B:94:0x012c, B:96:0x0132, B:99:0x013e, B:87:0x0118, B:89:0x011e, B:72:0x00eb, B:34:0x007b, B:37:0x0084), top: B:279:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x009b A[Catch: all -> 0x0423, TRY_LEAVE, TryCatch #1 {all -> 0x0423, blocks: (B:6:0x000e, B:8:0x0018, B:15:0x002a, B:24:0x004d, B:27:0x0056, B:29:0x005c, B:31:0x0064, B:33:0x006b, B:40:0x0090, B:42:0x0094, B:43:0x0097, B:45:0x009b, B:52:0x00ad, B:54:0x00b7, B:56:0x00c2, B:58:0x00c8, B:64:0x00d7, B:66:0x00dd, B:68:0x00e3, B:73:0x00ef, B:76:0x00f9, B:79:0x0101, B:81:0x0107, B:83:0x010d, B:94:0x012c, B:96:0x0132, B:99:0x013e, B:87:0x0118, B:89:0x011e, B:72:0x00eb, B:34:0x007b, B:37:0x0084), top: B:279:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ad A[Catch: all -> 0x0423, TRY_ENTER, TryCatch #1 {all -> 0x0423, blocks: (B:6:0x000e, B:8:0x0018, B:15:0x002a, B:24:0x004d, B:27:0x0056, B:29:0x005c, B:31:0x0064, B:33:0x006b, B:40:0x0090, B:42:0x0094, B:43:0x0097, B:45:0x009b, B:52:0x00ad, B:54:0x00b7, B:56:0x00c2, B:58:0x00c8, B:64:0x00d7, B:66:0x00dd, B:68:0x00e3, B:73:0x00ef, B:76:0x00f9, B:79:0x0101, B:81:0x0107, B:83:0x010d, B:94:0x012c, B:96:0x0132, B:99:0x013e, B:87:0x0118, B:89:0x011e, B:72:0x00eb, B:34:0x007b, B:37:0x0084), top: B:279:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0138  */
    @Override // android.widget.AbsListView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void layoutChildren() throws Throwable {
        int i;
        View view;
        View childAt;
        View view2;
        boolean z;
        boolean z2;
        int positionForView;
        AccessibilityNodeInfo accessibilityFocusedVirtualView;
        View accessibilityFocusedHost;
        View focusedChild;
        ViewRootImpl viewRootImpl;
        View viewFindFocus;
        boolean z3;
        View view3;
        View view4;
        View viewFillFromTop;
        FocusSelector focusSelector;
        Runnable runnable;
        int lastVisiblePosition;
        FocusSelector focusSelector2;
        View childAt2;
        boolean z4;
        boolean z5 = this.mBlockLayoutRequests;
        if (z5) {
            return;
        }
        this.mBlockLayoutRequests = true;
        try {
            super.layoutChildren();
            invalidate();
        } catch (Throwable th) {
            th = th;
        }
        if (this.mAdapter == null) {
            resetList();
            invokeOnItemScrollListener();
            FocusSelector focusSelector3 = this.mFocusSelector;
            if (focusSelector3 != null) {
                focusSelector3.onLayoutComplete();
            }
            if (z5) {
                return;
            }
            this.mBlockLayoutRequests = false;
            return;
        }
        int top = this.mListPadding.top;
        int i2 = (this.mBottom - this.mTop) - this.mListPadding.bottom;
        int childCount = getChildCount();
        int i3 = this.mLayoutMode;
        if (i3 == 1) {
            i = 0;
            view = null;
            childAt = null;
            view2 = null;
            z = this.mDataChanged;
            if (z) {
                handleDataChanged();
            }
            if (this.mItemCount != 0) {
                resetList();
                invokeOnItemScrollListener();
                FocusSelector focusSelector4 = this.mFocusSelector;
                if (focusSelector4 != null) {
                    focusSelector4.onLayoutComplete();
                }
                if (z5) {
                    return;
                }
                this.mBlockLayoutRequests = false;
                return;
            }
            try {
                if (this.mItemCount != this.mAdapter.getCount()) {
                    throw new IllegalStateException("The content of the adapter has changed but ListView did not receive a notification. Make sure the content of your adapter is not modified from a background thread, but only from the UI thread. Make sure your adapter calls notifyDataSetChanged() when its content changes. [in ListView(" + getId() + ", " + getClass() + ") with Adapter(" + this.mAdapter.getClass() + ")]");
                }
                setSelectedPositionInt(this.mNextSelectedPosition);
                ViewRootImpl viewRootImpl2 = getViewRootImpl();
                if (viewRootImpl2 != null && (accessibilityFocusedHost = viewRootImpl2.getAccessibilityFocusedHost()) != null) {
                    View accessibilityFocusedChild = getAccessibilityFocusedChild(accessibilityFocusedHost);
                    z2 = accessibilityFocusedHost != accessibilityFocusedChild;
                    if (accessibilityFocusedChild != null) {
                        if (!z || isDirectChildHeaderOrFooter(accessibilityFocusedChild) || accessibilityFocusedChild.hasTransientState() || this.mAdapterHasStableIds) {
                            accessibilityFocusedVirtualView = viewRootImpl2.getAccessibilityFocusedVirtualView();
                        } else {
                            accessibilityFocusedVirtualView = null;
                            accessibilityFocusedHost = null;
                        }
                        positionForView = getPositionForView(accessibilityFocusedChild);
                    }
                    focusedChild = getFocusedChild();
                    if (focusedChild == null) {
                        if (!z || isDirectChildHeaderOrFooter(focusedChild) || focusedChild.hasTransientState() || this.mAdapterHasStableIds) {
                            viewFindFocus = findFocus();
                            if (viewFindFocus != null) {
                                viewFindFocus.dispatchStartTemporaryDetach();
                                viewRootImpl = viewRootImpl2;
                                if (this.mSelectedPosition == 0) {
                                    z4 = true;
                                }
                            } else {
                                viewRootImpl = viewRootImpl2;
                            }
                            z4 = false;
                        } else {
                            viewRootImpl = viewRootImpl2;
                            z4 = false;
                            focusedChild = null;
                            viewFindFocus = null;
                        }
                        if (!hasFocus()) {
                            requestFocus();
                        }
                        z3 = z4;
                    } else {
                        viewRootImpl = viewRootImpl2;
                        focusedChild = null;
                        viewFindFocus = null;
                        z3 = false;
                    }
                    int i4 = this.mFirstPosition;
                    AbsListView.RecycleBin recycleBin = this.mRecycler;
                    if (z) {
                        view3 = view;
                        view4 = childAt;
                        recycleBin.fillActiveViews(childCount, i4);
                    } else {
                        int i5 = 0;
                        while (i5 < childCount) {
                            recycleBin.addScrapView(getChildAt(i5), i4 + i5);
                            i5++;
                            view = view;
                            childAt = childAt;
                        }
                        view3 = view;
                        view4 = childAt;
                    }
                    detachAllViewsFromParent();
                    recycleBin.removeSkippedScrap();
                    switch (this.mLayoutMode) {
                        case 1:
                            this.mFirstPosition = 0;
                            viewFillFromTop = fillFromTop(top);
                            adjustViewsUpOrDown();
                            break;
                        case 2:
                            View view5 = view4;
                            if (view5 == null) {
                                viewFillFromTop = fillFromMiddle(top, i2);
                                break;
                            } else {
                                viewFillFromTop = fillFromSelection(view5.getTop(), top, i2);
                                break;
                            }
                        case 3:
                            viewFillFromTop = fillUp(this.mItemCount - 1, i2);
                            adjustViewsUpOrDown();
                            break;
                        case 4:
                            int iReconcileSelectedPosition = reconcileSelectedPosition();
                            View viewFillSpecific = fillSpecific(iReconcileSelectedPosition, this.mSpecificTop);
                            if (viewFillSpecific == null && (focusSelector = this.mFocusSelector) != null && (runnable = focusSelector.setupFocusIfValid(iReconcileSelectedPosition)) != null) {
                                post(runnable);
                            }
                            if (this.mAppWidgetSnapScroll && this.mNeedLayoutSpecificDone && !this.mIsLayoutSpecificDone) {
                                this.mIsLayoutSpecificDone = true;
                            }
                            viewFillFromTop = viewFillSpecific;
                            break;
                        case 5:
                            if (!this.mSemScrollingByScrollbar) {
                                viewFillFromTop = fillSpecific(this.mSyncPosition, this.mSpecificTop);
                                break;
                            } else {
                                this.mSpecificTop = 0;
                                viewFillFromTop = fillSpecific(reconcileSelectedPosition(), this.mSpecificTop);
                                break;
                            }
                        case 6:
                            viewFillFromTop = moveSelection(view3, view4, i, top, i2);
                            break;
                        default:
                            View view6 = view3;
                            if (childCount != 0) {
                                if (this.mSelectedPosition >= 0 && this.mSelectedPosition < this.mItemCount) {
                                    int i6 = this.mSelectedPosition;
                                    if (view6 != null) {
                                        top = view6.getTop();
                                    }
                                    viewFillFromTop = fillSpecific(i6, top);
                                    break;
                                } else if (this.mFirstPosition >= this.mItemCount) {
                                    viewFillFromTop = fillSpecific(0, top);
                                    break;
                                } else {
                                    int i7 = this.mFirstPosition;
                                    if (view2 != null) {
                                        top = view2.getTop();
                                    }
                                    viewFillFromTop = fillSpecific(i7, top);
                                    break;
                                }
                            } else if (!this.mStackFromBottom) {
                                setSelectedPositionInt(lookForSelectablePosition(0, true));
                                viewFillFromTop = fillFromTop(top);
                                break;
                            } else {
                                setSelectedPositionInt(lookForSelectablePosition(this.mItemCount - 1, false));
                                viewFillFromTop = fillUp(this.mItemCount - 1, i2);
                                break;
                            }
                            break;
                    }
                    recycleBin.scrapActiveViews();
                    removeUnusedFixedViews(this.mHeaderViewInfos);
                    removeUnusedFixedViews(this.mFooterViewInfos);
                    if (viewFillFromTop == null) {
                        if (!this.mItemsCanFocus || !hasFocus() || (viewFillFromTop.hasFocus() && !z3)) {
                            positionSelector(-1, viewFillFromTop);
                        } else if ((viewFillFromTop == focusedChild && viewFindFocus != null && viewFindFocus.requestFocus()) || viewFillFromTop.requestFocus()) {
                            viewFillFromTop.setSelected(false);
                            this.mSelectorRect.setEmpty();
                        } else {
                            View focusedChild2 = getFocusedChild();
                            if (focusedChild2 != null) {
                                focusedChild2.clearFocus();
                            }
                            positionSelector(-1, viewFillFromTop);
                        }
                        this.mSelectedTop = viewFillFromTop.getTop();
                    } else {
                        if (this.mTouchMode == 1 || this.mTouchMode == 2) {
                            View childAt3 = getChildAt(this.mMotionPosition - this.mFirstPosition);
                            if (childAt3 != null) {
                                positionSelector(this.mMotionPosition, childAt3);
                            }
                        } else if (this.mSelectorPosition != -1 && this.mSelectorPosition < this.mItemCount && !this.mIsHoveredByMouse) {
                            View childAt4 = getChildAt(this.mSelectorPosition - this.mFirstPosition);
                            if (childAt4 != null) {
                                positionSelector(this.mSelectorPosition, childAt4);
                            }
                        } else if (!this.mIsHoveredByMouse) {
                            this.mSelectedTop = 0;
                            this.mSelectorRect.setEmpty();
                        }
                        if (hasFocus() && viewFindFocus != null) {
                            viewFindFocus.requestFocus();
                        }
                    }
                    if (viewRootImpl != null) {
                        View accessibilityFocusedHost2 = viewRootImpl.getAccessibilityFocusedHost();
                        if (accessibilityFocusedHost2 == null) {
                            if (accessibilityFocusedHost != null && accessibilityFocusedHost.isAttachedToWindow()) {
                                AccessibilityNodeProvider accessibilityNodeProvider = accessibilityFocusedHost.getAccessibilityNodeProvider();
                                if (accessibilityFocusedVirtualView == null || accessibilityNodeProvider == null) {
                                    accessibilityFocusedHost.requestAccessibilityFocus();
                                } else {
                                    accessibilityNodeProvider.performAction(AccessibilityNodeInfo.getVirtualDescendantId(accessibilityFocusedVirtualView.getSourceNodeId()), 64, null);
                                }
                            } else if (positionForView != -1 && (childAt2 = getChildAt(MathUtils.constrain(positionForView - this.mFirstPosition, 0, getChildCount() - 1))) != null) {
                                childAt2.requestAccessibilityFocus();
                            }
                        } else if (positionForView != -1) {
                            int iConstrain = MathUtils.constrain(positionForView - this.mFirstPosition, 0, getChildCount() - 1);
                            View viewFindViewById = z2 ? getChildAt(iConstrain).findViewById(accessibilityFocusedHost2.getId()) : getChildAt(iConstrain);
                            if (accessibilityFocusedHost2.isAccessibilityFocused() && accessibilityFocusedHost2 != viewFindViewById) {
                                accessibilityFocusedHost2.clearAccessibilityFocus();
                                if (viewFindViewById != null) {
                                    viewFindViewById.requestAccessibilityFocus();
                                }
                            }
                        }
                    }
                    if ((this.mDataChanged || this.mSemAdapterChanged) && this.mSemEnableFillOut) {
                        lastVisiblePosition = getLastVisiblePosition();
                        if (lastVisiblePosition != this.mItemCount - 1 || lastVisiblePosition < 0) {
                            this.mSemFillOutEmptyArea = -1;
                        } else {
                            View childAt5 = getChildAt(getChildCount() - 1);
                            this.mSemFillOutEmptyArea = childAt5 == null ? -1 : childAt5.getBottom();
                        }
                    }
                    if (viewFindFocus != null && viewFindFocus.getWindowToken() != null) {
                        viewFindFocus.dispatchFinishTemporaryDetach();
                    }
                    this.mLayoutMode = 0;
                    this.mDataChanged = false;
                    this.mSemAdapterChanged = false;
                    if (this.mPositionScrollAfterLayout != null) {
                        post(this.mPositionScrollAfterLayout);
                        this.mPositionScrollAfterLayout = null;
                    }
                    this.mNeedSync = false;
                    setNextSelectedPositionInt(this.mSelectedPosition);
                    updateScrollIndicators();
                    if (this.mItemCount > 0) {
                        checkSelectionChanged();
                    }
                    invokeOnItemScrollListener();
                    focusSelector2 = this.mFocusSelector;
                    if (focusSelector2 != null) {
                        focusSelector2.onLayoutComplete();
                    }
                    if (z5) {
                        this.mBlockLayoutRequests = false;
                        return;
                    }
                    return;
                }
                z2 = false;
                positionForView = -1;
                accessibilityFocusedVirtualView = null;
                accessibilityFocusedHost = null;
                focusedChild = getFocusedChild();
                if (focusedChild == null) {
                }
                int i42 = this.mFirstPosition;
                AbsListView.RecycleBin recycleBin2 = this.mRecycler;
                if (z) {
                }
                detachAllViewsFromParent();
                recycleBin2.removeSkippedScrap();
                switch (this.mLayoutMode) {
                }
                recycleBin2.scrapActiveViews();
                removeUnusedFixedViews(this.mHeaderViewInfos);
                removeUnusedFixedViews(this.mFooterViewInfos);
                if (viewFillFromTop == null) {
                }
                if (viewRootImpl != null) {
                }
                if (this.mDataChanged) {
                    lastVisiblePosition = getLastVisiblePosition();
                    if (lastVisiblePosition != this.mItemCount - 1) {
                    }
                } else {
                    lastVisiblePosition = getLastVisiblePosition();
                    if (lastVisiblePosition != this.mItemCount - 1) {
                        this.mSemFillOutEmptyArea = -1;
                    }
                }
                if (viewFindFocus != null) {
                    viewFindFocus.dispatchFinishTemporaryDetach();
                }
                this.mLayoutMode = 0;
                this.mDataChanged = false;
                this.mSemAdapterChanged = false;
                if (this.mPositionScrollAfterLayout != null) {
                }
                this.mNeedSync = false;
                setNextSelectedPositionInt(this.mSelectedPosition);
                updateScrollIndicators();
                if (this.mItemCount > 0) {
                }
                invokeOnItemScrollListener();
                focusSelector2 = this.mFocusSelector;
                if (focusSelector2 != null) {
                }
                if (z5) {
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } else {
            if (i3 == 2) {
                int i8 = this.mNextSelectedPosition - this.mFirstPosition;
                if (i8 >= 0 && i8 < childCount) {
                    childAt = getChildAt(i8);
                    i = 0;
                    view = null;
                }
                view2 = null;
                z = this.mDataChanged;
                if (z) {
                }
                if (this.mItemCount != 0) {
                }
            } else if (i3 != 3 && i3 != 4 && i3 != 5) {
                int i9 = this.mSelectedPosition - this.mFirstPosition;
                View childAt6 = (i9 < 0 || i9 >= childCount) ? null : getChildAt(i9);
                View childAt7 = getChildAt(0);
                int i10 = this.mNextSelectedPosition >= 0 ? this.mNextSelectedPosition - this.mSelectedPosition : 0;
                View view7 = childAt6;
                childAt = getChildAt(i9 + i10);
                view = view7;
                int i11 = i10;
                view2 = childAt7;
                i = i11;
                z = this.mDataChanged;
                if (z) {
                }
                if (this.mItemCount != 0) {
                }
            }
            i = 0;
            view = null;
            childAt = null;
            view2 = null;
            z = this.mDataChanged;
            if (z) {
            }
            if (this.mItemCount != 0) {
            }
        }
        FocusSelector focusSelector5 = this.mFocusSelector;
        if (focusSelector5 != null) {
            focusSelector5.onLayoutComplete();
        }
        if (!z5) {
            this.mBlockLayoutRequests = false;
        }
        throw th;
    }

    @Override // android.widget.AbsListView
    boolean trackMotionScroll(int i, int i2) throws Resources.NotFoundException {
        boolean zTrackMotionScroll = super.trackMotionScroll(i, i2);
        removeUnusedFixedViews(this.mHeaderViewInfos);
        removeUnusedFixedViews(this.mFooterViewInfos);
        return zTrackMotionScroll;
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

    private View makeAndAddView(int i, int i2, boolean z, int i3, boolean z2) throws Resources.NotFoundException {
        View activeView;
        if (!this.mDataChanged && (activeView = this.mRecycler.getActiveView(i)) != null) {
            setupChild(activeView, i, i2, z, i3, z2, true);
            return activeView;
        }
        View viewObtainView = obtainView(i, this.mIsScrap);
        if (viewObtainView != null) {
            setupChild(viewObtainView, i, i2, z, i3, z2, this.mIsScrap[0]);
            if (this.mMonotoneBaseColor != -1 && (viewObtainView instanceof ViewGroup)) {
                semTraverseViewTree(viewObtainView);
            }
        }
        return viewObtainView;
    }

    private void semSetMonotoneColor(int i) {
        this.mMonotoneBaseColor = i;
        Log.i(TAG, "semSetMonotoneColor / " + i);
        if (i != -1) {
            semTraverseViewTree(this);
        }
    }

    private void semTraverseViewTree(View view) {
        int defaultColor;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            Drawable background = viewGroup.getBackground();
            if (background instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) background;
                int defaultColor2 = gradientDrawable.getColor() != null ? gradientDrawable.getColor().getDefaultColor() : -1;
                if (defaultColor2 != -1 && defaultColor2 != 0) {
                    if (viewGroup.getId() == 16908288) {
                        viewGroup.setBackground(null);
                    } else {
                        GradientDrawable gradientDrawable2 = (GradientDrawable) background.mutate();
                        gradientDrawable2.setColor(ColorStateList.valueOf(semGetMixedColorWithAlpha(this.mMonotoneBaseColor, semGetAlphaFromColor(defaultColor2))));
                        viewGroup.setBackground(gradientDrawable2);
                    }
                }
            } else if (background instanceof ColorDrawable) {
                int color = ((ColorDrawable) background).getColor();
                if (color != -1 && color != 0) {
                    if (viewGroup.getId() == 16908288) {
                        viewGroup.setBackground(null);
                    } else {
                        viewGroup.setBackgroundColor(semGetMixedColorWithAlpha(this.mMonotoneBaseColor, semGetAlphaFromColor(color)));
                    }
                }
            } else {
                Log.e(TAG, "This drawable is not supported at monotone widget.");
            }
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                semTraverseViewTree(viewGroup.getChildAt(i));
            }
            return;
        }
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(semGetMixedColorWithAlpha(this.mMonotoneBaseColor, 255));
            return;
        }
        if (view instanceof ImageView) {
            if (view instanceof ImageButton) {
                Drawable background2 = view.getBackground();
                if (background2 instanceof GradientDrawable) {
                    GradientDrawable gradientDrawable3 = (GradientDrawable) background2;
                    if (gradientDrawable3.getColor() != null && (defaultColor = gradientDrawable3.getColor().getDefaultColor()) != -1) {
                        background2.setTint(semGetMixedColorWithAlpha(this.mMonotoneBaseColor, semGetAlphaFromColor(defaultColor)));
                        background2.setTintBlendMode(BlendMode.SRC);
                    }
                } else if (background2 instanceof ColorDrawable) {
                    setBackgroundColor(semGetMixedColorWithAlpha(this.mMonotoneBaseColor, semGetAlphaFromColor(((ColorDrawable) background2).getColor())));
                }
            }
            ImageView imageView = (ImageView) view;
            imageView.setImageTintList(null);
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof VectorDrawable) {
                ((VectorDrawable) drawable).setPathColor("all", this.mMonotoneBaseColor);
            } else if (drawable instanceof AnimatedVectorDrawable) {
                ((AnimatedVectorDrawable) drawable).hidden_semSetPathColor(this.mMonotoneBaseColor);
            } else {
                ColorFilter colorFilter = imageView.getColorFilter();
                if (colorFilter instanceof PorterDuffColorFilter) {
                    imageView.setColorFilter(semGetMixedColorWithAlpha(this.mMonotoneBaseColor, semGetAlphaFromColor(((PorterDuffColorFilter) colorFilter).getColor())));
                }
            }
            imageView.invalidate();
            return;
        }
        if (view instanceof ProgressBar) {
            ProgressBar progressBar = (ProgressBar) view;
            progressBar.setProgressTintList(ColorStateList.valueOf(semGetMixedColorWithAlpha(this.mMonotoneBaseColor, progressBar.getProgressTintList() != null ? semGetAlphaFromColor(progressBar.getProgressTintList().getDefaultColor()) : 255)));
            progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(semGetMixedColorWithAlpha(this.mMonotoneBaseColor, progressBar.getProgressBackgroundTintList() != null ? semGetAlphaFromColor(progressBar.getProgressBackgroundTintList().getDefaultColor()) : 255)));
            progressBar.invalidate();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setupChild(View view, int i, int i2, boolean z, int i3, boolean z2, boolean z3) throws Resources.NotFoundException {
        int iMakeSafeMeasureSpec;
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
                iMakeSafeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
            } else {
                iMakeSafeMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(getMeasuredHeight(), 0);
            }
            view.measure(childMeasureSpec, iMakeSafeMeasureSpec);
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x0011  */
    @Override // android.widget.AbsListView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void setSelectionInt(int i) throws Throwable {
        boolean z;
        setNextSelectedPositionInt(i);
        int i2 = this.mSelectedPosition;
        if (i2 >= 0) {
            z = true;
            if (i != i2 - 1 && i != i2 + 1) {
                z = false;
            }
        }
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        layoutChildren();
        if (z) {
            awakenScrollBars();
        }
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
        int iLookForSelectablePosition = lookForSelectablePosition(i2, z);
        if (iLookForSelectablePosition != -1) {
            return iLookForSelectablePosition;
        }
        int count = listAdapter.getCount() - 1;
        int iConstrain = MathUtils.constrain(i, -1, count);
        if (z) {
            int iMin = Math.min(i2 - 1, count);
            while (iMin > iConstrain && !listAdapter.isEnabled(iMin)) {
                iMin--;
            }
            if (iMin <= iConstrain) {
                return -1;
            }
            return iMin;
        }
        int iMax = Math.max(0, i2 + 1);
        while (iMax < iConstrain && !listAdapter.isEnabled(iMax)) {
            iMax++;
        }
        if (iMax >= iConstrain) {
            return -1;
        }
        return iMax;
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
        boolean zDispatchKeyEvent = super.dispatchKeyEvent(keyEvent);
        return (zDispatchKeyEvent || getFocusedChild() == null || keyEvent.getAction() != 0) ? zDispatchKeyEvent : onKeyDown(keyEvent.getKeyCode(), keyEvent);
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
    /* JADX WARN: Removed duplicated region for block: B:169:0x023a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x029f A[PHI: r7
      0x029f: PHI (r7v3 boolean) = 
      (r7v2 boolean)
      (r7v2 boolean)
      (r7v8 boolean)
      (r7v20 boolean)
      (r7v2 boolean)
      (r7v25 boolean)
      (r7v2 boolean)
      (r7v2 boolean)
      (r7v2 boolean)
      (r7v2 boolean)
      (r7v35 boolean)
      (r7v2 boolean)
      (r7v43 boolean)
      (r7v2 boolean)
      (r7v53 boolean)
      (r7v2 boolean)
      (r7v56 boolean)
      (r7v57 boolean)
      (r7v2 boolean)
     binds: [B:34:0x0066, B:35:0x0068, B:202:0x029b, B:72:0x00dd, B:140:0x01a5, B:73:0x00e0, B:126:0x017d, B:112:0x0155, B:105:0x0142, B:46:0x0082, B:96:0x0129, B:89:0x0112, B:75:0x00eb, B:66:0x00ce, B:59:0x00b1, B:58:0x00af, B:53:0x009d, B:54:0x009f, B:51:0x0091] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00e0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean commonKey(int i, int i2, KeyEvent keyEvent) throws Throwable {
        boolean zResurrectSelectionIfNeeded;
        int i3;
        boolean z;
        int height;
        boolean z2;
        int i4;
        if (this.mAdapter == null || !isAttachedToWindow()) {
            return false;
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        if (this.mAppWidgetInnerFocus && !KeyEvent.isConfirmKey(i) && keyEvent.getAction() != 1) {
            this.mNextClickable = null;
        }
        int action = keyEvent.getAction();
        if (KeyEvent.isConfirmKey(i) && keyEvent.hasNoModifiers() && action != 1) {
            zResurrectSelectionIfNeeded = resurrectSelectionIfNeeded();
            if (!zResurrectSelectionIfNeeded && keyEvent.getRepeatCount() == 0 && getChildCount() > 0) {
                keyPressed();
                zResurrectSelectionIfNeeded = true;
            }
        } else {
            zResurrectSelectionIfNeeded = false;
        }
        if (this.mIsHoveredByMouse) {
            this.mIsHoveredByMouse = false;
            Log.d(TAG, "mIsHoveredByMouse false");
        }
        if (zResurrectSelectionIfNeeded || action == 1) {
            i3 = i2;
        } else if (i == 61) {
            if (this.mAppWidgetSnapScroll) {
                View deepestFocusedChild = getDeepestFocusedChild();
                if (getPositionForView(deepestFocusedChild) != this.mFirstPosition) {
                    clearFocus();
                    deepestFocusedChild = this;
                }
                View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, deepestFocusedChild, 2);
                int height2 = getHeight() - this.mListPadding.bottom;
                if (viewFindNextFocus != null) {
                    int[] iArr = new int[2];
                    int[] iArr2 = new int[2];
                    getLocationOnScreen(iArr);
                    viewFindNextFocus.getLocationOnScreen(iArr2);
                    int i5 = iArr[1] + height2;
                    int height3 = iArr2[1] + viewFindNextFocus.getHeight();
                    View childAt = getChildAt(0);
                    if (i5 < height3) {
                        if (iArr2[1] > i5) {
                            setSelectionFromTop(this.mSelectedPosition, childAt.getTop() - iArr2[1]);
                        }
                        z = true;
                        if (!z) {
                            return false;
                        }
                    } else {
                        if (iArr2[1] < 0 && childAt != null) {
                            int iAbs = Math.abs(childAt.getTop() - iArr2[1]);
                            if (iAbs < height2 / 3) {
                                height = 0;
                                z2 = true;
                            } else {
                                height = iAbs - ((getHeight() - this.mListPadding.bottom) / 3);
                                z2 = false;
                            }
                            setSelectionFromTop(this.mSelectedPosition, -height);
                            i3 = i2;
                            zResurrectSelectionIfNeeded = z2;
                        }
                        z = false;
                        if (!z) {
                        }
                    }
                } else {
                    z = false;
                    if (!z) {
                    }
                }
            } else {
                z = false;
            }
            if (this.mAppWidgetInnerFocus) {
                addClickables(getChildAt(this.mSelectorPosition - this.mFirstPosition));
                if (consumeClickables()) {
                    viewSelectorLikeFocus(this.mNextClickable);
                    return true;
                }
            }
            if (this.mSelectZeroPositionOnKeyTab && getSelectedItemPosition() == getCount() - 1) {
                if (this.mAppWidgetInnerFocus) {
                    this.mSelectorPosition = 0;
                }
                setSelection(0);
                return true;
            }
            if (keyEvent.hasNoModifiers()) {
                zResurrectSelectionIfNeeded = resurrectSelectionIfNeeded() || arrowScroll(130);
            } else if (keyEvent.hasModifiers(1)) {
                if (resurrectSelectionIfNeeded() || arrowScroll(33)) {
                }
            }
            if (z) {
            }
            i3 = i2;
        } else if (i == 92) {
            if (keyEvent.hasNoModifiers()) {
                if (resurrectSelectionIfNeeded() || pageScroll(33)) {
                }
            } else if (keyEvent.hasModifiers(2)) {
                if (resurrectSelectionIfNeeded() || fullScroll(33)) {
                }
            }
            i3 = i2;
        } else if (i == 93) {
            if (keyEvent.hasNoModifiers()) {
                if (resurrectSelectionIfNeeded() || pageScroll(130)) {
                }
            } else if (keyEvent.hasModifiers(2)) {
                if (resurrectSelectionIfNeeded() || fullScroll(130)) {
                }
            }
            i3 = i2;
        } else if (i == 122) {
            if (keyEvent.hasNoModifiers()) {
                if (resurrectSelectionIfNeeded() || fullScroll(33)) {
                }
            }
            i3 = i2;
        } else if (i != 123) {
            switch (i) {
                case 19:
                    if (keyEvent.hasNoModifiers() || keyEvent.hasModifiers(1)) {
                        this.mSemCurrentFocusPosition = this.mSelectedPosition;
                        zResurrectSelectionIfNeeded = resurrectSelectionIfNeeded();
                        if (!zResurrectSelectionIfNeeded) {
                            z2 = zResurrectSelectionIfNeeded;
                            int i6 = i2;
                            while (true) {
                                i3 = i6 - 1;
                                if (i6 > 0 && arrowScroll(33)) {
                                    z2 = true;
                                    i6 = i3;
                                }
                            }
                            zResurrectSelectionIfNeeded = z2;
                            break;
                        }
                    } else if (keyEvent.hasModifiers(2)) {
                        zResurrectSelectionIfNeeded = resurrectSelectionIfNeeded() || fullScroll(33);
                    }
                    i3 = i2;
                    break;
                case 20:
                    if (keyEvent.hasNoModifiers() || keyEvent.hasModifiers(1)) {
                        this.mSemCurrentFocusPosition = this.mSelectedPosition;
                        zResurrectSelectionIfNeeded = resurrectSelectionIfNeeded();
                        if (!zResurrectSelectionIfNeeded) {
                            boolean z3 = zResurrectSelectionIfNeeded;
                            int i7 = i2;
                            while (true) {
                                i4 = i7 - 1;
                                if (i7 > 0 && arrowScroll(130)) {
                                    z3 = true;
                                    i7 = i4;
                                }
                            }
                            zResurrectSelectionIfNeeded = z3;
                            i3 = i4;
                            break;
                        }
                    } else if (keyEvent.hasModifiers(2)) {
                        if (resurrectSelectionIfNeeded() || fullScroll(130)) {
                        }
                    }
                    i3 = i2;
                    break;
                case 21:
                    if (keyEvent.hasNoModifiers() || keyEvent.hasModifiers(1)) {
                        this.mSemCurrentFocusPosition = this.mSelectedPosition;
                        zResurrectSelectionIfNeeded = handleHorizontalFocusWithinListItem(17);
                    }
                    i3 = i2;
                    break;
                case 22:
                    if (keyEvent.hasNoModifiers() || keyEvent.hasModifiers(1)) {
                        this.mSemCurrentFocusPosition = this.mSelectedPosition;
                        zResurrectSelectionIfNeeded = handleHorizontalFocusWithinListItem(66);
                        if (!zResurrectSelectionIfNeeded) {
                            zResurrectSelectionIfNeeded = resurrectSelectionIfNeeded();
                        }
                    }
                    i3 = i2;
                    break;
            }
        } else {
            if (keyEvent.hasNoModifiers()) {
                if (resurrectSelectionIfNeeded() || fullScroll(130)) {
                }
            }
            i3 = i2;
        }
        if (zResurrectSelectionIfNeeded) {
            if (this.mAppWidgetInnerFocus && !KeyEvent.isConfirmKey(i)) {
                this.mNextClickable = null;
                this.mClickableViewStates.clear();
            }
            return true;
        }
        if (sendToTextFilter(i, i3, keyEvent)) {
            return true;
        }
        if (action == 0) {
            return super.onKeyDown(i, keyEvent);
        }
        if (action == 1) {
            return super.onKeyUp(i, keyEvent);
        }
        if (action != 2) {
            return false;
        }
        return super.onKeyMultiple(i, i3, keyEvent);
    }

    boolean pageScroll(int i) throws Throwable {
        int iMin;
        boolean z;
        int iLookForSelectablePositionAfter;
        if (i != 33) {
            if (i == 130) {
                iMin = Math.min(this.mItemCount - 1, (this.mSelectedPosition + getChildCount()) - 1);
                z = true;
            }
            return false;
        }
        iMin = Math.max(0, (this.mSelectedPosition - getChildCount()) - 1);
        z = false;
        if (iMin >= 0 && (iLookForSelectablePositionAfter = lookForSelectablePositionAfter(this.mSelectedPosition, iMin, z)) >= 0) {
            this.mLayoutMode = 4;
            this.mSpecificTop = this.mPaddingTop + getVerticalFadingEdgeLength();
            if (z && iLookForSelectablePositionAfter > this.mItemCount - getChildCount()) {
                this.mLayoutMode = 3;
            }
            if (!z && iLookForSelectablePositionAfter < getChildCount()) {
                this.mLayoutMode = 1;
            }
            setSelectionInt(iLookForSelectablePositionAfter);
            semShowGoToTOP();
            invokeOnItemScrollListener();
            if (!awakenScrollBars()) {
                invalidate();
            }
            return true;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean fullScroll(int i) throws Throwable {
        int i2;
        boolean z = true;
        if (i == 33) {
            if (this.mSelectedPosition != 0) {
                int iLookForSelectablePositionAfter = lookForSelectablePositionAfter(this.mSelectedPosition, 0, true);
                if (iLookForSelectablePositionAfter >= 0) {
                    this.mLayoutMode = 1;
                    setSelectionInt(iLookForSelectablePositionAfter);
                    invokeOnItemScrollListener();
                }
            } else {
                z = false;
            }
        } else if (i == 130 && this.mSelectedPosition < (i2 = this.mItemCount - 1)) {
            int iLookForSelectablePositionAfter2 = lookForSelectablePositionAfter(this.mSelectedPosition, i2, false);
            if (iLookForSelectablePositionAfter2 >= 0) {
                this.mLayoutMode = 3;
                setSelectionInt(iLookForSelectablePositionAfter2);
                invokeOnItemScrollListener();
            }
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
        View viewFindFocus = selectedView.findFocus();
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup) selectedView, viewFindFocus, i);
        if (viewFindNextFocus != null) {
            if (viewFindFocus != null) {
                viewFindFocus.getFocusedRect(this.mTempRect);
                offsetDescendantRectToMyCoords(viewFindFocus, this.mTempRect);
                offsetRectIntoDescendantCoords(viewFindNextFocus, this.mTempRect);
            }
            if (viewFindNextFocus.requestFocus(i, this.mTempRect)) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
                return true;
            }
        }
        View viewFindNextFocus2 = FocusFinder.getInstance().findNextFocus((ViewGroup) getRootView(), viewFindFocus, i);
        if (viewFindNextFocus2 != null) {
            return isViewAncestorOf(viewFindNextFocus2, this);
        }
        return false;
    }

    boolean arrowScroll(int i) {
        try {
            this.mInLayout = true;
            boolean zArrowScrollImpl = arrowScrollImpl(i);
            if (zArrowScrollImpl) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
            }
            return zArrowScrollImpl;
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

    private boolean arrowScrollImpl(int i) throws Resources.NotFoundException {
        boolean z;
        View viewFindFocus;
        View focusedChild;
        if (getChildCount() <= 0) {
            return false;
        }
        View selectedView = getSelectedView();
        int i2 = this.mSelectedPosition;
        int iNextSelectedPositionForDirection = nextSelectedPositionForDirection(selectedView, i2, i);
        int iAmountToScroll = amountToScroll(i, iNextSelectedPositionForDirection);
        View view = null;
        ArrowScrollFocusResult arrowScrollFocusResultArrowScrollFocused = this.mItemsCanFocus ? arrowScrollFocused(i) : null;
        if (arrowScrollFocusResultArrowScrollFocused != null) {
            iNextSelectedPositionForDirection = arrowScrollFocusResultArrowScrollFocused.getSelectedPosition();
            iAmountToScroll = arrowScrollFocusResultArrowScrollFocused.getAmountToScroll();
        }
        if (!this.mAppWidgetSnapScroll) {
            z = false;
        } else {
            if (selectedView == null) {
                return false;
            }
            int height = getHeight() - this.mListPadding.bottom;
            if (i == 33 && i2 > 0 && iAmountToScroll > 0) {
                if (selectedView.getHeight() > height - this.mListPadding.top && selectedView.getTop() != 0) {
                    if (selectedView.getTop() + iAmountToScroll > 0) {
                        iAmountToScroll = -selectedView.getTop();
                    }
                    z = false;
                } else {
                    iNextSelectedPositionForDirection = i2 - 1;
                    smoothScrollToPosition(iNextSelectedPositionForDirection);
                    z = true;
                }
            } else {
                if (i == 130 && i2 < this.mAdapter.getCount() - 1 && iAmountToScroll > 0 && selectedView.getHeight() == height - this.mListPadding.top) {
                    iNextSelectedPositionForDirection = i2 + 1;
                    smoothScrollToPositionFromTop(iNextSelectedPositionForDirection, 0);
                    z = true;
                }
                z = false;
            }
        }
        if (z && !isFocused()) {
            requestFocus();
        }
        boolean z2 = arrowScrollFocusResultArrowScrollFocused != null;
        if (iNextSelectedPositionForDirection != -1) {
            handleNewSelectionChange(selectedView, i, iNextSelectedPositionForDirection, arrowScrollFocusResultArrowScrollFocused != null);
            setSelectedPositionInt(iNextSelectedPositionForDirection);
            setNextSelectedPositionInt(iNextSelectedPositionForDirection);
            selectedView = getSelectedView();
            if (this.mItemsCanFocus && arrowScrollFocusResultArrowScrollFocused == null && (focusedChild = getFocusedChild()) != null) {
                focusedChild.clearFocus();
            }
            checkSelectionChanged();
            i2 = iNextSelectedPositionForDirection;
            z2 = true;
        }
        if (iAmountToScroll > 0) {
            semShowGoToTOP();
            if (!z) {
                if (i != 33) {
                    iAmountToScroll = -iAmountToScroll;
                }
                scrollListItemsBy(iAmountToScroll);
            }
            z2 = true;
        }
        if (this.mItemsCanFocus && arrowScrollFocusResultArrowScrollFocused == null && selectedView != null && selectedView.hasFocus() && (viewFindFocus = selectedView.findFocus()) != null && (!isViewAncestorOf(viewFindFocus, this) || distanceToView(viewFindFocus) > 0)) {
            viewFindFocus.clearFocus();
        }
        if (iNextSelectedPositionForDirection != -1 || selectedView == null || isViewAncestorOf(selectedView, this)) {
            view = selectedView;
        } else {
            this.mSelectorRect.setEmpty();
            hideSelector();
            this.mResurrectToPosition = -1;
        }
        if (!z2) {
            return false;
        }
        if (view != null) {
            positionSelectorLikeFocus(i2, view);
            this.mSelectedTop = view.getTop();
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        invokeOnItemScrollListener();
        return true;
    }

    private void handleNewSelectionChange(View view, int i, int i2, boolean z) throws Resources.NotFoundException {
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

    private void measureAndAdjustDown(View view, int i, int i2) throws Resources.NotFoundException {
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
        int iMakeSafeMeasureSpec;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -2);
        }
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mListPadding.left + this.mListPadding.right, layoutParams.width);
        int i = layoutParams.height;
        if (i > 0) {
            iMakeSafeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
        } else {
            iMakeSafeMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(getMeasuredHeight(), 0);
        }
        view.measure(childMeasureSpec, iMakeSafeMeasureSpec);
    }

    private void relayoutMeasuredItem(View view) throws Resources.NotFoundException {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i = this.mListPadding.left;
        int top = view.getTop();
        view.layout(i, top, measuredWidth + i, measuredHeight + top);
    }

    private int getArrowScrollPreviewLength() {
        return Math.max(2, getVerticalFadingEdgeLength());
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009d  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x008b -> B:27:0x0085). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int amountToScroll(int i, int i2) throws Resources.NotFoundException {
        int i3;
        int height = getHeight() - this.mListPadding.bottom;
        int i4 = this.mListPadding.top;
        int childCount = getChildCount();
        if (i == 130) {
            int i5 = childCount - 1;
            if (i2 != -1) {
                i5 = i2 - this.mFirstPosition;
            }
            while (childCount <= i5) {
                addViewBelow(getChildAt(childCount - 1), (this.mFirstPosition + childCount) - 1);
                childCount++;
            }
            int i6 = this.mFirstPosition + i5;
            View childAt = getChildAt(i5);
            int arrowScrollPreviewLength = i6 < this.mItemCount + (-1) ? height - getArrowScrollPreviewLength() : height;
            if (childAt.getBottom() <= arrowScrollPreviewLength) {
                return 0;
            }
            if (i2 != -1 && arrowScrollPreviewLength - childAt.getTop() >= getMaxScrollAmount()) {
                return 0;
            }
            int bottom = childAt.getBottom() - arrowScrollPreviewLength;
            if (this.mFirstPosition + childCount == this.mItemCount) {
                bottom = Math.min(bottom, getChildAt(childCount - 1).getBottom() - height);
            }
            return Math.min(bottom, getMaxScrollAmount());
        }
        if (i2 != -1) {
            int i7 = this.mFirstPosition;
            i3 = i2 - i7;
            if (i3 < 0) {
                addViewAbove(getChildAt(0), this.mFirstPosition);
                this.mFirstPosition--;
                i7 = this.mFirstPosition;
                i3 = i2 - i7;
                if (i3 < 0) {
                    int i8 = this.mFirstPosition + i3;
                    View childAt2 = getChildAt(i3);
                    int arrowScrollPreviewLength2 = i8 > 0 ? getArrowScrollPreviewLength() + i4 : i4;
                    if (childAt2.getTop() >= arrowScrollPreviewLength2) {
                        return 0;
                    }
                    if (i2 != -1 && childAt2.getBottom() - arrowScrollPreviewLength2 >= getMaxScrollAmount()) {
                        return 0;
                    }
                    int top = arrowScrollPreviewLength2 - childAt2.getTop();
                    if (this.mFirstPosition == 0) {
                        top = Math.min(top, i4 - getChildAt(0).getTop());
                    }
                    return Math.min(top, getMaxScrollAmount());
                }
            }
        } else {
            i3 = 0;
            if (i3 < 0) {
            }
        }
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

    private ArrowScrollFocusResult arrowScrollFocused(int i) throws Resources.NotFoundException {
        View viewFindNextFocusFromRect;
        int iLookForSelectablePositionOnScreen;
        View selectedView = getSelectedView();
        if (selectedView != null && selectedView.hasFocus()) {
            viewFindNextFocusFromRect = FocusFinder.getInstance().findNextFocus(this, selectedView.findFocus(), i);
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
            viewFindNextFocusFromRect = FocusFinder.getInstance().findNextFocusFromRect(this, this.mTempRect, i);
        }
        if (viewFindNextFocusFromRect != null) {
            int iPositionOfNewFocus = positionOfNewFocus(viewFindNextFocusFromRect);
            if (this.mSelectedPosition != -1 && iPositionOfNewFocus != this.mSelectedPosition && (iLookForSelectablePositionOnScreen = lookForSelectablePositionOnScreen(i)) != -1 && ((i == 130 && iLookForSelectablePositionOnScreen < iPositionOfNewFocus) || (i == 33 && iLookForSelectablePositionOnScreen > iPositionOfNewFocus))) {
                return null;
            }
            int iAmountToScrollToNewFocus = amountToScrollToNewFocus(i, viewFindNextFocusFromRect, iPositionOfNewFocus);
            int maxScrollAmount = getMaxScrollAmount();
            if (iAmountToScrollToNewFocus < maxScrollAmount) {
                viewFindNextFocusFromRect.requestFocus(i);
                this.mArrowScrollFocusResult.populate(iPositionOfNewFocus, iAmountToScrollToNewFocus);
                return this.mArrowScrollFocusResult;
            }
            if (distanceToView(viewFindNextFocusFromRect) < maxScrollAmount) {
                viewFindNextFocusFromRect.requestFocus(i);
                this.mArrowScrollFocusResult.populate(iPositionOfNewFocus, maxScrollAmount);
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

    private void scrollListItemsBy(int i) throws Resources.NotFoundException {
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

    private View addViewAbove(View view, int i) throws Resources.NotFoundException {
        int i2 = i - 1;
        View viewObtainView = obtainView(i2, this.mIsScrap);
        setupChild(viewObtainView, i2, view.getTop() - this.mDividerHeight, false, this.mListPadding.left, false, this.mIsScrap[0]);
        return viewObtainView;
    }

    private View addViewBelow(View view, int i) throws Resources.NotFoundException {
        int i2 = i + 1;
        View viewObtainView = obtainView(i2, this.mIsScrap);
        setupChild(viewObtainView, i2, view.getBottom() + this.mDividerHeight, true, this.mListPadding.left, false, this.mIsScrap[0]);
        return viewObtainView;
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

    /* JADX WARN: Removed duplicated region for block: B:109:0x018c  */
    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
                int bottom = 0;
                int i11 = 0;
                while (i11 < childCount) {
                    int i12 = i8 + i11;
                    boolean z9 = i12 < headerViewsCount;
                    boolean z10 = i12 >= size;
                    if ((z5 || !z9) && (z6 || !z10)) {
                        bottom = getChildAt(i11).getBottom();
                        i4 = i5;
                        boolean z11 = i11 == childCount + (-1);
                        if (z8 && bottom < i9 && (!z || !z11)) {
                            boolean z12 = z11;
                            int i13 = i12 + 1;
                            if (listAdapter.isEnabled(i12) && ((z5 || (!z9 && i13 >= headerViewsCount)) && (z12 || (listAdapter.isEnabled(i13) && (z6 || (!z10 && i13 < size)))))) {
                                rect.top = bottom;
                                rect.bottom = bottom + i4;
                                drawDivider(canvas, rect, i11);
                            } else if (z7) {
                                rect.top = bottom;
                                rect.bottom = bottom + i4;
                                canvas.drawRect(rect, paint2);
                            }
                        }
                    } else {
                        i4 = i5;
                    }
                    i11++;
                    i5 = i4;
                }
                int i14 = this.mBottom + this.mScrollY;
                if (z && i8 + childCount == i7 && i14 > bottom) {
                    rect.top = bottom;
                    rect.bottom = i14;
                    drawOverscrollFooter(canvas, drawable2, rect);
                }
            } else {
                int i15 = this.mScrollY;
                if (childCount <= 0 || i6 == 0) {
                    z2 = false;
                } else {
                    rect.top = i15;
                    z2 = false;
                    rect.bottom = getChildAt(0).getTop();
                    drawOverscrollHeader(canvas, drawable, rect);
                }
                int i16 = i6;
                while (i16 < childCount) {
                    int i17 = i8 + i16;
                    boolean z13 = i17 < headerViewsCount ? true : z2;
                    boolean z14 = i17 >= size ? true : z2;
                    if ((z5 || !z13) && (z6 || !z14)) {
                        int top = getChildAt(i16).getTop();
                        if (!z8 || top <= i) {
                            i3 = i15;
                        } else {
                            boolean z15 = i16 == i6;
                            i3 = i15;
                            int i18 = i17 - 1;
                            if (listAdapter.isEnabled(i17) && ((z5 || (!z13 && i18 >= headerViewsCount)) && (z15 || (listAdapter.isEnabled(i18) && (z6 || (!z14 && i18 < size)))))) {
                                rect.top = top - i5;
                                rect.bottom = top;
                                drawDivider(canvas, rect, i16 - 1);
                            } else if (z7) {
                                rect.top = top - i5;
                                rect.bottom = top;
                                canvas.drawRect(rect, paint2);
                            }
                        }
                    }
                    i16++;
                    i15 = i3;
                    z2 = false;
                }
                int i19 = i15;
                if (childCount > 0 && i19 > 0) {
                    if (z) {
                        int i20 = this.mBottom;
                        rect.top = i20;
                        rect.bottom = i20 + i19;
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
        boolean zDrawChild = super.drawChild(canvas, view, j);
        if (this.mCachingActive && view.mCachingFailed) {
            this.mCachingActive = false;
        }
        SemDragAndDropListAnimator semDragAndDropListAnimator2 = this.mDndListAnimator;
        if (semDragAndDropListAnimator2 != null) {
            semDragAndDropListAnimator2.postDrawChild(canvas, view, j);
        }
        return zDrawChild;
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
    protected void onFocusChanged(boolean z, int i, Rect rect) throws Throwable {
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
            int top = 0;
            while (i3 < childCount) {
                if (listAdapter.isEnabled(i4 + i3)) {
                    View childAt = getChildAt(i3);
                    childAt.getDrawingRect(rect2);
                    offsetDescendantRectToMyCoords(childAt, rect2);
                    int distance = getDistance(rect, rect2, i);
                    if (distance < i5) {
                        top = childAt.getTop();
                        i2 = i3;
                        i5 = distance;
                    }
                }
                i3++;
            }
            i3 = top;
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
        View viewFindViewById;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = arrayList.get(i2).view;
            if (!view.isRootNamespace() && (viewFindViewById = view.findViewById(i)) != null) {
                return viewFindViewById;
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
        View viewFindViewWithTag;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            View view = arrayList.get(i).view;
            if (!view.isRootNamespace() && (viewFindViewWithTag = view.findViewWithTag(obj)) != null) {
                return viewFindViewWithTag;
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
        View viewFindViewByPredicate;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            View view2 = arrayList.get(i).view;
            if (view2 != view && !view2.isRootNamespace() && (viewFindViewByPredicate = view2.findViewByPredicate(predicate)) != null) {
                return viewFindViewByPredicate;
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
        int iMin = Math.min(i2, getCount() - 1);
        if (i2 < 0) {
            return false;
        }
        smoothScrollToPosition(iMin);
        return true;
    }

    @Override // android.widget.AbsListView
    public void onInitializeAccessibilityNodeInfoForItem(View view, int i, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoForItem(view, i, accessibilityNodeInfo);
        AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) view.getLayoutParams();
        accessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(i, 1, 0, 1, layoutParams != null && layoutParams.viewType == -2, isItemChecked(i)));
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws Resources.NotFoundException, IOException {
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
    public void semSetGoToTopEnabledForAppWidget(boolean z) throws Resources.NotFoundException {
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

    /* JADX WARN: Removed duplicated region for block: B:31:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean pointerScroll(int i) throws Resources.NotFoundException {
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
        int iNextFocusedPositionForDirection = nextFocusedPositionForDirection(childAt, i2, i);
        int iAmountToScroll = amountToScroll(i, iNextFocusedPositionForDirection);
        if (childAt == null) {
            return true;
        }
        int height = getHeight() - this.mListPadding.bottom;
        if (iAmountToScroll > 0) {
            if (i == 33 && i2 > 0) {
                iNextFocusedPositionForDirection = i2 - 1;
            } else if (i != 130 || i2 >= this.mAdapter.getCount() - 1) {
                z = false;
                if (i == 33 || childAt.getHeight() <= height - this.mListPadding.top || childAt.getTop() == 0) {
                    z2 = z;
                } else if (childAt.getTop() + iAmountToScroll >= 0) {
                    iAmountToScroll = -childAt.getTop();
                }
            } else {
                iNextFocusedPositionForDirection = i2 + 1;
            }
            z = true;
            if (i == 33) {
                z2 = z;
            }
        }
        if (z2) {
            setSelection(iNextFocusedPositionForDirection);
            return true;
        }
        if (iAmountToScroll > 0) {
            semShowGoToTOP();
            if (i != 33) {
                iAmountToScroll = -iAmountToScroll;
            }
            scrollListItemsBy(iAmountToScroll);
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

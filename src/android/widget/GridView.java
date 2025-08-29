package android.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.RemotableViewMethod;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View$InspectionCompanion$$ExternalSyntheticLambda0;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewRootImpl;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.GridLayoutAnimationController;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.AbsListView;
import android.widget.RemoteViews;
import com.android.internal.R;
import com.samsung.android.animation.SemAbsDragAndDropAnimator;
import com.samsung.android.animation.SemAddDeleteGridAnimator;
import com.samsung.android.animation.SemDragAndDropGridAnimator;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class GridView extends AbsListView {
    public static final int AUTO_FIT = -1;
    public static final int NO_STRETCH = 0;
    public static final int STRETCH_COLUMN_WIDTH = 2;
    public static final int STRETCH_SPACING = 1;
    public static final int STRETCH_SPACING_UNIFORM = 3;
    private static final String TAG = "GridView";
    private SemAddDeleteGridAnimator mAddDeleteGridAnimator;
    private int mColumnWidth;
    private SemDragAndDropGridAnimator mDndGridAnimator;
    private int mGravity;
    private int mHorizontalSpacing;
    private int mNumColumns;
    private View mReferenceView;
    private View mReferenceViewInSelectedRow;
    private int mRequestedColumnWidth;
    private int mRequestedHorizontalSpacing;
    private int mRequestedNumColumns;
    private boolean mSelectZeroPositionOnKeyTab;
    private int mStretchMode;
    private final Rect mTempRect;
    private int mVerticalSpacing;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StretchMode {
    }

    private int getTopSelectionPixel(int i, int i2, int i3) {
        return i3 > 0 ? i + i2 : i;
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<GridView> {
        private int mColumnWidthId;
        private int mGravityId;
        private int mHorizontalSpacingId;
        private int mNumColumnsId;
        private boolean mPropertiesMapped = false;
        private int mStretchModeId;
        private int mVerticalSpacingId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mColumnWidthId = propertyMapper.mapInt("columnWidth", 16843031);
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mHorizontalSpacingId = propertyMapper.mapInt("horizontalSpacing", 16843028);
            this.mNumColumnsId = propertyMapper.mapInt("numColumns", 16843032);
            SparseArray sparseArray = new SparseArray();
            sparseArray.put(0, "none");
            sparseArray.put(1, "spacingWidth");
            sparseArray.put(2, "columnWidth");
            sparseArray.put(3, "spacingWidthUniform");
            this.mStretchModeId = propertyMapper.mapIntEnum("stretchMode", 16843030, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mVerticalSpacingId = propertyMapper.mapInt("verticalSpacing", 16843029);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(GridView gridView, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readInt(this.mColumnWidthId, gridView.getColumnWidth());
            propertyReader.readGravity(this.mGravityId, gridView.getGravity());
            propertyReader.readInt(this.mHorizontalSpacingId, gridView.getHorizontalSpacing());
            propertyReader.readInt(this.mNumColumnsId, gridView.getNumColumns());
            propertyReader.readIntEnum(this.mStretchModeId, gridView.getStretchMode());
            propertyReader.readInt(this.mVerticalSpacingId, gridView.getVerticalSpacing());
        }
    }

    public GridView(Context context) {
        this(context, null);
    }

    public GridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842865);
    }

    public GridView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public GridView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mNumColumns = -1;
        this.mHorizontalSpacing = 0;
        this.mVerticalSpacing = 0;
        this.mStretchMode = 2;
        this.mReferenceView = null;
        this.mReferenceViewInSelectedRow = null;
        this.mGravity = Gravity.START;
        this.mTempRect = new Rect();
        this.mSelectZeroPositionOnKeyTab = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.GridView, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.GridView, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        setHorizontalSpacing(typedArrayObtainStyledAttributes.getDimensionPixelOffset(1, 0));
        setVerticalSpacing(typedArrayObtainStyledAttributes.getDimensionPixelOffset(2, 0));
        int i3 = typedArrayObtainStyledAttributes.getInt(3, 2);
        if (i3 >= 0) {
            setStretchMode(i3);
        }
        int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(4, -1);
        if (dimensionPixelOffset > 0) {
            setColumnWidth(dimensionPixelOffset);
        }
        setNumColumns(typedArrayObtainStyledAttributes.getInt(5, 1));
        int i4 = typedArrayObtainStyledAttributes.getInt(0, -1);
        if (i4 >= 0) {
            setGravity(i4);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setAddDeleteGridAnimator(SemAddDeleteGridAnimator semAddDeleteGridAnimator) {
        this.mAddDeleteGridAnimator = semAddDeleteGridAnimator;
    }

    public void setDndGridAnimator(SemDragAndDropGridAnimator semDragAndDropGridAnimator) {
        this.mDndGridAnimator = semDragAndDropGridAnimator;
        setChildrenDrawingOrderEnabled(true);
        this.mDndGridAnimator.setAutoScrollListener(new SemAbsDragAndDropAnimator.SemDragAutoScrollListener() { // from class: android.widget.GridView.1
            @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.SemDragAutoScrollListener
            public void onAutoScroll(int i) {
                GridView.this.trackMotionScroll(i, i);
            }
        });
    }

    @Override // android.widget.AdapterView
    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod(asyncImpl = "setRemoteViewsAdapterAsync")
    public void setRemoteViewsAdapter(Intent intent) throws Resources.NotFoundException {
        super.setRemoteViewsAdapter(intent);
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView
    public void setAdapter(ListAdapter listAdapter) throws Resources.NotFoundException {
        int iLookForSelectablePosition;
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        resetList();
        this.mRecycler.clear();
        this.mAdapter = listAdapter;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        super.setAdapter(listAdapter);
        if (this.mAdapter != null) {
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            this.mDataChanged = true;
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
            checkSelectionChanged();
        } else {
            checkFocus();
            checkSelectionChanged();
        }
        requestLayout();
    }

    @Override // android.widget.AdapterView
    int lookForSelectablePosition(int i, boolean z) {
        if (this.mAdapter == null || isInTouchMode() || i < 0 || i >= this.mItemCount) {
            return -1;
        }
        return i;
    }

    @Override // android.widget.AbsListView
    void fillGap(boolean z) throws Resources.NotFoundException {
        int i = this.mNumColumns;
        int i2 = this.mVerticalSpacing;
        int childCount = getChildCount();
        if (z) {
            int listPaddingTop = (this.mGroupFlags & 34) == 34 ? getListPaddingTop() : 0;
            if (childCount > 0) {
                listPaddingTop = getChildAt(childCount - 1).getBottom() + i2;
            }
            int i3 = this.mFirstPosition + childCount;
            if (this.mStackFromBottom) {
                i3 += i - 1;
            }
            fillDown(i3, listPaddingTop);
            correctTooHigh(i, i2, getChildCount());
            return;
        }
        int top = childCount > 0 ? getChildAt(0).getTop() - i2 : getHeight() - ((this.mGroupFlags & 34) == 34 ? getListPaddingBottom() : 0);
        int i4 = this.mFirstPosition;
        fillUp(!this.mStackFromBottom ? i4 - i : i4 - 1, top);
        correctTooLow(i, i2, getChildCount());
    }

    private View fillDown(int i, int i2) throws Resources.NotFoundException {
        int i3 = this.mBottom - this.mTop;
        View view = null;
        if ((this.mGroupFlags & 34) == 34) {
            i3 -= this.mListPadding.bottom;
        }
        while (i2 < i3 && i < this.mItemCount) {
            View viewMakeRow = makeRow(i, i2, true);
            if (viewMakeRow != null) {
                view = viewMakeRow;
            }
            i2 = this.mReferenceView.getBottom() + this.mVerticalSpacing;
            i += this.mNumColumns;
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return view;
    }

    private View makeRow(int i, int i2, boolean z) throws Resources.NotFoundException {
        int width;
        int iMin;
        int i3;
        int i4 = this.mColumnWidth;
        int i5 = this.mHorizontalSpacing;
        boolean zIsLayoutRtl = isLayoutRtl();
        if (zIsLayoutRtl) {
            width = ((getWidth() - this.mListPadding.right) - i4) - (this.mStretchMode == 3 ? i5 : 0);
        } else {
            width = this.mListPadding.left + (this.mStretchMode == 3 ? i5 : 0);
        }
        if (!this.mStackFromBottom) {
            i3 = i;
            iMin = Math.min(i + this.mNumColumns, this.mItemCount);
        } else {
            int i6 = i + 1;
            int iMax = Math.max(0, (i - this.mNumColumns) + 1);
            int i7 = i6 - iMax;
            int i8 = this.mNumColumns;
            if (i7 < i8) {
                width += (zIsLayoutRtl ? -1 : 1) * (i8 - i7) * (i4 + i5);
            }
            iMin = i6;
            i3 = iMax;
        }
        boolean zShouldShowSelector = shouldShowSelector();
        boolean z2 = touchModeDrawsInPressedState();
        int i9 = this.mSelectedPosition;
        int i10 = zIsLayoutRtl ? -1 : 1;
        View view = null;
        View view2 = null;
        int i11 = width;
        int i12 = i3;
        while (i12 < iMin) {
            boolean z3 = i12 == i9;
            int i13 = i9;
            int i14 = i12;
            View viewMakeAndAddView = makeAndAddView(i14, i2, z, i11, z3, z ? -1 : i12 - i3);
            i11 += i10 * i4;
            if (i14 < iMin - 1) {
                i11 += i10 * i5;
            }
            if (z3 && (zShouldShowSelector || z2)) {
                view2 = viewMakeAndAddView;
            }
            i12 = i14 + 1;
            view = viewMakeAndAddView;
            i9 = i13;
        }
        this.mReferenceView = view;
        if (view2 != null) {
            this.mReferenceViewInSelectedRow = view;
        }
        return view2;
    }

    private View fillUp(int i, int i2) throws Resources.NotFoundException {
        View view = null;
        int i3 = (this.mGroupFlags & 34) == 34 ? this.mListPadding.top : 0;
        while (i2 > i3 && i >= 0) {
            View viewMakeRow = makeRow(i, i2, false);
            if (viewMakeRow != null) {
                view = viewMakeRow;
            }
            i2 = this.mReferenceView.getTop() - this.mVerticalSpacing;
            this.mFirstPosition = i;
            i -= this.mNumColumns;
        }
        if (this.mStackFromBottom) {
            this.mFirstPosition = Math.max(0, i + 1);
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return view;
    }

    private View fillFromTop(int i) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        this.mFirstPosition -= this.mFirstPosition % this.mNumColumns;
        return fillDown(this.mFirstPosition, i);
    }

    private View fillFromBottom(int i, int i2) {
        int iMin = (this.mItemCount - 1) - Math.min(Math.max(i, this.mSelectedPosition), this.mItemCount - 1);
        return fillUp((this.mItemCount - 1) - (iMin - (iMin % this.mNumColumns)), i2);
    }

    private View fillSelection(int i, int i2) throws Resources.NotFoundException {
        int i3;
        int iMax;
        int iReconcileSelectedPosition = reconcileSelectedPosition();
        int i4 = this.mNumColumns;
        int i5 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            iMax = iReconcileSelectedPosition - (iReconcileSelectedPosition % i4);
            i3 = -1;
        } else {
            int i6 = (this.mItemCount - 1) - iReconcileSelectedPosition;
            i3 = (this.mItemCount - 1) - (i6 - (i6 % i4));
            iMax = Math.max(0, (i3 - i4) + 1);
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        View viewMakeRow = makeRow(this.mStackFromBottom ? i3 : iMax, getTopSelectionPixel(i, verticalFadingEdgeLength, iMax), true);
        this.mFirstPosition = iMax;
        View view = this.mReferenceView;
        if (!this.mStackFromBottom) {
            fillDown(iMax + i4, view.getBottom() + i5);
            pinToBottom(i2);
            fillUp(iMax - i4, view.getTop() - i5);
            adjustViewsUpOrDown();
            return viewMakeRow;
        }
        offsetChildrenTopAndBottom(getBottomSelectionPixel(i2, verticalFadingEdgeLength, i4, iMax) - view.getBottom());
        fillUp(iMax - 1, view.getTop() - i5);
        pinToTop(i);
        fillDown(i3 + i4, view.getBottom() + i5);
        adjustViewsUpOrDown();
        return viewMakeRow;
    }

    private void pinToTop(int i) {
        int top;
        if (this.mFirstPosition != 0 || (top = i - getChildAt(0).getTop()) >= 0) {
            return;
        }
        offsetChildrenTopAndBottom(top);
    }

    private void pinToBottom(int i) {
        int bottom;
        int childCount = getChildCount();
        if (this.mFirstPosition + childCount != this.mItemCount || (bottom = i - getChildAt(childCount - 1).getBottom()) <= 0) {
            return;
        }
        offsetChildrenTopAndBottom(bottom);
    }

    @Override // android.widget.AbsListView
    int findMotionRow(int i) {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return -1;
        }
        int i2 = this.mNumColumns;
        if (this.mStackFromBottom) {
            for (int i3 = childCount - 1; i3 >= 0; i3 -= i2) {
                if (i >= getChildAt(i3).getTop()) {
                    return this.mFirstPosition + i3;
                }
            }
            return -1;
        }
        for (int i4 = 0; i4 < childCount; i4 += i2) {
            if (i <= getChildAt(i4).getBottom()) {
                return this.mFirstPosition + i4;
            }
        }
        return -1;
    }

    private View fillSpecific(int i, int i2) throws Resources.NotFoundException {
        int i3;
        int iMax;
        View viewFillUp;
        View viewFillDown;
        int i4 = this.mNumColumns;
        if (!this.mStackFromBottom) {
            iMax = i - (i % i4);
            i3 = -1;
        } else {
            int i5 = (this.mItemCount - 1) - i;
            i3 = (this.mItemCount - 1) - (i5 - (i5 % i4));
            iMax = Math.max(0, (i3 - i4) + 1);
        }
        View viewMakeRow = makeRow(this.mStackFromBottom ? i3 : iMax, i2, true);
        this.mFirstPosition = iMax;
        View view = this.mReferenceView;
        if (view == null) {
            return null;
        }
        int i6 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            viewFillUp = fillUp(iMax - i4, view.getTop() - i6);
            adjustViewsUpOrDown();
            viewFillDown = fillDown(iMax + i4, view.getBottom() + i6);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHigh(i4, i6, childCount);
            }
        } else {
            View viewFillDown2 = fillDown(i3 + i4, view.getBottom() + i6);
            adjustViewsUpOrDown();
            View viewFillUp2 = fillUp(iMax - 1, view.getTop() - i6);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLow(i4, i6, childCount2);
            }
            viewFillUp = viewFillUp2;
            viewFillDown = viewFillDown2;
        }
        return viewMakeRow != null ? viewMakeRow : viewFillUp != null ? viewFillUp : viewFillDown;
    }

    private void correctTooHigh(int i, int i2, int i3) throws Resources.NotFoundException {
        if ((this.mFirstPosition + i3) - 1 != this.mItemCount - 1 || i3 <= 0) {
            return;
        }
        int bottom = ((this.mBottom - this.mTop) - this.mListPadding.bottom) - getChildAt(i3 - 1).getBottom();
        View childAt = getChildAt(0);
        int top = childAt.getTop();
        if (bottom > 0) {
            if (this.mFirstPosition > 0 || top < this.mListPadding.top) {
                if (this.mFirstPosition == 0) {
                    bottom = Math.min(bottom, this.mListPadding.top - top);
                }
                offsetChildrenTopAndBottom(bottom);
                if (this.mFirstPosition > 0) {
                    int i4 = this.mFirstPosition;
                    if (this.mStackFromBottom) {
                        i = 1;
                    }
                    fillUp(i4 - i, childAt.getTop() - i2);
                    adjustViewsUpOrDown();
                }
            }
        }
    }

    private void correctTooLow(int i, int i2, int i3) throws Resources.NotFoundException {
        if (this.mFirstPosition != 0 || i3 <= 0) {
            return;
        }
        int top = getChildAt(0).getTop();
        int i4 = this.mListPadding.top;
        int i5 = (this.mBottom - this.mTop) - this.mListPadding.bottom;
        int iMin = top - i4;
        View childAt = getChildAt(i3 - 1);
        int bottom = childAt.getBottom();
        int i6 = (this.mFirstPosition + i3) - 1;
        if (iMin > 0) {
            if (i6 < this.mItemCount - 1 || bottom > i5) {
                if (i6 == this.mItemCount - 1) {
                    iMin = Math.min(iMin, bottom - i5);
                }
                offsetChildrenTopAndBottom(-iMin);
                if (i6 < this.mItemCount - 1) {
                    if (!this.mStackFromBottom) {
                        i = 1;
                    }
                    fillDown(i6 + i, childAt.getBottom() + i2);
                    adjustViewsUpOrDown();
                }
            }
        }
    }

    private View fillFromSelection(int i, int i2, int i3) throws Resources.NotFoundException {
        int i4;
        int iMax;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int i5 = this.mSelectedPosition;
        int i6 = this.mNumColumns;
        int i7 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            iMax = i5 - (i5 % i6);
            i4 = -1;
        } else {
            int i8 = (this.mItemCount - 1) - i5;
            i4 = (this.mItemCount - 1) - (i8 - (i8 % i6));
            iMax = Math.max(0, (i4 - i6) + 1);
        }
        int topSelectionPixel = getTopSelectionPixel(i2, verticalFadingEdgeLength, iMax);
        int bottomSelectionPixel = getBottomSelectionPixel(i3, verticalFadingEdgeLength, i6, iMax);
        View viewMakeRow = makeRow(this.mStackFromBottom ? i4 : iMax, i, true);
        this.mFirstPosition = iMax;
        View view = this.mReferenceView;
        adjustForTopFadingEdge(view, topSelectionPixel, bottomSelectionPixel);
        adjustForBottomFadingEdge(view, topSelectionPixel, bottomSelectionPixel);
        if (!this.mStackFromBottom) {
            fillUp(iMax - i6, view.getTop() - i7);
            adjustViewsUpOrDown();
            fillDown(iMax + i6, view.getBottom() + i7);
            return viewMakeRow;
        }
        fillDown(i4 + i6, view.getBottom() + i7);
        adjustViewsUpOrDown();
        fillUp(iMax - 1, view.getTop() - i7);
        return viewMakeRow;
    }

    private int getBottomSelectionPixel(int i, int i2, int i3, int i4) {
        return (i4 + i3) + (-1) < this.mItemCount + (-1) ? i - i2 : i;
    }

    private void adjustForBottomFadingEdge(View view, int i, int i2) {
        if (view.getBottom() > i2) {
            offsetChildrenTopAndBottom(-Math.min(view.getTop() - i, view.getBottom() - i2));
        }
    }

    private void adjustForTopFadingEdge(View view, int i, int i2) {
        if (view.getTop() < i) {
            offsetChildrenTopAndBottom(Math.min(i - view.getTop(), i2 - view.getBottom()));
        }
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

    private View moveSelection(int i, int i2, int i3) throws Resources.NotFoundException {
        int i4;
        int iMax;
        int i5;
        View viewMakeRow;
        View view;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int i6 = this.mSelectedPosition;
        int i7 = this.mNumColumns;
        int i8 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            int i9 = i6 - i;
            iMax = i9 - (i9 % i7);
            i5 = i6 - (i6 % i7);
            i4 = -1;
        } else {
            int i10 = (this.mItemCount - 1) - i6;
            i4 = (this.mItemCount - 1) - (i10 - (i10 % i7));
            int iMax2 = Math.max(0, (i4 - i7) + 1);
            int i11 = (this.mItemCount - 1) - (i6 - i);
            iMax = Math.max(0, (((this.mItemCount - 1) - (i11 - (i11 % i7))) - i7) + 1);
            i5 = iMax2;
        }
        int i12 = i5 - iMax;
        int topSelectionPixel = getTopSelectionPixel(i2, verticalFadingEdgeLength, i5);
        int bottomSelectionPixel = getBottomSelectionPixel(i3, verticalFadingEdgeLength, i7, i5);
        this.mFirstPosition = i5;
        if (i12 > 0) {
            View view2 = this.mReferenceViewInSelectedRow;
            viewMakeRow = makeRow(this.mStackFromBottom ? i4 : i5, (view2 != null ? view2.getBottom() : 0) + i8, true);
            view = this.mReferenceView;
            adjustForBottomFadingEdge(view, topSelectionPixel, bottomSelectionPixel);
        } else if (i12 < 0) {
            View view3 = this.mReferenceViewInSelectedRow;
            viewMakeRow = makeRow(this.mStackFromBottom ? i4 : i5, (view3 == null ? 0 : view3.getTop()) - i8, false);
            view = this.mReferenceView;
            adjustForTopFadingEdge(view, topSelectionPixel, bottomSelectionPixel);
        } else {
            View view4 = this.mReferenceViewInSelectedRow;
            viewMakeRow = makeRow(this.mStackFromBottom ? i4 : i5, view4 != null ? view4.getTop() : 0, true);
            view = this.mReferenceView;
        }
        if (!this.mStackFromBottom) {
            fillUp(i5 - i7, view.getTop() - i8);
            adjustViewsUpOrDown();
            fillDown(i5 + i7, view.getBottom() + i8);
            return viewMakeRow;
        }
        fillDown(i4 + i7, view.getBottom() + i8);
        adjustViewsUpOrDown();
        fillUp(i5 - 1, view.getTop() - i8);
        return viewMakeRow;
    }

    private boolean determineColumns(int i) {
        int i2 = this.mRequestedHorizontalSpacing;
        int i3 = this.mStretchMode;
        int i4 = this.mRequestedColumnWidth;
        int i5 = this.mRequestedNumColumns;
        if (i5 != -1) {
            this.mNumColumns = i5;
        } else if (i4 > 0) {
            this.mNumColumns = (i + i2) / (i4 + i2);
        } else {
            this.mNumColumns = 2;
        }
        if (this.mNumColumns <= 0) {
            this.mNumColumns = 1;
        }
        if (i3 == 0) {
            this.mColumnWidth = i4;
            this.mHorizontalSpacing = i2;
            return false;
        }
        int i6 = this.mNumColumns;
        int i7 = (i - (i6 * i4)) - ((i6 - 1) * i2);
        boolean z = i7 < 0;
        if (i3 == 1) {
            this.mColumnWidth = i4;
            if (i6 > 1) {
                this.mHorizontalSpacing = i2 + (i7 / (i6 - 1));
                return z;
            }
            this.mHorizontalSpacing = i2 + i7;
            return z;
        }
        if (i3 == 2) {
            this.mColumnWidth = i4 + (i7 / i6);
            this.mHorizontalSpacing = i2;
            return z;
        }
        if (i3 != 3) {
            return z;
        }
        this.mColumnWidth = i4;
        if (i6 > 1) {
            this.mHorizontalSpacing = i2 + (i7 / (i6 + 1));
            return z;
        }
        this.mHorizontalSpacing = i2 + i7;
        return z;
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onMeasure(int i, int i2) {
        int measuredHeight;
        int i3;
        int i4;
        int i5;
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode == 0) {
            int i6 = this.mColumnWidth;
            if (i6 > 0) {
                i4 = i6 + this.mListPadding.left;
                i5 = this.mListPadding.right;
            } else {
                i4 = this.mListPadding.left;
                i5 = this.mListPadding.right;
            }
            size = i4 + i5 + getVerticalScrollbarWidth();
        }
        boolean zDetermineColumns = determineColumns((size - this.mListPadding.left) - this.mListPadding.right);
        int i7 = 0;
        this.mItemCount = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        int i8 = this.mItemCount;
        if (i8 > 0) {
            View viewObtainView = obtainView(0, this.mIsScrap);
            AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) viewObtainView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = (AbsListView.LayoutParams) generateDefaultLayoutParams();
                viewObtainView.setLayoutParams(layoutParams);
            }
            layoutParams.viewType = this.mAdapter.getItemViewType(0);
            layoutParams.isEnabled = this.mAdapter.isEnabled(0);
            layoutParams.forceAdd = true;
            viewObtainView.measure(getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(this.mColumnWidth, 1073741824), 0, layoutParams.width), getChildMeasureSpec(View.MeasureSpec.makeSafeMeasureSpec(View.MeasureSpec.getSize(i2), 0), 0, layoutParams.height));
            measuredHeight = viewObtainView.getMeasuredHeight();
            combineMeasuredStates(0, viewObtainView.getMeasuredState());
            if (this.mRecycler.shouldRecycleViewType(layoutParams.viewType)) {
                this.mRecycler.addScrapView(viewObtainView, -1);
            }
        } else {
            measuredHeight = 0;
        }
        if (mode2 == 0) {
            size2 = this.mListPadding.top + this.mListPadding.bottom + measuredHeight + (getVerticalFadingEdgeLength() * 2);
        }
        if (mode2 == Integer.MIN_VALUE) {
            int i9 = this.mListPadding.top + this.mListPadding.bottom;
            int i10 = this.mNumColumns;
            while (true) {
                if (i7 >= i8) {
                    size2 = i9;
                    break;
                }
                i9 += measuredHeight;
                i7 += i10;
                if (i7 < i8) {
                    i9 += this.mVerticalSpacing;
                }
                if (i9 >= size2) {
                    break;
                }
            }
        }
        if (mode == Integer.MIN_VALUE && (i3 = this.mRequestedNumColumns) != -1 && ((this.mColumnWidth * i3) + ((i3 - 1) * this.mHorizontalSpacing) + this.mListPadding.left + this.mListPadding.right > size || zDetermineColumns)) {
            size |= 16777216;
        }
        setMeasuredDimension(size, size2);
        this.mWidthMeasureSpec = i;
    }

    @Override // android.view.ViewGroup
    protected void attachLayoutAnimationParameters(View view, ViewGroup.LayoutParams layoutParams, int i, int i2) {
        GridLayoutAnimationController.AnimationParameters animationParameters = (GridLayoutAnimationController.AnimationParameters) layoutParams.layoutAnimationParameters;
        if (animationParameters == null) {
            animationParameters = new GridLayoutAnimationController.AnimationParameters();
            layoutParams.layoutAnimationParameters = animationParameters;
        }
        animationParameters.count = i2;
        animationParameters.index = i;
        animationParameters.columnsCount = this.mNumColumns;
        animationParameters.rowsCount = i2 / this.mNumColumns;
        if (!this.mStackFromBottom) {
            animationParameters.column = i % this.mNumColumns;
            animationParameters.row = i / this.mNumColumns;
        } else {
            int i3 = (i2 - 1) - i;
            int i4 = this.mNumColumns;
            animationParameters.column = (i4 - 1) - (i3 % i4);
            animationParameters.row = (animationParameters.rowsCount - 1) - (i3 / this.mNumColumns);
        }
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        SemDragAndDropGridAnimator semDragAndDropGridAnimator = this.mDndGridAnimator;
        if (semDragAndDropGridAnimator == null || !semDragAndDropGridAnimator.onInterceptTouchEvent(motionEvent)) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        SemDragAndDropGridAnimator semDragAndDropGridAnimator = this.mDndGridAnimator;
        if (semDragAndDropGridAnimator == null || !semDragAndDropGridAnimator.onTouchEvent(motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        SemDragAndDropGridAnimator semDragAndDropGridAnimator = this.mDndGridAnimator;
        return semDragAndDropGridAnimator != null ? semDragAndDropGridAnimator.getChildDrawingOrder(i, i2) : super.getChildDrawingOrder(i, i2);
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        SemDragAndDropGridAnimator semDragAndDropGridAnimator = this.mDndGridAnimator;
        if (semDragAndDropGridAnimator != null && !semDragAndDropGridAnimator.preDrawChild(canvas, view, j)) {
            return false;
        }
        boolean zDrawChild = super.drawChild(canvas, view, j);
        SemDragAndDropGridAnimator semDragAndDropGridAnimator2 = this.mDndGridAnimator;
        if (semDragAndDropGridAnimator2 != null) {
            semDragAndDropGridAnimator2.postDrawChild(canvas, view, j);
        }
        return zDrawChild;
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        SemAddDeleteGridAnimator semAddDeleteGridAnimator = this.mAddDeleteGridAnimator;
        if (semAddDeleteGridAnimator != null) {
            semAddDeleteGridAnimator.draw(canvas);
        }
        super.dispatchDraw(canvas);
        SemDragAndDropGridAnimator semDragAndDropGridAnimator = this.mDndGridAnimator;
        if (semDragAndDropGridAnimator != null) {
            semDragAndDropGridAnimator.dispatchDraw(canvas);
        }
    }

    @Override // android.widget.AbsListView, android.view.View
    public void onWindowFocusChanged(boolean z) throws Resources.NotFoundException {
        super.onWindowFocusChanged(z);
        if (!z || this.mDndGridAnimator == null) {
            return;
        }
        post(new Runnable() { // from class: android.widget.GridView.2
            @Override // java.lang.Runnable
            public void run() {
                GridView.this.mDndGridAnimator.speakDescriptionForAccessibility();
            }
        });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:13:0x0033. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:116:0x01ab A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:65:0x00cf, B:68:0x00ed, B:69:0x00f5, B:71:0x00fa, B:81:0x0144, B:83:0x0148, B:88:0x0152, B:114:0x01a6, B:116:0x01ab, B:133:0x01f4, B:136:0x01fc, B:138:0x0202, B:141:0x020a, B:142:0x0219, B:145:0x0220, B:147:0x0234, B:150:0x023b, B:152:0x024b, B:154:0x025c, B:157:0x0264, B:159:0x0269, B:153:0x0258, B:160:0x026c, B:162:0x0275, B:163:0x027d, B:165:0x028c, B:166:0x028f, B:117:0x01b6, B:119:0x01ba, B:121:0x01bf, B:123:0x01ca, B:124:0x01d0, B:126:0x01d5, B:128:0x01d9, B:130:0x01e4, B:131:0x01ea, B:89:0x015a, B:91:0x0162, B:96:0x016c, B:97:0x0174, B:99:0x0178, B:101:0x017e, B:105:0x0187, B:104:0x0183, B:106:0x018c, B:108:0x0192, B:112:0x019b, B:111:0x0197, B:113:0x01a0, B:72:0x00fd, B:73:0x0103, B:74:0x010d, B:75:0x0117, B:77:0x0126, B:78:0x0130, B:79:0x0136, B:67:0x00e6), top: B:177:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x01b6 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:65:0x00cf, B:68:0x00ed, B:69:0x00f5, B:71:0x00fa, B:81:0x0144, B:83:0x0148, B:88:0x0152, B:114:0x01a6, B:116:0x01ab, B:133:0x01f4, B:136:0x01fc, B:138:0x0202, B:141:0x020a, B:142:0x0219, B:145:0x0220, B:147:0x0234, B:150:0x023b, B:152:0x024b, B:154:0x025c, B:157:0x0264, B:159:0x0269, B:153:0x0258, B:160:0x026c, B:162:0x0275, B:163:0x027d, B:165:0x028c, B:166:0x028f, B:117:0x01b6, B:119:0x01ba, B:121:0x01bf, B:123:0x01ca, B:124:0x01d0, B:126:0x01d5, B:128:0x01d9, B:130:0x01e4, B:131:0x01ea, B:89:0x015a, B:91:0x0162, B:96:0x016c, B:97:0x0174, B:99:0x0178, B:101:0x017e, B:105:0x0187, B:104:0x0183, B:106:0x018c, B:108:0x0192, B:112:0x019b, B:111:0x0197, B:113:0x01a0, B:72:0x00fd, B:73:0x0103, B:74:0x010d, B:75:0x0117, B:77:0x0126, B:78:0x0130, B:79:0x0136, B:67:0x00e6), top: B:177:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x01f4 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:65:0x00cf, B:68:0x00ed, B:69:0x00f5, B:71:0x00fa, B:81:0x0144, B:83:0x0148, B:88:0x0152, B:114:0x01a6, B:116:0x01ab, B:133:0x01f4, B:136:0x01fc, B:138:0x0202, B:141:0x020a, B:142:0x0219, B:145:0x0220, B:147:0x0234, B:150:0x023b, B:152:0x024b, B:154:0x025c, B:157:0x0264, B:159:0x0269, B:153:0x0258, B:160:0x026c, B:162:0x0275, B:163:0x027d, B:165:0x028c, B:166:0x028f, B:117:0x01b6, B:119:0x01ba, B:121:0x01bf, B:123:0x01ca, B:124:0x01d0, B:126:0x01d5, B:128:0x01d9, B:130:0x01e4, B:131:0x01ea, B:89:0x015a, B:91:0x0162, B:96:0x016c, B:97:0x0174, B:99:0x0178, B:101:0x017e, B:105:0x0187, B:104:0x0183, B:106:0x018c, B:108:0x0192, B:112:0x019b, B:111:0x0197, B:113:0x01a0, B:72:0x00fd, B:73:0x0103, B:74:0x010d, B:75:0x0117, B:77:0x0126, B:78:0x0130, B:79:0x0136, B:67:0x00e6), top: B:177:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0275 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:65:0x00cf, B:68:0x00ed, B:69:0x00f5, B:71:0x00fa, B:81:0x0144, B:83:0x0148, B:88:0x0152, B:114:0x01a6, B:116:0x01ab, B:133:0x01f4, B:136:0x01fc, B:138:0x0202, B:141:0x020a, B:142:0x0219, B:145:0x0220, B:147:0x0234, B:150:0x023b, B:152:0x024b, B:154:0x025c, B:157:0x0264, B:159:0x0269, B:153:0x0258, B:160:0x026c, B:162:0x0275, B:163:0x027d, B:165:0x028c, B:166:0x028f, B:117:0x01b6, B:119:0x01ba, B:121:0x01bf, B:123:0x01ca, B:124:0x01d0, B:126:0x01d5, B:128:0x01d9, B:130:0x01e4, B:131:0x01ea, B:89:0x015a, B:91:0x0162, B:96:0x016c, B:97:0x0174, B:99:0x0178, B:101:0x017e, B:105:0x0187, B:104:0x0183, B:106:0x018c, B:108:0x0192, B:112:0x019b, B:111:0x0197, B:113:0x01a0, B:72:0x00fd, B:73:0x0103, B:74:0x010d, B:75:0x0117, B:77:0x0126, B:78:0x0130, B:79:0x0136, B:67:0x00e6), top: B:177:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x028c A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:65:0x00cf, B:68:0x00ed, B:69:0x00f5, B:71:0x00fa, B:81:0x0144, B:83:0x0148, B:88:0x0152, B:114:0x01a6, B:116:0x01ab, B:133:0x01f4, B:136:0x01fc, B:138:0x0202, B:141:0x020a, B:142:0x0219, B:145:0x0220, B:147:0x0234, B:150:0x023b, B:152:0x024b, B:154:0x025c, B:157:0x0264, B:159:0x0269, B:153:0x0258, B:160:0x026c, B:162:0x0275, B:163:0x027d, B:165:0x028c, B:166:0x028f, B:117:0x01b6, B:119:0x01ba, B:121:0x01bf, B:123:0x01ca, B:124:0x01d0, B:126:0x01d5, B:128:0x01d9, B:130:0x01e4, B:131:0x01ea, B:89:0x015a, B:91:0x0162, B:96:0x016c, B:97:0x0174, B:99:0x0178, B:101:0x017e, B:105:0x0187, B:104:0x0183, B:106:0x018c, B:108:0x0192, B:112:0x019b, B:111:0x0197, B:113:0x01a0, B:72:0x00fd, B:73:0x0103, B:74:0x010d, B:75:0x0117, B:77:0x0126, B:78:0x0130, B:79:0x0136, B:67:0x00e6), top: B:177:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:183:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00e6 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:65:0x00cf, B:68:0x00ed, B:69:0x00f5, B:71:0x00fa, B:81:0x0144, B:83:0x0148, B:88:0x0152, B:114:0x01a6, B:116:0x01ab, B:133:0x01f4, B:136:0x01fc, B:138:0x0202, B:141:0x020a, B:142:0x0219, B:145:0x0220, B:147:0x0234, B:150:0x023b, B:152:0x024b, B:154:0x025c, B:157:0x0264, B:159:0x0269, B:153:0x0258, B:160:0x026c, B:162:0x0275, B:163:0x027d, B:165:0x028c, B:166:0x028f, B:117:0x01b6, B:119:0x01ba, B:121:0x01bf, B:123:0x01ca, B:124:0x01d0, B:126:0x01d5, B:128:0x01d9, B:130:0x01e4, B:131:0x01ea, B:89:0x015a, B:91:0x0162, B:96:0x016c, B:97:0x0174, B:99:0x0178, B:101:0x017e, B:105:0x0187, B:104:0x0183, B:106:0x018c, B:108:0x0192, B:112:0x019b, B:111:0x0197, B:113:0x01a0, B:72:0x00fd, B:73:0x0103, B:74:0x010d, B:75:0x0117, B:77:0x0126, B:78:0x0130, B:79:0x0136, B:67:0x00e6), top: B:177:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00fd A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:65:0x00cf, B:68:0x00ed, B:69:0x00f5, B:71:0x00fa, B:81:0x0144, B:83:0x0148, B:88:0x0152, B:114:0x01a6, B:116:0x01ab, B:133:0x01f4, B:136:0x01fc, B:138:0x0202, B:141:0x020a, B:142:0x0219, B:145:0x0220, B:147:0x0234, B:150:0x023b, B:152:0x024b, B:154:0x025c, B:157:0x0264, B:159:0x0269, B:153:0x0258, B:160:0x026c, B:162:0x0275, B:163:0x027d, B:165:0x028c, B:166:0x028f, B:117:0x01b6, B:119:0x01ba, B:121:0x01bf, B:123:0x01ca, B:124:0x01d0, B:126:0x01d5, B:128:0x01d9, B:130:0x01e4, B:131:0x01ea, B:89:0x015a, B:91:0x0162, B:96:0x016c, B:97:0x0174, B:99:0x0178, B:101:0x017e, B:105:0x0187, B:104:0x0183, B:106:0x018c, B:108:0x0192, B:112:0x019b, B:111:0x0197, B:113:0x01a0, B:72:0x00fd, B:73:0x0103, B:74:0x010d, B:75:0x0117, B:77:0x0126, B:78:0x0130, B:79:0x0136, B:67:0x00e6), top: B:177:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0103 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:65:0x00cf, B:68:0x00ed, B:69:0x00f5, B:71:0x00fa, B:81:0x0144, B:83:0x0148, B:88:0x0152, B:114:0x01a6, B:116:0x01ab, B:133:0x01f4, B:136:0x01fc, B:138:0x0202, B:141:0x020a, B:142:0x0219, B:145:0x0220, B:147:0x0234, B:150:0x023b, B:152:0x024b, B:154:0x025c, B:157:0x0264, B:159:0x0269, B:153:0x0258, B:160:0x026c, B:162:0x0275, B:163:0x027d, B:165:0x028c, B:166:0x028f, B:117:0x01b6, B:119:0x01ba, B:121:0x01bf, B:123:0x01ca, B:124:0x01d0, B:126:0x01d5, B:128:0x01d9, B:130:0x01e4, B:131:0x01ea, B:89:0x015a, B:91:0x0162, B:96:0x016c, B:97:0x0174, B:99:0x0178, B:101:0x017e, B:105:0x0187, B:104:0x0183, B:106:0x018c, B:108:0x0192, B:112:0x019b, B:111:0x0197, B:113:0x01a0, B:72:0x00fd, B:73:0x0103, B:74:0x010d, B:75:0x0117, B:77:0x0126, B:78:0x0130, B:79:0x0136, B:67:0x00e6), top: B:177:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x010d A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:65:0x00cf, B:68:0x00ed, B:69:0x00f5, B:71:0x00fa, B:81:0x0144, B:83:0x0148, B:88:0x0152, B:114:0x01a6, B:116:0x01ab, B:133:0x01f4, B:136:0x01fc, B:138:0x0202, B:141:0x020a, B:142:0x0219, B:145:0x0220, B:147:0x0234, B:150:0x023b, B:152:0x024b, B:154:0x025c, B:157:0x0264, B:159:0x0269, B:153:0x0258, B:160:0x026c, B:162:0x0275, B:163:0x027d, B:165:0x028c, B:166:0x028f, B:117:0x01b6, B:119:0x01ba, B:121:0x01bf, B:123:0x01ca, B:124:0x01d0, B:126:0x01d5, B:128:0x01d9, B:130:0x01e4, B:131:0x01ea, B:89:0x015a, B:91:0x0162, B:96:0x016c, B:97:0x0174, B:99:0x0178, B:101:0x017e, B:105:0x0187, B:104:0x0183, B:106:0x018c, B:108:0x0192, B:112:0x019b, B:111:0x0197, B:113:0x01a0, B:72:0x00fd, B:73:0x0103, B:74:0x010d, B:75:0x0117, B:77:0x0126, B:78:0x0130, B:79:0x0136, B:67:0x00e6), top: B:177:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0117 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:65:0x00cf, B:68:0x00ed, B:69:0x00f5, B:71:0x00fa, B:81:0x0144, B:83:0x0148, B:88:0x0152, B:114:0x01a6, B:116:0x01ab, B:133:0x01f4, B:136:0x01fc, B:138:0x0202, B:141:0x020a, B:142:0x0219, B:145:0x0220, B:147:0x0234, B:150:0x023b, B:152:0x024b, B:154:0x025c, B:157:0x0264, B:159:0x0269, B:153:0x0258, B:160:0x026c, B:162:0x0275, B:163:0x027d, B:165:0x028c, B:166:0x028f, B:117:0x01b6, B:119:0x01ba, B:121:0x01bf, B:123:0x01ca, B:124:0x01d0, B:126:0x01d5, B:128:0x01d9, B:130:0x01e4, B:131:0x01ea, B:89:0x015a, B:91:0x0162, B:96:0x016c, B:97:0x0174, B:99:0x0178, B:101:0x017e, B:105:0x0187, B:104:0x0183, B:106:0x018c, B:108:0x0192, B:112:0x019b, B:111:0x0197, B:113:0x01a0, B:72:0x00fd, B:73:0x0103, B:74:0x010d, B:75:0x0117, B:77:0x0126, B:78:0x0130, B:79:0x0136, B:67:0x00e6), top: B:177:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0136 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:65:0x00cf, B:68:0x00ed, B:69:0x00f5, B:71:0x00fa, B:81:0x0144, B:83:0x0148, B:88:0x0152, B:114:0x01a6, B:116:0x01ab, B:133:0x01f4, B:136:0x01fc, B:138:0x0202, B:141:0x020a, B:142:0x0219, B:145:0x0220, B:147:0x0234, B:150:0x023b, B:152:0x024b, B:154:0x025c, B:157:0x0264, B:159:0x0269, B:153:0x0258, B:160:0x026c, B:162:0x0275, B:163:0x027d, B:165:0x028c, B:166:0x028f, B:117:0x01b6, B:119:0x01ba, B:121:0x01bf, B:123:0x01ca, B:124:0x01d0, B:126:0x01d5, B:128:0x01d9, B:130:0x01e4, B:131:0x01ea, B:89:0x015a, B:91:0x0162, B:96:0x016c, B:97:0x0174, B:99:0x0178, B:101:0x017e, B:105:0x0187, B:104:0x0183, B:106:0x018c, B:108:0x0192, B:112:0x019b, B:111:0x0197, B:113:0x01a0, B:72:0x00fd, B:73:0x0103, B:74:0x010d, B:75:0x0117, B:77:0x0126, B:78:0x0130, B:79:0x0136, B:67:0x00e6), top: B:177:0x00c8 }] */
    @Override // android.widget.AbsListView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void layoutChildren() throws Throwable {
        boolean z;
        int i;
        View childAt;
        View childAt2;
        View childAt3;
        boolean z2;
        int positionForView;
        AccessibilityNodeInfo accessibilityFocusedVirtualView;
        View accessibilityFocusedHost;
        AccessibilityNodeInfo accessibilityNodeInfo;
        View viewFillFromTop;
        View childAt4;
        View childAt5;
        boolean z3 = this.mBlockLayoutRequests;
        if (!z3) {
            this.mBlockLayoutRequests = true;
        }
        try {
            super.layoutChildren();
            invalidate();
            if (this.mAdapter == null) {
                resetList();
                invokeOnItemScrollListener();
                if (z3) {
                    return;
                }
                this.mBlockLayoutRequests = false;
                return;
            }
            int top = this.mListPadding.top;
            int i2 = (this.mBottom - this.mTop) - this.mListPadding.bottom;
            int childCount = getChildCount();
            switch (this.mLayoutMode) {
                case 1:
                case 3:
                case 4:
                case 5:
                    i = 0;
                    childAt = null;
                    childAt2 = null;
                    childAt3 = null;
                    break;
                case 2:
                    int i3 = this.mNextSelectedPosition - this.mFirstPosition;
                    if (i3 >= 0 && i3 < childCount) {
                        childAt = getChildAt(i3);
                        childAt2 = null;
                        childAt3 = null;
                        i = 0;
                        break;
                    }
                    i = 0;
                    childAt = null;
                    childAt2 = null;
                    childAt3 = null;
                    break;
                case 6:
                    if (this.mNextSelectedPosition >= 0) {
                        i = this.mNextSelectedPosition - this.mSelectedPosition;
                    }
                    childAt = null;
                    childAt2 = null;
                    childAt3 = null;
                    break;
                default:
                    int i4 = this.mSelectedPosition - this.mFirstPosition;
                    childAt2 = (i4 < 0 || i4 >= childCount) ? null : getChildAt(i4);
                    childAt3 = getChildAt(0);
                    childAt = null;
                    i = 0;
                    break;
            }
            boolean z4 = this.mDataChanged;
            if (z4) {
                handleDataChanged();
            }
            if (this.mItemCount == 0) {
                resetList();
                invokeOnItemScrollListener();
                if (z3) {
                    return;
                }
                this.mBlockLayoutRequests = false;
                return;
            }
            setSelectedPositionInt(this.mNextSelectedPosition);
            ViewRootImpl viewRootImpl = getViewRootImpl();
            try {
                if (viewRootImpl != null && (accessibilityFocusedHost = viewRootImpl.getAccessibilityFocusedHost()) != null) {
                    View accessibilityFocusedChild = getAccessibilityFocusedChild(accessibilityFocusedHost);
                    z2 = accessibilityFocusedHost != accessibilityFocusedChild;
                    if (accessibilityFocusedChild != null) {
                        if (!z4 || accessibilityFocusedChild.hasTransientState() || this.mAdapterHasStableIds) {
                            accessibilityFocusedVirtualView = viewRootImpl.getAccessibilityFocusedVirtualView();
                        } else {
                            accessibilityFocusedVirtualView = null;
                            accessibilityFocusedHost = null;
                        }
                        positionForView = getPositionForView(accessibilityFocusedChild);
                    }
                    int i5 = this.mFirstPosition;
                    AbsListView.RecycleBin recycleBin = this.mRecycler;
                    if (z4) {
                        z = z3;
                        accessibilityNodeInfo = accessibilityFocusedVirtualView;
                        recycleBin.fillActiveViews(childCount, i5);
                    } else {
                        int i6 = 0;
                        while (i6 < childCount) {
                            boolean z5 = z3;
                            recycleBin.addScrapView(getChildAt(i6), i5 + i6);
                            i6++;
                            z3 = z5;
                            accessibilityFocusedVirtualView = accessibilityFocusedVirtualView;
                        }
                        z = z3;
                        accessibilityNodeInfo = accessibilityFocusedVirtualView;
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
                            if (childAt != null) {
                                viewFillFromTop = fillFromSelection(childAt.getTop(), top, i2);
                                break;
                            } else {
                                viewFillFromTop = fillSelection(top, i2);
                                break;
                            }
                        case 3:
                            viewFillFromTop = fillUp(this.mItemCount - 1, i2);
                            adjustViewsUpOrDown();
                            break;
                        case 4:
                            viewFillFromTop = fillSpecific(this.mSelectedPosition, this.mSpecificTop);
                            break;
                        case 5:
                            viewFillFromTop = fillSpecific(this.mSyncPosition, this.mSpecificTop);
                            break;
                        case 6:
                            viewFillFromTop = moveSelection(i, top, i2);
                            break;
                        default:
                            if (childCount == 0) {
                                if (!this.mStackFromBottom) {
                                    setSelectedPositionInt((this.mAdapter == null || isInTouchMode()) ? -1 : 0);
                                    viewFillFromTop = fillFromTop(top);
                                    break;
                                } else {
                                    int i7 = this.mItemCount - 1;
                                    setSelectedPositionInt((this.mAdapter == null || isInTouchMode()) ? -1 : i7);
                                    viewFillFromTop = fillFromBottom(i7, i2);
                                    break;
                                }
                            } else if (this.mSelectedPosition >= 0 && this.mSelectedPosition < this.mItemCount) {
                                int i8 = this.mSelectedPosition;
                                if (childAt2 != null) {
                                    top = childAt2.getTop();
                                }
                                viewFillFromTop = fillSpecific(i8, top);
                                break;
                            } else if (this.mFirstPosition < this.mItemCount) {
                                int i9 = this.mFirstPosition;
                                if (childAt3 != null) {
                                    top = childAt3.getTop();
                                }
                                viewFillFromTop = fillSpecific(i9, top);
                                break;
                            } else {
                                viewFillFromTop = fillSpecific(0, top);
                                break;
                            }
                            break;
                    }
                    recycleBin.scrapActiveViews();
                    if (viewFillFromTop == null) {
                        positionSelector(-1, viewFillFromTop);
                        this.mSelectedTop = viewFillFromTop.getTop();
                    } else if (this.mTouchMode > 0 && this.mTouchMode < 3) {
                        View childAt6 = getChildAt(this.mMotionPosition - this.mFirstPosition);
                        if (childAt6 != null) {
                            positionSelector(this.mMotionPosition, childAt6);
                        }
                    } else if (this.mSelectedPosition != -1 && !this.mIsHoveredByMouse) {
                        View childAt7 = getChildAt(this.mSelectorPosition - this.mFirstPosition);
                        if (childAt7 != null) {
                            positionSelector(this.mSelectorPosition, childAt7);
                        }
                    } else {
                        this.mSelectedTop = 0;
                        this.mSelectorRect.setEmpty();
                    }
                    if (viewRootImpl != null) {
                        View accessibilityFocusedHost2 = viewRootImpl.getAccessibilityFocusedHost();
                        if (accessibilityFocusedHost2 == null) {
                            if (accessibilityFocusedHost != null && accessibilityFocusedHost.isAttachedToWindow()) {
                                AccessibilityNodeProvider accessibilityNodeProvider = accessibilityFocusedHost.getAccessibilityNodeProvider();
                                if (accessibilityNodeInfo != null && accessibilityNodeProvider != null) {
                                    accessibilityNodeProvider.performAction(AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getSourceNodeId()), 64, null);
                                } else {
                                    accessibilityFocusedHost.requestAccessibilityFocus();
                                }
                            } else if (positionForView != -1 && (childAt5 = getChildAt(MathUtils.constrain(positionForView - this.mFirstPosition, 0, getChildCount() - 1))) != null) {
                                childAt5.requestAccessibilityFocus();
                            }
                        } else if (positionForView != -1) {
                            int iConstrain = MathUtils.constrain(positionForView - this.mFirstPosition, 0, getChildCount() - 1);
                            if (z2) {
                                childAt4 = getChildAt(iConstrain).findViewById(accessibilityFocusedHost2.getId());
                            } else {
                                childAt4 = getChildAt(iConstrain);
                            }
                            if (accessibilityFocusedHost2.isAccessibilityFocused() && accessibilityFocusedHost2 != childAt4) {
                                accessibilityFocusedHost2.clearAccessibilityFocus();
                                if (childAt4 != null) {
                                    childAt4.requestAccessibilityFocus();
                                }
                            }
                        }
                    }
                    this.mLayoutMode = 0;
                    this.mDataChanged = false;
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
                    if (z) {
                        this.mBlockLayoutRequests = false;
                        return;
                    }
                    return;
                }
                z2 = false;
                if (z4) {
                }
                detachAllViewsFromParent();
                recycleBin.removeSkippedScrap();
                switch (this.mLayoutMode) {
                }
                recycleBin.scrapActiveViews();
                if (viewFillFromTop == null) {
                }
                if (viewRootImpl != null) {
                }
                this.mLayoutMode = 0;
                this.mDataChanged = false;
                if (this.mPositionScrollAfterLayout != null) {
                }
                this.mNeedSync = false;
                setNextSelectedPositionInt(this.mSelectedPosition);
                updateScrollIndicators();
                if (this.mItemCount > 0) {
                }
                invokeOnItemScrollListener();
                if (z) {
                }
            } catch (Throwable th) {
                th = th;
                if (!z) {
                    this.mBlockLayoutRequests = false;
                }
                throw th;
            }
            positionForView = -1;
            accessibilityFocusedVirtualView = null;
            accessibilityFocusedHost = null;
            int i52 = this.mFirstPosition;
            AbsListView.RecycleBin recycleBin2 = this.mRecycler;
        } catch (Throwable th2) {
            th = th2;
            z = z3;
        }
    }

    private View makeAndAddView(int i, int i2, boolean z, int i3, boolean z2, int i4) throws Resources.NotFoundException {
        View activeView;
        if (!this.mDataChanged && (activeView = this.mRecycler.getActiveView(i)) != null) {
            setupChild(activeView, i, i2, z, i3, z2, true, i4);
            return activeView;
        }
        View viewObtainView = obtainView(i, this.mIsScrap);
        setupChild(viewObtainView, i, i2, z, i3, z2, this.mIsScrap[0], i4);
        return viewObtainView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setupChild(View view, int i, int i2, boolean z, int i3, boolean z2, boolean z3, int i4) throws Resources.NotFoundException {
        int i5;
        Trace.traceBegin(8L, "setupGridItem");
        boolean z4 = z2 && shouldShowSelector();
        boolean z5 = z4 != view.isSelected();
        int i6 = this.mTouchMode;
        boolean z6 = i6 > 0 && i6 < 3 && this.mMotionPosition == i;
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
            if (z4) {
                requestFocus();
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
        if (z3 && !layoutParams.forceAdd) {
            attachViewToParent(view, i4, layoutParams);
            if (!z3 || ((AbsListView.LayoutParams) view.getLayoutParams()).scrappedFromPosition != i) {
                view.jumpDrawablesToCurrentState();
            }
        } else {
            layoutParams.forceAdd = false;
            addViewInLayout(view, i4, layoutParams, true);
        }
        if (z8) {
            view.measure(ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(this.mColumnWidth, 1073741824), 0, layoutParams.width), ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), 0, layoutParams.height));
        } else {
            cleanupLayoutState(view);
        }
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i7 = z ? i2 : i2 - measuredHeight;
        int absoluteGravity = Gravity.getAbsoluteGravity(this.mGravity, getLayoutDirection()) & 7;
        if (absoluteGravity == 1) {
            i5 = i3 + ((this.mColumnWidth - measuredWidth) / 2);
        } else {
            i5 = absoluteGravity != 5 ? i3 : (i3 + this.mColumnWidth) - measuredWidth;
        }
        if (z8) {
            view.layout(i5, i7, measuredWidth + i5, measuredHeight + i7);
        } else {
            view.offsetLeftAndRight(i5 - view.getLeft());
            view.offsetTopAndBottom(i7 - view.getTop());
        }
        if (this.mCachingStarted && !view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }
        Trace.traceEnd(8L);
    }

    @Override // android.widget.AdapterView
    public void setSelection(int i) {
        if (!isInTouchMode()) {
            setNextSelectedPositionInt(i);
        } else {
            this.mResurrectToPosition = i;
        }
        this.mLayoutMode = 2;
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        requestLayout();
        semShowGoToTOP();
    }

    @Override // android.widget.AbsListView
    void setSelectionInt(int i) throws Throwable {
        int i2 = this.mNextSelectedPosition;
        int i3 = this.mFirstPosition;
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        setNextSelectedPositionInt(i);
        layoutChildren();
        int i4 = this.mStackFromBottom ? (this.mItemCount - 1) - this.mNextSelectedPosition : this.mNextSelectedPosition;
        if (this.mStackFromBottom) {
            i2 = (this.mItemCount - 1) - i2;
        }
        int i5 = this.mNumColumns;
        int i6 = i4 / i5;
        int i7 = i2 / i5;
        if (i3 != this.mFirstPosition) {
            semShowGoToTOP();
        }
        if (i6 != i7) {
            awakenScrollBars();
        }
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

    /* JADX WARN: Removed duplicated region for block: B:50:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean commonKey(int i, int i2, KeyEvent keyEvent) throws Throwable {
        boolean zResurrectSelectionIfNeeded;
        if (this.mAdapter == null) {
            return false;
        }
        if (this.mDataChanged) {
            layoutChildren();
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
        if (!zResurrectSelectionIfNeeded && action != 1) {
            if (i != 61) {
                if (i != 92) {
                    if (i != 93) {
                        if (i != 122) {
                            if (i != 123) {
                                switch (i) {
                                    case 19:
                                        if (keyEvent.hasNoModifiers() || keyEvent.hasModifiers(1)) {
                                            this.mSemCurrentFocusPosition = this.mSelectedPosition;
                                            if (!resurrectSelectionIfNeeded() && !arrowScroll(33)) {
                                                zResurrectSelectionIfNeeded = false;
                                                break;
                                            } else {
                                                zResurrectSelectionIfNeeded = true;
                                                break;
                                            }
                                        } else if (keyEvent.hasModifiers(2)) {
                                            if (resurrectSelectionIfNeeded() || fullScroll(33)) {
                                            }
                                        }
                                        break;
                                    case 20:
                                        if (keyEvent.hasNoModifiers() || keyEvent.hasModifiers(1)) {
                                            this.mSemCurrentFocusPosition = this.mSelectedPosition;
                                            if (resurrectSelectionIfNeeded() || arrowScroll(130)) {
                                            }
                                        } else if (keyEvent.hasModifiers(2)) {
                                            if (resurrectSelectionIfNeeded() || fullScroll(130)) {
                                            }
                                        }
                                        break;
                                    case 21:
                                        if (!this.mSelectZeroPositionOnKeyTab) {
                                            if (keyEvent.hasNoModifiers() || keyEvent.hasModifiers(1)) {
                                                this.mSemCurrentFocusPosition = this.mSelectedPosition;
                                                if (resurrectSelectionIfNeeded() || arrowScroll(17)) {
                                                }
                                            }
                                        }
                                        break;
                                    case 22:
                                        if (!this.mSelectZeroPositionOnKeyTab) {
                                            if (keyEvent.hasNoModifiers() || keyEvent.hasModifiers(1)) {
                                                this.mSemCurrentFocusPosition = this.mSelectedPosition;
                                                if (resurrectSelectionIfNeeded() || arrowScroll(66)) {
                                                }
                                            }
                                        }
                                        break;
                                }
                            } else if (keyEvent.hasNoModifiers()) {
                                if (resurrectSelectionIfNeeded() || fullScroll(130)) {
                                }
                            }
                        } else if (keyEvent.hasNoModifiers()) {
                            if (resurrectSelectionIfNeeded() || fullScroll(33)) {
                            }
                        }
                    } else if (keyEvent.hasNoModifiers()) {
                        if (resurrectSelectionIfNeeded() || pageScroll(130)) {
                        }
                    } else if (keyEvent.hasModifiers(2)) {
                        if (resurrectSelectionIfNeeded() || fullScroll(130)) {
                        }
                    }
                } else if (keyEvent.hasNoModifiers()) {
                    if (resurrectSelectionIfNeeded() || pageScroll(33)) {
                    }
                } else if (keyEvent.hasModifiers(2)) {
                    if (resurrectSelectionIfNeeded() || fullScroll(33)) {
                    }
                }
            } else {
                if (this.mSelectZeroPositionOnKeyTab && getSelectedItemPosition() == getCount() - 1) {
                    setSelection(0);
                    return true;
                }
                if (keyEvent.hasNoModifiers()) {
                    if (resurrectSelectionIfNeeded() || sequenceScroll(2)) {
                    }
                } else if (keyEvent.hasModifiers(1)) {
                    if (resurrectSelectionIfNeeded() || sequenceScroll(1)) {
                    }
                }
            }
        }
        if (zResurrectSelectionIfNeeded || sendToTextFilter(i, i2, keyEvent)) {
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
        return super.onKeyMultiple(i, i2, keyEvent);
    }

    boolean pageScroll(int i) throws Throwable {
        int iMin;
        if (i == 33) {
            iMin = Math.max(0, this.mSelectedPosition - getChildCount());
        } else {
            iMin = i == 130 ? Math.min(this.mItemCount - 1, this.mSelectedPosition + getChildCount()) : -1;
        }
        if (iMin < 0) {
            return false;
        }
        setSelectionInt(iMin);
        invokeOnItemScrollListener();
        awakenScrollBars();
        return true;
    }

    boolean fullScroll(int i) throws Throwable {
        boolean z = true;
        if (i == 33) {
            this.mLayoutMode = 2;
            setSelectionInt(0);
            invokeOnItemScrollListener();
        } else if (i == 130) {
            this.mLayoutMode = 2;
            setSelectionInt(this.mItemCount - 1);
            invokeOnItemScrollListener();
        } else {
            z = false;
        }
        if (z) {
            awakenScrollBars();
        }
        return z;
    }

    boolean arrowScroll(int i) throws Throwable {
        int iMin;
        int iMax;
        boolean z;
        int i2 = this.mSelectedPosition;
        int i3 = this.mNumColumns;
        boolean z2 = true;
        if (!this.mStackFromBottom) {
            iMax = (i2 / i3) * i3;
            iMin = Math.min((iMax + i3) - 1, this.mItemCount - 1);
        } else {
            iMin = (this.mItemCount - 1) - ((((this.mItemCount - 1) - i2) / i3) * i3);
            iMax = Math.max(0, (iMin - i3) + 1);
        }
        if (i != 33) {
            if (i == 130 && iMin < this.mItemCount - 1) {
                this.mLayoutMode = 6;
                setSelectionInt(Math.min(i3 + i2, this.mItemCount - 1));
                z = true;
            }
            z = false;
        } else {
            if (iMax > 0) {
                this.mLayoutMode = 6;
                setSelectionInt(Math.max(0, i2 - i3));
                z = true;
            }
            z = false;
        }
        boolean zIsLayoutRtl = isLayoutRtl();
        if (i2 > iMax && ((i == 17 && !zIsLayoutRtl) || (i == 66 && zIsLayoutRtl))) {
            this.mLayoutMode = 6;
            setSelectionInt(Math.max(0, i2 - 1));
        } else if (i2 >= iMin || (!(i == 17 && zIsLayoutRtl) && (i != 66 || zIsLayoutRtl))) {
            z2 = z;
        } else {
            this.mLayoutMode = 6;
            setSelectionInt(Math.min(i2 + 1, this.mItemCount - 1));
        }
        if (z2) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
            invokeOnItemScrollListener();
        }
        if (z2) {
            awakenScrollBars();
        }
        return z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean sequenceScroll(int i) throws Throwable {
        int iMax;
        int iMin;
        boolean z;
        int i2 = this.mSelectedPosition;
        int i3 = this.mNumColumns;
        int i4 = this.mItemCount;
        boolean z2 = false;
        if (!this.mStackFromBottom) {
            iMax = (i2 / i3) * i3;
            iMin = Math.min((i3 + iMax) - 1, i4 - 1);
        } else {
            int i5 = i4 - 1;
            int i6 = i5 - (((i5 - i2) / i3) * i3);
            iMax = Math.max(0, (i6 - i3) + 1);
            iMin = i6;
        }
        if (i != 1) {
            if (i == 2 && i2 < i4 - 1) {
                this.mLayoutMode = 6;
                setSelectionInt(i2 + 1);
                if (i2 == iMin) {
                    z2 = true;
                }
                z = z2;
                z2 = true;
            }
            z = false;
        } else {
            if (i2 > 0) {
                this.mLayoutMode = 6;
                setSelectionInt(i2 - 1);
                if (i2 == iMax) {
                }
                z = z2;
                z2 = true;
            }
            z = false;
        }
        if (z2) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
            invokeOnItemScrollListener();
        }
        if (z) {
            awakenScrollBars();
        }
        return z2;
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) throws Resources.NotFoundException {
        super.onFocusChanged(z, i, rect);
        int i2 = -1;
        if (z && rect != null) {
            rect.offset(this.mScrollX, this.mScrollY);
            Rect rect2 = this.mTempRect;
            int childCount = getChildCount();
            int i3 = Integer.MAX_VALUE;
            for (int i4 = 0; i4 < childCount; i4++) {
                if (isCandidateSelection(i4, i)) {
                    View childAt = getChildAt(i4);
                    childAt.getDrawingRect(rect2);
                    offsetDescendantRectToMyCoords(childAt, rect2);
                    int distance = getDistance(rect, rect2, i);
                    if (distance < i3) {
                        i2 = i4;
                        i3 = distance;
                    }
                }
            }
        }
        if (i2 >= 0) {
            setSelection(i2 + this.mFirstPosition);
        } else {
            requestLayout();
        }
        if (!z || this.mDndGridAnimator == null) {
            return;
        }
        post(new Runnable() { // from class: android.widget.GridView.3
            @Override // java.lang.Runnable
            public void run() {
                GridView.this.mDndGridAnimator.speakDescriptionForAccessibility();
            }
        });
    }

    private boolean isCandidateSelection(int i, int i2) {
        int iMax;
        int iMin;
        int childCount = getChildCount();
        int i3 = childCount - 1;
        int i4 = i3 - i;
        if (!this.mStackFromBottom) {
            int i5 = this.mNumColumns;
            iMax = i - (i % i5);
            iMin = Math.min((i5 + iMax) - 1, childCount);
        } else {
            int i6 = this.mNumColumns;
            int i7 = i3 - (i4 - (i4 % i6));
            iMax = Math.max(0, (i7 - i6) + 1);
            iMin = i7;
        }
        if (i2 == 1) {
            return i == iMin && iMin == i3;
        }
        if (i2 == 2) {
            return i == iMax && iMax == 0;
        }
        if (i2 == 17) {
            return i == iMin;
        }
        if (i2 == 33) {
            return iMin == i3;
        }
        if (i2 == 66) {
            return i == iMax;
        }
        if (i2 == 130) {
            return iMax == 0;
        }
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
    }

    @RemotableViewMethod
    public void setGravity(int i) {
        if (this.mGravity != i) {
            this.mGravity = i;
            requestLayoutIfNecessary();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    @RemotableViewMethod
    public void setHorizontalSpacing(int i) {
        if (i != this.mRequestedHorizontalSpacing) {
            this.mRequestedHorizontalSpacing = i;
            requestLayoutIfNecessary();
        }
    }

    public int getHorizontalSpacing() {
        return this.mHorizontalSpacing;
    }

    public int getRequestedHorizontalSpacing() {
        return this.mRequestedHorizontalSpacing;
    }

    @RemotableViewMethod
    public void setVerticalSpacing(int i) {
        if (i != this.mVerticalSpacing) {
            this.mVerticalSpacing = i;
            requestLayoutIfNecessary();
        }
    }

    public int getVerticalSpacing() {
        return this.mVerticalSpacing;
    }

    @RemotableViewMethod
    public void setStretchMode(int i) {
        if (i != this.mStretchMode) {
            this.mStretchMode = i;
            requestLayoutIfNecessary();
        }
    }

    public int getStretchMode() {
        return this.mStretchMode;
    }

    @RemotableViewMethod
    public void setColumnWidth(int i) {
        if (i != this.mRequestedColumnWidth) {
            this.mRequestedColumnWidth = i;
            requestLayoutIfNecessary();
        }
    }

    public int getColumnWidth() {
        return this.mColumnWidth;
    }

    public int getRequestedColumnWidth() {
        return this.mRequestedColumnWidth;
    }

    @RemotableViewMethod
    public void setNumColumns(int i) {
        if (i != this.mRequestedNumColumns) {
            this.mRequestedNumColumns = i;
            requestLayoutIfNecessary();
        }
    }

    @ViewDebug.ExportedProperty
    public int getNumColumns() {
        return this.mNumColumns;
    }

    private void adjustViewsUpOrDown() {
        int childCount = getChildCount();
        if (childCount > 0) {
            int i = 0;
            if (!this.mStackFromBottom) {
                int top = getChildAt(0).getTop() - this.mListPadding.top;
                if (this.mFirstPosition != 0) {
                    top -= this.mVerticalSpacing;
                }
                if (top >= 0) {
                    i = top;
                }
            } else {
                int bottom = getChildAt(childCount - 1).getBottom() - (getHeight() - this.mListPadding.bottom);
                if (this.mFirstPosition + childCount < this.mItemCount) {
                    bottom += this.mVerticalSpacing;
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

    @Override // android.widget.AbsListView, android.view.View
    protected int computeVerticalScrollExtent() {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return 0;
        }
        int i = (((childCount + r2) - 1) / this.mNumColumns) * 100;
        View childAt = getChildAt(0);
        int top = childAt.getTop();
        int height = childAt.getHeight() + this.mVerticalSpacing;
        if (height > 0) {
            i += (top * 100) / height;
        }
        View childAt2 = getChildAt(childCount - 1);
        int bottom = childAt2.getBottom();
        int height2 = childAt2.getHeight() + this.mVerticalSpacing;
        return height2 > 0 ? i - (((bottom - getHeight()) * 100) / height2) : i;
    }

    @Override // android.widget.AbsListView, android.view.View
    protected int computeVerticalScrollOffset() {
        if (this.mFirstPosition >= 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            int top = childAt.getTop();
            int height = childAt.getHeight() + this.mVerticalSpacing;
            if (height > 0) {
                int i = this.mNumColumns;
                int i2 = ((this.mItemCount + i) - 1) / i;
                return Math.max(((((this.mFirstPosition + (isStackFromBottom() ? (i2 * i) - this.mItemCount : 0)) / i) * 100) - ((top * 100) / height)) + ((int) ((this.mScrollY / getHeight()) * i2 * 100.0f)), 0);
            }
        }
        return 0;
    }

    @Override // android.widget.AbsListView, android.view.View
    protected int computeVerticalScrollRange() {
        int i = ((this.mItemCount + r0) - 1) / this.mNumColumns;
        int iMax = Math.max(i * 100, 0);
        return this.mScrollY != 0 ? iMax + Math.abs((int) ((this.mScrollY / getHeight()) * i * 100.0f)) : iMax;
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return GridView.class.getName();
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        int numColumns = getNumColumns();
        int count = getCount() / numColumns;
        accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(count, numColumns, false, getSelectionModeForAccessibility()));
        if (numColumns > 0 || count > 0) {
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
        int numColumns = getNumColumns();
        int i2 = bundle.getInt(AccessibilityNodeInfo.ACTION_ARGUMENT_ROW_INT, -1);
        int iMin = Math.min(numColumns * i2, getCount() - 1);
        if (i2 < 0) {
            return false;
        }
        smoothScrollToPosition(iMin);
        return true;
    }

    @Override // android.widget.AbsListView
    public void onInitializeAccessibilityNodeInfoForItem(View view, int i, AccessibilityNodeInfo accessibilityNodeInfo) {
        int i2;
        int i3;
        super.onInitializeAccessibilityNodeInfoForItem(view, i, accessibilityNodeInfo);
        int count = getCount();
        int numColumns = getNumColumns();
        int i4 = count / numColumns;
        if (!this.mStackFromBottom) {
            int i5 = i % numColumns;
            i2 = i / numColumns;
            i3 = i5;
        } else {
            int i6 = (count - 1) - i;
            int i7 = (numColumns - 1) - (i6 % numColumns);
            i2 = (i4 - 1) - (i6 / numColumns);
            i3 = i7;
        }
        int i8 = i2;
        AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) view.getLayoutParams();
        accessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(i8, 1, i3, 1, layoutParams != null && layoutParams.viewType == -2, isItemChecked(i)));
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws Resources.NotFoundException, IOException {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("numColumns", getNumColumns());
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetAppWidgetEnabled(boolean z) {
        super.semSetAppWidgetEnabled(z);
    }

    @Override // android.widget.AbsListView
    @RemotableViewMethod
    public void semSetGoToTopEnabledForAppWidget(boolean z) throws Resources.NotFoundException {
        super.semSetGoToTopEnabledForAppWidget(z);
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
}

package android.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Trace;
import android.util.AttributeSet;
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
import android.view.accessibility.AccessibilityNodeInfo;
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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.GridView, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.GridView, attributeSet, obtainStyledAttributes, i, i2);
        setHorizontalSpacing(obtainStyledAttributes.getDimensionPixelOffset(1, 0));
        setVerticalSpacing(obtainStyledAttributes.getDimensionPixelOffset(2, 0));
        int i3 = obtainStyledAttributes.getInt(3, 2);
        if (i3 >= 0) {
            setStretchMode(i3);
        }
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(4, -1);
        if (dimensionPixelOffset > 0) {
            setColumnWidth(dimensionPixelOffset);
        }
        setNumColumns(obtainStyledAttributes.getInt(5, 1));
        int i4 = obtainStyledAttributes.getInt(0, -1);
        if (i4 >= 0) {
            setGravity(i4);
        }
        obtainStyledAttributes.recycle();
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
    public void setRemoteViewsAdapter(Intent intent) {
        super.setRemoteViewsAdapter(intent);
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView
    public void setAdapter(ListAdapter listAdapter) {
        int lookForSelectablePosition;
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
                lookForSelectablePosition = lookForSelectablePosition(this.mItemCount - 1, false);
            } else {
                lookForSelectablePosition = lookForSelectablePosition(0, true);
            }
            setSelectedPositionInt(lookForSelectablePosition);
            setNextSelectedPositionInt(lookForSelectablePosition);
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
    void fillGap(boolean z) {
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

    private View fillDown(int i, int i2) {
        int i3 = this.mBottom - this.mTop;
        View view = null;
        if ((this.mGroupFlags & 34) == 34) {
            i3 -= this.mListPadding.bottom;
        }
        while (i2 < i3 && i < this.mItemCount) {
            View makeRow = makeRow(i, i2, true);
            if (makeRow != null) {
                view = makeRow;
            }
            i2 = this.mReferenceView.getBottom() + this.mVerticalSpacing;
            i += this.mNumColumns;
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return view;
    }

    private View makeRow(int i, int i2, boolean z) {
        int i3;
        int i4;
        int i5;
        int i6 = this.mColumnWidth;
        int i7 = this.mHorizontalSpacing;
        boolean isLayoutRtl = isLayoutRtl();
        if (isLayoutRtl) {
            i3 = ((getWidth() - this.mListPadding.right) - i6) - (this.mStretchMode == 3 ? i7 : 0);
        } else {
            i3 = this.mListPadding.left + (this.mStretchMode == 3 ? i7 : 0);
        }
        if (!this.mStackFromBottom) {
            i5 = i;
            i4 = Math.min(i + this.mNumColumns, this.mItemCount);
        } else {
            int i8 = i + 1;
            int max = Math.max(0, (i - this.mNumColumns) + 1);
            int i9 = i8 - max;
            int i10 = this.mNumColumns;
            if (i9 < i10) {
                i3 += (isLayoutRtl ? -1 : 1) * (i10 - i9) * (i6 + i7);
            }
            i4 = i8;
            i5 = max;
        }
        boolean shouldShowSelector = shouldShowSelector();
        boolean z2 = touchModeDrawsInPressedState();
        int i11 = this.mSelectedPosition;
        int i12 = isLayoutRtl ? -1 : 1;
        View view = null;
        View view2 = null;
        int i13 = i3;
        int i14 = i5;
        while (i14 < i4) {
            boolean z3 = i14 == i11;
            int i15 = i11;
            int i16 = i14;
            View makeAndAddView = makeAndAddView(i16, i2, z, i13, z3, z ? -1 : i14 - i5);
            i13 += i12 * i6;
            if (i16 < i4 - 1) {
                i13 += i12 * i7;
            }
            if (z3 && (shouldShowSelector || z2)) {
                view2 = makeAndAddView;
            }
            i14 = i16 + 1;
            view = makeAndAddView;
            i11 = i15;
        }
        this.mReferenceView = view;
        if (view2 != null) {
            this.mReferenceViewInSelectedRow = view;
        }
        return view2;
    }

    private View fillUp(int i, int i2) {
        View view = null;
        int i3 = (this.mGroupFlags & 34) == 34 ? this.mListPadding.top : 0;
        while (i2 > i3 && i >= 0) {
            View makeRow = makeRow(i, i2, false);
            if (makeRow != null) {
                view = makeRow;
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
        int min = (this.mItemCount - 1) - Math.min(Math.max(i, this.mSelectedPosition), this.mItemCount - 1);
        return fillUp((this.mItemCount - 1) - (min - (min % this.mNumColumns)), i2);
    }

    private View fillSelection(int i, int i2) {
        int i3;
        int max;
        int reconcileSelectedPosition = reconcileSelectedPosition();
        int i4 = this.mNumColumns;
        int i5 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            max = reconcileSelectedPosition - (reconcileSelectedPosition % i4);
            i3 = -1;
        } else {
            int i6 = (this.mItemCount - 1) - reconcileSelectedPosition;
            i3 = (this.mItemCount - 1) - (i6 - (i6 % i4));
            max = Math.max(0, (i3 - i4) + 1);
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        View makeRow = makeRow(this.mStackFromBottom ? i3 : max, getTopSelectionPixel(i, verticalFadingEdgeLength, max), true);
        this.mFirstPosition = max;
        View view = this.mReferenceView;
        if (!this.mStackFromBottom) {
            fillDown(max + i4, view.getBottom() + i5);
            pinToBottom(i2);
            fillUp(max - i4, view.getTop() - i5);
            adjustViewsUpOrDown();
            return makeRow;
        }
        offsetChildrenTopAndBottom(getBottomSelectionPixel(i2, verticalFadingEdgeLength, i4, max) - view.getBottom());
        fillUp(max - 1, view.getTop() - i5);
        pinToTop(i);
        fillDown(i3 + i4, view.getBottom() + i5);
        adjustViewsUpOrDown();
        return makeRow;
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

    private View fillSpecific(int i, int i2) {
        int i3;
        int max;
        View view;
        View view2;
        int i4 = this.mNumColumns;
        if (!this.mStackFromBottom) {
            max = i - (i % i4);
            i3 = -1;
        } else {
            int i5 = (this.mItemCount - 1) - i;
            i3 = (this.mItemCount - 1) - (i5 - (i5 % i4));
            max = Math.max(0, (i3 - i4) + 1);
        }
        View makeRow = makeRow(this.mStackFromBottom ? i3 : max, i2, true);
        this.mFirstPosition = max;
        View view3 = this.mReferenceView;
        if (view3 == null) {
            return null;
        }
        int i6 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            view = fillUp(max - i4, view3.getTop() - i6);
            adjustViewsUpOrDown();
            view2 = fillDown(max + i4, view3.getBottom() + i6);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHigh(i4, i6, childCount);
            }
        } else {
            View fillDown = fillDown(i3 + i4, view3.getBottom() + i6);
            adjustViewsUpOrDown();
            View fillUp = fillUp(max - 1, view3.getTop() - i6);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLow(i4, i6, childCount2);
            }
            view = fillUp;
            view2 = fillDown;
        }
        return makeRow != null ? makeRow : view != null ? view : view2;
    }

    private void correctTooHigh(int i, int i2, int i3) {
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

    private void correctTooLow(int i, int i2, int i3) {
        if (this.mFirstPosition != 0 || i3 <= 0) {
            return;
        }
        int top = getChildAt(0).getTop();
        int i4 = this.mListPadding.top;
        int i5 = (this.mBottom - this.mTop) - this.mListPadding.bottom;
        int i6 = top - i4;
        View childAt = getChildAt(i3 - 1);
        int bottom = childAt.getBottom();
        int i7 = (this.mFirstPosition + i3) - 1;
        if (i6 > 0) {
            if (i7 < this.mItemCount - 1 || bottom > i5) {
                if (i7 == this.mItemCount - 1) {
                    i6 = Math.min(i6, bottom - i5);
                }
                offsetChildrenTopAndBottom(-i6);
                if (i7 < this.mItemCount - 1) {
                    if (!this.mStackFromBottom) {
                        i = 1;
                    }
                    fillDown(i7 + i, childAt.getBottom() + i2);
                    adjustViewsUpOrDown();
                }
            }
        }
    }

    private View fillFromSelection(int i, int i2, int i3) {
        int i4;
        int max;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int i5 = this.mSelectedPosition;
        int i6 = this.mNumColumns;
        int i7 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            max = i5 - (i5 % i6);
            i4 = -1;
        } else {
            int i8 = (this.mItemCount - 1) - i5;
            i4 = (this.mItemCount - 1) - (i8 - (i8 % i6));
            max = Math.max(0, (i4 - i6) + 1);
        }
        int topSelectionPixel = getTopSelectionPixel(i2, verticalFadingEdgeLength, max);
        int bottomSelectionPixel = getBottomSelectionPixel(i3, verticalFadingEdgeLength, i6, max);
        View makeRow = makeRow(this.mStackFromBottom ? i4 : max, i, true);
        this.mFirstPosition = max;
        View view = this.mReferenceView;
        adjustForTopFadingEdge(view, topSelectionPixel, bottomSelectionPixel);
        adjustForBottomFadingEdge(view, topSelectionPixel, bottomSelectionPixel);
        if (!this.mStackFromBottom) {
            fillUp(max - i6, view.getTop() - i7);
            adjustViewsUpOrDown();
            fillDown(max + i6, view.getBottom() + i7);
            return makeRow;
        }
        fillDown(i4 + i6, view.getBottom() + i7);
        adjustViewsUpOrDown();
        fillUp(max - 1, view.getTop() - i7);
        return makeRow;
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

    private View moveSelection(int i, int i2, int i3) {
        int i4;
        int max;
        int i5;
        View makeRow;
        View view;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int i6 = this.mSelectedPosition;
        int i7 = this.mNumColumns;
        int i8 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            int i9 = i6 - i;
            max = i9 - (i9 % i7);
            i5 = i6 - (i6 % i7);
            i4 = -1;
        } else {
            int i10 = (this.mItemCount - 1) - i6;
            i4 = (this.mItemCount - 1) - (i10 - (i10 % i7));
            int max2 = Math.max(0, (i4 - i7) + 1);
            int i11 = (this.mItemCount - 1) - (i6 - i);
            max = Math.max(0, (((this.mItemCount - 1) - (i11 - (i11 % i7))) - i7) + 1);
            i5 = max2;
        }
        int i12 = i5 - max;
        int topSelectionPixel = getTopSelectionPixel(i2, verticalFadingEdgeLength, i5);
        int bottomSelectionPixel = getBottomSelectionPixel(i3, verticalFadingEdgeLength, i7, i5);
        this.mFirstPosition = i5;
        if (i12 > 0) {
            View view2 = this.mReferenceViewInSelectedRow;
            makeRow = makeRow(this.mStackFromBottom ? i4 : i5, (view2 != null ? view2.getBottom() : 0) + i8, true);
            view = this.mReferenceView;
            adjustForBottomFadingEdge(view, topSelectionPixel, bottomSelectionPixel);
        } else if (i12 < 0) {
            View view3 = this.mReferenceViewInSelectedRow;
            makeRow = makeRow(this.mStackFromBottom ? i4 : i5, (view3 == null ? 0 : view3.getTop()) - i8, false);
            view = this.mReferenceView;
            adjustForTopFadingEdge(view, topSelectionPixel, bottomSelectionPixel);
        } else {
            View view4 = this.mReferenceViewInSelectedRow;
            makeRow = makeRow(this.mStackFromBottom ? i4 : i5, view4 != null ? view4.getTop() : 0, true);
            view = this.mReferenceView;
        }
        if (!this.mStackFromBottom) {
            fillUp(i5 - i7, view.getTop() - i8);
            adjustViewsUpOrDown();
            fillDown(i5 + i7, view.getBottom() + i8);
            return makeRow;
        }
        fillDown(i4 + i7, view.getBottom() + i8);
        adjustViewsUpOrDown();
        fillUp(i5 - 1, view.getTop() - i8);
        return makeRow;
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
        int i3;
        int i4;
        int i5;
        int i6;
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode == 0) {
            int i7 = this.mColumnWidth;
            if (i7 > 0) {
                i5 = i7 + this.mListPadding.left;
                i6 = this.mListPadding.right;
            } else {
                i5 = this.mListPadding.left;
                i6 = this.mListPadding.right;
            }
            size = i5 + i6 + getVerticalScrollbarWidth();
        }
        boolean determineColumns = determineColumns((size - this.mListPadding.left) - this.mListPadding.right);
        int i8 = 0;
        this.mItemCount = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        int i9 = this.mItemCount;
        if (i9 > 0) {
            View obtainView = obtainView(0, this.mIsScrap);
            AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) obtainView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = (AbsListView.LayoutParams) generateDefaultLayoutParams();
                obtainView.setLayoutParams(layoutParams);
            }
            layoutParams.viewType = this.mAdapter.getItemViewType(0);
            layoutParams.isEnabled = this.mAdapter.isEnabled(0);
            layoutParams.forceAdd = true;
            obtainView.measure(getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(this.mColumnWidth, 1073741824), 0, layoutParams.width), getChildMeasureSpec(View.MeasureSpec.makeSafeMeasureSpec(View.MeasureSpec.getSize(i2), 0), 0, layoutParams.height));
            i3 = obtainView.getMeasuredHeight();
            combineMeasuredStates(0, obtainView.getMeasuredState());
            if (this.mRecycler.shouldRecycleViewType(layoutParams.viewType)) {
                this.mRecycler.addScrapView(obtainView, -1);
            }
        } else {
            i3 = 0;
        }
        if (mode2 == 0) {
            size2 = this.mListPadding.top + this.mListPadding.bottom + i3 + (getVerticalFadingEdgeLength() * 2);
        }
        if (mode2 == Integer.MIN_VALUE) {
            int i10 = this.mListPadding.top + this.mListPadding.bottom;
            int i11 = this.mNumColumns;
            while (true) {
                if (i8 >= i9) {
                    size2 = i10;
                    break;
                }
                i10 += i3;
                i8 += i11;
                if (i8 < i9) {
                    i10 += this.mVerticalSpacing;
                }
                if (i10 >= size2) {
                    break;
                }
            }
        }
        if (mode == Integer.MIN_VALUE && (i4 = this.mRequestedNumColumns) != -1 && ((this.mColumnWidth * i4) + ((i4 - 1) * this.mHorizontalSpacing) + this.mListPadding.left + this.mListPadding.right > size || determineColumns)) {
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
        boolean drawChild = super.drawChild(canvas, view, j);
        SemDragAndDropGridAnimator semDragAndDropGridAnimator2 = this.mDndGridAnimator;
        if (semDragAndDropGridAnimator2 != null) {
            semDragAndDropGridAnimator2.postDrawChild(canvas, view, j);
        }
        return drawChild;
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
    public void onWindowFocusChanged(boolean z) {
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
    /* JADX WARN: Failed to find 'out' block for switch in B:15:0x0033. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0275 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:55:0x00cf, B:58:0x00ed, B:59:0x00f5, B:61:0x00fa, B:63:0x0144, B:65:0x0148, B:69:0x0152, B:70:0x01a6, B:72:0x01ab, B:74:0x01f4, B:77:0x01fc, B:79:0x0202, B:82:0x020a, B:83:0x0219, B:86:0x0220, B:88:0x0234, B:91:0x023b, B:93:0x024b, B:94:0x025c, B:97:0x0264, B:99:0x0269, B:100:0x0258, B:101:0x026c, B:103:0x0275, B:104:0x027d, B:106:0x028c, B:107:0x028f, B:113:0x01b6, B:115:0x01ba, B:117:0x01bf, B:119:0x01ca, B:120:0x01d0, B:122:0x01d5, B:124:0x01d9, B:126:0x01e4, B:127:0x01ea, B:129:0x015a, B:131:0x0162, B:135:0x016c, B:137:0x0174, B:139:0x0178, B:141:0x017e, B:144:0x0187, B:145:0x0183, B:146:0x018c, B:148:0x0192, B:151:0x019b, B:152:0x0197, B:153:0x01a0, B:154:0x00fd, B:155:0x0103, B:156:0x010d, B:157:0x0117, B:159:0x0126, B:160:0x0130, B:161:0x0136, B:162:0x00e6), top: B:50:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:106:0x028c A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:55:0x00cf, B:58:0x00ed, B:59:0x00f5, B:61:0x00fa, B:63:0x0144, B:65:0x0148, B:69:0x0152, B:70:0x01a6, B:72:0x01ab, B:74:0x01f4, B:77:0x01fc, B:79:0x0202, B:82:0x020a, B:83:0x0219, B:86:0x0220, B:88:0x0234, B:91:0x023b, B:93:0x024b, B:94:0x025c, B:97:0x0264, B:99:0x0269, B:100:0x0258, B:101:0x026c, B:103:0x0275, B:104:0x027d, B:106:0x028c, B:107:0x028f, B:113:0x01b6, B:115:0x01ba, B:117:0x01bf, B:119:0x01ca, B:120:0x01d0, B:122:0x01d5, B:124:0x01d9, B:126:0x01e4, B:127:0x01ea, B:129:0x015a, B:131:0x0162, B:135:0x016c, B:137:0x0174, B:139:0x0178, B:141:0x017e, B:144:0x0187, B:145:0x0183, B:146:0x018c, B:148:0x0192, B:151:0x019b, B:152:0x0197, B:153:0x01a0, B:154:0x00fd, B:155:0x0103, B:156:0x010d, B:157:0x0117, B:159:0x0126, B:160:0x0130, B:161:0x0136, B:162:0x00e6), top: B:50:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:111:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01b6 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:55:0x00cf, B:58:0x00ed, B:59:0x00f5, B:61:0x00fa, B:63:0x0144, B:65:0x0148, B:69:0x0152, B:70:0x01a6, B:72:0x01ab, B:74:0x01f4, B:77:0x01fc, B:79:0x0202, B:82:0x020a, B:83:0x0219, B:86:0x0220, B:88:0x0234, B:91:0x023b, B:93:0x024b, B:94:0x025c, B:97:0x0264, B:99:0x0269, B:100:0x0258, B:101:0x026c, B:103:0x0275, B:104:0x027d, B:106:0x028c, B:107:0x028f, B:113:0x01b6, B:115:0x01ba, B:117:0x01bf, B:119:0x01ca, B:120:0x01d0, B:122:0x01d5, B:124:0x01d9, B:126:0x01e4, B:127:0x01ea, B:129:0x015a, B:131:0x0162, B:135:0x016c, B:137:0x0174, B:139:0x0178, B:141:0x017e, B:144:0x0187, B:145:0x0183, B:146:0x018c, B:148:0x0192, B:151:0x019b, B:152:0x0197, B:153:0x01a0, B:154:0x00fd, B:155:0x0103, B:156:0x010d, B:157:0x0117, B:159:0x0126, B:160:0x0130, B:161:0x0136, B:162:0x00e6), top: B:50:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x00fd A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:55:0x00cf, B:58:0x00ed, B:59:0x00f5, B:61:0x00fa, B:63:0x0144, B:65:0x0148, B:69:0x0152, B:70:0x01a6, B:72:0x01ab, B:74:0x01f4, B:77:0x01fc, B:79:0x0202, B:82:0x020a, B:83:0x0219, B:86:0x0220, B:88:0x0234, B:91:0x023b, B:93:0x024b, B:94:0x025c, B:97:0x0264, B:99:0x0269, B:100:0x0258, B:101:0x026c, B:103:0x0275, B:104:0x027d, B:106:0x028c, B:107:0x028f, B:113:0x01b6, B:115:0x01ba, B:117:0x01bf, B:119:0x01ca, B:120:0x01d0, B:122:0x01d5, B:124:0x01d9, B:126:0x01e4, B:127:0x01ea, B:129:0x015a, B:131:0x0162, B:135:0x016c, B:137:0x0174, B:139:0x0178, B:141:0x017e, B:144:0x0187, B:145:0x0183, B:146:0x018c, B:148:0x0192, B:151:0x019b, B:152:0x0197, B:153:0x01a0, B:154:0x00fd, B:155:0x0103, B:156:0x010d, B:157:0x0117, B:159:0x0126, B:160:0x0130, B:161:0x0136, B:162:0x00e6), top: B:50:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0103 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:55:0x00cf, B:58:0x00ed, B:59:0x00f5, B:61:0x00fa, B:63:0x0144, B:65:0x0148, B:69:0x0152, B:70:0x01a6, B:72:0x01ab, B:74:0x01f4, B:77:0x01fc, B:79:0x0202, B:82:0x020a, B:83:0x0219, B:86:0x0220, B:88:0x0234, B:91:0x023b, B:93:0x024b, B:94:0x025c, B:97:0x0264, B:99:0x0269, B:100:0x0258, B:101:0x026c, B:103:0x0275, B:104:0x027d, B:106:0x028c, B:107:0x028f, B:113:0x01b6, B:115:0x01ba, B:117:0x01bf, B:119:0x01ca, B:120:0x01d0, B:122:0x01d5, B:124:0x01d9, B:126:0x01e4, B:127:0x01ea, B:129:0x015a, B:131:0x0162, B:135:0x016c, B:137:0x0174, B:139:0x0178, B:141:0x017e, B:144:0x0187, B:145:0x0183, B:146:0x018c, B:148:0x0192, B:151:0x019b, B:152:0x0197, B:153:0x01a0, B:154:0x00fd, B:155:0x0103, B:156:0x010d, B:157:0x0117, B:159:0x0126, B:160:0x0130, B:161:0x0136, B:162:0x00e6), top: B:50:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x010d A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:55:0x00cf, B:58:0x00ed, B:59:0x00f5, B:61:0x00fa, B:63:0x0144, B:65:0x0148, B:69:0x0152, B:70:0x01a6, B:72:0x01ab, B:74:0x01f4, B:77:0x01fc, B:79:0x0202, B:82:0x020a, B:83:0x0219, B:86:0x0220, B:88:0x0234, B:91:0x023b, B:93:0x024b, B:94:0x025c, B:97:0x0264, B:99:0x0269, B:100:0x0258, B:101:0x026c, B:103:0x0275, B:104:0x027d, B:106:0x028c, B:107:0x028f, B:113:0x01b6, B:115:0x01ba, B:117:0x01bf, B:119:0x01ca, B:120:0x01d0, B:122:0x01d5, B:124:0x01d9, B:126:0x01e4, B:127:0x01ea, B:129:0x015a, B:131:0x0162, B:135:0x016c, B:137:0x0174, B:139:0x0178, B:141:0x017e, B:144:0x0187, B:145:0x0183, B:146:0x018c, B:148:0x0192, B:151:0x019b, B:152:0x0197, B:153:0x01a0, B:154:0x00fd, B:155:0x0103, B:156:0x010d, B:157:0x0117, B:159:0x0126, B:160:0x0130, B:161:0x0136, B:162:0x00e6), top: B:50:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0117 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:55:0x00cf, B:58:0x00ed, B:59:0x00f5, B:61:0x00fa, B:63:0x0144, B:65:0x0148, B:69:0x0152, B:70:0x01a6, B:72:0x01ab, B:74:0x01f4, B:77:0x01fc, B:79:0x0202, B:82:0x020a, B:83:0x0219, B:86:0x0220, B:88:0x0234, B:91:0x023b, B:93:0x024b, B:94:0x025c, B:97:0x0264, B:99:0x0269, B:100:0x0258, B:101:0x026c, B:103:0x0275, B:104:0x027d, B:106:0x028c, B:107:0x028f, B:113:0x01b6, B:115:0x01ba, B:117:0x01bf, B:119:0x01ca, B:120:0x01d0, B:122:0x01d5, B:124:0x01d9, B:126:0x01e4, B:127:0x01ea, B:129:0x015a, B:131:0x0162, B:135:0x016c, B:137:0x0174, B:139:0x0178, B:141:0x017e, B:144:0x0187, B:145:0x0183, B:146:0x018c, B:148:0x0192, B:151:0x019b, B:152:0x0197, B:153:0x01a0, B:154:0x00fd, B:155:0x0103, B:156:0x010d, B:157:0x0117, B:159:0x0126, B:160:0x0130, B:161:0x0136, B:162:0x00e6), top: B:50:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0136 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:55:0x00cf, B:58:0x00ed, B:59:0x00f5, B:61:0x00fa, B:63:0x0144, B:65:0x0148, B:69:0x0152, B:70:0x01a6, B:72:0x01ab, B:74:0x01f4, B:77:0x01fc, B:79:0x0202, B:82:0x020a, B:83:0x0219, B:86:0x0220, B:88:0x0234, B:91:0x023b, B:93:0x024b, B:94:0x025c, B:97:0x0264, B:99:0x0269, B:100:0x0258, B:101:0x026c, B:103:0x0275, B:104:0x027d, B:106:0x028c, B:107:0x028f, B:113:0x01b6, B:115:0x01ba, B:117:0x01bf, B:119:0x01ca, B:120:0x01d0, B:122:0x01d5, B:124:0x01d9, B:126:0x01e4, B:127:0x01ea, B:129:0x015a, B:131:0x0162, B:135:0x016c, B:137:0x0174, B:139:0x0178, B:141:0x017e, B:144:0x0187, B:145:0x0183, B:146:0x018c, B:148:0x0192, B:151:0x019b, B:152:0x0197, B:153:0x01a0, B:154:0x00fd, B:155:0x0103, B:156:0x010d, B:157:0x0117, B:159:0x0126, B:160:0x0130, B:161:0x0136, B:162:0x00e6), top: B:50:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x00e6 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:55:0x00cf, B:58:0x00ed, B:59:0x00f5, B:61:0x00fa, B:63:0x0144, B:65:0x0148, B:69:0x0152, B:70:0x01a6, B:72:0x01ab, B:74:0x01f4, B:77:0x01fc, B:79:0x0202, B:82:0x020a, B:83:0x0219, B:86:0x0220, B:88:0x0234, B:91:0x023b, B:93:0x024b, B:94:0x025c, B:97:0x0264, B:99:0x0269, B:100:0x0258, B:101:0x026c, B:103:0x0275, B:104:0x027d, B:106:0x028c, B:107:0x028f, B:113:0x01b6, B:115:0x01ba, B:117:0x01bf, B:119:0x01ca, B:120:0x01d0, B:122:0x01d5, B:124:0x01d9, B:126:0x01e4, B:127:0x01ea, B:129:0x015a, B:131:0x0162, B:135:0x016c, B:137:0x0174, B:139:0x0178, B:141:0x017e, B:144:0x0187, B:145:0x0183, B:146:0x018c, B:148:0x0192, B:151:0x019b, B:152:0x0197, B:153:0x01a0, B:154:0x00fd, B:155:0x0103, B:156:0x010d, B:157:0x0117, B:159:0x0126, B:160:0x0130, B:161:0x0136, B:162:0x00e6), top: B:50:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01ab A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:55:0x00cf, B:58:0x00ed, B:59:0x00f5, B:61:0x00fa, B:63:0x0144, B:65:0x0148, B:69:0x0152, B:70:0x01a6, B:72:0x01ab, B:74:0x01f4, B:77:0x01fc, B:79:0x0202, B:82:0x020a, B:83:0x0219, B:86:0x0220, B:88:0x0234, B:91:0x023b, B:93:0x024b, B:94:0x025c, B:97:0x0264, B:99:0x0269, B:100:0x0258, B:101:0x026c, B:103:0x0275, B:104:0x027d, B:106:0x028c, B:107:0x028f, B:113:0x01b6, B:115:0x01ba, B:117:0x01bf, B:119:0x01ca, B:120:0x01d0, B:122:0x01d5, B:124:0x01d9, B:126:0x01e4, B:127:0x01ea, B:129:0x015a, B:131:0x0162, B:135:0x016c, B:137:0x0174, B:139:0x0178, B:141:0x017e, B:144:0x0187, B:145:0x0183, B:146:0x018c, B:148:0x0192, B:151:0x019b, B:152:0x0197, B:153:0x01a0, B:154:0x00fd, B:155:0x0103, B:156:0x010d, B:157:0x0117, B:159:0x0126, B:160:0x0130, B:161:0x0136, B:162:0x00e6), top: B:50:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01f4 A[Catch: all -> 0x0298, TryCatch #0 {all -> 0x0298, blocks: (B:55:0x00cf, B:58:0x00ed, B:59:0x00f5, B:61:0x00fa, B:63:0x0144, B:65:0x0148, B:69:0x0152, B:70:0x01a6, B:72:0x01ab, B:74:0x01f4, B:77:0x01fc, B:79:0x0202, B:82:0x020a, B:83:0x0219, B:86:0x0220, B:88:0x0234, B:91:0x023b, B:93:0x024b, B:94:0x025c, B:97:0x0264, B:99:0x0269, B:100:0x0258, B:101:0x026c, B:103:0x0275, B:104:0x027d, B:106:0x028c, B:107:0x028f, B:113:0x01b6, B:115:0x01ba, B:117:0x01bf, B:119:0x01ca, B:120:0x01d0, B:122:0x01d5, B:124:0x01d9, B:126:0x01e4, B:127:0x01ea, B:129:0x015a, B:131:0x0162, B:135:0x016c, B:137:0x0174, B:139:0x0178, B:141:0x017e, B:144:0x0187, B:145:0x0183, B:146:0x018c, B:148:0x0192, B:151:0x019b, B:152:0x0197, B:153:0x01a0, B:154:0x00fd, B:155:0x0103, B:156:0x010d, B:157:0x0117, B:159:0x0126, B:160:0x0130, B:161:0x0136, B:162:0x00e6), top: B:50:0x00c8 }] */
    @Override // android.widget.AbsListView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void layoutChildren() {
        /*
            Method dump skipped, instructions count: 708
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.GridView.layoutChildren():void");
    }

    private View makeAndAddView(int i, int i2, boolean z, int i3, boolean z2, int i4) {
        View activeView;
        if (!this.mDataChanged && (activeView = this.mRecycler.getActiveView(i)) != null) {
            setupChild(activeView, i, i2, z, i3, z2, true, i4);
            return activeView;
        }
        View obtainView = obtainView(i, this.mIsScrap);
        setupChild(obtainView, i, i2, z, i3, z2, this.mIsScrap[0], i4);
        return obtainView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setupChild(View view, int i, int i2, boolean z, int i3, boolean z2, boolean z3, int i4) {
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
    void setSelectionInt(int i) {
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

    /* JADX WARN: Code restructure failed: missing block: B:104:0x014e, code lost:
    
        if (pageScroll(130) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0162, code lost:
    
        if (fullScroll(130) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0176, code lost:
    
        if (pageScroll(33) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x018a, code lost:
    
        if (fullScroll(33) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x01b1, code lost:
    
        if (sequenceScroll(2) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x01c5, code lost:
    
        if (sequenceScroll(1) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0088, code lost:
    
        if (arrowScroll(66) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00b0, code lost:
    
        if (arrowScroll(17) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00d0, code lost:
    
        if (fullScroll(130) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00e1, code lost:
    
        if (arrowScroll(130) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0101, code lost:
    
        if (fullScroll(33) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0112, code lost:
    
        if (arrowScroll(33) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0126, code lost:
    
        if (fullScroll(130) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x013a, code lost:
    
        if (fullScroll(33) == false) goto L53;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean commonKey(int r9, int r10, android.view.KeyEvent r11) {
        /*
            Method dump skipped, instructions count: 502
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.GridView.commonKey(int, int, android.view.KeyEvent):boolean");
    }

    boolean pageScroll(int i) {
        int min;
        if (i == 33) {
            min = Math.max(0, this.mSelectedPosition - getChildCount());
        } else {
            min = i == 130 ? Math.min(this.mItemCount - 1, this.mSelectedPosition + getChildCount()) : -1;
        }
        if (min < 0) {
            return false;
        }
        setSelectionInt(min);
        invokeOnItemScrollListener();
        awakenScrollBars();
        return true;
    }

    boolean fullScroll(int i) {
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

    boolean arrowScroll(int i) {
        int i2;
        int max;
        boolean z;
        int i3 = this.mSelectedPosition;
        int i4 = this.mNumColumns;
        boolean z2 = true;
        if (!this.mStackFromBottom) {
            max = (i3 / i4) * i4;
            i2 = Math.min((max + i4) - 1, this.mItemCount - 1);
        } else {
            i2 = (this.mItemCount - 1) - ((((this.mItemCount - 1) - i3) / i4) * i4);
            max = Math.max(0, (i2 - i4) + 1);
        }
        if (i != 33) {
            if (i == 130 && i2 < this.mItemCount - 1) {
                this.mLayoutMode = 6;
                setSelectionInt(Math.min(i4 + i3, this.mItemCount - 1));
                z = true;
            }
            z = false;
        } else {
            if (max > 0) {
                this.mLayoutMode = 6;
                setSelectionInt(Math.max(0, i3 - i4));
                z = true;
            }
            z = false;
        }
        boolean isLayoutRtl = isLayoutRtl();
        if (i3 > max && ((i == 17 && !isLayoutRtl) || (i == 66 && isLayoutRtl))) {
            this.mLayoutMode = 6;
            setSelectionInt(Math.max(0, i3 - 1));
        } else if (i3 >= i2 || (!(i == 17 && isLayoutRtl) && (i != 66 || isLayoutRtl))) {
            z2 = z;
        } else {
            this.mLayoutMode = 6;
            setSelectionInt(Math.min(i3 + 1, this.mItemCount - 1));
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

    /* JADX WARN: Code restructure failed: missing block: B:11:0x003a, code lost:
    
        if (r0 == r1) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0049, code lost:
    
        r0 = r4;
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0048, code lost:
    
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0046, code lost:
    
        if (r0 == r3) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean sequenceScroll(int r9) {
        /*
            r8 = this;
            int r0 = r8.mSelectedPosition
            int r1 = r8.mNumColumns
            int r2 = r8.mItemCount
            boolean r3 = r8.mStackFromBottom
            r4 = 0
            r5 = 1
            if (r3 != 0) goto L18
            int r3 = r0 / r1
            int r3 = r3 * r1
            int r1 = r1 + r3
            int r1 = r1 - r5
            int r6 = r2 + (-1)
            int r1 = java.lang.Math.min(r1, r6)
            goto L29
        L18:
            int r3 = r2 + (-1)
            int r6 = r3 - r0
            int r6 = r6 / r1
            int r6 = r6 * r1
            int r3 = r3 - r6
            int r1 = r3 - r1
            int r1 = r1 + r5
            int r1 = java.lang.Math.max(r4, r1)
            r7 = r3
            r3 = r1
            r1 = r7
        L29:
            r6 = 6
            if (r9 == r5) goto L3d
            r3 = 2
            if (r9 == r3) goto L30
            goto L4c
        L30:
            int r2 = r2 - r5
            if (r0 >= r2) goto L4c
            r8.mLayoutMode = r6
            int r2 = r0 + 1
            r8.setSelectionInt(r2)
            if (r0 != r1) goto L49
            goto L48
        L3d:
            if (r0 <= 0) goto L4c
            r8.mLayoutMode = r6
            int r1 = r0 + (-1)
            r8.setSelectionInt(r1)
            if (r0 != r3) goto L49
        L48:
            r4 = r5
        L49:
            r0 = r4
            r4 = r5
            goto L4d
        L4c:
            r0 = r4
        L4d:
            if (r4 == 0) goto L59
            int r9 = android.view.SoundEffectConstants.getContantForFocusDirection(r9)
            r8.playSoundEffect(r9)
            r8.invokeOnItemScrollListener()
        L59:
            if (r0 == 0) goto L5e
            r8.awakenScrollBars()
        L5e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.GridView.sequenceScroll(int):boolean");
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
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
        int max;
        int i3;
        int childCount = getChildCount();
        int i4 = childCount - 1;
        int i5 = i4 - i;
        if (!this.mStackFromBottom) {
            int i6 = this.mNumColumns;
            max = i - (i % i6);
            i3 = Math.min((i6 + max) - 1, childCount);
        } else {
            int i7 = this.mNumColumns;
            int i8 = i4 - (i5 - (i5 % i7));
            max = Math.max(0, (i8 - i7) + 1);
            i3 = i8;
        }
        if (i2 == 1) {
            return i == i3 && i3 == i4;
        }
        if (i2 == 2) {
            return i == max && max == 0;
        }
        if (i2 == 17) {
            return i == i3;
        }
        if (i2 == 33) {
            return i3 == i4;
        }
        if (i2 == 66) {
            return i == max;
        }
        if (i2 == 130) {
            return max == 0;
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
        int max = Math.max(i * 100, 0);
        return this.mScrollY != 0 ? max + Math.abs((int) ((this.mScrollY / getHeight()) * i * 100.0f)) : max;
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
        int min = Math.min(numColumns * i2, getCount() - 1);
        if (i2 < 0) {
            return false;
        }
        smoothScrollToPosition(min);
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
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
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
    public void semSetGoToTopEnabledForAppWidget(boolean z) {
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

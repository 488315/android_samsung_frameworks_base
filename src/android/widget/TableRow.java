package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.TtmlUtils;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.LinearLayout;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class TableRow extends LinearLayout {
    private ChildrenTracker mChildrenTracker;
    private SparseIntArray mColumnToChildIndex;
    private int[] mColumnWidths;
    private int[] mConstrainedColumnWidths;
    private int mNumColumns;

    public static class LayoutParams extends LinearLayout.LayoutParams {
        private static final int LOCATION = 0;
        private static final int LOCATION_NEXT = 1;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public int column;
        private int[] mOffset;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public int span;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LayoutParams> {
            private int mLayout_columnId;
            private int mLayout_spanId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(PropertyMapper propertyMapper) {
                this.mLayout_columnId = propertyMapper.mapInt("layout_column", 16843084);
                this.mLayout_spanId = propertyMapper.mapInt("layout_span", 16843085);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(LayoutParams layoutParams, PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readInt(this.mLayout_columnId, layoutParams.column);
                propertyReader.readInt(this.mLayout_spanId, layoutParams.span);
            }
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mOffset = new int[2];
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TableRow_Cell);
            this.column = obtainStyledAttributes.getInt(0, -1);
            int i = obtainStyledAttributes.getInt(1, 1);
            this.span = i;
            if (i <= 1) {
                this.span = 1;
            }
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mOffset = new int[2];
            this.column = -1;
            this.span = 1;
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
            this.mOffset = new int[2];
            this.column = -1;
            this.span = 1;
        }

        public LayoutParams() {
            super(-1, -2);
            this.mOffset = new int[2];
            this.column = -1;
            this.span = 1;
        }

        public LayoutParams(int i) {
            this();
            this.column = i;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mOffset = new int[2];
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mOffset = new int[2];
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void setBaseAttributes(TypedArray typedArray, int i, int i2) {
            if (typedArray.hasValue(i)) {
                this.width = typedArray.getLayoutDimension(i, "layout_width");
            } else {
                this.width = -1;
            }
            if (typedArray.hasValue(i2)) {
                this.height = typedArray.getLayoutDimension(i2, "layout_height");
            } else {
                this.height = -2;
            }
        }

        @Override // android.widget.LinearLayout.LayoutParams, android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:column", this.column);
            viewHierarchyEncoder.addProperty("layout:span", this.span);
        }
    }

    public TableRow(Context context) {
        super(context);
        this.mNumColumns = 0;
        initTableRow();
    }

    public TableRow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNumColumns = 0;
        initTableRow();
    }

    private void initTableRow() {
        ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
        ChildrenTracker childrenTracker = new ChildrenTracker();
        this.mChildrenTracker = childrenTracker;
        if (onHierarchyChangeListener != null) {
            childrenTracker.setOnHierarchyChangeListener(onHierarchyChangeListener);
        }
        super.setOnHierarchyChangeListener(this.mChildrenTracker);
    }

    @Override // android.view.ViewGroup
    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mChildrenTracker.setOnHierarchyChangeListener(onHierarchyChangeListener);
    }

    void setColumnCollapsed(int i, boolean z) {
        View virtualChildAt = getVirtualChildAt(i);
        if (virtualChildAt != null) {
            virtualChildAt.setVisibility(z ? 8 : 0);
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        measureHorizontal(i, i2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layoutHorizontal(i, i2, i3, i4);
    }

    @Override // android.widget.LinearLayout
    public View getVirtualChildAt(int i) {
        if (this.mColumnToChildIndex == null) {
            mapIndexAndColumns();
        }
        int i2 = this.mColumnToChildIndex.get(i, -1);
        if (i2 != -1) {
            return getChildAt(i2);
        }
        return null;
    }

    @Override // android.widget.LinearLayout
    public int getVirtualChildCount() {
        if (this.mColumnToChildIndex == null) {
            mapIndexAndColumns();
        }
        return this.mNumColumns;
    }

    private void mapIndexAndColumns() {
        if (this.mColumnToChildIndex == null) {
            int childCount = getChildCount();
            SparseIntArray sparseIntArray = new SparseIntArray();
            this.mColumnToChildIndex = sparseIntArray;
            int i = 0;
            for (int i2 = 0; i2 < childCount; i2++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i2).getLayoutParams();
                if (layoutParams.column >= i) {
                    i = layoutParams.column;
                }
                int i3 = 0;
                while (i3 < layoutParams.span) {
                    sparseIntArray.put(i, i2);
                    i3++;
                    i++;
                }
            }
            this.mNumColumns = i;
        }
    }

    @Override // android.widget.LinearLayout
    int measureNullChild(int i) {
        return this.mConstrainedColumnWidths[i];
    }

    @Override // android.widget.LinearLayout
    void measureChildBeforeLayout(View view, int i, int i2, int i3, int i4, int i5) {
        if (this.mConstrainedColumnWidths != null) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            int i6 = layoutParams.span;
            int[] iArr = this.mConstrainedColumnWidths;
            int i7 = 0;
            for (int i8 = 0; i8 < i6; i8++) {
                i7 += iArr[i + i8];
            }
            int i9 = layoutParams.gravity;
            boolean isHorizontal = Gravity.isHorizontal(i9);
            view.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, (i7 - layoutParams.leftMargin) - layoutParams.rightMargin), isHorizontal ? Integer.MIN_VALUE : 1073741824), getChildMeasureSpec(i4, this.mPaddingTop + this.mPaddingBottom + layoutParams.topMargin + layoutParams.bottomMargin + i5, layoutParams.height));
            if (isHorizontal) {
                layoutParams.mOffset[1] = i7 - view.getMeasuredWidth();
                int absoluteGravity = Gravity.getAbsoluteGravity(i9, getLayoutDirection()) & 7;
                if (absoluteGravity != 1) {
                    if (absoluteGravity != 5) {
                        return;
                    }
                    layoutParams.mOffset[0] = layoutParams.mOffset[1];
                    return;
                }
                layoutParams.mOffset[0] = layoutParams.mOffset[1] / 2;
                return;
            }
            int[] iArr2 = layoutParams.mOffset;
            layoutParams.mOffset[1] = 0;
            iArr2[0] = 0;
            return;
        }
        super.measureChildBeforeLayout(view, i, i2, i3, i4, i5);
    }

    @Override // android.widget.LinearLayout
    int getChildrenSkipCount(View view, int i) {
        return ((LayoutParams) view.getLayoutParams()).span - 1;
    }

    @Override // android.widget.LinearLayout
    int getLocationOffset(View view) {
        return ((LayoutParams) view.getLayoutParams()).mOffset[0];
    }

    @Override // android.widget.LinearLayout
    int getNextLocationOffset(View view) {
        return ((LayoutParams) view.getLayoutParams()).mOffset[1];
    }

    int[] getColumnsWidths(int i, int i2) {
        int childMeasureSpec;
        int virtualChildCount = getVirtualChildCount();
        int[] iArr = this.mColumnWidths;
        if (iArr == null || virtualChildCount != iArr.length) {
            this.mColumnWidths = new int[virtualChildCount];
        }
        int[] iArr2 = this.mColumnWidths;
        for (int i3 = 0; i3 < virtualChildCount; i3++) {
            View virtualChildAt = getVirtualChildAt(i3);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (layoutParams.span == 1) {
                    int i4 = layoutParams.width;
                    if (i4 == -2) {
                        childMeasureSpec = getChildMeasureSpec(i, 0, -2);
                    } else if (i4 == -1) {
                        childMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(View.MeasureSpec.getSize(i2), 0);
                    } else {
                        childMeasureSpec = View.MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824);
                    }
                    virtualChildAt.measure(childMeasureSpec, childMeasureSpec);
                    iArr2[i3] = virtualChildAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                } else {
                    iArr2[i3] = 0;
                }
            } else {
                iArr2[i3] = 0;
            }
        }
        return iArr2;
    }

    void setColumnsWidthConstraints(int[] iArr) {
        if (iArr == null || iArr.length < getVirtualChildCount()) {
            throw new IllegalArgumentException("columnWidths should be >= getVirtualChildCount()");
        }
        this.mConstrainedColumnWidths = iArr;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LinearLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return TableRow.class.getName();
    }

    private class ChildrenTracker implements ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener listener;

        private ChildrenTracker() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
            this.listener = onHierarchyChangeListener;
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(View view, View view2) {
            TableRow.this.mColumnToChildIndex = null;
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.listener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(View view, View view2) {
            TableRow.this.mColumnToChildIndex = null;
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.listener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }
}

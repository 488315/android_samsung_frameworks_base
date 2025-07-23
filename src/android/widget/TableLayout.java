package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.R;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class TableLayout extends LinearLayout {
    private SparseBooleanArray mCollapsedColumns;
    private boolean mInitialized;
    private int[] mMaxWidths;
    private PassThroughHierarchyChangeListener mPassThroughListener;
    private boolean mShrinkAllColumns;
    private SparseBooleanArray mShrinkableColumns;
    private boolean mStretchAllColumns;
    private SparseBooleanArray mStretchableColumns;

    public TableLayout(Context context) {
        super(context);
        initTableLayout();
    }

    public TableLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TableLayout);
        String string = obtainStyledAttributes.getString(0);
        if (string != null) {
            if (string.charAt(0) == '*') {
                this.mStretchAllColumns = true;
            } else {
                this.mStretchableColumns = parseColumns(string);
            }
        }
        String string2 = obtainStyledAttributes.getString(1);
        if (string2 != null) {
            if (string2.charAt(0) == '*') {
                this.mShrinkAllColumns = true;
            } else {
                this.mShrinkableColumns = parseColumns(string2);
            }
        }
        String string3 = obtainStyledAttributes.getString(2);
        if (string3 != null) {
            this.mCollapsedColumns = parseColumns(string3);
        }
        obtainStyledAttributes.recycle();
        initTableLayout();
    }

    private static SparseBooleanArray parseColumns(String str) {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        for (String str2 : Pattern.compile("\\s*,\\s*").split(str)) {
            try {
                int parseInt = Integer.parseInt(str2);
                if (parseInt >= 0) {
                    sparseBooleanArray.put(parseInt, true);
                }
            } catch (NumberFormatException unused) {
            }
        }
        return sparseBooleanArray;
    }

    private void initTableLayout() {
        if (this.mCollapsedColumns == null) {
            this.mCollapsedColumns = new SparseBooleanArray();
        }
        if (this.mStretchableColumns == null) {
            this.mStretchableColumns = new SparseBooleanArray();
        }
        if (this.mShrinkableColumns == null) {
            this.mShrinkableColumns = new SparseBooleanArray();
        }
        setOrientation(1);
        PassThroughHierarchyChangeListener passThroughHierarchyChangeListener = new PassThroughHierarchyChangeListener();
        this.mPassThroughListener = passThroughHierarchyChangeListener;
        super.setOnHierarchyChangeListener(passThroughHierarchyChangeListener);
        this.mInitialized = true;
    }

    @Override // android.view.ViewGroup
    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mPassThroughListener.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    private void requestRowsLayout() {
        if (this.mInitialized) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).requestLayout();
            }
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.mInitialized) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).forceLayout();
            }
        }
        super.requestLayout();
    }

    public boolean isShrinkAllColumns() {
        return this.mShrinkAllColumns;
    }

    public void setShrinkAllColumns(boolean z) {
        this.mShrinkAllColumns = z;
    }

    public boolean isStretchAllColumns() {
        return this.mStretchAllColumns;
    }

    public void setStretchAllColumns(boolean z) {
        this.mStretchAllColumns = z;
    }

    public void setColumnCollapsed(int i, boolean z) {
        this.mCollapsedColumns.put(i, z);
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof TableRow) {
                ((TableRow) childAt).setColumnCollapsed(i, z);
            }
        }
        requestRowsLayout();
    }

    public boolean isColumnCollapsed(int i) {
        return this.mCollapsedColumns.get(i);
    }

    public void setColumnStretchable(int i, boolean z) {
        this.mStretchableColumns.put(i, z);
        requestRowsLayout();
    }

    public boolean isColumnStretchable(int i) {
        return this.mStretchAllColumns || this.mStretchableColumns.get(i);
    }

    public void setColumnShrinkable(int i, boolean z) {
        this.mShrinkableColumns.put(i, z);
        requestRowsLayout();
    }

    public boolean isColumnShrinkable(int i) {
        return this.mShrinkAllColumns || this.mShrinkableColumns.get(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trackCollapsedColumns(View view) {
        if (view instanceof TableRow) {
            TableRow tableRow = (TableRow) view;
            SparseBooleanArray sparseBooleanArray = this.mCollapsedColumns;
            int size = sparseBooleanArray.size();
            for (int i = 0; i < size; i++) {
                int keyAt = sparseBooleanArray.keyAt(i);
                boolean valueAt = sparseBooleanArray.valueAt(i);
                if (valueAt) {
                    tableRow.setColumnCollapsed(keyAt, valueAt);
                }
            }
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view) {
        super.addView(view);
        requestRowsLayout();
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i) {
        super.addView(view, i);
        requestRowsLayout();
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, layoutParams);
        requestRowsLayout();
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        requestRowsLayout();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        measureVertical(i, i2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layoutVertical(i, i2, i3, i4);
    }

    @Override // android.widget.LinearLayout
    void measureChildBeforeLayout(View view, int i, int i2, int i3, int i4, int i5) {
        if (view instanceof TableRow) {
            ((TableRow) view).setColumnsWidthConstraints(this.mMaxWidths);
        }
        super.measureChildBeforeLayout(view, i, i2, i3, i4, i5);
    }

    @Override // android.widget.LinearLayout
    void measureVertical(int i, int i2) {
        findLargestCells(i, i2);
        shrinkAndStretchColumns(i);
        super.measureVertical(i, i2);
    }

    private void findLargestCells(int i, int i2) {
        int childCount = getChildCount();
        boolean z = true;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8 && (childAt instanceof TableRow)) {
                TableRow tableRow = (TableRow) childAt;
                tableRow.getLayoutParams().height = -2;
                int[] columnsWidths = tableRow.getColumnsWidths(i, i2);
                int length = columnsWidths.length;
                if (z) {
                    int[] iArr = this.mMaxWidths;
                    if (iArr == null || iArr.length != length) {
                        this.mMaxWidths = new int[length];
                    }
                    System.arraycopy(columnsWidths, 0, this.mMaxWidths, 0, length);
                    z = false;
                } else {
                    int[] iArr2 = this.mMaxWidths;
                    int length2 = iArr2.length;
                    int i4 = length - length2;
                    if (i4 > 0) {
                        int[] iArr3 = new int[length];
                        this.mMaxWidths = iArr3;
                        System.arraycopy(iArr2, 0, iArr3, 0, iArr2.length);
                        System.arraycopy(columnsWidths, iArr2.length, this.mMaxWidths, iArr2.length, i4);
                    }
                    int[] iArr4 = this.mMaxWidths;
                    int min = Math.min(length2, length);
                    for (int i5 = 0; i5 < min; i5++) {
                        iArr4[i5] = Math.max(iArr4[i5], columnsWidths[i5]);
                    }
                }
            }
        }
    }

    private void shrinkAndStretchColumns(int i) {
        int[] iArr = this.mMaxWidths;
        if (iArr == null) {
            return;
        }
        int i2 = 0;
        for (int i3 : iArr) {
            i2 += i3;
        }
        int size = (View.MeasureSpec.getSize(i) - this.mPaddingLeft) - this.mPaddingRight;
        if (i2 > size && (this.mShrinkAllColumns || this.mShrinkableColumns.size() > 0)) {
            mutateColumnsWidth(this.mShrinkableColumns, this.mShrinkAllColumns, size, i2);
        } else if (i2 < size) {
            if (this.mStretchAllColumns || this.mStretchableColumns.size() > 0) {
                mutateColumnsWidth(this.mStretchableColumns, this.mStretchAllColumns, size, i2);
            }
        }
    }

    private void mutateColumnsWidth(SparseBooleanArray sparseBooleanArray, boolean z, int i, int i2) {
        int[] iArr = this.mMaxWidths;
        int length = iArr.length;
        int size = z ? length : sparseBooleanArray.size();
        int i3 = (i - i2) / size;
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt instanceof TableRow) {
                childAt.forceLayout();
            }
        }
        if (z) {
            for (int i5 = 0; i5 < size; i5++) {
                iArr[i5] = iArr[i5] + i3;
            }
            return;
        }
        int i6 = 0;
        for (int i7 = 0; i7 < size; i7++) {
            int keyAt = sparseBooleanArray.keyAt(i7);
            if (sparseBooleanArray.valueAt(i7)) {
                if (keyAt < length) {
                    iArr[keyAt] = iArr[keyAt] + i3;
                } else {
                    i6++;
                }
            }
        }
        if (i6 <= 0 || i6 >= size) {
            return;
        }
        int i8 = (i3 * i6) / (size - i6);
        for (int i9 = 0; i9 < size; i9++) {
            int keyAt2 = sparseBooleanArray.keyAt(i9);
            if (sparseBooleanArray.valueAt(i9) && keyAt2 < length) {
                int i10 = iArr[keyAt2];
                if (i8 > i10) {
                    iArr[keyAt2] = 0;
                } else {
                    iArr[keyAt2] = i10 + i8;
                }
            }
        }
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
        return TableLayout.class.getName();
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(-1, i2);
        }

        public LayoutParams(int i, int i2, float f) {
            super(-1, i2, f);
        }

        public LayoutParams() {
            super(-1, -2);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.width = -1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.width = -1;
            if (marginLayoutParams instanceof LayoutParams) {
                this.weight = ((LayoutParams) marginLayoutParams).weight;
            }
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void setBaseAttributes(TypedArray typedArray, int i, int i2) {
            this.width = -1;
            if (typedArray.hasValue(i2)) {
                this.height = typedArray.getLayoutDimension(i2, "layout_height");
            } else {
                this.height = -2;
            }
        }
    }

    private class PassThroughHierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(View view, View view2) {
            TableLayout.this.trackCollapsedColumns(view2);
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }
}

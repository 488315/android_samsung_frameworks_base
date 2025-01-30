package com.android.systemui.qs;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.android.systemui.QpRune;
import com.android.systemui.R$styleable;
import com.android.systemui.util.DeviceState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PseudoGridView extends ViewGroup {
    public final int mFixedChildWidth;
    public final int mHorizontalSpacing;
    public final int mNumColumns;
    public final int mVerticalSpacing;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewGroupAdapterBridge extends DataSetObserver {
        public final BaseAdapter mAdapter;
        public boolean mReleased = false;
        public final WeakReference mViewGroup;

        private ViewGroupAdapterBridge(ViewGroup viewGroup, BaseAdapter baseAdapter) {
            this.mViewGroup = new WeakReference(viewGroup);
            this.mAdapter = baseAdapter;
            baseAdapter.registerDataSetObserver(this);
            refresh();
        }

        public static void link(ViewGroup viewGroup, BaseAdapter baseAdapter) {
            new ViewGroupAdapterBridge(viewGroup, baseAdapter);
        }

        @Override // android.database.DataSetObserver
        public final void onChanged() {
            refresh();
        }

        @Override // android.database.DataSetObserver
        public final void onInvalidated() {
            if (this.mReleased) {
                return;
            }
            this.mReleased = true;
            this.mAdapter.unregisterDataSetObserver(this);
        }

        public final void refresh() {
            if (this.mReleased) {
                return;
            }
            ViewGroup viewGroup = (ViewGroup) this.mViewGroup.get();
            if (viewGroup == null) {
                if (this.mReleased) {
                    return;
                }
                this.mReleased = true;
                this.mAdapter.unregisterDataSetObserver(this);
                return;
            }
            int childCount = viewGroup.getChildCount();
            int count = this.mAdapter.getCount();
            int max = Math.max(childCount, count);
            int i = 0;
            while (i < max) {
                if (i < count) {
                    View childAt = i < childCount ? viewGroup.getChildAt(i) : null;
                    View view = this.mAdapter.getView(i, childAt, viewGroup);
                    if (childAt == null) {
                        viewGroup.addView(view);
                    } else if (childAt != view) {
                        viewGroup.removeViewAt(i);
                        viewGroup.addView(view, i);
                    }
                } else {
                    viewGroup.removeViewAt(viewGroup.getChildCount() - 1);
                }
                i++;
            }
        }
    }

    public PseudoGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNumColumns = 3;
        this.mFixedChildWidth = -1;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.PseudoGridView);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 2) {
                this.mNumColumns = obtainStyledAttributes.getInt(index, 3);
            } else if (index == 3) {
                this.mVerticalSpacing = obtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == 1) {
                this.mHorizontalSpacing = obtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == 0) {
                this.mFixedChildWidth = obtainStyledAttributes.getDimensionPixelSize(index, -1);
            }
        }
        obtainStyledAttributes.recycle();
        if (QpRune.QUICK_MANAGE_TWO_PHONE && DeviceState.supportsMultipleUsers()) {
            this.mNumColumns = 2;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean isLayoutRtl = isLayoutRtl();
        int childCount = getChildCount();
        int i5 = ((childCount + r13) - 1) / this.mNumColumns;
        int i6 = 0;
        for (int i7 = 0; i7 < i5; i7++) {
            int width = isLayoutRtl ? getWidth() : 0;
            int i8 = this.mNumColumns;
            int i9 = i7 * i8;
            int min = Math.min(i8 + i9, childCount);
            int i10 = 0;
            while (i9 < min) {
                View childAt = getChildAt(i9);
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (isLayoutRtl) {
                    width -= measuredWidth;
                }
                childAt.layout(width, i6, width + measuredWidth, i6 + measuredHeight);
                i10 = Math.max(i10, measuredHeight);
                width = isLayoutRtl ? width - this.mHorizontalSpacing : measuredWidth + this.mHorizontalSpacing + width;
                i9++;
            }
            i6 += i10 + this.mVerticalSpacing;
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) == 0) {
            throw new UnsupportedOperationException("Needs a maximum width");
        }
        int size = View.MeasureSpec.getSize(i);
        int i3 = this.mFixedChildWidth;
        int i4 = this.mNumColumns;
        int i5 = this.mHorizontalSpacing;
        int i6 = ((i4 - 1) * i5) + (i3 * i4);
        if (i3 == -1 || i6 > size) {
            i3 = (size - ((i4 - 1) * i5)) / i4;
        } else {
            size = (i3 * i4) + ((i4 - 1) * i5);
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i3, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        int childCount = getChildCount();
        int i7 = ((childCount + r3) - 1) / this.mNumColumns;
        int i8 = 0;
        for (int i9 = 0; i9 < i7; i9++) {
            int i10 = this.mNumColumns;
            int i11 = i9 * i10;
            int min = Math.min(i10 + i11, childCount);
            int i12 = 0;
            for (int i13 = i11; i13 < min; i13++) {
                View childAt = getChildAt(i13);
                childAt.measure(makeMeasureSpec, 0);
                i12 = Math.max(i12, childAt.getMeasuredHeight());
            }
            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i12, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            while (i11 < min) {
                View childAt2 = getChildAt(i11);
                if (childAt2.getMeasuredHeight() != i12) {
                    childAt2.measure(makeMeasureSpec, makeMeasureSpec2);
                }
                i11++;
            }
            i8 += i12;
            if (i9 > 0) {
                i8 += this.mVerticalSpacing;
            }
        }
        setMeasuredDimension(size, ViewGroup.resolveSizeAndState(i8, i2, 0));
    }
}

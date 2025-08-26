package com.android.systemui.qs;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.UserManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.android.systemui.QpRune;
import com.android.systemui.qs.tiles.UserDetailView;
import com.android.systemui.res.R$styleable;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class PseudoGridView extends ViewGroup {
    public final int mFixedChildWidth;
    public final int mHorizontalSpacing;
    public final int mNumColumns;
    public final int mVerticalSpacing;

    public class ViewGroupAdapterBridge extends DataSetObserver {
        public final BaseAdapter mAdapter;
        public boolean mReleased = false;
        public final WeakReference mViewGroup;

        private ViewGroupAdapterBridge(ViewGroup viewGroup, BaseAdapter baseAdapter) {
            this.mViewGroup = new WeakReference(viewGroup);
            this.mAdapter = baseAdapter;
            baseAdapter.registerDataSetObserver(this);
            refresh();
        }

        public static void link(ViewGroup viewGroup, UserDetailView.Adapter adapter) {
            new ViewGroupAdapterBridge(viewGroup, adapter);
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
            int iMax = Math.max(childCount, count);
            int i = 0;
            while (i < iMax) {
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
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.PseudoGridView);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == 2) {
                this.mNumColumns = typedArrayObtainStyledAttributes.getInt(index, 3);
            } else if (index == 3) {
                this.mVerticalSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == 1) {
                this.mHorizontalSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == 0) {
                this.mFixedChildWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, -1);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        if (QpRune.QUICK_MUM_TWO_PHONE && UserManager.supportsMultipleUsers()) {
            this.mNumColumns = 2;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean zIsLayoutRtl = isLayoutRtl();
        int childCount = getChildCount();
        int i5 = ((childCount + r13) - 1) / this.mNumColumns;
        int i6 = 0;
        for (int i7 = 0; i7 < i5; i7++) {
            int width = zIsLayoutRtl ? getWidth() : 0;
            int i8 = this.mNumColumns;
            int i9 = i7 * i8;
            int iMin = Math.min(i8 + i9, childCount);
            int iMax = 0;
            while (i9 < iMin) {
                View childAt = getChildAt(i9);
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (zIsLayoutRtl) {
                    width -= measuredWidth;
                }
                childAt.layout(width, i6, width + measuredWidth, i6 + measuredHeight);
                iMax = Math.max(iMax, measuredHeight);
                width = zIsLayoutRtl ? width - this.mHorizontalSpacing : measuredWidth + this.mHorizontalSpacing + width;
                i9++;
            }
            i6 += iMax + this.mVerticalSpacing;
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
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
        int childCount = getChildCount();
        int i7 = ((childCount + r3) - 1) / this.mNumColumns;
        int i8 = 0;
        for (int i9 = 0; i9 < i7; i9++) {
            int i10 = this.mNumColumns;
            int i11 = i9 * i10;
            int iMin = Math.min(i10 + i11, childCount);
            int iMax = 0;
            for (int i12 = i11; i12 < iMin; i12++) {
                View childAt = getChildAt(i12);
                childAt.measure(iMakeMeasureSpec, 0);
                iMax = Math.max(iMax, childAt.getMeasuredHeight());
            }
            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(iMax, 1073741824);
            while (i11 < iMin) {
                View childAt2 = getChildAt(i11);
                if (childAt2.getMeasuredHeight() != iMax) {
                    childAt2.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                }
                i11++;
            }
            i8 += iMax;
            if (i9 > 0) {
                i8 += this.mVerticalSpacing;
            }
        }
        setMeasuredDimension(size, ViewGroup.resolveSizeAndState(i8, i2, 0));
    }
}

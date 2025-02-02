package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class GridLayoutManager extends LinearLayoutManager {
    boolean mPendingSpanCountChange;
    int mSpanCount;
    DefaultSpanSizeLookup mSpanSizeLookup;

    public static final class DefaultSpanSizeLookup extends SpanSizeLookup {
    }

    public static class LayoutParams extends RecyclerView.LayoutParams {
    }

    public static abstract class SpanSizeLookup {
        final SparseIntArray mSpanIndexCache = new SparseIntArray();
        final SparseIntArray mSpanGroupIndexCache = new SparseIntArray();

        public static int getSpanGroupIndex(int i, int i2) {
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                i3++;
                if (i3 == i2) {
                    i4++;
                    i3 = 0;
                } else if (i3 > i2) {
                    i4++;
                    i3 = 1;
                }
            }
            return i3 + 1 > i2 ? i4 + 1 : i4;
        }

        public final void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        new SparseIntArray();
        new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        new Rect();
        int i3 = RecyclerView.LayoutManager.getProperties(context, attributeSet, i, i2).spanCount;
        if (i3 == this.mSpanCount) {
            return;
        }
        this.mPendingSpanCountChange = true;
        if (i3 < 1) {
            throw new IllegalArgumentException(SubMenuBuilder$$ExternalSyntheticOutline0.m0m("Span count should be at least 1. Provided ", i3));
        }
        this.mSpanCount = i3;
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        requestLayout();
    }

    private int getSpanGroupIndex(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!state.mInPreLayout) {
            DefaultSpanSizeLookup defaultSpanSizeLookup = this.mSpanSizeLookup;
            int i2 = this.mSpanCount;
            defaultSpanSizeLookup.getClass();
            return SpanSizeLookup.getSpanGroupIndex(i, i2);
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout == -1) {
            Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + i);
            return 0;
        }
        DefaultSpanSizeLookup defaultSpanSizeLookup2 = this.mSpanSizeLookup;
        int i3 = this.mSpanCount;
        defaultSpanSizeLookup2.getClass();
        return SpanSizeLookup.getSpanGroupIndex(convertPreLayoutPositionToPostLayout, i3);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void computeHorizontalScrollOffset(RecyclerView.State state) {
        super.computeHorizontalScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollRange(RecyclerView.State state) {
        return super.computeHorizontalScrollRange(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void computeVerticalScrollOffset(RecyclerView.State state) {
        super.computeVerticalScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollRange(RecyclerView.State state) {
        return super.computeVerticalScrollRange(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return this.mOrientation == 0 ? new LayoutParams(-2, -1) : new LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation != 1) {
            if (state.getItemCount() < 1) {
                return 0;
            }
            return getSpanGroupIndex(state.getItemCount() - 1, recycler, state) + 1;
        }
        int i = this.mSpanCount;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.getAdapter();
        }
        return Math.min(i, 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation != 0) {
            if (state.getItemCount() < 1) {
                return 0;
            }
            return getSpanGroupIndex(state.getItemCount() - 1, recycler, state) + 1;
        }
        int i = this.mSpanCount;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.getAdapter();
        }
        return Math.min(i, 0);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(recycler, state, accessibilityNodeInfoCompat);
        accessibilityNodeInfoCompat.setClassName(GridView.class.getName());
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    final boolean performAccessibilityAction(int i, Bundle bundle) {
        if (i != 16908343 || bundle == null) {
            return super.performAccessibilityAction(i, bundle);
        }
        int i2 = bundle.getInt("android.view.accessibility.action.ARGUMENT_ROW_INT", -1);
        int i3 = bundle.getInt("android.view.accessibility.action.ARGUMENT_COLUMN_INT", -1);
        if (i2 == -1 || i3 == -1) {
            return false;
        }
        this.mRecyclerView.getClass();
        throw null;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void setStackFromEnd(boolean z) {
        if (z) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }
}

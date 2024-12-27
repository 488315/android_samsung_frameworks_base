package com.android.systemui.util.recycler;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public final class HorizontalSpacerItemDecoration extends RecyclerView.ItemDecoration {
    public static final int $stable = 0;
    private final int offset;

    public HorizontalSpacerItemDecoration(int i) {
        this.offset = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        recyclerView.getClass();
        int childAdapterPosition = RecyclerView.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = recyclerView.mAdapter;
        int itemCount = adapter != null ? adapter.getItemCount() : 0;
        int i = this.offset;
        if (childAdapterPosition == 0) {
            i *= 2;
        }
        int i2 = itemCount - 1;
        int i3 = this.offset;
        if (childAdapterPosition == i2) {
            i3 *= 2;
        }
        rect.set(i, 0, i3, 0);
    }
}

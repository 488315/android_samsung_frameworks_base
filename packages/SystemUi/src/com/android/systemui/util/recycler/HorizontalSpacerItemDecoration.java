package com.android.systemui.util.recycler;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HorizontalSpacerItemDecoration extends RecyclerView.ItemDecoration {
    public final int offset;

    public HorizontalSpacerItemDecoration(int i) {
        this.offset = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        recyclerView.getClass();
        int childAdapterPosition = RecyclerView.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = recyclerView.mAdapter;
        int itemCount = adapter != null ? adapter.getItemCount() : 0;
        int i = this.offset;
        int i2 = childAdapterPosition == 0 ? i * 2 : i;
        if (childAdapterPosition == itemCount - 1) {
            i *= 2;
        }
        rect.set(i2, 0, i, 0);
    }
}

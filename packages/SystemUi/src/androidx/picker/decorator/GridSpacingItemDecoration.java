package androidx.picker.decorator;

import android.graphics.Rect;
import android.view.View;
import androidx.picker.adapter.viewholder.GroupTitleViewHolder;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    public final int spacing;

    public GridSpacingItemDecoration(int i) {
        this.spacing = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        recyclerView.getClass();
        int childAdapterPosition = RecyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition == -1 || recyclerView.mAdapter == null || (recyclerView.getChildViewHolder(view) instanceof GroupTitleViewHolder)) {
            return;
        }
        RecyclerView.LayoutManager layoutManager$1 = recyclerView.getLayoutManager$1();
        if (layoutManager$1 instanceof GridLayoutManager) {
            int i = ((GridLayoutManager) layoutManager$1).mSpanCount;
            int i2 = childAdapterPosition % i;
            int i3 = this.spacing;
            rect.left = i3 - ((i2 * i3) / i);
            rect.right = ((i2 + 1) * i3) / i;
            int i4 = i3 / 2;
            rect.top = i4;
            rect.bottom = i4;
        }
    }
}

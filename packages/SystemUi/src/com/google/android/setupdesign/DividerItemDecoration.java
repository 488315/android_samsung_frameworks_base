package com.google.android.setupdesign;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DividerItemDecoration extends RecyclerView.ItemDecoration {
    public Drawable divider;
    public int dividerCondition;
    public int dividerHeight;
    public int dividerIntrinsicHeight;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DividedViewHolder {
        boolean isDividerAllowedAbove();

        boolean isDividerAllowedBelow();
    }

    public DividerItemDecoration() {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (shouldDrawDividerBelow(view, recyclerView)) {
            int i = this.dividerHeight;
            if (i == 0) {
                i = this.dividerIntrinsicHeight;
            }
            rect.bottom = i;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
        if (this.divider == null) {
            return;
        }
        int childCount = recyclerView.getChildCount();
        int width = recyclerView.getWidth();
        int i = this.dividerHeight;
        if (i == 0) {
            i = this.dividerIntrinsicHeight;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = recyclerView.getChildAt(i2);
            if (shouldDrawDividerBelow(childAt, recyclerView)) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                int height = childAt.getHeight() + ((int) childAt.getY());
                this.divider.setBounds(0, height, width, height + i);
                this.divider.draw(canvas);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean shouldDrawDividerBelow(View view, RecyclerView recyclerView) {
        RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(view);
        int layoutPosition = childViewHolder.getLayoutPosition();
        int itemCount = recyclerView.mAdapter.getItemCount() - 1;
        if (!(childViewHolder instanceof DividedViewHolder) || ((DividedViewHolder) childViewHolder).isDividerAllowedBelow()) {
            if (this.dividerCondition == 0) {
                return true;
            }
        } else if (this.dividerCondition == 1 || layoutPosition == itemCount) {
            return false;
        }
        if (layoutPosition < itemCount) {
            Object findViewHolderForPosition = recyclerView.findViewHolderForPosition(layoutPosition + 1, false);
            if (!(!(findViewHolderForPosition instanceof DividedViewHolder) || ((DividedViewHolder) findViewHolderForPosition).isDividerAllowedAbove())) {
                return false;
            }
        }
        return true;
    }

    public DividerItemDecoration(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R$styleable.SudDividerItemDecoration);
        Drawable drawable = obtainStyledAttributes.getDrawable(1);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        int i = obtainStyledAttributes.getInt(2, 0);
        obtainStyledAttributes.recycle();
        if (drawable != null) {
            this.dividerIntrinsicHeight = drawable.getIntrinsicHeight();
        } else {
            this.dividerIntrinsicHeight = 0;
        }
        this.divider = drawable;
        this.dividerHeight = dimensionPixelSize;
        this.dividerCondition = i;
    }
}

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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    public Drawable divider;
    public final int dividerCondition;
    public final int dividerHeight;
    public int dividerIntrinsicHeight;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DividedViewHolder {
        boolean isDividerAllowedAbove();

        boolean isDividerAllowedBelow();
    }

    public DividerItemDecoration() {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (shouldDrawDividerBelow$2(view, recyclerView)) {
            int i = this.dividerHeight;
            if (i == 0) {
                i = this.dividerIntrinsicHeight;
            }
            rect.bottom = i;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
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
            if (shouldDrawDividerBelow$2(childAt, recyclerView)) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                int height = childAt.getHeight() + ((int) childAt.getY());
                this.divider.setBounds(0, height, width, height + i);
                this.divider.draw(canvas);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0027, code lost:
    
        if (r5.dividerCondition == 0) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0022, code lost:
    
        if (r0 != r1) goto L14;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldDrawDividerBelow$2(android.view.View r6, androidx.recyclerview.widget.RecyclerView r7) {
        /*
            r5 = this;
            androidx.recyclerview.widget.RecyclerView$ViewHolder r6 = r7.getChildViewHolder(r6)
            int r0 = r6.getLayoutPosition()
            androidx.recyclerview.widget.RecyclerView$Adapter r1 = r7.mAdapter
            int r1 = r1.getItemCount()
            r2 = 1
            int r1 = r1 - r2
            boolean r3 = r6 instanceof com.google.android.setupdesign.DividerItemDecoration.DividedViewHolder
            r4 = 0
            if (r3 == 0) goto L25
            com.google.android.setupdesign.DividerItemDecoration$DividedViewHolder r6 = (com.google.android.setupdesign.DividerItemDecoration.DividedViewHolder) r6
            boolean r6 = r6.isDividerAllowedBelow()
            if (r6 == 0) goto L1e
            goto L25
        L1e:
            int r5 = r5.dividerCondition
            if (r5 == r2) goto L3e
            if (r0 != r1) goto L2a
            goto L3e
        L25:
            int r5 = r5.dividerCondition
            if (r5 != 0) goto L2a
            goto L3f
        L2a:
            if (r0 >= r1) goto L3f
            int r0 = r0 + r2
            androidx.recyclerview.widget.RecyclerView$ViewHolder r5 = r7.findViewHolderForPosition(r0, r4)
            boolean r6 = r5 instanceof com.google.android.setupdesign.DividerItemDecoration.DividedViewHolder
            if (r6 == 0) goto L3f
            com.google.android.setupdesign.DividerItemDecoration$DividedViewHolder r5 = (com.google.android.setupdesign.DividerItemDecoration.DividedViewHolder) r5
            boolean r5 = r5.isDividerAllowedAbove()
            if (r5 == 0) goto L3e
            goto L3f
        L3e:
            return r4
        L3f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupdesign.DividerItemDecoration.shouldDrawDividerBelow$2(android.view.View, androidx.recyclerview.widget.RecyclerView):boolean");
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

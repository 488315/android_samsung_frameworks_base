package com.android.systemui.wallet.ui;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;

public final class DotIndicatorDecoration extends RecyclerView.ItemDecoration {
    public WalletCardCarousel mCardCarousel;
    public final int mDotMargin;
    public final Paint mPaint = new Paint(1);
    public final int mSelectedColor;
    public final int mSelectedRadius;
    public final int mUnselectedColor;
    public final int mUnselectedRadius;

    public DotIndicatorDecoration(Context context) {
        this.mUnselectedRadius = context.getResources().getDimensionPixelSize(R.dimen.card_carousel_dot_unselected_radius);
        this.mSelectedRadius = context.getResources().getDimensionPixelSize(R.dimen.card_carousel_dot_selected_radius);
        this.mDotMargin = context.getResources().getDimensionPixelSize(R.dimen.card_carousel_dot_margin);
        this.mUnselectedColor = context.getColor(R.color.material_dynamic_neutral70);
        this.mSelectedColor = context.getColor(R.color.material_dynamic_neutral100);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        super.getItemOffsets(rect, view, recyclerView, state);
        if (recyclerView.mAdapter.getItemCount() > 1) {
            rect.bottom = view.getResources().getDimensionPixelSize(R.dimen.card_carousel_dot_offset);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00c9, code lost:
    
        if (r10.mEdgeToCenterDistance >= 0.0f) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00b7, code lost:
    
        r10 = 2.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00d6, code lost:
    
        r11 = r5 / 2.0f;
        r16.mPaint.setColor(androidx.core.graphics.ColorUtils.setAlphaComponent(androidx.core.graphics.ColorUtils.blendARGB(r11, r4, r15), 255));
        r4 = android.util.MathUtils.lerp(r8, r15, r11);
        r17.drawCircle(r4, 0.0f, r4, r16.mPaint);
        r17.translate(r4 * 2.0f, 0.0f);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00d3, code lost:
    
        if (r10.mEdgeToCenterDistance < 0.0f) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ab, code lost:
    
        if (r12.mEdgeToCenterDistance >= 0.0f) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b5, code lost:
    
        if (r12.mEdgeToCenterDistance < 0.0f) goto L29;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onDrawOver(android.graphics.Canvas r17, androidx.recyclerview.widget.RecyclerView r18, androidx.recyclerview.widget.RecyclerView.State r19) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallet.ui.DotIndicatorDecoration.onDrawOver(android.graphics.Canvas, androidx.recyclerview.widget.RecyclerView, androidx.recyclerview.widget.RecyclerView$State):void");
    }
}

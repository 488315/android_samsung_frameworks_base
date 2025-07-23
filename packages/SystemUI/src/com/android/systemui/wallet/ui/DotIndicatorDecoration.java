package com.android.systemui.wallet.ui;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00cf, code lost:
    
        if (r10.mEdgeToCenterDistance >= 0.0f) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00db, code lost:
    
        r10 = r5 / r16;
        r17.mPaint.setColor(androidx.core.graphics.ColorUtils.setAlphaComponent(androidx.core.graphics.ColorUtils.blendARGB(r10, r4, r15), 255));
        r4 = android.util.MathUtils.lerp(r8, r15, r10);
        r11 = r19;
        r18.drawCircle(r4, r11, r4, r17.mPaint);
        r18.translate(r4 * r16, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00d9, code lost:
    
        if (r10.mEdgeToCenterDistance < 0.0f) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00b1, code lost:
    
        if (r12.mEdgeToCenterDistance >= 0.0f) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00bb, code lost:
    
        if (r12.mEdgeToCenterDistance < 0.0f) goto L39;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onDrawOver(android.graphics.Canvas r18, androidx.recyclerview.widget.RecyclerView r19, androidx.recyclerview.widget.RecyclerView.State r20) {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallet.ui.DotIndicatorDecoration.onDrawOver(android.graphics.Canvas, androidx.recyclerview.widget.RecyclerView, androidx.recyclerview.widget.RecyclerView$State):void");
    }
}

package com.android.systemui.wallet.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.MathUtils;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;

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

    /* JADX WARN: Removed duplicated region for block: B:30:0x00be A[PHI: r19
      0x00be: PHI (r19v2 float) = (r19v1 float), (r19v1 float), (r19v4 float), (r19v4 float) binds: [B:26:0x00b5, B:28:0x00bb, B:36:0x00d3, B:38:0x00d9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00db A[PHI: r19
      0x00db: PHI (r19v3 float) = (r19v1 float), (r19v1 float), (r19v4 float), (r19v4 float) binds: [B:28:0x00bb, B:24:0x00b1, B:38:0x00d9, B:34:0x00cf] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        float f;
        float f2;
        super.onDrawOver(canvas, recyclerView, state);
        this.mCardCarousel = (WalletCardCarousel) recyclerView;
        int itemCount = recyclerView.mAdapter.getItemCount();
        int i = 1;
        if (itemCount <= 1) {
            return;
        }
        canvas.save();
        float width = recyclerView.getWidth() / 6.0f;
        float fMin = 1.0f - (Math.min(Math.abs(this.mCardCarousel.mEdgeToCenterDistance), width) / width);
        int i2 = this.mDotMargin;
        int i3 = this.mUnselectedRadius;
        int i4 = i3 * 2;
        int i5 = this.mSelectedRadius;
        float f3 = 2.0f;
        canvas.translate((recyclerView.getWidth() - ((i5 * 2) + (((itemCount - 2) * i4) + ((itemCount - 1) * i2)))) / 2.0f, recyclerView.getHeight() - i2);
        int i6 = 0;
        while (i6 < itemCount) {
            WalletCardCarousel walletCardCarousel = this.mCardCarousel;
            int i7 = (walletCardCarousel == null || walletCardCarousel.getLayoutDirection() == 0) ? i6 : (itemCount - i6) - i;
            WalletCardCarousel walletCardCarousel2 = this.mCardCarousel;
            int i8 = walletCardCarousel2.mCenteredAdapterPosition;
            int i9 = this.mSelectedColor;
            int i10 = this.mUnselectedColor;
            float f4 = f3;
            if (i8 == i7) {
                float f5 = fMin / f4;
                this.mPaint.setColor(ColorUtils.setAlphaComponent(ColorUtils.blendARGB(f5, i9, i10), 255));
                float fLerp = MathUtils.lerp(i5, i3, f5);
                canvas.drawCircle(fLerp, 0.0f, fLerp, this.mPaint);
                canvas.translate(fLerp * f4, 0.0f);
                f2 = 0.0f;
            } else if (walletCardCarousel2 == null || walletCardCarousel2.getLayoutDirection() == 0) {
                WalletCardCarousel walletCardCarousel3 = this.mCardCarousel;
                int i11 = walletCardCarousel3.mCenteredAdapterPosition;
                f = 0.0f;
                if ((i11 + 1 != i7 || walletCardCarousel3.mEdgeToCenterDistance < 0.0f) && (i11 - 1 != i7 || walletCardCarousel3.mEdgeToCenterDistance >= 0.0f)) {
                }
            } else {
                f = 0.0f;
                WalletCardCarousel walletCardCarousel4 = this.mCardCarousel;
                int i12 = walletCardCarousel4.mCenteredAdapterPosition;
                if ((i12 - 1 != i7 || walletCardCarousel4.mEdgeToCenterDistance < 0.0f) && (i12 + 1 != i7 || walletCardCarousel4.mEdgeToCenterDistance >= 0.0f)) {
                    f2 = f;
                    this.mPaint.setColor(i10);
                    float f6 = i3;
                    canvas.drawCircle(f6, f2, f6, this.mPaint);
                    canvas.translate(i4, f2);
                } else {
                    float f7 = fMin / f4;
                    this.mPaint.setColor(ColorUtils.setAlphaComponent(ColorUtils.blendARGB(f7, i10, i9), 255));
                    float fLerp2 = MathUtils.lerp(i3, i9, f7);
                    f2 = f;
                    canvas.drawCircle(fLerp2, f2, fLerp2, this.mPaint);
                    canvas.translate(fLerp2 * f4, f2);
                }
            }
            canvas.translate(i2, f2);
            i6++;
            f3 = f4;
            i = 1;
        }
        canvas.restore();
        this.mCardCarousel = null;
    }
}

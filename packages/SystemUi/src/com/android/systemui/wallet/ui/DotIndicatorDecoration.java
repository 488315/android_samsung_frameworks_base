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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00b7, code lost:
    
        if (r13.mEdgeToCenterDistance >= 0.0f) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00dc, code lost:
    
        r10 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c1, code lost:
    
        if (r13.mEdgeToCenterDistance < 0.0f) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00d0, code lost:
    
        if (r10.mEdgeToCenterDistance >= 0.0f) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00da, code lost:
    
        if (r10.mEdgeToCenterDistance < 0.0f) goto L48;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        boolean z;
        this.mCardCarousel = (WalletCardCarousel) recyclerView;
        int itemCount = recyclerView.mAdapter.getItemCount();
        int i = 1;
        if (itemCount <= 1) {
            return;
        }
        canvas.save();
        float width = recyclerView.getWidth() / 6.0f;
        float min = 1.0f - (Math.min(Math.abs(this.mCardCarousel.mEdgeToCenterDistance), width) / width);
        int i2 = this.mDotMargin;
        int i3 = this.mUnselectedRadius;
        int i4 = i3 * 2;
        int i5 = this.mSelectedRadius;
        float f = 2.0f;
        canvas.translate((recyclerView.getWidth() - ((i5 * 2) + (((itemCount - 2) * i4) + ((itemCount - 1) * i2)))) / 2.0f, recyclerView.getHeight() - i2);
        int i6 = 0;
        while (i6 < itemCount) {
            WalletCardCarousel walletCardCarousel = this.mCardCarousel;
            int i7 = ((walletCardCarousel != null && walletCardCarousel.getLayoutDirection() != 0) ? 0 : i) != 0 ? i6 : (itemCount - i6) - i;
            WalletCardCarousel walletCardCarousel2 = this.mCardCarousel;
            int i8 = walletCardCarousel2.mCenteredAdapterPosition == i7 ? i : 0;
            Paint paint = this.mPaint;
            int i9 = this.mSelectedColor;
            int i10 = this.mUnselectedColor;
            int i11 = itemCount;
            if (i8 != 0) {
                float f2 = min / f;
                paint.setColor(ColorUtils.setAlphaComponent(ColorUtils.blendARGB(f2, i9, i10), 255));
                float lerp = MathUtils.lerp(i5, i3, f2);
                canvas.drawCircle(lerp, 0.0f, lerp, paint);
                canvas.translate(lerp * f, 0.0f);
            } else {
                if (walletCardCarousel2 == null || walletCardCarousel2.getLayoutDirection() == 0) {
                    WalletCardCarousel walletCardCarousel3 = this.mCardCarousel;
                    int i12 = walletCardCarousel3.mCenteredAdapterPosition;
                    if (i12 + 1 == i7) {
                    }
                    if (i12 - 1 == i7) {
                    }
                    z = false;
                } else {
                    WalletCardCarousel walletCardCarousel4 = this.mCardCarousel;
                    int i13 = walletCardCarousel4.mCenteredAdapterPosition;
                    if (i13 - 1 == i7) {
                    }
                    if (i13 + 1 == i7) {
                    }
                    z = false;
                }
                if (z) {
                    f = 2.0f;
                    float f3 = min / 2.0f;
                    paint.setColor(ColorUtils.setAlphaComponent(ColorUtils.blendARGB(f3, i10, i9), 255));
                    float lerp2 = MathUtils.lerp(i3, i9, f3);
                    canvas.drawCircle(lerp2, 0.0f, lerp2, paint);
                    canvas.translate(lerp2 * 2.0f, 0.0f);
                } else {
                    f = 2.0f;
                    paint.setColor(i10);
                    float f4 = i3;
                    canvas.drawCircle(f4, 0.0f, f4, paint);
                    canvas.translate(i4, 0.0f);
                }
            }
            canvas.translate(i2, 0.0f);
            i6++;
            itemCount = i11;
            i = 1;
        }
        canvas.restore();
        this.mCardCarousel = null;
    }
}

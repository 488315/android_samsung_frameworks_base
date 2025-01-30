package androidx.cardview.widget;

import androidx.cardview.widget.CardView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CardViewApi21Impl {
    public final void updatePadding(CardView.C01101 c01101) {
        float f;
        CardView cardView = CardView.this;
        if (!cardView.mCompatPadding) {
            c01101.setShadowPadding(0, 0, 0, 0);
            return;
        }
        RoundRectDrawable roundRectDrawable = (RoundRectDrawable) c01101.mCardBackground;
        float f2 = roundRectDrawable.mPadding;
        float f3 = roundRectDrawable.mRadius;
        if (cardView.mPreventCornerOverlap) {
            f = (float) (((1.0d - RoundRectDrawableWithShadow.COS_45) * f3) + f2);
        } else {
            int i = RoundRectDrawableWithShadow.$r8$clinit;
            f = f2;
        }
        int ceil = (int) Math.ceil(f);
        int ceil2 = (int) Math.ceil(RoundRectDrawableWithShadow.calculateVerticalPadding(f2, f3, CardView.this.mPreventCornerOverlap));
        c01101.setShadowPadding(ceil, ceil2, ceil, ceil2);
    }
}

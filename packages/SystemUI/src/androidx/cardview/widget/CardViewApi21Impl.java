package androidx.cardview.widget;

import android.graphics.drawable.Drawable;
import androidx.cardview.widget.CardView;

/* loaded from: classes.dex */
public class CardViewApi21Impl {
    public final void updatePadding(CardView.AnonymousClass1 anonymousClass1) {
        if (!CardView.this.mCompatPadding) {
            anonymousClass1.setShadowPadding(0, 0, 0, 0);
            return;
        }
        Drawable drawable = anonymousClass1.mCardBackground;
        float f = ((RoundRectDrawable) drawable).mPadding;
        float f2 = ((RoundRectDrawable) drawable).mRadius;
        int iCeil = (int) Math.ceil(RoundRectDrawableWithShadow.calculateHorizontalPadding(f, f2, r4.mPreventCornerOverlap));
        int iCeil2 = (int) Math.ceil(RoundRectDrawableWithShadow.calculateVerticalPadding(f, f2, CardView.this.mPreventCornerOverlap));
        anonymousClass1.setShadowPadding(iCeil, iCeil2, iCeil, iCeil2);
    }
}

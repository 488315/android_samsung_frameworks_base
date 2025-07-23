package androidx.cardview.widget;

import android.graphics.drawable.Drawable;
import androidx.cardview.widget.CardView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int ceil = (int) Math.ceil(RoundRectDrawableWithShadow.calculateHorizontalPadding(f, f2, r4.mPreventCornerOverlap));
        int ceil2 = (int) Math.ceil(RoundRectDrawableWithShadow.calculateVerticalPadding(f, f2, CardView.this.mPreventCornerOverlap));
        anonymousClass1.setShadowPadding(ceil, ceil2, ceil, ceil2);
    }
}

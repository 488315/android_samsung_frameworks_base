package androidx.cardview.widget;

import android.content.res.ColorStateList;
import androidx.cardview.widget.CardView;

/* loaded from: classes.dex */
final class CardViewApi21Impl {
    private static RoundRectDrawable getCardBackground(CardView.C00741 c00741) {
        return (RoundRectDrawable) c00741.getCardBackground();
    }

    public final ColorStateList getBackgroundColor(CardViewDelegate cardViewDelegate) {
        return getCardBackground((CardView.C00741) cardViewDelegate).getColor();
    }

    public final float getMaxElevation(CardViewDelegate cardViewDelegate) {
        return getCardBackground((CardView.C00741) cardViewDelegate).getPadding();
    }

    public final float getRadius(CardViewDelegate cardViewDelegate) {
        return getCardBackground((CardView.C00741) cardViewDelegate).getRadius();
    }

    public final void setBackgroundColor(CardViewDelegate cardViewDelegate, ColorStateList colorStateList) {
        getCardBackground((CardView.C00741) cardViewDelegate).setColor(colorStateList);
    }

    public final void setMaxElevation(CardViewDelegate cardViewDelegate, float f) {
        CardView.C00741 c00741 = (CardView.C00741) cardViewDelegate;
        RoundRectDrawable cardBackground = getCardBackground(c00741);
        boolean useCompatPadding = CardView.this.getUseCompatPadding();
        CardView cardView = CardView.this;
        cardBackground.setPadding(f, useCompatPadding, cardView.getPreventCornerOverlap());
        if (!cardView.getUseCompatPadding()) {
            c00741.setShadowPadding(0, 0, 0, 0);
            return;
        }
        float maxElevation = getMaxElevation(c00741);
        float radius = getRadius(c00741);
        int ceil = (int) Math.ceil(RoundRectDrawableWithShadow.calculateHorizontalPadding(maxElevation, radius, cardView.getPreventCornerOverlap()));
        int ceil2 = (int) Math.ceil(RoundRectDrawableWithShadow.calculateVerticalPadding(maxElevation, radius, cardView.getPreventCornerOverlap()));
        c00741.setShadowPadding(ceil, ceil2, ceil, ceil2);
    }

    public final void setRadius(CardViewDelegate cardViewDelegate, float f) {
        getCardBackground((CardView.C00741) cardViewDelegate).setRadius(f);
    }
}

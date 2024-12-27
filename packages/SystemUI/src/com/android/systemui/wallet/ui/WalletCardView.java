package com.android.systemui.wallet.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.cardview.widget.CardView;
import androidx.cardview.widget.CardViewApi21Impl;
import androidx.cardview.widget.RoundRectDrawable;
import com.android.systemui.R;

public class WalletCardView extends CardView {
    public final Paint mBorderPaint;

    public WalletCardView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        CardViewApi21Impl cardViewApi21Impl = CardView.IMPL;
        CardView.AnonymousClass1 anonymousClass1 = this.mCardViewDelegate;
        cardViewApi21Impl.getClass();
        float f = ((RoundRectDrawable) anonymousClass1.mCardBackground).mRadius;
        canvas.drawRoundRect(0.0f, 0.0f, getWidth(), getHeight(), f, f, this.mBorderPaint);
    }

    public WalletCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mBorderPaint = paint;
        paint.setColor(context.getColor(R.color.wallet_card_border));
        paint.setStrokeWidth(context.getResources().getDimension(R.dimen.wallet_card_border_width));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }
}

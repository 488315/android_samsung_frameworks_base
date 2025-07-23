package com.android.wm.shell.shared.bubbles;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DropTargetView extends View {
    public final float cornerRadius;
    public final RectF rect;
    public final Paint rectPaint;
    public final Paint strokePaint;

    public DropTargetView(Context context) {
        super(context);
        Paint paint = new Paint(1);
        paint.setColor(context.getColor(R.color.secondary_text_material_dark));
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(89);
        this.rectPaint = paint;
        Paint paint2 = new Paint(1);
        paint2.setColor(context.getColor(R.color.secondary_text_material_dark));
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(TypedValue.applyDimension(1, 2, getContext().getResources().getDisplayMetrics()));
        this.strokePaint = paint2;
        this.cornerRadius = TypedValue.applyDimension(1, 28, getContext().getResources().getDisplayMetrics());
        this.rect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        canvas.save();
        RectF rectF = this.rect;
        float f = this.cornerRadius;
        canvas.drawRoundRect(rectF, f, f, this.rectPaint);
        RectF rectF2 = this.rect;
        float f2 = this.cornerRadius;
        canvas.drawRoundRect(rectF2, f2, f2, this.strokePaint);
        canvas.restore();
    }
}

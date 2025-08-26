package androidx.recyclerview.sesl.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.ColorUtils;

/* loaded from: classes.dex */
public final class SeslFastScrollerBgDrawable extends Drawable {
    public final Paint paint;

    public SeslFastScrollerBgDrawable() {
        Paint paint = new Paint(1);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(8);
        paint.setColor(ColorUtils.setAlphaComponent(-16777216, 255));
        this.paint = paint;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        this.paint.setStrokeWidth(0.0f);
        float f = 2;
        canvas.drawLine(canvas.getWidth() / f, this.paint.getStrokeWidth() / f, canvas.getWidth() / f, canvas.getHeight() - (this.paint.getStrokeWidth() / f), this.paint);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
    }
}

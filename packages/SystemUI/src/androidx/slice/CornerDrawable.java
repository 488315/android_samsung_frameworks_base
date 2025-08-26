package androidx.slice;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;

/* loaded from: classes.dex */
public class CornerDrawable extends InsetDrawable {
    public final float mCornerRadius;
    public final Path mPath;

    public CornerDrawable(Drawable drawable, float f) {
        super(drawable, 0);
        this.mPath = new Path();
        this.mCornerRadius = f;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        int iSave = canvas.save();
        canvas.clipPath(this.mPath);
        super.draw(canvas);
        canvas.restoreToCount(iSave);
    }

    @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        Path path = this.mPath;
        if (path != null) {
            path.reset();
            Path path2 = this.mPath;
            RectF rectF = new RectF(rect);
            float f = this.mCornerRadius;
            path2.addRoundRect(rectF, f, f, Path.Direction.CW);
        }
        super.onBoundsChange(rect);
    }
}

package android.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public abstract class DynamicDrawableSpan extends ReplacementSpan {
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_CENTER = 2;
    private WeakReference<Drawable> mDrawableRef;
    protected final int mVerticalAlignment;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlignmentType {
    }

    public abstract Drawable getDrawable();

    public DynamicDrawableSpan() {
        this.mVerticalAlignment = 0;
    }

    protected DynamicDrawableSpan(int i) {
        this.mVerticalAlignment = i;
    }

    public int getVerticalAlignment() {
        return this.mVerticalAlignment;
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        Rect bounds = getCachedDrawable().getBounds();
        if (fontMetricsInt != null) {
            fontMetricsInt.ascent = -bounds.bottom;
            fontMetricsInt.descent = 0;
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = 0;
        }
        return bounds.right;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        Drawable cachedDrawable = getCachedDrawable();
        canvas.save();
        int i6 = i5 - cachedDrawable.getBounds().bottom;
        int i7 = this.mVerticalAlignment;
        if (i7 == 1) {
            i6 -= paint.getFontMetricsInt().descent;
        } else if (i7 == 2) {
            i6 = (i3 + ((i5 - i3) / 2)) - (cachedDrawable.getBounds().height() / 2);
        }
        canvas.translate(f, i6);
        cachedDrawable.draw(canvas);
        canvas.restore();
    }

    private Drawable getCachedDrawable() {
        WeakReference<Drawable> weakReference = this.mDrawableRef;
        Drawable drawable = weakReference != null ? weakReference.get() : null;
        if (drawable != null) {
            return drawable;
        }
        Drawable drawable2 = getDrawable();
        this.mDrawableRef = new WeakReference<>(drawable2);
        return drawable2;
    }

    public String toString() {
        return "DynamicDrawableSpan{verticalAlignment=" + getVerticalAlignment() + ", drawable=" + getDrawable() + '}';
    }
}

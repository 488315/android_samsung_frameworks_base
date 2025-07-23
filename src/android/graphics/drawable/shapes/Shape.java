package android.graphics.drawable.shapes;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class Shape implements Cloneable {
    private float mHeight;
    private float mWidth;

    public abstract void draw(Canvas canvas, Paint paint);

    public void getOutline(Outline outline) {
    }

    public boolean hasAlpha() {
        return true;
    }

    protected void onResize(float f, float f2) {
    }

    public final float getWidth() {
        return this.mWidth;
    }

    public final float getHeight() {
        return this.mHeight;
    }

    public final void resize(float f, float f2) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (this.mWidth == f && this.mHeight == f2) {
            return;
        }
        this.mWidth = f;
        this.mHeight = f2;
        onResize(f, f2);
    }

    @Override // 
    /* renamed from: clone */
    public Shape mo1487clone() throws CloneNotSupportedException {
        return (Shape) super.clone();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Shape shape = (Shape) obj;
            if (Float.compare(shape.mWidth, this.mWidth) == 0 && Float.compare(shape.mHeight, this.mHeight) == 0) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.mWidth), Float.valueOf(this.mHeight));
    }
}

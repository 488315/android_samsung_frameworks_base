package android.graphics.drawable.shapes;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Objects;

/* loaded from: classes.dex */
public class RectShape extends Shape {
    private RectF mRect = new RectF();

    @Override // android.graphics.drawable.shapes.Shape
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(this.mRect, paint);
    }

    @Override // android.graphics.drawable.shapes.Shape
    public void getOutline(Outline outline) {
        RectF rectFRect = rect();
        outline.setRect((int) Math.ceil(rectFRect.left), (int) Math.ceil(rectFRect.top), (int) Math.floor(rectFRect.right), (int) Math.floor(rectFRect.bottom));
    }

    @Override // android.graphics.drawable.shapes.Shape
    protected void onResize(float f, float f2) {
        this.mRect.set(0.0f, 0.0f, f, f2);
    }

    protected final RectF rect() {
        return this.mRect;
    }

    @Override // android.graphics.drawable.shapes.Shape
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public RectShape mo1491clone() throws CloneNotSupportedException {
        RectShape rectShape = (RectShape) super.mo1491clone();
        rectShape.mRect = new RectF(this.mRect);
        return rectShape;
    }

    @Override // android.graphics.drawable.shapes.Shape
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && super.equals(obj)) {
            return Objects.equals(this.mRect, ((RectShape) obj).mRect);
        }
        return false;
    }

    @Override // android.graphics.drawable.shapes.Shape
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.mRect);
    }
}

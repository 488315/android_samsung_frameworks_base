package android.graphics.drawable.shapes;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes.dex */
public class RoundRectShape extends RectShape {
    private float[] mInnerRadii;
    private RectF mInnerRect;
    private RectF mInset;
    private float[] mOuterRadii;
    private Path mPath;

    public RoundRectShape(float[] fArr, RectF rectF, float[] fArr2) {
        if (fArr != null && fArr.length < 8) {
            throw new ArrayIndexOutOfBoundsException("outer radii must have >= 8 values");
        }
        if (fArr2 != null && fArr2.length < 8) {
            throw new ArrayIndexOutOfBoundsException("inner radii must have >= 8 values");
        }
        this.mOuterRadii = fArr;
        this.mInset = rectF;
        this.mInnerRadii = fArr2;
        if (rectF != null) {
            this.mInnerRect = new RectF();
        }
        this.mPath = new Path();
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawPath(this.mPath, paint);
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public void getOutline(Outline outline) {
        float f;
        if (this.mInnerRect != null) {
            return;
        }
        float[] fArr = this.mOuterRadii;
        if (fArr != null) {
            f = fArr[0];
            for (int i = 1; i < 8; i++) {
                if (this.mOuterRadii[i] != f) {
                    outline.setPath(this.mPath);
                    return;
                }
            }
        } else {
            f = 0.0f;
        }
        RectF rect = rect();
        outline.setRoundRect((int) Math.ceil(rect.left), (int) Math.ceil(rect.top), (int) Math.floor(rect.right), (int) Math.floor(rect.bottom), f);
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    protected void onResize(float f, float f2) {
        super.onResize(f, f2);
        RectF rect = rect();
        this.mPath.reset();
        float[] fArr = this.mOuterRadii;
        if (fArr != null) {
            this.mPath.addRoundRect(rect, fArr, Path.Direction.CW);
        } else {
            this.mPath.addRect(rect, Path.Direction.CW);
        }
        RectF rectF = this.mInnerRect;
        if (rectF != null) {
            rectF.set(rect.left + this.mInset.left, rect.top + this.mInset.top, rect.right - this.mInset.right, rect.bottom - this.mInset.bottom);
            if (this.mInnerRect.width() >= f || this.mInnerRect.height() >= f2) {
                return;
            }
            float[] fArr2 = this.mInnerRadii;
            if (fArr2 != null) {
                this.mPath.addRoundRect(this.mInnerRect, fArr2, Path.Direction.CCW);
            } else {
                this.mPath.addRect(this.mInnerRect, Path.Direction.CCW);
            }
        }
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    /* renamed from: clone */
    public RoundRectShape mo1487clone() throws CloneNotSupportedException {
        RoundRectShape roundRectShape = (RoundRectShape) super.mo1487clone();
        float[] fArr = this.mOuterRadii;
        roundRectShape.mOuterRadii = fArr != null ? (float[]) fArr.clone() : null;
        float[] fArr2 = this.mInnerRadii;
        roundRectShape.mInnerRadii = fArr2 != null ? (float[]) fArr2.clone() : null;
        roundRectShape.mInset = new RectF(this.mInset);
        roundRectShape.mInnerRect = new RectF(this.mInnerRect);
        roundRectShape.mPath = new Path(this.mPath);
        return roundRectShape;
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        RoundRectShape roundRectShape = (RoundRectShape) obj;
        return Arrays.equals(this.mOuterRadii, roundRectShape.mOuterRadii) && Objects.equals(this.mInset, roundRectShape.mInset) && Arrays.equals(this.mInnerRadii, roundRectShape.mInnerRadii) && Objects.equals(this.mInnerRect, roundRectShape.mInnerRect) && Objects.equals(this.mPath, roundRectShape.mPath);
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public int hashCode() {
        return (((Objects.hash(Integer.valueOf(super.hashCode()), this.mInset, this.mInnerRect, this.mPath) * 31) + Arrays.hashCode(this.mOuterRadii)) * 31) + Arrays.hashCode(this.mInnerRadii);
    }
}

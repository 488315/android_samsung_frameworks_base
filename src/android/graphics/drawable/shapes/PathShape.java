package android.graphics.drawable.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import java.util.Objects;

/* loaded from: classes.dex */
public class PathShape extends Shape {
    private Path mPath;
    private float mScaleX;
    private float mScaleY;
    private final float mStdHeight;
    private final float mStdWidth;

    public PathShape(Path path, float f, float f2) {
        this.mPath = path;
        this.mStdWidth = f;
        this.mStdHeight = f2;
    }

    @Override // android.graphics.drawable.shapes.Shape
    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.scale(this.mScaleX, this.mScaleY);
        canvas.drawPath(this.mPath, paint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.shapes.Shape
    protected void onResize(float f, float f2) {
        this.mScaleX = f / this.mStdWidth;
        this.mScaleY = f2 / this.mStdHeight;
    }

    @Override // android.graphics.drawable.shapes.Shape
    /* renamed from: clone */
    public PathShape mo1487clone() throws CloneNotSupportedException {
        PathShape pathShape = (PathShape) super.mo1487clone();
        pathShape.mPath = new Path(this.mPath);
        return pathShape;
    }

    @Override // android.graphics.drawable.shapes.Shape
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        PathShape pathShape = (PathShape) obj;
        return Float.compare(pathShape.mStdWidth, this.mStdWidth) == 0 && Float.compare(pathShape.mStdHeight, this.mStdHeight) == 0 && Float.compare(pathShape.mScaleX, this.mScaleX) == 0 && Float.compare(pathShape.mScaleY, this.mScaleY) == 0 && Objects.equals(this.mPath, pathShape.mPath);
    }

    @Override // android.graphics.drawable.shapes.Shape
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), Float.valueOf(this.mStdWidth), Float.valueOf(this.mStdHeight), this.mPath, Float.valueOf(this.mScaleX), Float.valueOf(this.mScaleY));
    }
}

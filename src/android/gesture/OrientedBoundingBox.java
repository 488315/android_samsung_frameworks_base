package android.gesture;

import android.graphics.Matrix;
import android.graphics.Path;

/* loaded from: classes.dex */
public class OrientedBoundingBox {
    public final float centerX;
    public final float centerY;
    public final float height;
    public final float orientation;
    public final float squareness;
    public final float width;

    OrientedBoundingBox(float f, float f2, float f3, float f4, float f5) {
        this.orientation = f;
        this.width = f4;
        this.height = f5;
        this.centerX = f2;
        this.centerY = f3;
        float f6 = f4 / f5;
        if (f6 > 1.0f) {
            this.squareness = 1.0f / f6;
        } else {
            this.squareness = f6;
        }
    }

    public Path toPath() {
        Path path = new Path();
        float[] fArr = {(-this.width) / 2.0f, this.height / 2.0f};
        Matrix matrix = new Matrix();
        matrix.setRotate(this.orientation);
        matrix.postTranslate(this.centerX, this.centerY);
        matrix.mapPoints(fArr);
        path.moveTo(fArr[0], fArr[1]);
        fArr[0] = (-this.width) / 2.0f;
        fArr[1] = (-this.height) / 2.0f;
        matrix.mapPoints(fArr);
        path.lineTo(fArr[0], fArr[1]);
        fArr[0] = this.width / 2.0f;
        fArr[1] = (-this.height) / 2.0f;
        matrix.mapPoints(fArr);
        path.lineTo(fArr[0], fArr[1]);
        fArr[0] = this.width / 2.0f;
        fArr[1] = this.height / 2.0f;
        matrix.mapPoints(fArr);
        path.lineTo(fArr[0], fArr[1]);
        path.close();
        return path;
    }
}

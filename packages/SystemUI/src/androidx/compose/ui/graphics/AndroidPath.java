package androidx.compose.ui.graphics;

import android.graphics.Path;
import android.graphics.RectF;
import androidx.compose.ui.geometry.Rect;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidPath implements Path {
    public final android.graphics.Path internalPath;
    public android.graphics.Matrix mMatrix;
    public float[] radii;
    public RectF rectF;

    public AndroidPath() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final Rect getBounds() {
        if (this.rectF == null) {
            this.rectF = new RectF();
        }
        RectF rectF = this.rectF;
        rectF.getClass();
        this.internalPath.computeBounds(rectF, true);
        return new Rect(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    /* renamed from: op-N5in7k0, reason: not valid java name */
    public final boolean m443opN5in7k0(Path path, Path path2, int i) {
        PathOperation.Companion.getClass();
        Path.Op op = i == 0 ? Path.Op.DIFFERENCE : i == PathOperation.Intersect ? Path.Op.INTERSECT : i == PathOperation.ReverseDifference ? Path.Op.REVERSE_DIFFERENCE : i == PathOperation.Union ? Path.Op.UNION : Path.Op.XOR;
        android.graphics.Path path3 = this.internalPath;
        if (!(path instanceof AndroidPath)) {
            throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
        }
        android.graphics.Path path4 = ((AndroidPath) path).internalPath;
        if (path2 instanceof AndroidPath) {
            return path3.op(path4, ((AndroidPath) path2).internalPath, op);
        }
        throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
    }

    public final void reset() {
        this.internalPath.reset();
    }

    /* renamed from: setFillType-oQ8Xj4U, reason: not valid java name */
    public final void m444setFillTypeoQ8Xj4U(int i) {
        android.graphics.Path path = this.internalPath;
        PathFillType.Companion.getClass();
        path.setFillType(i == PathFillType.EvenOdd ? Path.FillType.EVEN_ODD : Path.FillType.WINDING);
    }

    /* renamed from: transform-58bKbWc, reason: not valid java name */
    public final void m445transform58bKbWc(float[] fArr) {
        if (this.mMatrix == null) {
            this.mMatrix = new android.graphics.Matrix();
        }
        android.graphics.Matrix matrix = this.mMatrix;
        matrix.getClass();
        AndroidMatrixConversions_androidKt.m432setFromEL8BTi8(matrix, fArr);
        android.graphics.Path path = this.internalPath;
        android.graphics.Matrix matrix2 = this.mMatrix;
        matrix2.getClass();
        path.transform(matrix2);
    }

    /* renamed from: translate-k-4lQ0M, reason: not valid java name */
    public final void m446translatek4lQ0M(long j) {
        android.graphics.Matrix matrix = this.mMatrix;
        if (matrix == null) {
            this.mMatrix = new android.graphics.Matrix();
        } else {
            matrix.getClass();
            matrix.reset();
        }
        android.graphics.Matrix matrix2 = this.mMatrix;
        matrix2.getClass();
        matrix2.setTranslate(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
        android.graphics.Path path = this.internalPath;
        android.graphics.Matrix matrix3 = this.mMatrix;
        matrix3.getClass();
        path.transform(matrix3);
    }

    public AndroidPath(android.graphics.Path path) {
        this.internalPath = path;
    }

    public /* synthetic */ AndroidPath(android.graphics.Path path, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new android.graphics.Path() : path);
    }
}

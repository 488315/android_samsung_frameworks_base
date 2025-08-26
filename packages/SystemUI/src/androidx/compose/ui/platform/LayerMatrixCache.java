package androidx.compose.ui.platform;

import android.graphics.Matrix;
import androidx.compose.ui.graphics.AndroidMatrixConversions_androidKt;
import androidx.compose.ui.graphics.MatrixKt;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final class LayerMatrixCache<T> {
    public Matrix androidMatrixCache;
    public final Function2 getMatrix;
    public boolean isDirty;
    public boolean isInverseDirty;
    public final float[] matrixCache = androidx.compose.ui.graphics.Matrix.m483constructorimpl$default();
    public final float[] inverseMatrixCache = androidx.compose.ui.graphics.Matrix.m483constructorimpl$default();
    public boolean isInverseValid = true;
    public boolean isIdentity = true;

    public LayerMatrixCache(Function2 function2) {
        this.getMatrix = function2;
    }

    /* renamed from: calculateInverseMatrix-bWbORWo, reason: not valid java name */
    public final float[] m707calculateInverseMatrixbWbORWo(Object obj) {
        boolean z = this.isInverseDirty;
        float[] fArr = this.inverseMatrixCache;
        if (z) {
            this.isInverseValid = InvertMatrixKt.m706invertToJiSxe2E(m708calculateMatrixGrdbGEg(obj), fArr);
            this.isInverseDirty = false;
        }
        if (this.isInverseValid) {
            return fArr;
        }
        return null;
    }

    /* renamed from: calculateMatrix-GrdbGEg, reason: not valid java name */
    public final float[] m708calculateMatrixGrdbGEg(Object obj) {
        boolean z = this.isDirty;
        float[] fArr = this.matrixCache;
        if (!z) {
            return fArr;
        }
        Matrix matrix = this.androidMatrixCache;
        if (matrix == null) {
            matrix = new Matrix();
            this.androidMatrixCache = matrix;
        }
        this.getMatrix.invoke(obj, matrix);
        AndroidMatrixConversions_androidKt.m435setFromtUYjHk(matrix, fArr);
        this.isDirty = false;
        this.isIdentity = MatrixKt.m491isIdentity58bKbWc(fArr);
        return fArr;
    }

    public final void invalidate() {
        this.isDirty = true;
        this.isInverseDirty = true;
    }
}

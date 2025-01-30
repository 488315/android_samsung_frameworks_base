package com.samsung.android.graphics.spr.document.attribute;

import android.graphics.Matrix;
import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.attribute.impl.SprMatrix;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprAttributeMatrix extends SprAttributeBase {
    private final SprAttributeMatrix mIntrinsic;
    public Matrix matrix;

    public SprAttributeMatrix() {
        super((byte) 64);
        this.mIntrinsic = (SprAttributeMatrix) super.mIntrinsic;
        this.matrix = new Matrix();
    }

    public SprAttributeMatrix(Matrix matrix) {
        super((byte) 64);
        this.mIntrinsic = (SprAttributeMatrix) super.mIntrinsic;
        this.matrix = matrix;
    }

    public SprAttributeMatrix(SprInputStream in) throws IOException {
        super((byte) 64);
        this.mIntrinsic = (SprAttributeMatrix) super.mIntrinsic;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.matrix = SprMatrix.fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream out) throws IOException {
        SprMatrix.toSPR(out, this.matrix);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 24;
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    /* renamed from: clone */
    public SprAttributeMatrix mo8903clone() throws CloneNotSupportedException {
        SprAttributeMatrix attribute = (SprAttributeMatrix) super.mo8903clone();
        attribute.matrix = new Matrix(this.matrix);
        return attribute;
    }

    public void reset() {
        this.matrix.set(this.mIntrinsic.matrix);
    }
}

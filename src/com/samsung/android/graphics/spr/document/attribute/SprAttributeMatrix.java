package com.samsung.android.graphics.spr.document.attribute;

import android.graphics.Matrix;
import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.attribute.impl.SprMatrix;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprAttributeMatrix extends SprAttributeBase {
    private final SprAttributeMatrix mIntrinsic;
    public Matrix matrix;

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 24;
    }

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

    public SprAttributeMatrix(SprInputStream sprInputStream) throws IOException {
        super((byte) 64);
        this.mIntrinsic = (SprAttributeMatrix) super.mIntrinsic;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.matrix = SprMatrix.fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        SprMatrix.toSPR(dataOutputStream, this.matrix);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    /* renamed from: clone */
    public SprAttributeMatrix mo9233clone() throws CloneNotSupportedException {
        SprAttributeMatrix sprAttributeMatrix = (SprAttributeMatrix) super.mo9233clone();
        sprAttributeMatrix.matrix = new Matrix(this.matrix);
        return sprAttributeMatrix;
    }

    public void reset() {
        this.matrix.set(this.mIntrinsic.matrix);
    }
}

package com.samsung.android.nexus.egl.world;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.samsung.android.nexus.egl.core.Shader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class BaseWorld {
    public final float[] mMVMatrix = new float[16];
    public final float[] mMVPMatrix = new float[16];
    public final float[] mModelMatrix;
    public float[] mProjectionMatrix;
    public final float[] mViewMatrix;

    public BaseWorld() {
        float[] fArr = new float[16];
        this.mProjectionMatrix = fArr;
        float[] fArr2 = new float[16];
        this.mViewMatrix = fArr2;
        float[] fArr3 = new float[16];
        this.mModelMatrix = fArr3;
        Matrix.setIdentityM(fArr, 0);
        Matrix.setIdentityM(fArr2, 0);
        Matrix.setIdentityM(fArr3, 0);
    }

    public final void sendParams(Shader shader) {
        if (shader == null) {
            return;
        }
        Matrix.multiplyMM(this.mMVMatrix, 0, this.mViewMatrix, 0, this.mModelMatrix, 0);
        Matrix.multiplyMM(this.mMVPMatrix, 0, this.mProjectionMatrix, 0, this.mMVMatrix, 0);
        int i = shader.modelMatrixHandle;
        if (i >= 0) {
            GLES20.glUniformMatrix4fv(i, 1, false, this.mMVMatrix, 0);
        }
        int i2 = shader.mvpMatrixHandle;
        if (i2 >= 0) {
            GLES20.glUniformMatrix4fv(i2, 1, false, this.mMVPMatrix, 0);
        }
    }
}

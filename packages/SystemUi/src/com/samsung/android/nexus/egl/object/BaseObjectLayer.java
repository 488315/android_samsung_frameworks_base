package com.samsung.android.nexus.egl.object;

import android.opengl.GLES20;
import com.samsung.android.nexus.base.layer.BaseLayer;
import com.samsung.android.nexus.base.layer.NexusLayerParams;
import com.samsung.android.nexus.egl.core.Shader;
import com.samsung.android.nexus.egl.world.WorldPerspective;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class BaseObjectLayer extends BaseLayer {
    public final int[] mElementHandles;
    public final String[] mElementNameList;
    public final int[] mElementSizeList;
    public int mGlobalAlphaHandle;
    public int mStrideSize;
    public WorldPerspective mWorld = null;
    public Shader mShader = null;
    public FloatBuffer mVertexBuffer = null;
    public ShortBuffer mIndexBuffer = null;
    public int mIndexCnt = 0;
    public int mTotalElementSize = 0;
    public final float mGlobalAlpha = 1.0f;

    public BaseObjectLayer() {
        this.mStrideSize = 0;
        int[] generateElementSizeList = generateElementSizeList();
        this.mElementSizeList = generateElementSizeList;
        String[] generateElementNameList = generateElementNameList();
        this.mElementNameList = generateElementNameList;
        this.mElementHandles = new int[generateElementNameList.length];
        for (int i : generateElementSizeList) {
            this.mTotalElementSize += i;
        }
        this.mStrideSize = this.mTotalElementSize * 4;
    }

    public void clear() {
        FloatBuffer floatBuffer = this.mVertexBuffer;
        if (floatBuffer != null) {
            floatBuffer.clear();
        }
        ShortBuffer shortBuffer = this.mIndexBuffer;
        if (shortBuffer != null) {
            shortBuffer.clear();
        }
        this.mIndexCnt = 0;
    }

    public abstract void drawElementInner();

    public String[] generateElementNameList() {
        return new String[]{"aPosition"};
    }

    public int[] generateElementSizeList() {
        return new int[]{3};
    }

    public void init() {
        if (this.mWorld == null) {
            this.mWorld = new WorldPerspective(getNexusContext().mWidth, getNexusContext().mHeight);
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onCreate() {
        init();
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onDestroy() {
        clear();
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onDraw() {
        onDrawElements();
    }

    public final void onDrawElements() {
        int[] iArr;
        Shader shader;
        int[] iArr2;
        int i;
        if (this.mWorld == null || (iArr = this.mElementSizeList) == null || this.mElementNameList == null || (shader = this.mShader) == null || this.mVertexBuffer == null) {
            return;
        }
        GLES20.glUseProgram(shader.mProgramId);
        this.mWorld.sendParams(this.mShader);
        int i2 = this.mGlobalAlphaHandle;
        if (i2 >= 0) {
            GLES20.glUniform1f(i2, this.mGlobalAlpha);
        }
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int length = iArr.length;
            iArr2 = this.mElementHandles;
            if (i3 >= length) {
                break;
            }
            int i5 = iArr2[i3];
            if (i5 < 0) {
                i = iArr[i3];
            } else {
                this.mVertexBuffer.position(i4);
                GLES20.glVertexAttribPointer(i5, iArr[i3], 5126, false, this.mStrideSize, (Buffer) this.mVertexBuffer);
                GLES20.glEnableVertexAttribArray(i5);
                i = iArr[i3];
            }
            i4 += i;
            i3++;
        }
        drawElementInner();
        for (int i6 = 0; i6 < iArr.length; i6++) {
            int i7 = iArr2[i6];
            if (i7 >= 0) {
                GLES20.glDisableVertexAttribArray(i7);
            }
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onLayerParamsChanged(NexusLayerParams nexusLayerParams) {
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onVisibilityChanged(Boolean bool) {
    }
}

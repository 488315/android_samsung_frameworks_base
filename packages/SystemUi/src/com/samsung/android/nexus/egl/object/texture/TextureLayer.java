package com.samsung.android.nexus.egl.object.texture;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import com.android.systemui.R;
import com.samsung.android.nexus.egl.core.Shader;
import com.samsung.android.nexus.egl.object.BaseObjectLayer;
import com.samsung.android.nexus.egl.utils.EglUtils;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class TextureLayer extends BaseObjectLayer {
    public static final float[] DEFAULT_COORD = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
    public final float alpha;
    public int alphaHandle;
    public FloatBuffer coordBuffer;
    public int coordHandle;
    public final float[] mCoordinates;
    public final Bitmap mImage;
    public final short[] mIndices;
    public TextureData mTextureData;
    public final float[] mVertices;
    public int textureHandle;

    public TextureLayer(Bitmap bitmap) {
        this(bitmap, null, null, null);
    }

    @Override // com.samsung.android.nexus.egl.object.BaseObjectLayer
    public final void clear() {
        super.clear();
        this.coordBuffer.clear();
        TextureData textureData = this.mTextureData;
        if (textureData != null) {
            int i = textureData.handle;
            GLES20.glDeleteTextures(1, new int[]{i}, 0);
            EglUtils.checkGlError("glDeleteTextures handle = " + i);
        }
    }

    @Override // com.samsung.android.nexus.egl.object.BaseObjectLayer
    public final void drawElementInner() {
        TextureData textureData = this.mTextureData;
        if ((textureData == null || textureData.handle == -1) ? false : true) {
            GLES20.glUniform1f(this.alphaHandle, this.alpha);
            GLES20.glActiveTexture(this.mTextureData.glId);
            GLES20.glBindTexture(3553, this.mTextureData.handle);
            GLES20.glUniform1i(this.textureHandle, this.mTextureData.glIndex);
            GLES20.glEnableVertexAttribArray(this.coordHandle);
            this.coordBuffer.position(0);
            GLES20.glVertexAttribPointer(this.coordHandle, 2, 5126, false, 0, (Buffer) this.coordBuffer);
            GLES20.glDrawElements(4, this.mIndexCnt, 5123, this.mIndexBuffer);
            GLES20.glBindTexture(3553, 0);
            GLES20.glDisableVertexAttribArray(this.coordHandle);
        }
    }

    @Override // com.samsung.android.nexus.egl.object.BaseObjectLayer
    public String[] generateElementNameList() {
        return new String[]{"aPosition", "aNormal"};
    }

    @Override // com.samsung.android.nexus.egl.object.BaseObjectLayer
    public int[] generateElementSizeList() {
        return new int[]{3, 3};
    }

    @Override // com.samsung.android.nexus.egl.object.BaseObjectLayer
    public void init() {
        super.init();
        if (this.mShader == null) {
            this.mShader = new Shader(getAppContext(), R.raw.basic_texture_vert_shader, R.raw.basic_texture_frag_shader);
            loadHandles();
        }
        float[] fArr = this.mVertices;
        if (fArr != null && fArr.length > 0) {
            if (fArr.length != 0) {
                FloatBuffer floatBuffer = this.mVertexBuffer;
                if (floatBuffer != null) {
                    floatBuffer.clear();
                }
                int length = fArr.length / this.mTotalElementSize;
                ByteBuffer allocateDirect = ByteBuffer.allocateDirect(fArr.length << 2);
                allocateDirect.order(ByteOrder.nativeOrder());
                FloatBuffer asFloatBuffer = allocateDirect.asFloatBuffer();
                asFloatBuffer.put(fArr);
                asFloatBuffer.position(0);
                this.mVertexBuffer = asFloatBuffer;
            }
            float[] fArr2 = this.mCoordinates;
            if (fArr2 != null && fArr2.length != 0) {
                FloatBuffer floatBuffer2 = this.coordBuffer;
                if (floatBuffer2 != null) {
                    floatBuffer2.clear();
                }
                ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(fArr2.length << 2);
                allocateDirect2.order(ByteOrder.nativeOrder());
                FloatBuffer asFloatBuffer2 = allocateDirect2.asFloatBuffer();
                asFloatBuffer2.put(fArr2);
                asFloatBuffer2.position(0);
                this.coordBuffer = asFloatBuffer2;
            }
            short[] sArr = this.mIndices;
            if (sArr != null) {
                ShortBuffer shortBuffer = this.mIndexBuffer;
                if (shortBuffer != null) {
                    shortBuffer.clear();
                }
                ByteBuffer allocateDirect3 = ByteBuffer.allocateDirect(sArr.length << 1);
                allocateDirect3.order(ByteOrder.nativeOrder());
                ShortBuffer asShortBuffer = allocateDirect3.asShortBuffer();
                asShortBuffer.put(sArr);
                asShortBuffer.position(0);
                this.mIndexBuffer = asShortBuffer;
                this.mIndexCnt = sArr.length;
            }
        }
        this.mTextureData = new TextureData(this.mImage, 0);
    }

    public final void loadHandles() {
        String[] strArr = this.mElementNameList;
        int length = strArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            this.mElementHandles[i2] = this.mShader.loadHandle(strArr[i]);
            i++;
            i2++;
        }
        this.mGlobalAlphaHandle = this.mShader.loadHandle("uGlobalAlpha");
        this.textureHandle = this.mShader.loadHandle("uTextureOrig");
        this.coordHandle = this.mShader.loadHandle("aTextureCoordOrig");
        this.alphaHandle = this.mShader.loadHandle("uTextureAlphaOrig");
    }

    public TextureLayer(Bitmap bitmap, float[] fArr, float[] fArr2, short[] sArr) {
        this.coordBuffer = null;
        this.mTextureData = null;
        this.alpha = 1.0f;
        this.mImage = bitmap;
        this.mVertices = fArr;
        this.mCoordinates = fArr2;
        this.mIndices = sArr;
    }
}

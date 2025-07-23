package com.samsung.android.nexus.egl.object.texture;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.Matrix;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class RectangleTextureLayer extends TextureLayer {
    public final Rect mObjectRect;
    public final float[] mRotationMatrix;
    public static final float[] DEFAULT_VERTICES = {-0.5f, 0.5f, 0.0f, 0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f};
    public static final short[] DEFAULT_INDICES = {0, 2, 3, 0, 3, 1};

    public RectangleTextureLayer(Rect rect) {
        this(rect, null);
    }

    @Override // com.samsung.android.nexus.egl.object.texture.TextureLayer, com.samsung.android.nexus.egl.object.BaseObjectLayer
    public final String[] generateElementNameList() {
        return new String[]{"aPosition"};
    }

    @Override // com.samsung.android.nexus.egl.object.texture.TextureLayer, com.samsung.android.nexus.egl.object.BaseObjectLayer
    public final int[] generateElementSizeList() {
        return new int[]{3};
    }

    @Override // com.samsung.android.nexus.egl.object.texture.TextureLayer, com.samsung.android.nexus.egl.object.BaseObjectLayer
    public final void init() {
        super.init();
        Matrix.setIdentityM(this.mRotationMatrix, 0);
        Matrix.rotateM(this.mRotationMatrix, 0, 0.0f, 0.0f, 0.0f, 0.0f);
        float abs = Math.abs(this.mObjectRect.width()) / 2.0f;
        float abs2 = Math.abs(this.mObjectRect.height()) / 2.0f;
        float f = -abs;
        float[] fArr = {f, abs2, 0.0f, 0.0f};
        float[] fArr2 = {abs, abs2, 0.0f, 0.0f};
        float f2 = -abs2;
        float[] fArr3 = {f, f2, 0.0f, 0.0f};
        float[] fArr4 = {abs, f2, 0.0f, 0.0f};
        Rect rect = this.mObjectRect;
        float f3 = ((rect.left + rect.right) / 2.0f) + 0.0f;
        float f4 = ((rect.top + rect.bottom) / 2.0f) + 0.0f;
        this.mVertexBuffer.position(0);
        this.mVertexBuffer.put(new float[]{fArr[0] + f3, fArr[1] + f4, 0.0f, fArr2[0] + f3, fArr2[1] + f4, 0.0f, fArr3[0] + f3, fArr3[1] + f4, 0.0f, fArr4[0] + f3, fArr4[1] + f4, 0.0f});
    }

    public RectangleTextureLayer(Rect rect, Bitmap bitmap) {
        super(bitmap, DEFAULT_VERTICES, TextureLayer.DEFAULT_COORD, DEFAULT_INDICES);
        this.mRotationMatrix = new float[16];
        this.mObjectRect = rect;
    }
}

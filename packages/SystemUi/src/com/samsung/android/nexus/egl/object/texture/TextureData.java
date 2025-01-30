package com.samsung.android.nexus.egl.object.texture;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.samsung.android.nexus.egl.utils.EglUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class TextureData {
    public final int glId;
    public final int glIndex;
    public final int handle;

    public TextureData(Bitmap bitmap, int i) {
        this.handle = -1;
        this.glId = 0;
        this.glIndex = 0;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        EglUtils.checkGlError("glGenTextures = " + iArr[0]);
        int i2 = iArr[0];
        if (i2 >= 0 && !bitmap.isRecycled()) {
            GLES20.glBindTexture(3553, i2);
            EglUtils.checkGlError("glBindTexture handle = " + i2);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
            EglUtils.checkGlError("glTexParameteri");
            GLUtils.texImage2D(3553, 0, bitmap, 0);
            EglUtils.checkGlError("texImage2D");
            GLES20.glBindTexture(3553, 0);
            EglUtils.checkGlError("glBindTexture : release");
        }
        this.handle = iArr[0];
        this.glIndex = i;
        int i3 = i + 33984;
        this.glId = i3 > 34015 ? 34015 : i3;
    }
}

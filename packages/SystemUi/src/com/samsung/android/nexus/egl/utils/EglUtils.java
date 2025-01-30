package com.samsung.android.nexus.egl.utils;

import android.opengl.GLES20;
import android.support.v4.media.AbstractC0000x2c234b15;
import com.samsung.android.nexus.base.utils.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EglUtils {
    public static void checkGlError(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, ": glError 0x");
            m2m.append(Integer.toHexString(glGetError));
            Log.m261e("EglUtils", m2m.toString());
        }
    }
}

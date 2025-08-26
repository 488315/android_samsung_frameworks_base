package com.samsung.android.nexus.egl.utils;

import android.opengl.GLES20;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.samsung.android.nexus.base.utils.Log;

/* loaded from: classes4.dex */
public class EglUtils {
    public static void checkGlError(String str) {
        int iGlGetError = GLES20.glGetError();
        if (iGlGetError != 0) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, ": glError 0x");
            sbM.append(Integer.toHexString(iGlGetError));
            Log.e("EglUtils", sbM.toString());
        }
    }
}

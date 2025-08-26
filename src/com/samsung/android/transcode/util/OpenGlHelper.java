package com.samsung.android.transcode.util;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

/* loaded from: classes6.dex */
public class OpenGlHelper {
    private OpenGlHelper() throws InstantiationException {
        throw new InstantiationException("do not instatiate");
    }

    public static int createProgram(String str, String str2) {
        int iCompileShader = compileShader(GLES20.GL_VERTEX_SHADER, str);
        if (iCompileShader == 0) {
            return 0;
        }
        int iCompileShader2 = compileShader(GLES20.GL_FRAGMENT_SHADER, str2);
        if (iCompileShader2 == 0) {
            GLES20.glDeleteShader(iCompileShader);
            return 0;
        }
        int iLinkProgram = linkProgram(iCompileShader, iCompileShader2);
        GLES20.glDeleteShader(iCompileShader);
        GLES20.glDeleteShader(iCompileShader2);
        return iLinkProgram;
    }

    private static int compileShader(int i, String str) {
        int iGlCreateShader = GLES20.glCreateShader(i);
        if (iGlCreateShader == 0) {
            checkGLError("shader type " + i + " creation failded");
            return 0;
        }
        GLES20.glShaderSource(iGlCreateShader, str);
        GLES20.glCompileShader(iGlCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(iGlCreateShader, GLES20.GL_COMPILE_STATUS, iArr, 0);
        if (iArr[0] != 0) {
            return iGlCreateShader;
        }
        LogS.e("TranscodeLib", "Could not compile shader " + i + " " + GLES20.glGetShaderInfoLog(iGlCreateShader));
        GLES20.glDeleteShader(iGlCreateShader);
        return 0;
    }

    private static int linkProgram(int i, int i2) {
        int iGlCreateProgram = GLES20.glCreateProgram();
        if (iGlCreateProgram == 0) {
            checkGLError("CreateProgram failed");
            return 0;
        }
        GLES20.glAttachShader(iGlCreateProgram, i);
        GLES20.glAttachShader(iGlCreateProgram, i2);
        GLES20.glLinkProgram(iGlCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(iGlCreateProgram, GLES20.GL_LINK_STATUS, iArr, 0);
        if (iArr[0] != 0) {
            return iGlCreateProgram;
        }
        LogS.e("TranscodeLib", "Couldn't link program :" + GLES20.glGetProgramInfoLog(iGlCreateProgram));
        GLES20.glDeleteProgram(iGlCreateProgram);
        return 0;
    }

    public static int loadTextureOES() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        int i = iArr[0];
        if (i == 0) {
            LogS.e("TranscodeLib", "Could not create new opengl oes texture object");
            return 0;
        }
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, i);
        if (checkGLError("glBindTexture error") != 0) {
            GLES20.glDeleteTextures(1, iArr, 0);
            return 0;
        }
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 10241, 9729.0f);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 10240, 9729.0f);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 10242, 33071);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 10243, 33071);
        if (checkGLError("External OES parameter set error.") != 0) {
            GLES20.glDeleteTextures(1, iArr, 0);
            return 0;
        }
        return iArr[0];
    }

    public static void deleteTexture(int i) {
        GLES20.glDeleteTextures(1, new int[]{i}, 0);
    }

    public static int checkGLError(String str) {
        while (true) {
            int iGlGetError = GLES20.glGetError();
            if (iGlGetError == 0) {
                return iGlGetError;
            }
            LogS.e("TranscodeLib", str + ": glError " + iGlGetError);
        }
    }
}

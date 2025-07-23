package com.samsung.android.transcode.util;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

/* loaded from: classes6.dex */
public class OpenGlHelper {
    private OpenGlHelper() throws InstantiationException {
        throw new InstantiationException("do not instatiate");
    }

    public static int createProgram(String str, String str2) {
        int compileShader = compileShader(GLES20.GL_VERTEX_SHADER, str);
        if (compileShader == 0) {
            return 0;
        }
        int compileShader2 = compileShader(GLES20.GL_FRAGMENT_SHADER, str2);
        if (compileShader2 == 0) {
            GLES20.glDeleteShader(compileShader);
            return 0;
        }
        int linkProgram = linkProgram(compileShader, compileShader2);
        GLES20.glDeleteShader(compileShader);
        GLES20.glDeleteShader(compileShader2);
        return linkProgram;
    }

    private static int compileShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        if (glCreateShader == 0) {
            checkGLError("shader type " + i + " creation failded");
            return 0;
        }
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, GLES20.GL_COMPILE_STATUS, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        LogS.e("TranscodeLib", "Could not compile shader " + i + " " + GLES20.glGetShaderInfoLog(glCreateShader));
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }

    private static int linkProgram(int i, int i2) {
        int glCreateProgram = GLES20.glCreateProgram();
        if (glCreateProgram == 0) {
            checkGLError("CreateProgram failed");
            return 0;
        }
        GLES20.glAttachShader(glCreateProgram, i);
        GLES20.glAttachShader(glCreateProgram, i2);
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, GLES20.GL_LINK_STATUS, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateProgram;
        }
        LogS.e("TranscodeLib", "Couldn't link program :" + GLES20.glGetProgramInfoLog(glCreateProgram));
        GLES20.glDeleteProgram(glCreateProgram);
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
            int glGetError = GLES20.glGetError();
            if (glGetError == 0) {
                return glGetError;
            }
            LogS.e("TranscodeLib", str + ": glError " + glGetError);
        }
    }
}

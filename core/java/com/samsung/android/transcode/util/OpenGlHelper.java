package com.samsung.android.transcode.util;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

public class OpenGlHelper {
    private OpenGlHelper() throws InstantiationException {
        throw new InstantiationException("do not instatiate");
    }

    public static int createProgram(String vertexShaderSource, String fragmentShaderSource) {
        int vertexShaderId = compileShader(GLES20.GL_VERTEX_SHADER, vertexShaderSource);
        if (vertexShaderId == 0) {
            return 0;
        }
        int fragmentShaderId = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderSource);
        if (fragmentShaderId == 0) {
            GLES20.glDeleteShader(vertexShaderId);
            return 0;
        }
        int programId = linkProgram(vertexShaderId, fragmentShaderId);
        GLES20.glDeleteShader(vertexShaderId);
        GLES20.glDeleteShader(fragmentShaderId);
        return programId;
    }

    private static int compileShader(int shaderType, String shaderSource) {
        int shaderId = GLES20.glCreateShader(shaderType);
        if (shaderId == 0) {
            checkGLError("shader type " + shaderType + " creation failded");
            return 0;
        }
        GLES20.glShaderSource(shaderId, shaderSource);
        GLES20.glCompileShader(shaderId);
        int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            LogS.e(
                    "TranscodeLib",
                    "Could not compile shader "
                            + shaderType
                            + " "
                            + GLES20.glGetShaderInfoLog(shaderId));
            GLES20.glDeleteShader(shaderId);
            return 0;
        }
        return shaderId;
    }

    private static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        int programId = GLES20.glCreateProgram();
        if (programId == 0) {
            checkGLError("CreateProgram failed");
            return 0;
        }
        GLES20.glAttachShader(programId, vertexShaderId);
        GLES20.glAttachShader(programId, fragmentShaderId);
        GLES20.glLinkProgram(programId);
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            LogS.e(
                    "TranscodeLib",
                    "Couldn't link program :" + GLES20.glGetProgramInfoLog(programId));
            GLES20.glDeleteProgram(programId);
            return 0;
        }
        return programId;
    }

    public static int loadTextureOES() {
        int[] textureObjectIds = new int[1];
        GLES20.glGenTextures(1, textureObjectIds, 0);
        if (textureObjectIds[0] == 0) {
            LogS.e("TranscodeLib", "Could not create new opengl oes texture object");
            return 0;
        }
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureObjectIds[0]);
        if (checkGLError("glBindTexture error") != 0) {
            GLES20.glDeleteTextures(1, textureObjectIds, 0);
            return 0;
        }
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 10241, 9729.0f);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 10240, 9729.0f);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 10242, 33071);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 10243, 33071);
        if (checkGLError("External OES parameter set error.") != 0) {
            GLES20.glDeleteTextures(1, textureObjectIds, 0);
            return 0;
        }
        return textureObjectIds[0];
    }

    public static void deleteTexture(int textureId) {
        GLES20.glDeleteTextures(1, new int[] {textureId}, 0);
    }

    public static int checkGLError(String operation) {
        while (true) {
            int error = GLES20.glGetError();
            if (error == 0) {
                return error;
            }
            LogS.e("TranscodeLib", operation + ": glError " + error);
        }
    }
}

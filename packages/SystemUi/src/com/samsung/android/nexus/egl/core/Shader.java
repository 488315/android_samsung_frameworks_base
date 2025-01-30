package com.samsung.android.nexus.egl.core;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.text.TextUtils;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.egl.utils.EglUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Shader {
    public int mFragmentShaderId;
    public final Map mHandleMap;
    public int mProgramId;
    public int mVertexShaderId;
    public final int modelMatrixHandle;
    public final int mvpMatrixHandle;

    public Shader(Context context, int i, int i2) {
        this(context, loadProgramFromRawResource(i, context.getResources()), loadProgramFromRawResource(i2, context.getResources()));
    }

    public static String loadProgramFromRawResource(int i, Resources resources) {
        try {
            InputStream openRawResource = resources.openRawResource(i);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    byte[] bArr = new byte[openRawResource.available()];
                    openRawResource.read(bArr);
                    byteArrayOutputStream.write(bArr);
                    byteArrayOutputStream.close();
                    openRawResource.close();
                    String byteArrayOutputStream2 = byteArrayOutputStream.toString();
                    byteArrayOutputStream.close();
                    openRawResource.close();
                    return byteArrayOutputStream2;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            Log.m261e("Shader", "Load program : " + e);
            return null;
        }
    }

    public static int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        EglUtils.checkGlError("glCreateShader type = " + i + ", id = " + glCreateShader);
        if (glCreateShader == 0) {
            return glCreateShader;
        }
        GLES20.glShaderSource(glCreateShader, str);
        EglUtils.checkGlError("glShaderSource id = " + glCreateShader);
        GLES20.glCompileShader(glCreateShader);
        EglUtils.checkGlError("glCompileShader id = " + glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        Log.m261e("Shader", "Could not compile shader " + i + ":");
        Log.m261e("Shader", GLES20.glGetShaderInfoLog(glCreateShader));
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }

    public final int getHandle(String str) {
        Integer num = (Integer) ((HashMap) this.mHandleMap).get(str);
        if (num == null || num.intValue() < 0) {
            num = Integer.valueOf(loadHandle(str));
        }
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public final int loadHandle(String str) {
        char charAt = str.charAt(0);
        Map map = this.mHandleMap;
        if (charAt == 'u') {
            int glGetUniformLocation = GLES20.glGetUniformLocation(this.mProgramId, str);
            Log.m262i("Shader", "load uniform handle for " + str + " = " + glGetUniformLocation);
            ((HashMap) map).put(str, Integer.valueOf(glGetUniformLocation));
            return glGetUniformLocation;
        }
        if (str.charAt(0) != 'a') {
            return -1;
        }
        int glGetAttribLocation = GLES20.glGetAttribLocation(this.mProgramId, str);
        Log.m262i("Shader", "load attribute handle for " + str + " = " + glGetAttribLocation);
        ((HashMap) map).put(str, Integer.valueOf(glGetAttribLocation));
        return glGetAttribLocation;
    }

    public Shader(Context context, String str, String str2) {
        this.mProgramId = 0;
        this.mVertexShaderId = 0;
        this.mFragmentShaderId = 0;
        this.mHandleMap = new HashMap();
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        context.getResources();
        this.mVertexShaderId = loadShader(35633, str);
        this.mFragmentShaderId = loadShader(35632, str2);
        this.mProgramId = GLES20.glCreateProgram();
        EglUtils.checkGlError("glCreateProgram id = " + this.mProgramId);
        int i = this.mProgramId;
        if (i != 0) {
            GLES20.glAttachShader(i, this.mVertexShaderId);
            EglUtils.checkGlError("glAttachShader : vertex");
            GLES20.glAttachShader(this.mProgramId, this.mFragmentShaderId);
            EglUtils.checkGlError("glAttachShader : fragment");
            GLES20.glLinkProgram(this.mProgramId);
            EglUtils.checkGlError("glLinkProgram");
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(this.mProgramId, 35714, iArr, 0);
            if (iArr[0] != 1) {
                Log.m261e("Shader", "Could not link program: ");
                Log.m261e("Shader", GLES20.glGetProgramInfoLog(this.mProgramId));
                GLES20.glDeleteProgram(this.mProgramId);
                this.mProgramId = 0;
            }
        }
        this.mProgramId = this.mProgramId;
        this.modelMatrixHandle = loadHandle("uModelMatrix");
        loadHandle("uViewMatrix");
        loadHandle("uProjectionMatrix");
        this.mvpMatrixHandle = loadHandle("uMvpMatrix");
        loadHandle("uLightPos");
        loadHandle("uDiffuseColor");
    }
}

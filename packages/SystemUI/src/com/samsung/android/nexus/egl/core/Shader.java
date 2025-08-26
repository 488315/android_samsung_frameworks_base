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

/* loaded from: classes4.dex */
public class Shader {
    public final int mFragmentShaderId;
    public final Map mHandleMap;
    public final int mProgramId;
    public final int mVertexShaderId;
    public final int modelMatrixHandle;
    public final int mvpMatrixHandle;

    public Shader(Context context, int i, int i2) {
        this(context, loadProgramFromRawResource(context.getResources(), i), loadProgramFromRawResource(context.getResources(), i2));
    }

    public static String loadProgramFromRawResource(Resources resources, int i) throws Resources.NotFoundException, IOException {
        try {
            InputStream inputStreamOpenRawResource = resources.openRawResource(i);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    byte[] bArr = new byte[inputStreamOpenRawResource.available()];
                    inputStreamOpenRawResource.read(bArr);
                    byteArrayOutputStream.write(bArr);
                    byteArrayOutputStream.close();
                    inputStreamOpenRawResource.close();
                    String string = byteArrayOutputStream.toString();
                    byteArrayOutputStream.close();
                    inputStreamOpenRawResource.close();
                    return string;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            Log.e("Shader", "Load program : " + e);
            return null;
        }
    }

    public static int loadShader(int i, String str) {
        int iGlCreateShader = GLES20.glCreateShader(i);
        EglUtils.checkGlError("glCreateShader type = " + i + ", id = " + iGlCreateShader);
        if (iGlCreateShader != 0) {
            GLES20.glShaderSource(iGlCreateShader, str);
            EglUtils.checkGlError("glShaderSource id = " + iGlCreateShader);
            GLES20.glCompileShader(iGlCreateShader);
            EglUtils.checkGlError("glCompileShader id = " + iGlCreateShader);
            int[] iArr = new int[1];
            GLES20.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
            if (iArr[0] == 0) {
                Log.e("Shader", "Could not compile shader " + i + ":");
                Log.e("Shader", GLES20.glGetShaderInfoLog(iGlCreateShader));
                GLES20.glDeleteShader(iGlCreateShader);
                return 0;
            }
        }
        return iGlCreateShader;
    }

    public final int getHandle(String str) {
        Integer numValueOf = (Integer) ((HashMap) this.mHandleMap).get(str);
        if (numValueOf == null || numValueOf.intValue() < 0) {
            numValueOf = Integer.valueOf(loadHandle(str));
        }
        return numValueOf.intValue();
    }

    public final int loadHandle(String str) {
        if (str.charAt(0) == 'u') {
            int iGlGetUniformLocation = GLES20.glGetUniformLocation(this.mProgramId, str);
            Log.i("Shader", "load uniform handle for " + str + " = " + iGlGetUniformLocation);
            ((HashMap) this.mHandleMap).put(str, Integer.valueOf(iGlGetUniformLocation));
            return iGlGetUniformLocation;
        }
        if (str.charAt(0) != 'a') {
            return -1;
        }
        int iGlGetAttribLocation = GLES20.glGetAttribLocation(this.mProgramId, str);
        Log.i("Shader", "load attribute handle for " + str + " = " + iGlGetAttribLocation);
        ((HashMap) this.mHandleMap).put(str, Integer.valueOf(iGlGetAttribLocation));
        return iGlGetAttribLocation;
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
                Log.e("Shader", "Could not link program: ");
                Log.e("Shader", GLES20.glGetProgramInfoLog(this.mProgramId));
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

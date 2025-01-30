package com.android.systemui.wallpaper.glwallpaper;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ImageGLProgram {
    public final Context mContext;
    public int mProgramHandle;

    public ImageGLProgram(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static int getShaderHandle(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        if (glCreateShader == 0) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Create shader failed, type=", i, "ImageGLProgram");
            return 0;
        }
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        return glCreateShader;
    }

    public final String getShaderResource(int i) {
        Resources resources = this.mContext.getResources();
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resources.openRawResource(i)));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                    sb.append("\n");
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            bufferedReader.close();
        } catch (Resources.NotFoundException | IOException e) {
            Log.d("ImageGLProgram", "Can not read the shader source", e);
            sb = null;
        }
        return sb == null ? "" : sb.toString();
    }
}

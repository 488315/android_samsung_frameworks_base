package android.opengl;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.content.smartclip.SemSmartClipMetaTagType;
import java.io.IOException;
import java.io.Writer;
import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGL11;

/* loaded from: classes3.dex */
class EGLLogWrapper implements EGL11 {
    private int mArgCount;
    boolean mCheckError;
    private EGL10 mEgl10;
    Writer mLog;
    boolean mLogArgumentNames;

    public EGLLogWrapper(EGL egl, int i, Writer writer) {
        this.mEgl10 = (EGL10) egl;
        this.mLog = writer;
        this.mLogArgumentNames = (i & 4) != 0;
        this.mCheckError = (i & 1) != 0;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglChooseConfig(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, int[] iArr, javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr, int i, int[] iArr2) {
        begin("eglChooseConfig");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("attrib_list", iArr);
        arg("config_size", i);
        end();
        boolean eglChooseConfig = this.mEgl10.eglChooseConfig(eGLDisplay, iArr, eGLConfigArr, i, iArr2);
        arg("configs", (Object[]) eGLConfigArr);
        arg("num_config", iArr2);
        returns(eglChooseConfig);
        checkError();
        return eglChooseConfig;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglCopyBuffers(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface, Object obj) {
        begin("eglCopyBuffers");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("surface", eGLSurface);
        arg("native_pixmap", obj);
        end();
        boolean eglCopyBuffers = this.mEgl10.eglCopyBuffers(eGLDisplay, eGLSurface, obj);
        returns(eglCopyBuffers);
        checkError();
        return eglCopyBuffers;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLContext eglCreateContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, javax.microedition.khronos.egl.EGLContext eGLContext, int[] iArr) {
        begin("eglCreateContext");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("share_context", eGLContext);
        arg("attrib_list", iArr);
        end();
        javax.microedition.khronos.egl.EGLContext eglCreateContext = this.mEgl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        returns(eglCreateContext);
        checkError();
        return eglCreateContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglCreatePbufferSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, int[] iArr) {
        begin("eglCreatePbufferSurface");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("attrib_list", iArr);
        end();
        javax.microedition.khronos.egl.EGLSurface eglCreatePbufferSurface = this.mEgl10.eglCreatePbufferSurface(eGLDisplay, eGLConfig, iArr);
        returns(eglCreatePbufferSurface);
        checkError();
        return eglCreatePbufferSurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglCreatePixmapSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, Object obj, int[] iArr) {
        begin("eglCreatePixmapSurface");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("native_pixmap", obj);
        arg("attrib_list", iArr);
        end();
        javax.microedition.khronos.egl.EGLSurface eglCreatePixmapSurface = this.mEgl10.eglCreatePixmapSurface(eGLDisplay, eGLConfig, obj, iArr);
        returns(eglCreatePixmapSurface);
        checkError();
        return eglCreatePixmapSurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglCreateWindowSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, Object obj, int[] iArr) {
        begin("eglCreateWindowSurface");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("native_window", obj);
        arg("attrib_list", iArr);
        end();
        javax.microedition.khronos.egl.EGLSurface eglCreateWindowSurface = this.mEgl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, iArr);
        returns(eglCreateWindowSurface);
        checkError();
        return eglCreateWindowSurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglDestroyContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLContext eGLContext) {
        begin("eglDestroyContext");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg(SemSmartClipMetaTagType.CONTEXT, eGLContext);
        end();
        boolean eglDestroyContext = this.mEgl10.eglDestroyContext(eGLDisplay, eGLContext);
        returns(eglDestroyContext);
        checkError();
        return eglDestroyContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglDestroySurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface) {
        begin("eglDestroySurface");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("surface", eGLSurface);
        end();
        boolean eglDestroySurface = this.mEgl10.eglDestroySurface(eGLDisplay, eGLSurface);
        returns(eglDestroySurface);
        checkError();
        return eglDestroySurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglGetConfigAttrib(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, int i, int[] iArr) {
        begin("eglGetConfigAttrib");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("attribute", i);
        end();
        boolean eglGetConfigAttrib = this.mEgl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, iArr);
        arg("value", iArr);
        returns(eglGetConfigAttrib);
        checkError();
        return false;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglGetConfigs(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr, int i, int[] iArr) {
        begin("eglGetConfigs");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config_size", i);
        end();
        boolean eglGetConfigs = this.mEgl10.eglGetConfigs(eGLDisplay, eGLConfigArr, i, iArr);
        arg("configs", (Object[]) eGLConfigArr);
        arg("num_config", iArr);
        returns(eglGetConfigs);
        checkError();
        return eglGetConfigs;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLContext eglGetCurrentContext() {
        begin("eglGetCurrentContext");
        end();
        javax.microedition.khronos.egl.EGLContext eglGetCurrentContext = this.mEgl10.eglGetCurrentContext();
        returns(eglGetCurrentContext);
        checkError();
        return eglGetCurrentContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLDisplay eglGetCurrentDisplay() {
        begin("eglGetCurrentDisplay");
        end();
        javax.microedition.khronos.egl.EGLDisplay eglGetCurrentDisplay = this.mEgl10.eglGetCurrentDisplay();
        returns(eglGetCurrentDisplay);
        checkError();
        return eglGetCurrentDisplay;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglGetCurrentSurface(int i) {
        begin("eglGetCurrentSurface");
        arg("readdraw", i);
        end();
        javax.microedition.khronos.egl.EGLSurface eglGetCurrentSurface = this.mEgl10.eglGetCurrentSurface(i);
        returns(eglGetCurrentSurface);
        checkError();
        return eglGetCurrentSurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLDisplay eglGetDisplay(Object obj) {
        begin("eglGetDisplay");
        arg("native_display", obj);
        end();
        javax.microedition.khronos.egl.EGLDisplay eglGetDisplay = this.mEgl10.eglGetDisplay(obj);
        returns(eglGetDisplay);
        checkError();
        return eglGetDisplay;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public int eglGetError() {
        begin("eglGetError");
        end();
        int eglGetError = this.mEgl10.eglGetError();
        returns(getErrorString(eglGetError));
        return eglGetError;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglInitialize(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, int[] iArr) {
        begin("eglInitialize");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        end();
        boolean eglInitialize = this.mEgl10.eglInitialize(eGLDisplay, iArr);
        returns(eglInitialize);
        arg("major_minor", iArr);
        checkError();
        return eglInitialize;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglMakeCurrent(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface, javax.microedition.khronos.egl.EGLSurface eGLSurface2, javax.microedition.khronos.egl.EGLContext eGLContext) {
        begin("eglMakeCurrent");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("draw", eGLSurface);
        arg("read", eGLSurface2);
        arg(SemSmartClipMetaTagType.CONTEXT, eGLContext);
        end();
        boolean eglMakeCurrent = this.mEgl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface2, eGLContext);
        returns(eglMakeCurrent);
        checkError();
        return eglMakeCurrent;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglQueryContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLContext eGLContext, int i, int[] iArr) {
        begin("eglQueryContext");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg(SemSmartClipMetaTagType.CONTEXT, eGLContext);
        arg("attribute", i);
        end();
        boolean eglQueryContext = this.mEgl10.eglQueryContext(eGLDisplay, eGLContext, i, iArr);
        returns(iArr[0]);
        returns(eglQueryContext);
        checkError();
        return eglQueryContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public String eglQueryString(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, int i) {
        begin("eglQueryString");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("name", i);
        end();
        String eglQueryString = this.mEgl10.eglQueryString(eGLDisplay, i);
        returns(eglQueryString);
        checkError();
        return eglQueryString;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglQuerySurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface, int i, int[] iArr) {
        begin("eglQuerySurface");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("surface", eGLSurface);
        arg("attribute", i);
        end();
        boolean eglQuerySurface = this.mEgl10.eglQuerySurface(eGLDisplay, eGLSurface, i, iArr);
        returns(iArr[0]);
        returns(eglQuerySurface);
        checkError();
        return eglQuerySurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglReleaseThread() {
        begin("eglReleaseThread");
        end();
        boolean eglReleaseThread = this.mEgl10.eglReleaseThread();
        returns(eglReleaseThread);
        checkError();
        return eglReleaseThread;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglSwapBuffers(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface) {
        begin("eglSwapBuffers");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("surface", eGLSurface);
        end();
        boolean eglSwapBuffers = this.mEgl10.eglSwapBuffers(eGLDisplay, eGLSurface);
        returns(eglSwapBuffers);
        checkError();
        return eglSwapBuffers;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglTerminate(javax.microedition.khronos.egl.EGLDisplay eGLDisplay) {
        begin("eglTerminate");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        end();
        boolean eglTerminate = this.mEgl10.eglTerminate(eGLDisplay);
        returns(eglTerminate);
        checkError();
        return eglTerminate;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglWaitGL() {
        begin("eglWaitGL");
        end();
        boolean eglWaitGL = this.mEgl10.eglWaitGL();
        returns(eglWaitGL);
        checkError();
        return eglWaitGL;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglWaitNative(int i, Object obj) {
        begin("eglWaitNative");
        arg(TextToSpeech.Engine.KEY_PARAM_ENGINE, i);
        arg("bindTarget", obj);
        end();
        boolean eglWaitNative = this.mEgl10.eglWaitNative(i, obj);
        returns(eglWaitNative);
        checkError();
        return eglWaitNative;
    }

    private void checkError() {
        int eglGetError = this.mEgl10.eglGetError();
        if (eglGetError != 12288) {
            String str = "eglError: " + getErrorString(eglGetError);
            logLine(str);
            if (this.mCheckError) {
                throw new GLException(eglGetError, str);
            }
        }
    }

    private void logLine(String str) {
        log(str + '\n');
    }

    private void log(String str) {
        try {
            this.mLog.write(str);
        } catch (IOException unused) {
        }
    }

    private void begin(String str) {
        log(str + '(');
        this.mArgCount = 0;
    }

    private void arg(String str, String str2) {
        int i = this.mArgCount;
        this.mArgCount = i + 1;
        if (i > 0) {
            log(", ");
        }
        if (this.mLogArgumentNames) {
            log(str + "=");
        }
        log(str2);
    }

    private void end() {
        log(");\n");
        flush();
    }

    private void flush() {
        try {
            this.mLog.flush();
        } catch (IOException unused) {
            this.mLog = null;
        }
    }

    private void arg(String str, int i) {
        arg(str, Integer.toString(i));
    }

    private void arg(String str, Object obj) {
        arg(str, toString(obj));
    }

    private void arg(String str, javax.microedition.khronos.egl.EGLDisplay eGLDisplay) {
        if (eGLDisplay == EGL10.EGL_DEFAULT_DISPLAY) {
            arg(str, "EGL10.EGL_DEFAULT_DISPLAY");
        } else if (eGLDisplay == EGL_NO_DISPLAY) {
            arg(str, "EGL10.EGL_NO_DISPLAY");
        } else {
            arg(str, toString(eGLDisplay));
        }
    }

    private void arg(String str, javax.microedition.khronos.egl.EGLContext eGLContext) {
        if (eGLContext == EGL10.EGL_NO_CONTEXT) {
            arg(str, "EGL10.EGL_NO_CONTEXT");
        } else {
            arg(str, toString(eGLContext));
        }
    }

    private void arg(String str, javax.microedition.khronos.egl.EGLSurface eGLSurface) {
        if (eGLSurface == EGL10.EGL_NO_SURFACE) {
            arg(str, "EGL10.EGL_NO_SURFACE");
        } else {
            arg(str, toString(eGLSurface));
        }
    }

    private void returns(String str) {
        log(" returns " + str + ";\n");
        flush();
    }

    private void returns(int i) {
        returns(Integer.toString(i));
    }

    private void returns(boolean z) {
        returns(Boolean.toString(z));
    }

    private void returns(Object obj) {
        returns(toString(obj));
    }

    private String toString(Object obj) {
        if (obj == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        return obj.toString();
    }

    private void arg(String str, int[] iArr) {
        if (iArr == null) {
            arg(str, PerfettoProtoLogImpl.NULL_STRING);
        } else {
            arg(str, toString(iArr.length, iArr, 0));
        }
    }

    private void arg(String str, Object[] objArr) {
        if (objArr == null) {
            arg(str, PerfettoProtoLogImpl.NULL_STRING);
        } else {
            arg(str, toString(objArr.length, objArr, 0));
        }
    }

    private String toString(int i, int[] iArr, int i2) {
        StringBuilder sb = new StringBuilder("{\n");
        int length = iArr.length;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = i2 + i3;
            sb.append(" [" + i4 + "] = ");
            if (i4 < 0 || i4 >= length) {
                sb.append("out of bounds");
            } else {
                sb.append(iArr[i4]);
            }
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private String toString(int i, Object[] objArr, int i2) {
        StringBuilder sb = new StringBuilder("{\n");
        int length = objArr.length;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = i2 + i3;
            sb.append(" [" + i4 + "] = ");
            if (i4 < 0 || i4 >= length) {
                sb.append("out of bounds");
            } else {
                sb.append(objArr[i4]);
            }
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private static String getHex(int i) {
        return "0x" + Integer.toHexString(i);
    }

    public static String getErrorString(int i) {
        switch (i) {
            case 12288:
                return "EGL_SUCCESS";
            case 12289:
                return "EGL_NOT_INITIALIZED";
            case 12290:
                return "EGL_BAD_ACCESS";
            case 12291:
                return "EGL_BAD_ALLOC";
            case 12292:
                return "EGL_BAD_ATTRIBUTE";
            case 12293:
                return "EGL_BAD_CONFIG";
            case 12294:
                return "EGL_BAD_CONTEXT";
            case 12295:
                return "EGL_BAD_CURRENT_SURFACE";
            case 12296:
                return "EGL_BAD_DISPLAY";
            case 12297:
                return "EGL_BAD_MATCH";
            case 12298:
                return "EGL_BAD_NATIVE_PIXMAP";
            case 12299:
                return "EGL_BAD_NATIVE_WINDOW";
            case 12300:
                return "EGL_BAD_PARAMETER";
            case 12301:
                return "EGL_BAD_SURFACE";
            case 12302:
                return "EGL_CONTEXT_LOST";
            default:
                return getHex(i);
        }
    }
}

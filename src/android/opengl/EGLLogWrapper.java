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
    public boolean eglChooseConfig(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, int[] iArr, javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr, int i, int[] iArr2) throws IOException {
        begin("eglChooseConfig");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("attrib_list", iArr);
        arg("config_size", i);
        end();
        boolean zEglChooseConfig = this.mEgl10.eglChooseConfig(eGLDisplay, iArr, eGLConfigArr, i, iArr2);
        arg("configs", (Object[]) eGLConfigArr);
        arg("num_config", iArr2);
        returns(zEglChooseConfig);
        checkError();
        return zEglChooseConfig;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglCopyBuffers(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface, Object obj) throws IOException {
        begin("eglCopyBuffers");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("surface", eGLSurface);
        arg("native_pixmap", obj);
        end();
        boolean zEglCopyBuffers = this.mEgl10.eglCopyBuffers(eGLDisplay, eGLSurface, obj);
        returns(zEglCopyBuffers);
        checkError();
        return zEglCopyBuffers;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLContext eglCreateContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, javax.microedition.khronos.egl.EGLContext eGLContext, int[] iArr) throws IOException {
        begin("eglCreateContext");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("share_context", eGLContext);
        arg("attrib_list", iArr);
        end();
        javax.microedition.khronos.egl.EGLContext eGLContextEglCreateContext = this.mEgl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        returns(eGLContextEglCreateContext);
        checkError();
        return eGLContextEglCreateContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglCreatePbufferSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, int[] iArr) throws IOException {
        begin("eglCreatePbufferSurface");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("attrib_list", iArr);
        end();
        javax.microedition.khronos.egl.EGLSurface eGLSurfaceEglCreatePbufferSurface = this.mEgl10.eglCreatePbufferSurface(eGLDisplay, eGLConfig, iArr);
        returns(eGLSurfaceEglCreatePbufferSurface);
        checkError();
        return eGLSurfaceEglCreatePbufferSurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglCreatePixmapSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, Object obj, int[] iArr) throws IOException {
        begin("eglCreatePixmapSurface");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("native_pixmap", obj);
        arg("attrib_list", iArr);
        end();
        javax.microedition.khronos.egl.EGLSurface eGLSurfaceEglCreatePixmapSurface = this.mEgl10.eglCreatePixmapSurface(eGLDisplay, eGLConfig, obj, iArr);
        returns(eGLSurfaceEglCreatePixmapSurface);
        checkError();
        return eGLSurfaceEglCreatePixmapSurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglCreateWindowSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, Object obj, int[] iArr) throws IOException {
        begin("eglCreateWindowSurface");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("native_window", obj);
        arg("attrib_list", iArr);
        end();
        javax.microedition.khronos.egl.EGLSurface eGLSurfaceEglCreateWindowSurface = this.mEgl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, iArr);
        returns(eGLSurfaceEglCreateWindowSurface);
        checkError();
        return eGLSurfaceEglCreateWindowSurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglDestroyContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLContext eGLContext) throws IOException {
        begin("eglDestroyContext");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg(SemSmartClipMetaTagType.CONTEXT, eGLContext);
        end();
        boolean zEglDestroyContext = this.mEgl10.eglDestroyContext(eGLDisplay, eGLContext);
        returns(zEglDestroyContext);
        checkError();
        return zEglDestroyContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglDestroySurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface) throws IOException {
        begin("eglDestroySurface");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("surface", eGLSurface);
        end();
        boolean zEglDestroySurface = this.mEgl10.eglDestroySurface(eGLDisplay, eGLSurface);
        returns(zEglDestroySurface);
        checkError();
        return zEglDestroySurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglGetConfigAttrib(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, int i, int[] iArr) throws IOException {
        begin("eglGetConfigAttrib");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("attribute", i);
        end();
        boolean zEglGetConfigAttrib = this.mEgl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, iArr);
        arg("value", iArr);
        returns(zEglGetConfigAttrib);
        checkError();
        return false;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglGetConfigs(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr, int i, int[] iArr) throws IOException {
        begin("eglGetConfigs");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config_size", i);
        end();
        boolean zEglGetConfigs = this.mEgl10.eglGetConfigs(eGLDisplay, eGLConfigArr, i, iArr);
        arg("configs", (Object[]) eGLConfigArr);
        arg("num_config", iArr);
        returns(zEglGetConfigs);
        checkError();
        return zEglGetConfigs;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLContext eglGetCurrentContext() throws IOException {
        begin("eglGetCurrentContext");
        end();
        javax.microedition.khronos.egl.EGLContext eGLContextEglGetCurrentContext = this.mEgl10.eglGetCurrentContext();
        returns(eGLContextEglGetCurrentContext);
        checkError();
        return eGLContextEglGetCurrentContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLDisplay eglGetCurrentDisplay() throws IOException {
        begin("eglGetCurrentDisplay");
        end();
        javax.microedition.khronos.egl.EGLDisplay eGLDisplayEglGetCurrentDisplay = this.mEgl10.eglGetCurrentDisplay();
        returns(eGLDisplayEglGetCurrentDisplay);
        checkError();
        return eGLDisplayEglGetCurrentDisplay;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglGetCurrentSurface(int i) throws IOException {
        begin("eglGetCurrentSurface");
        arg("readdraw", i);
        end();
        javax.microedition.khronos.egl.EGLSurface eGLSurfaceEglGetCurrentSurface = this.mEgl10.eglGetCurrentSurface(i);
        returns(eGLSurfaceEglGetCurrentSurface);
        checkError();
        return eGLSurfaceEglGetCurrentSurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLDisplay eglGetDisplay(Object obj) throws IOException {
        begin("eglGetDisplay");
        arg("native_display", obj);
        end();
        javax.microedition.khronos.egl.EGLDisplay eGLDisplayEglGetDisplay = this.mEgl10.eglGetDisplay(obj);
        returns(eGLDisplayEglGetDisplay);
        checkError();
        return eGLDisplayEglGetDisplay;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public int eglGetError() throws IOException {
        begin("eglGetError");
        end();
        int iEglGetError = this.mEgl10.eglGetError();
        returns(getErrorString(iEglGetError));
        return iEglGetError;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglInitialize(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, int[] iArr) throws IOException {
        begin("eglInitialize");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        end();
        boolean zEglInitialize = this.mEgl10.eglInitialize(eGLDisplay, iArr);
        returns(zEglInitialize);
        arg("major_minor", iArr);
        checkError();
        return zEglInitialize;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglMakeCurrent(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface, javax.microedition.khronos.egl.EGLSurface eGLSurface2, javax.microedition.khronos.egl.EGLContext eGLContext) throws IOException {
        begin("eglMakeCurrent");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("draw", eGLSurface);
        arg("read", eGLSurface2);
        arg(SemSmartClipMetaTagType.CONTEXT, eGLContext);
        end();
        boolean zEglMakeCurrent = this.mEgl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface2, eGLContext);
        returns(zEglMakeCurrent);
        checkError();
        return zEglMakeCurrent;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglQueryContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLContext eGLContext, int i, int[] iArr) throws IOException {
        begin("eglQueryContext");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg(SemSmartClipMetaTagType.CONTEXT, eGLContext);
        arg("attribute", i);
        end();
        boolean zEglQueryContext = this.mEgl10.eglQueryContext(eGLDisplay, eGLContext, i, iArr);
        returns(iArr[0]);
        returns(zEglQueryContext);
        checkError();
        return zEglQueryContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public String eglQueryString(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, int i) throws IOException {
        begin("eglQueryString");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("name", i);
        end();
        String strEglQueryString = this.mEgl10.eglQueryString(eGLDisplay, i);
        returns(strEglQueryString);
        checkError();
        return strEglQueryString;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglQuerySurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface, int i, int[] iArr) throws IOException {
        begin("eglQuerySurface");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("surface", eGLSurface);
        arg("attribute", i);
        end();
        boolean zEglQuerySurface = this.mEgl10.eglQuerySurface(eGLDisplay, eGLSurface, i, iArr);
        returns(iArr[0]);
        returns(zEglQuerySurface);
        checkError();
        return zEglQuerySurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglReleaseThread() throws IOException {
        begin("eglReleaseThread");
        end();
        boolean zEglReleaseThread = this.mEgl10.eglReleaseThread();
        returns(zEglReleaseThread);
        checkError();
        return zEglReleaseThread;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglSwapBuffers(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface) throws IOException {
        begin("eglSwapBuffers");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        arg("surface", eGLSurface);
        end();
        boolean zEglSwapBuffers = this.mEgl10.eglSwapBuffers(eGLDisplay, eGLSurface);
        returns(zEglSwapBuffers);
        checkError();
        return zEglSwapBuffers;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglTerminate(javax.microedition.khronos.egl.EGLDisplay eGLDisplay) throws IOException {
        begin("eglTerminate");
        arg(Context.DISPLAY_SERVICE, eGLDisplay);
        end();
        boolean zEglTerminate = this.mEgl10.eglTerminate(eGLDisplay);
        returns(zEglTerminate);
        checkError();
        return zEglTerminate;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglWaitGL() throws IOException {
        begin("eglWaitGL");
        end();
        boolean zEglWaitGL = this.mEgl10.eglWaitGL();
        returns(zEglWaitGL);
        checkError();
        return zEglWaitGL;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglWaitNative(int i, Object obj) throws IOException {
        begin("eglWaitNative");
        arg(TextToSpeech.Engine.KEY_PARAM_ENGINE, i);
        arg("bindTarget", obj);
        end();
        boolean zEglWaitNative = this.mEgl10.eglWaitNative(i, obj);
        returns(zEglWaitNative);
        checkError();
        return zEglWaitNative;
    }

    private void checkError() throws IOException {
        int iEglGetError = this.mEgl10.eglGetError();
        if (iEglGetError != 12288) {
            String str = "eglError: " + getErrorString(iEglGetError);
            logLine(str);
            if (this.mCheckError) {
                throw new GLException(iEglGetError, str);
            }
        }
    }

    private void logLine(String str) throws IOException {
        log(str + '\n');
    }

    private void log(String str) throws IOException {
        try {
            this.mLog.write(str);
        } catch (IOException unused) {
        }
    }

    private void begin(String str) throws IOException {
        log(str + '(');
        this.mArgCount = 0;
    }

    private void arg(String str, String str2) throws IOException {
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

    private void end() throws IOException {
        log(");\n");
        flush();
    }

    private void flush() throws IOException {
        try {
            this.mLog.flush();
        } catch (IOException unused) {
            this.mLog = null;
        }
    }

    private void arg(String str, int i) throws IOException {
        arg(str, Integer.toString(i));
    }

    private void arg(String str, Object obj) throws IOException {
        arg(str, toString(obj));
    }

    private void arg(String str, javax.microedition.khronos.egl.EGLDisplay eGLDisplay) throws IOException {
        if (eGLDisplay == EGL10.EGL_DEFAULT_DISPLAY) {
            arg(str, "EGL10.EGL_DEFAULT_DISPLAY");
        } else if (eGLDisplay == EGL_NO_DISPLAY) {
            arg(str, "EGL10.EGL_NO_DISPLAY");
        } else {
            arg(str, toString(eGLDisplay));
        }
    }

    private void arg(String str, javax.microedition.khronos.egl.EGLContext eGLContext) throws IOException {
        if (eGLContext == EGL10.EGL_NO_CONTEXT) {
            arg(str, "EGL10.EGL_NO_CONTEXT");
        } else {
            arg(str, toString(eGLContext));
        }
    }

    private void arg(String str, javax.microedition.khronos.egl.EGLSurface eGLSurface) throws IOException {
        if (eGLSurface == EGL10.EGL_NO_SURFACE) {
            arg(str, "EGL10.EGL_NO_SURFACE");
        } else {
            arg(str, toString(eGLSurface));
        }
    }

    private void returns(String str) throws IOException {
        log(" returns " + str + ";\n");
        flush();
    }

    private void returns(int i) throws IOException {
        returns(Integer.toString(i));
    }

    private void returns(boolean z) throws IOException {
        returns(Boolean.toString(z));
    }

    private void returns(Object obj) throws IOException {
        returns(toString(obj));
    }

    private String toString(Object obj) {
        if (obj == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        return obj.toString();
    }

    private void arg(String str, int[] iArr) throws IOException {
        if (iArr == null) {
            arg(str, PerfettoProtoLogImpl.NULL_STRING);
        } else {
            arg(str, toString(iArr.length, iArr, 0));
        }
    }

    private void arg(String str, Object[] objArr) throws IOException {
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

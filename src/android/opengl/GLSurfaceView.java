package android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes3.dex */
public class GLSurfaceView extends SurfaceView implements SurfaceHolder.Callback2 {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final boolean LOG_ATTACH_DETACH = false;
    private static final boolean LOG_EGL = false;
    private static final boolean LOG_PAUSE_RESUME = false;
    private static final boolean LOG_RENDERER = false;
    private static final boolean LOG_RENDERER_DRAW_FRAME = false;
    private static final boolean LOG_SURFACE = false;
    private static final boolean LOG_THREADS = false;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private static final String TAG = "GLSurfaceView";
    private static final GLThreadManager sGLThreadManager = new GLThreadManager();
    private int mDebugFlags;
    private boolean mDetached;
    private EGLConfigChooser mEGLConfigChooser;
    private int mEGLContextClientVersion;
    private EGLContextFactory mEGLContextFactory;
    private EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    private GLThread mGLThread;
    private GLWrapper mGLWrapper;
    private boolean mPreserveEGLContextOnPause;
    private Renderer mRenderer;
    private final WeakReference<GLSurfaceView> mThisWeakRef;

    public interface EGLConfigChooser {
        javax.microedition.khronos.egl.EGLConfig chooseConfig(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay);
    }

    public interface EGLContextFactory {
        javax.microedition.khronos.egl.EGLContext createContext(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig);

        void destroyContext(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLContext eGLContext);
    }

    public interface EGLWindowSurfaceFactory {
        javax.microedition.khronos.egl.EGLSurface createWindowSurface(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, Object obj);

        void destroySurface(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface);
    }

    public interface GLWrapper {
        GL wrap(GL gl);
    }

    public interface Renderer {
        void onDrawFrame(GL10 gl10);

        void onSurfaceChanged(GL10 gl10, int i, int i2);

        void onSurfaceCreated(GL10 gl10, javax.microedition.khronos.egl.EGLConfig eGLConfig);
    }

    @Override // android.view.SurfaceHolder.Callback2
    @Deprecated
    public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
    }

    public GLSurfaceView(Context context) {
        super(context);
        this.mThisWeakRef = new WeakReference<>(this);
        init();
    }

    public GLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mThisWeakRef = new WeakReference<>(this);
        init();
    }

    protected void finalize() throws Throwable {
        try {
            GLThread gLThread = this.mGLThread;
            if (gLThread != null) {
                gLThread.requestExitAndWait();
            }
        } finally {
            super.finalize();
        }
    }

    private void init() {
        getHolder().addCallback(this);
    }

    public void setGLWrapper(GLWrapper gLWrapper) {
        this.mGLWrapper = gLWrapper;
    }

    public void setDebugFlags(int i) {
        this.mDebugFlags = i;
    }

    public int getDebugFlags() {
        return this.mDebugFlags;
    }

    public void setPreserveEGLContextOnPause(boolean z) {
        this.mPreserveEGLContextOnPause = z;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.mPreserveEGLContextOnPause;
    }

    public void setRenderer(Renderer renderer) {
        checkRenderThreadState();
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new SimpleEGLConfigChooser(this, true);
        }
        if (this.mEGLContextFactory == null) {
            this.mEGLContextFactory = new DefaultContextFactory();
        }
        if (this.mEGLWindowSurfaceFactory == null) {
            this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
        }
        this.mRenderer = renderer;
        GLThread gLThread = new GLThread(this.mThisWeakRef);
        this.mGLThread = gLThread;
        gLThread.start();
    }

    public void setEGLContextFactory(EGLContextFactory eGLContextFactory) {
        checkRenderThreadState();
        this.mEGLContextFactory = eGLContextFactory;
    }

    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory eGLWindowSurfaceFactory) {
        checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = eGLWindowSurfaceFactory;
    }

    public void setEGLConfigChooser(EGLConfigChooser eGLConfigChooser) {
        checkRenderThreadState();
        this.mEGLConfigChooser = eGLConfigChooser;
    }

    public void setEGLConfigChooser(boolean z) {
        setEGLConfigChooser(new SimpleEGLConfigChooser(this, z));
    }

    public void setEGLConfigChooser(int i, int i2, int i3, int i4, int i5, int i6) {
        setEGLConfigChooser(new ComponentSizeChooser(this, i, i2, i3, i4, i5, i6));
    }

    public void setEGLContextClientVersion(int i) {
        checkRenderThreadState();
        this.mEGLContextClientVersion = i;
    }

    public void setRenderMode(int i) {
        this.mGLThread.setRenderMode(i);
    }

    public int getRenderMode() {
        return this.mGLThread.getRenderMode();
    }

    public void requestRender() {
        this.mGLThread.requestRender();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.mGLThread.surfaceCreated();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.mGLThread.surfaceDestroyed();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.mGLThread.onWindowResize(i2, i3);
    }

    @Override // android.view.SurfaceHolder.Callback2
    public void surfaceRedrawNeededAsync(SurfaceHolder surfaceHolder, Runnable runnable) {
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            gLThread.requestRenderAndNotify(runnable);
        }
    }

    public void onPause() {
        this.mGLThread.onPause();
    }

    public void onResume() {
        this.mGLThread.onResume();
    }

    public void queueEvent(Runnable runnable) {
        this.mGLThread.queueEvent(runnable);
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDetached && this.mRenderer != null) {
            GLThread gLThread = this.mGLThread;
            int renderMode = gLThread != null ? gLThread.getRenderMode() : 1;
            GLThread gLThread2 = new GLThread(this.mThisWeakRef);
            this.mGLThread = gLThread2;
            if (renderMode != 1) {
                gLThread2.setRenderMode(renderMode);
            }
            this.mGLThread.start();
        }
        this.mDetached = false;
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onDetachedFromWindow() throws Throwable {
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            gLThread.requestExitAndWait();
        }
        this.mDetached = true;
        super.onDetachedFromWindow();
    }

    private class DefaultContextFactory implements EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION;

        private DefaultContextFactory() {
            this.EGL_CONTEXT_CLIENT_VERSION = 12440;
        }

        @Override // android.opengl.GLSurfaceView.EGLContextFactory
        public javax.microedition.khronos.egl.EGLContext createContext(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig) {
            int[] iArr = {this.EGL_CONTEXT_CLIENT_VERSION, GLSurfaceView.this.mEGLContextClientVersion, 12344};
            javax.microedition.khronos.egl.EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            if (GLSurfaceView.this.mEGLContextClientVersion == 0) {
                iArr = null;
            }
            return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        }

        @Override // android.opengl.GLSurfaceView.EGLContextFactory
        public void destroyContext(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLContext eGLContext) {
            if (egl10.eglDestroyContext(eGLDisplay, eGLContext)) {
                return;
            }
            Log.e("DefaultContextFactory", "display:" + eGLDisplay + " context: " + eGLContext);
            EglHelper.throwEglException("eglDestroyContex", egl10.eglGetError());
        }
    }

    private static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
        private DefaultWindowSurfaceFactory() {
        }

        @Override // android.opengl.GLSurfaceView.EGLWindowSurfaceFactory
        public javax.microedition.khronos.egl.EGLSurface createWindowSurface(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, Object obj) {
            try {
                return egl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, null);
            } catch (IllegalArgumentException e) {
                Log.e(GLSurfaceView.TAG, "eglCreateWindowSurface", e);
                return null;
            }
        }

        @Override // android.opengl.GLSurfaceView.EGLWindowSurfaceFactory
        public void destroySurface(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface) {
            egl10.eglDestroySurface(eGLDisplay, eGLSurface);
        }
    }

    private abstract class BaseConfigChooser implements EGLConfigChooser {
        protected int[] mConfigSpec;

        abstract javax.microedition.khronos.egl.EGLConfig chooseConfig(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr);

        public BaseConfigChooser(int[] iArr) {
            this.mConfigSpec = filterConfigSpec(iArr);
        }

        @Override // android.opengl.GLSurfaceView.EGLConfigChooser
        public javax.microedition.khronos.egl.EGLConfig chooseConfig(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            if (!egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, null, 0, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig failed");
            }
            int i = iArr[0];
            if (i <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }
            javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr = new javax.microedition.khronos.egl.EGLConfig[i];
            if (!egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, eGLConfigArr, i, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            javax.microedition.khronos.egl.EGLConfig eGLConfigChooseConfig = chooseConfig(egl10, eGLDisplay, eGLConfigArr);
            if (eGLConfigChooseConfig != null) {
                return eGLConfigChooseConfig;
            }
            throw new IllegalArgumentException("No config chosen");
        }

        private int[] filterConfigSpec(int[] iArr) {
            if (GLSurfaceView.this.mEGLContextClientVersion != 2 && GLSurfaceView.this.mEGLContextClientVersion != 3) {
                return iArr;
            }
            int length = iArr.length;
            int[] iArr2 = new int[length + 2];
            int i = length - 1;
            System.arraycopy(iArr, 0, iArr2, 0, i);
            iArr2[i] = 12352;
            if (GLSurfaceView.this.mEGLContextClientVersion == 2) {
                iArr2[length] = 4;
            } else {
                iArr2[length] = 64;
            }
            iArr2[length + 1] = 12344;
            return iArr2;
        }
    }

    private class ComponentSizeChooser extends BaseConfigChooser {
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue;

        public ComponentSizeChooser(GLSurfaceView gLSurfaceView, int i, int i2, int i3, int i4, int i5, int i6) {
            super(new int[]{12324, i, 12323, i2, 12322, i3, 12321, i4, 12325, i5, 12326, i6, 12344});
            this.mValue = new int[1];
            this.mRedSize = i;
            this.mGreenSize = i2;
            this.mBlueSize = i3;
            this.mAlphaSize = i4;
            this.mDepthSize = i5;
            this.mStencilSize = i6;
        }

        @Override // android.opengl.GLSurfaceView.BaseConfigChooser
        public javax.microedition.khronos.egl.EGLConfig chooseConfig(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr) {
            int length = eGLConfigArr.length;
            int i = 0;
            while (i < length) {
                javax.microedition.khronos.egl.EGLConfig eGLConfig = eGLConfigArr[i];
                ComponentSizeChooser componentSizeChooser = this;
                EGL10 egl102 = egl10;
                javax.microedition.khronos.egl.EGLDisplay eGLDisplay2 = eGLDisplay;
                int iFindConfigAttrib = componentSizeChooser.findConfigAttrib(egl102, eGLDisplay2, eGLConfig, 12325, 0);
                int iFindConfigAttrib2 = componentSizeChooser.findConfigAttrib(egl102, eGLDisplay2, eGLConfig, 12326, 0);
                if (iFindConfigAttrib >= componentSizeChooser.mDepthSize && iFindConfigAttrib2 >= componentSizeChooser.mStencilSize) {
                    int iFindConfigAttrib3 = componentSizeChooser.findConfigAttrib(egl102, eGLDisplay2, eGLConfig, 12324, 0);
                    int iFindConfigAttrib4 = componentSizeChooser.findConfigAttrib(egl102, eGLDisplay2, eGLConfig, 12323, 0);
                    int iFindConfigAttrib5 = componentSizeChooser.findConfigAttrib(egl102, eGLDisplay2, eGLConfig, 12322, 0);
                    int iFindConfigAttrib6 = componentSizeChooser.findConfigAttrib(egl102, eGLDisplay2, eGLConfig, 12321, 0);
                    if (iFindConfigAttrib3 == componentSizeChooser.mRedSize && iFindConfigAttrib4 == componentSizeChooser.mGreenSize && iFindConfigAttrib5 == componentSizeChooser.mBlueSize && iFindConfigAttrib6 == componentSizeChooser.mAlphaSize) {
                        return eGLConfig;
                    }
                }
                i++;
                this = componentSizeChooser;
                egl10 = egl102;
                eGLDisplay = eGLDisplay2;
            }
            return null;
        }

        private int findConfigAttrib(EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, int i, int i2) {
            return egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, this.mValue) ? this.mValue[0] : i2;
        }
    }

    private class SimpleEGLConfigChooser extends ComponentSizeChooser {
        public SimpleEGLConfigChooser(GLSurfaceView gLSurfaceView, boolean z) {
            super(gLSurfaceView, 8, 8, 8, 0, z ? 16 : 0, 0);
        }
    }

    private static class EglHelper {
        EGL10 mEgl;
        javax.microedition.khronos.egl.EGLConfig mEglConfig;
        javax.microedition.khronos.egl.EGLContext mEglContext;
        javax.microedition.khronos.egl.EGLDisplay mEglDisplay;
        javax.microedition.khronos.egl.EGLSurface mEglSurface;
        private WeakReference<GLSurfaceView> mGLSurfaceViewWeakRef;

        public EglHelper(WeakReference<GLSurfaceView> weakReference) {
            this.mGLSurfaceViewWeakRef = weakReference;
        }

        public void start() {
            EGL10 egl10 = (EGL10) javax.microedition.khronos.egl.EGLContext.getEGL();
            this.mEgl = egl10;
            javax.microedition.khronos.egl.EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            this.mEglDisplay = eGLDisplayEglGetDisplay;
            if (eGLDisplayEglGetDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (!this.mEgl.eglInitialize(this.mEglDisplay, new int[2])) {
                throw new RuntimeException("eglInitialize failed");
            }
            GLSurfaceView gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
            if (gLSurfaceView == null) {
                this.mEglConfig = null;
                this.mEglContext = null;
            } else {
                this.mEglConfig = gLSurfaceView.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
                this.mEglContext = gLSurfaceView.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
            }
            javax.microedition.khronos.egl.EGLContext eGLContext = this.mEglContext;
            if (eGLContext == null || eGLContext == EGL10.EGL_NO_CONTEXT) {
                this.mEglContext = null;
                throwEglException("createContext");
            }
            this.mEglSurface = null;
        }

        public boolean createSurface() {
            if (this.mEgl == null) {
                throw new RuntimeException("egl not initialized");
            }
            if (this.mEglDisplay == null) {
                throw new RuntimeException("eglDisplay not initialized");
            }
            if (this.mEglConfig == null) {
                throw new RuntimeException("mEglConfig not initialized");
            }
            destroySurfaceImp();
            GLSurfaceView gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
            if (gLSurfaceView != null) {
                this.mEglSurface = gLSurfaceView.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, gLSurfaceView.getHolder());
            } else {
                this.mEglSurface = null;
            }
            javax.microedition.khronos.egl.EGLSurface eGLSurface = this.mEglSurface;
            if (eGLSurface == null || eGLSurface == EGL10.EGL_NO_SURFACE) {
                if (this.mEgl.eglGetError() == 12299) {
                    Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                }
                return false;
            }
            EGL10 egl10 = this.mEgl;
            javax.microedition.khronos.egl.EGLDisplay eGLDisplay = this.mEglDisplay;
            javax.microedition.khronos.egl.EGLSurface eGLSurface2 = this.mEglSurface;
            if (egl10.eglMakeCurrent(eGLDisplay, eGLSurface2, eGLSurface2, this.mEglContext)) {
                return true;
            }
            logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", this.mEgl.eglGetError());
            return false;
        }

        GL createGL() {
            GL gl = this.mEglContext.getGL();
            GLSurfaceView gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
            if (gLSurfaceView != null) {
                if (gLSurfaceView.mGLWrapper != null) {
                    gl = gLSurfaceView.mGLWrapper.wrap(gl);
                }
                if ((gLSurfaceView.mDebugFlags & 3) != 0) {
                    return GLDebugHelper.wrap(gl, (gLSurfaceView.mDebugFlags & 1) == 0 ? 0 : 1, (gLSurfaceView.mDebugFlags & 2) != 0 ? new LogWriter() : null);
                }
            }
            return gl;
        }

        public int swap() {
            if (this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
                return 12288;
            }
            return this.mEgl.eglGetError();
        }

        public void destroySurface() {
            destroySurfaceImp();
        }

        private void destroySurfaceImp() {
            javax.microedition.khronos.egl.EGLSurface eGLSurface = this.mEglSurface;
            if (eGLSurface == null || eGLSurface == EGL10.EGL_NO_SURFACE) {
                return;
            }
            this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            GLSurfaceView gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
            if (gLSurfaceView != null) {
                gLSurfaceView.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
            }
            this.mEglSurface = null;
        }

        public void finish() {
            if (this.mEglContext != null) {
                GLSurfaceView gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
                if (gLSurfaceView != null) {
                    gLSurfaceView.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                }
                this.mEglContext = null;
            }
            javax.microedition.khronos.egl.EGLDisplay eGLDisplay = this.mEglDisplay;
            if (eGLDisplay != null) {
                this.mEgl.eglTerminate(eGLDisplay);
                this.mEglDisplay = null;
            }
        }

        private void throwEglException(String str) {
            throwEglException(str, this.mEgl.eglGetError());
        }

        public static void throwEglException(String str, int i) {
            throw new RuntimeException(formatEglError(str, i));
        }

        public static void logEglErrorAsWarning(String str, String str2, int i) {
            Log.w(str, formatEglError(str2, i));
        }

        public static String formatEglError(String str, int i) {
            return str + " failed: " + EGLLogWrapper.getErrorString(i);
        }
    }

    static class GLThread extends Thread {
        private static final String TAG = "GLThread";
        private EglHelper mEglHelper;
        private boolean mExited;
        private boolean mFinishedCreatingEglSurface;
        private WeakReference<GLSurfaceView> mGLSurfaceViewWeakRef;
        private boolean mHasSurface;
        private boolean mHaveEglContext;
        private boolean mHaveEglSurface;
        private boolean mPaused;
        private boolean mRenderComplete;
        private boolean mRequestPaused;
        private boolean mShouldExit;
        private boolean mShouldReleaseEglContext;
        private boolean mSurfaceIsBad;
        private boolean mWaitingForSurface;
        private ArrayList<Runnable> mEventQueue = new ArrayList<>();
        private boolean mSizeChanged = true;
        private Runnable mFinishDrawingRunnable = null;
        private int mWidth = 0;
        private int mHeight = 0;
        private boolean mRequestRender = true;
        private int mRenderMode = 1;
        private boolean mWantRenderNotification = false;

        GLThread(WeakReference<GLSurfaceView> weakReference) {
            this.mGLSurfaceViewWeakRef = weakReference;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            setName("GLThread " + getId());
            try {
                guardedRun();
            } catch (InterruptedException unused) {
            } finally {
                GLSurfaceView.sGLThreadManager.threadExiting(this);
            }
        }

        private void stopEglSurfaceLocked() {
            if (this.mHaveEglSurface) {
                this.mHaveEglSurface = false;
                this.mEglHelper.destroySurface();
            }
        }

        private void stopEglContextLocked() {
            if (this.mHaveEglContext) {
                this.mEglHelper.finish();
                this.mHaveEglContext = false;
                GLSurfaceView.sGLThreadManager.releaseEglContextLocked(this);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:111:0x017c A[Catch: all -> 0x0251, TryCatch #1 {all -> 0x0251, blocks: (B:3:0x001f, B:4:0x0023, B:87:0x013e, B:90:0x0147, B:92:0x014f, B:93:0x0153, B:100:0x0163, B:101:0x0164, B:102:0x0168, B:109:0x0179, B:111:0x017c, B:114:0x018b, B:118:0x01a8, B:126:0x01b9, B:129:0x01d0, B:131:0x01d5, B:132:0x01d8, B:134:0x01da, B:141:0x01f9, B:143:0x01fe, B:144:0x0201, B:145:0x0202, B:149:0x0210, B:150:0x021b, B:157:0x022a, B:120:0x01ad, B:121:0x01b0, B:168:0x0250, B:95:0x0155, B:96:0x015e, B:128:0x01c3, B:104:0x016a, B:105:0x0175, B:152:0x021d, B:153:0x0226, B:117:0x0197, B:137:0x01e6, B:139:0x01f5, B:5:0x0024, B:7:0x0028, B:16:0x0039, B:18:0x0041, B:85:0x013b, B:19:0x004e, B:21:0x0054, B:23:0x005f, B:25:0x0063, B:27:0x006f, B:29:0x0078, B:31:0x007c, B:33:0x0081, B:35:0x0085, B:40:0x0097, B:38:0x0091, B:41:0x009a, B:43:0x009e, B:45:0x00a2, B:47:0x00a6, B:48:0x00a9, B:49:0x00b6, B:51:0x00ba, B:53:0x00be, B:55:0x00ca, B:56:0x00d8, B:58:0x00dc, B:60:0x00e2, B:62:0x00e8, B:66:0x00f0, B:68:0x00f6, B:70:0x0102, B:71:0x0109, B:72:0x010a, B:74:0x010e, B:76:0x0112, B:77:0x0118, B:79:0x011c, B:81:0x0120, B:82:0x012c, B:165:0x0244, B:164:0x0239), top: B:180:0x001f, inners: #0, #2, #5, #6, #7, #8, #9 }] */
        /* JADX WARN: Removed duplicated region for block: B:114:0x018b A[Catch: all -> 0x0251, TRY_LEAVE, TryCatch #1 {all -> 0x0251, blocks: (B:3:0x001f, B:4:0x0023, B:87:0x013e, B:90:0x0147, B:92:0x014f, B:93:0x0153, B:100:0x0163, B:101:0x0164, B:102:0x0168, B:109:0x0179, B:111:0x017c, B:114:0x018b, B:118:0x01a8, B:126:0x01b9, B:129:0x01d0, B:131:0x01d5, B:132:0x01d8, B:134:0x01da, B:141:0x01f9, B:143:0x01fe, B:144:0x0201, B:145:0x0202, B:149:0x0210, B:150:0x021b, B:157:0x022a, B:120:0x01ad, B:121:0x01b0, B:168:0x0250, B:95:0x0155, B:96:0x015e, B:128:0x01c3, B:104:0x016a, B:105:0x0175, B:152:0x021d, B:153:0x0226, B:117:0x0197, B:137:0x01e6, B:139:0x01f5, B:5:0x0024, B:7:0x0028, B:16:0x0039, B:18:0x0041, B:85:0x013b, B:19:0x004e, B:21:0x0054, B:23:0x005f, B:25:0x0063, B:27:0x006f, B:29:0x0078, B:31:0x007c, B:33:0x0081, B:35:0x0085, B:40:0x0097, B:38:0x0091, B:41:0x009a, B:43:0x009e, B:45:0x00a2, B:47:0x00a6, B:48:0x00a9, B:49:0x00b6, B:51:0x00ba, B:53:0x00be, B:55:0x00ca, B:56:0x00d8, B:58:0x00dc, B:60:0x00e2, B:62:0x00e8, B:66:0x00f0, B:68:0x00f6, B:70:0x0102, B:71:0x0109, B:72:0x010a, B:74:0x010e, B:76:0x0112, B:77:0x0118, B:79:0x011c, B:81:0x0120, B:82:0x012c, B:165:0x0244, B:164:0x0239), top: B:180:0x001f, inners: #0, #2, #5, #6, #7, #8, #9 }] */
        /* JADX WARN: Removed duplicated region for block: B:124:0x01b5  */
        /* JADX WARN: Removed duplicated region for block: B:126:0x01b9 A[Catch: all -> 0x0251, TRY_LEAVE, TryCatch #1 {all -> 0x0251, blocks: (B:3:0x001f, B:4:0x0023, B:87:0x013e, B:90:0x0147, B:92:0x014f, B:93:0x0153, B:100:0x0163, B:101:0x0164, B:102:0x0168, B:109:0x0179, B:111:0x017c, B:114:0x018b, B:118:0x01a8, B:126:0x01b9, B:129:0x01d0, B:131:0x01d5, B:132:0x01d8, B:134:0x01da, B:141:0x01f9, B:143:0x01fe, B:144:0x0201, B:145:0x0202, B:149:0x0210, B:150:0x021b, B:157:0x022a, B:120:0x01ad, B:121:0x01b0, B:168:0x0250, B:95:0x0155, B:96:0x015e, B:128:0x01c3, B:104:0x016a, B:105:0x0175, B:152:0x021d, B:153:0x0226, B:117:0x0197, B:137:0x01e6, B:139:0x01f5, B:5:0x0024, B:7:0x0028, B:16:0x0039, B:18:0x0041, B:85:0x013b, B:19:0x004e, B:21:0x0054, B:23:0x005f, B:25:0x0063, B:27:0x006f, B:29:0x0078, B:31:0x007c, B:33:0x0081, B:35:0x0085, B:40:0x0097, B:38:0x0091, B:41:0x009a, B:43:0x009e, B:45:0x00a2, B:47:0x00a6, B:48:0x00a9, B:49:0x00b6, B:51:0x00ba, B:53:0x00be, B:55:0x00ca, B:56:0x00d8, B:58:0x00dc, B:60:0x00e2, B:62:0x00e8, B:66:0x00f0, B:68:0x00f6, B:70:0x0102, B:71:0x0109, B:72:0x010a, B:74:0x010e, B:76:0x0112, B:77:0x0118, B:79:0x011c, B:81:0x0120, B:82:0x012c, B:165:0x0244, B:164:0x0239), top: B:180:0x001f, inners: #0, #2, #5, #6, #7, #8, #9 }] */
        /* JADX WARN: Removed duplicated region for block: B:136:0x01e4  */
        /* JADX WARN: Removed duplicated region for block: B:147:0x020c  */
        /* JADX WARN: Removed duplicated region for block: B:159:0x022e  */
        /* JADX WARN: Removed duplicated region for block: B:162:0x0233  */
        /* JADX WARN: Removed duplicated region for block: B:196:0x0257 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:201:0x0142 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void guardedRun() throws InterruptedException {
            Runnable runnable;
            boolean z;
            GLSurfaceView gLSurfaceView;
            int iSwap;
            boolean z2;
            this.mEglHelper = new EglHelper(this.mGLSurfaceViewWeakRef);
            this.mHaveEglContext = false;
            this.mHaveEglSurface = false;
            this.mWantRenderNotification = false;
            boolean z3 = false;
            boolean z4 = false;
            boolean z5 = false;
            boolean z6 = false;
            boolean z7 = false;
            boolean z8 = false;
            boolean z9 = false;
            boolean z10 = false;
            int i = 0;
            int i2 = 0;
            Runnable runnable2 = null;
            GL10 gl10 = null;
            Runnable runnableRemove = null;
            while (true) {
                try {
                    synchronized (GLSurfaceView.sGLThreadManager) {
                        while (!this.mShouldExit) {
                            if (!this.mEventQueue.isEmpty()) {
                                runnableRemove = this.mEventQueue.remove(0);
                                runnable = null;
                            } else {
                                boolean z11 = this.mPaused;
                                boolean z12 = this.mRequestPaused;
                                if (z11 != z12) {
                                    this.mPaused = z12;
                                    GLSurfaceView.sGLThreadManager.notifyAll();
                                } else {
                                    z12 = false;
                                }
                                if (this.mShouldReleaseEglContext) {
                                    stopEglSurfaceLocked();
                                    stopEglContextLocked();
                                    this.mShouldReleaseEglContext = false;
                                    z5 = true;
                                }
                                if (z3) {
                                    stopEglSurfaceLocked();
                                    stopEglContextLocked();
                                    z3 = false;
                                }
                                if (z12 && this.mHaveEglSurface) {
                                    stopEglSurfaceLocked();
                                }
                                if (z12 && this.mHaveEglContext) {
                                    GLSurfaceView gLSurfaceView2 = this.mGLSurfaceViewWeakRef.get();
                                    if (!(gLSurfaceView2 == null ? false : gLSurfaceView2.mPreserveEGLContextOnPause)) {
                                        stopEglContextLocked();
                                    }
                                }
                                if (!this.mHasSurface && !this.mWaitingForSurface) {
                                    if (this.mHaveEglSurface) {
                                        stopEglSurfaceLocked();
                                    }
                                    this.mWaitingForSurface = true;
                                    this.mSurfaceIsBad = false;
                                    GLSurfaceView.sGLThreadManager.notifyAll();
                                }
                                if (this.mHasSurface && this.mWaitingForSurface) {
                                    this.mWaitingForSurface = false;
                                    GLSurfaceView.sGLThreadManager.notifyAll();
                                }
                                if (z4) {
                                    this.mWantRenderNotification = false;
                                    this.mRenderComplete = true;
                                    GLSurfaceView.sGLThreadManager.notifyAll();
                                    z4 = false;
                                }
                                Runnable runnable3 = this.mFinishDrawingRunnable;
                                if (runnable3 != null) {
                                    runnable = null;
                                    this.mFinishDrawingRunnable = null;
                                    runnable2 = runnable3;
                                } else {
                                    runnable = null;
                                }
                                if (readyToDraw()) {
                                    if (!this.mHaveEglContext) {
                                        if (z5) {
                                            z5 = false;
                                        } else {
                                            try {
                                                this.mEglHelper.start();
                                                this.mHaveEglContext = true;
                                                GLSurfaceView.sGLThreadManager.notifyAll();
                                                z6 = true;
                                            } catch (RuntimeException e) {
                                                GLSurfaceView.sGLThreadManager.releaseEglContextLocked(this);
                                                throw e;
                                            }
                                        }
                                    }
                                    if (this.mHaveEglContext && !this.mHaveEglSurface) {
                                        this.mHaveEglSurface = true;
                                        z7 = true;
                                        z8 = true;
                                        z9 = true;
                                    }
                                    if (this.mHaveEglSurface) {
                                        if (this.mSizeChanged) {
                                            i = this.mWidth;
                                            i2 = this.mHeight;
                                            this.mWantRenderNotification = true;
                                            this.mSizeChanged = false;
                                            z7 = true;
                                            z9 = true;
                                        }
                                        this.mRequestRender = false;
                                        GLSurfaceView.sGLThreadManager.notifyAll();
                                        if (this.mWantRenderNotification) {
                                            z10 = true;
                                        }
                                    }
                                } else if (runnable2 != null) {
                                    Log.w(TAG, "Warning, !readyToDraw() but waiting for draw finished! Early reporting draw finished.");
                                    runnable2.run();
                                    runnable2 = null;
                                }
                                GLSurfaceView.sGLThreadManager.wait();
                            }
                        }
                        synchronized (GLSurfaceView.sGLThreadManager) {
                            stopEglSurfaceLocked();
                            stopEglContextLocked();
                        }
                        return;
                    }
                } catch (Throwable th) {
                    synchronized (GLSurfaceView.sGLThreadManager) {
                    }
                }
                if (runnableRemove != null) {
                    runnableRemove.run();
                    runnableRemove = runnable;
                } else {
                    if (z7) {
                        if (this.mEglHelper.createSurface()) {
                            synchronized (GLSurfaceView.sGLThreadManager) {
                                this.mFinishedCreatingEglSurface = true;
                                GLSurfaceView.sGLThreadManager.notifyAll();
                            }
                            z7 = false;
                            if (z8) {
                            }
                            boolean z13 = z3;
                            if (z6) {
                            }
                            if (z9) {
                            }
                            gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
                            if (gLSurfaceView != null) {
                            }
                            iSwap = this.mEglHelper.swap();
                            if (iSwap != 12288) {
                            }
                            z3 = z;
                            if (z10) {
                            }
                        } else {
                            synchronized (GLSurfaceView.sGLThreadManager) {
                                this.mFinishedCreatingEglSurface = true;
                                this.mSurfaceIsBad = true;
                                GLSurfaceView.sGLThreadManager.notifyAll();
                            }
                        }
                        synchronized (GLSurfaceView.sGLThreadManager) {
                            stopEglSurfaceLocked();
                            stopEglContextLocked();
                            throw th;
                        }
                    }
                    if (z8) {
                        gl10 = (GL10) this.mEglHelper.createGL();
                        z8 = false;
                    }
                    boolean z132 = z3;
                    if (z6) {
                        z = z132;
                    } else {
                        GLSurfaceView gLSurfaceView3 = this.mGLSurfaceViewWeakRef.get();
                        if (gLSurfaceView3 != null) {
                            z = z132;
                            try {
                                Trace.traceBegin(8L, "onSurfaceCreated");
                                gLSurfaceView3.mRenderer.onSurfaceCreated(gl10, this.mEglHelper.mEglConfig);
                                Trace.traceEnd(8L);
                            } finally {
                            }
                        } else {
                            z = z132;
                        }
                        z6 = false;
                    }
                    if (z9) {
                        GLSurfaceView gLSurfaceView4 = this.mGLSurfaceViewWeakRef.get();
                        if (gLSurfaceView4 != null) {
                            try {
                                Trace.traceBegin(8L, "onSurfaceChanged");
                                gLSurfaceView4.mRenderer.onSurfaceChanged(gl10, i, i2);
                                Trace.traceEnd(8L);
                            } finally {
                            }
                        }
                        z9 = false;
                    }
                    gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
                    if (gLSurfaceView != null) {
                        try {
                            Trace.traceBegin(8L, "onDrawFrame");
                            gLSurfaceView.mRenderer.onDrawFrame(gl10);
                            if (runnable2 != null) {
                                runnable2.run();
                                runnable2 = null;
                            }
                            Trace.traceEnd(8L);
                        } finally {
                        }
                    }
                    iSwap = this.mEglHelper.swap();
                    if (iSwap != 12288) {
                        z2 = true;
                    } else if (iSwap != 12302) {
                        EglHelper.logEglErrorAsWarning(TAG, "eglSwapBuffers", iSwap);
                        synchronized (GLSurfaceView.sGLThreadManager) {
                            z2 = true;
                            this.mSurfaceIsBad = true;
                            GLSurfaceView.sGLThreadManager.notifyAll();
                        }
                    } else {
                        z2 = true;
                        z3 = true;
                        if (z10) {
                            z4 = z2;
                            z10 = false;
                        }
                    }
                    z3 = z;
                    if (z10) {
                    }
                }
            }
        }

        public boolean ableToDraw() {
            return this.mHaveEglContext && this.mHaveEglSurface && readyToDraw();
        }

        private boolean readyToDraw() {
            if (this.mPaused || !this.mHasSurface || this.mSurfaceIsBad || this.mWidth <= 0 || this.mHeight <= 0) {
                return false;
            }
            return this.mRequestRender || this.mRenderMode == 1;
        }

        public void setRenderMode(int i) {
            if (i < 0 || i > 1) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mRenderMode = i;
                GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        public int getRenderMode() {
            int i;
            synchronized (GLSurfaceView.sGLThreadManager) {
                i = this.mRenderMode;
            }
            return i;
        }

        public void requestRender() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mRequestRender = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        public void requestRenderAndNotify(final Runnable runnable) {
            synchronized (GLSurfaceView.sGLThreadManager) {
                if (Thread.currentThread() == this) {
                    return;
                }
                this.mWantRenderNotification = true;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                final Runnable runnable2 = this.mFinishDrawingRunnable;
                this.mFinishDrawingRunnable = new Runnable() { // from class: android.opengl.GLSurfaceView$GLThread$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        GLSurfaceView.GLThread.lambda$requestRenderAndNotify$0(runnable2, runnable);
                    }
                };
                GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        static /* synthetic */ void lambda$requestRenderAndNotify$0(Runnable runnable, Runnable runnable2) {
            if (runnable != null) {
                runnable.run();
            }
            if (runnable2 != null) {
                runnable2.run();
            }
        }

        public void surfaceCreated() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mHasSurface = true;
                this.mFinishedCreatingEglSurface = false;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (this.mWaitingForSurface && !this.mFinishedCreatingEglSurface && !this.mExited) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void surfaceDestroyed() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mHasSurface = false;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mWaitingForSurface && !this.mExited) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onPause() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mRequestPaused = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onResume() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mRequestPaused = false;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && this.mPaused && !this.mRenderComplete) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onWindowResize(int i, int i2) {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mWidth = i;
                this.mHeight = i2;
                this.mSizeChanged = true;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                if (Thread.currentThread() == this) {
                    return;
                }
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused && !this.mRenderComplete && ableToDraw()) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestExitAndWait() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mShouldExit = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestReleaseEglContextLocked() {
            this.mShouldReleaseEglContext = true;
            GLSurfaceView.sGLThreadManager.notifyAll();
        }

        public void queueEvent(Runnable runnable) {
            if (runnable == null) {
                throw new IllegalArgumentException("r must not be null");
            }
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mEventQueue.add(runnable);
                GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }
    }

    static class LogWriter extends Writer {
        private StringBuilder mBuilder = new StringBuilder();

        LogWriter() {
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            flushBuilder();
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
            flushBuilder();
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                char c = cArr[i + i3];
                if (c == '\n') {
                    flushBuilder();
                } else {
                    this.mBuilder.append(c);
                }
            }
        }

        private void flushBuilder() {
            if (this.mBuilder.length() > 0) {
                Log.v(GLSurfaceView.TAG, this.mBuilder.toString());
                StringBuilder sb = this.mBuilder;
                sb.delete(0, sb.length());
            }
        }
    }

    private void checkRenderThreadState() {
        if (this.mGLThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    private static class GLThreadManager {
        private static String TAG = "GLThreadManager";

        private GLThreadManager() {
        }

        public synchronized void threadExiting(GLThread gLThread) {
            gLThread.mExited = true;
            notifyAll();
        }

        public void releaseEglContextLocked(GLThread gLThread) {
            notifyAll();
        }
    }
}

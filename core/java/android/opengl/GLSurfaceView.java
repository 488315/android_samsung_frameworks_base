package android.opengl;

import android.content.Context;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.InterfaceC5395GL;

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
    javax.microedition.khronos.egl.EGLConfig chooseConfig(
        EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay);
  }

  public interface EGLContextFactory {
    javax.microedition.khronos.egl.EGLContext createContext(
        EGL10 egl10,
        javax.microedition.khronos.egl.EGLDisplay eGLDisplay,
        javax.microedition.khronos.egl.EGLConfig eGLConfig);

    void destroyContext(
        EGL10 egl10,
        javax.microedition.khronos.egl.EGLDisplay eGLDisplay,
        javax.microedition.khronos.egl.EGLContext eGLContext);
  }

  public interface EGLWindowSurfaceFactory {
    javax.microedition.khronos.egl.EGLSurface createWindowSurface(
        EGL10 egl10,
        javax.microedition.khronos.egl.EGLDisplay eGLDisplay,
        javax.microedition.khronos.egl.EGLConfig eGLConfig,
        Object obj);

    void destroySurface(
        EGL10 egl10,
        javax.microedition.khronos.egl.EGLDisplay eGLDisplay,
        javax.microedition.khronos.egl.EGLSurface eGLSurface);
  }

  public interface GLWrapper {
    InterfaceC5395GL wrap(InterfaceC5395GL interfaceC5395GL);
  }

  public interface Renderer {
    void onDrawFrame(GL10 gl10);

    void onSurfaceChanged(GL10 gl10, int i, int i2);

    void onSurfaceCreated(GL10 gl10, javax.microedition.khronos.egl.EGLConfig eGLConfig);
  }

  public GLSurfaceView(Context context) {
    super(context);
    this.mThisWeakRef = new WeakReference<>(this);
    init();
  }

  public GLSurfaceView(Context context, AttributeSet attrs) {
    super(context, attrs);
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
    SurfaceHolder holder = getHolder();
    holder.addCallback(this);
  }

  public void setGLWrapper(GLWrapper glWrapper) {
    this.mGLWrapper = glWrapper;
  }

  public void setDebugFlags(int debugFlags) {
    this.mDebugFlags = debugFlags;
  }

  public int getDebugFlags() {
    return this.mDebugFlags;
  }

  public void setPreserveEGLContextOnPause(boolean preserveOnPause) {
    this.mPreserveEGLContextOnPause = preserveOnPause;
  }

  public boolean getPreserveEGLContextOnPause() {
    return this.mPreserveEGLContextOnPause;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void setRenderer(Renderer renderer) {
    checkRenderThreadState();
    if (this.mEGLConfigChooser == null) {
      this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
    }
    Object[] objArr = 0;
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

  public void setEGLContextFactory(EGLContextFactory factory) {
    checkRenderThreadState();
    this.mEGLContextFactory = factory;
  }

  public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory factory) {
    checkRenderThreadState();
    this.mEGLWindowSurfaceFactory = factory;
  }

  public void setEGLConfigChooser(EGLConfigChooser configChooser) {
    checkRenderThreadState();
    this.mEGLConfigChooser = configChooser;
  }

  public void setEGLConfigChooser(boolean needDepth) {
    setEGLConfigChooser(new SimpleEGLConfigChooser(needDepth));
  }

  public void setEGLConfigChooser(
      int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
    setEGLConfigChooser(
        new ComponentSizeChooser(redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize));
  }

  public void setEGLContextClientVersion(int version) {
    checkRenderThreadState();
    this.mEGLContextClientVersion = version;
  }

  public void setRenderMode(int renderMode) {
    this.mGLThread.setRenderMode(renderMode);
  }

  public int getRenderMode() {
    return this.mGLThread.getRenderMode();
  }

  public void requestRender() {
    this.mGLThread.requestRender();
  }

  @Override // android.view.SurfaceHolder.Callback
  public void surfaceCreated(SurfaceHolder holder) {
    this.mGLThread.surfaceCreated();
  }

  @Override // android.view.SurfaceHolder.Callback
  public void surfaceDestroyed(SurfaceHolder holder) {
    this.mGLThread.surfaceDestroyed();
  }

  @Override // android.view.SurfaceHolder.Callback
  public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    this.mGLThread.onWindowResize(w, h);
  }

  @Override // android.view.SurfaceHolder.Callback2
  public void surfaceRedrawNeededAsync(SurfaceHolder holder, Runnable finishDrawing) {
    GLThread gLThread = this.mGLThread;
    if (gLThread != null) {
      gLThread.requestRenderAndNotify(finishDrawing);
    }
  }

  @Override // android.view.SurfaceHolder.Callback2
  @Deprecated
  public void surfaceRedrawNeeded(SurfaceHolder holder) {}

  public void onPause() {
    this.mGLThread.onPause();
  }

  public void onResume() {
    this.mGLThread.onResume();
  }

  public void queueEvent(Runnable r) {
    this.mGLThread.queueEvent(r);
  }

  @Override // android.view.SurfaceView, android.view.View
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (this.mDetached && this.mRenderer != null) {
      int renderMode = 1;
      GLThread gLThread = this.mGLThread;
      if (gLThread != null) {
        renderMode = gLThread.getRenderMode();
      }
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
  protected void onDetachedFromWindow() {
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
    public javax.microedition.khronos.egl.EGLContext createContext(
        EGL10 egl,
        javax.microedition.khronos.egl.EGLDisplay display,
        javax.microedition.khronos.egl.EGLConfig config) {
      int[] attrib_list = {
        this.EGL_CONTEXT_CLIENT_VERSION, GLSurfaceView.this.mEGLContextClientVersion, 12344
      };
      return egl.eglCreateContext(
          display,
          config,
          EGL10.EGL_NO_CONTEXT,
          GLSurfaceView.this.mEGLContextClientVersion != 0 ? attrib_list : null);
    }

    @Override // android.opengl.GLSurfaceView.EGLContextFactory
    public void destroyContext(
        EGL10 egl,
        javax.microedition.khronos.egl.EGLDisplay display,
        javax.microedition.khronos.egl.EGLContext context) {
      if (!egl.eglDestroyContext(display, context)) {
        Log.m96e("DefaultContextFactory", "display:" + display + " context: " + context);
        EglHelper.throwEglException("eglDestroyContex", egl.eglGetError());
      }
    }
  }

  private static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
    private DefaultWindowSurfaceFactory() {}

    @Override // android.opengl.GLSurfaceView.EGLWindowSurfaceFactory
    public javax.microedition.khronos.egl.EGLSurface createWindowSurface(
        EGL10 egl,
        javax.microedition.khronos.egl.EGLDisplay display,
        javax.microedition.khronos.egl.EGLConfig config,
        Object nativeWindow) {
      try {
        javax.microedition.khronos.egl.EGLSurface result =
            egl.eglCreateWindowSurface(display, config, nativeWindow, null);
        return result;
      } catch (IllegalArgumentException e) {
        Log.m97e(GLSurfaceView.TAG, "eglCreateWindowSurface", e);
        return null;
      }
    }

    @Override // android.opengl.GLSurfaceView.EGLWindowSurfaceFactory
    public void destroySurface(
        EGL10 egl,
        javax.microedition.khronos.egl.EGLDisplay display,
        javax.microedition.khronos.egl.EGLSurface surface) {
      egl.eglDestroySurface(display, surface);
    }
  }

  private abstract class BaseConfigChooser implements EGLConfigChooser {
    protected int[] mConfigSpec;

    abstract javax.microedition.khronos.egl.EGLConfig chooseConfig(
        EGL10 egl10,
        javax.microedition.khronos.egl.EGLDisplay eGLDisplay,
        javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr);

    public BaseConfigChooser(int[] configSpec) {
      this.mConfigSpec = filterConfigSpec(configSpec);
    }

    @Override // android.opengl.GLSurfaceView.EGLConfigChooser
    public javax.microedition.khronos.egl.EGLConfig chooseConfig(
        EGL10 egl, javax.microedition.khronos.egl.EGLDisplay display) {
      int[] num_config = new int[1];
      if (!egl.eglChooseConfig(display, this.mConfigSpec, null, 0, num_config)) {
        throw new IllegalArgumentException("eglChooseConfig failed");
      }
      int numConfigs = num_config[0];
      if (numConfigs <= 0) {
        throw new IllegalArgumentException("No configs match configSpec");
      }
      javax.microedition.khronos.egl.EGLConfig[] configs =
          new javax.microedition.khronos.egl.EGLConfig[numConfigs];
      if (!egl.eglChooseConfig(display, this.mConfigSpec, configs, numConfigs, num_config)) {
        throw new IllegalArgumentException("eglChooseConfig#2 failed");
      }
      javax.microedition.khronos.egl.EGLConfig config = chooseConfig(egl, display, configs);
      if (config == null) {
        throw new IllegalArgumentException("No config chosen");
      }
      return config;
    }

    private int[] filterConfigSpec(int[] configSpec) {
      if (GLSurfaceView.this.mEGLContextClientVersion != 2
          && GLSurfaceView.this.mEGLContextClientVersion != 3) {
        return configSpec;
      }
      int len = configSpec.length;
      int[] newConfigSpec = new int[len + 2];
      System.arraycopy(configSpec, 0, newConfigSpec, 0, len - 1);
      newConfigSpec[len - 1] = 12352;
      if (GLSurfaceView.this.mEGLContextClientVersion == 2) {
        newConfigSpec[len] = 4;
      } else {
        newConfigSpec[len] = 64;
      }
      newConfigSpec[len + 1] = 12344;
      return newConfigSpec;
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

    public ComponentSizeChooser(
        int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
      super(
          new int[] {
            12324,
            redSize,
            12323,
            greenSize,
            12322,
            blueSize,
            12321,
            alphaSize,
            12325,
            depthSize,
            12326,
            stencilSize,
            12344
          });
      this.mValue = new int[1];
      this.mRedSize = redSize;
      this.mGreenSize = greenSize;
      this.mBlueSize = blueSize;
      this.mAlphaSize = alphaSize;
      this.mDepthSize = depthSize;
      this.mStencilSize = stencilSize;
    }

    @Override // android.opengl.GLSurfaceView.BaseConfigChooser
    public javax.microedition.khronos.egl.EGLConfig chooseConfig(
        EGL10 egl,
        javax.microedition.khronos.egl.EGLDisplay display,
        javax.microedition.khronos.egl.EGLConfig[] configs) {
      for (javax.microedition.khronos.egl.EGLConfig config : configs) {
        int d = findConfigAttrib(egl, display, config, 12325, 0);
        int s = findConfigAttrib(egl, display, config, 12326, 0);
        if (d >= this.mDepthSize && s >= this.mStencilSize) {
          int r = findConfigAttrib(egl, display, config, 12324, 0);
          int g = findConfigAttrib(egl, display, config, 12323, 0);
          int b = findConfigAttrib(egl, display, config, 12322, 0);
          int a = findConfigAttrib(egl, display, config, 12321, 0);
          if (r == this.mRedSize
              && g == this.mGreenSize
              && b == this.mBlueSize
              && a == this.mAlphaSize) {
            return config;
          }
        }
      }
      return null;
    }

    private int findConfigAttrib(
        EGL10 egl,
        javax.microedition.khronos.egl.EGLDisplay display,
        javax.microedition.khronos.egl.EGLConfig config,
        int attribute,
        int defaultValue) {
      if (egl.eglGetConfigAttrib(display, config, attribute, this.mValue)) {
        return this.mValue[0];
      }
      return defaultValue;
    }
  }

  private class SimpleEGLConfigChooser extends ComponentSizeChooser {
    public SimpleEGLConfigChooser(boolean withDepthBuffer) {
      super(8, 8, 8, 0, withDepthBuffer ? 16 : 0, 0);
    }
  }

  private static class EglHelper {
    EGL10 mEgl;
    javax.microedition.khronos.egl.EGLConfig mEglConfig;
    javax.microedition.khronos.egl.EGLContext mEglContext;
    javax.microedition.khronos.egl.EGLDisplay mEglDisplay;
    javax.microedition.khronos.egl.EGLSurface mEglSurface;
    private WeakReference<GLSurfaceView> mGLSurfaceViewWeakRef;

    public EglHelper(WeakReference<GLSurfaceView> glSurfaceViewWeakRef) {
      this.mGLSurfaceViewWeakRef = glSurfaceViewWeakRef;
    }

    public void start() {
      EGL10 egl10 = (EGL10) javax.microedition.khronos.egl.EGLContext.getEGL();
      this.mEgl = egl10;
      javax.microedition.khronos.egl.EGLDisplay eglGetDisplay =
          egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
      this.mEglDisplay = eglGetDisplay;
      if (eglGetDisplay == EGL10.EGL_NO_DISPLAY) {
        throw new RuntimeException("eglGetDisplay failed");
      }
      int[] version = new int[2];
      if (!this.mEgl.eglInitialize(this.mEglDisplay, version)) {
        throw new RuntimeException("eglInitialize failed");
      }
      GLSurfaceView view = this.mGLSurfaceViewWeakRef.get();
      if (view == null) {
        this.mEglConfig = null;
        this.mEglContext = null;
      } else {
        this.mEglConfig = view.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
        this.mEglContext =
            view.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
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
      GLSurfaceView view = this.mGLSurfaceViewWeakRef.get();
      if (view != null) {
        this.mEglSurface =
            view.mEGLWindowSurfaceFactory.createWindowSurface(
                this.mEgl, this.mEglDisplay, this.mEglConfig, view.getHolder());
      } else {
        this.mEglSurface = null;
      }
      javax.microedition.khronos.egl.EGLSurface eGLSurface = this.mEglSurface;
      if (eGLSurface == null || eGLSurface == EGL10.EGL_NO_SURFACE) {
        int error = this.mEgl.eglGetError();
        if (error == 12299) {
          Log.m96e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
        }
        return false;
      }
      EGL10 egl10 = this.mEgl;
      javax.microedition.khronos.egl.EGLDisplay eGLDisplay = this.mEglDisplay;
      javax.microedition.khronos.egl.EGLSurface eGLSurface2 = this.mEglSurface;
      if (!egl10.eglMakeCurrent(eGLDisplay, eGLSurface2, eGLSurface2, this.mEglContext)) {
        logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", this.mEgl.eglGetError());
        return false;
      }
      return true;
    }

    InterfaceC5395GL createGL() {
      InterfaceC5395GL gl = this.mEglContext.getGL();
      GLSurfaceView view = this.mGLSurfaceViewWeakRef.get();
      if (view != null) {
        if (view.mGLWrapper != null) {
          gl = view.mGLWrapper.wrap(gl);
        }
        if ((view.mDebugFlags & 3) != 0) {
          int configFlags = 0;
          Writer log = null;
          if ((view.mDebugFlags & 1) != 0) {
            configFlags = 0 | 1;
          }
          if ((view.mDebugFlags & 2) != 0) {
            log = new LogWriter();
          }
          return GLDebugHelper.wrap(gl, configFlags, log);
        }
        return gl;
      }
      return gl;
    }

    public int swap() {
      if (!this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
        return this.mEgl.eglGetError();
      }
      return 12288;
    }

    public void destroySurface() {
      destroySurfaceImp();
    }

    private void destroySurfaceImp() {
      javax.microedition.khronos.egl.EGLSurface eGLSurface = this.mEglSurface;
      if (eGLSurface != null && eGLSurface != EGL10.EGL_NO_SURFACE) {
        this.mEgl.eglMakeCurrent(
            this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        GLSurfaceView view = this.mGLSurfaceViewWeakRef.get();
        if (view != null) {
          view.mEGLWindowSurfaceFactory.destroySurface(
              this.mEgl, this.mEglDisplay, this.mEglSurface);
        }
        this.mEglSurface = null;
      }
    }

    public void finish() {
      if (this.mEglContext != null) {
        GLSurfaceView view = this.mGLSurfaceViewWeakRef.get();
        if (view != null) {
          view.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
        }
        this.mEglContext = null;
      }
      javax.microedition.khronos.egl.EGLDisplay eGLDisplay = this.mEglDisplay;
      if (eGLDisplay != null) {
        this.mEgl.eglTerminate(eGLDisplay);
        this.mEglDisplay = null;
      }
    }

    private void throwEglException(String function) {
      throwEglException(function, this.mEgl.eglGetError());
    }

    public static void throwEglException(String function, int error) {
      String message = formatEglError(function, error);
      throw new RuntimeException(message);
    }

    public static void logEglErrorAsWarning(String tag, String function, int error) {
      Log.m102w(tag, formatEglError(function, error));
    }

    public static String formatEglError(String function, int error) {
      return function + " failed: " + EGLLogWrapper.getErrorString(error);
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
    private String mTag = TAG;
    private ArrayList<Runnable> mEventQueue = new ArrayList<>();
    private boolean mSizeChanged = true;
    private Runnable mFinishDrawingRunnable = null;
    private int mWidth = 0;
    private int mHeight = 0;
    private boolean mRequestRender = true;
    private int mRenderMode = 1;
    private boolean mWantRenderNotification = false;

    GLThread(WeakReference<GLSurfaceView> glSurfaceViewWeakRef) {
      this.mGLSurfaceViewWeakRef = glSurfaceViewWeakRef;
      setTag();
    }

    private void setTag() {
      this.mTag = "GLThread@" + getId();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      setName("GLThread " + getId());
      try {
        guardedRun();
      } catch (InterruptedException e) {
      } catch (Throwable th) {
        GLSurfaceView.sGLThreadManager.threadExiting(this);
        throw th;
      }
      GLSurfaceView.sGLThreadManager.threadExiting(this);
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

    /* JADX WARN: Removed duplicated region for block: B:126:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x02a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void guardedRun() throws InterruptedException {
      GL10 gl;
      boolean lostEglContext;
      boolean pausing;
      this.mEglHelper = new EglHelper(this.mGLSurfaceViewWeakRef);
      this.mHaveEglContext = false;
      this.mHaveEglSurface = false;
      this.mWantRenderNotification = false;
      GL10 gl2 = null;
      boolean createEglContext = false;
      boolean createEglSurface = false;
      boolean createGlInterface = false;
      boolean lostEglContext2 = false;
      boolean sizeChanged = false;
      boolean wantRenderNotification = false;
      boolean doRenderNotification = false;
      boolean askedToReleaseEglContext = false;
      int w = 0;
      int h = 0;
      Runnable event = null;
      Runnable finishDrawingRunnable = null;
      while (true) {
        try {
        } catch (Throwable th) {
          synchronized (GLSurfaceView.sGLThreadManager) {
          }
        }
        synchronized (GLSurfaceView.sGLThreadManager) {
          while (!this.mShouldExit) {
            try {
              gl = gl2;
              if (this.mEventQueue.isEmpty()) {
                boolean z = this.mPaused;
                boolean pausing2 = this.mRequestPaused;
                if (z != pausing2) {
                  pausing = pausing2;
                  this.mPaused = pausing2;
                  GLSurfaceView.sGLThreadManager.notifyAll();
                } else {
                  pausing = false;
                }
                if (this.mShouldReleaseEglContext) {
                  stopEglSurfaceLocked();
                  stopEglContextLocked();
                  this.mShouldReleaseEglContext = false;
                  askedToReleaseEglContext = true;
                }
                if (lostEglContext2) {
                  stopEglSurfaceLocked();
                  stopEglContextLocked();
                  lostEglContext2 = false;
                }
                if (pausing && this.mHaveEglSurface) {
                  stopEglSurfaceLocked();
                }
                if (pausing && this.mHaveEglContext) {
                  GLSurfaceView view = this.mGLSurfaceViewWeakRef.get();
                  boolean preserveEglContextOnPause =
                      view == null ? false : view.mPreserveEGLContextOnPause;
                  if (!preserveEglContextOnPause) {
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
                if (doRenderNotification) {
                  this.mWantRenderNotification = false;
                  doRenderNotification = false;
                  this.mRenderComplete = true;
                  GLSurfaceView.sGLThreadManager.notifyAll();
                }
                Runnable runnable = this.mFinishDrawingRunnable;
                if (runnable != null) {
                  finishDrawingRunnable = runnable;
                  this.mFinishDrawingRunnable = null;
                }
                if (readyToDraw()) {
                  if (!this.mHaveEglContext) {
                    if (askedToReleaseEglContext) {
                      askedToReleaseEglContext = false;
                    } else {
                      try {
                        this.mEglHelper.start();
                        this.mHaveEglContext = true;
                        createEglContext = true;
                        GLSurfaceView.sGLThreadManager.notifyAll();
                      } catch (RuntimeException t) {
                        GLSurfaceView.sGLThreadManager.releaseEglContextLocked(this);
                        throw t;
                      }
                    }
                  }
                  if (this.mHaveEglContext && !this.mHaveEglSurface) {
                    this.mHaveEglSurface = true;
                    createEglSurface = true;
                    createGlInterface = true;
                    sizeChanged = true;
                  }
                  if (this.mHaveEglSurface) {
                    if (this.mSizeChanged) {
                      sizeChanged = true;
                      w = this.mWidth;
                      h = this.mHeight;
                      this.mWantRenderNotification = true;
                      createEglSurface = true;
                      this.mSizeChanged = false;
                    }
                    this.mRequestRender = false;
                    GLSurfaceView.sGLThreadManager.notifyAll();
                    if (this.mWantRenderNotification) {
                      wantRenderNotification = true;
                    }
                  }
                } else if (finishDrawingRunnable != null) {
                  Log.m102w(
                      TAG,
                      "Warning, !readyToDraw() but waiting for draw finished! Early reporting draw"
                          + " finished.");
                  finishDrawingRunnable.run();
                  finishDrawingRunnable = null;
                }
                GLSurfaceView.sGLThreadManager.wait();
                gl2 = gl;
              } else {
                try {
                  Runnable event2 = this.mEventQueue.remove(0);
                  event = event2;
                  try {
                  } catch (Throwable th2) {
                    th = th2;
                    throw th;
                  }
                } catch (Throwable th3) {
                  th = th3;
                  throw th;
                }
              }
            } catch (Throwable th4) {
              th = th4;
            }
            synchronized (GLSurfaceView.sGLThreadManager) {
              stopEglSurfaceLocked();
              stopEglContextLocked();
              throw th;
            }
          }
          try {
            synchronized (GLSurfaceView.sGLThreadManager) {
              stopEglSurfaceLocked();
              stopEglContextLocked();
            }
            return;
          } catch (Throwable th5) {
            th = th5;
            throw th;
          }
        }
        if (event == null) {
          if (createEglSurface) {
            if (this.mEglHelper.createSurface()) {
              synchronized (GLSurfaceView.sGLThreadManager) {
                this.mFinishedCreatingEglSurface = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
              }
              createEglSurface = false;
            } else {
              synchronized (GLSurfaceView.sGLThreadManager) {
                this.mFinishedCreatingEglSurface = true;
                this.mSurfaceIsBad = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
              }
              gl2 = gl;
            }
            synchronized (GLSurfaceView.sGLThreadManager) {
            }
          }
          if (createGlInterface) {
            gl2 = (GL10) this.mEglHelper.createGL();
            createGlInterface = false;
          } else {
            gl2 = gl;
          }
          boolean createEglSurface2 = createEglSurface;
          boolean createGlInterface2 = createGlInterface;
          if (createEglContext) {
            GLSurfaceView view2 = this.mGLSurfaceViewWeakRef.get();
            if (view2 != null) {
              try {
                Trace.traceBegin(8L, "onSurfaceCreated");
                view2.mRenderer.onSurfaceCreated(gl2, this.mEglHelper.mEglConfig);
                Trace.traceEnd(8L);
              } finally {
              }
            }
            createEglContext = false;
          }
          if (sizeChanged) {
            GLSurfaceView view3 = this.mGLSurfaceViewWeakRef.get();
            if (view3 != null) {
              lostEglContext = lostEglContext2;
              try {
                Trace.traceBegin(8L, "onSurfaceChanged");
                view3.mRenderer.onSurfaceChanged(gl2, w, h);
                Trace.traceEnd(8L);
              } catch (Throwable th6) {
                throw th6;
              }
            } else {
              lostEglContext = lostEglContext2;
            }
            sizeChanged = false;
          } else {
            lostEglContext = lostEglContext2;
          }
          GLSurfaceView view4 = this.mGLSurfaceViewWeakRef.get();
          if (view4 != null) {
            try {
              Trace.traceBegin(8L, "onDrawFrame");
              view4.mRenderer.onDrawFrame(gl2);
              if (finishDrawingRunnable != null) {
                finishDrawingRunnable.run();
                finishDrawingRunnable = null;
              }
              Trace.traceEnd(8L);
            } finally {
            }
          }
          int swapError = this.mEglHelper.swap();
          switch (swapError) {
            case 12288:
              lostEglContext2 = lostEglContext;
              if (wantRenderNotification) {
                doRenderNotification = true;
                wantRenderNotification = false;
              }
              createEglSurface = createEglSurface2;
              createGlInterface = createGlInterface2;
              break;
            case 12302:
              lostEglContext2 = true;
              if (wantRenderNotification) {}
              createEglSurface = createEglSurface2;
              createGlInterface = createGlInterface2;
              break;
            default:
              EglHelper.logEglErrorAsWarning(TAG, "eglSwapBuffers", swapError);
              synchronized (GLSurfaceView.sGLThreadManager) {
                this.mSurfaceIsBad = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
              }
              lostEglContext2 = lostEglContext;
              if (wantRenderNotification) {}
              createEglSurface = createEglSurface2;
              createGlInterface = createGlInterface2;
              break;
          }
        } else {
          event.run();
          event = null;
          gl2 = gl;
        }
      }
    }

    public boolean ableToDraw() {
      return this.mHaveEglContext && this.mHaveEglSurface && readyToDraw();
    }

    private boolean readyToDraw() {
      return !this.mPaused
          && this.mHasSurface
          && !this.mSurfaceIsBad
          && this.mWidth > 0
          && this.mHeight > 0
          && (this.mRequestRender || this.mRenderMode == 1);
    }

    public void setRenderMode(int renderMode) {
      if (renderMode < 0 || renderMode > 1) {
        throw new IllegalArgumentException("renderMode");
      }
      synchronized (GLSurfaceView.sGLThreadManager) {
        this.mRenderMode = renderMode;
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

    public void requestRenderAndNotify(final Runnable finishDrawing) {
      synchronized (GLSurfaceView.sGLThreadManager) {
        if (Thread.currentThread() == this) {
          return;
        }
        this.mWantRenderNotification = true;
        this.mRequestRender = true;
        this.mRenderComplete = false;
        final Runnable oldCallback = this.mFinishDrawingRunnable;
        this.mFinishDrawingRunnable =
            new Runnable() { // from class:
                             // android.opengl.GLSurfaceView$GLThread$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                GLSurfaceView.GLThread.lambda$requestRenderAndNotify$0(oldCallback, finishDrawing);
              }
            };
        GLSurfaceView.sGLThreadManager.notifyAll();
      }
    }

    static /* synthetic */ void lambda$requestRenderAndNotify$0(
        Runnable oldCallback, Runnable finishDrawing) {
      if (oldCallback != null) {
        oldCallback.run();
      }
      if (finishDrawing != null) {
        finishDrawing.run();
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
          } catch (InterruptedException e) {
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
          } catch (InterruptedException e) {
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
          } catch (InterruptedException e) {
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
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
      }
    }

    public void onWindowResize(int w, int h) {
      synchronized (GLSurfaceView.sGLThreadManager) {
        this.mWidth = w;
        this.mHeight = h;
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
          } catch (InterruptedException e) {
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
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
      }
    }

    public void requestReleaseEglContextLocked() {
      this.mShouldReleaseEglContext = true;
      GLSurfaceView.sGLThreadManager.notifyAll();
    }

    public void queueEvent(Runnable r) {
      if (r == null) {
        throw new IllegalArgumentException("r must not be null");
      }
      synchronized (GLSurfaceView.sGLThreadManager) {
        this.mEventQueue.add(r);
        GLSurfaceView.sGLThreadManager.notifyAll();
      }
    }
  }

  static class LogWriter extends Writer {
    private StringBuilder mBuilder = new StringBuilder();

    LogWriter() {}

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
      flushBuilder();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
      flushBuilder();
    }

    @Override // java.io.Writer
    public void write(char[] buf, int offset, int count) {
      for (int i = 0; i < count; i++) {
        char c = buf[offset + i];
        if (c == '\n') {
          flushBuilder();
        } else {
          this.mBuilder.append(c);
        }
      }
    }

    private void flushBuilder() {
      if (this.mBuilder.length() > 0) {
        Log.m100v(GLSurfaceView.TAG, this.mBuilder.toString());
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

    private GLThreadManager() {}

    public synchronized void threadExiting(GLThread thread) {
      thread.mExited = true;
      notifyAll();
    }

    public void releaseEglContextLocked(GLThread thread) {
      notifyAll();
    }
  }
}

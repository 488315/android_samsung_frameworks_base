package com.samsung.android.motionphoto.core;

import android.hardware.HardwareBuffer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes5.dex */
public class MPSurfaceReader implements AutoCloseable {
  private static final String TAG = MPSurfaceReader.class.getSimpleName();
  private final int format;
  private final int height;
  private boolean isReaderValid;
  private OnImageAvailableListener listener;
  private Executor listenerExecutor;
  private Handler listenerHandler;
  private long mNativeContext;
  private final int maxImages;
  public Surface surface;
  private final long usage;
  private final int width;
  private final int dataSpace = 0;
  private final Object listenerLock = new Object();
  private final Object closeLock = new Object();

  public interface OnImageAvailableListener {
    void onImageAvailable(HardwareBuffer hardwareBuffer, long j, int i);
  }

  private static native void nativeClassInit();

  private native void nativeClose();

  private native Surface nativeGetSurface();

  private native void nativeInit(Object obj, int i, int i2, int i3, int i4, int i5, long j);

  static {
    System.loadLibrary(Def.MP_NATIVE_LIB);
    nativeClassInit();
  }

  private MPSurfaceReader() {
    throw new UnsupportedOperationException();
  }

  private MPSurfaceReader(int width, int height, int format, int maxImages, long usage) {
    Log.m94d(
        TAG,
        String.format(
            "MPSurfaceReader: w=%d, h=%d, fmt=0x%x, maxImages=%d, usg=0x%x",
            Integer.valueOf(width),
            Integer.valueOf(height),
            Integer.valueOf(format),
            Integer.valueOf(maxImages),
            Long.valueOf(usage)));
    this.width = width;
    this.height = height;
    this.format = format;
    this.maxImages = maxImages;
    this.usage = usage;
    nativeInit(new WeakReference(this), width, height, format, 0, maxImages, usage);
    this.isReaderValid = true;
    this.surface = nativeGetSurface();
  }

  /* renamed from: of */
  public static MPSurfaceReader m289of(
      int width, int height, int format, int maxImages, long usage) {
    return new MPSurfaceReader(width, height, format, maxImages, usage);
  }

  public void setOnImageAvailableListener(OnImageAvailableListener listener, Handler handler) {
    synchronized (this.listenerLock) {
      if (listener != null) {
        Looper looper = handler != null ? handler.getLooper() : Looper.myLooper();
        if (looper == null) {
          throw new IllegalArgumentException(
              "handler is null but the current thread is not a looper");
        }
        Handler handler2 = this.listenerHandler;
        if (handler2 == null || handler2.getLooper() != looper) {
          Handler createAsync = Handler.createAsync(looper);
          this.listenerHandler = createAsync;
          this.listenerExecutor = new HandlerExecutor(createAsync);
        }
      } else {
        this.listenerHandler = null;
        this.listenerExecutor = null;
      }
      this.listener = listener;
    }
  }

  public Surface getSurface() {
    return this.surface;
  }

  @Override // java.lang.AutoCloseable
  public void close() throws Exception {
    String str = TAG;
    Log.m98i(str, "close MPSurfaceReader...E");
    setOnImageAvailableListener(null, null);
    Surface surface = this.surface;
    if (surface != null) {
      surface.release();
    }
    synchronized (this.closeLock) {
      this.isReaderValid = false;
      nativeClose();
    }
    Log.m98i(str, "close MPSurfaceReader...X");
  }

  private static final class HandlerExecutor implements Executor {
    private final Handler mHandler;

    public HandlerExecutor(Handler handler) {
      this.mHandler = (Handler) Objects.requireNonNull(handler);
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable command) {
      this.mHandler.post(command);
    }
  }

  private static void postEventFromNative(
      Object selfRef, final HardwareBuffer buffer, final long timestampUs, final int dataSpace) {
    Executor executor;
    final OnImageAvailableListener listener;
    boolean isReaderValid;
    WeakReference<MPSurfaceReader> weakSelf = (WeakReference) selfRef;
    MPSurfaceReader sr = weakSelf.get();
    if (sr == null) {
      return;
    }
    synchronized (sr.listenerLock) {
      executor = sr.listenerExecutor;
      listener = sr.listener;
    }
    synchronized (sr.closeLock) {
      isReaderValid = sr.isReaderValid;
    }
    if (executor != null && listener != null && isReaderValid) {
      executor.execute(
          new Runnable() { // from class:
                           // com.samsung.android.motionphoto.core.MPSurfaceReader$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              MPSurfaceReader.OnImageAvailableListener.this.onImageAvailable(
                  buffer, timestampUs, dataSpace);
            }
          });
    }
  }
}

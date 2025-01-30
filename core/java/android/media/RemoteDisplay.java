package android.media;

import android.p009os.Handler;
import android.util.Log;
import android.view.Surface;
import dalvik.system.CloseGuard;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class RemoteDisplay {
  public static final int DISPLAY_ERROR_CONNECTION_DROPPED = 2;
  public static final int DISPLAY_ERROR_UNKOWN = 1;
  public static final int DISPLAY_FLAG_AUDIO_ONLY = 16;
  public static final int DISPLAY_FLAG_DMR_SUPPORT = 64;
  public static final int DISPLAY_FLAG_HIGH_RESOLUTION_SUPPORT = 32;
  public static final int DISPLAY_FLAG_LANDSCAPE = 2;
  public static final int DISPLAY_FLAG_PORTRAIT_270 = 8;
  public static final int DISPLAY_FLAG_PORTRAIT_90 = 4;
  public static final int DISPLAY_FLAG_SECURE = 1;
  private static final String TAG = "RemoteDisplay_Java";
  private final CloseGuard mGuard;
  private final Handler mHandler;
  private final Listener mListener;
  private final NativeListener mNativeListener;
  private final String mOpPackageName;
  private long mPtr;

  public interface Listener {
    void onDisplayChanged(Surface surface, int i, int i2, int i3);

    void onDisplayConnected(Surface surface, int i, int i2, int i3, int i4, String str);

    void onDisplayDisconnected();

    void onDisplayError(int i);
  }

  public interface NativeListener {
    void onNotify(int i, String str);
  }

  private native void nativeDispose(long j);

  private native long nativeListen(String str, String str2);

  private native long nativeListen(String str, String str2, String str3);

  private native void nativePause(long j);

  private native void nativeResume(long j);

  private static native int nativeSetParam(String str);

  private RemoteDisplay(Listener listener, Handler handler, String opPackageName) {
    this.mGuard = CloseGuard.get();
    this.mListener = listener;
    this.mHandler = handler;
    this.mOpPackageName = opPackageName;
    this.mNativeListener = null;
  }

  private RemoteDisplay(
      Listener listener, Handler handler, String opPackageName, NativeListener nativeListener) {
    this.mGuard = CloseGuard.get();
    this.mListener = listener;
    this.mHandler = handler;
    this.mOpPackageName = opPackageName;
    this.mNativeListener = nativeListener;
  }

  protected void finalize() throws Throwable {
    try {
      dispose(true);
    } finally {
      super.finalize();
    }
  }

  public static RemoteDisplay listen(
      String iface, Listener listener, Handler handler, String opPackageName) {
    if (iface == null) {
      throw new IllegalArgumentException("iface must not be null");
    }
    if (listener == null) {
      throw new IllegalArgumentException("listener must not be null");
    }
    if (handler == null) {
      throw new IllegalArgumentException("handler must not be null");
    }
    RemoteDisplay display = new RemoteDisplay(listener, handler, opPackageName);
    display.startListening(iface);
    return display;
  }

  public static RemoteDisplay listen(
      String iface,
      Listener listener,
      Handler handler,
      String opPackageName,
      String setparamInfo,
      NativeListener nativeListener) {
    if (iface == null) {
      throw new IllegalArgumentException("iface must not be null");
    }
    if (listener == null) {
      throw new IllegalArgumentException("listener must not be null");
    }
    if (handler == null) {
      throw new IllegalArgumentException("handler must not be null");
    }
    RemoteDisplay display = new RemoteDisplay(listener, handler, opPackageName, nativeListener);
    display.startListening(iface, setparamInfo);
    return display;
  }

  public void dispose() {
    dispose(false);
  }

  public void pause() {
    nativePause(this.mPtr);
  }

  public void resume() {
    nativeResume(this.mPtr);
  }

  public int setParam(String key, Object data) {
    JSONObject mParam = new JSONObject();
    try {
      mParam.put(key, data);
    } catch (JSONException e) {
      Log.m102w(TAG, e.toString());
    }
    int iRet = nativeSetParam(mParam.toString());
    Log.m94d(TAG, "setParam >> ret is " + iRet);
    mParam.remove(key);
    return iRet;
  }

  private void dispose(boolean finalized) {
    if (this.mPtr != 0) {
      CloseGuard closeGuard = this.mGuard;
      if (closeGuard != null) {
        if (finalized) {
          closeGuard.warnIfOpen();
        } else {
          closeGuard.close();
        }
      }
      nativeDispose(this.mPtr);
      this.mPtr = 0L;
    }
  }

  private void startListening(String iface) {
    long nativeListen = nativeListen(iface, this.mOpPackageName);
    this.mPtr = nativeListen;
    if (nativeListen == 0) {
      throw new IllegalStateException(
          "Could not start listening for remote display connection on \"" + iface + "\"");
    }
    this.mGuard.open("dispose");
  }

  private void startListening(String iface, String initParam) {
    long nativeListen = nativeListen(iface, this.mOpPackageName, initParam);
    this.mPtr = nativeListen;
    if (nativeListen == 0) {
      throw new IllegalStateException(
          "Could not start listening for remote display connection on \"" + iface + "\"");
    }
    this.mGuard.open("dispose");
  }

  private void notifyDisplayConnected(
      final Surface surface,
      final int width,
      final int height,
      final int flags,
      final int session,
      final String msg) {
    this.mHandler.post(
        new Runnable() { // from class: android.media.RemoteDisplay.1
          @Override // java.lang.Runnable
          public void run() {
            RemoteDisplay.this.mListener.onDisplayConnected(
                surface, width, height, flags, session, msg);
          }
        });
  }

  private void notifyDisplayDisconnected() {
    this.mHandler.post(
        new Runnable() { // from class: android.media.RemoteDisplay.2
          @Override // java.lang.Runnable
          public void run() {
            RemoteDisplay.this.mListener.onDisplayDisconnected();
          }
        });
  }

  private void notifyDisplayError(final int error) {
    this.mHandler.post(
        new Runnable() { // from class: android.media.RemoteDisplay.3
          @Override // java.lang.Runnable
          public void run() {
            RemoteDisplay.this.mListener.onDisplayError(error);
          }
        });
  }

  private void notifyDisplayChanged(
      final Surface surface, final int width, final int height, final int flags) {
    this.mHandler.post(
        new Runnable() { // from class: android.media.RemoteDisplay.4
          @Override // java.lang.Runnable
          public void run() {
            RemoteDisplay.this.mListener.onDisplayChanged(surface, width, height, flags);
          }
        });
  }

  private void cbFromNativeWFD(final int msg, final String data) {
    this.mHandler.post(
        new Runnable() { // from class: android.media.RemoteDisplay.5
          @Override // java.lang.Runnable
          public void run() {
            if (RemoteDisplay.this.mNativeListener != null) {
              RemoteDisplay.this.mNativeListener.onNotify(msg, data);
            }
          }
        });
  }
}

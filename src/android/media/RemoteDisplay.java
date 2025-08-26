package android.media;

import android.os.Handler;
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

    private RemoteDisplay(Listener listener, Handler handler, String str) {
        this.mGuard = CloseGuard.get();
        this.mListener = listener;
        this.mHandler = handler;
        this.mOpPackageName = str;
        this.mNativeListener = null;
    }

    private RemoteDisplay(Listener listener, Handler handler, String str, NativeListener nativeListener) {
        this.mGuard = CloseGuard.get();
        this.mListener = listener;
        this.mHandler = handler;
        this.mOpPackageName = str;
        this.mNativeListener = nativeListener;
    }

    protected void finalize() throws Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    public static RemoteDisplay listen(String str, Listener listener, Handler handler, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("iface must not be null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("handler must not be null");
        }
        RemoteDisplay remoteDisplay = new RemoteDisplay(listener, handler, str2);
        remoteDisplay.startListening(str);
        return remoteDisplay;
    }

    public static RemoteDisplay listen(String str, Listener listener, Handler handler, String str2, String str3, NativeListener nativeListener) {
        if (str == null) {
            throw new IllegalArgumentException("iface must not be null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("handler must not be null");
        }
        RemoteDisplay remoteDisplay = new RemoteDisplay(listener, handler, str2, nativeListener);
        remoteDisplay.startListening(str, str3);
        return remoteDisplay;
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

    public int setParam(String str, Object obj) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(str, obj);
        } catch (JSONException e) {
            Log.w(TAG, e.toString());
        }
        int iNativeSetParam = nativeSetParam(jSONObject.toString());
        Log.d(TAG, "setParam >> ret is " + iNativeSetParam);
        jSONObject.remove(str);
        return iNativeSetParam;
    }

    private void dispose(boolean z) {
        if (this.mPtr != 0) {
            CloseGuard closeGuard = this.mGuard;
            if (closeGuard != null) {
                if (z) {
                    closeGuard.warnIfOpen();
                } else {
                    closeGuard.close();
                }
            }
            nativeDispose(this.mPtr);
            this.mPtr = 0L;
        }
    }

    private void startListening(String str) {
        long jNativeListen = nativeListen(str, this.mOpPackageName);
        this.mPtr = jNativeListen;
        if (jNativeListen == 0) {
            throw new IllegalStateException("Could not start listening for remote display connection on \"" + str + "\"");
        }
        this.mGuard.open("dispose");
    }

    private void startListening(String str, String str2) {
        long jNativeListen = nativeListen(str, this.mOpPackageName, str2);
        this.mPtr = jNativeListen;
        if (jNativeListen == 0) {
            throw new IllegalStateException("Could not start listening for remote display connection on \"" + str + "\"");
        }
        this.mGuard.open("dispose");
    }

    private void notifyDisplayConnected(final Surface surface, final int i, final int i2, final int i3, final int i4, final String str) {
        this.mHandler.post(new Runnable() { // from class: android.media.RemoteDisplay.1
            @Override // java.lang.Runnable
            public void run() {
                RemoteDisplay.this.mListener.onDisplayConnected(surface, i, i2, i3, i4, str);
            }
        });
    }

    private void notifyDisplayDisconnected() {
        this.mHandler.post(new Runnable() { // from class: android.media.RemoteDisplay.2
            @Override // java.lang.Runnable
            public void run() {
                RemoteDisplay.this.mListener.onDisplayDisconnected();
            }
        });
    }

    private void notifyDisplayError(final int i) {
        this.mHandler.post(new Runnable() { // from class: android.media.RemoteDisplay.3
            @Override // java.lang.Runnable
            public void run() {
                RemoteDisplay.this.mListener.onDisplayError(i);
            }
        });
    }

    private void notifyDisplayChanged(final Surface surface, final int i, final int i2, final int i3) {
        this.mHandler.post(new Runnable() { // from class: android.media.RemoteDisplay.4
            @Override // java.lang.Runnable
            public void run() {
                RemoteDisplay.this.mListener.onDisplayChanged(surface, i, i2, i3);
            }
        });
    }

    private void cbFromNativeWFD(final int i, final String str) {
        this.mHandler.post(new Runnable() { // from class: android.media.RemoteDisplay.5
            @Override // java.lang.Runnable
            public void run() {
                if (RemoteDisplay.this.mNativeListener != null) {
                    RemoteDisplay.this.mNativeListener.onNotify(i, str);
                }
            }
        });
    }
}

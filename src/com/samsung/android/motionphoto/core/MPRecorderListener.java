package com.samsung.android.motionphoto.core;

import android.os.Looper;
import android.util.Log;
import com.samsung.android.motionphoto.core.MPClientEventHandler;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public class MPRecorderListener {
    private static final String TAG;
    private MPClientEventHandler mEventHandler;
    private long mNativeContext;

    private native void native_finalize();

    private static native void native_init();

    private native void native_setup(Object obj);

    static {
        String property = System.getProperty(Def.JUNIT_TEST_EXECUTION_MODE);
        if (property == null || !Boolean.parseBoolean(property)) {
            System.loadLibrary(Def.MP_LEGACY_NATIVE_LIB);
            native_init();
        }
        TAG = "MPRecorderListener";
    }

    public MPRecorderListener() {
        Log.d(TAG, "MPRecorderListener");
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null) {
            this.mEventHandler = new MPClientEventHandler(looperMyLooper);
        } else {
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new MPClientEventHandler(mainLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        native_setup(new WeakReference(this));
    }

    public void release() {
        Log.d(TAG, "release");
        MPClientEventHandler mPClientEventHandler = this.mEventHandler;
        if (mPClientEventHandler != null) {
            mPClientEventHandler.removeCallbacksAndMessages(null);
            this.mEventHandler = null;
        }
        setOnInfoListener(null);
        setOnErrorListener(null);
        native_finalize();
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public void setToken(int i) {
        Log.d(TAG, "setToken");
        MPClientEventHandler mPClientEventHandler = this.mEventHandler;
        if (mPClientEventHandler != null) {
            mPClientEventHandler.setToken(i);
        }
    }

    public void setOnErrorListener(MPClientEventHandler.OnErrorListener onErrorListener) {
        Log.d(TAG, "setOnErrorListener");
        MPClientEventHandler mPClientEventHandler = this.mEventHandler;
        if (mPClientEventHandler != null) {
            mPClientEventHandler.setOnErrorListener(onErrorListener);
        }
    }

    public void setOnInfoListener(MPClientEventHandler.OnInfoListener onInfoListener) {
        Log.d(TAG, "setOnErrorListener");
        MPClientEventHandler mPClientEventHandler = this.mEventHandler;
        if (mPClientEventHandler != null) {
            mPClientEventHandler.setOnInfoListener(onInfoListener);
        }
    }

    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        MPClientEventHandler mPClientEventHandler;
        Log.d(TAG, String.format("postEventFromNative: %d, %d, %d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)) + ", obj=" + obj2);
        MPRecorderListener mPRecorderListener = (MPRecorderListener) ((WeakReference) obj).get();
        if (mPRecorderListener == null || (mPClientEventHandler = mPRecorderListener.mEventHandler) == null) {
            return;
        }
        mPRecorderListener.mEventHandler.sendMessage(mPClientEventHandler.obtainMessage(i, i2, i3, obj2));
    }
}

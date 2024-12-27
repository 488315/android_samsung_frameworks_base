package com.samsung.android.motionphoto.core;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

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
        System.loadLibrary(Def.MP_LEGACY_NATIVE_LIB);
        native_init();
        TAG = MPRecorderListener.class.getSimpleName();
    }

    public MPRecorderListener() {
        Log.d(TAG, "MPRecorderListener");
        Looper looper = Looper.myLooper();
        if (looper != null) {
            this.mEventHandler = new MPClientEventHandler(looper);
        } else {
            Looper looper2 = Looper.getMainLooper();
            if (looper2 != null) {
                this.mEventHandler = new MPClientEventHandler(looper2);
            } else {
                this.mEventHandler = null;
            }
        }
        native_setup(new WeakReference(this));
    }

    public void release() {
        Log.d(TAG, "release");
        if (this.mEventHandler != null) {
            this.mEventHandler.removeCallbacksAndMessages(null);
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

    public void setToken(int token) {
        Log.d(TAG, "setToken");
        if (this.mEventHandler != null) {
            this.mEventHandler.setToken(token);
        }
    }

    public void setOnErrorListener(MPClientEventHandler.OnErrorListener onErrorListener) {
        Log.d(TAG, "setOnErrorListener");
        if (this.mEventHandler != null) {
            this.mEventHandler.setOnErrorListener(onErrorListener);
        }
    }

    public void setOnInfoListener(MPClientEventHandler.OnInfoListener onInfoListener) {
        Log.d(TAG, "setOnErrorListener");
        if (this.mEventHandler != null) {
            this.mEventHandler.setOnInfoListener(onInfoListener);
        }
    }

    private static void postEventFromNative(
            Object listener_ref, int what, int arg1, int arg2, Object obj) {
        Log.d(
                TAG,
                String.format(
                                "postEventFromNative: %d, %d, %d",
                                Integer.valueOf(what), Integer.valueOf(arg1), Integer.valueOf(arg2))
                        + ", obj="
                        + obj);
        MPRecorderListener l = (MPRecorderListener) ((WeakReference) listener_ref).get();
        if (l != null && l.mEventHandler != null) {
            Message m = l.mEventHandler.obtainMessage(what, arg1, arg2, obj);
            l.mEventHandler.sendMessage(m);
        }
    }
}

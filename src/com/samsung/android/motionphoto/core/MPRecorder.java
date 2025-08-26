package com.samsung.android.motionphoto.core;

import android.util.Log;
import com.samsung.android.motionphoto.core.MPClientEventHandler;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes6.dex */
public class MPRecorder {
    private static final String TAG;
    private MPRecorderListener mListener;
    private final ReentrantLock mLock = new ReentrantLock();
    private long mNativeContext;
    private final int mToken;

    private native void native_finalize();

    private static native void native_init();

    private native int native_setup(Object obj, String str);

    private native void native_start(String str);

    private native void native_stop();

    private native int native_store(long j);

    private native int native_store(String str);

    static {
        String property = System.getProperty(Def.JUNIT_TEST_EXECUTION_MODE);
        if (property == null || !Boolean.parseBoolean(property)) {
            System.loadLibrary(Def.MP_LEGACY_NATIVE_LIB);
            native_init();
        }
        TAG = "MPRecorder";
    }

    public MPRecorder(MPRecorderListener mPRecorderListener) {
        this.mListener = mPRecorderListener;
        int iNative_setup = native_setup(mPRecorderListener, getClass().getName());
        this.mToken = iNative_setup;
        mPRecorderListener.setToken(iNative_setup);
    }

    public void release() {
        Log.d(TAG, "release");
        this.mLock.lock();
        try {
            this.mListener = null;
            if (this.mNativeContext != 0) {
                native_finalize();
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public int getToken() {
        return this.mToken;
    }

    public void start(String str) {
        Log.d(TAG, "start: " + str);
        this.mLock.lock();
        try {
            if (this.mNativeContext != 0) {
                native_start(str);
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public void stop() {
        Log.d(TAG, "stop");
        this.mLock.lock();
        try {
            if (this.mNativeContext != 0) {
                native_stop();
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public int store(Flattenable flattenable) {
        this.mLock.lock();
        try {
            if (this.mNativeContext != 0) {
                return native_store(flattenable.flatten());
            }
            this.mLock.unlock();
            return -1;
        } finally {
            this.mLock.unlock();
        }
    }

    public void store(long j) {
        this.mLock.lock();
        try {
            if (this.mNativeContext != 0) {
                native_store(j);
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public void store(String str) {
        this.mLock.lock();
        try {
            if (this.mNativeContext != 0) {
                native_store(str);
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public void setErrorListener(MPClientEventHandler.OnErrorListener onErrorListener) {
        MPRecorderListener mPRecorderListener = this.mListener;
        if (mPRecorderListener != null) {
            mPRecorderListener.setOnErrorListener(onErrorListener);
        }
    }

    public void setInfoListener(MPClientEventHandler.OnInfoListener onInfoListener) {
        MPRecorderListener mPRecorderListener = this.mListener;
        if (mPRecorderListener != null) {
            mPRecorderListener.setOnInfoListener(onInfoListener);
        }
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }
}

package com.samsung.android.motionphoto.core;

import android.hardware.HardwareBuffer;
import android.media.tv.TvContract;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes6.dex */
public class MPRecordingProxy {
    private static final String TAG;
    private MPRecorderListener mListener;
    private final ReentrantLock mLock = new ReentrantLock();
    private long mNativeContext;
    private final int mToken;

    public enum BufferMode {
        PREVIEW,
        SURFACE,
        VIDEOOUT
    }

    private native void native_finalize();

    private native Object[] native_getMetaBuffers();

    private static native void native_init();

    private native void native_notifyEvent(int i);

    private native void native_sendByteBuffer(ByteBuffer byteBuffer, int i, int i2, int i3, long j);

    private native void native_sendHardwareBuffer(HardwareBuffer hardwareBuffer, long j);

    private native void native_sendVdisMetadataBuffer(int i, String str);

    private native int native_setup(Object obj, int i, String str);

    static {
        System.loadLibrary(Def.MP_LEGACY_NATIVE_LIB);
        native_init();
        TAG = MPRecordingProxy.class.getSimpleName();
    }

    public MPRecordingProxy(int token) {
        Log.d(TAG, "token: " + token);
        native_setup(null, token, TvContract.PARAM_PREVIEW);
        this.mToken = token;
    }

    public MPRecordingProxy(int token, BufferMode bufferMode) {
        String str;
        Log.d(TAG, "token: " + token);
        if (bufferMode == BufferMode.PREVIEW) {
            str = TvContract.PARAM_PREVIEW;
        } else {
            str = bufferMode == BufferMode.VIDEOOUT ? "video-out" : "surface";
        }
        native_setup(null, token, str);
        this.mToken = token;
    }

    public MPRecordingProxy(MPRecorderListener listener, int token) {
        Log.d(TAG, "token: " + token);
        native_setup(listener, token, TvContract.PARAM_PREVIEW);
        this.mToken = token;
    }

    public MPRecordingProxy(MPRecorderListener listener, int token, BufferMode bufferMode) {
        String str;
        Log.d(TAG, "token: " + token);
        if (bufferMode == BufferMode.PREVIEW) {
            str = TvContract.PARAM_PREVIEW;
        } else {
            str = bufferMode == BufferMode.VIDEOOUT ? "video-out" : "surface";
        }
        native_setup(listener, token, str);
        this.mToken = token;
    }

    public void release() {
        try {
            this.mLock.lock();
            if (this.mNativeContext != 0) {
                native_finalize();
            }
        } finally {
            this.mLock.unlock();
        }
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public void sendBuffer(ByteBuffer buffer, int width, int height, int format, long timestampUs) {
        try {
            this.mLock.lock();
            if (this.mNativeContext != 0) {
                native_sendByteBuffer(buffer, width, height, format, timestampUs);
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public void sendBuffer(HardwareBuffer buffer, long timestampUs) {
        try {
            this.mLock.lock();
            if (this.mNativeContext != 0) {
                native_sendHardwareBuffer(buffer, timestampUs);
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public void notifyEvent(int event) {
        try {
            this.mLock.lock();
            if (this.mNativeContext != 0) {
                native_notifyEvent(event);
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public ByteBuffer[] getMetaBuffers() {
        try {
            this.mLock.lock();
            if (this.mNativeContext != 0) {
                return (ByteBuffer[]) native_getMetaBuffers();
            }
            this.mLock.unlock();
            return null;
        } finally {
            this.mLock.unlock();
        }
    }

    public void sendMetadata(int id, String params) {
        try {
            this.mLock.lock();
            if (this.mNativeContext != 0) {
                native_sendVdisMetadataBuffer(id, params);
            }
        } finally {
            this.mLock.unlock();
        }
    }
}

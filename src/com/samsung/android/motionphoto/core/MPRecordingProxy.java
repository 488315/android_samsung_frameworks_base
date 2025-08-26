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
        String property = System.getProperty(Def.JUNIT_TEST_EXECUTION_MODE);
        if (property == null || !Boolean.parseBoolean(property)) {
            System.loadLibrary(Def.MP_LEGACY_NATIVE_LIB);
            native_init();
        }
        TAG = "MPRecordingProxy";
    }

    public MPRecordingProxy(int i) {
        Log.d(TAG, "token: " + i);
        native_setup(null, i, TvContract.PARAM_PREVIEW);
        this.mToken = i;
    }

    public MPRecordingProxy(int i, BufferMode bufferMode) {
        String str;
        Log.d(TAG, "token: " + i);
        if (bufferMode == BufferMode.PREVIEW) {
            str = TvContract.PARAM_PREVIEW;
        } else {
            str = bufferMode == BufferMode.VIDEOOUT ? "video-out" : "surface";
        }
        native_setup(null, i, str);
        this.mToken = i;
    }

    public MPRecordingProxy(MPRecorderListener mPRecorderListener, int i) {
        Log.d(TAG, "token: " + i);
        native_setup(mPRecorderListener, i, TvContract.PARAM_PREVIEW);
        this.mToken = i;
    }

    public MPRecordingProxy(MPRecorderListener mPRecorderListener, int i, BufferMode bufferMode) {
        String str;
        Log.d(TAG, "token: " + i);
        if (bufferMode == BufferMode.PREVIEW) {
            str = TvContract.PARAM_PREVIEW;
        } else {
            str = bufferMode == BufferMode.VIDEOOUT ? "video-out" : "surface";
        }
        native_setup(mPRecorderListener, i, str);
        this.mToken = i;
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

    public void sendBuffer(ByteBuffer byteBuffer, int i, int i2, int i3, long j) {
        try {
            this.mLock.lock();
            if (this.mNativeContext != 0) {
                native_sendByteBuffer(byteBuffer, i, i2, i3, j);
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public void sendBuffer(HardwareBuffer hardwareBuffer, long j) {
        try {
            this.mLock.lock();
            if (this.mNativeContext != 0) {
                native_sendHardwareBuffer(hardwareBuffer, j);
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public void notifyEvent(int i) {
        try {
            this.mLock.lock();
            if (this.mNativeContext != 0) {
                native_notifyEvent(i);
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

    public void sendMetadata(int i, String str) {
        try {
            this.mLock.lock();
            if (this.mNativeContext != 0) {
                native_sendVdisMetadataBuffer(i, str);
            }
        } finally {
            this.mLock.unlock();
        }
    }
}

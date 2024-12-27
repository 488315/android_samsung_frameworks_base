package com.android.server.tv;

import android.media.tv.TvInputHardwareInfo;
import android.media.tv.TvStreamConfig;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Surface;

import com.android.internal.os.SomeArgs;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
final class TvInputHal implements Handler.Callback {
    public static final boolean DEBUG = false;
    public static final int ERROR_NO_INIT = -1;
    public static final int ERROR_STALE_CONFIG = -2;
    public static final int ERROR_UNKNOWN = -3;
    public static final int EVENT_DEVICE_AVAILABLE = 1;
    public static final int EVENT_DEVICE_UNAVAILABLE = 2;
    public static final int EVENT_FIRST_FRAME_CAPTURED = 4;
    public static final int EVENT_STREAM_CONFIGURATION_CHANGED = 3;
    public static final int EVENT_TV_MESSAGE = 5;
    public static final int SUCCESS = 0;
    public static final String TAG = "TvInputHal";
    public final Callback mCallback;
    public final Object mLock = new Object();
    public long mPtr = 0;
    public final SparseIntArray mStreamConfigGenerations = new SparseIntArray();
    public final SparseArray mStreamConfigs = new SparseArray();
    public final Handler mHandler = new Handler(this);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Callback {}

    public TvInputHal(Callback callback) {
        this.mCallback = callback;
    }

    private static native int nativeAddOrUpdateStream(long j, int i, int i2, Surface surface);

    private static native void nativeClose(long j);

    private static native TvStreamConfig[] nativeGetStreamConfigs(long j, int i, int i2);

    private native long nativeOpen(MessageQueue messageQueue);

    private static native int nativeRemoveStream(long j, int i, int i2);

    private static native int nativeSetTvMessageEnabled(long j, int i, int i2, int i3, boolean z);

    public final int addOrUpdateStream(int i, Surface surface, TvStreamConfig tvStreamConfig) {
        synchronized (this.mLock) {
            try {
                if (this.mPtr == 0) {
                    return -1;
                }
                if (this.mStreamConfigGenerations.get(i, 0) != tvStreamConfig.getGeneration()) {
                    return -2;
                }
                return nativeAddOrUpdateStream(this.mPtr, i, tvStreamConfig.getStreamId(), surface)
                                == 0
                        ? 0
                        : -3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void close() {
        synchronized (this.mLock) {
            try {
                long j = this.mPtr;
                if (j != 0) {
                    nativeClose(j);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void deviceAvailableFromNative(TvInputHardwareInfo tvInputHardwareInfo) {
        this.mHandler.obtainMessage(1, tvInputHardwareInfo).sendToTarget();
    }

    public final void deviceUnavailableFromNative(int i) {
        this.mHandler.obtainMessage(2, i, 0).sendToTarget();
    }

    public final void firstFrameCapturedFromNative(int i, int i2) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(3, i, i2));
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        TvStreamConfig[] tvStreamConfigArr;
        TvStreamConfig[] tvStreamConfigArr2;
        int i = message.what;
        if (i == 1) {
            TvInputHardwareInfo tvInputHardwareInfo = (TvInputHardwareInfo) message.obj;
            synchronized (this.mLock) {
                retrieveStreamConfigsLocked(tvInputHardwareInfo.getDeviceId());
                tvStreamConfigArr =
                        (TvStreamConfig[])
                                this.mStreamConfigs.get(tvInputHardwareInfo.getDeviceId());
            }
            ((TvInputHardwareManager) this.mCallback)
                    .onDeviceAvailable(tvInputHardwareInfo, tvStreamConfigArr);
        } else if (i == 2) {
            ((TvInputHardwareManager) this.mCallback).onDeviceUnavailable(message.arg1);
        } else if (i == 3) {
            int i2 = message.arg1;
            int i3 = message.arg2;
            synchronized (this.mLock) {
                retrieveStreamConfigsLocked(i2);
                tvStreamConfigArr2 = (TvStreamConfig[]) this.mStreamConfigs.get(i2);
            }
            ((TvInputHardwareManager) this.mCallback)
                    .onStreamConfigurationChanged(i2, tvStreamConfigArr2, i3);
        } else if (i == 4) {
            int i4 = message.arg1;
            TvInputHardwareManager tvInputHardwareManager = (TvInputHardwareManager) this.mCallback;
            synchronized (tvInputHardwareManager.mLock) {
                try {
                    TvInputHardwareManager.Connection connection =
                            (TvInputHardwareManager.Connection)
                                    tvInputHardwareManager.mConnections.get(i4);
                    if (connection == null) {
                        Slog.e(
                                "TvInputHardwareManager",
                                "FirstFrameCaptured: Cannot find a connection with " + i4);
                    } else {
                        Runnable runnable = connection.mOnFirstFrameCaptured;
                        if (runnable != null) {
                            runnable.run();
                            connection.mOnFirstFrameCaptured = null;
                        }
                    }
                } finally {
                }
            }
        } else {
            if (i != 5) {
                Slog.e(TAG, "Unknown event: " + message);
                return false;
            }
            int i5 = message.arg1;
            int i6 = message.arg2;
            Bundle bundle = (Bundle) message.obj;
            TvInputHardwareManager tvInputHardwareManager2 =
                    (TvInputHardwareManager) this.mCallback;
            synchronized (tvInputHardwareManager2.mLock) {
                try {
                    if (((String) tvInputHardwareManager2.mHardwareInputIdMap.get(i5)) != null) {
                        SomeArgs obtain = SomeArgs.obtain();
                        obtain.arg1 = tvInputHardwareManager2.mHardwareInputIdMap.get(i5);
                        obtain.arg2 = bundle;
                        tvInputHardwareManager2
                                .mHandler
                                .obtainMessage(8, i6, 0, obtain)
                                .sendToTarget();
                    }
                } finally {
                }
            }
        }
        return true;
    }

    public final void init() {
        synchronized (this.mLock) {
            this.mPtr = nativeOpen(this.mHandler.getLooper().getQueue());
        }
    }

    public final int removeStream(int i, TvStreamConfig tvStreamConfig) {
        synchronized (this.mLock) {
            try {
                if (this.mPtr == 0) {
                    return -1;
                }
                if (this.mStreamConfigGenerations.get(i, 0) != tvStreamConfig.getGeneration()) {
                    return -2;
                }
                return nativeRemoveStream(this.mPtr, i, tvStreamConfig.getStreamId()) == 0 ? 0 : -3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void retrieveStreamConfigsLocked(int i) {
        int i2 = this.mStreamConfigGenerations.get(i, 0) + 1;
        this.mStreamConfigs.put(i, nativeGetStreamConfigs(this.mPtr, i, i2));
        this.mStreamConfigGenerations.put(i, i2);
    }

    public final int setTvMessageEnabled(int i, TvStreamConfig tvStreamConfig, int i2, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mPtr == 0) {
                    return -1;
                }
                if (this.mStreamConfigGenerations.get(i, 0) != tvStreamConfig.getGeneration()) {
                    return -2;
                }
                return nativeSetTvMessageEnabled(this.mPtr, i, tvStreamConfig.getStreamId(), i2, z)
                                == 0
                        ? 0
                        : -3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void streamConfigsChangedFromNative(int i, int i2) {
        this.mHandler.obtainMessage(3, i, i2).sendToTarget();
    }

    public final void tvMessageReceivedFromNative(int i, int i2, Bundle bundle) {
        this.mHandler.obtainMessage(5, i, i2, bundle).sendToTarget();
    }
}

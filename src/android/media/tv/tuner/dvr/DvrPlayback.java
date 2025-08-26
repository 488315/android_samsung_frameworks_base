package android.media.tv.tuner.dvr;

import android.annotation.SystemApi;
import android.media.tv.tuner.TunerUtils;
import android.media.tv.tuner.TunerVersionChecker;
import android.media.tv.tuner.filter.Filter;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes3.dex */
public class DvrPlayback implements AutoCloseable {
    public static final int PLAYBACK_STATUS_ALMOST_EMPTY = 2;
    public static final int PLAYBACK_STATUS_ALMOST_FULL = 4;
    public static final int PLAYBACK_STATUS_EMPTY = 1;
    public static final int PLAYBACK_STATUS_FULL = 8;
    private static final String TAG = "TvTunerPlayback";
    private static int sInstantId;
    private Executor mExecutor;
    private OnPlaybackStatusChangedListener mListener;
    private long mNativeContext;
    private int mSegmentId;
    private int mUnderflow;
    private final Object mListenerLock = new Object();
    private int mUserId = Process.myUid();

    @Retention(RetentionPolicy.SOURCE)
    @interface PlaybackStatus {
    }

    private native int nativeAttachFilter(Filter filter);

    private native int nativeClose();

    private native int nativeConfigureDvr(DvrSettings dvrSettings);

    private native int nativeDetachFilter(Filter filter);

    private native int nativeFlushDvr();

    private native long nativeRead(long j);

    private native long nativeRead(byte[] bArr, long j, long j2);

    private native long nativeSeek(long j);

    private native void nativeSetFileDescriptor(int i);

    private native int nativeSetStatusCheckIntervalHint(long j);

    private native int nativeStartDvr();

    private native int nativeStopDvr();

    @Deprecated
    public int attachFilter(Filter filter) {
        return 1;
    }

    @Deprecated
    public int detachFilter(Filter filter) {
        return 1;
    }

    private DvrPlayback() {
        this.mSegmentId = 0;
        int i = sInstantId;
        this.mSegmentId = (65535 & i) << 16;
        sInstantId = i + 1;
    }

    public void setListener(Executor executor, OnPlaybackStatusChangedListener onPlaybackStatusChangedListener) {
        synchronized (this.mListenerLock) {
            this.mExecutor = executor;
            this.mListener = onPlaybackStatusChangedListener;
        }
    }

    private void onPlaybackStatusChanged(final int i) {
        if (i == 1) {
            this.mUnderflow++;
        }
        synchronized (this.mListenerLock) {
            Executor executor = this.mExecutor;
            if (executor != null && this.mListener != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.dvr.DvrPlayback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onPlaybackStatusChanged$0(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPlaybackStatusChanged$0(int i) {
        synchronized (this.mListenerLock) {
            OnPlaybackStatusChangedListener onPlaybackStatusChangedListener = this.mListener;
            if (onPlaybackStatusChangedListener != null) {
                onPlaybackStatusChangedListener.onPlaybackStatusChanged(i);
            }
        }
    }

    public int configure(DvrSettings dvrSettings) {
        return nativeConfigureDvr(dvrSettings);
    }

    public int setPlaybackBufferStatusCheckIntervalHint(long j) {
        if (TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "Set status check interval hint")) {
            return nativeSetStatusCheckIntervalHint(j);
        }
        return 1;
    }

    public int start() {
        int i = this.mSegmentId;
        this.mSegmentId = (((i & 65535) + 1) & 65535) | ((-65536) & i);
        this.mUnderflow = 0;
        Log.d(TAG, "Write Stats Log for Playback.");
        FrameworkStatsLog.write(279, this.mUserId, 1, 1, this.mSegmentId, 0);
        return nativeStartDvr();
    }

    public int stop() {
        Log.d(TAG, "Write Stats Log for Playback.");
        FrameworkStatsLog.write(279, this.mUserId, 1, 2, this.mSegmentId, this.mUnderflow);
        return nativeStopDvr();
    }

    public int flush() {
        return nativeFlushDvr();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        int iNativeClose = nativeClose();
        if (iNativeClose != 0) {
            TunerUtils.throwExceptionForResult(iNativeClose, "failed to close DVR playback");
        }
    }

    public void setFileDescriptor(ParcelFileDescriptor parcelFileDescriptor) {
        nativeSetFileDescriptor(parcelFileDescriptor.getFd());
    }

    public long read(long j) {
        return nativeRead(j);
    }

    public long read(byte[] bArr, long j, long j2) {
        if (j2 + j > bArr.length) {
            throw new ArrayIndexOutOfBoundsException("Array length=" + bArr.length + ", offset=" + j + ", size=" + j2);
        }
        return nativeRead(bArr, j, j2);
    }

    public long seek(long j) {
        return nativeSeek(j);
    }
}

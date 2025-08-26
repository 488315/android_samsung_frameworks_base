package android.media.tv.tuner.dvr;

import android.annotation.SystemApi;
import android.media.tv.tuner.TunerUtils;
import android.media.tv.tuner.TunerVersionChecker;
import android.media.tv.tuner.filter.Filter;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes3.dex */
public class DvrRecorder implements AutoCloseable {
    private static final String TAG = "TvTunerRecord";
    private static int sInstantId;
    private Executor mExecutor;
    private OnRecordStatusChangedListener mListener;
    private long mNativeContext;
    private int mOverflow;
    private int mSegmentId;
    private final Object mIsStoppedLock = new Object();
    private boolean mIsStopped = true;
    private final Object mListenerLock = new Object();
    private int mUserId = Process.myUid();

    private native int nativeAttachFilter(Filter filter);

    private native int nativeClose();

    private native int nativeConfigureDvr(DvrSettings dvrSettings);

    private native int nativeDetachFilter(Filter filter);

    private native int nativeFlushDvr();

    private native void nativeSetFileDescriptor(int i);

    private native int nativeSetStatusCheckIntervalHint(long j);

    private native int nativeStartDvr();

    private native int nativeStopDvr();

    private native long nativeWrite(long j);

    private native long nativeWrite(byte[] bArr, long j, long j2);

    private DvrRecorder() {
        this.mSegmentId = 0;
        int i = sInstantId;
        this.mSegmentId = (65535 & i) << 16;
        sInstantId = i + 1;
    }

    public void setListener(Executor executor, OnRecordStatusChangedListener onRecordStatusChangedListener) {
        synchronized (this.mListenerLock) {
            this.mExecutor = executor;
            this.mListener = onRecordStatusChangedListener;
        }
    }

    private void onRecordStatusChanged(final int i) {
        if (i == 8) {
            this.mOverflow++;
        }
        synchronized (this.mListenerLock) {
            Executor executor = this.mExecutor;
            if (executor != null && this.mListener != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.dvr.DvrRecorder$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onRecordStatusChanged$0(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRecordStatusChanged$0(int i) {
        synchronized (this.mListenerLock) {
            OnRecordStatusChangedListener onRecordStatusChangedListener = this.mListener;
            if (onRecordStatusChangedListener != null) {
                onRecordStatusChangedListener.onRecordStatusChanged(i);
            }
        }
    }

    public int attachFilter(Filter filter) {
        return nativeAttachFilter(filter);
    }

    public int detachFilter(Filter filter) {
        return nativeDetachFilter(filter);
    }

    public int configure(DvrSettings dvrSettings) {
        return nativeConfigureDvr(dvrSettings);
    }

    public int setRecordBufferStatusCheckIntervalHint(long j) {
        if (TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "Set status check interval hint")) {
            return nativeSetStatusCheckIntervalHint(j);
        }
        return 1;
    }

    public int start() {
        int iNativeStartDvr;
        int i = this.mSegmentId;
        this.mSegmentId = (((i & 65535) + 1) & 65535) | ((-65536) & i);
        this.mOverflow = 0;
        Log.d(TAG, "Write Stats Log for Record.");
        FrameworkStatsLog.write(279, this.mUserId, 2, 1, this.mSegmentId, 0);
        synchronized (this.mIsStoppedLock) {
            iNativeStartDvr = nativeStartDvr();
            if (iNativeStartDvr == 0) {
                this.mIsStopped = false;
            }
        }
        return iNativeStartDvr;
    }

    public int stop() {
        int iNativeStopDvr;
        Log.d(TAG, "Write Stats Log for Playback.");
        FrameworkStatsLog.write(279, this.mUserId, 2, 2, this.mSegmentId, this.mOverflow);
        synchronized (this.mIsStoppedLock) {
            iNativeStopDvr = nativeStopDvr();
            if (iNativeStopDvr == 0) {
                this.mIsStopped = true;
            }
        }
        return iNativeStopDvr;
    }

    public int flush() {
        synchronized (this.mIsStoppedLock) {
            if (this.mIsStopped) {
                return nativeFlushDvr();
            }
            Log.w(TAG, "Cannot flush non-stopped Record DVR.");
            return 3;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        int iNativeClose = nativeClose();
        if (iNativeClose != 0) {
            TunerUtils.throwExceptionForResult(iNativeClose, "failed to close DVR recorder");
        }
    }

    public void setFileDescriptor(ParcelFileDescriptor parcelFileDescriptor) {
        nativeSetFileDescriptor(parcelFileDescriptor.getFd());
    }

    public long write(long j) {
        return nativeWrite(j);
    }

    public long write(byte[] bArr, long j, long j2) {
        if (j2 + j > bArr.length) {
            throw new ArrayIndexOutOfBoundsException("Array length=" + bArr.length + ", offset=" + j + ", size=" + j2);
        }
        return nativeWrite(bArr, j, j2);
    }
}

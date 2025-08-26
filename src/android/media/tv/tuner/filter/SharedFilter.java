package android.media.tv.tuner.filter;

import android.annotation.SystemApi;
import android.media.tv.tuner.TunerUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes3.dex */
public final class SharedFilter implements AutoCloseable {
    public static final int STATUS_INACCESSIBLE = 128;
    private static final String TAG = "SharedFilter";
    private SharedFilterCallback mCallback;
    private Object mCallbackLock;
    private Executor mExecutor;
    private Object mLock;
    private long mNativeContext;
    private boolean mIsClosed = false;
    private boolean mIsAccessible = true;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    private native int nativeFlushSharedFilter();

    private native int nativeSharedClose();

    private native int nativeSharedRead(byte[] bArr, long j, long j2);

    private native int nativeStartSharedFilter();

    private native int nativeStopSharedFilter();

    private SharedFilter() {
        this.mCallbackLock = null;
        this.mLock = null;
        this.mCallbackLock = new Object();
        this.mLock = new Object();
    }

    private void onFilterStatus(final int i) {
        Executor executor;
        synchronized (this.mLock) {
            if (i == 128) {
                this.mIsAccessible = false;
            }
        }
        synchronized (this.mCallbackLock) {
            if (this.mCallback != null && (executor = this.mExecutor) != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.filter.SharedFilter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onFilterStatus$0(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFilterStatus$0(int i) {
        synchronized (this.mCallbackLock) {
            SharedFilterCallback sharedFilterCallback = this.mCallback;
            if (sharedFilterCallback != null) {
                sharedFilterCallback.onFilterStatusChanged(this, i);
            }
        }
    }

    private void onFilterEvent(final FilterEvent[] filterEventArr) {
        Executor executor;
        synchronized (this.mCallbackLock) {
            if (this.mCallback == null || (executor = this.mExecutor) == null) {
                for (FilterEvent filterEvent : filterEventArr) {
                    if (filterEvent instanceof MediaEvent) {
                        ((MediaEvent) filterEvent).release();
                    }
                }
            } else {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.filter.SharedFilter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onFilterEvent$1(filterEventArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFilterEvent$1(FilterEvent[] filterEventArr) {
        synchronized (this.mCallbackLock) {
            SharedFilterCallback sharedFilterCallback = this.mCallback;
            if (sharedFilterCallback != null) {
                sharedFilterCallback.onFilterEvent(this, filterEventArr);
            } else {
                for (FilterEvent filterEvent : filterEventArr) {
                    if (filterEvent instanceof MediaEvent) {
                        ((MediaEvent) filterEvent).release();
                    }
                }
            }
        }
    }

    public void setCallback(SharedFilterCallback sharedFilterCallback, Executor executor) {
        synchronized (this.mCallbackLock) {
            this.mCallback = sharedFilterCallback;
            this.mExecutor = executor;
        }
    }

    public SharedFilterCallback getCallback() {
        SharedFilterCallback sharedFilterCallback;
        synchronized (this.mCallbackLock) {
            sharedFilterCallback = this.mCallback;
        }
        return sharedFilterCallback;
    }

    public int start() {
        int iNativeStartSharedFilter;
        synchronized (this.mLock) {
            TunerUtils.checkResourceAccessible(TAG, this.mIsAccessible);
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            iNativeStartSharedFilter = nativeStartSharedFilter();
        }
        return iNativeStartSharedFilter;
    }

    public int stop() {
        int iNativeStopSharedFilter;
        synchronized (this.mLock) {
            TunerUtils.checkResourceAccessible(TAG, this.mIsAccessible);
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            iNativeStopSharedFilter = nativeStopSharedFilter();
        }
        return iNativeStopSharedFilter;
    }

    public int flush() {
        int iNativeFlushSharedFilter;
        synchronized (this.mLock) {
            TunerUtils.checkResourceAccessible(TAG, this.mIsAccessible);
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            iNativeFlushSharedFilter = nativeFlushSharedFilter();
        }
        return iNativeFlushSharedFilter;
    }

    public int read(byte[] bArr, long j, long j2) {
        int iNativeSharedRead;
        synchronized (this.mLock) {
            TunerUtils.checkResourceAccessible(TAG, this.mIsAccessible);
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            iNativeSharedRead = nativeSharedRead(bArr, j, Math.min(j2, bArr.length - j));
        }
        return iNativeSharedRead;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            synchronized (this.mCallbackLock) {
                this.mCallback = null;
                this.mExecutor = null;
            }
            nativeSharedClose();
            this.mIsClosed = true;
            this.mCallbackLock = null;
        }
    }
}

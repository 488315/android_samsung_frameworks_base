package android.media.tv.tuner.filter;

import android.annotation.SystemApi;
import android.media.tv.tuner.TunerUtils;
import android.media.tv.tuner.TunerVersionChecker;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes3.dex */
public class Filter implements AutoCloseable {
    public static final int MONITOR_EVENT_IP_CID_CHANGE = 2;
    public static final int MONITOR_EVENT_SCRAMBLING_STATUS = 1;
    public static final int SCRAMBLING_STATUS_NOT_SCRAMBLED = 2;
    public static final int SCRAMBLING_STATUS_SCRAMBLED = 4;
    public static final int SCRAMBLING_STATUS_UNKNOWN = 1;
    public static final int STATUS_DATA_READY = 1;
    public static final int STATUS_HIGH_WATER = 4;
    public static final int STATUS_LOW_WATER = 2;
    public static final int STATUS_NO_DATA = 16;
    public static final int STATUS_OVERFLOW = 8;
    public static final int SUBTYPE_AUDIO = 3;
    public static final int SUBTYPE_DOWNLOAD = 5;
    public static final int SUBTYPE_IP = 13;
    public static final int SUBTYPE_IP_PAYLOAD = 12;
    public static final int SUBTYPE_MMTP = 10;
    public static final int SUBTYPE_NTP = 11;
    public static final int SUBTYPE_PAYLOAD_THROUGH = 14;
    public static final int SUBTYPE_PCR = 8;
    public static final int SUBTYPE_PES = 2;
    public static final int SUBTYPE_PTP = 16;
    public static final int SUBTYPE_RECORD = 6;
    public static final int SUBTYPE_SECTION = 1;
    public static final int SUBTYPE_TEMI = 9;
    public static final int SUBTYPE_TLV = 15;
    public static final int SUBTYPE_TS = 7;
    public static final int SUBTYPE_UNDEFINED = 0;
    public static final int SUBTYPE_VIDEO = 4;
    private static final String TAG = "Filter";
    public static final int TYPE_ALP = 16;
    public static final int TYPE_IP = 4;
    public static final int TYPE_MMTP = 2;
    public static final int TYPE_TLV = 8;
    public static final int TYPE_TS = 1;
    public static final int TYPE_UNDEFINED = 0;
    private FilterCallback mCallback;
    private Executor mExecutor;
    private final long mId;
    private int mMainType;
    private long mNativeContext;
    private Filter mSource;
    private boolean mStarted;
    private int mSubtype;
    private final Object mCallbackLock = new Object();
    private boolean mIsClosed = false;
    private boolean mIsStarted = false;
    private boolean mIsShared = false;
    private final Object mLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    public @interface MonitorEventMask {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScramblingStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Subtype {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private native String nativeAcquireSharedFilterToken();

    private native int nativeClose();

    private native int nativeConfigureFilter(int i, int i2, FilterConfiguration filterConfiguration);

    private native int nativeConfigureMonitorEvent(int i);

    private native int nativeFlushFilter();

    private native void nativeFreeSharedFilterToken(String str);

    private native int nativeGetId();

    private native long nativeGetId64Bit();

    private native int nativeRead(byte[] bArr, long j, long j2);

    private native int nativeSetDataSizeDelayHint(int i);

    private native int nativeSetDataSource(Filter filter);

    private native int nativeSetTimeDelayHint(int i);

    private native int nativeStartFilter();

    private native int nativeStopFilter();

    private Filter(long id) {
        this.mId = id;
    }

    private void onFilterStatus(final int status) {
        synchronized (this.mCallbackLock) {
            if (this.mCallback != null && this.mExecutor != null) {
                this.mExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.filter.Filter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Filter.this.lambda$onFilterStatus$0(status);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFilterStatus$0(int status) {
        FilterCallback callback;
        synchronized (this.mCallbackLock) {
            callback = this.mCallback;
        }
        if (callback != null) {
            try {
                callback.onFilterStatusChanged(this, status);
            } catch (NullPointerException e) {
                Log.d(TAG, "catch exception:" + e);
            }
        }
        if (callback != null) {
            callback.onFilterStatusChanged(this, status);
        }
    }

    private void onFilterEvent(final FilterEvent[] events) {
        synchronized (this.mCallbackLock) {
            if (this.mCallback == null || this.mExecutor == null) {
                for (FilterEvent event : events) {
                    if (event instanceof MediaEvent) {
                        ((MediaEvent) event).release();
                    }
                }
            } else {
                this.mExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.filter.Filter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Filter.this.lambda$onFilterEvent$1(events);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFilterEvent$1(FilterEvent[] events) {
        FilterCallback callback;
        synchronized (this.mCallbackLock) {
            callback = this.mCallback;
        }
        if (callback != null) {
            try {
                callback.onFilterEvent(this, events);
                return;
            } catch (NullPointerException e) {
                Log.d(TAG, "catch exception:" + e);
                return;
            }
        }
        for (FilterEvent event : events) {
            if (event instanceof MediaEvent) {
                ((MediaEvent) event).release();
            }
        }
    }

    public void setType(int mainType, int subtype) {
        this.mMainType = mainType;
        this.mSubtype = TunerUtils.getFilterSubtype(mainType, subtype);
    }

    public void setCallback(FilterCallback cb, Executor executor) {
        synchronized (this.mCallbackLock) {
            this.mCallback = cb;
            this.mExecutor = executor;
        }
    }

    public FilterCallback getCallback() {
        FilterCallback filterCallback;
        synchronized (this.mCallbackLock) {
            filterCallback = this.mCallback;
        }
        return filterCallback;
    }

    public int configure(FilterConfiguration config) {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            Settings s = config.getSettings();
            int subType = s == null ? this.mSubtype : s.getType();
            if (this.mMainType != config.getType() || this.mSubtype != subType) {
                throw new IllegalArgumentException("Invalid filter config. filter main type=" + this.mMainType + ", filter subtype=" + this.mSubtype + ". config main type=" + config.getType() + ", config subtype=" + subType);
            }
            if ((s instanceof RecordSettings) && ((RecordSettings) s).getScIndexType() == 4 && !TunerVersionChecker.isHigherOrEqualVersionTo(196608)) {
                Log.e(TAG, "Tuner version " + TunerVersionChecker.getTunerVersion() + " does not support VVC");
                return 1;
            }
            return nativeConfigureFilter(config.getType(), subType, config);
        }
    }

    public int getId() {
        int nativeGetId;
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            nativeGetId = nativeGetId();
        }
        return nativeGetId;
    }

    public long getIdLong() {
        long nativeGetId64Bit;
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            nativeGetId64Bit = nativeGetId64Bit();
        }
        return nativeGetId64Bit;
    }

    public int setMonitorEventMask(int monitorEventMask) {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            if (!TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "setMonitorEventMask")) {
                return 1;
            }
            return nativeConfigureMonitorEvent(monitorEventMask);
        }
    }

    public int setDataSource(Filter source) {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            if (this.mSource != null) {
                throw new IllegalStateException("Data source is existing");
            }
            int res = nativeSetDataSource(source);
            if (res == 0) {
                this.mSource = source;
            }
            return res;
        }
    }

    public int start() {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            int res = nativeStartFilter();
            if (res == 0) {
                this.mIsStarted = true;
            }
            return res;
        }
    }

    public int stop() {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            int res = nativeStopFilter();
            if (res == 0) {
                this.mIsStarted = false;
            }
            return res;
        }
    }

    public int flush() {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            return nativeFlushFilter();
        }
    }

    public int read(byte[] buffer, long offset, long size) {
        synchronized (this.mLock) {
            try {
                TunerUtils.checkResourceState(TAG, this.mIsClosed);
                if (this.mIsShared) {
                    return 0;
                }
                try {
                    return nativeRead(buffer, offset, Math.min(size, buffer.length - offset));
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mCallbackLock) {
            this.mCallback = null;
            this.mExecutor = null;
        }
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            int res = nativeClose();
            if (res != 0) {
                TunerUtils.throwExceptionForResult(res, "Failed to close filter.");
            } else {
                this.mIsStarted = false;
                this.mIsClosed = true;
            }
        }
    }

    public String acquireSharedFilterToken() {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (!this.mIsStarted && !this.mIsShared) {
                String token = nativeAcquireSharedFilterToken();
                if (token != null) {
                    this.mIsShared = true;
                }
                return token;
            }
            Log.d(TAG, "Acquire shared filter in a wrong state, started: " + this.mIsStarted + "shared: " + this.mIsShared);
            return null;
        }
    }

    public void freeSharedFilterToken(String filterToken) {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                nativeFreeSharedFilterToken(filterToken);
                this.mIsShared = false;
            }
        }
    }

    public int delayCallbackForDurationMillis(long durationInMs) {
        int nativeSetTimeDelayHint;
        if (!TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setTimeDelayHint")) {
            return 1;
        }
        if (durationInMs >= 0 && durationInMs <= 2147483647L) {
            synchronized (this.mLock) {
                nativeSetTimeDelayHint = nativeSetTimeDelayHint((int) durationInMs);
            }
            return nativeSetTimeDelayHint;
        }
        return 4;
    }

    public int delayCallbackUntilBytesAccumulated(int bytesAccumulated) {
        int nativeSetDataSizeDelayHint;
        if (!TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setTimeDelayHint")) {
            return 1;
        }
        synchronized (this.mLock) {
            nativeSetDataSizeDelayHint = nativeSetDataSizeDelayHint(bytesAccumulated);
        }
        return nativeSetDataSizeDelayHint;
    }
}

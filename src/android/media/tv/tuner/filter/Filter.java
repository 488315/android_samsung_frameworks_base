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

    private Filter(long j) {
        this.mId = j;
    }

    private void onFilterStatus(final int i) {
        Executor executor;
        synchronized (this.mCallbackLock) {
            if (this.mCallback != null && (executor = this.mExecutor) != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.filter.Filter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Filter.this.lambda$onFilterStatus$0(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFilterStatus$0(int i) {
        FilterCallback filterCallback;
        synchronized (this.mCallbackLock) {
            filterCallback = this.mCallback;
        }
        if (filterCallback != null) {
            try {
                filterCallback.onFilterStatusChanged(this, i);
            } catch (NullPointerException e) {
                Log.d(TAG, "catch exception:" + e);
            }
        }
        if (filterCallback != null) {
            filterCallback.onFilterStatusChanged(this, i);
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
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.filter.Filter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Filter.this.lambda$onFilterEvent$1(filterEventArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFilterEvent$1(FilterEvent[] filterEventArr) {
        FilterCallback filterCallback;
        synchronized (this.mCallbackLock) {
            filterCallback = this.mCallback;
        }
        if (filterCallback != null) {
            try {
                filterCallback.onFilterEvent(this, filterEventArr);
                return;
            } catch (NullPointerException e) {
                Log.d(TAG, "catch exception:" + e);
                return;
            }
        }
        for (FilterEvent filterEvent : filterEventArr) {
            if (filterEvent instanceof MediaEvent) {
                ((MediaEvent) filterEvent).release();
            }
        }
    }

    public void setType(int i, int i2) {
        this.mMainType = i;
        this.mSubtype = TunerUtils.getFilterSubtype(i, i2);
    }

    public void setCallback(FilterCallback filterCallback, Executor executor) {
        synchronized (this.mCallbackLock) {
            this.mCallback = filterCallback;
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

    public int configure(FilterConfiguration filterConfiguration) {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            Settings settings = filterConfiguration.getSettings();
            int type = settings == null ? this.mSubtype : settings.getType();
            if (this.mMainType != filterConfiguration.getType() || this.mSubtype != type) {
                throw new IllegalArgumentException("Invalid filter config. filter main type=" + this.mMainType + ", filter subtype=" + this.mSubtype + ". config main type=" + filterConfiguration.getType() + ", config subtype=" + type);
            }
            if ((settings instanceof RecordSettings) && ((RecordSettings) settings).getScIndexType() == 4 && !TunerVersionChecker.isHigherOrEqualVersionTo(196608)) {
                Log.e(TAG, "Tuner version " + TunerVersionChecker.getTunerVersion() + " does not support VVC");
                return 1;
            }
            return nativeConfigureFilter(filterConfiguration.getType(), type, filterConfiguration);
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

    public int setMonitorEventMask(int i) {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            if (!TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "setMonitorEventMask")) {
                return 1;
            }
            return nativeConfigureMonitorEvent(i);
        }
    }

    public int setDataSource(Filter filter) {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            if (this.mSource != null) {
                throw new IllegalStateException("Data source is existing");
            }
            int nativeSetDataSource = nativeSetDataSource(filter);
            if (nativeSetDataSource == 0) {
                this.mSource = filter;
            }
            return nativeSetDataSource;
        }
    }

    public int start() {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            int nativeStartFilter = nativeStartFilter();
            if (nativeStartFilter == 0) {
                this.mIsStarted = true;
            }
            return nativeStartFilter;
        }
    }

    public int stop() {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            int nativeStopFilter = nativeStopFilter();
            if (nativeStopFilter == 0) {
                this.mIsStarted = false;
            }
            return nativeStopFilter;
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

    public int read(byte[] bArr, long j, long j2) {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 0;
            }
            return nativeRead(bArr, j, Math.min(j2, bArr.length - j));
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
            int nativeClose = nativeClose();
            if (nativeClose != 0) {
                TunerUtils.throwExceptionForResult(nativeClose, "Failed to close filter.");
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
                String nativeAcquireSharedFilterToken = nativeAcquireSharedFilterToken();
                if (nativeAcquireSharedFilterToken != null) {
                    this.mIsShared = true;
                }
                return nativeAcquireSharedFilterToken;
            }
            Log.d(TAG, "Acquire shared filter in a wrong state, started: " + this.mIsStarted + "shared: " + this.mIsShared);
            return null;
        }
    }

    public void freeSharedFilterToken(String str) {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                nativeFreeSharedFilterToken(str);
                this.mIsShared = false;
            }
        }
    }

    public int delayCallbackForDurationMillis(long j) {
        int nativeSetTimeDelayHint;
        if (!TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setTimeDelayHint")) {
            return 1;
        }
        if (j < 0 || j > 2147483647L) {
            return 4;
        }
        synchronized (this.mLock) {
            nativeSetTimeDelayHint = nativeSetTimeDelayHint((int) j);
        }
        return nativeSetTimeDelayHint;
    }

    public int delayCallbackUntilBytesAccumulated(int i) {
        int nativeSetDataSizeDelayHint;
        if (!TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setTimeDelayHint")) {
            return 1;
        }
        synchronized (this.mLock) {
            nativeSetDataSizeDelayHint = nativeSetDataSizeDelayHint(i);
        }
        return nativeSetDataSizeDelayHint;
    }
}

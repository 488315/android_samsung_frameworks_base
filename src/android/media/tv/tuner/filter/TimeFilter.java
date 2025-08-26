package android.media.tv.tuner.filter;

import android.annotation.SystemApi;
import android.media.tv.tuner.TunerUtils;

@SystemApi
/* loaded from: classes3.dex */
public class TimeFilter implements AutoCloseable {
    private boolean mEnable = false;
    private long mNativeContext;

    private native int nativeClearTimestamp();

    private native int nativeClose();

    private native Long nativeGetSourceTime();

    private native Long nativeGetTimestamp();

    private native int nativeSetTimestamp(long j);

    private TimeFilter() {
    }

    public int setCurrentTimestamp(long j) {
        int iNativeSetTimestamp = nativeSetTimestamp(j);
        if (iNativeSetTimestamp == 0) {
            this.mEnable = true;
        }
        return iNativeSetTimestamp;
    }

    public int clearTimestamp() {
        int iNativeClearTimestamp = nativeClearTimestamp();
        if (iNativeClearTimestamp == 0) {
            this.mEnable = false;
        }
        return iNativeClearTimestamp;
    }

    public long getTimeStamp() {
        if (this.mEnable) {
            return nativeGetTimestamp().longValue();
        }
        return -1L;
    }

    public long getSourceTime() {
        if (this.mEnable) {
            return nativeGetSourceTime().longValue();
        }
        return -1L;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        int iNativeClose = nativeClose();
        if (iNativeClose != 0) {
            TunerUtils.throwExceptionForResult(iNativeClose, "Failed to close time filter.");
        }
    }
}

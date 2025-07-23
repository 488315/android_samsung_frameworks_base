package android.media.tv.tuner;

import android.annotation.SystemApi;
import android.media.tv.tuner.filter.Filter;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public class Descrambler implements AutoCloseable {
    public static final int PID_TYPE_MMTP = 2;
    public static final int PID_TYPE_T = 1;
    private static final String TAG = "Descrambler";
    private boolean mIsClosed = false;
    private final Object mLock = new Object();
    private long mNativeContext;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PidType {
    }

    private native int nativeAddPid(int i, int i2, Filter filter);

    private native int nativeClose();

    private native int nativeRemovePid(int i, int i2, Filter filter);

    private native int nativeSetKeyToken(byte[] bArr);

    private Descrambler() {
    }

    public int addPid(int i, int i2, Filter filter) {
        int nativeAddPid;
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            nativeAddPid = nativeAddPid(i, i2, filter);
        }
        return nativeAddPid;
    }

    public int removePid(int i, int i2, Filter filter) {
        int nativeRemovePid;
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            nativeRemovePid = nativeRemovePid(i, i2, filter);
        }
        return nativeRemovePid;
    }

    public int setKeyToken(byte[] bArr) {
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed);
            Objects.requireNonNull(bArr, "key token must not be null");
            if (!isValidKeyToken(bArr)) {
                return 4;
            }
            return nativeSetKeyToken(bArr);
        }
    }

    public static boolean isValidKeyToken(byte[] bArr) {
        if (bArr.length != 0 && bArr.length <= 16) {
            return true;
        }
        Log.d(TAG, "Invalid key token size: " + (bArr.length * 8) + " bit.");
        return false;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            int nativeClose = nativeClose();
            if (nativeClose != 0) {
                TunerUtils.throwExceptionForResult(nativeClose, "Failed to close descrambler");
            } else {
                this.mIsClosed = true;
            }
        }
    }
}

package android.media.tv.tuner;

import android.annotation.SystemApi;
import android.media.tv.tunerresourcemanager.TunerResourceManager;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes3.dex */
public class Lnb implements AutoCloseable {
    public static final int EVENT_TYPE_DISEQC_RX_OVERFLOW = 0;
    public static final int EVENT_TYPE_DISEQC_RX_PARITY_ERROR = 2;
    public static final int EVENT_TYPE_DISEQC_RX_TIMEOUT = 1;
    public static final int EVENT_TYPE_LNB_OVERLOAD = 3;
    public static final int POSITION_A = 1;
    public static final int POSITION_B = 2;
    public static final int POSITION_UNDEFINED = 0;
    public static final int TONE_CONTINUOUS = 1;
    public static final int TONE_NONE = 0;
    public static final int VOLTAGE_11V = 2;
    public static final int VOLTAGE_12V = 3;
    public static final int VOLTAGE_13V = 4;
    public static final int VOLTAGE_14V = 5;
    public static final int VOLTAGE_15V = 6;
    public static final int VOLTAGE_18V = 7;
    public static final int VOLTAGE_19V = 8;
    public static final int VOLTAGE_5V = 1;
    public static final int VOLTAGE_NONE = 0;
    int mClientId;
    private long mNativeContext;
    Tuner mOwner;
    TunerResourceManager mTunerResourceManager;
    private static final String TAG = "Lnb";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    Map<LnbCallback, Executor> mCallbackMap = new HashMap();
    private final Object mCallbackLock = new Object();
    private Boolean mIsClosed = false;
    private final Object mLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Position {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Tone {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Voltage {
    }

    private native int nativeClose();

    private native int nativeSendDiseqcMessage(byte[] bArr);

    private native int nativeSetSatellitePosition(int i);

    private native int nativeSetTone(int i);

    private native int nativeSetVoltage(int i);

    private Lnb() {
    }

    void setCallbackAndOwner(Tuner tuner, Executor executor, LnbCallback lnbCallback) {
        synchronized (this.mCallbackLock) {
            if (lnbCallback != null && executor != null) {
                addCallback(executor, lnbCallback);
            }
        }
        setOwner(tuner);
        Tuner tuner2 = this.mOwner;
        if (tuner2 != null) {
            this.mTunerResourceManager = tuner2.getTunerResourceManager();
            this.mClientId = this.mOwner.getClientId();
        }
    }

    public void addCallback(Executor executor, LnbCallback lnbCallback) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(lnbCallback, "callback must not be null");
        synchronized (this.mCallbackLock) {
            this.mCallbackMap.put(lnbCallback, executor);
        }
    }

    public boolean removeCallback(LnbCallback lnbCallback) {
        boolean z;
        Objects.requireNonNull(lnbCallback, "callback must not be null");
        synchronized (this.mCallbackLock) {
            z = this.mCallbackMap.remove(lnbCallback) != null;
        }
        return z;
    }

    void setOwner(Tuner tuner) {
        Objects.requireNonNull(tuner, "newOwner must not be null");
        synchronized (this.mLock) {
            this.mOwner = tuner;
            this.mTunerResourceManager = tuner.getTunerResourceManager();
            this.mClientId = tuner.getClientId();
        }
    }

    private void onEvent(final int i) {
        synchronized (this.mCallbackLock) {
            for (final LnbCallback lnbCallback : this.mCallbackMap.keySet()) {
                Executor executor = this.mCallbackMap.get(lnbCallback);
                if (lnbCallback != null && executor != null) {
                    executor.execute(new Runnable() { // from class: android.media.tv.tuner.Lnb$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Lnb.this.lambda$onEvent$0(lnbCallback, i);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEvent$0(LnbCallback lnbCallback, int i) {
        synchronized (this.mCallbackLock) {
            if (lnbCallback != null) {
                lnbCallback.onEvent(i);
            }
        }
    }

    private void onDiseqcMessage(final byte[] bArr) {
        synchronized (this.mCallbackLock) {
            for (final LnbCallback lnbCallback : this.mCallbackMap.keySet()) {
                Executor executor = this.mCallbackMap.get(lnbCallback);
                if (lnbCallback != null && executor != null) {
                    executor.execute(new Runnable() { // from class: android.media.tv.tuner.Lnb$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Lnb.this.lambda$onDiseqcMessage$1(lnbCallback, bArr);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDiseqcMessage$1(LnbCallback lnbCallback, byte[] bArr) {
        synchronized (this.mCallbackLock) {
            if (lnbCallback != null) {
                lnbCallback.onDiseqcMessage(bArr);
            }
        }
    }

    boolean isClosed() {
        boolean booleanValue;
        synchronized (this.mLock) {
            booleanValue = this.mIsClosed.booleanValue();
        }
        return booleanValue;
    }

    void closeInternal() {
        synchronized (this.mLock) {
            if (this.mIsClosed.booleanValue()) {
                return;
            }
            int nativeClose = nativeClose();
            if (nativeClose != 0) {
                TunerUtils.throwExceptionForResult(nativeClose, "Failed to close LNB");
            } else {
                this.mIsClosed = true;
                Tuner tuner = this.mOwner;
                if (tuner != null) {
                    tuner.releaseLnb();
                    this.mOwner = null;
                }
                this.mCallbackMap.clear();
            }
        }
    }

    public int setVoltage(int i) {
        int nativeSetVoltage;
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed.booleanValue());
            nativeSetVoltage = nativeSetVoltage(i);
        }
        return nativeSetVoltage;
    }

    public int setTone(int i) {
        int nativeSetTone;
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed.booleanValue());
            nativeSetTone = nativeSetTone(i);
        }
        return nativeSetTone;
    }

    public int setSatellitePosition(int i) {
        int nativeSetSatellitePosition;
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed.booleanValue());
            nativeSetSatellitePosition = nativeSetSatellitePosition(i);
        }
        return nativeSetSatellitePosition;
    }

    public int sendDiseqcMessage(byte[] bArr) {
        int nativeSendDiseqcMessage;
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed.booleanValue());
            nativeSendDiseqcMessage = nativeSendDiseqcMessage(bArr);
        }
        return nativeSendDiseqcMessage;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        acquireTRMSLock("close()");
        try {
            closeInternal();
        } finally {
            releaseTRMSLock();
        }
    }

    private void acquireTRMSLock(String str) {
        if (DEBUG) {
            Log.d(TAG, "ATTEMPT:acquireLock() in " + str + "for clientId:" + this.mClientId);
        }
        if (this.mTunerResourceManager.acquireLock(this.mClientId)) {
            return;
        }
        Log.e(TAG, "FAILED:acquireLock() in " + str + " for clientId:" + this.mClientId + " - this can cause deadlock between Tuner API calls and onReclaimResources()");
    }

    private void releaseTRMSLock() {
        this.mTunerResourceManager.releaseLock(this.mClientId);
    }
}

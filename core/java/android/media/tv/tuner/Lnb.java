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

    void setCallbackAndOwner(Tuner tuner, Executor executor, LnbCallback callback) {
        synchronized (this.mCallbackLock) {
            if (callback != null && executor != null) {
                addCallback(executor, callback);
            }
        }
        setOwner(tuner);
        if (this.mOwner != null) {
            this.mTunerResourceManager = this.mOwner.getTunerResourceManager();
            this.mClientId = this.mOwner.getClientId();
        }
    }

    public void addCallback(Executor executor, LnbCallback callback) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(callback, "callback must not be null");
        synchronized (this.mCallbackLock) {
            this.mCallbackMap.put(callback, executor);
        }
    }

    public boolean removeCallback(LnbCallback callback) {
        boolean result;
        Objects.requireNonNull(callback, "callback must not be null");
        synchronized (this.mCallbackLock) {
            result = this.mCallbackMap.remove(callback) != null;
        }
        return result;
    }

    void setOwner(Tuner newOwner) {
        Objects.requireNonNull(newOwner, "newOwner must not be null");
        synchronized (this.mLock) {
            this.mOwner = newOwner;
            this.mTunerResourceManager = newOwner.getTunerResourceManager();
            this.mClientId = newOwner.getClientId();
        }
    }

    private void onEvent(final int eventType) {
        synchronized (this.mCallbackLock) {
            for (final LnbCallback callback : this.mCallbackMap.keySet()) {
                Executor executor = this.mCallbackMap.get(callback);
                if (callback != null && executor != null) {
                    executor.execute(new Runnable() { // from class: android.media.tv.tuner.Lnb$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Lnb.this.lambda$onEvent$0(callback, eventType);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEvent$0(LnbCallback callback, int eventType) {
        synchronized (this.mCallbackLock) {
            if (callback != null) {
                callback.onEvent(eventType);
            }
        }
    }

    private void onDiseqcMessage(final byte[] diseqcMessage) {
        synchronized (this.mCallbackLock) {
            for (final LnbCallback callback : this.mCallbackMap.keySet()) {
                Executor executor = this.mCallbackMap.get(callback);
                if (callback != null && executor != null) {
                    executor.execute(new Runnable() { // from class: android.media.tv.tuner.Lnb$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Lnb.this.lambda$onDiseqcMessage$1(callback, diseqcMessage);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDiseqcMessage$1(LnbCallback callback, byte[] diseqcMessage) {
        synchronized (this.mCallbackLock) {
            if (callback != null) {
                callback.onDiseqcMessage(diseqcMessage);
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
            int res = nativeClose();
            if (res != 0) {
                TunerUtils.throwExceptionForResult(res, "Failed to close LNB");
            } else {
                this.mIsClosed = true;
                if (this.mOwner != null) {
                    this.mOwner.releaseLnb();
                    this.mOwner = null;
                }
                this.mCallbackMap.clear();
            }
        }
    }

    public int setVoltage(int voltage) {
        int nativeSetVoltage;
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed.booleanValue());
            nativeSetVoltage = nativeSetVoltage(voltage);
        }
        return nativeSetVoltage;
    }

    public int setTone(int tone) {
        int nativeSetTone;
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed.booleanValue());
            nativeSetTone = nativeSetTone(tone);
        }
        return nativeSetTone;
    }

    public int setSatellitePosition(int position) {
        int nativeSetSatellitePosition;
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed.booleanValue());
            nativeSetSatellitePosition = nativeSetSatellitePosition(position);
        }
        return nativeSetSatellitePosition;
    }

    public int sendDiseqcMessage(byte[] message) {
        int nativeSendDiseqcMessage;
        synchronized (this.mLock) {
            TunerUtils.checkResourceState(TAG, this.mIsClosed.booleanValue());
            nativeSendDiseqcMessage = nativeSendDiseqcMessage(message);
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

    private void acquireTRMSLock(String functionNameForLog) {
        if (DEBUG) {
            Log.d(TAG, "ATTEMPT:acquireLock() in " + functionNameForLog + "for clientId:" + this.mClientId);
        }
        if (!this.mTunerResourceManager.acquireLock(this.mClientId)) {
            Log.e(TAG, "FAILED:acquireLock() in " + functionNameForLog + " for clientId:" + this.mClientId + " - this can cause deadlock between Tuner API calls and onReclaimResources()");
        }
    }

    private void releaseTRMSLock() {
        this.mTunerResourceManager.releaseLock(this.mClientId);
    }
}

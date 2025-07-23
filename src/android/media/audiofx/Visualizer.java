package android.media.audiofx;

import android.content.AttributionSource;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.util.Log;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class Visualizer {
    public static final int ALREADY_EXISTS = -2;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -4;
    public static final int ERROR_DEAD_OBJECT = -7;
    public static final int ERROR_INVALID_OPERATION = -5;
    public static final int ERROR_NO_INIT = -3;
    public static final int ERROR_NO_MEMORY = -6;
    public static final int MEASUREMENT_MODE_NONE = 0;
    public static final int MEASUREMENT_MODE_PEAK_RMS = 1;
    private static final int NATIVE_EVENT_FFT_CAPTURE = 1;
    private static final int NATIVE_EVENT_PCM_CAPTURE = 0;
    private static final int NATIVE_EVENT_SERVER_DIED = 2;
    public static final int SCALING_MODE_AS_PLAYED = 1;
    public static final int SCALING_MODE_NORMALIZED = 0;
    public static final int STATE_ENABLED = 2;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_UNINITIALIZED = 0;
    public static final int SUCCESS = 0;
    private static final String TAG = "Visualizer-JAVA";
    private OnDataCaptureListener mCaptureListener;
    private int mId;
    private long mJniData;
    private final Object mListenerLock;
    private Handler mNativeEventHandler;
    private long mNativeVisualizer;
    private OnServerDiedListener mServerDiedListener;
    private int mState;
    private final Object mStateLock;

    public static final class MeasurementPeakRms {
        public int mPeak;
        public int mRms;
    }

    public interface OnDataCaptureListener {
        void onFftDataCapture(Visualizer visualizer, byte[] bArr, int i);

        void onWaveFormDataCapture(Visualizer visualizer, byte[] bArr, int i);
    }

    public interface OnServerDiedListener {
        void onServerDied();
    }

    public static native int[] getCaptureSizeRange();

    public static native int getMaxCaptureRate();

    private final native void native_finalize();

    private final native int native_getCaptureSize();

    private final native boolean native_getEnabled();

    private final native int native_getFft(byte[] bArr);

    private final native int native_getMeasurementMode();

    private final native int native_getPeakRms(MeasurementPeakRms measurementPeakRms);

    private final native int native_getSamplingRate();

    private final native int native_getScalingMode();

    private final native int native_getWaveForm(byte[] bArr);

    private static final native void native_init();

    private final native void native_release();

    private final native int native_setCaptureSize(int i);

    private final native int native_setEnabled(boolean z);

    private final native int native_setMeasurementMode(int i);

    private final native int native_setPeriodicCapture(int i, boolean z, boolean z2);

    private final native int native_setScalingMode(int i);

    private final native int native_setup(Object obj, int i, int[] iArr, Parcel parcel);

    static {
        System.loadLibrary("audioeffect_jni");
        native_init();
    }

    public Visualizer(int i) throws UnsupportedOperationException, RuntimeException {
        this.mState = 0;
        Object obj = new Object();
        this.mStateLock = obj;
        this.mListenerLock = new Object();
        this.mNativeEventHandler = null;
        this.mCaptureListener = null;
        this.mServerDiedListener = null;
        int[] iArr = new int[1];
        synchronized (obj) {
            this.mState = 0;
            AttributionSource.ScopedParcelState asScopedParcelState = AttributionSource.myAttributionSource().asScopedParcelState();
            try {
                int native_setup = native_setup(new WeakReference(this), i, iArr, asScopedParcelState.getParcel());
                if (asScopedParcelState != null) {
                    asScopedParcelState.close();
                }
                if (native_setup != 0 && native_setup != -2) {
                    Log.e(TAG, "Error code " + native_setup + " when initializing Visualizer.");
                    if (native_setup == -5) {
                        throw new UnsupportedOperationException("Effect library not loaded");
                    }
                    throw new RuntimeException("Cannot initialize Visualizer engine, error: " + native_setup);
                }
                this.mId = iArr[0];
                if (native_getEnabled()) {
                    this.mState = 2;
                } else {
                    this.mState = 1;
                }
            } finally {
            }
        }
    }

    public void release() {
        synchronized (this.mStateLock) {
            native_release();
            this.mState = 0;
        }
    }

    protected void finalize() {
        synchronized (this.mStateLock) {
            native_finalize();
        }
    }

    public int setEnabled(boolean z) throws IllegalStateException {
        int i;
        synchronized (this.mStateLock) {
            int i2 = this.mState;
            if (i2 == 0) {
                throw new IllegalStateException("setEnabled() called in wrong state: " + this.mState);
            }
            int i3 = 2;
            if (!(z && i2 == 1) && (z || i2 != 2)) {
                i = 0;
            } else {
                i = native_setEnabled(z);
                if (i == 0) {
                    if (!z) {
                        i3 = 1;
                    }
                    this.mState = i3;
                }
            }
        }
        return i;
    }

    public boolean getEnabled() {
        boolean native_getEnabled;
        synchronized (this.mStateLock) {
            if (this.mState == 0) {
                throw new IllegalStateException("getEnabled() called in wrong state: " + this.mState);
            }
            native_getEnabled = native_getEnabled();
        }
        return native_getEnabled;
    }

    public int setCaptureSize(int i) throws IllegalStateException {
        int native_setCaptureSize;
        synchronized (this.mStateLock) {
            if (this.mState != 1) {
                throw new IllegalStateException("setCaptureSize() called in wrong state: " + this.mState);
            }
            native_setCaptureSize = native_setCaptureSize(i);
            if (native_setCaptureSize == -4) {
                throw new IllegalArgumentException("setCaptureSize to " + i + " failed");
            }
        }
        return native_setCaptureSize;
    }

    public int getCaptureSize() throws IllegalStateException {
        int native_getCaptureSize;
        synchronized (this.mStateLock) {
            if (this.mState == 0) {
                throw new IllegalStateException("getCaptureSize() called in wrong state: " + this.mState);
            }
            native_getCaptureSize = native_getCaptureSize();
        }
        return native_getCaptureSize;
    }

    public int setScalingMode(int i) throws IllegalStateException {
        int native_setScalingMode;
        synchronized (this.mStateLock) {
            if (this.mState == 0) {
                throw new IllegalStateException("setScalingMode() called in wrong state: " + this.mState);
            }
            native_setScalingMode = native_setScalingMode(i);
        }
        return native_setScalingMode;
    }

    public int getScalingMode() throws IllegalStateException {
        int native_getScalingMode;
        synchronized (this.mStateLock) {
            if (this.mState == 0) {
                throw new IllegalStateException("getScalingMode() called in wrong state: " + this.mState);
            }
            native_getScalingMode = native_getScalingMode();
        }
        return native_getScalingMode;
    }

    public int setMeasurementMode(int i) throws IllegalStateException {
        int native_setMeasurementMode;
        synchronized (this.mStateLock) {
            if (this.mState == 0) {
                throw new IllegalStateException("setMeasurementMode() called in wrong state: " + this.mState);
            }
            native_setMeasurementMode = native_setMeasurementMode(i);
        }
        return native_setMeasurementMode;
    }

    public int getMeasurementMode() throws IllegalStateException {
        int native_getMeasurementMode;
        synchronized (this.mStateLock) {
            if (this.mState == 0) {
                throw new IllegalStateException("getMeasurementMode() called in wrong state: " + this.mState);
            }
            native_getMeasurementMode = native_getMeasurementMode();
        }
        return native_getMeasurementMode;
    }

    public int getSamplingRate() throws IllegalStateException {
        int native_getSamplingRate;
        synchronized (this.mStateLock) {
            if (this.mState == 0) {
                throw new IllegalStateException("getSamplingRate() called in wrong state: " + this.mState);
            }
            native_getSamplingRate = native_getSamplingRate();
        }
        return native_getSamplingRate;
    }

    public int getWaveForm(byte[] bArr) throws IllegalStateException {
        int native_getWaveForm;
        synchronized (this.mStateLock) {
            if (this.mState != 2) {
                throw new IllegalStateException("getWaveForm() called in wrong state: " + this.mState);
            }
            int captureSize = getCaptureSize();
            if (captureSize > bArr.length) {
                throw new IllegalArgumentException("getWaveForm() called with illegal size: " + bArr.length + " expecting at least " + captureSize + " bytes");
            }
            native_getWaveForm = native_getWaveForm(bArr);
        }
        return native_getWaveForm;
    }

    public int getFft(byte[] bArr) throws IllegalStateException {
        int native_getFft;
        synchronized (this.mStateLock) {
            if (this.mState != 2) {
                throw new IllegalStateException("getFft() called in wrong state: " + this.mState);
            }
            native_getFft = native_getFft(bArr);
        }
        return native_getFft;
    }

    public int getMeasurementPeakRms(MeasurementPeakRms measurementPeakRms) {
        int native_getPeakRms;
        if (measurementPeakRms == null) {
            Log.e(TAG, "Cannot store measurements in a null object");
            return -4;
        }
        synchronized (this.mStateLock) {
            if (this.mState != 2) {
                throw new IllegalStateException("getMeasurementPeakRms() called in wrong state: " + this.mState);
            }
            native_getPeakRms = native_getPeakRms(measurementPeakRms);
        }
        return native_getPeakRms;
    }

    public int setDataCaptureListener(OnDataCaptureListener onDataCaptureListener, int i, boolean z, boolean z2) {
        int native_setPeriodicCapture;
        if (onDataCaptureListener == null) {
            z = false;
            z2 = false;
        }
        synchronized (this.mStateLock) {
            native_setPeriodicCapture = native_setPeriodicCapture(i, z, z2);
        }
        if (native_setPeriodicCapture != 0) {
            return native_setPeriodicCapture;
        }
        synchronized (this.mListenerLock) {
            this.mCaptureListener = onDataCaptureListener;
            if (onDataCaptureListener != null && this.mNativeEventHandler == null) {
                Looper myLooper = Looper.myLooper();
                if (myLooper != null) {
                    this.mNativeEventHandler = new Handler(myLooper);
                } else {
                    Looper mainLooper = Looper.getMainLooper();
                    if (mainLooper != null) {
                        this.mNativeEventHandler = new Handler(mainLooper);
                    } else {
                        this.mNativeEventHandler = null;
                        native_setPeriodicCapture = -3;
                    }
                }
            }
        }
        return native_setPeriodicCapture;
    }

    public int setServerDiedListener(OnServerDiedListener onServerDiedListener) {
        synchronized (this.mListenerLock) {
            this.mServerDiedListener = onServerDiedListener;
        }
        return 0;
    }

    private static void postEventFromNative(Object obj, final int i, final int i2, final byte[] bArr) {
        Handler handler;
        final Visualizer visualizer = (Visualizer) ((WeakReference) obj).get();
        if (visualizer == null) {
            return;
        }
        synchronized (visualizer.mListenerLock) {
            handler = visualizer.mNativeEventHandler;
        }
        if (handler == null) {
            return;
        }
        if (i == 0 || i == 1) {
            handler.post(new Runnable() { // from class: android.media.audiofx.Visualizer$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Visualizer.lambda$postEventFromNative$0(Visualizer.this, i, bArr, i2);
                }
            });
        } else {
            if (i == 2) {
                handler.post(new Runnable() { // from class: android.media.audiofx.Visualizer$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Visualizer.lambda$postEventFromNative$1(Visualizer.this);
                    }
                });
                return;
            }
            Log.e(TAG, "Unknown native event in postEventFromNative: " + i);
        }
    }

    static /* synthetic */ void lambda$postEventFromNative$0(Visualizer visualizer, int i, byte[] bArr, int i2) {
        OnDataCaptureListener onDataCaptureListener;
        synchronized (visualizer.mListenerLock) {
            onDataCaptureListener = visualizer.mCaptureListener;
        }
        if (onDataCaptureListener != null) {
            if (i == 0) {
                onDataCaptureListener.onWaveFormDataCapture(visualizer, bArr, i2);
            } else {
                onDataCaptureListener.onFftDataCapture(visualizer, bArr, i2);
            }
        }
    }

    static /* synthetic */ void lambda$postEventFromNative$1(Visualizer visualizer) {
        OnServerDiedListener onServerDiedListener;
        synchronized (visualizer.mListenerLock) {
            onServerDiedListener = visualizer.mServerDiedListener;
        }
        if (onServerDiedListener != null) {
            onServerDiedListener.onServerDied();
        }
    }
}

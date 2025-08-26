package android.os;

import android.content.Context;
import android.os.IVibratorManagerService;
import android.os.IVibratorStateListener;
import android.os.Vibrator;
import android.os.vibrator.IVibrationSession;
import android.os.vibrator.IVibrationSessionCallback;
import android.os.vibrator.VendorVibrationSession;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import com.samsung.android.edge.EdgeManagerInternal;
import com.samsung.android.vibrator.VibrationDebugInfo;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class SystemVibratorManager extends VibratorManager {
    private static final String TAG = "VibratorManager";
    private int mCapabilities;
    private final Context mContext;
    private final ArrayMap<Vibrator.OnVibratorStateChangedListener, OnVibratorStateChangedListenerDelegate> mListeners;
    private final Object mLock;
    private final IVibratorManagerService mService;
    private final Binder mToken;
    private final int mUid;
    private int[] mVibratorIds;
    private final SparseArray<Vibrator> mVibrators;

    public SystemVibratorManager(Context context) {
        super(context);
        this.mToken = new Binder();
        this.mLock = new Object();
        this.mVibrators = new SparseArray<>();
        this.mListeners = new ArrayMap<>();
        this.mContext = context;
        this.mUid = Process.myUid();
        this.mService = IVibratorManagerService.Stub.asInterface(ServiceManager.getService(Context.VIBRATOR_MANAGER_SERVICE));
    }

    @Override // android.os.VibratorManager
    public int[] getVibratorIds() {
        IVibratorManagerService iVibratorManagerService;
        synchronized (this.mLock) {
            int[] iArr = this.mVibratorIds;
            if (iArr != null) {
                return iArr;
            }
            try {
                iVibratorManagerService = this.mService;
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (iVibratorManagerService == null) {
                Log.w(TAG, "Failed to retrieve vibrator ids; no vibrator manager service.");
                return new int[0];
            }
            int[] vibratorIds = iVibratorManagerService.getVibratorIds();
            this.mVibratorIds = vibratorIds;
            return vibratorIds;
        }
    }

    @Override // android.os.VibratorManager
    public boolean hasCapabilities(int i) {
        return (getCapabilities() & i) == i;
    }

    @Override // android.os.VibratorManager
    public Vibrator getVibrator(int i) {
        Vibrator nullVibrator;
        synchronized (this.mLock) {
            Vibrator vibrator = this.mVibrators.get(i);
            if (vibrator != null) {
                return vibrator;
            }
            VibratorInfo vibratorInfo = null;
            try {
                IVibratorManagerService iVibratorManagerService = this.mService;
                if (iVibratorManagerService == null) {
                    Log.w(TAG, "Failed to retrieve vibrator; no vibrator manager service.");
                } else {
                    vibratorInfo = iVibratorManagerService.getVibratorInfo(i);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (vibratorInfo != null) {
                nullVibrator = new SingleVibrator(vibratorInfo);
                this.mVibrators.put(i, nullVibrator);
            } else {
                nullVibrator = NullVibrator.getInstance();
            }
            return nullVibrator;
        }
    }

    @Override // android.os.VibratorManager
    public Vibrator getDefaultVibrator() {
        return (Vibrator) this.mContext.getSystemService(Vibrator.class);
    }

    @Override // android.os.VibratorManager
    public boolean setAlwaysOnEffect(int i, String str, int i2, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes) {
        IVibratorManagerService iVibratorManagerService = this.mService;
        if (iVibratorManagerService == null) {
            Log.w(TAG, "Failed to set always-on effect; no vibrator manager service.");
            return false;
        }
        try {
            return iVibratorManagerService.setAlwaysOnEffect(i, str, i2, combinedVibration, vibrationAttributes);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to set always-on effect.", e);
            return false;
        }
    }

    @Override // android.os.VibratorManager
    public void vibrate(int i, String str, CombinedVibration combinedVibration, String str2, VibrationAttributes vibrationAttributes) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to vibrate; no vibrator manager service.");
            return;
        }
        Trace.traceBegin(8388608L, EdgeManagerInternal.NOTIFICATION_KEY_VIBRATE);
        try {
            try {
                this.mService.vibrate(i, this.mContext.getDeviceId(), str, combinedVibration, vibrationAttributes, str2, this.mToken);
                Trace.traceEnd(8388608L);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed to vibrate.", e);
                Trace.traceEnd(8388608L);
            }
        } catch (Throwable th) {
            Trace.traceEnd(8388608L);
            throw th;
        }
    }

    @Override // android.os.VibratorManager
    public void performHapticFeedback(int i, String str, int i2, int i3) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to perform haptic feedback; no vibrator manager service.");
            return;
        }
        Trace.traceBegin(8388608L, "performHapticFeedback");
        try {
            try {
                this.mService.performHapticFeedback(this.mUid, this.mContext.getDeviceId(), this.mPackageName, i, str, i2, i3);
                Trace.traceEnd(8388608L);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed to perform haptic feedback.", e);
                Trace.traceEnd(8388608L);
            }
        } catch (Throwable th) {
            Trace.traceEnd(8388608L);
            throw th;
        }
    }

    @Override // android.os.VibratorManager
    public void performHapticFeedbackForInputDevice(int i, int i2, int i3, String str, int i4, int i5) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to perform haptic feedback for input device; no vibrator manager service.");
            return;
        }
        Trace.traceBegin(8388608L, "performHapticFeedbackForInputDevice");
        try {
            try {
                this.mService.performHapticFeedbackForInputDevice(this.mUid, this.mContext.getDeviceId(), this.mPackageName, i, i2, i3, str, i4, i5);
                Trace.traceEnd(8388608L);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed to perform haptic feedback for input device.", e);
                Trace.traceEnd(8388608L);
            }
        } catch (Throwable th) {
            Trace.traceEnd(8388608L);
            throw th;
        }
    }

    @Override // android.os.VibratorManager
    public void cancel() {
        cancelVibration(-1);
    }

    @Override // android.os.VibratorManager
    public void cancel(int i) {
        cancelVibration(i);
    }

    @Override // android.os.VibratorManager
    public void startVendorSession(int[] iArr, VibrationAttributes vibrationAttributes, String str, CancellationSignal cancellationSignal, Executor executor, VendorVibrationSession.Callback callback) {
        Objects.requireNonNull(iArr);
        VendorVibrationSessionCallbackDelegate vendorVibrationSessionCallbackDelegate = new VendorVibrationSessionCallbackDelegate(executor, callback);
        IVibratorManagerService iVibratorManagerService = this.mService;
        if (iVibratorManagerService == null) {
            Log.w(TAG, "Failed to start vibration session; no vibrator manager service.");
            vendorVibrationSessionCallbackDelegate.onFinished(3);
            return;
        }
        try {
            ICancellationSignal iCancellationSignalStartVendorVibrationSession = iVibratorManagerService.startVendorVibrationSession(this.mUid, this.mContext.getDeviceId(), this.mPackageName, iArr, vibrationAttributes, str, vendorVibrationSessionCallbackDelegate);
            if (cancellationSignal != null) {
                cancellationSignal.setRemote(iCancellationSignalStartVendorVibrationSession);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to start vibration session.", e);
            vendorVibrationSessionCallbackDelegate.onFinished(5);
        }
    }

    private int getCapabilities() {
        IVibratorManagerService iVibratorManagerService;
        synchronized (this.mLock) {
            int i = this.mCapabilities;
            if (i != 0) {
                return i;
            }
            try {
                iVibratorManagerService = this.mService;
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (iVibratorManagerService == null) {
                Log.w(TAG, "Failed to retrieve vibrator manager capabilities; no vibrator manager service.");
                return 0;
            }
            int capabilities = iVibratorManagerService.getCapabilities();
            this.mCapabilities = capabilities;
            return capabilities;
        }
    }

    private void cancelVibration(int i) {
        IVibratorManagerService iVibratorManagerService = this.mService;
        if (iVibratorManagerService == null) {
            Log.w(TAG, "Failed to cancel vibration; no vibrator manager service.");
            return;
        }
        try {
            iVibratorManagerService.cancelVibrate(i, this.mToken);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to cancel vibration.", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnVibratorStateChangedListenerDelegate extends IVibratorStateListener.Stub {
        private final Executor mExecutor;
        private final Vibrator.OnVibratorStateChangedListener mListener;

        OnVibratorStateChangedListenerDelegate(Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener, Executor executor) {
            this.mExecutor = executor;
            this.mListener = onVibratorStateChangedListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVibrating$0(boolean z) {
            this.mListener.onVibratorStateChanged(z);
        }

        @Override // android.os.IVibratorStateListener
        public void onVibrating(final boolean z) {
            this.mExecutor.execute(new Runnable() { // from class: android.os.SystemVibratorManager$OnVibratorStateChangedListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onVibrating$0(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class VendorVibrationSessionCallbackDelegate extends IVibrationSessionCallback.Stub {
        private final VendorVibrationSession.Callback mCallback;
        private final Executor mExecutor;

        VendorVibrationSessionCallbackDelegate(Executor executor, VendorVibrationSession.Callback callback) {
            Objects.requireNonNull(executor);
            Objects.requireNonNull(callback);
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStarted$0(IVibrationSession iVibrationSession) {
            this.mCallback.onStarted(new VendorVibrationSession(iVibrationSession));
        }

        @Override // android.os.vibrator.IVibrationSessionCallback
        public void onStarted(final IVibrationSession iVibrationSession) {
            this.mExecutor.execute(new Runnable() { // from class: android.os.SystemVibratorManager$VendorVibrationSessionCallbackDelegate$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onStarted$0(iVibrationSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFinishing$1() {
            this.mCallback.onFinishing();
        }

        @Override // android.os.vibrator.IVibrationSessionCallback
        public void onFinishing() {
            this.mExecutor.execute(new Runnable() { // from class: android.os.SystemVibratorManager$VendorVibrationSessionCallbackDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onFinishing$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFinished$2(int i) {
            this.mCallback.onFinished(i);
        }

        @Override // android.os.vibrator.IVibrationSessionCallback
        public void onFinished(final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.os.SystemVibratorManager$VendorVibrationSessionCallbackDelegate$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onFinished$2(i);
                }
            });
        }
    }

    private final class SingleVibrator extends Vibrator {
        private final int[] mVibratorId;
        private final VibratorInfo mVibratorInfo;

        @Override // android.os.Vibrator
        public boolean hasVibrator() {
            return true;
        }

        SingleVibrator(VibratorInfo vibratorInfo) {
            this.mVibratorInfo = vibratorInfo;
            this.mVibratorId = new int[]{vibratorInfo.getId()};
        }

        @Override // android.os.Vibrator
        public VibratorInfo getInfo() {
            return this.mVibratorInfo;
        }

        @Override // android.os.Vibrator
        public boolean hasAmplitudeControl() {
            return this.mVibratorInfo.hasAmplitudeControl();
        }

        @Override // android.os.Vibrator
        public boolean areVendorSessionsSupported() {
            return SystemVibratorManager.this.hasCapabilities(256);
        }

        @Override // android.os.Vibrator
        public boolean setAlwaysOnEffect(int i, String str, int i2, VibrationEffect vibrationEffect, VibrationAttributes vibrationAttributes) {
            return SystemVibratorManager.this.setAlwaysOnEffect(i, str, i2, CombinedVibration.startParallel().addVibrator(this.mVibratorInfo.getId(), vibrationEffect).combine(), vibrationAttributes);
        }

        @Override // android.os.Vibrator
        public void vibrate(int i, String str, VibrationEffect vibrationEffect, String str2, VibrationAttributes vibrationAttributes) {
            SystemVibratorManager.this.vibrate(i, str, CombinedVibration.startParallel().addVibrator(this.mVibratorInfo.getId(), vibrationEffect).combine(), str2, vibrationAttributes);
        }

        @Override // android.os.Vibrator
        public void performHapticFeedback(int i, String str, int i2, int i3) {
            SystemVibratorManager.this.performHapticFeedback(i, str, i2, i3);
        }

        @Override // android.os.Vibrator
        public void cancel() {
            SystemVibratorManager.this.cancel();
        }

        @Override // android.os.Vibrator
        public void cancel(int i) {
            SystemVibratorManager.this.cancel(i);
        }

        @Override // android.os.Vibrator
        public boolean isVibrating() {
            if (SystemVibratorManager.this.mService == null) {
                Log.w(SystemVibratorManager.TAG, "Failed to check status of vibrator " + this.mVibratorInfo.getId() + "; no vibrator service.");
                return false;
            }
            try {
                return SystemVibratorManager.this.mService.isVibrating(this.mVibratorInfo.getId());
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return false;
            }
        }

        @Override // android.os.Vibrator
        public void addVibratorStateListener(Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
            Objects.requireNonNull(onVibratorStateChangedListener);
            if (SystemVibratorManager.this.mContext == null) {
                Log.w(SystemVibratorManager.TAG, "Failed to add vibrate state listener; no vibrator context.");
            } else {
                addVibratorStateListener(SystemVibratorManager.this.mContext.getMainExecutor(), onVibratorStateChangedListener);
            }
        }

        @Override // android.os.Vibrator
        public void addVibratorStateListener(Executor executor, Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
            OnVibratorStateChangedListenerDelegate onVibratorStateChangedListenerDelegate;
            Objects.requireNonNull(onVibratorStateChangedListener);
            Objects.requireNonNull(executor);
            if (SystemVibratorManager.this.mService == null) {
                Log.w(SystemVibratorManager.TAG, "Failed to add vibrate state listener to vibrator " + this.mVibratorInfo.getId() + "; no vibrator service.");
                return;
            }
            synchronized (SystemVibratorManager.this.mLock) {
                if (SystemVibratorManager.this.mListeners.containsKey(onVibratorStateChangedListener)) {
                    Log.w(SystemVibratorManager.TAG, "Listener already registered.");
                    return;
                }
                try {
                    onVibratorStateChangedListenerDelegate = new OnVibratorStateChangedListenerDelegate(onVibratorStateChangedListener, executor);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
                if (!SystemVibratorManager.this.mService.registerVibratorStateListener(this.mVibratorInfo.getId(), onVibratorStateChangedListenerDelegate)) {
                    Log.w(SystemVibratorManager.TAG, "Failed to add vibrate state listener to vibrator " + this.mVibratorInfo.getId());
                    return;
                }
                SystemVibratorManager.this.mListeners.put(onVibratorStateChangedListener, onVibratorStateChangedListenerDelegate);
            }
        }

        @Override // android.os.Vibrator
        public void removeVibratorStateListener(Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
            Objects.requireNonNull(onVibratorStateChangedListener);
            if (SystemVibratorManager.this.mService == null) {
                Log.w(SystemVibratorManager.TAG, "Failed to remove vibrate state listener from vibrator " + this.mVibratorInfo.getId() + "; no vibrator service.");
                return;
            }
            synchronized (SystemVibratorManager.this.mLock) {
                if (SystemVibratorManager.this.mListeners.containsKey(onVibratorStateChangedListener)) {
                    try {
                        if (!SystemVibratorManager.this.mService.unregisterVibratorStateListener(this.mVibratorInfo.getId(), (OnVibratorStateChangedListenerDelegate) SystemVibratorManager.this.mListeners.get(onVibratorStateChangedListener))) {
                            Log.w(SystemVibratorManager.TAG, "Failed to remove vibrate state listener from vibrator " + this.mVibratorInfo.getId());
                            return;
                        }
                        SystemVibratorManager.this.mListeners.remove(onVibratorStateChangedListener);
                    } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                }
            }
        }

        @Override // android.os.Vibrator
        public void startVendorSession(VibrationAttributes vibrationAttributes, String str, CancellationSignal cancellationSignal, Executor executor, VendorVibrationSession.Callback callback) {
            SystemVibratorManager.this.startVendorSession(this.mVibratorId, vibrationAttributes, str, cancellationSignal, executor, callback);
        }
    }

    @Override // android.os.VibratorManager
    public int semGetNumberOfSupportedPatterns() {
        IVibratorManagerService iVibratorManagerService = this.mService;
        if (iVibratorManagerService == null) {
            Log.w(TAG, "Failed to get semGetNumberOfSupportedPatterns");
            return 0;
        }
        try {
            return iVibratorManagerService.semGetNumberOfSupportedPatterns();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to get semGetNumberOfSupportedPatterns", e);
            return 0;
        }
    }

    @Override // android.os.VibratorManager
    public int semGetSupportedVibrationType() {
        IVibratorManagerService iVibratorManagerService = this.mService;
        if (iVibratorManagerService == null) {
            Log.w(TAG, "Failed to get semGetSupportedVibrationType");
            return 0;
        }
        try {
            return iVibratorManagerService.getSupportedVibratorGroup();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to get semGetNumberOfSupportedPatterns", e);
            return 0;
        }
    }

    @Override // android.os.VibratorManager
    public String executeVibrationDebugCommand(VibrationDebugInfo vibrationDebugInfo) {
        IVibratorManagerService iVibratorManagerService = this.mService;
        if (iVibratorManagerService == null) {
            Log.w(TAG, "Failed to executeVibrationDebugCommand");
            return "";
        }
        try {
            return iVibratorManagerService.executeVibrationDebugCommand(vibrationDebugInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to executeVibrationDebugCommand", e);
            return "";
        }
    }
}

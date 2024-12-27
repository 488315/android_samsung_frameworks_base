package android.os;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;

import com.samsung.android.vibrator.VibrationDebugInfo;

import java.util.Objects;
import java.util.concurrent.Executor;

public class SystemVibratorManager extends VibratorManager {
    private static final String TAG = "VibratorManager";
    private final Context mContext;
    private final ArrayMap<
                    Vibrator.OnVibratorStateChangedListener, OnVibratorStateChangedListenerDelegate>
            mListeners;
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
        this.mService =
                IVibratorManagerService.Stub.asInterface(
                        ServiceManager.getService(Context.VIBRATOR_MANAGER_SERVICE));
    }

    @Override // android.os.VibratorManager
    public int[] getVibratorIds() {
        synchronized (this.mLock) {
            if (this.mVibratorIds != null) {
                return this.mVibratorIds;
            }
            try {
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (this.mService == null) {
                Log.w(TAG, "Failed to retrieve vibrator ids; no vibrator manager service.");
                return new int[0];
            }
            int[] vibratorIds = this.mService.getVibratorIds();
            this.mVibratorIds = vibratorIds;
            return vibratorIds;
        }
    }

    @Override // android.os.VibratorManager
    public Vibrator getVibrator(int vibratorId) {
        Vibrator vibrator;
        synchronized (this.mLock) {
            Vibrator vibrator2 = this.mVibrators.get(vibratorId);
            if (vibrator2 != null) {
                return vibrator2;
            }
            VibratorInfo info = null;
            try {
                if (this.mService == null) {
                    Log.w(TAG, "Failed to retrieve vibrator; no vibrator manager service.");
                } else {
                    info = this.mService.getVibratorInfo(vibratorId);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (info != null) {
                vibrator = new SingleVibrator(info);
                this.mVibrators.put(vibratorId, vibrator);
            } else {
                vibrator = NullVibrator.getInstance();
            }
            return vibrator;
        }
    }

    @Override // android.os.VibratorManager
    public Vibrator getDefaultVibrator() {
        return (Vibrator) this.mContext.getSystemService(Vibrator.class);
    }

    @Override // android.os.VibratorManager
    public boolean setAlwaysOnEffect(
            int uid,
            String opPkg,
            int alwaysOnId,
            CombinedVibration effect,
            VibrationAttributes attributes) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to set always-on effect; no vibrator manager service.");
            return false;
        }
        try {
            return this.mService.setAlwaysOnEffect(uid, opPkg, alwaysOnId, effect, attributes);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to set always-on effect.", e);
            return false;
        }
    }

    @Override // android.os.VibratorManager
    public void vibrate(
            int uid,
            String opPkg,
            CombinedVibration effect,
            String reason,
            VibrationAttributes attributes) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to vibrate; no vibrator manager service.");
            return;
        }
        try {
            this.mService.vibrate(
                    uid,
                    this.mContext.getDeviceId(),
                    opPkg,
                    effect,
                    attributes,
                    reason,
                    this.mToken);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to vibrate.", e);
        }
    }

    @Override // android.os.VibratorManager
    public void performHapticFeedback(
            int constant, boolean always, String reason, boolean fromIme) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to perform haptic feedback; no vibrator manager service.");
            return;
        }
        try {
            this.mService.performHapticFeedback(
                    this.mUid,
                    this.mContext.getDeviceId(),
                    this.mPackageName,
                    constant,
                    always,
                    reason,
                    fromIme);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to perform haptic feedback.", e);
        }
    }

    @Override // android.os.VibratorManager
    public void cancel() {
        cancelVibration(-1);
    }

    @Override // android.os.VibratorManager
    public void cancel(int usageFilter) {
        cancelVibration(usageFilter);
    }

    private void cancelVibration(int usageFilter) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to cancel vibration; no vibrator manager service.");
            return;
        }
        try {
            this.mService.cancelVibrate(usageFilter, this.mToken);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to cancel vibration.", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnVibratorStateChangedListenerDelegate extends IVibratorStateListener.Stub {
        private final Executor mExecutor;
        private final Vibrator.OnVibratorStateChangedListener mListener;

        OnVibratorStateChangedListenerDelegate(
                Vibrator.OnVibratorStateChangedListener listener, Executor executor) {
            this.mExecutor = executor;
            this.mListener = listener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVibrating$0(boolean isVibrating) {
            this.mListener.onVibratorStateChanged(isVibrating);
        }

        @Override // android.os.IVibratorStateListener
        public void onVibrating(final boolean isVibrating) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.os.SystemVibratorManager$OnVibratorStateChangedListenerDelegate$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SystemVibratorManager.OnVibratorStateChangedListenerDelegate.this
                                    .lambda$onVibrating$0(isVibrating);
                        }
                    });
        }
    }

    private final class SingleVibrator extends Vibrator {
        private final VibratorInfo mVibratorInfo;

        SingleVibrator(VibratorInfo vibratorInfo) {
            this.mVibratorInfo = vibratorInfo;
        }

        @Override // android.os.Vibrator
        public VibratorInfo getInfo() {
            return this.mVibratorInfo;
        }

        @Override // android.os.Vibrator
        public boolean hasVibrator() {
            return true;
        }

        @Override // android.os.Vibrator
        public boolean hasAmplitudeControl() {
            return this.mVibratorInfo.hasAmplitudeControl();
        }

        @Override // android.os.Vibrator
        public boolean setAlwaysOnEffect(
                int uid,
                String opPkg,
                int alwaysOnId,
                VibrationEffect effect,
                VibrationAttributes attrs) {
            CombinedVibration combined =
                    CombinedVibration.startParallel()
                            .addVibrator(this.mVibratorInfo.getId(), effect)
                            .combine();
            return SystemVibratorManager.this.setAlwaysOnEffect(
                    uid, opPkg, alwaysOnId, combined, attrs);
        }

        @Override // android.os.Vibrator
        public void vibrate(
                int uid,
                String opPkg,
                VibrationEffect vibe,
                String reason,
                VibrationAttributes attributes) {
            CombinedVibration combined =
                    CombinedVibration.startParallel()
                            .addVibrator(this.mVibratorInfo.getId(), vibe)
                            .combine();
            SystemVibratorManager.this.vibrate(uid, opPkg, combined, reason, attributes);
        }

        @Override // android.os.Vibrator
        public void performHapticFeedback(
                int effectId, boolean always, String reason, boolean fromIme) {
            SystemVibratorManager.this.performHapticFeedback(effectId, always, reason, fromIme);
        }

        @Override // android.os.Vibrator
        public void cancel() {
            SystemVibratorManager.this.cancel();
        }

        @Override // android.os.Vibrator
        public void cancel(int usageFilter) {
            SystemVibratorManager.this.cancel(usageFilter);
        }

        @Override // android.os.Vibrator
        public boolean isVibrating() {
            if (SystemVibratorManager.this.mService == null) {
                Log.w(
                        SystemVibratorManager.TAG,
                        "Failed to check status of vibrator "
                                + this.mVibratorInfo.getId()
                                + "; no vibrator service.");
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
        public void addVibratorStateListener(Vibrator.OnVibratorStateChangedListener listener) {
            Objects.requireNonNull(listener);
            if (SystemVibratorManager.this.mContext == null) {
                Log.w(
                        SystemVibratorManager.TAG,
                        "Failed to add vibrate state listener; no vibrator context.");
            } else {
                addVibratorStateListener(
                        SystemVibratorManager.this.mContext.getMainExecutor(), listener);
            }
        }

        @Override // android.os.Vibrator
        public void addVibratorStateListener(
                Executor executor, Vibrator.OnVibratorStateChangedListener listener) {
            OnVibratorStateChangedListenerDelegate delegate;
            Objects.requireNonNull(listener);
            Objects.requireNonNull(executor);
            if (SystemVibratorManager.this.mService == null) {
                Log.w(
                        SystemVibratorManager.TAG,
                        "Failed to add vibrate state listener to vibrator "
                                + this.mVibratorInfo.getId()
                                + "; no vibrator service.");
                return;
            }
            synchronized (SystemVibratorManager.this.mLock) {
                if (SystemVibratorManager.this.mListeners.containsKey(listener)) {
                    Log.w(SystemVibratorManager.TAG, "Listener already registered.");
                    return;
                }
                try {
                    delegate = new OnVibratorStateChangedListenerDelegate(listener, executor);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
                if (!SystemVibratorManager.this.mService.registerVibratorStateListener(
                        this.mVibratorInfo.getId(), delegate)) {
                    Log.w(
                            SystemVibratorManager.TAG,
                            "Failed to add vibrate state listener to vibrator "
                                    + this.mVibratorInfo.getId());
                } else {
                    SystemVibratorManager.this.mListeners.put(listener, delegate);
                }
            }
        }

        @Override // android.os.Vibrator
        public void removeVibratorStateListener(Vibrator.OnVibratorStateChangedListener listener) {
            Objects.requireNonNull(listener);
            if (SystemVibratorManager.this.mService == null) {
                Log.w(
                        SystemVibratorManager.TAG,
                        "Failed to remove vibrate state listener from vibrator "
                                + this.mVibratorInfo.getId()
                                + "; no vibrator service.");
                return;
            }
            synchronized (SystemVibratorManager.this.mLock) {
                if (SystemVibratorManager.this.mListeners.containsKey(listener)) {
                    OnVibratorStateChangedListenerDelegate delegate =
                            (OnVibratorStateChangedListenerDelegate)
                                    SystemVibratorManager.this.mListeners.get(listener);
                    try {
                        if (!SystemVibratorManager.this.mService.unregisterVibratorStateListener(
                                this.mVibratorInfo.getId(), delegate)) {
                            Log.w(
                                    SystemVibratorManager.TAG,
                                    "Failed to remove vibrate state listener from vibrator "
                                            + this.mVibratorInfo.getId());
                            return;
                        }
                        SystemVibratorManager.this.mListeners.remove(listener);
                    } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    @Override // android.os.VibratorManager
    public int semGetNumberOfSupportedPatterns() {
        if (this.mService == null) {
            Log.w(TAG, "Failed to get semGetNumberOfSupportedPatterns");
            return 0;
        }
        try {
            return this.mService.semGetNumberOfSupportedPatterns();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to get semGetNumberOfSupportedPatterns", e);
            return 0;
        }
    }

    @Override // android.os.VibratorManager
    public int semGetSupportedVibrationType() {
        if (this.mService == null) {
            Log.w(TAG, "Failed to get semGetSupportedVibrationType");
            return 0;
        }
        try {
            return this.mService.getSupportedVibratorGroup();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to get semGetNumberOfSupportedPatterns", e);
            return 0;
        }
    }

    @Override // android.os.VibratorManager
    public String executeVibrationDebugCommand(VibrationDebugInfo param) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to executeVibrationDebugCommand");
            return "";
        }
        try {
            return this.mService.executeVibrationDebugCommand(param);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to executeVibrationDebugCommand", e);
            return "";
        }
    }
}

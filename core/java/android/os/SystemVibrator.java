package android.os;

import android.content.Context;
import android.os.SystemVibrator;
import android.os.Vibrator;
import android.os.vibrator.VibratorInfoFactory;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import com.samsung.android.vibrator.VibrationDebugInfo;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class SystemVibrator extends Vibrator {
    private static final String TAG = "Vibrator";
    private final ArrayList<MultiVibratorStateListener> mBrokenListeners;
    private final Context mContext;
    private final Object mLock;
    private final ArrayMap<Vibrator.OnVibratorStateChangedListener, MultiVibratorStateListener> mRegisteredListeners;
    private int mSupportedPatternCounts;
    private int mSupportedVibrationType;
    private VibratorInfo mVibratorInfo;
    private final VibratorManager mVibratorManager;

    public SystemVibrator(Context context) {
        super(context);
        this.mBrokenListeners = new ArrayList<>();
        this.mRegisteredListeners = new ArrayMap<>();
        this.mLock = new Object();
        this.mSupportedVibrationType = -1;
        this.mSupportedPatternCounts = -1;
        this.mContext = context;
        this.mVibratorManager = (VibratorManager) this.mContext.getSystemService(VibratorManager.class);
    }

    @Override // android.os.Vibrator
    public VibratorInfo getInfo() {
        synchronized (this.mLock) {
            if (this.mVibratorInfo != null) {
                return this.mVibratorInfo;
            }
            if (this.mVibratorManager == null) {
                Log.w(TAG, "Failed to retrieve vibrator info; no vibrator manager.");
                return VibratorInfo.EMPTY_VIBRATOR_INFO;
            }
            int[] vibratorIds = this.mVibratorManager.getVibratorIds();
            if (vibratorIds.length == 0) {
                VibratorInfo vibratorInfo = VibratorInfo.EMPTY_VIBRATOR_INFO;
                this.mVibratorInfo = vibratorInfo;
                return vibratorInfo;
            }
            VibratorInfo[] vibratorInfos = new VibratorInfo[vibratorIds.length];
            for (int i = 0; i < vibratorIds.length; i++) {
                Vibrator vibrator = this.mVibratorManager.getVibrator(vibratorIds[i]);
                if (vibrator instanceof NullVibrator) {
                    Log.w(TAG, "Vibrator manager service not ready; Info not yet available for vibrator: " + vibratorIds[i]);
                    return VibratorInfo.EMPTY_VIBRATOR_INFO;
                }
                vibratorInfos[i] = vibrator.getInfo();
            }
            VibratorInfo create = VibratorInfoFactory.create(-1, vibratorInfos);
            this.mVibratorInfo = create;
            return create;
        }
    }

    @Override // android.os.Vibrator
    public boolean hasVibrator() {
        if (this.mVibratorManager != null) {
            return this.mVibratorManager.getVibratorIds().length > 0;
        }
        Log.w(TAG, "Failed to check if vibrator exists; no vibrator manager.");
        return false;
    }

    @Override // android.os.Vibrator
    public boolean isVibrating() {
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to vibrate; no vibrator manager.");
            return false;
        }
        for (int vibratorId : this.mVibratorManager.getVibratorIds()) {
            if (this.mVibratorManager.getVibrator(vibratorId).isVibrating()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Vibrator
    public void addVibratorStateListener(Vibrator.OnVibratorStateChangedListener listener) {
        Objects.requireNonNull(listener);
        if (this.mContext == null) {
            Log.w(TAG, "Failed to add vibrate state listener; no vibrator context.");
        } else {
            addVibratorStateListener(this.mContext.getMainExecutor(), listener);
        }
    }

    @Override // android.os.Vibrator
    public void addVibratorStateListener(Executor executor, Vibrator.OnVibratorStateChangedListener listener) {
        Objects.requireNonNull(listener);
        Objects.requireNonNull(executor);
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to add vibrate state listener; no vibrator manager.");
            return;
        }
        MultiVibratorStateListener delegate = null;
        try {
            synchronized (this.mRegisteredListeners) {
                if (this.mRegisteredListeners.containsKey(listener)) {
                    Log.w(TAG, "Listener already registered.");
                    if (0 != 0 && delegate.hasRegisteredListeners()) {
                        synchronized (this.mBrokenListeners) {
                            this.mBrokenListeners.add(null);
                        }
                    }
                    tryUnregisterBrokenListeners();
                    return;
                }
                MultiVibratorStateListener delegate2 = new MultiVibratorStateListener(executor, listener);
                delegate2.register(this.mVibratorManager);
                this.mRegisteredListeners.put(listener, delegate2);
                MultiVibratorStateListener delegate3 = null;
                if (0 != 0 && delegate3.hasRegisteredListeners()) {
                    synchronized (this.mBrokenListeners) {
                        this.mBrokenListeners.add(null);
                    }
                }
                tryUnregisterBrokenListeners();
            }
        } catch (Throwable th) {
            if (0 != 0 && delegate.hasRegisteredListeners()) {
                synchronized (this.mBrokenListeners) {
                    this.mBrokenListeners.add(null);
                }
            }
            tryUnregisterBrokenListeners();
            throw th;
        }
    }

    @Override // android.os.Vibrator
    public void removeVibratorStateListener(Vibrator.OnVibratorStateChangedListener listener) {
        Objects.requireNonNull(listener);
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to remove vibrate state listener; no vibrator manager.");
            return;
        }
        synchronized (this.mRegisteredListeners) {
            if (this.mRegisteredListeners.containsKey(listener)) {
                MultiVibratorStateListener delegate = this.mRegisteredListeners.get(listener);
                delegate.unregister(this.mVibratorManager);
                this.mRegisteredListeners.remove(listener);
            }
        }
        tryUnregisterBrokenListeners();
    }

    @Override // android.os.Vibrator
    public boolean hasAmplitudeControl() {
        return getInfo().hasAmplitudeControl();
    }

    @Override // android.os.Vibrator
    public boolean setAlwaysOnEffect(int uid, String opPkg, int alwaysOnId, VibrationEffect effect, VibrationAttributes attrs) {
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to set always-on effect; no vibrator manager.");
            return false;
        }
        CombinedVibration combinedEffect = CombinedVibration.createParallel(effect);
        return this.mVibratorManager.setAlwaysOnEffect(uid, opPkg, alwaysOnId, combinedEffect, attrs);
    }

    @Override // android.os.Vibrator
    public void vibrate(int uid, String opPkg, VibrationEffect effect, String reason, VibrationAttributes attributes) {
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to vibrate; no vibrator manager.");
        } else {
            CombinedVibration combinedEffect = CombinedVibration.createParallel(effect);
            this.mVibratorManager.vibrate(uid, opPkg, combinedEffect, reason, attributes);
        }
    }

    @Override // android.os.Vibrator
    public void performHapticFeedback(int constant, boolean always, String reason, boolean fromIme) {
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to perform haptic feedback; no vibrator manager.");
        } else {
            this.mVibratorManager.performHapticFeedback(constant, always, reason, fromIme);
        }
    }

    @Override // android.os.Vibrator
    public void cancel() {
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to cancel vibrate; no vibrator manager.");
        } else {
            this.mVibratorManager.cancel();
        }
    }

    @Override // android.os.Vibrator
    public void cancel(int usageFilter) {
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to cancel vibrate; no vibrator manager.");
        } else {
            this.mVibratorManager.cancel(usageFilter);
        }
    }

    private void tryUnregisterBrokenListeners() {
        synchronized (this.mBrokenListeners) {
            try {
                int i = this.mBrokenListeners.size();
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    this.mBrokenListeners.get(i).unregister(this.mVibratorManager);
                    this.mBrokenListeners.remove(i);
                }
            } catch (RuntimeException e) {
                Log.w(TAG, "Failed to unregister broken listener", e);
            }
        }
    }

    private static class SingleVibratorStateListener implements Vibrator.OnVibratorStateChangedListener {
        private final MultiVibratorStateListener mAllVibratorsListener;
        private final int mVibratorIdx;

        SingleVibratorStateListener(MultiVibratorStateListener listener, int vibratorIdx) {
            this.mAllVibratorsListener = listener;
            this.mVibratorIdx = vibratorIdx;
        }

        @Override // android.os.Vibrator.OnVibratorStateChangedListener
        public void onVibratorStateChanged(boolean isVibrating) {
            this.mAllVibratorsListener.onVibrating(this.mVibratorIdx, isVibrating);
        }
    }

    public static class MultiVibratorStateListener {
        private final Vibrator.OnVibratorStateChangedListener mDelegate;
        private final Executor mExecutor;
        private int mInitializedMask;
        private int mVibratingMask;
        private final Object mLock = new Object();
        private final SparseArray<SingleVibratorStateListener> mVibratorListeners = new SparseArray<>();

        public MultiVibratorStateListener(Executor executor, Vibrator.OnVibratorStateChangedListener listener) {
            this.mExecutor = executor;
            this.mDelegate = listener;
        }

        public boolean hasRegisteredListeners() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mVibratorListeners.size() > 0;
            }
            return z;
        }

        public void register(VibratorManager vibratorManager) {
            int[] vibratorIds = vibratorManager.getVibratorIds();
            synchronized (this.mLock) {
                for (int i = 0; i < vibratorIds.length; i++) {
                    int vibratorId = vibratorIds[i];
                    SingleVibratorStateListener listener = new SingleVibratorStateListener(this, i);
                    try {
                        vibratorManager.getVibrator(vibratorId).addVibratorStateListener(this.mExecutor, listener);
                        this.mVibratorListeners.put(vibratorId, listener);
                    } catch (RuntimeException e) {
                        try {
                            unregister(vibratorManager);
                        } catch (RuntimeException e1) {
                            Log.w(SystemVibrator.TAG, "Failed to unregister listener while recovering from a failed register call", e1);
                        }
                        throw e;
                    }
                }
            }
        }

        public void unregister(VibratorManager vibratorManager) {
            synchronized (this.mLock) {
                int i = this.mVibratorListeners.size();
                while (true) {
                    i--;
                    if (i >= 0) {
                        int vibratorId = this.mVibratorListeners.keyAt(i);
                        SingleVibratorStateListener listener = this.mVibratorListeners.valueAt(i);
                        vibratorManager.getVibrator(vibratorId).removeVibratorStateListener(listener);
                        this.mVibratorListeners.removeAt(i);
                    }
                }
            }
        }

        public void onVibrating(final int vibratorIdx, final boolean vibrating) {
            this.mExecutor.execute(new Runnable() { // from class: android.os.SystemVibrator$MultiVibratorStateListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SystemVibrator.MultiVibratorStateListener.this.lambda$onVibrating$0(vibratorIdx, vibrating);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVibrating$0(int vibratorIdx, boolean vibrating) {
            boolean isAnyVibrating;
            int allInitializedMask;
            synchronized (this.mLock) {
                int i = 1;
                int allInitializedMask2 = (1 << this.mVibratorListeners.size()) - 1;
                boolean previousIsAnyVibrating = this.mVibratingMask != 0;
                boolean previousAreAllInitialized = this.mInitializedMask == allInitializedMask2;
                int vibratorMask = 1 << vibratorIdx;
                this.mInitializedMask |= vibratorMask;
                boolean previousVibrating = (this.mVibratingMask & vibratorMask) != 0;
                if (previousVibrating != vibrating) {
                    this.mVibratingMask ^= vibratorMask;
                }
                isAnyVibrating = this.mVibratingMask != 0;
                boolean areAllInitialized = this.mInitializedMask == allInitializedMask2;
                boolean isStateChanging = previousIsAnyVibrating != isAnyVibrating;
                if (!areAllInitialized || (previousAreAllInitialized && !isStateChanging)) {
                    i = 0;
                }
                allInitializedMask = i;
            }
            if (allInitializedMask != 0) {
                this.mDelegate.onVibratorStateChanged(isAnyVibrating);
            }
        }
    }

    @Override // android.os.Vibrator
    public int semGetSupportedVibrationType() {
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to call semGetSupportedVibrationType; no vibrator service.");
            return -1;
        }
        if (this.mSupportedVibrationType == -1) {
            this.mSupportedVibrationType = this.mVibratorManager.semGetSupportedVibrationType();
        }
        return this.mSupportedVibrationType;
    }

    @Override // android.os.Vibrator
    public int semGetNumberOfSupportedPatterns() {
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to call semGetNumberOfSupportedPatterns; no vibrator service.");
            return -1;
        }
        if (this.mSupportedPatternCounts == -1) {
            this.mSupportedPatternCounts = this.mVibratorManager.semGetNumberOfSupportedPatterns();
        }
        return this.mSupportedPatternCounts;
    }

    @Override // android.os.Vibrator
    public int getMaxMagnitude() {
        return 0;
    }

    @Override // android.os.Vibrator
    public boolean semIsHapticSupported() {
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to call semIsHapticSupported; no vibrator service.");
            return false;
        }
        if (this.mSupportedVibrationType == -1) {
            this.mSupportedVibrationType = this.mVibratorManager.semGetSupportedVibrationType();
        }
        return this.mSupportedVibrationType > 1;
    }

    @Override // android.os.Vibrator
    public boolean semIsVibrating() {
        return isVibrating();
    }

    @Override // android.os.Vibrator
    public String executeVibrationDebugCommand(VibrationDebugInfo param) {
        return this.mVibratorManager.executeVibrationDebugCommand(param);
    }
}

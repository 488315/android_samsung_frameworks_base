package android.os;

import android.content.Context;
import android.os.SystemVibrator;
import android.os.Vibrator;
import android.os.vibrator.VendorVibrationSession;
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
    private int[] mVibratorIds;
    private VibratorInfo mVibratorInfo;
    private final VibratorManager mVibratorManager;

    @Override // android.os.Vibrator
    public int getMaxMagnitude() {
        return 0;
    }

    public SystemVibrator(Context context) {
        super(context);
        this.mBrokenListeners = new ArrayList<>();
        this.mRegisteredListeners = new ArrayMap<>();
        this.mLock = new Object();
        this.mSupportedVibrationType = -1;
        this.mSupportedPatternCounts = -1;
        this.mContext = context;
        this.mVibratorManager = (VibratorManager) context.getSystemService(VibratorManager.class);
    }

    @Override // android.os.Vibrator
    public VibratorInfo getInfo() {
        synchronized (this.mLock) {
            VibratorInfo vibratorInfo = this.mVibratorInfo;
            if (vibratorInfo != null) {
                return vibratorInfo;
            }
            if (this.mVibratorManager == null) {
                Log.w(TAG, "Failed to retrieve vibrator info; no vibrator manager.");
                return VibratorInfo.EMPTY_VIBRATOR_INFO;
            }
            int[] vibratorIds = getVibratorIds();
            if (vibratorIds == null) {
                Log.w(TAG, "Failed to retrieve vibrator info; error retrieving vibrator ids.");
                return VibratorInfo.EMPTY_VIBRATOR_INFO;
            }
            if (vibratorIds.length == 0) {
                VibratorInfo vibratorInfo2 = VibratorInfo.EMPTY_VIBRATOR_INFO;
                this.mVibratorInfo = vibratorInfo2;
                return vibratorInfo2;
            }
            VibratorInfo[] vibratorInfoArr = new VibratorInfo[vibratorIds.length];
            for (int i = 0; i < vibratorIds.length; i++) {
                Vibrator vibrator = this.mVibratorManager.getVibrator(vibratorIds[i]);
                if (vibrator instanceof NullVibrator) {
                    Log.w(TAG, "Vibrator manager service not ready; Info not yet available for vibrator: " + vibratorIds[i]);
                    return VibratorInfo.EMPTY_VIBRATOR_INFO;
                }
                vibratorInfoArr[i] = vibrator.getInfo();
            }
            VibratorInfo create = VibratorInfoFactory.create(-1, vibratorInfoArr);
            this.mVibratorInfo = create;
            return create;
        }
    }

    @Override // android.os.Vibrator
    public boolean hasVibrator() {
        int[] vibratorIds = getVibratorIds();
        if (vibratorIds != null) {
            return vibratorIds.length > 0;
        }
        Log.w(TAG, "Failed to check if vibrator exists; no vibrator manager.");
        return false;
    }

    @Override // android.os.Vibrator
    public boolean isVibrating() {
        int[] vibratorIds = getVibratorIds();
        if (vibratorIds == null) {
            Log.w(TAG, "Failed to vibrate; no vibrator manager.");
            return false;
        }
        for (int i : vibratorIds) {
            if (this.mVibratorManager.getVibrator(i).isVibrating()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Vibrator
    public void addVibratorStateListener(Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
        Objects.requireNonNull(onVibratorStateChangedListener);
        Context context = this.mContext;
        if (context == null) {
            Log.w(TAG, "Failed to add vibrate state listener; no vibrator context.");
        } else {
            addVibratorStateListener(context.getMainExecutor(), onVibratorStateChangedListener);
        }
    }

    @Override // android.os.Vibrator
    public void addVibratorStateListener(Executor executor, Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
        Objects.requireNonNull(onVibratorStateChangedListener);
        Objects.requireNonNull(executor);
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to add vibrate state listener; no vibrator manager.");
            return;
        }
        int[] vibratorIds = getVibratorIds();
        if (vibratorIds == null) {
            Log.w(TAG, "Failed to add vibrate state listener; error retrieving vibrator ids.");
            return;
        }
        MultiVibratorStateListener multiVibratorStateListener = null;
        try {
            synchronized (this.mRegisteredListeners) {
                try {
                    if (this.mRegisteredListeners.containsKey(onVibratorStateChangedListener)) {
                        Log.w(TAG, "Listener already registered.");
                        tryUnregisterBrokenListeners();
                        return;
                    }
                    MultiVibratorStateListener multiVibratorStateListener2 = new MultiVibratorStateListener(executor, onVibratorStateChangedListener);
                    try {
                        multiVibratorStateListener2.register(this.mVibratorManager, vibratorIds);
                        this.mRegisteredListeners.put(onVibratorStateChangedListener, multiVibratorStateListener2);
                        tryUnregisterBrokenListeners();
                    } catch (Throwable th) {
                        th = th;
                        multiVibratorStateListener = multiVibratorStateListener2;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        } catch (Throwable th3) {
            if (multiVibratorStateListener != null && multiVibratorStateListener.hasRegisteredListeners()) {
                synchronized (this.mBrokenListeners) {
                    this.mBrokenListeners.add(multiVibratorStateListener);
                }
            }
            tryUnregisterBrokenListeners();
            throw th3;
        }
    }

    @Override // android.os.Vibrator
    public void removeVibratorStateListener(Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
        Objects.requireNonNull(onVibratorStateChangedListener);
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to remove vibrate state listener; no vibrator manager.");
            return;
        }
        synchronized (this.mRegisteredListeners) {
            if (this.mRegisteredListeners.containsKey(onVibratorStateChangedListener)) {
                this.mRegisteredListeners.get(onVibratorStateChangedListener).unregister(this.mVibratorManager);
                this.mRegisteredListeners.remove(onVibratorStateChangedListener);
            }
        }
        tryUnregisterBrokenListeners();
    }

    @Override // android.os.Vibrator
    public boolean hasAmplitudeControl() {
        return getInfo().hasAmplitudeControl();
    }

    @Override // android.os.Vibrator
    public boolean areVendorSessionsSupported() {
        return this.mVibratorManager.hasCapabilities(256);
    }

    @Override // android.os.Vibrator
    public boolean setAlwaysOnEffect(int i, String str, int i2, VibrationEffect vibrationEffect, VibrationAttributes vibrationAttributes) {
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to set always-on effect; no vibrator manager.");
            return false;
        }
        return this.mVibratorManager.setAlwaysOnEffect(i, str, i2, CombinedVibration.createParallel(vibrationEffect), vibrationAttributes);
    }

    @Override // android.os.Vibrator
    public void vibrate(int i, String str, VibrationEffect vibrationEffect, String str2, VibrationAttributes vibrationAttributes) {
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to vibrate; no vibrator manager.");
        } else {
            this.mVibratorManager.vibrate(i, str, CombinedVibration.createParallel(vibrationEffect), str2, vibrationAttributes);
        }
    }

    @Override // android.os.Vibrator
    public void performHapticFeedback(int i, String str, int i2, int i3) {
        VibratorManager vibratorManager = this.mVibratorManager;
        if (vibratorManager == null) {
            Log.w(TAG, "Failed to perform haptic feedback; no vibrator manager.");
        } else {
            vibratorManager.performHapticFeedback(i, str, i2, i3);
        }
    }

    @Override // android.os.Vibrator
    public void performHapticFeedbackForInputDevice(int i, int i2, int i3, String str, int i4, int i5) {
        VibratorManager vibratorManager = this.mVibratorManager;
        if (vibratorManager == null) {
            Log.w(TAG, "Failed to perform haptic feedback for input device; no vibrator manager.");
        } else {
            vibratorManager.performHapticFeedbackForInputDevice(i, i2, i3, str, i4, i5);
        }
    }

    @Override // android.os.Vibrator
    public void cancel() {
        VibratorManager vibratorManager = this.mVibratorManager;
        if (vibratorManager == null) {
            Log.w(TAG, "Failed to cancel vibrate; no vibrator manager.");
        } else {
            vibratorManager.cancel();
        }
    }

    @Override // android.os.Vibrator
    public void cancel(int i) {
        VibratorManager vibratorManager = this.mVibratorManager;
        if (vibratorManager == null) {
            Log.w(TAG, "Failed to cancel vibrate; no vibrator manager.");
        } else {
            vibratorManager.cancel(i);
        }
    }

    @Override // android.os.Vibrator
    public void startVendorSession(VibrationAttributes vibrationAttributes, String str, CancellationSignal cancellationSignal, Executor executor, final VendorVibrationSession.Callback callback) {
        if (this.mVibratorManager == null) {
            Log.w(TAG, "Failed to start vibration session; no vibrator manager.");
            executor.execute(new Runnable() { // from class: android.os.SystemVibrator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VendorVibrationSession.Callback.this.onFinished(5);
                }
            });
            return;
        }
        int[] vibratorIds = getVibratorIds();
        if (vibratorIds == null) {
            Log.w(TAG, "Failed to start vibration session; error retrieving vibrator ids.");
            executor.execute(new Runnable() { // from class: android.os.SystemVibrator$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    VendorVibrationSession.Callback.this.onFinished(5);
                }
            });
        } else {
            this.mVibratorManager.startVendorSession(vibratorIds, vibrationAttributes, str, cancellationSignal, executor, callback);
        }
    }

    private int[] getVibratorIds() {
        synchronized (this.mLock) {
            int[] iArr = this.mVibratorIds;
            if (iArr != null) {
                return iArr;
            }
            VibratorManager vibratorManager = this.mVibratorManager;
            if (vibratorManager == null) {
                Log.w(TAG, "Failed to retrieve vibrator ids; no vibrator manager.");
                return null;
            }
            int[] vibratorIds = vibratorManager.getVibratorIds();
            this.mVibratorIds = vibratorIds;
            return vibratorIds;
        }
    }

    private void tryUnregisterBrokenListeners() {
        synchronized (this.mBrokenListeners) {
            try {
                int size = this.mBrokenListeners.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    this.mBrokenListeners.get(size).unregister(this.mVibratorManager);
                    this.mBrokenListeners.remove(size);
                }
            } catch (RuntimeException e) {
                Log.w(TAG, "Failed to unregister broken listener", e);
            }
        }
    }

    private static class SingleVibratorStateListener implements Vibrator.OnVibratorStateChangedListener {
        private final MultiVibratorStateListener mAllVibratorsListener;
        private final int mVibratorIdx;

        SingleVibratorStateListener(MultiVibratorStateListener multiVibratorStateListener, int i) {
            this.mAllVibratorsListener = multiVibratorStateListener;
            this.mVibratorIdx = i;
        }

        @Override // android.os.Vibrator.OnVibratorStateChangedListener
        public void onVibratorStateChanged(boolean z) {
            this.mAllVibratorsListener.onVibrating(this.mVibratorIdx, z);
        }
    }

    public static class MultiVibratorStateListener {
        private final Vibrator.OnVibratorStateChangedListener mDelegate;
        private final Executor mExecutor;
        private int mInitializedMask;
        private int mVibratingMask;
        private final Object mLock = new Object();
        private final SparseArray<SingleVibratorStateListener> mVibratorListeners = new SparseArray<>();

        public MultiVibratorStateListener(Executor executor, Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
            this.mExecutor = executor;
            this.mDelegate = onVibratorStateChangedListener;
        }

        public boolean hasRegisteredListeners() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mVibratorListeners.size() > 0;
            }
            return z;
        }

        public void register(VibratorManager vibratorManager, int[] iArr) {
            synchronized (this.mLock) {
                for (int i = 0; i < iArr.length; i++) {
                    int i2 = iArr[i];
                    SingleVibratorStateListener singleVibratorStateListener = new SingleVibratorStateListener(this, i);
                    try {
                        vibratorManager.getVibrator(i2).addVibratorStateListener(this.mExecutor, singleVibratorStateListener);
                        this.mVibratorListeners.put(i2, singleVibratorStateListener);
                    } catch (RuntimeException e) {
                        try {
                            unregister(vibratorManager);
                        } catch (RuntimeException e2) {
                            Log.w(SystemVibrator.TAG, "Failed to unregister listener while recovering from a failed register call", e2);
                        }
                        throw e;
                    }
                }
            }
        }

        public void unregister(VibratorManager vibratorManager) {
            synchronized (this.mLock) {
                int size = this.mVibratorListeners.size();
                while (true) {
                    size--;
                    if (size >= 0) {
                        vibratorManager.getVibrator(this.mVibratorListeners.keyAt(size)).removeVibratorStateListener(this.mVibratorListeners.valueAt(size));
                        this.mVibratorListeners.removeAt(size);
                    }
                }
            }
        }

        public void onVibrating(final int i, final boolean z) {
            this.mExecutor.execute(new Runnable() { // from class: android.os.SystemVibrator$MultiVibratorStateListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SystemVibrator.MultiVibratorStateListener.this.lambda$onVibrating$0(i, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVibrating$0(int i, boolean z) {
            boolean z2;
            boolean z3;
            synchronized (this.mLock) {
                z2 = true;
                int size = (1 << this.mVibratorListeners.size()) - 1;
                int i2 = this.mVibratingMask;
                boolean z4 = i2 != 0;
                int i3 = this.mInitializedMask;
                boolean z5 = i3 == size;
                int i4 = 1 << i;
                int i5 = i3 | i4;
                this.mInitializedMask = i5;
                if (((i2 & i4) != 0) != z) {
                    this.mVibratingMask = i4 ^ i2;
                }
                z3 = this.mVibratingMask != 0;
                boolean z6 = i5 == size;
                boolean z7 = z4 != z3;
                if (!z6 || (z5 && !z7)) {
                    z2 = false;
                }
            }
            if (z2) {
                this.mDelegate.onVibratorStateChanged(z3);
            }
        }
    }

    @Override // android.os.Vibrator
    public int semGetSupportedVibrationType() {
        VibratorManager vibratorManager = this.mVibratorManager;
        if (vibratorManager == null) {
            Log.w(TAG, "Failed to call semGetSupportedVibrationType; no vibrator service.");
            return -1;
        }
        if (this.mSupportedVibrationType == -1) {
            this.mSupportedVibrationType = vibratorManager.semGetSupportedVibrationType();
        }
        return this.mSupportedVibrationType;
    }

    @Override // android.os.Vibrator
    public int semGetNumberOfSupportedPatterns() {
        VibratorManager vibratorManager = this.mVibratorManager;
        if (vibratorManager == null) {
            Log.w(TAG, "Failed to call semGetNumberOfSupportedPatterns; no vibrator service.");
            return -1;
        }
        if (this.mSupportedPatternCounts == -1) {
            this.mSupportedPatternCounts = vibratorManager.semGetNumberOfSupportedPatterns();
        }
        return this.mSupportedPatternCounts;
    }

    @Override // android.os.Vibrator
    public boolean semIsHapticSupported() {
        VibratorManager vibratorManager = this.mVibratorManager;
        if (vibratorManager == null) {
            Log.w(TAG, "Failed to call semIsHapticSupported; no vibrator service.");
            return false;
        }
        if (this.mSupportedVibrationType == -1) {
            this.mSupportedVibrationType = vibratorManager.semGetSupportedVibrationType();
        }
        return this.mSupportedVibrationType > 1;
    }

    @Override // android.os.Vibrator
    public boolean semIsVibrating() {
        return isVibrating();
    }

    @Override // android.os.Vibrator
    public String executeVibrationDebugCommand(VibrationDebugInfo vibrationDebugInfo) {
        return this.mVibratorManager.executeVibrationDebugCommand(vibrationDebugInfo);
    }
}

package com.android.systemui.classifier;

import android.hardware.biometrics.BiometricSourceType;
import android.util.Log;
import android.view.MotionEvent;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.dock.DockManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeQsExpansionListener;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.sensors.ProximitySensorImpl;
import com.android.systemui.util.sensors.ThresholdSensor;
import com.android.systemui.util.sensors.ThresholdSensorEvent;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FalsingCollectorImpl implements FalsingCollector {
    public static final boolean DEBUG = Log.isLoggable("FalsingCollector", 3);
    public boolean mAvoidGesture;
    public final C11443 mBatteryListener;
    public final C11454 mDockEventListener;
    public final DockManager mDockManager;
    public final FalsingDataProvider mFalsingDataProvider;
    public final FalsingManager mFalsingManager;
    public final HistoryTracker mHistoryTracker;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateCallback;
    public final DelayableExecutor mMainExecutor;
    public MotionEvent mPendingDownEvent;
    public final ProximitySensor mProximitySensor;
    public final FalsingCollectorImpl$$ExternalSyntheticLambda1 mSensorEventListener = new ThresholdSensor.Listener() { // from class: com.android.systemui.classifier.FalsingCollectorImpl$$ExternalSyntheticLambda1
        @Override // com.android.systemui.util.sensors.ThresholdSensor.Listener
        public final void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
            FalsingCollectorImpl falsingCollectorImpl = FalsingCollectorImpl.this;
            falsingCollectorImpl.getClass();
            falsingCollectorImpl.mFalsingManager.onProximityEvent(new FalsingCollectorImpl.ProximityEventImpl(thresholdSensorEvent));
        }
    };
    public boolean mSessionStarted;
    public boolean mShowingAod;
    public int mState;
    public final StatusBarStateController mStatusBarStateController;
    public final C11421 mStatusBarStateListener;
    public final SystemClock mSystemClock;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ProximityEventImpl implements FalsingManager.ProximityEvent {
        public final ThresholdSensorEvent mThresholdSensorEvent;

        public ProximityEventImpl(ThresholdSensorEvent thresholdSensorEvent) {
            this.mThresholdSensorEvent = thresholdSensorEvent;
        }

        @Override // com.android.systemui.plugins.FalsingManager.ProximityEvent
        public final boolean getCovered() {
            return this.mThresholdSensorEvent.mBelow;
        }

        @Override // com.android.systemui.plugins.FalsingManager.ProximityEvent
        public final long getTimestampNs() {
            return this.mThresholdSensorEvent.mTimestampNs;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.classifier.FalsingCollectorImpl$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.classifier.FalsingCollectorImpl$1, com.android.systemui.plugins.statusbar.StatusBarStateController$StateListener] */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.systemui.classifier.FalsingCollectorImpl$3, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.systemui.classifier.FalsingCollectorImpl$4] */
    public FalsingCollectorImpl(FalsingDataProvider falsingDataProvider, FalsingManager falsingManager, KeyguardUpdateMonitor keyguardUpdateMonitor, HistoryTracker historyTracker, ProximitySensor proximitySensor, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, ShadeExpansionStateManager shadeExpansionStateManager, BatteryController batteryController, DockManager dockManager, DelayableExecutor delayableExecutor, SystemClock systemClock) {
        ?? r3 = new StatusBarStateController.StateListener() { // from class: com.android.systemui.classifier.FalsingCollectorImpl.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                String str = "StatusBarState=" + StatusBarState.toString(i);
                if (FalsingCollectorImpl.DEBUG) {
                    Log.d("FalsingCollector", str, null);
                }
                FalsingCollectorImpl falsingCollectorImpl = FalsingCollectorImpl.this;
                falsingCollectorImpl.mState = i;
                falsingCollectorImpl.sessionEnd();
            }
        };
        this.mStatusBarStateListener = r3;
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.classifier.FalsingCollectorImpl.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                if (i == KeyguardUpdateMonitor.getCurrentUser() && biometricSourceType == BiometricSourceType.FACE) {
                    FalsingCollectorImpl.this.mFalsingDataProvider.mJustUnlockedWithFace = true;
                }
            }
        };
        this.mKeyguardUpdateCallback = keyguardUpdateMonitorCallback;
        ?? r5 = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.classifier.FalsingCollectorImpl.3
            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onWirelessChargingChanged(boolean z) {
                FalsingCollectorImpl falsingCollectorImpl = FalsingCollectorImpl.this;
                if (z) {
                    ((ProximitySensorImpl) falsingCollectorImpl.mProximitySensor).pause();
                } else {
                    falsingCollectorImpl.mDockManager.getClass();
                    ((ProximitySensorImpl) falsingCollectorImpl.mProximitySensor).resume();
                }
            }

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
            }
        };
        this.mBatteryListener = r5;
        this.mDockEventListener = new DockManager.DockEventListener(this) { // from class: com.android.systemui.classifier.FalsingCollectorImpl.4
        };
        this.mFalsingDataProvider = falsingDataProvider;
        this.mFalsingManager = falsingManager;
        this.mHistoryTracker = historyTracker;
        this.mProximitySensor = proximitySensor;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mDockManager = dockManager;
        this.mMainExecutor = delayableExecutor;
        this.mSystemClock = systemClock;
        ProximitySensorImpl proximitySensorImpl = (ProximitySensorImpl) proximitySensor;
        proximitySensorImpl.setTag("FalsingCollector");
        proximitySensorImpl.setDelay();
        statusBarStateController.addCallback(r3);
        this.mState = statusBarStateController.getState();
        keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
        shadeExpansionStateManager.addQsExpansionListener(new ShadeQsExpansionListener() { // from class: com.android.systemui.classifier.FalsingCollectorImpl$$ExternalSyntheticLambda2
            @Override // com.android.systemui.shade.ShadeQsExpansionListener
            public final void onQsExpansionChanged(boolean z) {
                FalsingCollectorImpl.this.onQsExpansionChanged(Boolean.valueOf(z));
            }
        });
        ((BatteryControllerImpl) batteryController).addCallback(r5);
        dockManager.getClass();
    }

    public final void avoidGesture() {
        this.mAvoidGesture = true;
        MotionEvent motionEvent = this.mPendingDownEvent;
        if (motionEvent != null) {
            motionEvent.recycle();
            this.mPendingDownEvent = null;
        }
    }

    public void onQsExpansionChanged(Boolean bool) {
        boolean booleanValue = bool.booleanValue();
        FalsingCollectorImpl$$ExternalSyntheticLambda1 falsingCollectorImpl$$ExternalSyntheticLambda1 = this.mSensorEventListener;
        ProximitySensor proximitySensor = this.mProximitySensor;
        if (booleanValue) {
            proximitySensor.unregister(falsingCollectorImpl$$ExternalSyntheticLambda1);
        } else if (this.mSessionStarted) {
            proximitySensor.register(falsingCollectorImpl$$ExternalSyntheticLambda1);
        }
    }

    public final void onTouchEvent(MotionEvent motionEvent) {
        if (!((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            avoidGesture();
            return;
        }
        if (motionEvent.getActionMasked() == 4) {
            return;
        }
        if (motionEvent.getActionMasked() == 0) {
            this.mPendingDownEvent = MotionEvent.obtain(motionEvent);
            this.mAvoidGesture = false;
        } else {
            if (this.mAvoidGesture) {
                return;
            }
            MotionEvent motionEvent2 = this.mPendingDownEvent;
            FalsingDataProvider falsingDataProvider = this.mFalsingDataProvider;
            if (motionEvent2 != null) {
                falsingDataProvider.onMotionEvent(motionEvent2);
                this.mPendingDownEvent.recycle();
                this.mPendingDownEvent = null;
            }
            falsingDataProvider.onMotionEvent(motionEvent);
        }
    }

    public final void sessionEnd() {
        if (this.mSessionStarted) {
            if (DEBUG) {
                Log.d("FalsingCollector", "Ending Session", null);
            }
            final int i = 0;
            this.mSessionStarted = false;
            this.mProximitySensor.unregister(this.mSensorEventListener);
            FalsingDataProvider falsingDataProvider = this.mFalsingDataProvider;
            Iterator it = falsingDataProvider.mRecentMotionEvents.iterator();
            while (it.hasNext()) {
                ((MotionEvent) it.next()).recycle();
            }
            falsingDataProvider.mRecentMotionEvents.clear();
            falsingDataProvider.mDirty = true;
            ((ArrayList) falsingDataProvider.mSessionListeners).forEach(new Consumer() { // from class: com.android.systemui.classifier.FalsingDataProvider$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i) {
                        case 0:
                            BrightLineFalsingManager brightLineFalsingManager = BrightLineFalsingManager.this;
                            brightLineFalsingManager.mLastProximityEvent = null;
                            brightLineFalsingManager.mClassifiers.forEach(new BrightLineFalsingManager$$ExternalSyntheticLambda0(2));
                            break;
                        default:
                            BrightLineFalsingManager.this.mClassifiers.forEach(new BrightLineFalsingManager$$ExternalSyntheticLambda0(3));
                            break;
                    }
                }
            });
        }
    }

    public final void updateFalseConfidence(FalsingClassifier.Result result) {
        Set singleton = Collections.singleton(result);
        ((SystemClockImpl) this.mSystemClock).getClass();
        this.mHistoryTracker.addResults(singleton, android.os.SystemClock.uptimeMillis());
    }
}

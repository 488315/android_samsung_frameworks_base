package com.android.server.display;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManagerInternal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.config.SensorData;
import com.android.server.display.utils.SensorUtils;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DisplayPowerProximityStateController {
    static final int MSG_PROXIMITY_SENSOR_DEBOUNCED = 1;
    static final int PROXIMITY_POSITIVE = 1;
    static final int PROXIMITY_SENSOR_POSITIVE_DEBOUNCE_DELAY = 0;
    static final int PROXIMITY_UNKNOWN = -1;
    public final DisplayPowerProximityStateController$Injector$$ExternalSyntheticLambda0 mClock;
    public DisplayDeviceConfig mDisplayDeviceConfig;
    public final int mDisplayId;
    public final DisplayPowerProximityStateHandler mHandler;
    public boolean mIgnoreProximityUntilChanged;
    public boolean mIsEarsenseProximity;
    public boolean mIsProximitySensorOnFoldingSide;
    public boolean mIsViewTypeCover;
    public final Runnable mNudgeUpdatePowerState;
    public boolean mPendingWaitForNegativeProximityLocked;
    public Sensor mProximitySensor;
    public boolean mProximitySensorEnabled;
    public float mProximityThreshold;
    public boolean mScreenOffBecauseOfProximity;
    public final SensorManager mSensorManager;
    public final String mTag;
    public boolean mWaitingForNegativeProximity;
    public final WakelockController mWakelockController;
    public final Object mLock = new Object();
    public final AnonymousClass1 mProximitySensorListener = new SensorEventListener() { // from class: com.android.server.display.DisplayPowerProximityStateController.1
        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
            DisplayPowerProximityStateController displayPowerProximityStateController = DisplayPowerProximityStateController.this;
            if (displayPowerProximityStateController.mProximitySensorEnabled) {
                displayPowerProximityStateController.mClock.getClass();
                long uptimeMillis = SystemClock.uptimeMillis();
                int i = 0;
                float f = sensorEvent.values[0];
                boolean z = f >= FullScreenMagnificationGestureHandler.MAX_SCALE && f < DisplayPowerProximityStateController.this.mProximityThreshold;
                Slog.d(DisplayPowerProximityStateController.this.mTag, "[api] onSensorChanged: proximity: " + z);
                DisplayPowerProximityStateController displayPowerProximityStateController2 = DisplayPowerProximityStateController.this;
                if (displayPowerProximityStateController2.mProximitySensorEnabled) {
                    int i2 = displayPowerProximityStateController2.mPendingProximity;
                    if (i2 != 0 || z) {
                        if (i2 == 1 && z) {
                            return;
                        }
                        displayPowerProximityStateController2.mHandler.removeMessages(1);
                        WakelockController wakelockController = displayPowerProximityStateController2.mWakelockController;
                        if (z) {
                            displayPowerProximityStateController2.mPendingProximity = 1;
                            int i3 = displayPowerProximityStateController2.mSensorPositiveDebounceDelay;
                            if (i3 == -1) {
                                i3 = 0;
                            }
                            long j = uptimeMillis + i3;
                            if (displayPowerProximityStateController2.mIsEarsenseProximity && displayPowerProximityStateController2.mIsViewTypeCover) {
                                i = 50;
                            }
                            displayPowerProximityStateController2.mPendingProximityDebounceTime = j + i;
                            wakelockController.acquireWakelock(3);
                        } else {
                            displayPowerProximityStateController2.mPendingProximity = 0;
                            displayPowerProximityStateController2.mPendingProximityDebounceTime = uptimeMillis + (displayPowerProximityStateController2.mSensorNegativeDebounceDelay != -1 ? r10 : 50);
                            wakelockController.acquireWakelock(3);
                        }
                        displayPowerProximityStateController2.debounceProximitySensor();
                    }
                }
            }
        }
    };
    public int mPendingProximity = -1;
    public long mPendingProximityDebounceTime = -1;
    public int mProximity = -1;
    public boolean mSkipRampBecauseOfProximityChangeToNegative = false;
    public int mSensorPositiveDebounceDelay = -1;
    public int mSensorNegativeDebounceDelay = -1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface Clock {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayPowerProximityStateHandler extends Handler {
        public DisplayPowerProximityStateHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            DisplayPowerProximityStateController displayPowerProximityStateController = DisplayPowerProximityStateController.this;
            if (i == 1) {
                displayPowerProximityStateController.debounceProximitySensor();
                return;
            }
            if (i == 2 && !displayPowerProximityStateController.mIgnoreProximityUntilChanged && displayPowerProximityStateController.mProximity == 1) {
                displayPowerProximityStateController.mIgnoreProximityUntilChanged = true;
                Slog.i(displayPowerProximityStateController.mTag, "Ignoring proximity");
                displayPowerProximityStateController.mNudgeUpdatePowerState.run();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.display.DisplayPowerProximityStateController$1] */
    public DisplayPowerProximityStateController(WakelockController wakelockController, DisplayDeviceConfig displayDeviceConfig, Looper looper, DisplayPowerController$$ExternalSyntheticLambda2 displayPowerController$$ExternalSyntheticLambda2, int i, SensorManager sensorManager) {
        new Injector();
        this.mClock = new DisplayPowerProximityStateController$Injector$$ExternalSyntheticLambda0();
        this.mWakelockController = wakelockController;
        this.mHandler = new DisplayPowerProximityStateHandler(looper);
        this.mNudgeUpdatePowerState = displayPowerController$$ExternalSyntheticLambda2;
        this.mDisplayDeviceConfig = displayDeviceConfig;
        this.mDisplayId = i;
        this.mTag = BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "DisplayPowerProximityStateController[", "]");
        this.mSensorManager = sensorManager;
        loadProximitySensor();
    }

    public final void debounceProximitySensor() {
        if (!this.mProximitySensorEnabled || this.mPendingProximity == -1 || this.mPendingProximityDebounceTime < 0) {
            return;
        }
        getClass();
        if (this.mPendingProximityDebounceTime > SystemClock.uptimeMillis()) {
            DisplayPowerProximityStateHandler displayPowerProximityStateHandler = this.mHandler;
            displayPowerProximityStateHandler.sendMessageAtTime(displayPowerProximityStateHandler.obtainMessage(1), this.mPendingProximityDebounceTime);
            return;
        }
        if (this.mProximity != this.mPendingProximity) {
            this.mIgnoreProximityUntilChanged = false;
            Slog.i(this.mTag, AmFmBandRange$$ExternalSyntheticOutline0.m(this.mPendingProximity, new StringBuilder("No longer ignoring proximity ["), "]"));
        }
        this.mProximity = this.mPendingProximity;
        this.mNudgeUpdatePowerState.run();
        if (this.mWakelockController.releaseWakelockInternal(3)) {
            this.mPendingProximityDebounceTime = -1L;
        }
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public int getPendingProximity() {
        return this.mPendingProximity;
    }

    public long getPendingProximityDebounceTime() {
        return this.mPendingProximityDebounceTime;
    }

    public boolean getPendingWaitForNegativeProximityLocked() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mPendingWaitForNegativeProximityLocked;
        }
        return z;
    }

    public int getProximity() {
        return this.mProximity;
    }

    public SensorEventListener getProximitySensorListener() {
        return this.mProximitySensorListener;
    }

    public boolean getWaitingForNegativeProximity() {
        return this.mWaitingForNegativeProximity;
    }

    public final boolean isProximityPositive() {
        return this.mProximity == 1;
    }

    public final boolean isProximitySensorValidState(DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        int i;
        int i2 = displayPowerRequest.dualScreenPolicy;
        if (displayPowerRequest.coverClosed) {
            return false;
        }
        return !PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY || ((i = this.mDisplayId) == 0 && i2 != 1) || (i == 1 && !this.mIsProximitySensorOnFoldingSide && i2 == 1);
    }

    public final void loadProximitySensor() {
        int i = this.mDisplayId;
        int i2 = (i == 0 || (i == 1 && PowerManagerUtil.SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY)) ? 8 : 0;
        SensorManager sensorManager = this.mSensorManager;
        SensorData sensorData = this.mDisplayDeviceConfig.mProximitySensor;
        Sensor findSensor = sensorData == null ? null : SensorUtils.findSensor(sensorManager, sensorData.type, sensorData.name, i2);
        this.mProximitySensor = findSensor;
        if (findSensor != null) {
            this.mProximityThreshold = Math.min(findSensor.getMaximumRange(), 5.0f);
            this.mIsEarsenseProximity = this.mProximitySensor.getName().contains("Palm") || this.mProximitySensor.getName().contains("Ear Hover");
            this.mIsProximitySensorOnFoldingSide = this.mProximitySensor.semIsOnFoldingSide();
        }
    }

    public final void setProximitySensorEnabled(boolean z) {
        AnonymousClass1 anonymousClass1 = this.mProximitySensorListener;
        DisplayPowerProximityStateHandler displayPowerProximityStateHandler = this.mHandler;
        String str = this.mTag;
        if (z) {
            if (this.mProximitySensorEnabled) {
                return;
            }
            this.mProximitySensorEnabled = true;
            this.mIgnoreProximityUntilChanged = false;
            Slog.d(str, "setProximitySensorEnabled::registerListener");
            this.mSensorManager.registerListener(anonymousClass1, this.mProximitySensor, 3, displayPowerProximityStateHandler);
            return;
        }
        if (this.mProximitySensorEnabled) {
            this.mProximitySensorEnabled = false;
            this.mProximity = -1;
            this.mIgnoreProximityUntilChanged = false;
            this.mPendingProximity = -1;
            displayPowerProximityStateHandler.removeMessages(1);
            Slog.d(str, "setProximitySensorEnabled::unregisterListener");
            this.mSensorManager.unregisterListener(anonymousClass1);
            if (this.mWakelockController.releaseWakelockInternal(3)) {
                this.mPendingProximityDebounceTime = -1L;
            }
        }
    }

    public boolean shouldIgnoreProximityUntilChanged() {
        return this.mIgnoreProximityUntilChanged;
    }

    public final boolean shouldSkipRampBecauseOfProximityChangeToNegative() {
        return this.mSkipRampBecauseOfProximityChangeToNegative;
    }

    public final void updatePendingProximityRequestsLocked() {
        synchronized (this.mLock) {
            try {
                this.mWaitingForNegativeProximity |= this.mPendingWaitForNegativeProximityLocked;
                this.mPendingWaitForNegativeProximityLocked = false;
                if (this.mIgnoreProximityUntilChanged) {
                    this.mWaitingForNegativeProximity = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}

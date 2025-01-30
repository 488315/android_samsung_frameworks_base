package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Bundle;
import android.util.Slog;
import com.android.server.biometrics.SemBiometricSysUiManager;
import com.android.server.biometrics.Utils;

/* loaded from: classes.dex */
public class SemFpGestureCalibrator implements SemFpEventListener {
    public boolean mAlreadyDone;
    public Callback mCallback;
    public final Context mContext;
    int mCurrentSwipeDirection;
    int mDbState;
    public final Injector mInjector;
    public boolean mIsGestureCalibrationInProgress;
    public final ServiceProvider mServiceProvider;
    SemBiometricSysUiManager.SysUiListener mSysUiListener;
    public final SemBiometricSysUiManager mSysUiManager;
    public int mSysUiSessionId;

    public interface Callback {
        void onCalibrationFinished();

        void onCalibrationStarted();
    }

    public class Injector {
        public void setCalibrationState(Context context, int i) {
            Utils.putIntDb(context, "fingerprint_gesture_calibration_state", true, i);
        }

        public int getCalibrationState(Context context) {
            return Utils.getIntDb(context, "fingerprint_gesture_calibration_state", true, -1, -2);
        }

        public void disableGestureOption(Context context) {
            Utils.putIntDb(context, "fingerprint_gesture_quick", false, 0, -2);
            Utils.putIntDb(context, "fingerprint_gesture_spay", false, 0, -2);
        }
    }

    public SemFpGestureCalibrator(Context context, ServiceProvider serviceProvider) {
        this(context, SemBiometricSysUiManager.get(), serviceProvider, new Injector());
    }

    public SemFpGestureCalibrator(Context context, SemBiometricSysUiManager semBiometricSysUiManager, ServiceProvider serviceProvider, Injector injector) {
        this.mDbState = -1;
        this.mContext = context;
        this.mServiceProvider = serviceProvider;
        this.mSysUiManager = semBiometricSysUiManager;
        this.mInjector = injector;
        this.mSysUiListener = new SemBiometricSysUiManager.SysUiListener() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpGestureCalibrator.1
            @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
            public void onEvent(int i, int i2) {
                SemFpGestureCalibrator.this.handleOnSysUiEvent(i, i2);
            }

            @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
            public void onError(int i, int i2) {
                SemFpGestureCalibrator.this.handleOnError(i, i2);
            }

            @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
            public void onDismissed(int i, byte[] bArr) {
                SemFpGestureCalibrator.this.handleOnDismissed(i);
            }
        };
    }

    public void onGestureSettingChanged(boolean z) {
        if (z) {
            this.mDbState = this.mInjector.getCalibrationState(this.mContext);
        } else {
            this.mInjector.setCalibrationState(this.mContext, -1);
            this.mDbState = -1;
        }
        Slog.d("FingerprintService.GestureCal", "onGestureSettingChanged: " + this.mDbState);
    }

    public boolean isCalibrationInProgress() {
        return this.mIsGestureCalibrationInProgress;
    }

    public boolean isInverseDirection() {
        return this.mDbState == 1;
    }

    public boolean hasCalibrationData() {
        return this.mDbState != -1;
    }

    public void onBootCompleted(boolean z) {
        if (z && this.mDbState == -1) {
            Slog.d("FingerprintService.GestureCal", "gesture function enabled, but No calibration data");
            this.mInjector.setCalibrationState(this.mContext, 0);
            this.mDbState = 0;
        }
    }

    public final void handleOnSysUiEvent(int i, int i2) {
        Slog.i("FingerprintService.GestureCal", "onEvent: " + i + ", " + i2);
        if (i == 1) {
            handleOnUiReady();
        }
    }

    public final void handleOnUiReady() {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onCalibrationStarted();
        }
        turnOnGestureMode();
        this.mServiceProvider.semAddEventListener(this);
    }

    public final void handleOnError(int i, int i2) {
        Slog.i("FingerprintService.GestureCal", "handleOnError: " + i + ", " + i2);
        this.mInjector.disableGestureOption(this.mContext);
        turnOffGestureMode();
        finish();
    }

    public final void handleOnDismissed(int i) {
        Slog.i("FingerprintService.GestureCal", "handleOnDismissed: " + i + ", " + this.mDbState);
        this.mIsGestureCalibrationInProgress = false;
        if (this.mAlreadyDone) {
            return;
        }
        if (i == 1) {
            this.mInjector.setCalibrationState(this.mContext, this.mDbState);
        } else {
            this.mInjector.disableGestureOption(this.mContext);
            turnOffGestureMode();
        }
        finish();
        this.mAlreadyDone = true;
    }

    public final void finish() {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onCalibrationFinished();
            this.mCallback = null;
        }
        this.mServiceProvider.semRemoveEventListener(this);
        this.mSysUiManager.closeSession(this.mSysUiSessionId);
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void start(boolean z) {
        this.mIsGestureCalibrationInProgress = true;
        this.mAlreadyDone = false;
        if (z) {
            this.mCurrentSwipeDirection = 20001;
        } else {
            this.mCurrentSwipeDirection = 20002;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("KEY_FP_GESTURE_DIRECTION", this.mCurrentSwipeDirection);
        int openSession = this.mSysUiManager.openSession(toString(), this.mSysUiListener);
        this.mSysUiSessionId = openSession;
        this.mSysUiManager.show(openSession, 65536, bundle);
    }

    public final void turnOffGestureMode() {
        ServiceProvider serviceProvider = this.mServiceProvider;
        serviceProvider.semRequest(((FingerprintSensorPropertiesInternal) serviceProvider.getSensorProperties().get(0)).sensorId, 16, 0, null, null);
    }

    public final void turnOnGestureMode() {
        ServiceProvider serviceProvider = this.mServiceProvider;
        serviceProvider.semRequest(((FingerprintSensorPropertiesInternal) serviceProvider.getSensorProperties().get(0)).sensorId, 15, 0, null, null);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEventListener
    public void onGestureEvent(int i, int i2) {
        notifyGestureEvent(i2);
        if (this.mCurrentSwipeDirection == i2) {
            this.mDbState = 0;
        } else {
            this.mDbState = 1;
        }
    }

    public final void notifyGestureEvent(int i) {
        this.mSysUiManager.sendCommand(this.mSysUiSessionId, 600, i, null);
    }
}

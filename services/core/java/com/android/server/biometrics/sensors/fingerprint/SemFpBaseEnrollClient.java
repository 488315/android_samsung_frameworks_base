package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.fingerprint.Fingerprint;
import android.os.IBinder;
import android.util.Slog;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.BiometricUtils;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.EnrollClient;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public abstract class SemFpBaseEnrollClient extends EnrollClient implements SemFpPauseResumeHandler, SemUdfpsConstraintStatusListener, SemFpTspBlockStatusHandler {
    public int mDuplicatedImgCnt;
    public final Injector mInjector;
    public int mLastErrorCode;
    public int mTotalQualityErrorCount;
    public SemUdfpsSysUiImpl mUdfpsImpl;

    public abstract void sendPauseCommand();

    public abstract void sendResumeCommand();

    public abstract void setEnrollType();

    public class Injector {
        public void sendBigDataForEnrollSuccess(int i) {
            SemBioAnalyticsManager.get().fpInsertLog("FPEN", Integer.toString(i), -1, 3);
        }

        public void startLogging(int i, String str) {
            SemBioLoggingManager.get().fpStart(i, "E", str);
        }

        public void stopLogging(int i, String str, int i2, int i3, int i4) {
            SemBioLoggingManager.get().fpStop(i, str, i4, i2, i3);
        }

        public SemUdfpsSysUiImpl createUdfpsSysUiImpl(Context context, IBinder iBinder, String str, int i) {
            return new SemUdfpsSysUiImpl(context, iBinder, str, i, true);
        }
    }

    public SemFpBaseEnrollClient(Context context, Supplier supplier, IBinder iBinder, ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, byte[] bArr, String str, BiometricUtils biometricUtils, int i2, int i3, boolean z, BiometricLogger biometricLogger, BiometricContext biometricContext) {
        this(context, supplier, iBinder, clientMonitorCallbackConverter, i, bArr, str, biometricUtils, i2, i3, z, biometricLogger, biometricContext, new Injector());
    }

    public SemFpBaseEnrollClient(Context context, Supplier supplier, IBinder iBinder, ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, byte[] bArr, String str, BiometricUtils biometricUtils, int i2, int i3, boolean z, BiometricLogger biometricLogger, BiometricContext biometricContext, Injector injector) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, bArr, str, biometricUtils, i2, i3, z, biometricLogger, biometricContext);
        this.mInjector = injector;
    }

    public void initForUdfps() {
        SemUdfpsSysUiImpl createUdfpsSysUiImpl = this.mInjector.createUdfpsSysUiImpl(getContext(), getToken(), getOwnerString(), getTargetUserId());
        this.mUdfpsImpl = createUdfpsSysUiImpl;
        createUdfpsSysUiImpl.setSysUiType(64);
        this.mUdfpsImpl.setSysUiListener(new SemUdfpsSysUiImpl.SysUiCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpBaseEnrollClient.1
            @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl.SysUiCallback
            public void onSysUiDismissed() {
                SemFpBaseEnrollClient.this.onUserCanceled();
            }

            @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl.SysUiCallback
            public void onSysUiError(int i, int i2) {
                SemFpBaseEnrollClient.this.onError(5, 0);
                SemFpBaseEnrollClient.this.cancel();
            }
        });
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpTspBlockStatusHandler
    public void onTspBlocked() {
        onAcquired(6, 1004);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpTspBlockStatusHandler
    public void onTspUnBlocked() {
        onAcquired(6, 1005);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsConstraintStatusListener
    public void onOneHandModeEnabled() {
        onError(8, 5001);
        cancel();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsConstraintStatusListener
    public void onWirelessPowerEnabled() {
        onError(8, 5004);
        cancel();
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient
    public boolean hasReachedEnrollmentLimit() {
        if (FingerprintUtils.getInstance(getSensorId()).getBiometricsForUser(getContext(), getTargetUserId()).size() < FingerprintUtils.semGetMaxTemplateNumberFromSPF()) {
            return false;
        }
        Slog.w("FingerprintService", "Too many fingerprints registered, user: " + getTargetUserId());
        return true;
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void onAcquired(int i, int i2) {
        super.onAcquired(i, i2);
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
            deliverEventToUdfpsSysUiIfNeeded(i, i2);
        }
        if (i == 6 && i2 == 1002) {
            this.mDuplicatedImgCnt++;
        }
        if (FingerprintUtils.semIsQualityFailedEvent(i, i2)) {
            this.mTotalQualityErrorCount++;
        }
    }

    public final void deliverEventToUdfpsSysUiIfNeeded(int i, int i2) {
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null && i == 6 && i2 == 10003) {
            semUdfpsSysUiImpl.handleOnAcquired(i, i2);
        }
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient, com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        if (i == 8) {
            this.mLastErrorCode = i2;
        } else {
            this.mLastErrorCode = i;
        }
        super.onError(i, i2);
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.handleOnError(i, i2);
        }
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient
    public void onEnrollResult(BiometricAuthenticator.Identifier identifier, int i) {
        if (i == 0) {
            SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
            if (semUdfpsSysUiImpl != null) {
                semUdfpsSysUiImpl.stop();
            }
            ((Fingerprint) identifier).semSetDuplicatedImgCount(this.mDuplicatedImgCnt);
            this.mInjector.sendBigDataForEnrollSuccess(identifier.getBiometricId());
            this.mInjector.stopLogging((int) getRequestId(), "S", this.mDuplicatedImgCnt, this.mTotalQualityErrorCount, 0);
        }
        super.onEnrollResult(identifier, i);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void startHalOperation() {
        SemUdfpsSysUiImpl semUdfpsSysUiImpl;
        setEnrollType();
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE && (semUdfpsSysUiImpl = this.mUdfpsImpl) != null) {
            semUdfpsSysUiImpl.start();
        }
        this.mInjector.startLogging((int) getRequestId(), getOwnerString());
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void stopHalOperation() {
        SemUdfpsSysUiImpl semUdfpsSysUiImpl;
        if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE || (semUdfpsSysUiImpl = this.mUdfpsImpl) == null) {
            return;
        }
        semUdfpsSysUiImpl.stop();
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void vibrateSuccess() {
        Utils.semVibrate(getContext(), 1);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpPauseResumeHandler
    public void onPause() {
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.handleOnPause();
        }
        sendPauseCommand();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpPauseResumeHandler
    public void onResume() {
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.handleOnResume();
        }
        sendResumeCommand();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor, com.android.server.biometrics.sensors.BaseClientMonitor
    public void destroy() {
        super.destroy();
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.destroy();
        }
        this.mInjector.stopLogging((int) getRequestId(), "U", 0, this.mTotalQualityErrorCount, this.mLastErrorCode);
    }

    public int getLastErrorCode() {
        return this.mLastErrorCode;
    }
}

package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.content.Context;
import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint;
import android.os.Build;
import android.os.Environment;
import android.os.RemoteException;
import android.os.SELinux;
import android.util.Slog;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.StartUserClient;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FingerprintUpdateActiveUserClient extends StartUserClient {
    public final Map mAuthenticatorIds;
    public final Supplier mCurrentUserId;
    public File mDirectory;
    public final boolean mForceUpdateAuthenticatorId;
    public final boolean mHasEnrolledBiometrics;

    public FingerprintUpdateActiveUserClient(Context context, HidlToAidlSensorAdapter$$ExternalSyntheticLambda3 hidlToAidlSensorAdapter$$ExternalSyntheticLambda3, int i, int i2, BiometricLogger biometricLogger, BiometricContext biometricContext, HidlToAidlSensorAdapter$$ExternalSyntheticLambda3 hidlToAidlSensorAdapter$$ExternalSyntheticLambda32, boolean z, Map map, boolean z2, StartUserClient.UserStartedCallback userStartedCallback) {
        super(context, hidlToAidlSensorAdapter$$ExternalSyntheticLambda3, null, i, i2, biometricLogger, biometricContext, userStartedCallback);
        this.mCurrentUserId = hidlToAidlSensorAdapter$$ExternalSyntheticLambda32;
        this.mForceUpdateAuthenticatorId = z2;
        this.mHasEnrolledBiometrics = z;
        this.mAuthenticatorIds = map;
    }

    @Override // com.android.server.biometrics.sensors.StartUserClient, com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 1;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        if (((Integer) this.mCurrentUserId.get()).intValue() == this.mTargetUserId && !this.mForceUpdateAuthenticatorId) {
            Slog.d("FingerprintUpdateActiveUserClient", "Already user: " + this.mCurrentUserId + ", returning");
            this.mUserStartedCallback.onUserStarted(this.mTargetUserId, 0, null);
            clientMonitorCallback.onClientFinished(this, true);
            return;
        }
        if (SemBiometricFeature.FP_FEATURE_USE_AOSP_HAL) {
            int i = Build.VERSION.DEVICE_INITIAL_SDK_INT;
            if (i < 1) {
                FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(i, "First SDK version ", " is invalid; must be at least VERSION_CODES.BASE", "FingerprintUpdateActiveUserClient");
            }
            File file = new File(i <= 27 ? Environment.getUserSystemDirectory(this.mTargetUserId) : Environment.getDataVendorDeDirectory(this.mTargetUserId), "fpdata");
            this.mDirectory = file;
            if (!file.exists()) {
                if (!this.mDirectory.mkdir()) {
                    Slog.e("FingerprintUpdateActiveUserClient", "Cannot make directory: " + this.mDirectory.getAbsolutePath());
                    clientMonitorCallback.onClientFinished(this, false);
                    return;
                }
                if (!SELinux.restorecon(this.mDirectory)) {
                    Slog.e("FingerprintUpdateActiveUserClient", "Restorecons failed. Directory will have wrong label.");
                    clientMonitorCallback.onClientFinished(this, false);
                    return;
                }
            }
        } else {
            this.mDirectory = new File("/data/vendor/biometrics/fp/User_" + this.mTargetUserId);
        }
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        final long j;
        try {
            if (Utils.DEBUG) {
                Slog.d("FingerprintUpdateActiveUserClient", "startHalOperation: setActiveGroup, " + this.mTargetUserId);
            }
            int i = this.mTargetUserId;
            Slog.d("FingerprintUpdateActiveUserClient", "Setting active user: " + i);
            HidlToAidlSessionAdapter hidlToAidlSessionAdapter = (HidlToAidlSessionAdapter) this.mLazyDaemon.get();
            if (((IBiometricsFingerprint) hidlToAidlSessionAdapter.mSession.get()) == null) {
                Slog.e("FingerprintUpdateActiveUserClient", "Failed to setActiveGroup: HIDL daemon is null.");
                this.mCallback.onClientFinished(this, false);
                return;
            }
            ((IBiometricsFingerprint) hidlToAidlSessionAdapter.mSession.get()).setActiveGroup(i, this.mDirectory.getAbsolutePath());
            if (this.mHasEnrolledBiometrics) {
                j = ((IBiometricsFingerprint) hidlToAidlSessionAdapter.mSession.get()).getAuthenticatorId();
                Slog.i("FingerprintUpdateActiveUserClient", "FingerprintUpdateActiveUserClient: from daemon: " + j);
                final SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.get();
                semBioLoggingManager.getFpHandler().post(new Runnable() { // from class: com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemBioLoggingManager semBioLoggingManager2 = SemBioLoggingManager.this;
                        long j2 = j;
                        synchronized (semBioLoggingManager2.mFpAuthLogList) {
                            try {
                                try {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm:ss", Locale.ENGLISH);
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(j2 == 0 ? "0L" : Integer.toString((int) j2));
                                    sb.append(", ");
                                    sb.append(simpleDateFormat.format(new Date(System.currentTimeMillis())));
                                    semBioLoggingManager2.mLastFpAuthenticatorId = sb.toString();
                                } catch (Exception e) {
                                    Slog.w("BiometricStats", "fpSetAuthenticatorId: " + e.getMessage());
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
            } else {
                j = 0;
            }
            this.mAuthenticatorIds.put(Integer.valueOf(i), Long.valueOf(j));
            this.mUserStartedCallback.onUserStarted(i, 0, null);
            this.mCallback.onClientFinished(this, true);
        } catch (RemoteException e) {
            Slog.e("FingerprintUpdateActiveUserClient", "Failed to setActiveGroup: " + e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {
    }
}

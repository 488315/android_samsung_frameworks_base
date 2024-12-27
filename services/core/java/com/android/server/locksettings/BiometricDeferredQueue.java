package com.android.server.locksettings;

import android.hardware.biometrics.BiometricManager;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.ServiceManager;
import android.service.gatekeeper.IGateKeeperService;
import android.util.Slog;

import com.android.internal.widget.VerifyCredentialResponse;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.BiometricHandlerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BiometricDeferredQueue {
    public BiometricManager mBiometricManager;
    public FaceManager mFaceManager;
    public FaceResetLockoutTask mFaceResetLockoutTask;
    public FingerprintManager mFingerprintManager;
    public final SyntheticPasswordManager mSpManager;
    public final BiometricDeferredQueue$$ExternalSyntheticLambda2 mFaceFinishCallback =
            new BiometricDeferredQueue$$ExternalSyntheticLambda2(this);
    public final Handler mHandler =
            BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler();
    public final ArrayList mPendingResetLockoutsForFingerprint = new ArrayList();
    public final ArrayList mPendingResetLockoutsForFace = new ArrayList();
    public final ArrayList mPendingResetLockouts = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FaceResetLockoutTask implements FaceManager.GenerateChallengeCallback {
        public FaceManager faceManager;
        public BiometricDeferredQueue$$ExternalSyntheticLambda2 finishCallback;
        public List pendingResetLockuts;
        public Set sensorIds;
        public SyntheticPasswordManager spManager;

        public final void onGenerateChallengeResult(int i, int i2, long j) {
            if (!this.sensorIds.contains(Integer.valueOf(i))) {
                NandswapManager$$ExternalSyntheticOutline0.m(
                        i, "Unknown sensorId received: ", "BiometricDeferredQueue");
                return;
            }
            for (UserAuthInfo userAuthInfo : this.pendingResetLockuts) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i, "Resetting face lockout for sensor: ", ", user: "),
                        userAuthInfo.userId,
                        "BiometricDeferredQueue");
                byte[] requestHatFromGatekeeperPassword =
                        BiometricDeferredQueue.requestHatFromGatekeeperPassword(
                                this.spManager, userAuthInfo, j);
                if (requestHatFromGatekeeperPassword != null) {
                    this.faceManager.resetLockout(
                            i, userAuthInfo.userId, requestHatFromGatekeeperPassword);
                }
            }
            this.sensorIds.remove(Integer.valueOf(i));
            this.faceManager.revokeChallenge(i, i2, j);
            if (this.sensorIds.isEmpty()) {
                Slog.d(
                        "BiometricDeferredQueue",
                        "Done requesting resetLockout for all face sensors");
                this.finishCallback.f$0.mFaceResetLockoutTask = null;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserAuthInfo {
        public final byte[] gatekeeperPassword;
        public final int userId;

        public UserAuthInfo(int i, byte[] bArr) {
            this.userId = i;
            this.gatekeeperPassword = bArr;
        }
    }

    public BiometricDeferredQueue(SyntheticPasswordManager syntheticPasswordManager) {
        this.mSpManager = syntheticPasswordManager;
    }

    public static byte[] requestHatFromGatekeeperPassword(
            SyntheticPasswordManager syntheticPasswordManager, UserAuthInfo userAuthInfo, long j) {
        IGateKeeperService asInterface;
        synchronized (BiometricDeferredQueue.class) {
            IBinder waitForService =
                    ServiceManager.waitForService("android.service.gatekeeper.IGateKeeperService");
            if (waitForService == null) {
                Slog.e("BiometricDeferredQueue", "Unable to acquire GateKeeperService");
                asInterface = null;
            } else {
                asInterface = IGateKeeperService.Stub.asInterface(waitForService);
            }
        }
        VerifyCredentialResponse verifyChallengeInternal =
                syntheticPasswordManager.verifyChallengeInternal(
                        userAuthInfo.userId, j, asInterface, userAuthInfo.gatekeeperPassword);
        if (verifyChallengeInternal == null) {
            Slog.wtf("BiometricDeferredQueue", "VerifyChallenge failed, null response");
            return null;
        }
        if (verifyChallengeInternal.getResponseCode() == 0) {
            if (verifyChallengeInternal.getGatekeeperHAT() == null) {
                Slog.e("BiometricDeferredQueue", "Null HAT received from spManager");
            }
            return verifyChallengeInternal.getGatekeeperHAT();
        }
        Slog.wtf(
                "BiometricDeferredQueue",
                "VerifyChallenge failed, response: " + verifyChallengeInternal.getResponseCode());
        return null;
    }
}

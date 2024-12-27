package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.face.IFaceServiceReceiver;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SemFpAidlResponseHandler$$ExternalSyntheticLambda3
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemFpAidlResponseHandler f$0;
    public final /* synthetic */ long f$1;

    public /* synthetic */ SemFpAidlResponseHandler$$ExternalSyntheticLambda3(
            SemFpAidlResponseHandler semFpAidlResponseHandler, long j, int i) {
        this.$r8$classId = i;
        this.f$0 = semFpAidlResponseHandler;
        this.f$1 = j;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SemFpAidlResponseHandler semFpAidlResponseHandler = this.f$0;
                long j = this.f$1;
                FingerprintRevokeChallengeClient fingerprintRevokeChallengeClient =
                        (FingerprintRevokeChallengeClient) obj;
                semFpAidlResponseHandler.getClass();
                Slog.i("FingerprintService", "handleChallengeRevoked: onChallengeRevoked");
                fingerprintRevokeChallengeClient.mCallback.onClientFinished(
                        fingerprintRevokeChallengeClient,
                        j == fingerprintRevokeChallengeClient.mChallenge);
                fingerprintRevokeChallengeClient
                        .getHandler()
                        .removeCallbacks(fingerprintRevokeChallengeClient.mRevokeWatchdog);
                semFpAidlResponseHandler.mHalCallbackEx.dispatchChallengeListener(
                        semFpAidlResponseHandler.mSensorId,
                        semFpAidlResponseHandler.mUserId,
                        false,
                        j);
                break;
            default:
                SemFpAidlResponseHandler semFpAidlResponseHandler2 = this.f$0;
                long j2 = this.f$1;
                FingerprintGenerateChallengeClient fingerprintGenerateChallengeClient =
                        (FingerprintGenerateChallengeClient) obj;
                semFpAidlResponseHandler2.getClass();
                Slog.i("FingerprintService", "handleChallengeGenerated: onChallengeGenerated");
                int i = semFpAidlResponseHandler2.mSensorId;
                int i2 = semFpAidlResponseHandler2.mUserId;
                fingerprintGenerateChallengeClient.getClass();
                try {
                    ClientMonitorCallbackConverter clientMonitorCallbackConverter =
                            fingerprintGenerateChallengeClient.mListener;
                    IFaceServiceReceiver iFaceServiceReceiver =
                            clientMonitorCallbackConverter.mFaceServiceReceiver;
                    if (iFaceServiceReceiver != null) {
                        iFaceServiceReceiver.onChallengeGenerated(i, i2, j2);
                    } else {
                        IFingerprintServiceReceiver iFingerprintServiceReceiver =
                                clientMonitorCallbackConverter.mFingerprintServiceReceiver;
                        if (iFingerprintServiceReceiver != null) {
                            iFingerprintServiceReceiver.onChallengeGenerated(i, i2, j2);
                        }
                    }
                    fingerprintGenerateChallengeClient.mCallback.onClientFinished(
                            fingerprintGenerateChallengeClient, true);
                } catch (RemoteException e) {
                    Slog.e("FingerprintGenerateChallengeClient", "Unable to send challenge", e);
                    fingerprintGenerateChallengeClient.mCallback.onClientFinished(
                            fingerprintGenerateChallengeClient, false);
                }
                semFpAidlResponseHandler2.mHalCallbackEx.dispatchChallengeListener(
                        semFpAidlResponseHandler2.mSensorId,
                        semFpAidlResponseHandler2.mUserId,
                        true,
                        j2);
                break;
        }
    }
}

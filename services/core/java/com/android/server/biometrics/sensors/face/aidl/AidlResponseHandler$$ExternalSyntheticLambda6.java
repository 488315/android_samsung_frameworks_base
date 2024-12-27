package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.face.IFaceServiceReceiver;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AidlResponseHandler$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AidlResponseHandler f$0;
    public final /* synthetic */ long f$1;

    public /* synthetic */ AidlResponseHandler$$ExternalSyntheticLambda6(AidlResponseHandler aidlResponseHandler, long j, int i) {
        this.$r8$classId = i;
        this.f$0 = aidlResponseHandler;
        this.f$1 = j;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                AidlResponseHandler aidlResponseHandler = this.f$0;
                long j = this.f$1;
                FaceGenerateChallengeClient faceGenerateChallengeClient = (FaceGenerateChallengeClient) obj;
                int i = aidlResponseHandler.mSensorId;
                int i2 = aidlResponseHandler.mUserId;
                faceGenerateChallengeClient.getClass();
                try {
                    ClientMonitorCallbackConverter clientMonitorCallbackConverter = faceGenerateChallengeClient.mListener;
                    IFaceServiceReceiver iFaceServiceReceiver = clientMonitorCallbackConverter.mFaceServiceReceiver;
                    if (iFaceServiceReceiver != null) {
                        iFaceServiceReceiver.onChallengeGenerated(i, i2, j);
                    } else {
                        IFingerprintServiceReceiver iFingerprintServiceReceiver = clientMonitorCallbackConverter.mFingerprintServiceReceiver;
                        if (iFingerprintServiceReceiver != null) {
                            iFingerprintServiceReceiver.onChallengeGenerated(i, i2, j);
                        }
                    }
                    faceGenerateChallengeClient.mCallback.onClientFinished(faceGenerateChallengeClient, true);
                    break;
                } catch (RemoteException e) {
                    Slog.e("FaceGenerateChallengeClient", "Unable to send challenge", e);
                    faceGenerateChallengeClient.mCallback.onClientFinished(faceGenerateChallengeClient, false);
                    return;
                }
            default:
                AidlResponseHandler aidlResponseHandler2 = this.f$0;
                long j2 = this.f$1;
                FaceRevokeChallengeClient faceRevokeChallengeClient = (FaceRevokeChallengeClient) obj;
                aidlResponseHandler2.getClass();
                faceRevokeChallengeClient.mCallback.onClientFinished(faceRevokeChallengeClient, j2 == faceRevokeChallengeClient.mChallenge);
                break;
        }
    }
}

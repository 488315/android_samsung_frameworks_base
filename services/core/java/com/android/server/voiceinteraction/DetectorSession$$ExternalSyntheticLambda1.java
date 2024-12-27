package com.android.server.voiceinteraction;

import android.os.Bundle;
import android.os.IRemoteCallback;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.service.voice.HotwordDetectionService;
import android.service.voice.ISandboxedDetectionService;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DetectorSession$$ExternalSyntheticLambda1
        implements ServiceConnector.Job {
    public final /* synthetic */ DetectorSession f$0;
    public final /* synthetic */ PersistableBundle f$1;
    public final /* synthetic */ SharedMemory f$2;

    public /* synthetic */ DetectorSession$$ExternalSyntheticLambda1(
            DetectorSession detectorSession,
            PersistableBundle persistableBundle,
            SharedMemory sharedMemory) {
        this.f$0 = detectorSession;
        this.f$1 = persistableBundle;
        this.f$2 = sharedMemory;
    }

    public final Object run(Object obj) {
        DetectorSession detectorSession = this.f$0;
        PersistableBundle persistableBundle = this.f$1;
        SharedMemory sharedMemory = this.f$2;
        ISandboxedDetectionService iSandboxedDetectionService = (ISandboxedDetectionService) obj;
        int i = detectorSession.mVoiceInteractionServiceUid;
        AndroidFuture androidFuture = new AndroidFuture();
        try {
            iSandboxedDetectionService.updateState(
                    persistableBundle,
                    sharedMemory,
                    new IRemoteCallback
                            .Stub() { // from class:
                                      // com.android.server.voiceinteraction.DetectorSession.1
                        public final /* synthetic */ AndroidFuture val$future;

                        public AnonymousClass1(AndroidFuture androidFuture2) {
                            r2 = androidFuture2;
                        }

                        public final void sendResult(Bundle bundle) {
                            Pair pair;
                            r2.complete((Object) null);
                            if (DetectorSession.this.mUpdateStateAfterStartFinished.getAndSet(
                                    true)) {
                                Slog.w("DetectorSession", "call callback after timeout");
                                if (DetectorSession.this.getDetectorType() != 3) {
                                    HotwordMetricsLogger.writeDetectorEvent(
                                            DetectorSession.this.getDetectorType(),
                                            5,
                                            DetectorSession.this.mVoiceInteractionServiceUid);
                                    return;
                                }
                                return;
                            }
                            if (bundle == null) {
                                pair = new Pair(100, 2);
                            } else {
                                int i2 = bundle.getInt("initialization_status", 100);
                                if (i2
                                        > HotwordDetectionService
                                                .getMaxCustomInitializationStatus()) {
                                    pair = new Pair(100, Integer.valueOf(i2 == 100 ? 2 : 3));
                                } else {
                                    pair =
                                            new Pair(
                                                    Integer.valueOf(i2),
                                                    Integer.valueOf(i2 == 0 ? 0 : 1));
                                }
                            }
                            int intValue = ((Integer) pair.first).intValue();
                            int intValue2 = ((Integer) pair.second).intValue();
                            try {
                                DetectorSession.this.mCallback.onStatusReported(intValue);
                                if (DetectorSession.this.getDetectorType() != 3) {
                                    int detectorType = DetectorSession.this.getDetectorType();
                                    FrameworkStatsLog.write(
                                            FrameworkStatsLog
                                                    .HOTWORD_DETECTION_SERVICE_INIT_RESULT_REPORTED,
                                            detectorType != 1 ? detectorType != 2 ? 0 : 2 : 1,
                                            intValue2,
                                            DetectorSession.this.mVoiceInteractionServiceUid);
                                }
                            } catch (RemoteException e) {
                                AccountManagerService$$ExternalSyntheticOutline0.m(
                                        "Failed to report initialization status: ",
                                        e,
                                        "DetectorSession");
                                if (DetectorSession.this.getDetectorType() != 3) {
                                    HotwordMetricsLogger.writeDetectorEvent(
                                            DetectorSession.this.getDetectorType(),
                                            14,
                                            DetectorSession.this.mVoiceInteractionServiceUid);
                                }
                                DetectorSession.this.notifyOnDetectorRemoteException();
                            }
                        }
                    });
            if (detectorSession.getDetectorType() != 3) {
                HotwordMetricsLogger.writeDetectorEvent(detectorSession.getDetectorType(), 4, i);
            }
        } catch (RemoteException e) {
            Slog.w("DetectorSession", "Failed to updateState for HotwordDetectionService", e);
            if (detectorSession.getDetectorType() != 3) {
                HotwordMetricsLogger.writeDetectorEvent(detectorSession.getDetectorType(), 19, i);
            }
        }
        return androidFuture2.orTimeout(30000L, TimeUnit.MILLISECONDS);
    }
}

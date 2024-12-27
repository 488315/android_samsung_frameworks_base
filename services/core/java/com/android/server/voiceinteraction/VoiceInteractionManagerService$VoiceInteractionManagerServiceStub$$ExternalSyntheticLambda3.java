package com.android.server.voiceinteraction;

import android.os.IBinder;
import android.os.RemoteException;
import android.service.voice.VisibleActivityInfo;
import android.util.Slog;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */
class VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda3
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VoiceInteractionManagerService.VoiceInteractionManagerServiceStub
            f$0;
    public final /* synthetic */ IBinder f$1;

    public /* synthetic */
    VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda3(
            int i,
            IBinder iBinder,
            VoiceInteractionManagerService.VoiceInteractionManagerServiceStub
                    voiceInteractionManagerServiceStub) {
        this.$r8$classId = i;
        this.f$0 = voiceInteractionManagerServiceStub;
        this.f$1 = iBinder;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                VoiceInteractionManagerService.VoiceInteractionManagerServiceStub
                        voiceInteractionManagerServiceStub = this.f$0;
                IBinder iBinder = this.f$1;
                VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl =
                        voiceInteractionManagerServiceStub.mImpl;
                voiceInteractionManagerServiceImpl.getClass();
                Slog.v("VoiceInteractionServiceManager", "destroyDetectorLocked");
                if (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection != null) {
                    HotwordDetectionConnection hotwordDetectionConnection =
                            voiceInteractionManagerServiceImpl.mHotwordDetectionConnection;
                    DetectorSession detectorSessionByTokenLocked =
                            hotwordDetectionConnection.getDetectorSessionByTokenLocked(iBinder);
                    if (detectorSessionByTokenLocked != null) {
                        detectorSessionByTokenLocked.destroyLocked();
                        int indexOfValue =
                                hotwordDetectionConnection.mDetectorSessions.indexOfValue(
                                        detectorSessionByTokenLocked);
                        if (indexOfValue >= 0
                                && indexOfValue
                                        <= hotwordDetectionConnection.mDetectorSessions.size()
                                                - 1) {
                            hotwordDetectionConnection.mDetectorSessions.removeAt(indexOfValue);
                            if (detectorSessionByTokenLocked
                                    instanceof VisualQueryDetectorSession) {
                                HotwordDetectionConnection.ServiceConnection serviceConnection =
                                        hotwordDetectionConnection
                                                .mRemoteVisualQueryDetectionService;
                                if (serviceConnection != null) {
                                    serviceConnection.unbind();
                                    hotwordDetectionConnection.mRemoteVisualQueryDetectionService =
                                            null;
                                }
                                hotwordDetectionConnection
                                        .resetDetectionProcessIdentityIfEmptyLocked();
                            }
                            if (hotwordDetectionConnection.mDetectorSessions.size() == 0
                                    || (hotwordDetectionConnection.mDetectorSessions.size() == 1
                                            && (hotwordDetectionConnection.mDetectorSessions.get(0)
                                                    instanceof VisualQueryDetectorSession))) {
                                HotwordDetectionConnection.ServiceConnection serviceConnection2 =
                                        hotwordDetectionConnection.mRemoteHotwordDetectionService;
                                if (serviceConnection2 != null) {
                                    serviceConnection2.unbind();
                                    hotwordDetectionConnection.mRemoteHotwordDetectionService =
                                            null;
                                }
                                hotwordDetectionConnection
                                        .resetDetectionProcessIdentityIfEmptyLocked();
                                break;
                            }
                        }
                    }
                } else {
                    Slog.w(
                            "VoiceInteractionServiceManager",
                            "destroy detector callback, but no hotword detection connection");
                    break;
                }
                break;
            default:
                VoiceInteractionManagerService.VoiceInteractionManagerServiceStub
                        voiceInteractionManagerServiceStub2 = this.f$0;
                final IBinder iBinder2 = this.f$1;
                final VoiceInteractionSessionConnection voiceInteractionSessionConnection =
                        voiceInteractionManagerServiceStub2.mImpl.mActiveSession;
                if (voiceInteractionSessionConnection != null
                        && voiceInteractionSessionConnection.mShown
                        && voiceInteractionSessionConnection.mListeningVisibleActivity) {
                    voiceInteractionSessionConnection.mScheduledExecutorService.execute(
                            new Runnable() { // from class:
                                             // com.android.server.voiceinteraction.VoiceInteractionSessionConnection$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    VoiceInteractionSessionConnection
                                            voiceInteractionSessionConnection2 =
                                                    VoiceInteractionSessionConnection.this;
                                    IBinder iBinder3 = iBinder2;
                                    synchronized (voiceInteractionSessionConnection2.mLock) {
                                        try {
                                            if (voiceInteractionSessionConnection2
                                                    .mListeningVisibleActivity) {
                                                if (voiceInteractionSessionConnection2.mShown
                                                        && !voiceInteractionSessionConnection2
                                                                .mCanceled
                                                        && voiceInteractionSessionConnection2
                                                                        .mSession
                                                                != null) {
                                                    VisibleActivityInfo visibleActivityInfo =
                                                            (VisibleActivityInfo)
                                                                    voiceInteractionSessionConnection2
                                                                            .mVisibleActivityInfoForToken
                                                                            .remove(iBinder3);
                                                    if (visibleActivityInfo != null) {
                                                        try {
                                                            voiceInteractionSessionConnection2
                                                                    .mSession
                                                                    .notifyVisibleActivityInfoChanged(
                                                                            visibleActivityInfo, 2);
                                                        } catch (RemoteException unused) {
                                                        }
                                                    }
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                }
                            });
                    break;
                }
                break;
        }
    }
}

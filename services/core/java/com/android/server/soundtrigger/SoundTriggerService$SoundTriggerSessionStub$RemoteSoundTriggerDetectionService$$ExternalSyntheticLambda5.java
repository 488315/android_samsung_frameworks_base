package com.android.server.soundtrigger;

import android.util.Slog;

import com.android.server.utils.EventLogger;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */
class SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda5
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService
                remoteSoundTriggerDetectionService =
                        (SoundTriggerService.SoundTriggerSessionStub
                                        .RemoteSoundTriggerDetectionService)
                                obj;
        synchronized (remoteSoundTriggerDetectionService.mRemoteServiceLock) {
            try {
                if (remoteSoundTriggerDetectionService.mIsDestroyed) {
                    return;
                }
                if (remoteSoundTriggerDetectionService.mService != null) {
                    int size = remoteSoundTriggerDetectionService.mRunningOpIds.size();
                    for (int i = 0; i < size; i++) {
                        try {
                            remoteSoundTriggerDetectionService.mService.onStopOperation(
                                    remoteSoundTriggerDetectionService.mPuuid,
                                    ((Integer)
                                                    remoteSoundTriggerDetectionService.mRunningOpIds
                                                            .valueAt(i))
                                            .intValue());
                        } catch (Exception e) {
                            Slog.e(
                                    "SoundTriggerService",
                                    remoteSoundTriggerDetectionService.mPuuid
                                            + ": Could not stop operation "
                                            + remoteSoundTriggerDetectionService.mRunningOpIds
                                                    .valueAt(i),
                                    e);
                            SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(
                                    new EventLogger.StringEvent(
                                            remoteSoundTriggerDetectionService.mPuuid
                                                    + ": Could not stop operation "
                                                    + remoteSoundTriggerDetectionService
                                                            .mRunningOpIds.valueAt(i)));
                        }
                    }
                    remoteSoundTriggerDetectionService.mRunningOpIds.clear();
                }
                remoteSoundTriggerDetectionService.disconnectLocked();
            } finally {
            }
        }
    }
}

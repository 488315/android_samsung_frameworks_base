package com.android.server.wm;

import android.os.IBinder;
import android.os.RemoteException;
import android.service.voice.IVoiceInteractionSession;

import com.android.internal.app.IVoiceInteractor;

import java.util.function.BiPredicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class Task$$ExternalSyntheticLambda29 implements BiPredicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ Task$$ExternalSyntheticLambda29(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.BiPredicate
    public final boolean test(Object obj, Object obj2) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        switch (this.$r8$classId) {
            case 0:
                IBinder iBinder = (IBinder) obj2;
                IVoiceInteractionSession iVoiceInteractionSession = activityRecord.voiceSession;
                if (iVoiceInteractionSession != null
                        && iVoiceInteractionSession.asBinder() == iBinder) {
                    activityRecord.voiceSession = null;
                    activityRecord.pendingVoiceInteractionStart = false;
                    try {
                        activityRecord.app.mThread.scheduleLocalVoiceInteractionStarted(
                                activityRecord.token, (IVoiceInteractor) null);
                    } catch (RemoteException unused) {
                    }
                    ActivityTaskManagerService activityTaskManagerService =
                            activityRecord.mAtmService;
                    if (activityTaskManagerService.mRunningVoice != null) {
                        activityTaskManagerService.mRunningVoice = null;
                        activityTaskManagerService.mVoiceWakeLock.release();
                        activityTaskManagerService.updateSleepIfNeededLocked();
                        break;
                    }
                }
                break;
            default:
                ActivityRecord activityRecord2 = (ActivityRecord) obj2;
                if (activityRecord.delayedResume
                        || activityRecord == activityRecord2
                        || !activityRecord.canBeTopRunning()) {}
                break;
        }
        return false;
    }
}

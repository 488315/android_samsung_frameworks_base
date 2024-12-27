package com.android.server.wm;

import android.util.TimeUtils;
import android.view.SurfaceControl;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.wm.TransitionController;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class Transition$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Transition$$ExternalSyntheticLambda3(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                TransitionController.Logger logger = (TransitionController.Logger) obj;
                if (!ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled[1]) {
                    logger.getClass();
                    return;
                }
                StringBuilder sb = new StringBuilder("Finish Transition (#");
                sb.append(logger.mSyncId);
                sb.append("): created at ");
                sb.append(TimeUtils.logTimeOfDay(logger.mCreateWallTimeMs));
                sb.append(" collect-started=");
                sb.append(TransitionController.Logger.toMsString(logger.mCollectTimeNs - logger.mCreateTimeNs));
                if (logger.mRequestTimeNs != 0) {
                    sb.append(" request-sent=");
                    sb.append(TransitionController.Logger.toMsString(logger.mRequestTimeNs - logger.mCreateTimeNs));
                }
                sb.append(" started=");
                sb.append(TransitionController.Logger.toMsString(logger.mStartTimeNs - logger.mCreateTimeNs));
                sb.append(" ready=");
                sb.append(TransitionController.Logger.toMsString(logger.mReadyTimeNs - logger.mCreateTimeNs));
                sb.append(" sent=");
                sb.append(TransitionController.Logger.toMsString(logger.mSendTimeNs - logger.mCreateTimeNs));
                sb.append(" finished=");
                sb.append(TransitionController.Logger.toMsString(logger.mFinishTimeNs - logger.mCreateTimeNs));
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, -5051723169912572741L, 0, "%s", String.valueOf(sb.toString()));
                return;
            default:
                Transition transition = (Transition) obj;
                WindowManagerGlobalLock windowManagerGlobalLock = transition.mController.mAtm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (transition.mState >= 2) {
                            SurfaceControl.Transaction transaction = transition.mStartTransaction;
                            if (transaction != null) {
                                transaction.apply();
                            }
                            SurfaceControl.Transaction transaction2 = transition.mFinishTransaction;
                            if (transaction2 != null) {
                                transaction2.apply();
                            }
                            transition.mController.finishTransition(transition);
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
        }
    }
}

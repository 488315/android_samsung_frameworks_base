package com.android.server.appop;

import android.os.IBinder;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AttributedOp$$ExternalSyntheticLambda0 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        AttributedOp attributedOp = (AttributedOp) obj;
        IBinder iBinder = (IBinder) obj2;
        boolean z = AppOpsService.DEBUG;
        synchronized (attributedOp.mAppOpsService) {
            try {
                if (attributedOp.isPaused() || attributedOp.isRunning()) {
                    AttributedOp.InProgressStartOpEvent inProgressStartOpEvent =
                            (AttributedOp.InProgressStartOpEvent)
                                    (attributedOp.isPaused()
                                                    ? attributedOp.mPausedInProgressEvents
                                                    : attributedOp.mInProgressEvents)
                                            .get(iBinder);
                    if (inProgressStartOpEvent != null) {
                        inProgressStartOpEvent.mNumUnfinishedStarts = 1;
                    }
                    attributedOp.finishOrPause(iBinder, false, false);
                }
            } finally {
            }
        }
    }
}

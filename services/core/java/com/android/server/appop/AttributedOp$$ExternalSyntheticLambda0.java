package com.android.server.appop;

import android.os.IBinder;

import java.util.function.BiConsumer;

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

package com.android.systemui.statusbar.notification.row.wrapper;

import com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationTemplateViewWrapper$ActionPendingIntentCancellationHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationTemplateViewWrapper.ActionPendingIntentCancellationHandler f$0;

    public /* synthetic */ NotificationTemplateViewWrapper$ActionPendingIntentCancellationHandler$$ExternalSyntheticLambda0(NotificationTemplateViewWrapper.ActionPendingIntentCancellationHandler actionPendingIntentCancellationHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = actionPendingIntentCancellationHandler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        NotificationTemplateViewWrapper.ActionPendingIntentCancellationHandler actionPendingIntentCancellationHandler = this.f$0;
        switch (i) {
            case 0:
                actionPendingIntentCancellationHandler.mPendingIntent.registerCancelListener(actionPendingIntentCancellationHandler.mCancelListener);
                break;
            case 1:
                actionPendingIntentCancellationHandler.mPendingIntent.unregisterCancelListener(actionPendingIntentCancellationHandler.mCancelListener);
                break;
            default:
                actionPendingIntentCancellationHandler.mPendingIntent.unregisterCancelListener(actionPendingIntentCancellationHandler.mCancelListener);
                break;
        }
    }
}

package com.android.systemui.globalactions;

import android.os.SystemClock;
import com.android.systemui.globalactions.GlobalActionsDialogLite;

/* loaded from: classes2.dex */
public final /* synthetic */ class GlobalActionsDialogLite$StandbyAction$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GlobalActionsDialogLite.StandbyAction f$0;

    public /* synthetic */ GlobalActionsDialogLite$StandbyAction$$ExternalSyntheticLambda0(GlobalActionsDialogLite.StandbyAction standbyAction, int i) {
        this.$r8$classId = i;
        this.f$0 = standbyAction;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        GlobalActionsDialogLite.StandbyAction standbyAction = this.f$0;
        switch (i) {
            case 0:
                GlobalActionsDialogLite globalActionsDialogLite = standbyAction.this$0;
                globalActionsDialogLite.mUiEventLogger.log(GlobalActionsDialogLite.GlobalActionsEvent.GA_STANDBY_PRESS);
                globalActionsDialogLite.mBackgroundExecutor.execute(new GlobalActionsDialogLite$StandbyAction$$ExternalSyntheticLambda0(standbyAction, 1));
                break;
            default:
                standbyAction.this$0.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 4, 0);
                break;
        }
    }
}

package com.android.wm.shell.desktopmode;

import android.os.IBinder;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopTasksLimiter;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopTasksController$$ExternalSyntheticLambda6 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IBinder f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Enum f$3;

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda6(IBinder iBinder, int i, int i2, Enum r4, int i3) {
        this.$r8$classId = i3;
        this.f$0 = iBinder;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = r4;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Enum r0 = this.f$3;
        switch (this.$r8$classId) {
            case 0:
                IBinder iBinder = this.f$0;
                DesktopTasksLimiter desktopTasksLimiter = (DesktopTasksLimiter) obj;
                DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                desktopTasksLimiter.getClass();
                desktopTasksLimiter.minimizeTransitionObserver.pendingUnminimizeTransitionTokensAndTasks.put(iBinder, new DesktopTasksLimiter.TaskDetails(this.f$1, this.f$2, null, null, null, (DesktopModeEventLogger.Companion.UnminimizeReason) r0, 28, null));
                break;
            default:
                IBinder iBinder2 = this.f$0;
                DesktopTasksLimiter desktopTasksLimiter2 = (DesktopTasksLimiter) obj;
                DesktopTasksController.Companion companion2 = DesktopTasksController.Companion;
                iBinder2.getClass();
                desktopTasksLimiter2.getClass();
                desktopTasksLimiter2.minimizeTransitionObserver.pendingTransitionTokensAndTasks.put(iBinder2, new DesktopTasksLimiter.TaskDetails(this.f$1, this.f$2, null, null, (DesktopModeEventLogger.Companion.MinimizeReason) r0, null, 36, null));
                break;
        }
        return Unit.INSTANCE;
    }
}

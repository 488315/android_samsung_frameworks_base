package com.android.wm.shell.desktopmode;

import android.os.IBinder;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.multidesks.DeskTransition;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopTasksController$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda3(IBinder iBinder, int i, List list) {
        this.f$0 = iBinder;
        this.f$2 = i;
        this.f$1 = list;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        int i = this.f$2;
        Object obj2 = this.f$1;
        Object obj3 = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                ((DesktopTasksController) obj3).desksTransitionObserver.addPendingTransition(new DeskTransition.DeactivateDesk((IBinder) obj, ((Integer) obj2).intValue(), i));
                break;
            default:
                IBinder iBinder = (IBinder) obj3;
                DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                iBinder.getClass();
                ((DesktopTasksLimiter) obj).addPendingMinimizeChanges(iBinder, i, (List) obj2, DesktopModeEventLogger.Companion.MinimizeReason.TASK_LIMIT);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda3(DesktopTasksController desktopTasksController, Integer num, int i) {
        this.f$0 = desktopTasksController;
        this.f$1 = num;
        this.f$2 = i;
    }
}

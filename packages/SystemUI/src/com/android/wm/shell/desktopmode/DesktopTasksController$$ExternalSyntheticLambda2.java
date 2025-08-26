package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.DesktopTasksController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopTasksController$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopTasksController f$0;

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda2(DesktopTasksController desktopTasksController, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopTasksController;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        DesktopTasksController desktopTasksController = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                ((DesktopTasksLimiter) obj).desktopTasksController = desktopTasksController;
                return Unit.INSTANCE;
            default:
                return desktopTasksController.shellTaskOrganizer.getRunningTaskInfo(((Integer) obj).intValue());
        }
    }
}

package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.DesktopTasksController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopTasksController$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopTasksController f$0;

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda1(DesktopTasksController desktopTasksController, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopTasksController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        DesktopTasksController desktopTasksController = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                desktopTasksController.releaseVisualIndicator();
                break;
            default:
                DesktopTasksController.Companion companion2 = DesktopTasksController.Companion;
                desktopTasksController.releaseVisualIndicator();
                break;
        }
        return Unit.INSTANCE;
    }
}

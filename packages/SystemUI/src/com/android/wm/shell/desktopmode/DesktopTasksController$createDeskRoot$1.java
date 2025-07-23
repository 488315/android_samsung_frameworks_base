package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.multidesks.DesksOrganizer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopTasksController$createDeskRoot$1 implements DesksOrganizer.OnCreateCallback {
    public final /* synthetic */ int $displayId;
    public final /* synthetic */ Function1 $onResult;
    public final /* synthetic */ int $userId;
    public final /* synthetic */ DesktopTasksController this$0;

    public DesktopTasksController$createDeskRoot$1(DesktopTasksController desktopTasksController, int i, int i2, Function1 function1) {
        this.this$0 = desktopTasksController;
        this.$displayId = i;
        this.$userId = i2;
        this.$onResult = function1;
    }

    public final void onCreated(int i) {
        Object[] objArr = {Integer.valueOf(i), Integer.valueOf(this.$displayId), Integer.valueOf(this.$userId)};
        DesktopTasksController.Companion companion = DesktopTasksController.Companion;
        this.this$0.getClass();
        DesktopTasksController.logD$1("createDesk obtained deskId=%d for displayId=%d and userId=%d", objArr);
        this.$onResult.mo779invoke(Integer.valueOf(i));
    }
}

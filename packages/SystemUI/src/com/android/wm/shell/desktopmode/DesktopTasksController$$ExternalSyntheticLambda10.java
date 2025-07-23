package com.android.wm.shell.desktopmode;

import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopTasksController$$ExternalSyntheticLambda10 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda10(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj).intValue();
                DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                break;
            default:
                DesktopTasksController.Companion companion2 = DesktopTasksController.Companion;
                ((BubbleController) obj).getClass();
                break;
        }
        return Unit.INSTANCE;
    }
}

package com.android.wm.shell.desktopmode;

import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopTasksController$$ExternalSyntheticLambda12 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda12(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
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

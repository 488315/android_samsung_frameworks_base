package com.android.wm.shell.desktopmode;

import android.os.IBinder;
import com.android.wm.shell.desktopmode.multidesks.DeskTransition;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopTasksController$$ExternalSyntheticLambda5 implements Function1 {
    public final /* synthetic */ DesktopTasksController f$0;
    public final /* synthetic */ Integer f$1;

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda5(DesktopTasksController desktopTasksController, Integer num) {
        this.f$0 = desktopTasksController;
        this.f$1 = num;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        this.f$0.desksTransitionObserver.addPendingTransition(new DeskTransition.DeactivateDesk((IBinder) obj, this.f$1.intValue()));
        return Unit.INSTANCE;
    }
}

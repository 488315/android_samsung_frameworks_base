package com.android.wm.shell.freeform;

import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FreeformTaskTransitionStarterInitializer {
    public final FreeformTaskTransitionStarter freeformTaskTransitionStarter;
    public final WindowDecorViewModel windowDecorViewModel;

    public FreeformTaskTransitionStarterInitializer(ShellInit shellInit, WindowDecorViewModel windowDecorViewModel, FreeformTaskTransitionStarter freeformTaskTransitionStarter) {
        this.windowDecorViewModel = windowDecorViewModel;
        this.freeformTaskTransitionStarter = freeformTaskTransitionStarter;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformTaskTransitionStarterInitializer.1
            @Override // java.lang.Runnable
            public final void run() {
                FreeformTaskTransitionStarterInitializer freeformTaskTransitionStarterInitializer = FreeformTaskTransitionStarterInitializer.this;
                freeformTaskTransitionStarterInitializer.windowDecorViewModel.setFreeformTaskTransitionStarter(freeformTaskTransitionStarterInitializer.freeformTaskTransitionStarter);
            }
        }, this);
    }
}

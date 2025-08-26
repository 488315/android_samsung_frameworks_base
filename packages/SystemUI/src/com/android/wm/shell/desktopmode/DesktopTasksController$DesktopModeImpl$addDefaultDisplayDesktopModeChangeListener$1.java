package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class DesktopTasksController$DesktopModeImpl$addDefaultDisplayDesktopModeChangeListener$1 implements Runnable {
    public final /* synthetic */ Executor $callbackExecutor;
    public final /* synthetic */ DesktopTasksController.DefaultDisplayDesktopModeChangeListener $listener;
    public final /* synthetic */ DesktopTasksController this$0;

    public DesktopTasksController$DesktopModeImpl$addDefaultDisplayDesktopModeChangeListener$1(DesktopTasksController desktopTasksController, DesktopTasksController.DefaultDisplayDesktopModeChangeListener defaultDisplayDesktopModeChangeListener, Executor executor) {
        this.this$0 = desktopTasksController;
        this.$listener = defaultDisplayDesktopModeChangeListener;
        this.$callbackExecutor = executor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DesktopTasksController desktopTasksController = this.this$0;
        final DesktopTasksController.DefaultDisplayDesktopModeChangeListener defaultDisplayDesktopModeChangeListener = this.$listener;
        Executor executor = this.$callbackExecutor;
        ((ArrayList) desktopTasksController.defaultDisplayDesktopModeChangeListeners).add(defaultDisplayDesktopModeChangeListener);
        ((ArrayList) desktopTasksController.defaultDisplayDesktopModeChangeListenerExecutors).add(executor);
        executor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$addDesktopModeChangeListener$1
            @Override // java.lang.Runnable
            public final void run() {
                DesktopTasksController.DefaultDisplayDesktopModeChangeListener defaultDisplayDesktopModeChangeListener2 = defaultDisplayDesktopModeChangeListener;
                DesktopStateImpl.Companion.getClass();
                defaultDisplayDesktopModeChangeListener2.onDefaultDisplayDesktopModeChanged(DesktopStateImpl.Companion.inDesktopWindowing(0));
            }
        });
    }
}

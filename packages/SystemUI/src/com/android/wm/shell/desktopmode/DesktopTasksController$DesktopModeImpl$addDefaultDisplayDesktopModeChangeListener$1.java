package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.DesktopTasksController;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        DesktopTasksController.DefaultDisplayDesktopModeChangeListener defaultDisplayDesktopModeChangeListener = this.$listener;
        Executor executor = this.$callbackExecutor;
        desktopTasksController.defaultDisplayDesktopModeChangeListener = defaultDisplayDesktopModeChangeListener;
        desktopTasksController.defaultDisplayDesktopModeChangeListenerExecutor = executor;
    }
}

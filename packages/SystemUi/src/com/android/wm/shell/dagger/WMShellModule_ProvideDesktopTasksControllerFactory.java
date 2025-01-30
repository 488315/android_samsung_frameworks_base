package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopModeTaskRepository;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler;
import com.android.wm.shell.desktopmode.ExitDesktopTaskTransitionHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvideDesktopTasksControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider desktopModeTaskRepositoryProvider;
    public final Provider displayControllerProvider;
    public final Provider enterDesktopTransitionHandlerProvider;
    public final Provider exitDesktopTransitionHandlerProvider;
    public final Provider mainExecutorProvider;
    public final Provider rootTaskDisplayAreaOrganizerProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider syncQueueProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideDesktopTasksControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellControllerProvider = provider3;
        this.displayControllerProvider = provider4;
        this.shellTaskOrganizerProvider = provider5;
        this.syncQueueProvider = provider6;
        this.rootTaskDisplayAreaOrganizerProvider = provider7;
        this.transitionsProvider = provider8;
        this.enterDesktopTransitionHandlerProvider = provider9;
        this.exitDesktopTransitionHandlerProvider = provider10;
        this.desktopModeTaskRepositoryProvider = provider11;
        this.mainExecutorProvider = provider12;
    }

    public static DesktopTasksController provideDesktopTasksController(Context context, ShellInit shellInit, ShellController shellController, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, Transitions transitions, EnterDesktopTaskTransitionHandler enterDesktopTaskTransitionHandler, ExitDesktopTaskTransitionHandler exitDesktopTaskTransitionHandler, DesktopModeTaskRepository desktopModeTaskRepository, ShellExecutor shellExecutor) {
        return new DesktopTasksController(context, shellInit, shellController, displayController, shellTaskOrganizer, syncTransactionQueue, rootTaskDisplayAreaOrganizer, transitions, enterDesktopTaskTransitionHandler, exitDesktopTaskTransitionHandler, desktopModeTaskRepository, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDesktopTasksController((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellController) this.shellControllerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (SyncTransactionQueue) this.syncQueueProvider.get(), (RootTaskDisplayAreaOrganizer) this.rootTaskDisplayAreaOrganizerProvider.get(), (Transitions) this.transitionsProvider.get(), (EnterDesktopTaskTransitionHandler) this.enterDesktopTransitionHandlerProvider.get(), (ExitDesktopTaskTransitionHandler) this.exitDesktopTransitionHandlerProvider.get(), (DesktopModeTaskRepository) this.desktopModeTaskRepositoryProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}

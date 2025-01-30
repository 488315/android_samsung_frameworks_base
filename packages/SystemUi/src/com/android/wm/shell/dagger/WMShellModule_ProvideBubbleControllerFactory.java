package com.android.wm.shell.dagger;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.os.Handler;
import android.os.UserManager;
import android.view.IWindowManager;
import android.view.WindowManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.WindowManagerShellWrapper;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleData;
import com.android.wm.shell.bubbles.BubbleDataRepository;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.taskview.TaskViewTransitions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvideBubbleControllerFactory implements Provider {
    public final Provider bgExecutorProvider;
    public final Provider contextProvider;
    public final Provider dataProvider;
    public final Provider displayControllerProvider;
    public final Provider dragAndDropControllerProvider;
    public final Provider floatingContentCoordinatorProvider;
    public final Provider launcherAppsProvider;
    public final Provider loggerProvider;
    public final Provider mainExecutorProvider;
    public final Provider mainHandlerProvider;
    public final Provider oneHandedOptionalProvider;
    public final Provider organizerProvider;
    public final Provider positionerProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;
    public final Provider statusBarServiceProvider;
    public final Provider syncQueueProvider;
    public final Provider taskStackListenerProvider;
    public final Provider taskViewTransitionsProvider;
    public final Provider userManagerProvider;
    public final Provider windowManagerProvider;
    public final Provider windowManagerShellWrapperProvider;
    public final Provider wmServiceProvider;

    public WMShellModule_ProvideBubbleControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellCommandHandlerProvider = provider3;
        this.shellControllerProvider = provider4;
        this.dataProvider = provider5;
        this.floatingContentCoordinatorProvider = provider6;
        this.statusBarServiceProvider = provider7;
        this.windowManagerProvider = provider8;
        this.windowManagerShellWrapperProvider = provider9;
        this.userManagerProvider = provider10;
        this.launcherAppsProvider = provider11;
        this.taskStackListenerProvider = provider12;
        this.loggerProvider = provider13;
        this.organizerProvider = provider14;
        this.positionerProvider = provider15;
        this.displayControllerProvider = provider16;
        this.oneHandedOptionalProvider = provider17;
        this.dragAndDropControllerProvider = provider18;
        this.mainExecutorProvider = provider19;
        this.mainHandlerProvider = provider20;
        this.bgExecutorProvider = provider21;
        this.taskViewTransitionsProvider = provider22;
        this.syncQueueProvider = provider23;
        this.wmServiceProvider = provider24;
    }

    public static BubbleController provideBubbleController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, BubbleData bubbleData, FloatingContentCoordinator floatingContentCoordinator, IStatusBarService iStatusBarService, WindowManager windowManager, WindowManagerShellWrapper windowManagerShellWrapper, UserManager userManager, LauncherApps launcherApps, TaskStackListenerImpl taskStackListenerImpl, BubbleLogger bubbleLogger, ShellTaskOrganizer shellTaskOrganizer, BubblePositioner bubblePositioner, DisplayController displayController, Optional optional, Optional optional2, ShellExecutor shellExecutor, Handler handler, ShellExecutor shellExecutor2, TaskViewTransitions taskViewTransitions, SyncTransactionQueue syncTransactionQueue, IWindowManager iWindowManager) {
        return new BubbleController(context, shellInit, shellCommandHandler, shellController, bubbleData, null, floatingContentCoordinator, new BubbleDataRepository(context, launcherApps, shellExecutor), iStatusBarService, windowManager, windowManagerShellWrapper, userManager, launcherApps, bubbleLogger, taskStackListenerImpl, shellTaskOrganizer, bubblePositioner, displayController, optional, optional2, shellExecutor, handler, shellExecutor2, taskViewTransitions, syncTransactionQueue, iWindowManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideBubbleController((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (ShellController) this.shellControllerProvider.get(), (BubbleData) this.dataProvider.get(), (FloatingContentCoordinator) this.floatingContentCoordinatorProvider.get(), (IStatusBarService) this.statusBarServiceProvider.get(), (WindowManager) this.windowManagerProvider.get(), (WindowManagerShellWrapper) this.windowManagerShellWrapperProvider.get(), (UserManager) this.userManagerProvider.get(), (LauncherApps) this.launcherAppsProvider.get(), (TaskStackListenerImpl) this.taskStackListenerProvider.get(), (BubbleLogger) this.loggerProvider.get(), (ShellTaskOrganizer) this.organizerProvider.get(), (BubblePositioner) this.positionerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (Optional) this.oneHandedOptionalProvider.get(), (Optional) this.dragAndDropControllerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (Handler) this.mainHandlerProvider.get(), (ShellExecutor) this.bgExecutorProvider.get(), (TaskViewTransitions) this.taskViewTransitionsProvider.get(), (SyncTransactionQueue) this.syncQueueProvider.get(), (IWindowManager) this.wmServiceProvider.get());
    }
}

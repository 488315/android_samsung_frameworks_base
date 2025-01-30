package com.android.p038wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import android.view.WindowManager;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.TaskStackListenerImpl;
import com.android.p038wm.shell.onehanded.BackgroundWindowManager;
import com.android.p038wm.shell.onehanded.OneHandedAccessibilityUtil;
import com.android.p038wm.shell.onehanded.OneHandedAnimationController;
import com.android.p038wm.shell.onehanded.OneHandedController;
import com.android.p038wm.shell.onehanded.OneHandedDisplayAreaOrganizer;
import com.android.p038wm.shell.onehanded.OneHandedSettingsUtil;
import com.android.p038wm.shell.onehanded.OneHandedState;
import com.android.p038wm.shell.onehanded.OneHandedTimeoutHandler;
import com.android.p038wm.shell.onehanded.OneHandedTouchHandler;
import com.android.p038wm.shell.onehanded.OneHandedTutorialHandler;
import com.android.p038wm.shell.onehanded.OneHandedUiEventLogger;
import com.android.p038wm.shell.sysui.ShellCommandHandler;
import com.android.p038wm.shell.sysui.ShellController;
import com.android.p038wm.shell.sysui.ShellInit;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvideOneHandedControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider displayLayoutProvider;
    public final Provider jankMonitorProvider;
    public final Provider mainExecutorProvider;
    public final Provider mainHandlerProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;
    public final Provider taskStackListenerProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider windowManagerProvider;

    public WMShellModule_ProvideOneHandedControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellCommandHandlerProvider = provider3;
        this.shellControllerProvider = provider4;
        this.windowManagerProvider = provider5;
        this.displayControllerProvider = provider6;
        this.displayLayoutProvider = provider7;
        this.taskStackListenerProvider = provider8;
        this.uiEventLoggerProvider = provider9;
        this.jankMonitorProvider = provider10;
        this.mainExecutorProvider = provider11;
        this.mainHandlerProvider = provider12;
    }

    public static OneHandedController provideOneHandedController(Context context, Handler handler, WindowManager windowManager, InteractionJankMonitor interactionJankMonitor, UiEventLogger uiEventLogger, DisplayController displayController, DisplayLayout displayLayout, ShellExecutor shellExecutor, TaskStackListenerImpl taskStackListenerImpl, ShellCommandHandler shellCommandHandler, ShellController shellController, ShellInit shellInit) {
        OneHandedSettingsUtil oneHandedSettingsUtil = new OneHandedSettingsUtil();
        OneHandedAccessibilityUtil oneHandedAccessibilityUtil = new OneHandedAccessibilityUtil(context);
        OneHandedTimeoutHandler oneHandedTimeoutHandler = new OneHandedTimeoutHandler(shellExecutor);
        OneHandedState oneHandedState = new OneHandedState();
        OneHandedTutorialHandler oneHandedTutorialHandler = new OneHandedTutorialHandler(context, oneHandedSettingsUtil, windowManager, new BackgroundWindowManager(context));
        OneHandedAnimationController oneHandedAnimationController = new OneHandedAnimationController(context);
        return new OneHandedController(context, shellInit, shellCommandHandler, shellController, displayController, new OneHandedDisplayAreaOrganizer(context, displayLayout, oneHandedSettingsUtil, oneHandedAnimationController, oneHandedTutorialHandler, interactionJankMonitor, shellExecutor), new OneHandedTouchHandler(oneHandedTimeoutHandler, shellExecutor), oneHandedTutorialHandler, oneHandedSettingsUtil, oneHandedAccessibilityUtil, oneHandedTimeoutHandler, oneHandedState, new OneHandedUiEventLogger(uiEventLogger), taskStackListenerImpl, shellExecutor, handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        Context context = (Context) this.contextProvider.get();
        ShellInit shellInit = (ShellInit) this.shellInitProvider.get();
        ShellCommandHandler shellCommandHandler = (ShellCommandHandler) this.shellCommandHandlerProvider.get();
        ShellController shellController = (ShellController) this.shellControllerProvider.get();
        WindowManager windowManager = (WindowManager) this.windowManagerProvider.get();
        DisplayController displayController = (DisplayController) this.displayControllerProvider.get();
        DisplayLayout displayLayout = (DisplayLayout) this.displayLayoutProvider.get();
        TaskStackListenerImpl taskStackListenerImpl = (TaskStackListenerImpl) this.taskStackListenerProvider.get();
        UiEventLogger uiEventLogger = (UiEventLogger) this.uiEventLoggerProvider.get();
        return provideOneHandedController(context, (Handler) this.mainHandlerProvider.get(), windowManager, (InteractionJankMonitor) this.jankMonitorProvider.get(), uiEventLogger, displayController, displayLayout, (ShellExecutor) this.mainExecutorProvider.get(), taskStackListenerImpl, shellCommandHandler, shellController, shellInit);
    }
}

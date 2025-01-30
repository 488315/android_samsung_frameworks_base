package com.android.wm.shell.dagger;

import android.R;
import android.app.ActivityTaskManager;
import android.content.Context;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideRecentTasksControllerFactory implements Provider {
    public final Provider activityTaskManagerProvider;
    public final Provider contextProvider;
    public final Provider desktopModeTaskRepositoryProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;
    public final Provider taskStackListenerProvider;

    public WMShellBaseModule_ProvideRecentTasksControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellControllerProvider = provider3;
        this.shellCommandHandlerProvider = provider4;
        this.taskStackListenerProvider = provider5;
        this.activityTaskManagerProvider = provider6;
        this.desktopModeTaskRepositoryProvider = provider7;
        this.mainExecutorProvider = provider8;
    }

    public static Optional provideRecentTasksController(Context context, ShellInit shellInit, ShellController shellController, ShellCommandHandler shellCommandHandler, TaskStackListenerImpl taskStackListenerImpl, ActivityTaskManager activityTaskManager, Optional optional, ShellExecutor shellExecutor) {
        Optional ofNullable = Optional.ofNullable(!context.getResources().getBoolean(R.bool.config_enable_iwlan_handover_policy) ? null : new RecentTasksController(context, shellInit, shellController, shellCommandHandler, taskStackListenerImpl, activityTaskManager, optional, shellExecutor));
        Preconditions.checkNotNullFromProvides(ofNullable);
        return ofNullable;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideRecentTasksController((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellController) this.shellControllerProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (TaskStackListenerImpl) this.taskStackListenerProvider.get(), (ActivityTaskManager) this.activityTaskManagerProvider.get(), (Optional) this.desktopModeTaskRepositoryProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}

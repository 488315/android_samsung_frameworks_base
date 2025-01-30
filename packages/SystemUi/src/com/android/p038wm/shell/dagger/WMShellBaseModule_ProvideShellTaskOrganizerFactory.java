package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.TaskStackListenerImpl;
import com.android.p038wm.shell.compatui.CompatUIController;
import com.android.p038wm.shell.sysui.ShellCommandHandler;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.systemui.R;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideShellTaskOrganizerFactory implements Provider {
    public final Provider compatUIProvider;
    public final Provider contextProvider;
    public final Provider mainExecutorProvider;
    public final Provider recentTasksOptionalProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellInitProvider;
    public final Provider taskStackListenerProvider;
    public final Provider unfoldAnimationControllerProvider;

    public WMShellBaseModule_ProvideShellTaskOrganizerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellCommandHandlerProvider = provider3;
        this.compatUIProvider = provider4;
        this.unfoldAnimationControllerProvider = provider5;
        this.recentTasksOptionalProvider = provider6;
        this.mainExecutorProvider = provider7;
        this.taskStackListenerProvider = provider8;
    }

    public static ShellTaskOrganizer provideShellTaskOrganizer(ShellInit shellInit, ShellCommandHandler shellCommandHandler, CompatUIController compatUIController, Optional optional, Optional optional2, ShellExecutor shellExecutor, TaskStackListenerImpl taskStackListenerImpl, Context context) {
        return new ShellTaskOrganizer(!context.getResources().getBoolean(R.bool.config_registerShellTaskOrganizerOnInit) ? new ShellInit(shellExecutor) : shellInit, shellCommandHandler, compatUIController, optional, optional2, shellExecutor, taskStackListenerImpl, context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShellTaskOrganizer((ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (CompatUIController) this.compatUIProvider.get(), (Optional) this.unfoldAnimationControllerProvider.get(), (Optional) this.recentTasksOptionalProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (TaskStackListenerImpl) this.taskStackListenerProvider.get(), (Context) this.contextProvider.get());
    }
}

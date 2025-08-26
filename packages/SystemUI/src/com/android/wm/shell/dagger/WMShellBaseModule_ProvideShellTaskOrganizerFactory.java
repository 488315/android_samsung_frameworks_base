package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.systemui.R;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.compatui.api.CompatUIHandler;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.FocusTransitionObserver;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideShellTaskOrganizerFactory implements Provider {
    public final Provider compatUIProvider;
    public final Provider contextProvider;
    public final Provider focusTransitionObserverProvider;
    public final Provider mainExecutorProvider;
    public final Provider recentTasksOptionalProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellInitProvider;
    public final Provider taskStackListenerProvider;
    public final Provider unfoldAnimationControllerProvider;

    public WMShellBaseModule_ProvideShellTaskOrganizerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellCommandHandlerProvider = provider3;
        this.compatUIProvider = provider4;
        this.unfoldAnimationControllerProvider = provider5;
        this.recentTasksOptionalProvider = provider6;
        this.mainExecutorProvider = provider7;
        this.taskStackListenerProvider = provider8;
        this.focusTransitionObserverProvider = provider9;
    }

    public static ShellTaskOrganizer provideShellTaskOrganizer(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, Optional optional, Optional optional2, Optional optional3, ShellExecutor shellExecutor, TaskStackListenerImpl taskStackListenerImpl, FocusTransitionObserver focusTransitionObserver) {
        ShellExecutor shellExecutor2;
        if (context.getResources().getBoolean(R.bool.config_registerShellTaskOrganizerOnInit)) {
            shellExecutor2 = shellExecutor;
        } else {
            shellExecutor2 = shellExecutor;
            shellInit = new ShellInit(shellExecutor2);
        }
        return new ShellTaskOrganizer(shellInit, shellCommandHandler, (CompatUIHandler) optional.orElse(null), optional2, optional3, shellExecutor2, taskStackListenerImpl, context, focusTransitionObserver);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShellTaskOrganizer((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (Optional) this.compatUIProvider.get(), (Optional) this.unfoldAnimationControllerProvider.get(), (Optional) this.recentTasksOptionalProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (TaskStackListenerImpl) this.taskStackListenerProvider.get(), (FocusTransitionObserver) this.focusTransitionObserverProvider.get());
    }
}

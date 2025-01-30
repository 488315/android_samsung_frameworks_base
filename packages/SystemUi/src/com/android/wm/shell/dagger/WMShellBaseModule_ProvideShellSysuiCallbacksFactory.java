package com.android.wm.shell.dagger;

import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInterface;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideShellSysuiCallbacksFactory implements Provider {
    public final Provider createTriggerProvider;
    public final Provider shellControllerProvider;

    public WMShellBaseModule_ProvideShellSysuiCallbacksFactory(Provider provider, Provider provider2) {
        this.createTriggerProvider = provider;
        this.shellControllerProvider = provider2;
    }

    public static ShellInterface provideShellSysuiCallbacks(ShellController shellController) {
        ShellController.ShellInterfaceImpl shellInterfaceImpl = shellController.mImpl;
        Preconditions.checkNotNullFromProvides(shellInterfaceImpl);
        return shellInterfaceImpl;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        this.createTriggerProvider.get();
        ShellController.ShellInterfaceImpl shellInterfaceImpl = ((ShellController) this.shellControllerProvider.get()).mImpl;
        Preconditions.checkNotNullFromProvides(shellInterfaceImpl);
        return shellInterfaceImpl;
    }
}

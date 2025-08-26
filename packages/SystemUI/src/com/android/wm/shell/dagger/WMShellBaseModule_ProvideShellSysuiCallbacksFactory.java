package com.android.wm.shell.dagger;

import com.android.wm.shell.sysui.ShellController;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideShellSysuiCallbacksFactory implements Provider {
    public final Provider createTriggerProvider;
    public final Provider shellControllerProvider;

    public WMShellBaseModule_ProvideShellSysuiCallbacksFactory(Provider provider, Provider provider2) {
        this.createTriggerProvider = provider;
        this.shellControllerProvider = provider2;
    }

    public static ShellController.ShellInterfaceImpl provideShellSysuiCallbacks(ShellController shellController) {
        ShellController.ShellInterfaceImpl shellInterfaceImpl = shellController.mImpl;
        shellInterfaceImpl.getClass();
        return shellInterfaceImpl;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        this.createTriggerProvider.get();
        ShellController.ShellInterfaceImpl shellInterfaceImpl = ((ShellController) this.shellControllerProvider.get()).mImpl;
        shellInterfaceImpl.getClass();
        return shellInterfaceImpl;
    }
}

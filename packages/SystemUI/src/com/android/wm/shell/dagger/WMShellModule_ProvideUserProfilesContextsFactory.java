package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.UserProfileContexts;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideUserProfilesContextsFactory implements Provider {
    public final Provider contextProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;

    public WMShellModule_ProvideUserProfilesContextsFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.shellControllerProvider = provider2;
        this.shellInitProvider = provider3;
    }

    public static UserProfileContexts provideUserProfilesContexts(Context context, ShellController shellController, ShellInit shellInit) {
        return new UserProfileContexts(context, shellController, shellInit);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new UserProfileContexts((Context) this.contextProvider.get(), (ShellController) this.shellControllerProvider.get(), (ShellInit) this.shellInitProvider.get());
    }
}

package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.UserProfileContexts;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideWindowDecorTaskResourceLoaderFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;
    public final Provider userProfileContextsProvider;

    public WMShellModule_ProvideWindowDecorTaskResourceLoaderFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.displayControllerProvider = provider3;
        this.shellControllerProvider = provider4;
        this.shellCommandHandlerProvider = provider5;
        this.userProfileContextsProvider = provider6;
    }

    public static WindowDecorTaskResourceLoader provideWindowDecorTaskResourceLoader(Context context, ShellInit shellInit, DisplayController displayController, ShellController shellController, ShellCommandHandler shellCommandHandler, UserProfileContexts userProfileContexts) {
        return new WindowDecorTaskResourceLoader(context, shellInit, displayController, shellController, shellCommandHandler, userProfileContexts);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new WindowDecorTaskResourceLoader((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (DisplayController) this.displayControllerProvider.get(), (ShellController) this.shellControllerProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (UserProfileContexts) this.userProfileContextsProvider.get());
    }
}

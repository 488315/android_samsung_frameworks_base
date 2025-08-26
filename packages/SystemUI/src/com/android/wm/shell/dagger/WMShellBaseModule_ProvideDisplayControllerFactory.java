package com.android.wm.shell.dagger;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.IWindowManager;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideDisplayControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider desktopStateProvider;
    public final Provider displayManagerProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellInitProvider;
    public final Provider wmServiceProvider;

    public WMShellBaseModule_ProvideDisplayControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.wmServiceProvider = provider2;
        this.shellInitProvider = provider3;
        this.mainExecutorProvider = provider4;
        this.displayManagerProvider = provider5;
        this.desktopStateProvider = provider6;
    }

    public static DisplayController provideDisplayController(Context context, IWindowManager iWindowManager, ShellInit shellInit, ShellExecutor shellExecutor, DisplayManager displayManager, DesktopState desktopState) {
        return new DisplayController(context, iWindowManager, shellInit, shellExecutor, displayManager, desktopState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DisplayController((Context) this.contextProvider.get(), (IWindowManager) this.wmServiceProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (DisplayManager) this.displayManagerProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}

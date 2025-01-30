package com.android.p038wm.shell.dagger;

import android.view.IWindowManager;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.SystemWindows;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideSystemWindowsFactory implements Provider {
    public final Provider displayControllerProvider;
    public final Provider wmServiceProvider;

    public WMShellBaseModule_ProvideSystemWindowsFactory(Provider provider, Provider provider2) {
        this.displayControllerProvider = provider;
        this.wmServiceProvider = provider2;
    }

    public static SystemWindows provideSystemWindows(DisplayController displayController, IWindowManager iWindowManager) {
        return new SystemWindows(displayController, iWindowManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new SystemWindows((DisplayController) this.displayControllerProvider.get(), (IWindowManager) this.wmServiceProvider.get());
    }
}

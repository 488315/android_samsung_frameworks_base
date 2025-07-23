package com.android.wm.shell.dagger;

import android.view.IWindowManager;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.SystemWindows;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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

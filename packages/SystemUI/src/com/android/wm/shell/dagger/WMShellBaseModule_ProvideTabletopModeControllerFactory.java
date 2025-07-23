package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.DevicePostureController;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TabletopModeController;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideTabletopModeControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider postureControllerProvider;
    public final Provider shellInitProvider;

    public WMShellBaseModule_ProvideTabletopModeControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.postureControllerProvider = provider3;
        this.displayControllerProvider = provider4;
        this.mainExecutorProvider = provider5;
    }

    public static TabletopModeController provideTabletopModeController(Context context, ShellInit shellInit, DevicePostureController devicePostureController, DisplayController displayController, ShellExecutor shellExecutor) {
        return new TabletopModeController(context, shellInit, devicePostureController, displayController, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new TabletopModeController((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (DevicePostureController) this.postureControllerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}

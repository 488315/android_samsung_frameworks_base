package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.p038wm.shell.common.DevicePostureController;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.sysui.ShellInit;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideDevicePostureControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellInitProvider;

    public WMShellBaseModule_ProvideDevicePostureControllerFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.mainExecutorProvider = provider3;
    }

    public static DevicePostureController provideDevicePostureController(Context context, ShellInit shellInit, ShellExecutor shellExecutor) {
        return new DevicePostureController(context, shellInit, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DevicePostureController((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}

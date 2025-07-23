package com.android.wm.shell.dagger;

import android.content.Context;
import android.window.DesktopModeFlags;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.windowdecor.common.viewhost.DefaultWindowDecorViewHostSupplier;
import com.android.wm.shell.windowdecor.common.viewhost.PooledWindowDecorViewHostSupplier;
import com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideWindowDecorViewHostSupplierFactory implements Provider {
    public final Provider contextProvider;
    public final Provider desktopConfigProvider;
    public final Provider desktopStateProvider;
    public final Provider mainScopeProvider;
    public final Provider shellInitProvider;

    public WMShellModule_ProvideWindowDecorViewHostSupplierFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.mainScopeProvider = provider2;
        this.shellInitProvider = provider3;
        this.desktopStateProvider = provider4;
        this.desktopConfigProvider = provider5;
    }

    public static WindowDecorViewHostSupplier provideWindowDecorViewHostSupplier(Context context, CoroutineScope coroutineScope, ShellInit shellInit, DesktopState desktopState, DesktopConfig desktopConfig) {
        int i;
        DesktopConfigImpl desktopConfigImpl = (DesktopConfigImpl) desktopConfig;
        desktopConfigImpl.getClass();
        int i2 = (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_SCVH_CACHE.isTrue() && (i = desktopConfigImpl.maxTaskLimit) > 0) ? i : 0;
        return (!desktopState.canEnterDesktopModeOrShowAppHandle() || i2 <= 0) ? new DefaultWindowDecorViewHostSupplier(coroutineScope) : new PooledWindowDecorViewHostSupplier(context, coroutineScope, shellInit, i2, desktopConfigImpl.windowDecorPreWarmSize);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideWindowDecorViewHostSupplier((Context) this.contextProvider.get(), (CoroutineScope) this.mainScopeProvider.get(), (ShellInit) this.shellInitProvider.get(), (DesktopState) this.desktopStateProvider.get(), (DesktopConfig) this.desktopConfigProvider.get());
    }
}

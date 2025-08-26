package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.apptoweb.AppToWebGenericLinksParser;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideGenericLinksParserFactory implements Provider {
    public final Provider contextProvider;
    public final Provider desktopConfigProvider;
    public final Provider mainExecutorProvider;

    public WMShellModule_ProvideGenericLinksParserFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.mainExecutorProvider = provider2;
        this.desktopConfigProvider = provider3;
    }

    public static AppToWebGenericLinksParser provideGenericLinksParser(Context context, ShellExecutor shellExecutor, DesktopConfig desktopConfig) {
        return new AppToWebGenericLinksParser(context, shellExecutor, desktopConfig);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new AppToWebGenericLinksParser((Context) this.contextProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (DesktopConfig) this.desktopConfigProvider.get());
    }
}

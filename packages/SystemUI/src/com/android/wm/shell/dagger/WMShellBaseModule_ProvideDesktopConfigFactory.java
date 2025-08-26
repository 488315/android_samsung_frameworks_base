package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideDesktopConfigFactory implements Provider {
    public final Provider contextProvider;
    public final Provider featuresProvider;

    public WMShellBaseModule_ProvideDesktopConfigFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.featuresProvider = provider2;
    }

    public static DesktopConfigImpl provideDesktopConfig(Context context, DesktopState desktopState) {
        return new DesktopConfigImpl(context, desktopState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopConfigImpl((Context) this.contextProvider.get(), (DesktopState) this.featuresProvider.get());
    }
}

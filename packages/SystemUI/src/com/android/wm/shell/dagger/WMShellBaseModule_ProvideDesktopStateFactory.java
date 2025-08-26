package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideDesktopStateFactory implements Provider {
    public final Provider contextProvider;

    public WMShellBaseModule_ProvideDesktopStateFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static DesktopStateImpl provideDesktopState(Context context) {
        return new DesktopStateImpl(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopStateImpl((Context) this.contextProvider.get());
    }
}

package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopPersistentRepositoryFactory implements Provider {
    public final Provider bgScopeProvider;
    public final Provider contextProvider;

    public WMShellModule_ProvideDesktopPersistentRepositoryFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.bgScopeProvider = provider2;
    }

    public static DesktopPersistentRepository provideDesktopPersistentRepository(Context context, CoroutineScope coroutineScope) {
        return new DesktopPersistentRepository(context, coroutineScope);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopPersistentRepository((Context) this.contextProvider.get(), (CoroutineScope) this.bgScopeProvider.get());
    }
}

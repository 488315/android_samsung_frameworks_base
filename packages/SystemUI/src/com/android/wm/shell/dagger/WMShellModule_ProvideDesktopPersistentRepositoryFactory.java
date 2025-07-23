package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

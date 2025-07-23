package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopRepositoryInitializerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider desktopConfigProvider;
    public final Provider desktopPersistentRepositoryProvider;
    public final Provider mainScopeProvider;

    public WMShellModule_ProvideDesktopRepositoryInitializerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.desktopPersistentRepositoryProvider = provider2;
        this.mainScopeProvider = provider3;
        this.desktopConfigProvider = provider4;
    }

    public static DesktopRepositoryInitializerImpl provideDesktopRepositoryInitializer(Context context, DesktopPersistentRepository desktopPersistentRepository, CoroutineScope coroutineScope, DesktopConfig desktopConfig) {
        return new DesktopRepositoryInitializerImpl(context, desktopPersistentRepository, coroutineScope, desktopConfig);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopRepositoryInitializerImpl((Context) this.contextProvider.get(), (DesktopPersistentRepository) this.desktopPersistentRepositoryProvider.get(), (CoroutineScope) this.mainScopeProvider.get(), (DesktopConfig) this.desktopConfigProvider.get());
    }
}

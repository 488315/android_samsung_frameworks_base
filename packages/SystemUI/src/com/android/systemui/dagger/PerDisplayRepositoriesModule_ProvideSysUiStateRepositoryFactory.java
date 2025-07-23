package com.android.systemui.dagger;

import com.android.app.displaylib.DefaultDisplayOnlyInstanceRepositoryImpl;
import com.android.app.displaylib.PerDisplayInstanceRepositoryImpl;
import com.android.app.displaylib.PerDisplayRepository;
import com.android.systemui.model.SysUIStateInstanceProvider;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PerDisplayRepositoriesModule_ProvideSysUiStateRepositoryFactory implements Provider {
    public final Provider instanceProvider;
    public final PerDisplayRepositoriesModule module;
    public final Provider repositoryFactoryProvider;

    public PerDisplayRepositoriesModule_ProvideSysUiStateRepositoryFactory(PerDisplayRepositoriesModule perDisplayRepositoriesModule, Provider provider, Provider provider2) {
        this.module = perDisplayRepositoriesModule;
        this.repositoryFactoryProvider = provider;
        this.instanceProvider = provider2;
    }

    public static PerDisplayRepository provideSysUiStateRepository(PerDisplayRepositoriesModule perDisplayRepositoriesModule, PerDisplayInstanceRepositoryImpl.Factory factory, SysUIStateInstanceProvider sysUIStateInstanceProvider) {
        perDisplayRepositoriesModule.getClass();
        ShadeWindowGoesAround.INSTANCE.getClass();
        return ShadeWindowGoesAround.FLAG.isTrue() ? factory.create("SysUiStatePerDisplayRepo", sysUIStateInstanceProvider, null) : new DefaultDisplayOnlyInstanceRepositoryImpl("SysUiStatePerDisplayRepo", sysUIStateInstanceProvider);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSysUiStateRepository(this.module, (PerDisplayInstanceRepositoryImpl.Factory) this.repositoryFactoryProvider.get(), (SysUIStateInstanceProvider) this.instanceProvider.get());
    }
}

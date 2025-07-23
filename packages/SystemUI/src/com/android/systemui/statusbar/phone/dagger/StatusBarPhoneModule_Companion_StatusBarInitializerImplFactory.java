package com.android.systemui.statusbar.phone.dagger;

import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.core.StatusBarInitializerImpl;
import com.android.systemui.statusbar.data.repository.DarkIconDispatcherStore;
import com.android.systemui.statusbar.data.repository.DarkIconDispatcherStoreImpl;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationController;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationControllerStore;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarPhoneModule_Companion_StatusBarInitializerImplFactory implements Provider {
    public final Provider darkIconDispatcherStoreProvider;
    public final Provider implFactoryProvider;
    public final Provider statusBarConfigurationControllerStoreProvider;
    public final Provider statusBarModeRepositoryStoreProvider;
    public final Provider statusBarWindowControllerStoreProvider;

    public StatusBarPhoneModule_Companion_StatusBarInitializerImplFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.implFactoryProvider = provider;
        this.statusBarWindowControllerStoreProvider = provider2;
        this.statusBarModeRepositoryStoreProvider = provider3;
        this.statusBarConfigurationControllerStoreProvider = provider4;
        this.darkIconDispatcherStoreProvider = provider5;
    }

    public static StatusBarInitializerImpl statusBarInitializerImpl(DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass120 anonymousClass120, StatusBarWindowControllerStore statusBarWindowControllerStore, StatusBarModeRepositoryStore statusBarModeRepositoryStore, StatusBarConfigurationControllerStore statusBarConfigurationControllerStore, DarkIconDispatcherStore darkIconDispatcherStore) {
        StatusBarPhoneModule.Companion.getClass();
        return anonymousClass120.create((StatusBarWindowController) statusBarWindowControllerStore.getDefaultDisplay(), (StatusBarModePerDisplayRepository) statusBarModeRepositoryStore.getDefaultDisplay(), (StatusBarConfigurationController) statusBarConfigurationControllerStore.getDefaultDisplay(), (DarkIconDispatcher) ((DarkIconDispatcherStoreImpl) darkIconDispatcherStore).getDefaultDisplay());
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return statusBarInitializerImpl((DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass120) this.implFactoryProvider.get(), (StatusBarWindowControllerStore) this.statusBarWindowControllerStoreProvider.get(), (StatusBarModeRepositoryStore) this.statusBarModeRepositoryStoreProvider.get(), (StatusBarConfigurationControllerStore) this.statusBarConfigurationControllerStoreProvider.get(), (DarkIconDispatcherStore) this.darkIconDispatcherStoreProvider.get());
    }
}

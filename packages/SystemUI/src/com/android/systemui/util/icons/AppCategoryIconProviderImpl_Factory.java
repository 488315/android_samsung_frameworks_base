package com.android.systemui.util.icons;

import android.content.pm.PackageManager;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.shared.system.PackageManagerWrapper;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes3.dex */
public final class AppCategoryIconProviderImpl_Factory implements Provider {
    private final Provider assistManagerProvider;
    private final Provider backgroundDispatcherProvider;
    private final Provider packageManagerProvider;
    private final Provider packageManagerWrapperProvider;

    public AppCategoryIconProviderImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.backgroundDispatcherProvider = provider;
        this.assistManagerProvider = provider2;
        this.packageManagerProvider = provider3;
        this.packageManagerWrapperProvider = provider4;
    }

    public static AppCategoryIconProviderImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new AppCategoryIconProviderImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4));
    }

    public static AppCategoryIconProviderImpl newInstance(CoroutineDispatcher coroutineDispatcher, AssistManager assistManager, PackageManager packageManager, PackageManagerWrapper packageManagerWrapper) {
        return new AppCategoryIconProviderImpl(coroutineDispatcher, assistManager, packageManager, packageManagerWrapper);
    }

    public static AppCategoryIconProviderImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new AppCategoryIconProviderImpl_Factory(provider, provider2, provider3, provider4);
    }

    @Override // javax.inject.Provider
    public AppCategoryIconProviderImpl get() {
        return newInstance((CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (AssistManager) this.assistManagerProvider.get(), (PackageManager) this.packageManagerProvider.get(), (PackageManagerWrapper) this.packageManagerWrapperProvider.get());
    }
}

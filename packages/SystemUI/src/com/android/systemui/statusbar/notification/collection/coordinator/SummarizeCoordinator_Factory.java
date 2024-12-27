package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.SummarizeController;
import com.android.systemui.util.settings.SystemSettings;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

public final class SummarizeCoordinator_Factory implements Provider {
    private final javax.inject.Provider commonNotifCollectionLazyProvider;
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider shadeExpansionStateManagerProvider;
    private final javax.inject.Provider statusBarStateControllerProvider;
    private final javax.inject.Provider summarizeControllerProvider;
    private final javax.inject.Provider systemSettingsProvider;

    public SummarizeCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        this.contextProvider = provider;
        this.systemSettingsProvider = provider2;
        this.commonNotifCollectionLazyProvider = provider3;
        this.shadeExpansionStateManagerProvider = provider4;
        this.summarizeControllerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
    }

    public static SummarizeCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        return new SummarizeCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static SummarizeCoordinator newInstance(Context context, SystemSettings systemSettings, Lazy lazy, ShadeExpansionStateManager shadeExpansionStateManager, SummarizeController summarizeController, StatusBarStateController statusBarStateController) {
        return new SummarizeCoordinator(context, systemSettings, lazy, shadeExpansionStateManager, summarizeController, statusBarStateController);
    }

    @Override // javax.inject.Provider
    public SummarizeCoordinator get() {
        return newInstance((Context) this.contextProvider.get(), (SystemSettings) this.systemSettingsProvider.get(), DoubleCheck.lazy(this.commonNotifCollectionLazyProvider), (ShadeExpansionStateManager) this.shadeExpansionStateManagerProvider.get(), (SummarizeController) this.summarizeControllerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get());
    }
}

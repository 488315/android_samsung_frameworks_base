package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.util.settings.SystemSettings;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HighlightsCoordinator_Factory implements Provider {
    private final javax.inject.Provider commonNotifCollectionLazyProvider;
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider highlightsHeaderControllerProvider;
    private final javax.inject.Provider shadeExpansionStateManagerProvider;
    private final javax.inject.Provider statusBarStateControllerProvider;
    private final javax.inject.Provider systemSettingsProvider;

    public HighlightsCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        this.highlightsHeaderControllerProvider = provider;
        this.commonNotifCollectionLazyProvider = provider2;
        this.contextProvider = provider3;
        this.systemSettingsProvider = provider4;
        this.shadeExpansionStateManagerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
    }

    public static HighlightsCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        return new HighlightsCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static HighlightsCoordinator newInstance(NodeController nodeController, Lazy lazy, Context context, SystemSettings systemSettings, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarStateController statusBarStateController) {
        return new HighlightsCoordinator(nodeController, lazy, context, systemSettings, shadeExpansionStateManager, statusBarStateController);
    }

    @Override // javax.inject.Provider
    public HighlightsCoordinator get() {
        return newInstance((NodeController) this.highlightsHeaderControllerProvider.get(), DoubleCheck.lazy(this.commonNotifCollectionLazyProvider), (Context) this.contextProvider.get(), (SystemSettings) this.systemSettingsProvider.get(), (ShadeExpansionStateManager) this.shadeExpansionStateManagerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get());
    }
}

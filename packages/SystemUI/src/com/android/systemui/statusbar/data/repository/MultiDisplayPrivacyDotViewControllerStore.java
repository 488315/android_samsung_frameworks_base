package com.android.systemui.statusbar.data.repository;

import com.android.app.displaylib.PerDisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiDisplayPrivacyDotViewControllerStore extends StatusBarPerDisplayStoreImpl implements PrivacyDotViewControllerStore {
    public final StatusBarContentInsetsProviderStore contentInsetsProviderStore;
    public final PerDisplayRepository displayScopeRepository;
    public final PrivacyDotViewControllerImpl.Factory factory;
    public final Class instanceClass;
    public final StatusBarConfigurationControllerStore statusBarConfigurationControllerStore;

    public MultiDisplayPrivacyDotViewControllerStore(CoroutineScope coroutineScope, DisplayRepository displayRepository, PrivacyDotViewControllerImpl.Factory factory, PerDisplayRepository perDisplayRepository, StatusBarConfigurationControllerStore statusBarConfigurationControllerStore, StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore) {
        super(coroutineScope, displayRepository);
        this.factory = factory;
        this.displayScopeRepository = perDisplayRepository;
        this.statusBarConfigurationControllerStore = statusBarConfigurationControllerStore;
        this.contentInsetsProviderStore = statusBarContentInsetsProviderStore;
        this.instanceClass = PrivacyDotViewController.class;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object createInstanceForDisplay(int i) {
        StatusBarContentInsetsProvider statusBarContentInsetsProvider;
        CoroutineScope coroutineScope;
        StatusBarConfigurationController statusBarConfigurationController = (StatusBarConfigurationController) this.statusBarConfigurationControllerStore.forDisplay(i);
        if (statusBarConfigurationController == null || (statusBarContentInsetsProvider = (StatusBarContentInsetsProvider) this.contentInsetsProviderStore.forDisplay(i)) == null || (coroutineScope = (CoroutineScope) this.displayScopeRepository.get(i)) == null) {
            return null;
        }
        return this.factory.create(coroutineScope, statusBarConfigurationController, statusBarContentInsetsProvider);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Class getInstanceClass() {
        return this.instanceClass;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object onDisplayRemovalAction(Object obj) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }
}

package com.android.systemui.statusbar.window;

import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepositoryImpl;
import com.android.systemui.display.shared.model.DisplayWindowProperties;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationController;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationControllerStore;
import com.android.systemui.statusbar.data.repository.StatusBarContentInsetsProviderStore;
import com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class MultiDisplayStatusBarWindowControllerStore extends StatusBarPerDisplayStoreImpl implements StatusBarWindowControllerStore {
    public final StatusBarWindowController.Factory controllerFactory;
    public final DisplayWindowPropertiesRepository displayWindowPropertiesRepository;
    public final StatusBarConfigurationControllerStore statusBarConfigurationControllerStore;
    public final StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore;

    public MultiDisplayStatusBarWindowControllerStore(CoroutineScope coroutineScope, StatusBarWindowController.Factory factory, DisplayWindowPropertiesRepository displayWindowPropertiesRepository, StatusBarConfigurationControllerStore statusBarConfigurationControllerStore, StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore, DisplayRepository displayRepository) {
        super(coroutineScope, displayRepository);
        this.controllerFactory = factory;
        this.displayWindowPropertiesRepository = displayWindowPropertiesRepository;
        this.statusBarConfigurationControllerStore = statusBarConfigurationControllerStore;
        this.statusBarContentInsetsProviderStore = statusBarContentInsetsProviderStore;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object createInstanceForDisplay(int i) {
        StatusBarConfigurationController statusBarConfigurationController;
        StatusBarContentInsetsProvider statusBarContentInsetsProvider;
        DisplayWindowProperties displayWindowProperties = ((DisplayWindowPropertiesRepositoryImpl) this.displayWindowPropertiesRepository).get(i, 2000);
        if (displayWindowProperties == null || (statusBarConfigurationController = (StatusBarConfigurationController) this.statusBarConfigurationControllerStore.forDisplay(i)) == null || (statusBarContentInsetsProvider = (StatusBarContentInsetsProvider) this.statusBarContentInsetsProviderStore.forDisplay(i)) == null) {
            return null;
        }
        return ((DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass8) this.controllerFactory).create(displayWindowProperties.context, displayWindowProperties.windowManager, statusBarConfigurationController, statusBarContentInsetsProvider);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Class getInstanceClass() {
        return null;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object onDisplayRemovalAction(Object obj) {
        int i = StatusBarConnectedDisplays.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }
}

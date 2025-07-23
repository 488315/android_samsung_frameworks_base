package com.android.systemui.statusbar.core;

import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.data.repository.DarkIconDispatcherStore;
import com.android.systemui.statusbar.data.repository.DarkIconDispatcherStoreImpl;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationController;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationControllerStore;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiDisplayStatusBarInitializerStore extends StatusBarPerDisplayStoreImpl implements StatusBarInitializerStore {
    public final DarkIconDispatcherStore darkIconDispatcherStore;
    public final StatusBarInitializer.Factory factory;
    public final StatusBarConfigurationControllerStore statusBarConfigurationControllerStore;
    public final StatusBarModeRepositoryStore statusBarModeRepositoryStore;
    public final StatusBarWindowControllerStore statusBarWindowControllerStore;

    public MultiDisplayStatusBarInitializerStore(CoroutineScope coroutineScope, DisplayRepository displayRepository, StatusBarInitializer.Factory factory, StatusBarWindowControllerStore statusBarWindowControllerStore, StatusBarModeRepositoryStore statusBarModeRepositoryStore, StatusBarConfigurationControllerStore statusBarConfigurationControllerStore, DarkIconDispatcherStore darkIconDispatcherStore) {
        super(coroutineScope, displayRepository);
        this.factory = factory;
        this.statusBarWindowControllerStore = statusBarWindowControllerStore;
        this.statusBarModeRepositoryStore = statusBarModeRepositoryStore;
        this.statusBarConfigurationControllerStore = statusBarConfigurationControllerStore;
        this.darkIconDispatcherStore = darkIconDispatcherStore;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object createInstanceForDisplay(int i) {
        StatusBarModePerDisplayRepository statusBarModePerDisplayRepository;
        StatusBarConfigurationController statusBarConfigurationController;
        DarkIconDispatcher darkIconDispatcher;
        StatusBarWindowController statusBarWindowController = (StatusBarWindowController) this.statusBarWindowControllerStore.forDisplay(i);
        if (statusBarWindowController == null || (statusBarModePerDisplayRepository = (StatusBarModePerDisplayRepository) this.statusBarModeRepositoryStore.forDisplay(i)) == null || (statusBarConfigurationController = (StatusBarConfigurationController) this.statusBarConfigurationControllerStore.forDisplay(i)) == null || (darkIconDispatcher = (DarkIconDispatcher) ((DarkIconDispatcherStoreImpl) this.darkIconDispatcherStore).forDisplay(i)) == null) {
            return null;
        }
        return ((DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass120) this.factory).create(statusBarWindowController, statusBarModePerDisplayRepository, statusBarConfigurationController, darkIconDispatcher);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Class getInstanceClass() {
        return null;
    }
}

package com.android.systemui.statusbar.core;

import com.android.app.displaylib.PerDisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarOrchestrator;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.AutoHideControllerStore;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import com.android.systemui.statusbar.window.data.repository.StatusBarWindowStatePerDisplayRepository;
import com.android.systemui.statusbar.window.data.repository.StatusBarWindowStateRepositoryStore;
import com.android.systemui.statusbar.window.data.repository.StatusBarWindowStateRepositoryStoreImpl;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiDisplayStatusBarOrchestratorStore extends StatusBarPerDisplayStoreImpl {
    public final AutoHideControllerStore autoHideControllerStore;
    public final PerDisplayRepository displayScopeRepository;
    public final StatusBarOrchestrator.Factory factory;
    public final StatusBarInitializerStore initializerStore;
    public final StatusBarModeRepositoryStore statusBarModeRepositoryStore;
    public final StatusBarWindowControllerStore statusBarWindowControllerStore;
    public final StatusBarWindowStateRepositoryStore statusBarWindowStateRepositoryStore;

    public MultiDisplayStatusBarOrchestratorStore(CoroutineScope coroutineScope, DisplayRepository displayRepository, StatusBarOrchestrator.Factory factory, StatusBarWindowControllerStore statusBarWindowControllerStore, StatusBarModeRepositoryStore statusBarModeRepositoryStore, StatusBarInitializerStore statusBarInitializerStore, AutoHideControllerStore autoHideControllerStore, PerDisplayRepository perDisplayRepository, StatusBarWindowStateRepositoryStore statusBarWindowStateRepositoryStore) {
        super(coroutineScope, displayRepository);
        this.factory = factory;
        this.statusBarWindowControllerStore = statusBarWindowControllerStore;
        this.statusBarModeRepositoryStore = statusBarModeRepositoryStore;
        this.initializerStore = statusBarInitializerStore;
        this.autoHideControllerStore = autoHideControllerStore;
        this.displayScopeRepository = perDisplayRepository;
        this.statusBarWindowStateRepositoryStore = statusBarWindowStateRepositoryStore;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object createInstanceForDisplay(int i) {
        StatusBarInitializer statusBarInitializer;
        StatusBarWindowController statusBarWindowController;
        AutoHideController autoHideController;
        CoroutineScope coroutineScope;
        StatusBarWindowStatePerDisplayRepository create;
        StatusBarWindowStatePerDisplayRepository statusBarWindowStatePerDisplayRepository;
        StatusBarModePerDisplayRepository statusBarModePerDisplayRepository = (StatusBarModePerDisplayRepository) this.statusBarModeRepositoryStore.forDisplay(i);
        if (statusBarModePerDisplayRepository == null || (statusBarInitializer = (StatusBarInitializer) this.initializerStore.forDisplay(i)) == null || (statusBarWindowController = (StatusBarWindowController) this.statusBarWindowControllerStore.forDisplay(i)) == null || (autoHideController = (AutoHideController) this.autoHideControllerStore.forDisplay(i)) == null || (coroutineScope = (CoroutineScope) this.displayScopeRepository.get(i)) == null) {
            return null;
        }
        StatusBarOrchestrator.Factory factory = this.factory;
        StatusBarWindowStateRepositoryStoreImpl statusBarWindowStateRepositoryStoreImpl = (StatusBarWindowStateRepositoryStoreImpl) this.statusBarWindowStateRepositoryStore;
        synchronized (statusBarWindowStateRepositoryStoreImpl.repositoryCache) {
            try {
                WeakReference weakReference = (WeakReference) ((LinkedHashMap) statusBarWindowStateRepositoryStoreImpl.repositoryCache).get(Integer.valueOf(i));
                if (weakReference != null) {
                    create = (StatusBarWindowStatePerDisplayRepository) weakReference.get();
                    if (create == null) {
                    }
                    statusBarWindowStatePerDisplayRepository = create;
                }
                create = statusBarWindowStateRepositoryStoreImpl.factory.create(i);
                statusBarWindowStateRepositoryStoreImpl.repositoryCache.put(Integer.valueOf(i), new WeakReference(create));
                statusBarWindowStatePerDisplayRepository = create;
            } catch (Throwable th) {
                throw th;
            }
        }
        return factory.create(i, coroutineScope, statusBarWindowStatePerDisplayRepository, statusBarModePerDisplayRepository, statusBarInitializer, statusBarWindowController, autoHideController);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Class getInstanceClass() {
        return null;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object onDisplayRemovalAction(Object obj) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }
}

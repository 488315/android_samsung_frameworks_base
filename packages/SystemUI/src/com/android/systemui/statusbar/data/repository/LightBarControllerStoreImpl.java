package com.android.systemui.statusbar.data.repository;

import com.android.app.displaylib.PerDisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarControllerImpl;
import kotlin.Unit;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class LightBarControllerStoreImpl extends StatusBarPerDisplayStoreImpl implements LightBarControllerStore {
    public final DarkIconDispatcherStore darkIconDispatcherStore;
    public final PerDisplayRepository displayScopeRepository;
    public final LightBarControllerImpl.Factory factory;
    public final Class instanceClass;
    public final StatusBarModeRepositoryStore statusBarModeRepositoryStore;

    public LightBarControllerStoreImpl(CoroutineScope coroutineScope, DisplayRepository displayRepository, LightBarControllerImpl.Factory factory, PerDisplayRepository perDisplayRepository, StatusBarModeRepositoryStore statusBarModeRepositoryStore, DarkIconDispatcherStore darkIconDispatcherStore) {
        super(coroutineScope, displayRepository);
        this.factory = factory;
        this.displayScopeRepository = perDisplayRepository;
        this.statusBarModeRepositoryStore = statusBarModeRepositoryStore;
        this.darkIconDispatcherStore = darkIconDispatcherStore;
        this.instanceClass = LightBarController.class;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object createInstanceForDisplay(int i) {
        StatusBarModePerDisplayRepository statusBarModePerDisplayRepository;
        CoroutineScope coroutineScope;
        DarkIconDispatcher darkIconDispatcher = (DarkIconDispatcher) ((DarkIconDispatcherStoreImpl) this.darkIconDispatcherStore).forDisplay(i);
        if (darkIconDispatcher == null || (statusBarModePerDisplayRepository = (StatusBarModePerDisplayRepository) this.statusBarModeRepositoryStore.forDisplay(i)) == null || (coroutineScope = (CoroutineScope) this.displayScopeRepository.get(i)) == null) {
            return null;
        }
        LightBarControllerImpl lightBarControllerImplCreate = this.factory.create(i, coroutineScope, darkIconDispatcher, statusBarModePerDisplayRepository);
        lightBarControllerImplCreate.start();
        return lightBarControllerImplCreate;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Class getInstanceClass() {
        return this.instanceClass;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object onDisplayRemovalAction(Object obj) {
        ((LightBarControllerImpl) ((LightBarController) obj)).stop();
        return Unit.INSTANCE;
    }
}

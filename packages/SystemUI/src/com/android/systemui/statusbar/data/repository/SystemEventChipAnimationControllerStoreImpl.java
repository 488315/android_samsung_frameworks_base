package com.android.systemui.statusbar.data.repository;

import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepositoryImpl;
import com.android.systemui.display.shared.model.DisplayWindowProperties;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.events.SystemEventChipAnimationController;
import com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import kotlin.Unit;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class SystemEventChipAnimationControllerStoreImpl extends StatusBarPerDisplayStoreImpl implements SystemEventChipAnimationControllerStore {
    public final DisplayWindowPropertiesRepository displayWindowPropertiesRepository;
    public final SystemEventChipAnimationControllerImpl.Factory factory;
    public final StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore;
    public final StatusBarWindowControllerStore statusBarWindowControllerStore;

    public SystemEventChipAnimationControllerStoreImpl(CoroutineScope coroutineScope, DisplayRepository displayRepository, SystemEventChipAnimationControllerImpl.Factory factory, DisplayWindowPropertiesRepository displayWindowPropertiesRepository, StatusBarWindowControllerStore statusBarWindowControllerStore, StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore) {
        super(coroutineScope, displayRepository);
        this.factory = factory;
        this.displayWindowPropertiesRepository = displayWindowPropertiesRepository;
        this.statusBarWindowControllerStore = statusBarWindowControllerStore;
        this.statusBarContentInsetsProviderStore = statusBarContentInsetsProviderStore;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object createInstanceForDisplay(int i) {
        StatusBarWindowController statusBarWindowController;
        StatusBarContentInsetsProvider statusBarContentInsetsProvider;
        DisplayWindowProperties displayWindowProperties = ((DisplayWindowPropertiesRepositoryImpl) this.displayWindowPropertiesRepository).get(i, 2000);
        if (displayWindowProperties == null || (statusBarWindowController = (StatusBarWindowController) this.statusBarWindowControllerStore.forDisplay(i)) == null || (statusBarContentInsetsProvider = (StatusBarContentInsetsProvider) this.statusBarContentInsetsProviderStore.forDisplay(i)) == null) {
            return null;
        }
        return this.factory.create(displayWindowProperties.context, statusBarWindowController, statusBarContentInsetsProvider);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Class getInstanceClass() {
        return null;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object onDisplayRemovalAction(Object obj) {
        ((SystemEventChipAnimationController) obj).stop();
        return Unit.INSTANCE;
    }
}

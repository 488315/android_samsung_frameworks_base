package com.android.systemui.statusbar.data.repository;

import android.content.Context;
import com.android.systemui.CameraProtectionLoaderImpl;
import com.android.systemui.SysUICutoutProviderImpl;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepositoryImpl;
import com.android.systemui.display.shared.model.DisplayWindowProperties;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderImpl;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class MultiDisplayStatusBarContentInsetsProviderStore extends StatusBarPerDisplayStoreImpl implements StatusBarContentInsetsProviderStore {
    public final CameraProtectionLoaderImpl.Factory cameraProtectionLoaderFactory;
    public final DisplayWindowPropertiesRepository displayWindowPropertiesRepository;
    public final StatusBarContentInsetsProviderImpl.Factory factory;
    public final Class instanceClass;
    public final StatusBarConfigurationControllerStore statusBarConfigurationControllerStore;
    public final SysUICutoutProviderImpl.Factory sysUICutoutProviderFactory;

    public MultiDisplayStatusBarContentInsetsProviderStore(CoroutineScope coroutineScope, DisplayRepository displayRepository, StatusBarContentInsetsProviderImpl.Factory factory, DisplayWindowPropertiesRepository displayWindowPropertiesRepository, StatusBarConfigurationControllerStore statusBarConfigurationControllerStore, SysUICutoutProviderImpl.Factory factory2, CameraProtectionLoaderImpl.Factory factory3) {
        super(coroutineScope, displayRepository);
        this.factory = factory;
        this.displayWindowPropertiesRepository = displayWindowPropertiesRepository;
        this.statusBarConfigurationControllerStore = statusBarConfigurationControllerStore;
        this.sysUICutoutProviderFactory = factory2;
        this.cameraProtectionLoaderFactory = factory3;
        this.instanceClass = StatusBarContentInsetsProvider.class;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object createInstanceForDisplay(int i) {
        DisplayWindowProperties displayWindowProperties = ((DisplayWindowPropertiesRepositoryImpl) this.displayWindowPropertiesRepository).get(i, 2000);
        if (displayWindowProperties == null) {
            return null;
        }
        Context context = displayWindowProperties.context;
        StatusBarConfigurationController statusBarConfigurationController = (StatusBarConfigurationController) this.statusBarConfigurationControllerStore.forDisplay(i);
        if (statusBarConfigurationController == null) {
            return null;
        }
        StatusBarContentInsetsProviderImpl statusBarContentInsetsProviderImplCreate = this.factory.create(context, statusBarConfigurationController, this.sysUICutoutProviderFactory.create(context, this.cameraProtectionLoaderFactory.create(context)));
        ((ConfigurationControllerImpl) statusBarContentInsetsProviderImplCreate.configurationController).addCallback(statusBarContentInsetsProviderImplCreate);
        statusBarContentInsetsProviderImplCreate.dumpManager.registerNormalDumpable(statusBarContentInsetsProviderImplCreate.dumpableName, statusBarContentInsetsProviderImplCreate);
        statusBarContentInsetsProviderImplCreate.commandRegistry.registerCommand(statusBarContentInsetsProviderImplCreate.commandName, new StatusBarContentInsetsProviderImpl$$ExternalSyntheticLambda0(statusBarContentInsetsProviderImplCreate, 1));
        return statusBarContentInsetsProviderImplCreate;
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

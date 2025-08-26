package com.android.systemui.statusbar.data.repository;

import com.android.app.displaylib.PerDisplayInstanceProvider;
import com.android.systemui.common.ui.ConfigurationStateImpl;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepositoryImpl;
import com.android.systemui.display.shared.model.DisplayWindowProperties;

/* loaded from: classes3.dex */
public final class StatusBarPerDisplayConfigurationStateProvider implements PerDisplayInstanceProvider {
    public final DisplayWindowPropertiesRepository displayWindowPropertiesRepository;
    public final ConfigurationStateImpl.Factory factory;
    public final StatusBarConfigurationControllerStore statusBarConfigurationControllerStore;

    public StatusBarPerDisplayConfigurationStateProvider(DisplayWindowPropertiesRepository displayWindowPropertiesRepository, StatusBarConfigurationControllerStore statusBarConfigurationControllerStore, ConfigurationStateImpl.Factory factory) {
        this.displayWindowPropertiesRepository = displayWindowPropertiesRepository;
        this.statusBarConfigurationControllerStore = statusBarConfigurationControllerStore;
        this.factory = factory;
    }

    @Override // com.android.app.displaylib.PerDisplayInstanceProvider
    public final Object createInstance(int i) {
        StatusBarConfigurationController statusBarConfigurationController;
        DisplayWindowProperties displayWindowProperties = ((DisplayWindowPropertiesRepositoryImpl) this.displayWindowPropertiesRepository).get(i, 2000);
        if (displayWindowProperties == null || (statusBarConfigurationController = (StatusBarConfigurationController) this.statusBarConfigurationControllerStore.forDisplay(i)) == null) {
            return null;
        }
        return this.factory.create(displayWindowProperties.context, statusBarConfigurationController);
    }
}

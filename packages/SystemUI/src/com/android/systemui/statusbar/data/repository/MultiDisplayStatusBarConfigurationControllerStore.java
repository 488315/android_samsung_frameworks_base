package com.android.systemui.statusbar.data.repository;

import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepositoryImpl;
import com.android.systemui.display.shared.model.DisplayWindowProperties;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiDisplayStatusBarConfigurationControllerStore extends StatusBarPerDisplayStoreImpl implements StatusBarConfigurationControllerStore {
    public final ConfigurationControllerImpl.Factory configurationControllerFactory;
    public final DisplayWindowPropertiesRepository displayWindowPropertiesRepository;

    public MultiDisplayStatusBarConfigurationControllerStore(CoroutineScope coroutineScope, DisplayRepository displayRepository, DisplayWindowPropertiesRepository displayWindowPropertiesRepository, ConfigurationControllerImpl.Factory factory) {
        super(coroutineScope, displayRepository);
        this.displayWindowPropertiesRepository = displayWindowPropertiesRepository;
        this.configurationControllerFactory = factory;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object createInstanceForDisplay(int i) {
        DisplayWindowProperties displayWindowProperties = ((DisplayWindowPropertiesRepositoryImpl) this.displayWindowPropertiesRepository).get(i, 2000);
        if (displayWindowProperties == null) {
            return null;
        }
        return this.configurationControllerFactory.create(displayWindowProperties.context);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Class getInstanceClass() {
        return null;
    }
}

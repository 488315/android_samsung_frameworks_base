package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepositoryImpl;
import com.android.systemui.display.shared.model.DisplayWindowProperties;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl;
import com.android.systemui.statusbar.phone.AutoHideControllerImpl;
import kotlin.Unit;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiDisplayAutoHideControllerStore extends StatusBarPerDisplayStoreImpl implements AutoHideControllerStore {
    public final AutoHideControllerImpl.Factory autoHideControllerFactory;
    public final DisplayWindowPropertiesRepository displayWindowPropertiesRepository;

    public MultiDisplayAutoHideControllerStore(CoroutineScope coroutineScope, DisplayRepository displayRepository, DisplayWindowPropertiesRepository displayWindowPropertiesRepository, AutoHideControllerImpl.Factory factory) {
        super(coroutineScope, displayRepository);
        this.displayWindowPropertiesRepository = displayWindowPropertiesRepository;
        this.autoHideControllerFactory = factory;
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
        Context context = displayWindowProperties.context;
        AutoHideControllerImpl.Factory factory = this.autoHideControllerFactory;
        return new AutoHideControllerImpl(context, factory.mHandler, factory.mIWindowManager);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Class getInstanceClass() {
        return null;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object onDisplayRemovalAction(Object obj) {
        AutoHideControllerImpl autoHideControllerImpl = (AutoHideControllerImpl) ((AutoHideController) obj);
        autoHideControllerImpl.mHandler.removeCallbacks(autoHideControllerImpl.mAutoHide);
        return Unit.INSTANCE;
    }
}

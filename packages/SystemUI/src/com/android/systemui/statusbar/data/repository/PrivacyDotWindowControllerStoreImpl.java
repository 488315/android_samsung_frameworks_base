package com.android.systemui.statusbar.data.repository;

import android.view.View;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepositoryImpl;
import com.android.systemui.display.shared.model.DisplayWindowProperties;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.PrivacyDotWindowController;
import java.util.Iterator;
import kotlin.Unit;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class PrivacyDotWindowControllerStoreImpl extends StatusBarPerDisplayStoreImpl implements PrivacyDotWindowControllerStore {
    public final DisplayWindowPropertiesRepository displayWindowPropertiesRepository;
    public final PrivacyDotViewControllerStore privacyDotViewControllerStore;
    public final PrivacyDotWindowController.Factory windowControllerFactory;

    public PrivacyDotWindowControllerStoreImpl(CoroutineScope coroutineScope, DisplayRepository displayRepository, PrivacyDotWindowController.Factory factory, DisplayWindowPropertiesRepository displayWindowPropertiesRepository, PrivacyDotViewControllerStore privacyDotViewControllerStore) {
        super(coroutineScope, displayRepository);
        this.windowControllerFactory = factory;
        this.displayWindowPropertiesRepository = displayWindowPropertiesRepository;
        this.privacyDotViewControllerStore = privacyDotViewControllerStore;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object createInstanceForDisplay(int i) {
        PrivacyDotViewController privacyDotViewController;
        if (i == 0) {
            throw new IllegalArgumentException("This class should only be used for connected displays");
        }
        DisplayWindowProperties displayWindowProperties = ((DisplayWindowPropertiesRepositoryImpl) this.displayWindowPropertiesRepository).get(i, 2024);
        if (displayWindowProperties == null || (privacyDotViewController = (PrivacyDotViewController) this.privacyDotViewControllerStore.forDisplay(i)) == null) {
            return null;
        }
        return this.windowControllerFactory.create(i, privacyDotViewController, displayWindowProperties.windowManager, displayWindowProperties.layoutInflater);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Class getInstanceClass() {
        return null;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object onDisplayRemovalAction(Object obj) {
        PrivacyDotWindowController privacyDotWindowController = (PrivacyDotWindowController) obj;
        Iterator it = privacyDotWindowController.dotViews.iterator();
        while (it.hasNext()) {
            privacyDotWindowController.windowManager.removeView((View) it.next());
        }
        return Unit.INSTANCE;
    }
}

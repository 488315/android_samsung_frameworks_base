package com.android.systemui.statusbar.window;

import android.content.Context;
import android.view.WindowManager;
import com.android.systemui.display.data.repository.PerDisplayStore;
import com.android.systemui.display.data.repository.SingleDisplayStore;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationController;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationControllerStore;
import com.android.systemui.statusbar.data.repository.StatusBarContentInsetsProviderStore;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;

/* loaded from: classes3.dex */
public final class SingleDisplayStatusBarWindowControllerStore implements StatusBarWindowControllerStore, PerDisplayStore {
    public final /* synthetic */ SingleDisplayStore $$delegate_0;

    public SingleDisplayStatusBarWindowControllerStore(Context context, WindowManager windowManager, StatusBarWindowControllerImpl.Factory factory, StatusBarConfigurationControllerStore statusBarConfigurationControllerStore, StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore) {
        this.$$delegate_0 = new SingleDisplayStore(factory.create(context, windowManager, (StatusBarConfigurationController) statusBarConfigurationControllerStore.getDefaultDisplay(), (StatusBarContentInsetsProvider) statusBarContentInsetsProviderStore.getDefaultDisplay()));
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object forDisplay(int i) {
        return (StatusBarWindowController) this.$$delegate_0.defaultDisplay;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object getDefaultDisplay() {
        return (StatusBarWindowController) this.$$delegate_0.defaultDisplay;
    }
}

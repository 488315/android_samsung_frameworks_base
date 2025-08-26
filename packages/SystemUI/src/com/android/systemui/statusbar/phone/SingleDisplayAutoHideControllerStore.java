package com.android.systemui.statusbar.phone;

import com.android.systemui.display.data.repository.PerDisplayStore;
import com.android.systemui.display.data.repository.SingleDisplayStore;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;

/* loaded from: classes3.dex */
public final class SingleDisplayAutoHideControllerStore implements AutoHideControllerStore, PerDisplayStore {
    public final /* synthetic */ SingleDisplayStore $$delegate_0;

    public SingleDisplayAutoHideControllerStore(AutoHideController autoHideController) {
        this.$$delegate_0 = new SingleDisplayStore(autoHideController);
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object forDisplay(int i) {
        return (AutoHideController) this.$$delegate_0.defaultDisplay;
    }
}

package com.android.systemui.statusbar.core;

import com.android.systemui.display.data.repository.PerDisplayStore;
import com.android.systemui.display.data.repository.SingleDisplayStore;
import com.android.systemui.flags.RefactorFlagUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SingleDisplayStatusBarInitializerStore implements StatusBarInitializerStore, PerDisplayStore {
    public final /* synthetic */ SingleDisplayStore $$delegate_0;

    public SingleDisplayStatusBarInitializerStore(StatusBarInitializer statusBarInitializer) {
        this.$$delegate_0 = new SingleDisplayStore(statusBarInitializer);
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object forDisplay(int i) {
        return (StatusBarInitializer) this.$$delegate_0.defaultDisplay;
    }
}

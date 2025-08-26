package com.android.systemui.statusbar.data.repository;

import android.content.Context;
import com.android.systemui.display.data.repository.PerDisplayStore;
import com.android.systemui.display.data.repository.SingleDisplayStore;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.phone.DarkIconDispatcherImpl;

/* loaded from: classes3.dex */
public final class SingleDisplayDarkIconDispatcherStore implements SysuiDarkIconDispatcherStore, PerDisplayStore {
    public final /* synthetic */ SingleDisplayStore $$delegate_0;

    public SingleDisplayDarkIconDispatcherStore(DarkIconDispatcherImpl.Factory factory, Context context) {
        this.$$delegate_0 = new SingleDisplayStore(factory.create(context.getDisplayId(), context));
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object forDisplay(int i) {
        return (DarkIconDispatcherImpl) this.$$delegate_0.defaultDisplay;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object getDefaultDisplay() {
        return (DarkIconDispatcherImpl) this.$$delegate_0.defaultDisplay;
    }
}

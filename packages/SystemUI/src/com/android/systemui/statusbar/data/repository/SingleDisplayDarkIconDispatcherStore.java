package com.android.systemui.statusbar.data.repository;

import android.content.Context;
import com.android.systemui.display.data.repository.PerDisplayStore;
import com.android.systemui.display.data.repository.SingleDisplayStore;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.phone.DarkIconDispatcherImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

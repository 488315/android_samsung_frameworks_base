package com.android.systemui.statusbar.data.repository;

import com.android.systemui.plugins.DarkIconDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DarkIconDispatcherStoreImpl implements DarkIconDispatcherStore {
    public final SysuiDarkIconDispatcherStore store;

    public DarkIconDispatcherStoreImpl(SysuiDarkIconDispatcherStore sysuiDarkIconDispatcherStore) {
        this.store = sysuiDarkIconDispatcherStore;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object forDisplay(int i) {
        return (DarkIconDispatcher) this.store.forDisplay(i);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object getDefaultDisplay() {
        return (DarkIconDispatcher) this.store.getDefaultDisplay();
    }
}

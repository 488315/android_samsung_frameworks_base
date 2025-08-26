package com.android.systemui.statusbar.data.repository;

import com.android.systemui.plugins.DarkIconDispatcher;

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

package com.android.systemui.statusbar.data.repository;

import com.android.systemui.display.data.repository.PerDisplayStore;
import com.android.systemui.display.data.repository.SingleDisplayStore;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;

/* loaded from: classes3.dex */
public final class SingleDisplayStatusBarContentInsetsProviderStore implements StatusBarContentInsetsProviderStore, PerDisplayStore {
    public final /* synthetic */ SingleDisplayStore $$delegate_0;

    public SingleDisplayStatusBarContentInsetsProviderStore(StatusBarContentInsetsProvider statusBarContentInsetsProvider) {
        this.$$delegate_0 = new SingleDisplayStore(statusBarContentInsetsProvider);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object forDisplay(int i) {
        return (StatusBarContentInsetsProvider) this.$$delegate_0.defaultDisplay;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object getDefaultDisplay() {
        return (StatusBarContentInsetsProvider) this.$$delegate_0.defaultDisplay;
    }
}

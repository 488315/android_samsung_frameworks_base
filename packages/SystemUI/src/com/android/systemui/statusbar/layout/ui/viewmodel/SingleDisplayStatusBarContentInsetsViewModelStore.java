package com.android.systemui.statusbar.layout.ui.viewmodel;

import com.android.systemui.display.data.repository.PerDisplayStore;
import com.android.systemui.display.data.repository.SingleDisplayStore;

/* loaded from: classes3.dex */
public final class SingleDisplayStatusBarContentInsetsViewModelStore implements StatusBarContentInsetsViewModelStore, PerDisplayStore {
    public final /* synthetic */ SingleDisplayStore $$delegate_0;

    public SingleDisplayStatusBarContentInsetsViewModelStore(StatusBarContentInsetsViewModel statusBarContentInsetsViewModel) {
        this.$$delegate_0 = new SingleDisplayStore(statusBarContentInsetsViewModel);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object forDisplay(int i) {
        return (StatusBarContentInsetsViewModel) this.$$delegate_0.defaultDisplay;
    }
}

package com.android.systemui.statusbar.layout.ui.viewmodel;

import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarContentInsetsProviderStore;
import com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiDisplayStatusBarContentInsetsViewModelStore extends StatusBarPerDisplayStoreImpl implements StatusBarContentInsetsViewModelStore {
    public final Class instanceClass;
    public final StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore;

    public MultiDisplayStatusBarContentInsetsViewModelStore(CoroutineScope coroutineScope, DisplayRepository displayRepository, StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore) {
        super(coroutineScope, displayRepository);
        this.statusBarContentInsetsProviderStore = statusBarContentInsetsProviderStore;
        this.instanceClass = StatusBarContentInsetsViewModel.class;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object createInstanceForDisplay(int i) {
        StatusBarContentInsetsProvider statusBarContentInsetsProvider = (StatusBarContentInsetsProvider) this.statusBarContentInsetsProviderStore.forDisplay(i);
        if (statusBarContentInsetsProvider == null) {
            return null;
        }
        return new StatusBarContentInsetsViewModel(statusBarContentInsetsProvider);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Class getInstanceClass() {
        return this.instanceClass;
    }
}

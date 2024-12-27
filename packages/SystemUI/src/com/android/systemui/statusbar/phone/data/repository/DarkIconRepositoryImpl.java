package com.android.systemui.statusbar.phone.data.repository;

import com.android.systemui.statusbar.phone.DarkIconDispatcherImpl;
import com.android.systemui.statusbar.phone.SysuiDarkIconDispatcher;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class DarkIconRepositoryImpl implements DarkIconRepository {
    public final ReadonlyStateFlow darkState;

    public DarkIconRepositoryImpl(SysuiDarkIconDispatcher sysuiDarkIconDispatcher) {
        this.darkState = FlowKt.asStateFlow(((DarkIconDispatcherImpl) sysuiDarkIconDispatcher).mDarkChangeFlow);
    }
}

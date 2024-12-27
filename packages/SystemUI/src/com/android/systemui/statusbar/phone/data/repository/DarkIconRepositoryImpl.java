package com.android.systemui.statusbar.phone.data.repository;

import com.android.systemui.statusbar.phone.DarkIconDispatcherImpl;
import com.android.systemui.statusbar.phone.SysuiDarkIconDispatcher;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DarkIconRepositoryImpl implements DarkIconRepository {
    public final ReadonlyStateFlow darkState;

    public DarkIconRepositoryImpl(SysuiDarkIconDispatcher sysuiDarkIconDispatcher) {
        this.darkState = FlowKt.asStateFlow(((DarkIconDispatcherImpl) sysuiDarkIconDispatcher).mDarkChangeFlow);
    }
}

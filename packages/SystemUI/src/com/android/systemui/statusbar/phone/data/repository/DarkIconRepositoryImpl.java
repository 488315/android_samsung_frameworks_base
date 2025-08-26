package com.android.systemui.statusbar.phone.data.repository;

import android.util.Log;
import com.android.systemui.statusbar.data.repository.SysuiDarkIconDispatcherStore;
import com.android.systemui.statusbar.phone.DarkIconDispatcherImpl;
import com.android.systemui.statusbar.phone.SysuiDarkIconDispatcher$DarkChange;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class DarkIconRepositoryImpl implements DarkIconRepository {
    public final SysuiDarkIconDispatcherStore darkIconDispatcherStore;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DarkIconRepositoryImpl(SysuiDarkIconDispatcherStore sysuiDarkIconDispatcherStore) {
        this.darkIconDispatcherStore = sysuiDarkIconDispatcherStore;
    }

    public final StateFlow darkState(int i) {
        DarkIconDispatcherImpl darkIconDispatcherImpl = (DarkIconDispatcherImpl) this.darkIconDispatcherStore.forDisplay(i);
        if (darkIconDispatcherImpl != null) {
            return FlowKt.asStateFlow(darkIconDispatcherImpl.mDarkChangeFlow);
        }
        Log.e("DarkIconRepositoryImpl", "DarkIconDispatcher for display " + i + " is null. Returning flow of DarkChange.EMPTY");
        return StateFlowKt.MutableStateFlow(SysuiDarkIconDispatcher$DarkChange.EMPTY);
    }
}

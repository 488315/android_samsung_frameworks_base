package com.android.systemui.statusbar.phone.data.repository;

import android.util.Log;
import com.android.systemui.statusbar.data.repository.SysuiDarkIconDispatcherStore;
import com.android.systemui.statusbar.phone.DarkIconDispatcherImpl;
import com.android.systemui.statusbar.phone.SysuiDarkIconDispatcher$DarkChange;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DarkIconRepositoryImpl implements DarkIconRepository {
    public final SysuiDarkIconDispatcherStore darkIconDispatcherStore;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

package com.android.systemui.biometrics.data.repository;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RearDisplayStateRepositoryImpl implements RearDisplayStateRepository {
    public final ReadonlyStateFlow isInRearDisplayMode;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public RearDisplayStateRepositoryImpl(CoroutineScope coroutineScope, Context context, DeviceStateManager deviceStateManager, Executor executor) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        RearDisplayStateRepositoryImpl$isInRearDisplayMode$1 rearDisplayStateRepositoryImpl$isInRearDisplayMode$1 = new RearDisplayStateRepositoryImpl$isInRearDisplayMode$1(deviceStateManager, executor, context, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = ConflatedCallbackFlow.conflatedCallbackFlow(rearDisplayStateRepositoryImpl$isInRearDisplayMode$1);
        SharingStarted.Companion.getClass();
        this.isInRearDisplayMode = FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }
}

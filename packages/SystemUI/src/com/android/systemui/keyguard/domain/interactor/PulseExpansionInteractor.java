package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.repository.PulseExpansionRepository;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class PulseExpansionInteractor extends FlowDumperImpl {
    public final StateFlow isPulseExpanding;
    public final PulseExpansionRepository repository;

    public PulseExpansionInteractor(PulseExpansionRepository pulseExpansionRepository, DumpManager dumpManager) {
        super(dumpManager, null, 2, null);
        this.repository = pulseExpansionRepository;
        this.isPulseExpanding = dumpValue(FlowKt.asStateFlow(pulseExpansionRepository.isPulseExpanding), "isPulseExpanding");
    }
}

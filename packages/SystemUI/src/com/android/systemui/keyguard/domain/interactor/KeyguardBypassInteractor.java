package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.repository.KeyguardBypassRepository;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class KeyguardBypassInteractor extends FlowDumperImpl {
    public final Flow canBypass;
    public final Flow isBypassAvailable;

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

    public KeyguardBypassInteractor(KeyguardBypassRepository keyguardBypassRepository, AlternateBouncerInteractor alternateBouncerInteractor, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, PulseExpansionInteractor pulseExpansionInteractor, SceneInteractor sceneInteractor, ShadeInteractor shadeInteractor, DumpManager dumpManager) {
        super(dumpManager, null, 2, null);
        Flow flowDumpWhileCollecting = dumpWhileCollecting(keyguardBypassRepository.isBypassAvailable, "isBypassAvailable");
        this.isBypassAvailable = flowDumpWhileCollecting;
        this.canBypass = dumpWhileCollecting(FlowKt.transformLatest(flowDumpWhileCollecting, new KeyguardBypassInteractor$special$$inlined$flatMapLatest$1(null, sceneInteractor, alternateBouncerInteractor, keyguardQuickAffordanceInteractor, pulseExpansionInteractor, shadeInteractor)), "canBypass");
    }
}

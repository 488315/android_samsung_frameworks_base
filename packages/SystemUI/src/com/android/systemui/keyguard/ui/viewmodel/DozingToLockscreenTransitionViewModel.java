package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DozingToLockscreenTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;

    public DozingToLockscreenTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromDozingTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m1961setupVtjQ1oo = keyguardTransitionAnimationFlow.m1961setupVtjQ1oo(FromDozingTransitionInteractor.TO_LOCKSCREEN_DURATION, KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.DOZING, KeyguardState.LOCKSCREEN));
        Duration.Companion companion = Duration.Companion;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m1962sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m1961setupVtjQ1oo, DurationKt.toDuration(150, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DozingToLockscreenTransitionViewModel$shortcutsAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(((Number) obj).floatValue());
            }
        }, 0L, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DozingToLockscreenTransitionViewModel$shortcutsAlpha$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState);
        this.shortcutsAlpha = m1962sharedFlow74qcysc$default;
        this.lockscreenAlpha = m1962sharedFlow74qcysc$default;
        this.deviceEntryBackgroundViewAlpha = m1961setupVtjQ1oo.immediatelyTransitionTo(1.0f);
        this.deviceEntryParentViewAlpha = m1961setupVtjQ1oo.immediatelyTransitionTo(1.0f);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}

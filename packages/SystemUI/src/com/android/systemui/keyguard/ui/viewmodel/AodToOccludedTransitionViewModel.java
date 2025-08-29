package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class AodToOccludedTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public AodToOccludedTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromAodTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM2613setupVtjQ1oo = keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(FromAodTransitionInteractor.TO_OCCLUDED_DURATION, KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.AOD, KeyguardState.OCCLUDED));
        this.transitionAnimation = flowBuilderM2613setupVtjQ1oo;
        this.deviceEntryParentViewAlpha = flowBuilderM2613setupVtjQ1oo.immediatelyTransitionTo(0.0f);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }

    public final Flow lockscreenAlpha(final ViewStateAccessor viewStateAccessor) {
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit);
        long duration2 = DurationKt.toDuration(0, durationUnit);
        return KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(this.transitionAnimation, duration, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToOccludedTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(ref$FloatRef.element, 0.0f, ((Float) obj).floatValue()));
            }
        }, duration2, new Function0(viewStateAccessor) { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToOccludedTransitionViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                this.f$0.element = 0.0f;
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
    }
}

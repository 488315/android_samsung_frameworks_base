package com.android.systemui.util.kotlin;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$4;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class FlowKt {
    public static final SafeFlow pairwise(ReadonlyStateFlow readonlyStateFlow) {
        return new SafeFlow(new FlowKt$pairwiseBy$1(readonlyStateFlow, FlowKt$pairwise$2.INSTANCE, null));
    }

    public static final SafeFlow pairwiseBy(Flow flow, Function1 function1, Function3 function3) {
        return new SafeFlow(new FlowKt$pairwiseBy$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$pairwiseBy$3(function1, null), flow), function3, null));
    }

    public static final SafeFlow sample(Flow flow, Flow flow2, Function3 function3) {
        return new SafeFlow(new FlowKt$sample$1(flow, flow2, function3, null));
    }

    public static final SafeFlow sample(KeyguardTransitionInteractor$special$$inlined$filter$4 keyguardTransitionInteractor$special$$inlined$filter$4, Flow flow) {
        return sample(keyguardTransitionInteractor$special$$inlined$filter$4, flow, new FlowKt$sample$2(null));
    }

    public static final SafeFlow pairwise(Flow flow, CurrentTilesInteractorImpl.UserAndTiles userAndTiles) {
        return new SafeFlow(new FlowKt$pairwiseBy$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$pairwiseBy$2(userAndTiles, null), flow), FlowKt$pairwise$4.INSTANCE, null));
    }
}

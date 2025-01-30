package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1", m277f = "DeviceBasedSatelliteRepositoryImpl.kt", m278l = {190}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1 */
/* loaded from: classes2.dex */
public final class C3341x625b3972 extends SuspendLambda implements Function3 {
    final /* synthetic */ Flow $orElse$inlined;
    final /* synthetic */ Flow $supported$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C3341x625b3972(Continuation continuation, Flow flow, Flow flow2) {
        super(3, continuation);
        this.$supported$inlined = flow;
        this.$orElse$inlined = flow2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        C3341x625b3972 c3341x625b3972 = new C3341x625b3972((Continuation) obj3, this.$supported$inlined, this.$orElse$inlined);
        c3341x625b3972.L$0 = (FlowCollector) obj;
        c3341x625b3972.L$1 = obj2;
        return c3341x625b3972.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = ((SatelliteSupport) this.L$1) instanceof SatelliteSupport.Supported ? this.$supported$inlined : this.$orElse$inlined;
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}

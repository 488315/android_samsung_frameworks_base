package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 implements Flow {
    public final /* synthetic */ Flow $flow$inlined;
    public final /* synthetic */ Flow $this_combine$inlined;
    public final /* synthetic */ Function3 $transform$inlined;

    public FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(Flow flow, Flow flow2, Function3 function3) {
        this.$this_combine$inlined = flow;
        this.$flow$inlined = flow2;
        this.$transform$inlined = function3;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object combineInternal = CombineKt.combineInternal(new Flow[]{this.$this_combine$inlined, this.$flow$inlined}, FlowKt__ZipKt$nullArrayFactory$1.INSTANCE, new FlowKt__ZipKt$combine$1$1(this.$transform$inlined, null), flowCollector, continuation);
        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
    }
}

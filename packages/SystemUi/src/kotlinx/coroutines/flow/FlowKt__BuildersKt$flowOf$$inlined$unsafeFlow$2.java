package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 implements Flow {
    public final /* synthetic */ Object $value$inlined;

    public FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Object obj) {
        this.$value$inlined = obj;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object emit = flowCollector.emit(this.$value$inlined, continuation);
        return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
    }
}

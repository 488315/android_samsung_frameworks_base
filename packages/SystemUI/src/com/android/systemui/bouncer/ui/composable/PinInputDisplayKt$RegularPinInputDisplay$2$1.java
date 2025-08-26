package com.android.systemui.bouncer.ui.composable;

import androidx.compose.runtime.SnapshotStateKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes.dex */
final class PinInputDisplayKt$RegularPinInputDisplay$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ PinInputRow $pinInputRow;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinInputDisplayKt$RegularPinInputDisplay$2$1(PinInputRow pinInputRow, Continuation continuation) {
        super(2, continuation);
        this.$pinInputRow = pinInputRow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PinInputDisplayKt$RegularPinInputDisplay$2$1(this.$pinInputRow, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PinInputDisplayKt$RegularPinInputDisplay$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SafeFlow safeFlowSnapshotFlow = SnapshotStateKt.snapshotFlow(new PinInputDisplayKt$$ExternalSyntheticLambda4(this.$pinInputRow, 1));
            final PinInputRow pinInputRow = this.$pinInputRow;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$RegularPinInputDisplay$2$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    if (((Boolean) obj2).booleanValue()) {
                        CollectionsKt__MutableCollectionsKt.removeAll(pinInputRow.entries, new PinInputRow$$ExternalSyntheticLambda0());
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (safeFlowSnapshotFlow.collect(flowCollector, this) == coroutineSingletons) {
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

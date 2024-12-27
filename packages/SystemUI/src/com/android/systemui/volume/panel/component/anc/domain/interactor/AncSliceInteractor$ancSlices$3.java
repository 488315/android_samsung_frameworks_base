package com.android.systemui.volume.panel.component.anc.domain.interactor;

import androidx.slice.Slice;
import com.android.systemui.volume.panel.component.anc.domain.model.AncSlices;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class AncSliceInteractor$ancSlices$3 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public AncSliceInteractor$ancSlices$3(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AncSliceInteractor$ancSlices$3 ancSliceInteractor$ancSlices$3 = new AncSliceInteractor$ancSlices$3((Continuation) obj3);
        ancSliceInteractor$ancSlices$3.L$0 = (Slice) obj;
        ancSliceInteractor$ancSlices$3.L$1 = (Slice) obj2;
        return ancSliceInteractor$ancSlices$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Slice slice = (Slice) this.L$0;
        Slice slice2 = (Slice) this.L$1;
        return (slice == null || slice2 == null) ? AncSlices.Unavailable.INSTANCE : new AncSlices.Ready(slice2, slice);
    }
}

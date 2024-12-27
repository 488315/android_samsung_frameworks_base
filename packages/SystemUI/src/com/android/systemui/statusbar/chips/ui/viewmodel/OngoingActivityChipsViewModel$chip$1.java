package com.android.systemui.statusbar.chips.ui.viewmodel;

import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class OngoingActivityChipsViewModel$chip$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    int label;

    public OngoingActivityChipsViewModel$chip$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        OngoingActivityChipsViewModel$chip$1 ongoingActivityChipsViewModel$chip$1 = new OngoingActivityChipsViewModel$chip$1((Continuation) obj5);
        ongoingActivityChipsViewModel$chip$1.L$0 = (OngoingActivityChipModel) obj;
        ongoingActivityChipsViewModel$chip$1.L$1 = (OngoingActivityChipModel) obj2;
        ongoingActivityChipsViewModel$chip$1.L$2 = (OngoingActivityChipModel) obj3;
        ongoingActivityChipsViewModel$chip$1.L$3 = (OngoingActivityChipModel) obj4;
        return ongoingActivityChipsViewModel$chip$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        OngoingActivityChipModel ongoingActivityChipModel = (OngoingActivityChipModel) this.L$0;
        OngoingActivityChipModel ongoingActivityChipModel2 = (OngoingActivityChipModel) this.L$1;
        OngoingActivityChipModel ongoingActivityChipModel3 = (OngoingActivityChipModel) this.L$2;
        return ongoingActivityChipModel instanceof OngoingActivityChipModel.Shown ? ongoingActivityChipModel : ongoingActivityChipModel2 instanceof OngoingActivityChipModel.Shown ? ongoingActivityChipModel2 : ongoingActivityChipModel3 instanceof OngoingActivityChipModel.Shown ? ongoingActivityChipModel3 : (OngoingActivityChipModel) this.L$3;
    }
}

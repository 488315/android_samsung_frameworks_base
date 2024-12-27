package com.android.systemui.qs.tiles.base.viewmodel;

import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SharedFlowImpl;

final class QSTileViewModelImpl$onActionPerformed$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ QSTileUserAction $userAction;
    int label;
    final /* synthetic */ QSTileViewModelImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSTileViewModelImpl$onActionPerformed$1(QSTileViewModelImpl qSTileViewModelImpl, QSTileUserAction qSTileUserAction, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSTileViewModelImpl;
        this.$userAction = qSTileUserAction;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new QSTileViewModelImpl$onActionPerformed$1(this.this$0, this.$userAction, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSTileViewModelImpl$onActionPerformed$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SharedFlowImpl sharedFlowImpl = this.this$0.userInputs;
            QSTileUserAction qSTileUserAction = this.$userAction;
            this.label = 1;
            if (sharedFlowImpl.emit(qSTileUserAction, this) == coroutineSingletons) {
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

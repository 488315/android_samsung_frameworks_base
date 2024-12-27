package com.android.systemui.qs.tiles.base.viewmodel;

import com.android.systemui.qs.tiles.base.interactor.DataUpdateTrigger;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class QSTileViewModelImpl$userInputFlow$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QSTileViewModelImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSTileViewModelImpl$userInputFlow$2(QSTileViewModelImpl qSTileViewModelImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSTileViewModelImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QSTileViewModelImpl$userInputFlow$2 qSTileViewModelImpl$userInputFlow$2 = new QSTileViewModelImpl$userInputFlow$2(this.this$0, continuation);
        qSTileViewModelImpl$userInputFlow$2.L$0 = obj;
        return qSTileViewModelImpl$userInputFlow$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSTileViewModelImpl$userInputFlow$2) create((DataUpdateTrigger.UserInput) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DataUpdateTrigger.UserInput userInput = (DataUpdateTrigger.UserInput) this.L$0;
            QSTileUserActionInteractor qSTileUserActionInteractor = (QSTileUserActionInteractor) this.this$0.userActionInteractor.invoke();
            QSTileInput qSTileInput = userInput.input;
            this.label = 1;
            if (qSTileUserActionInteractor.handleInput(qSTileInput, this) == coroutineSingletons) {
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

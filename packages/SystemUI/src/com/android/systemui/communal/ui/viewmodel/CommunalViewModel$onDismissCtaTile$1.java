package com.android.systemui.communal.ui.viewmodel;

import com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.ui.viewmodel.PopupType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CommunalViewModel$onDismissCtaTile$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalViewModel$onDismissCtaTile$1(CommunalViewModel communalViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalViewModel$onDismissCtaTile$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalViewModel$onDismissCtaTile$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CommunalInteractor communalInteractor = this.this$0.communalInteractor;
            this.label = 1;
            Object ctaDismissedForCurrentUser = ((CommunalPrefsRepositoryImpl) communalInteractor.communalPrefsRepository).setCtaDismissedForCurrentUser(this);
            if (ctaDismissedForCurrentUser != coroutineSingletons) {
                ctaDismissedForCurrentUser = Unit.INSTANCE;
            }
            if (ctaDismissedForCurrentUser == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        CommunalViewModel communalViewModel = this.this$0;
        PopupType.CtaTile ctaTile = PopupType.CtaTile.INSTANCE;
        int i2 = CommunalViewModel.$r8$clinit;
        communalViewModel.setCurrentPopupType(ctaTile);
        return Unit.INSTANCE;
    }
}

package com.android.systemui.communal.widgets;

import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class CommunalAppWidgetHostStartable$start$6 extends SuspendLambda implements Function2 {
    /* synthetic */ int I$0;
    int label;
    final /* synthetic */ CommunalAppWidgetHostStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalAppWidgetHostStartable$start$6(CommunalAppWidgetHostStartable communalAppWidgetHostStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalAppWidgetHostStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalAppWidgetHostStartable$start$6 communalAppWidgetHostStartable$start$6 = new CommunalAppWidgetHostStartable$start$6(this.this$0, continuation);
        communalAppWidgetHostStartable$start$6.I$0 = ((Number) obj).intValue();
        return communalAppWidgetHostStartable$start$6;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalAppWidgetHostStartable$start$6) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((CommunalWidgetRepositoryImpl) this.this$0.communalInteractor.widgetRepository).deleteWidget(this.I$0);
        return Unit.INSTANCE;
    }
}

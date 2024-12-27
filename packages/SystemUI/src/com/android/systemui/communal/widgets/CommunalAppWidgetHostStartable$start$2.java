package com.android.systemui.communal.widgets;

import com.android.systemui.util.kotlin.WithPrev;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;

final class CommunalAppWidgetHostStartable$start$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalAppWidgetHostStartable this$0;

    public CommunalAppWidgetHostStartable$start$2(CommunalAppWidgetHostStartable communalAppWidgetHostStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalAppWidgetHostStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalAppWidgetHostStartable$start$2 communalAppWidgetHostStartable$start$2 = new CommunalAppWidgetHostStartable$start$2(this.this$0, continuation);
        communalAppWidgetHostStartable$start$2.L$0 = obj;
        return communalAppWidgetHostStartable$start$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalAppWidgetHostStartable$start$2) create((WithPrev) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            boolean booleanValue = ((Boolean) ((WithPrev) this.L$0).component2()).booleanValue();
            CommunalAppWidgetHostStartable communalAppWidgetHostStartable = this.this$0;
            this.label = 1;
            communalAppWidgetHostStartable.getClass();
            Object withContext = BuildersKt.withContext(communalAppWidgetHostStartable.uiDispatcher, new CommunalAppWidgetHostStartable$updateAppWidgetHostActive$2(booleanValue, communalAppWidgetHostStartable, null), this);
            if (withContext != obj2) {
                withContext = Unit.INSTANCE;
            }
            if (withContext == obj2) {
                return obj2;
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

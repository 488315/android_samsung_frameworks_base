package com.android.systemui.communal.widgets;

import com.android.systemui.communal.widgets.CommunalAppWidgetHost;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class CommunalAppWidgetHost$startListening$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalAppWidgetHost this$0;

    public CommunalAppWidgetHost$startListening$1(CommunalAppWidgetHost communalAppWidgetHost, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalAppWidgetHost;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalAppWidgetHost$startListening$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalAppWidgetHost$startListening$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CommunalAppWidgetHost communalAppWidgetHost = this.this$0;
        synchronized (communalAppWidgetHost.observers) {
            Iterator it = communalAppWidgetHost.observers.iterator();
            while (it.hasNext()) {
                ((CommunalWidgetHost) ((CommunalAppWidgetHost.Observer) it.next())).refreshProviders();
            }
        }
        return Unit.INSTANCE;
    }
}

package com.android.systemui.communal.widgets;

import com.android.systemui.communal.widgets.CommunalAppWidgetHost;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;

final class CommunalAppWidgetHost$deleteAppWidgetId$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $appWidgetId;
    int label;
    final /* synthetic */ CommunalAppWidgetHost this$0;

    public CommunalAppWidgetHost$deleteAppWidgetId$1(CommunalAppWidgetHost communalAppWidgetHost, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalAppWidgetHost;
        this.$appWidgetId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalAppWidgetHost$deleteAppWidgetId$1(this.this$0, this.$appWidgetId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalAppWidgetHost$deleteAppWidgetId$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CommunalAppWidgetHost communalAppWidgetHost = this.this$0;
        Set set = communalAppWidgetHost.observers;
        int i = this.$appWidgetId;
        synchronized (set) {
            Iterator it = communalAppWidgetHost.observers.iterator();
            while (it.hasNext()) {
                CommunalWidgetHost communalWidgetHost = (CommunalWidgetHost) ((CommunalAppWidgetHost.Observer) it.next());
                communalWidgetHost.appWidgetHost.removeListener(i);
                StateFlowImpl stateFlowImpl = communalWidgetHost._appWidgetProviders;
                LinkedHashMap linkedHashMap = new LinkedHashMap((Map) stateFlowImpl.getValue());
                linkedHashMap.remove(Integer.valueOf(i));
                stateFlowImpl.updateState(null, linkedHashMap);
            }
        }
        return Unit.INSTANCE;
    }
}

package com.android.systemui.communal.data.repository;

import android.appwidget.AppWidgetProviderInfo;
import com.android.systemui.communal.data.db.CommunalItemRank;
import com.android.systemui.communal.data.db.CommunalWidgetItem;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl;
import java.util.ArrayList;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CommunalWidgetRepositoryImpl$widgetEntries$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public CommunalWidgetRepositoryImpl$widgetEntries$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalWidgetRepositoryImpl$widgetEntries$1 communalWidgetRepositoryImpl$widgetEntries$1 = new CommunalWidgetRepositoryImpl$widgetEntries$1((Continuation) obj3);
        communalWidgetRepositoryImpl$widgetEntries$1.L$0 = (Map) obj;
        communalWidgetRepositoryImpl$widgetEntries$1.L$1 = (Map) obj2;
        return communalWidgetRepositoryImpl$widgetEntries$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Map map = (Map) this.L$0;
        Map map2 = (Map) this.L$1;
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : map.entrySet()) {
            CommunalItemRank communalItemRank = (CommunalItemRank) entry.getKey();
            CommunalWidgetItem communalWidgetItem = (CommunalWidgetItem) entry.getValue();
            int i = communalWidgetItem.widgetId;
            arrayList.add(new CommunalWidgetRepositoryImpl.CommunalWidgetEntry(i, communalWidgetItem.componentName, communalItemRank.rank, (AppWidgetProviderInfo) map2.get(new Integer(i))));
        }
        return arrayList;
    }
}

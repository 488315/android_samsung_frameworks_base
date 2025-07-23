package com.android.systemui.statusbar.notification.promoted.domain.interactor;

import com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class PromotedNotificationsInteractor$orderedChipNotifications$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    int label;

    public PromotedNotificationsInteractor$orderedChipNotifications$1(Continuation continuation) {
        super(5, continuation);
    }

    public static final void invokeSuspend$addToList(List list, List list2, PromotedNotificationsInteractor.NotifAndPromotedContent notifAndPromotedContent) {
        if (notifAndPromotedContent != null) {
            ArrayList arrayList = (ArrayList) list;
            String str = notifAndPromotedContent.key;
            if (arrayList.contains(str)) {
                return;
            }
            ((ArrayList) list2).add(notifAndPromotedContent);
            arrayList.add(str);
        }
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        PromotedNotificationsInteractor$orderedChipNotifications$1 promotedNotificationsInteractor$orderedChipNotifications$1 = new PromotedNotificationsInteractor$orderedChipNotifications$1((Continuation) obj5);
        promotedNotificationsInteractor$orderedChipNotifications$1.L$0 = (PromotedNotificationsInteractor.NotifAndPromotedContent) obj;
        promotedNotificationsInteractor$orderedChipNotifications$1.L$1 = (PromotedNotificationsInteractor.NotifAndPromotedContent) obj2;
        promotedNotificationsInteractor$orderedChipNotifications$1.L$2 = (PromotedNotificationsInteractor.NotifAndPromotedContent) obj3;
        promotedNotificationsInteractor$orderedChipNotifications$1.L$3 = (List) obj4;
        return promotedNotificationsInteractor$orderedChipNotifications$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PromotedNotificationsInteractor.NotifAndPromotedContent notifAndPromotedContent = (PromotedNotificationsInteractor.NotifAndPromotedContent) this.L$0;
        PromotedNotificationsInteractor.NotifAndPromotedContent notifAndPromotedContent2 = (PromotedNotificationsInteractor.NotifAndPromotedContent) this.L$1;
        PromotedNotificationsInteractor.NotifAndPromotedContent notifAndPromotedContent3 = (PromotedNotificationsInteractor.NotifAndPromotedContent) this.L$2;
        List list = (List) this.L$3;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        invokeSuspend$addToList(arrayList2, arrayList, notifAndPromotedContent);
        invokeSuspend$addToList(arrayList2, arrayList, notifAndPromotedContent2);
        invokeSuspend$addToList(arrayList2, arrayList, notifAndPromotedContent3);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            invokeSuspend$addToList(arrayList2, arrayList, (PromotedNotificationsInteractor.NotifAndPromotedContent) it.next());
        }
        return arrayList;
    }
}

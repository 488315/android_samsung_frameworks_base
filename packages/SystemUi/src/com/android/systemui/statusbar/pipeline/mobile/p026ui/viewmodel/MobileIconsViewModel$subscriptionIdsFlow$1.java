package com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel;

import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$subscriptionIdsFlow$1", m277f = "MobileIconsViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconsViewModel$subscriptionIdsFlow$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public MobileIconsViewModel$subscriptionIdsFlow$1(Continuation<? super MobileIconsViewModel$subscriptionIdsFlow$1> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileIconsViewModel$subscriptionIdsFlow$1 mobileIconsViewModel$subscriptionIdsFlow$1 = new MobileIconsViewModel$subscriptionIdsFlow$1(continuation);
        mobileIconsViewModel$subscriptionIdsFlow$1.L$0 = obj;
        return mobileIconsViewModel$subscriptionIdsFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileIconsViewModel$subscriptionIdsFlow$1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new Integer(((SubscriptionModel) it.next()).subscriptionId));
        }
        return arrayList;
    }
}

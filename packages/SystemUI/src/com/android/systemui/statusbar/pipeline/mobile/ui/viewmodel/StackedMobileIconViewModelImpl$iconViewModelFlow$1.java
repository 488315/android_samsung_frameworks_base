package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import java.util.Comparator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class StackedMobileIconViewModelImpl$iconViewModelFlow$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public StackedMobileIconViewModelImpl$iconViewModelFlow$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        StackedMobileIconViewModelImpl$iconViewModelFlow$1 stackedMobileIconViewModelImpl$iconViewModelFlow$1 = new StackedMobileIconViewModelImpl$iconViewModelFlow$1((Continuation) obj3);
        stackedMobileIconViewModelImpl$iconViewModelFlow$1.L$0 = (List) obj;
        stackedMobileIconViewModelImpl$iconViewModelFlow$1.L$1 = (Integer) obj2;
        return stackedMobileIconViewModelImpl$iconViewModelFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        final Integer num = (Integer) this.L$1;
        return CollectionsKt___CollectionsKt.sortedWith(list, new Comparator() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$iconViewModelFlow$1$invokeSuspend$$inlined$sortedByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj2, Object obj3) {
                int subscriptionId = ((MobileIconViewModelCommon) obj3).getSubscriptionId();
                Integer num2 = num;
                boolean z = false;
                Boolean boolValueOf = Boolean.valueOf(num2 != null && subscriptionId == num2.intValue());
                int subscriptionId2 = ((MobileIconViewModelCommon) obj2).getSubscriptionId();
                Integer num3 = num;
                if (num3 != null && subscriptionId2 == num3.intValue()) {
                    z = true;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(boolValueOf, Boolean.valueOf(z));
            }
        });
    }
}

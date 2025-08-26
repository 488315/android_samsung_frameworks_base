package com.android.systemui.shared.condition;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
final class CombinedCondition$lazilyEvaluate$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Collection<Condition> $conditions;
    final /* synthetic */ boolean $filterUnknown;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CombinedCondition this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public CombinedCondition$lazilyEvaluate$1(Collection<? extends Condition> collection, boolean z, CombinedCondition combinedCondition, Continuation continuation) {
        super(2, continuation);
        this.$conditions = collection;
        this.$filterUnknown = z;
        this.this$0 = combinedCondition;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void invokeSuspend$cancelAllExcept(List list, Collection collection, List list2, int i) {
        Object obj;
        int size = list.size();
        for (final int i2 = 0; i2 < size; i2++) {
            if (i2 != i) {
                if (i == -1) {
                    Job job = (Job) list.get(i2);
                    if (job != null) {
                        job.cancel(null);
                    }
                    list.set(i2, null);
                    list2.set(i2, null);
                } else {
                    Collection collection2 = collection;
                    boolean z = collection2 instanceof List;
                    if (z) {
                        obj = ((List) collection2).get(i2);
                    } else {
                        Function1 function1 = new Function1() { // from class: kotlin.collections.CollectionsKt___CollectionsKt$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                ((Integer) obj2).intValue();
                                throw new IndexOutOfBoundsException(BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("Collection doesn't contain element at index "), i2, '.'));
                            }
                        };
                        if (!z) {
                            if (i2 < 0) {
                                function1.mo781invoke(Integer.valueOf(i2));
                                throw null;
                            }
                            int i3 = 0;
                            for (Object obj2 : collection2) {
                                int i4 = i3 + 1;
                                if (i2 == i3) {
                                    obj = obj2;
                                } else {
                                    i3 = i4;
                                }
                            }
                            function1.mo781invoke(Integer.valueOf(i2));
                            throw null;
                        }
                        List list3 = (List) collection2;
                        if (i2 < 0 || i2 >= list3.size()) {
                            function1.mo781invoke(Integer.valueOf(i2));
                            throw null;
                        }
                        obj = list3.get(i2);
                    }
                    if (((Condition) obj).getStartStrategy() == 2) {
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void invokeSuspend$collectFlow(List list, int i, CombinedCondition combinedCondition, ProducerScope producerScope, List list2, Collection collection, List list3, boolean z) {
        Boolean boolThreeValuedAndOrOr = null;
        if (!list.isEmpty() && i != -1) {
            list3.set(i, BuildersKt.launch$default(combinedCondition.scope, null, null, new CombinedCondition$lazilyEvaluate$1$collectFlow$1(list, i, list2, combinedCondition, producerScope, list3, collection, z, null), 3));
            return;
        }
        List listFilterNotNull = z ? CollectionsKt___CollectionsKt.filterNotNull(list2) : list2;
        Evaluator evaluator = Evaluator.INSTANCE;
        List list4 = listFilterNotNull;
        int i2 = combinedCondition.operand;
        evaluator.getClass();
        if (!list4.isEmpty()) {
            if (i2 == 0) {
                boolThreeValuedAndOrOr = Evaluator.threeValuedAndOrOr(list4, false);
            } else if (i2 == 1) {
                boolThreeValuedAndOrOr = Evaluator.threeValuedAndOrOr(list4, true);
            }
        }
        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(boolThreeValuedAndOrOr);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CombinedCondition$lazilyEvaluate$1 combinedCondition$lazilyEvaluate$1 = new CombinedCondition$lazilyEvaluate$1(this.$conditions, this.$filterUnknown, this.this$0, continuation);
        combinedCondition$lazilyEvaluate$1.L$0 = obj;
        return combinedCondition$lazilyEvaluate$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CombinedCondition$lazilyEvaluate$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            int size = this.$conditions.size();
            final ArrayList arrayList = new ArrayList(size);
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(null);
            }
            int size2 = this.$conditions.size();
            final ArrayList arrayList2 = new ArrayList(size2);
            for (int i3 = 0; i3 < size2; i3++) {
                arrayList2.add(null);
            }
            Collection<Condition> collection = this.$conditions;
            ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(collection, 10));
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                arrayList3.add(FlowKt.distinctUntilChanged(FlowKt.callbackFlow(new ConditionExtensionsKt$toFlow$1((Condition) it.next(), null))));
            }
            Iterator<T> it2 = this.$conditions.iterator();
            int i4 = 0;
            while (it2.hasNext()) {
                int i5 = i4 + 1;
                if (((Condition) it2.next()).getStartStrategy() == 0) {
                    invokeSuspend$collectFlow(arrayList3, i4, this.this$0, producerScope, arrayList2, this.$conditions, arrayList, this.$filterUnknown);
                    z = true;
                }
                i4 = i5;
            }
            if (!z) {
                invokeSuspend$collectFlow(arrayList3, 0, this.this$0, producerScope, arrayList2, this.$conditions, arrayList, this.$filterUnknown);
            }
            final Collection<Condition> collection2 = this.$conditions;
            Function0 function0 = new Function0() { // from class: com.android.systemui.shared.condition.CombinedCondition$lazilyEvaluate$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    CombinedCondition$lazilyEvaluate$1.invokeSuspend$cancelAllExcept(arrayList, collection2, arrayList2, -1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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

package com.android.app.displaylib;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class DisplayRepositoryImpl$mapElementsLazily$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Function1 $createValue;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplayRepositoryImpl$mapElementsLazily$1(Function1 function1, Continuation continuation) {
        super(3, continuation);
        this.$createValue = function1;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DisplayRepositoryImpl$mapElementsLazily$1 displayRepositoryImpl$mapElementsLazily$1 = new DisplayRepositoryImpl$mapElementsLazily$1(this.$createValue, (Continuation) obj3);
        displayRepositoryImpl$mapElementsLazily$1.L$0 = (DisplayRepositoryImpl$mapElementsLazily$State) obj;
        displayRepositoryImpl$mapElementsLazily$1.L$1 = (Set) obj2;
        return displayRepositoryImpl$mapElementsLazily$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DisplayRepositoryImpl$mapElementsLazily$State displayRepositoryImpl$mapElementsLazily$State = (DisplayRepositoryImpl$mapElementsLazily$State) this.L$0;
        Set set = (Set) this.L$1;
        if (Intrinsics.areEqual(set, displayRepositoryImpl$mapElementsLazily$State.previousSet)) {
            return displayRepositoryImpl$mapElementsLazily$State;
        }
        Set setMinus = SetsKt___SetsKt.minus(displayRepositoryImpl$mapElementsLazily$State.previousSet, (Iterable) set);
        Set setMinus2 = SetsKt___SetsKt.minus(set, (Iterable) displayRepositoryImpl$mapElementsLazily$State.previousSet);
        LinkedHashMap linkedHashMap = new LinkedHashMap(displayRepositoryImpl$mapElementsLazily$State.valueMap);
        Function1 function1 = this.$createValue;
        for (Object obj2 : setMinus2) {
            Object objMo781invoke = function1.mo781invoke(obj2);
            if (objMo781invoke != null) {
                linkedHashMap.put(obj2, objMo781invoke);
            }
        }
        Iterator it = setMinus.iterator();
        while (it.hasNext()) {
            linkedHashMap.remove(it.next());
        }
        return new DisplayRepositoryImpl$mapElementsLazily$State(set, linkedHashMap, CollectionsKt___CollectionsKt.toSet(linkedHashMap.values()));
    }
}

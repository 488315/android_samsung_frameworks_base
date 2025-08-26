package com.android.systemui.kairos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class BuildScope$toStateFlow$2 implements StateFlow {
    public final /* synthetic */ MutableStateFlow $innerStateFlow;

    public BuildScope$toStateFlow$2(MutableStateFlow mutableStateFlow) {
        this.$innerStateFlow = mutableStateFlow;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object collect(final FlowCollector flowCollector, Continuation continuation) {
        BuildScope$toStateFlow$2$collect$1 buildScope$toStateFlow$2$collect$1;
        if (continuation instanceof BuildScope$toStateFlow$2$collect$1) {
            buildScope$toStateFlow$2$collect$1 = (BuildScope$toStateFlow$2$collect$1) continuation;
            int i = buildScope$toStateFlow$2$collect$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                buildScope$toStateFlow$2$collect$1.label = i - Integer.MIN_VALUE;
            } else {
                buildScope$toStateFlow$2$collect$1 = new BuildScope$toStateFlow$2$collect$1(this, continuation);
            }
        }
        Object obj = buildScope$toStateFlow$2$collect$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = buildScope$toStateFlow$2$collect$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector2 = new FlowCollector() { // from class: com.android.systemui.kairos.BuildScope$toStateFlow$2$collect$2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation2) {
                    Object objEmit = flowCollector.emit(((DeferredValue) obj2).unwrapped.getValue(), continuation2);
                    return objEmit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEmit : Unit.INSTANCE;
                }
            };
            buildScope$toStateFlow$2$collect$1.label = 1;
            if (this.$innerStateFlow.collect(flowCollector2, buildScope$toStateFlow$2$collect$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }

    @Override // kotlinx.coroutines.flow.SharedFlow
    public final List getReplayCache() {
        List replayCache = this.$innerStateFlow.getReplayCache();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(replayCache, 10));
        Iterator it = replayCache.iterator();
        while (it.hasNext()) {
            arrayList.add(((DeferredValue) it.next()).unwrapped.getValue());
        }
        return arrayList;
    }

    @Override // kotlinx.coroutines.flow.StateFlow
    public final Object getValue() {
        return ((DeferredValue) this.$innerStateFlow.getValue()).unwrapped.getValue();
    }
}

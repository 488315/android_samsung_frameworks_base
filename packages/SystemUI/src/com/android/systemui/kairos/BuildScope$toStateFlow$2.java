package com.android.systemui.kairos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BuildScope$toStateFlow$2 implements StateFlow {
    public final /* synthetic */ MutableStateFlow $innerStateFlow;

    public BuildScope$toStateFlow$2(MutableStateFlow mutableStateFlow) {
        this.$innerStateFlow = mutableStateFlow;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object collect(final kotlinx.coroutines.flow.FlowCollector r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.kairos.BuildScope$toStateFlow$2$collect$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.kairos.BuildScope$toStateFlow$2$collect$1 r0 = (com.android.systemui.kairos.BuildScope$toStateFlow$2$collect$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.kairos.BuildScope$toStateFlow$2$collect$1 r0 = new com.android.systemui.kairos.BuildScope$toStateFlow$2$collect$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L42
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.kairos.BuildScope$toStateFlow$2$collect$2 r6 = new com.android.systemui.kairos.BuildScope$toStateFlow$2$collect$2
            r6.<init>()
            r0.label = r3
            kotlinx.coroutines.flow.MutableStateFlow r4 = r4.$innerStateFlow
            java.lang.Object r4 = r4.collect(r6, r0)
            if (r4 != r1) goto L42
            return r1
        L42:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.BuildScope$toStateFlow$2.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
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

package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.util.Maybe;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final /* synthetic */ class MergeKt$$ExternalSyntheticLambda1 implements Function3 {
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Object objM2588boximpl;
        EvalScope evalScope = (EvalScope) obj;
        Map map = (Map) obj2;
        ((Integer) obj3).intValue();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map.size()));
        for (Map.Entry entry : map.entrySet()) {
            Object key = entry.getKey();
            Maybe maybe = (Maybe) entry.getValue();
            if (maybe instanceof Maybe.Present) {
                Maybe.Companion companion = Maybe.Companion;
                EventsImpl eventsImpl = (EventsImpl) EventsKt.getInit((Events) ((Maybe.Present) maybe).value).connect(evalScope);
                companion.getClass();
                objM2588boximpl = Maybe.Present.m2588boximpl(eventsImpl);
            } else {
                if (!(maybe instanceof Maybe.Absent)) {
                    throw new NoWhenBranchMatchedException();
                }
                objM2588boximpl = Maybe.Absent.INSTANCE;
            }
            linkedHashMap.put(key, objM2588boximpl);
        }
        return linkedHashMap.entrySet();
    }
}

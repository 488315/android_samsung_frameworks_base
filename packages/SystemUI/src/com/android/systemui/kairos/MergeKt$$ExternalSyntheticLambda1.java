package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.util.Maybe;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MergeKt$$ExternalSyntheticLambda1 implements Function3 {
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Object obj4;
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
                obj4 = Maybe.Present.m2573boximpl(eventsImpl);
            } else {
                if (!(maybe instanceof Maybe.Absent)) {
                    throw new NoWhenBranchMatchedException();
                }
                obj4 = Maybe.Absent.INSTANCE;
            }
            linkedHashMap.put(key, obj4);
        }
        return linkedHashMap.entrySet();
    }
}

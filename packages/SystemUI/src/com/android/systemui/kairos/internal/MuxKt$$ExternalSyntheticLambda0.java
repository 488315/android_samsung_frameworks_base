package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.MapHolder;
import com.android.systemui.kairos.internal.store.MapK;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final /* synthetic */ class MuxKt$$ExternalSyntheticLambda0 implements Function3 {
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        EvalScope evalScope = (EvalScope) obj;
        ((Integer) obj3).getClass();
        Map map = ((MapHolder) ((MapK) obj2)).unwrapped;
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map.size()));
        for (Map.Entry entry : map.entrySet()) {
            linkedHashMap.put(entry.getKey(), ((PullNode) entry.getValue()).getPushEvent(evalScope));
        }
        return linkedHashMap;
    }
}

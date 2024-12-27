package com.android.systemui.media.mediaoutput.entity;

import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

public interface EntityString {
    List getAttributes();

    default String toLogText() {
        String simpleName = getClass().getSimpleName();
        List attributes = getAttributes();
        ArrayList arrayList = new ArrayList();
        for (Object obj : attributes) {
            if (((Pair) obj).getSecond() != null) {
                arrayList.add(obj);
            }
        }
        return simpleName + "(" + CollectionsKt___CollectionsKt.joinToString$default(arrayList, null, null, null, new Function1() { // from class: com.android.systemui.media.mediaoutput.entity.EntityString$toLogText$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                Pair pair = (Pair) obj2;
                return pair.getFirst() + "=" + pair.getSecond();
            }
        }, 31) + ")";
    }
}

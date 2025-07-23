package com.android.systemui.media.mediaoutput.entity;

import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        return simpleName + "(" + CollectionsKt___CollectionsKt.joinToString$default(arrayList, null, null, null, new EntityString$$ExternalSyntheticLambda0(), 31) + ")";
    }
}

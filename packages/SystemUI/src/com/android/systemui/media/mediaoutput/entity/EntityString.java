package com.android.systemui.media.mediaoutput.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes2.dex */
public interface EntityString {
    List getAttributes();

    default String toLogText() throws IOException {
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

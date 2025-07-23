package com.android.systemui.media.mediaoutput.ext;

import android.os.Bundle;
import java.util.LinkedHashMap;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class BundleExtKt {
    public static final String getSerialize(Bundle bundle) {
        Set<String> keySet;
        if (bundle == null || (keySet = bundle.keySet()) == null) {
            return "empty";
        }
        Set<String> set = keySet;
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        for (Object obj : set) {
            linkedHashMap.put(obj, bundle.get((String) obj));
        }
        String obj2 = linkedHashMap.toString();
        return obj2 == null ? "empty" : obj2;
    }
}

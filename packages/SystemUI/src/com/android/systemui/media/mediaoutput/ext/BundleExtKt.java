package com.android.systemui.media.mediaoutput.ext;

import android.os.Bundle;
import java.util.LinkedHashMap;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;

/* loaded from: classes2.dex */
public abstract class BundleExtKt {
    public static final String getSerialize(Bundle bundle) {
        Set<String> setKeySet;
        if (bundle == null || (setKeySet = bundle.keySet()) == null) {
            return "empty";
        }
        Set<String> set = setKeySet;
        int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
        if (iMapCapacity < 16) {
            iMapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
        for (Object obj : set) {
            linkedHashMap.put(obj, bundle.get((String) obj));
        }
        String string = linkedHashMap.toString();
        return string == null ? "empty" : string;
    }
}

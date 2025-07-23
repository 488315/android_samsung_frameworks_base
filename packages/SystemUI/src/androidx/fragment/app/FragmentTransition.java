package androidx.fragment.app;

import android.view.View;
import androidx.collection.ArrayMap;
import androidx.transition.FragmentTransitionSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FragmentTransition {
    public static final FragmentTransitionCompat21 PLATFORM_IMPL;
    public static final FragmentTransitionImpl SUPPORT_IMPL;

    static {
        new FragmentTransition();
        PLATFORM_IMPL = new FragmentTransitionCompat21();
        FragmentTransitionImpl fragmentTransitionImpl = null;
        try {
            Class[] clsArr = new Class[0];
            fragmentTransitionImpl = (FragmentTransitionImpl) FragmentTransitionSupport.class.getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
        }
        SUPPORT_IMPL = fragmentTransitionImpl;
    }

    private FragmentTransition() {
    }

    public static final String findKeyForValue(ArrayMap arrayMap, String str) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : arrayMap.entrySet()) {
            if (Intrinsics.areEqual(entry.getValue(), str)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add((String) ((Map.Entry) it.next()).getKey());
        }
        return (String) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList);
    }

    public static final void setViewVisibility(int i, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((View) it.next()).setVisibility(i);
        }
    }
}

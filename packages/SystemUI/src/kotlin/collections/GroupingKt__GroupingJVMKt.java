package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class GroupingKt__GroupingJVMKt {
    public static Map eachCount(Grouping grouping) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator sourceIterator = grouping.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object keyOf = grouping.keyOf(sourceIterator.next());
            Object obj = linkedHashMap.get(keyOf);
            if (obj == null && !linkedHashMap.containsKey(keyOf)) {
                obj = new Ref$IntRef();
            }
            Ref$IntRef ref$IntRef = (Ref$IntRef) obj;
            ref$IntRef.element++;
            linkedHashMap.put(keyOf, ref$IntRef);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            TypeIntrinsics.asMutableMapEntry(entry).setValue(Integer.valueOf(((Ref$IntRef) entry.getValue()).element));
        }
        return TypeIntrinsics.asMutableMap(linkedHashMap);
    }
}

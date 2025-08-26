package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.TypeIntrinsics;

/* loaded from: classes4.dex */
public class GroupingKt__GroupingJVMKt {
    public static Map eachCount(Grouping grouping) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator itSourceIterator = grouping.sourceIterator();
        while (itSourceIterator.hasNext()) {
            Object objKeyOf = grouping.keyOf(itSourceIterator.next());
            Object ref$IntRef = linkedHashMap.get(objKeyOf);
            if (ref$IntRef == null && !linkedHashMap.containsKey(objKeyOf)) {
                ref$IntRef = new Ref$IntRef();
            }
            Ref$IntRef ref$IntRef2 = (Ref$IntRef) ref$IntRef;
            ref$IntRef2.element++;
            linkedHashMap.put(objKeyOf, ref$IntRef2);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            TypeIntrinsics.asMutableMapEntry(entry).setValue(Integer.valueOf(((Ref$IntRef) entry.getValue()).element));
        }
        return TypeIntrinsics.asMutableMap(linkedHashMap);
    }
}

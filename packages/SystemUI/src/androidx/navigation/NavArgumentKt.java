package androidx.navigation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class NavArgumentKt {
    public static final List missingRequiredArguments(Map map, Function1 function1) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : ((LinkedHashMap) map).entrySet()) {
            NavArgument navArgument = (NavArgument) entry.getValue();
            Boolean valueOf = navArgument != null ? Boolean.valueOf(navArgument.isNullable) : null;
            valueOf.getClass();
            if (!valueOf.booleanValue() && !navArgument.isDefaultValuePresent) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        Set keySet = linkedHashMap.keySet();
        ArrayList arrayList = new ArrayList();
        for (Object obj : keySet) {
            if (((Boolean) function1.mo779invoke((String) obj)).booleanValue()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }
}

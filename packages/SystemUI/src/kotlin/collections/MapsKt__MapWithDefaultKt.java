package kotlin.collections;

import java.util.Map;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class MapsKt__MapWithDefaultKt {
    public static Map withDefault(Map map, Function1 function1) {
        return map instanceof MapWithDefaultImpl ? withDefault(((MapWithDefaultImpl) map).map, function1) : new MapWithDefaultImpl(map, function1);
    }
}

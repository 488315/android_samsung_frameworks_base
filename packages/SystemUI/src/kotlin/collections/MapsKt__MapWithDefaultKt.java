package kotlin.collections;

import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MapsKt__MapWithDefaultKt {
    public static Map withDefault(Map map, Function1 function1) {
        return map instanceof MapWithDefaultImpl ? withDefault(((MapWithDefaultImpl) map).map, function1) : new MapWithDefaultImpl(map, function1);
    }
}

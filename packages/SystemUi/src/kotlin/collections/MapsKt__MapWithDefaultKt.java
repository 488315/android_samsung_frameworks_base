package kotlin.collections;

import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class MapsKt__MapWithDefaultKt {
    public static final Map withDefault(Map map, Function1 function1) {
        return map instanceof MapWithDefaultImpl ? withDefault(((MapWithDefaultImpl) map).map, function1) : new MapWithDefaultImpl(map, function1);
    }
}

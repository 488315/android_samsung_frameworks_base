package kotlin.collections;

import java.util.Collections;
import java.util.Map;
import kotlin.Pair;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class MapsKt__MapsJVMKt extends MapsKt__MapWithDefaultKt {
    public static final int mapCapacity(int i) {
        if (i < 0) {
            return i;
        }
        if (i < 3) {
            return i + 1;
        }
        if (i < 1073741824) {
            return (int) ((i / 0.75f) + 1.0f);
        }
        return Integer.MAX_VALUE;
    }

    public static final Map mapOf(Pair pair) {
        return Collections.singletonMap(pair.getFirst(), pair.getSecond());
    }
}

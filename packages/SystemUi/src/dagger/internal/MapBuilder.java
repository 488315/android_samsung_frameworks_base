package dagger.internal;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MapBuilder {
    public final Map contributions;

    private MapBuilder(int i) {
        this.contributions = new LinkedHashMap(i < 3 ? i + 1 : i < 1073741824 ? (int) ((i / 0.75f) + 1.0f) : Integer.MAX_VALUE);
    }

    public static MapBuilder newMapBuilder(int i) {
        return new MapBuilder(i);
    }

    public final Map build() {
        Map map = this.contributions;
        return map.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(map);
    }
}

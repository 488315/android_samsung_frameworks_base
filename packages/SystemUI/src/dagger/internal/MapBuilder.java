package dagger.internal;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MapBuilder {
    public final Map contributions;

    private MapBuilder(int i) {
        this.contributions = new LinkedHashMap(i < 3 ? i + 1 : i < 1073741824 ? (int) ((i / 0.75f) + 1.0f) : Integer.MAX_VALUE);
    }

    public static MapBuilder newMapBuilder(int i) {
        return new MapBuilder(i);
    }

    public final Map build() {
        return this.contributions.isEmpty() ? Collections.EMPTY_MAP : Collections.unmodifiableMap(this.contributions);
    }
}

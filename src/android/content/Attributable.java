package android.content;

import java.util.List;

/* loaded from: classes.dex */
public interface Attributable {
    void setAttributionSource(AttributionSource attributionSource);

    static <T extends Attributable> T setAttributionSource(T t, AttributionSource attributionSource) {
        if (t != null) {
            t.setAttributionSource(attributionSource);
        }
        return t;
    }

    static <T extends Attributable> List<T> setAttributionSource(List<T> list, AttributionSource attributionSource) {
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                setAttributionSource(list.get(i), attributionSource);
            }
        }
        return list;
    }
}

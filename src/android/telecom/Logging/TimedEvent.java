package android.telecom.Logging;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class TimedEvent<T> {
    public abstract T getKey();

    public abstract long getTime();

    public static <T> Map<T, Double> averageTimings(Collection<? extends TimedEvent<T>> collection) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        for (TimedEvent<T> timedEvent : collection) {
            if (map.containsKey(timedEvent.getKey())) {
                map.put(timedEvent.getKey(), Integer.valueOf(((Integer) map.get(timedEvent.getKey())).intValue() + 1));
                map2.put(timedEvent.getKey(), Double.valueOf(((Double) map2.get(timedEvent.getKey())).doubleValue() + timedEvent.getTime()));
            } else {
                map.put(timedEvent.getKey(), 1);
                map2.put(timedEvent.getKey(), Double.valueOf(timedEvent.getTime()));
            }
        }
        for (Map.Entry entry : map2.entrySet()) {
            map2.put(entry.getKey(), Double.valueOf(((Double) entry.getValue()).doubleValue() / ((Integer) map.get(entry.getKey())).intValue()));
        }
        return map2;
    }
}

package android.telecom.Logging;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class TimedEvent<T> {
    public abstract T getKey();

    public abstract long getTime();

    public static <T> Map<T, Double> averageTimings(Collection<? extends TimedEvent<T>> collection) {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        for (TimedEvent<T> timedEvent : collection) {
            if (hashMap.containsKey(timedEvent.getKey())) {
                hashMap.put(timedEvent.getKey(), Integer.valueOf(((Integer) hashMap.get(timedEvent.getKey())).intValue() + 1));
                hashMap2.put(timedEvent.getKey(), Double.valueOf(((Double) hashMap2.get(timedEvent.getKey())).doubleValue() + timedEvent.getTime()));
            } else {
                hashMap.put(timedEvent.getKey(), 1);
                hashMap2.put(timedEvent.getKey(), Double.valueOf(timedEvent.getTime()));
            }
        }
        for (Map.Entry entry : hashMap2.entrySet()) {
            hashMap2.put(entry.getKey(), Double.valueOf(((Double) entry.getValue()).doubleValue() / ((Integer) hashMap.get(entry.getKey())).intValue()));
        }
        return hashMap2;
    }
}

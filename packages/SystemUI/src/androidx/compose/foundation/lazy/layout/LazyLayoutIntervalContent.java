package androidx.compose.foundation.lazy.layout;

import androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent.Interval;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyLayoutIntervalContent<Interval extends Interval> {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Interval {
        Function1 getKey();

        default Function1 getType() {
            return new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent$Interval$type$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                    ((Number) obj).intValue();
                    return null;
                }
            };
        }
    }

    public final Object getContentType(int i) {
        IntervalList$Interval intervalList$Interval = getIntervals$1().get(i);
        return ((Interval) intervalList$Interval.value).getType().mo779invoke(Integer.valueOf(i - intervalList$Interval.startIndex));
    }

    public abstract MutableIntervalList getIntervals$1();

    public final Object getKey(int i) {
        Object mo779invoke;
        IntervalList$Interval intervalList$Interval = getIntervals$1().get(i);
        int i2 = i - intervalList$Interval.startIndex;
        Function1 key = ((Interval) intervalList$Interval.value).getKey();
        return (key == null || (mo779invoke = key.mo779invoke(Integer.valueOf(i2))) == null) ? new DefaultLazyKey(i) : mo779invoke;
    }
}

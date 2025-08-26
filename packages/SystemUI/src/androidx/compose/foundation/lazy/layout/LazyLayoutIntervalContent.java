package androidx.compose.foundation.lazy.layout;

import androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent.Interval;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class LazyLayoutIntervalContent<Interval extends Interval> {

    public interface Interval {
        Function1 getKey();

        default Function1 getType() {
            return new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent$Interval$type$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                    ((Number) obj).intValue();
                    return null;
                }
            };
        }
    }

    public final Object getContentType(int i) {
        IntervalList$Interval intervalList$Interval = getIntervals$1().get(i);
        return ((Interval) intervalList$Interval.value).getType().mo781invoke(Integer.valueOf(i - intervalList$Interval.startIndex));
    }

    public abstract MutableIntervalList getIntervals$1();

    public final Object getKey(int i) {
        Object objMo781invoke;
        IntervalList$Interval intervalList$Interval = getIntervals$1().get(i);
        int i2 = i - intervalList$Interval.startIndex;
        Function1 key = ((Interval) intervalList$Interval.value).getKey();
        return (key == null || (objMo781invoke = key.mo781invoke(Integer.valueOf(i2))) == null) ? new DefaultLazyKey(i) : objMo781invoke;
    }
}

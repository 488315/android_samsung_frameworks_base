package kotlin.comparisons;

import java.util.Comparator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0 implements Comparator {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Object obj3 = this.f$0;
        switch (i) {
            case 0:
                for (Function1 function1 : (Function1[]) obj3) {
                    int compareValues = ComparisonsKt__ComparisonsKt.compareValues((Comparable) function1.mo779invoke(obj), (Comparable) function1.mo779invoke(obj2));
                    if (compareValues != 0) {
                        return compareValues;
                    }
                }
                return 0;
            default:
                NaturalOrderComparator naturalOrderComparator = (NaturalOrderComparator) obj3;
                if (obj == obj2) {
                    return 0;
                }
                if (obj == null) {
                    return 1;
                }
                if (obj2 == null) {
                    return -1;
                }
                return naturalOrderComparator.compare(obj, obj2);
        }
    }
}

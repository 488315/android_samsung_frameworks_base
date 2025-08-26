package kotlin.comparisons;

import java.util.Comparator;
import kotlin.jvm.functions.Function1;

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
                    int iCompareValues = ComparisonsKt__ComparisonsKt.compareValues((Comparable) function1.mo781invoke(obj), (Comparable) function1.mo781invoke(obj2));
                    if (iCompareValues != 0) {
                        return iCompareValues;
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

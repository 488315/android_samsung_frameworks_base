package kotlin.comparisons;

import java.util.Comparator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final /* synthetic */ class ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0 implements Comparator {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                for (Function1 function1 : (Function1[]) this.f$0) {
                    int compareValues = ComparisonsKt__ComparisonsKt.compareValues((Comparable) function1.invoke(obj), (Comparable) function1.invoke(obj2));
                    if (compareValues != 0) {
                        return compareValues;
                    }
                }
                return 0;
            default:
                Comparator comparator = (Comparator) this.f$0;
                if (obj == obj2) {
                    return 0;
                }
                if (obj == null) {
                    return 1;
                }
                if (obj2 == null) {
                    return -1;
                }
                return comparator.compare(obj, obj2);
        }
    }
}

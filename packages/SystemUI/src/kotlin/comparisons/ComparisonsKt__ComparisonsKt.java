package kotlin.comparisons;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ComparisonsKt__ComparisonsKt {
    public static ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0 compareBy(Function1... function1Arr) {
        if (function1Arr.length > 0) {
            return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0(function1Arr, 0);
        }
        throw new IllegalArgumentException("Failed requirement.");
    }

    public static int compareValues(Comparable comparable, Comparable comparable2) {
        if (comparable == comparable2) {
            return 0;
        }
        if (comparable == null) {
            return -1;
        }
        if (comparable2 == null) {
            return 1;
        }
        return comparable.compareTo(comparable2);
    }
}

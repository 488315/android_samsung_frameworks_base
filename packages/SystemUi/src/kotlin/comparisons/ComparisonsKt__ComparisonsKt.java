package kotlin.comparisons;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ComparisonsKt__ComparisonsKt {
    public static final ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0 compareBy(Function1... function1Arr) {
        if (function1Arr.length > 0) {
            return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0(function1Arr, 0);
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    public static final int compareValues(Comparable comparable, Comparable comparable2) {
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

package com.android.server.wm;

import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class FoldStarManagerService$$ExternalSyntheticLambda5
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        return ((entry.getKey() instanceof String) && (entry.getValue() instanceof Float))
                ? false
                : true;
    }
}

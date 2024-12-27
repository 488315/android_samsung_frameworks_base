package com.android.server.wm;

import java.util.Map;
import java.util.function.Predicate;

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

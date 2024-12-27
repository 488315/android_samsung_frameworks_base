package com.android.server.integrity;

import java.util.function.Predicate;

public final /* synthetic */ class AppIntegrityManagerServiceImpl$$ExternalSyntheticLambda3
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((String) obj).endsWith(".apk");
    }
}

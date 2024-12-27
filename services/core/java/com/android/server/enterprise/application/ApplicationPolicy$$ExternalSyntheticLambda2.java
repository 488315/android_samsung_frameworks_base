package com.android.server.enterprise.application;

import java.util.function.Predicate;

public final /* synthetic */ class ApplicationPolicy$$ExternalSyntheticLambda2
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !ApplicationPolicy.AUTHORIZATION_SCOPES_MAP.containsKey((String) obj);
    }
}

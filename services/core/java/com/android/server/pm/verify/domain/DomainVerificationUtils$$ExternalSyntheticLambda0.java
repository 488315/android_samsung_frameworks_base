package com.android.server.pm.verify.domain;

import android.util.Patterns;

import java.util.function.Supplier;

public final /* synthetic */ class DomainVerificationUtils$$ExternalSyntheticLambda0
        implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return Patterns.DOMAIN_NAME.matcher("");
    }
}

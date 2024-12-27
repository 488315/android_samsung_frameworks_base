package com.android.server.pm;

import java.util.function.Supplier;

public final /* synthetic */ class SaferIntentUtils$$ExternalSyntheticLambda1 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        ThreadLocal threadLocal = SaferIntentUtils.DISABLE_ENFORCE_INTENTS_TO_MATCH_INTENT_FILTERS;
        return Boolean.FALSE;
    }
}

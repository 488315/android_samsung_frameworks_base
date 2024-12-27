package com.android.server.pm;

import com.android.internal.infra.AndroidFuture;

import java.util.function.Predicate;

public final /* synthetic */ class ShortcutUser$$ExternalSyntheticLambda7 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((AndroidFuture) obj).isDone();
    }
}

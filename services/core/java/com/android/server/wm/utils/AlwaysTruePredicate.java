package com.android.server.wm.utils;

import java.util.function.Predicate;

public final class AlwaysTruePredicate implements Predicate {
    public static final AlwaysTruePredicate INSTANCE = new AlwaysTruePredicate();

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return true;
    }
}

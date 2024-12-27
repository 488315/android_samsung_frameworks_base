package com.android.server.power;

import java.util.function.Predicate;

public final /* synthetic */ class PowerHistorian$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return PowerHistorian.WakeUpRecord.class.isInstance((PowerHistorian.Record) obj);
    }
}

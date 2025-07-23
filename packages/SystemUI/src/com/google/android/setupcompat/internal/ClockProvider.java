package com.google.android.setupcompat.internal;

import com.google.android.setupcompat.internal.ClockProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ClockProvider {
    public static final ClockProvider$$ExternalSyntheticLambda0 SYSTEM_TICKER;
    public static Ticker ticker;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Supplier<T> {
        Object get();
    }

    static {
        ClockProvider$$ExternalSyntheticLambda0 clockProvider$$ExternalSyntheticLambda0 = new ClockProvider$$ExternalSyntheticLambda0();
        SYSTEM_TICKER = clockProvider$$ExternalSyntheticLambda0;
        ticker = clockProvider$$ExternalSyntheticLambda0;
    }

    public static void resetInstance() {
        ticker = SYSTEM_TICKER;
    }

    public static void setInstance(final Supplier<Long> supplier) {
        ticker = new Ticker() { // from class: com.google.android.setupcompat.internal.ClockProvider$$ExternalSyntheticLambda1
            @Override // com.google.android.setupcompat.internal.Ticker
            public final long read() {
                return ((Long) ClockProvider.Supplier.this.get()).longValue();
            }
        };
    }
}

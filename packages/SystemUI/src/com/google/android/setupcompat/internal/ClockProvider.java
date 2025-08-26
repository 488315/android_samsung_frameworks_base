package com.google.android.setupcompat.internal;

/* loaded from: classes4.dex */
public class ClockProvider {
    public static final ClockProvider$$ExternalSyntheticLambda0 SYSTEM_TICKER;
    public static Ticker ticker;

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
                return ((Long) supplier.get()).longValue();
            }
        };
    }
}

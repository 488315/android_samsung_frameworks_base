package kotlinx.coroutines;

import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Dispatchers {
    public static final DefaultScheduler Default;
    public static final Unconfined Unconfined;

    static {
        new Dispatchers();
        Default = DefaultScheduler.INSTANCE;
        Unconfined = Unconfined.INSTANCE;
    }

    private Dispatchers() {
    }
}

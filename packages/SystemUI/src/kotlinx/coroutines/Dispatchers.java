package kotlinx.coroutines;

import kotlinx.coroutines.scheduling.DefaultScheduler;

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

package kotlinx.coroutines;

import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Dispatchers {
    public static final DefaultScheduler Default;

    /* renamed from: IO */
    public static final DefaultIoScheduler f667IO;
    public static final Unconfined Unconfined;

    static {
        new Dispatchers();
        Default = DefaultScheduler.INSTANCE;
        Unconfined = Unconfined.INSTANCE;
        f667IO = DefaultIoScheduler.INSTANCE;
    }

    private Dispatchers() {
    }
}

package kotlinx.coroutines.sync;

import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class MutexKt {
    public static final Symbol NO_OWNER = new Symbol("NO_OWNER");

    static {
        new Symbol("ALREADY_LOCKED_BY_OWNER");
    }

    public static MutexImpl Mutex$default() {
        return new MutexImpl(false);
    }
}

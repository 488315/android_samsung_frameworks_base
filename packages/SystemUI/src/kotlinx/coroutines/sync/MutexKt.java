package kotlinx.coroutines.sync;

import kotlinx.coroutines.internal.Symbol;

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

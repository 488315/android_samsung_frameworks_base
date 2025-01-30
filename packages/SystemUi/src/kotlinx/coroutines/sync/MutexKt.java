package kotlinx.coroutines.sync;

import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class MutexKt {
    public static final Empty EMPTY_LOCKED;
    public static final Empty EMPTY_UNLOCKED;
    public static final Symbol LOCKED;
    public static final Symbol UNLOCKED;
    public static final Symbol UNLOCK_FAIL;

    static {
        new Symbol("LOCK_FAIL");
        UNLOCK_FAIL = new Symbol("UNLOCK_FAIL");
        Symbol symbol = new Symbol("LOCKED");
        LOCKED = symbol;
        Symbol symbol2 = new Symbol("UNLOCKED");
        UNLOCKED = symbol2;
        EMPTY_LOCKED = new Empty(symbol);
        EMPTY_UNLOCKED = new Empty(symbol2);
    }
}

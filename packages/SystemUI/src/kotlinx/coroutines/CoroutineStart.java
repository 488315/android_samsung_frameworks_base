package kotlinx.coroutines;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CoroutineStart {
    public static final /* synthetic */ CoroutineStart[] $VALUES;
    public static final CoroutineStart ATOMIC;
    public static final CoroutineStart DEFAULT;
    public static final CoroutineStart LAZY;
    public static final CoroutineStart UNDISPATCHED;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CoroutineStart.values().length];
            try {
                iArr[CoroutineStart.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CoroutineStart.ATOMIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CoroutineStart.UNDISPATCHED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CoroutineStart.LAZY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        CoroutineStart coroutineStart = new CoroutineStart("DEFAULT", 0);
        DEFAULT = coroutineStart;
        CoroutineStart coroutineStart2 = new CoroutineStart("LAZY", 1);
        LAZY = coroutineStart2;
        CoroutineStart coroutineStart3 = new CoroutineStart("ATOMIC", 2);
        ATOMIC = coroutineStart3;
        CoroutineStart coroutineStart4 = new CoroutineStart("UNDISPATCHED", 3);
        UNDISPATCHED = coroutineStart4;
        CoroutineStart[] coroutineStartArr = {coroutineStart, coroutineStart2, coroutineStart3, coroutineStart4};
        $VALUES = coroutineStartArr;
        EnumEntriesKt.enumEntries(coroutineStartArr);
    }

    private CoroutineStart(String str, int i) {
    }

    public static CoroutineStart valueOf(String str) {
        return (CoroutineStart) Enum.valueOf(CoroutineStart.class, str);
    }

    public static CoroutineStart[] values() {
        return (CoroutineStart[]) $VALUES.clone();
    }
}

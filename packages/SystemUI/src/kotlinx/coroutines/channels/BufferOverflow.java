package kotlinx.coroutines.channels;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class BufferOverflow {
    public static final /* synthetic */ BufferOverflow[] $VALUES;
    public static final BufferOverflow DROP_LATEST;
    public static final BufferOverflow DROP_OLDEST;
    public static final BufferOverflow SUSPEND;

    static {
        BufferOverflow bufferOverflow = new BufferOverflow("SUSPEND", 0);
        SUSPEND = bufferOverflow;
        BufferOverflow bufferOverflow2 = new BufferOverflow("DROP_OLDEST", 1);
        DROP_OLDEST = bufferOverflow2;
        BufferOverflow bufferOverflow3 = new BufferOverflow("DROP_LATEST", 2);
        DROP_LATEST = bufferOverflow3;
        BufferOverflow[] bufferOverflowArr = {bufferOverflow, bufferOverflow2, bufferOverflow3};
        $VALUES = bufferOverflowArr;
        EnumEntriesKt.enumEntries(bufferOverflowArr);
    }

    private BufferOverflow(String str, int i) {
    }

    public static BufferOverflow valueOf(String str) {
        return (BufferOverflow) Enum.valueOf(BufferOverflow.class, str);
    }

    public static BufferOverflow[] values() {
        return (BufferOverflow[]) $VALUES.clone();
    }
}

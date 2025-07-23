package kotlinx.coroutines.selects;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TrySelectDetailedResult {
    public static final /* synthetic */ TrySelectDetailedResult[] $VALUES;
    public static final TrySelectDetailedResult ALREADY_SELECTED;
    public static final TrySelectDetailedResult CANCELLED;
    public static final TrySelectDetailedResult REREGISTER;
    public static final TrySelectDetailedResult SUCCESSFUL;

    static {
        TrySelectDetailedResult trySelectDetailedResult = new TrySelectDetailedResult("SUCCESSFUL", 0);
        SUCCESSFUL = trySelectDetailedResult;
        TrySelectDetailedResult trySelectDetailedResult2 = new TrySelectDetailedResult("REREGISTER", 1);
        REREGISTER = trySelectDetailedResult2;
        TrySelectDetailedResult trySelectDetailedResult3 = new TrySelectDetailedResult("CANCELLED", 2);
        CANCELLED = trySelectDetailedResult3;
        TrySelectDetailedResult trySelectDetailedResult4 = new TrySelectDetailedResult("ALREADY_SELECTED", 3);
        ALREADY_SELECTED = trySelectDetailedResult4;
        TrySelectDetailedResult[] trySelectDetailedResultArr = {trySelectDetailedResult, trySelectDetailedResult2, trySelectDetailedResult3, trySelectDetailedResult4};
        $VALUES = trySelectDetailedResultArr;
        EnumEntriesKt.enumEntries(trySelectDetailedResultArr);
    }

    private TrySelectDetailedResult(String str, int i) {
    }

    public static TrySelectDetailedResult valueOf(String str) {
        return (TrySelectDetailedResult) Enum.valueOf(TrySelectDetailedResult.class, str);
    }

    public static TrySelectDetailedResult[] values() {
        return (TrySelectDetailedResult[]) $VALUES.clone();
    }
}

package androidx.compose.material3;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SnackbarResult {
    public static final /* synthetic */ SnackbarResult[] $VALUES;
    public static final SnackbarResult ActionPerformed;
    public static final SnackbarResult Dismissed;

    static {
        SnackbarResult snackbarResult = new SnackbarResult("Dismissed", 0);
        Dismissed = snackbarResult;
        SnackbarResult snackbarResult2 = new SnackbarResult("ActionPerformed", 1);
        ActionPerformed = snackbarResult2;
        SnackbarResult[] snackbarResultArr = {snackbarResult, snackbarResult2};
        $VALUES = snackbarResultArr;
        EnumEntriesKt.enumEntries(snackbarResultArr);
    }

    private SnackbarResult(String str, int i) {
    }

    public static SnackbarResult valueOf(String str) {
        return (SnackbarResult) Enum.valueOf(SnackbarResult.class, str);
    }

    public static SnackbarResult[] values() {
        return (SnackbarResult[]) $VALUES.clone();
    }
}

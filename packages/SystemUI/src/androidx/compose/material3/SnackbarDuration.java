package androidx.compose.material3;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SnackbarDuration {
    public static final /* synthetic */ SnackbarDuration[] $VALUES;
    public static final SnackbarDuration Indefinite;
    public static final SnackbarDuration Long;
    public static final SnackbarDuration Short;

    static {
        SnackbarDuration snackbarDuration = new SnackbarDuration("Short", 0);
        Short = snackbarDuration;
        SnackbarDuration snackbarDuration2 = new SnackbarDuration("Long", 1);
        Long = snackbarDuration2;
        SnackbarDuration snackbarDuration3 = new SnackbarDuration("Indefinite", 2);
        Indefinite = snackbarDuration3;
        SnackbarDuration[] snackbarDurationArr = {snackbarDuration, snackbarDuration2, snackbarDuration3};
        $VALUES = snackbarDurationArr;
        EnumEntriesKt.enumEntries(snackbarDurationArr);
    }

    private SnackbarDuration(String str, int i) {
    }

    public static SnackbarDuration valueOf(String str) {
        return (SnackbarDuration) Enum.valueOf(SnackbarDuration.class, str);
    }

    public static SnackbarDuration[] values() {
        return (SnackbarDuration[]) $VALUES.clone();
    }
}

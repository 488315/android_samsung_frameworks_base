package androidx.compose.ui.text.style;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ResolvedTextDirection {
    public static final /* synthetic */ ResolvedTextDirection[] $VALUES;
    public static final ResolvedTextDirection Ltr;
    public static final ResolvedTextDirection Rtl;

    static {
        ResolvedTextDirection resolvedTextDirection = new ResolvedTextDirection("Ltr", 0);
        Ltr = resolvedTextDirection;
        ResolvedTextDirection resolvedTextDirection2 = new ResolvedTextDirection("Rtl", 1);
        Rtl = resolvedTextDirection2;
        ResolvedTextDirection[] resolvedTextDirectionArr = {resolvedTextDirection, resolvedTextDirection2};
        $VALUES = resolvedTextDirectionArr;
        EnumEntriesKt.enumEntries(resolvedTextDirectionArr);
    }

    private ResolvedTextDirection(String str, int i) {
    }

    public static ResolvedTextDirection valueOf(String str) {
        return (ResolvedTextDirection) Enum.valueOf(ResolvedTextDirection.class, str);
    }

    public static ResolvedTextDirection[] values() {
        return (ResolvedTextDirection[]) $VALUES.clone();
    }
}

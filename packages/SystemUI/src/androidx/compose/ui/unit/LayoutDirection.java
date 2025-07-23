package androidx.compose.ui.unit;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LayoutDirection {
    public static final /* synthetic */ LayoutDirection[] $VALUES;
    public static final LayoutDirection Ltr;
    public static final LayoutDirection Rtl;

    static {
        LayoutDirection layoutDirection = new LayoutDirection("Ltr", 0);
        Ltr = layoutDirection;
        LayoutDirection layoutDirection2 = new LayoutDirection("Rtl", 1);
        Rtl = layoutDirection2;
        LayoutDirection[] layoutDirectionArr = {layoutDirection, layoutDirection2};
        $VALUES = layoutDirectionArr;
        EnumEntriesKt.enumEntries(layoutDirectionArr);
    }

    private LayoutDirection(String str, int i) {
    }

    public static LayoutDirection valueOf(String str) {
        return (LayoutDirection) Enum.valueOf(LayoutDirection.class, str);
    }

    public static LayoutDirection[] values() {
        return (LayoutDirection[]) $VALUES.clone();
    }
}

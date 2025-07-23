package androidx.compose.foundation.layout;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class IntrinsicSize {
    public static final /* synthetic */ IntrinsicSize[] $VALUES;
    public static final IntrinsicSize Max;
    public static final IntrinsicSize Min;

    static {
        IntrinsicSize intrinsicSize = new IntrinsicSize("Min", 0);
        Min = intrinsicSize;
        IntrinsicSize intrinsicSize2 = new IntrinsicSize("Max", 1);
        Max = intrinsicSize2;
        IntrinsicSize[] intrinsicSizeArr = {intrinsicSize, intrinsicSize2};
        $VALUES = intrinsicSizeArr;
        EnumEntriesKt.enumEntries(intrinsicSizeArr);
    }

    private IntrinsicSize(String str, int i) {
    }

    public static IntrinsicSize valueOf(String str) {
        return (IntrinsicSize) Enum.valueOf(IntrinsicSize.class, str);
    }

    public static IntrinsicSize[] values() {
        return (IntrinsicSize[]) $VALUES.clone();
    }
}

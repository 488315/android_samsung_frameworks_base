package androidx.compose.ui.layout;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class IntrinsicMinMax {
    public static final /* synthetic */ IntrinsicMinMax[] $VALUES;
    public static final IntrinsicMinMax Max;
    public static final IntrinsicMinMax Min;

    static {
        IntrinsicMinMax intrinsicMinMax = new IntrinsicMinMax("Min", 0);
        Min = intrinsicMinMax;
        IntrinsicMinMax intrinsicMinMax2 = new IntrinsicMinMax("Max", 1);
        Max = intrinsicMinMax2;
        IntrinsicMinMax[] intrinsicMinMaxArr = {intrinsicMinMax, intrinsicMinMax2};
        $VALUES = intrinsicMinMaxArr;
        EnumEntriesKt.enumEntries(intrinsicMinMaxArr);
    }

    private IntrinsicMinMax(String str, int i) {
    }

    public static IntrinsicMinMax valueOf(String str) {
        return (IntrinsicMinMax) Enum.valueOf(IntrinsicMinMax.class, str);
    }

    public static IntrinsicMinMax[] values() {
        return (IntrinsicMinMax[]) $VALUES.clone();
    }
}

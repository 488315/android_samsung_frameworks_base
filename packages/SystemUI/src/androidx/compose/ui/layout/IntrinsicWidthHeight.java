package androidx.compose.ui.layout;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class IntrinsicWidthHeight {
    public static final /* synthetic */ IntrinsicWidthHeight[] $VALUES;
    public static final IntrinsicWidthHeight Height;
    public static final IntrinsicWidthHeight Width;

    static {
        IntrinsicWidthHeight intrinsicWidthHeight = new IntrinsicWidthHeight("Width", 0);
        Width = intrinsicWidthHeight;
        IntrinsicWidthHeight intrinsicWidthHeight2 = new IntrinsicWidthHeight("Height", 1);
        Height = intrinsicWidthHeight2;
        IntrinsicWidthHeight[] intrinsicWidthHeightArr = {intrinsicWidthHeight, intrinsicWidthHeight2};
        $VALUES = intrinsicWidthHeightArr;
        EnumEntriesKt.enumEntries(intrinsicWidthHeightArr);
    }

    private IntrinsicWidthHeight(String str, int i) {
    }

    public static IntrinsicWidthHeight valueOf(String str) {
        return (IntrinsicWidthHeight) Enum.valueOf(IntrinsicWidthHeight.class, str);
    }

    public static IntrinsicWidthHeight[] values() {
        return (IntrinsicWidthHeight[]) $VALUES.clone();
    }
}

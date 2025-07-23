package androidx.compose.foundation.layout;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LayoutOrientation {
    public static final /* synthetic */ LayoutOrientation[] $VALUES;
    public static final LayoutOrientation Horizontal;
    public static final LayoutOrientation Vertical;

    static {
        LayoutOrientation layoutOrientation = new LayoutOrientation("Horizontal", 0);
        Horizontal = layoutOrientation;
        LayoutOrientation layoutOrientation2 = new LayoutOrientation("Vertical", 1);
        Vertical = layoutOrientation2;
        LayoutOrientation[] layoutOrientationArr = {layoutOrientation, layoutOrientation2};
        $VALUES = layoutOrientationArr;
        EnumEntriesKt.enumEntries(layoutOrientationArr);
    }

    private LayoutOrientation(String str, int i) {
    }

    public static LayoutOrientation valueOf(String str) {
        return (LayoutOrientation) Enum.valueOf(LayoutOrientation.class, str);
    }

    public static LayoutOrientation[] values() {
        return (LayoutOrientation[]) $VALUES.clone();
    }
}

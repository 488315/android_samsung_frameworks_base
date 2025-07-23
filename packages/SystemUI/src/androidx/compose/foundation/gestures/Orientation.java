package androidx.compose.foundation.gestures;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Orientation {
    public static final /* synthetic */ Orientation[] $VALUES;
    public static final Orientation Horizontal;
    public static final Orientation Vertical;

    static {
        Orientation orientation = new Orientation("Vertical", 0);
        Vertical = orientation;
        Orientation orientation2 = new Orientation("Horizontal", 1);
        Horizontal = orientation2;
        Orientation[] orientationArr = {orientation, orientation2};
        $VALUES = orientationArr;
        EnumEntriesKt.enumEntries(orientationArr);
    }

    private Orientation(String str, int i) {
    }

    public static Orientation valueOf(String str) {
        return (Orientation) Enum.valueOf(Orientation.class, str);
    }

    public static Orientation[] values() {
        return (Orientation[]) $VALUES.clone();
    }
}

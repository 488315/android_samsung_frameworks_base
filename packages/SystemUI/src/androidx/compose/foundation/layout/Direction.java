package androidx.compose.foundation.layout;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class Direction {
    public static final /* synthetic */ Direction[] $VALUES;
    public static final Direction Both;
    public static final Direction Horizontal;
    public static final Direction Vertical;

    static {
        Direction direction = new Direction("Vertical", 0);
        Vertical = direction;
        Direction direction2 = new Direction("Horizontal", 1);
        Horizontal = direction2;
        Direction direction3 = new Direction("Both", 2);
        Both = direction3;
        Direction[] directionArr = {direction, direction2, direction3};
        $VALUES = directionArr;
        EnumEntriesKt.enumEntries(directionArr);
    }

    private Direction(String str, int i) {
    }

    public static Direction valueOf(String str) {
        return (Direction) Enum.valueOf(Direction.class, str);
    }

    public static Direction[] values() {
        return (Direction[]) $VALUES.clone();
    }
}

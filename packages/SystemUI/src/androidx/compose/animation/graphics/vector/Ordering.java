package androidx.compose.animation.graphics.vector;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class Ordering {
    public static final /* synthetic */ Ordering[] $VALUES;
    public static final Ordering Sequentially;
    public static final Ordering Together;

    static {
        Ordering ordering = new Ordering("Together", 0);
        Together = ordering;
        Ordering ordering2 = new Ordering("Sequentially", 1);
        Sequentially = ordering2;
        Ordering[] orderingArr = {ordering, ordering2};
        $VALUES = orderingArr;
        EnumEntriesKt.enumEntries(orderingArr);
    }

    private Ordering(String str, int i) {
    }

    public static Ordering valueOf(String str) {
        return (Ordering) Enum.valueOf(Ordering.class, str);
    }

    public static Ordering[] values() {
        return (Ordering[]) $VALUES.clone();
    }
}

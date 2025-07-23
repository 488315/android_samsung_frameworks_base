package kotlin.reflect;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class KVariance {
    public static final /* synthetic */ KVariance[] $VALUES;
    public static final KVariance IN;
    public static final KVariance INVARIANT;
    public static final KVariance OUT;

    static {
        KVariance kVariance = new KVariance("INVARIANT", 0);
        INVARIANT = kVariance;
        KVariance kVariance2 = new KVariance("IN", 1);
        IN = kVariance2;
        KVariance kVariance3 = new KVariance("OUT", 2);
        OUT = kVariance3;
        KVariance[] kVarianceArr = {kVariance, kVariance2, kVariance3};
        $VALUES = kVarianceArr;
        EnumEntriesKt.enumEntries(kVarianceArr);
    }

    private KVariance(String str, int i) {
    }

    public static KVariance valueOf(String str) {
        return (KVariance) Enum.valueOf(KVariance.class, str);
    }

    public static KVariance[] values() {
        return (KVariance[]) $VALUES.clone();
    }
}

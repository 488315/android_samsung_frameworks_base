package com.samsung.android.nexus.particle.emitter;

import com.samsung.android.nexus.base.utils.RangeChecker;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'WIDTH' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FactorType {
    public static final /* synthetic */ FactorType[] $VALUES;
    public static final FactorType ALPHA;
    public static final FactorType COLOR_ALPHA;
    public static final FactorType COLOR_BLUE;
    public static final FactorType COLOR_GREEN;
    public static final FactorType COLOR_HUE;
    public static final FactorType COLOR_RED;
    public static final FactorType COLOR_SATURATION;
    public static final FactorType COLOR_VALUE;
    public static final FactorType HEIGHT;
    public static final FactorType POS;
    public static final FactorType POS_X;
    public static final FactorType POS_Y;
    public static final FactorType ROTATION;
    public static final FactorType SCALE;
    public static final FactorType SCALE_X;
    public static final FactorType SCALE_Y;
    public static final FactorType WIDTH;
    final int accelerationIdx;
    final int idx;
    final float max;
    final float min;
    final int opType;
    RangeChecker rangeChecker;
    final int speedIdx;
    final int valueIdx;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Holder {
        public static final int sCount;
        public static final FactorType[] sValuesCache;

        static {
            FactorType[] values = FactorType.values();
            sValuesCache = values;
            sCount = values.length;
        }

        private Holder() {
        }
    }

    static {
        Float valueOf = Float.valueOf(1.0f);
        Float valueOf2 = Float.valueOf(2.1474836E9f);
        FactorType factorType = new FactorType("WIDTH", 0, 0, new RangeChecker(valueOf, valueOf2));
        WIDTH = factorType;
        FactorType factorType2 = new FactorType("HEIGHT", 1, 0, new RangeChecker(valueOf, valueOf2));
        HEIGHT = factorType2;
        FactorType factorType3 = new FactorType("POS", 2, 0);
        POS = factorType3;
        FactorType factorType4 = new FactorType("POS_X", 3, 0);
        POS_X = factorType4;
        FactorType factorType5 = new FactorType("POS_Y", 4, 0);
        POS_Y = factorType5;
        FactorType factorType6 = new FactorType("ROTATION", 5, 0);
        ROTATION = factorType6;
        Float valueOf3 = Float.valueOf(0.0f);
        FactorType factorType7 = new FactorType("ALPHA", 6, 1, 1.0f, new RangeChecker(valueOf3, valueOf));
        ALPHA = factorType7;
        FactorType factorType8 = new FactorType("SCALE", 7, 1, 1.0f);
        SCALE = factorType8;
        FactorType factorType9 = new FactorType("SCALE_X", 8, 1, 1.0f);
        SCALE_X = factorType9;
        FactorType factorType10 = new FactorType("SCALE_Y", 9, 1, 1.0f);
        SCALE_Y = factorType10;
        Float valueOf4 = Float.valueOf(255.0f);
        FactorType factorType11 = new FactorType("COLOR_ALPHA", 10, 0, new RangeChecker(valueOf3, valueOf4));
        COLOR_ALPHA = factorType11;
        FactorType factorType12 = new FactorType("COLOR_RED", 11, 0, new RangeChecker(valueOf3, valueOf4));
        COLOR_RED = factorType12;
        FactorType factorType13 = new FactorType("COLOR_GREEN", 12, 0, new RangeChecker(valueOf3, valueOf4));
        COLOR_GREEN = factorType13;
        FactorType factorType14 = new FactorType("COLOR_BLUE", 13, 0, new RangeChecker(valueOf3, valueOf4));
        COLOR_BLUE = factorType14;
        FactorType factorType15 = new FactorType("COLOR_HUE", 14, 0, new RangeChecker(valueOf3, Float.valueOf(360.0f)));
        COLOR_HUE = factorType15;
        FactorType factorType16 = new FactorType("COLOR_SATURATION", 15, 0, new RangeChecker(valueOf3, valueOf));
        COLOR_SATURATION = factorType16;
        FactorType factorType17 = new FactorType("COLOR_VALUE", 16, 0, new RangeChecker(valueOf3, valueOf));
        COLOR_VALUE = factorType17;
        $VALUES = new FactorType[]{factorType, factorType2, factorType3, factorType4, factorType5, factorType6, factorType7, factorType8, factorType9, factorType10, factorType11, factorType12, factorType13, factorType14, factorType15, factorType16, factorType17};
    }

    private FactorType(String str, int i, int i2) {
        this(str, i, i2, 0.0f, 0.0f, null);
    }

    public static FactorType valueOf(String str) {
        return (FactorType) Enum.valueOf(FactorType.class, str);
    }

    public static FactorType[] values() {
        return (FactorType[]) $VALUES.clone();
    }

    private FactorType(String str, int i, int i2, RangeChecker rangeChecker) {
        this(str, i, i2, 0.0f, 0.0f, rangeChecker);
    }

    private FactorType(String str, int i, int i2, float f) {
        this(str, i, i2, f, f, null);
    }

    private FactorType(String str, int i, int i2, float f, RangeChecker rangeChecker) {
        this(str, i, i2, f, f, rangeChecker);
    }

    private FactorType(String str, int i, int i2, float f, float f2) {
        this(str, i, i2, f, f2, null);
    }

    private FactorType(String str, int i, int i2, float f, float f2, RangeChecker rangeChecker) {
        int ordinal = ordinal();
        this.idx = ordinal;
        this.valueIdx = (ordinal * 3) + 0;
        this.speedIdx = (ordinal * 3) + 1;
        this.accelerationIdx = (ordinal * 3) + 2;
        this.min = f;
        this.max = f2;
        this.rangeChecker = rangeChecker;
        this.opType = i2;
    }
}

package androidx.compose.material3.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class MotionSchemeKeyTokens {
    public static final /* synthetic */ MotionSchemeKeyTokens[] $VALUES;
    public static final MotionSchemeKeyTokens DefaultEffects;
    public static final MotionSchemeKeyTokens DefaultSpatial;
    public static final MotionSchemeKeyTokens FastEffects;
    public static final MotionSchemeKeyTokens FastSpatial;
    public static final MotionSchemeKeyTokens SlowEffects;
    public static final MotionSchemeKeyTokens SlowSpatial;

    static {
        MotionSchemeKeyTokens motionSchemeKeyTokens = new MotionSchemeKeyTokens("DefaultSpatial", 0);
        DefaultSpatial = motionSchemeKeyTokens;
        MotionSchemeKeyTokens motionSchemeKeyTokens2 = new MotionSchemeKeyTokens("FastSpatial", 1);
        FastSpatial = motionSchemeKeyTokens2;
        MotionSchemeKeyTokens motionSchemeKeyTokens3 = new MotionSchemeKeyTokens("SlowSpatial", 2);
        SlowSpatial = motionSchemeKeyTokens3;
        MotionSchemeKeyTokens motionSchemeKeyTokens4 = new MotionSchemeKeyTokens("DefaultEffects", 3);
        DefaultEffects = motionSchemeKeyTokens4;
        MotionSchemeKeyTokens motionSchemeKeyTokens5 = new MotionSchemeKeyTokens("FastEffects", 4);
        FastEffects = motionSchemeKeyTokens5;
        MotionSchemeKeyTokens motionSchemeKeyTokens6 = new MotionSchemeKeyTokens("SlowEffects", 5);
        SlowEffects = motionSchemeKeyTokens6;
        MotionSchemeKeyTokens[] motionSchemeKeyTokensArr = {motionSchemeKeyTokens, motionSchemeKeyTokens2, motionSchemeKeyTokens3, motionSchemeKeyTokens4, motionSchemeKeyTokens5, motionSchemeKeyTokens6};
        $VALUES = motionSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(motionSchemeKeyTokensArr);
    }

    private MotionSchemeKeyTokens(String str, int i) {
    }

    public static MotionSchemeKeyTokens valueOf(String str) {
        return (MotionSchemeKeyTokens) Enum.valueOf(MotionSchemeKeyTokens.class, str);
    }

    public static MotionSchemeKeyTokens[] values() {
        return (MotionSchemeKeyTokens[]) $VALUES.clone();
    }
}

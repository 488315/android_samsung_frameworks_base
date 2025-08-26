package androidx.compose.animation.core;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class RepeatMode {
    public static final /* synthetic */ RepeatMode[] $VALUES;
    public static final RepeatMode Restart;
    public static final RepeatMode Reverse;

    static {
        RepeatMode repeatMode = new RepeatMode("Restart", 0);
        Restart = repeatMode;
        RepeatMode repeatMode2 = new RepeatMode("Reverse", 1);
        Reverse = repeatMode2;
        RepeatMode[] repeatModeArr = {repeatMode, repeatMode2};
        $VALUES = repeatModeArr;
        EnumEntriesKt.enumEntries(repeatModeArr);
    }

    private RepeatMode(String str, int i) {
    }

    public static RepeatMode valueOf(String str) {
        return (RepeatMode) Enum.valueOf(RepeatMode.class, str);
    }

    public static RepeatMode[] values() {
        return (RepeatMode[]) $VALUES.clone();
    }
}

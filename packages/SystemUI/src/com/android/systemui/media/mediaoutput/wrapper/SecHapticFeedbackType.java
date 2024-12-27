package com.android.systemui.media.mediaoutput.wrapper;

import kotlin.enums.EnumEntriesKt;

public final class SecHapticFeedbackType {
    public static final /* synthetic */ SecHapticFeedbackType[] $VALUES;
    public static final SecHapticFeedbackType Seekbar;
    public static final SecHapticFeedbackType Switch;

    static {
        SecHapticFeedbackType secHapticFeedbackType = new SecHapticFeedbackType("Switch", 0);
        Switch = secHapticFeedbackType;
        SecHapticFeedbackType secHapticFeedbackType2 = new SecHapticFeedbackType("Seekbar", 1);
        Seekbar = secHapticFeedbackType2;
        SecHapticFeedbackType[] secHapticFeedbackTypeArr = {secHapticFeedbackType, secHapticFeedbackType2};
        $VALUES = secHapticFeedbackTypeArr;
        EnumEntriesKt.enumEntries(secHapticFeedbackTypeArr);
    }

    private SecHapticFeedbackType(String str, int i) {
    }

    public static SecHapticFeedbackType valueOf(String str) {
        return (SecHapticFeedbackType) Enum.valueOf(SecHapticFeedbackType.class, str);
    }

    public static SecHapticFeedbackType[] values() {
        return (SecHapticFeedbackType[]) $VALUES.clone();
    }
}

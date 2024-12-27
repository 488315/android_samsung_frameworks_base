package com.android.systemui.media.mediaoutput.wrapper;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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

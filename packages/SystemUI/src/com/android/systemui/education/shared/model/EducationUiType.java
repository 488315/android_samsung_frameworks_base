package com.android.systemui.education.shared.model;

import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class EducationUiType {
    public static final /* synthetic */ EducationUiType[] $VALUES;
    public static final EducationUiType Notification;
    public static final EducationUiType Toast;

    static {
        EducationUiType educationUiType = new EducationUiType("Toast", 0);
        Toast = educationUiType;
        EducationUiType educationUiType2 = new EducationUiType(PluginLockStar.NOTIFICATION_TYPE, 1);
        Notification = educationUiType2;
        EducationUiType[] educationUiTypeArr = {educationUiType, educationUiType2};
        $VALUES = educationUiTypeArr;
        EnumEntriesKt.enumEntries(educationUiTypeArr);
    }

    private EducationUiType(String str, int i) {
    }

    public static EducationUiType valueOf(String str) {
        return (EducationUiType) Enum.valueOf(EducationUiType.class, str);
    }

    public static EducationUiType[] values() {
        return (EducationUiType[]) $VALUES.clone();
    }
}

package com.android.systemui.keyguard.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CameraLaunchType {
    public static final /* synthetic */ CameraLaunchType[] $VALUES;
    public static final CameraLaunchType IGNORE;
    public static final CameraLaunchType LIFT_TRIGGER;
    public static final CameraLaunchType POWER_DOUBLE_TAP;
    public static final CameraLaunchType QUICK_AFFORDANCE;
    public static final CameraLaunchType WIGGLE;

    static {
        CameraLaunchType cameraLaunchType = new CameraLaunchType("IGNORE", 0);
        IGNORE = cameraLaunchType;
        CameraLaunchType cameraLaunchType2 = new CameraLaunchType("WIGGLE", 1);
        WIGGLE = cameraLaunchType2;
        CameraLaunchType cameraLaunchType3 = new CameraLaunchType("POWER_DOUBLE_TAP", 2);
        POWER_DOUBLE_TAP = cameraLaunchType3;
        CameraLaunchType cameraLaunchType4 = new CameraLaunchType("LIFT_TRIGGER", 3);
        LIFT_TRIGGER = cameraLaunchType4;
        CameraLaunchType cameraLaunchType5 = new CameraLaunchType("QUICK_AFFORDANCE", 4);
        QUICK_AFFORDANCE = cameraLaunchType5;
        CameraLaunchType[] cameraLaunchTypeArr = {cameraLaunchType, cameraLaunchType2, cameraLaunchType3, cameraLaunchType4, cameraLaunchType5};
        $VALUES = cameraLaunchTypeArr;
        EnumEntriesKt.enumEntries(cameraLaunchTypeArr);
    }

    private CameraLaunchType(String str, int i) {
    }

    public static CameraLaunchType valueOf(String str) {
        return (CameraLaunchType) Enum.valueOf(CameraLaunchType.class, str);
    }

    public static CameraLaunchType[] values() {
        return (CameraLaunchType[]) $VALUES.clone();
    }
}

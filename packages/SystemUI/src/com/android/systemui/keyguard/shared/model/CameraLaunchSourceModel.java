package com.android.systemui.keyguard.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class CameraLaunchSourceModel {
    public static final /* synthetic */ CameraLaunchSourceModel[] $VALUES;
    public static final CameraLaunchSourceModel LIFT_TRIGGER;
    public static final CameraLaunchSourceModel POWER_DOUBLE_TAP;
    public static final CameraLaunchSourceModel QUICK_AFFORDANCE;
    public static final CameraLaunchSourceModel WIGGLE;

    static {
        CameraLaunchSourceModel cameraLaunchSourceModel = new CameraLaunchSourceModel("WIGGLE", 0);
        WIGGLE = cameraLaunchSourceModel;
        CameraLaunchSourceModel cameraLaunchSourceModel2 = new CameraLaunchSourceModel("POWER_DOUBLE_TAP", 1);
        POWER_DOUBLE_TAP = cameraLaunchSourceModel2;
        CameraLaunchSourceModel cameraLaunchSourceModel3 = new CameraLaunchSourceModel("LIFT_TRIGGER", 2);
        LIFT_TRIGGER = cameraLaunchSourceModel3;
        CameraLaunchSourceModel cameraLaunchSourceModel4 = new CameraLaunchSourceModel("QUICK_AFFORDANCE", 3);
        QUICK_AFFORDANCE = cameraLaunchSourceModel4;
        CameraLaunchSourceModel[] cameraLaunchSourceModelArr = {cameraLaunchSourceModel, cameraLaunchSourceModel2, cameraLaunchSourceModel3, cameraLaunchSourceModel4};
        $VALUES = cameraLaunchSourceModelArr;
        EnumEntriesKt.enumEntries(cameraLaunchSourceModelArr);
    }

    private CameraLaunchSourceModel(String str, int i) {
    }

    public static CameraLaunchSourceModel valueOf(String str) {
        return (CameraLaunchSourceModel) Enum.valueOf(CameraLaunchSourceModel.class, str);
    }

    public static CameraLaunchSourceModel[] values() {
        return (CameraLaunchSourceModel[]) $VALUES.clone();
    }
}

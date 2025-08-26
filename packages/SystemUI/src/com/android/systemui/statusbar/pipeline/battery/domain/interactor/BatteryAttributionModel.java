package com.android.systemui.statusbar.pipeline.battery.domain.interactor;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class BatteryAttributionModel {
    public static final /* synthetic */ BatteryAttributionModel[] $VALUES;
    public static final BatteryAttributionModel Charging;
    public static final BatteryAttributionModel Defend;
    public static final BatteryAttributionModel PowerSave;

    static {
        BatteryAttributionModel batteryAttributionModel = new BatteryAttributionModel("Defend", 0);
        Defend = batteryAttributionModel;
        BatteryAttributionModel batteryAttributionModel2 = new BatteryAttributionModel("PowerSave", 1);
        PowerSave = batteryAttributionModel2;
        BatteryAttributionModel batteryAttributionModel3 = new BatteryAttributionModel("Charging", 2);
        Charging = batteryAttributionModel3;
        BatteryAttributionModel[] batteryAttributionModelArr = {batteryAttributionModel, batteryAttributionModel2, batteryAttributionModel3};
        $VALUES = batteryAttributionModelArr;
        EnumEntriesKt.enumEntries(batteryAttributionModelArr);
    }

    private BatteryAttributionModel(String str, int i) {
    }

    public static BatteryAttributionModel valueOf(String str) {
        return (BatteryAttributionModel) Enum.valueOf(BatteryAttributionModel.class, str);
    }

    public static BatteryAttributionModel[] values() {
        return (BatteryAttributionModel[]) $VALUES.clone();
    }
}

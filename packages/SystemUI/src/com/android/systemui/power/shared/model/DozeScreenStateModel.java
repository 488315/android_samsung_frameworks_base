package com.android.systemui.power.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DozeScreenStateModel {
    public static final /* synthetic */ DozeScreenStateModel[] $VALUES;
    public static final DozeScreenStateModel UNKNOWN;

    static {
        DozeScreenStateModel dozeScreenStateModel = new DozeScreenStateModel("UNKNOWN", 0);
        UNKNOWN = dozeScreenStateModel;
        DozeScreenStateModel[] dozeScreenStateModelArr = {dozeScreenStateModel, new DozeScreenStateModel("OFF", 1), new DozeScreenStateModel("ON", 2), new DozeScreenStateModel("DOZE", 3), new DozeScreenStateModel("DOZE_SUSPEND", 4), new DozeScreenStateModel("VR", 5), new DozeScreenStateModel("ON_SUSPEND", 6)};
        $VALUES = dozeScreenStateModelArr;
        EnumEntriesKt.enumEntries(dozeScreenStateModelArr);
    }

    private DozeScreenStateModel(String str, int i) {
    }

    public static DozeScreenStateModel valueOf(String str) {
        return (DozeScreenStateModel) Enum.valueOf(DozeScreenStateModel.class, str);
    }

    public static DozeScreenStateModel[] values() {
        return (DozeScreenStateModel[]) $VALUES.clone();
    }
}

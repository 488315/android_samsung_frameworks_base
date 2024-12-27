package com.android.systemui.statusbar.pipeline.mobile.data.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SimType {
    public static final /* synthetic */ SimType[] $VALUES;
    public static final SimType AIRTEL;
    public static final SimType CMCC;
    public static final SimType ETC;
    public static final SimType KT;
    public static final SimType LGT;
    public static final SimType NO_SIM;
    public static final SimType OFF;
    public static final SimType ORANGE;
    public static final SimType RELIANCE;
    public static final SimType SKT;
    public static final SimType VZW;

    static {
        SimType simType = new SimType("NO_SIM", 0);
        NO_SIM = simType;
        SimType simType2 = new SimType("OFF", 1);
        OFF = simType2;
        SimType simType3 = new SimType("ETC", 2);
        ETC = simType3;
        SimType simType4 = new SimType("AIRTEL", 3);
        AIRTEL = simType4;
        SimType simType5 = new SimType("CMCC", 4);
        CMCC = simType5;
        SimType simType6 = new SimType("RELIANCE", 5);
        RELIANCE = simType6;
        SimType simType7 = new SimType("SKT", 6);
        SKT = simType7;
        SimType simType8 = new SimType("KT", 7);
        KT = simType8;
        SimType simType9 = new SimType("LGT", 8);
        LGT = simType9;
        SimType simType10 = new SimType("ORANGE", 9);
        ORANGE = simType10;
        SimType simType11 = new SimType("VZW", 10);
        VZW = simType11;
        SimType[] simTypeArr = {simType, simType2, simType3, simType4, simType5, simType6, simType7, simType8, simType9, simType10, simType11};
        $VALUES = simTypeArr;
        EnumEntriesKt.enumEntries(simTypeArr);
    }

    private SimType(String str, int i) {
    }

    public static SimType valueOf(String str) {
        return (SimType) Enum.valueOf(SimType.class, str);
    }

    public static SimType[] values() {
        return (SimType[]) $VALUES.clone();
    }
}

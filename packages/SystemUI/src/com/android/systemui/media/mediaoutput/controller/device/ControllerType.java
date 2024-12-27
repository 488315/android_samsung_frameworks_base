package com.android.systemui.media.mediaoutput.controller.device;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ControllerType {
    public static final /* synthetic */ ControllerType[] $VALUES;
    public static final ControllerType AudioMirroring;
    public static final ControllerType AudioMirroringGroup;
    public static final ControllerType Bluetooth;
    public static final ControllerType BluetoothGroup;
    public static final ControllerType BuiltIn;
    public static final ControllerType ChromeCast;
    public static final ControllerType ChromeCastGroup;
    public static final ControllerType Dex;
    public static final ControllerType Disconnected;
    public static final ControllerType MusicShare;
    public static final ControllerType None;
    public static final ControllerType Remote;
    public static final ControllerType SmartMirroring;
    public static final ControllerType SmartView;

    static {
        ControllerType controllerType = new ControllerType("None", 0);
        None = controllerType;
        ControllerType controllerType2 = new ControllerType("BuiltIn", 1);
        BuiltIn = controllerType2;
        ControllerType controllerType3 = new ControllerType("Bluetooth", 2);
        Bluetooth = controllerType3;
        ControllerType controllerType4 = new ControllerType("Disconnected", 3);
        Disconnected = controllerType4;
        ControllerType controllerType5 = new ControllerType("ChromeCast", 4);
        ChromeCast = controllerType5;
        ControllerType controllerType6 = new ControllerType("AudioMirroring", 5);
        AudioMirroring = controllerType6;
        ControllerType controllerType7 = new ControllerType("MusicShare", 6);
        MusicShare = controllerType7;
        ControllerType controllerType8 = new ControllerType("SmartView", 7);
        SmartView = controllerType8;
        ControllerType controllerType9 = new ControllerType("Dex", 8);
        Dex = controllerType9;
        ControllerType controllerType10 = new ControllerType("SmartMirroring", 9);
        SmartMirroring = controllerType10;
        ControllerType controllerType11 = new ControllerType("BluetoothGroup", 10);
        BluetoothGroup = controllerType11;
        ControllerType controllerType12 = new ControllerType("ChromeCastGroup", 11);
        ChromeCastGroup = controllerType12;
        ControllerType controllerType13 = new ControllerType("AudioMirroringGroup", 12);
        AudioMirroringGroup = controllerType13;
        ControllerType controllerType14 = new ControllerType("Remote", 13);
        Remote = controllerType14;
        ControllerType[] controllerTypeArr = {controllerType, controllerType2, controllerType3, controllerType4, controllerType5, controllerType6, controllerType7, controllerType8, controllerType9, controllerType10, controllerType11, controllerType12, controllerType13, controllerType14};
        $VALUES = controllerTypeArr;
        EnumEntriesKt.enumEntries(controllerTypeArr);
    }

    private ControllerType(String str, int i) {
    }

    public static ControllerType valueOf(String str) {
        return (ControllerType) Enum.valueOf(ControllerType.class, str);
    }

    public static ControllerType[] values() {
        return (ControllerType[]) $VALUES.clone();
    }
}

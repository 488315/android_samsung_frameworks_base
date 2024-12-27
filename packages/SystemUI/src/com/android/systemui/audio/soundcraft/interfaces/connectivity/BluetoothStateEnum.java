package com.android.systemui.audio.soundcraft.interfaces.connectivity;

import com.samsung.android.bluetooth.SmepTag;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BluetoothStateEnum {
    public static final /* synthetic */ BluetoothStateEnum[] $VALUES;
    public static final BluetoothStateEnum ADAPTIVE;
    public static final BluetoothStateEnum AMBIENT;
    public static final BluetoothStateEnum ANC;
    public static final BluetoothStateEnum BATTERY_CRADLE;
    public static final BluetoothStateEnum BATTERY_LEFT;
    public static final BluetoothStateEnum BATTERY_RIGHT;
    public static final BluetoothStateEnum WEARING_L;
    public static final BluetoothStateEnum WEARING_R;
    private final SmepTag supportedTag;
    private final int tag;
    private final String title;

    static {
        int tag = SmepTag.STATE_BATTERY_L.getTag();
        SmepTag smepTag = SmepTag.FEATURE_BATTERY;
        BluetoothStateEnum bluetoothStateEnum = new BluetoothStateEnum("BATTERY_LEFT", 0, tag, smepTag, "L");
        BATTERY_LEFT = bluetoothStateEnum;
        BluetoothStateEnum bluetoothStateEnum2 = new BluetoothStateEnum("BATTERY_RIGHT", 1, SmepTag.STATE_BATTERY_R.getTag(), smepTag, "R");
        BATTERY_RIGHT = bluetoothStateEnum2;
        BluetoothStateEnum bluetoothStateEnum3 = new BluetoothStateEnum("BATTERY_CRADLE", 2, SmepTag.STATE_BATTERY_CRADLE.getTag(), smepTag, "Cradle");
        BATTERY_CRADLE = bluetoothStateEnum3;
        BluetoothStateEnum bluetoothStateEnum4 = new BluetoothStateEnum("ANC", 3, SmepTag.STATE_ANC.getTag(), SmepTag.FEATURE_ANC_LEVEL, "ANC");
        ANC = bluetoothStateEnum4;
        BluetoothStateEnum bluetoothStateEnum5 = new BluetoothStateEnum("AMBIENT", 4, SmepTag.STATE_AMBIENT.getTag(), SmepTag.FEATURE_AMBIENT_LEVEL, "Ambient");
        AMBIENT = bluetoothStateEnum5;
        BluetoothStateEnum bluetoothStateEnum6 = new BluetoothStateEnum("ADAPTIVE", 5, SmepTag.STATE_RESPONSIVE_HEARING.getTag(), SmepTag.FEATURE_RESPONSIVE_HEARING, "Adaptive");
        ADAPTIVE = bluetoothStateEnum6;
        int tag2 = SmepTag.STATE_WEARING_L.getTag();
        SmepTag smepTag2 = SmepTag.FEATURE_SWITCH_AUDIO_PATH_BY_WEARING_STATE;
        BluetoothStateEnum bluetoothStateEnum7 = new BluetoothStateEnum("WEARING_L", 6, tag2, smepTag2, "wearingL");
        WEARING_L = bluetoothStateEnum7;
        BluetoothStateEnum bluetoothStateEnum8 = new BluetoothStateEnum("WEARING_R", 7, SmepTag.STATE_WEARING_R.getTag(), smepTag2, "wearingR");
        WEARING_R = bluetoothStateEnum8;
        BluetoothStateEnum[] bluetoothStateEnumArr = {bluetoothStateEnum, bluetoothStateEnum2, bluetoothStateEnum3, bluetoothStateEnum4, bluetoothStateEnum5, bluetoothStateEnum6, bluetoothStateEnum7, bluetoothStateEnum8};
        $VALUES = bluetoothStateEnumArr;
        EnumEntriesKt.enumEntries(bluetoothStateEnumArr);
    }

    private BluetoothStateEnum(String str, int i, int i2, SmepTag smepTag, String str2) {
        this.tag = i2;
        this.supportedTag = smepTag;
        this.title = str2;
    }

    public static BluetoothStateEnum valueOf(String str) {
        return (BluetoothStateEnum) Enum.valueOf(BluetoothStateEnum.class, str);
    }

    public static BluetoothStateEnum[] values() {
        return (BluetoothStateEnum[]) $VALUES.clone();
    }

    public final SmepTag getSupportedTag() {
        return this.supportedTag;
    }

    public final int getTag() {
        return this.tag;
    }

    public final String getTitle() {
        return this.title;
    }
}

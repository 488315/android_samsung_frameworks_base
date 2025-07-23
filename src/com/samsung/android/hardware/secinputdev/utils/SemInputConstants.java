package com.samsung.android.hardware.secinputdev.utils;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.Credentials;
import android.security.keystore.KeyProperties;

/* loaded from: classes6.dex */
public class SemInputConstants {

    public enum Command implements Parcelable {
        NONE(0, false, false),
        GAME(1, false, true),
        SCAN_RATE(2, false, true),
        REFRESH_RATE(3, false, true),
        GLOVE(4, false, true),
        CLEAR_COVER(5, true, false),
        ORIENTATION(6, false, true),
        PROX_LP_SCAN(7, false, false),
        GRIP_DATA(8, true, false),
        SIP(9, true, false),
        NOTE_APP(10, true, true),
        TEMPERATURE(11, true, false),
        SPAY(12, true, true),
        STYLUS(13, true, false),
        BRUSH(14, true, false),
        AOD_RECT(15, true, false),
        AOD(16, true, true),
        FOD(17, true, true),
        FOD_ICON_VISIBLE(18, true, false),
        FOD_RECT(19, true, true),
        FOD_LP(20, true, true),
        SINGLETAP(21, true, true),
        EAR_DETECT(22, false, false),
        EXTERNAL_NOISE(23, false, true),
        TOUCHABLE_AREA(24, true, false),
        FP_INT_CONTROL(25, false, false),
        SYNC_CHANGED(26, true, false),
        POCKET_MODE(27, true, true),
        LOW_SENSITIVITY(28, true, true),
        CHARGER(29, false, true),
        AOT(30, false, true),
        FOLD_STATE(31, false, true),
        WIRELESS_CHARGER(32, true, true),
        TWO_FINGER_DOUBLETAP(33, false, true),
        SPEN_COVER_TYPE(34, true, true),
        SPEN_SAVING_MODE(35, true, true),
        SPEN_POWER(36, true, true),
        SPEN_BLE_CHARGING(37, true, true),
        SPEN_SCREEN_OFF_MEMO(38, false, true),
        SPEN_PDCT_LOWSENSITIVITY(39, true, true),
        SPEN_LOWCURRENT(40, true, false),
        ALWAYS_LOW_POWER_MODE(41, true, false),
        BEZEL(42, false, false),
        BODY_DETECTION(43, false, false),
        AWD(44, false, false),
        NFC_FIELD(45, false, false),
        SPEN_SET_WIRELESS_CHARGER_TX_ID(46, false, false),
        AOD_NOTI_RECT(47, true, false),
        FAST_RESPONSE(48, false, true);

        public static final Parcelable.Creator<Command> CREATOR = new Parcelable.Creator<Command>() { // from class: com.samsung.android.hardware.secinputdev.utils.SemInputConstants.Command.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Command createFromParcel(Parcel parcel) {
                return Command.getFromInt(parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Command[] newArray(int i) {
                return new Command[i];
            }
        };
        private boolean isExternal;
        private boolean needUpdate;
        private int value;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        Command(int i, boolean z, boolean z2) {
            this.value = i;
            this.isExternal = z;
            this.needUpdate = z2;
        }

        public int toInt() {
            return this.value;
        }

        public boolean isExternal() {
            return this.isExternal;
        }

        public boolean needUpdate() {
            return this.needUpdate;
        }

        public static Command getFromInt(int i) {
            for (Command command : values()) {
                if (command.value == i) {
                    return command;
                }
            }
            return NONE;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(toInt());
        }
    }

    public enum Device implements Parcelable {
        CURRENT_TSP(0, ""),
        DEFAULT_TSP(1, "TSP"),
        EXTRA_TSP(2, "TSP_SUB"),
        SPEN(11, "SPEN"),
        KEY(21, Credentials.EXTRA_PUBLIC_KEY),
        KEYBOARD(31, "KEYBOARD"),
        TAAS(41, "TAAS"),
        NOT_SPECIFIED(100, "");

        public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() { // from class: com.samsung.android.hardware.secinputdev.utils.SemInputConstants.Device.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Device createFromParcel(Parcel parcel) {
                return Device.getFromInt(parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Device[] newArray(int i) {
                return new Device[i];
            }
        };
        private String name;
        private int value;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        Device(int i, String str) {
            this.value = i;
            this.name = str;
        }

        public int toInt() {
            return this.value;
        }

        public static Device getFromInt(int i) {
            for (Device device : values()) {
                if (device.value == i) {
                    return device;
                }
            }
            return NOT_SPECIFIED;
        }

        public String getName() {
            return this.name;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.name + NavigationBarInflaterView.KEY_CODE_START + this.value + NavigationBarInflaterView.KEY_CODE_END;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(toInt());
        }
    }

    public enum DisplayState implements Parcelable {
        SHUTDOWN(-1, false),
        NONE(0, false),
        OFF(1, false),
        ON(2, false),
        DOZE(3, false),
        DOZE_SUSPEND(4, false),
        FORCE_OFF(21, true),
        FORCE_ON(22, true);

        public static final Parcelable.Creator<DisplayState> CREATOR = new Parcelable.Creator<DisplayState>() { // from class: com.samsung.android.hardware.secinputdev.utils.SemInputConstants.DisplayState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DisplayState createFromParcel(Parcel parcel) {
                return DisplayState.getFromInt(parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DisplayState[] newArray(int i) {
                return new DisplayState[i];
            }
        };
        private boolean isExternal;
        private int value;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        DisplayState(int i, boolean z) {
            this.value = i;
            this.isExternal = z;
        }

        public int toInt() {
            return this.value;
        }

        public boolean isExternal() {
            return this.isExternal;
        }

        public static DisplayState getFromInt(int i) {
            for (DisplayState displayState : values()) {
                if (displayState.value == i) {
                    return displayState;
                }
            }
            return NONE;
        }

        @Override // java.lang.Enum
        public String toString() {
            return super.toString() + NavigationBarInflaterView.KEY_CODE_START + this.value + NavigationBarInflaterView.KEY_CODE_END;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(toInt());
        }
    }

    public enum Property implements Parcelable {
        NONE(0, "", false, false),
        FEATURE(1, "support_feature", false, false),
        CMD_LIST(2, "cmd_list", false, false),
        SCRUB_POS(3, "scrub_pos", true, false),
        FOD_INFO(4, "fod_info", true, false),
        FOD_POS(5, "fod_pos", true, false),
        AOD_ACTIVE_AREA(6, "aod_active_area", true, false),
        AOD_ENABLE(7, "aod_enable", false, false),
        EPEN_POS(8, "get_epen_pos", true, false),
        PROX_OFF(9, "prox_power_off", false, true),
        HW_PARAM(10, "hw_param", false, false),
        LP_DUMP(11, "get_lp_dump", false, false),
        BLE_CHARGING(12, "epen_ble_charging_mode", false, false),
        EPEN_SAVING(13, "epen_saving_mode", false, false),
        EPEN_MEMO(14, "screen_off_memo_enable", false, false),
        HAND_EDGE(15, "hand_edge", false, false),
        EPEN_WCHARGING(16, "epen_wcharging_mode", false, false),
        ENABLED(17, "enabled", false, false),
        CMD(18, "cmd", false, false);

        public static final Parcelable.Creator<Property> CREATOR = new Parcelable.Creator<Property>() { // from class: com.samsung.android.hardware.secinputdev.utils.SemInputConstants.Property.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Property createFromParcel(Parcel parcel) {
                return Property.getFromInt(parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Property[] newArray(int i) {
                return new Property[i];
            }
        };
        private boolean isExternalRead;
        private boolean isExternalWrite;
        private String nodeName;
        private int value;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        Property(int i, String str, boolean z, boolean z2) {
            this.value = i;
            this.nodeName = str;
            this.isExternalRead = z;
            this.isExternalWrite = z2;
        }

        public int toInt() {
            return this.value;
        }

        public boolean isExternalRead() {
            return this.isExternalRead;
        }

        public boolean isExternalWrite() {
            return this.isExternalWrite;
        }

        public static Property getFromInt(int i) {
            for (Property property : values()) {
                if (property.value == i) {
                    return property;
                }
            }
            return NONE;
        }

        public String getNodeName() {
            return this.nodeName;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(toInt());
        }
    }

    public enum MotionType implements Parcelable {
        NONE(0, KeyProperties.DIGEST_NONE, 0),
        PALM_MUTE(1, "PALM", 1048576),
        PALM_SWIPE(2, "PALM_SWIPE", 4194304),
        EAR_DETECTION(3, "EAR_DETECTION", 0),
        GRIP_FILTER(4, "GRIP_FILTER", 0),
        AIVF(5, "AIVF", 2097152),
        AWD(6, "AWD", 8388608),
        STREAM(7, "STREAM", -1),
        CALLBACK(8, "CALLBACK", -1),
        APD(9, "POCKET_DETECT", 524288);

        public static final Parcelable.Creator<MotionType> CREATOR = new Parcelable.Creator<MotionType>() { // from class: com.samsung.android.hardware.secinputdev.utils.SemInputConstants.MotionType.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MotionType createFromParcel(Parcel parcel) {
                return MotionType.getFromInt(parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MotionType[] newArray(int i) {
                return new MotionType[i];
            }
        };
        private int feature;
        private String name;
        private int value;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        MotionType(int i, String str, int i2) {
            this.value = i;
            this.name = str;
            this.feature = i2;
        }

        public int toInt() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static MotionType getFromInt(int i) {
            for (MotionType motionType : values()) {
                if (motionType.value == i) {
                    return motionType;
                }
            }
            return NONE;
        }

        public static MotionType getFromName(String str) {
            for (MotionType motionType : values()) {
                if (motionType.name.equals(str)) {
                    return motionType;
                }
            }
            return NONE;
        }

        public boolean isFeatureEnabled(int i) {
            return (this.feature & i) != 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(toInt());
        }
    }
}

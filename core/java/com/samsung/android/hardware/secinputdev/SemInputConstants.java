package com.samsung.android.hardware.secinputdev;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.security.Credentials;
import android.security.keystore.KeyProperties;

/* loaded from: classes5.dex */
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
    SPEN_SET_WIRELESS_CHARGER_TX_ID(46, false, false);

    public static final Parcelable.Creator<Command> CREATOR =
        new Parcelable.Creator<
            Command>() { // from class:
                         // com.samsung.android.hardware.secinputdev.SemInputConstants.Command.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public Command createFromParcel(Parcel in) {
            return Command.getFromInt(in.readInt());
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public Command[] newArray(int size) {
            return new Command[size];
          }
        };
    private boolean isExternal;
    private boolean needUpdate;
    private int value;

    Command(int value, boolean isExternal, boolean needUpdate) {
      this.value = value;
      this.isExternal = isExternal;
      this.needUpdate = needUpdate;
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

    public static Command getFromInt(int value) {
      for (Command command : values()) {
        if (command.value == value) {
          return command;
        }
      }
      return NONE;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(toInt());
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
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

    public static final Parcelable.Creator<Device> CREATOR =
        new Parcelable.Creator<
            Device>() { // from class:
                        // com.samsung.android.hardware.secinputdev.SemInputConstants.Device.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public Device createFromParcel(Parcel in) {
            return Device.getFromInt(in.readInt());
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public Device[] newArray(int size) {
            return new Device[size];
          }
        };
    private String name;
    private int value;

    Device(int value, String name) {
      this.value = value;
      this.name = name;
    }

    public int toInt() {
      return this.value;
    }

    public static Device getFromInt(int value) {
      for (Device device : values()) {
        if (device.value == value) {
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
      return this.name
          + NavigationBarInflaterView.KEY_CODE_START
          + this.value
          + NavigationBarInflaterView.KEY_CODE_END;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(toInt());
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
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

    public static final Parcelable.Creator<DisplayState> CREATOR =
        new Parcelable.Creator<
            DisplayState>() { // from class:
                              // com.samsung.android.hardware.secinputdev.SemInputConstants.DisplayState.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public DisplayState createFromParcel(Parcel in) {
            return DisplayState.getFromInt(in.readInt());
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public DisplayState[] newArray(int size) {
            return new DisplayState[size];
          }
        };
    private boolean isExternal;
    private int value;

    DisplayState(int value, boolean isExternal) {
      this.value = value;
      this.isExternal = isExternal;
    }

    public int toInt() {
      return this.value;
    }

    public boolean isExternal() {
      return this.isExternal;
    }

    public static DisplayState getFromInt(int value) {
      for (DisplayState state : values()) {
        if (state.value == value) {
          return state;
        }
      }
      return NONE;
    }

    @Override // java.lang.Enum
    public String toString() {
      return super.toString()
          + NavigationBarInflaterView.KEY_CODE_START
          + this.value
          + NavigationBarInflaterView.KEY_CODE_END;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(toInt());
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
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

    public static final Parcelable.Creator<Property> CREATOR =
        new Parcelable.Creator<
            Property>() { // from class:
                          // com.samsung.android.hardware.secinputdev.SemInputConstants.Property.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public Property createFromParcel(Parcel in) {
            return Property.getFromInt(in.readInt());
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public Property[] newArray(int size) {
            return new Property[size];
          }
        };
    private boolean isExternalRead;
    private boolean isExternalWrite;
    private String nodeName;
    private int value;

    Property(int value, String nodeName, boolean isExternalRead, boolean isExternalWrite) {
      this.value = value;
      this.nodeName = nodeName;
      this.isExternalRead = isExternalRead;
      this.isExternalWrite = isExternalWrite;
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

    public static Property getFromInt(int value) {
      for (Property property : values()) {
        if (property.value == value) {
          return property;
        }
      }
      return NONE;
    }

    public String getNodeName() {
      return this.nodeName;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(toInt());
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
    }
  }

  public enum MotionType implements Parcelable {
    NONE(0, KeyProperties.DIGEST_NONE, 0),
    PALM_MUTE(1, SemInputDeviceManager.MOTION_ENABLE_TYPE_PALM, 1048576),
    PALM_SWIPE(2, SemInputDeviceManager.MOTION_ENABLE_TYPE_PALM_SWIPE, 4194304),
    EAR_DETECTION(3, "EAR_DETECTION", 0),
    GRIP_FILTER(4, "GRIP_FILTER", 0),
    AIVF(5, SemInputDeviceManager.MOTION_ENABLE_TYPE_AIVF, 2097152),
    AWD(6, SemInputDeviceManager.MOTION_ENABLE_TYPE_AWD, 8388608),
    CALLBACK(7, "CALLBACK", -1),
    STREAM(8, "STREAM", -1),
    APD(9, SemInputDeviceManager.MOTION_ENABLE_TYPE_POCKET_DETECT, 524288);

    public static final Parcelable.Creator<MotionType> CREATOR =
        new Parcelable.Creator<
            MotionType>() { // from class:
                            // com.samsung.android.hardware.secinputdev.SemInputConstants.MotionType.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public MotionType createFromParcel(Parcel in) {
            return MotionType.getFromInt(in.readInt());
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public MotionType[] newArray(int size) {
            return new MotionType[size];
          }
        };
    private int feature;
    private String name;
    private int value;

    MotionType(int value, String name, int feature) {
      this.value = value;
      this.name = name;
      this.feature = feature;
    }

    public int toInt() {
      return this.value;
    }

    public String getName() {
      return this.name;
    }

    public static MotionType getFromInt(int value) {
      for (MotionType type : values()) {
        if (type.value == value) {
          return type;
        }
      }
      return NONE;
    }

    public static MotionType getFromName(String name) {
      for (MotionType type : values()) {
        if (type.name.equals(name)) {
          return type;
        }
      }
      return NONE;
    }

    public boolean isFeatureEnabled(int feature) {
      if ((this.feature & feature) != 0) {
        return true;
      }
      return false;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(toInt());
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
    }
  }
}

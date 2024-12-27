package com.samsung.android.hardware.secinputdev;

import android.os.IBinder;
import android.util.Log;

public class SemInputDeviceManager {
    public static final int CLEAR_COVER_CLOSED = 3;
    public static final int CLEAR_COVER_OPENED = 0;
    public static final int DEVID_DEFAULT_TSP = 1;
    public static final int DEVID_EXTRA_TSP = 2;
    public static final int DEVID_KEY = 21;
    public static final int DEVID_KEYBOARD = 31;
    public static final int DEVID_SPEN = 11;
    public static final int DEVID_TAAS = 41;
    public static final int DEVID_TSP_MAX = 3;
    public static final int EAR_DETECT_DISABLE = 0;
    public static final int EAR_DETECT_HIGH_SENSE_ENABLE = 3;
    public static final int EAR_DETECT_LOW_SENSE_ENABLE = 1;
    public static final int EXTERNAL_NOISE_DEX = 1;
    public static final int FOD_PRESS_FAST_MODE_DISABLE = 0;
    public static final int FOD_PRESS_FAST_MODE_ENABLE = 1;
    public static final int FOD_STRICT_MODE_DISABLE = 0;
    public static final int FOD_STRICT_MODE_ENABLE = 1;
    public static final int FORCE_OFF = 21;
    public static final int FORCE_ON = 22;
    public static final int KEY_APPSELECT = 580;
    public static final int KEY_BACK = 158;
    public static final int KEY_EMERGENCY = 672;
    public static final int KEY_HOME = 172;
    public static final int KEY_HOT = 252;
    public static final int KEY_MICMUTE = 248;
    public static final int KEY_POWER = 116;
    public static final int KEY_RECENT = 254;
    public static final int KEY_VOLUMEDOWN = 114;
    public static final int KEY_VOLUMEUP = 115;
    public static final int LCD_DOZE1 = 3;
    public static final int LCD_DOZE2 = 4;
    public static final boolean LCD_EARLY_EVENT = false;
    public static final boolean LCD_LATE_EVENT = true;
    public static final int LCD_NONE = 0;
    public static final int LCD_OFF = 1;
    public static final int LCD_ON = 2;
    public static final int MODE_DISABLE = 0;
    public static final int MODE_ENABLE = 1;
    public static final int MOTION_CONTROL_AIVF_THRESHOLD_TO_HIGH = 2;
    public static final int MOTION_CONTROL_AIVF_THRESHOLD_TO_LOW = 0;
    public static final int MOTION_CONTROL_AIVF_THRESHOLD_TO_MID = 1;
    public static final int MOTION_CONTROL_ALL_ORIENTATION_0 = 20;
    public static final int MOTION_CONTROL_ALL_ORIENTATION_180 = 22;
    public static final int MOTION_CONTROL_ALL_ORIENTATION_270 = 23;
    public static final int MOTION_CONTROL_ALL_ORIENTATION_90 = 21;
    public static final int MOTION_CONTROL_ALL_SIP_IS_DISABLED = 12;
    public static final int MOTION_CONTROL_ALL_SIP_IS_ENABLED = 11;
    public static final String MOTION_CONTROL_TYPE_AIVF_EVENT = "AIVF_EVENT";
    public static final String MOTION_CONTROL_TYPE_AIVF_SENSITIVITY = "AIVF_SENSITIVITY";
    public static final String MOTION_CONTROL_TYPE_AIVF_THRESHOLD = "AIVF_THRESHOLD";
    public static final String MOTION_CONTROL_TYPE_AIVF_VOLUME = "AIVF_VOLUME";
    public static final String MOTION_CONTROL_TYPE_ALL = "ALL";
    public static final String MOTION_ENABLE_TYPE_AIVF = "AIVF";
    public static final String MOTION_ENABLE_TYPE_AWD = "AWD";
    public static final String MOTION_ENABLE_TYPE_PALM = "PALM";
    public static final String MOTION_ENABLE_TYPE_PALM_SWIPE = "PALM_SWIPE";
    public static final String MOTION_ENABLE_TYPE_POCKET_DETECT = "POCKET_DETECT";
    public static final int MOTION_ERROR_TYPE_NOT_LOADED_SERVICE = -2;
    public static final int MOTION_ERROR_TYPE_NOT_SUPPORT_HARDWARE = -1;
    public static final int MOTION_ERROR_TYPE_NOT_SUPPORT_MOTION = -3;
    public static final int MOTION_ERROR_TYPE_NULL_STRING = -4;
    public static final int MOTION_TYPE_AIVF = 5;
    public static final int MOTION_TYPE_AWD = 6;
    public static final int MOTION_TYPE_EAR_DETECTION = 3;
    public static final int MOTION_TYPE_GRIP_FILTER = 4;
    public static final int MOTION_TYPE_NONE = 0;
    public static final int MOTION_TYPE_PALM_MUTE = 1;
    public static final int MOTION_TYPE_PALM_SWIPE = 2;
    public static final int MOTION_TYPE_POCKET_DETECT = 9;
    public static final int MOTION_TYPE_RAWDATA_ALWAYS_ON = 7;
    public static final int RAWDATA_CALLBACK = 1;
    public static final int RAWDATA_LISTENER = 2;
    public static final int RAWDATA_TYPE_MAX = 3;
    public static final String REPORT_INFO_HANDEDGE = "handedge";
    public static final int RESULT_NG = -1;
    public static final int RESULT_OK = 0;
    public static final String RESULT_STR_NA = "NA";
    public static final String RESULT_STR_NG = "NG";
    public static final int SERVICE_SHUTDOWN = -1;
    public static final int SPEN_MODE_NONE = 0;
    public static final int SPEN_MODE_POGO_KEYBOARD = 2;
    public static final int SPEN_MODE_SPEN_COVER = 1;
    public static final int SUPPORT_AOT = 1;
    public static final int SUPPORT_INPUT_MONITOR = 65536;
    public static final int SUPPORT_MISCALIBRATION = 512;
    public static final int SUPPORT_MULTICALIBRATION = 1024;
    public static final int SUPPORT_OPENSHORT = 256;
    public static final int SUPPORT_PRESSURE = 2;
    public static final int SUPPORT_PROX_LP_SCAN_ENABLED = 64;
    public static final int SUPPORT_RAWDATA_MOTION_AIVF = 2097152;
    public static final int SUPPORT_RAWDATA_MOTION_AWD = 8388608;
    public static final int SUPPORT_RAWDATA_MOTION_PALM = 1048576;
    public static final int SUPPORT_RAWDATA_MOTION_PALM_SWIPE = 4194304;
    public static final int SUPPORT_RAWDATA_MOTION_POCKET_DETECT = 524288;
    public static final int SUPPORT_RAWDATA_TRANSFER = 262144;
    public static final int SUPPORT_RR120 = 4;
    public static final int SUPPORT_SYSINPUT_ENABLED = 32;
    public static final int SUPPORT_VRR = 8;
    public static final int SUPPORT_WIRELESS_TX = 16;
    public static final int SYNC_CHANGED_30_TO_60 = 1;
    private static final String TAG = "SemInputDeviceManager";
    public static int gloveMode = 0;
    private ISemInputDeviceManager service;

    public SemInputDeviceManager(ISemInputDeviceManager service) {
        if (service == null) {
            Log.d(TAG, "ISemInputDeviceManager is null");
        } else {
            Log.d(TAG, "SemInputDeviceManager ++");
            this.service = service;
        }
    }

    public int getSupportDevice(int devid) {
        if (this.service == null) {
            Log.e(TAG, "getSupportDevice: service is not enabled");
            return -1;
        }
        try {
            int ret = this.service.getSupportDevice(SemInputConstants.Device.getFromInt(devid));
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    private int activate(
            SemInputConstants.Device device, SemInputConstants.DisplayState mode, boolean state) {
        if (this.service == null) {
            Log.e(TAG, "activate: service is not enabled");
            return -1;
        }
        Log.d(TAG, "activate: " + device + " " + mode + "," + state);
        try {
            int ret = this.service.activate(device, mode, state);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    private int setProperty(
            SemInputConstants.Device device, SemInputConstants.Command command, String mode) {
        if (this.service == null) {
            Log.e(TAG, "setProperty: service is not enabled");
            return -1;
        }
        Log.d(
                TAG,
                "setProperty: "
                        + device
                        + " "
                        + SemInputConstants.Property.CMD
                        + ","
                        + command
                        + ","
                        + mode);
        try {
            int ret = this.service.setCommand(device, command, mode);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "setProperty: Failed to call interface: ", e);
            return -1;
        }
    }

    private int setProperty(SemInputConstants.Command command, String mode) {
        return setProperty(SemInputConstants.Device.NOT_SPECIFIED, command, mode);
    }

    private int setProperty(
            SemInputConstants.Device device, SemInputConstants.Property property, String mode) {
        if (this.service == null) {
            Log.e(TAG, "setProperty: service is not enabled");
            return -1;
        }
        Log.d(TAG, "setProperty: " + device + " " + property + "," + mode);
        try {
            int ret = this.service.setProperty(device, property, mode);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "setProperty: Failed to call interface: ", e);
            return -1;
        }
    }

    private String getProperty(
            SemInputConstants.Device device, SemInputConstants.Property property) {
        if (this.service == null) {
            Log.e(TAG, "getProperty: service is not enabled");
            return "NG";
        }
        Log.d(TAG, "getProperty: " + device + " " + property);
        try {
            String retval = this.service.getProperty(device, property);
            return retval;
        } catch (Exception e) {
            Log.e(TAG, "getProperty: Failed to call interface: ", e);
            return "NG";
        }
    }

    public String getCommandList(int devid) {
        if (this.service == null) {
            Log.e(TAG, "getCommandList: service is not enabled");
            return "NG";
        }
        try {
            String ret = this.service.getCommandList(SemInputConstants.Device.getFromInt(devid));
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return "NG";
        }
    }

    private String runCommand(SemInputConstants.Device device, String cmd) {
        if (this.service == null) {
            Log.e(TAG, "runCommand: service is not enabled");
            return "NG";
        }
        Log.d(TAG, "runCommand: " + device + " " + cmd);
        try {
            String ret = this.service.runCommand(device, cmd);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return "NG";
        }
    }

    public String runEmergency(int devid, String cmd) {
        return runCommand(SemInputConstants.Device.getFromInt(devid), cmd);
    }

    public String runEmergencyCurrentTsp(String cmd) {
        return runCommand(SemInputConstants.Device.CURRENT_TSP, cmd);
    }

    public String getKeyPressStateAll() {
        if (this.service == null) {
            Log.e(TAG, "getKeyPressStateAll: service is not enabled");
            return "";
        }
        try {
            String keystate = this.service.getKeyPressStateAll();
            return keystate;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return "";
        }
    }

    public boolean isKeyPressedByKeycode(int keycode) {
        if (this.service == null) {
            Log.e(TAG, "isKeyPressedByKeycode: service is not enabled");
            return false;
        }
        try {
            boolean keystate = this.service.isKeyPressedByKeycode(keycode);
            return keystate;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return false;
        }
    }

    public int setTspEnabled(int devid, int mode, boolean state) {
        return activate(
                SemInputConstants.Device.getFromInt(devid),
                SemInputConstants.DisplayState.getFromInt(mode),
                state);
    }

    public int setGripData(String mode) {
        return setProperty(
                SemInputConstants.Device.CURRENT_TSP, SemInputConstants.Command.GRIP_DATA, mode);
    }

    public int setSipMode(int mode) {
        int control = mode == 0 ? 12 : 11;
        setMotionControl(MOTION_CONTROL_TYPE_ALL, control, getClass().getName());
        return setProperty(SemInputConstants.Command.SIP, mode + "");
    }

    public int setNoteMode(int mode) {
        return setProperty(SemInputConstants.Command.NOTE_APP, mode + "");
    }

    public int setTemperature(int value) {
        return setProperty(SemInputConstants.Command.TEMPERATURE, value + "");
    }

    public int setSpayEnable(int mode) {
        return setProperty(SemInputConstants.Command.SPAY, mode + "");
    }

    public int setStylusEnable(int mode) {
        return setProperty(SemInputConstants.Command.STYLUS, mode + "");
    }

    public int setBrushEnable(int mode) {
        return setProperty(SemInputConstants.Command.BRUSH, mode + "");
    }

    public int setAodRect(int w, int h, int x, int y) {
        return setProperty(SemInputConstants.Command.AOD_RECT, w + "," + h + "," + x + "," + y);
    }

    public int setAodNotiRect(int w, int h, int x, int y) {
        return setProperty(
                SemInputConstants.Command.AOD_NOTI_RECT, w + "," + h + "," + x + "," + y);
    }

    public int setAodEnable(int mode) {
        return setProperty(SemInputConstants.Command.AOD, mode + "");
    }

    public int setAotEnable(int mode) {
        if (this.service == null) {
            Log.e(TAG, "setAotEnable: service is not enabled");
            return -1;
        }
        Log.d(TAG, "setAotEnable: " + mode);
        try {
            int ret = this.service.setAotEnable(mode);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    public int setFodEnable(int mode, int pressFast, int strictMode, int control) {
        if (mode == 1) {
            return setProperty(
                    SemInputConstants.Command.FOD,
                    mode + "," + pressFast + "," + strictMode + "," + control);
        }
        return setProperty(SemInputConstants.Command.FOD, mode + "");
    }

    public int setFodIconVisible(int mode) {
        return setProperty(SemInputConstants.Command.FOD_ICON_VISIBLE, mode + "");
    }

    public int setFodRect(int left, int top, int right, int bottom) {
        String mode = left + "," + top + "," + right + "," + bottom;
        return setProperty(
                SemInputConstants.Device.CURRENT_TSP, SemInputConstants.Command.FOD_RECT, mode);
    }

    public int setFodLpMode(int mode) {
        return setProperty(SemInputConstants.Command.FOD_LP, mode + "");
    }

    public int setSingletapEnable(int mode) {
        return setProperty(SemInputConstants.Command.SINGLETAP, mode + "");
    }

    public int setTouchableArea(int mode) {
        return setProperty(SemInputConstants.Command.TOUCHABLE_AREA, mode + "");
    }

    public int setSyncChanged(int mode) {
        return setProperty(SemInputConstants.Command.SYNC_CHANGED, mode + "");
    }

    public int setPocketModeEnable(int mode) {
        return setProperty(SemInputConstants.Command.POCKET_MODE, mode + "");
    }

    public int setLowSensitivityModeEnable(int mode) {
        return setProperty(SemInputConstants.Command.LOW_SENSITIVITY, mode + "");
    }

    public int setLowSensitivityMode(int mode, int level) {
        return setProperty(SemInputConstants.Command.LOW_SENSITIVITY, mode + "," + level);
    }

    public int setAlwaysLowPowerMode(int devid, int enable) {
        return setProperty(
                SemInputConstants.Device.getFromInt(devid),
                SemInputConstants.Command.ALWAYS_LOW_POWER_MODE,
                enable + "");
    }

    public int getTspSupportFeature(int devid) {
        if (this.service == null) {
            Log.e(TAG, "getTspSupportFeature: service is not enabled");
            return 0;
        }
        try {
            int ret = this.service.getTspSupportFeature(SemInputConstants.Device.getFromInt(devid));
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return 0;
        }
    }

    public String getScrubPosition(int devid) {
        return getProperty(
                SemInputConstants.Device.getFromInt(devid), SemInputConstants.Property.SCRUB_POS);
    }

    public int setProxPowerOff(int devid, int mode) {
        return setProperty(
                SemInputConstants.Device.getFromInt(devid),
                SemInputConstants.Property.PROX_OFF,
                mode + "");
    }

    public int setWirelessChargingMode(int devid, int mode) {
        if (devid == 1) {
            return setProperty(
                    SemInputConstants.Device.NOT_SPECIFIED,
                    SemInputConstants.Command.WIRELESS_CHARGER,
                    mode + "");
        }
        return setProperty(
                SemInputConstants.Device.getFromInt(devid),
                SemInputConstants.Command.WIRELESS_CHARGER,
                mode + "");
    }

    public void setCoverMode(boolean switchState, int coverType) {
        if (switchState) {
            setProperty(
                    SemInputConstants.Device.DEFAULT_TSP,
                    SemInputConstants.Command.CLEAR_COVER,
                    gloveMode + "");
            setProperty(
                    SemInputConstants.Device.EXTRA_TSP,
                    SemInputConstants.Command.CLEAR_COVER,
                    gloveMode + "");
            setProperty(
                    SemInputConstants.Device.SPEN,
                    SemInputConstants.Command.CLEAR_COVER,
                    "0," + coverType);
            return;
        }
        setProperty(
                SemInputConstants.Device.NOT_SPECIFIED,
                SemInputConstants.Command.CLEAR_COVER,
                "3," + coverType);
    }

    public String getFodInfo(int devid) {
        return getProperty(
                SemInputConstants.Device.getFromInt(devid), SemInputConstants.Property.FOD_INFO);
    }

    public String getFodPosition(int devid) {
        return getProperty(
                SemInputConstants.Device.getFromInt(devid), SemInputConstants.Property.FOD_POS);
    }

    public String getAodActiveArea(int devid) {
        return getProperty(
                SemInputConstants.Device.getFromInt(devid),
                SemInputConstants.Property.AOD_ACTIVE_AREA);
    }

    public int setSpenEnabled(int devid, int mode, boolean state) {
        return activate(
                SemInputConstants.Device.getFromInt(devid),
                SemInputConstants.DisplayState.getFromInt(mode),
                state);
    }

    public int setSpenCoverType(int type) {
        return setProperty(
                SemInputConstants.Device.SPEN,
                SemInputConstants.Command.SPEN_COVER_TYPE,
                type + "");
    }

    public String getSpenPosition() {
        return getProperty(SemInputConstants.Device.SPEN, SemInputConstants.Property.EPEN_POS);
    }

    public int setSpenPower(int mode) {
        return setProperty(
                SemInputConstants.Device.SPEN, SemInputConstants.Command.SPEN_POWER, mode + "");
    }

    public int setSpenBleChargeMode(int mode) {
        return setProperty(
                SemInputConstants.Device.SPEN,
                SemInputConstants.Command.SPEN_BLE_CHARGING,
                mode + "");
    }

    public int setSpenPdctLowSensitivityEnable(int mode) {
        return setProperty(
                SemInputConstants.Device.SPEN,
                SemInputConstants.Command.SPEN_PDCT_LOWSENSITIVITY,
                mode + "");
    }

    public int setSpenLowCurrentMode(int mode) {
        return setProperty(
                SemInputConstants.Device.SPEN,
                SemInputConstants.Command.SPEN_LOWCURRENT,
                mode + "");
    }

    public int setSpenPowerSavingMode(int mode) {
        return setProperty(
                SemInputConstants.Device.SPEN,
                SemInputConstants.Command.SPEN_SAVING_MODE,
                mode + "");
    }

    public int getDeviceEnabled(int devid) {
        if (this.service == null) {
            Log.e(TAG, "getDeviceEnabled: service is not enabled");
            return -1;
        }
        try {
            int ret = this.service.getDeviceEnabled(SemInputConstants.Device.getFromInt(devid));
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    public boolean registerListener(int type, String client) {
        if (this.service == null) {
            Log.e(TAG, "registerListener: service is not enabled");
            return false;
        }
        try {
            boolean ret = this.service.registerListener(null, type, client);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return false;
        }
    }

    public boolean unregisterListener(int type, String client) {
        if (this.service == null) {
            Log.e(TAG, "unregisterListener: service is not enabled");
            return false;
        }
        try {
            boolean ret = this.service.unregisterListener(null, type, client);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return false;
        }
    }

    public boolean registerListener(IBinder binder, int type, String client) {
        if (this.service == null) {
            Log.e(TAG, "registerListener: service is not enabled");
            return false;
        }
        try {
            boolean ret = this.service.registerListener(binder, type, client);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return false;
        }
    }

    public boolean unregisterListener(IBinder binder, int type, String client) {
        if (this.service == null) {
            Log.e(TAG, "unregisterListener: service is not enabled");
            return false;
        }
        try {
            boolean ret = this.service.unregisterListener(binder, type, client);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return false;
        }
    }

    public int sendRawdataTsp(int devid, int[] data) {
        if (this.service == null) {
            Log.e(TAG, "sendRawdataTsp: service is not enabled");
            return -1;
        }
        try {
            int ret = this.service.sendRawdataTsp(SemInputConstants.Device.getFromInt(devid), data);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    public boolean isSupportMotion(String type) {
        if (this.service == null) {
            Log.e(TAG, "isSupportMotion: service is not enabled");
            return false;
        }
        try {
            boolean ret = this.service.isSupportMotion(type);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return false;
        }
    }

    public boolean isSupportMotion(SemInputConstants.MotionType motionType) {
        return isSupportMotion(motionType.getName());
    }

    public int enableMotion(String type, boolean enable, String client) {
        if (this.service == null) {
            Log.e(TAG, "enableMotion: service is not enabled");
            return -1;
        }
        try {
            int ret = this.service.enableMotion(type, enable, client);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    public int enableMotion(
            SemInputConstants.MotionType motionType, boolean enable, String client) {
        return enableMotion(motionType.getName(), enable, client);
    }

    public int setMotionControl(String subtype, int control, String client) {
        if (this.service == null) {
            Log.e(TAG, "setMotionControl: service is not enabled");
            return -1;
        }
        try {
            int ret = this.service.setMotionControl(subtype, control, client);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    public int isEnableMotion(String type, String client) {
        if (this.service == null) {
            Log.e(TAG, "isEnableMotion: service is not enabled");
            return -1;
        }
        try {
            int ret = this.service.isEnableMotion(type, client);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    public int isEnableMotion(SemInputConstants.MotionType motionType, String client) {
        return isEnableMotion(motionType.getName(), client);
    }

    public int getMotionControl(String subtype, String client) {
        if (this.service == null) {
            Log.e(TAG, "getMotionControl: service is not enabled");
            return -1;
        }
        try {
            int ret = this.service.getMotionControl(subtype, client);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }
}

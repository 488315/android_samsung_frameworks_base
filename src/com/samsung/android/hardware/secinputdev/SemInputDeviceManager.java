package com.samsung.android.hardware.secinputdev;

import android.os.IBinder;
import android.util.Log;
import com.samsung.android.hardware.secinputdev.utils.SemInputConstants;

/* loaded from: classes6.dex */
public class SemInputDeviceManager {
    public static final int DEVID_DEFAULT_TSP = 1;
    public static final int DEVID_EXTRA_TSP = 2;
    public static final int DEVID_KEY = 21;
    public static final int DEVID_KEYBOARD = 31;
    public static final int DEVID_SPEN = 11;
    public static final int DEVID_TAAS = 41;
    public static final int DEVID_TSP_MAX = 3;
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
    public static final int MODE_DISABLE = 0;
    public static final int MODE_ENABLE = 1;
    public static final int MOTION_CONTROL_AIVF_THRESHOLD_TO_HIGH = 2;
    public static final int MOTION_CONTROL_AIVF_THRESHOLD_TO_LOW = 0;
    public static final int MOTION_CONTROL_AIVF_THRESHOLD_TO_MID = 1;
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
    public static final int MOTION_TYPE_CALLBACK = 8;
    public static final int MOTION_TYPE_EAR_DETECTION = 3;
    public static final int MOTION_TYPE_GRIP_FILTER = 4;
    public static final int MOTION_TYPE_NONE = 0;
    public static final int MOTION_TYPE_PALM_MUTE = 1;
    public static final int MOTION_TYPE_PALM_SWIPE = 2;
    public static final int MOTION_TYPE_POCKET_DETECT = 9;
    public static final int MOTION_TYPE_STREAM = 7;
    public static final int RESULT_NG = -1;
    public static final int RESULT_OK = 0;
    public static final String RESULT_STR_NA = "NA";
    public static final String RESULT_STR_NG = "NG";
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
    private static final String TAG = "SemInputDeviceManager";
    public static int gloveMode;
    private ISemInputDeviceManager service;

    public SemInputDeviceManager(ISemInputDeviceManager iSemInputDeviceManager) {
        if (iSemInputDeviceManager == null) {
            Log.d(TAG, "ISemInputDeviceManager is null");
        } else {
            Log.d(TAG, "SemInputDeviceManager ++");
            this.service = iSemInputDeviceManager;
        }
    }

    public int getSupportDevice(int i) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "getSupportDevice: service is not enabled");
            return -1;
        }
        try {
            return iSemInputDeviceManager.getSupportDevice(SemInputConstants.Device.getFromInt(i));
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    private int activate(SemInputConstants.Device device, SemInputConstants.DisplayState displayState, boolean z) {
        if (this.service == null) {
            Log.e(TAG, "activate: service is not enabled");
            return -1;
        }
        Log.d(TAG, "activate: " + device + " " + displayState + "," + z);
        try {
            return this.service.activate(device, displayState, z);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    private int setProperty(SemInputConstants.Device device, SemInputConstants.Command command, String str) {
        if (this.service == null) {
            Log.e(TAG, "setProperty: service is not enabled");
            return -1;
        }
        Log.d(TAG, "setProperty: " + device + " " + SemInputConstants.Property.CMD + "," + command + "," + str);
        try {
            return this.service.setCommand(device, command, str);
        } catch (Exception e) {
            Log.e(TAG, "setProperty: Failed to call interface: ", e);
            return -1;
        }
    }

    private int setProperty(SemInputConstants.Command command, String str) {
        return setProperty(SemInputConstants.Device.NOT_SPECIFIED, command, str);
    }

    private int setProperty(SemInputConstants.Device device, SemInputConstants.Property property, String str) {
        if (this.service == null) {
            Log.e(TAG, "setProperty: service is not enabled");
            return -1;
        }
        Log.d(TAG, "setProperty: " + device + " " + property + "," + str);
        try {
            return this.service.setProperty(device, property, str);
        } catch (Exception e) {
            Log.e(TAG, "setProperty: Failed to call interface: ", e);
            return -1;
        }
    }

    private String getProperty(SemInputConstants.Device device, SemInputConstants.Property property) {
        if (this.service == null) {
            Log.e(TAG, "getProperty: service is not enabled");
            return "NG";
        }
        Log.d(TAG, "getProperty: " + device + " " + property);
        try {
            return this.service.getProperty(device, property);
        } catch (Exception e) {
            Log.e(TAG, "getProperty: Failed to call interface: ", e);
            return "NG";
        }
    }

    public String getCommandList(int i) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "getCommandList: service is not enabled");
            return "NG";
        }
        try {
            return iSemInputDeviceManager.getCommandList(SemInputConstants.Device.getFromInt(i));
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return "NG";
        }
    }

    private String runCommand(SemInputConstants.Device device, String str) {
        if (this.service == null) {
            Log.e(TAG, "runCommand: service is not enabled");
            return "NG";
        }
        Log.d(TAG, "runCommand: " + device + " " + str);
        try {
            return this.service.runCommand(device, str);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return "NG";
        }
    }

    public String runEmergency(int i, String str) {
        return runCommand(SemInputConstants.Device.getFromInt(i), str);
    }

    public String runEmergencyCurrentTsp(String str) {
        return runCommand(SemInputConstants.Device.CURRENT_TSP, str);
    }

    public String getKeyPressStateAll() {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "getKeyPressStateAll: service is not enabled");
            return "";
        }
        try {
            return iSemInputDeviceManager.getKeyPressStateAll();
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return "";
        }
    }

    public boolean isKeyPressedByKeycode(int i) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "isKeyPressedByKeycode: service is not enabled");
            return false;
        }
        try {
            return iSemInputDeviceManager.isKeyPressedByKeycode(i);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return false;
        }
    }

    public int setTspEnabled(int i, int i2, boolean z) {
        return activate(SemInputConstants.Device.getFromInt(i), SemInputConstants.DisplayState.getFromInt(i2), z);
    }

    public int setGripData(String str) {
        return setProperty(SemInputConstants.Device.CURRENT_TSP, SemInputConstants.Command.GRIP_DATA, str);
    }

    public int setSipMode(int i) {
        setMotionControl("ALL", i == 0 ? 12 : 11, getClass().getName());
        return setProperty(SemInputConstants.Command.SIP, i + "");
    }

    public int setNoteMode(int i) {
        return setProperty(SemInputConstants.Command.NOTE_APP, i + "");
    }

    public int setTemperature(int i) {
        return setProperty(SemInputConstants.Command.TEMPERATURE, i + "");
    }

    public int setSpayEnable(int i) {
        return setProperty(SemInputConstants.Command.SPAY, i + "");
    }

    public int setStylusEnable(int i) {
        return setProperty(SemInputConstants.Command.STYLUS, i + "");
    }

    public int setBrushEnable(int i) {
        return setProperty(SemInputConstants.Command.BRUSH, i + "");
    }

    public int setAodRect(int i, int i2, int i3, int i4) {
        return setProperty(SemInputConstants.Command.AOD_RECT, i + "," + i2 + "," + i3 + "," + i4);
    }

    public int setAodNotiRect(int i, int i2, int i3, int i4) {
        return setProperty(SemInputConstants.Command.AOD_NOTI_RECT, i + "," + i2 + "," + i3 + "," + i4);
    }

    public int setAodEnable(int i) {
        return setProperty(SemInputConstants.Command.AOD, i + "");
    }

    public int setAotEnable(int i) {
        if (this.service == null) {
            Log.e(TAG, "setAotEnable: service is not enabled");
            return -1;
        }
        Log.d(TAG, "setAotEnable: " + i);
        try {
            return this.service.setAotEnable(i);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    public int setFodEnable(int i, int i2, int i3, int i4) {
        if (i == 1) {
            return setProperty(SemInputConstants.Command.FOD, i + "," + i2 + "," + i3 + "," + i4);
        }
        return setProperty(SemInputConstants.Command.FOD, i + "");
    }

    public int setFodIconVisible(int i) {
        return setProperty(SemInputConstants.Command.FOD_ICON_VISIBLE, i + "");
    }

    public int setFodRect(int i, int i2, int i3, int i4) {
        return setProperty(SemInputConstants.Device.CURRENT_TSP, SemInputConstants.Command.FOD_RECT, i + "," + i2 + "," + i3 + "," + i4);
    }

    public int setFodLpMode(int i) {
        return setProperty(SemInputConstants.Command.FOD_LP, i + "");
    }

    public int setSingletapEnable(int i) {
        return setProperty(SemInputConstants.Command.SINGLETAP, i + "");
    }

    public int setTouchableArea(int i) {
        return setProperty(SemInputConstants.Command.TOUCHABLE_AREA, i + "");
    }

    public int setSyncChanged(int i) {
        return setProperty(SemInputConstants.Command.SYNC_CHANGED, i + "");
    }

    public int setPocketModeEnable(int i) {
        return setProperty(SemInputConstants.Command.POCKET_MODE, i + "");
    }

    public int setLowSensitivityModeEnable(int i) {
        return setProperty(SemInputConstants.Command.LOW_SENSITIVITY, i + "");
    }

    public int setLowSensitivityMode(int i, int i2) {
        return setProperty(SemInputConstants.Command.LOW_SENSITIVITY, i + "," + i2);
    }

    public int setAlwaysLowPowerMode(int i, int i2) {
        return setProperty(SemInputConstants.Device.getFromInt(i), SemInputConstants.Command.ALWAYS_LOW_POWER_MODE, i2 + "");
    }

    public int getTspSupportFeature(int i) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "getTspSupportFeature: service is not enabled");
            return 0;
        }
        try {
            return iSemInputDeviceManager.getTspSupportFeature(SemInputConstants.Device.getFromInt(i));
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return 0;
        }
    }

    public String getScrubPosition(int i) {
        return getProperty(SemInputConstants.Device.getFromInt(i), SemInputConstants.Property.SCRUB_POS);
    }

    public int setProxPowerOff(int i, int i2) {
        return setProperty(SemInputConstants.Device.getFromInt(i), SemInputConstants.Property.PROX_OFF, i2 + "");
    }

    public int setWirelessChargingMode(int i, int i2) {
        if (i == 1) {
            return setProperty(SemInputConstants.Device.NOT_SPECIFIED, SemInputConstants.Command.WIRELESS_CHARGER, i2 + "");
        }
        return setProperty(SemInputConstants.Device.getFromInt(i), SemInputConstants.Command.WIRELESS_CHARGER, i2 + "");
    }

    public void setCoverMode(boolean z, int i) {
        if (z) {
            setProperty(SemInputConstants.Device.DEFAULT_TSP, SemInputConstants.Command.CLEAR_COVER, gloveMode + "");
            setProperty(SemInputConstants.Device.EXTRA_TSP, SemInputConstants.Command.CLEAR_COVER, gloveMode + "");
            setProperty(SemInputConstants.Device.SPEN, SemInputConstants.Command.CLEAR_COVER, "0," + i);
            return;
        }
        setProperty(SemInputConstants.Device.NOT_SPECIFIED, SemInputConstants.Command.CLEAR_COVER, "3," + i);
    }

    public String getFodInfo(int i) {
        return getProperty(SemInputConstants.Device.getFromInt(i), SemInputConstants.Property.FOD_INFO);
    }

    public String getFodPosition(int i) {
        return getProperty(SemInputConstants.Device.getFromInt(i), SemInputConstants.Property.FOD_POS);
    }

    public String getAodActiveArea(int i) {
        return getProperty(SemInputConstants.Device.getFromInt(i), SemInputConstants.Property.AOD_ACTIVE_AREA);
    }

    public int setSpenEnabled(int i, int i2, boolean z) {
        return activate(SemInputConstants.Device.getFromInt(i), SemInputConstants.DisplayState.getFromInt(i2), z);
    }

    public int setSpenCoverType(int i) {
        return setProperty(SemInputConstants.Device.SPEN, SemInputConstants.Command.SPEN_COVER_TYPE, i + "");
    }

    public String getSpenPosition() {
        return getProperty(SemInputConstants.Device.SPEN, SemInputConstants.Property.EPEN_POS);
    }

    public int setSpenPower(int i) {
        return setProperty(SemInputConstants.Device.SPEN, SemInputConstants.Command.SPEN_POWER, i + "");
    }

    public int setSpenBleChargeMode(int i) {
        return setProperty(SemInputConstants.Device.SPEN, SemInputConstants.Command.SPEN_BLE_CHARGING, i + "");
    }

    public int setSpenPdctLowSensitivityEnable(int i) {
        return setProperty(SemInputConstants.Device.SPEN, SemInputConstants.Command.SPEN_PDCT_LOWSENSITIVITY, i + "");
    }

    public int setSpenLowCurrentMode(int i) {
        return setProperty(SemInputConstants.Device.SPEN, SemInputConstants.Command.SPEN_LOWCURRENT, i + "");
    }

    public int setSpenPowerSavingMode(int i) {
        return setProperty(SemInputConstants.Device.SPEN, SemInputConstants.Command.SPEN_SAVING_MODE, i + "");
    }

    public int getDeviceEnabled(int i) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "getDeviceEnabled: service is not enabled");
            return -1;
        }
        try {
            return iSemInputDeviceManager.getDeviceEnabled(SemInputConstants.Device.getFromInt(i));
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    public boolean registerListener(int i, String str) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "registerListener: service is not enabled");
            return false;
        }
        try {
            return iSemInputDeviceManager.registerListener(null, i, str);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return false;
        }
    }

    public boolean unregisterListener(int i, String str) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "unregisterListener: service is not enabled");
            return false;
        }
        try {
            return iSemInputDeviceManager.unregisterListener(null, i, str);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return false;
        }
    }

    public boolean registerListener(IBinder iBinder, int i, String str) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "registerListener: service is not enabled");
            return false;
        }
        try {
            return iSemInputDeviceManager.registerListener(iBinder, i, str);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return false;
        }
    }

    public boolean unregisterListener(IBinder iBinder, int i, String str) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "unregisterListener: service is not enabled");
            return false;
        }
        try {
            return iSemInputDeviceManager.unregisterListener(iBinder, i, str);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return false;
        }
    }

    public int sendRawdataTsp(int i, int[] iArr) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "sendRawdataTsp: service is not enabled");
            return -1;
        }
        try {
            return iSemInputDeviceManager.sendRawdataTsp(SemInputConstants.Device.getFromInt(i), iArr);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    public boolean isSupportMotion(String str) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "isSupportMotion: service is not enabled");
            return false;
        }
        try {
            return iSemInputDeviceManager.isSupportMotion(str);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return false;
        }
    }

    public boolean isSupportMotion(SemInputConstants.MotionType motionType) {
        return isSupportMotion(motionType.getName());
    }

    public int enableMotion(String str, boolean z, String str2) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "enableMotion: service is not enabled");
            return -1;
        }
        try {
            return iSemInputDeviceManager.enableMotion(str, z, str2);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    public int enableMotion(SemInputConstants.MotionType motionType, boolean z, String str) {
        return enableMotion(motionType.getName(), z, str);
    }

    public int setMotionControl(String str, int i, String str2) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "setMotionControl: service is not enabled");
            return -1;
        }
        try {
            return iSemInputDeviceManager.setMotionControl(str, i, str2);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    public int isEnableMotion(String str, String str2) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "isEnableMotion: service is not enabled");
            return -1;
        }
        try {
            return iSemInputDeviceManager.isEnableMotion(str, str2);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }

    public int isEnableMotion(SemInputConstants.MotionType motionType, String str) {
        return isEnableMotion(motionType.getName(), str);
    }

    public int getMotionControl(String str, String str2) {
        ISemInputDeviceManager iSemInputDeviceManager = this.service;
        if (iSemInputDeviceManager == null) {
            Log.e(TAG, "getMotionControl: service is not enabled");
            return -1;
        }
        try {
            return iSemInputDeviceManager.getMotionControl(str, str2);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call interface: ", e);
            return -1;
        }
    }
}

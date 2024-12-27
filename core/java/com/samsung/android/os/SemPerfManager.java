package com.samsung.android.os;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.Looper;
import android.os.Message;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;

import com.sec.android.sdhms.ISamsungDeviceHealthManager;

public class SemPerfManager {
    public static final String COMMAND_ACTIVITY_EXECUTION = "EXEC_ACTIVITY";
    public static final String COMMAND_BROWSER_DASH_MODE = "SBROWSER_DASH_MODE";
    public static final String COMMAND_BROWSER_PAGE_LOADING = "SBROWSER_PAGE_LOADING";
    public static final String COMMAND_BUS_DCVS_GOVERNOR_CHANGE = "BUS_DCVS_GOVERNOR";
    public static final String COMMAND_FINGER_HOVER_OFF = "FINGER_HOVER_OFF";
    public static final String COMMAND_FINGER_HOVER_ON = "FINGER_HOVER_ON";
    public static final String COMMAND_GAME_TOUCH_BOOSTER = "GAME_TOUCH_BOOSTER";
    public static final String COMMAND_GENERAL_SHELL = "GENERAL_SHELL";
    public static final String COMMAND_GESTURE_DETECTED = "GESTURE_DETECTED";
    public static final String COMMAND_HOVERING_EVENT = "HOVERING_EVENT";
    public static final String COMMAND_REQUEST_CACHE_DROP = "REQ_DROP_CACHE";
    public static final String COMMAND_SAMSUNG_SIP = "KNOWN_APP_SIP";
    public static final String COMMAND_SCREEN_ROTATION = "TYPE_WINDOW_ORIENTATION";
    public static final String COMMAND_SCROLL = "TYPE_SCROLL";
    public static final String COMMAND_SMOOTH_SCROLL = "SMOOTH_SCROLL";
    public static final String COMMAND_SUSTAINED_PERF = "SUSTAINED_PERF";
    public static final String COMMAND_USB_TETHERING = "USBTETHERING";
    public static final String COMMAND_VR_MODE = "VR_MODE";
    private static final String LOG_TAG = "SemPerfManager";
    public static final String VALUE_GAME_TOUCH_BOOSTER_HIGH = "high_game_touch_booster";
    public static final String VALUE_GAME_TOUCH_BOOSTER_LOW = "low_game_touch_booster";
    public static final String VALUE_GAME_TOUCH_BOOSTER_MID = "mid_game_touch_booster";
    public static final String VALUE_GAME_TOUCH_BOOSTER_OFF = "off_game_touch_booster";
    static boolean sIsDebugLevelHigh =
            "0x4948".equals(SystemProperties.get("ro.debug_level", "0x4f4c"));
    static String BOARD_PLATFORM = SystemProperties.get("ro.board.platform");
    static final String DEVICE_TYPE = SystemProperties.get("ro.build.characteristics");
    private static Handler mCommandHandler =
            new Handler(
                    Looper.getMainLooper()) { // from class: com.samsung.android.os.SemPerfManager.1
                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    Bundle bundle = msg.getData();
                    String type = bundle.getString("type");
                    String value = bundle.getString("value");
                    SemPerfManager.sendCommand(type, value);
                }
            };
    static volatile ICustomFrequencyManager sCfmsService = null;
    static volatile ISamsungDeviceHealthManager sdhmservice = null;

    protected SemPerfManager() {}

    public static void onScrollEvent(boolean isScroll) {
        sendCommandToSsrm(COMMAND_SCROLL, isScroll ? "TRUE" : "FALSE");
    }

    public static void onSmoothScrollEvent(boolean isScroll) {
        sendCommandToSsrm("SMOOTH_SCROLL", isScroll ? "TRUE" : "FALSE");
    }

    public static void sendCommandToSsrm(String type, String value) {
        try {
            Message msg = mCommandHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            bundle.putString("value", value);
            msg.setData(bundle);
            mCommandHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sendCommand(String type, String value) {
        try {
            if (sCfmsService == null || sdhmservice == null) {
                initService();
            }
            if (!COMMAND_SCROLL.equals(type)
                    && !"SMOOTH_SCROLL".equals(type)
                    && !"GESTURE_DETECTED".equals(type)
                    && !"TASK_BOOST".equals(type)
                    && !"ANIMATION_BOOST".equals(type)) {
                if ((COMMAND_HOVERING_EVENT.equals(type)
                                || COMMAND_BROWSER_DASH_MODE.equals(type)
                                || COMMAND_SUSTAINED_PERF.equals(type)
                                || COMMAND_GAME_TOUCH_BOOSTER.equals(type)
                                || "NORMAL_TOUCH_BOOSTER".equals(type)
                                || "MIDGROUND_PROCESS_DETECT".equals(type))
                        && sdhmservice != null) {
                    sdhmservice.sendCommand(type, value);
                    return;
                }
                return;
            }
            if (sCfmsService != null) {
                sCfmsService.sendCommandToSSRM(type, value);
            }
        } catch (DeadObjectException e) {
            sCfmsService = null;
            sdhmservice = null;
            e.printStackTrace();
        } catch (Exception e2) {
            sCfmsService = null;
            sdhmservice = null;
            e2.printStackTrace();
        }
    }

    private static void initService() {
        IBinder b;
        IBinder b2;
        try {
            if (sCfmsService == null
                    && (b2 = ServiceManager.getService(Context.CFMS_SERVICE)) != null) {
                sCfmsService = ICustomFrequencyManager.Stub.asInterface(b2);
            }
            if (sdhmservice == null && (b = ServiceManager.getService("sdhms")) != null) {
                sdhmservice = ISamsungDeviceHealthManager.Stub.asInterface(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logOnEng(String tag, String msg) {
        if (sIsDebugLevelHigh) {
            Log.i(tag, msg);
        }
    }
}

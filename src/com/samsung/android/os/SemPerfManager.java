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

/* loaded from: classes6.dex */
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
    static boolean sIsDebugLevelHigh = "0x4948".equals(SystemProperties.get("ro.debug_level", "0x4f4c"));
    static String BOARD_PLATFORM = SystemProperties.get("ro.board.platform");
    static final String DEVICE_TYPE = SystemProperties.get("ro.build.characteristics");
    private static Handler mCommandHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.os.SemPerfManager.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Bundle data = message.getData();
            SemPerfManager.sendCommand(data.getString("type"), data.getString("value"));
        }
    };
    static volatile ICustomFrequencyManager sCfmsService = null;
    static volatile ISamsungDeviceHealthManager sdhmservice = null;

    protected SemPerfManager() {
    }

    public static void onScrollEvent(boolean z) {
        sendCommandToSsrm(COMMAND_SCROLL, z ? "TRUE" : "FALSE");
    }

    public static void onSmoothScrollEvent(boolean z) {
        sendCommandToSsrm("SMOOTH_SCROLL", z ? "TRUE" : "FALSE");
    }

    public static void sendCommandToSsrm(String str, String str2) {
        try {
            Message messageObtainMessage = mCommandHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("type", str);
            bundle.putString("value", str2);
            messageObtainMessage.setData(bundle);
            mCommandHandler.sendMessage(messageObtainMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sendCommand(String str, String str2) {
        try {
            if (sCfmsService == null || sdhmservice == null) {
                initService();
            }
            if (!COMMAND_SCROLL.equals(str) && !"SMOOTH_SCROLL".equals(str) && !"GESTURE_DETECTED".equals(str) && !"TASK_BOOST".equals(str) && !"ANIMATION_BOOST".equals(str)) {
                if ((COMMAND_HOVERING_EVENT.equals(str) || COMMAND_BROWSER_DASH_MODE.equals(str) || COMMAND_SUSTAINED_PERF.equals(str) || COMMAND_GAME_TOUCH_BOOSTER.equals(str) || "NORMAL_TOUCH_BOOSTER".equals(str) || "MIDGROUND_PROCESS_DETECT".equals(str)) && sdhmservice != null) {
                    sdhmservice.sendCommand(str, str2);
                    return;
                }
                return;
            }
            if (sCfmsService != null) {
                sCfmsService.sendCommandToSSRM(str, str2);
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
        IBinder service;
        IBinder service2;
        try {
            if (sCfmsService == null && (service2 = ServiceManager.getService(Context.CFMS_SERVICE)) != null) {
                sCfmsService = ICustomFrequencyManager.Stub.asInterface(service2);
            }
            if (sdhmservice != null || (service = ServiceManager.getService("sdhms")) == null) {
                return;
            }
            sdhmservice = ISamsungDeviceHealthManager.Stub.asInterface(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logOnEng(String str, String str2) {
        if (sIsDebugLevelHigh) {
            Log.i(str, str2);
        }
    }
}

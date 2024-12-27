package com.samsung.android.hardware.secinputdev;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.samsung.android.hardware.secinputdev.device.SemInputDevice;
import com.samsung.android.hardware.secinputdev.external.SemInputExternal;
import com.samsung.android.hardware.secinputdev.external.SemInputExternalListener;
import com.samsung.android.hardware.secinputdev.external.SemInputExternalReceiver;
import com.samsung.android.hardware.secinputdev.hal.SysinputHALFactory;
import com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface;
import com.samsung.android.hardware.secinputdev.taas.SemInputTaas;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SemInputDeviceManagerService extends ISemInputDeviceManager.Stub
        implements SemInputExternal.IExternalEventRegister {
    private static final int CONNECTED = 1;
    private static final String COVER_ATTACH_CHANGED_INTENT =
            "com.samsung.android.intent.action.COVER_ATTACH_CHANGED";
    private static final int DISCONNECTED = 0;
    private static final String DISPLAY_CATEGORY_BUILTIN =
            "com.samsung.android.hardware.display.category.BUILTIN";
    public static final int ERR_CMD_FAILED = -6;
    public static final int ERR_DUPLICATED = -8;
    public static final int ERR_EXCEPTION = -7;
    public static final int ERR_NONE = 0;
    public static final int ERR_NONE_PENDING = 2;
    public static final int ERR_NONE_WAIT = 1;
    public static final int ERR_NOT_DEFINED = -20;
    public static final int ERR_NO_CMD_EXIST = -5;
    public static final int ERR_NO_DEVICE = -2;
    public static final int ERR_NO_HAL_SERVICE = -3;
    public static final int ERR_TIMEOUT = -9;
    private static final String EXTRA_COVER_ATTACH = "attach";
    private static final String EXTRA_REAL_COVER_TYPE = "real_cover_type";
    private static final String GOS_INTENT = "com.samsung.android.game.gos.action.TSP";
    private static final int POWER_STATE_OFF = 3;
    private static final int POWER_STATE_OFF_ON = 4;
    private static final int POWER_STATE_ON = 1;
    private static final int POWER_STATE_ON_OFF = 2;
    private static final int POWER_STATE_SHUTDOWN = -1;
    private static final String SET_GAME_MODE = "set_game_mode";
    private static final String SET_SCAN_RATE = "set_scan_rate";
    private static final String TAG = "SemInputDeviceManagerService";
    private static final int WIRELESS = 4;
    private static Handler mainHandler = null;
    private static final HashSet<SemInputConstants.Command> spenCommands =
            new HashSet<>(Arrays.asList(SemInputConstants.Command.CLEAR_COVER));
    private final SemInputCommandService commandService;
    private final Context context;
    private SemInputExternalListener externalEventHandler;
    private final SemInputDeviceMotionHelper motionHelper;
    private final Handler settingHandler;
    private final SysinputHALInterface sysinputHAL;
    private final SemInputTaas taasService;
    private final StringBuilder bootingDump = new StringBuilder();
    private final InitialOperation initialOperation = new InitialOperation();
    private final HashMap<SemInputConstants.Device, DeviceDisplayState> deviceDisplayStates =
            new HashMap<SemInputConstants.Device, DeviceDisplayState>() { // from class:
                // com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService.1
                {
                    put(SemInputConstants.Device.DEFAULT_TSP, new DeviceDisplayState());
                    put(SemInputConstants.Device.EXTRA_TSP, new DeviceDisplayState());
                }
            };
    private String currentGameMode = "0";
    private String currentScanRate = "0";
    private String currentFastResponse = "0";
    private int currentChargingStatus = 0;
    private int currentChargingType = 0;
    private int keepProxLpScanMode = -1;
    private int gloveMode = 0;
    private boolean currentFolded = false;
    private boolean supportProxLpScanEnabled = false;
    private SemInputSettingObserver settingObserver = null;
    private SemInputExternalReceiver externalBroadcastHandler = null;
    private int wirelessChargingState = 0;
    private int bedtimeRunningState = 0;
    private int aotEnabled = 0;
    private int bodyDetection = 0;
    private int wristDetectionDisabled = -1;
    private SemInputCommandInterface commandOperator = new CommandOperator();
    private final SemInputExternal.IBroadcastReceiver broadcastReceiver =
            new SemInputExternal.IBroadcastReceiver() { // from class:
                // com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService.2
                private String nfcRFSettingRunner = "rf";
                private boolean nfcRFDetected = false;

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IBroadcastReceiver
                public void onBatteryChanged(int status, int type) {
                    if (status != SemInputDeviceManagerService.this.currentChargingStatus
                            || type != SemInputDeviceManagerService.this.currentChargingType) {
                        Log.i(
                                SemInputDeviceManagerService.TAG,
                                "onBatteryChanged: type: " + type + " status: " + status);
                        if (SemInputFeatures.IS_WEAROS) {
                            if (type == 4 && status == 1) {
                                SemInputDeviceManagerService.this.wirelessChargingState = 1;
                                SemInputDeviceManagerService.this.commandService.setProperty(
                                        1, SemInputConstants.Command.AOT, "1");
                                Log.d(
                                        SemInputDeviceManagerService.TAG,
                                        "onBatteryChanged: wireless connected, AOT: 1");
                            } else {
                                SemInputDeviceManagerService.this.wirelessChargingState = 0;
                                if (SemInputDeviceManagerService.this.bedtimeRunningState == 1) {
                                    SemInputDeviceManagerService.this.commandService.setProperty(
                                            1, SemInputConstants.Command.AOT, "0");
                                    Log.d(
                                            SemInputDeviceManagerService.TAG,
                                            "onBatteryChanged: bedtime or powersaving, AOT: 0");
                                } else {
                                    SemInputDeviceManagerService.this.commandService.setProperty(
                                            1,
                                            SemInputConstants.Command.AOT,
                                            SemInputDeviceManagerService.this.aotEnabled + "");
                                    Log.d(
                                            SemInputDeviceManagerService.TAG,
                                            "onBatteryChanged: charger "
                                                    + (status == 1 ? "connected" : "disconnected")
                                                    + ", AOT: "
                                                    + SemInputDeviceManagerService.this.aotEnabled);
                                }
                            }
                            SemInputDeviceManagerService.this.commandService.setProperty(
                                    1, SemInputConstants.Command.CHARGER, status + "");
                        } else {
                            SemInputDeviceManagerService.this.commandService.setPropertyAllTouch(
                                    SemInputConstants.Command.CHARGER, status + "");
                        }
                    }
                    SemInputDeviceManagerService.this.currentChargingStatus = status;
                    SemInputDeviceManagerService.this.currentChargingType = type;
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IBroadcastReceiver
                public void onBatteryTxIdChanged(int tx_id) {
                    SemInputDeviceManagerService.this.commandService.setProperty(
                            11,
                            SemInputConstants.Command.SPEN_SET_WIRELESS_CHARGER_TX_ID,
                            tx_id + "");
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IBroadcastReceiver
                public void onShutdown() {
                    Log.i(SemInputDeviceManagerService.TAG, "shutdownBroadcastReceiver");
                    SemInputDeviceManagerService.this.motionHelper.onShutdown();
                    SemInputDeviceManagerService.this.taasService.destroy();
                    SemInputDeviceManagerService.this.commandService.activate(1, -1, true, false);
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IBroadcastReceiver
                public void onLazybootCompleted() {
                    Log.i(SemInputDeviceManagerService.TAG, "lazyBootCompleteBroadcastReceiver");
                    SemInputDeviceManagerService.this.initialOperation.update();
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IBroadcastReceiver
                public void onGameMode(String gameMode, String scanRate, String fastResponse) {
                    Log.i(
                            SemInputDeviceManagerService.TAG,
                            "gameServiceBroadcastReceiver: onGameMode");
                    if (gameMode != null && !gameMode.trim().isEmpty()) {
                        if (SemInputDeviceManagerService.this.currentGameMode.equals(gameMode)) {
                            Log.d(
                                    SemInputDeviceManagerService.TAG,
                                    "gameServiceBroadcastReceiver: game mode is not changed");
                        } else {
                            SemInputDeviceManagerService.this.commandService.setPropertyAllTouch(
                                    SemInputConstants.Command.GAME, gameMode);
                            SemInputDeviceManagerService.this.currentGameMode = gameMode;
                        }
                    }
                    if (scanRate != null && !scanRate.trim().isEmpty()) {
                        if (SemInputDeviceManagerService.this.currentScanRate.equals(scanRate)) {
                            Log.d(
                                    SemInputDeviceManagerService.TAG,
                                    "gameServiceBroadcastReceiver: scan rate is not changed");
                        } else {
                            SemInputDeviceManagerService.this.commandService.setPropertyAllTouch(
                                    SemInputConstants.Command.SCAN_RATE, scanRate);
                            SemInputDeviceManagerService.this.currentScanRate = scanRate;
                        }
                    }
                    if (fastResponse != null && !fastResponse.trim().isEmpty()) {
                        if (SemInputDeviceManagerService.this.currentFastResponse.equals(
                                fastResponse)) {
                            Log.d(
                                    SemInputDeviceManagerService.TAG,
                                    "gameServiceBroadcastReceiver: fastResponse is not changed");
                        } else {
                            SemInputDeviceManagerService.this.commandService.setPropertyAllTouch(
                                    SemInputConstants.Command.FAST_RESPONSE, fastResponse);
                            SemInputDeviceManagerService.this.currentFastResponse = fastResponse;
                        }
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IBroadcastReceiver
                public void onCoverAttached(boolean attached, int cover_type) {
                    Log.i(
                            SemInputDeviceManagerService.TAG,
                            "coverBroadcastReceiver: attached:"
                                    + attached
                                    + " cover_type:"
                                    + cover_type);
                    if (!attached) {
                        SemInputDeviceManagerService.this.commandService.setProperty(
                                11, SemInputConstants.Command.SPEN_COVER_TYPE, "0");
                        return;
                    }
                    if (cover_type == 0) {
                        SemInputDeviceManagerService.this.commandService.setProperty(
                                11, SemInputConstants.Command.SPEN_COVER_TYPE, "5");
                    } else if (cover_type == 4) {
                        SemInputDeviceManagerService.this.commandService.setProperty(
                                11, SemInputConstants.Command.SPEN_COVER_TYPE, "6");
                    } else {
                        Log.d(
                                SemInputDeviceManagerService.TAG,
                                "coverBroadcastReceiver: invalid cover type: " + cover_type);
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IBroadcastReceiver
                public void onUserSwitched() {
                    Log.i(SemInputDeviceManagerService.TAG, "userSwitchedBroadcastReceiver");
                    if (SemInputDeviceManagerService.this.settingObserver != null) {
                        SemInputDeviceManagerService.this.settingObserver.updateAll();
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IBroadcastReceiver
                public void onRFDetected(boolean on) {
                    if (on) {
                        if (!this.nfcRFDetected) {
                            this.nfcRFDetected = true;
                            Log.i(SemInputDeviceManagerService.TAG, "NFC RFBroadcastReceiver: on");
                            SemInputDeviceManagerService.this.commandService.setProperty(
                                    1, SemInputConstants.Command.NFC_FIELD, "1");
                            return;
                        }
                        SemInputDeviceManagerService.mainHandler.removeCallbacksAndMessages(
                                this.nfcRFSettingRunner);
                        return;
                    }
                    SemInputDeviceManagerService.mainHandler.postDelayed(
                            new Runnable() { // from class:
                                // com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass2.this.nfcRFDetected = false;
                                    Log.i(
                                            SemInputDeviceManagerService.TAG,
                                            "RF FieldBroadcastReceiver: set off");
                                    SemInputDeviceManagerService.this.commandService.setProperty(
                                            1, SemInputConstants.Command.NFC_FIELD, "0");
                                }
                            },
                            this.nfcRFSettingRunner,
                            500L);
                }
            };
    private final SemInputExternal.IServiceListener serviceListener =
            new SemInputExternal.IServiceListener() { // from class:
                // com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService.3
                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onSemUEvent(String result) {
                    if ("PROBE_DONE".equals(result)) {
                        SemInputDeviceManagerService.this.initialOperation.update();
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onDisplayStateChanged(
                        boolean isEarly, int stateLogical, int statePhysical, int displayType) {
                    if (!SemInputFeatures.IS_JDM_PROJECT) {
                        SemInputDeviceManagerService.this.setDisplayStateToDevice(
                                isEarly, stateLogical, statePhysical, displayType);
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onDisplayChanged(int displayRotation) {
                    Log.i(SemInputDeviceManagerService.TAG, "onDisplayChanged: " + displayRotation);
                    SemInputDeviceManagerService.this.commandService.setPropertyAllTouch(
                            SemInputConstants.Command.ORIENTATION, displayRotation + "");
                    SemInputDeviceManagerService.this.setMotionControl(
                            SemInputDeviceManager.MOTION_CONTROL_TYPE_ALL,
                            displayRotation + 20,
                            SemInputDeviceManagerService.TAG);
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onFoldStateChanged(boolean folded) {
                    SemInputDeviceManagerService.this.currentFolded = folded;
                    Log.i(
                            SemInputDeviceManagerService.TAG,
                            "onFoldStateChanged: "
                                    + (SemInputDeviceManagerService.this.currentFolded
                                            ? "folded"
                                            : "opened"));
                    SemInputDeviceManagerService.this.commandService.setPropertyAllTouchAndSpen(
                            SemInputConstants.Command.FOLD_STATE,
                            SemInputDeviceManagerService.this.currentFolded ? "1" : "0");
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onLpScanSensorChanged(int mode) {
                    DeviceDisplayState deviceDisplayState =
                            (DeviceDisplayState)
                                    SemInputDeviceManagerService.this.deviceDisplayStates.get(
                                            SemInputConstants.Device.DEFAULT_TSP);
                    if (deviceDisplayState == null) {
                        Log.e(SemInputDeviceManagerService.TAG, "DeviceDisplayState object null!!");
                        return;
                    }
                    int currentPowerState = deviceDisplayState.currentPowerState;
                    if (currentPowerState == 1 || currentPowerState == 4) {
                        Log.d(
                                SemInputDeviceManagerService.TAG,
                                "onLpScanSensorChanged: skip: screen on");
                        SemInputDeviceManagerService.this.keepProxLpScanMode = -1;
                    } else if (currentPowerState == 2) {
                        Log.d(
                                SemInputDeviceManagerService.TAG,
                                "onLpScanSensorChanged: keep: on ~ off");
                        SemInputDeviceManagerService.this.keepProxLpScanMode = mode;
                    } else {
                        SemInputDeviceManagerService.this.commandService.setPropertyAllTouch(
                                SemInputConstants.Command.PROX_LP_SCAN, mode + "");
                        SemInputDeviceManagerService.this.keepProxLpScanMode = -1;
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onDesktopModeStateChanged(int mode) {
                    Log.i(SemInputDeviceManagerService.TAG, "onDesktopModeStateChanged: " + mode);
                    SemInputDeviceManagerService.this.commandService.setPropertyAllTouch(
                            SemInputConstants.Command.EXTERNAL_NOISE, "1," + mode);
                    if (!SemInputDeviceManagerService.this.commandService.isSupportAot()) {
                        SemInputDeviceManagerService.this.commandService.setPropertyAllTouch(
                                SemInputConstants.Command.AOT, mode + "");
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onBodyDetected(int mode) {
                    if (SemInputDeviceManagerService.this.bodyDetection == mode) {
                        return;
                    }
                    Log.i(SemInputDeviceManagerService.TAG, "onBodyDetected: " + mode);
                    SemInputDeviceManagerService.this.bodyDetection = mode;
                    boolean enable = SemInputDeviceManagerService.this.bodyDetection == 1;
                    SemInputDeviceManagerService.this.motionHelper.enableMotion(
                            SemInputConstants.MotionType.AWD, enable, "onBodyDetected");
                    if (SemInputDeviceManagerService.this.wristDetectionDisabled == 0) {
                        SemInputDeviceManagerService.this.commandService.setProperty(
                                1,
                                SemInputConstants.Command.BODY_DETECTION,
                                SemInputDeviceManagerService.this.bodyDetection + "");
                    }
                }
            };
    private final HandlerThread mainHandlerThread = new HandlerThread("SemInputMainHandlerThread");

    public interface MainHandlerMessage {
        public static final int HAL_FACTORY_REGISTER_CALLBACK = 1;
        public static final int SYSTEM_READY = 2;
    }

    private static class DeviceDisplayState {
        private boolean currentDisplayIsEarly;
        private int currentDisplayState;
        private int currentPowerState;
        private boolean isSkippedDisplayStateChange;
        private boolean isTspForceOff;

        private DeviceDisplayState() {
            this.currentDisplayIsEarly = false;
            this.currentDisplayState = 0;
            this.isSkippedDisplayStateChange = false;
            this.currentPowerState = 1;
            this.isTspForceOff = false;
        }
    }

    public SemInputDeviceManagerService(Context context) {
        this.externalEventHandler = null;
        this.context = context;
        this.mainHandlerThread.start();
        mainHandler = new MainHandler(this.mainHandlerThread.getLooper());
        this.settingHandler = new SettingHandler(this.mainHandlerThread.getLooper());
        this.sysinputHAL = SysinputHALFactory.connectHAL();
        registerCallbackWithRetries(5);
        this.commandService = new SemInputCommandService(this.sysinputHAL);
        this.motionHelper = new SemInputDeviceMotionHelper(mainHandler, this, this.commandOperator);
        registerBroadcastReceiver();
        this.externalEventHandler = new SemInputExternalListener(context, mainHandler);
        this.taasService = new SemInputTaas(context, this);
        Log.d(TAG, "done");
    }

    private class CommandOperator implements SemInputCommandInterface {
        private CommandOperator() {}

        @Override // com.samsung.android.hardware.secinputdev.SemInputCommandInterface
        public void setProperty(
                SemInputConstants.Device device, SemInputConstants.Command command, int value) {
            Log.i(
                    SemInputDeviceManagerService.TAG,
                    "SemInputCommand: setProperty " + command + ", " + value);
            if (SemInputDeviceManagerService.this.commandService != null
                    && command == SemInputConstants.Command.AWD) {
                SemInputDeviceManagerService.this.commandService.setProperty(
                        1, SemInputConstants.Command.AWD, value + "");
            }
        }
    }

    public int registerCallbackWithRetries(int retry) {
        for (int ii = 1; ii <= retry; ii++) {
            if (SysinputHALFactory.registerCallback() >= 0) {
                return 0;
            }
            try {
                Thread.sleep(20L);
            } catch (Exception e) {
                Log.e(TAG, "registerCallbackWithRetries: " + e);
            }
            Log.w(TAG, "registerCallbackWithRetries " + ii);
        }
        return -9;
    }

    public static void systemReady() {
        Log.i(TAG, "systemReady");
        if (mainHandler != null) {
            mainHandler.sendEmptyMessage(2);
        } else {
            Log.e(TAG, "systemReady: mainHandler is null");
        }
    }

    public static void registerCallbackForHalRecovery(int msDelay) {
        if (mainHandler != null) {
            SemInputDevice.setRecoveryState(true);
            mainHandler.sendEmptyMessageDelayed(1, msDelay);
        }
    }

    private class MainHandler extends Handler implements MainHandlerMessage {
        MainHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SemInputDeviceManagerService.this.motionHelper.forcePause();
                    int ret = SemInputDeviceManagerService.this.registerCallbackWithRetries(5);
                    if (ret < 0) {
                        sendEmptyMessageDelayed(1, 100L);
                        break;
                    } else {
                        SemInputDevice.setRecoveryState(false);
                        for (Map.Entry<SemInputConstants.Device, DeviceDisplayState> element :
                                SemInputDeviceManagerService.this.deviceDisplayStates.entrySet()) {
                            int currentPowerState = element.getValue().currentPowerState;
                            if (currentPowerState == 1) {
                                SemInputDeviceManagerService.this.motionHelper.restart(
                                        element.getKey(), false);
                            }
                        }
                        break;
                    }
                case 2:
                    SemInputDeviceManagerService.this.initialOperation.update();
                    break;
                default:
                    Log.d(SemInputDeviceManagerService.TAG, "MainHandler: " + msg);
                    break;
            }
        }
    }

    private class InitialOperation {
        private final ConcurrentHashMap<SemInputExternal.Event, Boolean> checkedStates =
                new ConcurrentHashMap<>();

        InitialOperation() {
            this.checkedStates.put(SemInputExternal.Event.LISTENER_DISPLAY_STATE, false);
            if (SemInputFeatures.IS_WEAROS && SemInputFeatures.SUPPORT_AWD) {
                this.checkedStates.put(SemInputExternal.Event.LISTENER_SENSOR_WATCH, false);
                return;
            }
            this.checkedStates.put(SemInputExternal.Event.LISTENER_DISPLAY, false);
            this.checkedStates.put(SemInputExternal.Event.SETTING_AOT, false);
            this.checkedStates.put(SemInputExternal.Event.SETTING_SPEN, false);
            this.checkedStates.put(SemInputExternal.Event.SETTING_TSP_EXTRA, false);
            this.checkedStates.put(SemInputExternal.Event.LISTENER_FOLD_STATE, false);
            this.checkedStates.put(SemInputExternal.Event.LISTENER_DEX, false);
            this.checkedStates.put(SemInputExternal.Event.LISTENER_PROX_LP_SCAN, false);
            this.checkedStates.put(SemInputExternal.Event.OBSERVER_UEVENT, false);
        }

        public void put(SemInputExternal.Event key, boolean registered) {
            this.checkedStates.put(key, Boolean.valueOf(registered));
        }

        public boolean get(SemInputExternal.Event key) {
            Boolean value = this.checkedStates.get(key);
            if (value == null) {
                return false;
            }
            return value.booleanValue();
        }

        public void update() {
            Log.d(
                    SemInputDeviceManagerService.TAG,
                    "InitialOperation::update, wearos: " + SemInputFeatures.IS_WEAROS);
            SemInputDeviceManagerService.this.commandService.getSupportDeviceList();
            SemInputDeviceManagerService.this.registerExternalService();
            SemInputDeviceManagerService.this.registerSettingObserver();
            SemInputDeviceManagerService.this.registerRawdataService();
        }
    }

    public void registerRawdataService() {
        if (!SemInputFeatures.SUPPORT_RAWDATA_FEATURE) {
            return;
        }
        for (int devid = 1; devid < 3; devid++) {
            if (this.commandService.getSupportDevice(devid) == 0) {
                this.motionHelper.registerRawdataService(
                        SemInputConstants.Device.getFromInt(devid),
                        this.context,
                        this.sysinputHAL,
                        this.commandService.getTspSupportFeature(devid));
            }
        }
    }

    public void registerSettingObserver() {
        Log.d(TAG, "registerSettingObserver");
        if (this.settingObserver == null) {
            this.settingObserver = SemInputSettingObserver.getInstance(this.context);
            this.settingObserver.registerDefault(this.settingHandler);
        }
        if (SemInputFeatures.IS_WEAROS) {
            return;
        }
        if (!this.initialOperation.get(SemInputExternal.Event.SETTING_AOT)
                && this.commandService.isSupportAot()) {
            this.initialOperation.put(SemInputExternal.Event.SETTING_AOT, true);
            this.settingObserver.addObserver(this.settingHandler, 6, 0);
            this.bootingDump.append("- Setting AOT registered\n");
        }
        if (!this.initialOperation.get(SemInputExternal.Event.SETTING_SPEN)
                && this.commandService.getSupportDevice(11) == 0) {
            this.initialOperation.put(SemInputExternal.Event.SETTING_SPEN, true);
            this.settingObserver.addObserver(this.settingHandler, 7, 0);
            this.settingObserver.addObserver(this.settingHandler, 8, 0);
            this.bootingDump.append("- Setting Spen Mode registered\n");
        }
    }

    private class SettingHandler extends Handler implements SemInputSettingObserver.HandlerMessage {
        private int touchBezelOn;

        SettingHandler(Looper looper) {
            super(looper);
            this.touchBezelOn = -1;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 4:
                    updateTwoFingerDoubletapMode(msg.arg1);
                    break;
                case 5:
                    updateGloveMode(msg.arg1);
                    break;
                case 6:
                    updateDoubleTapToWakeUp(msg.arg1);
                    break;
                case 7:
                    updateSpenSavingMode(msg.arg1);
                    break;
                case 8:
                    updateScreenOffMemo(msg.arg1);
                    break;
                case SemInputSettingObserver.HandlerMessage.SETTING_TOUCH_SENSITIVITY_ON /* 80 */:
                    updateGloveMode(msg.arg1);
                    break;
                case SemInputSettingObserver.HandlerMessage.REFRESH_RATE_MODE /* 101 */:
                    updateRefreshRateMode(msg.arg1);
                    break;
                case SemInputSettingObserver.HandlerMessage
                        .SETTING_BEDTIME_MODE_RUNNING_STATE /* 201 */:
                    updateBedtimeRunningState(msg.arg1);
                    break;
                case SemInputSettingObserver.HandlerMessage.SETTING_TOUCH_BEZEL_ON /* 202 */:
                    updateTouchBezelFromSettings(msg.arg1);
                    break;
                case SemInputSettingObserver.HandlerMessage
                        .SEM_SETTING_WRIST_DETECTION_DISABLED /* 203 */:
                    updateWristDetection(msg.arg1);
                    break;
                default:
                    Log.d(SemInputDeviceManagerService.TAG, "SettingHandler: " + msg);
                    break;
            }
        }

        private void updateBedtimeRunningState(int value) {
            Log.i(SemInputDeviceManagerService.TAG, "updateBedtimeRunningState: " + value);
            SemInputDeviceManagerService.this.bedtimeRunningState = value;
            if (SemInputDeviceManagerService.this.wirelessChargingState == 1) {
                Log.d(SemInputDeviceManagerService.TAG, "updateBedtimeRunningState: aot set skip.");
            } else if (value == 1) {
                SemInputDeviceManagerService.this.commandService.setProperty(
                        1, SemInputConstants.Command.AOT, "0");
            } else {
                SemInputDeviceManagerService.this.commandService.setProperty(
                        1,
                        SemInputConstants.Command.AOT,
                        SemInputDeviceManagerService.this.aotEnabled + "");
            }
        }

        private void updateTouchBezelFromSettings(int value) {
            Log.i(SemInputDeviceManagerService.TAG, "updateTouchBezelFromSettings: " + value);
            if (value != this.touchBezelOn) {
                SemInputDeviceManagerService.this.commandService.setProperty(
                        1, SemInputConstants.Command.BEZEL, value + "");
            }
            this.touchBezelOn = value;
        }

        private void updateWristDetection(int value) {
            Log.i(SemInputDeviceManagerService.TAG, "updateWristDetection: " + value);
            if (value != SemInputDeviceManagerService.this.wristDetectionDisabled) {
                if (value == 0) {
                    SemInputDeviceManagerService.this.commandService.setProperty(
                            1,
                            SemInputConstants.Command.BODY_DETECTION,
                            SemInputDeviceManagerService.this.bodyDetection + "");
                } else {
                    SemInputDeviceManagerService.this.commandService.setProperty(
                            1, SemInputConstants.Command.BODY_DETECTION, "1");
                }
            }
            SemInputDeviceManagerService.this.wristDetectionDisabled = value;
        }

        private int updateRefreshRateMode(int value) {
            Log.i(SemInputDeviceManagerService.TAG, "updateRefreshRateMode: " + value);
            return SemInputDeviceManagerService.this.commandService.setPropertyAllTouchAndSpen(
                    SemInputConstants.Command.REFRESH_RATE, value + "");
        }

        private int updateTwoFingerDoubletapMode(int value) {
            Log.i(SemInputDeviceManagerService.TAG, "updateTwoFingerDoubletapMode: " + value);
            return SemInputDeviceManagerService.this.commandService.setPropertyAllTouch(
                    SemInputConstants.Command.TWO_FINGER_DOUBLETAP, value + "");
        }

        private int updateGloveMode(int value) {
            Log.i(SemInputDeviceManagerService.TAG, "updateGloveMode: " + value);
            SemInputDeviceManagerService.this.gloveMode = value;
            SemInputDeviceManager.gloveMode = SemInputDeviceManagerService.this.gloveMode;
            if (SemInputFeatures.IS_WEAROS) {
                return SemInputDeviceManagerService.this.commandService.setProperty(
                        1, SemInputConstants.Command.GLOVE, value + "");
            }
            return SemInputDeviceManagerService.this.commandService.setPropertyAllTouch(
                    SemInputConstants.Command.GLOVE, value + "");
        }

        private int updateDoubleTapToWakeUp(int value) {
            Log.i(SemInputDeviceManagerService.TAG, "updateDoubleTapToWakeUp: " + value);
            return SemInputDeviceManagerService.this.commandService.setPropertyAllTouch(
                    SemInputConstants.Command.AOT, value + "");
        }

        private int updateSpenSavingMode(int value) {
            Log.i(SemInputDeviceManagerService.TAG, "updateSpenSavingMode: " + value);
            return SemInputDeviceManagerService.this.commandService.setProperty(
                    11, SemInputConstants.Command.SPEN_SAVING_MODE, value + "");
        }

        private int updateScreenOffMemo(int value) {
            Log.i(SemInputDeviceManagerService.TAG, "updateScreenOffMemo: " + value);
            return SemInputDeviceManagerService.this.commandService.setProperty(
                    11, SemInputConstants.Command.SPEN_SCREEN_OFF_MEMO, value + "");
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IExternalEventRegister
    public boolean registerBroadcastReceiver(
            SemInputExternal.Event event, SemInputExternal.IBroadcastReceiver receiver) {
        if (receiver == null || this.externalBroadcastHandler == null) {
            return false;
        }
        this.externalBroadcastHandler.registerBroadcastReceiver(event, receiver);
        return true;
    }

    private void registerBroadcastReceiver() {
        if (this.externalBroadcastHandler != null) {
            return;
        }
        this.externalBroadcastHandler = new SemInputExternalReceiver(this.context, mainHandler);
        if (this.externalBroadcastHandler != null) {
            this.externalBroadcastHandler.registerBroadcastReceiver(
                    SemInputExternal.Event.BROADCAST_BATTERY, this.broadcastReceiver);
            this.externalBroadcastHandler.registerBroadcastReceiver(
                    SemInputExternal.Event.BROADCAST_BATTERY_EXTRA, this.broadcastReceiver);
            this.externalBroadcastHandler.registerBroadcastReceiver(
                    SemInputExternal.Event.BROADCAST_SHUTDOWN, this.broadcastReceiver);
            this.externalBroadcastHandler.registerBroadcastReceiver(
                    SemInputExternal.Event.BROADCAST_LAZY_BOOT, this.broadcastReceiver);
            this.externalBroadcastHandler.registerBroadcastReceiver(
                    SemInputExternal.Event.BROADCAST_USER_SWITCHED, this.broadcastReceiver);
            if (SemInputFeatures.IS_WEAROS) {
                this.externalBroadcastHandler.registerBroadcastReceiver(
                        SemInputExternal.Event.BROADCAST_RF_DETECTED, this.broadcastReceiver);
                this.externalBroadcastHandler.registerBroadcastReceiver(
                        SemInputExternal.Event.BROADCAST_RF_OFF_DETECTED, this.broadcastReceiver);
            } else {
                this.externalBroadcastHandler.registerBroadcastReceiver(
                        SemInputExternal.Event.BROADCAST_GAME, this.broadcastReceiver);
                this.externalBroadcastHandler.registerBroadcastReceiver(
                        SemInputExternal.Event.BROADCAST_COVER, this.broadcastReceiver);
            }
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IExternalEventRegister
    public boolean registerServiceListener(
            SemInputExternal.Event event, SemInputExternal.IServiceListener listener) {
        if (this.externalEventHandler == null) {
            return false;
        }
        this.externalEventHandler.registerServiceListener(event, listener);
        return true;
    }

    public void registerExternalService() {
        for (Map.Entry<SemInputExternal.Event, String> element :
                this.externalEventHandler.getSupportList().entrySet()) {
            if (!this.initialOperation.get(element.getKey())) {
                if (element.getKey() == SemInputExternal.Event.LISTENER_PROX_LP_SCAN) {
                    this.supportProxLpScanEnabled =
                            this.commandService.isSupportProxLpScanEnabled();
                    if (!this.supportProxLpScanEnabled) {
                        this.initialOperation.put(element.getKey(), true);
                        this.bootingDump.append(
                                "- SensorProxLpScanListenerWrapper NOT registered: not"
                                        + " supported\n");
                    }
                }
                boolean ret =
                        this.externalEventHandler.registerServiceListener(
                                element.getKey(), this.serviceListener);
                this.initialOperation.put(element.getKey(), ret);
            }
        }
        this.bootingDump.append(this.externalEventHandler.getLog());
    }

    public void setDisplayStateToDevice(
            boolean isEarly, int stateLogical, int statePhysical, int displayType) {
        int stateForDevice;
        if (SemInputFeatures.SUPPORT_AMOLED_DISPLAY) {
            stateForDevice = stateLogical;
        } else {
            stateForDevice = statePhysical;
        }
        if (!isDevidTsp(displayType)) {
            displayType = 1;
            Log.d(
                    TAG,
                    "setDisplayStateToDevice:display type(1) is not defined, set to default tsp");
        }
        DeviceDisplayState deviceDisplayState =
                this.deviceDisplayStates.get(SemInputConstants.Device.getFromInt(displayType));
        if (deviceDisplayState == null) {
            Log.e(TAG, "Device(" + displayType + ") does not exist");
            return;
        }
        Log.d(
                TAG,
                "setDisplayState["
                        + displayType
                        + "] "
                        + (isEarly ? "EARLY: " : "LATE: ")
                        + SemInputConstants.DisplayState.getFromInt(
                                deviceDisplayState.currentDisplayState)
                        + " -> "
                        + SemInputConstants.DisplayState.getFromInt(stateForDevice)
                        + (stateLogical == statePhysical
                                ? ""
                                : String.format(
                                        " (%s,%s)",
                                        SemInputConstants.DisplayState.getFromInt(stateLogical),
                                        SemInputConstants.DisplayState.getFromInt(statePhysical))));
        if (deviceDisplayState.currentDisplayState == stateForDevice
                && deviceDisplayState.currentDisplayIsEarly == isEarly) {
            Log.d(TAG, "setDisplayStateToDevice: The same state&isEarly was called. Skip this");
            return;
        }
        deviceDisplayState.currentDisplayIsEarly = isEarly;
        if (deviceDisplayState.currentDisplayState != stateForDevice) {
            deviceDisplayState.isSkippedDisplayStateChange = false;
        } else if (isEarly) {
            deviceDisplayState.isSkippedDisplayStateChange = true;
            Log.d(
                    TAG,
                    "setDisplayStateToDevice: The same state was called. Skip this("
                            + isEarly
                            + ")");
            return;
        } else if (deviceDisplayState.isSkippedDisplayStateChange) {
            deviceDisplayState.isSkippedDisplayStateChange = false;
            Log.d(
                    TAG,
                    "setDisplayStateToDevice: The same state was called. Skip this("
                            + isEarly
                            + ")");
            return;
        }
        deviceDisplayState.isTspForceOff = false;
        if (isEarly) {
            if (stateForDevice == 2) {
                deviceDisplayState.currentPowerState = 4;
            } else if (stateForDevice == 1) {
                deviceDisplayState.currentPowerState = 2;
                this.motionHelper.pause(SemInputConstants.Device.getFromInt(displayType), true);
            }
        }
        if (!isEarly && stateForDevice == 2) {
            this.commandService.activate(displayType, stateForDevice, isEarly, true);
        } else {
            this.commandService.activate(
                    displayType, stateForDevice, isEarly, SemInputFeatures.USE_CMDTHREAD);
        }
        if (displayType == 1 && stateForDevice == 1) {
            setMainDisplayOff(isEarly);
        }
        if (stateForDevice == 2) {
            setDisplayOn(isEarly, displayType);
        }
        deviceDisplayState.currentDisplayState = stateForDevice;
        if (!isEarly) {
            if (stateForDevice == 2) {
                deviceDisplayState.currentPowerState = 1;
            } else if (stateForDevice == 1) {
                deviceDisplayState.currentPowerState = 3;
            }
        }
    }

    private void setMainDisplayOff(boolean isEarly) {
        if (isEarly) {
            this.commandService.activate(11, 1, isEarly, true);
        } else if (this.supportProxLpScanEnabled && this.keepProxLpScanMode >= 0) {
            Log.d(TAG, "setMainDisplayOff: running keeping prox lp scan mode");
            this.commandService.setPropertyAllTouch(
                    SemInputConstants.Command.PROX_LP_SCAN, this.keepProxLpScanMode + "");
            this.keepProxLpScanMode = -1;
        }
    }

    private void setDisplayOn(boolean isEarly, int displayId) {
        if (isDevidTsp(displayId) && !isEarly) {
            if (displayId == 1) {
                this.commandService.activate(11, 2, isEarly, true);
                this.commandService.activate(31, 2, isEarly, true);
            }
            this.motionHelper.restart(SemInputConstants.Device.getFromInt(displayId), true);
        }
    }

    @Override // android.os.Binder
    protected void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        if (pw == null) {
            return;
        }
        pw.println(
                "dumping SemInputDeviceManagerService"
                        + (SemInputFeatures.IS_JDM_PROJECT ? " (JDM)" : ""));
        pw.println("- hal version: " + this.sysinputHAL.getVersion());
        pw.print(this.bootingDump.toString());
        if (this.commandService != null) {
            pw.println("");
            this.commandService.dump(pw);
        }
        pw.println("");
        this.taasService.dump(pw);
        if (SemInputMotionEventDispatcher.isCreated()) {
            pw.println("");
            SemInputMotionEventDispatcher.getInstance(this.context).dump(pw);
        }
        this.motionHelper.dump(pw);
        pw.println("end");
        dumpEvents(pw);
        pw.println("end SemInputDeviceManagerService");
    }

    private void dumpEvents(PrintWriter pw) {
        pw.println("\ndumping SemInputDeviceManager Events");
        if (this.commandService != null) {
            this.commandService.dumpEvents(pw);
        }
        pw.println("");
        this.sysinputHAL.dumpEvents(pw);
        if (SemInputMotionEventDispatcher.isCreated()) {
            pw.println("");
            SemInputMotionEventDispatcher.getInstance(this.context).dumpEvents(pw);
        }
        this.motionHelper.dumpEvents(pw);
    }

    public int getCurrentTSP() {
        if (this.currentFolded) {
            return 2;
        }
        return 1;
    }

    public static boolean isDevidTsp(int devid) {
        if (devid >= 1 && devid < 3) {
            return true;
        }
        return false;
    }

    public static boolean isDevidSpen(int devid) {
        if (devid == 11) {
            return true;
        }
        return false;
    }

    public static boolean isDevidKeyboard(int devid) {
        if (devid == 31) {
            return true;
        }
        return false;
    }

    public static void loggingException(String tag, String word, Exception e) {
        Log.e(tag, word + ": " + e);
    }

    public static void loggingThrowable(String tag, String word, Throwable e) {
        Log.e(tag, word + ": " + e);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public String getKeyPressStateAll() throws RemoteException {
        return this.commandService.getKeyPressStateAll();
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public boolean isKeyPressedByKeycode(int keycode) throws RemoteException {
        return this.commandService.isKeyPressedByKeycode(keycode);
    }

    @Deprecated
    public boolean registerCallback(ISemInputDeviceRemoteServiceCallback callback)
            throws RemoteException {
        Log.e(TAG, "registerCallback: not supported anymore. Please use registerListener");
        return false;
    }

    @Deprecated
    public boolean unregisterCallback(ISemInputDeviceRemoteServiceCallback callback)
            throws RemoteException {
        Log.e(TAG, "registerCallback: not supported anymore. Please use unregisterListener");
        return false;
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public boolean registerListener(IBinder binder, int type, String client)
            throws RemoteException {
        return this.motionHelper.registerListener(
                binder, SemInputConstants.MotionType.getFromInt(type), client);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public boolean unregisterListener(IBinder binder, int type, String client)
            throws RemoteException {
        return this.motionHelper.unregisterListener(
                binder, SemInputConstants.MotionType.getFromInt(type), client);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int sendRawdataTsp(SemInputConstants.Device device, int[] data) throws RemoteException {
        Log.d(
                TAG,
                "sendRawdataTsp["
                        + device
                        + "]: "
                        + data[0]
                        + " "
                        + data[1]
                        + " "
                        + data[2]
                        + " "
                        + data[3]
                        + " "
                        + data[4]
                        + " "
                        + data[5]);
        return this.sysinputHAL.injectRawdata(device.toInt(), data, data.length);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public boolean isSupportMotion(String type) {
        return isSupportMotion(SemInputConstants.MotionType.getFromName(type));
    }

    public boolean isSupportMotion(SemInputConstants.MotionType motionType) {
        int feature = this.commandService.getTspSupportFeature(1);
        int featureSub = this.commandService.getTspSupportFeature(2);
        if (motionType.isFeatureEnabled(feature) || motionType.isFeatureEnabled(featureSub)) {
            Log.i(TAG, "isSupportMotion: " + motionType + ": true");
            return true;
        }
        Log.i(TAG, "isSupportMotion: " + motionType + ": false");
        return false;
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int enableMotion(String type, boolean enable, String client) {
        return this.motionHelper.enableMotion(
                SemInputConstants.MotionType.getFromName(type), enable, client);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int isEnableMotion(String type, String client) {
        return this.motionHelper.isEnableMotion(
                SemInputConstants.MotionType.getFromName(type), client);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setMotionControl(String subtype, int control, String client) {
        return this.motionHelper.setMotionControl(subtype, control, client);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int getMotionControl(String subtype, String client) {
        if (subtype == null || subtype.isEmpty()) {
            return -4;
        }
        return this.motionHelper.getMotionControl(subtype, client);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int getSupportDevice(SemInputConstants.Device device) throws RemoteException {
        return this.commandService.getSupportDevice(device.toInt());
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public String getCommandList(SemInputConstants.Device device) throws RemoteException {
        return this.commandService.getCommandList(device.toInt());
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int getTspSupportFeature(SemInputConstants.Device device) throws RemoteException {
        return this.commandService.getTspSupportFeature(device.toInt());
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int getDeviceEnabled(SemInputConstants.Device device) throws RemoteException {
        try {
            String result =
                    this.commandService.getProperty(
                            device.toInt(), SemInputConstants.Property.ENABLED);
            if ("NG".equals(result)) {
                return -6;
            }
            if (SemInputDeviceManager.RESULT_STR_NA.equals(result)) {
                return -5;
            }
            return Integer.parseInt(result);
        } catch (Exception e) {
            Log.e(TAG, "getDeviceEnabled: " + e);
            return -7;
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public String runCommand(SemInputConstants.Device device, String cmd) throws RemoteException {
        if (device == SemInputConstants.Device.CURRENT_TSP) {
            return this.commandService.runCommand(getCurrentTSP(), cmd);
        }
        return this.commandService.runCommand(device.toInt(), cmd);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int activate(
            SemInputConstants.Device device, SemInputConstants.DisplayState mode, boolean state)
            throws RemoteException {
        int devid = device.toInt();
        if (!isDevidTsp(devid)
                && !isDevidSpen(devid)
                && this.commandService.getSupportDevice(devid) == 0) {
            throw new SecurityException(device + " is not allowed");
        }
        if (mode != SemInputConstants.DisplayState.FORCE_ON
                && mode != SemInputConstants.DisplayState.FORCE_OFF) {
            throw new SecurityException(mode + " is not allowed");
        }
        String caller = getCallerClassName(4) + ":" + Process.myTid();
        if (isDevidTsp(devid)) {
            DeviceDisplayState deviceDisplayState = this.deviceDisplayStates.get(device);
            if (mode == SemInputConstants.DisplayState.FORCE_OFF
                    && deviceDisplayState.currentDisplayState == 2) {
                deviceDisplayState.isTspForceOff = true;
                this.motionHelper.pause(device, false);
            } else if (mode == SemInputConstants.DisplayState.FORCE_ON
                    && deviceDisplayState.isTspForceOff) {
                this.commandService.activate(devid, mode.toInt(), state, caller, true);
                this.motionHelper.restart(device, true);
                deviceDisplayState.isTspForceOff = false;
                return 1;
            }
        }
        return this.commandService.activate(devid, mode.toInt(), state, caller, true);
    }

    private String getCallerClassName(int stackIndex) {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        String callerClassName = null;
        try {
            String callerClassName2 = stack[stackIndex].getClassName();
            String[] classNameSplit = callerClassName2.split("[.]");
            callerClassName = classNameSplit[classNameSplit.length - 1];
            return callerClassName.split("[$]")[0];
        } catch (Exception e) {
            if (callerClassName == null) {
                return "";
            }
            return callerClassName;
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setTspEnabled(int devid, int mode, boolean state) throws RemoteException {
        if (!isDevidTsp(devid)) {
            return -2;
        }
        return activate(
                SemInputConstants.Device.getFromInt(devid),
                SemInputConstants.DisplayState.getFromInt(mode),
                state);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setTemperature(int value) throws RemoteException {
        return this.commandService.setPropertyAllTouch(
                SemInputConstants.Command.TEMPERATURE, value + "");
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setAodRect(int w, int h, int x, int y) throws RemoteException {
        String mode = w + "," + h + "," + x + "," + y;
        return this.commandService.setProperty(
                getCurrentTSP(), SemInputConstants.Command.AOD_RECT, mode);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setAodEnable(int mode) throws RemoteException {
        if (SemInputFeatures.IS_WEAROS) {
            return this.commandService.setProperty(1, SemInputConstants.Command.AOD, mode + "");
        }
        return this.commandService.setPropertyAllTouchAndSpen(
                SemInputConstants.Command.AOD, mode + "");
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setAotEnable(int value) {
        if (!SemInputFeatures.IS_WEAROS) {
            return -1;
        }
        Log.i(TAG, "setAotEnable: " + value);
        this.aotEnabled = value;
        if (this.bedtimeRunningState == 1 || this.wirelessChargingState == 1) {
            Log.d(
                    TAG,
                    "skip setting: bedtime: "
                            + this.bedtimeRunningState
                            + " wc: "
                            + this.wirelessChargingState);
            return 0;
        }
        return this.commandService.setProperty(1, SemInputConstants.Command.AOT, value + "");
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setFodEnable(int mode, int pressFast, int strictMode, int control)
            throws RemoteException {
        if (mode == 1) {
            return this.commandService.setPropertyAllTouch(
                    SemInputConstants.Command.FOD,
                    mode + "," + pressFast + "," + strictMode + "," + control);
        }
        return this.commandService.setPropertyAllTouch(SemInputConstants.Command.FOD, mode + "");
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setFodRect(int left, int top, int right, int bottom) throws RemoteException {
        String mode = left + "," + top + "," + right + "," + bottom;
        return this.commandService.setProperty(
                getCurrentTSP(), SemInputConstants.Command.FOD_RECT, mode);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setFodLpMode(int mode) throws RemoteException {
        return this.commandService.setPropertyAllTouch(SemInputConstants.Command.FOD_LP, mode + "");
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setSingletapEnable(int mode) throws RemoteException {
        return this.commandService.setPropertyAllTouch(
                SemInputConstants.Command.SINGLETAP, mode + "");
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setSyncChanged(int mode) throws RemoteException {
        return this.commandService.setPropertyAllTouch(
                SemInputConstants.Command.SYNC_CHANGED, mode + "");
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setSpenEnabled(int devid, int mode, boolean state) throws RemoteException {
        if (!isDevidSpen(devid)) {
            return -2;
        }
        return activate(
                SemInputConstants.Device.getFromInt(devid),
                SemInputConstants.DisplayState.getFromInt(mode),
                state);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setCommand(
            SemInputConstants.Device device, SemInputConstants.Command command, String mode)
            throws RemoteException {
        if (!command.isExternal()) {
            throw new SecurityException(command + " is not allowed");
        }
        switch (device) {
            case NOT_SPECIFIED:
                if (SemInputFeatures.IS_WEAROS) {
                    return this.commandService.setProperty(1, command, mode);
                }
                if (spenCommands.contains(command)) {
                    return this.commandService.setPropertyAllTouchAndSpen(command, mode);
                }
                return this.commandService.setPropertyAllTouch(command, mode);
            case CURRENT_TSP:
                return this.commandService.setProperty(getCurrentTSP(), command, mode);
            default:
                return this.commandService.setProperty(device.toInt(), command, mode);
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public int setProperty(
            SemInputConstants.Device device, SemInputConstants.Property property, String mode)
            throws RemoteException {
        if (!property.isExternalWrite()) {
            throw new SecurityException(property + " is not allowed");
        }
        return this.commandService.setProperty(device.toInt(), property, mode);
    }

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
    public String getProperty(SemInputConstants.Device device, SemInputConstants.Property property)
            throws RemoteException {
        if (!property.isExternalRead()) {
            throw new SecurityException(property + " is not allowed");
        }
        return this.commandService.getProperty(device.toInt(), property);
    }
}

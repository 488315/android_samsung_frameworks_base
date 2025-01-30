package android.hardware.usb;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.p002pm.PackageManager;
import android.hardware.usb.IDisplayPortAltModeInfoListener;
import android.hardware.usb.UsbManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.ParcelFileDescriptor;
import android.p009os.Process;
import android.p009os.RemoteException;
import android.p009os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;
import android.util.sysfwutil.Slog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class UsbManager {
    public static final String ACTION_USB_ACCESSORY_ATTACHED = "android.hardware.usb.action.USB_ACCESSORY_ATTACHED";
    public static final String ACTION_USB_ACCESSORY_DETACHED = "android.hardware.usb.action.USB_ACCESSORY_DETACHED";

    @SystemApi
    public static final String ACTION_USB_ACCESSORY_HANDSHAKE = "android.hardware.usb.action.USB_ACCESSORY_HANDSHAKE";
    public static final String ACTION_USB_CABLE_STATE = "android.hardware.usb.action.USB_CABLE_STATE";
    public static final String ACTION_USB_DEVICE_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    public static final String ACTION_USB_DEVICE_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";

    @SystemApi
    public static final String ACTION_USB_PORT_CHANGED = "android.hardware.usb.action.USB_PORT_CHANGED";

    @SystemApi
    public static final String ACTION_USB_PORT_COMPLIANCE_CHANGED = "android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED";

    @SystemApi
    public static final String ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE";
    public static final int[] DEFAULT_MODES;
    public static final String EXTRA_ACCESSORY = "accessory";

    @SystemApi
    public static final String EXTRA_ACCESSORY_HANDSHAKE_END = "android.hardware.usb.extra.ACCESSORY_HANDSHAKE_END";

    @SystemApi
    public static final String EXTRA_ACCESSORY_START = "android.hardware.usb.extra.ACCESSORY_START";

    @SystemApi
    public static final String EXTRA_ACCESSORY_STRING_COUNT = "android.hardware.usb.extra.ACCESSORY_STRING_COUNT";

    @SystemApi
    public static final String EXTRA_ACCESSORY_UEVENT_TIME = "android.hardware.usb.extra.ACCESSORY_UEVENT_TIME";
    public static final String EXTRA_CAN_BE_DEFAULT = "android.hardware.usb.extra.CAN_BE_DEFAULT";
    public static final String EXTRA_DEVICE = "device";
    public static final String EXTRA_PACKAGE = "android.hardware.usb.extra.PACKAGE";
    public static final String EXTRA_PERMISSION_GRANTED = "permission";
    public static final String EXTRA_PORT = "port";
    public static final String EXTRA_PORT_STATUS = "portStatus";

    @SystemApi
    public static final long FUNCTION_ACCESSORY = 2;
    public static final long FUNCTION_ACM = 4096;

    @SystemApi
    public static final long FUNCTION_ADB = 1;

    @SystemApi
    public static final long FUNCTION_AUDIO_SOURCE = 64;
    public static final long FUNCTION_CONN_GADGET = 4194304;
    public static final long FUNCTION_DIAG = 2048;
    public static final long FUNCTION_DIAG_MDM = 8388608;
    public static final long FUNCTION_DM = 8192;
    public static final long FUNCTION_DM1 = 1048576;
    public static final long FUNCTION_DPL = 32768;
    public static final long FUNCTION_MASS_STORAGE = 524288;
    public static final long FUNCTION_MBIM = 67108864;

    @SystemApi
    public static final long FUNCTION_MIDI = 8;

    @SystemApi
    public static final long FUNCTION_MTP = 4;
    private static final Map<String, Long> FUNCTION_NAME_TO_CODE;

    @SystemApi
    public static final long FUNCTION_NCM = 1024;

    @SystemApi
    public static final long FUNCTION_NONE = 0;

    @SystemApi
    public static final long FUNCTION_PTP = 16;
    public static final long FUNCTION_QDSS = 16777216;
    public static final long FUNCTION_QDSS_MDM = 33554432;
    public static final long FUNCTION_RMNET = 131072;

    @SystemApi
    public static final long FUNCTION_RNDIS = 32;
    public static final long FUNCTION_SEC_CHARGING = 262144;
    public static final long FUNCTION_SERIAL_CDEV = 16384;
    public static final long FUNCTION_SHUTDOWN = 134217728;
    public static final long FUNCTION_UTS = 65536;

    @SystemApi
    public static final long FUNCTION_UVC = 128;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int GADGET_HAL_NOT_SUPPORTED = -1;
    public static final String GADGET_HAL_UNKNOWN = "unknown";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int GADGET_HAL_V1_0 = 10;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int GADGET_HAL_V1_1 = 11;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int GADGET_HAL_V1_2 = 12;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int GADGET_HAL_V2_0 = 20;
    public static final String GADGET_HAL_VERSION_1_0 = "V1_0";
    public static final String GADGET_HAL_VERSION_1_1 = "V1_1";
    public static final String GADGET_HAL_VERSION_1_2 = "V1_2";
    public static final String GADGET_HAL_VERSION_2_0 = "V2_0";
    public static final int SEM_DATA_ROLE_STATUS_DEVICE = 2;
    public static final int SEM_DATA_ROLE_STATUS_HOST = 1;
    public static final int SEM_DATA_ROLE_STATUS_NONE = -1;
    public static final int SEM_DATA_ROLE_STATUS_SWAPPING = 0;
    public static final int SEM_MODE_DATA_MASK = 14;
    public static final int SEM_MODE_DATA_MASS_STORAGE = 8;
    public static final int SEM_MODE_DATA_MIDI = 6;
    public static final int SEM_MODE_DATA_MTP = 2;
    public static final int SEM_MODE_DATA_NONE = 0;
    public static final int SEM_MODE_DATA_PTP = 4;
    public static final int SEM_MODE_MTP_AND_CONN_GADGET = 10;
    public static final int SEM_MODE_POWER_MASK = 1;
    public static final int SEM_MODE_POWER_SINK = 0;
    public static final int SEM_MODE_POWER_SOURCE = 1;
    public static final int SEM_POWER_ROLE_STATUS_NONE = -1;
    public static final int SEM_POWER_ROLE_STATUS_SINK = 2;
    public static final int SEM_POWER_ROLE_STATUS_SOURCE = 1;
    public static final int SEM_POWER_ROLE_STATUS_SWAPPING = 0;
    private static final long SETTABLE_FUNCTIONS = 266337468;
    private static final String TAG = "UsbManager";

    @SystemApi
    public static final String USB_CONFIGURED = "configured";
    public static final String USB_CONFIG_CHANGED = "config_changed";

    @SystemApi
    public static final String USB_CONNECTED = "connected";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_10G = 10240;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_20G = 20480;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_40G = 40960;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_5G = 5120;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_FULL_SPEED = 12;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_HIGH_SPEED = 480;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_LOW_SPEED = 2;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_UNKNOWN = -1;
    public static final String USB_DATA_UNLOCKED = "unlocked";
    public static final String USB_FUNCTION_ACCESSORY = "accessory";
    public static final String USB_FUNCTION_ACM = "acm";
    public static final String USB_FUNCTION_ADB = "adb";
    public static final String USB_FUNCTION_ASKON = "askon";
    public static final String USB_FUNCTION_AUDIO_SOURCE = "audio_source";
    public static final String USB_FUNCTION_CHARGING = "charging";
    public static final String USB_FUNCTION_CONN_GADGET = "conn_gadget";
    public static final String USB_FUNCTION_DIAG = "diag";
    public static final String USB_FUNCTION_DIAG_ACM = "diag,acm";
    public static final String USB_FUNCTION_DIAG_MDM = "diag_mdm";
    public static final String USB_FUNCTION_DM = "dm";
    public static final String USB_FUNCTION_DM1 = "dm1";
    public static final String USB_FUNCTION_DM_ACM_ADB = "dm,acm,adb";
    public static final String USB_FUNCTION_DPL = "dpl";
    public static final String USB_FUNCTION_MASS_STORAGE = "mass_storage";
    public static final String USB_FUNCTION_MBIM = "mbim";
    public static final String USB_FUNCTION_MIDI = "midi";
    public static final String USB_FUNCTION_MTP = "mtp";
    public static final String USB_FUNCTION_MTP_ADB = "mtp,adb";
    public static final String USB_FUNCTION_MTP_GADGET = "mtp,conn_gadget";

    @SystemApi
    public static final String USB_FUNCTION_NCM = "ncm";
    public static final String USB_FUNCTION_NONE = "none";
    public static final String USB_FUNCTION_PTP = "ptp";
    public static final String USB_FUNCTION_PTP_ADB = "ptp,adb";
    public static final String USB_FUNCTION_QDSS = "qdss";
    public static final String USB_FUNCTION_QDSS_MDM = "qdss_mdm";
    public static final String USB_FUNCTION_RMNET = "rmnet";

    @SystemApi
    public static final String USB_FUNCTION_RNDIS = "rndis";
    public static final String USB_FUNCTION_RNDIS_ACM_DIAG = "rndis,acm,diag";
    public static final String USB_FUNCTION_RNDIS_ACM_DM = "rndis,acm,dm";
    public static final String USB_FUNCTION_RNDIS_ACM_DM_ADB = "rndis,acm,dm,adb";
    public static final String USB_FUNCTION_RNDIS_ADB = "rndis,adb";
    public static final String USB_FUNCTION_RNDIS_DIAG = "rndis,diag";
    public static final String USB_FUNCTION_RNDIS_DM = "rndis,dm";
    public static final String USB_FUNCTION_SEC_CHARGING = "sec_charging";
    public static final String USB_FUNCTION_SERIAL_CDEV = "serial_cdev";
    public static final String USB_FUNCTION_SHUTDOWN = "shutdown";
    public static final String USB_FUNCTION_UTS = "uts";
    public static final String USB_FUNCTION_UVC = "uvc";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_NOT_SUPPORTED = -1;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_RETRY = -2;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_V1_0 = 10;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_V1_1 = 11;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_V1_2 = 12;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_V1_3 = 13;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_V2_0 = 20;
    public static final String USB_HOST_CONNECTED = "host_connected";
    private static final AtomicInteger sUsbOperationCount;
    private final Context mContext;
    private ArrayMap<DisplayPortAltModeInfoListener, Executor> mDisplayPortListeners;
    private final Object mDisplayPortListenersLock = new Object();
    private DisplayPortAltModeInfoDispatchingListener mDisplayPortServiceListener;
    private final IUsbManager mService;

    @SystemApi
    public interface DisplayPortAltModeInfoListener {
        void onDisplayPortAltModeInfoChanged(String str, DisplayPortAltModeInfo displayPortAltModeInfo);
    }

    public @interface UsbFunctionMode {
    }

    public @interface UsbGadgetHalVersion {
    }

    public @interface UsbHalVersion {
    }

    static {
        HashMap hashMap = new HashMap();
        FUNCTION_NAME_TO_CODE = hashMap;
        sUsbOperationCount = new AtomicInteger();
        hashMap.put(USB_FUNCTION_MTP, 4L);
        hashMap.put(USB_FUNCTION_PTP, 16L);
        hashMap.put(USB_FUNCTION_RNDIS, 32L);
        hashMap.put("midi", 8L);
        hashMap.put("accessory", 2L);
        hashMap.put(USB_FUNCTION_AUDIO_SOURCE, 64L);
        hashMap.put("adb", 1L);
        hashMap.put(USB_FUNCTION_NCM, 1024L);
        hashMap.put(USB_FUNCTION_UVC, 128L);
        hashMap.put(USB_FUNCTION_DIAG, 2048L);
        hashMap.put(USB_FUNCTION_ACM, 4096L);
        hashMap.put("dm", 8192L);
        hashMap.put(USB_FUNCTION_DM1, 1048576L);
        hashMap.put(USB_FUNCTION_SERIAL_CDEV, 16384L);
        hashMap.put(USB_FUNCTION_DPL, 32768L);
        hashMap.put(USB_FUNCTION_UTS, 65536L);
        hashMap.put(USB_FUNCTION_RMNET, 131072L);
        hashMap.put(USB_FUNCTION_SEC_CHARGING, 262144L);
        hashMap.put(USB_FUNCTION_MASS_STORAGE, 524288L);
        hashMap.put(USB_FUNCTION_CONN_GADGET, 4194304L);
        hashMap.put(USB_FUNCTION_DIAG_MDM, 8388608L);
        hashMap.put(USB_FUNCTION_QDSS, 16777216L);
        hashMap.put(USB_FUNCTION_QDSS_MDM, 33554432L);
        hashMap.put(USB_FUNCTION_MBIM, 67108864L);
        hashMap.put(USB_FUNCTION_SHUTDOWN, 134217728L);
        DEFAULT_MODES = new int[]{2, 4, 6, 0, 1};
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DisplayPortAltModeInfoDispatchingListener extends IDisplayPortAltModeInfoListener.Stub {
        private DisplayPortAltModeInfoDispatchingListener() {
        }

        @Override // android.hardware.usb.IDisplayPortAltModeInfoListener
        public void onDisplayPortAltModeInfoChanged(final String portId, final DisplayPortAltModeInfo displayPortAltModeInfo) {
            synchronized (UsbManager.this.mDisplayPortListenersLock) {
                for (Map.Entry<DisplayPortAltModeInfoListener, Executor> entry : UsbManager.this.mDisplayPortListeners.entrySet()) {
                    Executor executor = entry.getValue();
                    final DisplayPortAltModeInfoListener callback = entry.getKey();
                    long token = Binder.clearCallingIdentity();
                    try {
                        try {
                            executor.execute(new Runnable() { // from class: android.hardware.usb.UsbManager$DisplayPortAltModeInfoDispatchingListener$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    UsbManager.DisplayPortAltModeInfoListener.this.onDisplayPortAltModeInfoChanged(portId, displayPortAltModeInfo);
                                }
                            });
                        } catch (Exception e) {
                            Slog.m150e(UsbManager.TAG, "Exception during onDisplayPortAltModeInfoChanged from executor: " + executor, e);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(token);
                    }
                }
            }
        }
    }

    public UsbManager(Context context, IUsbManager service) {
        this.mContext = context;
        this.mService = service;
    }

    public HashMap<String, UsbDevice> getDeviceList() {
        HashMap<String, UsbDevice> result = new HashMap<>();
        if (this.mService == null) {
            return result;
        }
        Bundle bundle = new Bundle();
        try {
            this.mService.getDeviceList(bundle);
            for (String name : bundle.keySet()) {
                result.put(name, (UsbDevice) bundle.get(name));
            }
            return result;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public UsbDeviceConnection openDevice(UsbDevice device) {
        try {
            String deviceName = device.getDeviceName();
            ParcelFileDescriptor pfd = this.mService.openDevice(deviceName, this.mContext.getPackageName());
            if (pfd != null) {
                UsbDeviceConnection connection = new UsbDeviceConnection(device);
                boolean result = connection.open(deviceName, pfd, this.mContext);
                pfd.close();
                if (result) {
                    return connection;
                }
                return null;
            }
            return null;
        } catch (Exception e) {
            Log.m97e(TAG, "exception in UsbManager.openDevice", e);
            return null;
        }
    }

    public UsbAccessory[] getAccessoryList() {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return null;
        }
        try {
            UsbAccessory accessory = iUsbManager.getCurrentAccessory();
            if (accessory == null) {
                return null;
            }
            return new UsbAccessory[]{accessory};
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor openAccessory(UsbAccessory accessory) {
        try {
            return this.mService.openAccessory(accessory);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor getControlFd(long function) {
        try {
            return this.mService.getControlFd(function);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(UsbDevice device) {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return false;
        }
        try {
            return iUsbManager.hasDevicePermission(device, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(UsbDevice device, String packageName, int pid, int uid) {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return false;
        }
        try {
            return iUsbManager.hasDevicePermissionWithIdentity(device, packageName, pid, uid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(UsbAccessory accessory) {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return false;
        }
        try {
            return iUsbManager.hasAccessoryPermission(accessory);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(UsbAccessory accessory, int pid, int uid) {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return false;
        }
        try {
            return iUsbManager.hasAccessoryPermissionWithIdentity(accessory, pid, uid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestPermission(UsbDevice device, PendingIntent pi) {
        try {
            this.mService.requestDevicePermission(device, this.mContext.getPackageName(), pi);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestPermission(UsbAccessory accessory, PendingIntent pi) {
        try {
            this.mService.requestAccessoryPermission(accessory, this.mContext.getPackageName(), pi);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void grantPermission(UsbDevice device) {
        grantPermission(device, Process.myUid());
    }

    public void grantPermission(UsbDevice device, int uid) {
        try {
            this.mService.grantDevicePermission(device, uid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void grantPermission(UsbDevice device, String packageName) {
        try {
            int uid = this.mContext.getPackageManager().getPackageUidAsUser(packageName, this.mContext.getUserId());
            grantPermission(device, uid);
        } catch (PackageManager.NameNotFoundException e) {
            Log.m97e(TAG, "Package " + packageName + " not found.", e);
        }
    }

    @Deprecated
    public boolean isFunctionEnabled(String function) {
        try {
            return this.mService.isFunctionEnabled(function);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setCurrentFunctions(long functions) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "setCurrentFunctions", new Exception("who's calling?"));
        } else {
            Log.m95d(TAG, "setCurrentFunctions", new Exception("who's calling?"));
        }
        Log.m94d(TAG, "setCurrentFunction: functions=" + usbFunctionsToString(functions));
        int operationId = sUsbOperationCount.incrementAndGet() + Binder.getCallingUid();
        try {
            this.mService.setCurrentFunctions(functions, operationId);
        } catch (RemoteException e) {
            Log.m97e(TAG, "setCurrentFunctions: failed to call setCurrentFunctions. functions:" + functions + ", opId:" + operationId, e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setCurrentFunction(String functions, boolean usbDataUnlocked) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "setCurrentFunctions", new Exception("who's calling?"));
        } else {
            Log.m95d(TAG, "setCurrentFunctions", new Exception("who's calling?"));
        }
        Log.m94d(TAG, "setCurrentFunction(String): functions=" + functions);
        int operationId = sUsbOperationCount.incrementAndGet() + Binder.getCallingUid();
        try {
            this.mService.setCurrentFunction(functions, usbDataUnlocked, operationId);
        } catch (RemoteException e) {
            Log.m97e(TAG, "setCurrentFunction: failed to call setCurrentFunction. functions:" + functions + ", opId:" + operationId, e);
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public long getCurrentFunctions() {
        try {
            return this.mService.getCurrentFunctions();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setScreenUnlockedFunctions(long functions) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "setScreenUnlockedFunctions", new Exception("who's calling?"));
        } else {
            Log.m95d(TAG, "setScreenUnlockedFunctions", new Exception("who's calling?"));
        }
        Log.m94d(TAG, "setScreenUnlockedFunctions: functions=" + usbFunctionsToString(functions));
        try {
            this.mService.setScreenUnlockedFunctions(functions);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getScreenUnlockedFunctions() {
        try {
            return this.mService.getScreenUnlockedFunctions();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public int getUsbBandwidthMbps() {
        try {
            int usbSpeed = this.mService.getCurrentUsbSpeed();
            return usbSpeedToBandwidth(usbSpeed);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public int getGadgetHalVersion() {
        try {
            return this.mService.getGadgetHalVersion();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public int getUsbHalVersion() {
        try {
            return this.mService.getUsbHalVersion();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void resetUsbGadget() {
        try {
            this.mService.resetUsbGadget();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static boolean isUvcSupportEnabled() {
        return SystemProperties.getBoolean("ro.usb.uvc.enabled", false);
    }

    public boolean enableUsbDataSignal(boolean enable) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "Enable USB Data Signal", new Exception("who's calling?"));
        } else {
            Log.m95d(TAG, "Enable USB Data Signal", new Exception("who's calling?"));
        }
        return setUsbDataSignal(getPorts(), !enable, true);
    }

    private boolean setUsbDataSignal(List<UsbPort> usbPorts, boolean disable, boolean revertOnFailure) {
        List<UsbPort> changedPorts = new ArrayList<>();
        for (int i = 0; i < usbPorts.size(); i++) {
            UsbPort port = usbPorts.get(i);
            Log.m94d(TAG, "Set USB Data Signal : Port Disabled[" + isPortDisabled(port) + "], Disable[" + disable + NavigationBarInflaterView.SIZE_MOD_END);
            if (isPortDisabled(port) != disable) {
                changedPorts.add(port);
                Log.m94d(TAG, "Set USB Data Signal : port return[" + port.enableUsbData(!disable) + "], Revert On Fail[" + revertOnFailure + NavigationBarInflaterView.SIZE_MOD_END);
                if (port.enableUsbData(!disable) != 0 && revertOnFailure) {
                    Log.m96e(TAG, "Failed to set usb data signal for portID(" + port.getId() + NavigationBarInflaterView.KEY_CODE_END);
                    setUsbDataSignal(changedPorts, !disable, false);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPortDisabled(UsbPort usbPort) {
        Log.m94d(TAG, "Port Disabled Status[" + getPortStatus(usbPort).getUsbDataStatus() + NavigationBarInflaterView.SIZE_MOD_END);
        return (getPortStatus(usbPort).getUsbDataStatus() & 16) == 16;
    }

    @SystemApi
    public List<UsbPort> getPorts() {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return Collections.emptyList();
        }
        try {
            List<ParcelableUsbPort> parcelablePorts = iUsbManager.getPorts();
            if (parcelablePorts == null) {
                return Collections.emptyList();
            }
            int numPorts = parcelablePorts.size();
            ArrayList<UsbPort> ports = new ArrayList<>(numPorts);
            for (int i = 0; i < numPorts; i++) {
                ports.add(parcelablePorts.get(i).getUsbPort(this));
            }
            return ports;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    UsbPortStatus getPortStatus(UsbPort port) {
        try {
            return this.mService.getPortStatus(port.getId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setPortRoles(UsbPort port, int powerRole, int dataRole) {
        Log.m94d(TAG, "setPortRoles: portId=" + port.getId() + " powerRole=" + powerRole + " dataRole=" + dataRole);
        Log.m94d(TAG, "setPortRoles Package:" + this.mContext.getPackageName());
        try {
            this.mService.setPortRoles(port.getId(), powerRole, dataRole);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void enableContaminantDetection(UsbPort port, boolean enable) {
        try {
            this.mService.enableContaminantDetection(port.getId(), enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void enableLimitPowerTransfer(UsbPort port, boolean limit, int operationId, IUsbOperationInternal callback) {
        Objects.requireNonNull(port, "enableLimitPowerTransfer:port must not be null. opId:" + operationId);
        try {
            this.mService.enableLimitPowerTransfer(port.getId(), limit, operationId, callback);
        } catch (RemoteException e) {
            Log.m97e(TAG, "enableLimitPowerTransfer failed. opId:" + operationId, e);
            try {
                callback.onOperationComplete(1);
            } catch (RemoteException r) {
                Log.m97e(TAG, "enableLimitPowerTransfer failed to call onOperationComplete. opId:" + operationId, r);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    void resetUsbPort(UsbPort port, int operationId, IUsbOperationInternal callback) {
        Objects.requireNonNull(port, "resetUsbPort: port must not be null. opId:" + operationId);
        try {
            this.mService.resetUsbPort(port.getId(), operationId, callback);
        } catch (RemoteException e) {
            Log.m97e(TAG, "resetUsbPort: failed. ", e);
            try {
                callback.onOperationComplete(1);
            } catch (RemoteException r) {
                Log.m97e(TAG, "resetUsbPort: failed to call onOperationComplete. opId:" + operationId, r);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    boolean enableUsbData(UsbPort port, boolean enable, int operationId, IUsbOperationInternal callback) {
        Objects.requireNonNull(port, "enableUsbData: port must not be null. opId:" + operationId);
        try {
            return this.mService.enableUsbData(port.getId(), enable, operationId, callback);
        } catch (RemoteException e) {
            Log.m97e(TAG, "enableUsbData: failed. opId:" + operationId, e);
            try {
                callback.onOperationComplete(1);
            } catch (RemoteException r) {
                Log.m97e(TAG, "enableUsbData: failed to call onOperationComplete. opId:" + operationId, r);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    void enableUsbDataWhileDocked(UsbPort port, int operationId, IUsbOperationInternal callback) {
        Objects.requireNonNull(port, "enableUsbDataWhileDocked: port must not be null. opId:" + operationId);
        try {
            this.mService.enableUsbDataWhileDocked(port.getId(), operationId, callback);
        } catch (RemoteException e) {
            Log.m97e(TAG, "enableUsbDataWhileDocked: failed. opId:" + operationId, e);
            try {
                callback.onOperationComplete(1);
            } catch (RemoteException r) {
                Log.m97e(TAG, "enableUsbDataWhileDocked: failed to call onOperationComplete. opId:" + operationId, r);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean registerDisplayPortAltModeEventsIfNeededLocked() {
        DisplayPortAltModeInfoDispatchingListener displayPortDispatchingListener = new DisplayPortAltModeInfoDispatchingListener();
        try {
            if (this.mService.registerForDisplayPortEvents(displayPortDispatchingListener)) {
                this.mDisplayPortServiceListener = displayPortDispatchingListener;
                return true;
            }
            return false;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void registerDisplayPortAltModeInfoListener(Executor executor, DisplayPortAltModeInfoListener listener) {
        Objects.requireNonNull(executor, "registerDisplayPortAltModeInfoListener: executor must not be null.");
        Objects.requireNonNull(listener, "registerDisplayPortAltModeInfoListener: listener must not be null.");
        synchronized (this.mDisplayPortListenersLock) {
            if (this.mDisplayPortListeners == null) {
                this.mDisplayPortListeners = new ArrayMap<>();
            }
            if (this.mDisplayPortServiceListener == null && !registerDisplayPortAltModeEventsIfNeededLocked()) {
                throw new IllegalStateException("Unexpected failure registering service listener");
            }
            if (this.mDisplayPortListeners.containsKey(listener)) {
                throw new IllegalStateException("Listener has already been registered.");
            }
            this.mDisplayPortListeners.put(listener, executor);
        }
    }

    private void unregisterDisplayPortAltModeEventsLocked() {
        DisplayPortAltModeInfoDispatchingListener displayPortAltModeInfoDispatchingListener = this.mDisplayPortServiceListener;
        if (displayPortAltModeInfoDispatchingListener != null) {
            try {
                try {
                    this.mService.unregisterForDisplayPortEvents(displayPortAltModeInfoDispatchingListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                this.mDisplayPortServiceListener = null;
            }
        }
    }

    @SystemApi
    public void unregisterDisplayPortAltModeInfoListener(DisplayPortAltModeInfoListener listener) {
        synchronized (this.mDisplayPortListenersLock) {
            ArrayMap<DisplayPortAltModeInfoListener, Executor> arrayMap = this.mDisplayPortListeners;
            if (arrayMap == null) {
                return;
            }
            arrayMap.remove(listener);
            if (this.mDisplayPortListeners.isEmpty()) {
                unregisterDisplayPortAltModeEventsLocked();
            }
        }
    }

    public void setUsbDeviceConnectionHandler(ComponentName usbDeviceConnectionHandler) {
        try {
            this.mService.setUsbDeviceConnectionHandler(usbDeviceConnectionHandler);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean areSettableFunctions(long functions) {
        if (functions != 0) {
            return ((-266337469) & functions) == 0 && (Long.bitCount(functions) >= 1 || functions == 1056);
        }
        return true;
    }

    public static String usbFunctionsToString(long functions) {
        StringJoiner joiner = new StringJoiner(",");
        if ((4 & functions) != 0) {
            joiner.add(USB_FUNCTION_MTP);
        }
        if ((16 & functions) != 0) {
            joiner.add(USB_FUNCTION_PTP);
        }
        if ((32 & functions) != 0) {
            joiner.add(USB_FUNCTION_RNDIS);
        }
        if ((8 & functions) != 0) {
            joiner.add("midi");
        }
        if ((2 & functions) != 0) {
            joiner.add("accessory");
        }
        if ((64 & functions) != 0) {
            joiner.add(USB_FUNCTION_AUDIO_SOURCE);
        }
        if ((1024 & functions) != 0) {
            joiner.add(USB_FUNCTION_NCM);
        }
        if ((128 & functions) != 0) {
            joiner.add(USB_FUNCTION_UVC);
        }
        if ((4096 & functions) != 0) {
            joiner.add(USB_FUNCTION_ACM);
        }
        if ((67108864 & functions) != 0) {
            joiner.add(USB_FUNCTION_MBIM);
        }
        if ((8192 & functions) != 0) {
            joiner.add("dm");
        }
        if ((1048576 & functions) != 0) {
            joiner.add(USB_FUNCTION_DM1);
        }
        if ((2048 & functions) != 0) {
            joiner.add(USB_FUNCTION_DIAG);
        }
        if ((8388608 & functions) != 0) {
            joiner.add(USB_FUNCTION_DIAG_MDM);
        }
        if ((16777216 & functions) != 0) {
            joiner.add(USB_FUNCTION_QDSS);
        }
        if ((33554432 & functions) != 0) {
            joiner.add(USB_FUNCTION_QDSS_MDM);
        }
        if ((16384 & functions) != 0) {
            joiner.add(USB_FUNCTION_SERIAL_CDEV);
        }
        if ((65536 & functions) != 0) {
            joiner.add(USB_FUNCTION_UTS);
        }
        if ((131072 & functions) != 0) {
            joiner.add(USB_FUNCTION_RMNET);
        }
        if ((32768 & functions) != 0) {
            joiner.add(USB_FUNCTION_DPL);
        }
        if ((262144 & functions) != 0) {
            joiner.add(USB_FUNCTION_SEC_CHARGING);
        }
        if ((524288 & functions) != 0) {
            joiner.add(USB_FUNCTION_MASS_STORAGE);
        }
        if ((4194304 & functions) != 0) {
            joiner.add(USB_FUNCTION_CONN_GADGET);
        }
        if ((134217728 & functions) != 0) {
            joiner.add(USB_FUNCTION_SHUTDOWN);
        }
        if ((1 & functions) != 0) {
            joiner.add("adb");
        }
        return joiner.toString();
    }

    public static long usbFunctionsFromString(String functions) {
        if (functions == null || functions.equals("none")) {
            return 0L;
        }
        long ret = 0;
        for (String function : functions.split(",")) {
            Map<String, Long> map = FUNCTION_NAME_TO_CODE;
            if (map.containsKey(function)) {
                ret |= map.get(function).longValue();
            } else if (function.length() > 0) {
                Log.m94d(TAG, "usbFunctionsFromString: Invalid usb functions=" + functions);
                return 0L;
            }
        }
        return ret;
    }

    public boolean isUsbBlocked() {
        try {
            return this.mService.isUsbBlocked();
        } catch (RemoteException e) {
            Log.m97e(TAG, "RemoteException in isUsbBlocked", e);
            return false;
        }
    }

    @SystemApi
    public boolean isSupportScrLckBlk() {
        try {
            return this.mService.isSupportScrLckBlk();
        } catch (RemoteException e) {
            Log.m97e(TAG, "RemoteException in isSupportScrLckBlk", e);
            return false;
        }
    }

    public boolean isSupportDexRestrict() {
        try {
            return this.mService.isSupportDexRestrict();
        } catch (RemoteException e) {
            Log.m97e(TAG, "RemoteException in isSupportDexRestrict", e);
            return false;
        }
    }

    public int restrictUsbHostInterface(boolean enable, String strAllowList) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("no permission to call finishMediaUpdate()");
        }
        try {
            return this.mService.restrictUsbHostInterface(enable, strAllowList);
        } catch (RemoteException e) {
            Log.m97e(TAG, "RemoteException in restrictUsbHostInterface", e);
            return -1;
        }
    }

    public void setUsbHiddenMenuState(boolean enable) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "setUsbHiddenMenuState", new Exception("who's calling?"));
        } else {
            Log.m95d(TAG, "setUsbHiddenMenuState", new Exception("who's calling?"));
        }
        Log.m94d(TAG, "setUsbHiddenMenuState: enable=" + enable);
        try {
            this.mService.setUsbHiddenMenuState(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int usbSpeedToBandwidth(int speed) {
        switch (speed) {
        }
        return 20480;
    }

    public static String usbGadgetHalVersionToString(int version) {
        if (version == 20) {
            return GADGET_HAL_VERSION_2_0;
        }
        if (version == 12) {
            return GADGET_HAL_VERSION_1_2;
        }
        if (version == 11) {
            return GADGET_HAL_VERSION_1_1;
        }
        if (version == 10) {
            return GADGET_HAL_VERSION_1_0;
        }
        return "unknown";
    }

    public void semGrantDevicePermission(UsbDevice device, int uid) {
        try {
            this.mService.semGrantDevicePermission(device, uid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semSetDevicePackage(UsbDevice device, String packageName, int userId) {
        try {
            this.mService.semSetDevicePackage(device, packageName, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semSetMode(int usbMode) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "semSetMode", new Exception("who's calling?"));
        } else {
            Log.m95d(TAG, "semSetMode", new Exception("who's calling?"));
        }
        try {
            this.mService.semSetMode(usbMode);
        } catch (RemoteException e) {
            Log.m97e(TAG, "RemoteException in semSetMode", e);
        }
    }

    public int semGetPowerRoleStatus() {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "semGetPowerRoleStatus", new Exception("who's calling?"));
        } else {
            Log.m95d(TAG, "semGetPowerRoleStatus", new Exception("who's calling?"));
        }
        try {
            int ret = this.mService.semGetPowerRoleStatus();
            return ret;
        } catch (RemoteException e) {
            Log.m97e(TAG, "RemoteException in UsbManager.semGetPowerRoleStatus", e);
            return -1;
        }
    }

    public int semGetDataRoleStatus() {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "semGetDataRoleStatus", new Exception("who's calling?"));
        } else {
            Log.m95d(TAG, "semGetDataRoleStatus", new Exception("who's calling?"));
        }
        try {
            int ret = this.mService.semGetDataRoleStatus();
            return ret;
        } catch (RemoteException e) {
            Log.m97e(TAG, "RemoteException in UsbManager.semGetDataRoleStatus", e);
            return -1;
        }
    }
}

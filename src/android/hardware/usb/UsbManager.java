package android.hardware.usb;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.usb.IDisplayPortAltModeInfoListener;
import android.hardware.usb.UsbManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.sysfwutil.Slog;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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

    @SystemApi
    public static final int USB_DATA_TRANSFER_RATE_10G = 10240;

    @SystemApi
    public static final int USB_DATA_TRANSFER_RATE_20G = 20480;

    @SystemApi
    public static final int USB_DATA_TRANSFER_RATE_40G = 40960;

    @SystemApi
    public static final int USB_DATA_TRANSFER_RATE_5G = 5120;

    @SystemApi
    public static final int USB_DATA_TRANSFER_RATE_FULL_SPEED = 12;

    @SystemApi
    public static final int USB_DATA_TRANSFER_RATE_HIGH_SPEED = 480;

    @SystemApi
    public static final int USB_DATA_TRANSFER_RATE_LOW_SPEED = 2;

    @SystemApi
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
    private ArrayMap<UsbAccessory, AccessoryHandle> mAccessoryHandleMap;
    private final Context mContext;
    private ArrayMap<DisplayPortAltModeInfoListener, Executor> mDisplayPortListeners;
    private DisplayPortAltModeInfoDispatchingListener mDisplayPortServiceListener;
    private final IUsbManager mService;
    private final Object mDisplayPortListenersLock = new Object();
    private final Object mAccessoryHandleMapLock = new Object();

    @SystemApi
    public interface DisplayPortAltModeInfoListener {
        void onDisplayPortAltModeInfoChanged(String str, DisplayPortAltModeInfo displayPortAltModeInfo);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UsbFunctionMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UsbGadgetHalVersion {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UsbHalVersion {
    }

    @SystemApi
    public static boolean isUvcSupportEnabled() {
        return false;
    }

    public static int usbSpeedToBandwidth(int i) {
        switch (i) {
            case 0:
                return 2;
            case 1:
                return 12;
            case 2:
                return 480;
            case 3:
                return 5120;
            case 4:
                return 10240;
            case 5:
                return 20480;
            case 6:
                return 10240;
            case 7:
            case 8:
                return 20480;
            case 9:
                return USB_DATA_TRANSFER_RATE_40G;
            default:
                return -1;
        }
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
        hashMap.put(USB_FUNCTION_SEC_CHARGING, 262144L);
        hashMap.put(USB_FUNCTION_ACM, 4096L);
        hashMap.put("dm", 8192L);
        hashMap.put(USB_FUNCTION_DM1, 1048576L);
        hashMap.put(USB_FUNCTION_SERIAL_CDEV, 16384L);
        hashMap.put(USB_FUNCTION_DPL, 32768L);
        hashMap.put(USB_FUNCTION_UTS, 65536L);
        hashMap.put(USB_FUNCTION_RMNET, 131072L);
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
        public void onDisplayPortAltModeInfoChanged(final String str, final DisplayPortAltModeInfo displayPortAltModeInfo) {
            synchronized (UsbManager.this.mDisplayPortListenersLock) {
                for (Map.Entry entry : UsbManager.this.mDisplayPortListeners.entrySet()) {
                    Executor executor = (Executor) entry.getValue();
                    final DisplayPortAltModeInfoListener displayPortAltModeInfoListener = (DisplayPortAltModeInfoListener) entry.getKey();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            executor.execute(new Runnable() { // from class: android.hardware.usb.UsbManager$DisplayPortAltModeInfoDispatchingListener$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    UsbManager.DisplayPortAltModeInfoListener.this.onDisplayPortAltModeInfoChanged(str, displayPortAltModeInfo);
                                }
                            });
                        } catch (Exception e) {
                            Slog.e(UsbManager.TAG, "Exception during onDisplayPortAltModeInfoChanged from executor: " + executor, e);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        }
    }

    private AccessoryHandle openHandleForAccessory(UsbAccessory usbAccessory, boolean z) throws RemoteException {
        synchronized (this.mAccessoryHandleMapLock) {
            if (this.mAccessoryHandleMap == null) {
                this.mAccessoryHandleMap = new ArrayMap<>();
            }
            if (!this.mAccessoryHandleMap.containsKey(usbAccessory)) {
                AccessoryHandle accessoryHandle = new AccessoryHandle(this.mService.openAccessory(usbAccessory), z, !z);
                this.mAccessoryHandleMap.put(usbAccessory, accessoryHandle);
                return accessoryHandle;
            }
            AccessoryHandle accessoryHandle2 = this.mAccessoryHandleMap.get(usbAccessory);
            if (accessoryHandle2 == null) {
                throw new IllegalStateException("Accessory doesn't have an associated handle yet!");
            }
            AccessoryHandle modifiedHandleForOpeningStream = getModifiedHandleForOpeningStream(z, accessoryHandle2);
            this.mAccessoryHandleMap.put(usbAccessory, modifiedHandleForOpeningStream);
            return modifiedHandleForOpeningStream;
        }
    }

    private AccessoryHandle getModifiedHandleForOpeningStream(boolean z, AccessoryHandle accessoryHandle) {
        if (accessoryHandle.isInputStreamOpened() && z) {
            throw new IllegalStateException("Input stream already open for this accessory! Please close the existing input stream before opening a new one.");
        }
        if (accessoryHandle.isOutputStreamOpened() && !z) {
            throw new IllegalStateException("Output stream already open for this accessory! Please close the existing output stream before opening a new one.");
        }
        return new AccessoryHandle(accessoryHandle.getPfd(), z || accessoryHandle.isInputStreamOpened(), !z || accessoryHandle.isOutputStreamOpened());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeHandleForAccessory(UsbAccessory usbAccessory, boolean z) throws IOException {
        synchronized (this.mAccessoryHandleMapLock) {
            AccessoryHandle accessoryHandle = this.mAccessoryHandleMap.get(usbAccessory);
            if (accessoryHandle == null) {
                throw new IllegalStateException("No handle has been initialised for this accessory!");
            }
            AccessoryHandle modifiedHandleForClosingStream = getModifiedHandleForClosingStream(z, accessoryHandle);
            if (!modifiedHandleForClosingStream.isOpen()) {
                modifiedHandleForClosingStream.getPfd().close();
                this.mAccessoryHandleMap.remove(usbAccessory);
            } else {
                this.mAccessoryHandleMap.put(usbAccessory, modifiedHandleForClosingStream);
            }
        }
    }

    private AccessoryHandle getModifiedHandleForClosingStream(boolean z, AccessoryHandle accessoryHandle) {
        if (!accessoryHandle.isInputStreamOpened() && z) {
            throw new IllegalStateException("Attempting to close an input stream that has not been opened for this accessory!");
        }
        if (!accessoryHandle.isOutputStreamOpened() && !z) {
            throw new IllegalStateException("Attempting to close an output stream that has not been opened for this accessory!");
        }
        return new AccessoryHandle(accessoryHandle.getPfd(), !z && accessoryHandle.isInputStreamOpened(), z && accessoryHandle.isOutputStreamOpened());
    }

    private class AccessoryAutoCloseInputStream extends FileInputStream {
        private final UsbAccessory mAccessory;
        private final ParcelFileDescriptor mPfd;

        AccessoryAutoCloseInputStream(UsbAccessory usbAccessory, ParcelFileDescriptor parcelFileDescriptor) {
            super(parcelFileDescriptor.getFileDescriptor());
            this.mAccessory = usbAccessory;
            this.mPfd = parcelFileDescriptor;
        }

        @Override // java.io.FileInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            super.close();
            UsbManager.this.closeHandleForAccessory(this.mAccessory, true);
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int read() throws IOException {
            int read = super.read();
            checkError(read);
            return read;
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr) throws IOException {
            int read = super.read(bArr);
            checkError(read);
            return read;
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read = super.read(bArr, i, i2);
            checkError(read);
            return read;
        }

        private void checkError(int i) throws IOException {
            if (i == -1 && this.mPfd.canDetectErrors()) {
                this.mPfd.checkError();
            }
        }
    }

    private class AccessoryAutoCloseOutputStream extends FileOutputStream {
        private final UsbAccessory mAccessory;

        AccessoryAutoCloseOutputStream(UsbAccessory usbAccessory, ParcelFileDescriptor parcelFileDescriptor) {
            super(parcelFileDescriptor.getFileDescriptor());
            this.mAccessory = usbAccessory;
        }

        @Override // java.io.FileOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            super.close();
            UsbManager.this.closeHandleForAccessory(this.mAccessory, false);
        }
    }

    private static class AccessoryHandle {
        private final boolean mInputStreamOpened;
        private final boolean mOutputStreamOpened;
        private final ParcelFileDescriptor mPfd;

        AccessoryHandle(ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2) {
            this.mPfd = parcelFileDescriptor;
            this.mInputStreamOpened = z;
            this.mOutputStreamOpened = z2;
        }

        public ParcelFileDescriptor getPfd() {
            return this.mPfd;
        }

        public boolean isInputStreamOpened() {
            return this.mInputStreamOpened;
        }

        public boolean isOutputStreamOpened() {
            return this.mOutputStreamOpened;
        }

        public boolean isOpen() {
            return this.mInputStreamOpened || this.mOutputStreamOpened;
        }
    }

    public UsbManager(Context context, IUsbManager iUsbManager) {
        this.mContext = context;
        this.mService = iUsbManager;
    }

    public HashMap<String, UsbDevice> getDeviceList() {
        HashMap<String, UsbDevice> hashMap = new HashMap<>();
        if (this.mService != null) {
            Bundle bundle = new Bundle();
            try {
                this.mService.getDeviceList(bundle);
                for (String str : bundle.keySet()) {
                    hashMap.put(str, (UsbDevice) bundle.get(str));
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return hashMap;
    }

    public UsbDeviceConnection openDevice(UsbDevice usbDevice) {
        try {
            String deviceName = usbDevice.getDeviceName();
            ParcelFileDescriptor openDevice = this.mService.openDevice(deviceName, this.mContext.getPackageName());
            if (openDevice == null) {
                return null;
            }
            UsbDeviceConnection usbDeviceConnection = new UsbDeviceConnection(usbDevice);
            boolean open = usbDeviceConnection.open(deviceName, openDevice, this.mContext);
            openDevice.close();
            if (open) {
                return usbDeviceConnection;
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "exception in UsbManager.openDevice", e);
            return null;
        }
    }

    public UsbAccessory[] getAccessoryList() {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return null;
        }
        try {
            UsbAccessory currentAccessory = iUsbManager.getCurrentAccessory();
            if (currentAccessory == null) {
                return null;
            }
            return new UsbAccessory[]{currentAccessory};
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor openAccessory(UsbAccessory usbAccessory) {
        try {
            return this.mService.openAccessory(usbAccessory);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public InputStream openAccessoryInputStream(UsbAccessory usbAccessory) {
        try {
            return new AccessoryAutoCloseInputStream(usbAccessory, openHandleForAccessory(usbAccessory, true).getPfd());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public OutputStream openAccessoryOutputStream(UsbAccessory usbAccessory) {
        try {
            return new AccessoryAutoCloseOutputStream(usbAccessory, openHandleForAccessory(usbAccessory, false).getPfd());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor getControlFd(long j) {
        try {
            return this.mService.getControlFd(j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(UsbDevice usbDevice) {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return false;
        }
        try {
            return iUsbManager.hasDevicePermission(usbDevice, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(UsbDevice usbDevice, String str, int i, int i2) {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return false;
        }
        try {
            return iUsbManager.hasDevicePermissionWithIdentity(usbDevice, str, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(UsbAccessory usbAccessory) {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return false;
        }
        try {
            return iUsbManager.hasAccessoryPermission(usbAccessory);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(UsbAccessory usbAccessory, int i, int i2) {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return false;
        }
        try {
            return iUsbManager.hasAccessoryPermissionWithIdentity(usbAccessory, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestPermission(UsbDevice usbDevice, PendingIntent pendingIntent) {
        try {
            this.mService.requestDevicePermission(usbDevice, this.mContext.getPackageName(), pendingIntent);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestPermission(UsbAccessory usbAccessory, PendingIntent pendingIntent) {
        try {
            this.mService.requestAccessoryPermission(usbAccessory, this.mContext.getPackageName(), pendingIntent);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void grantPermission(UsbDevice usbDevice) {
        grantPermission(usbDevice, Process.myUid());
    }

    public void grantPermission(UsbDevice usbDevice, int i) {
        try {
            this.mService.grantDevicePermission(usbDevice, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void grantPermission(UsbDevice usbDevice, String str) {
        try {
            grantPermission(usbDevice, this.mContext.getPackageManager().getPackageUidAsUser(str, this.mContext.getUserId()));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Package " + str + " not found.", e);
        }
    }

    @Deprecated
    public boolean isFunctionEnabled(String str) {
        try {
            return this.mService.isFunctionEnabled(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUvcGadgetSupportEnabled() {
        try {
            return this.mService.isUvcGadgetSupportEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setCurrentFunctions(long j) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "setCurrentFunctions", new Exception("who's calling?"));
        } else {
            Log.d(TAG, "setCurrentFunctions", new Exception("who's calling?"));
        }
        Log.d(TAG, "setCurrentFunction: functions=" + usbFunctionsToString(j));
        int incrementAndGet = sUsbOperationCount.incrementAndGet() + Binder.getCallingUid();
        try {
            this.mService.setCurrentFunctions(j, incrementAndGet);
        } catch (RemoteException e) {
            Log.e(TAG, "setCurrentFunctions: failed to call setCurrentFunctions. functions:" + j + ", opId:" + incrementAndGet, e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setCurrentFunction(String str, boolean z) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "setCurrentFunctions", new Exception("who's calling?"));
        } else {
            Log.d(TAG, "setCurrentFunctions", new Exception("who's calling?"));
        }
        Log.d(TAG, "setCurrentFunction(String): functions=" + str);
        int incrementAndGet = sUsbOperationCount.incrementAndGet() + Binder.getCallingUid();
        try {
            this.mService.setCurrentFunction(str, z, incrementAndGet);
        } catch (RemoteException e) {
            Log.e(TAG, "setCurrentFunction: failed to call setCurrentFunction. functions:" + str + ", opId:" + incrementAndGet, e);
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

    public void setScreenUnlockedFunctions(long j) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "setScreenUnlockedFunctions", new Exception("who's calling?"));
        } else {
            Log.d(TAG, "setScreenUnlockedFunctions", new Exception("who's calling?"));
        }
        Log.d(TAG, "setScreenUnlockedFunctions: functions=" + usbFunctionsToString(j));
        try {
            this.mService.setScreenUnlockedFunctions(j);
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

    @SystemApi
    public int getUsbBandwidthMbps() {
        try {
            return usbSpeedToBandwidth(this.mService.getCurrentUsbSpeed());
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

    public boolean enableUsbDataSignal(boolean z) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "Enable USB Data Signal", new Exception("who's calling?"));
        } else {
            Log.d(TAG, "Enable USB Data Signal", new Exception("who's calling?"));
        }
        return setUsbDataSignal(getPorts(), !z, true);
    }

    private boolean setUsbDataSignal(List<UsbPort> list, boolean z, boolean z2) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            UsbPort usbPort = list.get(i);
            Log.d(TAG, "Set USB Data Signal : Port Disabled[" + isPortDisabled(usbPort) + "], Disable[" + z + NavigationBarInflaterView.SIZE_MOD_END);
            if (isPortDisabled(usbPort) != z) {
                arrayList.add(usbPort);
                Log.d(TAG, "Set USB Data Signal : port return[" + usbPort.enableUsbData(!z) + "], Revert On Fail[" + z2 + NavigationBarInflaterView.SIZE_MOD_END);
                if (usbPort.enableUsbData(!z) != 0 && z2) {
                    Log.e(TAG, "Failed to set usb data signal for portID(" + usbPort.getId() + NavigationBarInflaterView.KEY_CODE_END);
                    setUsbDataSignal(arrayList, z ^ true, false);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPortDisabled(UsbPort usbPort) {
        Log.d(TAG, "Port Disabled Status[" + getPortStatus(usbPort).getUsbDataStatus() + NavigationBarInflaterView.SIZE_MOD_END);
        return (getPortStatus(usbPort).getUsbDataStatus() & 16) == 16;
    }

    @SystemApi
    public List<UsbPort> getPorts() {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            List<ParcelableUsbPort> ports = iUsbManager.getPorts();
            if (ports == null) {
                return Collections.EMPTY_LIST;
            }
            int size = ports.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(ports.get(i).getUsbPort(this));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    UsbPortStatus getPortStatus(UsbPort usbPort) {
        try {
            return this.mService.getPortStatus(usbPort.getId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean isModeChangeSupported(UsbPort usbPort) {
        try {
            return this.mService.isModeChangeSupported(usbPort.getId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setPortRoles(UsbPort usbPort, int i, int i2) {
        Log.d(TAG, "setPortRoles: portId=" + usbPort.getId() + " powerRole=" + i + " dataRole=" + i2);
        StringBuilder sb = new StringBuilder("setPortRoles Package:");
        sb.append(this.mContext.getPackageName());
        Log.d(TAG, sb.toString());
        try {
            this.mService.setPortRoles(usbPort.getId(), i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void enableContaminantDetection(UsbPort usbPort, boolean z) {
        try {
            this.mService.enableContaminantDetection(usbPort.getId(), z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void enableLimitPowerTransfer(UsbPort usbPort, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) {
        Objects.requireNonNull(usbPort, "enableLimitPowerTransfer:port must not be null. opId:" + i);
        try {
            this.mService.enableLimitPowerTransfer(usbPort.getId(), z, i, iUsbOperationInternal);
        } catch (RemoteException e) {
            Log.e(TAG, "enableLimitPowerTransfer failed. opId:" + i, e);
            try {
                iUsbOperationInternal.onOperationComplete(1);
            } catch (RemoteException e2) {
                Log.e(TAG, "enableLimitPowerTransfer failed to call onOperationComplete. opId:" + i, e2);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    void resetUsbPort(UsbPort usbPort, int i, IUsbOperationInternal iUsbOperationInternal) {
        Objects.requireNonNull(usbPort, "resetUsbPort: port must not be null. opId:" + i);
        try {
            this.mService.resetUsbPort(usbPort.getId(), i, iUsbOperationInternal);
        } catch (RemoteException e) {
            Log.e(TAG, "resetUsbPort: failed. ", e);
            try {
                iUsbOperationInternal.onOperationComplete(1);
            } catch (RemoteException e2) {
                Log.e(TAG, "resetUsbPort: failed to call onOperationComplete. opId:" + i, e2);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    boolean enableUsbData(UsbPort usbPort, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) {
        Objects.requireNonNull(usbPort, "enableUsbData: port must not be null. opId:" + i);
        try {
            return this.mService.enableUsbData(usbPort.getId(), z, i, iUsbOperationInternal);
        } catch (RemoteException e) {
            Log.e(TAG, "enableUsbData: failed. opId:" + i, e);
            try {
                iUsbOperationInternal.onOperationComplete(1);
            } catch (RemoteException e2) {
                Log.e(TAG, "enableUsbData: failed to call onOperationComplete. opId:" + i, e2);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    void enableUsbDataWhileDocked(UsbPort usbPort, int i, IUsbOperationInternal iUsbOperationInternal) {
        Objects.requireNonNull(usbPort, "enableUsbDataWhileDocked: port must not be null. opId:" + i);
        try {
            this.mService.enableUsbDataWhileDocked(usbPort.getId(), i, iUsbOperationInternal);
        } catch (RemoteException e) {
            Log.e(TAG, "enableUsbDataWhileDocked: failed. opId:" + i, e);
            try {
                iUsbOperationInternal.onOperationComplete(1);
            } catch (RemoteException e2) {
                Log.e(TAG, "enableUsbDataWhileDocked: failed to call onOperationComplete. opId:" + i, e2);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean registerDisplayPortAltModeEventsIfNeededLocked() {
        DisplayPortAltModeInfoDispatchingListener displayPortAltModeInfoDispatchingListener = new DisplayPortAltModeInfoDispatchingListener();
        try {
            if (!this.mService.registerForDisplayPortEvents(displayPortAltModeInfoDispatchingListener)) {
                return false;
            }
            this.mDisplayPortServiceListener = displayPortAltModeInfoDispatchingListener;
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void registerDisplayPortAltModeInfoListener(Executor executor, DisplayPortAltModeInfoListener displayPortAltModeInfoListener) {
        Objects.requireNonNull(executor, "registerDisplayPortAltModeInfoListener: executor must not be null.");
        Objects.requireNonNull(displayPortAltModeInfoListener, "registerDisplayPortAltModeInfoListener: listener must not be null.");
        synchronized (this.mDisplayPortListenersLock) {
            if (this.mDisplayPortListeners == null) {
                this.mDisplayPortListeners = new ArrayMap<>();
            }
            if (this.mDisplayPortServiceListener == null && !registerDisplayPortAltModeEventsIfNeededLocked()) {
                throw new IllegalStateException("Unexpected failure registering service listener");
            }
            if (this.mDisplayPortListeners.containsKey(displayPortAltModeInfoListener)) {
                throw new IllegalStateException("Listener has already been registered.");
            }
            this.mDisplayPortListeners.put(displayPortAltModeInfoListener, executor);
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
    public void unregisterDisplayPortAltModeInfoListener(DisplayPortAltModeInfoListener displayPortAltModeInfoListener) {
        synchronized (this.mDisplayPortListenersLock) {
            ArrayMap<DisplayPortAltModeInfoListener, Executor> arrayMap = this.mDisplayPortListeners;
            if (arrayMap == null) {
                return;
            }
            arrayMap.remove(displayPortAltModeInfoListener);
            if (this.mDisplayPortListeners.isEmpty()) {
                unregisterDisplayPortAltModeEventsLocked();
            }
        }
    }

    public void setUsbDeviceConnectionHandler(ComponentName componentName) {
        try {
            this.mService.setUsbDeviceConnectionHandler(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean areSettableFunctions(long j) {
        if (j != 0) {
            if (((-266337469) & j) != 0) {
                return false;
            }
            if (Long.bitCount(j) < 1 && j != 1056) {
                return false;
            }
        }
        return true;
    }

    public static String usbFunctionsToString(long j) {
        StringJoiner stringJoiner = new StringJoiner(",");
        if ((4 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_MTP);
        }
        if ((16 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_PTP);
        }
        if ((32 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_RNDIS);
        }
        if ((8 & j) != 0) {
            stringJoiner.add("midi");
        }
        if ((2 & j) != 0) {
            stringJoiner.add("accessory");
        }
        if ((64 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_AUDIO_SOURCE);
        }
        if ((1024 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_NCM);
        }
        if ((128 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_UVC);
        }
        if ((262144 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_SEC_CHARGING);
        }
        if ((4096 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_ACM);
        }
        if ((67108864 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_MBIM);
        }
        if ((8192 & j) != 0) {
            stringJoiner.add("dm");
        }
        if ((1048576 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_DM1);
        }
        if ((2048 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_DIAG);
        }
        if ((8388608 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_DIAG_MDM);
        }
        if ((16777216 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_QDSS);
        }
        if ((33554432 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_QDSS_MDM);
        }
        if ((16384 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_SERIAL_CDEV);
        }
        if ((65536 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_UTS);
        }
        if ((131072 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_RMNET);
        }
        if ((32768 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_DPL);
        }
        if ((524288 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_MASS_STORAGE);
        }
        if ((4194304 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_CONN_GADGET);
        }
        if ((134217728 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_SHUTDOWN);
        }
        if ((j & 1) != 0) {
            stringJoiner.add("adb");
        }
        return stringJoiner.toString();
    }

    public static long usbFunctionsFromString(String str) {
        if (str == null || str.equals("none")) {
            return 0L;
        }
        long j = 0;
        for (String str2 : str.split(",")) {
            Map<String, Long> map = FUNCTION_NAME_TO_CODE;
            if (map.containsKey(str2)) {
                j |= map.get(str2).longValue();
            } else if (str2.length() > 0) {
                Log.d(TAG, "usbFunctionsFromString: Invalid usb functions=" + str);
                return 0L;
            }
        }
        return j;
    }

    public boolean isUsbBlocked() {
        try {
            return this.mService.isUsbBlocked();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in isUsbBlocked", e);
            return false;
        }
    }

    public boolean isSupportDexRestrict() {
        try {
            return this.mService.isSupportDexRestrict();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in isSupportDexRestrict", e);
            return false;
        }
    }

    public int restrictUsbHostInterface(boolean z, String str) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("no permission to call finishMediaUpdate()");
        }
        try {
            return this.mService.restrictUsbHostInterface(z, str);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in restrictUsbHostInterface", e);
            return -1;
        }
    }

    public void setUsbHiddenMenuState(boolean z) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "setUsbHiddenMenuState", new Exception("who's calling?"));
        } else {
            Log.d(TAG, "setUsbHiddenMenuState", new Exception("who's calling?"));
        }
        Log.d(TAG, "setUsbHiddenMenuState: enable=" + z);
        try {
            this.mService.setUsbHiddenMenuState(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String usbGadgetHalVersionToString(int i) {
        if (i == 20) {
            return GADGET_HAL_VERSION_2_0;
        }
        if (i == 12) {
            return GADGET_HAL_VERSION_1_2;
        }
        if (i == 11) {
            return GADGET_HAL_VERSION_1_1;
        }
        if (i == 10) {
            return GADGET_HAL_VERSION_1_0;
        }
        return "unknown";
    }

    public void semGrantDevicePermission(UsbDevice usbDevice, int i) {
        try {
            this.mService.semGrantDevicePermission(usbDevice, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semSetDevicePackage(UsbDevice usbDevice, String str, int i) {
        try {
            this.mService.semSetDevicePackage(usbDevice, str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semSetMode(int i) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "semSetMode", new Exception("who's calling?"));
        } else {
            Log.d(TAG, "semSetMode", new Exception("who's calling?"));
        }
        try {
            this.mService.semSetMode(i);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in semSetMode", e);
        }
    }

    public int semGetPowerRoleStatus() {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "semGetPowerRoleStatus", new Exception("who's calling?"));
        } else {
            Log.d(TAG, "semGetPowerRoleStatus", new Exception("who's calling?"));
        }
        try {
            return this.mService.semGetPowerRoleStatus();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in UsbManager.semGetPowerRoleStatus", e);
            return -1;
        }
    }

    public int semGetDataRoleStatus() {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "semGetDataRoleStatus", new Exception("who's calling?"));
        } else {
            Log.d(TAG, "semGetDataRoleStatus", new Exception("who's calling?"));
        }
        try {
            return this.mService.semGetDataRoleStatus();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in UsbManager.semGetDataRoleStatus", e);
            return -1;
        }
    }
}

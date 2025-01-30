package com.android.server.input;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothHidDeviceAppQosSettings;
import android.bluetooth.BluetoothHidDeviceAppSdpSettings;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.DisplayThread;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.continuity.SemContinuityManager;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class WirelessKeyboardMouseShare {
    public static final int[] CONNECTED_DISCONNECTED_STATES;
    public static final boolean DEBUG;
    public static final boolean SHIP_BUILD;
    public BluetoothAdapter mAdapter;
    public final BluetoothHidDevice.Callback mCallback;
    public final Context mContext;
    public BluetoothDevice mFocusedDevice;
    public final WirelessKeyboardMouseShareHandler mHandler;
    public BluetoothHidDevice mHidDevice;
    public boolean mInitialized;
    public final InputManagerService mInputManager;
    public BluetoothDevice mNextConnectedDevice;
    public BluetoothDevice mPluggedDevice;
    public final BroadcastReceiver mReceiver;
    public final BluetoothProfile.ServiceListener mServiceListener;
    public StatusInfo mStatusInfo;
    public ToastDialog mToastDialog;
    public boolean mReadyToConnect = false;
    public boolean mToLocalTablet = true;
    public int mConnectionState = 0;
    public boolean mAppRegistered = false;
    public String mLastestConnectedName = "";
    public String mTabletName = "";
    public boolean mRegisterAfterRemove = false;
    public boolean mPogoConnected = false;
    public boolean mNeedToTurnOnBT = false;
    public boolean mNotiTurnOnBT = false;
    public boolean mNeedNoti = false;
    public boolean mNeedNotiTablet = false;
    public boolean mFinishNewDevice = false;
    public boolean mUnregisterWhenConnectionFail = false;
    public boolean mDisconnectWithoutUnregister = false;
    public int mRetryNum = 0;
    public final int MAX_DEVICES_NUM = 4;
    public final int MAX_PAIRED_NUM = 3;
    public BluetoothDevice[] mPairedDevices = new BluetoothDevice[4];
    public int mAddIndex = 4;
    public final int ADD_NEW_DEVICE = 2;
    public final int ADD_EXISTED_DEVICE = 1;
    public final int FAIL_ADD = 0;
    public final Map mLogInfos = new HashMap();
    public final String[] REG_ID = {"67261", "67262", "67263"};
    public final String[] CONN_ID = {"67265", "67266", "67267"};
    public final Object innerLock = new Object();
    public Executor mExecutor = new Executor() { // from class: com.android.server.input.WirelessKeyboardMouseShare.1
        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            runnable.run();
        }
    };
    public byte mProtocol = 1;
    public final Mouse mMouse = new Mouse();
    public final Keyboard mKeyboard = new Keyboard();
    public SparseArray mInputReportCache = new SparseArray();
    public SparseArray mOutputReportCache = new SparseArray();

    static {
        boolean equals = "true".equals(SystemProperties.get("ro.product_ship", "false"));
        SHIP_BUILD = equals;
        DEBUG = !equals;
        CONNECTED_DISCONNECTED_STATES = new int[]{0, 2};
    }

    public class ReportData {
        public byte[] data;

        public ReportData() {
        }
    }

    public class StatusInfo {
        public boolean mAppRegistered;
        public int mConnectionState;
        public String mFocusedDeviceToString;
        public String mPluggedDeviceToString;
        public String mPluggedDeviceToStringList;
        public boolean mToLocalTablet;

        public StatusInfo() {
            this.mConnectionState = 0;
            this.mPluggedDeviceToString = "null";
            this.mFocusedDeviceToString = "null";
            this.mPluggedDeviceToStringList = "null";
            this.mToLocalTablet = true;
            this.mAppRegistered = false;
        }

        public void setInfo(int i, BluetoothDevice bluetoothDevice, BluetoothDevice bluetoothDevice2, String str, boolean z, boolean z2) {
            this.mConnectionState = i;
            this.mPluggedDeviceToString = bluetoothDevice == null ? "null" : bluetoothDevice.getAddress();
            this.mFocusedDeviceToString = bluetoothDevice2 != null ? bluetoothDevice2.getAddress() : "null";
            this.mPluggedDeviceToStringList = str;
            this.mToLocalTablet = z;
            this.mAppRegistered = z2;
        }

        public void setInfo(Object obj) {
            if (obj instanceof StatusInfo) {
                StatusInfo statusInfo = (StatusInfo) obj;
                this.mConnectionState = statusInfo.mConnectionState;
                this.mPluggedDeviceToString = statusInfo.mPluggedDeviceToString;
                this.mFocusedDeviceToString = statusInfo.mFocusedDeviceToString;
                this.mPluggedDeviceToStringList = statusInfo.mPluggedDeviceToStringList;
                this.mToLocalTablet = statusInfo.mToLocalTablet;
                this.mAppRegistered = statusInfo.mAppRegistered;
            }
        }

        public int Equals(Object obj) {
            if (!(obj instanceof StatusInfo)) {
                return 0;
            }
            StatusInfo statusInfo = (StatusInfo) obj;
            int i = this.mConnectionState != statusInfo.mConnectionState ? 1 : 0;
            if (!this.mPluggedDeviceToString.equals(statusInfo.mPluggedDeviceToString)) {
                i |= 2;
            }
            if (!this.mFocusedDeviceToString.equals(statusInfo.mFocusedDeviceToString)) {
                i |= 4;
            }
            if (!this.mPluggedDeviceToStringList.equals(statusInfo.mPluggedDeviceToStringList)) {
                i |= 8;
            }
            if (this.mToLocalTablet != statusInfo.mToLocalTablet) {
                i |= 16;
            }
            return this.mAppRegistered != statusInfo.mAppRegistered ? i | 32 : i;
        }
    }

    public final BluetoothDevice isBonedDeviceFromString(String str) {
        for (BluetoothDevice bluetoothDevice : this.mAdapter.getBondedDevices()) {
            Slog.d("WirelessKeyboardMouseShare", "bonded bt device : " + bluetoothDevice.toString());
            if (bluetoothDevice.getAddress().equals(str)) {
                return bluetoothDevice;
            }
        }
        Slog.d("WirelessKeyboardMouseShare", "not exist bonded device : " + str);
        return null;
    }

    public final void initializing() {
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "wireless_keyboard_setting_repository", 0);
        if (TextUtils.isEmpty(stringForUser)) {
            return;
        }
        Slog.d("WirelessKeyboardMouseShare", "initializing : " + stringForUser);
        String[] split = stringForUser.split("/");
        if (!TextUtils.isEmpty(split[2]) || split.length >= 3) {
            String[] split2 = split[2].split(",");
            for (int i = 1; i < 4 && i < split2.length + 1; i++) {
                String str = split2[i - 1];
                if (str == "" || TextUtils.isEmpty(str)) {
                    this.mPairedDevices[i] = null;
                } else {
                    this.mPairedDevices[i] = isBonedDeviceFromString(str);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public WirelessKeyboardMouseShare(Context context, InputManagerService inputManagerService, ToastDialog toastDialog) {
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mInitialized = false;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.input.WirelessKeyboardMouseShare.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                    int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                    if (!WirelessKeyboardMouseShare.this.mInitialized && BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                        if (WirelessKeyboardMouseShare.this.mAdapter == null) {
                            WirelessKeyboardMouseShare.this.mAdapter = BluetoothAdapter.getDefaultAdapter();
                        }
                        Slog.d("WirelessKeyboardMouseShare", "BluetoothAdapter.ACTION_STATE_CHANGED enabled, initailizing");
                        WirelessKeyboardMouseShare.this.initializing();
                        WirelessKeyboardMouseShare.this.sendMessageStatus();
                        WirelessKeyboardMouseShare.this.mInitialized = true;
                    }
                    if (intExtra != 12 || !WirelessKeyboardMouseShare.this.mNeedToTurnOnBT) {
                        if (intExtra == 10) {
                            WirelessKeyboardMouseShare.this.stopHIDDevice();
                            return;
                        }
                        return;
                    } else {
                        WirelessKeyboardMouseShare.this.mNeedToTurnOnBT = false;
                        WirelessKeyboardMouseShare.this.mNotiTurnOnBT = true;
                        WirelessKeyboardMouseShare.this.startHIDDevice();
                        WirelessKeyboardMouseShare.this.sendMessageStatus();
                        return;
                    }
                }
                if ("android.bluetooth.device.action.PAIRING_REQUEST".equals(action)) {
                    WirelessKeyboardMouseShare.this.mToastDialog.dismissAlertDialogWKS();
                }
            }
        };
        this.mReceiver = broadcastReceiver;
        this.mServiceListener = new BluetoothProfile.ServiceListener() { // from class: com.android.server.input.WirelessKeyboardMouseShare.3
            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                if (i != 19) {
                    return;
                }
                Slog.d("WirelessKeyboardMouseShare", "Profile proxy connected");
                WirelessKeyboardMouseShare.this.mHidDevice = (BluetoothHidDevice) bluetoothProfile;
                WirelessKeyboardMouseShare.this.mHandler.obtainMessage(1).sendToTarget();
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceDisconnected(int i) {
                if (i != 19) {
                    return;
                }
                Slog.d("WirelessKeyboardMouseShare", "Profile proxy disconnected");
            }
        };
        this.mCallback = new BluetoothHidDevice.Callback() { // from class: com.android.server.input.WirelessKeyboardMouseShare.4
            @Override // android.bluetooth.BluetoothHidDevice.Callback
            public void onAppStatusChanged(BluetoothDevice bluetoothDevice, boolean z) {
                StringBuilder sb = new StringBuilder();
                sb.append("onAppStatusChanged: pluggedDevice=");
                sb.append(bluetoothDevice == null ? null : bluetoothDevice.toString());
                sb.append(" registered=");
                sb.append(z);
                Slog.d("WirelessKeyboardMouseShare", sb.toString());
                synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                    if (z) {
                        WirelessKeyboardMouseShare.this.mHandler.obtainMessage(5).sendToTarget();
                    } else {
                        WirelessKeyboardMouseShare.this.mPluggedDevice = null;
                        WirelessKeyboardMouseShareHandler wirelessKeyboardMouseShareHandler = WirelessKeyboardMouseShare.this.mHandler;
                        int i = 1;
                        if (!WirelessKeyboardMouseShare.this.setToLocalTablet(true)) {
                            i = 0;
                        }
                        wirelessKeyboardMouseShareHandler.obtainMessage(6, i, 0).sendToTarget();
                    }
                    WirelessKeyboardMouseShare.this.sendMessageStatus();
                }
            }

            @Override // android.bluetooth.BluetoothHidDevice.Callback
            public void onConnectionStateChanged(BluetoothDevice bluetoothDevice, int i) {
                int i2;
                StringBuilder sb = new StringBuilder();
                sb.append("onConnectionStateChanged: device=");
                sb.append(bluetoothDevice == null ? null : bluetoothDevice.toString());
                sb.append(" state=");
                sb.append(i);
                Slog.d("WirelessKeyboardMouseShare", sb.toString());
                synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                    try {
                        if (bluetoothDevice == null) {
                            return;
                        }
                        if (i == 2) {
                            if (WirelessKeyboardMouseShare.this.mAddIndex == 4) {
                                WirelessKeyboardMouseShare wirelessKeyboardMouseShare = WirelessKeyboardMouseShare.this;
                                wirelessKeyboardMouseShare.mAddIndex = wirelessKeyboardMouseShare.getFirstEmptySlot();
                            }
                            WirelessKeyboardMouseShare wirelessKeyboardMouseShare2 = WirelessKeyboardMouseShare.this;
                            i2 = wirelessKeyboardMouseShare2.addPairedDevicesListLocked(bluetoothDevice, wirelessKeyboardMouseShare2.mAddIndex);
                            if (i2 == 0) {
                                WirelessKeyboardMouseShare.this.disconnectBT(bluetoothDevice);
                                bluetoothDevice.removeBond();
                                WirelessKeyboardMouseShare.this.sendMessageStatus();
                                WirelessKeyboardMouseShare wirelessKeyboardMouseShare3 = WirelessKeyboardMouseShare.this;
                                if (wirelessKeyboardMouseShare3.existBluetoothDeviceLocked(wirelessKeyboardMouseShare3.mFocusedDevice)) {
                                    WirelessKeyboardMouseShare wirelessKeyboardMouseShare4 = WirelessKeyboardMouseShare.this;
                                    wirelessKeyboardMouseShare4.sendMessageConnectDeviceLocked(wirelessKeyboardMouseShare4.mFocusedDevice);
                                }
                                return;
                            }
                        } else {
                            i2 = 0;
                        }
                        WirelessKeyboardMouseShare.this.mConnectionState = i;
                        if (i == 2) {
                            WirelessKeyboardMouseShare.this.mHandler.obtainMessage(2).sendToTarget();
                            if (!bluetoothDevice.equals(WirelessKeyboardMouseShare.this.mPluggedDevice)) {
                                WirelessKeyboardMouseShare.this.mPluggedDevice = bluetoothDevice;
                                WirelessKeyboardMouseShare.this.mLastestConnectedName = bluetoothDevice.getName();
                                Slog.d("WirelessKeyboardMouseShare", "mPluggedDevice is " + bluetoothDevice.toString());
                            }
                            WirelessKeyboardMouseShare.this.mFocusedDevice = bluetoothDevice;
                            WirelessKeyboardMouseShare.this.mToLocalTablet = false;
                            WirelessKeyboardMouseShare.this.mHandler.obtainMessage(6, WirelessKeyboardMouseShare.this.mToLocalTablet ? 1 : 0, 0).sendToTarget();
                            WirelessKeyboardMouseShare.this.sendMessageStatus();
                            if (i2 == 2) {
                                WirelessKeyboardMouseShare.this.mFinishNewDevice = true;
                                WirelessKeyboardMouseShare.this.sendMessageMCF();
                            }
                            WirelessKeyboardMouseShare.this.mUnregisterWhenConnectionFail = false;
                            if (WirelessKeyboardMouseShare.this.mHandler.hasMessages(11)) {
                                WirelessKeyboardMouseShare.this.mHandler.removeMessages(11);
                            }
                        } else {
                            if (i == 0) {
                                if (!WirelessKeyboardMouseShare.this.mToLocalTablet) {
                                    WirelessKeyboardMouseShare.this.mKeyboard.clear(false);
                                    WirelessKeyboardMouseShare.this.mMouse.clear(false);
                                }
                                if (!WirelessKeyboardMouseShare.this.mDisconnectWithoutUnregister) {
                                    if (WirelessKeyboardMouseShare.this.mPluggedDevice != null && WirelessKeyboardMouseShare.this.mPluggedDevice.equals(bluetoothDevice)) {
                                        WirelessKeyboardMouseShare.this.stopHIDDevice();
                                    } else {
                                        if (WirelessKeyboardMouseShare.this.mHandler.hasMessages(11)) {
                                            WirelessKeyboardMouseShare.this.mHandler.removeMessages(11);
                                        }
                                        WirelessKeyboardMouseShare.this.mUnregisterWhenConnectionFail = true;
                                        WirelessKeyboardMouseShare.this.mHandler.sendMessageDelayed(WirelessKeyboardMouseShare.this.mHandler.obtainMessage(11), 5000L);
                                    }
                                }
                                WirelessKeyboardMouseShare.this.mDisconnectWithoutUnregister = false;
                                WirelessKeyboardMouseShare.this.mToLocalTablet = true;
                                WirelessKeyboardMouseShare.this.mPluggedDevice = null;
                                WirelessKeyboardMouseShare.this.mHandler.obtainMessage(6, WirelessKeyboardMouseShare.this.mToLocalTablet ? 1 : 0, 0).sendToTarget();
                                WirelessKeyboardMouseShare.this.sendMessageStatus();
                            }
                            WirelessKeyboardMouseShare.this.mProtocol = (byte) 1;
                        }
                        if (CoreRune.IFW_WIRELESS_KEYBOARD_SA_LOGGING && i == 2) {
                            for (int i3 = 1; i3 <= 3; i3++) {
                                BluetoothDevice bluetoothDevice2 = WirelessKeyboardMouseShare.this.mPairedDevices[i3];
                                if (bluetoothDevice2 != null && bluetoothDevice.equals(bluetoothDevice2)) {
                                    if (WirelessKeyboardMouseShare.this.mLogInfos.get(Integer.valueOf(i3)) != null) {
                                        WirelessKeyboardMouseShare.this.mLogInfos.put(Integer.valueOf(i3), Integer.valueOf(((Integer) WirelessKeyboardMouseShare.this.mLogInfos.get(Integer.valueOf(i3))).intValue() + 1));
                                        return;
                                    } else {
                                        WirelessKeyboardMouseShare.this.mLogInfos.put(Integer.valueOf(i3), 1);
                                        return;
                                    }
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.bluetooth.BluetoothHidDevice.Callback
            public void onInterruptData(BluetoothDevice bluetoothDevice, byte b, byte[] bArr) {
                Slog.d("WirelessKeyboardMouseShare", "intr data: reportId=" + ((int) b) + " data=" + Arrays.toString(bArr));
            }

            @Override // android.bluetooth.BluetoothHidDevice.Callback
            public void onSetProtocol(BluetoothDevice bluetoothDevice, byte b) {
                Slog.d("WirelessKeyboardMouseShare", "protocol set to " + ((int) b));
                WirelessKeyboardMouseShare.this.mProtocol = b;
            }

            @Override // android.bluetooth.BluetoothHidDevice.Callback
            public void onVirtualCableUnplug(BluetoothDevice bluetoothDevice) {
                synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                    WirelessKeyboardMouseShare.this.mPluggedDevice = null;
                    Slog.d("WirelessKeyboardMouseShare", "onVirtualCableUnplug mPluggedDevice is null");
                }
            }

            @Override // android.bluetooth.BluetoothHidDevice.Callback
            public void onGetReport(BluetoothDevice bluetoothDevice, byte b, byte b2, int i) {
                if (b == 1) {
                    WirelessKeyboardMouseShare.this.mHandler.obtainMessage(3, b2, i).sendToTarget();
                    return;
                }
                if (b != 2) {
                    Log.v("WirelessKeyboardMouseShare", "onGetReport(), unsupported report type = " + ((int) b));
                    WirelessKeyboardMouseShare.this.mHidDevice.reportError(bluetoothDevice, (byte) 3);
                    return;
                }
                if (b2 != 1) {
                    Log.v("WirelessKeyboardMouseShare", "onGetReport(), output report for invalid id = " + ((int) b2));
                    WirelessKeyboardMouseShare.this.mHidDevice.reportError(bluetoothDevice, (byte) 2);
                    return;
                }
                ReportData reportData = (ReportData) WirelessKeyboardMouseShare.this.mOutputReportCache.get(b2);
                if (reportData == null) {
                    byte[] bArr = new byte[8];
                    Log.v("WirelessKeyboardMouseShare", "get_report id for keyboard");
                    for (int i2 = 0; i2 < 8; i2++) {
                        bArr[i2] = 0;
                    }
                    WirelessKeyboardMouseShare.this.mHidDevice.replyReport(bluetoothDevice, (byte) 2, b2, bArr);
                    return;
                }
                WirelessKeyboardMouseShare.this.mHidDevice.replyReport(bluetoothDevice, (byte) 2, b2, reportData.data);
            }

            @Override // android.bluetooth.BluetoothHidDevice.Callback
            public void onSetReport(BluetoothDevice bluetoothDevice, byte b, byte b2, byte[] bArr) {
                if (b != 2) {
                    Log.v("WirelessKeyboardMouseShare", "onSetReport(), unsupported report type = " + ((int) b));
                    WirelessKeyboardMouseShare.this.mHidDevice.reportError(bluetoothDevice, (byte) 3);
                    return;
                }
                if (b2 == 1) {
                    WirelessKeyboardMouseShare.this.mHandler.obtainMessage(4, b2, 0, ByteBuffer.wrap(bArr)).sendToTarget();
                } else if (b2 == 2) {
                    Log.v("WirelessKeyboardMouseShare", "onSetReport(), mouse report id, sending successful handshake for set report");
                    WirelessKeyboardMouseShare.this.mHidDevice.reportError(bluetoothDevice, (byte) 0);
                } else {
                    WirelessKeyboardMouseShare.this.mHidDevice.reportError(bluetoothDevice, (byte) 2);
                }
            }
        };
        this.mContext = context;
        WirelessKeyboardMouseShareHandler wirelessKeyboardMouseShareHandler = new WirelessKeyboardMouseShareHandler(DisplayThread.get().getLooper());
        this.mHandler = wirelessKeyboardMouseShareHandler;
        this.mInputManager = inputManagerService;
        this.mToastDialog = toastDialog;
        Slog.d("WirelessKeyboardMouseShare", "WirelessKeyboardMouseShare()");
        this.mNextConnectedDevice = null;
        this.mStatusInfo = new StatusInfo();
        if (this.mAdapter == null) {
            this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        Arrays.fill(this.mPairedDevices, (Object) null);
        this.mInitialized = false;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
        context.registerReceiver(broadcastReceiver, intentFilter, null, wirelessKeyboardMouseShareHandler);
    }

    public void setPogoConnected(boolean z) {
        synchronized (this.innerLock) {
            if (this.mPogoConnected != z) {
                this.mPogoConnected = z;
                Slog.d("WirelessKeyboardMouseShare", "setPogoConnected mPogoConnected : " + this.mPogoConnected);
                if (CoreRune.IFW_WIRELESS_KEYBOARD_SA_LOGGING) {
                    CoreSaLogger.logSettingStatusForBasic("67260", z ? "1" : "0");
                }
            }
            if (!z) {
                stopHIDDevice();
            }
        }
    }

    public void sendMessageStatus() {
        this.mHandler.obtainMessage(7).sendToTarget();
    }

    public void sendMessageConnectDeviceLocked(BluetoothDevice bluetoothDevice) {
        this.mNextConnectedDevice = bluetoothDevice;
        if (this.mHandler.hasMessages(9)) {
            return;
        }
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(9), 300L);
    }

    public void sendMessageMCF() {
        this.mHandler.obtainMessage(8, 1, 0).sendToTarget();
    }

    public boolean addDevice(int i) {
        BluetoothDevice bluetoothDevice;
        synchronized (this.innerLock) {
            if (i > 3 || i == 0) {
                Slog.d("WirelessKeyboardMouseShare", "wrong index=" + i);
                return false;
            }
            this.mAddIndex = i;
            if (this.mPairedDevices[i] != null) {
                Slog.d("WirelessKeyboardMouseShare", "not empty slot" + i + " " + this.mPairedDevices[i].toString());
                sendMessageStatus();
                return false;
            }
            if (this.mHidDevice != null && (bluetoothDevice = this.mPluggedDevice) != null && bluetoothDevice.isConnected()) {
                disconnectBTWithoutUnregister(this.mPluggedDevice);
            }
            this.mPairedDevices[i] = null;
            if (!isRegistered()) {
                startHIDDevice();
            }
            this.mUnregisterWhenConnectionFail = true;
            if (this.mHandler.hasMessages(11)) {
                this.mHandler.removeMessages(11);
            }
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(11), 60000L);
            this.mNeedNoti = true;
            sendMessageStatus();
            sendMessageMCF();
            return true;
        }
    }

    public boolean switchDevice(String str, int i) {
        synchronized (this.innerLock) {
            BluetoothDevice findBluetoothDeviceFromString = findBluetoothDeviceFromString(str, i);
            boolean z = i == 0;
            BluetoothAdapter bluetoothAdapter = this.mAdapter;
            if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
                Slog.d("WirelessKeyboardMouseShare", "switchDevice need to turn on bt.");
                return false;
            }
            if (findBluetoothDeviceFromString == null && !z) {
                sendMessageStatus();
                Slog.d("WirelessKeyboardMouseShare", "not find device : " + str);
                return false;
            }
            BluetoothDevice bluetoothDevice = this.mPluggedDevice;
            Slog.d("WirelessKeyboardMouseShare", "switchDevice : " + (bluetoothDevice == null ? "null" : bluetoothDevice.toString()) + " -> " + (findBluetoothDeviceFromString == null ? "null" : findBluetoothDeviceFromString.toString()) + " index:" + i);
            if (!z) {
                if (!isRegistered()) {
                    startHIDDevice();
                }
                BluetoothDevice bluetoothDevice2 = this.mPluggedDevice;
                if (bluetoothDevice2 != null && bluetoothDevice2.getAddress().equals(findBluetoothDeviceFromString.getAddress())) {
                    this.mFocusedDevice = findBluetoothDeviceFromString;
                    this.mToLocalTablet = this.mPluggedDevice == null;
                    sendMessageStatus();
                    return true;
                }
            }
            if (this.mHidDevice != null) {
                BluetoothDevice bluetoothDevice3 = this.mPluggedDevice;
                if (bluetoothDevice3 != null && bluetoothDevice3.isConnected()) {
                    disconnectBTWithoutUnregister(this.mPluggedDevice);
                }
                if (!z) {
                    sendMessageConnectDeviceLocked(findBluetoothDeviceFromString);
                }
            }
            if (!z) {
                this.mFocusedDevice = findBluetoothDeviceFromString;
            }
            this.mToLocalTablet = this.mPluggedDevice == null;
            sendMessageStatus();
            return true;
        }
    }

    public boolean setToLocalTablet(boolean z) {
        String str;
        BluetoothDevice bluetoothDevice;
        BluetoothDevice bluetoothDevice2;
        synchronized (this.innerLock) {
            if (this.mToLocalTablet == z) {
                StringBuilder sb = new StringBuilder();
                sb.append("not changed remote device : ");
                sb.append(this.mToLocalTablet ? "local tablet" : "remote device");
                Slog.d("WirelessKeyboardMouseShare", sb.toString());
                return this.mToLocalTablet;
            }
            BluetoothDevice bluetoothDevice3 = this.mFocusedDevice;
            if ((bluetoothDevice3 == null || !existBluetoothDeviceLocked(bluetoothDevice3)) && !z) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("last connected device is ");
                if (this.mFocusedDevice == null) {
                    str = "null.";
                } else {
                    str = this.mFocusedDevice.toString() + ". but not exist";
                }
                sb2.append(str);
                Slog.d("WirelessKeyboardMouseShare", sb2.toString());
                int i = 1;
                if (getDeviceListSize() == 0) {
                    this.mToLocalTablet = true;
                    return true;
                }
                while (true) {
                    if (i >= 4) {
                        break;
                    }
                    BluetoothDevice bluetoothDevice4 = this.mPairedDevices[i];
                    if (bluetoothDevice4 != null) {
                        this.mFocusedDevice = bluetoothDevice4;
                        Slog.d("WirelessKeyboardMouseShare", "last connected device must be slot[" + i + "] " + this.mFocusedDevice.toString());
                        break;
                    }
                    i++;
                }
            }
            if (z) {
                this.mToLocalTablet = z;
            }
            if (this.mHidDevice != null) {
                if (!z && (bluetoothDevice2 = this.mFocusedDevice) != null) {
                    sendMessageConnectDeviceLocked(bluetoothDevice2);
                } else if (z && (bluetoothDevice = this.mPluggedDevice) != null) {
                    disconnectBT(bluetoothDevice);
                }
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("try switch remote bt ");
            sb3.append(z ? "local tablet" : "remote device");
            BluetoothDevice bluetoothDevice5 = this.mFocusedDevice;
            sb3.append(bluetoothDevice5 == null ? "null" : bluetoothDevice5.toString());
            Slog.d("WirelessKeyboardMouseShare", sb3.toString());
            return this.mToLocalTablet;
        }
    }

    public boolean isToLocalTablet() {
        boolean z;
        synchronized (this.innerLock) {
            z = this.mToLocalTablet;
        }
        return z;
    }

    public void setHostRoleWirelessKeyboardShare() {
        synchronized (this.innerLock) {
            if (isRegistered()) {
                stopHIDDevice();
            }
            this.mUnregisterWhenConnectionFail = false;
            if (this.mHandler.hasMessages(11)) {
                this.mHandler.removeMessages(11);
            }
            Slog.d("WirelessKeyboardMouseShare", "setHostRoleWirelessKeyboardShare : -> Host role(tablet)");
        }
    }

    public void connectByBtDevice(BluetoothDevice bluetoothDevice) {
        synchronized (this.innerLock) {
            BluetoothHidDevice bluetoothHidDevice = this.mHidDevice;
            if (bluetoothHidDevice == null) {
                Slog.d("WirelessKeyboardMouseShare", "connectByBtDevice mHidDevice is null");
            } else if (bluetoothDevice == null) {
                Slog.d("WirelessKeyboardMouseShare", "connectByBtDevice BluetoothDevice is null");
            } else {
                bluetoothHidDevice.connect(bluetoothDevice);
            }
        }
    }

    public String getTabletName() {
        String str;
        synchronized (this.innerLock) {
            str = this.mTabletName;
        }
        return str;
    }

    public String getLastestConnectedName() {
        String str;
        synchronized (this.innerLock) {
            str = this.mLastestConnectedName;
        }
        return str;
    }

    public void sendReadyToConnectIntent() {
        synchronized (this.innerLock) {
            Intent intent = new Intent("com.samsung.android.input.REMOTE_INPUT_READY_TO_CONNECT");
            String str = "";
            if (this.mFinishNewDevice) {
                this.mToastDialog.showToastWKSRegister();
            }
            int i = 0;
            for (int i2 = 1; i2 < 4; i2++) {
                BluetoothDevice bluetoothDevice = this.mPairedDevices[i2];
                String str2 = "";
                if (bluetoothDevice != null) {
                    str2 = bluetoothDevice.getAddress();
                    i++;
                }
                str = i2 == 1 ? str2 : str + "," + str2;
            }
            if (!this.mAppRegistered) {
                this.mReadyToConnect = false;
            } else if (i >= 3) {
                this.mReadyToConnect = false;
            } else {
                this.mReadyToConnect = true;
            }
            intent.setPackage("com.samsung.android.mcfds");
            intent.putExtra("ready", this.mReadyToConnect);
            intent.putExtra("hostlist", str);
            intent.putExtra("finishNewDevice", this.mFinishNewDevice);
            intent.addFlags(16777216);
            this.mContext.sendBroadcast(intent);
            Slog.d("WirelessKeyboardMouseShare", "mReadyToConnect : " + this.mReadyToConnect + "mFinishNewDevice" + this.mFinishNewDevice);
            this.mFinishNewDevice = false;
        }
    }

    public void sendWirelessKeyboardShareStatus() {
        int nearbyDeviceCount;
        synchronized (this.innerLock) {
            StatusInfo statusInfo = new StatusInfo();
            String str = "";
            for (int i = 1; i < 4; i++) {
                BluetoothDevice bluetoothDevice = this.mPairedDevices[i];
                String address = bluetoothDevice != null ? bluetoothDevice.getAddress() : "";
                str = i == 1 ? address : str + "," + address;
            }
            statusInfo.setInfo(this.mConnectionState, this.mPluggedDevice, this.mFocusedDevice, str, isToLocalTablet(), this.mAppRegistered);
            int Equals = this.mStatusInfo.Equals(statusInfo);
            if (Equals != 0 || this.mNotiTurnOnBT || this.mNeedNoti || this.mNeedNotiTablet) {
                if ((Equals & 1) != 0) {
                    this.mInputManager.deliverWirelessKeyboardShareChanged(0, this.mConnectionState == 2 ? "true" : "false", true);
                }
                int i2 = Equals & 2;
                if (i2 != 0) {
                    InputManagerService inputManagerService = this.mInputManager;
                    BluetoothDevice bluetoothDevice2 = this.mPluggedDevice;
                    inputManagerService.deliverWirelessKeyboardShareChanged(1, bluetoothDevice2 == null ? "" : bluetoothDevice2.getAddress(), true);
                }
                if ((Equals & 4) != 0) {
                    InputManagerService inputManagerService2 = this.mInputManager;
                    BluetoothDevice bluetoothDevice3 = this.mFocusedDevice;
                    inputManagerService2.deliverWirelessKeyboardShareChanged(2, bluetoothDevice3 == null ? "" : bluetoothDevice3.getAddress(), true);
                }
                if ((Equals & 8) != 0) {
                    this.mInputManager.deliverWirelessKeyboardShareChanged(3, str, true);
                }
                int i3 = Equals & 16;
                if (i3 != 0) {
                    this.mInputManager.deliverWirelessKeyboardShareChanged(4, isToLocalTablet() ? "true" : "false", true);
                }
                if ((Equals & 32) != 0) {
                    this.mInputManager.deliverWirelessKeyboardShareChanged(5, this.mAppRegistered ? "true" : "false", true);
                }
                boolean z = this.mNeedNoti;
                if (z) {
                    this.mInputManager.deliverWirelessKeyboardShareChanged(7, z ? "true" : "false", true);
                }
                boolean z2 = this.mNeedNotiTablet;
                if (z2) {
                    this.mInputManager.deliverWirelessKeyboardShareChanged(8, z2 ? "true" : "false", true);
                }
                if (i2 != 0) {
                    if (this.mPluggedDevice != null) {
                        this.mToastDialog.showToastWKSDeviceSwitching(this.mContext.getResources().getString(17043401, getLastestConnectedName()));
                    } else {
                        this.mToastDialog.showToastWKSUnregister(this.mContext.getResources().getString(17043402, getLastestConnectedName()));
                    }
                }
                if ((i3 != 0 || this.mNotiTurnOnBT) && isToLocalTablet()) {
                    this.mToastDialog.showToastWKSDeviceSwitching(this.mContext.getResources().getString(17043401, getTabletName()));
                }
                if (this.mNeedNoti) {
                    this.mToastDialog.showToastWKSParingFail(this.mContext.getResources().getString(17043396, getTabletName()));
                    this.mUnregisterWhenConnectionFail = true;
                    if (this.mHandler.hasMessages(11)) {
                        this.mHandler.removeMessages(11);
                    }
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(11), 60000L);
                    SemContinuityManager semContinuityManager = (SemContinuityManager) this.mContext.getSystemService("SemContinuityService");
                    if (semContinuityManager == null) {
                        Log.e("WirelessKeyboardMouseShare", "SemContinuityManager is null");
                        nearbyDeviceCount = 0;
                    } else {
                        nearbyDeviceCount = semContinuityManager.getNearbyDeviceCount(1);
                    }
                    this.mToastDialog.showAlertDialogWKS(this.mContext.getResources().getString(17043399), nearbyDeviceCount == 0 ? this.mContext.getResources().getString(17043400) : this.mContext.getResources().getString(17043397) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + this.mContext.getResources().getString(17043398));
                }
                if (Equals != 0) {
                    this.mStatusInfo.setInfo(statusInfo);
                }
                BluetoothDevice bluetoothDevice4 = this.mPluggedDevice;
                Slog.d("WirelessKeyboardMouseShare", "List : " + str + " p = " + (bluetoothDevice4 == null ? "" : bluetoothDevice4.getAddress()) + ", Noti = " + this.mNeedNoti);
                this.mNotiTurnOnBT = false;
                this.mNeedNoti = false;
                this.mNeedNotiTablet = false;
                Slog.d("WirelessKeyboardMouseShare", "sendWirelessKeyboardShareStatus");
            }
        }
    }

    public void updateWirelessKeyboardShareStatus() {
        synchronized (this.innerLock) {
            String str = "";
            for (int i = 1; i < 4; i++) {
                BluetoothDevice bluetoothDevice = this.mPairedDevices[i];
                String address = bluetoothDevice != null ? bluetoothDevice.getAddress() : "";
                str = i == 1 ? address : str + "," + address;
            }
            this.mInputManager.deliverWirelessKeyboardShareChanged(0, this.mConnectionState == 2 ? "true" : "false", false);
            InputManagerService inputManagerService = this.mInputManager;
            BluetoothDevice bluetoothDevice2 = this.mPluggedDevice;
            inputManagerService.deliverWirelessKeyboardShareChanged(1, bluetoothDevice2 == null ? "" : bluetoothDevice2.getAddress(), false);
            InputManagerService inputManagerService2 = this.mInputManager;
            BluetoothDevice bluetoothDevice3 = this.mFocusedDevice;
            inputManagerService2.deliverWirelessKeyboardShareChanged(2, bluetoothDevice3 == null ? "" : bluetoothDevice3.getAddress(), false);
            this.mInputManager.deliverWirelessKeyboardShareChanged(3, str, false);
            this.mInputManager.deliverWirelessKeyboardShareChanged(4, isToLocalTablet() ? "true" : "false", false);
            this.mInputManager.deliverWirelessKeyboardShareChanged(5, this.mAppRegistered ? "true" : "false", false);
            this.mInputManager.deliverWirelessKeyboardShareChanged(7, this.mNeedNoti ? "true" : "false", false);
            this.mInputManager.deliverWirelessKeyboardShareChanged(8, this.mNeedNotiTablet ? "true" : "false", false);
            Slog.d("WirelessKeyboardMouseShare", "updateWirelessKeyboardShareStatus");
        }
    }

    public boolean isRegistered() {
        boolean z;
        synchronized (this.innerLock) {
            z = this.mAppRegistered;
        }
        return z;
    }

    public boolean switchRemoteDeviceByKey(boolean z) {
        synchronized (this.innerLock) {
            if (z) {
                if (this.mHandler.hasMessages(11)) {
                    this.mHandler.removeMessages(11);
                }
                this.mUnregisterWhenConnectionFail = true;
                this.mHandler.obtainMessage(11).sendToTarget();
                Slog.d("WirelessKeyboardMouseShare", "switchRemoteDevice : -> Host role(tablet)");
            } else {
                BluetoothAdapter bluetoothAdapter = this.mAdapter;
                if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
                    Slog.d("WirelessKeyboardMouseShare", "startHIDDeviceByKey need to turn on bt.");
                    this.mNeedToTurnOnBT = true;
                    this.mAdapter.enable();
                } else {
                    this.mHandler.post(new Runnable() { // from class: com.android.server.input.WirelessKeyboardMouseShare$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            WirelessKeyboardMouseShare.this.lambda$switchRemoteDeviceByKey$0();
                        }
                    });
                    return setToLocalTablet(false);
                }
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchRemoteDeviceByKey$0() {
        this.mUnregisterWhenConnectionFail = true;
        if (this.mHandler.hasMessages(11)) {
            this.mHandler.removeMessages(11);
        }
        Message obtainMessage = this.mHandler.obtainMessage(11);
        if (!isRegistered()) {
            startHIDDevice();
        }
        if (getDeviceListSize() == 0) {
            this.mHandler.sendMessageDelayed(obtainMessage, 60000L);
            this.mNeedNoti = true;
            sendMessageMCF();
        } else {
            this.mHandler.sendMessageDelayed(obtainMessage, 5000L);
        }
        sendMessageStatus();
    }

    public boolean startHIDDevice() {
        synchronized (this.innerLock) {
            if (!this.mPogoConnected) {
                Slog.d("WirelessKeyboardMouseShare", "startHIDDevice mPogoConnected : " + this.mPogoConnected);
                return false;
            }
            if (this.mAdapter == null) {
                this.mAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            BluetoothAdapter bluetoothAdapter = this.mAdapter;
            if (bluetoothAdapter != null) {
                this.mTabletName = bluetoothAdapter.getName();
            }
            registerApp();
            Slog.d("WirelessKeyboardMouseShare", "startHIDDevice mAppRegistered : " + this.mAppRegistered);
            return this.mAppRegistered;
        }
    }

    public boolean stopHIDDevice() {
        boolean z;
        synchronized (this.innerLock) {
            unregisterApp();
            Slog.d("WirelessKeyboardMouseShare", "stopHIDDevice mAppRegistered : " + this.mAppRegistered);
            z = !this.mAppRegistered;
        }
        return z;
    }

    public void changeHIDDevice(String str, int i) {
        BluetoothDevice bluetoothDevice;
        synchronized (this.innerLock) {
            BluetoothDevice findBluetoothDeviceFromString = findBluetoothDeviceFromString(str, i);
            if (findBluetoothDeviceFromString != null && (bluetoothDevice = this.mPluggedDevice) != null) {
                if (findBluetoothDeviceFromString.equals(bluetoothDevice)) {
                    if (this.mHidDevice != null) {
                        disconnectBTWithoutUnregister(this.mPluggedDevice);
                        this.mPluggedDevice.removeBond();
                        this.mUnregisterWhenConnectionFail = true;
                        if (this.mHandler.hasMessages(11)) {
                            this.mHandler.removeMessages(11);
                        }
                        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(11), 60000L);
                    }
                    removePairedDevicesListLocked(str, i);
                    this.mNeedNoti = true;
                    this.mAddIndex = i;
                    sendMessageStatus();
                    sendMessageMCF();
                } else {
                    Slog.d("WirelessKeyboardMouseShare", "warning: request device : " + str + " connected device : " + this.mPluggedDevice.toString());
                }
                return;
            }
            sendMessageStatus();
            StringBuilder sb = new StringBuilder();
            sb.append("not find device : ");
            sb.append(str);
            sb.append(",");
            BluetoothDevice bluetoothDevice2 = this.mPluggedDevice;
            sb.append(bluetoothDevice2 == null ? "" : bluetoothDevice2.toString());
            Slog.d("WirelessKeyboardMouseShare", sb.toString());
        }
    }

    public void removeHIDDevice(String str, int i) {
        synchronized (this.innerLock) {
            BluetoothDevice findBluetoothDeviceFromString = findBluetoothDeviceFromString(str, i);
            if (findBluetoothDeviceFromString == null) {
                sendMessageStatus();
                Slog.d("WirelessKeyboardMouseShare", "not find device : " + str);
                return;
            }
            if (this.mHidDevice != null) {
                BluetoothDevice bluetoothDevice = this.mPluggedDevice;
                if (bluetoothDevice != null && findBluetoothDeviceFromString.equals(bluetoothDevice)) {
                    disconnectBT(this.mPluggedDevice);
                }
                findBluetoothDeviceFromString.removeBond();
            }
            removePairedDevicesListLocked(str, i);
        }
    }

    public final void unregisterApp() {
        synchronized (this.innerLock) {
            Slog.d("WirelessKeyboardMouseShare", "unregisterApp(): mAppRegistered=" + this.mAppRegistered);
            if (this.mHandler.hasMessages(11)) {
                this.mHandler.removeMessages(11);
            }
            if (this.mAppRegistered) {
                BluetoothHidDevice bluetoothHidDevice = this.mHidDevice;
                if (bluetoothHidDevice != null) {
                    bluetoothHidDevice.unregisterApp();
                    this.mAdapter.closeProfileProxy(19, this.mHidDevice);
                }
                this.mAppRegistered = false;
                sendMessageStatus();
            }
        }
    }

    public final void registerApp() {
        synchronized (this.innerLock) {
            StringBuilder sb = new StringBuilder();
            sb.append("registerApp(): mAppRegistered=");
            sb.append(this.mAppRegistered);
            sb.append(" mAdapter=");
            sb.append(this.mAdapter == null ? "NULL" : "OK");
            Slog.d("WirelessKeyboardMouseShare", sb.toString());
            BluetoothAdapter bluetoothAdapter = this.mAdapter;
            if (bluetoothAdapter == null) {
                return;
            }
            if (!this.mAppRegistered && bluetoothAdapter.isEnabled()) {
                if (!this.mAdapter.getProfileProxy(this.mContext, this.mServiceListener, 19)) {
                    Slog.w("WirelessKeyboardMouseShare", "Cannot obtain profile proxy");
                    return;
                }
                this.mAppRegistered = true;
                this.mAdapter.setScanMode(23);
                sendMessageStatus();
            }
        }
    }

    public int getFirstEmptySlot() {
        synchronized (this.innerLock) {
            for (int i = 1; i < 4; i++) {
                if (this.mPairedDevices[i] == null) {
                    return i;
                }
            }
            return 4;
        }
    }

    public final void disconnectBTWithoutUnregister(BluetoothDevice bluetoothDevice) {
        synchronized (this.innerLock) {
            this.mDisconnectWithoutUnregister = true;
            disconnectBT(bluetoothDevice);
        }
    }

    public final void disconnectBT(BluetoothDevice bluetoothDevice) {
        synchronized (this.innerLock) {
            if (this.mHidDevice != null && bluetoothDevice != null) {
                if (bluetoothDevice.equals(this.mPluggedDevice)) {
                    this.mKeyboard.clear(true);
                    this.mMouse.clear(true);
                }
                this.mHidDevice.disconnect(bluetoothDevice);
            }
        }
    }

    public int getDeviceListSize() {
        int i;
        synchronized (this.innerLock) {
            i = 0;
            for (int i2 = 1; i2 < 4; i2++) {
                if (this.mPairedDevices[i2] != null) {
                    i++;
                }
            }
        }
        return i;
    }

    public final boolean existBluetoothDeviceLocked(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return false;
        }
        for (int i = 1; i < 4; i++) {
            BluetoothDevice bluetoothDevice2 = this.mPairedDevices[i];
            if (bluetoothDevice2 != null && bluetoothDevice2.getAddress().equals(bluetoothDevice.getAddress())) {
                return true;
            }
        }
        return false;
    }

    public final BluetoothDevice findBluetoothDeviceFromString(String str, int i) {
        if (i > 3 || i == 0) {
            Slog.d("WirelessKeyboardMouseShare", "wrong index=" + i + " " + str);
            return null;
        }
        BluetoothDevice bluetoothDevice = this.mPairedDevices[i];
        if (bluetoothDevice != null) {
            Slog.d("WirelessKeyboardMouseShare", "find device slot[" + i + "] =  " + bluetoothDevice.toString() + " requested device = " + str);
            return bluetoothDevice;
        }
        for (int i2 = 1; i2 < 4; i2++) {
            BluetoothDevice bluetoothDevice2 = this.mPairedDevices[i2];
            if (bluetoothDevice2 != null && bluetoothDevice2.getAddress().equals(str)) {
                Slog.d("WirelessKeyboardMouseShare", "wrong index=" + i + ", but find device : " + str);
                return bluetoothDevice2;
            }
        }
        Slog.d("WirelessKeyboardMouseShare", "not find device index=" + i + " " + str);
        return null;
    }

    public final int addPairedDevicesListLocked(BluetoothDevice bluetoothDevice, int i) {
        int i2 = 0;
        String str = "";
        for (int i3 = 1; i3 < 4; i3++) {
            BluetoothDevice bluetoothDevice2 = this.mPairedDevices[i3];
            String address = bluetoothDevice2 != null ? bluetoothDevice2.getAddress() : "";
            str = i3 == 1 ? address : str + "," + address;
            if (address.equals(bluetoothDevice.getAddress())) {
                Slog.d("WirelessKeyboardMouseShare", "already exist device slot[" + i3 + "] requested_index=" + i + ", device=" + bluetoothDevice.toString());
                i2 = 1;
            }
        }
        this.mAddIndex = 4;
        if (i2 != 0) {
            return i2;
        }
        if (i > 3 || i == 0) {
            Slog.d("WirelessKeyboardMouseShare", "cannot add list, index=" + i);
            return i2;
        }
        BluetoothDevice bluetoothDevice3 = this.mPairedDevices[i];
        if (bluetoothDevice3 != null) {
            Slog.d("WirelessKeyboardMouseShare", "not empty slot " + i + " " + bluetoothDevice3.toString());
        }
        this.mPairedDevices[i] = bluetoothDevice;
        Slog.d("WirelessKeyboardMouseShare", "add paired device list " + getDeviceListSize() + ": " + str + " : " + bluetoothDevice.toString());
        sendMessageStatus();
        if (!CoreRune.IFW_WIRELESS_KEYBOARD_SA_LOGGING) {
            return 2;
        }
        CoreSaLogger.logSettingStatusForBasic("67264", Integer.toString(getDeviceListSize()));
        CoreSaLogger.logForBasic(this.REG_ID[i - 1], "1 " + this.mPairedDevices[i].getName());
        return 2;
    }

    public final void removePairedDevicesListLocked(String str, int i) {
        if (i > 3 || i == 0) {
            Slog.d("WirelessKeyboardMouseShare", "cannot add list, index=" + i);
            return;
        }
        BluetoothDevice bluetoothDevice = this.mPairedDevices[i];
        if (bluetoothDevice != null && str.equals(bluetoothDevice.getAddress())) {
            this.mPairedDevices[i] = null;
            Slog.d("WirelessKeyboardMouseShare", "remove device=" + str + ", requested_index " + i);
            sendMessageStatus();
            if (CoreRune.IFW_WIRELESS_KEYBOARD_SA_LOGGING) {
                try {
                    int i2 = i - 1;
                    CoreSaLogger.logForBasic(this.REG_ID[i2], "0 " + bluetoothDevice.getName());
                    if (this.mLogInfos.get(Integer.valueOf(i)) != null) {
                        CoreSaLogger.logForBasic(this.CONN_ID[i2], this.mLogInfos.get(Integer.valueOf(i)) + " " + bluetoothDevice.getName());
                        this.mLogInfos.remove(Integer.valueOf(i));
                        return;
                    }
                    return;
                } catch (NullPointerException unused) {
                    Slog.d("WirelessKeyboardMouseShare", "notifySALogging nullpointer exception ");
                    return;
                }
            }
            return;
        }
        String str2 = "";
        for (int i3 = 1; i3 < 4; i3++) {
            BluetoothDevice bluetoothDevice2 = this.mPairedDevices[i3];
            String address = bluetoothDevice2 != null ? bluetoothDevice2.getAddress() : "";
            str2 = i3 == 1 ? address : str2 + "," + address;
            if (address.equals(str)) {
                Slog.d("WirelessKeyboardMouseShare", "exist other slot[" + i3 + "] requested_index=" + i + ", device=" + str);
                this.mPairedDevices[i3] = null;
                sendMessageStatus();
                return;
            }
        }
        Slog.d("WirelessKeyboardMouseShare", "fail to remove " + getDeviceListSize() + " : " + str2 + " : " + str);
        sendMessageStatus();
    }

    public void notifyMouseAciton(int i, float f, float f2, int i2) {
        if (this.mHidDevice == null || !this.mAppRegistered) {
            return;
        }
        if (i == 0) {
            this.mMouse.mouseButtonDownLocked((byte) f, (byte) f2, i2);
            return;
        }
        if (i == 1) {
            this.mMouse.mouseButtonUpLocked((byte) f, (byte) f2, i2);
        } else if (i == 2) {
            this.mMouse.mouseMoveLocked((byte) f, (byte) f2);
        } else {
            if (i != 8) {
                return;
            }
            this.mMouse.mouseScrollLocked((byte) f2);
        }
    }

    public void notifyKeyboardAciton(int i, int i2) {
        if (this.mHidDevice == null || !this.mAppRegistered) {
            return;
        }
        byte convertScancodeToHidKeycode = HidKeycodes.convertScancodeToHidKeycode(i2);
        if (i == 0) {
            this.mKeyboard.keyDownLocked(convertScancodeToHidKeycode);
        } else {
            this.mKeyboard.keyUpLocked(convertScancodeToHidKeycode);
        }
    }

    public final class WirelessKeyboardMouseShareHandler extends Handler {
        public WirelessKeyboardMouseShareHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    try {
                        Slog.d("WirelessKeyboardMouseShare", "registerApp()=" + WirelessKeyboardMouseShare.this.mHidDevice.registerApp(new BluetoothHidDeviceAppSdpSettings("Samsung HID Device", "Samsung HID Keyboard/Mouse", "Samsung", (byte) -64, HidConsts.DESCRIPTOR), new BluetoothHidDeviceAppQosSettings(2, 200, 2, 200, 10000, 10000), new BluetoothHidDeviceAppQosSettings(2, 900, 9, 900, 10000, 10000), WirelessKeyboardMouseShare.this.mExecutor, WirelessKeyboardMouseShare.this.mCallback));
                        return;
                    } catch (Exception unused) {
                        Slog.d("WirelessKeyboardMouseShare", "Can't registerApp");
                        return;
                    }
                case 2:
                    WirelessKeyboardMouseShare.this.mInputReportCache.clear();
                    WirelessKeyboardMouseShare.this.mOutputReportCache.clear();
                    return;
                case 3:
                    byte b = (byte) message.arg1;
                    ReportData reportData = (ReportData) WirelessKeyboardMouseShare.this.mInputReportCache.get(b);
                    synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                        if (reportData != null) {
                            WirelessKeyboardMouseShare.this.mHidDevice.replyReport(WirelessKeyboardMouseShare.this.mPluggedDevice, (byte) 1, b, reportData.data);
                        } else {
                            new ReportData();
                            if (b == 1) {
                                byte[] bArr = new byte[8];
                                Slog.d("WirelessKeyboardMouseShare", "get_report id for keyboard");
                                for (int i = 0; i < 8; i++) {
                                    bArr[i] = 0;
                                }
                                WirelessKeyboardMouseShare.this.storeReportLocked(b, bArr, true);
                                WirelessKeyboardMouseShare.this.mHidDevice.replyReport(WirelessKeyboardMouseShare.this.mPluggedDevice, (byte) 1, b, bArr);
                            } else if (b == 2) {
                                byte[] bArr2 = new byte[4];
                                Slog.d("WirelessKeyboardMouseShare", "get_report id for mouse");
                                for (int i2 = 0; i2 < 4; i2++) {
                                    bArr2[i2] = 0;
                                }
                                WirelessKeyboardMouseShare.this.storeReportLocked(b, bArr2, true);
                                WirelessKeyboardMouseShare.this.mHidDevice.replyReport(WirelessKeyboardMouseShare.this.mPluggedDevice, (byte) 1, b, bArr2);
                            } else {
                                Slog.d("WirelessKeyboardMouseShare", "Get Report for Invalid report id = " + ((int) b));
                                WirelessKeyboardMouseShare.this.mHidDevice.reportError(WirelessKeyboardMouseShare.this.mPluggedDevice, (byte) 2);
                            }
                        }
                    }
                    return;
                case 4:
                    Slog.d("WirelessKeyboardMouseShare", "MESSAGE_SET_REPORT_RECEIVED for id = " + ((int) ((byte) message.arg1)));
                    Slog.d("WirelessKeyboardMouseShare", "onSetReport(), sending successful handshake for set report");
                    WirelessKeyboardMouseShare.this.mHidDevice.reportError(WirelessKeyboardMouseShare.this.mPluggedDevice, (byte) 0);
                    return;
                case 5:
                    Slog.d("WirelessKeyboardMouseShare", "MESSAGE_NEED_TO_INITIALIZING");
                    return;
                case 6:
                    WirelessKeyboardMouseShare.this.mInputManager.switchWirelessKeyboardShare(message.arg1 == 1);
                    return;
                case 7:
                    WirelessKeyboardMouseShare.this.sendWirelessKeyboardShareStatus();
                    return;
                case 8:
                    WirelessKeyboardMouseShare.this.sendReadyToConnectIntent();
                    return;
                case 9:
                    synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                        if (WirelessKeyboardMouseShare.this.mPluggedDevice == null && WirelessKeyboardMouseShare.this.isRegistered()) {
                            if (WirelessKeyboardMouseShare.this.mHidDevice != null && WirelessKeyboardMouseShare.this.mNextConnectedDevice != null && WirelessKeyboardMouseShare.this.mRetryNum < 3) {
                                Slog.d("WirelessKeyboardMouseShare", "will connect " + WirelessKeyboardMouseShare.this.mNextConnectedDevice.toString() + " " + WirelessKeyboardMouseShare.this.mRetryNum);
                                WirelessKeyboardMouseShare.this.mHidDevice.connect(WirelessKeyboardMouseShare.this.mNextConnectedDevice);
                                WirelessKeyboardMouseShare.this.mRetryNum = 0;
                            } else if (WirelessKeyboardMouseShare.this.mRetryNum > 2) {
                                Slog.d("WirelessKeyboardMouseShare", "retry count : " + WirelessKeyboardMouseShare.this.mRetryNum);
                                WirelessKeyboardMouseShare.this.stopHIDDevice();
                                WirelessKeyboardMouseShare.this.mRetryNum = 0;
                            }
                        }
                        WirelessKeyboardMouseShare.this.mRetryNum++;
                        Slog.d("WirelessKeyboardMouseShare", "retry connect " + WirelessKeyboardMouseShare.this.mRetryNum);
                        if (WirelessKeyboardMouseShare.this.mRetryNum < 3) {
                            WirelessKeyboardMouseShare wirelessKeyboardMouseShare = WirelessKeyboardMouseShare.this;
                            wirelessKeyboardMouseShare.sendMessageConnectDeviceLocked(wirelessKeyboardMouseShare.mNextConnectedDevice);
                        } else {
                            WirelessKeyboardMouseShare.this.mRetryNum = 0;
                        }
                    }
                    return;
                case 10:
                    synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                        if (WirelessKeyboardMouseShare.this.mAdapter != null && !WirelessKeyboardMouseShare.this.mAdapter.isEnabled()) {
                            Slog.d("WirelessKeyboardMouseShare", "startHIDDeviceByKey need to turn on bt.");
                            WirelessKeyboardMouseShare.this.mNeedToTurnOnBT = true;
                            WirelessKeyboardMouseShare.this.mAdapter.enable();
                            return;
                        } else if (WirelessKeyboardMouseShare.this.isRegistered() && WirelessKeyboardMouseShare.this.getDeviceListSize() == 0) {
                            WirelessKeyboardMouseShare.this.mNeedNoti = true;
                            WirelessKeyboardMouseShare.this.sendMessageStatus();
                            WirelessKeyboardMouseShare.this.sendMessageMCF();
                            return;
                        } else {
                            if (!WirelessKeyboardMouseShare.this.isRegistered() && WirelessKeyboardMouseShare.this.getDeviceListSize() < 3) {
                                WirelessKeyboardMouseShare.this.mNeedNotiTablet = true;
                            }
                            WirelessKeyboardMouseShare.this.startHIDDevice();
                            return;
                        }
                    }
                case 11:
                    synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                        Slog.d("WirelessKeyboardMouseShare", "unregister by MESSAGE_NEED_HOST_ROLE message");
                        if (WirelessKeyboardMouseShare.this.isRegistered() && WirelessKeyboardMouseShare.this.mUnregisterWhenConnectionFail) {
                            WirelessKeyboardMouseShare.this.stopHIDDevice();
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public class Keyboard {
        public byte[] mBuffer;
        public byte mId;

        public Keyboard() {
            this.mBuffer = new byte[8];
            this.mId = (byte) 1;
        }

        public void clear(boolean z) {
            boolean z2;
            synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                if (!z) {
                    int i = 0;
                    while (true) {
                        if (i >= 8) {
                            z2 = false;
                            break;
                        } else {
                            if (this.mBuffer[i] != 0) {
                                Slog.d("WirelessKeyboardMouseShare", "Keyboard need clear().");
                                z2 = true;
                                break;
                            }
                            i++;
                        }
                    }
                    if (!z2) {
                        return;
                    }
                }
                for (int i2 = 0; i2 < 8; i2++) {
                    this.mBuffer[i2] = 0;
                }
                WirelessKeyboardMouseShare.this.storeReportLocked(this.mId, this.mBuffer, true);
                try {
                    WirelessKeyboardMouseShare.this.mHidDevice.sendReport(WirelessKeyboardMouseShare.this.mPluggedDevice, this.mId, this.mBuffer);
                } catch (NullPointerException unused) {
                    Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
                }
            }
        }

        public void keyDownLocked(byte b) {
            if (WirelessKeyboardMouseShare.this.mConnectionState != 2) {
                return;
            }
            if (b < -16 || b >= -9) {
                int i = 1;
                while (true) {
                    if (i >= 8) {
                        break;
                    }
                    byte[] bArr = this.mBuffer;
                    if (bArr[i] == 0) {
                        bArr[i] = b;
                        break;
                    }
                    i++;
                }
            } else {
                byte[] bArr2 = this.mBuffer;
                bArr2[0] = (byte) ((1 << (b - (-16))) | bArr2[0]);
            }
            WirelessKeyboardMouseShare.this.storeReportLocked(this.mId, this.mBuffer, true);
            try {
                WirelessKeyboardMouseShare.this.mHidDevice.sendReport(WirelessKeyboardMouseShare.this.mPluggedDevice, this.mId, this.mBuffer);
            } catch (NullPointerException unused) {
                Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
            }
        }

        public void keyUpLocked(byte b) {
            if (WirelessKeyboardMouseShare.this.mConnectionState != 2) {
                return;
            }
            if (b < -16 || b >= -9) {
                for (int i = 1; i < 8; i++) {
                    byte[] bArr = this.mBuffer;
                    if (bArr[i] == b) {
                        bArr[i] = 0;
                    }
                }
            } else {
                byte[] bArr2 = this.mBuffer;
                bArr2[0] = (byte) ((~(1 << (b - (-16)))) & bArr2[0]);
            }
            WirelessKeyboardMouseShare.this.storeReportLocked(this.mId, this.mBuffer, true);
            try {
                WirelessKeyboardMouseShare.this.mHidDevice.sendReport(WirelessKeyboardMouseShare.this.mPluggedDevice, this.mId, this.mBuffer);
            } catch (NullPointerException unused) {
                Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
            }
        }
    }

    public class Mouse {
        public byte[] mBuffer;
        public byte mId;

        public Mouse() {
            this.mBuffer = new byte[4];
            this.mId = (byte) 2;
        }

        public void clear(boolean z) {
            synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                if (!z) {
                    if (this.mBuffer[0] == 0) {
                        return;
                    } else {
                        Slog.d("WirelessKeyboardMouseShare", "Mouse need clear().");
                    }
                }
                byte[] bArr = this.mBuffer;
                bArr[0] = 0;
                bArr[1] = 0;
                bArr[2] = 0;
                bArr[3] = 0;
                WirelessKeyboardMouseShare.this.storeReportLocked(this.mId, bArr, true);
                try {
                    WirelessKeyboardMouseShare.this.mHidDevice.sendReport(WirelessKeyboardMouseShare.this.mPluggedDevice, this.mId, this.mBuffer);
                } catch (NullPointerException unused) {
                    Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
                }
            }
        }

        public void mouseMoveLocked(byte b, byte b2) {
            if (WirelessKeyboardMouseShare.this.mConnectionState != 2) {
                return;
            }
            byte[] bArr = this.mBuffer;
            bArr[1] = b;
            bArr[2] = b2;
            WirelessKeyboardMouseShare.this.storeReportLocked(this.mId, bArr, true);
            try {
                WirelessKeyboardMouseShare.this.mHidDevice.sendReport(WirelessKeyboardMouseShare.this.mPluggedDevice, this.mId, this.mBuffer);
            } catch (NullPointerException unused) {
                Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
            }
        }

        public void mouseButtonDownLocked(byte b, byte b2, int i) {
            if (WirelessKeyboardMouseShare.this.mConnectionState != 2) {
                return;
            }
            byte[] bArr = this.mBuffer;
            bArr[0] = (byte) (i | bArr[0]);
            bArr[1] = 0;
            bArr[2] = 0;
            WirelessKeyboardMouseShare.this.storeReportLocked(this.mId, bArr, true);
            try {
                WirelessKeyboardMouseShare.this.mHidDevice.sendReport(WirelessKeyboardMouseShare.this.mPluggedDevice, this.mId, this.mBuffer);
            } catch (NullPointerException unused) {
                Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
            }
        }

        public void mouseButtonUpLocked(byte b, byte b2, int i) {
            if (WirelessKeyboardMouseShare.this.mConnectionState != 2) {
                return;
            }
            byte[] bArr = this.mBuffer;
            bArr[0] = (byte) ((~i) & bArr[0]);
            bArr[1] = 0;
            bArr[2] = 0;
            WirelessKeyboardMouseShare.this.storeReportLocked(this.mId, bArr, true);
            try {
                WirelessKeyboardMouseShare.this.mHidDevice.sendReport(WirelessKeyboardMouseShare.this.mPluggedDevice, this.mId, this.mBuffer);
            } catch (NullPointerException unused) {
                Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
            }
        }

        public void mouseScrollLocked(byte b) {
            if (WirelessKeyboardMouseShare.this.mConnectionState != 2) {
                return;
            }
            byte[] bArr = this.mBuffer;
            bArr[1] = 0;
            bArr[2] = 0;
            bArr[3] = b;
            WirelessKeyboardMouseShare.this.storeReportLocked(this.mId, bArr, true);
            try {
                WirelessKeyboardMouseShare.this.mHidDevice.sendReport(WirelessKeyboardMouseShare.this.mPluggedDevice, this.mId, this.mBuffer);
            } catch (NullPointerException unused) {
                Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
            }
            this.mBuffer[3] = 0;
        }
    }

    public final void storeReportLocked(byte b, byte[] bArr, boolean z) {
        ReportData reportData;
        if (z) {
            reportData = (ReportData) this.mInputReportCache.get(b);
        } else {
            reportData = (ReportData) this.mOutputReportCache.get(b);
        }
        if (reportData == null) {
            reportData = new ReportData();
            if (z) {
                this.mInputReportCache.put(b, reportData);
            } else {
                this.mOutputReportCache.put(b, reportData);
            }
        }
        reportData.data = (byte[]) bArr.clone();
    }

    public void notifySALogging() {
        if (this.mLogInfos.isEmpty()) {
            return;
        }
        this.mLogInfos.entrySet().forEach(new Consumer() { // from class: com.android.server.input.WirelessKeyboardMouseShare$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WirelessKeyboardMouseShare.this.lambda$notifySALogging$1((Map.Entry) obj);
            }
        });
        this.mLogInfos.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifySALogging$1(Map.Entry entry) {
        try {
            int intValue = ((Integer) entry.getKey()).intValue();
            int intValue2 = ((Integer) entry.getValue()).intValue();
            CoreSaLogger.logForBasic(this.CONN_ID[intValue - 1], intValue2 + " " + this.mPairedDevices[intValue].getName());
        } catch (NullPointerException unused) {
            Slog.d("WirelessKeyboardMouseShare", "notifySALogging nullpointer exception");
        }
    }
}

package com.android.server.input;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothHidDeviceAppQosSettings;
import android.bluetooth.BluetoothHidDeviceAppSdpSettings;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.os.SomeArgs;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DisplayThread;

import com.samsung.android.continuity.SemContinuityManager;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.InputRune;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;

public final class WirelessKeyboardMouseShare {
    public final Object innerLock;
    public BluetoothAdapter mAdapter;
    public final AnonymousClass4 mCallback;
    public final Context mContext;
    public BluetoothDevice mFocusedDevice;
    public final WirelessKeyboardMouseShareHandler mHandler;
    public BluetoothHidDevice mHidDevice;
    public boolean mInitialized;
    public final InputManagerService mInputManager;
    public BluetoothDevice mNextConnectedDevice;
    public final BluetoothDevice[] mPairedDevices;
    public BluetoothDevice mPluggedDevice;
    public final AnonymousClass2 mReceiver;
    public final AnonymousClass3 mServiceListener;
    public final StatusInfo mStatusInfo;
    public final ToastDialog mToastDialog;
    public boolean mReadyToConnect = false;
    public boolean mToLocalTablet = true;
    public int mConnectionState = 0;
    public boolean mAppRegistered = false;
    public String mLastestConnectedName = "";
    public String mTabletName = "";
    public boolean mPogoConnected = false;
    public boolean mNeedToTurnOnBT = false;
    public boolean mNotiTurnOnBT = false;
    public boolean mNeedNoti = false;
    public boolean mNeedNotiTablet = false;
    public boolean mFinishNewDevice = false;
    public boolean mUnregisterWhenConnectionFail = false;
    public boolean mDisconnectWithoutUnregister = false;
    public int mRetryNum = 0;
    public int mAddIndex = 4;
    public final Map mLogInfos = new HashMap();
    public final String[] REG_ID = {"67261", "67262", "67263"};
    public final String[] CONN_ID = {"67265", "67266", "67267"};
    public final AnonymousClass1 mExecutor = new AnonymousClass1();
    public final Mouse mMouse = new Mouse(this, 0);
    public final Mouse mKeyboard = new Mouse(this, 1);
    public final SparseArray mInputReportCache = new SparseArray();
    public final SparseArray mOutputReportCache = new SparseArray();

    /* renamed from: com.android.server.input.WirelessKeyboardMouseShare$1, reason: invalid class name */
    public final class AnonymousClass1 implements Executor {
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            runnable.run();
        }
    }

    public final class Mouse {
        public final /* synthetic */ int $r8$classId;
        public final byte[] mBuffer;
        public final /* synthetic */ WirelessKeyboardMouseShare this$0;

        public Mouse(WirelessKeyboardMouseShare wirelessKeyboardMouseShare, int i) {
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = wirelessKeyboardMouseShare;
                    this.mBuffer = new byte[8];
                    break;
                default:
                    this.this$0 = wirelessKeyboardMouseShare;
                    this.mBuffer = new byte[4];
                    break;
            }
        }

        public final void clear(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.this$0.innerLock) {
                        if (!z) {
                            try {
                                if (this.mBuffer[0] == 0) {
                                    return;
                                } else {
                                    Slog.d("WirelessKeyboardMouseShare", "Mouse need clear().");
                                }
                            } finally {
                            }
                        }
                        byte[] bArr = this.mBuffer;
                        bArr[0] = 0;
                        bArr[1] = 0;
                        bArr[2] = 0;
                        bArr[3] = 0;
                        WirelessKeyboardMouseShare.m586$$Nest$mstoreReportLocked(
                                this.this$0, (byte) 2, bArr);
                        try {
                            WirelessKeyboardMouseShare wirelessKeyboardMouseShare = this.this$0;
                            wirelessKeyboardMouseShare.mHidDevice.sendReport(
                                    wirelessKeyboardMouseShare.mPluggedDevice, 2, this.mBuffer);
                        } catch (NullPointerException unused) {
                            Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
                        }
                        return;
                    }
                default:
                    synchronized (this.this$0.innerLock) {
                        if (!z) {
                            for (int i = 0; i < 8; i++) {
                                try {
                                    if (this.mBuffer[i] != 0) {
                                        Slog.d(
                                                "WirelessKeyboardMouseShare",
                                                "Keyboard need clear().");
                                    }
                                } finally {
                                }
                            }
                            return;
                        }
                        for (int i2 = 0; i2 < 8; i2++) {
                            this.mBuffer[i2] = 0;
                        }
                        WirelessKeyboardMouseShare.m586$$Nest$mstoreReportLocked(
                                this.this$0, (byte) 1, this.mBuffer);
                        try {
                            WirelessKeyboardMouseShare wirelessKeyboardMouseShare2 = this.this$0;
                            wirelessKeyboardMouseShare2.mHidDevice.sendReport(
                                    wirelessKeyboardMouseShare2.mPluggedDevice, 1, this.mBuffer);
                        } catch (NullPointerException unused2) {
                            Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
                        }
                        return;
                    }
            }
        }
    }

    public final class ReportData {
        public byte[] data;
    }

    public final class StatusInfo {
        public int mConnectionState = 0;
        public String mPluggedDeviceToString = "null";
        public String mFocusedDeviceToString = "null";
        public String mPluggedDeviceToStringList = "null";
        public boolean mToLocalTablet = true;
        public boolean mAppRegistered = false;

        public final int Equals(StatusInfo statusInfo) {
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

    public final class WirelessKeyboardMouseShareHandler extends Handler {
        public WirelessKeyboardMouseShareHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    BluetoothHidDeviceAppSdpSettings bluetoothHidDeviceAppSdpSettings =
                            new BluetoothHidDeviceAppSdpSettings(
                                    "Samsung HID Device",
                                    "Samsung HID Keyboard/Mouse",
                                    "Samsung",
                                    (byte) -64,
                                    HidConsts.DESCRIPTOR);
                    BluetoothHidDeviceAppQosSettings bluetoothHidDeviceAppQosSettings =
                            new BluetoothHidDeviceAppQosSettings(2, 200, 2, 200, 10000, 10000);
                    BluetoothHidDeviceAppQosSettings bluetoothHidDeviceAppQosSettings2 =
                            new BluetoothHidDeviceAppQosSettings(
                                    2,
                                    FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT,
                                    9,
                                    FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT,
                                    10000,
                                    10000);
                    try {
                        WirelessKeyboardMouseShare wirelessKeyboardMouseShare =
                                WirelessKeyboardMouseShare.this;
                        Slog.d(
                                "WirelessKeyboardMouseShare",
                                "registerApp()="
                                        + wirelessKeyboardMouseShare.mHidDevice.registerApp(
                                                bluetoothHidDeviceAppSdpSettings,
                                                bluetoothHidDeviceAppQosSettings,
                                                bluetoothHidDeviceAppQosSettings2,
                                                wirelessKeyboardMouseShare.mExecutor,
                                                wirelessKeyboardMouseShare.mCallback));
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
                    ReportData reportData =
                            (ReportData) WirelessKeyboardMouseShare.this.mInputReportCache.get(b);
                    synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                        try {
                            if (reportData != null) {
                                WirelessKeyboardMouseShare wirelessKeyboardMouseShare2 =
                                        WirelessKeyboardMouseShare.this;
                                wirelessKeyboardMouseShare2.mHidDevice.replyReport(
                                        wirelessKeyboardMouseShare2.mPluggedDevice,
                                        (byte) 1,
                                        b,
                                        reportData.data);
                            } else if (b == 1) {
                                byte[] bArr = new byte[8];
                                Slog.d("WirelessKeyboardMouseShare", "get_report id for keyboard");
                                for (int i = 0; i < 8; i++) {
                                    bArr[i] = 0;
                                }
                                WirelessKeyboardMouseShare.m586$$Nest$mstoreReportLocked(
                                        WirelessKeyboardMouseShare.this, b, bArr);
                                WirelessKeyboardMouseShare wirelessKeyboardMouseShare3 =
                                        WirelessKeyboardMouseShare.this;
                                wirelessKeyboardMouseShare3.mHidDevice.replyReport(
                                        wirelessKeyboardMouseShare3.mPluggedDevice,
                                        (byte) 1,
                                        b,
                                        bArr);
                            } else if (b == 2) {
                                byte[] bArr2 = new byte[4];
                                Slog.d("WirelessKeyboardMouseShare", "get_report id for mouse");
                                for (int i2 = 0; i2 < 4; i2++) {
                                    bArr2[i2] = 0;
                                }
                                WirelessKeyboardMouseShare.m586$$Nest$mstoreReportLocked(
                                        WirelessKeyboardMouseShare.this, b, bArr2);
                                WirelessKeyboardMouseShare wirelessKeyboardMouseShare4 =
                                        WirelessKeyboardMouseShare.this;
                                wirelessKeyboardMouseShare4.mHidDevice.replyReport(
                                        wirelessKeyboardMouseShare4.mPluggedDevice,
                                        (byte) 1,
                                        b,
                                        bArr2);
                            } else {
                                Slog.d(
                                        "WirelessKeyboardMouseShare",
                                        "Get Report for Invalid report id = " + ((int) b));
                                WirelessKeyboardMouseShare wirelessKeyboardMouseShare5 =
                                        WirelessKeyboardMouseShare.this;
                                wirelessKeyboardMouseShare5.mHidDevice.reportError(
                                        wirelessKeyboardMouseShare5.mPluggedDevice, (byte) 2);
                            }
                        } finally {
                        }
                    }
                    return;
                case 4:
                    Slog.d(
                            "WirelessKeyboardMouseShare",
                            "MESSAGE_SET_REPORT_RECEIVED for id = "
                                    + ((int) ((byte) message.arg1)));
                    Slog.d(
                            "WirelessKeyboardMouseShare",
                            "onSetReport(), sending successful handshake for set report");
                    WirelessKeyboardMouseShare wirelessKeyboardMouseShare6 =
                            WirelessKeyboardMouseShare.this;
                    wirelessKeyboardMouseShare6.mHidDevice.reportError(
                            wirelessKeyboardMouseShare6.mPluggedDevice, (byte) 0);
                    return;
                case 5:
                    Slog.d("WirelessKeyboardMouseShare", "MESSAGE_NEED_TO_INITIALIZING");
                    return;
                case 6:
                    WirelessKeyboardMouseShare.this.mInputManager.switchWirelessKeyboardShare(
                            message.arg1 == 1);
                    return;
                case 7:
                    WirelessKeyboardMouseShare.this.sendWirelessKeyboardShareStatus();
                    return;
                case 8:
                    WirelessKeyboardMouseShare.this.sendReadyToConnectIntent();
                    return;
                case 9:
                    synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                        try {
                            WirelessKeyboardMouseShare wirelessKeyboardMouseShare7 =
                                    WirelessKeyboardMouseShare.this;
                            if (wirelessKeyboardMouseShare7.mPluggedDevice == null
                                    && wirelessKeyboardMouseShare7.isRegistered()) {
                                WirelessKeyboardMouseShare wirelessKeyboardMouseShare8 =
                                        WirelessKeyboardMouseShare.this;
                                if (wirelessKeyboardMouseShare8.mHidDevice != null
                                        && wirelessKeyboardMouseShare8.mNextConnectedDevice != null
                                        && wirelessKeyboardMouseShare8.mRetryNum < 3) {
                                    Slog.d(
                                            "WirelessKeyboardMouseShare",
                                            "will connect "
                                                    + WirelessKeyboardMouseShare.this
                                                            .mNextConnectedDevice.toString()
                                                    + " "
                                                    + WirelessKeyboardMouseShare.this.mRetryNum);
                                    WirelessKeyboardMouseShare wirelessKeyboardMouseShare9 =
                                            WirelessKeyboardMouseShare.this;
                                    wirelessKeyboardMouseShare9.mHidDevice.connect(
                                            wirelessKeyboardMouseShare9.mNextConnectedDevice);
                                    WirelessKeyboardMouseShare.this.mRetryNum = 0;
                                } else if (wirelessKeyboardMouseShare8.mRetryNum > 2) {
                                    Slog.d(
                                            "WirelessKeyboardMouseShare",
                                            "retry count : "
                                                    + WirelessKeyboardMouseShare.this.mRetryNum);
                                    WirelessKeyboardMouseShare.this.stopHIDDevice();
                                    WirelessKeyboardMouseShare.this.mRetryNum = 0;
                                }
                            }
                            WirelessKeyboardMouseShare.this.mRetryNum++;
                            Slog.d(
                                    "WirelessKeyboardMouseShare",
                                    "retry connect " + WirelessKeyboardMouseShare.this.mRetryNum);
                            WirelessKeyboardMouseShare wirelessKeyboardMouseShare10 =
                                    WirelessKeyboardMouseShare.this;
                            if (wirelessKeyboardMouseShare10.mRetryNum < 3) {
                                wirelessKeyboardMouseShare10.sendMessageConnectDeviceLocked(
                                        wirelessKeyboardMouseShare10.mNextConnectedDevice);
                            } else {
                                wirelessKeyboardMouseShare10.mRetryNum = 0;
                            }
                        } finally {
                        }
                    }
                    return;
                case 10:
                    synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                        try {
                            BluetoothAdapter bluetoothAdapter =
                                    WirelessKeyboardMouseShare.this.mAdapter;
                            if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
                                Slog.d(
                                        "WirelessKeyboardMouseShare",
                                        "startHIDDeviceByKey need to turn on bt.");
                                WirelessKeyboardMouseShare wirelessKeyboardMouseShare11 =
                                        WirelessKeyboardMouseShare.this;
                                wirelessKeyboardMouseShare11.mNeedToTurnOnBT = true;
                                wirelessKeyboardMouseShare11.mAdapter.enable();
                                return;
                            }
                            if (WirelessKeyboardMouseShare.this.isRegistered()
                                    && WirelessKeyboardMouseShare.this.getDeviceListSize() == 0) {
                                WirelessKeyboardMouseShare wirelessKeyboardMouseShare12 =
                                        WirelessKeyboardMouseShare.this;
                                wirelessKeyboardMouseShare12.mNeedNoti = true;
                                wirelessKeyboardMouseShare12.sendMessageStatus();
                                WirelessKeyboardMouseShare.this.sendMessageMCF();
                                return;
                            }
                            if (!WirelessKeyboardMouseShare.this.isRegistered()
                                    && WirelessKeyboardMouseShare.this.getDeviceListSize() < 3) {
                                WirelessKeyboardMouseShare.this.mNeedNotiTablet = true;
                            }
                            WirelessKeyboardMouseShare.this.startHIDDevice();
                            return;
                        } finally {
                        }
                    }
                case 11:
                    synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                        try {
                            Slog.d(
                                    "WirelessKeyboardMouseShare",
                                    "unregister by MESSAGE_NEED_HOST_ROLE message");
                            if (WirelessKeyboardMouseShare.this.isRegistered()) {
                                WirelessKeyboardMouseShare wirelessKeyboardMouseShare13 =
                                        WirelessKeyboardMouseShare.this;
                                if (wirelessKeyboardMouseShare13.mUnregisterWhenConnectionFail) {
                                    wirelessKeyboardMouseShare13.stopHIDDevice();
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: -$$Nest$maddPairedDevicesListLocked, reason: not valid java name */
    public static int m585$$Nest$maddPairedDevicesListLocked(
            WirelessKeyboardMouseShare wirelessKeyboardMouseShare,
            BluetoothDevice bluetoothDevice,
            int i) {
        int i2 = 0;
        int i3 = 1;
        String str = "";
        while (i3 < 4) {
            BluetoothDevice bluetoothDevice2 = wirelessKeyboardMouseShare.mPairedDevices[i3];
            String address = bluetoothDevice2 != null ? bluetoothDevice2.getAddress() : "";
            str =
                    i3 == 1
                            ? address
                            : AnyMotionDetector$$ExternalSyntheticOutline0.m(str, ",", address);
            if (address.equals(bluetoothDevice.getAddress())) {
                StringBuilder m =
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i3,
                                i,
                                "already exist device slot[",
                                "] requested_index=",
                                ", device=");
                m.append(bluetoothDevice.toString());
                Slog.d("WirelessKeyboardMouseShare", m.toString());
                i2 = 1;
            }
            i3++;
        }
        wirelessKeyboardMouseShare.mAddIndex = 4;
        if (i2 != 0) {
            return i2;
        }
        if (i > 3 || i == 0) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "cannot add list, index=", "WirelessKeyboardMouseShare");
            return i2;
        }
        BluetoothDevice bluetoothDevice3 = wirelessKeyboardMouseShare.mPairedDevices[i];
        if (bluetoothDevice3 != null) {
            StringBuilder m2 =
                    BatteryService$$ExternalSyntheticOutline0.m(i, "not empty slot ", " ");
            m2.append(bluetoothDevice3.toString());
            Slog.d("WirelessKeyboardMouseShare", m2.toString());
        }
        wirelessKeyboardMouseShare.mPairedDevices[i] = bluetoothDevice;
        Slog.d(
                "WirelessKeyboardMouseShare",
                "add paired device list "
                        + wirelessKeyboardMouseShare.getDeviceListSize()
                        + ": "
                        + str
                        + " : "
                        + bluetoothDevice.toString());
        wirelessKeyboardMouseShare.sendMessageStatus();
        if (InputRune.IFW_WIRELESS_KEYBOARD_SA_LOGGING) {
            CoreSaLogger.logSettingStatusForBasic(
                    "67264", Integer.toString(wirelessKeyboardMouseShare.getDeviceListSize()));
            CoreSaLogger.logForBasic(
                    wirelessKeyboardMouseShare.REG_ID[i - 1],
                    "1 " + wirelessKeyboardMouseShare.mPairedDevices[i].getName());
        }
        return 2;
    }

    /* renamed from: -$$Nest$mstoreReportLocked, reason: not valid java name */
    public static void m586$$Nest$mstoreReportLocked(
            WirelessKeyboardMouseShare wirelessKeyboardMouseShare, byte b, byte[] bArr) {
        ReportData reportData = (ReportData) wirelessKeyboardMouseShare.mInputReportCache.get(b);
        if (reportData == null) {
            reportData = new ReportData();
            wirelessKeyboardMouseShare.mInputReportCache.put(b, reportData);
        }
        reportData.data = (byte[]) bArr.clone();
    }

    static {
        "true".equals(SystemProperties.get("ro.product_ship", "false"));
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.server.input.WirelessKeyboardMouseShare$3] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.server.input.WirelessKeyboardMouseShare$4] */
    public WirelessKeyboardMouseShare(
            Context context,
            InputManagerService inputManagerService,
            ToastDialog toastDialog,
            Object obj) {
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mInitialized = false;
        BluetoothDevice[] bluetoothDeviceArr = new BluetoothDevice[4];
        this.mPairedDevices = bluetoothDeviceArr;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.input.WirelessKeyboardMouseShare.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        ToastDialog toastDialog2;
                        AlertDialog alertDialog;
                        BluetoothDevice bluetoothDevice;
                        String action = intent.getAction();
                        if (!"android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                            if (!"android.bluetooth.device.action.PAIRING_REQUEST".equals(action)
                                    || (alertDialog =
                                                    (toastDialog2 =
                                                                    WirelessKeyboardMouseShare.this
                                                                            .mToastDialog)
                                                            .mAlertDialog)
                                            == null) {
                                return;
                            }
                            alertDialog.dismiss();
                            toastDialog2.mAlertDialog = null;
                            return;
                        }
                        int intExtra =
                                intent.getIntExtra(
                                        "android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                        if (!WirelessKeyboardMouseShare.this.mInitialized
                                && BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                            WirelessKeyboardMouseShare wirelessKeyboardMouseShare =
                                    WirelessKeyboardMouseShare.this;
                            if (wirelessKeyboardMouseShare.mAdapter == null) {
                                wirelessKeyboardMouseShare.mAdapter =
                                        BluetoothAdapter.getDefaultAdapter();
                            }
                            Slog.d(
                                    "WirelessKeyboardMouseShare",
                                    "BluetoothAdapter.ACTION_STATE_CHANGED enabled, initailizing");
                            WirelessKeyboardMouseShare wirelessKeyboardMouseShare2 =
                                    WirelessKeyboardMouseShare.this;
                            String stringForUser =
                                    Settings.Secure.getStringForUser(
                                            wirelessKeyboardMouseShare2.mContext
                                                    .getContentResolver(),
                                            "wireless_keyboard_setting_repository",
                                            0);
                            if (!TextUtils.isEmpty(stringForUser)) {
                                Slog.d(
                                        "WirelessKeyboardMouseShare",
                                        "initializing : " + stringForUser);
                                String[] split = stringForUser.split("/");
                                if (!TextUtils.isEmpty(split[2]) || split.length >= 3) {
                                    String[] split2 = split[2].split(",");
                                    for (int i = 1; i < 4 && i < split2.length + 1; i++) {
                                        String str = split2[i - 1];
                                        if (str == "" || TextUtils.isEmpty(str)) {
                                            wirelessKeyboardMouseShare2.mPairedDevices[i] = null;
                                        } else {
                                            BluetoothDevice[] bluetoothDeviceArr2 =
                                                    wirelessKeyboardMouseShare2.mPairedDevices;
                                            Iterator<BluetoothDevice> it =
                                                    wirelessKeyboardMouseShare2
                                                            .mAdapter
                                                            .getBondedDevices()
                                                            .iterator();
                                            while (true) {
                                                if (!it.hasNext()) {
                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                            .m(
                                                                    "not exist bonded device : ",
                                                                    str,
                                                                    "WirelessKeyboardMouseShare");
                                                    bluetoothDevice = null;
                                                    break;
                                                }
                                                bluetoothDevice = it.next();
                                                Slog.d(
                                                        "WirelessKeyboardMouseShare",
                                                        "bonded bt device : "
                                                                + bluetoothDevice.toString());
                                                if (bluetoothDevice.getAddress().equals(str)) {
                                                    break;
                                                }
                                            }
                                            bluetoothDeviceArr2[i] = bluetoothDevice;
                                        }
                                    }
                                }
                            }
                            WirelessKeyboardMouseShare.this.sendMessageStatus();
                            WirelessKeyboardMouseShare.this.mInitialized = true;
                        }
                        if (intExtra == 12) {
                            WirelessKeyboardMouseShare wirelessKeyboardMouseShare3 =
                                    WirelessKeyboardMouseShare.this;
                            if (wirelessKeyboardMouseShare3.mNeedToTurnOnBT) {
                                wirelessKeyboardMouseShare3.mNeedToTurnOnBT = false;
                                wirelessKeyboardMouseShare3.mNotiTurnOnBT = true;
                                wirelessKeyboardMouseShare3.startHIDDevice();
                                WirelessKeyboardMouseShare.this.sendMessageStatus();
                                return;
                            }
                        }
                        if (intExtra == 10) {
                            WirelessKeyboardMouseShare.this.stopHIDDevice();
                        }
                    }
                };
        this.mServiceListener =
                new BluetoothProfile
                        .ServiceListener() { // from class:
                                             // com.android.server.input.WirelessKeyboardMouseShare.3
                    @Override // android.bluetooth.BluetoothProfile.ServiceListener
                    public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                        if (i != 19) {
                            return;
                        }
                        Slog.d("WirelessKeyboardMouseShare", "Profile proxy connected");
                        WirelessKeyboardMouseShare wirelessKeyboardMouseShare =
                                WirelessKeyboardMouseShare.this;
                        wirelessKeyboardMouseShare.mHidDevice =
                                (BluetoothHidDevice) bluetoothProfile;
                        wirelessKeyboardMouseShare.mHandler.obtainMessage(1).sendToTarget();
                    }

                    @Override // android.bluetooth.BluetoothProfile.ServiceListener
                    public final void onServiceDisconnected(int i) {
                        if (i != 19) {
                            return;
                        }
                        Slog.d("WirelessKeyboardMouseShare", "Profile proxy disconnected");
                    }
                };
        this.mCallback =
                new BluetoothHidDevice.Callback() { // from class:
                    // com.android.server.input.WirelessKeyboardMouseShare.4
                    @Override // android.bluetooth.BluetoothHidDevice.Callback
                    public final void onAppStatusChanged(
                            BluetoothDevice bluetoothDevice, boolean z) {
                        StringBuilder sb = new StringBuilder("onAppStatusChanged: pluggedDevice=");
                        sb.append(bluetoothDevice == null ? null : bluetoothDevice.toString());
                        sb.append(" registered=");
                        sb.append(z);
                        Slog.d("WirelessKeyboardMouseShare", sb.toString());
                        synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                            try {
                                if (z) {
                                    WirelessKeyboardMouseShare.this
                                            .mHandler
                                            .obtainMessage(5)
                                            .sendToTarget();
                                } else {
                                    WirelessKeyboardMouseShare wirelessKeyboardMouseShare =
                                            WirelessKeyboardMouseShare.this;
                                    wirelessKeyboardMouseShare.mPluggedDevice = null;
                                    wirelessKeyboardMouseShare
                                            .mHandler
                                            .obtainMessage(
                                                    6,
                                                    wirelessKeyboardMouseShare.setToLocalTablet(
                                                                    true)
                                                            ? 1
                                                            : 0,
                                                    0)
                                            .sendToTarget();
                                }
                                WirelessKeyboardMouseShare.this.sendMessageStatus();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // android.bluetooth.BluetoothHidDevice.Callback
                    public final void onConnectionStateChanged(
                            BluetoothDevice bluetoothDevice, int i) {
                        int i2;
                        StringBuilder sb = new StringBuilder("onConnectionStateChanged: device=");
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
                                    WirelessKeyboardMouseShare wirelessKeyboardMouseShare =
                                            WirelessKeyboardMouseShare.this;
                                    int i3 = 4;
                                    if (wirelessKeyboardMouseShare.mAddIndex == 4) {
                                        synchronized (wirelessKeyboardMouseShare.innerLock) {
                                            int i4 = 1;
                                            while (true) {
                                                if (i4 >= 4) {
                                                    break;
                                                }
                                                try {
                                                    if (wirelessKeyboardMouseShare
                                                                    .mPairedDevices[i4]
                                                            == null) {
                                                        i3 = i4;
                                                        break;
                                                    }
                                                    i4++;
                                                } finally {
                                                }
                                            }
                                        }
                                        wirelessKeyboardMouseShare.mAddIndex = i3;
                                    }
                                    WirelessKeyboardMouseShare wirelessKeyboardMouseShare2 =
                                            WirelessKeyboardMouseShare.this;
                                    i2 =
                                            WirelessKeyboardMouseShare
                                                    .m585$$Nest$maddPairedDevicesListLocked(
                                                            wirelessKeyboardMouseShare2,
                                                            bluetoothDevice,
                                                            wirelessKeyboardMouseShare2.mAddIndex);
                                    if (i2 == 0) {
                                        WirelessKeyboardMouseShare.this.disconnectBT(
                                                bluetoothDevice);
                                        bluetoothDevice.removeBond();
                                        WirelessKeyboardMouseShare.this.sendMessageStatus();
                                        WirelessKeyboardMouseShare wirelessKeyboardMouseShare3 =
                                                WirelessKeyboardMouseShare.this;
                                        if (wirelessKeyboardMouseShare3.existBluetoothDeviceLocked(
                                                wirelessKeyboardMouseShare3.mFocusedDevice)) {
                                            WirelessKeyboardMouseShare wirelessKeyboardMouseShare4 =
                                                    WirelessKeyboardMouseShare.this;
                                            wirelessKeyboardMouseShare4
                                                    .sendMessageConnectDeviceLocked(
                                                            wirelessKeyboardMouseShare4
                                                                    .mFocusedDevice);
                                        }
                                        return;
                                    }
                                } else {
                                    i2 = 0;
                                }
                                WirelessKeyboardMouseShare wirelessKeyboardMouseShare5 =
                                        WirelessKeyboardMouseShare.this;
                                wirelessKeyboardMouseShare5.mConnectionState = i;
                                if (i == 2) {
                                    wirelessKeyboardMouseShare5
                                            .mHandler
                                            .obtainMessage(2)
                                            .sendToTarget();
                                    if (!bluetoothDevice.equals(
                                            WirelessKeyboardMouseShare.this.mPluggedDevice)) {
                                        WirelessKeyboardMouseShare wirelessKeyboardMouseShare6 =
                                                WirelessKeyboardMouseShare.this;
                                        wirelessKeyboardMouseShare6.mPluggedDevice =
                                                bluetoothDevice;
                                        wirelessKeyboardMouseShare6.mLastestConnectedName =
                                                bluetoothDevice.getName();
                                        Slog.d(
                                                "WirelessKeyboardMouseShare",
                                                "mPluggedDevice is " + bluetoothDevice.toString());
                                    }
                                    WirelessKeyboardMouseShare wirelessKeyboardMouseShare7 =
                                            WirelessKeyboardMouseShare.this;
                                    wirelessKeyboardMouseShare7.mFocusedDevice = bluetoothDevice;
                                    wirelessKeyboardMouseShare7.mToLocalTablet = false;
                                    wirelessKeyboardMouseShare7
                                            .mHandler
                                            .obtainMessage(6, 0, 0)
                                            .sendToTarget();
                                    WirelessKeyboardMouseShare.this.sendMessageStatus();
                                    if (i2 == 2) {
                                        WirelessKeyboardMouseShare wirelessKeyboardMouseShare8 =
                                                WirelessKeyboardMouseShare.this;
                                        wirelessKeyboardMouseShare8.mFinishNewDevice = true;
                                        wirelessKeyboardMouseShare8.sendMessageMCF();
                                    }
                                    WirelessKeyboardMouseShare wirelessKeyboardMouseShare9 =
                                            WirelessKeyboardMouseShare.this;
                                    wirelessKeyboardMouseShare9.mUnregisterWhenConnectionFail =
                                            false;
                                    if (wirelessKeyboardMouseShare9.mHandler.hasMessages(11)) {
                                        WirelessKeyboardMouseShare.this.mHandler.removeMessages(11);
                                    }
                                } else {
                                    if (i == 0) {
                                        if (!wirelessKeyboardMouseShare5.mToLocalTablet) {
                                            wirelessKeyboardMouseShare5.mKeyboard.clear(false);
                                            WirelessKeyboardMouseShare.this.mMouse.clear(false);
                                        }
                                        WirelessKeyboardMouseShare wirelessKeyboardMouseShare10 =
                                                WirelessKeyboardMouseShare.this;
                                        if (!wirelessKeyboardMouseShare10
                                                .mDisconnectWithoutUnregister) {
                                            BluetoothDevice bluetoothDevice2 =
                                                    wirelessKeyboardMouseShare10.mPluggedDevice;
                                            if (bluetoothDevice2 == null
                                                    || !bluetoothDevice2.equals(bluetoothDevice)) {
                                                if (WirelessKeyboardMouseShare.this.mHandler
                                                        .hasMessages(11)) {
                                                    WirelessKeyboardMouseShare.this.mHandler
                                                            .removeMessages(11);
                                                }
                                                WirelessKeyboardMouseShare
                                                        wirelessKeyboardMouseShare11 =
                                                                WirelessKeyboardMouseShare.this;
                                                wirelessKeyboardMouseShare11
                                                                .mUnregisterWhenConnectionFail =
                                                        true;
                                                WirelessKeyboardMouseShare.this.mHandler
                                                        .sendMessageDelayed(
                                                                wirelessKeyboardMouseShare11
                                                                        .mHandler.obtainMessage(11),
                                                                5000L);
                                            } else {
                                                WirelessKeyboardMouseShare.this.stopHIDDevice();
                                            }
                                        }
                                        WirelessKeyboardMouseShare wirelessKeyboardMouseShare12 =
                                                WirelessKeyboardMouseShare.this;
                                        wirelessKeyboardMouseShare12.mDisconnectWithoutUnregister =
                                                false;
                                        wirelessKeyboardMouseShare12.mToLocalTablet = true;
                                        wirelessKeyboardMouseShare12.mPluggedDevice = null;
                                        wirelessKeyboardMouseShare12
                                                .mHandler
                                                .obtainMessage(6, 1, 0)
                                                .sendToTarget();
                                        WirelessKeyboardMouseShare.this.sendMessageStatus();
                                    }
                                    WirelessKeyboardMouseShare.this.getClass();
                                }
                                if (InputRune.IFW_WIRELESS_KEYBOARD_SA_LOGGING && i == 2) {
                                    for (int i5 = 1; i5 <= 3; i5++) {
                                        BluetoothDevice bluetoothDevice3 =
                                                WirelessKeyboardMouseShare.this.mPairedDevices[i5];
                                        if (bluetoothDevice3 != null
                                                && bluetoothDevice.equals(bluetoothDevice3)) {
                                            if (((HashMap)
                                                                    WirelessKeyboardMouseShare.this
                                                                            .mLogInfos)
                                                            .get(Integer.valueOf(i5))
                                                    == null) {
                                                ((HashMap)
                                                                WirelessKeyboardMouseShare.this
                                                                        .mLogInfos)
                                                        .put(Integer.valueOf(i5), 1);
                                                return;
                                            }
                                            ((HashMap) WirelessKeyboardMouseShare.this.mLogInfos)
                                                    .put(
                                                            Integer.valueOf(i5),
                                                            Integer.valueOf(
                                                                    ((Integer)
                                                                                            ((HashMap)
                                                                                                            WirelessKeyboardMouseShare
                                                                                                                    .this
                                                                                                                    .mLogInfos)
                                                                                                    .get(
                                                                                                            Integer
                                                                                                                    .valueOf(
                                                                                                                            i5)))
                                                                                    .intValue()
                                                                            + 1));
                                            return;
                                        }
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // android.bluetooth.BluetoothHidDevice.Callback
                    public final void onGetReport(
                            BluetoothDevice bluetoothDevice, byte b, byte b2, int i) {
                        if (b == 1) {
                            WirelessKeyboardMouseShare.this
                                    .mHandler
                                    .obtainMessage(3, b2, i)
                                    .sendToTarget();
                            return;
                        }
                        if (b != 2) {
                            Log.v(
                                    "WirelessKeyboardMouseShare",
                                    "onGetReport(), unsupported report type = " + ((int) b));
                            WirelessKeyboardMouseShare.this.mHidDevice.reportError(
                                    bluetoothDevice, (byte) 3);
                            return;
                        }
                        if (b2 != 1) {
                            Log.v(
                                    "WirelessKeyboardMouseShare",
                                    "onGetReport(), output report for invalid id = " + ((int) b2));
                            WirelessKeyboardMouseShare.this.mHidDevice.reportError(
                                    bluetoothDevice, (byte) 2);
                            return;
                        }
                        ReportData reportData =
                                (ReportData)
                                        WirelessKeyboardMouseShare.this.mOutputReportCache.get(b2);
                        if (reportData != null) {
                            WirelessKeyboardMouseShare.this.mHidDevice.replyReport(
                                    bluetoothDevice, (byte) 2, b2, reportData.data);
                            return;
                        }
                        byte[] bArr = new byte[8];
                        Log.v("WirelessKeyboardMouseShare", "get_report id for keyboard");
                        for (int i2 = 0; i2 < 8; i2++) {
                            bArr[i2] = 0;
                        }
                        WirelessKeyboardMouseShare.this.mHidDevice.replyReport(
                                bluetoothDevice, (byte) 2, b2, bArr);
                    }

                    @Override // android.bluetooth.BluetoothHidDevice.Callback
                    public final void onInterruptData(
                            BluetoothDevice bluetoothDevice, byte b, byte[] bArr) {
                        StringBuilder m =
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        b, "intr data: reportId=", " data=");
                        m.append(Arrays.toString(bArr));
                        Slog.d("WirelessKeyboardMouseShare", m.toString());
                    }

                    @Override // android.bluetooth.BluetoothHidDevice.Callback
                    public final void onSetProtocol(BluetoothDevice bluetoothDevice, byte b) {
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                b, "protocol set to ", "WirelessKeyboardMouseShare");
                        WirelessKeyboardMouseShare.this.getClass();
                    }

                    @Override // android.bluetooth.BluetoothHidDevice.Callback
                    public final void onSetReport(
                            BluetoothDevice bluetoothDevice, byte b, byte b2, byte[] bArr) {
                        if (b != 2) {
                            Log.v(
                                    "WirelessKeyboardMouseShare",
                                    "onSetReport(), unsupported report type = " + ((int) b));
                            WirelessKeyboardMouseShare.this.mHidDevice.reportError(
                                    bluetoothDevice, (byte) 3);
                            return;
                        }
                        if (b2 == 1) {
                            WirelessKeyboardMouseShare.this
                                    .mHandler
                                    .obtainMessage(4, b2, 0, ByteBuffer.wrap(bArr))
                                    .sendToTarget();
                        } else if (b2 != 2) {
                            WirelessKeyboardMouseShare.this.mHidDevice.reportError(
                                    bluetoothDevice, (byte) 2);
                        } else {
                            Log.v(
                                    "WirelessKeyboardMouseShare",
                                    "onSetReport(), mouse report id, sending successful handshake"
                                        + " for set report");
                            WirelessKeyboardMouseShare.this.mHidDevice.reportError(
                                    bluetoothDevice, (byte) 0);
                        }
                    }

                    @Override // android.bluetooth.BluetoothHidDevice.Callback
                    public final void onVirtualCableUnplug(BluetoothDevice bluetoothDevice) {
                        synchronized (WirelessKeyboardMouseShare.this.innerLock) {
                            WirelessKeyboardMouseShare.this.mPluggedDevice = null;
                            Slog.d(
                                    "WirelessKeyboardMouseShare",
                                    "onVirtualCableUnplug mPluggedDevice is null");
                        }
                    }
                };
        this.mContext = context;
        WirelessKeyboardMouseShareHandler wirelessKeyboardMouseShareHandler =
                new WirelessKeyboardMouseShareHandler(DisplayThread.get().getLooper());
        this.mHandler = wirelessKeyboardMouseShareHandler;
        this.innerLock = obj;
        this.mInputManager = inputManagerService;
        this.mToastDialog = toastDialog;
        Slog.d("WirelessKeyboardMouseShare", "WirelessKeyboardMouseShare()");
        this.mNextConnectedDevice = null;
        this.mStatusInfo = new StatusInfo();
        if (this.mAdapter == null) {
            this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        Arrays.fill(bluetoothDeviceArr, (Object) null);
        this.mInitialized = false;
        context.registerReceiver(
                broadcastReceiver,
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "android.bluetooth.adapter.action.STATE_CHANGED",
                        "android.bluetooth.device.action.PAIRING_REQUEST"),
                null,
                wirelessKeyboardMouseShareHandler);
    }

    public final boolean addDevice(int i) {
        BluetoothDevice bluetoothDevice;
        synchronized (this.innerLock) {
            try {
                if (i > 3 || i == 0) {
                    Slog.d("WirelessKeyboardMouseShare", "wrong index=" + i);
                    return false;
                }
                this.mAddIndex = i;
                if (this.mPairedDevices[i] != null) {
                    Slog.d(
                            "WirelessKeyboardMouseShare",
                            "not empty slot" + i + " " + this.mPairedDevices[i].toString());
                    sendMessageStatus();
                    return false;
                }
                if (this.mHidDevice != null
                        && (bluetoothDevice = this.mPluggedDevice) != null
                        && bluetoothDevice.isConnected()) {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void changeHIDDevice(int i, String str) {
        BluetoothDevice bluetoothDevice;
        synchronized (this.innerLock) {
            try {
                BluetoothDevice findBluetoothDeviceFromString =
                        findBluetoothDeviceFromString(i, str);
                if (findBluetoothDeviceFromString != null
                        && (bluetoothDevice = this.mPluggedDevice) != null) {
                    if (findBluetoothDeviceFromString.equals(bluetoothDevice)) {
                        if (this.mHidDevice != null) {
                            disconnectBTWithoutUnregister(this.mPluggedDevice);
                            this.mPluggedDevice.removeBond();
                            this.mUnregisterWhenConnectionFail = true;
                            if (this.mHandler.hasMessages(11)) {
                                this.mHandler.removeMessages(11);
                            }
                            this.mHandler.sendMessageDelayed(
                                    this.mHandler.obtainMessage(11), 60000L);
                        }
                        removePairedDevicesListLocked(i, str);
                        this.mNeedNoti = true;
                        this.mAddIndex = i;
                        sendMessageStatus();
                        sendMessageMCF();
                    } else {
                        Slog.d(
                                "WirelessKeyboardMouseShare",
                                "warning: request device : "
                                        + str
                                        + " connected device : "
                                        + this.mPluggedDevice.toString());
                    }
                    return;
                }
                sendMessageStatus();
                StringBuilder sb = new StringBuilder("not find device : ");
                sb.append(str);
                sb.append(",");
                BluetoothDevice bluetoothDevice2 = this.mPluggedDevice;
                sb.append(bluetoothDevice2 == null ? "" : bluetoothDevice2.toString());
                Slog.d("WirelessKeyboardMouseShare", sb.toString());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void connectByBtDevice(BluetoothDevice bluetoothDevice) {
        synchronized (this.innerLock) {
            try {
                BluetoothHidDevice bluetoothHidDevice = this.mHidDevice;
                if (bluetoothHidDevice == null) {
                    Slog.d("WirelessKeyboardMouseShare", "connectByBtDevice mHidDevice is null");
                } else if (bluetoothDevice == null) {
                    Slog.d(
                            "WirelessKeyboardMouseShare",
                            "connectByBtDevice BluetoothDevice is null");
                } else {
                    bluetoothHidDevice.connect(bluetoothDevice);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void disconnectBT(BluetoothDevice bluetoothDevice) {
        synchronized (this.innerLock) {
            try {
                if (this.mHidDevice != null && bluetoothDevice != null) {
                    if (bluetoothDevice.equals(this.mPluggedDevice)) {
                        this.mKeyboard.clear(true);
                        this.mMouse.clear(true);
                    }
                    this.mHidDevice.disconnect(bluetoothDevice);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void disconnectBTWithoutUnregister(BluetoothDevice bluetoothDevice) {
        synchronized (this.innerLock) {
            this.mDisconnectWithoutUnregister = true;
            disconnectBT(bluetoothDevice);
        }
    }

    public final boolean existBluetoothDeviceLocked(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return false;
        }
        for (int i = 1; i < 4; i++) {
            BluetoothDevice bluetoothDevice2 = this.mPairedDevices[i];
            if (bluetoothDevice2 != null
                    && bluetoothDevice2.getAddress().equals(bluetoothDevice.getAddress())) {
                return true;
            }
        }
        return false;
    }

    public final BluetoothDevice findBluetoothDeviceFromString(int i, String str) {
        if (i > 3 || i == 0) {
            Slog.d("WirelessKeyboardMouseShare", "wrong index=" + i + " " + str);
            return null;
        }
        BluetoothDevice bluetoothDevice = this.mPairedDevices[i];
        if (bluetoothDevice != null) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(i, "find device slot[", "] =  ");
            m.append(bluetoothDevice.toString());
            m.append(" requested device = ");
            m.append(str);
            Slog.d("WirelessKeyboardMouseShare", m.toString());
            return bluetoothDevice;
        }
        for (int i2 = 1; i2 < 4; i2++) {
            BluetoothDevice bluetoothDevice2 = this.mPairedDevices[i2];
            if (bluetoothDevice2 != null && bluetoothDevice2.getAddress().equals(str)) {
                Slog.d(
                        "WirelessKeyboardMouseShare",
                        "wrong index=" + i + ", but find device : " + str);
                return bluetoothDevice2;
            }
        }
        Slog.d("WirelessKeyboardMouseShare", "not find device index=" + i + " " + str);
        return null;
    }

    public final int getDeviceListSize() {
        int i;
        synchronized (this.innerLock) {
            i = 0;
            for (int i2 = 1; i2 < 4; i2++) {
                try {
                    if (this.mPairedDevices[i2] != null) {
                        i++;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return i;
    }

    public final boolean isRegistered() {
        boolean z;
        synchronized (this.innerLock) {
            z = this.mAppRegistered;
        }
        return z;
    }

    public final boolean isToLocalTablet() {
        boolean z;
        synchronized (this.innerLock) {
            z = this.mToLocalTablet;
        }
        return z;
    }

    public final void notifyKeyboardAciton(int i, int i2) {
        byte b;
        if (this.mHidDevice == null || !this.mAppRegistered) {
            return;
        }
        if (i2 >= 0 && i2 <= 226) {
            b = HidKeycodes.ScancodeToHidKeycode[i2];
        } else if (i2 == 254) {
            b = 104;
        } else if (i2 == 701) {
            b = 109;
        } else if (i2 == 705) {
            b = 105;
        } else if (i2 == 706) {
            b = 106;
        } else if (i2 == 709) {
            b = 112;
        } else if (i2 != 710) {
            switch (i2) {
                case 757:
                    b = 113;
                    break;
                case 758:
                    b = 114;
                    break;
                case 759:
                    b = 115;
                    break;
                default:
                    b = 0;
                    break;
            }
        } else {
            b = 108;
        }
        Mouse mouse = this.mKeyboard;
        if (i == 0) {
            WirelessKeyboardMouseShare wirelessKeyboardMouseShare = mouse.this$0;
            if (wirelessKeyboardMouseShare.mConnectionState != 2) {
                return;
            }
            byte[] bArr = mouse.mBuffer;
            if (b < -16 || b >= -9) {
                int i3 = 1;
                while (true) {
                    if (i3 < 8) {
                        if (bArr[i3] == 0) {
                            bArr[i3] = b;
                        } else {
                            i3++;
                        }
                    }
                }
            } else {
                bArr[0] = (byte) ((1 << (b - (-16))) | bArr[0]);
            }
            m586$$Nest$mstoreReportLocked(wirelessKeyboardMouseShare, (byte) 1, bArr);
            try {
                wirelessKeyboardMouseShare.mHidDevice.sendReport(
                        wirelessKeyboardMouseShare.mPluggedDevice, 1, bArr);
                return;
            } catch (NullPointerException unused) {
                Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
                return;
            }
        }
        WirelessKeyboardMouseShare wirelessKeyboardMouseShare2 = mouse.this$0;
        if (wirelessKeyboardMouseShare2.mConnectionState != 2) {
            return;
        }
        byte[] bArr2 = mouse.mBuffer;
        if (b < -16 || b >= -9) {
            for (int i4 = 1; i4 < 8; i4++) {
                if (bArr2[i4] == b) {
                    bArr2[i4] = 0;
                }
            }
        } else {
            bArr2[0] = (byte) ((~(1 << (b - (-16)))) & bArr2[0]);
        }
        m586$$Nest$mstoreReportLocked(wirelessKeyboardMouseShare2, (byte) 1, bArr2);
        try {
            wirelessKeyboardMouseShare2.mHidDevice.sendReport(
                    wirelessKeyboardMouseShare2.mPluggedDevice, 1, bArr2);
        } catch (NullPointerException unused2) {
            Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
        }
    }

    public final void notifyMouseAciton(int i, float f, float f2, int i2) {
        if (this.mHidDevice == null || !this.mAppRegistered) {
            return;
        }
        Mouse mouse = this.mMouse;
        if (i == 0) {
            WirelessKeyboardMouseShare wirelessKeyboardMouseShare = mouse.this$0;
            if (wirelessKeyboardMouseShare.mConnectionState != 2) {
                return;
            }
            byte[] bArr = mouse.mBuffer;
            bArr[0] = (byte) (bArr[0] | i2);
            bArr[1] = 0;
            bArr[2] = 0;
            m586$$Nest$mstoreReportLocked(wirelessKeyboardMouseShare, (byte) 2, bArr);
            try {
                wirelessKeyboardMouseShare.mHidDevice.sendReport(
                        wirelessKeyboardMouseShare.mPluggedDevice, 2, bArr);
                return;
            } catch (NullPointerException unused) {
                Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
                return;
            }
        }
        if (i == 1) {
            WirelessKeyboardMouseShare wirelessKeyboardMouseShare2 = mouse.this$0;
            if (wirelessKeyboardMouseShare2.mConnectionState != 2) {
                return;
            }
            byte[] bArr2 = mouse.mBuffer;
            bArr2[0] = (byte) (bArr2[0] & (~i2));
            bArr2[1] = 0;
            bArr2[2] = 0;
            m586$$Nest$mstoreReportLocked(wirelessKeyboardMouseShare2, (byte) 2, bArr2);
            try {
                wirelessKeyboardMouseShare2.mHidDevice.sendReport(
                        wirelessKeyboardMouseShare2.mPluggedDevice, 2, bArr2);
                return;
            } catch (NullPointerException unused2) {
                Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
                return;
            }
        }
        if (i == 2) {
            byte b = (byte) f;
            byte b2 = (byte) f2;
            WirelessKeyboardMouseShare wirelessKeyboardMouseShare3 = mouse.this$0;
            if (wirelessKeyboardMouseShare3.mConnectionState != 2) {
                return;
            }
            byte[] bArr3 = mouse.mBuffer;
            bArr3[1] = b;
            bArr3[2] = b2;
            m586$$Nest$mstoreReportLocked(wirelessKeyboardMouseShare3, (byte) 2, bArr3);
            try {
                wirelessKeyboardMouseShare3.mHidDevice.sendReport(
                        wirelessKeyboardMouseShare3.mPluggedDevice, 2, bArr3);
                return;
            } catch (NullPointerException unused3) {
                Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
                return;
            }
        }
        if (i != 8) {
            return;
        }
        byte b3 = (byte) f2;
        WirelessKeyboardMouseShare wirelessKeyboardMouseShare4 = mouse.this$0;
        if (wirelessKeyboardMouseShare4.mConnectionState != 2) {
            return;
        }
        byte[] bArr4 = mouse.mBuffer;
        bArr4[1] = 0;
        bArr4[2] = 0;
        bArr4[3] = b3;
        m586$$Nest$mstoreReportLocked(wirelessKeyboardMouseShare4, (byte) 2, bArr4);
        try {
            wirelessKeyboardMouseShare4.mHidDevice.sendReport(
                    wirelessKeyboardMouseShare4.mPluggedDevice, 2, bArr4);
        } catch (NullPointerException unused4) {
            Slog.d("WirelessKeyboardMouseShare", "ignore nullpointer exception ");
        }
        bArr4[3] = 0;
    }

    public final void registerApp() {
        synchronized (this.innerLock) {
            try {
                StringBuilder sb = new StringBuilder("registerApp(): mAppRegistered=");
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
            } finally {
            }
        }
    }

    public final void removeHIDDevice(int i, String str) {
        synchronized (this.innerLock) {
            try {
                BluetoothDevice findBluetoothDeviceFromString =
                        findBluetoothDeviceFromString(i, str);
                if (findBluetoothDeviceFromString == null) {
                    sendMessageStatus();
                    Slog.d("WirelessKeyboardMouseShare", "not find device : " + str);
                    return;
                }
                if (this.mHidDevice != null) {
                    BluetoothDevice bluetoothDevice = this.mPluggedDevice;
                    if (bluetoothDevice != null
                            && findBluetoothDeviceFromString.equals(bluetoothDevice)) {
                        disconnectBT(this.mPluggedDevice);
                    }
                    findBluetoothDeviceFromString.removeBond();
                }
                removePairedDevicesListLocked(i, str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removePairedDevicesListLocked(int i, String str) {
        if (i > 3 || i == 0) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "cannot add list, index=", "WirelessKeyboardMouseShare");
            return;
        }
        BluetoothDevice bluetoothDevice = this.mPairedDevices[i];
        if (bluetoothDevice == null || !str.equals(bluetoothDevice.getAddress())) {
            String str2 = "";
            int i2 = 1;
            while (i2 < 4) {
                BluetoothDevice bluetoothDevice2 = this.mPairedDevices[i2];
                String address = bluetoothDevice2 != null ? bluetoothDevice2.getAddress() : "";
                str2 =
                        i2 == 1
                                ? address
                                : AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                        str2, ",", address);
                if (address.equals(str)) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            ArrayUtils$$ExternalSyntheticOutline0.m(
                                    i2, i, "exist other slot[", "] requested_index=", ", device="),
                            str,
                            "WirelessKeyboardMouseShare");
                    this.mPairedDevices[i2] = null;
                    sendMessageStatus();
                    return;
                }
                i2++;
            }
            Slog.d(
                    "WirelessKeyboardMouseShare",
                    "fail to remove " + getDeviceListSize() + " : " + str2 + " : " + str);
            sendMessageStatus();
            return;
        }
        this.mPairedDevices[i] = null;
        Slog.d("WirelessKeyboardMouseShare", "remove device=" + str + ", requested_index " + i);
        sendMessageStatus();
        if (InputRune.IFW_WIRELESS_KEYBOARD_SA_LOGGING) {
            try {
                int i3 = i - 1;
                CoreSaLogger.logForBasic(this.REG_ID[i3], "0 " + bluetoothDevice.getName());
                if (((HashMap) this.mLogInfos).get(Integer.valueOf(i)) != null) {
                    CoreSaLogger.logForBasic(
                            this.CONN_ID[i3],
                            ((HashMap) this.mLogInfos).get(Integer.valueOf(i))
                                    + " "
                                    + bluetoothDevice.getName());
                    ((HashMap) this.mLogInfos).remove(Integer.valueOf(i));
                }
            } catch (NullPointerException unused) {
                Slog.d("WirelessKeyboardMouseShare", "notifySALogging nullpointer exception ");
            }
        }
    }

    public final void sendMessageConnectDeviceLocked(BluetoothDevice bluetoothDevice) {
        this.mNextConnectedDevice = bluetoothDevice;
        WirelessKeyboardMouseShareHandler wirelessKeyboardMouseShareHandler = this.mHandler;
        if (wirelessKeyboardMouseShareHandler.hasMessages(9)) {
            return;
        }
        wirelessKeyboardMouseShareHandler.sendMessageDelayed(
                wirelessKeyboardMouseShareHandler.obtainMessage(9), 300L);
    }

    public final void sendMessageMCF() {
        this.mHandler.obtainMessage(8, 1, 0).sendToTarget();
    }

    public final void sendMessageStatus() {
        this.mHandler.obtainMessage(7).sendToTarget();
    }

    public final void sendReadyToConnectIntent() {
        synchronized (this.innerLock) {
            try {
                Intent intent =
                        new Intent("com.samsung.android.input.REMOTE_INPUT_READY_TO_CONNECT");
                String str = "";
                if (this.mFinishNewDevice) {
                    this.mToastDialog.mHandler.removeMessages(1);
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
                Slog.d(
                        "WirelessKeyboardMouseShare",
                        "mReadyToConnect : "
                                + this.mReadyToConnect
                                + "mFinishNewDevice"
                                + this.mFinishNewDevice);
                this.mFinishNewDevice = false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendWirelessKeyboardShareStatus() {
        String str;
        String str2;
        int nearbyDeviceCount;
        String str3;
        String str4;
        synchronized (this.innerLock) {
            try {
                StatusInfo statusInfo = new StatusInfo();
                String str5 = "";
                for (int i = 1; i < 4; i++) {
                    BluetoothDevice bluetoothDevice = this.mPairedDevices[i];
                    String address = bluetoothDevice != null ? bluetoothDevice.getAddress() : "";
                    str5 = i == 1 ? address : str5 + "," + address;
                }
                int i2 = this.mConnectionState;
                BluetoothDevice bluetoothDevice2 = this.mPluggedDevice;
                BluetoothDevice bluetoothDevice3 = this.mFocusedDevice;
                boolean isToLocalTablet = isToLocalTablet();
                boolean z = this.mAppRegistered;
                statusInfo.mConnectionState = i2;
                String str6 = "null";
                statusInfo.mPluggedDeviceToString =
                        bluetoothDevice2 == null ? "null" : bluetoothDevice2.getAddress();
                if (bluetoothDevice3 != null) {
                    str6 = bluetoothDevice3.getAddress();
                }
                statusInfo.mFocusedDeviceToString = str6;
                statusInfo.mPluggedDeviceToStringList = str5;
                statusInfo.mToLocalTablet = isToLocalTablet;
                statusInfo.mAppRegistered = z;
                int Equals = this.mStatusInfo.Equals(statusInfo);
                if (Equals != 0 || this.mNotiTurnOnBT || this.mNeedNoti || this.mNeedNotiTablet) {
                    if ((Equals & 1) != 0) {
                        this.mInputManager.deliverWirelessKeyboardShareChanged(
                                0, this.mConnectionState == 2 ? "true" : "false", true);
                    }
                    int i3 = Equals & 2;
                    if (i3 != 0) {
                        InputManagerService inputManagerService = this.mInputManager;
                        BluetoothDevice bluetoothDevice4 = this.mPluggedDevice;
                        inputManagerService.deliverWirelessKeyboardShareChanged(
                                1,
                                bluetoothDevice4 == null ? "" : bluetoothDevice4.getAddress(),
                                true);
                    }
                    if ((Equals & 4) != 0) {
                        InputManagerService inputManagerService2 = this.mInputManager;
                        BluetoothDevice bluetoothDevice5 = this.mFocusedDevice;
                        inputManagerService2.deliverWirelessKeyboardShareChanged(
                                2,
                                bluetoothDevice5 == null ? "" : bluetoothDevice5.getAddress(),
                                true);
                    }
                    if ((Equals & 8) != 0) {
                        this.mInputManager.deliverWirelessKeyboardShareChanged(3, str5, true);
                    }
                    int i4 = Equals & 16;
                    if (i4 != 0) {
                        this.mInputManager.deliverWirelessKeyboardShareChanged(
                                4, isToLocalTablet() ? "true" : "false", true);
                    }
                    if ((Equals & 32) != 0) {
                        this.mInputManager.deliverWirelessKeyboardShareChanged(
                                5, this.mAppRegistered ? "true" : "false", true);
                    }
                    boolean z2 = this.mNeedNoti;
                    if (z2) {
                        this.mInputManager.deliverWirelessKeyboardShareChanged(
                                7, z2 ? "true" : "false", true);
                    }
                    boolean z3 = this.mNeedNotiTablet;
                    if (z3) {
                        this.mInputManager.deliverWirelessKeyboardShareChanged(
                                8, z3 ? "true" : "false", true);
                    }
                    if (i3 != 0) {
                        if (this.mPluggedDevice != null) {
                            Resources resources = this.mContext.getResources();
                            synchronized (this.innerLock) {
                                str4 = this.mLastestConnectedName;
                            }
                            String string = resources.getString(17043624, str4);
                            ToastDialog toastDialog = this.mToastDialog;
                            toastDialog.getClass();
                            SomeArgs obtain = SomeArgs.obtain();
                            obtain.arg1 = string;
                            ToastDialog.ToastDialogHandler toastDialogHandler =
                                    toastDialog.mHandler;
                            toastDialogHandler.removeMessages(2);
                            toastDialogHandler.obtainMessage(2, obtain).sendToTarget();
                        } else {
                            Resources resources2 = this.mContext.getResources();
                            synchronized (this.innerLock) {
                                str3 = this.mLastestConnectedName;
                            }
                            String string2 = resources2.getString(17043625, str3);
                            ToastDialog toastDialog2 = this.mToastDialog;
                            toastDialog2.getClass();
                            SomeArgs obtain2 = SomeArgs.obtain();
                            obtain2.arg1 = string2;
                            ToastDialog.ToastDialogHandler toastDialogHandler2 =
                                    toastDialog2.mHandler;
                            toastDialogHandler2.removeMessages(3);
                            toastDialogHandler2.obtainMessage(3, obtain2).sendToTarget();
                        }
                    }
                    if ((i4 != 0 || this.mNotiTurnOnBT) && isToLocalTablet()) {
                        Resources resources3 = this.mContext.getResources();
                        synchronized (this.innerLock) {
                            str = this.mTabletName;
                        }
                        String string3 = resources3.getString(17043624, str);
                        ToastDialog toastDialog3 = this.mToastDialog;
                        toastDialog3.getClass();
                        SomeArgs obtain3 = SomeArgs.obtain();
                        obtain3.arg1 = string3;
                        ToastDialog.ToastDialogHandler toastDialogHandler3 = toastDialog3.mHandler;
                        toastDialogHandler3.removeMessages(2);
                        toastDialogHandler3.obtainMessage(2, obtain3).sendToTarget();
                    }
                    if (this.mNeedNoti) {
                        Resources resources4 = this.mContext.getResources();
                        synchronized (this.innerLock) {
                            str2 = this.mTabletName;
                        }
                        String string4 = resources4.getString(17043619, str2);
                        ToastDialog toastDialog4 = this.mToastDialog;
                        toastDialog4.getClass();
                        SomeArgs obtain4 = SomeArgs.obtain();
                        obtain4.arg1 = string4;
                        ToastDialog.ToastDialogHandler toastDialogHandler4 = toastDialog4.mHandler;
                        Message obtainMessage = toastDialogHandler4.obtainMessage(1, obtain4);
                        toastDialogHandler4.removeMessages(1);
                        toastDialogHandler4.sendMessageDelayed(obtainMessage, 60000L);
                        this.mUnregisterWhenConnectionFail = true;
                        if (this.mHandler.hasMessages(11)) {
                            this.mHandler.removeMessages(11);
                        }
                        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(11), 60000L);
                        SemContinuityManager semContinuityManager =
                                (SemContinuityManager)
                                        this.mContext.getSystemService("SemContinuityService");
                        if (semContinuityManager == null) {
                            Log.e("WirelessKeyboardMouseShare", "SemContinuityManager is null");
                            nearbyDeviceCount = 0;
                        } else {
                            nearbyDeviceCount = semContinuityManager.getNearbyDeviceCount(1);
                        }
                        String string5 = this.mContext.getResources().getString(17043622);
                        String string6 =
                                nearbyDeviceCount == 0
                                        ? this.mContext.getResources().getString(17043623)
                                        : this.mContext.getResources().getString(17043620)
                                                + "\n"
                                                + this.mContext.getResources().getString(17043621);
                        ToastDialog toastDialog5 = this.mToastDialog;
                        toastDialog5.getClass();
                        SomeArgs obtain5 = SomeArgs.obtain();
                        obtain5.arg1 = string5;
                        obtain5.arg2 = string6;
                        ToastDialog.ToastDialogHandler toastDialogHandler5 = toastDialog5.mHandler;
                        toastDialogHandler5.removeMessages(10);
                        toastDialogHandler5.obtainMessage(10, obtain5).sendToTarget();
                    }
                    if (Equals != 0) {
                        StatusInfo statusInfo2 = this.mStatusInfo;
                        statusInfo2.getClass();
                        statusInfo2.mConnectionState = statusInfo.mConnectionState;
                        statusInfo2.mPluggedDeviceToString = statusInfo.mPluggedDeviceToString;
                        statusInfo2.mFocusedDeviceToString = statusInfo.mFocusedDeviceToString;
                        statusInfo2.mPluggedDeviceToStringList =
                                statusInfo.mPluggedDeviceToStringList;
                        statusInfo2.mToLocalTablet = statusInfo.mToLocalTablet;
                        statusInfo2.mAppRegistered = statusInfo.mAppRegistered;
                    }
                    BluetoothDevice bluetoothDevice6 = this.mPluggedDevice;
                    Slog.d(
                            "WirelessKeyboardMouseShare",
                            "List : "
                                    + str5
                                    + " p = "
                                    + (bluetoothDevice6 == null
                                            ? ""
                                            : bluetoothDevice6.getAddress())
                                    + ", Noti = "
                                    + this.mNeedNoti);
                    this.mNotiTurnOnBT = false;
                    this.mNeedNoti = false;
                    this.mNeedNotiTablet = false;
                    Slog.d("WirelessKeyboardMouseShare", "sendWirelessKeyboardShareStatus");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setHostRoleWirelessKeyboardShare() {
        synchronized (this.innerLock) {
            try {
                if (isRegistered()) {
                    stopHIDDevice();
                }
                this.mUnregisterWhenConnectionFail = false;
                if (this.mHandler.hasMessages(11)) {
                    this.mHandler.removeMessages(11);
                }
                Slog.d(
                        "WirelessKeyboardMouseShare",
                        "setHostRoleWirelessKeyboardShare : -> Host role(tablet)");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setPogoConnected(boolean z) {
        synchronized (this.innerLock) {
            try {
                if (this.mPogoConnected != z) {
                    this.mPogoConnected = z;
                    Slog.d(
                            "WirelessKeyboardMouseShare",
                            "setPogoConnected mPogoConnected : " + this.mPogoConnected);
                    if (InputRune.IFW_WIRELESS_KEYBOARD_SA_LOGGING) {
                        CoreSaLogger.logSettingStatusForBasic("67260", z ? "1" : "0");
                    }
                }
                if (!z) {
                    stopHIDDevice();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setToLocalTablet(boolean z) {
        String str;
        BluetoothDevice bluetoothDevice;
        BluetoothDevice bluetoothDevice2;
        synchronized (this.innerLock) {
            try {
                boolean z2 = this.mToLocalTablet;
                if (z2 == z) {
                    Slog.d(
                            "WirelessKeyboardMouseShare",
                            "not changed remote device : "
                                    .concat(z2 ? "local tablet" : "remote device"));
                    return this.mToLocalTablet;
                }
                BluetoothDevice bluetoothDevice3 = this.mFocusedDevice;
                if ((bluetoothDevice3 == null || !existBluetoothDeviceLocked(bluetoothDevice3))
                        && !z) {
                    StringBuilder sb = new StringBuilder("last connected device is ");
                    if (this.mFocusedDevice == null) {
                        str = "null.";
                    } else {
                        str = this.mFocusedDevice.toString() + ". but not exist";
                    }
                    sb.append(str);
                    Slog.d("WirelessKeyboardMouseShare", sb.toString());
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
                            Slog.d(
                                    "WirelessKeyboardMouseShare",
                                    "last connected device must be slot["
                                            + i
                                            + "] "
                                            + this.mFocusedDevice.toString());
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
                StringBuilder sb2 = new StringBuilder();
                sb2.append("try switch remote bt ");
                sb2.append(z ? "local tablet" : "remote device");
                BluetoothDevice bluetoothDevice5 = this.mFocusedDevice;
                sb2.append(bluetoothDevice5 == null ? "null" : bluetoothDevice5.toString());
                Slog.d("WirelessKeyboardMouseShare", sb2.toString());
                return this.mToLocalTablet;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startHIDDevice() {
        synchronized (this.innerLock) {
            try {
                if (!this.mPogoConnected) {
                    Slog.d(
                            "WirelessKeyboardMouseShare",
                            "startHIDDevice mPogoConnected : " + this.mPogoConnected);
                    return;
                }
                if (this.mAdapter == null) {
                    this.mAdapter = BluetoothAdapter.getDefaultAdapter();
                }
                BluetoothAdapter bluetoothAdapter = this.mAdapter;
                if (bluetoothAdapter != null) {
                    this.mTabletName = bluetoothAdapter.getName();
                }
                registerApp();
                Slog.d(
                        "WirelessKeyboardMouseShare",
                        "startHIDDevice mAppRegistered : " + this.mAppRegistered);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopHIDDevice() {
        synchronized (this.innerLock) {
            unregisterApp();
            Slog.d(
                    "WirelessKeyboardMouseShare",
                    "stopHIDDevice mAppRegistered : " + this.mAppRegistered);
        }
    }

    public final boolean switchDevice(int i, String str) {
        synchronized (this.innerLock) {
            try {
                BluetoothDevice findBluetoothDeviceFromString =
                        findBluetoothDeviceFromString(i, str);
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
                Slog.d(
                        "WirelessKeyboardMouseShare",
                        "switchDevice : "
                                + (bluetoothDevice == null ? "null" : bluetoothDevice.toString())
                                + " -> "
                                + (findBluetoothDeviceFromString == null
                                        ? "null"
                                        : findBluetoothDeviceFromString.toString())
                                + " index:"
                                + i);
                if (!z) {
                    if (!isRegistered()) {
                        startHIDDevice();
                    }
                    BluetoothDevice bluetoothDevice2 = this.mPluggedDevice;
                    if (bluetoothDevice2 != null
                            && bluetoothDevice2
                                    .getAddress()
                                    .equals(findBluetoothDeviceFromString.getAddress())) {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean switchRemoteDeviceByKey(boolean z) {
        synchronized (this.innerLock) {
            try {
                if (z) {
                    if (this.mHandler.hasMessages(11)) {
                        this.mHandler.removeMessages(11);
                    }
                    this.mUnregisterWhenConnectionFail = true;
                    this.mHandler.obtainMessage(11).sendToTarget();
                    Slog.d(
                            "WirelessKeyboardMouseShare",
                            "switchRemoteDevice : -> Host role(tablet)");
                } else {
                    BluetoothAdapter bluetoothAdapter = this.mAdapter;
                    if (bluetoothAdapter == null || bluetoothAdapter.isEnabled()) {
                        this.mHandler.post(
                                new Runnable() { // from class:
                                                 // com.android.server.input.WirelessKeyboardMouseShare$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        WirelessKeyboardMouseShare wirelessKeyboardMouseShare =
                                                WirelessKeyboardMouseShare.this;
                                        wirelessKeyboardMouseShare.mUnregisterWhenConnectionFail =
                                                true;
                                        WirelessKeyboardMouseShare.WirelessKeyboardMouseShareHandler
                                                wirelessKeyboardMouseShareHandler =
                                                        wirelessKeyboardMouseShare.mHandler;
                                        if (wirelessKeyboardMouseShareHandler.hasMessages(11)) {
                                            wirelessKeyboardMouseShareHandler.removeMessages(11);
                                        }
                                        Message obtainMessage =
                                                wirelessKeyboardMouseShareHandler.obtainMessage(11);
                                        if (!wirelessKeyboardMouseShare.isRegistered()) {
                                            wirelessKeyboardMouseShare.startHIDDevice();
                                        }
                                        if (wirelessKeyboardMouseShare.getDeviceListSize() == 0) {
                                            wirelessKeyboardMouseShareHandler.sendMessageDelayed(
                                                    obtainMessage, 60000L);
                                            wirelessKeyboardMouseShare.mNeedNoti = true;
                                            wirelessKeyboardMouseShare.sendMessageMCF();
                                        } else {
                                            wirelessKeyboardMouseShareHandler.sendMessageDelayed(
                                                    obtainMessage, 5000L);
                                        }
                                        wirelessKeyboardMouseShare.sendMessageStatus();
                                    }
                                });
                        return setToLocalTablet(false);
                    }
                    Slog.d("WirelessKeyboardMouseShare", "startHIDDeviceByKey need to turn on bt.");
                    this.mNeedToTurnOnBT = true;
                    this.mAdapter.enable();
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterApp() {
        synchronized (this.innerLock) {
            try {
                Slog.d(
                        "WirelessKeyboardMouseShare",
                        "unregisterApp(): mAppRegistered=" + this.mAppRegistered);
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateWirelessKeyboardShareStatus() {
        synchronized (this.innerLock) {
            String str = "";
            for (int i = 1; i < 4; i++) {
                try {
                    BluetoothDevice bluetoothDevice = this.mPairedDevices[i];
                    String address = bluetoothDevice != null ? bluetoothDevice.getAddress() : "";
                    str = i == 1 ? address : str + "," + address;
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mInputManager.deliverWirelessKeyboardShareChanged(
                    0, this.mConnectionState == 2 ? "true" : "false", false);
            InputManagerService inputManagerService = this.mInputManager;
            BluetoothDevice bluetoothDevice2 = this.mPluggedDevice;
            inputManagerService.deliverWirelessKeyboardShareChanged(
                    1, bluetoothDevice2 == null ? "" : bluetoothDevice2.getAddress(), false);
            InputManagerService inputManagerService2 = this.mInputManager;
            BluetoothDevice bluetoothDevice3 = this.mFocusedDevice;
            inputManagerService2.deliverWirelessKeyboardShareChanged(
                    2, bluetoothDevice3 == null ? "" : bluetoothDevice3.getAddress(), false);
            this.mInputManager.deliverWirelessKeyboardShareChanged(3, str, false);
            this.mInputManager.deliverWirelessKeyboardShareChanged(
                    4, isToLocalTablet() ? "true" : "false", false);
            this.mInputManager.deliverWirelessKeyboardShareChanged(
                    5, this.mAppRegistered ? "true" : "false", false);
            this.mInputManager.deliverWirelessKeyboardShareChanged(
                    7, this.mNeedNoti ? "true" : "false", false);
            this.mInputManager.deliverWirelessKeyboardShareChanged(
                    8, this.mNeedNotiTablet ? "true" : "false", false);
            Slog.d("WirelessKeyboardMouseShare", "updateWirelessKeyboardShareStatus");
        }
    }
}

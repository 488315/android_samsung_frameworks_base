package com.android.systemui.keyboard;

import android.R;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.Toast;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.inject.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class KeyboardUI implements CoreStartable, InputManager.OnTabletModeChangedListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BluetoothDialogDelegate mBluetoothDialogDelegate;
    public final Provider mBluetoothManagerProvider;
    public boolean mBootCompleted;
    public long mBootCompletedTime;
    public CachedBluetoothDeviceManager mCachedDeviceManager;
    public volatile Context mContext;
    public SystemUIDialog mDialog;
    public boolean mEnabled;
    public volatile KeyboardHandler mHandler;
    public String mKeyboardName;
    public LocalBluetoothAdapter mLocalBluetoothAdapter;
    public KeyboardScanCallback mScanCallback;
    public final SecureSettings mSecureSettings;
    public int mState;
    public volatile KeyboardUIHandler mUIHandler;
    public int mInTabletMode = -1;
    public int mScanAttempt = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BluetoothCallbackHandler implements BluetoothCallback {
        public /* synthetic */ BluetoothCallbackHandler(KeyboardUI keyboardUI, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onBluetoothStateChanged(int i) {
            KeyboardUI.this.mHandler.obtainMessage(4, i, 0).sendToTarget();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
            KeyboardUI.this.mHandler.obtainMessage(5, i, 0, cachedBluetoothDevice).sendToTarget();
        }

        private BluetoothCallbackHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BluetoothDialogClickListener implements DialogInterface.OnClickListener {
        public /* synthetic */ BluetoothDialogClickListener(KeyboardUI keyboardUI, int i) {
            this();
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            KeyboardUI.this.mHandler.obtainMessage(3, -1 == i ? 1 : 0, 0).sendToTarget();
            KeyboardUI.this.mDialog = null;
        }

        private BluetoothDialogClickListener() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BluetoothDialogDismissListener implements DialogInterface.OnDismissListener {
        public /* synthetic */ BluetoothDialogDismissListener(KeyboardUI keyboardUI, int i) {
            this();
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {
            KeyboardUI.this.mDialog = null;
        }

        private BluetoothDialogDismissListener() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BluetoothErrorListener {
        public /* synthetic */ BluetoothErrorListener(KeyboardUI keyboardUI, int i) {
            this(keyboardUI);
        }

        private BluetoothErrorListener(KeyboardUI keyboardUI) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class KeyboardHandler extends Handler {
        public KeyboardHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            LocalBluetoothManager localBluetoothManager;
            int i = 0;
            switch (message.what) {
                case 0:
                    KeyboardUI keyboardUI = KeyboardUI.this;
                    Context context = keyboardUI.mContext;
                    String string = context.getString(R.string.ext_media_status_unmountable);
                    keyboardUI.mKeyboardName = string;
                    if (!TextUtils.isEmpty(string) && (localBluetoothManager = (LocalBluetoothManager) keyboardUI.mBluetoothManagerProvider.get()) != null) {
                        keyboardUI.mEnabled = true;
                        keyboardUI.mCachedDeviceManager = localBluetoothManager.mCachedDeviceManager;
                        keyboardUI.mLocalBluetoothAdapter = localBluetoothManager.mLocalAdapter;
                        localBluetoothManager.mEventManager.registerCallback(new BluetoothCallbackHandler(keyboardUI, i));
                        new BluetoothErrorListener(keyboardUI, i);
                        boolean z = BluetoothUtils.DEBUG;
                        InputManager inputManager = (InputManager) context.getSystemService(InputManager.class);
                        inputManager.registerOnTabletModeChangedListener(keyboardUI, keyboardUI.mHandler);
                        keyboardUI.mInTabletMode = inputManager.isInTabletMode();
                        keyboardUI.processKeyboardState();
                        keyboardUI.mUIHandler = keyboardUI.new KeyboardUIHandler();
                        break;
                    }
                    break;
                case 1:
                    KeyboardUI keyboardUI2 = KeyboardUI.this;
                    keyboardUI2.mBootCompleted = true;
                    keyboardUI2.mBootCompletedTime = SystemClock.uptimeMillis();
                    if (keyboardUI2.mState == 1) {
                        keyboardUI2.processKeyboardState();
                        break;
                    }
                    break;
                case 2:
                    KeyboardUI keyboardUI3 = KeyboardUI.this;
                    int i2 = KeyboardUI.$r8$clinit;
                    keyboardUI3.processKeyboardState();
                    break;
                case 3:
                    if (message.arg1 != 1) {
                        KeyboardUI.this.mState = 8;
                        break;
                    } else {
                        KeyboardUI.this.mLocalBluetoothAdapter.mAdapter.enable();
                        break;
                    }
                case 4:
                    int i3 = message.arg1;
                    KeyboardUI keyboardUI4 = KeyboardUI.this;
                    if (i3 != 12) {
                        int i4 = KeyboardUI.$r8$clinit;
                        keyboardUI4.getClass();
                        break;
                    } else if (keyboardUI4.mState == 4) {
                        keyboardUI4.processKeyboardState();
                        break;
                    }
                    break;
                case 5:
                    CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) message.obj;
                    int i5 = message.arg1;
                    KeyboardUI keyboardUI5 = KeyboardUI.this;
                    if (keyboardUI5.mState == 5 && cachedBluetoothDevice.getName().equals(keyboardUI5.mKeyboardName)) {
                        if (i5 != 12) {
                            if (i5 == 10) {
                                keyboardUI5.mState = 7;
                                break;
                            }
                        } else {
                            keyboardUI5.mState = 6;
                            break;
                        }
                    }
                    break;
                case 6:
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) message.obj;
                    KeyboardUI keyboardUI6 = KeyboardUI.this;
                    int i6 = KeyboardUI.$r8$clinit;
                    CachedBluetoothDevice findDevice = keyboardUI6.mCachedDeviceManager.findDevice(bluetoothDevice);
                    if (findDevice == null) {
                        findDevice = keyboardUI6.mCachedDeviceManager.addDevice(bluetoothDevice);
                    }
                    KeyboardUI keyboardUI7 = KeyboardUI.this;
                    if (keyboardUI7.mState == 3 && findDevice.getName().equals(keyboardUI7.mKeyboardName)) {
                        keyboardUI7.stopScanning();
                        findDevice.startPairing();
                        keyboardUI7.mState = 5;
                        break;
                    }
                    break;
                case 7:
                    KeyboardUI keyboardUI8 = KeyboardUI.this;
                    keyboardUI8.mScanCallback = null;
                    if (keyboardUI8.mState == 3) {
                        keyboardUI8.mState = 9;
                        break;
                    }
                    break;
                case 10:
                    int i7 = message.arg1;
                    KeyboardUI keyboardUI9 = KeyboardUI.this;
                    if (keyboardUI9.mState == 3 && i7 == keyboardUI9.mScanAttempt) {
                        keyboardUI9.stopScanning();
                        keyboardUI9.mState = 9;
                        break;
                    }
                    break;
                case 11:
                    Pair pair = (Pair) message.obj;
                    KeyboardUI keyboardUI10 = KeyboardUI.this;
                    Context context2 = (Context) pair.first;
                    String str = (String) pair.second;
                    int i8 = message.arg1;
                    int i9 = keyboardUI10.mState;
                    if ((i9 == 5 || i9 == 7) && keyboardUI10.mKeyboardName.equals(str)) {
                        Toast.makeText(context2, context2.getString(i8, str), 0).show();
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class KeyboardScanCallback extends ScanCallback {
        public /* synthetic */ KeyboardScanCallback(KeyboardUI keyboardUI, int i) {
            this();
        }

        @Override // android.bluetooth.le.ScanCallback
        public final void onBatchScanResults(List list) {
            Iterator it = list.iterator();
            BluetoothDevice bluetoothDevice = null;
            int i = Integer.MIN_VALUE;
            while (it.hasNext()) {
                ScanResult scanResult = (ScanResult) it.next();
                if ((scanResult.getScanRecord().getAdvertiseFlags() & 3) != 0 && scanResult.getRssi() > i) {
                    bluetoothDevice = scanResult.getDevice();
                    i = scanResult.getRssi();
                }
            }
            if (bluetoothDevice != null) {
                KeyboardUI.this.mHandler.obtainMessage(6, bluetoothDevice).sendToTarget();
            }
        }

        @Override // android.bluetooth.le.ScanCallback
        public final void onScanFailed(int i) {
            KeyboardUI.this.mHandler.obtainMessage(7).sendToTarget();
        }

        @Override // android.bluetooth.le.ScanCallback
        public final void onScanResult(int i, ScanResult scanResult) {
            if ((scanResult.getScanRecord().getAdvertiseFlags() & 3) != 0) {
                KeyboardUI.this.mHandler.obtainMessage(6, scanResult.getDevice()).sendToTarget();
            }
        }

        private KeyboardScanCallback() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class KeyboardUIHandler extends Handler {
        public KeyboardUIHandler() {
            super(Looper.getMainLooper(), null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            SystemUIDialog systemUIDialog;
            int i = message.what;
            KeyboardUI keyboardUI = KeyboardUI.this;
            if (i != 8) {
                if (i == 9 && (systemUIDialog = keyboardUI.mDialog) != null) {
                    systemUIDialog.dismiss();
                    return;
                }
                return;
            }
            if (keyboardUI.mDialog != null) {
                return;
            }
            int i2 = 0;
            BluetoothDialogClickListener bluetoothDialogClickListener = new BluetoothDialogClickListener(keyboardUI, i2);
            BluetoothDialogDismissListener bluetoothDialogDismissListener = new BluetoothDialogDismissListener(keyboardUI, i2);
            SystemUIDialog createDialog = keyboardUI.mBluetoothDialogDelegate.createDialog();
            keyboardUI.mDialog = createDialog;
            createDialog.setTitle(com.android.systemui.R.string.enable_bluetooth_title);
            keyboardUI.mDialog.setMessage(com.android.systemui.R.string.enable_bluetooth_message);
            keyboardUI.mDialog.setPositiveButton(com.android.systemui.R.string.enable_bluetooth_confirmation_ok, bluetoothDialogClickListener);
            keyboardUI.mDialog.setNegativeButton(R.string.cancel, bluetoothDialogClickListener);
            keyboardUI.mDialog.setOnDismissListener(bluetoothDialogDismissListener);
            keyboardUI.mDialog.show();
        }
    }

    public KeyboardUI(Context context, Provider provider, SecureSettings secureSettings, BluetoothDialogDelegate bluetoothDialogDelegate) {
        this.mContext = context;
        this.mBluetoothManagerProvider = provider;
        this.mSecureSettings = secureSettings;
        this.mBluetoothDialogDelegate = bluetoothDialogDelegate;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String str;
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "KeyboardUI:", "  mEnabled="), this.mEnabled, printWriter, "  mBootCompleted="), this.mEnabled, printWriter, "  mBootCompletedTime=");
        m.append(this.mBootCompletedTime);
        printWriter.println(m.toString());
        printWriter.println("  mKeyboardName=" + this.mKeyboardName);
        StringBuilder m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mInTabletMode="), this.mInTabletMode, printWriter, "  mState=");
        int i = this.mState;
        switch (i) {
            case -1:
                str = "STATE_NOT_ENABLED";
                break;
            case 0:
            default:
                str = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "STATE_UNKNOWN (", ")");
                break;
            case 1:
                str = "STATE_WAITING_FOR_BOOT_COMPLETED";
                break;
            case 2:
                str = "STATE_WAITING_FOR_TABLET_MODE_EXIT";
                break;
            case 3:
                str = "STATE_WAITING_FOR_DEVICE_DISCOVERY";
                break;
            case 4:
                str = "STATE_WAITING_FOR_BLUETOOTH";
                break;
            case 5:
                str = "STATE_PAIRING";
                break;
            case 6:
                str = "STATE_PAIRED";
                break;
            case 7:
                str = "STATE_PAIRING_FAILED";
                break;
            case 8:
                str = "STATE_USER_CANCELLED";
                break;
            case 9:
                str = "STATE_DEVICE_NOT_FOUND";
                break;
        }
        CarrierTextController$$ExternalSyntheticOutline0.m(m2, str, printWriter);
    }

    @Override // com.android.systemui.CoreStartable
    public final void onBootCompleted() {
        this.mHandler.sendEmptyMessage(1);
    }

    public final void onTabletModeChanged(long j, boolean z) {
        if ((!z || this.mInTabletMode == 1) && (z || this.mInTabletMode == 0)) {
            return;
        }
        this.mInTabletMode = z ? 1 : 0;
        processKeyboardState();
    }

    public final void processKeyboardState() {
        CachedBluetoothDevice cachedBluetoothDevice;
        CachedBluetoothDevice cachedBluetoothDevice2;
        this.mHandler.removeMessages(2);
        if (!this.mEnabled) {
            this.mState = -1;
            return;
        }
        if (!this.mBootCompleted) {
            this.mState = 1;
            return;
        }
        if (this.mInTabletMode != 0) {
            int i = this.mState;
            if (i == 3) {
                stopScanning();
            } else if (i == 4) {
                this.mUIHandler.sendEmptyMessage(9);
            }
            this.mState = 2;
            return;
        }
        int state = this.mLocalBluetoothAdapter.mAdapter.getState();
        if ((state == 11 || state == 12) && this.mState == 4) {
            this.mUIHandler.sendEmptyMessage(9);
        }
        if (state == 11) {
            this.mState = 4;
            return;
        }
        int i2 = 0;
        if (state != 12) {
            this.mState = 4;
            if (this.mSecureSettings.getIntForUser(SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, -2) == 0) {
                this.mLocalBluetoothAdapter.mAdapter.enable();
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            long j = this.mBootCompletedTime + 10000;
            if (j < uptimeMillis) {
                this.mUIHandler.sendEmptyMessage(8);
                return;
            } else {
                this.mHandler.sendEmptyMessageAtTime(2, j);
                return;
            }
        }
        Iterator<BluetoothDevice> it = this.mLocalBluetoothAdapter.mAdapter.getBondedDevices().iterator();
        while (true) {
            cachedBluetoothDevice = null;
            if (!it.hasNext()) {
                cachedBluetoothDevice2 = null;
                break;
            }
            BluetoothDevice next = it.next();
            if (this.mKeyboardName.equals(next.getName())) {
                cachedBluetoothDevice2 = this.mCachedDeviceManager.findDevice(next);
                if (cachedBluetoothDevice2 == null) {
                    cachedBluetoothDevice2 = this.mCachedDeviceManager.addDevice(next);
                }
            }
        }
        int i3 = this.mState;
        if (i3 == 2 || i3 == 4) {
            if (cachedBluetoothDevice2 != null) {
                this.mState = 6;
                cachedBluetoothDevice2.connect$1();
                return;
            }
            this.mCachedDeviceManager.clearNonBondedDevices();
        }
        ArrayList arrayList = (ArrayList) this.mCachedDeviceManager.getCachedDevicesCopy();
        int size = arrayList.size();
        int i4 = 0;
        while (true) {
            if (i4 >= size) {
                break;
            }
            Object obj = arrayList.get(i4);
            i4++;
            CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) obj;
            if (cachedBluetoothDevice3.getName().equals(this.mKeyboardName)) {
                cachedBluetoothDevice = cachedBluetoothDevice3;
                break;
            }
        }
        if (cachedBluetoothDevice != null) {
            this.mState = 5;
            cachedBluetoothDevice.startPairing();
            return;
        }
        this.mState = 3;
        BluetoothLeScanner bluetoothLeScanner = this.mLocalBluetoothAdapter.mAdapter.getBluetoothLeScanner();
        ScanFilter build = new ScanFilter.Builder().setDeviceName(this.mKeyboardName).build();
        ScanSettings build2 = new ScanSettings.Builder().setCallbackType(1).setNumOfMatches(1).setScanMode(2).setReportDelay(0L).build();
        this.mScanCallback = new KeyboardScanCallback(this, i2);
        bluetoothLeScanner.startScan(Arrays.asList(build), build2, this.mScanCallback);
        KeyboardHandler keyboardHandler = this.mHandler;
        int i5 = this.mScanAttempt + 1;
        this.mScanAttempt = i5;
        this.mHandler.sendMessageDelayed(keyboardHandler.obtainMessage(10, i5, 0), 30000L);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        HandlerThread handlerThread = new HandlerThread("Keyboard", 10);
        handlerThread.start();
        this.mHandler = new KeyboardHandler(handlerThread.getLooper());
        this.mHandler.sendEmptyMessage(0);
    }

    public final void stopScanning() {
        if (this.mScanCallback != null) {
            BluetoothLeScanner bluetoothLeScanner = this.mLocalBluetoothAdapter.mAdapter.getBluetoothLeScanner();
            if (bluetoothLeScanner != null) {
                bluetoothLeScanner.stopScan(this.mScanCallback);
            }
            this.mScanCallback = null;
        }
    }
}

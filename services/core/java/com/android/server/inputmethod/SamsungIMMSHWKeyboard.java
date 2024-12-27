package com.android.server.inputmethod;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.IInputManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.input.InputManagerService;
import com.samsung.android.knoxguard.service.utils.Constants;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SamsungIMMSHWKeyboard {
    public int keyboardState;
    public int mBTKeyboardCount;
    public InputMethodManagerService$$ExternalSyntheticLambda2 mHardKeyboardStatusChangeListener;
    public int mUSBKeyboardCount;
    public int mUSBKeyboardCountOld;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BTKeyboardReceiver extends BroadcastReceiver {
        public boolean mIsBTConnect = false;

        public BTKeyboardReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.w("InputMethodManagerServicePhysicalKey", "InputMethodService onReceive() intentAction" + action);
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
                AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(intExtra, intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", -1), "[BT Setting State] state = ", " prevState = ", "InputMethodManagerServicePhysicalKey");
                if (intExtra != 10) {
                    if (intExtra == 12) {
                        Log.i("InputMethodManagerServicePhysicalKey", "[BT Setting off -> on] mBTKeyboardCount =" + SamsungIMMSHWKeyboard.this.mBTKeyboardCount + ", KEYBOARD_BT(" + (SamsungIMMSHWKeyboard.this.keyboardState & 2) + ")");
                        this.mIsBTConnect = true;
                        return;
                    }
                    if (intExtra != 13) {
                        Log.i("InputMethodManagerServicePhysicalKey", "[BT] mBTKeyboardCount =" + SamsungIMMSHWKeyboard.this.mBTKeyboardCount + ", KEYBOARD_BT(" + (SamsungIMMSHWKeyboard.this.keyboardState & 2) + ")");
                        return;
                    }
                }
                Log.i("InputMethodManagerServicePhysicalKey", "[BT Setting on -> off] mBTKeyboardCount =" + SamsungIMMSHWKeyboard.this.mBTKeyboardCount + ", KEYBOARD_BT(" + (SamsungIMMSHWKeyboard.this.keyboardState & 2) + ")");
                resetBTKeyboardState();
                this.mIsBTConnect = false;
                return;
            }
            if ("android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED".equals(action)) {
                int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                boolean booleanExtra = intent.getBooleanExtra("android.bluetooth.profile.extra.isKeyboard", false);
                int intExtra3 = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", -1);
                Log.w("InputMethodManagerServicePhysicalKey", "onReceive() isKeyboard = " + booleanExtra);
                if (booleanExtra) {
                    Log.w("InputMethodManagerServicePhysicalKey", "onReceive() state = " + intExtra2 + ", prevState = " + intExtra3);
                    if (intExtra2 == 2) {
                        this.mIsBTConnect = true;
                        if (SamsungIMMSHWKeyboard.this.mBTKeyboardCount < 0) {
                            Log.e("InputMethodManagerServicePhysicalKey", "[STATE_CONNECTED] change mBTKeyboardCount " + SamsungIMMSHWKeyboard.this.mBTKeyboardCount + "--> 0");
                            SamsungIMMSHWKeyboard.this.mBTKeyboardCount = 0;
                        }
                        SamsungIMMSHWKeyboard.this.mBTKeyboardCount++;
                        AudioService$$ExternalSyntheticOutline0.m(new StringBuilder("[STATE_CONNECTED] mBTKeyboardCount[+] -->"), SamsungIMMSHWKeyboard.this.mBTKeyboardCount, "InputMethodManagerServicePhysicalKey");
                        SamsungIMMSHWKeyboard samsungIMMSHWKeyboard = SamsungIMMSHWKeyboard.this;
                        samsungIMMSHWKeyboard.keyboardState |= 2;
                        SamsungIMMSHWKeyboard.m604$$Nest$mconnectedHWKeyboard(samsungIMMSHWKeyboard);
                        return;
                    }
                    if (intExtra3 == 2) {
                        if (SamsungIMMSHWKeyboard.this.mBTKeyboardCount < 1) {
                            Log.e("InputMethodManagerServicePhysicalKey", "[prevState : STATE_CONNECTED] change mBTKeyboardCount " + SamsungIMMSHWKeyboard.this.mBTKeyboardCount + "--> 1");
                            SamsungIMMSHWKeyboard.this.mBTKeyboardCount = 1;
                        }
                        SamsungIMMSHWKeyboard samsungIMMSHWKeyboard2 = SamsungIMMSHWKeyboard.this;
                        int i = samsungIMMSHWKeyboard2.mBTKeyboardCount - 1;
                        samsungIMMSHWKeyboard2.mBTKeyboardCount = i;
                        if (i == 0 || (!this.mIsBTConnect && (samsungIMMSHWKeyboard2.keyboardState & 2) == 2)) {
                            resetBTKeyboardState();
                        }
                        Log.i("InputMethodManagerServicePhysicalKey", "[prevState : STATE_CONNECTED] mBTKeyboardCount[-] -->" + SamsungIMMSHWKeyboard.this.mBTKeyboardCount + "keyboardState(" + SamsungIMMSHWKeyboard.this.keyboardState + ")");
                    }
                }
            }
        }

        public final void resetBTKeyboardState() {
            SamsungIMMSHWKeyboard samsungIMMSHWKeyboard = SamsungIMMSHWKeyboard.this;
            int i = samsungIMMSHWKeyboard.keyboardState;
            if ((i & 2) == 2) {
                samsungIMMSHWKeyboard.keyboardState = i & (-3);
            }
            samsungIMMSHWKeyboard.mBTKeyboardCount = 0;
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(new StringBuilder("resetBTKeyboardState : keyboardState = "), SamsungIMMSHWKeyboard.this.keyboardState, "InputMethodManagerServicePhysicalKey");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KMSKeyboardReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ SamsungIMMSHWKeyboard this$0;

        public /* synthetic */ KMSKeyboardReceiver(SamsungIMMSHWKeyboard samsungIMMSHWKeyboard, int i) {
            this.$r8$classId = i;
            this.this$0 = samsungIMMSHWKeyboard;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    Slog.w("InputMethodManagerServicePhysicalKey", "KMSKeyboardReceiver :  onReceive() intentAction" + action);
                    if ("com.sec.android.sidesync.action.KMS_KEYBOARD".equals(action)) {
                        int intExtra = intent.getIntExtra("device_state", -1);
                        DeviceIdleController$$ExternalSyntheticOutline0.m(intExtra, "KMSKeyboardReceiver : onReceive(), getIntExtra ", "InputMethodManagerServicePhysicalKey");
                        if (intExtra == 1) {
                            SamsungIMMSHWKeyboard samsungIMMSHWKeyboard = this.this$0;
                            samsungIMMSHWKeyboard.keyboardState |= 32;
                            SamsungIMMSHWKeyboard.m604$$Nest$mconnectedHWKeyboard(samsungIMMSHWKeyboard);
                        } else {
                            this.this$0.keyboardState &= -33;
                        }
                    }
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("KMSKeyboardReceiver : onReceive(), keyboardState - "), this.this$0.keyboardState, "InputMethodManagerServicePhysicalKey");
                    break;
                case 1:
                    String action2 = intent.getAction();
                    Log.w("InputMethodManagerServicePhysicalKey", "PSSKeyboardReceiver : onReceive() intentAction" + action2);
                    if ("com.sec.android.sidesync.action.PSS_KEYBOARD".equals(action2)) {
                        int intExtra2 = intent.getIntExtra("device_state", -1);
                        NetworkScoreService$$ExternalSyntheticOutline0.m(intExtra2, "PSSKeyboardReceiver : onReceive(), getIntExtra - ", "InputMethodManagerServicePhysicalKey");
                        if (intExtra2 == 1) {
                            SamsungIMMSHWKeyboard samsungIMMSHWKeyboard2 = this.this$0;
                            samsungIMMSHWKeyboard2.keyboardState |= 16;
                            SamsungIMMSHWKeyboard.m604$$Nest$mconnectedHWKeyboard(samsungIMMSHWKeyboard2);
                        } else {
                            this.this$0.keyboardState &= -17;
                        }
                    }
                    AudioService$$ExternalSyntheticOutline0.m(new StringBuilder("PSSKeyboardReceiver :  onReceive(), keyboardState - "), this.this$0.keyboardState, "InputMethodManagerServicePhysicalKey");
                    break;
                default:
                    String action3 = intent.getAction();
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                    int interfaceCount = usbDevice == null ? 0 : usbDevice.getInterfaceCount();
                    NetworkScoreService$$ExternalSyntheticOutline0.m(interfaceCount, "onReceive() USBK interfaceCount : ", "InputMethodManagerServicePhysicalKey");
                    int i = 0;
                    while (true) {
                        if (i < interfaceCount) {
                            UsbInterface usbInterface = usbDevice.getInterface(i);
                            if (usbInterface == null) {
                                NetworkScoreService$$ExternalSyntheticOutline0.m(i, "onReceive() usbInterface is null, index : ", "InputMethodManagerServicePhysicalKey");
                            } else {
                                int interfaceClass = usbInterface.getInterfaceClass();
                                int interfaceProtocol = usbInterface.getInterfaceProtocol();
                                if (interfaceClass == 3 && interfaceProtocol == 1) {
                                    if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action3)) {
                                        if (this.this$0.mUSBKeyboardCount < 0) {
                                            Log.e("InputMethodManagerServicePhysicalKey", "[ACTION_USB_DEVICE_ATTACHED] change mUSBKeyboardCount " + this.this$0.mUSBKeyboardCount + "--> 0");
                                            this.this$0.mUSBKeyboardCount = 0;
                                        }
                                        this.this$0.mUSBKeyboardCount++;
                                    } else if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action3)) {
                                        if (this.this$0.mUSBKeyboardCount < 1) {
                                            Log.e("InputMethodManagerServicePhysicalKey", "[ACTION_USB_DEVICE_DETACHED] change mUSBKeyboardCount " + this.this$0.mUSBKeyboardCount + "--> 1");
                                            this.this$0.mUSBKeyboardCount = 1;
                                        }
                                        this.this$0.mUSBKeyboardCount--;
                                    }
                                }
                            }
                            i++;
                        }
                    }
                    SamsungIMMSHWKeyboard samsungIMMSHWKeyboard3 = this.this$0;
                    int i2 = samsungIMMSHWKeyboard3.mUSBKeyboardCountOld;
                    int i3 = samsungIMMSHWKeyboard3.mUSBKeyboardCount;
                    boolean z = i2 != i3;
                    samsungIMMSHWKeyboard3.mUSBKeyboardCountOld = i3;
                    if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action3)) {
                        if (z) {
                            this.this$0.keyboardState |= 4;
                            AudioService$$ExternalSyntheticOutline0.m(new StringBuilder("onReceive() USBK Connect C : "), this.this$0.mUSBKeyboardCount, "InputMethodManagerServicePhysicalKey");
                            SamsungIMMSHWKeyboard.m604$$Nest$mconnectedHWKeyboard(this.this$0);
                        }
                    } else if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action3)) {
                        SamsungIMMSHWKeyboard samsungIMMSHWKeyboard4 = this.this$0;
                        if (samsungIMMSHWKeyboard4.mUSBKeyboardCount == 0) {
                            samsungIMMSHWKeyboard4.keyboardState &= -5;
                            AudioService$$ExternalSyntheticOutline0.m(new StringBuilder("onReceive() USBK DisConnect C : "), this.this$0.mUSBKeyboardCount, "InputMethodManagerServicePhysicalKey");
                            this.this$0.getClass();
                        }
                    }
                    AudioService$$ExternalSyntheticOutline0.m(new StringBuilder("onReceive() keyboardState : "), this.this$0.keyboardState, "InputMethodManagerServicePhysicalKey");
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class POGOKeyboardReceiver extends BroadcastReceiver {
        public final InputManagerService mInputManagerService = IInputManager.Stub.asInterface(ServiceManager.getService("input"));
        public final AnonymousClass1 mCallbacks = new AnonymousClass1();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.inputmethod.SamsungIMMSHWKeyboard$POGOKeyboardReceiver$1, reason: invalid class name */
        public final class AnonymousClass1 implements InputManagerService.InputMethodManagerCallbacks {
            public AnonymousClass1() {
            }
        }

        public POGOKeyboardReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.samsung.android.input.POGO_KEYBOARD_CHANGED".equals(intent.getAction())) {
                boolean booleanExtra = intent.getBooleanExtra(Constants.JSON_CLIENT_DATA_STATUS, false);
                if (booleanExtra) {
                    SamsungIMMSHWKeyboard samsungIMMSHWKeyboard = SamsungIMMSHWKeyboard.this;
                    samsungIMMSHWKeyboard.keyboardState |= 64;
                    SamsungIMMSHWKeyboard.m604$$Nest$mconnectedHWKeyboard(samsungIMMSHWKeyboard);
                } else {
                    SamsungIMMSHWKeyboard.this.keyboardState &= -73;
                }
                UiModeManagerService$13$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m("onReceive: PogoKeyboard connected=", ", keyboardState=", booleanExtra), SamsungIMMSHWKeyboard.this.keyboardState, "InputMethodManagerServicePhysicalKey");
            }
        }
    }

    /* renamed from: -$$Nest$mconnectedHWKeyboard, reason: not valid java name */
    public static void m604$$Nest$mconnectedHWKeyboard(SamsungIMMSHWKeyboard samsungIMMSHWKeyboard) {
        InputMethodManagerService inputMethodManagerService = samsungIMMSHWKeyboard.mHardKeyboardStatusChangeListener.f$0;
        inputMethodManagerService.getClass();
        Slog.d("InputMethodManagerService", "HW Keyboard connected");
        synchronized (ImfLock.class) {
            inputMethodManagerService.hideCurrentInputLocked(inputMethodManagerService.mImeBindingState.mFocusedWindow, inputMethodManagerService.createStatsTokenForFocusedClient(56, false), 0, null, 56);
        }
    }

    public final boolean isPogoBackfolded() {
        boolean z = (this.keyboardState & 8) != 0;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isPogoBackfolded ", "InputMethodManagerServicePhysicalKey", z);
        return z;
    }
}

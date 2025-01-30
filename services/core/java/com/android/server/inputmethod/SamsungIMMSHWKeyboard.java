package com.android.server.inputmethod;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.input.IInputManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import com.android.server.input.InputManagerService;

/* loaded from: classes2.dex */
public class SamsungIMMSHWKeyboard {
  public int keyboardState;
  public Context mContext;
  public HWKeyboardStatusChangeListener mHardKeyboardStatusChangeListener;
  public int prevAutoMode;
  public int mBTKeyboardCount = 0;
  public int mUSBKeyboardCount = 0;
  public int mUSBKeyboardCountOld = 0;

  public int isAccessoryKeyboard() {
    return this.keyboardState;
  }

  public void updateKeyboardStateForDEXOnPC(int i) {
    if (i == 3) {
      this.keyboardState |= 128;
    } else {
      this.keyboardState &= -129;
    }
  }

  public boolean isHWAccessoryKeyboard() {
    int i = this.keyboardState;
    boolean z = (i & 15) != 0;
    boolean z2 = (i & 128) != 0;
    Log.d("InputMethodManagerServicePhysicalKey", "isConnectedDexOnPC " + z2);
    return z || z2;
  }

  public boolean isPogoBackfolded() {
    boolean z = (this.keyboardState & 8) != 0;
    Log.d("InputMethodManagerServicePhysicalKey", "isPogoBackfolded " + z);
    return z;
  }

  public boolean isPogoConnectedOnly() {
    int i = this.keyboardState;
    return i != 0 && (i & (-73)) == 0;
  }

  public final void connectedHWKeyboard() {
    this.mHardKeyboardStatusChangeListener.onHardKeyboardStatusChange(true);
  }

  public final void updateBrightnessMode() {
    if (this.prevAutoMode == 1) {
      Settings.System.putInt(
          this.mContext.getContentResolver(), "screen_brightness_mode", this.prevAutoMode);
    }
    this.prevAutoMode = 0;
  }

  public void registerReceiver(Context context) {
    this.mContext = context;
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.sec.android.sidesync.action.PSS_KEYBOARD");
    context.registerReceiver(
        new PSSKeyboardReceiver(),
        intentFilter,
        "com.sec.android.permission.SIDESYNC_RECEIVER_PERMISSION",
        null);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("com.sec.android.sidesync.action.KMS_KEYBOARD");
    context.registerReceiver(
        new KMSKeyboardReceiver(),
        intentFilter2,
        "com.sec.android.permission.SIDESYNC_RECEIVER_PERMISSION",
        null);
    IntentFilter intentFilter3 = new IntentFilter();
    intentFilter3.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
    intentFilter3.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
    context.registerReceiver(
        new USBKeyboardReceiver(), intentFilter3, "android.permission.MANAGE_USB", null);
    IntentFilter intentFilter4 = new IntentFilter();
    intentFilter4.addAction("android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
    intentFilter4.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
    context.registerReceiver(new BTKeyboardReceiver(), intentFilter4);
    IntentFilter intentFilter5 = new IntentFilter();
    intentFilter5.addAction("com.samsung.android.input.POGO_KEYBOARD_CHANGED");
    POGOKeyboardReceiver pOGOKeyboardReceiver = new POGOKeyboardReceiver();
    context.registerReceiver(pOGOKeyboardReceiver, intentFilter5);
    pOGOKeyboardReceiver.init();
  }

  public class USBKeyboardReceiver extends BroadcastReceiver {
    public USBKeyboardReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
      int interfaceCount = usbDevice == null ? 0 : usbDevice.getInterfaceCount();
      Log.w(
          "InputMethodManagerServicePhysicalKey",
          "onReceive() USBK interfaceCount : " + interfaceCount);
      int i = 0;
      while (true) {
        if (i >= interfaceCount) {
          break;
        }
        UsbInterface usbInterface = usbDevice.getInterface(i);
        if (usbInterface == null) {
          Log.w(
              "InputMethodManagerServicePhysicalKey",
              "onReceive() usbInterface is null, index : " + i);
        } else {
          int interfaceClass = usbInterface.getInterfaceClass();
          int interfaceProtocol = usbInterface.getInterfaceProtocol();
          if (interfaceClass == 3 && interfaceProtocol == 1) {
            if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action)) {
              if (SamsungIMMSHWKeyboard.this.mUSBKeyboardCount < 0) {
                Log.e(
                    "InputMethodManagerServicePhysicalKey",
                    "[ACTION_USB_DEVICE_ATTACHED] change mUSBKeyboardCount "
                        + SamsungIMMSHWKeyboard.this.mUSBKeyboardCount
                        + "--> 0");
                SamsungIMMSHWKeyboard.this.mUSBKeyboardCount = 0;
              }
              SamsungIMMSHWKeyboard.this.mUSBKeyboardCount++;
            } else if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action)) {
              if (SamsungIMMSHWKeyboard.this.mUSBKeyboardCount < 1) {
                Log.e(
                    "InputMethodManagerServicePhysicalKey",
                    "[ACTION_USB_DEVICE_DETACHED] change mUSBKeyboardCount "
                        + SamsungIMMSHWKeyboard.this.mUSBKeyboardCount
                        + "--> 1");
                SamsungIMMSHWKeyboard.this.mUSBKeyboardCount = 1;
              }
              SamsungIMMSHWKeyboard.this.mUSBKeyboardCount--;
            }
          }
        }
        i++;
      }
      boolean z =
          SamsungIMMSHWKeyboard.this.mUSBKeyboardCountOld
              != SamsungIMMSHWKeyboard.this.mUSBKeyboardCount;
      SamsungIMMSHWKeyboard samsungIMMSHWKeyboard = SamsungIMMSHWKeyboard.this;
      samsungIMMSHWKeyboard.mUSBKeyboardCountOld = samsungIMMSHWKeyboard.mUSBKeyboardCount;
      if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action)) {
        if (z) {
          SamsungIMMSHWKeyboard.this.keyboardState |= 4;
          Log.w(
              "InputMethodManagerServicePhysicalKey",
              "onReceive() USBK Connect C : " + SamsungIMMSHWKeyboard.this.mUSBKeyboardCount);
          SamsungIMMSHWKeyboard.this.connectedHWKeyboard();
        }
      } else if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action)
          && SamsungIMMSHWKeyboard.this.mUSBKeyboardCount == 0) {
        SamsungIMMSHWKeyboard.this.keyboardState &= -5;
        Log.w(
            "InputMethodManagerServicePhysicalKey",
            "onReceive() USBK DisConnect C : " + SamsungIMMSHWKeyboard.this.mUSBKeyboardCount);
        SamsungIMMSHWKeyboard.this.updateBrightnessMode();
      }
      Log.w(
          "InputMethodManagerServicePhysicalKey",
          "onReceive() keyboardState : " + SamsungIMMSHWKeyboard.this.keyboardState);
    }
  }

  public class BTKeyboardReceiver extends BroadcastReceiver {
    public boolean mIsBTConnect = false;

    public BTKeyboardReceiver() {}

    public final void resetBTKeyboardState() {
      if ((SamsungIMMSHWKeyboard.this.keyboardState & 2) == 2) {
        SamsungIMMSHWKeyboard.this.keyboardState &= -3;
        SamsungIMMSHWKeyboard.this.updateBrightnessMode();
      }
      SamsungIMMSHWKeyboard.this.mBTKeyboardCount = 0;
      Log.i(
          "InputMethodManagerServicePhysicalKey",
          "resetBTKeyboardState : keyboardState = " + SamsungIMMSHWKeyboard.this.keyboardState);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      Slog.w(
          "InputMethodManagerServicePhysicalKey",
          "InputMethodService onReceive() intentAction" + action);
      if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
        int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
        Log.i(
            "InputMethodManagerServicePhysicalKey",
            "[BT Setting State] state = "
                + intExtra
                + " prevState = "
                + intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", -1));
        if (intExtra != 10) {
          if (intExtra == 12) {
            Log.i(
                "InputMethodManagerServicePhysicalKey",
                "[BT Setting off -> on] mBTKeyboardCount ="
                    + SamsungIMMSHWKeyboard.this.mBTKeyboardCount
                    + ", KEYBOARD_BT("
                    + (SamsungIMMSHWKeyboard.this.keyboardState & 2)
                    + ")");
            this.mIsBTConnect = true;
            return;
          }
          if (intExtra != 13) {
            Log.i(
                "InputMethodManagerServicePhysicalKey",
                "[BT] mBTKeyboardCount ="
                    + SamsungIMMSHWKeyboard.this.mBTKeyboardCount
                    + ", KEYBOARD_BT("
                    + (SamsungIMMSHWKeyboard.this.keyboardState & 2)
                    + ")");
            return;
          }
        }
        Log.i(
            "InputMethodManagerServicePhysicalKey",
            "[BT Setting on -> off] mBTKeyboardCount ="
                + SamsungIMMSHWKeyboard.this.mBTKeyboardCount
                + ", KEYBOARD_BT("
                + (SamsungIMMSHWKeyboard.this.keyboardState & 2)
                + ")");
        resetBTKeyboardState();
        this.mIsBTConnect = false;
        return;
      }
      if ("android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED".equals(action)) {
        int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
        boolean booleanExtra =
            intent.getBooleanExtra("android.bluetooth.profile.extra.isKeyboard", false);
        int intExtra3 = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", -1);
        Log.w("InputMethodManagerServicePhysicalKey", "onReceive() isKeyboard = " + booleanExtra);
        if (booleanExtra) {
          Log.w(
              "InputMethodManagerServicePhysicalKey",
              "onReceive() state = " + intExtra2 + ", prevState = " + intExtra3);
          if (intExtra2 == 2) {
            this.mIsBTConnect = true;
            if (SamsungIMMSHWKeyboard.this.mBTKeyboardCount < 0) {
              Log.e(
                  "InputMethodManagerServicePhysicalKey",
                  "[STATE_CONNECTED] change mBTKeyboardCount "
                      + SamsungIMMSHWKeyboard.this.mBTKeyboardCount
                      + "--> 0");
              SamsungIMMSHWKeyboard.this.mBTKeyboardCount = 0;
            }
            SamsungIMMSHWKeyboard.this.mBTKeyboardCount++;
            Log.w(
                "InputMethodManagerServicePhysicalKey",
                "[STATE_CONNECTED] mBTKeyboardCount[+] -->"
                    + SamsungIMMSHWKeyboard.this.mBTKeyboardCount);
            SamsungIMMSHWKeyboard samsungIMMSHWKeyboard = SamsungIMMSHWKeyboard.this;
            samsungIMMSHWKeyboard.keyboardState = samsungIMMSHWKeyboard.keyboardState | 2;
            SamsungIMMSHWKeyboard.this.connectedHWKeyboard();
            return;
          }
          if (intExtra3 == 2) {
            if (SamsungIMMSHWKeyboard.this.mBTKeyboardCount < 1) {
              Log.e(
                  "InputMethodManagerServicePhysicalKey",
                  "[prevState : STATE_CONNECTED] change mBTKeyboardCount "
                      + SamsungIMMSHWKeyboard.this.mBTKeyboardCount
                      + "--> 1");
              SamsungIMMSHWKeyboard.this.mBTKeyboardCount = 1;
            }
            SamsungIMMSHWKeyboard samsungIMMSHWKeyboard2 = SamsungIMMSHWKeyboard.this;
            int i = samsungIMMSHWKeyboard2.mBTKeyboardCount - 1;
            samsungIMMSHWKeyboard2.mBTKeyboardCount = i;
            if (i == 0
                || (!this.mIsBTConnect && (SamsungIMMSHWKeyboard.this.keyboardState & 2) == 2)) {
              resetBTKeyboardState();
            }
            Log.i(
                "InputMethodManagerServicePhysicalKey",
                "[prevState : STATE_CONNECTED] mBTKeyboardCount[-] -->"
                    + SamsungIMMSHWKeyboard.this.mBTKeyboardCount
                    + "keyboardState("
                    + SamsungIMMSHWKeyboard.this.keyboardState
                    + ")");
          }
        }
      }
    }
  }

  public class POGOKeyboardReceiver extends BroadcastReceiver {
    public InputManagerService mInputManagerService =
        IInputManager.Stub.asInterface(ServiceManager.getService("input"));
    public InputManagerService.InputMethodManagerCallbacks mCallbacks =
        new InputManagerService
            .InputMethodManagerCallbacks() { // from class:
                                             // com.android.server.inputmethod.SamsungIMMSHWKeyboard.POGOKeyboardReceiver.1
          @Override // com.android.server.input.InputManagerService.InputMethodManagerCallbacks
          public void notifyKeyboardCoverBackfolded(long j, boolean z) {
            if (z) {
              SamsungIMMSHWKeyboard.this.keyboardState |= 8;
            } else {
              SamsungIMMSHWKeyboard.this.keyboardState &= -9;
            }
            Log.i(
                "InputMethodManagerServicePhysicalKey",
                "notifyKeyboardCoverBackfolded: backfolded="
                    + z
                    + ", keyboardState="
                    + SamsungIMMSHWKeyboard.this.keyboardState);
          }
        };

    public POGOKeyboardReceiver() {}

    public void init() {
      InputManagerService inputManagerService = this.mInputManagerService;
      if (inputManagerService != null) {
        inputManagerService.setInputMethodManagerCallbacks(this.mCallbacks);
      }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      if ("com.samsung.android.input.POGO_KEYBOARD_CHANGED".equals(intent.getAction())) {
        boolean booleanExtra = intent.getBooleanExtra("status", false);
        if (booleanExtra) {
          SamsungIMMSHWKeyboard.this.keyboardState |= 64;
          SamsungIMMSHWKeyboard.this.connectedHWKeyboard();
        } else {
          SamsungIMMSHWKeyboard.this.keyboardState &= -65;
          SamsungIMMSHWKeyboard.this.keyboardState &= -9;
        }
        Log.i(
            "InputMethodManagerServicePhysicalKey",
            "onReceive: PogoKeyboard connected="
                + booleanExtra
                + ", keyboardState="
                + SamsungIMMSHWKeyboard.this.keyboardState);
      }
    }
  }

  public class PSSKeyboardReceiver extends BroadcastReceiver {
    public PSSKeyboardReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      Log.w(
          "InputMethodManagerServicePhysicalKey",
          "PSSKeyboardReceiver : onReceive() intentAction" + action);
      if ("com.sec.android.sidesync.action.PSS_KEYBOARD".equals(action)) {
        int intExtra = intent.getIntExtra("device_state", -1);
        Log.w(
            "InputMethodManagerServicePhysicalKey",
            "PSSKeyboardReceiver : onReceive(), getIntExtra - " + intExtra);
        if (intExtra == 1) {
          SamsungIMMSHWKeyboard.this.keyboardState |= 16;
          SamsungIMMSHWKeyboard.this.connectedHWKeyboard();
        } else {
          SamsungIMMSHWKeyboard.this.keyboardState &= -17;
          SamsungIMMSHWKeyboard.this.updateBrightnessMode();
        }
      }
      Log.w(
          "InputMethodManagerServicePhysicalKey",
          "PSSKeyboardReceiver :  onReceive(), keyboardState - "
              + SamsungIMMSHWKeyboard.this.keyboardState);
    }
  }

  public class KMSKeyboardReceiver extends BroadcastReceiver {
    public KMSKeyboardReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      Slog.w(
          "InputMethodManagerServicePhysicalKey",
          "KMSKeyboardReceiver :  onReceive() intentAction" + action);
      if ("com.sec.android.sidesync.action.KMS_KEYBOARD".equals(action)) {
        int intExtra = intent.getIntExtra("device_state", -1);
        Slog.w(
            "InputMethodManagerServicePhysicalKey",
            "KMSKeyboardReceiver : onReceive(), getIntExtra " + intExtra);
        if (intExtra == 1) {
          SamsungIMMSHWKeyboard.this.keyboardState |= 32;
          SamsungIMMSHWKeyboard.this.connectedHWKeyboard();
        } else {
          SamsungIMMSHWKeyboard.this.keyboardState &= -33;
          SamsungIMMSHWKeyboard.this.updateBrightnessMode();
        }
      }
      Slog.w(
          "InputMethodManagerServicePhysicalKey",
          "KMSKeyboardReceiver : onReceive(), keyboardState - "
              + SamsungIMMSHWKeyboard.this.keyboardState);
    }
  }

  public void setOnHardKeyboardStatusChangeListener(
      HWKeyboardStatusChangeListener hWKeyboardStatusChangeListener) {
    this.mHardKeyboardStatusChangeListener = hWKeyboardStatusChangeListener;
  }
}

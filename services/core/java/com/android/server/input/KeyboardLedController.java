package com.android.server.input;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorPrivacyManager;
import android.hardware.input.InputManager;
import android.hardware.lights.Light;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import android.view.InputDevice;

public final class KeyboardLedController implements InputManager.InputDeviceListener {
    public AudioManager mAudioManager;
    public final Context mContext;
    public final Handler mHandler;
    public InputManager mInputManager;
    public final SparseArray mKeyboardsWithMicMuteLed = new SparseArray();
    public final AnonymousClass1 mMicrophoneMuteChangedIntentReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.input.KeyboardLedController.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    KeyboardLedController.this.mHandler.sendMessage(
                            Message.obtain(KeyboardLedController.this.mHandler, 2));
                }
            };
    public final NativeInputManagerService mNative;
    public SensorPrivacyManager mSensorPrivacyManager;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.input.KeyboardLedController$1] */
    public KeyboardLedController(
            Context context, Looper looper, NativeInputManagerService.NativeImpl nativeImpl) {
        this.mContext = context;
        this.mNative = nativeImpl;
        this.mHandler =
                new Handler(
                        looper,
                        new Handler
                                .Callback() { // from class:
                                              // com.android.server.input.KeyboardLedController$$ExternalSyntheticLambda0
                            @Override // android.os.Handler.Callback
                            public final boolean handleMessage(Message message) {
                                KeyboardLedController keyboardLedController =
                                        KeyboardLedController.this;
                                keyboardLedController.getClass();
                                int i = message.what;
                                int i2 = 0;
                                if (i == 1) {
                                    int[] iArr = (int[]) message.obj;
                                    int length = iArr.length;
                                    while (i2 < length) {
                                        keyboardLedController.onInputDeviceChanged(iArr[i2]);
                                        i2++;
                                    }
                                    return true;
                                }
                                if (i != 2) {
                                    return false;
                                }
                                int i3 =
                                        (keyboardLedController.mAudioManager.isMicrophoneMute()
                                                        || keyboardLedController
                                                                .mSensorPrivacyManager
                                                                .areAnySensorPrivacyTogglesEnabled(
                                                                        1))
                                                ? -1
                                                : 0;
                                while (i2 < keyboardLedController.mKeyboardsWithMicMuteLed.size()) {
                                    InputDevice inputDevice =
                                            (InputDevice)
                                                    keyboardLedController.mKeyboardsWithMicMuteLed
                                                            .valueAt(i2);
                                    if (inputDevice != null) {
                                        int id = inputDevice.getId();
                                        Light keyboardMicMuteLight =
                                                KeyboardLedController.getKeyboardMicMuteLight(
                                                        inputDevice);
                                        if (keyboardMicMuteLight != null) {
                                            keyboardLedController.mNative.setLightColor(
                                                    id, keyboardMicMuteLight.getId(), i3);
                                        }
                                    }
                                    i2++;
                                }
                                return true;
                            }
                        });
    }

    public static Light getKeyboardMicMuteLight(InputDevice inputDevice) {
        for (Light light : inputDevice.getLightsManager().getLights()) {
            if (light.getType() == 10004 && light.hasBrightnessControl()) {
                return light;
            }
        }
        return null;
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(int i) {
        onInputDeviceChanged(i);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(int i) {
        InputDevice inputDevice = this.mInputManager.getInputDevice(i);
        if (inputDevice == null || getKeyboardMicMuteLight(inputDevice) == null) {
            return;
        }
        this.mKeyboardsWithMicMuteLed.put(i, inputDevice);
        this.mHandler.sendMessage(Message.obtain(this.mHandler, 2));
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(int i) {
        this.mKeyboardsWithMicMuteLed.remove(i);
    }
}

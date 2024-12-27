package com.android.server.vibrator;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.VibratorManager;
import android.util.SparseArray;
import android.view.InputDevice;

public final class InputDeviceDelegate implements InputManager.InputDeviceListener {
    public final Context mContext;
    public final Handler mHandler;
    public InputManager mInputManager;
    public boolean mShouldVibrateInputDevices;
    public final Object mLock = new Object();
    public final SparseArray mInputDeviceVibrators = new SparseArray();

    public InputDeviceDelegate(Context context, Handler handler) {
        this.mHandler = handler;
        this.mContext = context;
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(int i) {
        updateInputDevice(i);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(int i) {
        updateInputDevice(i);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(int i) {
        synchronized (this.mLock) {
            this.mInputDeviceVibrators.remove(i);
        }
    }

    public final void updateInputDevice(int i) {
        synchronized (this.mLock) {
            try {
                InputManager inputManager = this.mInputManager;
                if (inputManager == null) {
                    return;
                }
                if (this.mShouldVibrateInputDevices) {
                    InputDevice inputDevice = inputManager.getInputDevice(i);
                    if (inputDevice == null) {
                        this.mInputDeviceVibrators.remove(i);
                        return;
                    }
                    VibratorManager vibratorManager = inputDevice.getVibratorManager();
                    if (vibratorManager.getVibratorIds().length > 0) {
                        this.mInputDeviceVibrators.put(inputDevice.getId(), vibratorManager);
                    } else {
                        this.mInputDeviceVibrators.remove(i);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean updateInputDeviceVibrators(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mInputManager == null) {
                    return false;
                }
                if (z == this.mShouldVibrateInputDevices) {
                    return false;
                }
                this.mShouldVibrateInputDevices = z;
                this.mInputDeviceVibrators.clear();
                if (z) {
                    this.mInputManager.registerInputDeviceListener(this, this.mHandler);
                    for (int i : this.mInputManager.getInputDeviceIds()) {
                        InputDevice inputDevice = this.mInputManager.getInputDevice(i);
                        if (inputDevice != null) {
                            VibratorManager vibratorManager = inputDevice.getVibratorManager();
                            if (vibratorManager.getVibratorIds().length > 0) {
                                this.mInputDeviceVibrators.put(
                                        inputDevice.getId(), vibratorManager);
                            }
                        }
                    }
                } else {
                    this.mInputManager.unregisterInputDeviceListener(this);
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}

package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.hardware.input.InputManager;
import android.hardware.input.KeyboardLayout;
import android.view.InputDevice;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class InputMethodManagerCompat {
    private static final InputMethodManagerCompat sInstance = new InputMethodManagerCompat();
    private static final InputMethodManager mInputMethodManager = (InputMethodManager) AppGlobals.getInitialApplication().getSystemService("input_method");
    private static final InputManager mInputManager = InputManager.getInstance();

    private InputMethodManagerCompat() {
    }

    public static InputMethodManagerCompat getInstance() {
        return sInstance;
    }

    public HashMap<String, String> getKeyboardLayoutForInputDevice(String str, InputDevice inputDevice, int i) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (InputMethodInfo inputMethodInfo : mInputMethodManager.getEnabledInputMethodList()) {
            if (str.equals(inputMethodInfo.getId())) {
                for (InputMethodSubtype inputMethodSubtype : mInputMethodManager.getEnabledInputMethodSubtypeList(inputMethodInfo, true)) {
                    InputManager inputManager = mInputManager;
                    String keyboardLayoutForInputDevice = inputManager.getKeyboardLayoutForInputDevice(inputDevice.getIdentifier(), i, inputMethodInfo, inputMethodSubtype);
                    KeyboardLayout[] keyboardLayoutListForInputDevice = inputManager.getKeyboardLayoutListForInputDevice(inputDevice.getIdentifier(), i, inputMethodInfo, inputMethodSubtype);
                    String str2 = "null";
                    if (keyboardLayoutForInputDevice != null) {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= keyboardLayoutListForInputDevice.length) {
                                break;
                            }
                            if (keyboardLayoutListForInputDevice[i2].getDescriptor().equals(keyboardLayoutForInputDevice)) {
                                str2 = keyboardLayoutListForInputDevice[i2].getLabel();
                                break;
                            }
                            i2++;
                        }
                        hashMap.put(inputMethodSubtype.getLocale(), str2);
                    } else {
                        hashMap.put(inputMethodSubtype.getLocale(), "null");
                    }
                }
            }
        }
        return hashMap;
    }

    public void restartInput(View view) {
        mInputMethodManager.restartInput(view);
    }
}

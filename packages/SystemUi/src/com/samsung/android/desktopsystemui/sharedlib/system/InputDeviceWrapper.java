package com.samsung.android.desktopsystemui.sharedlib.system;

import android.hardware.input.InputDeviceIdentifier;
import android.view.InputDevice;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class InputDeviceWrapper {
    private static final int PRODUCT_ID_POGO_KEYBOARD = 41013;
    private static final int VENDOR_ID_SAMSUNG = 1256;

    public static boolean isPogoKeyboardConnected(InputDevice inputDevice) {
        InputDeviceIdentifier identifier = inputDevice.getIdentifier();
        return identifier.getVendorId() == VENDOR_ID_SAMSUNG && identifier.getProductId() == PRODUCT_ID_POGO_KEYBOARD;
    }
}

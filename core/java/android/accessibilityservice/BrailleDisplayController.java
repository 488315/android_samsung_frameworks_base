package android.accessibilityservice;

import android.bluetooth.BluetoothDevice;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.Flags;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public interface BrailleDisplayController {
    public static final String TEST_BRAILLE_DISPLAY_BUS_BLUETOOTH = "BUS_BLUETOOTH";
    public static final String TEST_BRAILLE_DISPLAY_DESCRIPTOR = "DESCRIPTOR";
    public static final String TEST_BRAILLE_DISPLAY_HIDRAW_PATH = "HIDRAW_PATH";
    public static final String TEST_BRAILLE_DISPLAY_NAME = "NAME";
    public static final String TEST_BRAILLE_DISPLAY_UNIQUE_ID = "UNIQUE_ID";

    public interface BrailleDisplayCallback {
        public static final int FLAG_ERROR_BRAILLE_DISPLAY_NOT_FOUND = 2;
        public static final int FLAG_ERROR_CANNOT_ACCESS = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ErrorCode {}

        void onConnected(byte[] bArr);

        void onConnectionFailed(int i);

        void onDisconnected();

        void onInput(byte[] bArr);
    }

    void connect(BluetoothDevice bluetoothDevice, BrailleDisplayCallback brailleDisplayCallback);

    void connect(
            BluetoothDevice bluetoothDevice,
            Executor executor,
            BrailleDisplayCallback brailleDisplayCallback);

    void connect(UsbDevice usbDevice, BrailleDisplayCallback brailleDisplayCallback);

    void connect(
            UsbDevice usbDevice, Executor executor, BrailleDisplayCallback brailleDisplayCallback);

    void disconnect();

    boolean isConnected();

    void write(byte[] bArr) throws IOException;

    static void checkApiFlagIsEnabled() {
        if (!Flags.brailleDisplayHid()) {
            throw new IllegalStateException("Flag BRAILLE_DISPLAY_HID not enabled");
        }
    }

    static void setTestBrailleDisplayData(
            AccessibilityService service, List<Bundle> brailleDisplays) {
        checkApiFlagIsEnabled();
        IAccessibilityServiceConnection serviceConnection =
                AccessibilityInteractionClient.getConnection(service.getConnectionId());
        if (serviceConnection != null) {
            try {
                serviceConnection.setTestBrailleDisplayData(brailleDisplays);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}

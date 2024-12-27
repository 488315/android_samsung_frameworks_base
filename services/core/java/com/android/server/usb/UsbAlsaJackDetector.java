package com.android.server.usb;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UsbAlsaJackDetector implements Runnable {
    public static final String TAG = "UsbAlsaJackDetector";
    public UsbAlsaDevice mAlsaDevice;
    public boolean mStopJackDetect = false;

    public UsbAlsaJackDetector(UsbAlsaDevice usbAlsaDevice) {
        this.mAlsaDevice = usbAlsaDevice;
    }

    private static native boolean nativeHasJackDetect(int i);

    private native boolean nativeInputJackConnected(int i);

    private native boolean nativeJackDetect(int i);

    private native boolean nativeOutputJackConnected(int i);

    public static UsbAlsaJackDetector startJackDetect(UsbAlsaDevice usbAlsaDevice) {
        if (!nativeHasJackDetect(usbAlsaDevice.mCardNum)) {
            return null;
        }
        UsbAlsaJackDetector usbAlsaJackDetector = new UsbAlsaJackDetector(usbAlsaDevice);
        new Thread(usbAlsaJackDetector, "USB jack detect thread").start();
        return usbAlsaJackDetector;
    }

    public final boolean isInputJackConnected() {
        return nativeInputJackConnected(this.mAlsaDevice.mCardNum);
    }

    public final boolean isOutputJackConnected() {
        return nativeOutputJackConnected(this.mAlsaDevice.mCardNum);
    }

    public final boolean jackDetectCallback() {
        synchronized (this) {
            try {
                if (this.mStopJackDetect) {
                    return false;
                }
                UsbAlsaDevice usbAlsaDevice = this.mAlsaDevice;
                synchronized (usbAlsaDevice) {
                    usbAlsaDevice.updateWiredDeviceConnectionState(1, true);
                }
                UsbAlsaDevice usbAlsaDevice2 = this.mAlsaDevice;
                synchronized (usbAlsaDevice2) {
                    usbAlsaDevice2.updateWiredDeviceConnectionState(0, true);
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void pleaseStop() {
        synchronized (this) {
            this.mStopJackDetect = true;
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        nativeJackDetect(this.mAlsaDevice.mCardNum);
    }
}

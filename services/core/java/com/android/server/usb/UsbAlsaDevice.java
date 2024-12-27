package com.android.server.usb;

import android.media.AudioDeviceAttributes;
import android.media.IAudioService;
import android.os.RemoteException;
import android.util.sysfwutil.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UsbAlsaDevice {
    public static final String[] DIRECTION_STR = {"INPUT", "OUTPUT"};
    public final String mAlsaCardDeviceString;
    public final IAudioService mAudioService;
    public final int mCardNum;
    public final String mDeviceAddress;
    public final int[] mDeviceType;
    public final boolean[] mHasDevice;
    public final boolean mIsDock;
    public final boolean[] mIsHeadset;
    public UsbAlsaJackDetector mJackDetector;
    public final boolean[] mIsSelected = new boolean[2];
    public final int[] mState = new int[2];
    public String mDeviceName = "";
    public boolean mHasJackDetect = true;

    public UsbAlsaDevice(
            IAudioService iAudioService,
            int i,
            String str,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            boolean z5) {
        boolean[] zArr = {z2, z};
        this.mHasDevice = zArr;
        this.mIsHeadset = new boolean[] {z3, z4};
        int[] iArr = new int[2];
        this.mDeviceType = iArr;
        this.mAudioService = iAudioService;
        this.mCardNum = i;
        this.mDeviceAddress = str;
        this.mIsDock = z5;
        iArr[0] = zArr[0] ? z3 ? -2113929216 : -2147479552 : 0;
        iArr[1] =
                zArr[1]
                        ? z5
                                ? 4096
                                : z4
                                        ? 67108864
                                        : EndpointMonitorConst
                                                .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION
                        : 0;
        this.mAlsaCardDeviceString = getAlsaCardDeviceString();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof UsbAlsaDevice)) {
            return false;
        }
        UsbAlsaDevice usbAlsaDevice = (UsbAlsaDevice) obj;
        return this.mCardNum == usbAlsaDevice.mCardNum
                && Arrays.equals(this.mHasDevice, usbAlsaDevice.mHasDevice)
                && Arrays.equals(this.mIsHeadset, usbAlsaDevice.mIsHeadset)
                && this.mIsDock == usbAlsaDevice.mIsDock;
    }

    public final String getAlsaCardDeviceString() {
        int i = this.mCardNum;
        if (i >= 0) {
            int i2 = AudioService.BECOMING_NOISY_DELAY_MS;
            return BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "card=", ";device=0");
        }
        Slog.e("UsbAlsaDevice", "Invalid alsa card or device alsaCard: " + i + " alsaDevice: 0");
        return null;
    }

    public final int hashCode() {
        int i = (this.mCardNum + 31) * 961;
        boolean[] zArr = this.mHasDevice;
        int i2 = (((i + (!zArr[1] ? 1 : 0)) * 31) + (!zArr[0] ? 1 : 0)) * 31;
        boolean[] zArr2 = this.mIsHeadset;
        return ((((i2 + (!zArr2[0] ? 1 : 0)) * 31) + (!zArr2[1] ? 1 : 0)) * 31)
                + (!this.mIsDock ? 1 : 0);
    }

    public final void startDevice(int i) {
        boolean[] zArr = this.mIsSelected;
        if (zArr[i]) {
            return;
        }
        zArr[i] = true;
        this.mState[i] = 0;
        synchronized (this) {
            if (this.mJackDetector == null) {
                if (this.mHasJackDetect) {
                    UsbAlsaJackDetector startJackDetect = UsbAlsaJackDetector.startJackDetect(this);
                    this.mJackDetector = startJackDetect;
                    if (startJackDetect == null) {
                        this.mHasJackDetect = false;
                    }
                }
            }
        }
        updateWiredDeviceConnectionState(i, true);
    }

    public final synchronized void stopInput() {
        boolean[] zArr = this.mIsSelected;
        if (zArr[0]) {
            if (!zArr[1]) {
                synchronized (this) {
                    try {
                        UsbAlsaJackDetector usbAlsaJackDetector = this.mJackDetector;
                        if (usbAlsaJackDetector != null) {
                            usbAlsaJackDetector.pleaseStop();
                        }
                        this.mJackDetector = null;
                    } finally {
                    }
                }
            }
            synchronized (this) {
                updateWiredDeviceConnectionState(0, false);
                this.mIsSelected[0] = false;
            }
        }
    }

    public final synchronized void stopOutput() {
        boolean[] zArr = this.mIsSelected;
        if (zArr[1]) {
            if (!zArr[0]) {
                synchronized (this) {
                    try {
                        UsbAlsaJackDetector usbAlsaJackDetector = this.mJackDetector;
                        if (usbAlsaJackDetector != null) {
                            usbAlsaJackDetector.pleaseStop();
                        }
                        this.mJackDetector = null;
                    } finally {
                    }
                }
            }
            synchronized (this) {
                updateWiredDeviceConnectionState(1, false);
                this.mIsSelected[1] = false;
            }
        }
    }

    public final synchronized String toString() {
        return "UsbAlsaDevice: [card: "
                + this.mCardNum
                + ", device: 0, name: "
                + this.mDeviceName
                + ", hasOutput: "
                + this.mHasDevice[1]
                + ", hasInput: "
                + this.mHasDevice[0]
                + "]";
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateWiredDeviceConnectionState(int r7, boolean r8) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.usb.UsbAlsaDevice.updateWiredDeviceConnectionState(int,"
                    + " boolean):boolean");
    }

    public final synchronized void updateWiredDeviceConnectionStateByBundle(boolean z) {
        Slog.d("UsbAlsaDevice", "updateWiredDeviceConnectionStateByBundle()");
        String alsaCardDeviceString = getAlsaCardDeviceString();
        if (alsaCardDeviceString == null) {
            Slog.e("UsbAlsaDevice", "no alsaCardDeviceString");
            return;
        }
        try {
            if (this.mHasDevice[0]) {
                Slog.d(
                        "UsbAlsaDevice",
                        "INPUT addr:" + alsaCardDeviceString + " name:" + this.mDeviceName);
                this.mAudioService.setWiredDeviceConnectionState(
                        new AudioDeviceAttributes(
                                -2113929216, alsaCardDeviceString, this.mDeviceName),
                        z ? 1 : 0,
                        "UsbAlsaDevice");
            }
            if (this.mHasDevice[1]) {
                Slog.d(
                        "UsbAlsaDevice",
                        "OUTPUT addr:" + alsaCardDeviceString + " name:" + this.mDeviceName);
                this.mAudioService.setWiredDeviceConnectionState(
                        new AudioDeviceAttributes(67108864, alsaCardDeviceString, this.mDeviceName),
                        z ? 1 : 0,
                        "UsbAlsaDevice");
            }
        } catch (RemoteException unused) {
            Slog.e("UsbAlsaDevice", "RemoteException in updateWiredDeviceConnectionStateByBundle");
        }
    }
}

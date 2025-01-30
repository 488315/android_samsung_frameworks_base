package com.android.server.usb;

import android.media.AudioDeviceAttributes;
import android.media.IAudioService;
import android.os.RemoteException;
import android.p005os.IInstalld;
import android.util.sysfwutil.Slog;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.server.audio.AudioService;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class UsbAlsaDevice {
    public static final String[] DIRECTION_STR = {KnoxVpnFirewallHelper.INPUT_CHAIN, KnoxVpnFirewallHelper.OUTPUT_CHAIN};
    public final String mAlsaCardDeviceString;
    public IAudioService mAudioService;
    public final int mCardNum;
    public final String mDeviceAddress;
    public final int mDeviceNum;
    public final boolean[] mHasDevice;
    public final boolean mIsDock;
    public final boolean[] mIsHeadset;
    public UsbAlsaJackDetector mJackDetector;
    public final int[] mDeviceType = new int[2];
    public boolean[] mIsSelected = new boolean[2];
    public int[] mState = new int[2];
    public String mDeviceName = "";
    public String mDeviceDescription = "";
    public boolean mHasJackDetect = true;

    public UsbAlsaDevice(IAudioService iAudioService, int i, int i2, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.mHasDevice = new boolean[]{z2, z};
        this.mIsHeadset = new boolean[]{z3, z4};
        this.mAudioService = iAudioService;
        this.mCardNum = i;
        this.mDeviceNum = i2;
        this.mDeviceAddress = str;
        this.mIsDock = z5;
        initDeviceType();
        this.mAlsaCardDeviceString = getAlsaCardDeviceString();
    }

    public int getCardNum() {
        return this.mCardNum;
    }

    public String getDeviceAddress() {
        return this.mDeviceAddress;
    }

    public String getAlsaCardDeviceString() {
        int i;
        int i2 = this.mCardNum;
        if (i2 < 0 || (i = this.mDeviceNum) < 0) {
            Slog.e("UsbAlsaDevice", "Invalid alsa card or device alsaCard: " + this.mCardNum + " alsaDevice: " + this.mDeviceNum);
            return null;
        }
        return AudioService.makeAlsaAddressString(i2, i);
    }

    public final synchronized boolean isInputJackConnected() {
        UsbAlsaJackDetector usbAlsaJackDetector = this.mJackDetector;
        if (usbAlsaJackDetector == null) {
            return true;
        }
        return usbAlsaJackDetector.isInputJackConnected();
    }

    public final synchronized boolean isOutputJackConnected() {
        UsbAlsaJackDetector usbAlsaJackDetector = this.mJackDetector;
        if (usbAlsaJackDetector == null) {
            return true;
        }
        return usbAlsaJackDetector.isOutputJackConnected();
    }

    public final synchronized void startJackDetect() {
        if (this.mJackDetector != null) {
            return;
        }
        if (this.mHasJackDetect) {
            UsbAlsaJackDetector startJackDetect = UsbAlsaJackDetector.startJackDetect(this);
            this.mJackDetector = startJackDetect;
            if (startJackDetect == null) {
                this.mHasJackDetect = false;
            }
        }
    }

    public final synchronized void stopJackDetect() {
        UsbAlsaJackDetector usbAlsaJackDetector = this.mJackDetector;
        if (usbAlsaJackDetector != null) {
            usbAlsaJackDetector.pleaseStop();
        }
        this.mJackDetector = null;
    }

    public synchronized void start() {
        startOutput();
        startInput();
    }

    public synchronized void startInput() {
        startDevice(0);
    }

    public synchronized void startOutput() {
        startDevice(1);
    }

    public final void startDevice(int i) {
        boolean[] zArr = this.mIsSelected;
        if (zArr[i]) {
            return;
        }
        zArr[i] = true;
        this.mState[i] = 0;
        startJackDetect();
        updateWiredDeviceConnectionState(i, true);
    }

    public synchronized void stop() {
        stopOutput();
        stopInput();
    }

    public synchronized void stopInput() {
        boolean[] zArr = this.mIsSelected;
        if (zArr[0]) {
            if (!zArr[1]) {
                stopJackDetect();
            }
            updateInputWiredDeviceConnectionState(false);
            this.mIsSelected[0] = false;
        }
    }

    public synchronized void stopOutput() {
        boolean[] zArr = this.mIsSelected;
        if (zArr[1]) {
            if (!zArr[0]) {
                stopJackDetect();
            }
            updateOutputWiredDeviceConnectionState(false);
            this.mIsSelected[1] = false;
        }
    }

    public final void initDeviceType() {
        int i;
        int[] iArr = this.mDeviceType;
        boolean[] zArr = this.mHasDevice;
        int i2 = 0;
        if (zArr[0]) {
            i = this.mIsHeadset[0] ? -2113929216 : -2147479552;
        } else {
            i = 0;
        }
        iArr[0] = i;
        if (zArr[1]) {
            if (this.mIsDock) {
                i2 = IInstalld.FLAG_USE_QUOTA;
            } else {
                i2 = this.mIsHeadset[1] ? 67108864 : 16384;
            }
        }
        iArr[1] = i2;
    }

    public int getOutputDeviceType() {
        return this.mDeviceType[1];
    }

    public int getInputDeviceType() {
        return this.mDeviceType[0];
    }

    public final boolean updateWiredDeviceConnectionState(int i, boolean z) {
        if (!this.mIsSelected[i]) {
            Slog.e("UsbAlsaDevice", "Updating wired device connection state on unselected device");
            return false;
        }
        if (this.mDeviceType[i] == 0) {
            Slog.d("UsbAlsaDevice", "Unable to set device connection state as " + DIRECTION_STR[i] + " device type is none");
            return false;
        }
        if (this.mAlsaCardDeviceString == null) {
            Slog.w("UsbAlsaDevice", "Failed to update " + DIRECTION_STR[i] + " device connection state failed as alsa card device string is null");
            return false;
        }
        Slog.d("UsbAlsaDevice", "pre-call device:0x" + Integer.toHexString(this.mDeviceType[i]) + " addr:" + this.mAlsaCardDeviceString + " name:" + this.mDeviceName);
        boolean isInputJackConnected = i == 0 ? isInputJackConnected() : isOutputJackConnected();
        Slog.i("UsbAlsaDevice", DIRECTION_STR[i] + " JACK connected: " + isInputJackConnected);
        int i2 = (z && isInputJackConnected) ? 1 : 0;
        int[] iArr = this.mState;
        if (i2 != iArr[i]) {
            iArr[i] = i2;
            try {
                this.mAudioService.setWiredDeviceConnectionState(new AudioDeviceAttributes(this.mDeviceType[i], this.mAlsaCardDeviceString, this.mDeviceName), i2, "UsbAlsaDevice");
            } catch (RemoteException unused) {
                Slog.e("UsbAlsaDevice", "RemoteException in setWiredDeviceConnectionState for " + DIRECTION_STR[i]);
                return false;
            }
        }
        return true;
    }

    public synchronized void updateWiredDeviceConnectionStateByBundle(boolean z) {
        Slog.d("UsbAlsaDevice", "updateWiredDeviceConnectionStateByBundle()");
        String alsaCardDeviceString = getAlsaCardDeviceString();
        if (alsaCardDeviceString == null) {
            Slog.e("UsbAlsaDevice", "no alsaCardDeviceString");
            return;
        }
        try {
            if (this.mHasDevice[0]) {
                Slog.d("UsbAlsaDevice", "INPUT addr:" + alsaCardDeviceString + " name:" + this.mDeviceName);
                this.mAudioService.setWiredDeviceConnectionState(new AudioDeviceAttributes(-2113929216, alsaCardDeviceString, this.mDeviceName), z ? 1 : 0, "UsbAlsaDevice");
            }
            if (this.mHasDevice[1]) {
                Slog.d("UsbAlsaDevice", "OUTPUT addr:" + alsaCardDeviceString + " name:" + this.mDeviceName);
                this.mAudioService.setWiredDeviceConnectionState(new AudioDeviceAttributes(67108864, alsaCardDeviceString, this.mDeviceName), z ? 1 : 0, "UsbAlsaDevice");
            }
        } catch (RemoteException unused) {
            Slog.e("UsbAlsaDevice", "RemoteException in updateWiredDeviceConnectionStateByBundle");
        }
    }

    public synchronized boolean updateInputWiredDeviceConnectionState(boolean z) {
        return updateWiredDeviceConnectionState(0, z);
    }

    public synchronized boolean updateOutputWiredDeviceConnectionState(boolean z) {
        return updateWiredDeviceConnectionState(1, z);
    }

    public synchronized String toString() {
        return "UsbAlsaDevice: [card: " + this.mCardNum + ", device: " + this.mDeviceNum + ", name: " + this.mDeviceName + ", hasOutput: " + this.mHasDevice[1] + ", hasInput: " + this.mHasDevice[0] + "]";
    }

    public synchronized void dump(DualDumpOutputStream dualDumpOutputStream, String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("card", 1120986464257L, this.mCardNum);
        dualDumpOutputStream.write("device", 1120986464258L, this.mDeviceNum);
        dualDumpOutputStream.write("name", 1138166333443L, this.mDeviceName);
        dualDumpOutputStream.write("has_output", 1133871366148L, this.mHasDevice[1]);
        dualDumpOutputStream.write("has_input", 1133871366149L, this.mHasDevice[0]);
        dualDumpOutputStream.write("address", 1138166333446L, this.mDeviceAddress);
        dualDumpOutputStream.end(start);
    }

    public synchronized String toShortString() {
        return "[card:" + this.mCardNum + " device:" + this.mDeviceNum + " " + this.mDeviceName + "]";
    }

    public synchronized void setDeviceNameAndDescription(String str, String str2) {
        this.mDeviceName = str;
        this.mDeviceDescription = str2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UsbAlsaDevice)) {
            return false;
        }
        UsbAlsaDevice usbAlsaDevice = (UsbAlsaDevice) obj;
        return this.mCardNum == usbAlsaDevice.mCardNum && this.mDeviceNum == usbAlsaDevice.mDeviceNum && Arrays.equals(this.mHasDevice, usbAlsaDevice.mHasDevice) && Arrays.equals(this.mIsHeadset, usbAlsaDevice.mIsHeadset) && this.mIsDock == usbAlsaDevice.mIsDock;
    }

    public int hashCode() {
        int i = (((this.mCardNum + 31) * 31) + this.mDeviceNum) * 31;
        boolean[] zArr = this.mHasDevice;
        int i2 = (((i + (!zArr[1] ? 1 : 0)) * 31) + (!zArr[0] ? 1 : 0)) * 31;
        boolean[] zArr2 = this.mIsHeadset;
        return ((((i2 + (!zArr2[0] ? 1 : 0)) * 31) + (!zArr2[1] ? 1 : 0)) * 31) + (!this.mIsDock ? 1 : 0);
    }
}

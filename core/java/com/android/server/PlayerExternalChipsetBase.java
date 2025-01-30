package com.android.server;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbManager;

/* loaded from: classes5.dex */
public abstract class PlayerExternalChipsetBase {
    protected boolean mIsSeeking;
    protected boolean mIsTuning;
    protected FMRadioService mService;
    protected UsbDevice mUsbDevice;
    protected UsbDeviceConnection mUsbDeviceConnection;
    protected UsbEndpoint mUsbEndpoint;
    protected UsbManager mUsbManager;
    protected boolean mIsSeekTuneing = false;
    protected int mCmdTuneFreq = 0;
    protected int mPreviousTuneFreq = 0;

    public abstract void destruct();

    public abstract int getBand();

    public abstract int getChannelSpacing();

    public abstract long getCurrentRSSI();

    public abstract int getDEConstant();

    public abstract int getIntTuningParameter(String str, int i);

    public abstract long getLongTuningParameter(String str, long j);

    public abstract int getRssiThreshold();

    public abstract int getSeekDC();

    public abstract int getSeekQA();

    public abstract boolean getSoundMode();

    public abstract String getStringTuningParameter(String str, String str2);

    public abstract int getTunedFrequency();

    public abstract int getVolume();

    public abstract void init(UsbDevice usbDevice);

    public abstract void initTuningParameters();

    public abstract boolean isAfEnabled();

    public abstract boolean isOn();

    public abstract boolean isRdsEnabled();

    public abstract void muteOff();

    public abstract void muteOn();

    public abstract boolean off();

    /* renamed from: on */
    public abstract boolean mo235on();

    public abstract long searchAll();

    public abstract long seekDown();

    public abstract long seekUp();

    public abstract boolean setAfEnable(boolean z);

    public abstract boolean setBand(int i);

    public abstract boolean setChannelSpacing(int i);

    public abstract boolean setDEConstant(int i);

    public abstract void setIntTuningParameter(String str, int i);

    public abstract void setLongTuningParameter(String str, long j);

    public abstract boolean setRdsEnable(boolean z);

    public abstract void setRecordMode(boolean z);

    public abstract void setRssiThreshold(int i);

    public abstract boolean setSeekDC(int i);

    public abstract boolean setSeekQA(int i);

    public abstract boolean setSoundMode(int i);

    public abstract void setStringTuningParameter(String str, String str2);

    public abstract boolean setVolume(int i);

    public abstract boolean startNotifyThread(boolean z);

    public abstract void stopNotifyThread(boolean z);

    public abstract boolean stopSeek();

    public abstract void tune(int i);
}

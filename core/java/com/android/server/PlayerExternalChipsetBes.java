package com.android.server;

import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.util.Log;

import com.samsung.android.feature.SemFloatingFeature;

import java.util.Arrays;

/* loaded from: classes5.dex */
public abstract class PlayerExternalChipsetBes extends PlayerExternalChipsetBase {
    public static final boolean FEATURE_SUPPORT_RDS =
            SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FMRADIO_SUPPORT_RDS");
    public static final int GET = 162;
    public static final int GET_CURRENT_CHANNEL = 13;
    public static final int GET_CURRENT_FM_BAND = 3;
    public static final int GET_CURRENT_FM_IC_INFO = 16;
    public static final int GET_CURRENT_RSSI = 4;
    public static final int GET_CURRENT_SEEKING_DC_THRESHOLD = 14;
    public static final int GET_CURRENT_SEEKING_SPIKING_THRESHOLD = 15;
    public static final int GET_CURRENT_SPACING = 5;
    public static final int GET_CURRENT_VOLUME = 8;
    public static final int GET_DATA_LENGTH = 2;
    public static final int GET_FM_IC_NO = 1;
    public static final int GET_FM_IC_POWER_ON_STATE = 2;
    public static final int GET_FM_INDEX = 0;
    public static final int GET_FM_PROTOCOL_VERSION = 18;
    public static final int GET_FM_RECORDING_MODE_STATUS = 17;
    public static final int GET_FORCED_MONO_STATE = 7;
    public static final int GET_MUTE_STATE = 6;
    public static final int GET_RDS_STATUS = 10;
    public static final int QUERY = 163;
    public static final int QUERY_RESULT_FAIL = 0;
    public static final int QUERY_RESULT_RDS = 3;
    public static final int QUERY_RESULT_SEEK = 2;
    public static final int QUERY_RESULT_TUNE = 1;
    public static final int READ = 192;
    public static final int SET = 161;
    public static final int SET_CHANNEL = 9;
    public static final int SET_CHAN_RSSI_TH = 2;
    public static final int SET_CHAN_SPACING = 3;
    public static final int SET_DATA_LENGTH = 1;
    public static final int SET_DC_THRES = 11;
    public static final int SET_FM_BAND = 1;
    public static final int SET_FM_IC_POWER_OFF = 0;
    public static final int SET_FM_IC_POWER_ON = 1;
    public static final int SET_FM_IC_RECORDING_POWER_OFF = 0;
    public static final int SET_FM_IC_RECORDING_POWER_ON = 1;
    public static final int SET_MONO_MODE = 6;
    public static final int SET_MUTE = 4;
    public static final int SET_POWER_STATE = 0;
    public static final int SET_RDS = 10;
    public static final int SET_RECORDING_MODE = 14;
    public static final int SET_SEEK_DOWN = 2;
    public static final int SET_SEEK_START = 7;
    public static final int SET_SEEK_STOP = 8;
    public static final int SET_SEEK_UP = 1;
    public static final int SET_SPIKE_THRES = 12;
    public static final int SET_TEST_MODE = 13;
    public static final int SET_TEST_MODE_OFF = 0;
    public static final int SET_TEST_MODE_ON = 1;
    public static final int SET_VOLUME = 5;
    public static final int WRITE = 64;
    protected UsbInterface mCDCInterface;
    protected boolean mIsGettingRds;
    private boolean mIsRunning;
    protected boolean mIsScanning;
    private NotifyWorkerThread notifyWorkerThread;
    private final String TAG = "FMRadioBestechnic";
    protected boolean mIsRDSEnabled = false;
    protected int mSeekFreq = -1;
    protected long mCurrentRssi = -1;
    protected RDSParser rdsParser = RDSParser.getInstance();

    @Override // com.android.server.PlayerExternalChipsetBase
    public long getCurrentRSSI() {
        return this.mCurrentRssi;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] queryInfo() {
        int ret;
        byte[] buffer = new byte[12];
        int ret2 = 0;
        while (ret2 < buffer.length) {
            buffer[ret2] = -1;
            ret2++;
        }
        if (!FEATURE_SUPPORT_RDS) {
            boolean running = true;
            int timeout = 0;
            ret = ret2;
            while (timeout < 400) {
                ret = this.mUsbDeviceConnection.controlTransfer(192, 163, 0, 0, buffer, 12, 4000);
                threadSleep(50L);
                int channel = (buffer[2] & 255) + ((buffer[3] << 8) & 65535);
                if (buffer[1] == 0) {
                    if (timeout < 300) {
                        timeout = 300;
                    }
                } else if (this.mIsTuning) {
                    if (buffer[0] == 1) {
                        running = channel != this.mCmdTuneFreq;
                    }
                } else if (this.mIsSeeking && buffer[0] == 0) {
                    running = channel == this.mPreviousTuneFreq;
                }
                running = running && this.mIsSeekTuneing && !Thread.currentThread().isInterrupted();
                if (!running) {
                    break;
                }
                timeout++;
            }
        } else {
            ret = this.mUsbDeviceConnection.controlTransfer(192, 163, 0, 0, buffer, 12, 4000);
            threadSleep(10L);
        }
        if (ret > 0) {
            return buffer;
        }
        return null;
    }

    protected void initEndpointBes() {
        UsbInterface usbInterface;
        Log.d(
                "FMRadioBestechnic",
                "interface Count - "
                        + this.mUsbDevice.getInterfaceCount()
                        + " End ID - "
                        + this.mUsbDevice
                                .getInterface(this.mUsbDevice.getInterfaceCount() - 1)
                                .getId());
        if (this.mUsbDevice.getInterface(this.mUsbDevice.getInterfaceCount() - 1).getId() == 4
                && (usbInterface =
                                this.mUsbDevice.getInterface(
                                        this.mUsbDevice.getInterfaceCount() - 1))
                        != null) {
            Log.d("FMRadioBestechnic", "claim HID " + usbInterface.toString());
            if (this.mUsbDeviceConnection.claimInterface(usbInterface, true)) {
                this.mCDCInterface = usbInterface;
                for (int j = 0; j < usbInterface.getEndpointCount(); j++) {
                    UsbEndpoint endpoint = usbInterface.getEndpoint(j);
                    switch (endpoint.getType()) {
                        case 3:
                            Log.d(
                                    "FMRadioBestechnic",
                                    "found USB endpoint the Type is  USB_ENDPOINT_XFER_INT");
                            if (endpoint.getDirection() == 0) {
                                Log.d("FMRadioBestechnic", "found USB_DIR_OUT");
                                Log.d(
                                        "FMRadioBestechnic",
                                        "endpoint.getEndpointNumber:"
                                                + endpoint.getEndpointNumber());
                                this.mUsbEndpoint = endpoint;
                                break;
                            } else if (endpoint.getDirection() == 128) {
                                Log.d("FMRadioBestechnic", "found USB_DIR_IN");
                                this.mUsbEndpoint = endpoint;
                                Log.d(
                                        "FMRadioBestechnic",
                                        "endpoint.getEndpointNumber:"
                                                + endpoint.getEndpointNumber());
                                break;
                            } else {
                                break;
                            }
                    }
                }
                return;
            }
            Log.d("FMRadioBestechnic", "Cannot claim interface");
        }
    }

    protected void startNotifyThreadBes() {
        if (this.notifyWorkerThread == null && this.mUsbEndpoint != null) {
            this.notifyWorkerThread = new NotifyWorkerThread();
            this.notifyWorkerThread.start();
            this.mIsRunning = true;
            Log.d("FMRadioBestechnic", "start Notify Thread");
        }
    }

    protected void stopNotifyThreadBes() {
        if (this.notifyWorkerThread != null) {
            this.notifyWorkerThread.terminate();
            this.notifyWorkerThread = null;
            Log.d("FMRadioBestechnic", "Notify Thread is stopped");
        }
    }

    protected void releaseInterfaceBes() {
        Log.d("FMRadioBestechnic", "release()");
        if (this.mUsbDeviceConnection != null && this.mCDCInterface != null) {
            this.mUsbDeviceConnection.releaseInterface(this.mCDCInterface);
            this.mCDCInterface = null;
        }
    }

    private class NotifyWorkerThread extends Thread {
        NotifyWorkerThread() {
            super("FMNotifyWorkerThread");
        }

        private int verifyInfo(byte[] datas) {
            byte[] num = {1, 0, 8, 0, 0};
            if (PlayerExternalChipsetBes.this.startsWith(datas, num)
                    && PlayerExternalChipsetBes.this.mUsbDevice.getDeviceClass() != 2) {
                byte[] buffer = PlayerExternalChipsetBes.this.queryInfo();
                if (buffer != null) {
                    Log.d(
                            "FMRadioBestechnic",
                            "buffer: " + PlayerExternalChipsetBes.this.toHex(buffer));
                }
                if (buffer == null) {
                    return 0;
                }
                Log.d("FMRadioBestechnic", "has result");
                if (PlayerExternalChipsetBes.this.startsWith(buffer, new byte[] {1, 1})
                        || PlayerExternalChipsetBes.this.startsWith(buffer, new byte[] {1, 0})) {
                    if (!PlayerExternalChipsetBes.this.startsWith(buffer, new byte[] {1, 0})) {
                        int i = (buffer[2] & 255) + ((buffer[3] << 8) & 65535);
                        PlayerExternalChipsetBes.this.mCurrentRssi = Byte.toUnsignedInt(buffer[4]);
                    } else {
                        Log.d("FMRadioBestechnic", "tune fail");
                    }
                    return 1;
                }
                if (PlayerExternalChipsetBes.this.startsWith(buffer, new byte[] {0, 1})
                        || PlayerExternalChipsetBes.this.startsWith(buffer, new byte[] {0, 0})) {
                    if (!PlayerExternalChipsetBes.this.startsWith(buffer, new byte[] {0, 0})) {
                        int chan = (buffer[2] & 255) + ((buffer[3] << 8) & 65535);
                        PlayerExternalChipsetBes.this.mCurrentRssi = Byte.toUnsignedInt(buffer[4]);
                        if (chan > 10800 || chan < 8700) {
                            PlayerExternalChipsetBes.this.mSeekFreq = -1;
                        }
                        PlayerExternalChipsetBes.this.mSeekFreq = chan;
                    } else {
                        Log.d("FMRadioBestechnic", "seek fail");
                    }
                    return 2;
                }
                if (PlayerExternalChipsetBes.this.startsWith(buffer, new byte[] {2})
                        || PlayerExternalChipsetBes.this.mIsRDSEnabled) {
                    ExtRDSData radio_data = new ExtRDSData(buffer);
                    PlayerExternalChipsetBes.this.rdsParser.parseData(radio_data);
                    if (PlayerExternalChipsetBes.this.rdsParser.isRDSDataValid()) {
                        Log.d(
                                "FMRadioBestechnic",
                                "RDSDataValid, PS: "
                                        + PlayerExternalChipsetBes.this.rdsParser
                                                .getProgramService()
                                        + " - RT: "
                                        + PlayerExternalChipsetBes.this.rdsParser.getRadioText());
                        FMPlayerNativeBase.RDSData RDSdata =
                                new FMPlayerNativeBase.RDSData(
                                        PlayerExternalChipsetBes.this.getTunedFrequency(),
                                        PlayerExternalChipsetBes.this.rdsParser.getProgramService(),
                                        PlayerExternalChipsetBes.this.rdsParser.getRadioText());
                        Log.d("FMRadioBestechnic", RDSdata.toString());
                        PlayerExternalChipsetBes.this.mService.notifyEvent(10, RDSdata);
                    }
                    return 3;
                }
            }
            return 0;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Log.d("FMRadioBestechnic", "notify thread is running");
            int length = PlayerExternalChipsetBes.this.mUsbEndpoint.getMaxPacketSize();
            byte[] buffer = new byte[length];
            for (int ret = 0; ret < length; ret++) {
                buffer[ret] = -1;
            }
            int count = 0;
            while (true) {
                if (PlayerExternalChipsetBes.this.mIsRDSEnabled
                        || (PlayerExternalChipsetBes.this.mIsRunning
                                && !Thread.currentThread().isInterrupted())) {
                    int ret2 =
                            PlayerExternalChipsetBes.this.mUsbDeviceConnection.bulkTransfer(
                                    PlayerExternalChipsetBes.this.mUsbEndpoint,
                                    buffer,
                                    length,
                                    1000);
                    if (ret2 < 0) {
                        count++;
                        if (count == 50 && PlayerExternalChipsetBes.this.mUsbDevice != null) {
                            count = 0;
                            PlayerExternalChipsetBes.this.releaseInterfaceBes();
                            PlayerExternalChipsetBes.this.initEndpointBes();
                        }
                    } else if (ret2 > 0) {
                        count = 0;
                        Log.d(
                                "FMRadioBestechnic",
                                "Received NOTIFY: " + PlayerExternalChipsetBes.this.toHex(buffer));
                        int infoRet = verifyInfo(Arrays.copyOfRange(buffer, 0, ret2));
                        switch (infoRet) {
                            case 0:
                                Log.d("FMRadioBestechnic", "no result complete");
                                break;
                            case 1:
                            case 2:
                                Log.d("FMRadioBestechnic", "seek or tune complete");
                                PlayerExternalChipsetBes.this.mIsSeekTuneing = false;
                                break;
                            case 3:
                                Log.d("FMRadioBestechnic", "rds segment complete");
                                PlayerExternalChipsetBes.this.mIsGettingRds = false;
                                break;
                        }
                    }
                    PlayerExternalChipsetBes.this.threadSleep(10L);
                } else {
                    return;
                }
            }
        }

        void terminate() {
            PlayerExternalChipsetBes.this.mIsRunning = false;
            if (isAlive()) {
                interrupt();
            }
        }
    }

    protected boolean startsWith(byte[] data, byte[] param) {
        if (data == null) {
            return param == null;
        }
        if (param == null) {
            return true;
        }
        if (data.length < param.length) {
            return false;
        }
        for (int i = 0; i < param.length; i++) {
            if (data[i] != param[i]) {
                return false;
            }
        }
        return true;
    }

    protected void threadSleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Log.d("FMRadioBestechnic", "Thread sleep interrupted");
        }
    }

    protected String toHex(byte[] data) {
        StringBuffer buffer = new StringBuffer();
        for (byte b : data) {
            buffer.append(String.format("%02x", Byte.valueOf(b))).append(",");
        }
        return buffer.toString();
    }

    public static int bytesToInt(byte[] data) {
        if (data.length < 2) {
            return -1;
        }
        int ret = (data[0] & 255) + ((data[1] << 8) & 65535);
        return ret;
    }
}

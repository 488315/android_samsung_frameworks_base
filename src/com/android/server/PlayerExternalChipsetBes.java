package com.android.server;

import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.util.Log;
import com.android.server.FMPlayerNativeBase;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.Arrays;

/* loaded from: classes6.dex */
public abstract class PlayerExternalChipsetBes extends PlayerExternalChipsetBase {
    public static final boolean FEATURE_SUPPORT_RDS = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FMRADIO_SUPPORT_RDS");
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
    /* JADX WARN: Removed duplicated region for block: B:21:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public byte[] queryInfo() throws InterruptedException {
        byte[] bArr = new byte[12];
        int iControlTransfer = 0;
        while (iControlTransfer < 12) {
            bArr[iControlTransfer] = -1;
            iControlTransfer++;
        }
        if (FEATURE_SUPPORT_RDS) {
            iControlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 163, 0, 0, bArr, 12, 4000);
            threadSleep(10L);
        } else {
            boolean z = true;
            int i = 0;
            while (i < 400) {
                iControlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 163, 0, 0, bArr, 12, 4000);
                threadSleep(50L);
                int i2 = (bArr[2] & 255) + ((bArr[3] << 8) & 65535);
                if (bArr[1] == 0) {
                    if (i < 300) {
                        i = 300;
                    }
                } else if (this.mIsTuning) {
                    if (bArr[0] == 1) {
                        z = i2 != this.mCmdTuneFreq;
                    }
                } else if (this.mIsSeeking && bArr[0] == 0) {
                    if (i2 == this.mPreviousTuneFreq) {
                    }
                }
                z = z && this.mIsSeekTuneing && !Thread.currentThread().isInterrupted();
                if (!z) {
                    break;
                }
                i++;
            }
        }
        if (iControlTransfer > 0) {
            return bArr;
        }
        return null;
    }

    protected void initEndpointBes() {
        UsbInterface usbInterface;
        Log.d("FMRadioBestechnic", "interface Count - " + this.mUsbDevice.getInterfaceCount() + " End ID - " + this.mUsbDevice.getInterface(this.mUsbDevice.getInterfaceCount() - 1).getId());
        if (this.mUsbDevice.getInterface(this.mUsbDevice.getInterfaceCount() - 1).getId() == 4 && (usbInterface = this.mUsbDevice.getInterface(this.mUsbDevice.getInterfaceCount() - 1)) != null) {
            Log.d("FMRadioBestechnic", "claim HID " + usbInterface.toString());
            if (this.mUsbDeviceConnection.claimInterface(usbInterface, true)) {
                this.mCDCInterface = usbInterface;
                for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
                    UsbEndpoint endpoint = usbInterface.getEndpoint(i);
                    if (endpoint.getType() == 3) {
                        Log.d("FMRadioBestechnic", "found USB endpoint the Type is  USB_ENDPOINT_XFER_INT");
                        if (endpoint.getDirection() == 0) {
                            Log.d("FMRadioBestechnic", "found USB_DIR_OUT");
                            Log.d("FMRadioBestechnic", "endpoint.getEndpointNumber:" + endpoint.getEndpointNumber());
                            this.mUsbEndpoint = endpoint;
                        } else if (endpoint.getDirection() == 128) {
                            Log.d("FMRadioBestechnic", "found USB_DIR_IN");
                            this.mUsbEndpoint = endpoint;
                            Log.d("FMRadioBestechnic", "endpoint.getEndpointNumber:" + endpoint.getEndpointNumber());
                        }
                    }
                }
                return;
            }
            Log.d("FMRadioBestechnic", "Cannot claim interface");
        }
    }

    protected void startNotifyThreadBes() {
        if (this.notifyWorkerThread != null || this.mUsbEndpoint == null) {
            return;
        }
        NotifyWorkerThread notifyWorkerThread = new NotifyWorkerThread();
        this.notifyWorkerThread = notifyWorkerThread;
        notifyWorkerThread.start();
        this.mIsRunning = true;
        Log.d("FMRadioBestechnic", "start Notify Thread");
    }

    protected void stopNotifyThreadBes() {
        NotifyWorkerThread notifyWorkerThread = this.notifyWorkerThread;
        if (notifyWorkerThread != null) {
            notifyWorkerThread.terminate();
            this.notifyWorkerThread = null;
            Log.d("FMRadioBestechnic", "Notify Thread is stopped");
        }
    }

    protected void releaseInterfaceBes() {
        Log.d("FMRadioBestechnic", "release()");
        if (this.mUsbDeviceConnection == null || this.mCDCInterface == null) {
            return;
        }
        this.mUsbDeviceConnection.releaseInterface(this.mCDCInterface);
        this.mCDCInterface = null;
    }

    private class NotifyWorkerThread extends Thread {
        NotifyWorkerThread() {
            super("FMNotifyWorkerThread");
        }

        private int verifyInfo(byte[] bArr) throws InterruptedException {
            if (PlayerExternalChipsetBes.this.startsWith(bArr, new byte[]{1, 0, 8, 0, 0}) && PlayerExternalChipsetBes.this.mUsbDevice.getDeviceClass() != 2) {
                byte[] bArrQueryInfo = PlayerExternalChipsetBes.this.queryInfo();
                if (bArrQueryInfo != null) {
                    Log.d("FMRadioBestechnic", "buffer: " + PlayerExternalChipsetBes.this.toHex(bArrQueryInfo));
                }
                if (bArrQueryInfo == null) {
                    return 0;
                }
                Log.d("FMRadioBestechnic", "has result");
                if (PlayerExternalChipsetBes.this.startsWith(bArrQueryInfo, new byte[]{1, 1}) || PlayerExternalChipsetBes.this.startsWith(bArrQueryInfo, new byte[]{1, 0})) {
                    if (PlayerExternalChipsetBes.this.startsWith(bArrQueryInfo, new byte[]{1, 0})) {
                        Log.d("FMRadioBestechnic", "tune fail");
                    } else {
                        byte b = bArrQueryInfo[2];
                        byte b2 = bArrQueryInfo[3];
                        PlayerExternalChipsetBes.this.mCurrentRssi = Byte.toUnsignedInt(bArrQueryInfo[4]);
                    }
                    return 1;
                }
                if (PlayerExternalChipsetBes.this.startsWith(bArrQueryInfo, new byte[]{0, 1}) || PlayerExternalChipsetBes.this.startsWith(bArrQueryInfo, new byte[]{0, 0})) {
                    if (PlayerExternalChipsetBes.this.startsWith(bArrQueryInfo, new byte[]{0, 0})) {
                        Log.d("FMRadioBestechnic", "seek fail");
                    } else {
                        int i = (bArrQueryInfo[2] & 255) + ((bArrQueryInfo[3] << 8) & 65535);
                        PlayerExternalChipsetBes.this.mCurrentRssi = Byte.toUnsignedInt(bArrQueryInfo[4]);
                        if (i > 10800 || i < 8700) {
                            PlayerExternalChipsetBes.this.mSeekFreq = -1;
                        }
                        PlayerExternalChipsetBes.this.mSeekFreq = i;
                    }
                    return 2;
                }
                if (PlayerExternalChipsetBes.this.startsWith(bArrQueryInfo, new byte[]{2}) || PlayerExternalChipsetBes.this.mIsRDSEnabled) {
                    PlayerExternalChipsetBes.this.rdsParser.parseData(new ExtRDSData(bArrQueryInfo));
                    if (PlayerExternalChipsetBes.this.rdsParser.isRDSDataValid()) {
                        Log.d("FMRadioBestechnic", "RDSDataValid, PS: " + PlayerExternalChipsetBes.this.rdsParser.getProgramService() + " - RT: " + PlayerExternalChipsetBes.this.rdsParser.getRadioText());
                        FMPlayerNativeBase.RDSData rDSData = new FMPlayerNativeBase.RDSData((long) PlayerExternalChipsetBes.this.getTunedFrequency(), PlayerExternalChipsetBes.this.rdsParser.getProgramService(), PlayerExternalChipsetBes.this.rdsParser.getRadioText());
                        Log.d("FMRadioBestechnic", rDSData.toString());
                        PlayerExternalChipsetBes.this.mService.notifyEvent(10, rDSData);
                    }
                    return 3;
                }
            }
            return 0;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws InterruptedException {
            Log.d("FMRadioBestechnic", "notify thread is running");
            int maxPacketSize = PlayerExternalChipsetBes.this.mUsbEndpoint.getMaxPacketSize();
            byte[] bArr = new byte[maxPacketSize];
            for (int i = 0; i < maxPacketSize; i++) {
                bArr[i] = -1;
            }
            int i2 = 0;
            while (true) {
                if (!PlayerExternalChipsetBes.this.mIsRDSEnabled && (!PlayerExternalChipsetBes.this.mIsRunning || Thread.currentThread().isInterrupted())) {
                    return;
                }
                int iBulkTransfer = PlayerExternalChipsetBes.this.mUsbDeviceConnection.bulkTransfer(PlayerExternalChipsetBes.this.mUsbEndpoint, bArr, maxPacketSize, 1000);
                if (iBulkTransfer < 0) {
                    i2++;
                    if (i2 == 50 && PlayerExternalChipsetBes.this.mUsbDevice != null) {
                        PlayerExternalChipsetBes.this.releaseInterfaceBes();
                        PlayerExternalChipsetBes.this.initEndpointBes();
                        i2 = 0;
                    }
                } else if (iBulkTransfer > 0) {
                    Log.d("FMRadioBestechnic", "Received NOTIFY: " + PlayerExternalChipsetBes.this.toHex(bArr));
                    int iVerifyInfo = verifyInfo(Arrays.copyOfRange(bArr, 0, iBulkTransfer));
                    if (iVerifyInfo == 0) {
                        Log.d("FMRadioBestechnic", "no result complete");
                    } else if (iVerifyInfo == 1 || iVerifyInfo == 2) {
                        Log.d("FMRadioBestechnic", "seek or tune complete");
                        PlayerExternalChipsetBes.this.mIsSeekTuneing = false;
                    } else if (iVerifyInfo == 3) {
                        Log.d("FMRadioBestechnic", "rds segment complete");
                        PlayerExternalChipsetBes.this.mIsGettingRds = false;
                    }
                    i2 = 0;
                }
                PlayerExternalChipsetBes.this.threadSleep(10L);
            }
        }

        void terminate() {
            PlayerExternalChipsetBes.this.mIsRunning = false;
            if (isAlive()) {
                interrupt();
            }
        }
    }

    protected boolean startsWith(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return bArr2 == null;
        }
        if (bArr2 == null) {
            return true;
        }
        if (bArr.length < bArr2.length) {
            return false;
        }
        for (int i = 0; i < bArr2.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    protected void threadSleep(long j) throws InterruptedException {
        try {
            Thread.sleep(j);
        } catch (InterruptedException unused) {
            Log.d("FMRadioBestechnic", "Thread sleep interrupted");
        }
    }

    protected String toHex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(String.format("%02x", Byte.valueOf(b))).append(",");
        }
        return stringBuffer.toString();
    }

    public static int bytesToInt(byte[] bArr) {
        if (bArr.length < 2) {
            return -1;
        }
        return (bArr[0] & 255) + ((bArr[1] << 8) & 65535);
    }
}

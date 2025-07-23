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
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0051, code lost:
    
        if (r2 != r12.mCmdTuneFreq) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0053, code lost:
    
        r11 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0055, code lost:
    
        r11 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0061, code lost:
    
        if (r2 == r12.mPreviousTuneFreq) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] queryInfo() {
        /*
            r12 = this;
            r0 = 12
            byte[] r6 = new byte[r0]
            r9 = 0
            r1 = r9
        L6:
            if (r1 >= r0) goto Le
            r2 = -1
            r6[r1] = r2
            int r1 = r1 + 1
            goto L6
        Le:
            boolean r0 = com.android.server.PlayerExternalChipsetBes.FEATURE_SUPPORT_RDS
            if (r0 != 0) goto L7c
            r0 = 1
            r11 = r0
            r10 = r9
        L15:
            r2 = 400(0x190, float:5.6E-43)
            if (r10 >= r2) goto L91
            android.hardware.usb.UsbDeviceConnection r1 = r12.mUsbDeviceConnection
            r7 = 12
            r8 = 4000(0xfa0, float:5.605E-42)
            r2 = 192(0xc0, float:2.69E-43)
            r3 = 163(0xa3, float:2.28E-43)
            r4 = 0
            r5 = 0
            int r1 = r1.controlTransfer(r2, r3, r4, r5, r6, r7, r8)
            r2 = 50
            r12.threadSleep(r2)
            r2 = 2
            r2 = r6[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            r3 = 3
            r3 = r6[r3]
            int r3 = r3 << 8
            r4 = 65535(0xffff, float:9.1834E-41)
            r3 = r3 & r4
            int r2 = r2 + r3
            r3 = r6[r0]
            if (r3 != 0) goto L47
            r2 = 300(0x12c, float:4.2E-43)
            if (r10 >= r2) goto L64
            r10 = r2
            goto L64
        L47:
            boolean r3 = r12.mIsTuning
            if (r3 == 0) goto L57
            r3 = r6[r9]
            if (r3 != r0) goto L64
            int r3 = r12.mCmdTuneFreq
            if (r2 == r3) goto L55
        L53:
            r11 = r0
            goto L64
        L55:
            r11 = r9
            goto L64
        L57:
            boolean r3 = r12.mIsSeeking
            if (r3 == 0) goto L64
            r3 = r6[r9]
            if (r3 != 0) goto L64
            int r3 = r12.mPreviousTuneFreq
            if (r2 != r3) goto L55
            goto L53
        L64:
            if (r11 == 0) goto L76
            boolean r2 = r12.mIsSeekTuneing
            if (r2 == 0) goto L76
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            boolean r2 = r2.isInterrupted()
            if (r2 != 0) goto L76
            r11 = r0
            goto L77
        L76:
            r11 = r9
        L77:
            if (r11 != 0) goto L7a
            goto L91
        L7a:
            int r10 = r10 + r0
            goto L15
        L7c:
            android.hardware.usb.UsbDeviceConnection r1 = r12.mUsbDeviceConnection
            r7 = 12
            r8 = 4000(0xfa0, float:5.605E-42)
            r2 = 192(0xc0, float:2.69E-43)
            r3 = 163(0xa3, float:2.28E-43)
            r4 = 0
            r5 = 0
            int r1 = r1.controlTransfer(r2, r3, r4, r5, r6, r7, r8)
            r2 = 10
            r12.threadSleep(r2)
        L91:
            if (r1 <= 0) goto L94
            return r6
        L94:
            r12 = 0
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.PlayerExternalChipsetBes.queryInfo():byte[]");
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

        private int verifyInfo(byte[] bArr) {
            if (PlayerExternalChipsetBes.this.startsWith(bArr, new byte[]{1, 0, 8, 0, 0}) && PlayerExternalChipsetBes.this.mUsbDevice.getDeviceClass() != 2) {
                byte[] queryInfo = PlayerExternalChipsetBes.this.queryInfo();
                if (queryInfo != null) {
                    Log.d("FMRadioBestechnic", "buffer: " + PlayerExternalChipsetBes.this.toHex(queryInfo));
                }
                if (queryInfo == null) {
                    return 0;
                }
                Log.d("FMRadioBestechnic", "has result");
                if (PlayerExternalChipsetBes.this.startsWith(queryInfo, new byte[]{1, 1}) || PlayerExternalChipsetBes.this.startsWith(queryInfo, new byte[]{1, 0})) {
                    if (PlayerExternalChipsetBes.this.startsWith(queryInfo, new byte[]{1, 0})) {
                        Log.d("FMRadioBestechnic", "tune fail");
                    } else {
                        byte b = queryInfo[2];
                        byte b2 = queryInfo[3];
                        PlayerExternalChipsetBes.this.mCurrentRssi = Byte.toUnsignedInt(queryInfo[4]);
                    }
                    return 1;
                }
                if (PlayerExternalChipsetBes.this.startsWith(queryInfo, new byte[]{0, 1}) || PlayerExternalChipsetBes.this.startsWith(queryInfo, new byte[]{0, 0})) {
                    if (PlayerExternalChipsetBes.this.startsWith(queryInfo, new byte[]{0, 0})) {
                        Log.d("FMRadioBestechnic", "seek fail");
                    } else {
                        int i = (queryInfo[2] & 255) + ((queryInfo[3] << 8) & 65535);
                        PlayerExternalChipsetBes.this.mCurrentRssi = Byte.toUnsignedInt(queryInfo[4]);
                        if (i > 10800 || i < 8700) {
                            PlayerExternalChipsetBes.this.mSeekFreq = -1;
                        }
                        PlayerExternalChipsetBes.this.mSeekFreq = i;
                    }
                    return 2;
                }
                if (PlayerExternalChipsetBes.this.startsWith(queryInfo, new byte[]{2}) || PlayerExternalChipsetBes.this.mIsRDSEnabled) {
                    PlayerExternalChipsetBes.this.rdsParser.parseData(new ExtRDSData(queryInfo));
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
        public void run() {
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
                int bulkTransfer = PlayerExternalChipsetBes.this.mUsbDeviceConnection.bulkTransfer(PlayerExternalChipsetBes.this.mUsbEndpoint, bArr, maxPacketSize, 1000);
                if (bulkTransfer < 0) {
                    i2++;
                    if (i2 == 50 && PlayerExternalChipsetBes.this.mUsbDevice != null) {
                        PlayerExternalChipsetBes.this.releaseInterfaceBes();
                        PlayerExternalChipsetBes.this.initEndpointBes();
                        i2 = 0;
                    }
                    PlayerExternalChipsetBes.this.threadSleep(10L);
                } else if (bulkTransfer > 0) {
                    Log.d("FMRadioBestechnic", "Received NOTIFY: " + PlayerExternalChipsetBes.this.toHex(bArr));
                    int verifyInfo = verifyInfo(Arrays.copyOfRange(bArr, 0, bulkTransfer));
                    if (verifyInfo == 0) {
                        Log.d("FMRadioBestechnic", "no result complete");
                    } else if (verifyInfo == 1 || verifyInfo == 2) {
                        Log.d("FMRadioBestechnic", "seek or tune complete");
                        PlayerExternalChipsetBes.this.mIsSeekTuneing = false;
                    } else if (verifyInfo == 3) {
                        Log.d("FMRadioBestechnic", "rds segment complete");
                        PlayerExternalChipsetBes.this.mIsGettingRds = false;
                    }
                    i2 = 0;
                    PlayerExternalChipsetBes.this.threadSleep(10L);
                } else {
                    PlayerExternalChipsetBes.this.threadSleep(10L);
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

    protected void threadSleep(long j) {
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

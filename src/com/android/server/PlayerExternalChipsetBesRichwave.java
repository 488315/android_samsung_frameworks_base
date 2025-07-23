package com.android.server;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import com.samsung.android.common.AsPackageName;

/* loaded from: classes6.dex */
public class PlayerExternalChipsetBesRichwave extends PlayerExternalChipsetBes implements RichwaveTuningParameteres {
    private static final int SEEKTUNE_TIMEOUT = 2100;
    private static final String TAG = "FMRadioExtRichwave";
    private boolean isThreadRunning;
    private Context mContext;
    private boolean mNeedToStopSeek;
    private boolean restoreRdsForScan;
    private boolean mNeedOnTestMode = true;
    private int[] registerList = {3, 4, 5, 7, 8, 9, 10, 11, 16, 18, 19, 20, 21, 43, 44, 45};

    public PlayerExternalChipsetBesRichwave(Context context, FMRadioService fMRadioService) {
        this.mContext = context;
        this.mService = fMRadioService;
    }

    @Override // com.android.server.PlayerExternalChipsetBase, com.android.server.CommonTuningParamters
    public void setRssiThreshold(int i) {
        log("setRssiThreshold" + i);
        if (openConnection()) {
            byte[] bArr = {-1};
            log("set_rssith_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 161, 2, i, bArr, 1, 4000) + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
            threadSleep(15L);
            closeConnection();
        }
    }

    @Override // com.android.server.PlayerExternalChipsetBase, com.android.server.CommonTuningParamters
    public int getRssiThreshold() {
        log("getRssiThreshold");
        if (!openConnection()) {
            return -1;
        }
        byte[] bArr = {-1, -1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 162, 4, 0, bArr, 2, 4000);
        log("get_rssith_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        if (controlTransfer == 2) {
            return bytesToInt(bArr);
        }
        return -1;
    }

    @Override // com.android.server.CommonTuningParamters
    public void setSnrThreshold(int i) {
        log("setSnrThreshold");
    }

    @Override // com.android.server.CommonTuningParamters
    public int getSnrThreshold() {
        log("setSnrThreshold");
        return 0;
    }

    @Override // com.android.server.CommonTuningParamters
    public void setCntThreshold(int i) {
        log("setSnrThreshold");
    }

    @Override // com.android.server.CommonTuningParamters
    public int getCntThreshold() {
        log("getCntThreshold");
        return 0;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void init(UsbDevice usbDevice) {
        log("init");
        if (this.mUsbManager == null) {
            this.mUsbManager = (UsbManager) this.mContext.getSystemService("usb");
        }
        this.mUsbDevice = usbDevice;
        if (this.mUsbDevice != null) {
            log("mUsbDevice: " + this.mUsbDevice.toString());
        } else {
            this.mIsTuning = false;
            this.mIsSeekTuneing = false;
            this.mIsScanning = false;
        }
    }

    private void printFMICInfo() {
        int i = 0;
        while (true) {
            int[] iArr = this.registerList;
            if (i >= iArr.length) {
                return;
            }
            getFMICDebugInfo(iArr[i]);
            i++;
        }
    }

    private void setTestMode(int i) {
        log("setTestMode: " + i);
        byte[] bArr = {-1};
        log("test_mode ret: " + this.mUsbDeviceConnection.controlTransfer(192, 161, 13, i, bArr, 1, 4000) + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        threadSleep(20L);
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean on() {
        log("on");
        boolean z = false;
        if (!openConnection()) {
            return false;
        }
        getFirmwareVersion();
        byte[] bArr = new byte[2];
        log("recording_status_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 162, 17, 0, bArr, 2, 0) + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        if (startsWith(bArr, new byte[]{1})) {
            log("Do not turn on FM in playback mode cause current mode are recording");
            return false;
        }
        byte[] bArr2 = new byte[2];
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 162, 1, 0, bArr2, 2, 0);
        log("ret: " + controlTransfer + ", data: " + toHex(bArr2) + ", ascii: " + toASCII(bArr2));
        if (controlTransfer == 2 && startsWith(bArr2, new byte[]{1})) {
            byte[] bArr3 = {-1};
            log("on set power state begin");
            log("open_fm_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 161, 0, 1, bArr3, 1, 4000) + ", data: " + toHex(bArr3) + ", ascii: " + toASCII(bArr3));
            threadSleep(20L);
            log("on set power state done");
            byte[] bArr4 = {-1, -1};
            log("fm_state_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 162, 2, 0, bArr4, 2, 4000) + ", data: " + toHex(bArr4) + ", ascii: " + toASCII(bArr4));
            if (startsWith(bArr4, new byte[]{1})) {
                z = true;
            }
        }
        closeConnection();
        return z;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean off() {
        log("off");
        this.mIsSeekTuneing = false;
        if (!openConnection()) {
            return false;
        }
        byte[] bArr = {-1};
        log("off_fm_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 161, 0, 0, bArr, 1, 4000) + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        threadSleep(50L);
        log("off set power state done");
        byte[] bArr2 = new byte[1];
        log("set_record_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 161, 14, 0, bArr2, 1, 4000) + ", data: " + toHex(bArr2) + ", ascii: " + toASCII(bArr2));
        threadSleep(50L);
        byte[] bArr3 = {-1, -1};
        log("fm_state_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 162, 2, 0, bArr3, 2, 4000) + ", data: " + toHex(bArr3) + ", ascii: " + toASCII(bArr3));
        byte[] bArr4 = new byte[2];
        log("recording_status_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 162, 17, 0, bArr4, 2, 0) + ", data: " + toHex(bArr4) + ", ascii: " + toASCII(bArr4));
        closeConnection();
        return startsWith(bArr3, new byte[]{0}) && startsWith(bArr4, new byte[]{0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean isOn() {
        log("isOn");
        if (!openConnection()) {
            return false;
        }
        byte[] bArr = {-1, -1};
        log("fm_state_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 162, 2, 0, bArr, 2, 4000) + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        byte[] bArr2 = {-1, -1};
        log("recording_status_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 162, 17, 0, bArr2, 2, 0) + ", data: " + toHex(bArr2) + ", ascii: " + toASCII(bArr2));
        closeConnection();
        return startsWith(bArr, new byte[]{1}) || startsWith(bArr2, new byte[]{1});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void tune(int i) {
        seekTune(9, i);
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public int getTunedFrequency() {
        log("getTunedFrequency");
        if (!openConnection()) {
            return -1;
        }
        byte[] bArr = {-1, -1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 162, 13, 0, bArr, 2, 4000);
        log("getTunedFrequency ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        if (controlTransfer == 2) {
            return bytesToInt(bArr);
        }
        return -1;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public long seekUp() {
        log("seekUp");
        seekTune(7, 1);
        return this.mSeekFreq;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public long seekDown() {
        log("seekDown");
        seekTune(7, 2);
        return this.mSeekFreq;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0088 A[Catch: all -> 0x00d4, TryCatch #0 {, blocks: (B:6:0x0008, B:7:0x0015, B:9:0x0019, B:10:0x001e, B:12:0x0025, B:17:0x002d, B:19:0x0031, B:20:0x0037, B:21:0x0083, B:23:0x0088, B:26:0x0092, B:28:0x0096, B:29:0x0099, B:32:0x00ac, B:34:0x00b8, B:36:0x00be, B:37:0x00c6, B:40:0x00cc, B:41:0x00cf, B:45:0x00c4, B:50:0x000d), top: B:4:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b8 A[Catch: all -> 0x00d4, TryCatch #0 {, blocks: (B:6:0x0008, B:7:0x0015, B:9:0x0019, B:10:0x001e, B:12:0x0025, B:17:0x002d, B:19:0x0031, B:20:0x0037, B:21:0x0083, B:23:0x0088, B:26:0x0092, B:28:0x0096, B:29:0x0099, B:32:0x00ac, B:34:0x00b8, B:36:0x00be, B:37:0x00c6, B:40:0x00cc, B:41:0x00cf, B:45:0x00c4, B:50:0x000d), top: B:4:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c4 A[Catch: all -> 0x00d4, TryCatch #0 {, blocks: (B:6:0x0008, B:7:0x0015, B:9:0x0019, B:10:0x001e, B:12:0x0025, B:17:0x002d, B:19:0x0031, B:20:0x0037, B:21:0x0083, B:23:0x0088, B:26:0x0092, B:28:0x0096, B:29:0x0099, B:32:0x00ac, B:34:0x00b8, B:36:0x00be, B:37:0x00c6, B:40:0x00cc, B:41:0x00cf, B:45:0x00c4, B:50:0x000d), top: B:4:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0099 A[EDGE_INSN: B:48:0x0099->B:29:0x0099 BREAK  A[LOOP:0: B:21:0x0083->B:47:?], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized void seekTune(int r14, int r15) {
        /*
            Method dump skipped, instructions count: 216
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.PlayerExternalChipsetBesRichwave.seekTune(int, int):void");
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean stopSeek() {
        log("stopSeek");
        if (this.mIsTuning) {
            this.mNeedToStopSeek = true;
            return false;
        }
        return stopSeekExecute();
    }

    private boolean stopSeekExecute() {
        if (this.mIsScanning) {
            this.mIsScanning = false;
        }
        if (!this.mIsSeeking) {
            return true;
        }
        if (!openConnection()) {
            this.mIsSeekTuneing = false;
            return false;
        }
        byte[] bArr = {-1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 161, 8, 0, bArr, 1, 200);
        log("seek_stop ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        threadSleep(50L);
        if (!this.mIsScanning && this.mIsSeeking) {
            this.mSeekFreq = this.mPreviousTuneFreq;
        }
        this.mIsSeekTuneing = false;
        closeConnection();
        log("stopSeek is done");
        return controlTransfer > 0;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public long searchAll() {
        log("searchAll");
        return seekUp();
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setVolume(int i) {
        log("setVolume" + i);
        if (!openConnection()) {
            return false;
        }
        byte[] bArr = {-1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 161, 5, i, bArr, 1, 4000);
        log("set_volume_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        threadSleep(30L);
        closeConnection();
        return controlTransfer == 1 && startsWith(bArr, new byte[]{0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public int getVolume() {
        log("getVolume");
        if (!openConnection()) {
            return -1;
        }
        byte[] bArr = {-1, -1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 162, 8, 0, bArr, 2, 4000);
        log("get_volume_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        if (controlTransfer != 2) {
            return -1;
        }
        log("get_volume_cmd[0]: " + ((int) bArr[0]) + " - get_volume_cmd[1]: " + ((int) bArr[1]));
        return bytesToInt(bArr);
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setSoundMode(int i) {
        log("setSoundMode");
        if (!openConnection()) {
            return false;
        }
        byte[] bArr = {-1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 161, 6, i, bArr, 1, 4000);
        log("sound_mode_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        threadSleep(15L);
        return controlTransfer == 1 && startsWith(bArr, new byte[]{0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean getSoundMode() {
        log("getSoundMode");
        if (!openConnection()) {
            return false;
        }
        byte[] bArr = {-1, -1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 162, 7, 0, bArr, 2, 4000);
        log("get_soundmode_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        if (controlTransfer != 2) {
            return false;
        }
        log("data[0]: " + ((int) bArr[0]) + " - data[1]: " + ((int) bArr[1]));
        return startsWith(bArr, new byte[]{1});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setRdsEnable(boolean z) {
        log("setRdsEnable: " + z);
        if (this.mIsRDSEnabled == z) {
            log("RDS already " + z);
            return true;
        }
        this.mIsRDSEnabled = z;
        if (!openConnection()) {
            return false;
        }
        byte[] bArr = {-1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 161, 10, z ? 1 : 0, bArr, 1, 4000);
        log("set_rds_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        threadSleep(10L);
        if (controlTransfer != 1 || !startsWith(bArr, new byte[]{0})) {
            closeConnection();
            return false;
        }
        if (z) {
            this.rdsParser.reset();
            startNotifyThread(false);
        } else {
            stopNotifyThread(false);
        }
        return true;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean isRdsEnabled() {
        log("isRdsEnabled");
        if (!openConnection()) {
            return false;
        }
        byte[] bArr = {-1, -1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 162, 10, 0, bArr, 2, 4000);
        log("is_rdsenabled_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        if (controlTransfer != 2) {
            return false;
        }
        log("data[0]: " + ((int) bArr[0]) + " - data[1]: " + ((int) bArr[1]));
        return startsWith(bArr, new byte[]{1});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setAfEnable(boolean z) {
        log("setAfEnable");
        return false;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean isAfEnabled() {
        log("isAfEnabled");
        return false;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setBand(int i) {
        log("setBand" + i);
        if (!openConnection()) {
            return false;
        }
        byte[] bArr = {-1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 161, 1, i, bArr, 1, 4000);
        log("set_band_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        threadSleep(15L);
        return controlTransfer == 1 && startsWith(bArr, new byte[]{0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public int getBand() {
        log("getBand");
        if (!openConnection()) {
            return 0;
        }
        byte[] bArr = {-1, -1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 162, 3, 0, bArr, 2, 4000);
        log("get_band_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        if (controlTransfer != 2) {
            return 0;
        }
        log("get_band_cmd[0]: " + ((int) bArr[0]) + " - get_band_cmd[1]: " + ((int) bArr[1]));
        return bytesToInt(bArr);
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setChannelSpacing(int i) {
        log("setChannelSpacing" + i);
        if (!openConnection()) {
            return false;
        }
        byte[] bArr = {-1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 161, 3, i, bArr, 1, 4000);
        log("set_space_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        threadSleep(15L);
        return controlTransfer == 1 && startsWith(bArr, new byte[]{0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public int getChannelSpacing() {
        log("getChannelSpacing");
        if (!openConnection()) {
            return 0;
        }
        byte[] bArr = {-1, -1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 162, 5, 0, bArr, 2, 4000);
        log("get_spacing_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        if (controlTransfer != 2) {
            return 0;
        }
        log("get_spacing_cmd[0]: " + ((int) bArr[0]) + " - get_spacing_cmd[1]: " + ((int) bArr[1]));
        return bytesToInt(bArr);
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setDEConstant(int i) {
        log("setDEConstant");
        return false;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public int getDEConstant() {
        log("getDEConstant");
        return 0;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void initTuningParameters() {
        log("initTuningParameters");
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void setIntTuningParameter(String str, int i) {
        log("setIntTuningParameter");
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public int getIntTuningParameter(String str, int i) {
        log("getIntTuningParameter");
        return 0;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void setLongTuningParameter(String str, long j) {
        log("setLongTuningParameter");
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public long getLongTuningParameter(String str, long j) {
        log("getLongTuningParameter");
        return 0L;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void setStringTuningParameter(String str, String str2) {
        log("setStringTuningParameter");
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public String getStringTuningParameter(String str, String str2) {
        log("getStringTuningParameter");
        return null;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void destruct() {
        log("destruct");
        if (this.mUsbDeviceConnection != null) {
            this.mUsbDeviceConnection.close();
            this.mUsbDeviceConnection = null;
        }
    }

    @Override // com.android.server.PlayerExternalChipsetBase, com.android.server.RichwaveTuningParameteres
    public boolean setSeekQA(int i) {
        log("setSeekQA" + i);
        if (!openConnection()) {
            return false;
        }
        byte[] bArr = {-1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 161, 12, i, bArr, 1, 4000);
        log("set_qa_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        threadSleep(15L);
        return controlTransfer == 1 && startsWith(bArr, new byte[]{0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase, com.android.server.RichwaveTuningParameteres
    public int getSeekQA() {
        log("getSeekQA");
        if (!openConnection()) {
            return 0;
        }
        byte[] bArr = {-1, -1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 162, 15, 0, bArr, 2, 4000);
        log("get_qa_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        if (controlTransfer != 2) {
            return 0;
        }
        log("get_qa_cmd[0]: " + ((int) bArr[0]) + " - get_qa_cmd[1]: " + ((int) bArr[1]));
        return bytesToInt(bArr);
    }

    @Override // com.android.server.PlayerExternalChipsetBase, com.android.server.RichwaveTuningParameteres
    public boolean setSeekDC(int i) {
        log("setSeekDC" + i);
        if (!openConnection()) {
            return false;
        }
        byte[] bArr = {-1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 161, 11, i, bArr, 1, 4000);
        log("set_dc_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        closeConnection();
        threadSleep(15L);
        return controlTransfer == 1 && startsWith(bArr, new byte[]{0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase, com.android.server.RichwaveTuningParameteres
    public int getSeekDC() {
        log("getSeekDC");
        if (!openConnection()) {
            return 0;
        }
        byte[] bArr = {-1, -1};
        int controlTransfer = this.mUsbDeviceConnection.controlTransfer(192, 162, 14, 0, bArr, 2, 4000);
        log("get_dc_cmd ret: " + controlTransfer + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
        this.mUsbDeviceConnection.close();
        if (controlTransfer != 2) {
            return 0;
        }
        log("get_dc_cmd[0]: " + ((int) bArr[0]) + " - get_dc_cmd[1]: " + ((int) bArr[1]));
        return bytesToInt(bArr);
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void muteOn() {
        log("muteOn");
        if (openConnection()) {
            byte[] bArr = {-1};
            log("mute_on_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 161, 4, 1, bArr, 1, 4000) + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
            threadSleep(10L);
            closeConnection();
        }
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void muteOff() {
        log("muteOff");
        if (openConnection()) {
            byte[] bArr = {-1};
            log("mute_off_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 161, 4, 0, bArr, 1, 4000) + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
            threadSleep(10L);
            closeConnection();
        }
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean startNotifyThread(boolean z) {
        if (!openConnection()) {
            log("Can't open connection for Notify Thread");
            return false;
        }
        this.isThreadRunning = true;
        initEndpointBes();
        startNotifyThreadBes();
        this.mIsScanning = z;
        if (z) {
            this.mSeekFreq = -1;
            if (this.mIsRDSEnabled) {
                setRdsEnable(false);
                this.restoreRdsForScan = true;
            }
        }
        return true;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void stopNotifyThread(boolean z) {
        log("stopNotifyThread");
        if (z) {
            this.mIsScanning = false;
            if (this.restoreRdsForScan) {
                setRdsEnable(true);
                this.restoreRdsForScan = false;
            }
        }
        if (this.mUsbDevice != null && (this.mIsScanning || this.mIsSeekTuneing || this.mIsRDSEnabled)) {
            log("Need to keep NotifyThread alive");
            return;
        }
        stopNotifyThreadBes();
        this.isThreadRunning = false;
        releaseInterfaceBes();
        if (this.mUsbDeviceConnection != null) {
            this.mUsbDeviceConnection.close();
        }
        this.mIsScanning = false;
    }

    private boolean openConnection() {
        if (this.mUsbDevice == null) {
            log("can't open connection");
            return false;
        }
        if (this.isThreadRunning) {
            log("thread is running, already open connection");
            return true;
        }
        if (!this.mUsbManager.hasPermission(this.mUsbDevice)) {
            log("log request to grant permission");
            this.mUsbManager.grantPermission(this.mUsbDevice, AsPackageName.FM_RADIO);
        }
        if (!this.mUsbManager.hasPermission(this.mUsbDevice)) {
            log("permission not granted");
            return false;
        }
        this.mUsbDeviceConnection = this.mUsbManager.openDevice(this.mUsbDevice);
        return this.mUsbDeviceConnection != null;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void setRecordMode(boolean z) {
        log("setRecordMode:" + z);
        if (openConnection()) {
            getFirmwareVersion();
            byte[] bArr = new byte[2];
            log("[GET PROTOCOL VERIONS] ret: " + this.mUsbDeviceConnection.controlTransfer(192, 162, 18, 0, bArr, 2, 4000) + ", data: " + toHex(bArr) + ", ascii: " + toASCII(bArr));
            if (z) {
                byte[] bArr2 = {-1, -1};
                log("fm_state_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 162, 2, 0, bArr2, 2, 4000) + ", data: " + toHex(bArr2) + ", ascii: " + toASCII(bArr2));
                if (startsWith(bArr2, new byte[]{1})) {
                    log("Do not record, FM is on playback mode, please turn off it first");
                    return;
                }
            }
            byte[] bArr3 = new byte[1];
            log("set_record_cmd ret: " + this.mUsbDeviceConnection.controlTransfer(192, 161, 14, z ? 1 : 0, bArr3, 1, 4000) + ", data: " + toHex(bArr3) + ", ascii: " + toASCII(bArr3));
            threadSleep(50L);
            closeConnection();
        }
    }

    private void closeConnection() {
        if (this.isThreadRunning) {
            return;
        }
        this.mUsbDeviceConnection.close();
    }

    public static void log(String str) {
        Log.d(TAG, str);
    }

    public String getFirmwareVersion() {
        byte[] bArr = {81, 85, 69, 82, 89, 95, 83, 87, 95, 86, 69, 82};
        this.mUsbDeviceConnection.controlTransfer(64, 6, 0, 0, bArr, 12, 400);
        log("getFirmwareVersion getFirmwareVersion SEND ascii = " + toASCII(bArr));
        byte[] bArr2 = new byte[14];
        this.mUsbDeviceConnection.controlTransfer(192, 12, 0, 0, bArr2, 14, 400);
        String ascii = toASCII(bArr2);
        log("getFirmwareVersion getFirmwareVersion BACK ascii = " + ascii);
        return ascii;
    }

    public static String toASCII(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            if (b != 0) {
                stringBuffer.append((char) b);
            }
        }
        return stringBuffer.toString();
    }

    private void getFMICDebugInfo(int i) {
        byte[] bArr = {-1, -1};
        log("getFMICDebugInfo: register: " + i + ", ret: " + this.mUsbDeviceConnection.controlTransfer(192, 162, 16, i, bArr, 2, 4000) + ", data: " + toHex(bArr));
    }
}

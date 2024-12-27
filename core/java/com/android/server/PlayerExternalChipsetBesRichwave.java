package com.android.server;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.samsung.android.common.AsPackageName;

public class PlayerExternalChipsetBesRichwave extends PlayerExternalChipsetBes
        implements RichwaveTuningParameteres {
    private static final int SEEKTUNE_TIMEOUT = 2100;
    private static final String TAG = "FMRadioExtRichwave";
    private boolean isThreadRunning;
    private Context mContext;
    private boolean mNeedToStopSeek;
    private boolean restoreRdsForScan;
    private boolean mNeedOnTestMode = true;
    private int[] registerList = {3, 4, 5, 7, 8, 9, 10, 11, 16, 18, 19, 20, 21, 43, 44, 45};

    public PlayerExternalChipsetBesRichwave(Context context, FMRadioService service) {
        this.mContext = null;
        this.mContext = context;
        this.mService = service;
    }

    @Override // com.android.server.PlayerExternalChipsetBase,
              // com.android.server.CommonTuningParamters
    public void setRssiThreshold(int rssiThreshold) {
        log("setRssiThreshold" + rssiThreshold);
        if (openConnection()) {
            byte[] set_rssith_cmd = {-1};
            int ret =
                    this.mUsbDeviceConnection.controlTransfer(
                            192, 161, 2, rssiThreshold, set_rssith_cmd, 1, 4000);
            log(
                    "set_rssith_cmd ret: "
                            + ret
                            + ", data: "
                            + toHex(set_rssith_cmd)
                            + ", ascii: "
                            + toASCII(set_rssith_cmd));
            threadSleep(15L);
            closeConnection();
        }
    }

    @Override // com.android.server.PlayerExternalChipsetBase,
              // com.android.server.CommonTuningParamters
    public int getRssiThreshold() {
        log("getRssiThreshold");
        if (!openConnection()) {
            return -1;
        }
        byte[] get_rssith_cmd = {-1, -1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(192, 162, 4, 0, get_rssith_cmd, 2, 4000);
        log(
                "get_rssith_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(get_rssith_cmd)
                        + ", ascii: "
                        + toASCII(get_rssith_cmd));
        closeConnection();
        if (ret == 2) {
            return bytesToInt(get_rssith_cmd);
        }
        return -1;
    }

    @Override // com.android.server.CommonTuningParamters
    public void setSnrThreshold(int snrThreshold) {
        log("setSnrThreshold");
    }

    @Override // com.android.server.CommonTuningParamters
    public int getSnrThreshold() {
        log("setSnrThreshold");
        return 0;
    }

    @Override // com.android.server.CommonTuningParamters
    public void setCntThreshold(int rssiThreshold) {
        log("setSnrThreshold");
    }

    @Override // com.android.server.CommonTuningParamters
    public int getCntThreshold() {
        log("getCntThreshold");
        return 0;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void init(UsbDevice device) {
        log("init");
        if (this.mUsbManager == null) {
            this.mUsbManager = (UsbManager) this.mContext.getSystemService("usb");
        }
        this.mUsbDevice = device;
        if (this.mUsbDevice != null) {
            log("mUsbDevice: " + this.mUsbDevice.toString());
            return;
        }
        this.mIsTuning = false;
        this.mIsSeekTuneing = false;
        this.mIsScanning = false;
    }

    private void printFMICInfo() {
        for (int i = 0; i < this.registerList.length; i++) {
            getFMICDebugInfo(this.registerList[i]);
        }
    }

    private void setTestMode(int testMode) {
        log("setTestMode: " + testMode);
        byte[] test_mode = {-1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(
                        192, 161, 13, testMode, test_mode, 1, 4000);
        log(
                "test_mode ret: "
                        + ret
                        + ", data: "
                        + toHex(test_mode)
                        + ", ascii: "
                        + toASCII(test_mode));
        threadSleep(20L);
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean on() {
        log("on");
        boolean isOn = false;
        if (!openConnection()) {
            return false;
        }
        getFirmwareVersion();
        byte[] recording_status_cmd = new byte[2];
        log(
                "recording_status_cmd ret: "
                        + this.mUsbDeviceConnection.controlTransfer(
                                192, 162, 17, 0, recording_status_cmd, 2, 0)
                        + ", data: "
                        + toHex(recording_status_cmd)
                        + ", ascii: "
                        + toASCII(recording_status_cmd));
        if (startsWith(recording_status_cmd, new byte[] {1})) {
            log("Do not turn on FM in playback mode cause current mode are recording");
            return false;
        }
        byte[] buffer = new byte[2];
        int ret = this.mUsbDeviceConnection.controlTransfer(192, 162, 1, 0, buffer, 2, 0);
        log("ret: " + ret + ", data: " + toHex(buffer) + ", ascii: " + toASCII(buffer));
        if (ret == 2 && startsWith(buffer, new byte[] {1})) {
            byte[] open_fm_cmd = {-1};
            log("on set power state begin");
            log(
                    "open_fm_cmd ret: "
                            + this.mUsbDeviceConnection.controlTransfer(
                                    192, 161, 0, 1, open_fm_cmd, 1, 4000)
                            + ", data: "
                            + toHex(open_fm_cmd)
                            + ", ascii: "
                            + toASCII(open_fm_cmd));
            threadSleep(20L);
            log("on set power state done");
            byte[] fm_state_cmd = {-1, -1};
            log(
                    "fm_state_cmd ret: "
                            + this.mUsbDeviceConnection.controlTransfer(
                                    192, 162, 2, 0, fm_state_cmd, 2, 4000)
                            + ", data: "
                            + toHex(fm_state_cmd)
                            + ", ascii: "
                            + toASCII(fm_state_cmd));
            if (startsWith(fm_state_cmd, new byte[] {1})) {
                isOn = true;
            }
        }
        closeConnection();
        return isOn;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean off() {
        log("off");
        this.mIsSeekTuneing = false;
        if (!openConnection()) {
            return false;
        }
        byte[] off_fm_cmd = {-1};
        int ret = this.mUsbDeviceConnection.controlTransfer(192, 161, 0, 0, off_fm_cmd, 1, 4000);
        log(
                "off_fm_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(off_fm_cmd)
                        + ", ascii: "
                        + toASCII(off_fm_cmd));
        threadSleep(50L);
        log("off set power state done");
        byte[] set_record_cmd = new byte[1];
        int ret2 =
                this.mUsbDeviceConnection.controlTransfer(192, 161, 14, 0, set_record_cmd, 1, 4000);
        log(
                "set_record_cmd ret: "
                        + ret2
                        + ", data: "
                        + toHex(set_record_cmd)
                        + ", ascii: "
                        + toASCII(set_record_cmd));
        threadSleep(50L);
        byte[] fm_state_cmd = {-1, -1};
        int ret3 = this.mUsbDeviceConnection.controlTransfer(192, 162, 2, 0, fm_state_cmd, 2, 4000);
        log(
                "fm_state_cmd ret: "
                        + ret3
                        + ", data: "
                        + toHex(fm_state_cmd)
                        + ", ascii: "
                        + toASCII(fm_state_cmd));
        byte[] recording_status_cmd = new byte[2];
        int ret4 =
                this.mUsbDeviceConnection.controlTransfer(
                        192, 162, 17, 0, recording_status_cmd, 2, 0);
        log(
                "recording_status_cmd ret: "
                        + ret4
                        + ", data: "
                        + toHex(recording_status_cmd)
                        + ", ascii: "
                        + toASCII(recording_status_cmd));
        closeConnection();
        if (!startsWith(fm_state_cmd, new byte[] {0})
                || !startsWith(recording_status_cmd, new byte[] {0})) {
            return false;
        }
        return true;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean isOn() {
        log("isOn");
        if (!openConnection()) {
            return false;
        }
        byte[] fm_state_cmd = {-1, -1};
        int ret = this.mUsbDeviceConnection.controlTransfer(192, 162, 2, 0, fm_state_cmd, 2, 4000);
        log(
                "fm_state_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(fm_state_cmd)
                        + ", ascii: "
                        + toASCII(fm_state_cmd));
        byte[] recording_status_cmd = {-1, -1};
        int ret2 =
                this.mUsbDeviceConnection.controlTransfer(
                        192, 162, 17, 0, recording_status_cmd, 2, 0);
        log(
                "recording_status_cmd ret: "
                        + ret2
                        + ", data: "
                        + toHex(recording_status_cmd)
                        + ", ascii: "
                        + toASCII(recording_status_cmd));
        closeConnection();
        return startsWith(fm_state_cmd, new byte[] {1})
                || startsWith(recording_status_cmd, new byte[] {1});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void tune(int freq) {
        seekTune(9, freq);
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public int getTunedFrequency() {
        log("getTunedFrequency");
        if (!openConnection()) {
            return -1;
        }
        byte[] get_tune_freq_cmd = {-1, -1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(
                        192, 162, 13, 0, get_tune_freq_cmd, 2, 4000);
        log(
                "getTunedFrequency ret: "
                        + ret
                        + ", data: "
                        + toHex(get_tune_freq_cmd)
                        + ", ascii: "
                        + toASCII(get_tune_freq_cmd));
        closeConnection();
        if (ret == 2) {
            return bytesToInt(get_tune_freq_cmd);
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized void seekTune(int r17, int r18) {
        /*
            Method dump skipped, instructions count: 243
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.PlayerExternalChipsetBesRichwave.seekTune(int,"
                    + " int):void");
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
        byte[] seek_stop = {-1};
        int ret = this.mUsbDeviceConnection.controlTransfer(192, 161, 8, 0, seek_stop, 1, 200);
        log(
                "seek_stop ret: "
                        + ret
                        + ", data: "
                        + toHex(seek_stop)
                        + ", ascii: "
                        + toASCII(seek_stop));
        threadSleep(50L);
        if (!this.mIsScanning && this.mIsSeeking) {
            this.mSeekFreq = this.mPreviousTuneFreq;
        }
        this.mIsSeekTuneing = false;
        closeConnection();
        log("stopSeek is done");
        return ret > 0;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public long searchAll() {
        log("searchAll");
        return seekUp();
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setVolume(int volume) {
        log("setVolume" + volume);
        if (!openConnection()) {
            return false;
        }
        byte[] set_volume_cmd = {-1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(
                        192, 161, 5, volume, set_volume_cmd, 1, 4000);
        log(
                "set_volume_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(set_volume_cmd)
                        + ", ascii: "
                        + toASCII(set_volume_cmd));
        threadSleep(30L);
        closeConnection();
        return ret == 1 && startsWith(set_volume_cmd, new byte[] {0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public int getVolume() {
        log("getVolume");
        if (!openConnection()) {
            return -1;
        }
        byte[] get_volume_cmd = {-1, -1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(192, 162, 8, 0, get_volume_cmd, 2, 4000);
        log(
                "get_volume_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(get_volume_cmd)
                        + ", ascii: "
                        + toASCII(get_volume_cmd));
        closeConnection();
        if (ret != 2) {
            return -1;
        }
        log(
                "get_volume_cmd[0]: "
                        + ((int) get_volume_cmd[0])
                        + " - get_volume_cmd[1]: "
                        + ((int) get_volume_cmd[1]));
        return bytesToInt(get_volume_cmd);
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setSoundMode(int mode) {
        log("setSoundMode");
        if (!openConnection()) {
            return false;
        }
        byte[] sound_mode_cmd = {-1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(
                        192, 161, 6, mode, sound_mode_cmd, 1, 4000);
        log(
                "sound_mode_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(sound_mode_cmd)
                        + ", ascii: "
                        + toASCII(sound_mode_cmd));
        closeConnection();
        threadSleep(15L);
        return ret == 1 && startsWith(sound_mode_cmd, new byte[] {0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean getSoundMode() {
        log("getSoundMode");
        if (!openConnection()) {
            return false;
        }
        byte[] get_soundmode_cmd = {-1, -1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(
                        192, 162, 7, 0, get_soundmode_cmd, 2, 4000);
        log(
                "get_soundmode_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(get_soundmode_cmd)
                        + ", ascii: "
                        + toASCII(get_soundmode_cmd));
        closeConnection();
        if (ret != 2) {
            return false;
        }
        log(
                "data[0]: "
                        + ((int) get_soundmode_cmd[0])
                        + " - data[1]: "
                        + ((int) get_soundmode_cmd[1]));
        return startsWith(get_soundmode_cmd, new byte[] {1});
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
        int controlTransfer =
                this.mUsbDeviceConnection.controlTransfer(192, 161, 10, z ? 1 : 0, bArr, 1, 4000);
        log(
                "set_rds_cmd ret: "
                        + controlTransfer
                        + ", data: "
                        + toHex(bArr)
                        + ", ascii: "
                        + toASCII(bArr));
        threadSleep(10L);
        if (controlTransfer == 1 && startsWith(bArr, new byte[] {0})) {
            if (z) {
                this.rdsParser.reset();
                startNotifyThread(false);
            } else {
                stopNotifyThread(false);
            }
            return true;
        }
        closeConnection();
        return false;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean isRdsEnabled() {
        log("isRdsEnabled");
        if (!openConnection()) {
            return false;
        }
        byte[] is_rdsenabled_cmd = {-1, -1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(
                        192, 162, 10, 0, is_rdsenabled_cmd, 2, 4000);
        log(
                "is_rdsenabled_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(is_rdsenabled_cmd)
                        + ", ascii: "
                        + toASCII(is_rdsenabled_cmd));
        closeConnection();
        if (ret != 2) {
            return false;
        }
        log(
                "data[0]: "
                        + ((int) is_rdsenabled_cmd[0])
                        + " - data[1]: "
                        + ((int) is_rdsenabled_cmd[1]));
        return startsWith(is_rdsenabled_cmd, new byte[] {1});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setAfEnable(boolean isEnabled) {
        log("setAfEnable");
        return false;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean isAfEnabled() {
        log("isAfEnabled");
        return false;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setBand(int band) {
        log("setBand" + band);
        if (!openConnection()) {
            return false;
        }
        byte[] set_band_cmd = {-1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(192, 161, 1, band, set_band_cmd, 1, 4000);
        log(
                "set_band_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(set_band_cmd)
                        + ", ascii: "
                        + toASCII(set_band_cmd));
        closeConnection();
        threadSleep(15L);
        return ret == 1 && startsWith(set_band_cmd, new byte[] {0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public int getBand() {
        log("getBand");
        if (!openConnection()) {
            return 0;
        }
        byte[] get_band_cmd = {-1, -1};
        int ret = this.mUsbDeviceConnection.controlTransfer(192, 162, 3, 0, get_band_cmd, 2, 4000);
        log(
                "get_band_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(get_band_cmd)
                        + ", ascii: "
                        + toASCII(get_band_cmd));
        closeConnection();
        if (ret != 2) {
            return 0;
        }
        log(
                "get_band_cmd[0]: "
                        + ((int) get_band_cmd[0])
                        + " - get_band_cmd[1]: "
                        + ((int) get_band_cmd[1]));
        return bytesToInt(get_band_cmd);
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setChannelSpacing(int spacing) {
        log("setChannelSpacing" + spacing);
        if (!openConnection()) {
            return false;
        }
        byte[] set_space_cmd = {-1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(
                        192, 161, 3, spacing, set_space_cmd, 1, 4000);
        log(
                "set_space_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(set_space_cmd)
                        + ", ascii: "
                        + toASCII(set_space_cmd));
        closeConnection();
        threadSleep(15L);
        return ret == 1 && startsWith(set_space_cmd, new byte[] {0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public int getChannelSpacing() {
        log("getChannelSpacing");
        if (!openConnection()) {
            return 0;
        }
        byte[] get_spacing_cmd = {-1, -1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(192, 162, 5, 0, get_spacing_cmd, 2, 4000);
        log(
                "get_spacing_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(get_spacing_cmd)
                        + ", ascii: "
                        + toASCII(get_spacing_cmd));
        closeConnection();
        if (ret != 2) {
            return 0;
        }
        log(
                "get_spacing_cmd[0]: "
                        + ((int) get_spacing_cmd[0])
                        + " - get_spacing_cmd[1]: "
                        + ((int) get_spacing_cmd[1]));
        return bytesToInt(get_spacing_cmd);
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean setDEConstant(int constant) {
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
    public void setIntTuningParameter(String name, int value) {
        log("setIntTuningParameter");
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public int getIntTuningParameter(String name, int def) {
        log("getIntTuningParameter");
        return 0;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void setLongTuningParameter(String name, long value) {
        log("setLongTuningParameter");
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public long getLongTuningParameter(String name, long def) {
        log("getLongTuningParameter");
        return 0L;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void setStringTuningParameter(String name, String value) {
        log("setStringTuningParameter");
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public String getStringTuningParameter(String name, String def) {
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

    @Override // com.android.server.PlayerExternalChipsetBase,
              // com.android.server.RichwaveTuningParameteres
    public boolean setSeekQA(int value) {
        log("setSeekQA" + value);
        if (!openConnection()) {
            return false;
        }
        byte[] set_qa_cmd = {-1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(192, 161, 12, value, set_qa_cmd, 1, 4000);
        log(
                "set_qa_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(set_qa_cmd)
                        + ", ascii: "
                        + toASCII(set_qa_cmd));
        closeConnection();
        threadSleep(15L);
        return ret == 1 && startsWith(set_qa_cmd, new byte[] {0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase,
              // com.android.server.RichwaveTuningParameteres
    public int getSeekQA() {
        log("getSeekQA");
        if (!openConnection()) {
            return 0;
        }
        byte[] get_qa_cmd = {-1, -1};
        int ret = this.mUsbDeviceConnection.controlTransfer(192, 162, 15, 0, get_qa_cmd, 2, 4000);
        log(
                "get_qa_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(get_qa_cmd)
                        + ", ascii: "
                        + toASCII(get_qa_cmd));
        closeConnection();
        if (ret != 2) {
            return 0;
        }
        log(
                "get_qa_cmd[0]: "
                        + ((int) get_qa_cmd[0])
                        + " - get_qa_cmd[1]: "
                        + ((int) get_qa_cmd[1]));
        return bytesToInt(get_qa_cmd);
    }

    @Override // com.android.server.PlayerExternalChipsetBase,
              // com.android.server.RichwaveTuningParameteres
    public boolean setSeekDC(int value) {
        log("setSeekDC" + value);
        if (!openConnection()) {
            return false;
        }
        byte[] set_dc_cmd = {-1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(192, 161, 11, value, set_dc_cmd, 1, 4000);
        log(
                "set_dc_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(set_dc_cmd)
                        + ", ascii: "
                        + toASCII(set_dc_cmd));
        closeConnection();
        threadSleep(15L);
        return ret == 1 && startsWith(set_dc_cmd, new byte[] {0});
    }

    @Override // com.android.server.PlayerExternalChipsetBase,
              // com.android.server.RichwaveTuningParameteres
    public int getSeekDC() {
        log("getSeekDC");
        if (!openConnection()) {
            return 0;
        }
        byte[] get_dc_cmd = {-1, -1};
        int ret = this.mUsbDeviceConnection.controlTransfer(192, 162, 14, 0, get_dc_cmd, 2, 4000);
        log(
                "get_dc_cmd ret: "
                        + ret
                        + ", data: "
                        + toHex(get_dc_cmd)
                        + ", ascii: "
                        + toASCII(get_dc_cmd));
        this.mUsbDeviceConnection.close();
        if (ret != 2) {
            return 0;
        }
        log(
                "get_dc_cmd[0]: "
                        + ((int) get_dc_cmd[0])
                        + " - get_dc_cmd[1]: "
                        + ((int) get_dc_cmd[1]));
        return bytesToInt(get_dc_cmd);
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void muteOn() {
        log("muteOn");
        if (openConnection()) {
            byte[] mute_on_cmd = {-1};
            int ret =
                    this.mUsbDeviceConnection.controlTransfer(192, 161, 4, 1, mute_on_cmd, 1, 4000);
            log(
                    "mute_on_cmd ret: "
                            + ret
                            + ", data: "
                            + toHex(mute_on_cmd)
                            + ", ascii: "
                            + toASCII(mute_on_cmd));
            threadSleep(10L);
            closeConnection();
        }
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void muteOff() {
        log("muteOff");
        if (openConnection()) {
            byte[] mute_off_cmd = {-1};
            int ret =
                    this.mUsbDeviceConnection.controlTransfer(
                            192, 161, 4, 0, mute_off_cmd, 1, 4000);
            log(
                    "mute_off_cmd ret: "
                            + ret
                            + ", data: "
                            + toHex(mute_off_cmd)
                            + ", ascii: "
                            + toASCII(mute_off_cmd));
            threadSleep(10L);
            closeConnection();
        }
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public boolean startNotifyThread(boolean isForScan) {
        if (!openConnection()) {
            log("Can't open connection for Notify Thread");
            return false;
        }
        this.isThreadRunning = true;
        initEndpointBes();
        startNotifyThreadBes();
        this.mIsScanning = isForScan;
        if (isForScan) {
            this.mSeekFreq = -1;
            if (this.mIsRDSEnabled) {
                setRdsEnable(false);
                this.restoreRdsForScan = true;
            }
        }
        return true;
    }

    @Override // com.android.server.PlayerExternalChipsetBase
    public void stopNotifyThread(boolean isForScan) {
        log("stopNotifyThread");
        if (isForScan) {
            this.mIsScanning = false;
            if (this.restoreRdsForScan) {
                setRdsEnable(true);
                this.restoreRdsForScan = false;
            }
        }
        if (this.mUsbDevice != null
                && (this.mIsScanning || this.mIsSeekTuneing || this.mIsRDSEnabled)) {
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
            log(
                    "[GET PROTOCOL VERIONS] ret: "
                            + this.mUsbDeviceConnection.controlTransfer(
                                    192, 162, 18, 0, bArr, 2, 4000)
                            + ", data: "
                            + toHex(bArr)
                            + ", ascii: "
                            + toASCII(bArr));
            if (z) {
                byte[] bArr2 = {-1, -1};
                log(
                        "fm_state_cmd ret: "
                                + this.mUsbDeviceConnection.controlTransfer(
                                        192, 162, 2, 0, bArr2, 2, 4000)
                                + ", data: "
                                + toHex(bArr2)
                                + ", ascii: "
                                + toASCII(bArr2));
                if (startsWith(bArr2, new byte[] {1})) {
                    log("Do not record, FM is on playback mode, please turn off it first");
                    return;
                }
            }
            byte[] bArr3 = new byte[1];
            log(
                    "set_record_cmd ret: "
                            + this.mUsbDeviceConnection.controlTransfer(
                                    192, 161, 14, z ? 1 : 0, bArr3, 1, 4000)
                            + ", data: "
                            + toHex(bArr3)
                            + ", ascii: "
                            + toASCII(bArr3));
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
        byte[] getFWVersion = {81, 85, 69, 82, 89, 95, 83, 87, 95, 86, 69, 82};
        this.mUsbDeviceConnection.controlTransfer(
                64, 6, 0, 0, getFWVersion, getFWVersion.length, 400);
        log("getFirmwareVersion getFirmwareVersion SEND ascii = " + toASCII(getFWVersion));
        byte[] buffer = new byte[14];
        this.mUsbDeviceConnection.controlTransfer(192, 12, 0, 0, buffer, buffer.length, 400);
        String version = toASCII(buffer);
        log("getFirmwareVersion getFirmwareVersion BACK ascii = " + version);
        return version;
    }

    public static String toASCII(byte[] data) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0) {
                buffer.append((char) data[i]);
            }
        }
        return buffer.toString();
    }

    private void getFMICDebugInfo(int register) {
        byte[] get_fmic_info_cmd = {-1, -1};
        int ret =
                this.mUsbDeviceConnection.controlTransfer(
                        192, 162, 16, register, get_fmic_info_cmd, 2, 4000);
        log(
                "getFMICDebugInfo: register: "
                        + register
                        + ", ret: "
                        + ret
                        + ", data: "
                        + toHex(get_fmic_info_cmd));
    }
}

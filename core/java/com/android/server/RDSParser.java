package com.android.server;

import android.hardware.gnss.GnssSignalType;
import android.util.Log;

/* loaded from: classes5.dex */
public class RDSParser {
    public static final int FM_RDS_STATUS_UNCORRECTABLE = 3;
    public static final int GROUP_TYPE_0A = 0;
    public static final int GROUP_TYPE_0B = 1;
    public static final int GROUP_TYPE_2A = 4;
    public static final int GROUP_TYPE_2B = 5;
    public static final int PROGRAM_SERVICE_MAX_SIZE = 8;
    public static final int PS_CHECK_BOUND = 2;
    public static final int RADIO_TEXT_MAX_SIZE = 64;
    public static final int RT_CHECK_BOUND = 1;
    private static final String TAG = "FMRDSParser";
    private static RDSParser mInstance = null;
    private String mFinalRadioText = null;
    private String mFinalProgramService = null;
    private int mPI = -1;
    private int mPTY = -1;
    private int mTP = -1;
    private RadioText mRadioText = new RadioText();
    private ProgramService mProgramService = new ProgramService();

    public static RDSParser getInstance() {
        if (mInstance == null) {
            mInstance = new RDSParser();
        }
        return mInstance;
    }

    private RDSParser() {
    }

    public void reset() {
        this.mFinalRadioText = null;
        this.mFinalProgramService = null;
        this.mRadioText = new RadioText();
        this.mProgramService = new ProgramService();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x01fb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void parseData(ExtRDSData data) {
        int max_data_length;
        int data_segment_size;
        if (data.blera == 3) {
        }
        int RDSGroupCode = (data.rdsb[0] & 255) >> 3;
        Log("Group code: " + (RDSGroupCode / 2) + (RDSGroupCode % 2 == 0 ? "A" : GnssSignalType.CODE_TYPE_B));
        int PI = ((data.rdsa[0] & 255) << 8) | (data.rdsa[1] & 255);
        if (PI != this.mPI) {
            this.mPI = PI;
            this.mRadioText.resetBuffer();
            this.mProgramService.resetBuffer();
        }
        switch (RDSGroupCode) {
            case 0:
            case 1:
                if (data.blerd == 3) {
                    Log("RDS is corrupted!");
                    break;
                } else {
                    int PS_data_segment = (3 & data.rdsb[1]) << 1;
                    for (int index = 0; index < 2; index++) {
                        if (this.mProgramService.buffer_ps[PS_data_segment + index] != ((char) data.rdsd[index])) {
                            this.mProgramService.buffer_ps[PS_data_segment + index] = (char) data.rdsd[index];
                            this.mProgramService.receivedChar++;
                        }
                    }
                    if (this.mProgramService.isPSValid()) {
                        this.mProgramService.validateBuffer();
                        this.mProgramService.resetBuffer();
                        break;
                    }
                }
                break;
            case 4:
            case 5:
                int RT_data_segment = data.rdsb[1] & 15;
                int RTChangeFlag = (data.rdsb[1] & 16) >> 4;
                char[] rds_data = new char[4];
                Log("RTChangeFlag: " + RTChangeFlag);
                if (RTChangeFlag != this.mRadioText.previousRTChangeFlag) {
                    Log("Detected change");
                    this.mRadioText.resetBuffer();
                    this.mRadioText.buffer_validate = 0;
                    this.mRadioText.previousRTChangeFlag = RTChangeFlag;
                }
                if (RDSGroupCode == 4) {
                    if (data.blerc == 3 || data.blerd == 3) {
                        Log("RDS is corrupted!");
                        break;
                    } else {
                        rds_data[0] = (char) data.rdsc[0];
                        rds_data[1] = (char) data.rdsc[1];
                        rds_data[2] = (char) data.rdsd[0];
                        rds_data[3] = (char) data.rdsd[1];
                        max_data_length = 64;
                        data_segment_size = 4;
                    }
                } else if (data.blerd == 3) {
                    Log("RDS is corrupted!");
                    break;
                } else {
                    rds_data[0] = (char) data.rdsd[0];
                    rds_data[1] = (char) data.rdsd[1];
                    max_data_length = 32;
                    data_segment_size = 2;
                }
                Log("Group 2 - Segment:" + RT_data_segment + " - data:" + ((char) data.rdsc[0]) + "," + ((char) data.rdsc[1]) + "," + ((char) data.rdsd[0]) + "," + ((char) data.rdsd[1]));
                Log("Group 2 - Segment:" + RT_data_segment + " - data:" + ((int) data.rdsc[0]) + "," + ((int) data.rdsc[1]) + "," + ((int) data.rdsd[0]) + "," + ((int) data.rdsd[1]));
                int RT_data_segment2 = RT_data_segment * data_segment_size;
                for (int i = 0; i < data_segment_size; i++) {
                    if (rds_data[i] == '\r' || rds_data[i] == 0) {
                        Log("RT endReceived");
                        this.mRadioText.endReceived = true;
                        this.mRadioText.length = RT_data_segment2 + i;
                        Log.m94d("MonitorRDS", "Radio Text Buffer: " + new String(this.mRadioText.buffer_rt));
                        Log("mRadioText.receivedChar: " + this.mRadioText.receivedChar);
                        if (this.mRadioText.isRTValid(max_data_length)) {
                            if (this.mRadioText.receivedChar == 64) {
                                this.mRadioText.length = 64;
                            }
                            this.mRadioText.validateBuffer();
                            this.mRadioText.resetBuffer();
                        }
                        if (!this.mRadioText.endReceived && this.mRadioText.receivedChar > this.mRadioText.length) {
                            this.mRadioText.resetBuffer();
                            break;
                        }
                    } else {
                        this.mRadioText.buffer_rt[RT_data_segment2 + i] = rds_data[i];
                        this.mRadioText.receivedChar++;
                    }
                }
                Log.m94d("MonitorRDS", "Radio Text Buffer: " + new String(this.mRadioText.buffer_rt));
                Log("mRadioText.receivedChar: " + this.mRadioText.receivedChar);
                if (this.mRadioText.isRTValid(max_data_length)) {
                }
                if (!this.mRadioText.endReceived) {
                }
                break;
        }
    }

    public boolean isRDSDataValid() {
        return this.mRadioText.isRTValid | this.mProgramService.isPSValid;
    }

    public String getRadioText() {
        if (this.mRadioText.buffer_validate >= 1) {
            this.mFinalRadioText = this.mRadioText.getRadioText();
        }
        return this.mFinalRadioText;
    }

    public String getProgramService() {
        if (this.mProgramService.buffer_validate >= 2) {
            this.mFinalProgramService = this.mProgramService.getProgramService();
        }
        return this.mFinalProgramService;
    }

    private class RadioText {
        private char[] buffer_rt;
        private String final_rt = null;
        private int buffer_validate = 0;
        private boolean endReceived = false;
        private int receivedChar = 0;
        private int length = 0;
        private boolean isRTValid = false;
        private int previousRTChangeFlag = -1;

        RadioText() {
            resetBuffer();
        }

        public void resetBuffer() {
            this.buffer_rt = new char[65];
            this.length = 0;
            this.receivedChar = 0;
            this.endReceived = false;
        }

        public void validateBuffer() {
            RDSParser.Log("validateBuffer: " + this.buffer_validate);
            String str = this.final_rt;
            if (str == null || str.length() != this.length) {
                latch();
                return;
            }
            for (int i = 0; i < this.length; i++) {
                if (this.buffer_rt[i] != this.final_rt.charAt(i)) {
                    latch();
                    return;
                }
            }
            RDSParser.Log("validateBuffer++ : " + this.final_rt);
            this.buffer_validate++;
        }

        public String getRadioText() {
            return this.final_rt;
        }

        public void latch() {
            Log.m94d(RDSParser.TAG, "latch Radio Text");
            this.buffer_validate = 1;
            this.final_rt = new String(this.buffer_rt).substring(0, this.length);
            this.isRTValid = true;
        }

        public boolean isRTValid(int maxLength) {
            return (RDSParser.this.mRadioText.endReceived && RDSParser.this.mRadioText.length == RDSParser.this.mRadioText.receivedChar) || RDSParser.this.mRadioText.receivedChar == maxLength;
        }
    }

    private class ProgramService {
        private char[] buffer_ps;
        private int buffer_validate;
        private String final_ps;
        private boolean isPSValid;
        private int receivedChar;

        private ProgramService() {
            this.final_ps = null;
            this.buffer_validate = 0;
            this.receivedChar = 0;
            this.isPSValid = false;
            resetBuffer();
        }

        public void resetBuffer() {
            this.buffer_ps = new char[9];
            this.receivedChar = 0;
        }

        public String getProgramService() {
            return this.final_ps;
        }

        public void latch() {
            this.buffer_validate = 1;
            this.final_ps = new String(this.buffer_ps).substring(0, 8);
            this.isPSValid = true;
        }

        public void validateBuffer() {
            RDSParser.Log("validatePSBuffer: " + this.buffer_validate);
            if (this.final_ps == null) {
                latch();
                return;
            }
            for (int i = 0; i < 8; i++) {
                if (this.buffer_ps[i] != this.final_ps.charAt(i)) {
                    latch();
                    return;
                }
            }
            RDSParser.Log("validatePSBuffer++ : " + this.final_ps);
            this.buffer_validate++;
        }

        public boolean isPSValid() {
            return RDSParser.this.mProgramService.receivedChar == 8;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void Log(String msg) {
        Log.m94d(TAG, msg);
    }
}

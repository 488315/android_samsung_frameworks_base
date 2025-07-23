package com.android.server;

import android.hardware.gnss.GnssSignalType;
import android.util.Log;

/* loaded from: classes6.dex */
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
    private static RDSParser mInstance;
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

    public void parseData(ExtRDSData extRDSData) {
        int i;
        if (extRDSData.blera == 3) {
            return;
        }
        int i2 = 0;
        int i3 = (extRDSData.rdsb[0] & 255) >> 3;
        StringBuilder sb = new StringBuilder("Group code: ");
        sb.append(i3 / 2);
        sb.append(i3 % 2 == 0 ? "A" : GnssSignalType.CODE_TYPE_B);
        Log(sb.toString());
        int i4 = ((extRDSData.rdsa[0] & 255) << 8) | (extRDSData.rdsa[1] & 255);
        if (i4 != this.mPI) {
            this.mPI = i4;
            this.mRadioText.resetBuffer();
            this.mProgramService.resetBuffer();
        }
        int i5 = 2;
        if (i3 == 0 || i3 == 1) {
            if (extRDSData.blerd == 3) {
                Log("RDS is corrupted!");
                return;
            }
            int i6 = (extRDSData.rdsb[1] & 3) << 1;
            while (i2 < 2) {
                int i7 = i6 + i2;
                if (this.mProgramService.buffer_ps[i7] != ((char) extRDSData.rdsd[i2])) {
                    this.mProgramService.buffer_ps[i7] = (char) extRDSData.rdsd[i2];
                    this.mProgramService.receivedChar++;
                }
                i2++;
            }
            if (this.mProgramService.isPSValid()) {
                this.mProgramService.validateBuffer();
                this.mProgramService.resetBuffer();
                return;
            }
            return;
        }
        if (i3 == 4 || i3 == 5) {
            int i8 = extRDSData.rdsb[1] & 15;
            int i9 = (extRDSData.rdsb[1] & 16) >> 4;
            char[] cArr = new char[4];
            Log("RTChangeFlag: " + i9);
            if (i9 != this.mRadioText.previousRTChangeFlag) {
                Log("Detected change");
                this.mRadioText.resetBuffer();
                this.mRadioText.buffer_validate = 0;
                this.mRadioText.previousRTChangeFlag = i9;
            }
            if (i3 == 4) {
                if (extRDSData.blerc == 3 || extRDSData.blerd == 3) {
                    Log("RDS is corrupted!");
                    return;
                }
                cArr[0] = (char) extRDSData.rdsc[0];
                cArr[1] = (char) extRDSData.rdsc[1];
                cArr[2] = (char) extRDSData.rdsd[0];
                cArr[3] = (char) extRDSData.rdsd[1];
                i5 = 4;
                i = 64;
            } else if (extRDSData.blerd == 3) {
                Log("RDS is corrupted!");
                return;
            } else {
                cArr[0] = (char) extRDSData.rdsd[0];
                cArr[1] = (char) extRDSData.rdsd[1];
                i = 32;
            }
            Log("Group 2 - Segment:" + i8 + " - data:" + ((char) extRDSData.rdsc[0]) + "," + ((char) extRDSData.rdsc[1]) + "," + ((char) extRDSData.rdsd[0]) + "," + ((char) extRDSData.rdsd[1]));
            Log("Group 2 - Segment:" + i8 + " - data:" + ((int) extRDSData.rdsc[0]) + "," + ((int) extRDSData.rdsc[1]) + "," + ((int) extRDSData.rdsd[0]) + "," + ((int) extRDSData.rdsd[1]));
            int i10 = i8 * i5;
            while (i2 < i5) {
                char c = cArr[i2];
                if (c == '\r' || c == 0) {
                    Log("RT endReceived");
                    this.mRadioText.endReceived = true;
                    this.mRadioText.length = i10 + i2;
                    break;
                } else {
                    this.mRadioText.buffer_rt[i10 + i2] = cArr[i2];
                    this.mRadioText.receivedChar++;
                    i2++;
                }
            }
            Log.d("MonitorRDS", "Radio Text Buffer: ".concat(new String(this.mRadioText.buffer_rt)));
            Log("mRadioText.receivedChar: " + this.mRadioText.receivedChar);
            if (this.mRadioText.isRTValid(i)) {
                if (this.mRadioText.receivedChar == 64) {
                    this.mRadioText.length = 64;
                }
                this.mRadioText.validateBuffer();
                this.mRadioText.resetBuffer();
            }
            if (!this.mRadioText.endReceived || this.mRadioText.receivedChar <= this.mRadioText.length) {
                return;
            }
            this.mRadioText.resetBuffer();
        }
    }

    public boolean isRDSDataValid() {
        return this.mProgramService.isPSValid | this.mRadioText.isRTValid;
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
            this.buffer_validate = this.buffer_validate + 1;
        }

        public String getRadioText() {
            return this.final_rt;
        }

        public void latch() {
            Log.d(RDSParser.TAG, "latch Radio Text");
            this.buffer_validate = 1;
            this.final_rt = new String(this.buffer_rt).substring(0, this.length);
            this.isRTValid = true;
        }

        public boolean isRTValid(int i) {
            return (RDSParser.this.mRadioText.endReceived && RDSParser.this.mRadioText.length == RDSParser.this.mRadioText.receivedChar) || RDSParser.this.mRadioText.receivedChar == i;
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
            this.buffer_validate = this.buffer_validate + 1;
        }

        public boolean isPSValid() {
            return RDSParser.this.mProgramService.receivedChar == 8;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void Log(String str) {
        Log.d(TAG, str);
    }
}

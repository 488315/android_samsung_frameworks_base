package com.android.server;

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

    private RDSParser() {}

    public void reset() {
        this.mFinalRadioText = null;
        this.mFinalProgramService = null;
        this.mRadioText = new RadioText();
        this.mProgramService = new ProgramService();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x01fb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parseData(com.android.server.ExtRDSData r15) {
        /*
            Method dump skipped, instructions count: 658
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.RDSParser.parseData(com.android.server.ExtRDSData):void");
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
            if (this.final_rt == null || this.final_rt.length() != this.length) {
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
            Log.d(RDSParser.TAG, "latch Radio Text");
            this.buffer_validate = 1;
            this.final_rt = new String(this.buffer_rt).substring(0, this.length);
            this.isRTValid = true;
        }

        public boolean isRTValid(int maxLength) {
            return (RDSParser.this.mRadioText.endReceived
                            && RDSParser.this.mRadioText.length
                                    == RDSParser.this.mRadioText.receivedChar)
                    || RDSParser.this.mRadioText.receivedChar == maxLength;
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
        Log.d(TAG, msg);
    }
}

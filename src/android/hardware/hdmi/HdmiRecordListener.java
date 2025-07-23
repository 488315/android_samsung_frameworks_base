package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.hardware.hdmi.HdmiRecordSources;

@SystemApi
/* loaded from: classes2.dex */
public abstract class HdmiRecordListener {
    public void onClearTimerRecordingResult(int i, int i2) {
    }

    public void onOneTouchRecordResult(int i, int i2) {
    }

    public abstract HdmiRecordSources.RecordSource onOneTouchRecordSourceRequested(int i);

    public void onTimerRecordingResult(int i, TimerStatusData timerStatusData) {
    }

    @SystemApi
    public static class TimerStatusData {
        private int mDurationHour;
        private int mDurationMinute;
        private int mExtraError;
        private int mMediaInfo;
        private int mNotProgrammedError;
        private boolean mOverlapped;
        private boolean mProgrammed;
        private int mProgrammedInfo;

        private static int bcdByteToInt(byte b) {
            return ((((b >> 4) & 15) * 10) + b) & 15;
        }

        static TimerStatusData parseFrom(int i) {
            TimerStatusData timerStatusData = new TimerStatusData();
            timerStatusData.mOverlapped = ((i >> 31) & 1) != 0;
            timerStatusData.mMediaInfo = (i >> 29) & 3;
            boolean z = ((i >> 28) & 1) != 0;
            timerStatusData.mProgrammed = z;
            if (z) {
                timerStatusData.mProgrammedInfo = (i >> 24) & 15;
                timerStatusData.mDurationHour = bcdByteToInt((byte) ((i >> 16) & 255));
                timerStatusData.mDurationMinute = bcdByteToInt((byte) ((i >> 8) & 255));
            } else {
                timerStatusData.mNotProgrammedError = (i >> 24) & 15;
                timerStatusData.mDurationHour = bcdByteToInt((byte) ((i >> 16) & 255));
                timerStatusData.mDurationMinute = bcdByteToInt((byte) ((i >> 8) & 255));
            }
            timerStatusData.mExtraError = i & 255;
            return timerStatusData;
        }

        private TimerStatusData() {
        }

        public boolean isOverlapped() {
            return this.mOverlapped;
        }

        public int getMediaInfo() {
            return this.mMediaInfo;
        }

        public boolean isProgrammed() {
            return this.mProgrammed;
        }

        public int getProgrammedInfo() {
            if (!isProgrammed()) {
                throw new IllegalStateException("No programmed info. Call getNotProgammedError() instead.");
            }
            return this.mProgrammedInfo;
        }

        public int getNotProgammedError() {
            if (isProgrammed()) {
                throw new IllegalStateException("Has no not-programmed error. Call getProgrammedInfo() instead.");
            }
            return this.mNotProgrammedError;
        }

        public int getDurationHour() {
            return this.mDurationHour;
        }

        public int getDurationMinute() {
            return this.mDurationMinute;
        }

        public int getExtraError() {
            return this.mExtraError;
        }
    }
}

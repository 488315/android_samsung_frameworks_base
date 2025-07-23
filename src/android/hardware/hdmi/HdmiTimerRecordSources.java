package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.hardware.hdmi.HdmiRecordSources;
import android.util.Log;

@SystemApi
/* loaded from: classes2.dex */
public class HdmiTimerRecordSources {
    private static final int EXTERNAL_SOURCE_SPECIFIER_EXTERNAL_PHYSICAL_ADDRESS = 5;
    private static final int EXTERNAL_SOURCE_SPECIFIER_EXTERNAL_PLUG = 4;
    public static final int RECORDING_SEQUENCE_REPEAT_FRIDAY = 32;
    private static final int RECORDING_SEQUENCE_REPEAT_MASK = 127;
    public static final int RECORDING_SEQUENCE_REPEAT_MONDAY = 2;
    public static final int RECORDING_SEQUENCE_REPEAT_ONCE_ONLY = 0;
    public static final int RECORDING_SEQUENCE_REPEAT_SATUREDAY = 64;
    public static final int RECORDING_SEQUENCE_REPEAT_SUNDAY = 1;
    public static final int RECORDING_SEQUENCE_REPEAT_THURSDAY = 16;
    public static final int RECORDING_SEQUENCE_REPEAT_TUESDAY = 4;
    public static final int RECORDING_SEQUENCE_REPEAT_WEDNESDAY = 8;
    private static final String TAG = "HdmiTimerRecordingSources";

    private HdmiTimerRecordSources() {
    }

    public static TimerRecordSource ofDigitalSource(TimerInfo timerInfo, HdmiRecordSources.DigitalServiceSource digitalServiceSource) {
        checkTimerRecordSourceInputs(timerInfo, digitalServiceSource);
        return new TimerRecordSource(timerInfo, digitalServiceSource);
    }

    public static TimerRecordSource ofAnalogueSource(TimerInfo timerInfo, HdmiRecordSources.AnalogueServiceSource analogueServiceSource) {
        checkTimerRecordSourceInputs(timerInfo, analogueServiceSource);
        return new TimerRecordSource(timerInfo, analogueServiceSource);
    }

    public static TimerRecordSource ofExternalPlug(TimerInfo timerInfo, HdmiRecordSources.ExternalPlugData externalPlugData) {
        checkTimerRecordSourceInputs(timerInfo, externalPlugData);
        return new TimerRecordSource(timerInfo, new ExternalSourceDecorator(externalPlugData, 4));
    }

    public static TimerRecordSource ofExternalPhysicalAddress(TimerInfo timerInfo, HdmiRecordSources.ExternalPhysicalAddress externalPhysicalAddress) {
        checkTimerRecordSourceInputs(timerInfo, externalPhysicalAddress);
        return new TimerRecordSource(timerInfo, new ExternalSourceDecorator(externalPhysicalAddress, 5));
    }

    private static void checkTimerRecordSourceInputs(TimerInfo timerInfo, HdmiRecordSources.RecordSource recordSource) {
        if (timerInfo == null) {
            Log.w(TAG, "TimerInfo should not be null.");
            throw new IllegalArgumentException("TimerInfo should not be null.");
        }
        if (recordSource != null) {
            return;
        }
        Log.w(TAG, "source should not be null.");
        throw new IllegalArgumentException("source should not be null.");
    }

    public static Time timeOf(int i, int i2) {
        checkTimeValue(i, i2);
        return new Time(i, i2);
    }

    private static void checkTimeValue(int i, int i2) {
        if (i < 0 || i > 23) {
            throw new IllegalArgumentException("Hour should be in rage of [0, 23]:" + i);
        }
        if (i2 < 0 || i2 > 59) {
            throw new IllegalArgumentException("Minute should be in rage of [0, 59]:" + i2);
        }
    }

    public static Duration durationOf(int i, int i2) {
        checkDurationValue(i, i2);
        return new Duration(i, i2);
    }

    private static void checkDurationValue(int i, int i2) {
        if (i < 0 || i > 99) {
            throw new IllegalArgumentException("Hour should be in rage of [0, 99]:" + i);
        }
        if (i2 < 0 || i2 > 59) {
            throw new IllegalArgumentException("minute should be in rage of [0, 59]:" + i2);
        }
    }

    static class TimeUnit {
        final int mHour;
        final int mMinute;

        TimeUnit(int i, int i2) {
            this.mHour = i;
            this.mMinute = i2;
        }

        int toByteArray(byte[] bArr, int i) {
            bArr[i] = toBcdByte(this.mHour);
            bArr[i + 1] = toBcdByte(this.mMinute);
            return 2;
        }

        static byte toBcdByte(int i) {
            return (byte) ((i % 10) | (((i / 10) % 10) << 4));
        }
    }

    @SystemApi
    public static final class Time extends TimeUnit {
        private Time(int i, int i2) {
            super(i, i2);
        }
    }

    @SystemApi
    public static final class Duration extends TimeUnit {
        private Duration(int i, int i2) {
            super(i, i2);
        }
    }

    public static TimerInfo timerInfoOf(int i, int i2, Time time, Duration duration, int i3) {
        if (i < 0 || i > 31) {
            throw new IllegalArgumentException("Day of month should be in range of [0, 31]:" + i);
        }
        if (i2 < 1 || i2 > 12) {
            throw new IllegalArgumentException("Month of year should be in range of [1, 12]:" + i2);
        }
        checkTimeValue(time.mHour, time.mMinute);
        checkDurationValue(duration.mHour, duration.mMinute);
        if (i3 != 0 && (i3 & (-128)) != 0) {
            throw new IllegalArgumentException("Invalid reecording sequence value:" + i3);
        }
        return new TimerInfo(i, i2, time, duration, i3);
    }

    @SystemApi
    public static final class TimerInfo {
        private static final int BASIC_INFO_SIZE = 7;
        private static final int DAY_OF_MONTH_SIZE = 1;
        private static final int DURATION_SIZE = 2;
        private static final int MONTH_OF_YEAR_SIZE = 1;
        private static final int RECORDING_SEQUENCE_SIZE = 1;
        private static final int START_TIME_SIZE = 2;
        private final int mDayOfMonth;
        private final Duration mDuration;
        private final int mMonthOfYear;
        private final int mRecordingSequence;
        private final Time mStartTime;

        int getDataSize() {
            return 7;
        }

        private TimerInfo(int i, int i2, Time time, Duration duration, int i3) {
            this.mDayOfMonth = i;
            this.mMonthOfYear = i2;
            this.mStartTime = time;
            this.mDuration = duration;
            this.mRecordingSequence = i3;
        }

        int toByteArray(byte[] bArr, int i) {
            bArr[i] = (byte) this.mDayOfMonth;
            bArr[i + 1] = (byte) this.mMonthOfYear;
            int i2 = i + 2;
            int byteArray = i2 + this.mStartTime.toByteArray(bArr, i2);
            bArr[byteArray + this.mDuration.toByteArray(bArr, byteArray)] = (byte) this.mRecordingSequence;
            return getDataSize();
        }
    }

    @SystemApi
    public static final class TimerRecordSource {
        private final HdmiRecordSources.RecordSource mRecordSource;
        private final TimerInfo mTimerInfo;

        private TimerRecordSource(TimerInfo timerInfo, HdmiRecordSources.RecordSource recordSource) {
            this.mTimerInfo = timerInfo;
            this.mRecordSource = recordSource;
        }

        int getDataSize() {
            return this.mTimerInfo.getDataSize() + this.mRecordSource.getDataSize(false);
        }

        int toByteArray(byte[] bArr, int i) {
            this.mRecordSource.toByteArray(false, bArr, i + this.mTimerInfo.toByteArray(bArr, i));
            return getDataSize();
        }
    }

    private static class ExternalSourceDecorator extends HdmiRecordSources.RecordSource {
        private final int mExternalSourceSpecifier;
        private final HdmiRecordSources.RecordSource mRecordSource;

        private ExternalSourceDecorator(HdmiRecordSources.RecordSource recordSource, int i) {
            super(recordSource.mSourceType, recordSource.getDataSize(false) + 1);
            this.mRecordSource = recordSource;
            this.mExternalSourceSpecifier = i;
        }

        @Override // android.hardware.hdmi.HdmiRecordSources.RecordSource
        int extraParamToByteArray(byte[] bArr, int i) {
            bArr[i] = (byte) this.mExternalSourceSpecifier;
            this.mRecordSource.toByteArray(false, bArr, i + 1);
            return getDataSize(false);
        }
    }

    @SystemApi
    public static boolean checkTimerRecordSource(int i, byte[] bArr) {
        int length = bArr.length - 7;
        if (i == 1) {
            return 7 == length;
        }
        if (i == 2) {
            return 4 == length;
        }
        if (i != 3) {
            return false;
        }
        byte b = bArr[7];
        return b == 4 ? 2 == length : b == 5 && 3 == length;
    }
}

package android.hardware.radio;

import android.annotation.SystemApi;
import android.graphics.Bitmap;
import android.hardware.radio.ProgramList;
import android.hardware.radio.RadioManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;

@SystemApi
/* loaded from: classes2.dex */
public abstract class RadioTuner {
    public static final int DIRECTION_DOWN = 1;
    public static final int DIRECTION_UP = 0;

    @Deprecated
    public static final int ERROR_BACKGROUND_SCAN_FAILED = 6;

    @Deprecated
    public static final int ERROR_BACKGROUND_SCAN_UNAVAILABLE = 5;

    @Deprecated
    public static final int ERROR_CANCELLED = 2;

    @Deprecated
    public static final int ERROR_CONFIG = 4;

    @Deprecated
    public static final int ERROR_HARDWARE_FAILURE = 0;

    @Deprecated
    public static final int ERROR_SCAN_TIMEOUT = 3;

    @Deprecated
    public static final int ERROR_SERVER_DIED = 1;
    public static final int TUNER_RESULT_CANCELED = 6;
    public static final int TUNER_RESULT_INTERNAL_ERROR = 1;
    public static final int TUNER_RESULT_INVALID_ARGUMENTS = 2;
    public static final int TUNER_RESULT_INVALID_STATE = 3;
    public static final int TUNER_RESULT_NOT_SUPPORTED = 4;
    public static final int TUNER_RESULT_OK = 0;
    public static final int TUNER_RESULT_TIMEOUT = 5;
    public static final int TUNER_RESULT_UNKNOWN_ERROR = 7;

    public static abstract class Callback {
        public void onAntennaState(boolean z) {
        }

        public void onBackgroundScanAvailabilityChange(boolean z) {
        }

        public void onBackgroundScanComplete() {
        }

        public void onConfigFlagUpdated(int i, boolean z) {
        }

        @Deprecated
        public void onConfigurationChanged(RadioManager.BandConfig bandConfig) {
        }

        public void onControlChanged(boolean z) {
        }

        public void onEmergencyAnnouncement(boolean z) {
        }

        public void onError(int i) {
        }

        @Deprecated
        public void onMetadataChanged(RadioMetadata radioMetadata) {
        }

        public void onParametersUpdated(Map<String, String> map) {
        }

        public void onProgramInfoChanged(RadioManager.ProgramInfo programInfo) {
        }

        public void onProgramListChanged() {
        }

        public void onTrafficAnnouncement(boolean z) {
        }

        public void onTuneFailed(int i, ProgramSelector programSelector) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TunerResultType {
    }

    public abstract int cancel();

    @Deprecated
    public abstract void cancelAnnouncement();

    public abstract void close();

    @Deprecated
    public abstract int getConfiguration(RadioManager.BandConfig[] bandConfigArr);

    public ProgramList getDynamicProgramList(ProgramList.Filter filter) {
        return null;
    }

    public abstract boolean getMute();

    @Deprecated
    public abstract int getProgramInformation(RadioManager.ProgramInfo[] programInfoArr);

    @Deprecated
    public abstract List<RadioManager.ProgramInfo> getProgramList(Map<String, String> map);

    public abstract boolean hasControl();

    @Deprecated
    public abstract boolean isAnalogForced();

    @Deprecated
    public abstract boolean isAntennaConnected();

    public boolean isConfigFlagSupported(int i) {
        return false;
    }

    @Deprecated
    public abstract int scan(int i, boolean z);

    @Deprecated
    public abstract void setAnalogForced(boolean z);

    @Deprecated
    public abstract int setConfiguration(RadioManager.BandConfig bandConfig);

    public abstract int setMute(boolean z);

    @Deprecated
    public abstract boolean startBackgroundScan();

    public abstract int step(int i, boolean z);

    @Deprecated
    public abstract int tune(int i, int i2);

    public abstract void tune(ProgramSelector programSelector);

    public int seek(int i, boolean z) {
        throw new UnsupportedOperationException("Seeking is not supported");
    }

    public Bitmap getMetadataImage(int i) {
        throw new UnsupportedOperationException("Getting metadata image must be implemented in child classes");
    }

    public boolean isConfigFlagSet(int i) {
        throw new UnsupportedOperationException("isConfigFlagSet is not supported");
    }

    public void setConfigFlag(int i, boolean z) {
        throw new UnsupportedOperationException("Setting config flag is not supported");
    }

    public Map<String, String> setParameters(Map<String, String> map) {
        throw new UnsupportedOperationException("Setting parameters is not supported");
    }

    public Map<String, String> getParameters(List<String> list) {
        throw new UnsupportedOperationException("Getting parameters is not supported");
    }
}

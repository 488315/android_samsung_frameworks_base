package android.os.vibrator;

import android.content.res.Resources;
import android.os.VibrationAttributes;
import android.util.IndentingPrintWriter;
import com.android.internal.R;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class VibrationConfig {
    private static final int DEFAULT_AMPLITUDE = 255;
    private static final float DEFAULT_SCALE_LEVEL_GAIN = 1.4f;
    private final int mDefaultAlarmVibrationIntensity;
    private final int mDefaultHapticFeedbackIntensity;
    private final int mDefaultKeyboardVibrationIntensity;
    private final int mDefaultMediaVibrationIntensity;
    private final int mDefaultNotificationVibrationIntensity;
    private final int mDefaultRingVibrationIntensity;
    private final int mDefaultVibrationAmplitude;
    private final float mHapticChannelMaxVibrationAmplitude;
    private final boolean mIgnoreVibrationsOnWirelessCharger;
    private final boolean mKeyboardVibrationSettingsSupported;
    private final int mRampDownDurationMs;
    private final int mRampStepDurationMs;
    private final int[] mRequestVibrationParamsForUsages;
    private final int mRequestVibrationParamsTimeoutMs;
    private final int mVibrationPipelineMaxDurationMs;

    public float getDefaultVibrationScaleLevelGain() {
        return DEFAULT_SCALE_LEVEL_GAIN;
    }

    public VibrationConfig(Resources resources) {
        this.mDefaultVibrationAmplitude = resources.getInteger(R.integer.config_defaultVibrationAmplitude);
        this.mHapticChannelMaxVibrationAmplitude = loadFloat(resources, R.dimen.config_hapticChannelMaxVibrationAmplitude);
        this.mRampDownDurationMs = loadInteger(resources, R.integer.config_vibrationWaveformRampDownDuration, 0);
        this.mRampStepDurationMs = loadInteger(resources, R.integer.config_vibrationWaveformRampStepDuration, 0);
        this.mRequestVibrationParamsTimeoutMs = loadInteger(resources, R.integer.config_requestVibrationParamsTimeout, 0);
        this.mRequestVibrationParamsForUsages = loadIntArray(resources, R.array.config_requestVibrationParamsForUsages);
        this.mIgnoreVibrationsOnWirelessCharger = loadBoolean(resources, R.bool.config_ignoreVibrationsOnWirelessCharger);
        this.mKeyboardVibrationSettingsSupported = loadBoolean(resources, R.bool.config_keyboardVibrationSettingsSupported);
        this.mVibrationPipelineMaxDurationMs = loadInteger(resources, R.integer.config_vibrationPipelineMaxDuration, 0);
        this.mDefaultAlarmVibrationIntensity = loadDefaultIntensity(resources, R.integer.config_defaultAlarmVibrationIntensity);
        this.mDefaultHapticFeedbackIntensity = loadDefaultIntensity(resources, R.integer.config_defaultHapticFeedbackIntensity);
        this.mDefaultMediaVibrationIntensity = loadDefaultIntensity(resources, R.integer.config_defaultMediaVibrationIntensity);
        this.mDefaultNotificationVibrationIntensity = loadDefaultIntensity(resources, R.integer.config_defaultNotificationVibrationIntensity);
        this.mDefaultRingVibrationIntensity = loadDefaultIntensity(resources, R.integer.config_defaultRingVibrationIntensity);
        this.mDefaultKeyboardVibrationIntensity = loadDefaultIntensity(resources, R.integer.config_defaultKeyboardVibrationIntensity);
    }

    private static int loadDefaultIntensity(Resources resources, int i) {
        int iLoadInteger = loadInteger(resources, i, 2);
        if (iLoadInteger < 0 || iLoadInteger > 3) {
            return 2;
        }
        return iLoadInteger;
    }

    private static float loadFloat(Resources resources, int i) {
        if (resources != null) {
            return resources.getFloat(i);
        }
        return 0.0f;
    }

    private static int loadInteger(Resources resources, int i, int i2) {
        return resources != null ? resources.getInteger(i) : i2;
    }

    private static boolean loadBoolean(Resources resources, int i) {
        return resources != null && resources.getBoolean(i);
    }

    private static int[] loadIntArray(Resources resources, int i) {
        return resources != null ? resources.getIntArray(i) : new int[0];
    }

    public float getHapticChannelMaximumAmplitude() {
        float f = this.mHapticChannelMaxVibrationAmplitude;
        if (f <= 0.0f) {
            return Float.NaN;
        }
        return f;
    }

    public int getDefaultVibrationAmplitude() {
        int i = this.mDefaultVibrationAmplitude;
        if (i < 1 || i > 255) {
            return 255;
        }
        return i;
    }

    public int getRampDownDurationMs() {
        int i = this.mRampDownDurationMs;
        if (i < 0) {
            return 0;
        }
        return i;
    }

    public int getRequestVibrationParamsTimeoutMs() {
        return Math.max(this.mRequestVibrationParamsTimeoutMs, 0);
    }

    public int[] getRequestVibrationParamsForUsages() {
        return this.mRequestVibrationParamsForUsages;
    }

    public int getRampStepDurationMs() {
        int i = this.mRampStepDurationMs;
        if (i < 0) {
            return 0;
        }
        return i;
    }

    public int getVibrationPipelineMaxDurationMs() {
        int i = this.mVibrationPipelineMaxDurationMs;
        if (i < 0) {
            return 0;
        }
        return i;
    }

    public boolean ignoreVibrationsOnWirelessCharger() {
        return this.mIgnoreVibrationsOnWirelessCharger;
    }

    public boolean isKeyboardVibrationSettingsSupported() {
        return this.mKeyboardVibrationSettingsSupported;
    }

    public int getDefaultVibrationIntensity(int i) {
        if (i == 17) {
            return this.mDefaultAlarmVibrationIntensity;
        }
        if (i != 18) {
            if (i == 33) {
                return this.mDefaultRingVibrationIntensity;
            }
            if (i != 34) {
                if (i != 49) {
                    if (i != 50) {
                        if (i != 65) {
                            if (i != 66) {
                                if (i != 82) {
                                    return this.mDefaultMediaVibrationIntensity;
                                }
                                return isKeyboardVibrationSettingsSupported() ? this.mDefaultKeyboardVibrationIntensity : this.mDefaultHapticFeedbackIntensity;
                            }
                        }
                    }
                }
                return this.mDefaultNotificationVibrationIntensity;
            }
        }
        return this.mDefaultHapticFeedbackIntensity;
    }

    public String toString() {
        return "VibrationConfig{mIgnoreVibrationsOnWirelessCharger=" + this.mIgnoreVibrationsOnWirelessCharger + ", mDefaultVibrationAmplitude=" + this.mDefaultVibrationAmplitude + ", mHapticChannelMaxVibrationAmplitude=" + this.mHapticChannelMaxVibrationAmplitude + ", mRampStepDurationMs=" + this.mRampStepDurationMs + ", mRampDownDurationMs=" + this.mRampDownDurationMs + ", mRequestVibrationParamsForUsages=" + Arrays.toString(getRequestVibrationParamsForUsagesNames()) + ", mRequestVibrationParamsTimeoutMs=" + this.mRequestVibrationParamsTimeoutMs + ", mDefaultAlarmIntensity=" + this.mDefaultAlarmVibrationIntensity + ", mDefaultHapticFeedbackIntensity=" + this.mDefaultHapticFeedbackIntensity + ", mDefaultMediaIntensity=" + this.mDefaultMediaVibrationIntensity + ", mDefaultNotificationIntensity=" + this.mDefaultNotificationVibrationIntensity + ", mDefaultRingIntensity=" + this.mDefaultRingVibrationIntensity + ", mDefaultKeyboardIntensity=" + this.mDefaultKeyboardVibrationIntensity + ", mKeyboardVibrationSettingsSupported=" + this.mKeyboardVibrationSettingsSupported + "}";
    }

    public void dumpWithoutDefaultSettings(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("VibrationConfig:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("ignoreVibrationsOnWirelessCharger = " + this.mIgnoreVibrationsOnWirelessCharger);
        indentingPrintWriter.println("defaultVibrationAmplitude = " + this.mDefaultVibrationAmplitude);
        indentingPrintWriter.println("hapticChannelMaxAmplitude = " + this.mHapticChannelMaxVibrationAmplitude);
        indentingPrintWriter.println("rampStepDurationMs = " + this.mRampStepDurationMs);
        indentingPrintWriter.println("rampDownDurationMs = " + this.mRampDownDurationMs);
        indentingPrintWriter.println("requestVibrationParamsForUsages = " + Arrays.toString(getRequestVibrationParamsForUsagesNames()));
        indentingPrintWriter.println("requestVibrationParamsTimeoutMs = " + this.mRequestVibrationParamsTimeoutMs);
        indentingPrintWriter.decreaseIndent();
    }

    private String[] getRequestVibrationParamsForUsagesNames() {
        int length = this.mRequestVibrationParamsForUsages.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = VibrationAttributes.usageToString(this.mRequestVibrationParamsForUsages[i]);
        }
        return strArr;
    }
}

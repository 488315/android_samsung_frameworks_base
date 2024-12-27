package android.os.vibrator;

import android.content.res.Resources;
import android.os.VibrationAttributes;
import android.util.IndentingPrintWriter;
import com.android.internal.R;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class VibrationConfig {
    private final int mDefaultAlarmVibrationIntensity;
    private final int mDefaultHapticFeedbackIntensity;
    private final boolean mDefaultKeyboardVibrationEnabled;
    private final int mDefaultMediaVibrationIntensity;
    private final int mDefaultNotificationVibrationIntensity;
    private final int mDefaultRingVibrationIntensity;
    private final float mHapticChannelMaxVibrationAmplitude;
    private final boolean mHasFixedKeyboardAmplitude;
    private final boolean mIgnoreVibrationsOnWirelessCharger;
    private final int mRampDownDurationMs;
    private final int mRampStepDurationMs;
    private final int[] mRequestVibrationParamsForUsages;
    private final int mRequestVibrationParamsTimeoutMs;

    public VibrationConfig(Resources resources) {
        this.mHapticChannelMaxVibrationAmplitude = loadFloat(resources, R.dimen.config_hapticChannelMaxVibrationAmplitude, 0.0f);
        this.mRampDownDurationMs = loadInteger(resources, R.integer.config_vibrationWaveformRampDownDuration, 0);
        this.mRampStepDurationMs = loadInteger(resources, R.integer.config_vibrationWaveformRampStepDuration, 0);
        this.mRequestVibrationParamsTimeoutMs = loadInteger(resources, R.integer.config_requestVibrationParamsTimeout, 0);
        this.mRequestVibrationParamsForUsages = loadIntArray(resources, R.array.config_requestVibrationParamsForUsages);
        this.mIgnoreVibrationsOnWirelessCharger = loadBoolean(resources, R.bool.config_ignoreVibrationsOnWirelessCharger, false);
        this.mDefaultKeyboardVibrationEnabled = loadBoolean(resources, R.bool.config_defaultKeyboardVibrationEnabled, true);
        this.mHasFixedKeyboardAmplitude = loadFloat(resources, R.dimen.config_keyboardHapticFeedbackFixedAmplitude, -1.0f) > 0.0f;
        this.mDefaultAlarmVibrationIntensity = loadDefaultIntensity(resources, R.integer.config_defaultAlarmVibrationIntensity);
        this.mDefaultHapticFeedbackIntensity = loadDefaultIntensity(resources, R.integer.config_defaultHapticFeedbackIntensity);
        this.mDefaultMediaVibrationIntensity = loadDefaultIntensity(resources, R.integer.config_defaultMediaVibrationIntensity);
        this.mDefaultNotificationVibrationIntensity = loadDefaultIntensity(resources, R.integer.config_defaultNotificationVibrationIntensity);
        this.mDefaultRingVibrationIntensity = loadDefaultIntensity(resources, R.integer.config_defaultRingVibrationIntensity);
    }

    private static int loadDefaultIntensity(Resources res, int resId) {
        int value = loadInteger(res, resId, 2);
        if (value < 0 || value > 3) {
            return 2;
        }
        return value;
    }

    private static float loadFloat(Resources res, int resId, float defaultValue) {
        return res != null ? res.getFloat(resId) : defaultValue;
    }

    private static int loadInteger(Resources res, int resId, int defaultValue) {
        return res != null ? res.getInteger(resId) : defaultValue;
    }

    private static boolean loadBoolean(Resources res, int resId, boolean defaultValue) {
        return res != null ? res.getBoolean(resId) : defaultValue;
    }

    private static int[] loadIntArray(Resources res, int resId) {
        return res != null ? res.getIntArray(resId) : new int[0];
    }

    public float getHapticChannelMaximumAmplitude() {
        if (this.mHapticChannelMaxVibrationAmplitude <= 0.0f) {
            return Float.NaN;
        }
        return this.mHapticChannelMaxVibrationAmplitude;
    }

    public int getRampDownDurationMs() {
        if (this.mRampDownDurationMs < 0) {
            return 0;
        }
        return this.mRampDownDurationMs;
    }

    public int getRequestVibrationParamsTimeoutMs() {
        return Math.max(this.mRequestVibrationParamsTimeoutMs, 0);
    }

    public int[] getRequestVibrationParamsForUsages() {
        return this.mRequestVibrationParamsForUsages;
    }

    public int getRampStepDurationMs() {
        if (this.mRampStepDurationMs < 0) {
            return 0;
        }
        return this.mRampStepDurationMs;
    }

    public boolean ignoreVibrationsOnWirelessCharger() {
        return this.mIgnoreVibrationsOnWirelessCharger;
    }

    public boolean isDefaultKeyboardVibrationEnabled() {
        return this.mDefaultKeyboardVibrationEnabled;
    }

    public boolean hasFixedKeyboardAmplitude() {
        return this.mHasFixedKeyboardAmplitude;
    }

    public int getDefaultVibrationIntensity(int usage) {
        switch (usage) {
            case 17:
                return this.mDefaultAlarmVibrationIntensity;
            case 18:
            case 34:
            case 50:
            case 66:
                return this.mDefaultHapticFeedbackIntensity;
            case 33:
                return this.mDefaultRingVibrationIntensity;
            case 49:
            case 65:
                return this.mDefaultNotificationVibrationIntensity;
            default:
                return this.mDefaultMediaVibrationIntensity;
        }
    }

    public String toString() {
        return "VibrationConfig{mIgnoreVibrationsOnWirelessCharger=" + this.mIgnoreVibrationsOnWirelessCharger + ", mHapticChannelMaxVibrationAmplitude=" + this.mHapticChannelMaxVibrationAmplitude + ", mRampStepDurationMs=" + this.mRampStepDurationMs + ", mRampDownDurationMs=" + this.mRampDownDurationMs + ", mRequestVibrationParamsForUsages=" + Arrays.toString(getRequestVibrationParamsForUsagesNames()) + ", mRequestVibrationParamsTimeoutMs=" + this.mRequestVibrationParamsTimeoutMs + ", mDefaultAlarmIntensity=" + this.mDefaultAlarmVibrationIntensity + ", mDefaultHapticFeedbackIntensity=" + this.mDefaultHapticFeedbackIntensity + ", mDefaultMediaIntensity=" + this.mDefaultMediaVibrationIntensity + ", mDefaultNotificationIntensity=" + this.mDefaultNotificationVibrationIntensity + ", mDefaultRingIntensity=" + this.mDefaultRingVibrationIntensity + ", mDefaultKeyboardVibrationEnabled=" + this.mDefaultKeyboardVibrationEnabled + "}";
    }

    public void dumpWithoutDefaultSettings(IndentingPrintWriter pw) {
        pw.println("VibrationConfig:");
        pw.increaseIndent();
        pw.println("ignoreVibrationsOnWirelessCharger = " + this.mIgnoreVibrationsOnWirelessCharger);
        pw.println("hapticChannelMaxAmplitude = " + this.mHapticChannelMaxVibrationAmplitude);
        pw.println("rampStepDurationMs = " + this.mRampStepDurationMs);
        pw.println("rampDownDurationMs = " + this.mRampDownDurationMs);
        pw.println("requestVibrationParamsForUsages = " + Arrays.toString(getRequestVibrationParamsForUsagesNames()));
        pw.println("requestVibrationParamsTimeoutMs = " + this.mRequestVibrationParamsTimeoutMs);
        pw.decreaseIndent();
    }

    private String[] getRequestVibrationParamsForUsagesNames() {
        int usagesCount = this.mRequestVibrationParamsForUsages.length;
        String[] names = new String[usagesCount];
        for (int i = 0; i < usagesCount; i++) {
            names[i] = VibrationAttributes.usageToString(this.mRequestVibrationParamsForUsages[i]);
        }
        return names;
    }
}

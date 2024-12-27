package com.android.server.vibrator;

import android.R;
import android.content.Context;
import android.os.VibrationAttributes;
import android.os.vibrator.Flags;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VibrationScaler {
    public static final ScaleLevel SCALE_LEVEL_NONE = new ScaleLevel(1.0f);
    public final SparseArray mAdaptiveHapticsScales = new SparseArray();
    public final int mDefaultVibrationAmplitude;
    public final SparseArray mScaleLevels;
    public final VibrationSettings mSettingsController;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScaleLevel {
        public final float factor;

        public ScaleLevel(float f) {
            this.factor = f;
        }

        public final String toString() {
            return "ScaleLevel{factor=" + this.factor + "}";
        }
    }

    public VibrationScaler(Context context, VibrationSettings vibrationSettings) {
        this.mSettingsController = vibrationSettings;
        this.mDefaultVibrationAmplitude = context.getResources().getInteger(R.integer.config_dreamOpenAnimationDuration);
        SparseArray sparseArray = new SparseArray();
        this.mScaleLevels = sparseArray;
        sparseArray.put(-2, new ScaleLevel(0.6f));
        sparseArray.put(-1, new ScaleLevel(0.8f));
        sparseArray.put(0, SCALE_LEVEL_NONE);
        sparseArray.put(1, new ScaleLevel(1.2f));
        sparseArray.put(2, new ScaleLevel(1.4f));
    }

    public static String scaleLevelToString(int i) {
        return i != -2 ? i != -1 ? i != 0 ? i != 1 ? i != 2 ? String.valueOf(i) : "VERY_HIGH" : "HIGH" : "NONE" : "LOW" : "VERY_LOW";
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("VibrationScaler:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("defaultVibrationAmplitude = " + this.mDefaultVibrationAmplitude);
        indentingPrintWriter.println("ScaleLevels:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mScaleLevels.size(); i++) {
            int keyAt = this.mScaleLevels.keyAt(i);
            indentingPrintWriter.println(scaleLevelToString(keyAt) + " = " + ((ScaleLevel) this.mScaleLevels.valueAt(i)));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("AdaptiveHapticsScales:");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < this.mAdaptiveHapticsScales.size(); i2++) {
            int keyAt2 = this.mAdaptiveHapticsScales.keyAt(i2);
            Float f = (Float) this.mAdaptiveHapticsScales.valueAt(i2);
            f.getClass();
            indentingPrintWriter.println(VibrationAttributes.usageToString(keyAt2) + " = " + String.format(Locale.ROOT, "%.2f", f));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public final float getAdaptiveHapticsScale(int i) {
        if (Flags.adaptiveHapticsEnabled()) {
            return ((Float) this.mAdaptiveHapticsScales.get(i, Float.valueOf(1.0f))).floatValue();
        }
        return 1.0f;
    }

    public final int getEffectStrength(int i) {
        VibrationSettings vibrationSettings = this.mSettingsController;
        int currentIntensity = vibrationSettings.getCurrentIntensity(i);
        if (currentIntensity == 0) {
            currentIntensity = vibrationSettings.mVibrationConfig.getDefaultVibrationIntensity(i);
        }
        if (currentIntensity == 1) {
            return 0;
        }
        if (currentIntensity == 2) {
            return 1;
        }
        if (currentIntensity != 3) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(currentIntensity, "Got unexpected vibration intensity: ", "VibrationScaler");
        }
        return 2;
    }

    public final int getScaleLevel(int i) {
        VibrationSettings vibrationSettings = this.mSettingsController;
        int defaultVibrationIntensity = vibrationSettings.mVibrationConfig.getDefaultVibrationIntensity(i);
        int currentIntensity = vibrationSettings.getCurrentIntensity(i);
        if (currentIntensity == 0) {
            return 0;
        }
        int i2 = currentIntensity - defaultVibrationIntensity;
        if (i2 >= -2 && i2 <= 2) {
            return i2;
        }
        Slog.wtf("VibrationScaler", "Error in scaling calculations, ended up with invalid scale level " + i2 + " for vibration with usage " + i);
        return 0;
    }

    public final String toString() {
        return "VibrationScaler{mScaleLevels=" + this.mScaleLevels + ", mDefaultVibrationAmplitude=" + this.mDefaultVibrationAmplitude + ", mAdaptiveHapticsScales=" + this.mAdaptiveHapticsScales + '}';
    }
}

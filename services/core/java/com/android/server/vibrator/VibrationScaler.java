package com.android.server.vibrator;

import android.R;
import android.content.Context;
import android.os.vibrator.PrebakedSegment;
import android.util.Slog;
import android.util.SparseArray;

/* loaded from: classes3.dex */
public final class VibrationScaler {
    public final int mDefaultVibrationAmplitude;
    public final SparseArray mScaleLevels;
    public final VibrationSettings mSettingsController;

    public VibrationScaler(Context context, VibrationSettings vibrationSettings) {
        this.mSettingsController = vibrationSettings;
        this.mDefaultVibrationAmplitude = context.getResources().getInteger(R.integer.config_dockedStackDividerSnapMode);
        SparseArray sparseArray = new SparseArray();
        this.mScaleLevels = sparseArray;
        sparseArray.put(-2, new ScaleLevel(0.6f));
        sparseArray.put(-1, new ScaleLevel(0.8f));
        sparseArray.put(0, new ScaleLevel(1.0f));
        sparseArray.put(1, new ScaleLevel(1.2f));
        sparseArray.put(2, new ScaleLevel(1.4f));
    }

    public int getExternalVibrationScale(int i) {
        int defaultIntensity = this.mSettingsController.getDefaultIntensity(i);
        int currentIntensity = this.mSettingsController.getCurrentIntensity(i);
        if (currentIntensity == 0) {
            return 0;
        }
        int i2 = currentIntensity - defaultIntensity;
        if (i2 >= -2 && i2 <= 2) {
            return i2;
        }
        Slog.w("VibrationScaler", "Error in scaling calculations, ended up with invalid scale level " + i2 + " for vibration with usage " + i);
        return 0;
    }

    public PrebakedSegment scale(PrebakedSegment prebakedSegment, int i) {
        int currentIntensity = this.mSettingsController.getCurrentIntensity(i);
        if (currentIntensity == 0) {
            currentIntensity = this.mSettingsController.getDefaultIntensity(i);
        }
        return prebakedSegment.applyEffectStrength(intensityToEffectStrength(currentIntensity));
    }

    public static int intensityToEffectStrength(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i != 3) {
            Slog.w("VibrationScaler", "Got unexpected vibration intensity: " + i);
        }
        return 2;
    }

    public final class ScaleLevel {
        public final float factor;

        public ScaleLevel(float f) {
            this.factor = f;
        }

        public String toString() {
            return "ScaleLevel{factor=" + this.factor + "}";
        }
    }
}

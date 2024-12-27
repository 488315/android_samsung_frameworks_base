package com.android.server.notification;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.media.AudioAttributes;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Slog;

import java.time.Duration;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VibratorHelper {
    public static final long[] DEFAULT_VIBRATE_PATTERN = {0, 250, 250, 250};
    public final int mDefaultVibrationAmplitude;
    public final long[] mFallbackPattern;
    public final float[] mFallbackPwlePattern;
    public final Vibrator mVibrator;

    public VibratorHelper(Context context) {
        this.mVibrator = (Vibrator) context.getSystemService(Vibrator.class);
        Resources resources = context.getResources();
        long[] jArr = DEFAULT_VIBRATE_PATTERN;
        getLongArray(resources, R.array.config_smallAreaDetectionAllowlist, jArr);
        this.mFallbackPattern = getLongArray(context.getResources(), 17236266, jArr);
        getFloatArray(context.getResources(), R.array.config_sms_enabled_locking_shift_tables);
        this.mFallbackPwlePattern = getFloatArray(context.getResources(), 17236267);
        this.mDefaultVibrationAmplitude =
                context.getResources().getInteger(R.integer.config_dreamOpenAnimationDuration);
    }

    public static VibrationEffect createPwleWaveformVibration(float[] fArr, boolean z) {
        if (fArr == null) {
            return null;
        }
        try {
            int length = fArr.length;
            if (length != 0 && length % 3 == 0) {
                VibrationEffect.WaveformBuilder startWaveform = VibrationEffect.startWaveform();
                for (int i = 0; i < length; i += 3) {
                    startWaveform.addTransition(
                            Duration.ofMillis((int) fArr[i + 2]),
                            VibrationEffect.VibrationParameter.targetAmplitude(fArr[i]),
                            VibrationEffect.VibrationParameter.targetFrequency(fArr[i + 1]));
                }
                VibrationEffect build = startWaveform.build();
                return z
                        ? VibrationEffect.startComposition()
                                .repeatEffectIndefinitely(build)
                                .compose()
                        : build;
            }
            return null;
        } catch (IllegalArgumentException unused) {
            Slog.e(
                    "NotificationVibratorHelper",
                    "Error creating vibration PWLE waveform with pattern: "
                            + Arrays.toString(fArr));
            return null;
        }
    }

    public static VibrationEffect createWaveformVibration(long[] jArr, boolean z) {
        if (jArr == null) {
            return null;
        }
        try {
            return VibrationEffect.createWaveform(jArr, z ? 0 : -1);
        } catch (IllegalArgumentException unused) {
            Slog.e(
                    "NotificationVibratorHelper",
                    "Error creating vibration waveform with pattern: " + Arrays.toString(jArr));
            return null;
        }
    }

    public static float[] getFloatArray(Resources resources, int i) {
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            int length = obtainTypedArray.length();
            float[] fArr = new float[length];
            for (int i2 = 0; i2 < length; i2++) {
                float f = obtainTypedArray.getFloat(i2, Float.NaN);
                fArr[i2] = f;
                if (Float.isNaN(f)) {
                    obtainTypedArray.recycle();
                    return null;
                }
            }
            return fArr;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    public static long[] getLongArray(Resources resources, int i, long[] jArr) {
        int[] intArray = resources.getIntArray(i);
        if (intArray == null) {
            return jArr;
        }
        int length = intArray.length <= 17 ? intArray.length : 17;
        long[] jArr2 = new long[length];
        for (int i2 = 0; i2 < length; i2++) {
            jArr2[i2] = intArray[i2];
        }
        return jArr2;
    }

    public final void vibrate(
            VibrationEffect vibrationEffect, AudioAttributes audioAttributes, String str) {
        this.mVibrator.vibrate(
                1000,
                "android",
                vibrationEffect,
                str,
                new VibrationAttributes.Builder(audioAttributes).build());
    }
}

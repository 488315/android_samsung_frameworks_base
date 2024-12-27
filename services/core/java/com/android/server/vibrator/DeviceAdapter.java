package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.VibrationEffect;
import android.os.VibratorInfo;
import android.util.Slog;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DeviceAdapter implements CombinedVibration.VibratorAdapter {
    public final int[] mAvailableVibratorIds;
    public final SparseArray mAvailableVibrators;
    public final List mSegmentAdapters;

    public DeviceAdapter(VibrationSettings vibrationSettings, SparseArray sparseArray) {
        this.mSegmentAdapters =
                Arrays.asList(
                        new RampToStepAdapter(
                                vibrationSettings.mVibrationConfig.getRampStepDurationMs()),
                        new StepToRampAdapter(),
                        new RampDownAdapter(
                                vibrationSettings.mVibrationConfig.getRampDownDurationMs(),
                                vibrationSettings.mVibrationConfig.getRampStepDurationMs()),
                        new SplitSegmentsAdapter(),
                        new ClippingAmplitudeAndFrequencyAdapter());
        this.mAvailableVibrators = sparseArray;
        this.mAvailableVibratorIds = new int[sparseArray.size()];
        for (int i = 0; i < sparseArray.size(); i++) {
            this.mAvailableVibratorIds[i] = sparseArray.keyAt(i);
        }
    }

    public final VibrationEffect adaptToVibrator(int i, VibrationEffect vibrationEffect) {
        if (!(vibrationEffect instanceof VibrationEffect.Composed)) {
            Slog.wtf(
                    "DeviceAdapter",
                    "Error adapting unsupported vibration effect: " + vibrationEffect);
            return vibrationEffect;
        }
        VibratorController vibratorController =
                (VibratorController) this.mAvailableVibrators.get(i);
        if (vibratorController == null) {
            return vibrationEffect;
        }
        VibratorInfo vibratorInfo = vibratorController.mVibratorInfo;
        VibrationEffect.Composed composed = (VibrationEffect.Composed) vibrationEffect;
        ArrayList arrayList = new ArrayList(composed.getSegments());
        int repeatIndex = composed.getRepeatIndex();
        int size = this.mSegmentAdapters.size();
        for (int i2 = 0; i2 < size; i2++) {
            repeatIndex =
                    ((VibrationSegmentsAdapter) this.mSegmentAdapters.get(i2))
                            .adaptToVibrator(vibratorInfo, arrayList, repeatIndex);
        }
        return new VibrationEffect.Composed(arrayList, repeatIndex);
    }

    public final int[] getAvailableVibratorIds() {
        return this.mAvailableVibratorIds;
    }
}

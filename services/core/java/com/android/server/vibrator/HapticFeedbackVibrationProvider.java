package com.android.server.vibrator;

import android.R;
import android.content.res.Resources;
import android.os.VibrationAttributes;
import android.os.Vibrator;
import android.os.VibratorInfo;
import android.os.vibrator.Flags;
import android.util.SparseArray;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HapticFeedbackVibrationProvider {
    public final boolean mHapticTextHandleEnabled;
    public boolean mIsDcMotor;
    public final float mKeyboardVibrationFixedAmplitude;
    public final VibratorInfo mVibratorInfo;
    public static final VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES =
            VibrationAttributes.createForUsage(18);
    public static final VibrationAttributes PHYSICAL_EMULATION_VIBRATION_ATTRIBUTES =
            VibrationAttributes.createForUsage(34);
    public static final VibrationAttributes HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES =
            VibrationAttributes.createForUsage(50);
    public static final VibrationAttributes COMMUNICATION_REQUEST_VIBRATION_ATTRIBUTES =
            VibrationAttributes.createForUsage(65);

    public HapticFeedbackVibrationProvider(Resources resources, Vibrator vibrator) {
        this(resources, vibrator.getInfo());
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public HapticFeedbackVibrationProvider(
            android.content.res.Resources r4, android.os.VibratorInfo r5) {
        /*
            r3 = this;
            android.util.SparseArray r0 = com.android.server.vibrator.HapticFeedbackCustomization.loadVibrationsInternal(r4, r5)     // Catch: java.lang.Throwable -> L5 java.lang.Throwable -> Le
            goto L17
        L5:
            r0 = move-exception
            com.android.server.vibrator.HapticFeedbackCustomization$CustomizationParserException r1 = new com.android.server.vibrator.HapticFeedbackCustomization$CustomizationParserException     // Catch: java.lang.Throwable -> Le java.lang.Throwable -> Le
            java.lang.String r2 = "Error parsing haptic feedback customization file."
            r1.<init>(r2, r0)     // Catch: java.lang.Throwable -> Le java.lang.Throwable -> Le
            throw r1     // Catch: java.lang.Throwable -> Le java.lang.Throwable -> Le
        Le:
            r0 = move-exception
            java.lang.String r1 = "HapticFeedbackVibrationProvider"
            java.lang.String r2 = "Unable to load haptic customizations."
            android.util.Slog.e(r1, r2, r0)
            r0 = 0
        L17:
            r3.<init>(r4, r5, r0)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.vibrator.HapticFeedbackVibrationProvider.<init>(android.content.res.Resources,"
                    + " android.os.VibratorInfo):void");
    }

    public HapticFeedbackVibrationProvider(
            Resources resources, VibratorInfo vibratorInfo, SparseArray sparseArray) {
        this.mIsDcMotor = false;
        this.mVibratorInfo = vibratorInfo;
        this.mHapticTextHandleEnabled =
                resources.getBoolean(R.bool.config_enableNewAutoSelectNetworkUI);
        if (sparseArray != null && sparseArray.size() == 0) {
            sparseArray = null;
        }
        if (sparseArray == null || !sparseArray.contains(10001)) {
            VibrationSettings.createEffectFromResource(resources, 17236299);
        }
        float f = resources.getFloat(R.dimen.config_qsTileStrokeWidthInactive);
        this.mKeyboardVibrationFixedAmplitude = f;
        if (f < FullScreenMagnificationGestureHandler.MAX_SCALE || f > 1.0f) {
            this.mKeyboardVibrationFixedAmplitude = -1.0f;
        }
    }

    public final VibrationAttributes getVibrationAttributesForHapticFeedback(
            int i, boolean z, boolean z2) {
        VibrationAttributes build;
        boolean scrollFeedbackApi;
        if (i != 3 && i != 7) {
            if (i != 14 && i != 15) {
                switch (i) {
                    default:
                        switch (i) {
                            case 10002:
                            case 10003:
                                break;
                            case 10004:
                            case FrameworkStatsLog.SUBSYSTEM_SLEEP_STATE /* 10005 */:
                                build = COMMUNICATION_REQUEST_VIBRATION_ATTRIBUTES;
                                break;
                            default:
                                build = TOUCH_VIBRATION_ATTRIBUTES;
                                break;
                        }
                    case 18:
                    case 19:
                    case 20:
                        build = HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES;
                        break;
                }
            } else {
                build = PHYSICAL_EMULATION_VIBRATION_ATTRIBUTES;
            }
        } else {
            build =
                    (Flags.keyboardCategoryEnabled() && z2)
                            ? new VibrationAttributes.Builder(TOUCH_VIBRATION_ATTRIBUTES)
                                    .setCategory(1)
                                    .build()
                            : TOUCH_VIBRATION_ATTRIBUTES;
        }
        boolean z3 = false;
        int i2 = z ? 2 : 0;
        switch (i) {
            case 18:
            case 19:
            case 20:
                scrollFeedbackApi = android.view.flags.Flags.scrollFeedbackApi();
                break;
            default:
                scrollFeedbackApi = false;
                break;
        }
        if (scrollFeedbackApi) {
            i2 |= 1;
        }
        if (Flags.keyboardCategoryEnabled()
                && this.mKeyboardVibrationFixedAmplitude
                        >= FullScreenMagnificationGestureHandler.MAX_SCALE
                && z2) {
            if (i == 3) {
                z3 = this.mVibratorInfo.isPrimitiveSupported(1);
            } else if (i == 7) {
                z3 = this.mVibratorInfo.isPrimitiveSupported(7);
            }
        }
        if (z3) {
            i2 |= 16;
        }
        return i2 == 0 ? build : new VibrationAttributes.Builder(build).setFlags(i2).build();
    }
}

package com.android.server.display.config;

import android.R;
import android.content.res.Resources;
import android.util.Spline;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SecHysteresisLevels extends HysteresisLevels {
    public static final float[] DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS = {100.0f};
    public static final float[] DEFAULT_AMBIENT_DARKENING_THRESHOLDS = {200.0f};
    public static final float[] DEFAULT_AMBIENT_THRESHOLD_LEVELS = {
        FullScreenMagnificationGestureHandler.MAX_SCALE
    };
    public final Spline mHysteresisBrightSpline;
    public final Spline mHysteresisDarkSpline;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SecHysteresisLevels(int[] r8, int[] r9, int[] r10, int[] r11) {
        /*
            r7 = this;
            float[] r1 = com.android.server.display.config.SecHysteresisLevels.DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS
            float[] r2 = com.android.server.display.config.SecHysteresisLevels.DEFAULT_AMBIENT_DARKENING_THRESHOLDS
            float[] r4 = com.android.server.display.config.SecHysteresisLevels.DEFAULT_AMBIENT_THRESHOLD_LEVELS
            r5 = 0
            r6 = 0
            r0 = r7
            r3 = r4
            r0.<init>(r1, r2, r3, r4, r5, r6)
            int r0 = r8.length
            int r1 = r9.length
            if (r0 != r1) goto L32
            int r0 = r10.length
            int r1 = r11.length
            if (r0 != r1) goto L32
            float[] r8 = convertArrayFromIntToFloat(r8)
            float[] r9 = convertArrayFromIntToFloat(r9)
            float[] r10 = convertArrayFromIntToFloat(r10)
            float[] r11 = convertArrayFromIntToFloat(r11)
            android.util.Spline r8 = android.util.Spline.createSpline(r8, r9)
            r7.mHysteresisBrightSpline = r8
            android.util.Spline r8 = android.util.Spline.createSpline(r10, r11)
            r7.mHysteresisDarkSpline = r8
            return
        L32:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "Mismatch between hysteresis array lengths."
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.config.SecHysteresisLevels.<init>(int[], int[],"
                    + " int[], int[]):void");
    }

    public static float[] convertArrayFromIntToFloat(int[] iArr) {
        float[] fArr = new float[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            fArr[i] = iArr[i];
        }
        return fArr;
    }

    public static SecHysteresisLevels loadAmbientBrightnessConfig(Resources resources) {
        if (resources != null) {
            return new SecHysteresisLevels(
                    resources.getIntArray(
                            R.array.config_face_acquire_vendor_biometricprompt_ignorelist),
                    resources.getIntArray(R.array.config_face_acquire_vendor_enroll_ignorelist),
                    resources.getIntArray(R.array.config_face_acquire_vendor_keyguard_ignorelist),
                    resources.getIntArray(R.array.config_fillBuiltInDisplayCutoutArray));
        }
        return null;
    }

    @Override // com.android.server.display.config.HysteresisLevels
    public final float getBrighteningThreshold(float f) {
        return Math.round(this.mHysteresisBrightSpline.interpolate(f));
    }

    @Override // com.android.server.display.config.HysteresisLevels
    public final float getDarkeningThreshold(float f) {
        if (this.mHysteresisDarkSpline.interpolate(f)
                >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            return Math.round(r0);
        }
        return -1.0f;
    }

    @Override // com.android.server.display.config.HysteresisLevels
    public final String toString() {
        return "SecHysteresisLevels {\n    mHysteresisBrightSpline="
                + this.mHysteresisBrightSpline
                + ",\n    mHysteresisDarkSpline="
                + this.mHysteresisDarkSpline
                + "\n}";
    }
}

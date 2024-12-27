package com.android.server.display.color;

import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;

import java.util.Arrays;

public final class ReduceBrightColorsTintController extends TintController {
    public int mStrength;
    public final float[] mMatrix = new float[16];
    public final float[] mCoefficients = new float[3];

    @Override // com.android.server.display.color.TintController
    public final int getLevel() {
        return FrameworkStatsLog
                .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE;
    }

    @Override // com.android.server.display.color.TintController
    public final float[] getMatrix() {
        if (this.mIsActivationLocked) {
            Slog.d("ColorDisplayService", "ReduceBrightColorsTintController: activation lock");
            return ColorDisplayService.MATRIX_IDENTITY;
        }
        if (!isActivated()) {
            return ColorDisplayService.MATRIX_IDENTITY;
        }
        float[] fArr = this.mMatrix;
        return Arrays.copyOf(fArr, fArr.length);
    }

    public final void setActivated(Boolean bool) {
        this.mIsActivated = bool;
        Slog.i(
                "ColorDisplayService",
                (bool == null || !bool.booleanValue())
                        ? "Turning off reduce bright colors"
                        : "Turning on reduce bright colors");
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0038, code lost:

       if (r5 < com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MAX_SCALE) goto L10;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setMatrix(int r5) {
        /*
            r4 = this;
            r0 = 0
            if (r5 >= 0) goto L5
            r5 = r0
            goto La
        L5:
            r1 = 100
            if (r5 <= r1) goto La
            r5 = r1
        La:
            java.lang.String r1 = "Setting bright color reduction level: "
            java.lang.String r2 = "ColorDisplayService"
            com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0.m(r5, r1, r2)
            r4.mStrength = r5
            float[] r1 = r4.mMatrix
            android.opengl.Matrix.setIdentityM(r1, r0)
            float r5 = (float) r5
            r2 = 1120403456(0x42c80000, float:100.0)
            float r5 = r5 / r2
            float r2 = r5 * r5
            r3 = 0
            float[] r4 = r4.mCoefficients
            r3 = r4[r3]
            float r2 = r2 * r3
            r3 = 1
            r3 = r4[r3]
            float r5 = r5 * r3
            float r5 = r5 + r2
            r2 = 2
            r4 = r4[r2]
            float r5 = r5 + r4
            r4 = 1065353216(0x3f800000, float:1.0)
            int r2 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r2 <= 0) goto L35
        L33:
            r5 = r4
            goto L3b
        L35:
            r4 = 0
            int r2 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r2 >= 0) goto L3b
            goto L33
        L3b:
            r1[r0] = r5
            r4 = 5
            r1[r4] = r5
            r4 = 10
            r1[r4] = r5
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.color.ReduceBrightColorsTintController.setMatrix(int):void");
    }
}

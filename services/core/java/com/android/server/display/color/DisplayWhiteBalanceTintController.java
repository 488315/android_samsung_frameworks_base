package com.android.server.display.color;

import android.content.Context;
import android.graphics.ColorSpace;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.opengl.Matrix;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.display.feature.DisplayManagerFlags;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DisplayWhiteBalanceTintController extends TintController {
    public int mAppliedCct;
    public CctEvaluator mCctEvaluator;
    public float[] mChromaticAdaptationMatrix;
    int mCurrentColorTemperature;
    public float[] mCurrentColorTemperatureXYZ;
    ColorSpace.Rgb mDisplayColorSpaceRGB;
    public final DisplayManagerFlags mDisplayManagerFlags;
    public final DisplayManagerInternal mDisplayManagerInternal;
    public int mDisplayNominalWhiteCct;
    public Boolean mIsAvailable;
    public int mTargetCct;
    public int mTemperatureDefault;
    int mTemperatureMax;
    int mTemperatureMin;
    public long mTransitionDuration;
    public long mTransitionDurationDecrease;
    public long mTransitionDurationIncrease;
    public final Object mLock = new Object();
    float[] mDisplayNominalWhiteXYZ = new float[3];
    boolean mSetUp = false;
    public final float[] mMatrixDisplayWhiteBalance = new float[16];
    public boolean mIsAllowed = true;

    public DisplayWhiteBalanceTintController(
            DisplayManagerInternal displayManagerInternal,
            DisplayManagerFlags displayManagerFlags) {
        this.mDisplayManagerInternal = displayManagerInternal;
        this.mDisplayManagerFlags = displayManagerFlags;
    }

    public static float[] mul3x3(float[] fArr, float[] fArr2) {
        float f = fArr[0];
        float f2 = fArr2[0];
        float f3 = fArr[3];
        float f4 = fArr2[1];
        float f5 = fArr[6];
        float f6 = fArr2[2];
        float f7 = (f5 * f6) + (f3 * f4) + (f * f2);
        float f8 = fArr[1];
        float f9 = fArr[4];
        float f10 = fArr[7];
        float f11 = (f10 * f6) + (f9 * f4) + (f8 * f2);
        float f12 = fArr[2];
        float f13 = fArr[5];
        float f14 = fArr[8];
        float f15 = (f6 * f14) + (f4 * f13) + (f2 * f12);
        float f16 = fArr2[3];
        float f17 = fArr2[4];
        float f18 = fArr2[5];
        float f19 = (f5 * f18) + (f3 * f17) + (f * f16);
        float f20 = (f10 * f18) + (f9 * f17) + (f8 * f16);
        float f21 = (f18 * f14) + (f17 * f13) + (f16 * f12);
        float f22 = fArr2[6];
        float f23 = fArr2[7];
        float f24 = (f3 * f23) + (f * f22);
        float f25 = fArr2[8];
        return new float[] {
            f7,
            f11,
            f15,
            f19,
            f20,
            f21,
            (f5 * f25) + f24,
            (f10 * f25) + (f9 * f23) + (f8 * f22),
            (f14 * f25) + (f13 * f23) + (f12 * f22)
        };
    }

    public final float[] computeMatrixForCct(int i) {
        float[] fArr;
        if (!this.mSetUp || i == 0) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "Couldn't compute matrix for cct=", "ColorDisplayService");
            return ColorDisplayService.MATRIX_IDENTITY;
        }
        synchronized (this.mLock) {
            try {
                this.mCurrentColorTemperature = i;
                if (i != this.mDisplayNominalWhiteCct || isActivated()) {
                    computeMatrixForCctLocked(i);
                } else {
                    Matrix.setIdentityM(this.mMatrixDisplayWhiteBalance, 0);
                }
                Slog.d(
                        "ColorDisplayService",
                        "computeDisplayWhiteBalanceMatrix: cct ="
                                + i
                                + " matrix ="
                                + TintController.matrixToString(
                                        16, this.mMatrixDisplayWhiteBalance));
                fArr = this.mMatrixDisplayWhiteBalance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return fArr;
    }

    public final void computeMatrixForCctLocked(int i) {
        float[] cctToXyz = ColorSpace.cctToXyz(i);
        this.mCurrentColorTemperatureXYZ = cctToXyz;
        float[] chromaticAdaptation =
                ColorSpace.chromaticAdaptation(
                        ColorSpace.Adaptation.BRADFORD, this.mDisplayNominalWhiteXYZ, cctToXyz);
        this.mChromaticAdaptationMatrix = chromaticAdaptation;
        float[] mul3x3 =
                mul3x3(
                        this.mDisplayColorSpaceRGB.getInverseTransform(),
                        mul3x3(chromaticAdaptation, this.mDisplayColorSpaceRGB.getTransform()));
        float max =
                Math.max(
                        Math.max(
                                mul3x3[0] + mul3x3[3] + mul3x3[6],
                                mul3x3[1] + mul3x3[4] + mul3x3[7]),
                        mul3x3[2] + mul3x3[5] + mul3x3[8]);
        float[] fArr = this.mMatrixDisplayWhiteBalance;
        Matrix.setIdentityM(fArr, 0);
        for (int i2 = 0; i2 < 9; i2++) {
            float f = mul3x3[i2] / max;
            mul3x3[i2] = f;
            if (Float.isNaN(f) || Float.isInfinite(f)) {
                Slog.e("ColorDisplayService", "Invalid DWB color matrix");
                return;
            }
        }
        System.arraycopy(mul3x3, 0, fArr, 0, 3);
        System.arraycopy(mul3x3, 3, fArr, 4, 3);
        System.arraycopy(mul3x3, 6, fArr, 8, 3);
    }

    @Override // com.android.server.display.color.TintController
    public final int getLevel() {
        return 125;
    }

    @Override // com.android.server.display.color.TintController
    public final float[] getMatrix() {
        if (!this.mSetUp || !isActivated()) {
            return ColorDisplayService.MATRIX_IDENTITY;
        }
        computeMatrixForCct(this.mAppliedCct);
        return this.mMatrixDisplayWhiteBalance;
    }

    @Override // com.android.server.display.color.TintController
    public final long getTransitionDurationMilliseconds() {
        return this.mTransitionDuration;
    }

    public final boolean isAvailable(Context context) {
        if (this.mIsAvailable == null) {
            this.mIsAvailable =
                    Boolean.valueOf(ColorDisplayManager.isDisplayWhiteBalanceAvailable(context));
        }
        return this.mIsAvailable.booleanValue();
    }
}

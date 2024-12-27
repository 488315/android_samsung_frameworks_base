package com.android.server.accessibility;

import android.util.Slog;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CVDCalculator {
    public double CVDSeverity;
    public int CVDType;
    public double[] SpotsU;
    public double[] SpotsV;
    public double c_index;
    public ColorTransferTable mColorTransferTable;
    public int[] mInputNums;
    public double majorAngle;
    public double majorRadius;
    public double minorRadius;
    public double[] u;
    public double[] v;

    public final int[] getRGBCMY(int i, int i2, double d, double d2) {
        int[] iArr = new int[12];
        StringBuilder m =
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i, i2, "getRGBCMY ", ", CVDType : ", ", Severity : ");
        m.append(d);
        m.append(", user : ");
        m.append(d2);
        Slog.d("CVDCalculator", m.toString());
        if (i == 0) {
            if (i2 == 0 || i2 == 1 || i2 == 2) {
                this.mColorTransferTable.getClass();
                int colorTransferValue_DMC =
                        ColorTransferTable.getColorTransferValue_DMC(1, 1, i2, d, d2);
                this.mColorTransferTable.getClass();
                iArr[0] =
                        (colorTransferValue_DMC * 256)
                                + ColorTransferTable.getColorTransferValue_DMC(4, 1, i2, d, d2);
                this.mColorTransferTable.getClass();
                int colorTransferValue_DMC2 =
                        ColorTransferTable.getColorTransferValue_DMC(1, 3, i2, d, d2);
                this.mColorTransferTable.getClass();
                iArr[1] =
                        (colorTransferValue_DMC2 * 256)
                                + ColorTransferTable.getColorTransferValue_DMC(4, 3, i2, d, d2);
                this.mColorTransferTable.getClass();
                int colorTransferValue_DMC3 =
                        ColorTransferTable.getColorTransferValue_DMC(1, 5, i2, d, d2);
                this.mColorTransferTable.getClass();
                iArr[2] =
                        (colorTransferValue_DMC3 * 256)
                                + ColorTransferTable.getColorTransferValue_DMC(4, 5, i2, d, d2);
                this.mColorTransferTable.getClass();
                int colorTransferValue_DMC4 =
                        ColorTransferTable.getColorTransferValue_DMC(3, 1, i2, d, d2);
                this.mColorTransferTable.getClass();
                iArr[3] =
                        (colorTransferValue_DMC4 * 256)
                                + ColorTransferTable.getColorTransferValue_DMC(6, 1, i2, d, d2);
                this.mColorTransferTable.getClass();
                int colorTransferValue_DMC5 =
                        ColorTransferTable.getColorTransferValue_DMC(3, 3, i2, d, d2);
                this.mColorTransferTable.getClass();
                iArr[4] =
                        (colorTransferValue_DMC5 * 256)
                                + ColorTransferTable.getColorTransferValue_DMC(6, 3, i2, d, d2);
                this.mColorTransferTable.getClass();
                int colorTransferValue_DMC6 =
                        ColorTransferTable.getColorTransferValue_DMC(3, 5, i2, d, d2);
                this.mColorTransferTable.getClass();
                iArr[5] =
                        (colorTransferValue_DMC6 * 256)
                                + ColorTransferTable.getColorTransferValue_DMC(6, 5, i2, d, d2);
                this.mColorTransferTable.getClass();
                int colorTransferValue_DMC7 =
                        ColorTransferTable.getColorTransferValue_DMC(5, 1, i2, d, d2);
                this.mColorTransferTable.getClass();
                iArr[6] =
                        (colorTransferValue_DMC7 * 256)
                                + ColorTransferTable.getColorTransferValue_DMC(2, 1, i2, d, d2);
                this.mColorTransferTable.getClass();
                int colorTransferValue_DMC8 =
                        ColorTransferTable.getColorTransferValue_DMC(5, 3, i2, d, d2);
                this.mColorTransferTable.getClass();
                iArr[7] =
                        (colorTransferValue_DMC8 * 256)
                                + ColorTransferTable.getColorTransferValue_DMC(2, 3, i2, d, d2);
                this.mColorTransferTable.getClass();
                int colorTransferValue_DMC9 =
                        ColorTransferTable.getColorTransferValue_DMC(5, 5, i2, d, d2);
                this.mColorTransferTable.getClass();
                iArr[8] =
                        (colorTransferValue_DMC9 * 256)
                                + ColorTransferTable.getColorTransferValue_DMC(2, 5, i2, d, d2);
                iArr[9] = 255;
                iArr[10] = 255;
                iArr[11] = 255;
            } else {
                iArr[0] = 65280;
                iArr[1] = 255;
                iArr[2] = 255;
                iArr[3] = 255;
                iArr[4] = 65280;
                iArr[5] = 255;
                iArr[6] = 255;
                iArr[7] = 255;
                iArr[8] = 65280;
                iArr[9] = 255;
                iArr[10] = 255;
                iArr[11] = 255;
            }
        } else if (i != 1) {
            iArr[0] = 65280;
            iArr[1] = 255;
            iArr[2] = 255;
            iArr[3] = 255;
            iArr[4] = 65280;
            iArr[5] = 255;
            iArr[6] = 255;
            iArr[7] = 255;
            iArr[8] = 65280;
            iArr[9] = 255;
            iArr[10] = 255;
            iArr[11] = 255;
        } else if (i2 == 0 || i2 == 1 || i2 == 2) {
            this.mColorTransferTable.getClass();
            int colorTransferValue_Hybrid =
                    ColorTransferTable.getColorTransferValue_Hybrid(1, 1, i2, d, d2);
            this.mColorTransferTable.getClass();
            iArr[0] =
                    (colorTransferValue_Hybrid * 256)
                            + ColorTransferTable.getColorTransferValue_Hybrid(4, 1, i2, d, d2);
            this.mColorTransferTable.getClass();
            int colorTransferValue_Hybrid2 =
                    ColorTransferTable.getColorTransferValue_Hybrid(1, 3, i2, d, d2);
            this.mColorTransferTable.getClass();
            iArr[1] =
                    (colorTransferValue_Hybrid2 * 256)
                            + ColorTransferTable.getColorTransferValue_Hybrid(4, 3, i2, d, d2);
            this.mColorTransferTable.getClass();
            int colorTransferValue_Hybrid3 =
                    ColorTransferTable.getColorTransferValue_Hybrid(1, 5, i2, d, d2);
            this.mColorTransferTable.getClass();
            iArr[2] =
                    (colorTransferValue_Hybrid3 * 256)
                            + ColorTransferTable.getColorTransferValue_Hybrid(4, 5, i2, d, d2);
            this.mColorTransferTable.getClass();
            int colorTransferValue_Hybrid4 =
                    ColorTransferTable.getColorTransferValue_Hybrid(3, 1, i2, d, d2);
            this.mColorTransferTable.getClass();
            iArr[3] =
                    (colorTransferValue_Hybrid4 * 256)
                            + ColorTransferTable.getColorTransferValue_Hybrid(6, 1, i2, d, d2);
            this.mColorTransferTable.getClass();
            int colorTransferValue_Hybrid5 =
                    ColorTransferTable.getColorTransferValue_Hybrid(3, 3, i2, d, d2);
            this.mColorTransferTable.getClass();
            iArr[4] =
                    (colorTransferValue_Hybrid5 * 256)
                            + ColorTransferTable.getColorTransferValue_Hybrid(6, 3, i2, d, d2);
            this.mColorTransferTable.getClass();
            int colorTransferValue_Hybrid6 =
                    ColorTransferTable.getColorTransferValue_Hybrid(3, 5, i2, d, d2);
            this.mColorTransferTable.getClass();
            iArr[5] =
                    (colorTransferValue_Hybrid6 * 256)
                            + ColorTransferTable.getColorTransferValue_Hybrid(6, 5, i2, d, d2);
            this.mColorTransferTable.getClass();
            int colorTransferValue_Hybrid7 =
                    ColorTransferTable.getColorTransferValue_Hybrid(5, 1, i2, d, d2);
            this.mColorTransferTable.getClass();
            iArr[6] =
                    (colorTransferValue_Hybrid7 * 256)
                            + ColorTransferTable.getColorTransferValue_Hybrid(2, 1, i2, d, d2);
            this.mColorTransferTable.getClass();
            int colorTransferValue_Hybrid8 =
                    ColorTransferTable.getColorTransferValue_Hybrid(5, 3, i2, d, d2);
            this.mColorTransferTable.getClass();
            iArr[7] =
                    (colorTransferValue_Hybrid8 * 256)
                            + ColorTransferTable.getColorTransferValue_Hybrid(2, 3, i2, d, d2);
            this.mColorTransferTable.getClass();
            int colorTransferValue_Hybrid9 =
                    ColorTransferTable.getColorTransferValue_Hybrid(5, 5, i2, d, d2);
            this.mColorTransferTable.getClass();
            iArr[8] =
                    (colorTransferValue_Hybrid9 * 256)
                            + ColorTransferTable.getColorTransferValue_Hybrid(2, 5, i2, d, d2);
            if (((int) Math.round(10.0d * d)) < 6) {
                iArr[9] = 204;
                iArr[10] = 204;
                iArr[11] = 204;
            } else {
                iArr[9] = 255;
                iArr[10] = 255;
                iArr[11] = 255;
            }
        } else {
            iArr[0] = 65280;
            iArr[1] = 255;
            iArr[2] = 255;
            iArr[3] = 255;
            iArr[4] = 65280;
            iArr[5] = 255;
            iArr[6] = 255;
            iArr[7] = 255;
            iArr[8] = 65280;
            iArr[9] = 255;
            iArr[10] = 255;
            iArr[11] = 255;
        }
        return iArr;
    }
}

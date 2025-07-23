package android.hardware;

import android.text.format.Time;
import java.util.Calendar;
import java.util.TimeZone;

/* loaded from: classes2.dex */
public class GeomagneticField {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final float EARTH_REFERENCE_RADIUS_KM = 6371.2f;
    private static final float EARTH_SEMI_MAJOR_AXIS_KM = 6378.137f;
    private static final float EARTH_SEMI_MINOR_AXIS_KM = 6356.7524f;
    private static final float[][] G_COEFF;
    private static final float[][] SCHMIDT_QUASI_NORM_FACTORS;
    private float mGcLatitudeRad;
    private float mGcLongitudeRad;
    private float mGcRadiusKm;
    private float mX;
    private float mY;
    private float mZ;
    private static final float[][] H_COEFF = {new float[]{0.0f}, new float[]{0.0f, 4652.9f}, new float[]{0.0f, -2991.6f, -734.8f}, new float[]{0.0f, -82.2f, 241.8f, -542.9f}, new float[]{0.0f, 282.0f, -158.4f, 199.8f, -350.1f}, new float[]{0.0f, 47.7f, 208.4f, -121.3f, 32.2f, 99.1f}, new float[]{0.0f, -19.1f, 25.0f, 52.7f, -64.4f, 9.0f, 68.1f}, new float[]{0.0f, -51.4f, -16.8f, 2.3f, 23.5f, -2.2f, -27.2f, -1.9f}, new float[]{0.0f, 8.4f, -15.3f, 12.8f, -11.8f, 14.9f, 3.6f, -6.9f, 2.8f}, new float[]{0.0f, -23.3f, 11.1f, 9.8f, -5.1f, -6.2f, 7.8f, 0.4f, -1.5f, 9.7f}, new float[]{0.0f, 3.4f, -0.2f, 3.5f, 4.8f, -8.6f, -0.1f, -4.2f, -3.4f, -0.1f, -8.8f}, new float[]{0.0f, 0.0f, 2.6f, -0.5f, -0.4f, 0.6f, -0.2f, -1.7f, -1.6f, -3.0f, -2.0f, -2.6f}, new float[]{0.0f, -1.2f, 0.5f, 1.3f, -1.8f, 0.1f, 0.7f, -0.1f, 0.6f, 0.2f, -0.9f, 0.0f, 0.5f}};
    private static final float[][] DELTA_G = {new float[]{0.0f}, new float[]{6.7f, 7.7f}, new float[]{-11.5f, -7.1f, -2.2f}, new float[]{2.8f, -6.2f, 3.4f, -12.2f}, new float[]{-1.1f, -1.6f, -6.0f, 5.4f, -5.5f}, new float[]{-0.3f, 0.6f, -0.7f, 0.1f, 1.2f, 1.0f}, new float[]{-0.6f, -0.4f, 0.5f, 1.4f, -1.4f, 0.0f, 0.8f}, new float[]{-0.1f, -0.3f, -0.1f, 0.7f, 0.2f, -0.5f, -0.8f, 1.0f}, new float[]{-0.1f, 0.1f, -0.1f, 0.5f, -0.1f, 0.4f, 0.5f, 0.0f, 0.4f}, new float[]{-0.1f, -0.2f, 0.0f, 0.4f, -0.3f, 0.0f, 0.3f, 0.0f, 0.0f, -0.4f}, new float[]{0.0f, 0.0f, 0.0f, 0.2f, -0.1f, -0.2f, 0.0f, -0.1f, -0.2f, -0.1f, 0.0f}, new float[]{0.0f, -0.1f, 0.0f, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, -0.1f, -0.1f, -0.1f, -0.1f}, new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.1f}};
    private static final float[][] DELTA_H = {new float[]{0.0f}, new float[]{0.0f, -25.1f}, new float[]{0.0f, -30.2f, -23.9f}, new float[]{0.0f, 5.7f, -1.0f, 1.1f}, new float[]{0.0f, 0.2f, 6.9f, 3.7f, -5.6f}, new float[]{0.0f, 0.1f, 2.5f, -0.9f, 3.0f, 0.5f}, new float[]{0.0f, 0.1f, -1.8f, -1.4f, 0.9f, 0.1f, 1.0f}, new float[]{0.0f, 0.5f, 0.6f, -0.7f, -0.2f, -1.2f, 0.2f, 0.3f}, new float[]{0.0f, -0.3f, 0.7f, -0.2f, 0.5f, -0.3f, -0.5f, 0.4f, 0.1f}, new float[]{0.0f, -0.3f, 0.2f, -0.4f, 0.4f, 0.1f, 0.0f, -0.2f, 0.5f, 0.2f}, new float[]{0.0f, 0.0f, 0.1f, -0.3f, 0.1f, -0.2f, 0.1f, 0.0f, -0.1f, 0.2f, 0.0f}, new float[]{0.0f, 0.0f, 0.1f, 0.0f, 0.2f, 0.0f, 0.0f, 0.1f, 0.0f, -0.1f, 0.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, -0.1f, 0.1f, 0.0f, 0.0f, 0.0f, 0.1f, 0.0f, 0.0f, 0.0f, -0.1f}};
    private static final long BASE_TIME = new Calendar.Builder().setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC)).setDate(2020, 0, 1).build().getTimeInMillis();

    static {
        float[][] fArr = {new float[]{0.0f}, new float[]{-29404.5f, -1450.7f}, new float[]{-2500.0f, 2982.0f, 1676.8f}, new float[]{1363.9f, -2381.0f, 1236.2f, 525.7f}, new float[]{903.1f, 809.4f, 86.2f, -309.4f, 47.9f}, new float[]{-234.4f, 363.1f, 187.8f, -140.7f, -151.2f, 13.7f}, new float[]{65.9f, 65.6f, 73.0f, -121.5f, -36.2f, 13.5f, -64.7f}, new float[]{80.6f, -76.8f, -8.3f, 56.5f, 15.8f, 6.4f, -7.2f, 9.8f}, new float[]{23.6f, 9.8f, -17.5f, -0.4f, -21.1f, 15.3f, 13.7f, -16.5f, -0.3f}, new float[]{5.0f, 8.2f, 2.9f, -1.4f, -1.1f, -13.3f, 1.1f, 8.9f, -9.3f, -11.9f}, new float[]{-1.9f, -6.2f, -0.1f, 1.7f, -0.9f, 0.6f, -0.9f, 1.9f, 1.4f, -2.4f, -3.9f}, new float[]{3.0f, -1.4f, -2.5f, 2.4f, -0.9f, 0.3f, -0.7f, -0.1f, 1.4f, -0.6f, 0.2f, 3.1f}, new float[]{-2.0f, -0.1f, 0.5f, 1.3f, -1.2f, 0.7f, 0.3f, 0.5f, -0.2f, -0.5f, 0.1f, -1.1f, -0.3f}};
        G_COEFF = fArr;
        SCHMIDT_QUASI_NORM_FACTORS = computeSchmidtQuasiNormFactors(fArr.length);
    }

    public GeomagneticField(float f, float f2, float f3, long j) {
        int length = G_COEFF.length;
        float min = Math.min(89.99999f, Math.max(-89.99999f, f));
        computeGeocentricCoordinates(min, f2, f3);
        LegendreTable legendreTable = new LegendreTable(length - 1, (float) (1.5707963267948966d - this.mGcLatitudeRad));
        int i = length + 2;
        float[] fArr = new float[i];
        int i2 = 0;
        fArr[0] = 1.0f;
        int i3 = 1;
        fArr[1] = EARTH_REFERENCE_RADIUS_KM / this.mGcRadiusKm;
        for (int i4 = 2; i4 < i; i4++) {
            fArr[i4] = fArr[i4 - 1] * fArr[1];
        }
        float[] fArr2 = new float[length];
        float[] fArr3 = new float[length];
        float f4 = 0.0f;
        fArr2[0] = 0.0f;
        fArr3[0] = 1.0f;
        fArr2[1] = (float) Math.sin(this.mGcLongitudeRad);
        fArr3[1] = (float) Math.cos(this.mGcLongitudeRad);
        for (int i5 = 2; i5 < length; i5++) {
            int i6 = i5 >> 1;
            int i7 = i5 - i6;
            fArr2[i5] = (fArr2[i7] * fArr3[i6]) + (fArr3[i7] * fArr2[i6]);
            fArr3[i5] = (fArr3[i7] * fArr3[i6]) - (fArr2[i7] * fArr2[i6]);
        }
        float cos = 1.0f / ((float) Math.cos(this.mGcLatitudeRad));
        float f5 = (j - BASE_TIME) / 3.1536E10f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        while (i3 < length) {
            int i8 = i2;
            while (i8 <= i3) {
                float f8 = G_COEFF[i3][i8] + (DELTA_G[i3][i8] * f5);
                float f9 = H_COEFF[i3][i8] + (DELTA_H[i3][i8] * f5);
                int i9 = i3 + 2;
                float f10 = fArr[i9] * ((fArr3[i8] * f8) + (fArr2[i8] * f9)) * legendreTable.mPDeriv[i3][i8];
                float[][] fArr4 = SCHMIDT_QUASI_NORM_FACTORS;
                f4 += f10 * fArr4[i3][i8];
                f7 += fArr[i9] * i8 * ((fArr2[i8] * f8) - (fArr3[i8] * f9)) * legendreTable.mP[i3][i8] * fArr4[i3][i8] * cos;
                f6 -= ((((i3 + 1) * fArr[i9]) * ((f8 * fArr3[i8]) + (f9 * fArr2[i8]))) * legendreTable.mP[i3][i8]) * fArr4[i3][i8];
                i8++;
                length = length;
            }
            i3++;
            i2 = 0;
        }
        double radians = Math.toRadians(min) - this.mGcLatitudeRad;
        double d = f6;
        this.mX = (float) ((f4 * Math.cos(radians)) + (Math.sin(radians) * d));
        this.mY = f7;
        this.mZ = (float) (((-f4) * Math.sin(radians)) + (d * Math.cos(radians)));
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public float getZ() {
        return this.mZ;
    }

    public float getDeclination() {
        return (float) Math.toDegrees(Math.atan2(this.mY, this.mX));
    }

    public float getInclination() {
        return (float) Math.toDegrees(Math.atan2(this.mZ, getHorizontalStrength()));
    }

    public float getHorizontalStrength() {
        return (float) Math.hypot(this.mX, this.mY);
    }

    public float getFieldStrength() {
        float f = this.mX;
        float f2 = this.mY;
        float f3 = this.mZ;
        return (float) Math.sqrt((f * f) + (f2 * f2) + (f3 * f3));
    }

    private void computeGeocentricCoordinates(float f, float f2, float f3) {
        double radians = Math.toRadians(f);
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);
        float sqrt = ((float) Math.sqrt((cos * 4.0680636E7f * cos) + (sin * 4.04083E7f * sin))) * (f3 / 1000.0f);
        this.mGcLatitudeRad = (float) Math.atan(((sin / cos) * (4.04083E7f + sqrt)) / (sqrt + 4.0680636E7f));
        this.mGcLongitudeRad = (float) Math.toRadians(f2);
        this.mGcRadiusKm = (float) Math.sqrt((r12 * r12) + (r12 * 2.0f * ((float) Math.sqrt(r5))) + ((((1.6549141E15f * cos) * cos) + ((1.6328307E15f * sin) * sin)) / r3));
    }

    private static class LegendreTable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public final float[][] mP;
        public final float[][] mPDeriv;

        public LegendreTable(int i, float f) {
            int i2;
            double d = f;
            float cos = (float) Math.cos(d);
            float sin = (float) Math.sin(d);
            int i3 = i + 1;
            float[][] fArr = new float[i3][];
            this.mP = fArr;
            float[][] fArr2 = new float[i3][];
            this.mPDeriv = fArr2;
            fArr[0] = new float[]{1.0f};
            fArr2[0] = new float[]{0.0f};
            int i4 = 1;
            while (i4 <= i) {
                int i5 = i4 + 1;
                this.mP[i4] = new float[i5];
                this.mPDeriv[i4] = new float[i5];
                for (int i6 = 0; i6 <= i4; i6++) {
                    if (i4 == i6) {
                        float[][] fArr3 = this.mP;
                        float[] fArr4 = fArr3[i4];
                        int i7 = i4 - 1;
                        float[] fArr5 = fArr3[i7];
                        int i8 = i6 - 1;
                        fArr4[i6] = fArr5[i8] * sin;
                        float[][] fArr6 = this.mPDeriv;
                        fArr6[i4][i6] = (fArr5[i8] * cos) + (fArr6[i7][i8] * sin);
                    } else if (i4 == 1 || i6 == i4 - 1) {
                        float[][] fArr7 = this.mP;
                        float[] fArr8 = fArr7[i4];
                        int i9 = i4 - 1;
                        float[] fArr9 = fArr7[i9];
                        fArr8[i6] = fArr9[i6] * cos;
                        float[][] fArr10 = this.mPDeriv;
                        fArr10[i4][i6] = ((-sin) * fArr9[i6]) + (fArr10[i9][i6] * cos);
                    } else {
                        int i10 = i4 * 2;
                        float f2 = ((i2 * i2) - (i6 * i6)) / ((i10 - 1) * (i10 - 3));
                        float[][] fArr11 = this.mP;
                        float[] fArr12 = fArr11[i4];
                        float[] fArr13 = fArr11[i2];
                        int i11 = i4 - 2;
                        fArr12[i6] = (fArr13[i6] * cos) - (fArr11[i11][i6] * f2);
                        float[][] fArr14 = this.mPDeriv;
                        fArr14[i4][i6] = (((-sin) * fArr13[i6]) + (fArr14[i2][i6] * cos)) - (f2 * fArr14[i11][i6]);
                    }
                }
                i4 = i5;
            }
        }
    }

    private static float[][] computeSchmidtQuasiNormFactors(int i) {
        float[][] fArr = new float[i + 1][];
        fArr[0] = new float[]{1.0f};
        int i2 = 1;
        while (i2 <= i) {
            int i3 = i2 + 1;
            float[] fArr2 = new float[i3];
            fArr[i2] = fArr2;
            fArr2[0] = (fArr[i2 - 1][0] * ((i2 * 2) - 1)) / i2;
            int i4 = 1;
            while (i4 <= i2) {
                float[] fArr3 = fArr[i2];
                fArr3[i4] = fArr3[i4 - 1] * ((float) Math.sqrt((((i2 - i4) + 1) * (i4 == 1 ? 2 : 1)) / (i2 + i4)));
                i4++;
            }
            i2 = i3;
        }
        return fArr;
    }
}

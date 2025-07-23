package com.android.internal.graphics.cam;

import android.hardware.scontext.SContextConstants;
import com.android.internal.graphics.ColorUtils;

/* loaded from: classes5.dex */
public class Cam {
    private static final float CHROMA_SEARCH_ENDPOINT = 0.4f;
    private static final float DE_MAX = 1.0f;
    private static final float DL_MAX = 0.2f;
    private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01f;
    private final float mAstar;
    private final float mBstar;
    private final float mChroma;
    private final float mHue;
    private final float mJ;
    private final float mJstar;
    private final float mM;
    private final float mQ;
    private final float mS;

    public float getHue() {
        return this.mHue;
    }

    public float getChroma() {
        return this.mChroma;
    }

    public float getJ() {
        return this.mJ;
    }

    public float getQ() {
        return this.mQ;
    }

    public float getM() {
        return this.mM;
    }

    public float getS() {
        return this.mS;
    }

    public float getJstar() {
        return this.mJstar;
    }

    public float getAstar() {
        return this.mAstar;
    }

    public float getBstar() {
        return this.mBstar;
    }

    Cam(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.mHue = f;
        this.mChroma = f2;
        this.mJ = f3;
        this.mQ = f4;
        this.mM = f5;
        this.mS = f6;
        this.mJstar = f7;
        this.mAstar = f8;
        this.mBstar = f9;
    }

    public static int getInt(float f, float f2, float f3) {
        return getInt(f, f2, f3, Frame.DEFAULT);
    }

    public static Cam fromInt(int i) {
        return fromIntInFrame(i, Frame.DEFAULT);
    }

    public static Cam fromIntInFrame(int i, Frame frame) {
        float[] xyzFromInt = CamUtils.xyzFromInt(i);
        float[][] fArr = CamUtils.XYZ_TO_CAM16RGB;
        float f = xyzFromInt[0];
        float[] fArr2 = fArr[0];
        float f2 = fArr2[0] * f;
        float f3 = xyzFromInt[1];
        float f4 = f2 + (fArr2[1] * f3);
        float f5 = xyzFromInt[2];
        float f6 = f4 + (fArr2[2] * f5);
        float[] fArr3 = fArr[1];
        float f7 = (fArr3[0] * f) + (fArr3[1] * f3) + (fArr3[2] * f5);
        float[] fArr4 = fArr[2];
        float f8 = (f * fArr4[0]) + (f3 * fArr4[1]) + (f5 * fArr4[2]);
        float f9 = frame.getRgbD()[0] * f6;
        float f10 = frame.getRgbD()[1] * f7;
        float f11 = frame.getRgbD()[2] * f8;
        float pow = (float) Math.pow((frame.getFl() * Math.abs(f9)) / 100.0d, 0.42d);
        float pow2 = (float) Math.pow((frame.getFl() * Math.abs(f10)) / 100.0d, 0.42d);
        float pow3 = (float) Math.pow((frame.getFl() * Math.abs(f11)) / 100.0d, 0.42d);
        float signum = ((Math.signum(f9) * 400.0f) * pow) / (pow + 27.13f);
        float signum2 = ((Math.signum(f10) * 400.0f) * pow2) / (pow2 + 27.13f);
        float signum3 = ((Math.signum(f11) * 400.0f) * pow3) / (pow3 + 27.13f);
        double d = signum3;
        float f12 = ((float) (((signum * 11.0d) + (signum2 * (-12.0d))) + d)) / 11.0f;
        float f13 = ((float) ((signum + signum2) - (d * 2.0d))) / 9.0f;
        float f14 = signum2 * 20.0f;
        float f15 = (((signum * 20.0f) + f14) + (21.0f * signum3)) / 20.0f;
        float f16 = (((signum * 40.0f) + f14) + signum3) / 20.0f;
        float atan2 = (((float) Math.atan2(f13, f12)) * 180.0f) / 3.1415927f;
        if (atan2 < 0.0f) {
            atan2 += 360.0f;
        } else if (atan2 >= 360.0f) {
            atan2 -= 360.0f;
        }
        float f17 = atan2;
        float f18 = (3.1415927f * f17) / 180.0f;
        float pow4 = ((float) Math.pow((f16 * frame.getNbb()) / frame.getAw(), frame.getC() * frame.getZ())) * 100.0f;
        float c = (4.0f / frame.getC()) * ((float) Math.sqrt(pow4 / 100.0f)) * (frame.getAw() + 4.0f) * frame.getFlRoot();
        float pow5 = ((float) Math.pow((((((((float) (Math.cos((((((double) f17) < 20.14d ? 360.0f + f17 : f17) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f) * 3846.1538f) * frame.getNc()) * frame.getNcb()) * ((float) Math.sqrt((f12 * f12) + (f13 * f13)))) / (f15 + 0.305f), 0.9d)) * ((float) Math.pow(1.64d - Math.pow(0.29d, frame.getN()), 0.73d)) * ((float) Math.sqrt(pow4 / 100.0d));
        float flRoot = pow5 * frame.getFlRoot();
        float sqrt = ((float) Math.sqrt((r1 * frame.getC()) / (frame.getAw() + 4.0f))) * 50.0f;
        float f19 = (1.7f * pow4) / ((0.007f * pow4) + 1.0f);
        float log = ((float) Math.log((0.0228f * flRoot) + 1.0f)) * 43.85965f;
        double d2 = f18;
        return new Cam(f17, pow5, pow4, c, flRoot, sqrt, f19, log * ((float) Math.cos(d2)), log * ((float) Math.sin(d2)));
    }

    private static Cam fromJch(float f, float f2, float f3) {
        return fromJchInFrame(f, f2, f3, Frame.DEFAULT);
    }

    private static Cam fromJchInFrame(float f, float f2, float f3, Frame frame) {
        float c = (4.0f / frame.getC()) * ((float) Math.sqrt(f / 100.0d)) * (frame.getAw() + 4.0f) * frame.getFlRoot();
        float flRoot = frame.getFlRoot() * f2;
        float sqrt = ((float) Math.sqrt(((f2 / ((float) Math.sqrt(r4))) * frame.getC()) / (frame.getAw() + 4.0f))) * 50.0f;
        float f4 = (1.7f * f) / ((0.007f * f) + 1.0f);
        float log = ((float) Math.log((flRoot * 0.0228d) + 1.0d)) * 43.85965f;
        double d = (3.1415927f * f3) / 180.0f;
        return new Cam(f3, f2, f, c, flRoot, sqrt, f4, ((float) Math.cos(d)) * log, log * ((float) Math.sin(d)));
    }

    public float distance(Cam cam) {
        float jstar = getJstar() - cam.getJstar();
        float astar = getAstar() - cam.getAstar();
        float bstar = getBstar() - cam.getBstar();
        return (float) (Math.pow(Math.sqrt((jstar * jstar) + (astar * astar) + (bstar * bstar)), 0.63d) * 1.41d);
    }

    public int viewedInSrgb() {
        return viewed(Frame.DEFAULT);
    }

    public int viewed(Frame frame) {
        float pow = (float) Math.pow(((((double) getChroma()) == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || ((double) getJ()) == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) ? 0.0f : getChroma() / ((float) Math.sqrt(getJ() / 100.0d))) / Math.pow(1.64d - Math.pow(0.29d, frame.getN()), 0.73d), 1.1111111111111112d);
        double hue = (getHue() * 3.1415927f) / 180.0f;
        float cos = ((float) (Math.cos(2.0d + hue) + 3.8d)) * 0.25f;
        float aw = frame.getAw() * ((float) Math.pow(getJ() / 100.0d, (1.0d / frame.getC()) / frame.getZ()));
        float nc = cos * 3846.1538f * frame.getNc() * frame.getNcb();
        float nbb = aw / frame.getNbb();
        float sin = (float) Math.sin(hue);
        float cos2 = (float) Math.cos(hue);
        float f = (((0.305f + nbb) * 23.0f) * pow) / (((nc * 23.0f) + ((11.0f * pow) * cos2)) + ((pow * 108.0f) * sin));
        float f2 = cos2 * f;
        float f3 = f * sin;
        float f4 = nbb * 460.0f;
        float f5 = (((451.0f * f2) + f4) + (288.0f * f3)) / 1403.0f;
        float f6 = ((f4 - (891.0f * f2)) - (261.0f * f3)) / 1403.0f;
        float signum = Math.signum(f5) * (100.0f / frame.getFl()) * ((float) Math.pow((float) Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, (Math.abs(f5) * 27.13d) / (400.0d - Math.abs(f5))), 2.380952380952381d));
        float signum2 = Math.signum(f6) * (100.0f / frame.getFl()) * ((float) Math.pow((float) Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, (Math.abs(f6) * 27.13d) / (400.0d - Math.abs(f6))), 2.380952380952381d));
        float signum3 = Math.signum(((f4 - (f2 * 220.0f)) - (f3 * 6300.0f)) / 1403.0f) * (100.0f / frame.getFl()) * ((float) Math.pow((float) Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, (Math.abs(r8) * 27.13d) / (400.0d - Math.abs(r8))), 2.380952380952381d));
        float f7 = signum / frame.getRgbD()[0];
        float f8 = signum2 / frame.getRgbD()[1];
        float f9 = signum3 / frame.getRgbD()[2];
        float[][] fArr = CamUtils.CAM16RGB_TO_XYZ;
        float[] fArr2 = fArr[0];
        float f10 = (fArr2[0] * f7) + (fArr2[1] * f8) + (fArr2[2] * f9);
        float[] fArr3 = fArr[1];
        float f11 = (fArr3[0] * f7) + (fArr3[1] * f8) + (fArr3[2] * f9);
        float[] fArr4 = fArr[2];
        return ColorUtils.XYZToColor(f10, f11, (f7 * fArr4[0]) + (f8 * fArr4[1]) + (f9 * fArr4[2]));
    }

    public static int getInt(float f, float f2, float f3, Frame frame) {
        if (frame == Frame.DEFAULT) {
            return HctSolver.solveToInt(f, f2, f3);
        }
        if (f2 < 1.0d || Math.round(f3) <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || Math.round(f3) >= 100.0d) {
            return CamUtils.intFromLstar(f3);
        }
        float min = f < 0.0f ? 0.0f : Math.min(360.0f, f);
        Cam cam = null;
        boolean z = true;
        float f4 = 0.0f;
        float f5 = f2;
        while (Math.abs(f4 - f2) >= 0.4f) {
            Cam findCamByJ = findCamByJ(min, f5, f3);
            if (!z) {
                if (findCamByJ == null) {
                    f2 = f5;
                } else {
                    f4 = f5;
                    cam = findCamByJ;
                }
                f5 = ((f2 - f4) / 2.0f) + f4;
            } else {
                if (findCamByJ != null) {
                    return findCamByJ.viewed(frame);
                }
                f5 = ((f2 - f4) / 2.0f) + f4;
                z = false;
            }
        }
        if (cam == null) {
            return CamUtils.intFromLstar(f3);
        }
        return cam.viewed(frame);
    }

    private static Cam findCamByJ(float f, float f2, float f3) {
        float f4 = 100.0f;
        float f5 = 1000.0f;
        float f6 = 0.0f;
        Cam cam = null;
        float f7 = 1000.0f;
        while (Math.abs(f6 - f4) > 0.01f) {
            float f8 = ((f4 - f6) / 2.0f) + f6;
            int viewedInSrgb = fromJch(f8, f2, f).viewedInSrgb();
            float lstarFromInt = CamUtils.lstarFromInt(viewedInSrgb);
            float abs = Math.abs(f3 - lstarFromInt);
            if (abs < 0.2f) {
                Cam fromInt = fromInt(viewedInSrgb);
                float distance = fromInt.distance(fromJch(fromInt.getJ(), fromInt.getChroma(), f));
                if (distance <= 1.0f) {
                    cam = fromInt;
                    f5 = abs;
                    f7 = distance;
                }
            }
            if (f5 == 0.0f && f7 == 0.0f) {
                return cam;
            }
            if (lstarFromInt < f3) {
                f6 = f8;
            } else {
                f4 = f8;
            }
        }
        return cam;
    }
}

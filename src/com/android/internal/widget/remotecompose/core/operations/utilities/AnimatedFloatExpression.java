package com.android.internal.widget.remotecompose.core.operations.utilities;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.audio.common.AudioChannelLayout;
import android.media.quality.ParameterCapability;
import android.provider.Telephony;
import android.telecom.Logging.Session;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.widget.remotecompose.core.operations.utilities.easing.MonotonicSpline;
import java.io.PrintStream;
import java.util.Random;

/* loaded from: classes6.dex */
public class AnimatedFloatExpression {
    private static final float FP_TO_DEG = 0.017453292f;
    private static final float FP_TO_RAD = 57.29578f;
    public static final int LAST_OP = 3211314;
    static final int[] NO_OF_OPS;
    public static final int OFFSET = 3211264;
    private static final int OP_SMOOTH_STEP = 3211314;
    private static Random sRandom;
    CollectionsAccess mCollectionsAccess;
    static IntMap<String> sNames = new IntMap<>();
    private static final int OP_ADD = 3211265;
    public static final float ADD = asNan(OP_ADD);
    private static final int OP_SUB = 3211266;
    public static final float SUB = asNan(OP_SUB);
    private static final int OP_MUL = 3211267;
    public static final float MUL = asNan(OP_MUL);
    private static final int OP_DIV = 3211268;
    public static final float DIV = asNan(OP_DIV);
    private static final int OP_MOD = 3211269;
    public static final float MOD = asNan(OP_MOD);
    private static final int OP_MIN = 3211270;
    public static final float MIN = asNan(OP_MIN);
    private static final int OP_MAX = 3211271;
    public static final float MAX = asNan(OP_MAX);
    private static final int OP_POW = 3211272;
    public static final float POW = asNan(OP_POW);
    private static final int OP_SQRT = 3211273;
    public static final float SQRT = asNan(OP_SQRT);
    private static final int OP_ABS = 3211274;
    public static final float ABS = asNan(OP_ABS);
    private static final int OP_SIGN = 3211275;
    public static final float SIGN = asNan(OP_SIGN);
    private static final int OP_COPY_SIGN = 3211276;
    public static final float COPY_SIGN = asNan(OP_COPY_SIGN);
    private static final int OP_EXP = 3211277;
    public static final float EXP = asNan(OP_EXP);
    private static final int OP_FLOOR = 3211278;
    public static final float FLOOR = asNan(OP_FLOOR);
    private static final int OP_LOG = 3211279;
    public static final float LOG = asNan(OP_LOG);
    private static final int OP_LN = 3211280;
    public static final float LN = asNan(OP_LN);
    private static final int OP_ROUND = 3211281;
    public static final float ROUND = asNan(OP_ROUND);
    private static final int OP_SIN = 3211282;
    public static final float SIN = asNan(OP_SIN);
    private static final int OP_COS = 3211283;
    public static final float COS = asNan(OP_COS);
    private static final int OP_TAN = 3211284;
    public static final float TAN = asNan(OP_TAN);
    private static final int OP_ASIN = 3211285;
    public static final float ASIN = asNan(OP_ASIN);
    private static final int OP_ACOS = 3211286;
    public static final float ACOS = asNan(OP_ACOS);
    private static final int OP_ATAN = 3211287;
    public static final float ATAN = asNan(OP_ATAN);
    private static final int OP_ATAN2 = 3211288;
    public static final float ATAN2 = asNan(OP_ATAN2);
    private static final int OP_MAD = 3211289;
    public static final float MAD = asNan(OP_MAD);
    private static final int OP_TERNARY_CONDITIONAL = 3211290;
    public static final float IFELSE = asNan(OP_TERNARY_CONDITIONAL);
    private static final int OP_CLAMP = 3211291;
    public static final float CLAMP = asNan(OP_CLAMP);
    private static final int OP_CBRT = 3211292;
    public static final float CBRT = asNan(OP_CBRT);
    private static final int OP_DEG = 3211293;
    public static final float DEG = asNan(OP_DEG);
    private static final int OP_RAD = 3211294;
    public static final float RAD = asNan(OP_RAD);
    private static final int OP_CEIL = 3211295;
    public static final float CEIL = asNan(OP_CEIL);
    private static final int OP_A_DEREF = 3211296;
    public static final float A_DEREF = asNan(OP_A_DEREF);
    private static final int OP_A_MAX = 3211297;
    public static final float A_MAX = asNan(OP_A_MAX);
    private static final int OP_A_MIN = 3211298;
    public static final float A_MIN = asNan(OP_A_MIN);
    private static final int OP_A_SUM = 3211299;
    public static final float A_SUM = asNan(OP_A_SUM);
    private static final int OP_A_AVG = 3211300;
    public static final float A_AVG = asNan(OP_A_AVG);
    private static final int OP_A_LEN = 3211301;
    public static final float A_LEN = asNan(OP_A_LEN);
    private static final int OP_A_SPLINE = 3211302;
    public static final float A_SPLINE = asNan(OP_A_SPLINE);
    private static final int OP_RAND = 3211303;
    public static final float RAND = asNan(OP_RAND);
    private static final int OP_RAND_SEED = 3211304;
    public static final float RAND_SEED = asNan(OP_RAND_SEED);
    private static final int OP_NOISE_FROM = 3211305;
    public static final float NOISE_FROM = asNan(OP_NOISE_FROM);
    private static final int OP_RAND_IN_RANGE = 3211306;
    public static final float RAND_IN_RANGE = asNan(OP_RAND_IN_RANGE);
    private static final int OP_SQUARE_SUM = 3211307;
    public static final float SQUARE_SUM = asNan(OP_SQUARE_SUM);
    private static final int OP_STEP = 3211308;
    public static final float STEP = asNan(OP_STEP);
    private static final int OP_SQUARE = 3211309;
    public static final float SQUARE = asNan(OP_SQUARE);
    private static final int OP_DUP = 3211310;
    public static final float DUP = asNan(OP_DUP);
    private static final int OP_HYPOT = 3211311;
    public static final float HYPOT = asNan(OP_HYPOT);
    private static final int OP_SWAP = 3211312;
    public static final float SWAP = asNan(OP_SWAP);
    private static final int OP_LERP = 3211313;
    public static final float LERP = asNan(OP_LERP);
    public static final float SMOOTH_STEP = asNan(3211314);
    private static final int OP_FIRST_VAR = 3211315;
    public static final float VAR1 = asNan(OP_FIRST_VAR);
    private static final int OP_SECOND_VAR = 3211316;
    public static final float VAR2 = asNan(OP_SECOND_VAR);
    private static final int OP_THIRD_VAR = 3211317;
    public static final float VAR3 = asNan(OP_THIRD_VAR);
    float[] mStack = new float[0];
    float[] mLocalStack = new float[128];
    float[] mVar = new float[0];
    IntMap<MonotonicSpline> mSplineMap = new IntMap<>();

    interface Op {
        int eval(int i);
    }

    static boolean infix(int i) {
        return i < 6 || i == 25 || i == 26;
    }

    static {
        sNames.put(0, "NOP");
        sNames.put(1, "+");
        sNames.put(2, NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        sNames.put(3, "*");
        sNames.put(4, "/");
        sNames.put(5, "%");
        sNames.put(6, ParameterCapability.CAPABILITY_MIN);
        sNames.put(7, "max");
        sNames.put(8, "pow");
        sNames.put(9, "sqrt");
        sNames.put(10, "abs");
        sNames.put(11, "sign");
        sNames.put(12, "copySign");
        sNames.put(13, Telephony.BaseMmsColumns.EXPIRY);
        sNames.put(14, "floor");
        sNames.put(15, "log");
        sNames.put(16, "ln");
        sNames.put(17, "round");
        sNames.put(18, "sin");
        sNames.put(19, "cos");
        sNames.put(20, "tan");
        sNames.put(21, "asin");
        sNames.put(22, "acos");
        sNames.put(23, "atan");
        sNames.put(24, "atan2");
        sNames.put(25, "mad");
        sNames.put(26, "ifElse");
        sNames.put(27, "clamp");
        sNames.put(28, "cbrt");
        sNames.put(29, "deg");
        sNames.put(30, "rad");
        sNames.put(31, "ceil");
        sNames.put(32, "A_DEREF");
        sNames.put(33, "A_MAX");
        sNames.put(34, "A_MIN");
        sNames.put(35, "A_SUM");
        sNames.put(36, "A_AVG");
        sNames.put(37, "A_LEN");
        sNames.put(38, "A_SPLINE");
        sNames.put(39, "RAND");
        sNames.put(40, "RAND_SEED");
        sNames.put(41, "noise_from");
        sNames.put(42, "rand_in_range");
        sNames.put(43, "square_sum");
        sNames.put(44, "step");
        sNames.put(45, "square");
        sNames.put(46, "dup");
        sNames.put(47, "hypot");
        sNames.put(48, "swap");
        sNames.put(49, "lerp");
        sNames.put(50, "smooth_step");
        sNames.put(51, "a[0]");
        sNames.put(52, "a[1]");
        sNames.put(53, "a[2]");
        NO_OF_OPS = new int[]{-1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 1, 1, 1, 1, 0, 0, 0};
    }

    private float getSplineValue(int i, float f) {
        MonotonicSpline monotonicSpline = this.mSplineMap.get(i);
        float[] floats = this.mCollectionsAccess.getFloats(i);
        if (monotonicSpline != null && monotonicSpline.getArray() == floats) {
            return monotonicSpline.getPos(f);
        }
        MonotonicSpline monotonicSpline2 = new MonotonicSpline(null, floats);
        this.mSplineMap.put(i, monotonicSpline2);
        return monotonicSpline2.getPos(f);
    }

    public static boolean isMathOperator(float f) {
        if (Float.isNaN(f)) {
            int iFromNaN = fromNaN(f);
            if (!NanMap.isDataVariable(f) && iFromNaN > 3211264 && iFromNaN <= 3211314) {
                return true;
            }
        }
        return false;
    }

    public float eval(float[] fArr, float... fArr2) {
        this.mStack = fArr;
        this.mVar = fArr2;
        int iOpEval = -1;
        int i = 0;
        while (true) {
            float[] fArr3 = this.mStack;
            if (i < fArr3.length) {
                float f = fArr3[i];
                if (Float.isNaN(f)) {
                    iOpEval = opEval(iOpEval, fromNaN(f));
                } else {
                    iOpEval++;
                    this.mStack[iOpEval] = f;
                }
                i++;
            } else {
                return fArr3[iOpEval];
            }
        }
    }

    public float eval(CollectionsAccess collectionsAccess, float[] fArr, int i, float... fArr2) {
        int i2 = 0;
        System.arraycopy(fArr, 0, this.mLocalStack, 0, i);
        this.mStack = this.mLocalStack;
        this.mVar = fArr2;
        this.mCollectionsAccess = collectionsAccess;
        int iOpEval = -1;
        while (true) {
            float[] fArr3 = this.mStack;
            if (i2 < fArr3.length) {
                float f = fArr3[i2];
                if (Float.isNaN(f)) {
                    int iFromNaN = fromNaN(f);
                    if ((7340032 & iFromNaN) != 2097152) {
                        iOpEval = opEval(iOpEval, iFromNaN);
                    } else {
                        iOpEval++;
                        this.mStack[iOpEval] = f;
                    }
                } else {
                    iOpEval++;
                    this.mStack[iOpEval] = f;
                }
                i2++;
            } else {
                return fArr3[iOpEval];
            }
        }
    }

    public float eval(CollectionsAccess collectionsAccess, float[] fArr, int i) {
        System.arraycopy(fArr, 0, this.mLocalStack, 0, i);
        this.mStack = this.mLocalStack;
        this.mCollectionsAccess = collectionsAccess;
        int iOpEval = -1;
        for (int i2 = 0; i2 < i; i2++) {
            float f = this.mStack[i2];
            if (Float.isNaN(f)) {
                int iFromNaN = fromNaN(f);
                if ((7340032 & iFromNaN) != 2097152) {
                    iOpEval = opEval(iOpEval, iFromNaN);
                } else {
                    iOpEval++;
                    this.mStack[iOpEval] = f;
                }
            } else {
                iOpEval++;
                this.mStack[iOpEval] = f;
            }
        }
        return this.mStack[iOpEval];
    }

    private int dereference(CollectionsAccess collectionsAccess, int i, int i2) {
        float[] fArr = this.mStack;
        fArr[i2] = collectionsAccess.getFloatValue(i, (int) fArr[i2]);
        return i2;
    }

    public float eval(float[] fArr, int i, float... fArr2) {
        System.arraycopy(fArr, 0, this.mLocalStack, 0, i);
        this.mStack = this.mLocalStack;
        this.mVar = fArr2;
        int iOpEval = -1;
        for (int i2 = 0; i2 < i; i2++) {
            float f = this.mStack[i2];
            if (Float.isNaN(f)) {
                iOpEval = opEval(iOpEval, fromNaN(f));
            } else {
                iOpEval++;
                this.mStack[iOpEval] = f;
            }
        }
        return this.mStack[iOpEval];
    }

    public float evalDB(float[] fArr, float... fArr2) {
        this.mStack = fArr;
        this.mVar = fArr2;
        int iOpEval = -1;
        for (float f : fArr) {
            if (Float.isNaN(f)) {
                iOpEval = opEval(iOpEval, fromNaN(f));
            } else {
                System.out.print(" " + f);
                iOpEval++;
                this.mStack[iOpEval] = f;
            }
        }
        return this.mStack[iOpEval];
    }

    public static String toMathName(float f) {
        return sNames.get(fromNaN(f) - OFFSET);
    }

    public static String toString(float[] fArr, String[] strArr) {
        String str;
        StringBuilder sb;
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < fArr.length; i++) {
            float f = fArr[i];
            if (Float.isNaN(f)) {
                if (isMathOperator(f)) {
                    sb2.append(toMathName(f));
                } else {
                    int iFromNaN = fromNaN(f);
                    if (iFromNaN > 2097152) {
                        sb = new StringBuilder("A_");
                        iFromNaN &= 1048575;
                    } else {
                        sb = new StringBuilder("");
                    }
                    sb.append(iFromNaN);
                    String string = sb.toString();
                    sb2.append(NavigationBarInflaterView.SIZE_MOD_START);
                    sb2.append(string);
                    sb2.append(NavigationBarInflaterView.SIZE_MOD_END);
                }
            } else if (strArr != null && (str = strArr[i]) != null) {
                sb2.append(str);
                if (!strArr[i].contains(Session.SESSION_SEPARATION_CHAR_CHILD)) {
                    sb2.append(f);
                }
            } else {
                sb2.append(f);
            }
            sb2.append(" ");
        }
        return sb2.toString();
    }

    static String toString(float[] fArr, int i) {
        if (Float.isNaN(fArr[i])) {
            int iFromNaN = fromNaN(fArr[i]) - OFFSET;
            int i2 = NO_OF_OPS[iFromNaN];
            if (i2 == -1) {
                return "nop";
            }
            if (i2 == 1) {
                return sNames.get(iFromNaN) + NavigationBarInflaterView.KEY_CODE_START + toString(fArr, i + 1) + ") ";
            }
            if (i2 == 2) {
                if (infix(iFromNaN)) {
                    return NavigationBarInflaterView.KEY_CODE_START + toString(fArr, i + 1) + sNames.get(iFromNaN) + " " + toString(fArr, i + 2) + ") ";
                }
                return sNames.get(iFromNaN) + NavigationBarInflaterView.KEY_CODE_START + toString(fArr, i + 1) + ", " + toString(fArr, i + 2) + NavigationBarInflaterView.KEY_CODE_END;
            }
            if (i2 == 3) {
                if (infix(iFromNaN)) {
                    return "((" + toString(fArr, i + 1) + ") ? " + toString(fArr, i + 2) + ":" + toString(fArr, i + 3) + NavigationBarInflaterView.KEY_CODE_END;
                }
                return sNames.get(iFromNaN) + NavigationBarInflaterView.KEY_CODE_START + toString(fArr, i + 1) + ", " + toString(fArr, i + 2) + ", " + toString(fArr, i + 3) + NavigationBarInflaterView.KEY_CODE_END;
            }
        }
        return Float.toString(fArr[i]);
    }

    public static float asNan(int i) {
        return Float.intBitsToFloat(i | (-8388608));
    }

    public static int fromNaN(float f) {
        return Float.floatToRawIntBits(f) & AudioChannelLayout.INDEX_MASK_23;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    int opEval(int i, int i2) {
        int i3 = 0;
        float f = 0.0f;
        int i4 = 1;
        switch (i2) {
            case OP_ADD /* 3211265 */:
                float[] fArr = this.mStack;
                int i5 = i - 1;
                fArr[i5] = fArr[i5] + fArr[i];
                return i5;
            case OP_SUB /* 3211266 */:
                float[] fArr2 = this.mStack;
                int i6 = i - 1;
                fArr2[i6] = fArr2[i6] - fArr2[i];
                return i6;
            case OP_MUL /* 3211267 */:
                float[] fArr3 = this.mStack;
                int i7 = i - 1;
                fArr3[i7] = fArr3[i7] * fArr3[i];
                return i7;
            case OP_DIV /* 3211268 */:
                float[] fArr4 = this.mStack;
                int i8 = i - 1;
                fArr4[i8] = fArr4[i8] / fArr4[i];
                return i8;
            case OP_MOD /* 3211269 */:
                float[] fArr5 = this.mStack;
                int i9 = i - 1;
                fArr5[i9] = fArr5[i9] % fArr5[i];
                return i9;
            case OP_MIN /* 3211270 */:
                float[] fArr6 = this.mStack;
                int i10 = i - 1;
                fArr6[i10] = Math.min(fArr6[i10], fArr6[i]);
                return i10;
            case OP_MAX /* 3211271 */:
                float[] fArr7 = this.mStack;
                int i11 = i - 1;
                fArr7[i11] = Math.max(fArr7[i11], fArr7[i]);
                return i11;
            case OP_POW /* 3211272 */:
                int i12 = i - 1;
                this.mStack[i12] = (float) Math.pow(r7[i12], r7[i]);
                return i12;
            case OP_SQRT /* 3211273 */:
                this.mStack[i] = (float) Math.sqrt(r7[i]);
                return i;
            case OP_ABS /* 3211274 */:
                float[] fArr8 = this.mStack;
                fArr8[i] = Math.abs(fArr8[i]);
                return i;
            case OP_SIGN /* 3211275 */:
                float[] fArr9 = this.mStack;
                fArr9[i] = Math.signum(fArr9[i]);
                return i;
            case OP_COPY_SIGN /* 3211276 */:
                float[] fArr10 = this.mStack;
                int i13 = i - 1;
                fArr10[i13] = Math.copySign(fArr10[i13], fArr10[i]);
                return i13;
            case OP_EXP /* 3211277 */:
                this.mStack[i] = (float) Math.exp(r7[i]);
                return i;
            case OP_FLOOR /* 3211278 */:
                this.mStack[i] = (float) Math.floor(r7[i]);
                return i;
            case OP_LOG /* 3211279 */:
                this.mStack[i] = (float) Math.log10(r7[i]);
                return i;
            case OP_LN /* 3211280 */:
                this.mStack[i] = (float) Math.log(r7[i]);
                return i;
            case OP_ROUND /* 3211281 */:
                this.mStack[i] = Math.round(r7[i]);
                return i;
            case OP_SIN /* 3211282 */:
                this.mStack[i] = (float) Math.sin(r7[i]);
                return i;
            case OP_COS /* 3211283 */:
                this.mStack[i] = (float) Math.cos(r7[i]);
                return i;
            case OP_TAN /* 3211284 */:
                this.mStack[i] = (float) Math.tan(r7[i]);
                return i;
            case OP_ASIN /* 3211285 */:
                this.mStack[i] = (float) Math.asin(r7[i]);
                return i;
            case OP_ACOS /* 3211286 */:
                this.mStack[i] = (float) Math.acos(r7[i]);
                return i;
            case OP_ATAN /* 3211287 */:
                this.mStack[i] = (float) Math.atan(r7[i]);
                return i;
            case OP_ATAN2 /* 3211288 */:
                int i14 = i - 1;
                this.mStack[i14] = (float) Math.atan2(r7[i14], r7[i]);
                return i14;
            case OP_MAD /* 3211289 */:
                float[] fArr11 = this.mStack;
                int i15 = i - 2;
                fArr11[i15] = fArr11[i] + (fArr11[i - 1] * fArr11[i15]);
                return i15;
            case OP_TERNARY_CONDITIONAL /* 3211290 */:
                float[] fArr12 = this.mStack;
                int i16 = i - 2;
                fArr12[i16] = fArr12[i] > 0.0f ? fArr12[i - 1] : fArr12[i16];
                return i16;
            case OP_CLAMP /* 3211291 */:
                float[] fArr13 = this.mStack;
                int i17 = i - 2;
                fArr13[i17] = Math.min(Math.max(fArr13[i17], fArr13[i]), this.mStack[i - 1]);
                return i17;
            case OP_CBRT /* 3211292 */:
                this.mStack[i] = (float) Math.pow(r7[i], 0.3333333333333333d);
                return i;
            case OP_DEG /* 3211293 */:
                float[] fArr14 = this.mStack;
                fArr14[i] = fArr14[i] * 57.29578f;
                return i;
            case OP_RAD /* 3211294 */:
                float[] fArr15 = this.mStack;
                fArr15[i] = fArr15[i] * FP_TO_DEG;
                return i;
            case OP_CEIL /* 3211295 */:
                this.mStack[i] = (float) Math.ceil(r7[i]);
                return i;
            case OP_A_DEREF /* 3211296 */:
                int i18 = i - 1;
                int iFromNaN = fromNaN(this.mStack[i18]);
                float[] fArr16 = this.mStack;
                fArr16[i18] = this.mCollectionsAccess.getFloatValue(iFromNaN, (int) fArr16[i]);
                return i18;
            case OP_A_MAX /* 3211297 */:
                float[] floats = this.mCollectionsAccess.getFloats(fromNaN(this.mStack[i]));
                float fMax = floats[0];
                while (i4 < floats.length) {
                    fMax = Math.max(fMax, floats[i4]);
                    i4++;
                }
                this.mStack[i] = fMax;
                return i;
            case OP_A_MIN /* 3211298 */:
                float[] floats2 = this.mCollectionsAccess.getFloats(fromNaN(this.mStack[i]));
                if (floats2.length != 0) {
                    float fMin = floats2[0];
                    while (i4 < floats2.length) {
                        fMin = Math.min(fMin, floats2[i4]);
                        i4++;
                    }
                    this.mStack[i] = fMin;
                    return i;
                }
                return i;
            case OP_A_SUM /* 3211299 */:
                float[] floats3 = this.mCollectionsAccess.getFloats(fromNaN(this.mStack[i]));
                while (i3 < floats3.length) {
                    f += floats3[i3];
                    i3++;
                }
                this.mStack[i] = f;
                return i;
            case OP_A_AVG /* 3211300 */:
                float[] floats4 = this.mCollectionsAccess.getFloats(fromNaN(this.mStack[i]));
                while (i3 < floats4.length) {
                    f += floats4[i3];
                    i3++;
                }
                this.mStack[i] = f / floats4.length;
                return i;
            case OP_A_LEN /* 3211301 */:
                this.mStack[i] = this.mCollectionsAccess.getListLength(fromNaN(this.mStack[i]));
                return i;
            case OP_A_SPLINE /* 3211302 */:
                int i19 = i - 1;
                int iFromNaN2 = fromNaN(this.mStack[i19]);
                float[] fArr17 = this.mStack;
                fArr17[i19] = getSplineValue(iFromNaN2, fArr17[i]);
                return i19;
            case OP_RAND /* 3211303 */:
                if (sRandom == null) {
                    sRandom = new Random();
                }
                int i20 = i + 1;
                this.mStack[i20] = sRandom.nextFloat();
                return i20;
            case OP_RAND_SEED /* 3211304 */:
                if (this.mStack[i] == 0.0f) {
                    sRandom = new Random();
                } else {
                    Random random = sRandom;
                    if (random == null) {
                        sRandom = new Random(Float.floatToRawIntBits(r7));
                    } else {
                        random.setSeed(Float.floatToRawIntBits(r7));
                    }
                }
                return i - 1;
            case OP_NOISE_FROM /* 3211305 */:
                int iFloatToRawIntBits = Float.floatToRawIntBits(this.mStack[i]);
                int i21 = iFloatToRawIntBits ^ (iFloatToRawIntBits << 13);
                this.mStack[i] = 1.0f - ((((i21 * (((i21 * i21) * 15731) + 789221)) + 1376312589) & Integer.MAX_VALUE) / 1.0737418E9f);
                return i;
            case OP_RAND_IN_RANGE /* 3211306 */:
                if (sRandom == null) {
                    sRandom = new Random();
                }
                float[] fArr18 = this.mStack;
                float fNextFloat = sRandom.nextFloat();
                float[] fArr19 = this.mStack;
                float f2 = fArr19[i];
                float f3 = fArr19[i - 1];
                fArr18[i] = (fNextFloat * (f2 - f3)) + f3;
                return i;
            case OP_SQUARE_SUM /* 3211307 */:
                float[] fArr20 = this.mStack;
                int i22 = i - 1;
                float f4 = fArr20[i22];
                float f5 = fArr20[i];
                fArr20[i22] = (f4 * f4) + (f5 * f5);
                return i22;
            case OP_STEP /* 3211308 */:
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append(this.mStack[i]);
                sb.append(" > ");
                int i23 = i - 1;
                sb.append(this.mStack[i23]);
                printStream.println(sb.toString());
                float[] fArr21 = this.mStack;
                fArr21[i23] = fArr21[i23] <= fArr21[i] ? 0.0f : 1.0f;
                return i23;
            case OP_SQUARE /* 3211309 */:
                float[] fArr22 = this.mStack;
                float f6 = fArr22[i];
                fArr22[i] = f6 * f6;
                return i;
            case OP_DUP /* 3211310 */:
                float[] fArr23 = this.mStack;
                int i24 = i + 1;
                fArr23[i24] = fArr23[i];
                return i24;
            case OP_HYPOT /* 3211311 */:
                int i25 = i - 1;
                this.mStack[i25] = (float) Math.hypot(r7[i25], r7[i]);
                return i25;
            case OP_SWAP /* 3211312 */:
                float[] fArr24 = this.mStack;
                int i26 = i - 1;
                float f7 = fArr24[i26];
                fArr24[i26] = fArr24[i];
                fArr24[i] = f7;
                return i;
            case OP_LERP /* 3211313 */:
                float[] fArr25 = this.mStack;
                int i27 = i - 2;
                float f8 = fArr25[i27];
                fArr25[i27] = f8 + ((fArr25[i - 1] - f8) * fArr25[i]);
                return i27;
            case 3211314:
                float[] fArr26 = this.mStack;
                int i28 = i - 2;
                float f9 = fArr26[i28];
                float f10 = fArr26[i - 1];
                float f11 = fArr26[i];
                System.out.println("val3 = " + f9 + " min1 = " + f11 + " max2 = " + f10);
                if (f9 < f11) {
                    this.mStack[i28] = 0.0f;
                    System.out.println("below min ");
                } else if (f9 > f10) {
                    this.mStack[i28] = 1.0f;
                    System.out.println("above max ");
                } else {
                    float f12 = (f9 - f11) / (f10 - f11);
                    System.out.println("v = " + f12);
                    this.mStack[i28] = f12 * f12 * (3.0f - (f12 * 2.0f));
                }
                return i28;
            case OP_FIRST_VAR /* 3211315 */:
                this.mStack[i] = this.mVar[0];
                return i;
            case OP_SECOND_VAR /* 3211316 */:
                this.mStack[i] = this.mVar[1];
                return i;
            case OP_THIRD_VAR /* 3211317 */:
                this.mStack[i] = this.mVar[2];
                return i;
            default:
                return i;
        }
    }
}

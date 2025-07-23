package com.android.internal.widget.remotecompose.core.operations.utilities;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.quality.ParameterCapability;
import android.util.NtpTrustedTime;
import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes6.dex */
public class IntegerExpressionEvaluator {
    public static final int I_ABS = 65552;
    public static final int I_ADD = 65537;
    public static final int I_AND = 65546;
    public static final int I_CLAMP = 65557;
    public static final int I_COPY_SIGN = 65548;
    public static final int I_DECR = 65554;
    public static final int I_DIV = 65540;
    public static final int I_IFELSE = 65558;
    public static final int I_INCR = 65553;
    public static final int I_MAD = 65559;
    public static final int I_MAX = 65550;
    public static final int I_MIN = 65549;
    public static final int I_MOD = 65541;
    public static final int I_MUL = 65539;
    public static final int I_NEG = 65551;
    public static final int I_NOT = 65555;
    public static final int I_OR = 65545;
    public static final int I_SHL = 65542;
    public static final int I_SHR = 65543;
    public static final int I_SIGN = 65556;
    public static final int I_SUB = 65538;
    public static final int I_USHR = 65544;
    public static final int I_VAR1 = 65560;
    public static final int I_VAR2 = 65561;
    public static final int I_XOR = 65547;
    public static final float LAST_OP = 25.0f;
    static final int[] NO_OF_OPS;
    public static final int OFFSET = 65536;
    private static final int OP_ABS = 65552;
    private static final int OP_ADD = 65537;
    private static final int OP_AND = 65546;
    private static final int OP_CLAMP = 65557;
    private static final int OP_COPY_SIGN = 65548;
    private static final int OP_DECR = 65554;
    private static final int OP_DIV = 65540;
    private static final int OP_FIRST_VAR = 65560;
    private static final int OP_INCR = 65553;
    private static final int OP_MAD = 65559;
    private static final int OP_MAX = 65550;
    private static final int OP_MIN = 65549;
    private static final int OP_MOD = 65541;
    private static final int OP_MUL = 65539;
    private static final int OP_NEG = 65551;
    private static final int OP_NOT = 65555;
    private static final int OP_OR = 65545;
    private static final int OP_SECOND_VAR = 65561;
    private static final int OP_SHL = 65542;
    private static final int OP_SHR = 65543;
    private static final int OP_SIGN = 65556;
    private static final int OP_SUB = 65538;
    private static final int OP_TERNARY_CONDITIONAL = 65558;
    private static final int OP_THIRD_VAR = 65562;
    private static final int OP_USHR = 65544;
    private static final int OP_XOR = 65547;
    static IntMap<String> sNames;
    int[] mStack = new int[0];
    int[] mLocalStack = new int[128];
    int[] mVar = new int[0];

    interface Op {
        int eval(int i);
    }

    static boolean infix(int i) {
        return i < 12;
    }

    public static boolean isOperation(int i, int i2) {
        return (i & (1 << i2)) != 0;
    }

    static {
        IntMap<String> intMap = new IntMap<>();
        sNames = intMap;
        intMap.put(0, "NOP");
        sNames.put(1, "+");
        sNames.put(2, NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        sNames.put(3, "*");
        sNames.put(4, "/");
        sNames.put(5, "%");
        sNames.put(6, "<<");
        sNames.put(7, ">>");
        sNames.put(8, ">>>");
        sNames.put(9, NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        sNames.put(10, "&");
        sNames.put(11, "^");
        sNames.put(12, "copySign");
        sNames.put(13, ParameterCapability.CAPABILITY_MIN);
        sNames.put(14, "max");
        sNames.put(15, "neg");
        sNames.put(16, "abs");
        sNames.put(17, "incr");
        sNames.put(18, "decr");
        sNames.put(19, "not");
        sNames.put(20, "sign");
        sNames.put(21, "clamp");
        sNames.put(22, "ifElse");
        sNames.put(23, "mad");
        sNames.put(24, "ceil");
        sNames.put(25, "a[0]");
        sNames.put(26, "a[1]");
        sNames.put(27, "a[2]");
        NO_OF_OPS = new int[]{-1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 3, 3, 3, 0, 0, 0};
    }

    public int eval(int i, int[] iArr, int... iArr2) {
        this.mStack = iArr;
        this.mVar = iArr2;
        int i2 = -1;
        int i3 = 0;
        while (true) {
            int[] iArr3 = this.mStack;
            if (i3 < iArr3.length) {
                int i4 = iArr3[i3];
                if (((1 << i3) & i) != 0) {
                    i2 = opEval(i2, i4);
                } else {
                    i2++;
                    iArr3[i2] = i4;
                }
                i3++;
            } else {
                return iArr3[i2];
            }
        }
    }

    public int eval(int i, int[] iArr, int i2, int... iArr2) {
        System.arraycopy(iArr, 0, this.mLocalStack, 0, i2);
        this.mStack = this.mLocalStack;
        this.mVar = iArr2;
        int i3 = -1;
        for (int i4 = 0; i4 < i2; i4++) {
            int[] iArr3 = this.mStack;
            int i5 = iArr3[i4];
            if (((1 << i4) & i) != 0) {
                i3 = opEval(i3, i5);
            } else {
                i3++;
                iArr3[i3] = i5;
            }
        }
        return this.mStack[i3];
    }

    public int evalDB(int i, int[] iArr, int... iArr2) {
        this.mStack = iArr;
        this.mVar = iArr2;
        int i2 = -1;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            int[] iArr3 = this.mStack;
            int i4 = iArr3[i3];
            if (((1 << i3) & i) != 0) {
                i2 = opEval(i2, i4);
            } else {
                i2++;
                iArr3[i2] = i4;
            }
        }
        return this.mStack[i2];
    }

    int opEval(int i, int i2) {
        switch (i2) {
            case 65537:
                int[] iArr = this.mStack;
                int i3 = i - 1;
                iArr[i3] = iArr[i3] + iArr[i];
                return i3;
            case 65538:
                int[] iArr2 = this.mStack;
                int i4 = i - 1;
                iArr2[i4] = iArr2[i4] - iArr2[i];
                return i4;
            case 65539:
                int[] iArr3 = this.mStack;
                int i5 = i - 1;
                iArr3[i5] = iArr3[i5] * iArr3[i];
                return i5;
            case 65540:
                int[] iArr4 = this.mStack;
                int i6 = i - 1;
                iArr4[i6] = iArr4[i6] / iArr4[i];
                return i6;
            case 65541:
                int[] iArr5 = this.mStack;
                int i7 = i - 1;
                iArr5[i7] = iArr5[i7] % iArr5[i];
                return i7;
            case 65542:
                int[] iArr6 = this.mStack;
                int i8 = i - 1;
                iArr6[i8] = iArr6[i8] << iArr6[i];
                return i8;
            case 65543:
                int[] iArr7 = this.mStack;
                int i9 = i - 1;
                iArr7[i9] = iArr7[i9] >> iArr7[i];
                return i9;
            case 65544:
                int[] iArr8 = this.mStack;
                int i10 = i - 1;
                iArr8[i10] = iArr8[i10] >>> iArr8[i];
                return i10;
            case 65545:
                int[] iArr9 = this.mStack;
                int i11 = i - 1;
                iArr9[i11] = iArr9[i] | iArr9[i11];
                return i11;
            case 65546:
                int[] iArr10 = this.mStack;
                int i12 = i - 1;
                iArr10[i12] = iArr10[i] & iArr10[i12];
                return i12;
            case 65547:
                int[] iArr11 = this.mStack;
                int i13 = i - 1;
                iArr11[i13] = iArr11[i] ^ iArr11[i13];
                return i13;
            case 65548:
                int[] iArr12 = this.mStack;
                int i14 = i - 1;
                int i15 = iArr12[i14];
                int i16 = iArr12[i];
                iArr12[i14] = (i15 ^ (i16 >> 31)) - (i16 >> 31);
                return i14;
            case 65549:
                int[] iArr13 = this.mStack;
                int i17 = i - 1;
                iArr13[i17] = Math.min(iArr13[i17], iArr13[i]);
                return i17;
            case 65550:
                int[] iArr14 = this.mStack;
                int i18 = i - 1;
                iArr14[i18] = Math.max(iArr14[i18], iArr14[i]);
                return i18;
            case 65551:
                int[] iArr15 = this.mStack;
                iArr15[i] = -iArr15[i];
                return i;
            case 65552:
                int[] iArr16 = this.mStack;
                iArr16[i] = Math.abs(iArr16[i]);
                return i;
            case 65553:
                int[] iArr17 = this.mStack;
                iArr17[i] = iArr17[i] + 1;
                return i;
            case 65554:
                int[] iArr18 = this.mStack;
                iArr18[i] = iArr18[i] - 1;
                return i;
            case 65555:
                int[] iArr19 = this.mStack;
                iArr19[i] = ~iArr19[i];
                return i;
            case 65556:
                int[] iArr20 = this.mStack;
                int i19 = iArr20[i];
                iArr20[i] = ((-i19) >>> 31) | (i19 >> 31);
                return i;
            case 65557:
                int[] iArr21 = this.mStack;
                int i20 = i - 2;
                iArr21[i20] = Math.min(Math.max(iArr21[i20], iArr21[i]), this.mStack[i - 1]);
                return i20;
            case 65558:
                int[] iArr22 = this.mStack;
                int i21 = i - 2;
                iArr22[i21] = iArr22[i] > 0 ? iArr22[i - 1] : iArr22[i21];
                return i21;
            case 65559:
                int[] iArr23 = this.mStack;
                int i22 = i - 2;
                iArr23[i22] = iArr23[i] + (iArr23[i - 1] * iArr23[i22]);
                return i22;
            case 65560:
                this.mStack[i] = this.mVar[0];
                return i;
            case 65561:
                this.mStack[i] = this.mVar[1];
                return i;
            case 65562:
                this.mStack[i] = this.mVar[2];
                return i;
            default:
                return 0;
        }
    }

    public static String toMathName(int i) {
        return sNames.get(i - 65536);
    }

    public static String toString(int i, int[] iArr, String[] strArr) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int i3 = iArr[i2];
            if (((1 << i2) & i) == 0) {
                String str = strArr[i2];
                if (str != null) {
                    sb.append(str);
                }
                sb.append(i3);
            } else if (i3 < 65536) {
                sb.append(toMathName(i3));
            } else {
                sb.append(NavigationBarInflaterView.SIZE_MOD_START);
                sb.append(i3);
                sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            }
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String toString(int i, int[] iArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toBinaryString(i));
        sb.append(" : ");
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int i3 = iArr[i2];
            if (((1 << i2) & i) != 0) {
                if (i3 > 65536) {
                    sb.append(" ");
                    sb.append(toMathName(i3));
                    sb.append(" ");
                } else {
                    sb.append(NavigationBarInflaterView.SIZE_MOD_START);
                    sb.append(i3);
                    sb.append(NavigationBarInflaterView.SIZE_MOD_END);
                }
            }
            sb.append(" " + i3);
        }
        return sb.toString();
    }

    public static String toStringInfix(int i, int[] iArr) {
        return toString(i, iArr, iArr.length - 1);
    }

    static String toString(int i, int[] iArr, int i2) {
        if (((1 << i2) & i) != 0) {
            int i3 = iArr[i2] - 65536;
            int i4 = NO_OF_OPS[i3];
            if (i4 == -1) {
                return "nop";
            }
            if (i4 == 1) {
                return sNames.get(i3) + NavigationBarInflaterView.KEY_CODE_START + toString(i, iArr, i2 - 1) + ") ";
            }
            if (i4 == 2) {
                if (infix(i3)) {
                    return NavigationBarInflaterView.KEY_CODE_START + toString(i, iArr, i2 - 2) + " " + sNames.get(i3) + " " + toString(i, iArr, i2 - 1) + ") ";
                }
                return sNames.get(i3) + NavigationBarInflaterView.KEY_CODE_START + toString(i, iArr, i2 - 2) + ", " + toString(i, iArr, i2 - 1) + NavigationBarInflaterView.KEY_CODE_END;
            }
            if (i4 == 3) {
                if (infix(i3)) {
                    return "((" + toString(i, iArr, i2 + 3) + ") ? " + toString(i, iArr, i2 - 2) + ":" + toString(i, iArr, i2 - 1) + NavigationBarInflaterView.KEY_CODE_END;
                }
                return sNames.get(i3) + NavigationBarInflaterView.KEY_CODE_START + toString(i, iArr, i2 - 3) + ", " + toString(i, iArr, i2 - 2) + ", " + toString(i, iArr, i2 - 1) + NavigationBarInflaterView.KEY_CODE_END;
            }
        }
        return Integer.toString(iArr[i2]);
    }
}

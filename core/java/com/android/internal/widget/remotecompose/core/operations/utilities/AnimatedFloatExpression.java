package com.android.internal.widget.remotecompose.core.operations.utilities;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.provider.Telephony;
import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes5.dex */
public class AnimatedFloatExpression {
    private static final float FP_PI = 3.1415927f;
    private static final float FP_TO_DEG = 0.017453292f;
    private static final float FP_TO_RAD = 57.29578f;
    public static final float LAST_OP = 31.0f;
    static final int[] NO_OF_OPS;
    public static final int OFFSET = 256;
    float[] mLocalStack = new float[128];
    Op[] mOps = {null, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda0
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$0;
            lambda$new$0 = AnimatedFloatExpression.this.lambda$new$0(i);
            return lambda$new$0;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda11
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$1;
            lambda$new$1 = AnimatedFloatExpression.this.lambda$new$1(i);
            return lambda$new$1;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda22
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$2;
            lambda$new$2 = AnimatedFloatExpression.this.lambda$new$2(i);
            return lambda$new$2;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda27
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$3;
            lambda$new$3 = AnimatedFloatExpression.this.lambda$new$3(i);
            return lambda$new$3;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda28
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$4;
            lambda$new$4 = AnimatedFloatExpression.this.lambda$new$4(i);
            return lambda$new$4;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda29
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$5;
            lambda$new$5 = AnimatedFloatExpression.this.lambda$new$5(i);
            return lambda$new$5;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda30
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$6;
            lambda$new$6 = AnimatedFloatExpression.this.lambda$new$6(i);
            return lambda$new$6;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda31
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$7;
            lambda$new$7 = AnimatedFloatExpression.this.lambda$new$7(i);
            return lambda$new$7;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda32
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$8;
            lambda$new$8 = AnimatedFloatExpression.this.lambda$new$8(i);
            return lambda$new$8;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda33
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$9;
            lambda$new$9 = AnimatedFloatExpression.this.lambda$new$9(i);
            return lambda$new$9;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda1
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$10;
            lambda$new$10 = AnimatedFloatExpression.this.lambda$new$10(i);
            return lambda$new$10;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda2
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$11;
            lambda$new$11 = AnimatedFloatExpression.this.lambda$new$11(i);
            return lambda$new$11;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda3
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$12;
            lambda$new$12 = AnimatedFloatExpression.this.lambda$new$12(i);
            return lambda$new$12;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda4
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$13;
            lambda$new$13 = AnimatedFloatExpression.this.lambda$new$13(i);
            return lambda$new$13;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda5
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$14;
            lambda$new$14 = AnimatedFloatExpression.this.lambda$new$14(i);
            return lambda$new$14;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda6
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$15;
            lambda$new$15 = AnimatedFloatExpression.this.lambda$new$15(i);
            return lambda$new$15;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda7
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$16;
            lambda$new$16 = AnimatedFloatExpression.this.lambda$new$16(i);
            return lambda$new$16;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda8
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$17;
            lambda$new$17 = AnimatedFloatExpression.this.lambda$new$17(i);
            return lambda$new$17;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda9
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$18;
            lambda$new$18 = AnimatedFloatExpression.this.lambda$new$18(i);
            return lambda$new$18;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda10
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$19;
            lambda$new$19 = AnimatedFloatExpression.this.lambda$new$19(i);
            return lambda$new$19;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda12
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$20;
            lambda$new$20 = AnimatedFloatExpression.this.lambda$new$20(i);
            return lambda$new$20;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda13
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$21;
            lambda$new$21 = AnimatedFloatExpression.this.lambda$new$21(i);
            return lambda$new$21;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda14
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$22;
            lambda$new$22 = AnimatedFloatExpression.this.lambda$new$22(i);
            return lambda$new$22;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda15
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$23;
            lambda$new$23 = AnimatedFloatExpression.this.lambda$new$23(i);
            return lambda$new$23;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda16
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$24;
            lambda$new$24 = AnimatedFloatExpression.this.lambda$new$24(i);
            return lambda$new$24;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda17
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$25;
            lambda$new$25 = AnimatedFloatExpression.this.lambda$new$25(i);
            return lambda$new$25;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda18
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$26;
            lambda$new$26 = AnimatedFloatExpression.this.lambda$new$26(i);
            return lambda$new$26;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda19
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$27;
            lambda$new$27 = AnimatedFloatExpression.this.lambda$new$27(i);
            return lambda$new$27;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda20
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$28;
            lambda$new$28 = AnimatedFloatExpression.this.lambda$new$28(i);
            return lambda$new$28;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda21
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$29;
            lambda$new$29 = AnimatedFloatExpression.this.lambda$new$29(i);
            return lambda$new$29;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda23
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$30;
            lambda$new$30 = AnimatedFloatExpression.this.lambda$new$30(i);
            return lambda$new$30;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda24
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$31;
            lambda$new$31 = AnimatedFloatExpression.this.lambda$new$31(i);
            return lambda$new$31;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda25
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$32;
            lambda$new$32 = AnimatedFloatExpression.this.lambda$new$32(i);
            return lambda$new$32;
        }
    }, new Op() { // from class: com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression$$ExternalSyntheticLambda26
        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.AnimatedFloatExpression.Op
        public final int eval(int i) {
            int lambda$new$33;
            lambda$new$33 = AnimatedFloatExpression.this.lambda$new$33(i);
            return lambda$new$33;
        }
    }};
    float[] mStack;
    float[] mVar;
    static IntMap<String> sNames = new IntMap<>();
    public static final float ADD = asNan(257);
    public static final float SUB = asNan(258);
    public static final float MUL = asNan(259);
    public static final float DIV = asNan(260);
    public static final float MOD = asNan(261);
    public static final float MIN = asNan(262);
    public static final float MAX = asNan(263);
    public static final float POW = asNan(264);
    public static final float SQRT = asNan(265);
    public static final float ABS = asNan(266);
    public static final float SIGN = asNan(267);
    public static final float COPY_SIGN = asNan(268);
    public static final float EXP = asNan(269);
    public static final float FLOOR = asNan(270);
    public static final float LOG = asNan(271);
    public static final float LN = asNan(272);
    public static final float ROUND = asNan(273);
    public static final float SIN = asNan(274);
    public static final float COS = asNan(275);
    public static final float TAN = asNan(276);
    public static final float ASIN = asNan(277);
    public static final float ACOS = asNan(278);
    public static final float ATAN = asNan(279);
    public static final float ATAN2 = asNan(280);
    public static final float MAD = asNan(281);
    public static final float IFELSE = asNan(282);
    public static final float CLAMP = asNan(283);
    public static final float CBRT = asNan(284);
    public static final float DEG = asNan(285);
    public static final float RAD = asNan(286);
    public static final float CEIL = asNan(287);
    public static final float VAR1 = asNan(283);
    public static final float VAR2 = asNan(284);

    interface Op {
        int eval(int i);
    }

    static {
        int k = 0 + 1;
        sNames.put(0, "NOP");
        int k2 = k + 1;
        sNames.put(k, "+");
        int k3 = k2 + 1;
        sNames.put(k2, NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        int k4 = k3 + 1;
        sNames.put(k3, "*");
        int k5 = k4 + 1;
        sNames.put(k4, "/");
        int k6 = k5 + 1;
        sNames.put(k5, "%");
        int k7 = k6 + 1;
        sNames.put(k6, "min");
        int k8 = k7 + 1;
        sNames.put(k7, "max");
        int k9 = k8 + 1;
        sNames.put(k8, "pow");
        int k10 = k9 + 1;
        sNames.put(k9, "sqrt");
        int k11 = k10 + 1;
        sNames.put(k10, "abs");
        int k12 = k11 + 1;
        sNames.put(k11, "sign");
        int k13 = k12 + 1;
        sNames.put(k12, "copySign");
        int k14 = k13 + 1;
        sNames.put(k13, Telephony.BaseMmsColumns.EXPIRY);
        int k15 = k14 + 1;
        sNames.put(k14, "floor");
        int k16 = k15 + 1;
        sNames.put(k15, "log");
        int k17 = k16 + 1;
        sNames.put(k16, "ln");
        int k18 = k17 + 1;
        sNames.put(k17, "round");
        int k19 = k18 + 1;
        sNames.put(k18, "sin");
        int k20 = k19 + 1;
        sNames.put(k19, "cos");
        int k21 = k20 + 1;
        sNames.put(k20, "tan");
        int k22 = k21 + 1;
        sNames.put(k21, "asin");
        int k23 = k22 + 1;
        sNames.put(k22, "acos");
        int k24 = k23 + 1;
        sNames.put(k23, "atan");
        int k25 = k24 + 1;
        sNames.put(k24, "atan2");
        int k26 = k25 + 1;
        sNames.put(k25, "mad");
        int k27 = k26 + 1;
        sNames.put(k26, "ifElse");
        int k28 = k27 + 1;
        sNames.put(k27, "clamp");
        int k29 = k28 + 1;
        sNames.put(k28, "cbrt");
        int k30 = k29 + 1;
        sNames.put(k29, "deg");
        int k31 = k30 + 1;
        sNames.put(k30, "rad");
        int k32 = k31 + 1;
        sNames.put(k31, "ceil");
        int k33 = k32 + 1;
        sNames.put(k32, "a[0]");
        int k34 = k33 + 1;
        sNames.put(k33, "a[1]");
        int i = k34 + 1;
        sNames.put(k34, "a[2]");
        NO_OF_OPS = new int[]{-1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 1, 1, 1, 1, 0, 0, 0};
    }

    public static boolean isMathOperator(float v) {
        int pos;
        return Float.isNaN(v) && (pos = fromNaN(v)) > 256 && ((float) pos) <= 287.0f;
    }

    public float eval(float[] exp, float... var) {
        this.mStack = exp;
        this.mVar = var;
        int sp = -1;
        for (int i = 0; i < this.mStack.length; i++) {
            float v = this.mStack[i];
            if (Float.isNaN(v)) {
                sp = this.mOps[fromNaN(v) - 256].eval(sp);
            } else {
                sp++;
                this.mStack[sp] = v;
            }
        }
        return this.mStack[sp];
    }

    public float eval(float[] exp, int len, float... var) {
        System.arraycopy(exp, 0, this.mLocalStack, 0, len);
        this.mStack = this.mLocalStack;
        this.mVar = var;
        int sp = -1;
        for (int i = 0; i < len; i++) {
            float v = this.mStack[i];
            if (Float.isNaN(v)) {
                sp = this.mOps[fromNaN(v) - 256].eval(sp);
            } else {
                sp++;
                this.mStack[sp] = v;
            }
        }
        return this.mStack[sp];
    }

    public float evalDB(float[] exp, float... var) {
        this.mStack = exp;
        this.mVar = var;
        int sp = -1;
        for (float v : exp) {
            if (Float.isNaN(v)) {
                System.out.print(" " + sNames.get(fromNaN(v) - 256));
                sp = this.mOps[fromNaN(v) - 256].eval(sp);
            } else {
                System.out.print(" " + v);
                sp++;
                this.mStack[sp] = v;
            }
        }
        return this.mStack[sp];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$0(int sp) {
        this.mStack[sp - 1] = this.mStack[sp - 1] + this.mStack[sp];
        return sp - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$1(int sp) {
        this.mStack[sp - 1] = this.mStack[sp - 1] - this.mStack[sp];
        return sp - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$2(int sp) {
        this.mStack[sp - 1] = this.mStack[sp - 1] * this.mStack[sp];
        return sp - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$3(int sp) {
        this.mStack[sp - 1] = this.mStack[sp - 1] / this.mStack[sp];
        return sp - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$4(int sp) {
        this.mStack[sp - 1] = this.mStack[sp - 1] % this.mStack[sp];
        return sp - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$5(int sp) {
        this.mStack[sp - 1] = Math.min(this.mStack[sp - 1], this.mStack[sp]);
        return sp - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$6(int sp) {
        this.mStack[sp - 1] = Math.max(this.mStack[sp - 1], this.mStack[sp]);
        return sp - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$7(int sp) {
        this.mStack[sp - 1] = (float) Math.pow(this.mStack[sp - 1], this.mStack[sp]);
        return sp - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$8(int sp) {
        this.mStack[sp] = (float) Math.sqrt(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$9(int sp) {
        this.mStack[sp] = Math.abs(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$10(int sp) {
        this.mStack[sp] = Math.signum(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$11(int sp) {
        this.mStack[sp - 1] = Math.copySign(this.mStack[sp - 1], this.mStack[sp]);
        return sp - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$12(int sp) {
        this.mStack[sp] = (float) Math.exp(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$13(int sp) {
        this.mStack[sp] = (float) Math.floor(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$14(int sp) {
        this.mStack[sp] = (float) Math.log10(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$15(int sp) {
        this.mStack[sp] = (float) Math.log(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$16(int sp) {
        this.mStack[sp] = Math.round(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$17(int sp) {
        this.mStack[sp] = (float) Math.sin(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$18(int sp) {
        this.mStack[sp] = (float) Math.cos(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$19(int sp) {
        this.mStack[sp] = (float) Math.tan(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$20(int sp) {
        this.mStack[sp] = (float) Math.asin(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$21(int sp) {
        this.mStack[sp] = (float) Math.acos(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$22(int sp) {
        this.mStack[sp] = (float) Math.atan(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$23(int sp) {
        this.mStack[sp - 1] = (float) Math.atan2(this.mStack[sp - 1], this.mStack[sp]);
        return sp - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$24(int sp) {
        this.mStack[sp - 2] = this.mStack[sp] + (this.mStack[sp - 1] * this.mStack[sp - 2]);
        return sp - 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$25(int sp) {
        this.mStack[sp - 2] = this.mStack[sp] > 0.0f ? this.mStack[sp - 1] : this.mStack[sp - 2];
        return sp - 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$26(int sp) {
        this.mStack[sp - 2] = Math.min(Math.max(this.mStack[sp - 2], this.mStack[sp]), this.mStack[sp - 1]);
        return sp - 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$27(int sp) {
        this.mStack[sp] = (float) Math.pow(this.mStack[sp], 0.3333333333333333d);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$28(int sp) {
        this.mStack[sp] = this.mStack[sp] * 57.29578f;
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$29(int sp) {
        this.mStack[sp] = this.mStack[sp] * FP_TO_DEG;
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$30(int sp) {
        this.mStack[sp] = (float) Math.ceil(this.mStack[sp]);
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$31(int sp) {
        this.mStack[sp] = this.mVar[0];
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$32(int sp) {
        this.mStack[sp] = this.mVar[1];
        return sp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$33(int sp) {
        this.mStack[sp] = this.mVar[2];
        return sp;
    }

    public static String toMathName(float f) {
        int id = fromNaN(f) - 256;
        return sNames.get(id);
    }

    public static String toString(float[] exp, String[] labels) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < exp.length; i++) {
            float v = exp[i];
            if (Float.isNaN(v)) {
                if (isMathOperator(v)) {
                    s.append(toMathName(v));
                } else {
                    s.append(NavigationBarInflaterView.SIZE_MOD_START);
                    s.append(fromNaN(v));
                    s.append(NavigationBarInflaterView.SIZE_MOD_END);
                }
            } else {
                if (labels[i] != null) {
                    s.append(labels[i]);
                }
                s.append(v);
            }
            s.append(" ");
        }
        return s.toString();
    }

    static String toString(float[] exp, int sp) {
        String[] strArr = new String[exp.length];
        if (Float.isNaN(exp[sp])) {
            int id = fromNaN(exp[sp]) - 256;
            switch (NO_OF_OPS[id]) {
                case -1:
                    return "nop";
                case 1:
                    return sNames.get(id) + NavigationBarInflaterView.KEY_CODE_START + toString(exp, sp + 1) + ") ";
                case 2:
                    if (infix(id)) {
                        return NavigationBarInflaterView.KEY_CODE_START + toString(exp, sp + 1) + sNames.get(id) + " " + toString(exp, sp + 2) + ") ";
                    }
                    return sNames.get(id) + NavigationBarInflaterView.KEY_CODE_START + toString(exp, sp + 1) + ", " + toString(exp, sp + 2) + NavigationBarInflaterView.KEY_CODE_END;
                case 3:
                    if (infix(id)) {
                        return "((" + toString(exp, sp + 1) + ") ? " + toString(exp, sp + 2) + ":" + toString(exp, sp + 3) + NavigationBarInflaterView.KEY_CODE_END;
                    }
                    return sNames.get(id) + NavigationBarInflaterView.KEY_CODE_START + toString(exp, sp + 1) + ", " + toString(exp, sp + 2) + ", " + toString(exp, sp + 3) + NavigationBarInflaterView.KEY_CODE_END;
            }
        }
        return Float.toString(exp[sp]);
    }

    static boolean infix(int n) {
        return n < 6 || n == 25 || n == 26;
    }

    public static float asNan(int v) {
        return Float.intBitsToFloat((-8388608) | v);
    }

    public static int fromNaN(float v) {
        int b = Float.floatToRawIntBits(v);
        return 1048575 & b;
    }
}

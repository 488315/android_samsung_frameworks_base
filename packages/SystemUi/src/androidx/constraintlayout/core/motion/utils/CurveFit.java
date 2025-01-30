package androidx.constraintlayout.core.motion.utils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class CurveFit {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Constant extends CurveFit {
        public final double mTime;
        public final double[] mValue;

        public Constant(double d, double[] dArr) {
            this.mTime = d;
            this.mValue = dArr;
        }

        @Override // androidx.constraintlayout.core.motion.utils.CurveFit
        public final void getPos(double d, double[] dArr) {
            double[] dArr2 = this.mValue;
            System.arraycopy(dArr2, 0, dArr, 0, dArr2.length);
        }

        @Override // androidx.constraintlayout.core.motion.utils.CurveFit
        public final double getSlope(double d) {
            return 0.0d;
        }

        @Override // androidx.constraintlayout.core.motion.utils.CurveFit
        public final double[] getTimePoints() {
            return new double[]{this.mTime};
        }

        @Override // androidx.constraintlayout.core.motion.utils.CurveFit
        public final void getPos(double d, float[] fArr) {
            int i = 0;
            while (true) {
                double[] dArr = this.mValue;
                if (i >= dArr.length) {
                    return;
                }
                fArr[i] = (float) dArr[i];
                i++;
            }
        }

        @Override // androidx.constraintlayout.core.motion.utils.CurveFit
        public final void getSlope(double d, double[] dArr) {
            for (int i = 0; i < this.mValue.length; i++) {
                dArr[i] = 0.0d;
            }
        }

        @Override // androidx.constraintlayout.core.motion.utils.CurveFit
        public final double getPos(double d) {
            return this.mValue[0];
        }
    }

    public static CurveFit get(int i, double[] dArr, double[][] dArr2) {
        if (dArr.length == 1) {
            i = 2;
        }
        return i != 0 ? i != 2 ? new LinearCurveFit(dArr, dArr2) : new Constant(dArr[0], dArr2[0]) : new MonotonicCurveFit(dArr, dArr2);
    }

    public abstract double getPos(double d);

    public abstract void getPos(double d, double[] dArr);

    public abstract void getPos(double d, float[] fArr);

    public abstract double getSlope(double d);

    public abstract void getSlope(double d, double[] dArr);

    public abstract double[] getTimePoints();
}

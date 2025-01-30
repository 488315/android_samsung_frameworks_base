package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class Easing {
    public String str = "identity";
    public static final Easing sDefault = new Easing();
    public static final String[] NAMED_EASING = {"standard", "accelerate", "decelerate", "linear"};

    public static Easing getInterpolator(String str) {
        if (str == null) {
            return null;
        }
        if (str.startsWith("cubic")) {
            return new CubicEasing(str);
        }
        if (str.startsWith("spline")) {
            return new StepCurve(str);
        }
        if (str.startsWith("Schlick")) {
            return new Schlick(str);
        }
        switch (str) {
            case "accelerate":
                return new CubicEasing("cubic(0.4, 0.05, 0.8, 0.7)");
            case "decelerate":
                return new CubicEasing("cubic(0.0, 0.0, 0.2, 0.95)");
            case "anticipate":
                return new CubicEasing("cubic(0.36, 0, 0.66, -0.56)");
            case "linear":
                return new CubicEasing("cubic(1, 1, 0, 0)");
            case "overshoot":
                return new CubicEasing("cubic(0.34, 1.56, 0.64, 1)");
            case "standard":
                return new CubicEasing("cubic(0.4, 0.0, 0.2, 1)");
            default:
                System.err.println("transitionEasing syntax error syntax:transitionEasing=\"cubic(1.0,0.5,0.0,0.6)\" or " + Arrays.toString(NAMED_EASING));
                return sDefault;
        }
    }

    public double getDiff(double d) {
        return 1.0d;
    }

    public final String toString() {
        return this.str;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CubicEasing extends Easing {

        /* renamed from: x1 */
        public double f3x1;

        /* renamed from: x2 */
        public double f4x2;

        /* renamed from: y1 */
        public double f5y1;

        /* renamed from: y2 */
        public double f6y2;

        public CubicEasing(String str) {
            this.str = str;
            int indexOf = str.indexOf(40);
            int indexOf2 = str.indexOf(44, indexOf);
            this.f3x1 = Double.parseDouble(str.substring(indexOf + 1, indexOf2).trim());
            int i = indexOf2 + 1;
            int indexOf3 = str.indexOf(44, i);
            this.f5y1 = Double.parseDouble(str.substring(i, indexOf3).trim());
            int i2 = indexOf3 + 1;
            int indexOf4 = str.indexOf(44, i2);
            this.f4x2 = Double.parseDouble(str.substring(i2, indexOf4).trim());
            int i3 = indexOf4 + 1;
            this.f6y2 = Double.parseDouble(str.substring(i3, str.indexOf(41, i3)).trim());
        }

        @Override // androidx.constraintlayout.core.motion.utils.Easing
        public final double get(double d) {
            if (d <= 0.0d) {
                return 0.0d;
            }
            if (d >= 1.0d) {
                return 1.0d;
            }
            double d2 = 0.5d;
            double d3 = 0.5d;
            while (d2 > 0.01d) {
                d2 *= 0.5d;
                d3 = getX(d3) < d ? d3 + d2 : d3 - d2;
            }
            double d4 = d3 - d2;
            double x = getX(d4);
            double d5 = d3 + d2;
            double x2 = getX(d5);
            double y = getY(d4);
            return (((d - x) * (getY(d5) - y)) / (x2 - x)) + y;
        }

        @Override // androidx.constraintlayout.core.motion.utils.Easing
        public final double getDiff(double d) {
            double d2 = 0.5d;
            double d3 = 0.5d;
            while (d2 > 1.0E-4d) {
                d2 *= 0.5d;
                d3 = getX(d3) < d ? d3 + d2 : d3 - d2;
            }
            double d4 = d3 - d2;
            double d5 = d3 + d2;
            return (getY(d5) - getY(d4)) / (getX(d5) - getX(d4));
        }

        public final double getX(double d) {
            double d2 = 1.0d - d;
            double d3 = 3.0d * d2;
            double d4 = d2 * d3 * d;
            double d5 = d3 * d * d;
            return (this.f4x2 * d5) + (this.f3x1 * d4) + (d * d * d);
        }

        public final double getY(double d) {
            double d2 = 1.0d - d;
            double d3 = 3.0d * d2;
            double d4 = d2 * d3 * d;
            double d5 = d3 * d * d;
            return (this.f6y2 * d5) + (this.f5y1 * d4) + (d * d * d);
        }

        public CubicEasing(double d, double d2, double d3, double d4) {
            this.f3x1 = d;
            this.f5y1 = d2;
            this.f4x2 = d3;
            this.f6y2 = d4;
        }
    }

    public double get(double d) {
        return d;
    }
}

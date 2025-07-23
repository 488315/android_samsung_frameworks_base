package androidx.constraintlayout.core.motion.utils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Schlick extends Easing {
    public final double mS;
    public final double mT;

    public Schlick(String str) {
        this.mStr = str;
        int indexOf = str.indexOf(40);
        int indexOf2 = str.indexOf(44, indexOf);
        this.mS = Double.parseDouble(str.substring(indexOf + 1, indexOf2).trim());
        int i = indexOf2 + 1;
        this.mT = Double.parseDouble(str.substring(i, str.indexOf(44, i)).trim());
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public final double get(double d) {
        double d2 = this.mT;
        double d3 = this.mS;
        if (d < d2) {
            return (d2 * d) / (((d2 - d) * d3) + d);
        }
        return ((d - 1.0d) * (1.0d - d2)) / ((1.0d - d) - ((d2 - d) * d3));
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public final double getDiff(double d) {
        double d2 = this.mT;
        double d3 = this.mS;
        if (d < d2) {
            double d4 = d3 * d2 * d2;
            double d5 = ((d2 - d) * d3) + d;
            return d4 / (d5 * d5);
        }
        double d6 = d2 - 1.0d;
        double d7 = (((d2 - d) * (-d3)) - d) + 1.0d;
        return ((d6 * d3) * d6) / (d7 * d7);
    }
}

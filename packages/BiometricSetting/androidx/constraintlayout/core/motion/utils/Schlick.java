package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public final class Schlick extends Easing {

    /* renamed from: mS */
    double f6mS;

    /* renamed from: mT */
    double f7mT;

    Schlick(String str) {
        this.mStr = str;
        int indexOf = str.indexOf(40);
        int indexOf2 = str.indexOf(44, indexOf);
        this.f6mS = Double.parseDouble(str.substring(indexOf + 1, indexOf2).trim());
        int i = indexOf2 + 1;
        this.f7mT = Double.parseDouble(str.substring(i, str.indexOf(44, i)).trim());
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public final double get(double d) {
        double d2 = this.f7mT;
        if (d < d2) {
            return (d2 * d) / (((d2 - d) * this.f6mS) + d);
        }
        return ((d - 1.0d) * (1.0d - d2)) / ((1.0d - d) - ((d2 - d) * this.f6mS));
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public final double getDiff(double d) {
        double d2 = this.f7mT;
        if (d < d2) {
            double d3 = this.f6mS;
            double d4 = d3 * d2 * d2;
            double d5 = ((d2 - d) * d3) + d;
            return d4 / (d5 * d5);
        }
        double d6 = this.f6mS;
        double d7 = d2 - 1.0d;
        double d8 = (((d2 - d) * (-d6)) - d) + 1.0d;
        return ((d7 * d6) * d7) / (d8 * d8);
    }
}

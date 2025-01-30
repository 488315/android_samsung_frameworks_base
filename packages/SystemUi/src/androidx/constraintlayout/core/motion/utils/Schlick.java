package androidx.constraintlayout.core.motion.utils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Schlick extends Easing {

    /* renamed from: mS */
    public final double f11mS;

    /* renamed from: mT */
    public final double f12mT;

    public Schlick(String str) {
        this.str = str;
        int indexOf = str.indexOf(40);
        int indexOf2 = str.indexOf(44, indexOf);
        this.f11mS = Double.parseDouble(str.substring(indexOf + 1, indexOf2).trim());
        int i = indexOf2 + 1;
        this.f12mT = Double.parseDouble(str.substring(i, str.indexOf(44, i)).trim());
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public final double get(double d) {
        double d2 = this.f12mT;
        double d3 = this.f11mS;
        if (d < d2) {
            return (d2 * d) / (((d2 - d) * d3) + d);
        }
        return ((d - 1.0d) * (1.0d - d2)) / ((1.0d - d) - ((d2 - d) * d3));
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public final double getDiff(double d) {
        double d2 = this.f12mT;
        double d3 = this.f11mS;
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

package androidx.compose.foundation.layout;

import androidx.compose.ui.unit.Constraints;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AspectRatioKt {
    /* renamed from: isSatisfiedBy-NN6Ew-U, reason: not valid java name */
    public static final boolean m95isSatisfiedByNN6EwU(int i, int i2, long j) {
        int m823getMinWidthimpl = Constraints.m823getMinWidthimpl(j);
        if (i > Constraints.m821getMaxWidthimpl(j) || m823getMinWidthimpl > i) {
            return false;
        }
        return i2 <= Constraints.m820getMaxHeightimpl(j) && Constraints.m822getMinHeightimpl(j) <= i2;
    }
}

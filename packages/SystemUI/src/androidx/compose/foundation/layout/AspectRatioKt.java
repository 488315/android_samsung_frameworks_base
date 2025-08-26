package androidx.compose.foundation.layout;

import androidx.compose.ui.unit.Constraints;

/* loaded from: classes.dex */
public abstract class AspectRatioKt {
    /* renamed from: isSatisfiedBy-NN6Ew-U, reason: not valid java name */
    public static final boolean m96isSatisfiedByNN6EwU(int i, int i2, long j) {
        int iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j);
        if (i > Constraints.m823getMaxWidthimpl(j) || iM825getMinWidthimpl > i) {
            return false;
        }
        return i2 <= Constraints.m822getMaxHeightimpl(j) && Constraints.m824getMinHeightimpl(j) <= i2;
    }
}

package androidx.compose.foundation.layout;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.internal.InlineClassHelperKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
final class PaddingElement extends ModifierNodeElement<PaddingNode> {
    public final float bottom;
    public final float end;
    public final boolean rtlAware;
    public final float start;
    public final float top;

    public /* synthetic */ PaddingElement(float f, float f2, float f3, float f4, boolean z, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, z, function1);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new PaddingNode(this.start, this.top, this.end, this.bottom, this.rtlAware, null);
    }

    public final boolean equals(Object obj) {
        PaddingElement paddingElement = obj instanceof PaddingElement ? (PaddingElement) obj : null;
        return paddingElement != null && Dp.m838equalsimpl0(this.start, paddingElement.start) && Dp.m838equalsimpl0(this.top, paddingElement.top) && Dp.m838equalsimpl0(this.end, paddingElement.end) && Dp.m838equalsimpl0(this.bottom, paddingElement.bottom) && this.rtlAware == paddingElement.rtlAware;
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return Boolean.hashCode(this.rtlAware) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.bottom, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.end, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.top, Float.hashCode(this.start) * 31, 31), 31), 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        PaddingNode paddingNode = (PaddingNode) node;
        paddingNode.start = this.start;
        paddingNode.top = this.top;
        paddingNode.end = this.end;
        paddingNode.bottom = this.bottom;
        paddingNode.rtlAware = this.rtlAware;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private PaddingElement(float f, float f2, float f3, float f4, boolean z, Function1 function1) {
        boolean z2;
        this.start = f;
        this.top = f2;
        this.end = f3;
        this.bottom = f4;
        this.rtlAware = z;
        if (f < 0.0f) {
            Dp.Companion.getClass();
            if (Dp.m838equalsimpl0(f, Dp.Unspecified)) {
                if (f2 < 0.0f) {
                    Dp.Companion.getClass();
                    if (Dp.m838equalsimpl0(f2, Dp.Unspecified)) {
                        if (f3 < 0.0f) {
                            Dp.Companion.getClass();
                            if (Dp.m838equalsimpl0(f3, Dp.Unspecified)) {
                                if (f4 < 0.0f) {
                                    Dp.Companion.getClass();
                                    if (!Dp.m838equalsimpl0(f4, Dp.Unspecified)) {
                                        z2 = false;
                                    }
                                }
                                z2 = true;
                            }
                        }
                    }
                }
            }
        }
        if (z2) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("Padding must be non-negative");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public PaddingElement(float f, float f2, float f3, float f4, boolean z, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            f = 0;
            Dp.Companion companion = Dp.Companion;
        }
        float f5 = f;
        if ((i & 2) != 0) {
            f2 = 0;
            Dp.Companion companion2 = Dp.Companion;
        }
        float f6 = f2;
        if ((i & 4) != 0) {
            f3 = 0;
            Dp.Companion companion3 = Dp.Companion;
        }
        float f7 = f3;
        if ((i & 8) != 0) {
            f4 = 0;
            Dp.Companion companion4 = Dp.Companion;
        }
        this(f5, f6, f7, f4, z, function1, null);
    }
}

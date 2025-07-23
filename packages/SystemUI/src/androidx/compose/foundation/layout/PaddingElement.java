package androidx.compose.foundation.layout;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return paddingElement != null && Dp.m836equalsimpl0(this.start, paddingElement.start) && Dp.m836equalsimpl0(this.top, paddingElement.top) && Dp.m836equalsimpl0(this.end, paddingElement.end) && Dp.m836equalsimpl0(this.bottom, paddingElement.bottom) && this.rtlAware == paddingElement.rtlAware;
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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002e, code lost:
    
        if (androidx.compose.ui.unit.Dp.m836equalsimpl0(r2, androidx.compose.ui.unit.Dp.Unspecified) != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003f, code lost:
    
        if (androidx.compose.ui.unit.Dp.m836equalsimpl0(r3, androidx.compose.ui.unit.Dp.Unspecified) != false) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0050, code lost:
    
        if (androidx.compose.ui.unit.Dp.m836equalsimpl0(r4, androidx.compose.ui.unit.Dp.Unspecified) != false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x001d, code lost:
    
        if (androidx.compose.ui.unit.Dp.m836equalsimpl0(r1, androidx.compose.ui.unit.Dp.Unspecified) != false) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0053, code lost:
    
        r0 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private PaddingElement(float r1, float r2, float r3, float r4, boolean r5, kotlin.jvm.functions.Function1 r6) {
        /*
            r0 = this;
            r0.<init>()
            r0.start = r1
            r0.top = r2
            r0.end = r3
            r0.bottom = r4
            r0.rtlAware = r5
            r0 = 0
            int r5 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r5 >= 0) goto L1f
            androidx.compose.ui.unit.Dp$Companion r5 = androidx.compose.ui.unit.Dp.Companion
            r5.getClass()
            float r5 = androidx.compose.ui.unit.Dp.Unspecified
            boolean r1 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r1, r5)
            if (r1 == 0) goto L53
        L1f:
            int r1 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r1 >= 0) goto L30
            androidx.compose.ui.unit.Dp$Companion r1 = androidx.compose.ui.unit.Dp.Companion
            r1.getClass()
            float r1 = androidx.compose.ui.unit.Dp.Unspecified
            boolean r1 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r2, r1)
            if (r1 == 0) goto L53
        L30:
            int r1 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r1 >= 0) goto L41
            androidx.compose.ui.unit.Dp$Companion r1 = androidx.compose.ui.unit.Dp.Companion
            r1.getClass()
            float r1 = androidx.compose.ui.unit.Dp.Unspecified
            boolean r1 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r3, r1)
            if (r1 == 0) goto L53
        L41:
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 >= 0) goto L55
            androidx.compose.ui.unit.Dp$Companion r0 = androidx.compose.ui.unit.Dp.Companion
            r0.getClass()
            float r0 = androidx.compose.ui.unit.Dp.Unspecified
            boolean r0 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r4, r0)
            if (r0 == 0) goto L53
            goto L55
        L53:
            r0 = 0
            goto L56
        L55:
            r0 = 1
        L56:
            if (r0 != 0) goto L5d
            java.lang.String r0 = "Padding must be non-negative"
            androidx.compose.foundation.layout.internal.InlineClassHelperKt.throwIllegalArgumentException(r0)
        L5d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.PaddingElement.<init>(float, float, float, float, boolean, kotlin.jvm.functions.Function1):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PaddingElement(float r11, float r12, float r13, float r14, boolean r15, kotlin.jvm.functions.Function1 r16, int r17, kotlin.jvm.internal.DefaultConstructorMarker r18) {
        /*
            r10 = this;
            r0 = r17 & 1
            r1 = 0
            if (r0 == 0) goto L8
            float r11 = (float) r1
            androidx.compose.ui.unit.Dp$Companion r0 = androidx.compose.ui.unit.Dp.Companion
        L8:
            r3 = r11
            r11 = r17 & 2
            if (r11 == 0) goto L10
            float r12 = (float) r1
            androidx.compose.ui.unit.Dp$Companion r11 = androidx.compose.ui.unit.Dp.Companion
        L10:
            r4 = r12
            r11 = r17 & 4
            if (r11 == 0) goto L18
            float r13 = (float) r1
            androidx.compose.ui.unit.Dp$Companion r11 = androidx.compose.ui.unit.Dp.Companion
        L18:
            r5 = r13
            r11 = r17 & 8
            if (r11 == 0) goto L20
            float r14 = (float) r1
            androidx.compose.ui.unit.Dp$Companion r11 = androidx.compose.ui.unit.Dp.Companion
        L20:
            r6 = r14
            r9 = 0
            r2 = r10
            r7 = r15
            r8 = r16
            r2.<init>(r3, r4, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.PaddingElement.<init>(float, float, float, float, boolean, kotlin.jvm.functions.Function1, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}

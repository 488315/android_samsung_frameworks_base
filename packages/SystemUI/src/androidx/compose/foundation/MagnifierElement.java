package androidx.compose.foundation;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MagnifierElement extends ModifierNodeElement<MagnifierNode> {
    public final boolean clippingEnabled;
    public final float cornerRadius;
    public final float elevation;
    public final Function1 magnifierCenter;
    public final Function1 onSizeChanged;
    public final PlatformMagnifierFactory platformMagnifierFactory;
    public final long size;
    public final Function1 sourceCenter;
    public final boolean useTextDefault;
    public final float zoom;

    public /* synthetic */ MagnifierElement(Function1 function1, Function1 function12, Function1 function13, float f, boolean z, long j, float f2, float f3, boolean z2, PlatformMagnifierFactory platformMagnifierFactory, DefaultConstructorMarker defaultConstructorMarker) {
        this(function1, function12, function13, f, z, j, f2, f3, z2, platformMagnifierFactory);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new MagnifierNode(this.sourceCenter, this.magnifierCenter, this.onSizeChanged, this.zoom, this.useTextDefault, this.size, this.cornerRadius, this.elevation, this.clippingEnabled, this.platformMagnifierFactory, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MagnifierElement) {
            MagnifierElement magnifierElement = (MagnifierElement) obj;
            if (this.sourceCenter == magnifierElement.sourceCenter && this.magnifierCenter == magnifierElement.magnifierCenter && this.zoom == magnifierElement.zoom && this.useTextDefault == magnifierElement.useTextDefault) {
                DpSize.Companion companion = DpSize.Companion;
                if (this.size == magnifierElement.size && Dp.m836equalsimpl0(this.cornerRadius, magnifierElement.cornerRadius) && Dp.m836equalsimpl0(this.elevation, magnifierElement.elevation) && this.clippingEnabled == magnifierElement.clippingEnabled && this.onSizeChanged == magnifierElement.onSizeChanged && Intrinsics.areEqual(this.platformMagnifierFactory, magnifierElement.platformMagnifierFactory)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = this.sourceCenter.hashCode() * 31;
        Function1 function1 = this.magnifierCenter;
        int m = TransitionData$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.zoom, (hashCode + (function1 != null ? function1.hashCode() : 0)) * 31, 31), 31, this.useTextDefault);
        DpSize.Companion companion = DpSize.Companion;
        int m2 = MoveResult$$ExternalSyntheticOutline0.m(m, 31, this.size);
        Dp.Companion companion2 = Dp.Companion;
        int m3 = TransitionData$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.elevation, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.cornerRadius, m2, 31), 31), 31, this.clippingEnabled);
        Function1 function12 = this.onSizeChanged;
        return this.platformMagnifierFactory.hashCode() + ((m3 + (function12 != null ? function12.hashCode() : 0)) * 31);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0096, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r14, r11) != false) goto L29;
     */
    @Override // androidx.compose.ui.node.ModifierNodeElement
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update(androidx.compose.ui.Modifier.Node r22) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            androidx.compose.foundation.MagnifierNode r1 = (androidx.compose.foundation.MagnifierNode) r1
            float r2 = r1.zoom
            long r3 = r1.size
            float r5 = r1.cornerRadius
            boolean r6 = r1.useTextDefault
            float r7 = r1.elevation
            boolean r8 = r1.clippingEnabled
            androidx.compose.foundation.PlatformMagnifierFactory r9 = r1.platformMagnifierFactory
            android.view.View r10 = r1.view
            androidx.compose.ui.unit.Density r11 = r1.density
            kotlin.jvm.functions.Function1 r12 = r0.sourceCenter
            r1.sourceCenter = r12
            kotlin.jvm.functions.Function1 r12 = r0.magnifierCenter
            r1.magnifierCenter = r12
            float r12 = r0.zoom
            r1.zoom = r12
            boolean r13 = r0.useTextDefault
            r1.useTextDefault = r13
            long r14 = r0.size
            r1.size = r14
            r22 = r2
            float r2 = r0.cornerRadius
            r1.cornerRadius = r2
            r16 = r3
            float r3 = r0.elevation
            r1.elevation = r3
            boolean r4 = r0.clippingEnabled
            r1.clippingEnabled = r4
            r18 = r12
            kotlin.jvm.functions.Function1 r12 = r0.onSizeChanged
            r1.onSizeChanged = r12
            androidx.compose.foundation.PlatformMagnifierFactory r0 = r0.platformMagnifierFactory
            r1.platformMagnifierFactory = r0
            android.view.View r12 = androidx.compose.ui.node.DelegatableNode_androidKt.requireView(r1)
            r19 = r14
            androidx.compose.ui.node.LayoutNode r14 = androidx.compose.ui.node.DelegatableNodeKt.requireLayoutNode(r1)
            androidx.compose.ui.unit.Density r14 = r14.density
            androidx.compose.foundation.PlatformMagnifier r15 = r1.magnifier
            if (r15 == 0) goto L9b
            androidx.compose.ui.semantics.SemanticsPropertyKey r15 = androidx.compose.foundation.Magnifier_androidKt.MagnifierPositionInRoot
            boolean r15 = java.lang.Float.isNaN(r18)
            if (r15 == 0) goto L65
            boolean r15 = java.lang.Float.isNaN(r22)
            if (r15 == 0) goto L65
            goto L70
        L65:
            int r15 = (r18 > r22 ? 1 : (r18 == r22 ? 0 : -1))
            if (r15 != 0) goto L6a
            goto L70
        L6a:
            boolean r15 = r0.getCanUpdateZoom()
            if (r15 == 0) goto L98
        L70:
            androidx.compose.ui.unit.DpSize$Companion r15 = androidx.compose.ui.unit.DpSize.Companion
            int r15 = (r19 > r16 ? 1 : (r19 == r16 ? 0 : -1))
            if (r15 != 0) goto L98
            boolean r2 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r2, r5)
            if (r2 == 0) goto L98
            boolean r2 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r3, r7)
            if (r2 == 0) goto L98
            if (r13 != r6) goto L98
            if (r4 != r8) goto L98
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r9)
            if (r0 == 0) goto L98
            boolean r0 = r12.equals(r10)
            if (r0 == 0) goto L98
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r14, r11)
            if (r0 != 0) goto L9b
        L98:
            r1.recreateMagnifier()
        L9b:
            r1.updateMagnifier()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.MagnifierElement.update(androidx.compose.ui.Modifier$Node):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MagnifierElement(kotlin.jvm.functions.Function1 r2, kotlin.jvm.functions.Function1 r3, kotlin.jvm.functions.Function1 r4, float r5, boolean r6, long r7, float r9, float r10, boolean r11, androidx.compose.foundation.PlatformMagnifierFactory r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r1 = this;
            r14 = r13 & 2
            r0 = 0
            if (r14 == 0) goto L6
            r3 = r0
        L6:
            r14 = r13 & 4
            if (r14 == 0) goto Lb
            r4 = r0
        Lb:
            r14 = r13 & 8
            if (r14 == 0) goto L11
            r5 = 2143289344(0x7fc00000, float:NaN)
        L11:
            r14 = r13 & 16
            if (r14 == 0) goto L16
            r6 = 0
        L16:
            r14 = r13 & 32
            if (r14 == 0) goto L21
            androidx.compose.ui.unit.DpSize$Companion r7 = androidx.compose.ui.unit.DpSize.Companion
            r7.getClass()
            long r7 = androidx.compose.ui.unit.DpSize.Unspecified
        L21:
            r14 = r13 & 64
            if (r14 == 0) goto L2c
            androidx.compose.ui.unit.Dp$Companion r9 = androidx.compose.ui.unit.Dp.Companion
            r9.getClass()
            float r9 = androidx.compose.ui.unit.Dp.Unspecified
        L2c:
            r14 = r13 & 128(0x80, float:1.8E-43)
            if (r14 == 0) goto L37
            androidx.compose.ui.unit.Dp$Companion r10 = androidx.compose.ui.unit.Dp.Companion
            r10.getClass()
            float r10 = androidx.compose.ui.unit.Dp.Unspecified
        L37:
            r13 = r13 & 256(0x100, float:3.59E-43)
            if (r13 == 0) goto L3c
            r11 = 1
        L3c:
            r13 = 0
            r1.<init>(r2, r3, r4, r5, r6, r7, r9, r10, r11, r12, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.MagnifierElement.<init>(kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, float, boolean, long, float, float, boolean, androidx.compose.foundation.PlatformMagnifierFactory, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private MagnifierElement(Function1 function1, Function1 function12, Function1 function13, float f, boolean z, long j, float f2, float f3, boolean z2, PlatformMagnifierFactory platformMagnifierFactory) {
        this.sourceCenter = function1;
        this.magnifierCenter = function12;
        this.onSizeChanged = function13;
        this.zoom = f;
        this.useTextDefault = z;
        this.size = j;
        this.cornerRadius = f2;
        this.elevation = f3;
        this.clippingEnabled = z2;
        this.platformMagnifierFactory = platformMagnifierFactory;
    }
}

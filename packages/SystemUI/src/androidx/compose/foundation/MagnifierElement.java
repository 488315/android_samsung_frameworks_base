package androidx.compose.foundation;

import android.view.View;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatableNode_androidKt;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
                if (this.size == magnifierElement.size && Dp.m838equalsimpl0(this.cornerRadius, magnifierElement.cornerRadius) && Dp.m838equalsimpl0(this.elevation, magnifierElement.elevation) && this.clippingEnabled == magnifierElement.clippingEnabled && this.onSizeChanged == magnifierElement.onSizeChanged && Intrinsics.areEqual(this.platformMagnifierFactory, magnifierElement.platformMagnifierFactory)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = this.sourceCenter.hashCode() * 31;
        Function1 function1 = this.magnifierCenter;
        int iM = TransitionData$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.zoom, (iHashCode + (function1 != null ? function1.hashCode() : 0)) * 31, 31), 31, this.useTextDefault);
        DpSize.Companion companion = DpSize.Companion;
        int iM2 = MoveResult$$ExternalSyntheticOutline0.m(iM, 31, this.size);
        Dp.Companion companion2 = Dp.Companion;
        int iM3 = TransitionData$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.elevation, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.cornerRadius, iM2, 31), 31), 31, this.clippingEnabled);
        Function1 function12 = this.onSizeChanged;
        return this.platformMagnifierFactory.hashCode() + ((iM3 + (function12 != null ? function12.hashCode() : 0)) * 31);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0098  */
    @Override // androidx.compose.ui.node.ModifierNodeElement
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(Modifier.Node node) {
        MagnifierNode magnifierNode = (MagnifierNode) node;
        float f = magnifierNode.zoom;
        long j = magnifierNode.size;
        float f2 = magnifierNode.cornerRadius;
        boolean z = magnifierNode.useTextDefault;
        float f3 = magnifierNode.elevation;
        boolean z2 = magnifierNode.clippingEnabled;
        PlatformMagnifierFactory platformMagnifierFactory = magnifierNode.platformMagnifierFactory;
        View view = magnifierNode.view;
        Density density = magnifierNode.density;
        magnifierNode.sourceCenter = this.sourceCenter;
        magnifierNode.magnifierCenter = this.magnifierCenter;
        float f4 = this.zoom;
        magnifierNode.zoom = f4;
        boolean z3 = this.useTextDefault;
        magnifierNode.useTextDefault = z3;
        long j2 = this.size;
        magnifierNode.size = j2;
        float f5 = this.cornerRadius;
        magnifierNode.cornerRadius = f5;
        float f6 = this.elevation;
        magnifierNode.elevation = f6;
        boolean z4 = this.clippingEnabled;
        magnifierNode.clippingEnabled = z4;
        magnifierNode.onSizeChanged = this.onSizeChanged;
        PlatformMagnifierFactory platformMagnifierFactory2 = this.platformMagnifierFactory;
        magnifierNode.platformMagnifierFactory = platformMagnifierFactory2;
        View viewRequireView = DelegatableNode_androidKt.requireView(magnifierNode);
        Density density2 = DelegatableNodeKt.requireLayoutNode(magnifierNode).density;
        if (magnifierNode.magnifier != null) {
            SemanticsPropertyKey semanticsPropertyKey = Magnifier_androidKt.MagnifierPositionInRoot;
            if ((Float.isNaN(f4) && Float.isNaN(f)) || f4 == f || platformMagnifierFactory2.getCanUpdateZoom()) {
                DpSize.Companion companion = DpSize.Companion;
                if (j2 != j || !Dp.m838equalsimpl0(f5, f2) || !Dp.m838equalsimpl0(f6, f3) || z3 != z || z4 != z2 || !Intrinsics.areEqual(platformMagnifierFactory2, platformMagnifierFactory) || !viewRequireView.equals(view) || !Intrinsics.areEqual(density2, density)) {
                }
            } else {
                magnifierNode.recreateMagnifier();
            }
        }
        magnifierNode.updateMagnifier();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MagnifierElement(Function1 function1, Function1 function12, Function1 function13, float f, boolean z, long j, float f2, float f3, boolean z2, PlatformMagnifierFactory platformMagnifierFactory, int i, DefaultConstructorMarker defaultConstructorMarker) {
        function12 = (i & 2) != 0 ? null : function12;
        function13 = (i & 4) != 0 ? null : function13;
        f = (i & 8) != 0 ? Float.NaN : f;
        z = (i & 16) != 0 ? false : z;
        if ((i & 32) != 0) {
            DpSize.Companion.getClass();
            j = DpSize.Unspecified;
        }
        if ((i & 64) != 0) {
            Dp.Companion.getClass();
            f2 = Dp.Unspecified;
        }
        if ((i & 128) != 0) {
            Dp.Companion.getClass();
            f3 = Dp.Unspecified;
        }
        this(function1, function12, function13, f, z, j, f2, f3, (i & 256) != 0 ? true : z2, platformMagnifierFactory, null);
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

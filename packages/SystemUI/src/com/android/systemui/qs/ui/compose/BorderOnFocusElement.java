package com.android.systemui.qs.ui.compose;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class BorderOnFocusElement extends ModifierNodeElement<BorderOnFocusNode> {
    public final long color;
    public final CornerSize cornerSize;
    public final float padding;
    public final float strokeWidth;

    public /* synthetic */ BorderOnFocusElement(long j, CornerSize cornerSize, float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, cornerSize, f, f2);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new BorderOnFocusNode(this.color, this.cornerSize, this.strokeWidth, this.padding, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BorderOnFocusElement)) {
            return false;
        }
        BorderOnFocusElement borderOnFocusElement = (BorderOnFocusElement) obj;
        long j = borderOnFocusElement.color;
        Color.Companion companion = Color.Companion;
        return ULong.m3447equalsimpl0(this.color, j) && Intrinsics.areEqual(this.cornerSize, borderOnFocusElement.cornerSize) && Dp.m838equalsimpl0(this.strokeWidth, borderOnFocusElement.strokeWidth) && Dp.m838equalsimpl0(this.padding, borderOnFocusElement.padding);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        int iHashCode = (this.cornerSize.hashCode() + (Long.hashCode(this.color) * 31)) * 31;
        Dp.Companion companion2 = Dp.Companion;
        return Float.hashCode(this.padding) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.strokeWidth, iHashCode, 31);
    }

    public final String toString() {
        String strM464toStringimpl = Color.m464toStringimpl(this.color);
        String strM839toStringimpl = Dp.m839toStringimpl(this.strokeWidth);
        String strM839toStringimpl2 = Dp.m839toStringimpl(this.padding);
        StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("BorderOnFocusElement(color=", strM464toStringimpl, ", cornerSize=");
        sbM.append(this.cornerSize);
        sbM.append(", strokeWidth=");
        sbM.append(strM839toStringimpl);
        sbM.append(", padding=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sbM, strM839toStringimpl2, ")");
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        BorderOnFocusNode borderOnFocusNode = (BorderOnFocusNode) node;
        borderOnFocusNode.color = this.color;
        borderOnFocusNode.cornerSize = this.cornerSize;
        borderOnFocusNode.strokeWidth = this.strokeWidth;
        borderOnFocusNode.padding = this.padding;
    }

    private BorderOnFocusElement(long j, CornerSize cornerSize, float f, float f2) {
        this.color = j;
        this.cornerSize = cornerSize;
        this.strokeWidth = f;
        this.padding = f2;
    }
}

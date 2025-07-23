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
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return ULong.m3427equalsimpl0(this.color, j) && Intrinsics.areEqual(this.cornerSize, borderOnFocusElement.cornerSize) && Dp.m836equalsimpl0(this.strokeWidth, borderOnFocusElement.strokeWidth) && Dp.m836equalsimpl0(this.padding, borderOnFocusElement.padding);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        int hashCode = (this.cornerSize.hashCode() + (Long.hashCode(this.color) * 31)) * 31;
        Dp.Companion companion2 = Dp.Companion;
        return Float.hashCode(this.padding) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.strokeWidth, hashCode, 31);
    }

    public final String toString() {
        String m462toStringimpl = Color.m462toStringimpl(this.color);
        String m837toStringimpl = Dp.m837toStringimpl(this.strokeWidth);
        String m837toStringimpl2 = Dp.m837toStringimpl(this.padding);
        StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("BorderOnFocusElement(color=", m462toStringimpl, ", cornerSize=");
        m.append(this.cornerSize);
        m.append(", strokeWidth=");
        m.append(m837toStringimpl);
        m.append(", padding=");
        return TransitionKt$$ExternalSyntheticOutline0.m(m, m837toStringimpl2, ")");
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

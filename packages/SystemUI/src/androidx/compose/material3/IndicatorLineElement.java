package androidx.compose.material3;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNodeElement$$ExternalSyntheticOutline0;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class IndicatorLineElement extends ModifierNodeElement<IndicatorLineNode> {
    public final TextFieldColors colors;
    public final boolean enabled;
    public final float focusedIndicatorLineThickness;
    public final InteractionSource interactionSource;
    public final boolean isError;
    public final Shape textFieldShape;
    public final float unfocusedIndicatorLineThickness;

    public /* synthetic */ IndicatorLineElement(boolean z, boolean z2, InteractionSource interactionSource, TextFieldColors textFieldColors, Shape shape, float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, interactionSource, textFieldColors, shape, f, f2);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new IndicatorLineNode(this.enabled, this.isError, this.interactionSource, this.colors, this.textFieldShape, this.focusedIndicatorLineThickness, this.unfocusedIndicatorLineThickness, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IndicatorLineElement)) {
            return false;
        }
        IndicatorLineElement indicatorLineElement = (IndicatorLineElement) obj;
        return this.enabled == indicatorLineElement.enabled && this.isError == indicatorLineElement.isError && Intrinsics.areEqual(this.interactionSource, indicatorLineElement.interactionSource) && Intrinsics.areEqual(this.colors, indicatorLineElement.colors) && Intrinsics.areEqual(this.textFieldShape, indicatorLineElement.textFieldShape) && Dp.m836equalsimpl0(this.focusedIndicatorLineThickness, indicatorLineElement.focusedIndicatorLineThickness) && Dp.m836equalsimpl0(this.unfocusedIndicatorLineThickness, indicatorLineElement.unfocusedIndicatorLineThickness);
    }

    public final int hashCode() {
        int hashCode = (this.interactionSource.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.enabled) * 31, 31, this.isError)) * 31;
        TextFieldColors textFieldColors = this.colors;
        int hashCode2 = (hashCode + (textFieldColors == null ? 0 : textFieldColors.hashCode())) * 31;
        Shape shape = this.textFieldShape;
        int hashCode3 = (hashCode2 + (shape != null ? shape.hashCode() : 0)) * 31;
        Dp.Companion companion = Dp.Companion;
        return Float.hashCode(this.unfocusedIndicatorLineThickness) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.focusedIndicatorLineThickness, hashCode3, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("IndicatorLineElement(enabled=");
        sb.append(this.enabled);
        sb.append(", isError=");
        sb.append(this.isError);
        sb.append(", interactionSource=");
        sb.append(this.interactionSource);
        sb.append(", colors=");
        sb.append(this.colors);
        sb.append(", textFieldShape=");
        sb.append(this.textFieldShape);
        sb.append(", focusedIndicatorLineThickness=");
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.focusedIndicatorLineThickness, ", unfocusedIndicatorLineThickness=", sb);
        sb.append((Object) Dp.m837toStringimpl(this.unfocusedIndicatorLineThickness));
        sb.append(')');
        return sb.toString();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        boolean z;
        IndicatorLineNode indicatorLineNode = (IndicatorLineNode) node;
        boolean z2 = indicatorLineNode.enabled;
        boolean z3 = this.enabled;
        boolean z4 = true;
        if (z2 != z3) {
            indicatorLineNode.enabled = z3;
            z = true;
        } else {
            z = false;
        }
        boolean z5 = indicatorLineNode.isError;
        boolean z6 = this.isError;
        if (z5 != z6) {
            indicatorLineNode.isError = z6;
            z = true;
        }
        InteractionSource interactionSource = indicatorLineNode.interactionSource;
        InteractionSource interactionSource2 = this.interactionSource;
        if (interactionSource != interactionSource2) {
            indicatorLineNode.interactionSource = interactionSource2;
            StandaloneCoroutine standaloneCoroutine = indicatorLineNode.trackFocusStateJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            indicatorLineNode.trackFocusStateJob = BuildersKt.launch$default(indicatorLineNode.getCoroutineScope(), null, null, new IndicatorLineNode$update$1(indicatorLineNode, null), 3);
        }
        TextFieldColors textFieldColors = indicatorLineNode._colors;
        TextFieldColors textFieldColors2 = this.colors;
        if (!Intrinsics.areEqual(textFieldColors, textFieldColors2)) {
            indicatorLineNode._colors = textFieldColors2;
            z = true;
        }
        Shape shape = indicatorLineNode._shape;
        Shape shape2 = this.textFieldShape;
        if (!Intrinsics.areEqual(shape, shape2)) {
            if (!Intrinsics.areEqual(indicatorLineNode._shape, shape2)) {
                indicatorLineNode._shape = shape2;
                indicatorLineNode.drawWithCacheModifierNode.invalidateDrawCache();
            }
            z = true;
        }
        float f = indicatorLineNode.focusedIndicatorWidth;
        float f2 = this.focusedIndicatorLineThickness;
        if (!Dp.m836equalsimpl0(f, f2)) {
            indicatorLineNode.focusedIndicatorWidth = f2;
            z = true;
        }
        float f3 = indicatorLineNode.unfocusedIndicatorWidth;
        float f4 = this.unfocusedIndicatorLineThickness;
        if (Dp.m836equalsimpl0(f3, f4)) {
            z4 = z;
        } else {
            indicatorLineNode.unfocusedIndicatorWidth = f4;
        }
        if (z4) {
            indicatorLineNode.invalidateIndicator();
        }
    }

    private IndicatorLineElement(boolean z, boolean z2, InteractionSource interactionSource, TextFieldColors textFieldColors, Shape shape, float f, float f2) {
        this.enabled = z;
        this.isError = z2;
        this.interactionSource = interactionSource;
        this.colors = textFieldColors;
        this.textFieldShape = shape;
        this.focusedIndicatorLineThickness = f;
        this.unfocusedIndicatorLineThickness = f2;
    }
}

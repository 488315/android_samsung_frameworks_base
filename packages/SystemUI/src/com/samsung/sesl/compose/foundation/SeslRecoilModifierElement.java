package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.ModifierNodeElement;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
final class SeslRecoilModifierElement extends ModifierNodeElement<SeslRecoilNode> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SeslRecoilDrawStrategy drawStrategy;
    public final boolean enabled;
    public final SeslFeedbackAlpha feedbackAlpha;
    public final long feedbackColor;
    public final PaddingValues feedbackMargin;
    public final Shape feedbackShape;
    public final InteractionSource interactionSource;
    public final float scale;

    public /* synthetic */ SeslRecoilModifierElement(InteractionSource interactionSource, boolean z, float f, long j, Shape shape, PaddingValues paddingValues, SeslFeedbackAlpha seslFeedbackAlpha, SeslRecoilDrawStrategy seslRecoilDrawStrategy, DefaultConstructorMarker defaultConstructorMarker) {
        this(interactionSource, z, f, j, shape, paddingValues, seslFeedbackAlpha, seslRecoilDrawStrategy);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        ColorProducer colorProducer = new ColorProducer() { // from class: com.samsung.sesl.compose.foundation.SeslRecoilModifierElement.create.1
            @Override // androidx.compose.ui.graphics.ColorProducer
            /* renamed from: invoke-0d7_KjU */
            public final long mo262invoke0d7_KjU() {
                return SeslRecoilModifierElement.this.feedbackColor;
            }
        };
        SeslRecoilModifierElement$$ExternalSyntheticLambda0 seslRecoilModifierElement$$ExternalSyntheticLambda0 = new SeslRecoilModifierElement$$ExternalSyntheticLambda0(this, 1);
        return new SeslRecoilNode(this.interactionSource, this.enabled, this.scale, this.feedbackShape, colorProducer, this.feedbackMargin, seslRecoilModifierElement$$ExternalSyntheticLambda0, this.drawStrategy);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslRecoilModifierElement)) {
            return false;
        }
        SeslRecoilModifierElement seslRecoilModifierElement = (SeslRecoilModifierElement) obj;
        if (!Intrinsics.areEqual(this.interactionSource, seslRecoilModifierElement.interactionSource) || this.enabled != seslRecoilModifierElement.enabled || Float.compare(this.scale, seslRecoilModifierElement.scale) != 0) {
            return false;
        }
        Color.Companion companion = Color.Companion;
        return ULong.m3447equalsimpl0(this.feedbackColor, seslRecoilModifierElement.feedbackColor) && Intrinsics.areEqual(this.feedbackShape, seslRecoilModifierElement.feedbackShape) && Intrinsics.areEqual(this.feedbackMargin, seslRecoilModifierElement.feedbackMargin) && Intrinsics.areEqual(this.feedbackAlpha, seslRecoilModifierElement.feedbackAlpha) && Intrinsics.areEqual(this.drawStrategy, seslRecoilModifierElement.drawStrategy);
    }

    public final int hashCode() {
        int iM = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scale, TransitionData$$ExternalSyntheticOutline0.m(this.interactionSource.hashCode() * 31, 31, this.enabled), 31);
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        int iM2 = MoveResult$$ExternalSyntheticOutline0.m(iM, 31, this.feedbackColor);
        Shape shape = this.feedbackShape;
        return this.drawStrategy.hashCode() + ((this.feedbackAlpha.hashCode() + ((this.feedbackMargin.hashCode() + ((iM2 + (shape == null ? 0 : shape.hashCode())) * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "SeslRecoilModifierElement(interactionSource=" + this.interactionSource + ", enabled=" + this.enabled + ", scale=" + this.scale + ", feedbackColor=" + Color.m464toStringimpl(this.feedbackColor) + ", feedbackShape=" + this.feedbackShape + ", feedbackMargin=" + this.feedbackMargin + ", feedbackAlpha=" + this.feedbackAlpha + ", drawStrategy=" + this.drawStrategy + ")";
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        SeslRecoilNode seslRecoilNode = (SeslRecoilNode) node;
        seslRecoilNode.scale = this.scale;
        InteractionSource interactionSource = seslRecoilNode.interactionSource;
        InteractionSource interactionSource2 = this.interactionSource;
        if (!Intrinsics.areEqual(interactionSource, interactionSource2)) {
            seslRecoilNode.interactionSource = interactionSource2;
            seslRecoilNode.interactionAwareModifierNode.setInteractionSource(interactionSource2);
            SeslFeedbackNode seslFeedbackNode = seslRecoilNode.feedbackNode;
            if (seslFeedbackNode != null && !Intrinsics.areEqual(seslFeedbackNode.interactionSource, interactionSource2)) {
                seslFeedbackNode.interactionSource = interactionSource2;
                seslFeedbackNode.interactionAwareModifierNode.setInteractionSource(interactionSource2);
            }
        }
        seslRecoilNode.feedbackShape = this.feedbackShape;
        seslRecoilNode.feedbackColor = new ColorProducer() { // from class: com.samsung.sesl.compose.foundation.SeslRecoilModifierElement.update.1
            @Override // androidx.compose.ui.graphics.ColorProducer
            /* renamed from: invoke-0d7_KjU */
            public final long mo262invoke0d7_KjU() {
                return SeslRecoilModifierElement.this.feedbackColor;
            }
        };
        seslRecoilNode.feedbackMargin = this.feedbackMargin;
        seslRecoilNode.enabled = this.enabled;
        seslRecoilNode.feedbackAlpha = new SeslRecoilModifierElement$$ExternalSyntheticLambda0(this, 0);
        seslRecoilNode.drawStrategy = this.drawStrategy;
    }

    private SeslRecoilModifierElement(InteractionSource interactionSource, boolean z, float f, long j, Shape shape, PaddingValues paddingValues, SeslFeedbackAlpha seslFeedbackAlpha, SeslRecoilDrawStrategy seslRecoilDrawStrategy) {
        this.interactionSource = interactionSource;
        this.enabled = z;
        this.scale = f;
        this.feedbackColor = j;
        this.feedbackShape = shape;
        this.feedbackMargin = paddingValues;
        this.feedbackAlpha = seslFeedbackAlpha;
        this.drawStrategy = seslRecoilDrawStrategy;
    }
}

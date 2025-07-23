package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import com.samsung.sesl.compose.foundation.interaction.SeslInteractionAwareModifierNode;
import com.samsung.sesl.compose.foundation.interaction.SeslInteractionState;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslRecoilNode extends DelegatingNode implements DrawModifierNode, CompositionLocalConsumerModifierNode {
    public SeslRecoilDrawStrategy drawStrategy;
    public boolean enabled;
    public Function0 feedbackAlpha;
    public ColorProducer feedbackColor;
    public PaddingValues feedbackMargin;
    public final SeslFeedbackNode feedbackNode;
    public Shape feedbackShape;
    public final SeslInteractionAwareModifierNode interactionAwareModifierNode;
    public InteractionSource interactionSource;
    public SeslInteractionState interactionState;
    public float scale;
    public final Animatable scaleAnimatable;

    public SeslRecoilNode(InteractionSource interactionSource, boolean z, float f, Shape shape, ColorProducer colorProducer, PaddingValues paddingValues, Function0 function0, SeslRecoilDrawStrategy seslRecoilDrawStrategy) {
        InteractionSource interactionSource2;
        SeslFeedbackNode seslFeedbackNode;
        this.enabled = z;
        this.scale = f;
        this.feedbackShape = shape;
        this.feedbackColor = colorProducer;
        this.feedbackMargin = paddingValues;
        this.feedbackAlpha = function0;
        this.drawStrategy = seslRecoilDrawStrategy;
        SeslInteractionState.Companion.getClass();
        this.interactionState = SeslInteractionState.None;
        this.interactionAwareModifierNode = new SeslInteractionAwareModifierNode(interactionSource, new SeslRecoilNode$$ExternalSyntheticLambda2(this, 0));
        Shape shape2 = this.feedbackShape;
        if (shape2 != null) {
            interactionSource2 = interactionSource;
            seslFeedbackNode = new SeslFeedbackNode(interactionSource2, new SeslRecoilNode$$ExternalSyntheticLambda3(this, 0), new SeslRecoilNode$$ExternalSyntheticLambda3(shape2, 2), this.feedbackColor, this.feedbackAlpha, new SeslRecoilNode$$ExternalSyntheticLambda3(this, 1));
        } else {
            interactionSource2 = interactionSource;
            seslFeedbackNode = null;
        }
        this.feedbackNode = seslFeedbackNode;
        this.interactionSource = interactionSource2;
        this.scaleAnimatable = AnimatableKt.Animatable(1.0f, 0.01f);
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        if (this.enabled) {
            this.drawStrategy.draw$foundation_release(layoutNodeDrawScope, new SeslRecoilNode$$ExternalSyntheticLambda0(this), new SeslRecoilNode$$ExternalSyntheticLambda2(this, 1));
        } else {
            layoutNodeDrawScope.drawContent();
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        delegate(this.interactionAwareModifierNode);
        SeslFeedbackNode seslFeedbackNode = this.feedbackNode;
        if (seslFeedbackNode != null) {
            delegate(seslFeedbackNode);
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        undelegate(this.interactionAwareModifierNode);
        SeslFeedbackNode seslFeedbackNode = this.feedbackNode;
        if (seslFeedbackNode != null) {
            undelegate(seslFeedbackNode);
        }
    }
}
